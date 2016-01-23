package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class ConpanyZoneTouPiaoItem {
	private long id;
	private long conpanyId;
	private long conpanyZoneTouPiaoId;
	private long groupId;
	private String name;
	private String image;
	private long countNum;
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
	public long getConpanyZoneTouPiaoId() {
		return conpanyZoneTouPiaoId;
	}
	public void setConpanyZoneTouPiaoId(long conpanyZoneTouPiaoId) {
		this.conpanyZoneTouPiaoId = conpanyZoneTouPiaoId;
	}
	@Column
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column
	public long getCountNum() {
		return countNum;
	}
	public void setCountNum(long countNum) {
		this.countNum = countNum;
	}
	
}
