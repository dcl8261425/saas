package com.dcl.blog.model.weixin.auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AotuSendImageTextMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String articleCount;
	private AotuSendImageTextMessageContentList articles;
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
	/**
	 * 图文消息个数，限制为10条以内
	 * @return
	 */
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	public AotuSendImageTextMessageContentList getArticles() {
		return articles;
	}
	public void setArticles(AotuSendImageTextMessageContentList articles) {
		this.articles = articles;
	}
	

	
}
