package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class InOrder {
	private long id;
	private String name;
	private Date createDate;
	private String orderNum;
	private int state;
	private long conpanyId;
	private long createUser;
	private String createUserName;
	private long inStoreUser;
	private String inStoreUserName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Column
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
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
	
}
