package com.bvear.estore.common.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 顾客类
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 * */
@Component
@Scope(value = "prototype")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	private Long id;

	/**
	 * 用户名
	 * */
	private String name;

	/**
	 * 密码
	 * */
	private String password;

	/**
	 * 邮编
	 * */
	private String zip;

	/**
	 * 地址
	 * */
	private String address1;
	private String address2;

	/**
	 * 手机号
	 * */
	private String homePhone;
	private String officePhone;
	private String mobilePhone;

	/**
	 * email
	 * */
	private String email;

	/**
	 * 关联关系 - 一对多
	 * */
	private Set<Order> orders = new HashSet<>();
	
	public Customer(){
		
	}

	public Customer(Long id, String name, String password, String zip, String email, Set<Order> orders) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.zip = zip;
		this.email = email;
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", zip='" + zip + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", orders=" + orders +
                '}';
    }
}
