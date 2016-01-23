package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Orders {
	private long id;
	private Date createDate;
	private boolean pay;
	private double countPrice;
	private String marks;
	private long ChanceId;
	private String ChanceName;
	private long conpanyId;
	private String title;
	private String orderNum;
	private long createUser;
	private int state;
	private String createUserName;
	private long inStoreUser;
	private String inStoreUserName;
	private long linkmanId;
	private String linkmanName;
	private long userTableId;
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
	public boolean isPay() {
		return pay;
	}
	public void setPay(boolean pay) {
		this.pay = pay;
	}
	@Column
	public double getCountPrice() {
		return countPrice;
	}
	public void setCountPrice(double countPrice) {
		this.countPrice = countPrice;
	}
	@Column(length=8000)
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	@Column
	public long getChanceId() {
		return ChanceId;
	}
	public void setChanceId(long chanceId) {
		ChanceId = chanceId;
	}
	@Column
	public String getChanceName() {
		return ChanceName;
	}
	public void setChanceName(String chanceName) {
		ChanceName = chanceName;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	@Column
	public long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}
	@Column
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@Column
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Column
	public long getInStoreUser() {
		return inStoreUser;
	}
	public void setInStoreUser(long inStoreUser) {
		this.inStoreUser = inStoreUser;
	}
	@Column
	public String getInStoreUserName() {
		return inStoreUserName;
	}
	public void setInStoreUserName(String inStoreUserName) {
		this.inStoreUserName = inStoreUserName;
	}
	@Column
	public long getLinkmanId() {
		return linkmanId;
	}
	public void setLinkmanId(long linkmanId) {
		this.linkmanId = linkmanId;
	}
	@Column
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	@Column
	public long getUserTableId() {
		return userTableId;
	}
	public void setUserTableId(long userTableId) {
		this.userTableId = userTableId;
	}
	
}
