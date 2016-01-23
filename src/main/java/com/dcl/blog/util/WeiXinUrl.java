package com.dcl.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;

public class WeiXinUrl {
	/**
	 * 获取access_token是公众号的全局唯一票据，
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getAccess_token(String appid,String secret){
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
	}
	/**
	 * 图片
	 */
	public static final String UPDATE_TYPE_IMAGE="image";
	/**
	 * 声音
	 */
	public static final String UPDATE_TYPE_VOICE="voice";
	/**
	 * 视频
	 */
	public static final String UPDATE_TYPE_VIDEO="video";
	/**
	 * 缩略图
	 */
	public static final String UPDATE_TYPE_THUMB="thumb";
	/**
	 * 上传文件 media form-data中媒体文件标识，有filename、filelength、content-type等信息
	 * @param access_token
	 * @param type
	 * @return
	 */
	public static String getUpdateFile(String access_token,String type){
		return "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+access_token+"&type="+type;
	}
	/**
	 * 下载多媒体文件
	 * @param access_token
	 * @param media_id
	 * @return
	 */
	public static String getDewnLoad(String access_token,String media_id){
		return "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+media_id;
	}
	/**
	 * 当用户主动发消息给公众号的时候（包括发送信息、点击自定义菜单click事件、订阅事件、扫描二维码事件、支付成功事件、用户维权），微信将会把消息数据推送给开发者，开发者在一段时间内（目前修改为48小时）可以调用客服消息接口，通过POST一个JSON数据包来发送消息给普通用户，在48小时内不限制发送次数。此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
	 */
	public static String sendMessage(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
	}
	/**
	 * 创建分组
	 */
	public static String getCreateGroup(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/groups/create?access_token="+access_token;
	}
	/**
	 * 查询分组
	 */
	public static String getLooKUpGroup(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+access_token;
	}
	/**
	 * 查询用户所在组
	 */
	public static String queryUserByGroup(String access_token,String openid){
		return "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="+access_token;
	}
	/**
	 * 修改分组名
	 */
	public static String updateUserByGroup(String access_token,String id,String name){
		return "https://api.weixin.qq.com/cgi-bin/groups/update?access_token="+access_token;
	}
	/**
	 * 移动用户分组
	 */
	public static String moveUserToGroup(String access_token,String openid,String to_groupid){
		return "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="+access_token;
	}
	/**
	 * 获取用户信息
	 */
	public static String getUserInfo(String access_token,String openid){
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	}
	/**
	 * 获取关注用户列表
	 */
	public static String getUserList(String access_token,String next_openid){
		return "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&next_openid="+next_openid;
	}
	/**
	 * 接口调用请求说明
	 * button	 是	 一级菜单数组，个数应为1~3个
sub_button	 否	 二级菜单数组，个数应为1~5个
type	 是	 菜单的响应动作类型，目前有click、view两种类型
name	 是	 菜单标题，不超过16个字节，子菜单不超过40个字节
key	 click类型必须	 菜单KEY值，用于消息接口推送，不超过128字节
url	 view类型必须	 网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	public static String createMenu(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
	}
	/**
	 * 获取菜单
	 */
	public static String getMenu(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+access_token;
	}
	/**
	 * 删除
	 * @param access_token
	 * @return
	 */
	public static String deletdMenu(String access_token){
		return "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+access_token;
	}
	//从输入流读取post参数
		public	static String readStreamParameter(ServletInputStream in){
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader=null;
			try{
				reader = new BufferedReader(new InputStreamReader(in));
				String line=null;
				while((line = reader.readLine())!=null){
					buffer.append(line);
		        }
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=reader){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return buffer.toString();
		}
}
