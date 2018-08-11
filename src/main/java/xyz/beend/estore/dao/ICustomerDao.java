package xyz.beend.estore.dao;


import xyz.beend.estore.common.bean.Customer;

/**
 * 对用户的持久化操作接口
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public interface ICustomerDao {

	/**
	 * 按用户名字查找用户
	 * @param name
	 * @return
	 */
	Customer findCustomerByName(String name);

	/**
	 * 添加新用户
	 * @param customer
	 */
	void saveCustomer(Customer customer);

	/**
	 * 更新用户信息
	 * @param customer
	 */
	void updateCustomer(Customer customer);
}
