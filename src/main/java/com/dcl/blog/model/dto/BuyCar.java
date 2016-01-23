package com.dcl.blog.model.dto;

import java.util.List;
import java.util.Map;

import com.dcl.blog.model.OrdersItem;

public class BuyCar {
	private Map<Long,List<Map<Long,OrdersItem>>> orderList;
	private String address;
	private String phone;
	private String username;
	private long userid;
	private long linkManId;
	private String marks;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getLinkManId() {
		return linkManId;
	}
	public void setLinkManId(long linkManId) {
		this.linkManId = linkManId;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public Map<Long,List<Map<Long,OrdersItem>>> getOrderList() {
		return orderList;
	}
	public void setOrderList(Map<Long,List<Map<Long,OrdersItem>>> orderList) {
		this.orderList = orderList;
	}
	
}
