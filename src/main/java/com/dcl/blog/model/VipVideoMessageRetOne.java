package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class VipVideoMessageRetOne {
	private long id;
	private long conpanyId;
	private String message;
	private long vipVideoMessageId;
	private long createVipId;
	private long createUserTableId;
	private String createVipName;
	private long zan;
	private Date createDate;
	private String vipVideoMessageTitle;
	private String touXiangImage;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Column
	public long getVipVideoMessageId() {
		return vipVideoMessageId;
	}
	public void setVipVideoMessageId(long vipVideoMessageId) {
		this.vipVideoMessageId = vipVideoMessageId;
	}
	@Column
	public long getCreateVipId() {
		return createVipId;
	}
	public void setCreateVipId(long createVipId) {
		this.createVipId = createVipId;
	}
	@Column
	public long getCreateUserTableId() {
		return createUserTableId;
	}
	public void setCreateUserTableId(long createUserTableId) {
		this.createUserTableId = createUserTableId;
	}
	@Column
	public String getCreateVipName() {
		return createVipName;
	}
	public void setCreateVipName(String createVipName) {
		this.createVipName = createVipName;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	@Column
	public String getVipVideoMessageTitle() {
		return vipVideoMessageTitle;
	}
	public void setVipVideoMessageTitle(String vipVideoMessageTitle) {
		this.vipVideoMessageTitle = vipVideoMessageTitle;
	}
	@Column
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	
}
