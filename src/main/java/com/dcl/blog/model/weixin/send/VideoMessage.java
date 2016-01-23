package com.dcl.blog.model.weixin.send;

import java.util.HashMap;
import java.util.Map;

public class VideoMessage {
	private String access_token;
	private String touser;
	private String msgtype;
	private Map<String,String> video;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public Map<String, String> getVideo() {
		return video;
	}
	/**
	 * access_token	 是	 调用接口凭证
touser	 是	 普通用户openid
msgtype	 是	 消息类型，video
media_id	 是	 发送的视频的媒体ID
title	 否	 视频消息的标题
description	 否	 视频消息的描述
	 * @param media_id
	 * @param title
	 * @param description
	 */
	public void setVideo(String media_id,String title,String description) {
		video=new HashMap<String, String>();
		video.put("media_id", media_id);
		video.put("title", title);
		video.put("description", description);
	}


}
