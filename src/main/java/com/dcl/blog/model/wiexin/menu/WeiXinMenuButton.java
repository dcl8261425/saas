package com.dcl.blog.model.wiexin.menu;

import java.util.List;

import com.dcl.blog.model.WeiXin;

public class WeiXinMenuButton {
	private String name;
	private List<WeiXinMenuSubButton> sub_button;
	private String type;
	private String key;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WeiXinMenuSubButton> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<WeiXinMenuSubButton> sub_button) {
		this.sub_button = sub_button;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
