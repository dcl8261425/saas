package com.dcl.blog.service;

import java.util.List;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserMeeting;
import com.dcl.blog.model.Meeting;
import com.dcl.blog.model.Performance;

public interface HrService {
	public List<ConpanyUser> getConpanyUser(long conpanyId,String trueName);
	public Long getConpanyUserNum(long conpanyId,String trueName);
	public List<ConpanyUser> getConpanyUserPage(long conpanyId,String trueName,int nowPage,int countRow);
	/**
	 * 通过日期获取那天的签到记录
	 * @param date
	 * @param conpanyId
	 * @return
	 */
	public List<ConpanyUserMeeting> getConpanUserMeetingByDate(String date,long conpanyId);
	/**
	 * 通过日期和用户id获取 签到记录
	 * @param date
	 * @param conpanyId
	 * @return
	 */
	public List<ConpanyUserMeeting> getConpanUserMeetingByDateAndUser(String date,long userId,long conpanyId);
	/**
	 * 创建当天所有公司员工的空签到记录
	 */
	public boolean getCreateConpanyCusteemerMeeting(long conpanyId);
	/**
	 * 查询设置过的签到时间段 前十条
	 */
	public List<Meeting> getMeetingListTopTen(long conpanyId);
	/**
	 * 查询设置过的签到时间段 最新的一条
	 */
	public Meeting getMeetingListTop(long conpanyId);
	/**
	 * 获取某一个员工按照日期的一个绩效记录
	 */
	public Performance getPerformanceByUser(long userId,long conpanyId,String startDate,String endDate);
	/**
	 * 获取全部员工的一段时间绩效
	 */
	public List<Performance> getPerformanceByAll(long conpanyId,String startDate,String endDate);
	/**
	 * 获取当天的绩效
	 */
	public Performance getPerformanceByToDayUser(long conpanyId,long userId,String userTrueName);
}
