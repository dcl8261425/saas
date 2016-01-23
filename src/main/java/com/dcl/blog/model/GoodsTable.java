package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GoodsTable {
	private long id;
	private String goodsName;
	private double goodsNum;
	private String goodsType;
	private float price;
	private double inPrice;
	private double score;
	private double salesNum;
	private double totalPrice;
	private double totalInPrice;
	private long conpanyId;
	private String codeid;//货物的扫码
	private String spell;//拼音简写
	private String goodsModel;//货物型号
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Column
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	@Column
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}
	@Column
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	@Column
	public String getSpell() {
		return spell;
	}
	public void setSpell(String spell) {
		this.spell = spell;
	}
	@Column
	public String getGoodsModel() {
		return goodsModel;
	}
	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}
	@Column
	public double getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(double goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Column
	public double getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(double salesNum) {
		this.salesNum = salesNum;
	}
	@Column
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column
	public double getTotalInPrice() {
		return totalInPrice;
	}
	public void setTotalInPrice(double totalInPrice) {
		this.totalInPrice = totalInPrice;
	}
	
}
