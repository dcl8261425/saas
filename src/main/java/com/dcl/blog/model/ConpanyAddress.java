package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConpanyAddress {
	private long id;
	private long conpanyId;//所属公司
	private String map_x;
	private String map_y;
	private String conpanyName;
	private String phone;
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
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getMap_x() {
		return map_x;
	}
	public void setMap_x(String map_x) {
		this.map_x = map_x;
	}
	@Column
	public String getMap_y() {
		return map_y;
	}
	public void setMap_y(String map_y) {
		this.map_y = map_y;
	}
	@Column
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
