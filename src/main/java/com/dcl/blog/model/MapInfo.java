package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class MapInfo {
	 private long id;
	 private long conpanyId;
	 private String mapLocation;//储存格式为 longitude,latitude; 如 117.23622,37.2837;238.29321,45.12323;
	 private Date createDate;
	 private long conpanyUserId;
	 private String conpanyUserName;
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
	@Column(length=12000)
	public String getMapLocation() {
		return mapLocation;
	}
	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getConpanyUserId() {
		return conpanyUserId;
	}
	public void setConpanyUserId(long conpanyUserId) {
		this.conpanyUserId = conpanyUserId;
	}
	@Column
	public String getConpanyUserName() {
		return conpanyUserName;
	}
	public void setConpanyUserName(String conpanyUserName) {
		this.conpanyUserName = conpanyUserName;
	}
	 
}
