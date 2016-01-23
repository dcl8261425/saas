package com.dcl.blog.model.dto;

import java.util.List;

public class MessageXML {
	private String name;
	private String result;
	private List<MessageXMLItem> Items;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<MessageXMLItem> getItems() {
		return Items;
	}
	public void setItems(List<MessageXMLItem> items) {
		Items = items;
	}
	
}
