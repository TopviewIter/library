package com.xluo.po;

import java.util.List;

public class Role {

	private String Id;
	private String roleName;
	private String createTime;
	private String roleDesc;
	private List<User> user;
	
	public String getId() {
		return Id;
	}
	
	public void setId(String id) {
		Id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}
	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
	
}
