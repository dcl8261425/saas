package com.dcl.blog.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Vote{
	private long id;
	private String name;
	private Date stardate;
	private Date endDate;
	private boolean publics;//是否允许非关注用户投票
	private boolean ones;//是否是多选
	private List<VoteItem> voteItem;
	private long conpanyId;
	private long wenzhangId;
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
	public Date getStardate() {
		return stardate;
	}
	public void setStardate(Date stardate) {
		this.stardate = stardate;
	}
	@Column
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column
	public boolean isPublics() {
		return publics;
	}
	public void setPublics(boolean publics) {
		this.publics = publics;
	}
	@Column
	public boolean isOnes() {
		return ones;
	}
	public void setOnes(boolean ones) {
		this.ones = ones;
	}
	@OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<VoteItem> getVoteItem() {
		return voteItem;
	}
	public void setVoteItem(List<VoteItem> voteItem) {
		this.voteItem = voteItem;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getWenzhangId() {
		return wenzhangId;
	}
	public void setWenzhangId(long wenzhangId) {
		this.wenzhangId = wenzhangId;
	}
	
	
	
}
