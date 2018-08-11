package xyz.beend.estore.web.controller;

import xyz.beend.estore.common.bean.Book;
import xyz.beend.estore.common.bean.Customer;
import xyz.beend.estore.common.bean.Line;
import xyz.beend.estore.common.bean.ShoppingCar;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.common.util.ResponseResult;
import xyz.beend.estore.service.interfaces.IBookService;
import xyz.beend.estore.service.interfaces.ILineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 处理购物车相关请求
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Controller
@RequestMapping("/shoppingCar")
public class ShoppingCarController {

    @Autowired
    private ILineService lineService;

    @Autowired
    private IBookService bookService;

    /**
     * 获取购物车信息和用户登录信息
     * @param session
     * @return 购物车信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object getShoppingCar(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        List<Object> objects = new ArrayList<>();
        if (customer == null) {
            objects.add(false);
        }else {
            objects.add(true);
        }
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if(shoppingCar == null){
            objects.add(null);
            return ResponseResult.ok(objects);
        }
        Map<Long, Line> mapLines = shoppingCar.getLines();
        Collection<Line> lines = mapLines.values();
        objects.add(lines);
        return ResponseResult.ok(objects);
    }

    /**
     * 修改商品数量
     *
     * @param productId 商品编号
     * @param productNum 商品数量
     * @param session
     * @return 购物车信息
     */
    @ResponseBody
    @RequestMapping(value = "/{productId}/{productNum}",method = RequestMethod.PUT)
    public Object updateLineNum(@PathVariable Long productId, @PathVariable Integer productNum, HttpSession session){
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        Map<Long, Line> lines = shoppingCar.getLines();
        Line line = lines.get(productId);
        try {
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer != null) {
                lineService.updateCartLineNum(line.getId(), productNum);
            }
            line.setNum(productNum);
            return ResponseResult.ok(lines.values());
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "修改失败");
        }
    }

    /**
     * 处理加购请求
     *
     * @param productId 商品编号
     * @param session
     * @return 购物车信息
     */
    @ResponseBody
    @RequestMapping(value = "/{productId}", method = RequestMethod.POST)
    public Object addProductToCart(@PathVariable Long productId, HttpSession session){
        Book book = null;
        try {
            book = bookService.findBookById(productId);
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "fail");
        }

        Line line = new Line();
        line.setBook(book);
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if(shoppingCar == null){
            shoppingCar = new ShoppingCar();
            session.setAttribute("shoppingCar",shoppingCar);
        }
        //是否是新加购的商品
        boolean isNewLine = shoppingCar.add(line);
        try {
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer != null) {  //用户存在，则把购物车信息保存到数据库
                if (isNewLine) {
                    line.setCustomerId(customer.getId());
                    lineService.addCartLine(line);
                } else {
                    lineService.updateCartLineNum(line.getId(), line.getNum());
                }
            }
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "fail");
        }
        return ResponseResult.ok();
    }

    /**
     * 删除购物车里的商品
     * @param productId 商品编号
     * @param session
     * @return 购物车信息
     */
    @ResponseBody
    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public Object deleteShoppingCarLine(@PathVariable Long productId, HttpSession session){
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        try {
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer != null) {
                lineService.removeCartLine(customer.getId(), productId);
            }
            shoppingCar.delete(productId);
            Map<Long, Line> lines = shoppingCar.getLines();
            return ResponseResult.ok(lines.values());
        } catch (EstoreCommonException e) {
            return ResponseResult.build(500, "删除失败");
        }
    }

    /**
     * 清空购物车
     * @param session
     * @return 购物车信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object clearShoppingCar(HttpSession session){
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if (shoppingCar != null) {
            try {
                Customer customer = (Customer) session.getAttribute("customer");
                if (customer != null) {
                    lineService.removeCartLine(customer.getId());
                }
                shoppingCar.clear();
            } catch (EstoreCommonException e) {
                return ResponseResult.build(500, "清除失败");
            }
        }
        return ResponseResult.ok();
    }

    /**
     * 获取购物车和用户信息
     * @param session
     * @return 购物车和用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public Object getUserinfoAndShoppingCar(HttpSession session){
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null){
            return ResponseResult.build(401, "您还未登录");
        }
        ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
        if (shoppingCar == null || shoppingCar.getLines().isEmpty()) {
            return ResponseResult.build(404, "您还未加购任何商品");
        }
        List<Object> customerAndShoppingCar = new ArrayList<>();
        customerAndShoppingCar.add(customer);
        customerAndShoppingCar.add(shoppingCar.getLines().values());
        return ResponseResult.ok(customerAndShoppingCar);
    }
}
