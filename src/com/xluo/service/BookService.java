package com.xluo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xluo.po.Book;

public class BookService {

	public Object[][] changeToArray(List<Book> books, String[] attributes){
		
		Object[][] result = new Object[books.size()][attributes.length];
		for(int i = 0; i < books.size(); i++){
			Book b = books.get(i);
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
	
	private String createGetMethod(String attribute){
		return "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1); 
	}
	
}
