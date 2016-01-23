package com.dcl.blog.model.dto;

import com.dcl.blog.model.Vote;

public class VoteItemDTO {
	private long id;
	private String name;
	private long num;
	private float baifenbi;
	private long conpanyId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public float getBaifenbi() {
		return baifenbi;
	}
	public void setBaifenbi(float baifenbi) {
		this.baifenbi = baifenbi;
	}
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
