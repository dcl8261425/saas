package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * @author Administrator
 *
 */
@Entity
public class VipLiuYanItem {
	private long id;
	private long fromVipId;
	private String fromVipName;
	private String fromVipTouxiang;
	private long fromUserTableId;
	private String vipMessageTitle;
	private long vipMessageId;
	private long messageOneId;
	private Date sendDate;
	private long conpanyId;
	private long vipLiuYanListid;
	private String message;
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
	public long getFromVipId() {
		return fromVipId;
	}
	public void setFromVipId(long fromVipId) {
		this.fromVipId = fromVipId;
	}
	@Column
	public String getFromVipName() {
		return fromVipName;
	}
	public void setFromVipName(String fromVipName) {
		this.fromVipName = fromVipName;
	}
	@Column
	public String getFromVipTouxiang() {
		return fromVipTouxiang;
	}
	public void setFromVipTouxiang(String fromVipTouxiang) {
		this.fromVipTouxiang = fromVipTouxiang;
	}
	@Column
	public long getFromUserTableId() {
		return fromUserTableId;
	}
	public void setFromUserTableId(long fromUserTableId) {
		this.fromUserTableId = fromUserTableId;
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
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getVipLiuYanListid() {
		return vipLiuYanListid;
	}
	public void setVipLiuYanListid(long vipLiuYanListid) {
		this.vipLiuYanListid = vipLiuYanListid;
	}
	@Column
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Column
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
