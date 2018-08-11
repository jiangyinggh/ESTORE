package xyz.beend.estore.service.impl;

import xyz.beend.estore.common.bean.Customer;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.common.util.MD5;
import xyz.beend.estore.dao.ICustomerDao;
import xyz.beend.estore.service.interfaces.ICustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户的业务逻辑
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Service("customerService")
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public void addCustomer(Customer customer) throws EstoreCommonException {
        Customer c = customerDao.findCustomerByName(customer.getName());
        if (c != null || customer == null) {
            throw EstoreCommonException.getException(401);
        }
        customer.setPassword(MD5.getMD5Str(customer.getPassword()));
        customerDao.saveCustomer(customer);
    }

    @Override
    public Customer findCustomer(String name, String password) throws EstoreCommonException {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            throw EstoreCommonException.getException(401);
        }

        Customer customer = customerDao.findCustomerByName(name);

        if (customer == null || !customer.getPassword().equals(MD5.getMD5Str(password)) ) {
            throw EstoreCommonException.getException(402);
        }
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) throws EstoreCommonException {
        if (customer == null) {
            throw EstoreCommonException.getException(401);
        }
        customer.setPassword(MD5.getMD5Str(customer.getPassword()));
        customerDao.updateCustomer(customer);
    }

    @Override
    public Customer findCustomer(String name) throws EstoreCommonException {
        if(StringUtils.isBlank(name)){
            throw EstoreCommonException.getException(401);
        }
        return customerDao.findCustomerByName(name);
    }
}
