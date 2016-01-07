package com.xluo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xluo.po.Book;
import com.xluo.po.User;
import com.xluo.util.DBUtil;
import com.xluo.util.UUIDUtil;

public class BookDao extends BaseDao<Book> {
	
	public boolean isRent(String userId, String bookId) throws SQLException{
		String sql = "select count(*) from user_book where userId = ? and bookId = ? and type = 2";
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		long result = (qr.query(conn, sql, new ScalarHandler<Long>(1), new Object[]{userId, bookId})).intValue();  
		DbUtils.closeQuietly(conn);
		return result >= 1 ? true : false;
	}
	
	public boolean updateUserBook(String userId, String bookId) throws SQLException {
		String sql = "update user_book set type = 3 where userId = ? and bookId = ?";
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		int result = qr.update(conn, sql, new Object[] {userId, bookId});
		DbUtils.closeQuietly(conn);
		return result >= 1 ? true : false;
	}
	
	public boolean insertUserBook(String userId, String bookId) throws SQLException {
		String sql = "insert into user_book(id, userId, bookId, type) values(?, ?, ?, ?)";
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		int result = qr.update(conn, sql, new Object[] { UUIDUtil.getUUID(), userId, bookId, 2 });
		DbUtils.closeQuietly(conn);
		return result == 1 ? true : false;
	}
	
	public List<Book> selectScanBook(User user) throws SQLException {
		return selectBook(user, 1);
	}

	public List<Book> selectRentBook(User user) throws SQLException {
		return selectBook(user, 2);
	}

	private List<Book> selectBook(User user, int type) throws SQLException {

		String sql = "select * from book where id in (select bookId from user_book where userId = ? and type = ?)";
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		return qr.query(conn, sql, new BeanListHandler<Book>(Book.class), new Object[] { user.getId(), type });

	}

}
