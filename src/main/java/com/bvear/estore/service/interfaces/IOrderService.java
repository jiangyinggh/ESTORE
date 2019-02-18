package com.bvear.estore.service.interfaces;

import java.util.Collection;
import java.util.List;

import com.bvear.estore.common.bean.Line;
import com.bvear.estore.common.bean.Order;
import com.bvear.estore.common.bean.PayStatus;
import com.bvear.estore.common.exception.EstoreCommonException;

public interface IOrderService {

    /**
     * 提交订单
     * @param lineService
     * @param order
     * @param lines
     * @throws EstoreCommonException
     */
	void addOrder(ILineService lineService, Order order, Collection<Line> lines) throws EstoreCommonException;

    /**
     * 删除订单
     * @param orderId
     * @throws EstoreCommonException
     */
	void deleteOrder(Long orderId) throws EstoreCommonException;				//删除订单

    /**
     * 查找订单详细
     * @param orderId
     * @param customerId
     * @return
     * @throws EstoreCommonException
     */
	Order findOrderDetail(Long orderId, Long customerId) throws EstoreCommonException;					//按订单编号查找订单

    /**
     * 查找订单
     * @param orderId
     * @return
     * @throws EstoreCommonException
     */
    Order findOrderSimple(Long orderId) throws EstoreCommonException;

    /**
     * 查找用户所有订单
     * @param customerId
     * @return
     * @throws EstoreCommonException
     */
	List<Order> findOrderByCustomerId(Long customerId) throws EstoreCommonException;	//按用户id查找订单

    /**
     * 更新支付状态
     * @param orderId
     * @param payStatus
     * @throws EstoreCommonException
     */
    void updateOrderPayStatus(Long orderId, PayStatus payStatus) throws EstoreCommonException;
}
