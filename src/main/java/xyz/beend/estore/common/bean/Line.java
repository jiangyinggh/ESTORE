package xyz.beend.estore.common.bean;

import java.io.Serializable;

/**
 * 订单项
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 * */
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;        //订单项的编号
	private Integer num;	//数量
	/**
	 * 关联关系 -- 多对一  对应一个订单
	 * */
	private Long orderId;
	/**
	 * 关联关系 -- 多对一  对应一类书
	 * */
	private Book book;

	private Long customerId;
	
	public Line(){
		
	}
	public Line(Long id, Integer num) {
		this.id = id;
		this.num = num;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public double getCost(){
	    return book.getPrice()*num;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
