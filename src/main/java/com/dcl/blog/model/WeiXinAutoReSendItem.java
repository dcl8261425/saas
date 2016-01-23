package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class WeiXinAutoReSendItem {
	private long id;
	private long resendid;
	private long aoturesendId;
	private String name;
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
	public long getResendid() {
		return resendid;
	}
	public void setResendid(long resendid) {
		this.resendid = resendid;
	}
	@Column
	public long getAoturesendId() {
		return aoturesendId;
	}
	public void setAoturesendId(long aoturesendId) {
		this.aoturesendId = aoturesendId;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
