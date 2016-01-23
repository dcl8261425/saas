package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 厂家联系人
 * @author Administrator
 */
@Entity
public class GoodsSourceLinkMan {
	private long id;
	private String name;
	private String phone;
	private Date linkManBirthday;
	private long goodsSourceid;
	private String goodsSourceName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public String getGoodsSourceName() {
		return goodsSourceName;
	}
	public void setGoodsSourceName(String goodsSourceName) {
		this.goodsSourceName = goodsSourceName;
	}
	@Column
	public Date getLinkManBirthday() {
		return linkManBirthday;
	}
	public void setLinkManBirthday(Date linkManBirthday) {
		this.linkManBirthday = linkManBirthday;
	}
	@Column
	public long getGoodsSourceid() {
		return goodsSourceid;
	}
	public void setGoodsSourceid(long goodsSourceid) {
		this.goodsSourceid = goodsSourceid;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
