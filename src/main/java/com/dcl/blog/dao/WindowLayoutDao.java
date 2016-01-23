package com.dcl.blog.dao;

import java.util.List;

import com.dcl.blog.model.WindowLayout;

public interface WindowLayoutDao{
	public WindowLayout getWindowLayoutByUserIdAndUrl(long userId,String url);
	public List<WindowLayout> getWindowLayoutByUser(long userId,String mainUrls);
}
