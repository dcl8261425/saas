package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConpanyUserLinkRole {
	private long id;
	private String conpanyUserName;
	private String conpanyUserTrueName;
	private long roleId;
	private long userid;
	private String roleName;
	private long conpanyId;
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
	public String getConpanyUserName() {
		return conpanyUserName;
	}
	public void setConpanyUserName(String conpanyUserName) {
		this.conpanyUserName = conpanyUserName;
	}
	@Column
	public String getConpanyUserTrueName() {
		return conpanyUserTrueName;
	}
	public void setConpanyUserTrueName(String conpanyUserTrueName) {
		this.conpanyUserTrueName = conpanyUserTrueName;
	}
	@Column
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	@Column
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	@Column
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
