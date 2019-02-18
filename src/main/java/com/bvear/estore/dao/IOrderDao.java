package com.bvear.estore.dao;


import com.bvear.estore.common.bean.Order;
import com.bvear.estore.common.bean.PayStatus;

import java.util.List;

/**
 * 对订单的持久化操作
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public interface IOrderDao {

    /**
     * 添加订单
     * @param order
     */
	void saveOrder(Order order);

    /**
     * 通过订单编号和用户编号查找订单详细
     * @param orderId
     * @param customerId
     * @return
     */
	Order findOrderDetail(Long orderId, Long customerId);

    /**
     * 通过订单id查找订单简况
     * @param id
     * @return
     */
    Order findOrderSimpleById(Long id);

    /**
     * 删除订单
     * @param id
     */
	void deleteOrder(Long id);

    /**
     * 按用户id查找订单
     * @param id
     * @return
     */
    List<Order> findOrdersByCustomerId(Long id);

    /**
     * 修改订单支付状态
     * @param orderId
     * @param payStatus
     */
    void updateOrderPayStatus(Long orderId, PayStatus payStatus);
}
