package com.dcl.blog.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DataTypeTestUtil {
	public static final int INT=1;
	public static final int STRING=2;
	public static final int DATE=3;
	public static final int LONG=4;
	public static final int FLOAT=5;
	public static final int BOOLEAN=6;
	/**
	 * 用于检测请求的数据类型是否正确
	 * @param request
	 * @param typemap
	 * @return
	 */
	public static Map<String,Object> testDate(HttpServletRequest request,Map<String,Integer> typemap){
		Map<String,Object> map3=new HashMap<String, Object>();
		Enumeration e=request.getParameterNames();
		boolean b=true;
		List<Map> list=new ArrayList<Map>();
		while (e.hasMoreElements()) {
			Map<String, Object> map2=new HashMap<String, Object>();
			String s=(String) e.nextElement();
			String content=request.getParameter(s);
			boolean b2=false;
			String info="";
			if(s.equals("app")){
				continue;
			}
			if(s.equals("codeid")){
				continue;
			}
			if(typemap.get(s)==INT){
				try {
					Integer.parseInt(content);
				} catch (Exception e2) {
					b2=true;
					info="必须为数字";
					b=false;
				}
			}
			if(typemap.get(s)==STRING){
				try {
					String.valueOf(content);
				} catch (Exception e2) {
					b2=true;
					info="请输入内容";
					b=false;
				}
			}
			if(typemap.get(s)==DATE){
				try {
					DateUtil.toDateType(content);
				} catch (Exception e2) {
					b2=true;
					info="请输入日期";
					b=false;
				}
			}
			if(typemap.get(s)==LONG){
				try {
					Long.parseLong(content);
				} catch (Exception e2) {
					b2=true;
					info="请输入数字";
					b=false;
				}
			}
			if(typemap.get(s)==FLOAT){
				try {
					Double.parseDouble(content);
				} catch (Exception e2) {
					b2=true;
					info="请输入数字";
					b=false;
				}
			}
			if(typemap.get(s)==BOOLEAN){
				try {
					Boolean.parseBoolean(content);
				} catch (Exception e2) {
					b2=true;
					info="请选择";
					b=false;
				}
			}
			if(b2){
				map2.put("info", info);
				map2.put("name", s);
				map2.put("success", false);
				list.add(map2);
			}else{
				map2.put("info", "正确");
				map2.put("name", s);
				map2.put("success", true);
				list.add(map2);
			}
		}
		map3.put("success", b);
		map3.put("list", list);
		return map3;
	}
}
