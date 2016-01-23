package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class WindowLayout {
	private long id;
	private long conpanyId;
	private long userid;
	private String urls;
	private boolean rights;
	private boolean lefts;
	private String mainUrl;
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
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	@Column
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	@Column
	public boolean isRights() {
		return rights;
	}
	public void setRights(boolean rights) {
		this.rights = rights;
	}
	@Column
	public boolean isLefts() {
		return lefts;
	}
	public void setLefts(boolean lefts) {
		this.lefts = lefts;
	}
	@Column
	public String getMainUrl() {
		return mainUrl;
	}
	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	
	
	
}
