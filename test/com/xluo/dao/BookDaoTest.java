package com.xluo.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.xluo.po.Book;
import com.xluo.po.User;
import com.xluo.util.UUIDUtil;

public class BookDaoTest {

	private BookDao bookDao = new BookDao();

	@Test
	public void testInsert() {
		try {
			bookDao.insert(new String[] { "id", "bookname", "author", "account", "publishTime", "type", "img"},
					new Object[] { UUIDUtil.getUUID(), "oracle", "xiaoming", 2, "2015-01-01", "database", "/door.jpg"});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectList(){
		try {
			List<Book> books = bookDao.selectList(new String[] { "type" }, new Object[] {"database"},
					new String[] { "img", "bookname" });
			for(Book book : books){
				System.out.println(book.getBookname());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectListOrder(){
		try {
			List<Book> books = bookDao.selectListOrder(new String[] { "type" }, new Object[] {"database"},
					new String[] { "img", "bookname", "publishTime" }, new String[]{"publishTime"}, true);
			for(Book book : books){
				System.out.println(book.getPublishTime());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectScanBook() throws SQLException{
		User user = new User();
		user.setId("9bb64ac54e2345bc80ec3d8e680326ad");
		List<Book> books = bookDao.selectScanBook(user);
		if(books != null){
			for(Book book : books){
				System.out.println(book.getBookname());
			}
		}
	}

}
