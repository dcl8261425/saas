package com.dcl.blog.service;

import java.util.List;

import com.dcl.blog.model.Message;

public interface MessageService {
	/**
	 * 获取用户全部信息
	 * @param userid
	 * @param conpanyid
	 * @return
	 */
	public List<Message> getMessage(long userid,long conpanyid);
	/**
	 * 获取用户未读信息
	 * @param userid
	 * @param conpanyid
	 * @return
	 */
	public List<Message> getMessageNotRead(long userid,long conpanyid);
	/**
	 * 获取用户已读信息
	 * @param userid
	 * @param conpanyid
	 * @return
	 */
	public List<Message> getMessageRead(long userid,long conpanyid);
	
}
