package com.xluo.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.xluo.po.Permission;
import com.xluo.po.User;

public class PermissionDaoTest {

	private PermissionDao permissionDao = new PermissionDao();
	
	@Test
	public void testGetLoginPermission() throws SQLException {
		
		User user = new User();
		user.setId("9bb64ac54e2345bc80ec3d8e680326ad");
		List<Permission> permissions = permissionDao.getLoginPermission(user);
		if(permissions != null){
			for(Permission permission : permissions){
				System.out.println(permission.getPermissionName());
			}
		}
		
	}
	
	@Test
	public void testGetChildPermission() throws SQLException{
		
		List<Permission> permissions = permissionDao.getChildPermission("9bb64ac54e2345bc80ec3d8e680326ax");
		if(permissions != null){
			for(Permission permission : permissions){
				System.out.println(permission.getPermissionName());
			}
		}
		
	}

}
