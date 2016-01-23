package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GameConpany {
	private long id;
	private long vipRunNum;//游戏参加人数
	private String name;
	private String gameMarks;//游戏规则
	private String gameInfo;//游戏简介
	private String gameImage1;
	private String gameImage2;
	private String gameImage3;
	private long gameId;
	private boolean openUse;
	private String gameServiceAddress;
	private long indexNum;
	private boolean paming;
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
	public long getVipRunNum() {
		return vipRunNum;
	}
	public void setVipRunNum(long vipRunNum) {
		this.vipRunNum = vipRunNum;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getGameMarks() {
		return gameMarks;
	}
	public void setGameMarks(String gameMarks) {
		this.gameMarks = gameMarks;
	}
	@Column
	public String getGameInfo() {
		return gameInfo;
	}
	public void setGameInfo(String gameInfo) {
		this.gameInfo = gameInfo;
	}
	@Column
	public String getGameImage1() {
		return gameImage1;
	}
	public void setGameImage1(String gameImage1) {
		this.gameImage1 = gameImage1;
	}
	@Column
	public String getGameImage2() {
		return gameImage2;
	}
	public void setGameImage2(String gameImage2) {
		this.gameImage2 = gameImage2;
	}
	@Column
	public String getGameImage3() {
		return gameImage3;
	}
	public void setGameImage3(String gameImage3) {
		this.gameImage3 = gameImage3;
	}
	@Column
	public boolean isOpenUse() {
		return openUse;
	}
	public void setOpenUse(boolean openUse) {
		this.openUse = openUse;
	}
	@Column
	public String getGameServiceAddress() {
		return gameServiceAddress;
	}
	public void setGameServiceAddress(String gameServiceAddress) {
		this.gameServiceAddress = gameServiceAddress;
	}
	@Column
	public long getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(long indexNum) {
		this.indexNum = indexNum;
	}
	@Column
	public boolean isPaming() {
		return paming;
	}
	public void setPaming(boolean paming) {
		this.paming = paming;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	
}
