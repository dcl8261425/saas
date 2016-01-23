package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class VWiFi {
	private long id;
	private long conpanyId;
	private String name;
	private boolean useUp;
	private boolean webRigister;
	private String tokens;
	private String htmlContent;
	private Integer sys_uptime;//路由器启动时间
	private Integer sys_memfree;//系统剩余内存 kb
	private Float sys_load;
	private Integer wifidog_uptime;//wifidog运行时间
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
	public long getConpanyId() {
		return conpanyId;
	}

	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column
	public boolean isUseUp() {
		return useUp;
	}

	public void setUseUp(boolean useUp) {
		this.useUp = useUp;
	}
	@Column
	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}
	@Column(length=80000)
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	@Column
	public Integer getSys_uptime() {
		return sys_uptime;
	}

	public void setSys_uptime(Integer sys_uptime) {
		this.sys_uptime = sys_uptime;
	}
	@Column
	public Integer getSys_memfree() {
		return sys_memfree;
	}

	public void setSys_memfree(Integer sys_memfree) {
		this.sys_memfree = sys_memfree;
	}
	@Column
	public Float getSys_load() {
		return sys_load;
	}

	public void setSys_load(Float sys_load) {
		this.sys_load = sys_load;
	}
	@Column
	public Integer getWifidog_uptime() {
		return wifidog_uptime;
	}

	public void setWifidog_uptime(Integer wifidog_uptime) {
		this.wifidog_uptime = wifidog_uptime;
	}
	@Column
	public boolean isWebRigister() {
		return webRigister;
	}

	public void setWebRigister(boolean webRigister) {
		this.webRigister = webRigister;
	}
	
	
}
