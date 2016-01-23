package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class VipErShouReturnMessageTwo {
	private long id;
	private long conpanyId;
	private String message;
	private long mainId;
	private long createVipId;
	private long createUserTableId;
	private String createVipName;
	private Date createDate;
	private long toCreateVipId;
	private long toCcreateUserTableId;
	private String toCreateVipName;
	private long vipErShouMessageId;
	private String vipErShouMessageName;
	private String touXiangImage;
	private String toCreateUserTouXiang;
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
	public long getMainId() {
		return mainId;
	}
	public void setMainId(long mainId) {
		this.mainId = mainId;
	}
	@Column
	public long getToCreateVipId() {
		return toCreateVipId;
	}
	public void setToCreateVipId(long toCreateVipId) {
		this.toCreateVipId = toCreateVipId;
	}
	@Column
	public long getToCcreateUserTableId() {
		return toCcreateUserTableId;
	}
	public void setToCcreateUserTableId(long toCcreateUserTableId) {
		this.toCcreateUserTableId = toCcreateUserTableId;
	}
	@Column
	public String getToCreateVipName() {
		return toCreateVipName;
	}
	public void setToCreateVipName(String toCreateVipName) {
		this.toCreateVipName = toCreateVipName;
	}
	@Column
	public String getVipErShouMessageName() {
		return vipErShouMessageName;
	}
	public void setVipErShouMessageName(String vipErShouMessageName) {
		this.vipErShouMessageName = vipErShouMessageName;
	}
	@Column
	public long getVipErShouMessageId() {
		return vipErShouMessageId;
	}
	public void setVipErShouMessageId(long vipErShouMessageId) {
		this.vipErShouMessageId = vipErShouMessageId;
	}
	@Column
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	@Column
	public String getToCreateUserTouXiang() {
		return toCreateUserTouXiang;
	}
	public void setToCreateUserTouXiang(String toCreateUserTouXiang) {
		this.toCreateUserTouXiang = toCreateUserTouXiang;
	}
	
}
