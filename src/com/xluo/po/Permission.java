package com.xluo.po;

import java.util.List;

public class Permission {

	private String id;
	private String parentId;
	private String permissionName;
	private String permissionDesc;
	private String icon;
	private String frameSign;
	private int index; //显示的序号
	private List<Role> role;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getPermissionName() {
		return permissionName;
	}
	
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public String getPermissionDesc() {
		return permissionDesc;
	}
	
	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}
	
	public String getFrameSign() {
		return frameSign;
	}
	
	public void setFrameSign(String frameSign) {
		this.frameSign = frameSign;
	}
	
	public List<Role> getRole() {
		return role;
	}
	
	public void setRole(List<Role> role) {
		this.role = role;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
