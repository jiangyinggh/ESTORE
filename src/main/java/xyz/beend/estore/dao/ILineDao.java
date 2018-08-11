package xyz.beend.estore.dao;


import xyz.beend.estore.common.bean.Line;

import java.util.List;

/**
 * 对订单项或购物车项的持久化操作
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public interface ILineDao {

    /**
     * 保存订单项
     * @param line
     */
    void saveOrderLine(Line line);

    /**
     * 删除订单项
     * @param orderId
     */
    void deleteOrderLineByOrderId(Long orderId);  //

    /**
     * 添加购物车项
     * @param line
     */
    void saveCartLine(Line line);

    /**
     * 更新购物车项的数量
     * @param lineId
     * @param num
     */
    void updateCartLineNum(Long lineId, Integer num);

    /**
     * 根据用户编号清空购物车
     * @param customerId
     */
    void deleteCartLine(Long customerId);

    /**
     * 根据用户编号及书本编号删除购物车项
     * @param customerId
     * @param bookId
     */
    void deleteCartLineByBookId(Long customerId, Long bookId);

    /**
     * 根据用户编号查找用户所有购物车项
     * @param customerId
     * @return
     */
    List<Line> selectCartLine(Long customerId);
}
