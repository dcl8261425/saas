package com.dcl.blog.model.dto;

import java.util.Date;
import java.util.List;

import com.dcl.blog.model.ConpanyZoneImage;
import com.dcl.blog.model.ConpanyZoneTouPiao;
import com.dcl.blog.model.ConpanyZoneZan;

public class KongJianDto {
	private long id;
	private String title;
	private long createUserId;
	private String createUserName;
	private long conpanyId;
	private long groupId;
	private long zan;
	private Date createDate;
	private String content;
	private boolean touPiao;
	private int indexNum;
	private List<Object> imageList;
	private List<Object> vidioList;
	private List<Object> fileList;
	private ConpanyZoneTouPiao toupiaoList;
	private List<ConpanyZoneTouPiaoItemDto> toupiaoItemList;
	private List<ConpanyZoneRetDto> retList;
	private List<Object> zanList;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
	public List<Object> getImageList() {
		return imageList;
	}
	public void setImageList(List<Object> imageList) {
		this.imageList = imageList;
	}
	public ConpanyZoneTouPiao getToupiaoList() {
		return toupiaoList;
	}
	public void setToupiaoList(ConpanyZoneTouPiao toupiaoList) {
		this.toupiaoList = toupiaoList;
	}
	public List<ConpanyZoneTouPiaoItemDto> getToupiaoItemList() {
		return toupiaoItemList;
	}
	public void setToupiaoItemList(List<ConpanyZoneTouPiaoItemDto> toupiaoItemList) {
		this.toupiaoItemList = toupiaoItemList;
	}
	public List<ConpanyZoneRetDto> getRetList() {
		return retList;
	}
	public void setRetList(List<ConpanyZoneRetDto> retList) {
		this.retList = retList;
	}
	public List<Object> getZanList() {
		return zanList;
	}
	public void setZanList(List<Object> zanList) {
		this.zanList = zanList;
	}
	public List<Object> getVidioList() {
		return vidioList;
	}
	public void setVidioList(List<Object> vidioList) {
		this.vidioList = vidioList;
	}
	public List<Object> getFileList() {
		return fileList;
	}
	public void setFileList(List<Object> fileList) {
		this.fileList = fileList;
	}
	public boolean isTouPiao() {
		return touPiao;
	}
	public void setTouPiao(boolean touPiao) {
		this.touPiao = touPiao;
	}
	
}
