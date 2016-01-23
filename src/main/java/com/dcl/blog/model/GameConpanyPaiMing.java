package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GameConpanyPaiMing {
	private long id;
	private long conpanyGameId;
	private long gameId;
	private long score;
	private long createVipId;
	private String createVipName;
	private long conpanyId;
	private String conpanyName;
	private long createUserTableId;
	private String touxiang;
	
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
	public long getConpanyGameId() {
		return conpanyGameId;
	}
	public void setConpanyGameId(long conpanyGameId) {
		this.conpanyGameId = conpanyGameId;
	}
	@Column
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	@Column
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	@Column
	public long getCreateVipId() {
		return createVipId;
	}
	public void setCreateVipId(long createVipId) {
		this.createVipId = createVipId;
	}
	@Column
	public String getCreateVipName() {
		return createVipName;
	}
	public void setCreateVipName(String createVipName) {
		this.createVipName = createVipName;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	@Column
	public long getCreateUserTableId() {
		return createUserTableId;
	}
	public void setCreateUserTableId(long createUserTableId) {
		this.createUserTableId = createUserTableId;
	}
	@Column
	public String getTouxiang() {
		return touxiang;
	}
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	
}
