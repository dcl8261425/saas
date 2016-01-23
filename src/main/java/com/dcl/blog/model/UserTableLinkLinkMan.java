package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserTableLinkLinkMan {
	private long id;
	private long usertableid;
	private String usertableUserName;
	private long conpanId;
	private String ConpanyName;
	private long linkmanId;
	private String linkmanName;
	private long chanceId;
	private String chanceName;
	private Date linkDate;
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
	public long getUsertableid() {
		return usertableid;
	}
	public void setUsertableid(long usertableid) {
		this.usertableid = usertableid;
	}
	@Column
	public String getUsertableUserName() {
		return usertableUserName;
	}
	public void setUsertableUserName(String usertableUserName) {
		this.usertableUserName = usertableUserName;
	}
	@Column
	public long getConpanId() {
		return conpanId;
	}
	public void setConpanId(long conpanId) {
		this.conpanId = conpanId;
	}
	@Column
	public String getConpanyName() {
		return ConpanyName;
	}
	public void setConpanyName(String conpanyName) {
		ConpanyName = conpanyName;
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
	public long getChanceId() {
		return chanceId;
	}
	public void setChanceId(long chanceId) {
		this.chanceId = chanceId;
	}
	@Column
	public String getChanceName() {
		return chanceName;
	}
	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}
	@Column
	public Date getLinkDate() {
		return linkDate;
	}
	public void setLinkDate(Date linkDate) {
		this.linkDate = linkDate;
	}
	
}
