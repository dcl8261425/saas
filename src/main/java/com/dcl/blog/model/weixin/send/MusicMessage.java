package com.dcl.blog.model.weixin.send;

import java.util.HashMap;
import java.util.Map;

public class MusicMessage {
	private String access_token;
	private String touser;
	private String msgtype;
	private Map<String,String> music;
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
	public Map<String, String> getMusic() {
		return music;
	}
	/**
	 * access_token	 是	 调用接口凭证
touser	 是	 普通用户openid
msgtype	 是	 消息类型，music
title	 否	 音乐标题
description	 否	 音乐描述
musicurl	 是	 音乐链接
hqmusicurl	 是	 高品质音乐链接，wifi环境优先使用该链接播放音乐
thumb_media_id	 是	 缩略图的媒体ID
	 * @param musicurl
	 * @param title
	 * @param description
	 * @param hqmusicurl
	 * @param thumb_media_id
	 */
	public void setMusic(String musicurl,String title,String description,String hqmusicurl,String thumb_media_id) {
		music=new HashMap<String, String>();
		music.put("musicurl", musicurl);
		music.put("title", title);
		music.put("description", description);
		music.put("hqmusicurl", hqmusicurl);
		music.put("thumb_media_id", thumb_media_id);
	}


}
