package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 记录详情
 * @author Administrator
 *
 */
@Entity
public class NotesInfo {
	private long id;
	private long notesId;
	private String notesMark;
	private Date notesDate;
	private String notesTitle;
	private long notesUserId;
	private String notesUserName;
	private String notesAddress;
	private String notesDriver;
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
	public long getNotesId() {
		return notesId;
	}
	public void setNotesId(long notesId) {
		this.notesId = notesId;
	}
	@Column(length=8000)
	public String getNotesMark() {
		return notesMark;
	}
	public void setNotesMark(String notesMark) {
		this.notesMark = notesMark;
	}
	@Column
	public Date getNotesDate() {
		return notesDate;
	}
	public void setNotesDate(Date notesDate) {
		this.notesDate = notesDate;
	}
	@Column
	public String getNotesTitle() {
		return notesTitle;
	}
	public void setNotesTitle(String notesTitle) {
		this.notesTitle = notesTitle;
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
	@Column(length=8000)
	public String getNotesAddress() {
		return notesAddress;
	}
	public void setNotesAddress(String notesAddress) {
		this.notesAddress = notesAddress;
	}
	@Column
	public String getNotesDriver() {
		return notesDriver;
	}
	public void setNotesDriver(String notesDriver) {
		this.notesDriver = notesDriver;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
