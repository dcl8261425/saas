package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Games {
	private long id;
	private String name;
	private String marks;
	private boolean uses;
	private int num;//游戏一天可以使用的次数
	private int zhongjianggailu;
	private long conpanyId;
	private boolean score;
	private double scoreNum;
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
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public boolean isUses() {
		return uses;
	}
	public void setUses(boolean uses) {
		this.uses = uses;
	}
	@Column
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Column
	public int getZhongjianggailu() {
		return zhongjianggailu;
	}
	public void setZhongjianggailu(int zhongjianggailu) {
		this.zhongjianggailu = zhongjianggailu;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public boolean isScore() {
		return score;
	}
	public void setScore(boolean score) {
		this.score = score;
	}
	@Column
	public double getScoreNum() {
		return scoreNum;
	}
	public void setScoreNum(double scoreNum) {
		this.scoreNum = scoreNum;
	}
	

	
}
