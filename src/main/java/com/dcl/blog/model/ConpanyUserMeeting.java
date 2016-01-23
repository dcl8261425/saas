package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class ConpanyUserMeeting {
	private long id;
	private String conpanyUsertrueName;
	private long userId;
	private Date startDate;
	private Date endDate;
	private long conpanyId;
	private long stute;//状态，如：0未签到1迟到2早退3迟到加早退4外出5正常签到
	private String stuteMarks;//外出备注
	private String latitude;
	private String longitude;
	private String tuilatitude;
	private String tuilongitude;
	private Date createDate;
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
	public String getConpanyUsertrueName() {
		return conpanyUsertrueName;
	}
	public void setConpanyUsertrueName(String conpanyUsertrueName) {
		this.conpanyUsertrueName = conpanyUsertrueName;
	}
	@Column
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Column
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getStute() {
		return stute;
	}
	public void setStute(long stute) {
		this.stute = stute;
	}
	@Column
	public String getStuteMarks() {
		return stuteMarks;
	}
	public void setStuteMarks(String stuteMarks) {
		this.stuteMarks = stuteMarks;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Column
	public String getTuilatitude() {
		return tuilatitude;
	}
	public void setTuilatitude(String tuilatitude) {
		this.tuilatitude = tuilatitude;
	}
	@Column
	public String getTuilongitude() {
		return tuilongitude;
	}
	public void setTuilongitude(String tuilongitude) {
		this.tuilongitude = tuilongitude;
	}
	
	
}
