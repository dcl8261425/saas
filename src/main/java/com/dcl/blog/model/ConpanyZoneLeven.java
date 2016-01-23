package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConpanyZoneLeven {
	private long id;
	private String levenName;
	private String LevenMark;
	private long score;
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
	public String getLevenName() {
		return levenName;
	}
	public void setLevenName(String levenName) {
		this.levenName = levenName;
	}
	@Column
	public String getLevenMark() {
		return LevenMark;
	}
	public void setLevenMark(String levenMark) {
		LevenMark = levenMark;
	}
	@Column
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
