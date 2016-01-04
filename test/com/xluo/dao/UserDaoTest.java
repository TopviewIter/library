package com.xluo.dao;

import java.sql.SQLException;

import org.junit.Test;

import com.xluo.po.User;
import com.xluo.util.UUIDUtil;

public class UserDaoTest {

	private UserDao userDao = new UserDao();

	@Test
	public void testInsert() {
		try {
			userDao.insert(new String[] { "id", "username", "password", "email" },
					new Object[] { UUIDUtil.getUUID(), "xiaoming", "xiaoming", "110@qq.com" });
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectOne() {
		try {
			User user = userDao.selectOne(new String[] { "password", "email" },
					new Object[] { "xiaoming", "110@qq.com" }, new String[] { "username" });
			System.out.println(user.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
