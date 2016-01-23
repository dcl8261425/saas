package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 客户开发 记录表
 * @author Administrator
 *
 */
@Entity
public class Notes {
	private long id;
	private long notesUserId;
	private String notesUserName;
	private long chanceListId;
	private String chanceListName;
	private Date startDate;
	private Date endDate;
	private long conpanyId;
	private Date updateDate;
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
	public long getNotesUserId() {
		return notesUserId;
	}
	public void setNotesUserId(long notesUserId) {
		this.notesUserId = notesUserId;
	}
	@Column
	public String getNotesUserName() {
		return notesUserName;
	}
	public void setNotesUserName(String notesUserName) {
		this.notesUserName = notesUserName;
	}
	@Column
	public long getChanceListId() {
		return chanceListId;
	}
	public void setChanceListId(long chanceListId) {
		this.chanceListId = chanceListId;
	}
	@Column
	public String getChanceListName() {
		return chanceListName;
	}
	public void setChanceListName(String chanceListName) {
		this.chanceListName = chanceListName;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
