package com.dcl.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class WeiXinAutoReSendMenu {
	public static int TYPE_TEXT=1;
	public static int TYPE_IMAGE=2;
	public static int TYPE_VOICE=3;
	public static int TYPE_VIDEO=4;
	public static int TYPE_LOCATION=5;
	public static int TYPE_LINK=6;
	public static int TYPE_EVENT=7;
	public static int EVENT_SUBSCRIBE=1;//关注subscribe
	public static int EVENT_UNSUBSCRIB=2;//取消关注
	public static int EVENT_LOCATION=3;
	public static int EVENT_CLICK=4;
	private long id;
	private String name;
	private long weixin_keys;
	private String content;
	private String weixin_events;
	private long type;
	private boolean uses;
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
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	@Column
	public boolean isUses() {
		return uses;
	}
	public void setUses(boolean uses) {
		this.uses = uses;
	}
	@Column
	public long getWeixin_keys() {
		return weixin_keys;
	}
	public void setWeixin_keys(long weixin_keys) {
		this.weixin_keys = weixin_keys;
	}
	@Column
	public String getWeixin_events() {
		return weixin_events;
	}
	public void setWeixin_events(String weixin_events) {
		this.weixin_events = weixin_events;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public long getConpanyId() {
		return conpanyId;
	}
	public void setConpanyId(long conpanyId) {
		this.conpanyId = conpanyId;
	}
	
}
