package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.annotations.Beta;
@Entity
public class Message {
	private long id;
	private long userid;
	private String usertrueName;
	private String title;
	private boolean reades;
	private long types;//0.无动作--展示文本1,被指定新客户机会-点击后直接展示新客户机会信息 
	private long conpanyId;
	private Date createDate;
	private long contentid;
	private String content;
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
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	@Column
	public String getUsertrueName() {
		return usertrueName;
	}
	public void setUsertrueName(String usertrueName) {
		this.usertrueName = usertrueName;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public boolean isReades() {
		return reades;
	}
	public void setReades(boolean reades) {
		this.reades = reades;
	}
	@Column
	public long getTypes() {
		return types;
	}
	public void setTypes(long types) {
		this.types = types;
	}
	@Column
	public long getContentid() {
		return contentid;
	}
	public void setContentid(long contentid) {
		this.contentid = contentid;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
