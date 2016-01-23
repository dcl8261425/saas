package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Meeting {
	private long id;
	private long conpanyId;
	private boolean IPTest;
	private String ipAddress;
	private long managerUserId;
	private String managerUserTrueName;
	private Date managerDate;
	private String startDate;//上班时间
	private String endDate;//下班时间
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
	public boolean isIPTest() {
		return IPTest;
	}
	public void setIPTest(boolean iPTest) {
		IPTest = iPTest;
	}
	@Column
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	@Column
	public long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(long managerUserId) {
		this.managerUserId = managerUserId;
	}
	@Column
	public String getManagerUserTrueName() {
		return managerUserTrueName;
	}
	public void setManagerUserTrueName(String managerUserTrueName) {
		this.managerUserTrueName = managerUserTrueName;
	}
	@Column
	public Date getManagerDate() {
		return managerDate;
	}
	public void setManagerDate(Date managerDate) {
		this.managerDate = managerDate;
	}
	@Column
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
