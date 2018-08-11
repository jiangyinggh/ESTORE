package xyz.beend.estore.service.impl;

import xyz.beend.estore.common.bean.Line;
import xyz.beend.estore.common.bean.Order;
import xyz.beend.estore.common.bean.PayStatus;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.dao.IOrderDao;
import xyz.beend.estore.service.interfaces.ILineService;
import xyz.beend.estore.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 订单的业务逻辑
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Override
    public void addOrder(ILineService lineService, Order order, Collection<Line> lines) throws EstoreCommonException {
        if(order == null || lines == null) {
            throw EstoreCommonException.getException(401);
        }
        orderDao.saveOrder(order);
        lineService.addOrderLine(order.getId(),lines);
    }

    @Override
    public Order findOrderDetail(Long orderId, Long customerId) throws EstoreCommonException {
        if (orderId == null || customerId == null) {
            throw EstoreCommonException.getException(401);
        }
        Order order = orderDao.findOrderDetail(orderId, customerId);
        return order;
    }

    @Override
    public Order findOrderSimple(Long orderId) throws EstoreCommonException {
        if (orderId == null) {
            throw EstoreCommonException.getException(401);
        }
        Order order = orderDao.findOrderSimpleById(orderId);
        return order;
    }

    @Override
    public List<Order> findOrderByCustomerId(Long customerId) throws EstoreCommonException {
        if (customerId == null) {
            throw EstoreCommonException.getException(401);
        }
        List<Order> orders = orderDao.findOrdersByCustomerId(customerId);
        return orders;
    }

    @Override
    public void updateOrderPayStatus(Long orderId, PayStatus payStatus) throws EstoreCommonException {
        if(orderId == null || payStatus == null) {
            throw EstoreCommonException.getException(401);
        }
        orderDao.updateOrderPayStatus(orderId,payStatus);
    }

    @Override
    public void deleteOrder(Long id) throws EstoreCommonException {
        if (id == null) {
            throw EstoreCommonException.getException(401);
        }
        orderDao.deleteOrder(id);
    }
}
