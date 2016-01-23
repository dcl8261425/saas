package com.dcl.blog.model.dto;

import java.util.Date;
import java.util.List;

public class VipImageMessageRetOneDTO {
	private long id;
	private long conpanyId;
	private String message;
	private long vipImageMessageId;
	private long createVipId;
	private long createUserTableId;
	private String createVipName;
	private long zan;
	private Date createDate;
	private String vipImageMessageName;
	private List<Object> vipImageMessageRetTwoList;
	private String touXiangImage;
	private boolean zanUser;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getVipImageMessageId() {
		return vipImageMessageId;
	}
	public void setVipImageMessageId(long vipImageMessageId) {
		this.vipImageMessageId = vipImageMessageId;
	}
	public long getCreateVipId() {
		return createVipId;
	}
	public void setCreateVipId(long createVipId) {
		this.createVipId = createVipId;
	}
	public long getCreateUserTableId() {
		return createUserTableId;
	}
	public void setCreateUserTableId(long createUserTableId) {
		this.createUserTableId = createUserTableId;
	}
	public String getCreateVipName() {
		return createVipName;
	}
	public void setCreateVipName(String createVipName) {
		this.createVipName = createVipName;
	}
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getVipImageMessageName() {
		return vipImageMessageName;
	}
	public void setVipImageMessageName(String vipImageMessageName) {
		this.vipImageMessageName = vipImageMessageName;
	}
	public List<Object> getVipImageMessageRetTwoList() {
		return vipImageMessageRetTwoList;
	}
	public void setVipImageMessageRetTwoList(List<Object> vipImageMessageRetTwoList) {
		this.vipImageMessageRetTwoList = vipImageMessageRetTwoList;
	}
	public boolean isZanUser() {
		return zanUser;
	}
	public void setZanUser(boolean zanUser) {
		this.zanUser = zanUser;
	}
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	
}
