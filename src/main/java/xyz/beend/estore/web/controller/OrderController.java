package xyz.beend.estore.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import xyz.beend.estore.common.bean.*;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.common.util.AlipayConfig;
import xyz.beend.estore.common.util.ResponseResult;
import xyz.beend.estore.service.interfaces.ILineService;
import xyz.beend.estore.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 处理订单相关请求
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Controller
@RequestMapping("/superUser/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ILineService lineService;

    /**
     * 获取所有订单
     * @param session
     * @return 将json格式订单返回给前端
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object getAllOrders(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null) {   //判断用户登录是否过期
            return ResponseResult.build(401,"您的登录已过期");
        }
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        return ResponseResult.ok(orders);
    }

    /**
     * 处理提交订单请求
     *
     * @param payway 支付方式
     * @param session
     * @return 是否提交成功的结果
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object confirmOrder(String payway, HttpSession session) {
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if(shoppingCar == null || shoppingCar.getLines().isEmpty()) {      //购物车不存在或者为空
            return ResponseResult.build(404,"购物车为空");
        }else{
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer == null) {  //判断用户登录是否过期
                return ResponseResult.build(401,"您的登录已过期");
            }
            Map<Long, Line> lines = shoppingCar.getLines();
            Collection<Line> collection = lines.values();

            //生成订单
            Order order = new Order();
            order.setCost(shoppingCar.getCost());
            order.setOrderDate(new Date());
            order.setPayStatus(PayStatus.NotPaid);
            order.setPayway(payway);
            order.setCustomerId(customer.getId());

            try {
                orderService.addOrder(lineService, order, collection);
                List<Order> orders = (List<Order>) session.getAttribute("orders");
                if(orders == null){
                    orders = new ArrayList<>();
                }
                orders.add(order);//添加到session的orders对象里
                lineService.removeCartLine(customer.getId());
                shoppingCar.clear();    //清空购物车
                return ResponseResult.ok();
            } catch (EstoreCommonException e) {
                return ResponseResult.build(500, "生成订单失败");
            }
        }
    }

    /**
     * 获取订单项明细
     *
     * @param orderId 订单id
     * @param session
     * @return json格式的订单明细信息
     */
    @ResponseBody
    @RequestMapping(value = "/{orderId}",method = RequestMethod.GET)
    public Object getOrderinfo(@PathVariable Long orderId, HttpSession session){
        try {
            Customer customer = (Customer) session.getAttribute("customer");
            if(customer == null) {
                return ResponseResult.build(401, "您的登录已过期");
            }

            //根据订单编号和用户编号查找订单，防止用户查看到其他用户订单
            Order order = orderService.findOrderDetail(orderId, customer.getId());
            if (order == null) {
                return ResponseResult.build(404, "您请求的资源不存在");
            }
            List<Object> userinfoAndOrderinfo = new ArrayList<>();
            userinfoAndOrderinfo.add(customer);
            userinfoAndOrderinfo.add(order);
            return ResponseResult.ok(userinfoAndOrderinfo);
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "服务器发生一个错误");
        }
    }

    /**
     * 删除订单
     *
     * @param orderId 订单id
     * @return 删除之后的所有订单
     */
    @ResponseBody
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public Object deleteOrder(@PathVariable Long orderId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null) {
            return ResponseResult.build(401, "您的登录已过期");
        }
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        int orderIndex = -1;
        boolean isOrder = false;
        for (Order order : orders) {
            orderIndex++;
            if(orderId == order.getId()){
                isOrder = true;
                break;
            }
        }
        if (isOrder) {
            try {
                //先删除订单项，再删除订单
                lineService.deleteOrderLineByOrderId(orderId);
                orderService.deleteOrder(orderId);
            } catch (EstoreCommonException e) {
                return ResponseResult.build(500, "删除订单失败");
            }
            //更新用户的订单
            orders.remove(orderIndex);
            return ResponseResult.ok(orders);
        }
        return ResponseResult.build(404, "您请求的资源不存在");
    }

    /**
     * 处理支付请求，响应支付页面
     *
     * @param orderIndex
     * @param session
     * @param response
     */
    @RequestMapping("/payForOrder")
    public void payForOrder(Integer orderIndex, HttpSession session, HttpServletResponse response) throws IOException, AlipayApiException{
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        Order order = orders.get(orderIndex);
        //判断订单是否存在或支付
        if(order == null || order.getPayStatus() == PayStatus.Paid){
            response.sendRedirect("/superUser/orderList.html");
            return;
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getId().toString();
        //付款金额，必填
        String total_amount = order.getCost().toString();
        //订单名称，必填
        String subject = "[ESTORE]正版书籍";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        PrintWriter out = response.getWriter();
        //输出
        out.write(result);
        out.flush();
        out.close();
    }

    /**
     * 支付结果同步通知
     *
     * @param request
     * @return
     */
    @RequestMapping("/returnForPay")
    public String returnForPay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException{
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);

        //——请在这里编写您的程序（以下代码仅作参考）——
        try {
            if(signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                //String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

                //out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);

                System.out.println("cost:"+total_amount);

                long orderId = Long.parseLong(out_trade_no);
                Order order = orderService.findOrderSimple(orderId);
                if(order != null && Double.parseDouble(total_amount) == order.getCost() && order.getPayStatus() == PayStatus.NotPaid){
                    order.setPayStatus(PayStatus.Paid);
                    orderService.updateOrderPayStatus(order.getId(),order.getPayStatus());
                    HttpSession session = request.getSession();
                    Customer customer = (Customer) session.getAttribute("customer");
                    List<Order> orders = orderService.findOrderByCustomerId(customer.getId());
                    session.setAttribute("orders", orders);
                }
            }else {
                System.out.println("验签失败");
            }
        }catch (EstoreCommonException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以上代码仅作参考）——
        return "redirect:/superUser/orderList.html";
    }

    /**
     * 支付结果异步通知
     *
     * @param request
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @RequestMapping("/notifyForPay")
    public void notifyForPay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        try {
            if(signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                //String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

                //out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);

                long orderId = Long.parseLong(out_trade_no);
                Order order = orderService.findOrderSimple(orderId);
                if(order != null && total_amount.equals(order.getCost().toString()) && order.getPayStatus() == PayStatus.NotPaid){
                    order.setPayStatus(PayStatus.Paid);
                    orderService.updateOrderPayStatus(order.getId(),order.getPayStatus());
                    HttpSession session = request.getSession();
                    Customer customer = (Customer) session.getAttribute("customer");
                    List<Order> orders = orderService.findOrderByCustomerId(customer.getId());
                    session.setAttribute("orders", orders);
                }
            }else {
                //out.println("验签失败");
                System.out.println("验签失败");
            }
        }catch (EstoreCommonException e) {
            e.printStackTrace();
        }
    }
}
