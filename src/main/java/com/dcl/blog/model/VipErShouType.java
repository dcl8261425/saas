package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class VipErShouType {
	private long id;
	private long conpanyId;
	private String vipErShouTypeName;
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
	public String getVipErShouTypeName() {
		return vipErShouTypeName;
	}
	public void setVipErShouTypeName(String vipErShouTypeName) {
		this.vipErShouTypeName = vipErShouTypeName;
	}
	
}
