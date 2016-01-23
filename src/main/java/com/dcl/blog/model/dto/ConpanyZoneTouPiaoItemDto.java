package com.dcl.blog.model.dto;

import java.util.List;

public class ConpanyZoneTouPiaoItemDto {
	private long id;
	private long conpanyId;
	private long conpanyZoneTouPiaoId;
	private long groupId;
	private String name;
	private String image;
	private long countNum;
	private float baifenbi;
	private List<Object> sendUserlist;
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
	public long getConpanyZoneTouPiaoId() {
		return conpanyZoneTouPiaoId;
	}
	public void setConpanyZoneTouPiaoId(long conpanyZoneTouPiaoId) {
		this.conpanyZoneTouPiaoId = conpanyZoneTouPiaoId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getCountNum() {
		return countNum;
	}
	public void setCountNum(long countNum) {
		this.countNum = countNum;
	}
	public List<Object> getSendUserlist() {
		return sendUserlist;
	}
	public void setSendUserlist(List<Object> sendUserlist) {
		this.sendUserlist = sendUserlist;
	}
	public float getBaifenbi() {
		return baifenbi;
	}
	public void setBaifenbi(float baifenbi) {
		this.baifenbi = baifenbi;
	}
	
}
