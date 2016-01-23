package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 用户绑定到组的记录表
 * 主要记录一个组里有那些用户
 * 也可以看做一个用户都在那些组里。
 * 一个组也可以看成一个部门，一个人可以在多个部门里兼职不同的岗位，
 * @author Administrator
 *
 */
@Entity
public class GroupConpanyLinkUser {
	private long id;
	private String conpanyUserName;//公司员工的账号
	private String conpanyUserTrueName;//公司员工真实名
	private long groupId;//组id
	private long conpanyUserId;//员工id
	private long conpanyId;//所属公司id
	private String groupName;//组名称
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
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	@Column
	public long getConpanyUserId() {
		return conpanyUserId;
	}
	public void setConpanyUserId(long conpanyUserId) {
		this.conpanyUserId = conpanyUserId;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	
}
