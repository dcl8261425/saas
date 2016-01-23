package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Game {
	private long id;
	private String name;
	private String gameMarks;//游戏规则
	private String gameInfo;//游戏简介
	private String gameImage1;
	private String gameImage2;
	private String gameImage3;
	private int indexNum;
	private long shopAddNum;
	private String gameServiceAddress;
	private int price;
	private int nowprice;
	private boolean paming;
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
	public int getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
	@Column
	public long getShopAddNum() {
		return shopAddNum;
	}
	public void setShopAddNum(long shopAddNum) {
		this.shopAddNum = shopAddNum;
	}
	@Column
	public String getGameServiceAddress() {
		return gameServiceAddress;
	}
	public void setGameServiceAddress(String gameServiceAddress) {
		this.gameServiceAddress = gameServiceAddress;
	}
	@Column
	public boolean isPaming() {
		return paming;
	}
	public void setPaming(boolean paming) {
		this.paming = paming;
	}
	@Column
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Column
	public int getNowprice() {
		return nowprice;
	}
	public void setNowprice(int nowprice) {
		this.nowprice = nowprice;
	}
	
}
