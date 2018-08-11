package xyz.beend.estore.service.impl;

import xyz.beend.estore.common.bean.Book;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.dao.IBookDao;
import xyz.beend.estore.service.interfaces.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 书的业务逻辑
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Service("bookService")
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookDao bookDao;

    @Override
    public PageInfo<Book> findAllBookWithPage(int page, int row) throws EstoreCommonException {
        PageHelper.startPage(page, row);
        List<Book> books = bookDao.queryAllBook();
        return new PageInfo<>(books);
    }

    @Override
    public List<Book> listAllBooks() throws EstoreCommonException {
        List<Book> books = bookDao.queryAllBook();
        return books;
    }

    @Override
    public Book findBookById(Long id) throws EstoreCommonException {
        if(id == null){
            throw EstoreCommonException.getException(404);
        }
        Book book = bookDao.queryBookById(id);
        if(book == null){
            throw EstoreCommonException.getException(404);
        }
        return book;
    }
}
