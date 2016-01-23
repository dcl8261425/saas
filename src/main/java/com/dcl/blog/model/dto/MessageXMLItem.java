package com.dcl.blog.model.dto;

public class MessageXMLItem {
	private String cid;//发送短信的企业编号
	private String sid;//发送短信的员工编号
	private String msgid;//每次发送的消息编号
	private String total;//任务需要的短信条数
	private String price;//任务中每条短信的价格
	private String remain;//本次发送后企业的帐户余额
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRemain() {
		return remain;
	}
	public void setRemain(String remain) {
		this.remain = remain;
	}
	
}
