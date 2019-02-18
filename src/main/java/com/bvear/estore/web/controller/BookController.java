package com.bvear.estore.web.controller;

import com.bvear.estore.common.bean.Book;
import com.bvear.estore.common.exception.EstoreCommonException;
import com.bvear.estore.common.util.ResponseResult;
import com.bvear.estore.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理书本信息请求
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    /**
     * 获取书的详情信息
     * @param bookId  书本编号
     * @return 书本信息
     */
    @ResponseBody
    @RequestMapping("/{bookId}")
    public Object getBook(@PathVariable Long bookId){
        try {
            Book book = bookService.findBookById(bookId);
            return ResponseResult.ok(book);
        } catch (EstoreCommonException e) {
            e.printStackTrace();
            return ResponseResult.build(e.getErrcode(),"查询失败：" + e.getMessage());
        }
    }
}
