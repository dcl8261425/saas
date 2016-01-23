package com.dcl.blog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 字符串转换日期类  字符格式yyyy-mm-dd
	 * @return
	 */
	public static Date toDateType(String date){
		try{
			String[] dates=date.split("-");
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
			return cal.getTime();
		}catch(Exception ex){
			return null;
		}
		
	}
	/**
	 * 比较当前日期是否是两个日期之间。
	 */
	public static boolean betweenNowDate(Date startDate,Date endDate){
		Date date=new Date();
		Calendar c=Calendar.getInstance();
		//以为比较的时候只能之前或者之后，所有前后都要做操作。
		c.setTime(startDate);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
		startDate=c.getTime();
		c.setTime(endDate);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
		endDate=c.getTime();
		return date.before(endDate)&&date.after(startDate);
	}
	/**
	 * 检测日期是否在同一天
	 */
	public static boolean validateDateIsNowDate(Date date){
		Date nowdate=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		date=c.getTime();
		c.setTime(nowdate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, -1);
		nowdate=c.getTime();
		if(nowdate.after(date)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 日期格式化输出
	 */
	public static String formatDateYYYY_MM_DD(Date date){
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		return time.format(date);
	}
}
