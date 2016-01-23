package com.dcl.blog.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class VoteItem{
	private long id;
	private String name;
	private long num;
	private Vote vote;
	private long ConpanyId;
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
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "vote_id") 
	public Vote getVote() {
		return vote;
	}
	public void setVote(Vote vote) {
		this.vote = vote;
	}
	@Column
	public long getConpanyId() {
		return ConpanyId;
	}
	public void setConpanyId(long conpanyId) {
		ConpanyId = conpanyId;
	}
	
	
}
