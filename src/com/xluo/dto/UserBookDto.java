package com.xluo.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.xluo.po.Book;
import com.xluo.po.User;

public class UserBookDto {

	private String username;
	private String bookname;
	private String isreturn;
	
	public static List<UserBookDto> changeToDto(User user, List<Book> books){
		
		List<UserBookDto> dto = new ArrayList<UserBookDto>();
		for(Book book : books){
			UserBookDto d = new UserBookDto();
			d.setUsername(user.getUsername());
			d.setIsreturn("N");
			d.setBookname(book.getBookname());
			dto.add(d);
		}
		return dto;
		
	}
	
	public static Object[][] changeToArray(List<UserBookDto> dto, String[] attributes){
		Object[][] result = new Object[dto.size()][attributes.length];
		for(int i = 0; i < dto.size(); i++){
			UserBookDto b = dto.get(i);
			for(int j = 0; j < attributes.length; j++){
				try {
					result[i][j] = b.getClass().getMethod(createGetMethod(attributes[j]), null).invoke(b, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} 
			}
		}
		return result;
	}
	
	private static String createGetMethod(String attribute){
		return "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1); 
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getBookname() {
		return bookname;
	}
	
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getIsreturn() {
		return isreturn;
	}

	public void setIsreturn(String isreturn) {
		this.isreturn = isreturn;
	}
	
}
