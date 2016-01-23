package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class DaiLiSHangUser {
	private long id;
	private String dailiName;//代理商名字
	private String dailiNum;//代理商号
	private String country;//国家
	private String province; //省
	private String city;//市
	private String district; //区
	private String dailiUserName;
	private String dailiPassword;
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
	public String getDailiName() {
		return dailiName;
	}
	public void setDailiName(String dailiName) {
		this.dailiName = dailiName;
	}
	@Column
	public String getDailiNum() {
		return dailiNum;
	}
	public void setDailiNum(String dailiNum) {
		this.dailiNum = dailiNum;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	@Column
	public String getDailiUserName() {
		return dailiUserName;
	}
	public void setDailiUserName(String dailiUserName) {
		this.dailiUserName = dailiUserName;
	}
	@Column
	public String getDailiPassword() {
		return dailiPassword;
	}
	public void setDailiPassword(String dailiPassword) {
		this.dailiPassword = dailiPassword;
	}
	
}
