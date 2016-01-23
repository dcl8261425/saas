package com.dcl.blog.model.weixin.send;

import java.util.HashMap;
import java.util.Map;

public class TextMessage {
	private String access_token;
	private String touser;
	private String msgtype;
	private Map<String,String> text;
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
	public Map<String, String> getText() {
		return text;
	}
	/**
	 * access_token	 是	 调用接口凭证
touser	 是	 普通用户openid
msgtype	 是	 消息类型，text
content	 是	 文本消息内容
	 * @param content
	 */
	public void setText(String content) {
		text=new HashMap<String, String>();
		text.put("content", content);
	}
	
}
