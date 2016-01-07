package com.xluo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xluo.po.Permission;
import com.xluo.po.User;
import com.xluo.util.DBUtil;

public class PermissionDao extends BaseDao<Permission> {

	public List<Permission> getLoginPermission(User user) throws SQLException {

		String rootPermissionSql = "select * from permission where id in ("
				+ "select permissionId from role_permission where roleId in ("
				+ "select roleId from user_role where userId = ?)) order by `index`";

		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		return qr.query(conn, rootPermissionSql, new BeanListHandler<Permission>(Permission.class), user.getId());

	}

	public List<Permission> getChildPermission(String parentId) throws SQLException {

		String sql = "select * from permission where parentId = ? order by `index`";
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		return qr.query(conn, sql, new BeanListHandler<Permission>(Permission.class), parentId);

	}

}
