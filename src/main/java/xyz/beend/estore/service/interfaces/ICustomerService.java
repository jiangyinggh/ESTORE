package xyz.beend.estore.service.interfaces;

import xyz.beend.estore.common.bean.Customer;
import xyz.beend.estore.common.exception.EstoreCommonException;

public interface ICustomerService {

    /**
     * 添加用户
     * @param customer
     * @throws EstoreCommonException
     */
	void addCustomer(Customer customer) throws EstoreCommonException;				//注册用户

    /**
     * 找出用户名与密码一致的用户
     * @param name
     * @param password
     * @return Customer
     * @throws EstoreCommonException
     */
	Customer findCustomer(String name, String password) throws EstoreCommonException;	//用户登录

    /**
     * 更新用户信息
     * @param customer
     * @throws EstoreCommonException
     */
	void updateCustomer(Customer customer) throws EstoreCommonException;		//更新用户信息

    /**
     * 根据用户名查找用户
     * @param name
     * @return Customer
     * @throws EstoreCommonException
     */
    Customer findCustomer(String name) throws EstoreCommonException;
}
