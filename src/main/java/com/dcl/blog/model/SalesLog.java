package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SalesLog {
	private long id;
	private long goodsId;
	private String goodsName;
	private int goodsSalesNum;
	private float goodsSalesPrice;
	private int storehouseOutId;
	private int storehouseOutName;
	private Date startDate;
	private long goodsSourceId;
	private String goodsSourceName;
	private long chanceId;
	private String chanceName;
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
	public int getGoodsSalesNum() {
		return goodsSalesNum;
	}
	public void setGoodsSalesNum(int goodsSalesNum) {
		this.goodsSalesNum = goodsSalesNum;
	}
	@Column
	public float getGoodsSalesPrice() {
		return goodsSalesPrice;
	}
	public void setGoodsSalesPrice(float goodsSalesPrice) {
		this.goodsSalesPrice = goodsSalesPrice;
	}
	@Column
	public int getStorehouseOutId() {
		return storehouseOutId;
	}
	public void setStorehouseOutId(int storehouseOutId) {
		this.storehouseOutId = storehouseOutId;
	}
	@Column
	public int getStorehouseOutName() {
		return storehouseOutName;
	}
	public void setStorehouseOutName(int storehouseOutName) {
		this.storehouseOutName = storehouseOutName;
	}
	@Column
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column
	public long getGoodsSourceId() {
		return goodsSourceId;
	}
	public void setGoodsSourceId(long goodsSourceId) {
		this.goodsSourceId = goodsSourceId;
	}
	@Column
	public String getGoodsSourceName() {
		return goodsSourceName;
	}
	public void setGoodsSourceName(String goodsSourceName) {
		this.goodsSourceName = goodsSourceName;
	}
	@Column
	public long getChanceId() {
		return chanceId;
	}
	public void setChanceId(long chanceId) {
		this.chanceId = chanceId;
	}
	@Column
	public String getChanceName() {
		return chanceName;
	}
	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
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
	
}
