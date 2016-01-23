package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class VoteUser {
	private long id;
	private String name;
	private long linkmenId;
	private String phone;
	private long voteId;
	private String voteItemName;
	private long voteItemId;
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
	@Column
	public long getLinkmenId() {
		return linkmenId;
	}
	public void setLinkmenId(long linkmenId) {
		this.linkmenId = linkmenId;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public long getVoteId() {
		return voteId;
	}
	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}
	@Column
	public String getVoteItemName() {
		return voteItemName;
	}
	public void setVoteItemName(String voteItemName) {
		this.voteItemName = voteItemName;
	}
	@Column
	public long getVoteItemId() {
		return voteItemId;
	}
	public void setVoteItemId(long voteItemId) {
		this.voteItemId = voteItemId;
	}
	
	
}
