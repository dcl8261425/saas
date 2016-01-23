package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 工资修改记录表
 * 每次修改工资时都会把修改之前的工资加进去
 * 并记录 为何修改
 * @author Administrator
 *
 */
@Entity
public class PriceLog {
	private long id;
	private String trueName;//员工真实名	
	private long userid;//员工id
	private float price;//工资
	private long conpanyid;//所属公司
	private Date logDate;//记录日期
	private String marks;//修改原因
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
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	@Column
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column
	public long getConpanyid() {
		return conpanyid;
	}
	public void setConpanyid(long conpanyid) {
		this.conpanyid = conpanyid;
	}
	@Column
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	@Column
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
