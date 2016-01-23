package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ChanceListLog {
	private long id;
	private Date createLogDate;
	private long createManId;
	private String creayeManName;
	private String customerName;
	private String customerAddress;
	private String customerType;
	private int customerLevel;//客户的级别 重要程度
	private int state;//1.普通机会 2.优质机会 3.已成客户 4.已流失 5.超过3个月无购物
	private long notesUserId;//0为未指定
	private String NotesUserName;
	private long conpanyId;
	private Date createDate;
	private Date lastBuy;
	private String eventType;
	private long chanceId;
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
	public Date getCreateLogDate() {
		return createLogDate;
	}
	public void setCreateLogDate(Date createLogDate) {
		this.createLogDate = createLogDate;
	}
	@Column
	public long getCreateManId() {
		return createManId;
	}
	public void setCreateManId(long createManId) {
		this.createManId = createManId;
	}
	@Column
	public String getCreayeManName() {
		return creayeManName;
	}
	public void setCreayeManName(String creayeManName) {
		this.creayeManName = creayeManName;
	}
	@Column
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Column
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	@Column
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	@Column
	public int getCustomerLevel() {
		return customerLevel;
	}
	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}
	@Column
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Column
	public long getNotesUserId() {
		return notesUserId;
	}
	public void setNotesUserId(long notesUserId) {
		this.notesUserId = notesUserId;
	}
	@Column
	public String getNotesUserName() {
		return NotesUserName;
	}
	public void setNotesUserName(String notesUserName) {
		NotesUserName = notesUserName;
	}

	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public Date getLastBuy() {
		return lastBuy;
	}
	public void setLastBuy(Date lastBuy) {
		this.lastBuy = lastBuy;
	}
	@Column
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	@Column
	public long getChanceId() {
		return chanceId;
	}
	public void setChanceId(long chanceId) {
		this.chanceId = chanceId;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
