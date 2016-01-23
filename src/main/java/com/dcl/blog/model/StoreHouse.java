package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StoreHouse {
	private long id;
	private String name;
	private long managerUserId;
	private String managerUserName;
	private String tal;
	private String address;
	private long conpanyId;
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
	public String getTal() {
		return tal;
	}
	public void setTal(String tal) {
		this.tal = tal;
	}
	@Column																							
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(long managerUserId) {
		this.managerUserId = managerUserId;
	}
	@Column
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	
}
