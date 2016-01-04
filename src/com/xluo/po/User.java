package com.xluo.po;

import java.util.List;

public class User {

	private String id;
	private String username;
	private String password;
	private String email;
	private List<Book> rentBooks;
	private List<Book> scanBooks;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Book> getRentBooks() {
		return rentBooks;
	}
	
	public void setRentBooks(List<Book> rentBooks) {
		this.rentBooks = rentBooks;
	}
	
	public List<Book> getScanBooks() {
		return scanBooks;
	}
	
	public void setScanBooks(List<Book> scanBooks) {
		this.scanBooks = scanBooks;
	}
	
}
