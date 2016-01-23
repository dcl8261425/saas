package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 软件功能访问权限和角色绑定的
 * @author Administrator
 *
 */
@Entity
public class SoftPermissionLinkConpanyRole {
	private long id;
	private long conpanyId;//所属公司名
	private long softPermissionId;//权限id
	private String functionName;//权限名称
	private long roleId;//角色id
	private String roleName;//角色名
	private long groupId;
	@Id
	@GeneratedValue
	@GenericGenerator(name="generator",strategy="increment")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getSoftPermissionId() {
		return softPermissionId;
	}
	public void setSoftPermissionId(long softPermissionId) {
		this.softPermissionId = softPermissionId;
	}
	@Column
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	@Column
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	@Column
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
}
