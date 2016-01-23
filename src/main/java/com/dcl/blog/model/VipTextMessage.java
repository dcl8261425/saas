package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class VipTextMessage {
	private long id;
	private String message;
	private long createVipId;
	private String title;
	private String createVipName;
	private String createVipPhone;
	private long conpanyId;
	private String conpanyName;
	private long zan;
	private long returnNum;
	private long lookNum;
	private long indexNum;
	private String country;
	private String province;
	private String district;
	private String city;
	private long hangyeId;
	private String hangyeName;
	private long userTableId;
	private Date createDate;
	private String touXiangImage;
	private int pass;
	private String passMessage;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getCreateVipPhone() {
		return createVipPhone;
	}
	public void setCreateVipPhone(String createVipPhone) {
		this.createVipPhone = createVipPhone;
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
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	@Column
	public long getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(long returnNum) {
		this.returnNum = returnNum;
	}
	@Column
	public long getLookNum() {
		return lookNum;
	}
	public void setLookNum(long lookNum) {
		this.lookNum = lookNum;
	}
	@Column
	public long getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(long indexNum) {
		this.indexNum = indexNum;
	}
	@Column
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Column
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@Column
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	@Column
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column
	public long getHangyeId() {
		return hangyeId;
	}
	public void setHangyeId(long hangyeId) {
		this.hangyeId = hangyeId;
	}
	@Column
	public String getHangyeName() {
		return hangyeName;
	}
	public void setHangyeName(String hangyeName) {
		this.hangyeName = hangyeName;
	}
	@Column
	public long getUserTableId() {
		return userTableId;
	}
	public void setUserTableId(long userTableId) {
		this.userTableId = userTableId;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	@Column
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	@Column
	public String getPassMessage() {
		return passMessage;
	}
	public void setPassMessage(String passMessage) {
		this.passMessage = passMessage;
	}
	
}
