package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ScoreToGoodsList {
 private long id;
 private String name;
 private long scoreDuiHuanId;
 private String xuliehao;
 private int num;
 private boolean uses;
 private long userid;
 private long linkmainId;
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
public long getScoreDuiHuanId() {
	return scoreDuiHuanId;
}
public void setScoreDuiHuanId(long scoreDuiHuanId) {
	this.scoreDuiHuanId = scoreDuiHuanId;
}
@Column
public String getXuliehao() {
	return xuliehao;
}
public void setXuliehao(String xuliehao) {
	this.xuliehao = xuliehao;
}
@Column
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
@Column
public boolean isUses() {
	return uses;
}
public void setUses(boolean uses) {
	this.uses = uses;
}
@Column
public long getUserid() {
	return userid;
}
public void setUserid(long userid) {
	this.userid = userid;
}
@Column
public long getConpanyId() {
	return conpanyId;
}
public void setConpanyId(long conpanyId) {
	this.conpanyId = conpanyId;
}
@Column
public long getLinkmainId() {
	return linkmainId;
}
public void setLinkmainId(long linkmainId) {
	this.linkmainId = linkmainId;
}


}
