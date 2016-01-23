package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class GoodsLog {

	public static final int ACTION_ADD=1;
	public static final int ACTION_REDUCE=2;
	public static final int ACTION_UPDATE_PRICE=3;
	private long id;
	private long goodsId;
	private String goodsName;
	private String goodsType;
	private double goodsinPrice;
	private long goodsSourceId;
	private String goodsSourceName;
	private double goodsNum;
	private long goodsToStorehouseId;
	private Date startdate;
	private String goodsToStorehouseName;
	private long conpanyId;
	private String codeid;//货物的扫码
	private String spell;//拼音简写
	private String goodsModel;//货物型号
	private double salesNum;
	private int action;
	private double price;
	private double totalPrice;
	private double totalInPrice;
	private String createManName;
	private long createManId;
	private long chanceId;
	private String chanceName;
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
	public long getGoodsToStorehouseId() {
		return goodsToStorehouseId;
	}
	public void setGoodsToStorehouseId(long goodsToStorehouseId) {
		this.goodsToStorehouseId = goodsToStorehouseId;
	}
	@Column
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	@Column
	public String getGoodsToStorehouseName() {
		return goodsToStorehouseName;
	}
	public void setGoodsToStorehouseName(String goodsToStorehouseName) {
		this.goodsToStorehouseName = goodsToStorehouseName;
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
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	@Column
	public String getCreateManName() {
		return createManName;
	}
	public void setCreateManName(String createManName) {
		this.createManName = createManName;
	}
	@Column
	public long getCreateManId() {
		return createManId;
	}
	public void setCreateManId(long createManId) {
		this.createManId = createManId;
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
	
	
}
