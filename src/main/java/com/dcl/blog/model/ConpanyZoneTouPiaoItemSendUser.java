package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class ConpanyZoneTouPiaoItemSendUser {
	private long id;
	private long conpanyId;
	private long conpanyUserId;
	private long conpanyZoneTouPiaoItemId;
	private long groupId;
	private long conpanyZoneTouPiaoId;
	private Date createDate;
	private String createUserName;
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
	public long getConpanyUserId() {
		return conpanyUserId;
	}
	public void setConpanyUserId(long conpanyUserId) {
		this.conpanyUserId = conpanyUserId;
	}
	@Column
	public long getConpanyZoneTouPiaoItemId() {
		return conpanyZoneTouPiaoItemId;
	}
	public void setConpanyZoneTouPiaoItemId(long conpanyZoneTouPiaoItemId) {
		this.conpanyZoneTouPiaoItemId = conpanyZoneTouPiaoItemId;
	}
	@Column
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	@Column
	public long getConpanyZoneTouPiaoId() {
		return conpanyZoneTouPiaoId;
	}
	public void setConpanyZoneTouPiaoId(long conpanyZoneTouPiaoId) {
		this.conpanyZoneTouPiaoId = conpanyZoneTouPiaoId;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}
