package com.dcl.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.dcl.blog.model.Hangye;
import com.dcl.blog.service.DaoService;

public class StringUtil {
	/**
	 * 检测email
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(isStringIsNull(email)){
			return false;
		}
		String checkPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		   Pattern regex = Pattern.compile(checkPattern);
		   Matcher matcher = regex.matcher(email);
		   return matcher.find();
	}
	/**
	 * 检测phone
	 * @param phone
	 * @return
	 */
	public static boolean isphone(String phone){
		if(isStringIsNull(phone)){
			return false;
		}
		String checkPattern = "1[0-9]{10}";
		   Pattern regex = Pattern.compile(checkPattern);
		   Matcher matcher = regex.matcher(phone);
		   return matcher.find();
	}
	/**
	 * 检测邮编
	 * @param phone
	 * @return
	 */
	public static boolean isCode(String code){
		if(isStringIsNull(code)){
			return false;
		}
			String checkPattern = "[1-9]{1}(\\d+){5}";
		   Pattern regex = Pattern.compile(checkPattern);
		   Matcher matcher = regex.matcher(code);
		   return matcher.find();
	}
	/**
	 * 检测身份证
	 * @param shenfen
	 * @return
	 */
	public static boolean isShenfen(String shenfen){
		if(isStringIsNull(shenfen)){
			return false;
		}
			String checkPattern = "\\d{18}|\\d{15}";
		   Pattern regex = Pattern.compile(checkPattern);
		   Matcher matcher = regex.matcher(shenfen);
		   return matcher.find();
	}
	/**
	 * 判断字符串是不是为空
	 * 是空返回true;
	 */
	public static boolean isStringIsNull(String str){
		if(str==null){
			return true;
		}
		if(str.trim().equals("")){
			return true;
		}
		return false;
	}
	/**
	 * 判断行业
	 * 是空返回true;
	 */
	public static Hangye isHangyeIsNull(String str,HttpServletRequest req,DaoService dao){
		Hangye hy=null;
		hy=(Hangye) dao.getObject(Long.parseLong(str), "Hangye");
		return hy;
	}
}
