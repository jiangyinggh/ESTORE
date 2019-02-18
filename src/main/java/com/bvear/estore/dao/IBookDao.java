package com.bvear.estore.dao;

import java.util.List;


import com.bvear.estore.common.bean.Book;

/**
 * 对书的持久化操作接口
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public interface IBookDao {

	/**
	 * 查询所有的书，并存于list集合
	 * @return
	 */
	List<Book> queryAllBook();

	/**
	 * 按书的id查找
	 * @param id
	 * @return
	 */
	Book queryBookById(Long id);
}
