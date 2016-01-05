package com.xluo.util;

import org.junit.Test;

public class SQLUtilTest {

	@Test
	public void testGetInsertSql() {

		System.out.println(SQLUtil.getInsertSql("user", new String[] { "name", "email" }));

	}

	@Test
	public void testGetSelectSql() {

		System.out.println(SQLUtil.getSelectSql("user", new String[] { "name", "email" }, new String[] { "id" }));

	}

	@Test
	public void testGetSelectSqlOrder() {

		System.out.println(SQLUtil.getSelectSqlOrder("user", new String[] { "name", "email" }, new String[] { "id" },
				new String[] { "name" }, true));

	}

}
