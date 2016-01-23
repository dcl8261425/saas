package com.dcl.blog.model.weixin.auto;

import java.util.HashMap;
import java.util.Map;

public class AotuSendVoiceMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private AotuSendImageMessageImage voice;
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
	public AotuSendImageMessageImage getVoice() {
		return voice;
	}
	public void setVoice(String media_id) {
		voice=new AotuSendImageMessageImage();
		voice.setMedia_id(media_id);
	}
	
	
}
