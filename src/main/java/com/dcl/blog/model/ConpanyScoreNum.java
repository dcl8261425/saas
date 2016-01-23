package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 积分卷列表
 * @author Administrator
 *
 */
@Entity
public class ConpanyScoreNum {
	private long id;
	private String num;
	private long orderId;
	private String orderNum;
	private long conpanyId;
	private long tolinkMan;
	private String tolinkManName;
	private double score;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public long getTolinkMan() {
		return tolinkMan;
	}
	public void setTolinkMan(long tolinkMan) {
		this.tolinkMan = tolinkMan;
	}
	@Column
	public String getTolinkManName() {
		return tolinkManName;
	}
	public void setTolinkManName(String tolinkManName) {
		this.tolinkManName = tolinkManName;
	}
	@Column
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	@Column
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	@Column
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
