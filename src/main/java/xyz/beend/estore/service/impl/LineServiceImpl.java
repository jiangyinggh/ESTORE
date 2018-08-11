package xyz.beend.estore.service.impl;

import xyz.beend.estore.common.bean.Line;
import xyz.beend.estore.common.exception.EstoreCommonException;
import xyz.beend.estore.dao.ILineDao;
import xyz.beend.estore.service.interfaces.ILineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 订单项和购物车项的业务逻辑
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */

@Service("lineService")
public class LineServiceImpl implements ILineService {

    @Autowired
    private ILineDao lineDao;

    @Override
    public void addOrderLine(Long orderId, Collection<Line> lines) throws EstoreCommonException {
        for (Line line : lines) {
            line.setOrderId(orderId);
            lineDao.saveOrderLine(line);
        }
    }

    @Override
    public void deleteOrderLineByOrderId(Long orderId) throws EstoreCommonException {
        if(orderId == null) {
            throw EstoreCommonException.getException(401);
        }
        lineDao.deleteOrderLineByOrderId(orderId);
    }

    @Override
    public void addCartLine(Line line) throws EstoreCommonException {
        if(line == null) {
            throw EstoreCommonException.getException(401);
        }
        lineDao.saveCartLine(line);
    }

    @Override
    public void removeCartLine(Long customerId) throws EstoreCommonException {
        if (customerId == null) {
            throw EstoreCommonException.getException(401);
        }
        lineDao.deleteCartLine(customerId);
    }

    @Override
    public void removeCartLine(Long customerId, Long bookId) throws EstoreCommonException {
        if(customerId == null || bookId == null) {
            throw EstoreCommonException.getException(401);
        }
        lineDao.deleteCartLineByBookId(customerId,bookId);
    }

    @Override
    public void updateCartLineNum(Long lineId, Integer num) throws EstoreCommonException {
        if(lineId == null) {
            throw EstoreCommonException.getException(401);
        }
        lineDao.updateCartLineNum(lineId,num);
    }

    @Override
    public List<Line> queryCartLine(Long customerId) throws EstoreCommonException {
        if(customerId == null) {
            throw EstoreCommonException.getException(401);
        }
        List<Line> lines = lineDao.selectCartLine(customerId);
        return lines;
    }
}
