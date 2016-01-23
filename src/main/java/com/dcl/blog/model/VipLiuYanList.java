package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class VipLiuYanList {
	private long id;
	private long toVipId;
	private String toVipName;
	private String toVipTouxiang;
	private long toUserTableId;
	private String vipMessageTitle;
	private long vipMessageId;
	private long messageOneId;
	private Date createDate;
	private long conpanyId;
	private int type;//1.图片，2.视频，3.文字，4.二手
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
	public long getToVipId() {
		return toVipId;
	}
	public void setToVipId(long toVipId) {
		this.toVipId = toVipId;
	}
	@Column
	public String getToVipName() {
		return toVipName;
	}
	public void setToVipName(String toVipName) {
		this.toVipName = toVipName;
	}
	@Column
	public String getToVipTouxiang() {
		return toVipTouxiang;
	}
	public void setToVipTouxiang(String toVipTouxiang) {
		this.toVipTouxiang = toVipTouxiang;
	}
	@Column
	public long getToUserTableId() {
		return toUserTableId;
	}
	public void setToUserTableId(long toUserTableId) {
		this.toUserTableId = toUserTableId;
	}
	@Column
	public String getVipMessageTitle() {
		return vipMessageTitle;
	}
	public void setVipMessageTitle(String vipMessageTitle) {
		this.vipMessageTitle = vipMessageTitle;
	}
	@Column
	public long getVipMessageId() {
		return vipMessageId;
	}
	public void setVipMessageId(long vipMessageId) {
		this.vipMessageId = vipMessageId;
	}
	@Column
	public long getMessageOneId() {
		return messageOneId;
	}
	public void setMessageOneId(long messageOneId) {
		this.messageOneId = messageOneId;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
