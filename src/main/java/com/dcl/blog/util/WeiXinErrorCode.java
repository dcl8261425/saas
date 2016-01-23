package com.dcl.blog.util;

public class WeiXinErrorCode {
	public static String getString(int code){
		String str="";
		switch (code) {
		case -1:
			str="系统繁忙";
			break;
		case 0:
			str="请求成功";
			break;
		case 40001:
			str="获取access_token时AppSecret错误，或者access_token无效";
			break;
		case 40002:
			str="不合法的凭证类型";
			break;
		case 40003:
			str="不合法的OpenID";
			break;
		case 40004:
			str="不合法的媒体文件类型";
			break;
		case 40005:
			str="不合法的文件类型";
			break;
		case 40006:
			str="不合法的文件大小";
			break;
		case 40007:
			str="不合法的媒体文件id";
			break;
		case 40008:
			str="不合法的消息类型";
			break;
		case 40009:
			str="不合法的图片文件大小";
			break;
		case 40010:
			str="不合法的语音文件大小";
			break;
		case 40011:
			str="不合法的视频文件大小";
			break;
		case 40012:
			str="不合法的缩略图文件大小";
			break;
		case 40013:
			str="不合法的APPID";
			break;
		case 40014:
			str="不合法的access_token";
			break;
		case 40015:
			str="不合法的菜单类型";
			break;
		case 40016:
			str="不合法的按钮个数";
			break;
		case 40017:
			str="不合法的按钮个数";
			break;
		case 40018:
			str="不合法的按钮名字长度";
			break;
		case 40019:
			str="不合法的按钮KEY长度";
			break;
		case 40020:
			str="不合法的按钮URL长度";
			break;
		case 40021:
			str="不合法的菜单版本号";
			break;
		case 40022:
			str="不合法的子菜单级数";
			break;
		case 40023:
			str="不合法的子菜单按钮个数";
			break;
		case 40024:
			str="不合法的子菜单按钮类型";
			break;
		case 40025:
			str="不合法的子菜单按钮名字长度";
			break;
		case 40026:
			str="不合法的子菜单按钮KEY长度";
			break;
		case 40027:
			str="不合法的子菜单按钮URL长度";
			break;
		case 40028:
			str="不合法的自定义菜单使用用户";
			break;
		case 40029:
			str="不合法的oauth_code";
			break;
		case 40031:
			str="不合法的openid列表";
			break;
		case 40032:
			str="不合法的openid列表长度";
			break;
		case 40033:
			str="不合法的请求字符，不能包含\\uxxxx格式的字符";
			break;
		case 40035:
			str="不合法的参数";
			break;
		case 40038:
			str="不合法的请求格式";
			break;
		case 40039:
			str="不合法的URL长度";
			break;
		case 40050:
			str="不合法的分组id";
			break;
		case 40051:
			str="分组名字不合法";
			break;
		case 41001:
			str="缺少access_token参数";
			break;
		case 41002:
			str="缺少appid参数";
			break;
		case 41003:
			str="缺少refresh_token参数";
			break;
		case 41004:
			str="缺少secret参数";
			break;
		case 41005:
			str="缺少多媒体文件数据";
			break;
		case 41006:
			str="缺少media_id参数";
			break;
		case 41007:
			str="缺少子菜单数据";
			break;
		case 41008:
			str="缺少oauth code";
			break;
		case 41009:
			str="缺少openid";
			break;
		case 42001:
			str="access_token超时";
			break;
		case 42002:
			str="refresh_token超时";
			break;
		case 42003:
			str="oauth_code超时";
			break;
		case 43001:
			str="需要GET请求";
			break;
		case 43002:
			str="需要POST请求";
			break;
		case 43003:
			str="需要HTTPS请求";
			break;
		case 43004:
			str="需要接收者关注";
			break;
		case 43005:
			str="需要好友关系";
			break;
		case 44001:
			str="多媒体文件为空";
			break;
		case 44002:
			str="POST的数据包为空";
			break;
		case 44003:
			str="图文消息内容为空";
			break;
		case 44004:
			str="文本消息内容为空";
			break;
		case 45001:
			str="多媒体文件大小超过限制";
			break;
		case 45002:
			str="消息内容超过限制";
			break;
		case 45003:
			str="标题字段超过限制";
			break;
		case 45004:
			str="描述字段超过限制";
			break;
		case 45005:
			str="链接字段超过限制";
			break;
		case 45006:
			str="图片链接字段超过限制";
			break;
		case 45007:
			str="语音播放时间超过限制";
			break;
		case 45008:
			str="图文消息超过限制";
			break;
		case 45009:
			str="接口调用超过限制";
			break;
		case 45010:
			str="创建菜单个数超过限制";
			break;
		case 45015:
			str="回复时间超过限制";
			break;
		case 45016:
			str="系统分组，不允许修改";
			break;
		case 45017:
			str="分组名字过长";
			break;
		case 45018:
			str="分组数量超过上限";
			break;
		case 46001:
			str="不存在媒体数据";
			break;
		case 46002:
			str="不存在的菜单版本";
			break;
		case 46003:
			str="不存在的菜单数据";
			break;
		case 46004:
			str="不存在的用户";
			break;
		case 47001:
			str="解析JSON/XML内容错误";
			break;
		case 48001:
			str="api功能未授权";
			break;
		case 50001:
			str="用户未授权该api";
			break;
		default:
			str="未知错误";
			break;
		}
		return str;
	}
}
