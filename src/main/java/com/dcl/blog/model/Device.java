package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Device {
	private long id;//
	private long ap_id;
	private String ip;//连接设备的ip地址
	private String mac;// mac地址
	private Integer outgoing;//上传流量 单位 b
	private Integer incoming;//下载流量 单位 b
	private long conpanyId;
	private String token; 
	private String tokens; 
	private long countNum;//登陆次数
	private String linkmainName;
	private boolean noLogin;
	private Date endDate;
	@Id
	@GeneratedValue
	@GenericGenerator(name = "generator", strategy = "increment")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column
	public long getAp_id() {
		return ap_id;
	}
	public void setAp_id(long ap_id) {
		this.ap_id = ap_id;
	}
	@Column
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Column
	public Integer getOutgoing() {
		return outgoing;
	}
	public void setOutgoing(Integer outgoing) {
		this.outgoing = outgoing;
	}
	@Column
	public Integer getIncoming() {
		return incoming;
	}
	public void setIncoming(Integer incoming) {
		this.incoming = incoming;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Column
	public String getTokens() {
		return tokens;
	}
	public void setTokens(String tokens) {
		this.tokens = tokens;
	}
	@Column
	public String getLinkmainName() {
		return linkmainName;
	}
	public void setLinkmainName(String linkmainName) {
		this.linkmainName = linkmainName;
	}
	@Column
	public boolean isNoLogin() {
		return noLogin;
	}
	public void setNoLogin(boolean noLogin) {
		this.noLogin = noLogin;
	}
	@Column
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column
	public long getCountNum() {
		return countNum;
	}
	public void setCountNum(long countNum) {
		this.countNum = countNum;
	}
	
}
