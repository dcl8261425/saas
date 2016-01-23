package com.dcl.blog.model.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
public class VipImageDTO {
	private long id;
	private String imageLink;
	private boolean gif;
	private String title;
	private String message;
	private long createVipId;
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
	private boolean zanUser;
	private List<Object> oneMsg;
	private String touXiangImage;
	private String fileName;
	private long fileSize;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public boolean isGif() {
		return gif;
	}
	public void setGif(boolean gif) {
		this.gif = gif;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getCreateVipId() {
		return createVipId;
	}
	public void setCreateVipId(long createVipId) {
		this.createVipId = createVipId;
	}
	public String getCreateVipName() {
		return createVipName;
	}
	public void setCreateVipName(String createVipName) {
		this.createVipName = createVipName;
	}
	public String getCreateVipPhone() {
		return createVipPhone;
	}
	public void setCreateVipPhone(String createVipPhone) {
		this.createVipPhone = createVipPhone;
	}
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	public long getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(long returnNum) {
		this.returnNum = returnNum;
	}
	public long getLookNum() {
		return lookNum;
	}
	public void setLookNum(long lookNum) {
		this.lookNum = lookNum;
	}
	public long getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(long indexNum) {
		this.indexNum = indexNum;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getHangyeId() {
		return hangyeId;
	}
	public void setHangyeId(long hangyeId) {
		this.hangyeId = hangyeId;
	}
	public String getHangyeName() {
		return hangyeName;
	}
	public void setHangyeName(String hangyeName) {
		this.hangyeName = hangyeName;
	}
	public long getUserTableId() {
		return userTableId;
	}
	public void setUserTableId(long userTableId) {
		this.userTableId = userTableId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isZanUser() {
		return zanUser;
	}
	public void setZanUser(boolean zanUser) {
		this.zanUser = zanUser;
	}
	public List<Object> getOneMsg() {
		return oneMsg;
	}
	public void setOneMsg(List<Object> oneMsg) {
		this.oneMsg = oneMsg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTouXiangImage() {
		return touXiangImage;
	}
	public void setTouXiangImage(String touXiangImage) {
		this.touXiangImage = touXiangImage;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
}
