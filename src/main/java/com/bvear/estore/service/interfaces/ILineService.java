package com.bvear.estore.service.interfaces;

import com.bvear.estore.common.bean.Line;
import com.bvear.estore.common.exception.EstoreCommonException;

import java.util.Collection;
import java.util.List;

public interface ILineService {

    /**
     * 添加订单项
     * @param orderId
     * @param lines
     * @throws EstoreCommonException
     */
    void addOrderLine(Long orderId, Collection<Line> lines) throws EstoreCommonException; //添加订单明细

    /**
     * 通过订单编号删除订单项
     * @param orderId
     * @throws EstoreCommonException
     */
    void deleteOrderLineByOrderId(Long orderId) throws EstoreCommonException;     //按订单id删除订单明细

    /**
     * 添加购物车项
     * @param line
     * @throws EstoreCommonException
     */
    void addCartLine(Line line) throws EstoreCommonException;

    /**
     * 通过用户编号删除购物车项
     * @param customerId
     * @throws EstoreCommonException
     */
    public void removeCartLine(Long customerId) throws EstoreCommonException;

    /**
     * 根据用户编号和书本编号删除购物车项
     * @param customerId
     * @param bookId
     * @throws EstoreCommonException
     */
    void removeCartLine(Long customerId, Long bookId) throws EstoreCommonException;

    /**
     * 修改购物车项数量
     * @param lineId
     * @param num
     * @throws EstoreCommonException
     */
    void updateCartLineNum(Long lineId, Integer num) throws EstoreCommonException;

    /**
     * 查找购物车
     * @param customerId
     * @return
     * @throws EstoreCommonException
     */
    List<Line> queryCartLine(Long customerId) throws EstoreCommonException;
}
