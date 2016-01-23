package com.dcl.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class ConpanyZoneScore {
	private long id;
	private long conpanyId;
	private long conpanyUserId;
	private String conpanyUserName;
	private long score;
	private long leveId;
	private String leveName;
	@Id
	@GeneratedValue
	@GenericGenerator(name="generator",strategy="increment")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	public long getConpanyUserId() {
		return conpanyUserId;
	}
	public void setConpanyUserId(long conpanyUserId) {
		this.conpanyUserId = conpanyUserId;
	}
	public String getConpanyUserName() {
		return conpanyUserName;
	}
	public void setConpanyUserName(String conpanyUserName) {
		this.conpanyUserName = conpanyUserName;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public long getLeveId() {
		return leveId;
	}
	public void setLeveId(long leveId) {
		this.leveId = leveId;
	}
	public String getLeveName() {
		return leveName;
	}
	public void setLeveName(String leveName) {
		this.leveName = leveName;
	}
	
}
