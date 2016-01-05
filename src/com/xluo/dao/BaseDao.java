package com.xluo.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xluo.util.DBUtil;
import com.xluo.util.SQLUtil;

public class BaseDao<T> {

	public void insert(String[] paramName, Object[] paramValue) throws SQLException {
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		String sql = SQLUtil.getInsertSql(getTableName(), paramName);
		System.out.println(sql);
		qr.update(conn, sql, paramValue);
		DbUtils.closeQuietly(conn);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> selectList(String[] paramName, Object[] paramValue, String[] resultName) throws SQLException {
		String sql = SQLUtil.getSelectSql(getTableName(), paramName, resultName);
		return (List<T>) select(sql, paramName, paramValue, resultName, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> selectListOrder(String[] paramName, Object[] paramValue, String[] resultName, String[] orderByParam, boolean isDesc) throws SQLException {
		String sql = SQLUtil.getSelectSqlOrder(getTableName(), paramName, resultName, orderByParam, isDesc);
		return (List<T>) select(sql, paramName, paramValue, resultName, false);
	}
	
	@SuppressWarnings("unchecked")
	public T selectOne(String[] paramName, Object[] paramValue, String[] resultName) throws SQLException {
		String sql = SQLUtil.getSelectSql(getTableName(), paramName, resultName);
		return (T) select(sql, paramName, paramValue, resultName, true);
	}
	
	@SuppressWarnings("unchecked")
	public T selectOneOrder(String[] paramName, Object[] paramValue, String[] resultName, String[] orderByParam, boolean isDesc) throws SQLException {
		String sql = SQLUtil.getSelectSqlOrder(getTableName(), paramName, resultName, orderByParam, isDesc);
		return (T) select(sql, paramName, paramValue, resultName, true);
	}
	
	private Object select(String sql, String[] paramName, Object[] paramValue, String[] resultName, boolean isOne) throws SQLException{
		Connection conn = DBUtil.getCon();
		QueryRunner qr = new QueryRunner();
		System.out.println(sql);
		Object result = null;
		if(isOne){
			result = qr.query(conn, sql, new BeanHandler<T>(getEntityClass()), paramValue);
		}else{
			result = qr.query(conn, sql, new BeanListHandler<T>(getEntityClass()), paramValue);
		}
		DbUtils.closeQuietly(conn);
		return result;
	}
	
	private String getTableName(){
		return getEntityClass().getSimpleName().toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
}
