package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class OrdersItem {
	private long id;
	private String goodsName;
	private String goodsType;
	private double goodsinPrice;
	private long goodsSourceId;
	private String goodsSourceName;
	private double goodsNum;
	private float price;
	private double score;
	private long goodsToStorehouseId;
	private String goodsToStorehouseName;
	private Date createDate;
	private long inOrderId;
	private long goodsId;
	private String codeid;//货物的扫码
	private String spell;//拼音简写
	private String goodsModel;//货物型号
	private long conpanyId;
	private String marks;
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
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
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
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public double getGoodsinPrice() {
		return goodsinPrice;
	}
	public void setGoodsinPrice(double goodsinPrice) {
		this.goodsinPrice = goodsinPrice;
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
	public double getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(double goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Column
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Column
	public long getGoodsToStorehouseId() {
		return goodsToStorehouseId;
	}
	public void setGoodsToStorehouseId(long goodsToStorehouseId) {
		this.goodsToStorehouseId = goodsToStorehouseId;
	}
	@Column
	public String getGoodsToStorehouseName() {
		return goodsToStorehouseName;
	}
	public void setGoodsToStorehouseName(String goodsToStorehouseName) {
		this.goodsToStorehouseName = goodsToStorehouseName;
	}
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column
	public long getInOrderId() {
		return inOrderId;
	}
	public void setInOrderId(long inOrderId) {
		this.inOrderId = inOrderId;
	}
	@Column
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	
	
}
