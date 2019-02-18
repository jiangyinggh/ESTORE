package com.bvear.estore.web.controller;

import com.bvear.estore.common.bean.Book;
import com.bvear.estore.common.bean.Customer;
import com.bvear.estore.common.exception.EstoreCommonException;
import com.bvear.estore.common.util.ResponseResult;
import com.bvear.estore.service.interfaces.IBookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理首页请求
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Controller
public class IndexController {

    @Autowired
    private IBookService bookService;

    /**
     * 获取首页中对应页的书的列表数据
     * @param page 对应的页数
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/index/{page}")
    public Object getIndexData(@PathVariable int page, HttpSession session){
        try {
            // 3为每页显示书的记录数
            PageInfo<Book> pageInfo = bookService.findAllBookWithPage(page, 3);
            Customer customer = (Customer) session.getAttribute("customer");
            List<Object> indexData = new ArrayList<>();
            if(customer == null){
                indexData.add(null);
            }else {
                indexData.add(customer.getName());
            }
            indexData.add(pageInfo);
            return ResponseResult.ok(indexData);
        } catch (EstoreCommonException e) {
            return ResponseResult.build(400, "请求失败，请刷新页面");
        }
    }
}
