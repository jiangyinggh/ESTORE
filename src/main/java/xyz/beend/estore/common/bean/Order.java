package xyz.beend.estore.common.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 订单
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 * */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;              //订单编号
	private Double cost;          //价钱（总价）
	private Date orderDate;       //订单创建时间
	private PayStatus payStatus;  //订单支付状态
	private String payway;        //付款方式

	/**
	 * 关联关系 - 一对多 : 对应多个订单项
	 * */
	private Set<Line> lines = new HashSet<>();
	/**
	 * 关联关系 - 多对一 : 对应一个顾客
	 * */
	private Long customerId;
	
	public Order(){
		
	}
	public Order(Long id, Double cost, Date orderDate) {
		this.id = id;
		this.cost = cost;
		this.orderDate = orderDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Set<Line> getLines() {
		return lines;
	}
	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }
}
