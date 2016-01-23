package com.dcl.blog.service;

import java.util.List;

import com.dcl.blog.model.WindowLayout;

public interface WindowLayoutService {
	/**
	 * 通过用户id和url获取功能块
	 * @param userId
	 * @param url
	 * @return
	 */
	public WindowLayout getWindowLayoutByUserIdAndUrl(long userId,String url);
	/**
	 * 查询一个用户的某个模块的已有功能块
	 * @param userId
	 * @param mainUrls
	 * @return
	 */
	public List<WindowLayout> getWindowLayoutByUser(long userId,String mainUrls);
}
