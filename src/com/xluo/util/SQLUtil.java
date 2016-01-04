package com.xluo.util;

public class SQLUtil {

	public static String getInsertSql(String tableName, String[] paramNames) {
		StringBuilder sb = new StringBuilder("insert into ")
				.append(tableName)
				.append("(");
		for(String paramName : paramNames){
			sb.append(paramName + ",");
		}
		sb = new StringBuilder(sb.substring(0, sb.toString().lastIndexOf(",")));
		sb.append(") values(");
		int len = paramNames.length;
		while(len-- != 0){
			sb.append("?, ");
		}
		sb = new StringBuilder(sb.substring(0, sb.toString().lastIndexOf(",")));
		sb.append(")");
		return sb.toString();
		
	}
	
	public static String getSelectSql(String tableName, String[] paramNames, String[] resultNames) {
		StringBuilder sb = new StringBuilder("select ");
		for(String resultName : resultNames){
			sb.append(resultName + ",");
		}
		sb = new StringBuilder(sb.substring(0, sb.toString().lastIndexOf(",")));
		sb.append(" from " + tableName + " where ");
		for(String paramName : paramNames){
			sb.append(paramName + " = ? and ");
		}
		sb = new StringBuilder(sb.substring(0, sb.toString().lastIndexOf("and")));
		return sb.toString();
		
	}

}
