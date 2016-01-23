package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Performance {
	private long id;
	private String userTrueName;
	private long userId;
	private long conpanyId;
	private long myCreateChanceNum;//此人创建的机会数
	private long myCreateChanceBuyNum;//此人创建的机会输成交次数
	private double myCreateChanceBuyCountPrice;//此人创建的机会总成交价格
	private long toChanceNum;//此人被分配的数
	private long toChanceBuyNum;//此人被分配的机会总买数
	private double toChanceBuyCountPrice;//此人被分配机会的总成交价格
	private long meetingNum;//签到次数
	private Date createDate;
	private long meetingOutNum;
	private long meetingLastNum;
	private long meetingLeave;
	private long meetingNumOut;//签退次数
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
	public String getUserTrueName() {
		return userTrueName;
	}
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}
	@Column
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getMyCreateChanceNum() {
		return myCreateChanceNum;
	}
	public void setMyCreateChanceNum(long myCreateChanceNum) {
		this.myCreateChanceNum = myCreateChanceNum;
	}
	@Column
	public long getMyCreateChanceBuyNum() {
		return myCreateChanceBuyNum;
	}
	public void setMyCreateChanceBuyNum(long myCreateChanceBuyNum) {
		this.myCreateChanceBuyNum = myCreateChanceBuyNum;
	}
	@Column
	public double getMyCreateChanceBuyCountPrice() {
		return myCreateChanceBuyCountPrice;
	}
	public void setMyCreateChanceBuyCountPrice(double myCreateChanceBuyCountPrice) {
		this.myCreateChanceBuyCountPrice = myCreateChanceBuyCountPrice;
	}
	@Column
	public long getToChanceNum() {
		return toChanceNum;
	}
	public void setToChanceNum(long toChanceNum) {
		this.toChanceNum = toChanceNum;
	}
	@Column
	public long getToChanceBuyNum() {
		return toChanceBuyNum;
	}
	public void setToChanceBuyNum(long toChanceBuyNum) {
		this.toChanceBuyNum = toChanceBuyNum;
	}
	@Column
	public double getToChanceBuyCountPrice() {
		return toChanceBuyCountPrice;
	}
	public void setToChanceBuyCountPrice(double toChanceBuyCountPrice) {
		this.toChanceBuyCountPrice = toChanceBuyCountPrice;
	}
	@Column
	public long getMeetingNum() {
		return meetingNum;
	}
	public void setMeetingNum(long meetingNum) {
		this.meetingNum = meetingNum;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getMeetingOutNum() {
		return meetingOutNum;
	}
	public void setMeetingOutNum(long meetingOutNum) {
		this.meetingOutNum = meetingOutNum;
	}
	@Column
	public long getMeetingLastNum() {
		return meetingLastNum;
	}
	public void setMeetingLastNum(long meetingLastNum) {
		this.meetingLastNum = meetingLastNum;
	}
	@Column
	public long getMeetingLeave() {
		return meetingLeave;
	}
	public void setMeetingLeave(long meetingLeave) {
		this.meetingLeave = meetingLeave;
	}
	@Column
	public long getMeetingNumOut() {
		return meetingNumOut;
	}
	public void setMeetingNumOut(long meetingNumOut) {
		this.meetingNumOut = meetingNumOut;
	}
	
}
