package com.dcl.blog.model.json;

import java.util.List;
import java.util.Map;

public class FlowJson {
	public static final String NODE_NAME="NODE_NAME";
	public static final String NODE_OBJ="NODE_OBJ";
	public static final String LINE_NAME="LINE_NAME";
	public static final String LINE_OBJ="LINE_OBJ";
	public static final String AREAS_NAME="AREAS_NAME";
	public static final String AREAS_OBJ="AREAS_OBJ";
	private String title;
	private List<Map<String,Object>> nodes;
	private List<Map<String,Object>> lines;
	private List<Map<String,Object>> areas;
	private int initNum;
	public int getInitNum() {
		return initNum;
	}
	public void setInitNum(int initNum) {
		this.initNum = initNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Map<String, Object>> getNodes() {
		return nodes;
	}
	public void setNodes(List<Map<String, Object>> nodes) {
		this.nodes = nodes;
	}
	public List<Map<String, Object>> getLines() {
		return lines;
	}
	public void setLines(List<Map<String, Object>> lines) {
		this.lines = lines;
	}
	public List<Map<String, Object>> getAreas() {
		return areas;
	}
	public void setAreas(List<Map<String, Object>> areas) {
		this.areas = areas;
	}
	
	
	
}
