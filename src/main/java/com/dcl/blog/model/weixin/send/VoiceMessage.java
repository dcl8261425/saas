package com.dcl.blog.model.weixin.send;

import java.util.HashMap;
import java.util.Map;

public class VoiceMessage {
	private String access_token;
	private String touser;
	private String msgtype;
	private Map<String,String> voice;
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
	public Map<String, String> getVoice() {
		return voice;
	}
	/**
	 * access_token	 是	 调用接口凭证
touser	 是	 普通用户openid
msgtype	 是	 消息类型，voice
media_id	 是	 发送的语音的媒体ID
	 * @param media_id
	 */
	public void setVoice(String media_id) {
		voice=new HashMap<String, String>();
		voice.put("media_id", media_id);
	}

}
