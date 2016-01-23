package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 权限表
 * 记录程序中各个功能的访问权限
 * @author Administrator
 *
 */
@Entity
public class SoftPermission {
	private long id;
	private String functionName;//功能名
	private long uplevel;//上级功能id
	private String url;//功能的url
	private String marks;//备注
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
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	@Column
	public long getUplevel() {
		return uplevel;
	}
	public void setUplevel(long uplevel) {
		this.uplevel = uplevel;
	}
	@Column
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(length=4000)
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	
}
