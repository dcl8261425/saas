package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GamesAwardsList {
	 private long id;
	 private long gamesid;
	 private long awardsid;
	 private String content;
	 private long value;
	 private Date startDate;
	 private Date endDate;
	 private int num;
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
	public long getGamesid() {
		return gamesid;
	}
	public void setGamesid(long gamesid) {
		this.gamesid = gamesid;
	}
	@Column
	public long getAwardsid() {
		return awardsid;
	}
	public void setAwardsid(long awardsid) {
		this.awardsid = awardsid;
	}
	@Column
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
