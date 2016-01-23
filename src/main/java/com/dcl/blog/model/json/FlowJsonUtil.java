package com.dcl.blog.model.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dcl.blog.util.text.JSONObject;

public class FlowJsonUtil {
	public static FlowJson getFlowJson(String jsonString,String name){
		 JSONObject obj =new JSONObject(jsonString);
		 FlowJson flow=new FlowJson();
		 flow.setTitle(name);
		 List<Map<String,Object>> nodelist=new ArrayList<Map<String,Object>>();
		 List<Map<String,Object>> linelist=new ArrayList<Map<String,Object>>();
		 List<Map<String,Object>> areaslist=new ArrayList<Map<String,Object>>();
		 JSONObject nodes=(JSONObject) obj.get("nodes");
		 Set set=nodes.keySet();
		 Iterator t=set.iterator();
		 while(t.hasNext()){
			 String objNodeName=(String) t.next();
			 JSONObject jsons=nodes.getJSONObject(objNodeName);
			 Map<String,Object> nodemap=new HashMap<String, Object>();
			 FlowJsonNodeItem nodeItem=new FlowJsonNodeItem();
			String names= jsons.getString("name");
			int left=jsons.getInt("left");
			int top=jsons.getInt("top");
			String type=jsons.getString("type");
			int width=jsons.getInt("width");
			int height=jsons.getInt("height");
			boolean alt= jsons.getBoolean("alt");
			nodeItem.setAlt(alt);
			nodeItem.setHeight(height);
			nodeItem.setLeft(left);
			nodeItem.setName(names);
			nodeItem.setTop(top);
			nodeItem.setType(type);
			nodeItem.setWidth(width);
			nodemap.put(FlowJson.NODE_NAME, objNodeName);
			nodemap.put(FlowJson.NODE_OBJ, nodeItem);
			nodelist.add(nodemap);
		 }
		 JSONObject lines=(JSONObject) obj.get("lines");
		 Set set2=lines.keySet();
		 Iterator t2=set2.iterator();
		 while(t2.hasNext()){
			 String objNodeName=(String) t2.next();
			 JSONObject jsons=lines.getJSONObject(objNodeName);
			 Map<String,Object> linemap=new HashMap<String, Object>();
			 FlowJsonLineItem line=new FlowJsonLineItem();
			String type= jsons.getString("type");
			String from=jsons.getString("from");
			String to=jsons.getString("to");
			String names=jsons.getString("name");
			boolean alt=jsons.getBoolean("alt");
			line.setAlt(alt);
			line.setFrom(from);
			line.setName(names);
			line.setTo(to);
			line.setType(type);
			linemap.put(FlowJson.LINE_NAME, objNodeName);
			linemap.put(FlowJson.LINE_OBJ, line);
			linelist.add(linemap);
		 }
		 JSONObject areas=(JSONObject) obj.get("areas");
		 Set set3=areas.keySet();
		 Iterator t3=set3.iterator();
		 while(t3.hasNext()){
			 String objNodeName=(String) t3.next();
			 JSONObject jsons=areas.getJSONObject(objNodeName);
			 Map<String,Object> areasmap=new HashMap<String, Object>();
			 FlowJsonAreasItem areasItem=new FlowJsonAreasItem();
			String names= jsons.getString("name");
			int left=jsons.getInt("left");
			int top=jsons.getInt("top");
			String color=jsons.getString("color");
			int width=jsons.getInt("width");
			int height=jsons.getInt("height");
			boolean alt= jsons.getBoolean("alt");
			areasItem.setAlt(alt);
			areasItem.setHeight(height);
			areasItem.setLeft(left);
			areasItem.setName(names);
			areasItem.setTop(top);
			areasItem.setColor(color);
			areasItem.setWidth(width);
			areasmap.put(FlowJson.AREAS_NAME, objNodeName);
			areasmap.put(FlowJson.AREAS_OBJ, areasItem);
			areaslist.add(areasmap);
		 }
		 flow.setAreas(areaslist);
		 flow.setLines(linelist);
		 flow.setNodes(nodelist);
		 int initNum=obj.getInt("initNum");
		 flow.setInitNum(initNum);
		 return flow;
	}
}
