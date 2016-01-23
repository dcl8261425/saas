package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 角色表
 * 用户绑定各个应用程序功能的权限，一个用户可以有多个角色，一个角色可以有多个权限
 * @author Administrator
 *
 */
@Entity
public class Role {
	private long id;
	private long conpanyId;//所属公司
	private long groupid;//该角色所属组名
	private String name;//角色名
	private String marks;//备注
	private boolean conpanyAdminRole;
	private String groupName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=4000)
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	@Column
	public long getGroupid() {
		return groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	@Column
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column
	public boolean isConpanyAdminRole() {
		return conpanyAdminRole;
	}
	public void setConpanyAdminRole(boolean conpanyAdminRole) {
		this.conpanyAdminRole = conpanyAdminRole;
	}
	
}
