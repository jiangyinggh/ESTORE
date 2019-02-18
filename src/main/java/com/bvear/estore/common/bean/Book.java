package com.bvear.estore.common.bean;

import java.io.Serializable;

/**
 * 书
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 * */
public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;         //书本编号
	private String name;	 //书名
	private Double price;	 //价钱
	private String author;   //作者
	private String publish;  //出版社
	private String isbn;	 //书号
	private Integer page;    //页数
	private String type;     //类型
	private String descUrl;  //简介描述的文本地址
	private String picUrl;   //图片地址
	
	public Book(){
		
	}

	public Book(Long id, String name, Double price, String author, String publish, String isbn, Integer page, String type, String descUrl, String picUrl) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.author = author;
		this.publish = publish;
		this.isbn = isbn;
		this.page = page;
		this.type = type;
		this.descUrl = descUrl;
		this.picUrl = picUrl;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescUrl() {
		return descUrl;
	}

	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
