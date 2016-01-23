package com.dcl.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class NumLibs {
		private long id;
		private String content;
		private long awardsId;
		private String xuliehao;
		private Date startDate;
		private Date endDate;
		private boolean uses;
		private long userid;
		private long linkmanId;
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
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@Column
		public String getXuliehao() {
			return xuliehao;
		}
		public void setXuliehao(String xuliehao) {
			this.xuliehao = xuliehao;
		}
		@Column
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		@Column
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
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
		public long getAwardsId() {
			return awardsId;
		}
		public void setAwardsId(long awardsId) {
			this.awardsId = awardsId;
		}
		private long appShopId;
		@Column
		public long getAppShopId() {
			return appShopId;
		}
		public void setAppShopId(long appShopId) {
			this.appShopId = appShopId;
		}
		@Column
		public long getConpanyId() {
			return conpanyId;
		}
		public void setConpanyId(long conpanyId) {
			this.conpanyId = conpanyId;
		}
		@Column
		public long getLinkmanId() {
			return linkmanId;
		}
		public void setLinkmanId(long linkmanId) {
			this.linkmanId = linkmanId;
		}
		
}
