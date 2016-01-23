package com.dcl.blog.model.weixin.auto;

import java.util.HashMap;
import java.util.Map;

public class AotuSendImageMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private AotuSendImageMessageImage image;
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public AotuSendImageMessageImage getImage() {
		return image;
	}
	public void setImage(String media_id) {
		AotuSendImageMessageImage image=new AotuSendImageMessageImage();
		image.setMedia_id(media_id);
	}

	
}
