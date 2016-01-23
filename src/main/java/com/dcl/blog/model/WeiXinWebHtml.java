package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class WeiXinWebHtml {
	private long id;
	private String name;
	private String htmls;
	private long conpanyId;
	private String conpanyName;
	private long createUserId;
	private String createUserName;
	private boolean privates;//是否是私有，不是私有则所有公司都可以使用
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=400000)
	public String getHtmls() {
		return htmls;
	}
	public void setHtmls(String htmls) {
		this.htmls = htmls;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	@Column
	public long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	@Column
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@Column
	public boolean isPrivates() {
		return privates;
	}
	public void setPrivates(boolean privates) {
		this.privates = privates;
	}
	
	
}
