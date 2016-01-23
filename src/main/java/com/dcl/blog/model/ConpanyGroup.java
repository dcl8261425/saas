package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 组
 * 利用此表可以创建出公司的组织机构
 * @author Administrator
 *
 */
@Entity
public class ConpanyGroup {
	private long id;
	private String groupName;//组名称
	private String groupMarks;//备注
	private long conpanyId;//所属公司
	private long upLevelConpanyGroup;//上一层组
	private long createConpanyGroupUserId;//创建人id
	private String createConpanyGroupUserTrueName;//创建组人物的真实名字
	private String createConpanyGroupUserName;//创建组人物的账号
	private long userNum;//用户数量
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(length=4000)
	public String getGroupMarks() {
		return groupMarks;
	}
	public void setGroupMarks(String groupMarks) {
		this.groupMarks = groupMarks;
	}
	@Column
	public long getUpLevelConpanyGroup() {
		return upLevelConpanyGroup;
	}
	public void setUpLevelConpanyGroup(long upLevelConpanyGroup) {
		this.upLevelConpanyGroup = upLevelConpanyGroup;
	}
	@Column
	public long getCreateConpanyGroupUserId() {
		return createConpanyGroupUserId;
	}
	public void setCreateConpanyGroupUserId(long createConpanyGroupUserId) {
		this.createConpanyGroupUserId = createConpanyGroupUserId;
	}
	@Column
	public String getCreateConpanyGroupUserTrueName() {
		return createConpanyGroupUserTrueName;
	}
	public void setCreateConpanyGroupUserTrueName(
			String createConpanyGroupUserTrueName) {
		this.createConpanyGroupUserTrueName = createConpanyGroupUserTrueName;
	}
	@Column
	public long getUserNum() {
		return userNum;
	}
	public void setUserNum(long userNum) {
		this.userNum = userNum;
	}
	@Column
	public String getCreateConpanyGroupUserName() {
		return createConpanyGroupUserName;
	}
	public void setCreateConpanyGroupUserName(String createConpanyGroupUserName) {
		this.createConpanyGroupUserName = createConpanyGroupUserName;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
