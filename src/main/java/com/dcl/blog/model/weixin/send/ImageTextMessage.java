package com.dcl.blog.model.weixin.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageTextMessage {
	private String access_token;
	private String touser;
	private String msgtype;
	private Map<String, List<Map<String, String>>> news;
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
	public Map<String, List<Map<String, String>>> getNews() {
		return news;
	}
	public void setNews(List<Map<String,String>> articles) {
		news=new HashMap<String, List<Map<String,String>>>();
		news.put("articles", articles);
	}
	/**
	 * access_token	 是	 调用接口凭证
touser	 是	 普通用户openid
msgtype	 是	 消息类型，news
title	 否	 标题
description	 否	 描述
url	 否	 点击后跳转的链接
picurl	 否	 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
	 * @param title
	 * @param description
	 * @param url
	 * @param picurl
	 * @return
	 */
	public Map<String,String> getMap(String title,String description,String url,String picurl){
		Map<String,String> map=new HashMap<String, String>();
		map.put("title", title);
		map.put("description", description);
		map.put("url", url);
		map.put("picurl", picurl);
		return map;
	}
	
}
