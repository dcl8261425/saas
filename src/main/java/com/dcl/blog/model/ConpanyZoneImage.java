package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConpanyZoneImage {
	private long id;
	private long conpanyId;
	private boolean isRet;//是回复还是主题
	private String link;
	private String systemFile;
	private String type;//1.图片，2.视频,3.文件
	private long mainId;
	private String fileName;
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
	public boolean isRet() {
		return isRet;
	}
	public void setRet(boolean isRet) {
		this.isRet = isRet;
	}
	@Column
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public long getMainId() {
		return mainId;
	}
	public void setMainId(long mainId) {
		this.mainId = mainId;
	}
	@Column
	public String getSystemFile() {
		return systemFile;
	}
	public void setSystemFile(String systemFile) {
		this.systemFile = systemFile;
	}
	@Column
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
