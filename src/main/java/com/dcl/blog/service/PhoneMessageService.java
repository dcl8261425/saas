package com.dcl.blog.service;

import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.Orders;

public interface PhoneMessageService {
		//获取一个公司的信息设置
		public MessageSet getMessageSet(long conpanyId);
		//发送消息并记录
		public String getSendMessage(String phone,String content,long conpanyId,int type,boolean oneToOne);
		//判断是否可以发送
		public boolean getAllowSendMessage(long conpanyId,int type);
		/**
		 * 获取生成发送的内容 
		 * @param conpanyId 公司id
		 * @param type 发送的类型
		 * @param lk 联系人
		 * @param score 本次操作的积分
		 * @param Price 本次操作的钱
		 * @return
		 */
		public String getMessageSetContent(long conpanyId,int type,LinkManList lk,double score,double price,Orders order,String weixinContent);
		
}
