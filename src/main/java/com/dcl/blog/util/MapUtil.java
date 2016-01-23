package com.dcl.blog.util;

public class MapUtil {
	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	/**
	 * 获取远近数据坐标直接获取sql
	 */
	public static String Distance(String x, String y) {
		double max_lati=Double.parseDouble(x)-1;
		double min_lati=Double.parseDouble(x)+1;
		double max_long=Double.parseDouble(y)-1;
		double min_long=Double.parseDouble(y)+1;
		return "FROM ConpanyAddress WHERE (map_x BETWEEN "+min_lati+" AND "+max_lati+") AND (map_y BETWEEN "+min_lati+" AND "+max_long+")";
	}
}
