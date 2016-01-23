package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 联系人
 * @author Administrator
 *
 */
@Entity
public class LinkManList {
	private long id;
	private long chanceListId;
	private String chanceListName;
	private String vipId;
	private String linkManName;
	private String linkManSex;
	private String linkManPhone;
	private String linkManJob;
	private String linkManMark;
	private Date linkManBirthday;
	private double linkManScore;
	private long conpanyId;
	private String addUserName;
	private long addUserId;
	private String openid;
	private long userTableId;
	private double linkManMaxScore;
	private String vipMarks;
	private long vipidNum;
	private String vipLevel;
	private double money;
	private String mac;
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
	public long getChanceListId() {
		return chanceListId;
	}
	public void setChanceListId(long chanceListId) {
		this.chanceListId = chanceListId;
	}
	@Column
	public String getLinkManName() {
		return linkManName;
	}
	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}
	@Column
	public String getLinkManSex() {
		return linkManSex;
	}
	public void setLinkManSex(String linkManSex) {
		this.linkManSex = linkManSex;
	}
	@Column
	public String getLinkManPhone() {
		return linkManPhone;
	}
	public void setLinkManPhone(String linkManPhone) {
		this.linkManPhone = linkManPhone;
	}
	@Column
	public String getLinkManJob() {
		return linkManJob;
	}
	public void setLinkManJob(String linkManJob) {
		this.linkManJob = linkManJob;
	}
	@Column(length=8000)
	public String getLinkManMark() {
		return linkManMark;
	}
	public void setLinkManMark(String linkManMark) {
		this.linkManMark = linkManMark;
	}
	@Column
	public Date getLinkManBirthday() {
		return linkManBirthday;
	}
	public void setLinkManBirthday(Date linkManBirthday) {
		this.linkManBirthday = linkManBirthday;
	}
	@Column
	public double getLinkManScore() {
		return linkManScore;
	}
	public void setLinkManScore(double linkManScore) {
		this.linkManScore = linkManScore;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	@Column
	public long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(long addUserId) {
		this.addUserId = addUserId;
	}
	@Column
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	@Column
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column
	public double getLinkManMaxScore() {
		return linkManMaxScore;
	}
	public void setLinkManMaxScore(double linkManMaxScore) {
		this.linkManMaxScore = linkManMaxScore;
	}
	@Column
	public String getVipMarks() {
		return vipMarks;
	}
	public void setVipMarks(String vipMarks) {
		this.vipMarks = vipMarks;
	}
	@Column
	public long getVipidNum() {
		return vipidNum;
	}
	public void setVipidNum(long vipidNum) {
		this.vipidNum = vipidNum;
	}
	@Column
	public String getChanceListName() {
		return chanceListName;
	}
	public void setChanceListName(String chanceListName) {
		this.chanceListName = chanceListName;
	}
	@Column
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	@Column
	public long getUserTableId() {
		return userTableId;
	}
	public void setUserTableId(long userTableId) {
		this.userTableId = userTableId;
	}
	@Column
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	@Column
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
}
