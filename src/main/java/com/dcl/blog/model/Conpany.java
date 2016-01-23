package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Conpany {
	private long id;
	private String conpanyName;//公司名
	private String conpanyType;//公司类别
	private String softAdminName;//公司软件管理员名
	private String softAdminPhone;//公司软件管理员电话
	private int nowUserNum;//公司当前员工数
	private Date conpanyRigister;//公司注册时间
	private boolean useConpany;//是否激活了
	private String conpanyAdminEmail;//公司软件管理员的email
	private boolean payConpany;//是付费用户
	private Date endDate;//费用到期日期
	private String country;//国家
	private String province; //省
	private String city;//市
	private String district; //区
	private long hangyeId;//行业
	private String hangyeName;//行业
	private String guanggao;//有广告为"" 没广告为 mei
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
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	@Column
	public String getConpanyType() {
		return conpanyType;
	}
	public void setConpanyType(String conpanyType) {
		this.conpanyType = conpanyType;
	}
	@Column
	public String getSoftAdminName() {
		return softAdminName;
	}
	public void setSoftAdminName(String softAdminName) {
		this.softAdminName = softAdminName;
	}
	@Column
	public String getSoftAdminPhone() {
		return softAdminPhone;
	}
	public void setSoftAdminPhone(String softAdminPhone) {
		this.softAdminPhone = softAdminPhone;
	}
	@Column
	public int getNowUserNum() {
		return nowUserNum;
	}
	public void setNowUserNum(int nowUserNum) {
		this.nowUserNum = nowUserNum;
	}
	@Column
	public Date getConpanyRigister() {
		return conpanyRigister;
	}
	public void setConpanyRigister(Date conpanyRigister) {
		this.conpanyRigister = conpanyRigister;
	}
	@Column
	public String getConpanyAdminEmail() {
		return conpanyAdminEmail;
	}
	public void setConpanyAdminEmail(String conpanyAdminEmail) {
		this.conpanyAdminEmail = conpanyAdminEmail;
	}
	@Column
	public boolean isUseConpany() {
		return useConpany;
	}
	public void setUseConpany(boolean useConpany) {
		this.useConpany = useConpany;
	}
	@Column
	public boolean isPayConpany() {
		return payConpany;
	}
	public void setPayConpany(boolean payConpany) {
		this.payConpany = payConpany;
	}
	@Column
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public String getGuanggao() {
		return guanggao;
	}
	public void setGuanggao(String guanggao) {
		this.guanggao = guanggao;
	}
	
}
