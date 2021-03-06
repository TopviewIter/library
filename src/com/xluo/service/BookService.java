package com.xluo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import com.xluo.po.Book;

public class BookService {

	public Vector<String> changeTypeInVector(List<Book> books){
		Set<String> bookType = new TreeSet<String>();
		Vector<String> result = new Vector<String>();
		for(Book book : books){
			bookType.add(book.getType());
		}
		result.addAll(bookType);
		return result;
	}
	
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

	public Map<String, String> changeToMap(List<Book> books) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		result.put("select-one", null);
		for(Book book : books){
			result.put(book.getBookname() + "-" + book.getAuthor(), book.getId());
		}
		return result;
	}
	
}
