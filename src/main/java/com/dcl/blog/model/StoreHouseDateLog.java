package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StoreHouseDateLog {
	private long id;
	private long storeHouseId;
	private String storeHoseName;
	private long goodsId;
	private String goodsName;
	private double num;
	private double countnum;
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
	public long getStoreHouseId() {
		return storeHouseId;
	}
	public void setStoreHouseId(long storeHouseId) {
		this.storeHouseId = storeHouseId;
	}
	@Column
	public String getStoreHoseName() {
		return storeHoseName;
	}
	public void setStoreHoseName(String storeHoseName) {
		this.storeHoseName = storeHoseName;
	}
	@Column
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	@Column
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	@Column
	public double getCountnum() {
		return countnum;
	}
	public void setCountnum(double countnum) {
		this.countnum = countnum;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
