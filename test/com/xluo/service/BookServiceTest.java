package com.xluo.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.xluo.dao.BookDao;
import com.xluo.po.Book;

public class BookServiceTest {

	private BookDao bookDao = new BookDao();
	private BookService bookService = new BookService();

	@Test
	public void testChangeToArray() {
		
		try {
			List<Book> books = bookDao.selectListOrder(null, null, 
					new String[]{ "bookname", "simpleInfo", "author", "account" }, 
					new String[]{"publishTime"}, true);;
			Object[][] results = bookService.changeToArray(books, new String[]{ "author", "bookname" });
			for(Object[] result : results){
				for(Object s : result){
					System.out.println(s);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
