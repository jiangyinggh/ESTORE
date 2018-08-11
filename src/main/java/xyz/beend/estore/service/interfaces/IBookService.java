package xyz.beend.estore.service.interfaces;

import java.util.List;

import xyz.beend.estore.common.bean.Book;
import xyz.beend.estore.common.exception.EstoreCommonException;
import com.github.pagehelper.PageInfo;

public interface IBookService {

    /**
     *根据页数和每页的行数查找书
     *
     * @throws EstoreCommonException
     */
    PageInfo<Book> findAllBookWithPage(int page, int row) throws EstoreCommonException;

    /**
     * 找出所有的书
     * @return List<Book>
     * @throws EstoreCommonException
     */
	List<Book> listAllBooks() throws EstoreCommonException;

    /**
     * 根据书本编号查找书
     * @param id
     * @return Book
     * @throws EstoreCommonException
     */
	Book findBookById(Long id) throws EstoreCommonException;
}
