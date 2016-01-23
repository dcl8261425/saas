package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConpanyZoneTouPiao {
	private long id;
	private long conpanyZoneId;
	private long conpanyId;
	private long createUserId;
	private boolean isDuoXuan;
	private long groupId;
	private Date startDate;
	private Date endDate;
	private String createName;
	private String title;
	private long countTouPiao;
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
	public long getConpanyZoneId() {
		return conpanyZoneId;
	}
	public void setConpanyZoneId(long conpanyZoneId) {
		this.conpanyZoneId = conpanyZoneId;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	@Column
	public boolean isDuoXuan() {
		return isDuoXuan;
	}
	public void setDuoXuan(boolean isDuoXuan) {
		this.isDuoXuan = isDuoXuan;
	}
	@Column
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
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
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public long getCountTouPiao() {
		return countTouPiao;
	}
	public void setCountTouPiao(long countTouPiao) {
		this.countTouPiao = countTouPiao;
	}
	
}
