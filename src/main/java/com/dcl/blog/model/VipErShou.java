package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class VipErShou {
	private long id;
	private String title;
	private String message;
	private long createVipId;
	private String createVipName;
	private String createVipPhone;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private long conpanyId;
	private String conpanyName;
	private String country;
	private String province;
	private String district;
	private String city;
	private long hangyeId;
	private String hangyeName;
	private String price;
	private Date createDate;
	private long userTableId;
	private long indexNum;
	private long erShouTypeId;
	private String erShouTypeName;
	private String touXiangImage;
	private String phone;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	@Column
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	@Column
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	@Column
	public String getImage4() {
		return image4;
	}
	public void setImage4(String image4) {
		this.image4 = image4;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getUserTableId() {
		return userTableId;
	}
	public void setUserTableId(long userTableId) {
		this.userTableId = userTableId;
	}
	@Column
	public long getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(long indexNum) {
		this.indexNum = indexNum;
	}
	@Column
	public long getErShouTypeId() {
		return erShouTypeId;
	}
	public void setErShouTypeId(long erShouTypeId) {
		this.erShouTypeId = erShouTypeId;
	}
	@Column
	public String getErShouTypeName() {
		return erShouTypeName;
	}
	public void setErShouTypeName(String erShouTypeName) {
		this.erShouTypeName = erShouTypeName;
	}
	@Column
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	@Column
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
