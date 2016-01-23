package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class WeiXinMenuTable {
	private long id;
	private long pid;
	private boolean url_is;
	private boolean key_is;
	private String type;
	private String name;
	private String keys_s;
	private String urls_s;
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
	public boolean isUrl_is() {
		return url_is;
	}
	public void setUrl_is(boolean url_is) {
		this.url_is = url_is;
	}
	@Column
	public boolean isKey_is() {
		return key_is;
	}
	public void setKey_is(boolean key_is) {
		this.key_is = key_is;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getKeys_s() {
		return keys_s;
	}
	public void setKeys_s(String keys_s) {
		this.keys_s = keys_s;
	}
	@Column
	public String getUrls_s() {
		return urls_s;
	}
	public void setUrls_s(String urls_s) {
		this.urls_s = urls_s;
	}
	@Column
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
	
}
