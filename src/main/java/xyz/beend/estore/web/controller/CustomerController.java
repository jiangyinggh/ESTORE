package xyz.beend.estore.web.controller;

import xyz.beend.estore.common.bean.Customer;
import xyz.beend.estore.common.bean.Line;
import xyz.beend.estore.common.bean.Order;
import xyz.beend.estore.common.bean.ShoppingCar;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.common.util.ResponseResult;
import xyz.beend.estore.service.interfaces.ICustomerService;
import xyz.beend.estore.service.interfaces.ILineService;
import xyz.beend.estore.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理用户注册、登录、信息修改、退出请求
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ILineService lineService;

    /**
     * 检查用户名是否存在
     * @return
     */
    @RequestMapping(value = "/checkUserName/{username}")
    @ResponseBody
    public Object checkUserName(@PathVariable String username){
        try {
            Customer customer = customerService.findCustomer(username);
            if(customer == null){
                return ResponseResult.ok();
            }
            return ResponseResult.build(500, "fail");
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "fail");
        }
    }

    /**
     * 处理注册请求
     * @param customer
     * @return 是否成功信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object register(Customer customer){
        try {
            customerService.addCustomer(customer);
            return ResponseResult.ok();
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "fail");
        }
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object getUserinfo(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return ResponseResult.build(401, "您的登录已过期");
        }
        return ResponseResult.ok(customer);
    }

    /**
     * 处理登录请求
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return 是否登录成功信息
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password, HttpSession session){
        Customer customer = null;
        try {
            customer = customerService.findCustomer(username, password);
        } catch (EstoreCommonException e) {
            return ResponseResult.build(e.getErrcode(), e.getMessage());
        }

        //获取上一个用户
        Customer oldCustomer = (Customer) session.getAttribute("customer");

        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if (shoppingCar == null) {
            shoppingCar = new ShoppingCar();
            session.setAttribute("shoppingCar", shoppingCar);
        }
        //将上一个用户购物车清除
        if (oldCustomer != null) shoppingCar.clear();

        //将新登录的用户放入session中
        session.setAttribute("customer", customer);

        //将用户订单存入session
        List<Order> orders = null;
        try {
            orders = orderService.findOrderByCustomerId(customer.getId());
        } catch (EstoreCommonException e) {
            orders = new ArrayList<>();
        }
        session.setAttribute("orders", orders);

        //得到登录之前的购物车
        Map<Long, Line> lines = shoppingCar.getLines();

        ResponseResult responseResult = ResponseResult.ok();

        List<Line> oldLines = null;
        try {
            //得到用户以前保存的购物车
            oldLines = lineService.queryCartLine(customer.getId());
        } catch (EstoreCommonException e) {
            responseResult.setMsg("获取购物车失败");
        }

        //创建新的购物车，并把用户以前保存的购物车信息保存到新的购物车
        ShoppingCar newShoppingCar = new ShoppingCar();
        if (oldLines != null && !oldLines.isEmpty())
            newShoppingCar.addLines(oldLines);

        //将登录之前的购物车记录添加到数据库和新购物车中
        if (lines != null && !lines.isEmpty()) {
            for (Line line : lines.values()) {
                //合并登录请后的购物车,isNewLine为是否是新的购物车项
                boolean isNewLine = newShoppingCar.add(line);
                try {
                    if (isNewLine) {
                        line.setCustomerId(customer.getId());
                        lineService.addCartLine(line);
                    } else {
                        lineService.updateCartLineNum(line.getId(), line.getNum());
                    }
                } catch (EstoreCommonException e) {
                    responseResult.setMsg(responseResult.getMsg()+",合并购物车失败");
                }
            }
        }
        //将整合后的购物车放入session中
        session.setAttribute("shoppingCar", newShoppingCar);
        return responseResult;
    }

    /**
     * 处理用户信息修改请求
     *
     * @param customer
     * @param session
     * @return 是否修改成功信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Object modifyUserinfo(Customer customer, HttpSession session) {
        Customer oldCustomer = (Customer)session.getAttribute("customer");
        if (oldCustomer != null) {
            customer.setId(oldCustomer.getId());
            try {
                customerService.updateCustomer(customer);
                session.setAttribute("customer",customer);
            } catch (EstoreCommonException e) {
                return ResponseResult.build(500, "修改失败");
            }
            return ResponseResult.ok();
        }
        return ResponseResult.build(401, "您的登录已过期");
    }

    /**
     * 处理用户退出请求
     *
     * @param session
     * @return 重定向到首页
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer != null) {
            session.invalidate();
        }
        return "redirect:/index.html";
    }
}
