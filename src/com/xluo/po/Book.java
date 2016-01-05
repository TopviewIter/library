package com.xluo.po;

import java.util.List;

public class Book {

	private String id;
	private String bookname;
	private String author;
	private String account;
	private String publishTime;
	private String type;
	private String img;
	private String simpleInfo;
	private List<User> renter;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBookname() {
		return bookname;
	}
	
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPublishTime() {
		return publishTime;
	}
	
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<User> getRenter() {
		return renter;
	}
	
	public void setRenter(List<User> renter) {
		this.renter = renter;
	}

	public String getSimpleInfo() {
		return simpleInfo;
	}

	public void setSimpleInfo(String simpleInfo) {
		this.simpleInfo = simpleInfo;
	}
	
}
