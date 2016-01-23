package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 公司员工的账户
 * 记录公司员工的个人信息
 * @author Administrator
 *
 */
@Entity
public class ConpanyUser {
	private long id;
	private String username;//用户账号
	private String password;//用户密码
	private String email;//用户email
	private Date accuntEndDate;//账户到期时间
	private Date accuntStartDate;//账户开始日期
	private boolean freeAcccunt;//是不是免费的用户
	private boolean useLogin;//该账户是否启用
	private String image;//员工的照片
	private String phone;//员工电话
	private String address;//员工通讯地址
	private String idImage;//员工身份证照片
	private String state;//员工的状态，如临时工，小时工，合同工，实习工，等等
	private float price;//工资
	private String trueName;//真实姓名
	private long conpanyId;//所属公司
	private String idNum;
	private boolean sex;
	private String marks;
	private boolean conpanyAdmin;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public Date getAccuntEndDate() {
		return accuntEndDate;
	}
	public void setAccuntEndDate(Date accuntEndDate) {
		this.accuntEndDate = accuntEndDate;
	}
	@Column
	public Date getAccuntStartDate() {
		return accuntStartDate;
	}
	public void setAccuntStartDate(Date accuntStartDate) {
		this.accuntStartDate = accuntStartDate;
	}
	@Column
	public boolean isFreeAcccunt() {
		return freeAcccunt;
	}
	public void setFreeAcccunt(boolean freeAcccunt) {
		this.freeAcccunt = freeAcccunt;
	}
	@Column
	public boolean isUseLogin() {
		return useLogin;
	}
	public void setUseLogin(boolean useLogin) {
		this.useLogin = useLogin;
	}
	@Column
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getIdImage() {
		return idImage;
	}
	public void setIdImage(String idImage) {
		this.idImage = idImage;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	@Column
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	@Column
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	@Column(length=8000)
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	@Column
	public boolean isConpanyAdmin() {
		return conpanyAdmin;
	}
	public void setConpanyAdmin(boolean conpanyAdmin) {
		this.conpanyAdmin = conpanyAdmin;
	}

	
}
