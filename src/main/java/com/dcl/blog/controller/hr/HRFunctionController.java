package com.dcl.blog.controller.hr;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserMeeting;
import com.dcl.blog.model.ConpanyZone;
import com.dcl.blog.model.ConpanyZoneImage;
import com.dcl.blog.model.ConpanyZoneRet;
import com.dcl.blog.model.ConpanyZoneTouPiao;
import com.dcl.blog.model.ConpanyZoneTouPiaoItem;
import com.dcl.blog.model.ConpanyZoneZan;
import com.dcl.blog.model.GroupConpanyLinkUser;
import com.dcl.blog.model.Meeting;
import com.dcl.blog.model.Performance;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.model.SoftPermissionLinkConpanyRole;
import com.dcl.blog.model.dto.ConpanyZoneRetDto;
import com.dcl.blog.model.dto.ConpanyZoneTouPiaoItemDto;
import com.dcl.blog.model.dto.KongJianDto;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DataTypeTestUtil;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.IPUtils;
import com.dcl.blog.util.MD5Util;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping({ "/hr/function" })
public class HRFunctionController {
	private static final Logger logger = LoggerFactory
			.getLogger(HRFunctionController.class);
	private DaoService dao;
	private emailimpl email;

	@Resource
	public void setEmail(emailimpl email) {
		this.email = email;
	}

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}

	@RequestMapping({ "/addCustemmer" })
	@ResponseBody
	public Map addCustemmer(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		Map stringtest = new HashMap();
		stringtest.put("username", Integer.valueOf(2));
		stringtest.put("password", Integer.valueOf(2));
		stringtest.put("name", Integer.valueOf(2));
		stringtest.put("phone", Integer.valueOf(2));
		stringtest.put("email", Integer.valueOf(2));
		stringtest.put("idimage", Integer.valueOf(2));
		stringtest.put("useLogin", Integer.valueOf(6));
		stringtest.put("image", Integer.valueOf(2));
		stringtest.put("stute", Integer.valueOf(2));
		stringtest.put("idnum", Integer.valueOf(2));
		stringtest.put("sex", Integer.valueOf(6));
		stringtest.put("marks", Integer.valueOf(2));
		stringtest.put("address", Integer.valueOf(2));
		stringtest.put("price", Integer.valueOf(5));
		map = DataTypeTestUtil.testDate(req, stringtest);
		if (((Boolean) map.get("success")).booleanValue()) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String email = req.getParameter("email");
			String useLogin = req.getParameter("useLogin");
			String image = req.getParameter("image");
			String idimage = req.getParameter("idimage");
			String stute = req.getParameter("stute");
			String idnum = req.getParameter("idnum");
			String sex = req.getParameter("sex");
			String marks = req.getParameter("marks");
			String address = req.getParameter("address");
			String price = req.getParameter("price");
			ConpanyUser extuser = this.dao.getConpanyUserByUserName(username
					+ "@" + users.getConpanyId() + ".com");
			if (extuser == null) {
				extuser = new ConpanyUser();
				extuser.setAddress(address);
				extuser.setConpanyId(users.getConpanyId());
				extuser.setEmail(email);
				extuser.setIdImage(idimage);
				extuser.setIdNum(idnum);
				extuser.setImage(image);
				extuser.setMarks(marks);
				try {
					extuser.setPassword(MD5Util.getEncryptedPwd(password));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				extuser.setPhone(phone);
				extuser.setPrice(Float.parseFloat(price));
				extuser.setSex(Boolean.parseBoolean(sex));
				extuser.setState(stute);
				extuser.setTrueName(name);
				extuser.setUseLogin(Boolean.parseBoolean(useLogin));
				extuser.setUsername(username + "@" + users.getConpanyId()
						+ ".com");
				extuser.setAccuntStartDate(new Date());
				this.dao.add(extuser);
				ConpanyGroup cg = (ConpanyGroup) this.dao
						.getTopOneGroup(users.getConpanyId()).iterator().next();
				GroupConpanyLinkUser gcl = new GroupConpanyLinkUser();
				gcl.setConpanyId(users.getConpanyId());
				gcl.setConpanyUserId(extuser.getId());
				gcl.setConpanyUserName(extuser.getUsername());
				gcl.setConpanyUserTrueName(extuser.getTrueName());
				gcl.setGroupId(cg.getId());
				gcl.setGroupName(cg.getGroupName());
				this.dao.add(gcl);
				map.put("success", Boolean.valueOf(true));
				map.put("info", "添加成功");
			} else {
				map.put("success", Boolean.valueOf(false));
				map.put("info", "错误，该用户名称已存在。");
			}
		} else {
			return stringtest;
		}
		return map;
	}

	@RequestMapping({ "/queryCustemmer" })
	@ResponseBody
	public Map queryCustemmer(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String trueName = req.getParameter("trueName");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num = this.dao.getConpanyUserNum(users.getConpanyId(), trueName)
				.longValue();
		List list = this.dao.getConpanyUserPage(users.getConpanyId(), trueName,
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		for (int i = 0; i < list.size(); i++) {
			((ConpanyUser) list.get(i)).setPrice(0.0F);
		}
		map.put("pagenum", Long.valueOf(num / Integer.parseInt(countNum) + 1L));
		map.put("success", Boolean.valueOf(true));
		map.put("data", list);
		return map;
	}

	@RequestMapping({ "/lookCustemmerInfo" })
	@ResponseBody
	public Map lookCustemmerInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id = req.getParameter("id");
		ConpanyUser user = (ConpanyUser) this.dao.getObject(Long.parseLong(id),
				"ConpanyUser", users.getConpanyId());
		if (user == null) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "没有找到该员工");
		} else {
			map.put("success", Boolean.valueOf(true));
			map.put("obj", user);
		}
		return map;
	}

	@RequestMapping({ "/updateCustemmerInfo" })
	@ResponseBody
	public Map updateCustemmerInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String value = req.getParameter("value");
		ConpanyUser user = (ConpanyUser) this.dao.getObject(Long.parseLong(id),
				"ConpanyUser", users.getConpanyId());
		if (user == null) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "找不到该员工");
			return map;
		}
		if (name.equals("trueName")) {
			user.setTrueName(value);
		}
		if (name.equals("email")) {
			user.setEmail(value);
		}
		if (name.equals("sex")) {
			try {
				user.setSex(Boolean.parseBoolean(value));
			} catch (Exception e) {
				List list = new ArrayList();
				Map map2 = new HashMap();
				map2.put("info", "错误,请从新选择");
				map2.put("name", name);
				map2.put("success", Boolean.valueOf(false));
				list.add(map2);
				map.put("list", list);
				map.put("success", Boolean.valueOf(false));
				return map;
			}
		}
		if (name.equals("price")) {
			try {
				user.setPrice(Float.parseFloat(value));
			} catch (Exception e) {
				List list = new ArrayList();
				Map map2 = new HashMap();
				map2.put("info", "错误请填入数字");
				map2.put("name", name);
				map2.put("success", Boolean.valueOf(false));
				list.add(map2);
				map.put("list", list);
				map.put("success", Boolean.valueOf(false));
				return map;
			}
		}
		if (name.equals("address")) {
			user.setAddress(value);
		}
		if (name.equals("stute")) {
			user.setState(value);
		}
		if (name.equals("idnum")) {
			user.setIdNum(value);
		}
		if (name.equals("useLogin")) {
			try {
				user.setUseLogin(Boolean.parseBoolean(value));
			} catch (Exception e) {
				List list = new ArrayList();
				Map map2 = new HashMap();
				map2.put("info", "错误,请从新选择");
				map2.put("name", name);
				map2.put("success", Boolean.valueOf(false));
				list.add(map2);
				map.put("list", list);
				map.put("success", Boolean.valueOf(false));
				return map;
			}
		}
		if (name.equals("phone")) {
			user.setPhone(value);
		}
		if (name.equals("marks")) {
			user.setMarks(value);
		}
		this.dao.update(user);
		map.put("success", Boolean.valueOf(true));
		map.put("obj", user);
		map.put("info", "修改成功");
		return map;
	}

	@RequestMapping({ "/getCustemmerMeetingInfo" })
	@ResponseBody
	public Map getCustemmerMeetingInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String date = req.getParameter("date");

		this.dao.getCreateConpanyCusteemerMeeting(users.getConpanyId());
		List list = this.dao.getConpanUserMeetingByDate(date,
				users.getConpanyId());
		map.put("success", Boolean.valueOf(true));
		map.put("data", list);
		return map;
	}

	@RequestMapping({ "/getUpdateMeetingInfo" })
	@ResponseBody
	public Map getUpdateMeetingInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		try {
			ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
					"USER_OBJ");
			String type = req.getParameter("type");
			String value = req.getParameter("value");
			String id = req.getParameter("id");
			ConpanyUserMeeting cu = (ConpanyUserMeeting) this.dao.getObject(
					Long.parseLong(id), "ConpanyUserMeeting",
					users.getConpanyId());
			if (type.equals("marks")) {
				cu.setStuteMarks(value);
				this.dao.update(cu);
				map.put("success", Boolean.valueOf(true));
				map.put("info", "修改成功");
			} else if (type.equals("stute")) {
				cu.setStute(Long.parseLong(value));
				this.dao.update(cu);
				map.put("success", Boolean.valueOf(true));
				map.put("info", "修改成功");
			} else {
				map.put("success", Boolean.valueOf(false));
				map.put("info", "提交数据不合法");
			}
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "提交数据不合法");
		}
		return map;
	}

	@RequestMapping({ "/getMeetingSet" })
	@ResponseBody
	public Map getMeetingSet(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		try {
			ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
					"USER_OBJ");
			String ip = req.getParameter("ip");
			String uptime = req.getParameter("uptime");
			String downtime = req.getParameter("downtime");
			if ((uptime == null) && (uptime.trim().equals(""))
					&& (uptime.indexOf(":") == -1)) {
				map.put("success", Boolean.valueOf(false));
				map.put("info", "时间格式错误，格式为00:00");
			}
			if ((downtime == null) && (downtime.trim().equals(""))
					&& (downtime.indexOf(":") == -1)) {
				map.put("success", Boolean.valueOf(false));
				map.put("info", "时间格式错误,格式为00:00");
			}
			Meeting me = new Meeting();
			me.setConpanyId(users.getConpanyId());
			me.setEndDate(downtime);
			me.setStartDate(uptime);
			if (Boolean.parseBoolean(ip)) {
				me.setIPTest(Boolean.parseBoolean(ip));
				String ipadd = IPUtils.getIpAddr(req);
				me.setIpAddress(ipadd);
			} else {
				me.setIPTest(Boolean.parseBoolean(ip));
			}
			me.setManagerDate(new Date());
			me.setManagerUserId(users.getId());
			me.setManagerUserTrueName(users.getTrueName());
			this.dao.add(me);
			map.put("success", Boolean.valueOf(true));
			map.put("info", "创建成功");
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "您提交的数据有误");
		}
		return map;
	}

	@RequestMapping({ "/getMeetingSetInfo" })
	@ResponseBody
	public Map getMeetingSetInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		try {
			ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
					"USER_OBJ");
			Meeting me = this.dao.getMeetingListTop(users.getConpanyId());
			List list = this.dao.getMeetingListTopTen(users.getConpanyId());
			map.put("success", Boolean.valueOf(true));
			map.put("data", list);
			map.put("obj", me);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", Boolean.valueOf(false));
			map.put("info", "出错,请重试。");
		}
		return map;
	}

	@RequestMapping({ "/getPerForMance" })
	@ResponseBody
	public Map getPerForMance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		try {
			ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
					"USER_OBJ");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			List list = this.dao.getPerformanceByAll(users.getConpanyId(),
					startDate, endDate);
			map.put("success", Boolean.valueOf(true));
			map.put("data", list);
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "出错,请重试。");
		}
		return map;
	}

	@RequestMapping({ "/meeting" })
	@ResponseBody
	public Map meeting(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		try {
			ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
					"USER_OBJ");
			String marks = req.getParameter("marks");
			String type = req.getParameter("type");
			Meeting meet2 = this.dao.getMeetingListTop(users.getConpanyId());
			if (meet2 == null) {
				map.put("success", Boolean.valueOf(false));
				map.put("info", "管理员未设置上班时间，目前无法签到。");
				return map;
			}
			if (meet2.isIPTest()) {
				String ip = IPUtils.getIpAddr(req);
				if (!ip.equals(meet2.getIpAddress())) {
					map.put("success", Boolean.valueOf(false));
					map.put("info", "签到管理员已经设置ip检测，您的签到设备不在此ip范围内");
					return map;
				}

			}

			if (type.equals("test")) {
				Calendar calend = Calendar.getInstance();
				calend.setTime(new Date());
				calend.add(5, 1);
				String startDate = DateUtil.formatDateYYYY_MM_DD(calend
						.getTime());
				List list = this.dao.getConpanUserMeetingByDateAndUser(
						startDate, users.getId(), users.getConpanyId());
				if (list.size() == 0) {
					this.dao.getCreateConpanyCusteemerMeeting(users
							.getConpanyId());
					list = this.dao.getConpanUserMeetingByDateAndUser(
							startDate, users.getId(), users.getConpanyId());
				}
				ConpanyUserMeeting cum = (ConpanyUserMeeting) list.get(0);
				if (cum.getStartDate() == null) {
					map.put("success", Boolean.valueOf(true));
					map.put("isup", Boolean.valueOf(true));
					return map;
				}
				if (cum.getEndDate() == null) {
					map.put("success", Boolean.valueOf(true));
					map.put("isup", Boolean.valueOf(false));
					return map;
				}
				map.put("success", Boolean.valueOf(false));
				map.put("info", "今日已签到签退");
				return map;
			}

			if (type.equals("up")) {
				Calendar calend = Calendar.getInstance();
				calend.setTime(new Date());
				calend.add(5, 1);
				String startDate = DateUtil.formatDateYYYY_MM_DD(calend
						.getTime());
				List list = this.dao.getConpanUserMeetingByDateAndUser(
						startDate, users.getId(), users.getConpanyId());
				Meeting meet = this.dao.getMeetingListTop(users.getConpanyId());
				ConpanyUserMeeting cum = null;
				if (meet != null) {
					if (meet.isIPTest()) {
						String ip = IPUtils.getIpAddr(req);
						if (!ip.equals(meet.getIpAddress())) {
							map.put("success", Boolean.valueOf(false));
							map.put("info", "签到管理员已经设置ip检测，您的签到设备不在此ip范围内");
							return map;
						}
					}
					Calendar calend2 = Calendar.getInstance();
					calend2.setTime(new Date());
					int nowhour = calend.get(11);
					int nowminute = calend.get(12);
					String[] start = meet.getStartDate().split(":");
					int hour = Integer.parseInt(start[0]);
					int minute = Integer.parseInt(start[1]);
					if (list.size() > 0) {
						cum = (ConpanyUserMeeting) list.get(0);
						if (cum.getStartDate() == null) {
							if (marks == null) {
								cum.setStartDate(new Date());
							} else {
								cum.setStartDate(new Date());
								cum.setStuteMarks(marks);
							}

							Performance pf = this.dao
									.getPerformanceByToDayUser(
											users.getConpanyId(),
											users.getId(), users.getTrueName());
							pf.setMeetingNum(pf.getMeetingNum() + 1L);
							this.dao.update(pf);
							if (hour > nowhour) {
								cum.setStute(5L);
							} else if (nowhour == hour) {
								if (minute > nowhour) {
									cum.setStute(5L);
								} else {
									cum.setStute(1L);
									pf.setMeetingLastNum(pf.getMeetingLastNum() + 1L);
									this.dao.update(pf);
								}
							} else {
								cum.setStute(1L);
								pf.setMeetingLastNum(pf.getMeetingLastNum() + 1L);
								this.dao.update(pf);
							}
							this.dao.update(cum);
							map.put("success", Boolean.valueOf(true));
							map.put("info", "签到完成");
						} else {
							map.put("success", Boolean.valueOf(false));
							map.put("info", "已签过到了，不可以重复。");
						}
					} else {
						map.put("success", Boolean.valueOf(false));
						map.put("info", "出错,请重试。");
					}
				} else {
					map.put("success", Boolean.valueOf(false));
					map.put("info", "管理员未设置上班时间，目前无法签到。");
				}
			} else if (type.equals("down")) {
				Calendar calend = Calendar.getInstance();
				calend.setTime(new Date());
				calend.add(5, 1);
				String startDate = DateUtil.formatDateYYYY_MM_DD(calend
						.getTime());
				List list = this.dao.getConpanUserMeetingByDateAndUser(
						startDate, users.getId(), users.getConpanyId());
				Meeting meet = this.dao.getMeetingListTop(users.getConpanyId());
				ConpanyUserMeeting cum = null;
				if (meet != null) {
					if (meet.isIPTest()) {
						String ip = IPUtils.getIpAddr(req);
						if (!ip.equals(meet.getIpAddress())) {
							map.put("success", Boolean.valueOf(false));
							map.put("info", "签到管理员已经设置ip检测，您的签到设备不在此ip范围内");
							return map;
						}
					}
					Calendar calend2 = Calendar.getInstance();
					calend2.setTime(new Date());
					int nowhour = calend.get(11);
					int nowminute = calend.get(12);
					String[] start = meet.getEndDate().split(":");
					int hour = Integer.parseInt(start[0]);
					int minute = Integer.parseInt(start[1]);
					if (list.size() > 0) {
						cum = (ConpanyUserMeeting) list.get(0);
						if (cum.getStartDate() != null) {
							if (cum.getEndDate() == null) {
								cum.setEndDate(new Date());
								Performance pf = this.dao
										.getPerformanceByToDayUser(
												users.getConpanyId(),
												users.getId(),
												users.getTrueName());
								pf.setMeetingNumOut(pf.getMeetingNumOut() + 1L);
								this.dao.update(pf);
								if (nowhour <= hour) {
									if (hour == nowhour) {
										if (minute > nowminute) {
											if (cum.getStute() == 1L)
												cum.setStute(3L);
											else {
												cum.setStute(2L);
											}
											pf.setMeetingLeave(pf
													.getMeetingLeave() + 1L);
											this.dao.update(pf);
										}
									} else {
										if (cum.getStute() == 1L)
											cum.setStute(3L);
										else {
											cum.setStute(2L);
										}
										pf.setMeetingLeave(pf.getMeetingLeave() + 1L);
										this.dao.update(pf);
									}
								}
								this.dao.update(cum);
								map.put("success", Boolean.valueOf(true));
								map.put("info", "签退完成");
							} else {
								map.put("success", Boolean.valueOf(false));
								map.put("info", "已签过到了，不可以重复。");
							}
						} else {
							map.put("success", Boolean.valueOf(false));
							map.put("info", "今天还没有签到上班");
						}
					} else {
						map.put("success", Boolean.valueOf(false));
						map.put("info", "出错,请重试。");
					}
				} else {
					map.put("success", Boolean.valueOf(false));
					map.put("info", "管理员未设置上班时间，目前无法签到。");
				}
			}
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("info", "出错,请重试。");
		}
		return map;
	}

	@RequestMapping({ "/waiqinGroupManager" })
	@ResponseBody
	public Map waiqinGroupManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String groupId=req.getParameter("groupid");
		if(groupId!=null){
			String trueName=req.getParameter("trueName");
			List<ConpanyUser> userlist=new ArrayList<ConpanyUser>();
			List<Object> listpage=dao.getObjectList("GroupConpanyLinkUser", "where conpanyUserTrueName like '%"+trueName+"%' and groupId="+groupId+" and conpanyId="+users.getConpanyId());
			Iterator<Object> i=listpage.iterator();
			//循环出所有用户
			while(i.hasNext()){
				GroupConpanyLinkUser u=(GroupConpanyLinkUser) i.next();
				ConpanyUser cu=(ConpanyUser) dao.getObject(u.getConpanyUserId(), "ConpanyUser");
				cu.setPassword("*************");
				userlist.add(cu);
			}
			map.put("data", userlist);
			map.put("success", true);
			
			return map;
		}
		String userId=req.getParameter("userid");
		if(userId!=null){
			String startDate=req.getParameter("startDate");
			String endDate=req.getParameter("endDate");
			List<Object> maps=dao.getObjectList("MapInfo", "where conpanyId="+users.getConpanyId()+" and conpanyUserId="+userId+" and createDate between '"+startDate+"' and '"+endDate+"' order by createDate desc");
			map.put("success", true);
			map.put("data", maps);
			return map;
		}
		List<Object> list=dao.getObjectList("SoftPermission", "where url='/hr/function/waiqinGroupManager'");
		SoftPermission s=(SoftPermission) list.iterator().next();
		List<Object> groups=dao.getObjectListByeSql("select * from ConpanyGroup where id in (select conpanyuserlinkrole.groupId from Conpanyuserlinkrole inner join softpermissionlinkconpanyrole on softpermissionlinkconpanyrole.softPermissionId="+s.getId()+" and conpanyuserlinkrole.conpanyId="+users.getConpanyId()+" and conpanyuserlinkrole.userId="+users.getId()+" and conpanyuserlinkrole.groupId=softpermissionlinkconpanyrole.groupid)");
		map.put("success", true);
		map.put("data", groups);
		return map;
	}
	@RequestMapping({ "/getHuDongKongJian" })
	@ResponseBody
	public Map getHuDongKongJian(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String groupId=req.getParameter("groupId");
		if(groupId!=null){
			List<KongJianDto> kongjian=new ArrayList<KongJianDto>();
			String nowpage = req.getParameter("nowpage");
			String countNum = req.getParameter("countNum");
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "8";
			}
			List<Object> zoneDto=dao.getObjectListPage("ConpanyZone", "where groupId="+groupId+" and conpanyId="+users.getConpanyId()+" order by indexNum desc,createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			Iterator<Object> zoneI=zoneDto.iterator();
			while(zoneI.hasNext()){
				ConpanyZone zone=(ConpanyZone) zoneI.next();
				KongJianDto zoneDtobean=new KongJianDto();
				zoneDtobean.setConpanyId(zone.getConpanyId());
				zoneDtobean.setContent(zone.getContent());
				zoneDtobean.setCreateDate(zone.getCreateDate());
				zoneDtobean.setCreateUserId(zone.getCreateUserId());
				zoneDtobean.setCreateUserName(zone.getCreateUserName());
				zoneDtobean.setGroupId(zone.getGroupId());
				zoneDtobean.setId(zone.getId());
				zoneDtobean.setIndexNum(zone.getIndexNum());
				zoneDtobean.setTitle(zone.getTitle());
				zoneDtobean.setTouPiao(zone.isTouPiao());
				zoneDtobean.setZan(zone.getZan());
				List<Object> fileList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=3 and ret=false and mainId="+zone.getId());
				zoneDtobean.setFileList(fileList);
				List<Object> imageList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=1 and ret=false and mainId="+zone.getId());
				zoneDtobean.setImageList(imageList);
				List<Object> videoList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=2 and ret=false and mainId="+zone.getId());
				zoneDtobean.setVidioList(videoList);
				if(zoneDtobean.isTouPiao()){
					
					List<Object> listTouPiao=dao.getObjectList("ConpanyZoneTouPiao", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneId="+zone.getId());
					if(listTouPiao.iterator().hasNext()){
						List<ConpanyZoneTouPiaoItemDto> toupiaoitemDto=new ArrayList<ConpanyZoneTouPiaoItemDto>();
						ConpanyZoneTouPiao ctoupiao=(ConpanyZoneTouPiao) listTouPiao.iterator().next();
						zoneDtobean.setToupiaoList(ctoupiao);
						List<Object> toupiaoItems=dao.getObjectList("ConpanyZoneTouPiaoItem", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneTouPiaoId="+ctoupiao.getId());
						Iterator<Object> itemi=toupiaoItems.iterator();
						while(itemi.hasNext()){
							ConpanyZoneTouPiaoItem toupiaoitem=(ConpanyZoneTouPiaoItem) itemi.next();
							ConpanyZoneTouPiaoItemDto itemdto=new ConpanyZoneTouPiaoItemDto();
							if(ctoupiao.getCountTouPiao()==0){
								itemdto.setBaifenbi(0);
							}else{
								itemdto.setBaifenbi((toupiaoitem.getCountNum()/ctoupiao.getCountTouPiao())*100);
							}
							itemdto.setConpanyId(toupiaoitem.getConpanyId());
							itemdto.setConpanyZoneTouPiaoId(toupiaoitem.getConpanyZoneTouPiaoId());
							itemdto.setCountNum(toupiaoitem.getCountNum());
							itemdto.setGroupId(toupiaoitem.getGroupId());
							itemdto.setId(toupiaoitem.getId());
							itemdto.setImage(toupiaoitem.getImage());
							itemdto.setName(toupiaoitem.getName());
							List<Object> sendUser=dao.getObjectList("ConpanyZoneTouPiaoItemSendUser", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneTouPiaoItemId="+toupiaoitem.getId()+" and conpanyZoneTouPiaoId="+ctoupiao.getId()+" order by createDate desc");
							itemdto.setSendUserlist(sendUser);
							toupiaoitemDto.add(itemdto);
						}
						zoneDtobean.setToupiaoItemList(toupiaoitemDto);
					}else{
						zoneDtobean.setToupiaoList(new ConpanyZoneTouPiao());
					}

				}
				List<Object> retMessge=dao.getObjectList("ConpanyZoneRet", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneId="+zone.getId());
				List<ConpanyZoneRetDto> retdtolist=new ArrayList<ConpanyZoneRetDto>();
				Iterator<Object> retMessgei=retMessge.iterator();
				while(retMessgei.hasNext()){
					ConpanyZoneRet cret=(ConpanyZoneRet) retMessgei.next();
					ConpanyZoneRetDto retdto=new ConpanyZoneRetDto();
					retdto.setConpanyId(cret.getConpanyId());
					retdto.setConpanyZoneId(cret.getConpanyZoneId());
					retdto.setContent(cret.getContent());
					retdto.setCreateDate(cret.getCreateDate());
					retdto.setCreateUserId(cret.getCreateUserId());
					retdto.setCreateUserName(cret.getCreateUserName());
					retdto.setGroupId(cret.getGroupId());
					retdto.setId(cret.getId());
					retdto.setIndexNum(cret.getIndexNum());
					List<Object> rvideoList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=2 and ret=true and mainId="+cret.getId());
					retdto.setVidioList(rvideoList);
					List<Object> rfileList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=3 and ret=true and mainId="+cret.getId());
					retdto.setFileList(rfileList);
					List<Object> rimageList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=1 and ret=true and mainId="+cret.getId());
					retdto.setImageList(rimageList);
					retdtolist.add(retdto);
				}
				zoneDtobean.setRetList(retdtolist);
				List<Object> zanList=dao.getObjectList("ConpanyZoneZan", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneid="+zone.getId());
				zoneDtobean.setZanList(zanList);
				kongjian.add(zoneDtobean);
			}
			
			map.put("data", kongjian);
			map.put("success", true);
		}else{
			List<ConpanyGroup> groups=dao.getUserOfGroups(users.getId());
			map.put("data", groups);
			map.put("success", true);
		}
		return map;
	}
	@RequestMapping({ "/sendKongJianManager" })
	@ResponseBody
	public Map sendKongJianManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String groupId=req.getParameter("groupId");
		if(null==groupId||"".equals(groupId)){
			map.put("success", false);
			map.put("info", "请先选择组");
		}else{
			ConpanyGroup group=(ConpanyGroup) dao.getObject(Long.parseLong(groupId), "ConpanyGroup");
			if(group==null){
				map.put("success", false);
				map.put("info", "此组不存在");
			}else{
				if(group.getConpanyId()==users.getConpanyId()){
					String title=req.getParameter("title");
					String content=req.getParameter("content");
					String filestr=req.getParameter("file");
					String imgstr=req.getParameter("img");
					String videostr=req.getParameter("video");
					String toupiaostr=req.getParameter("toupiao");
					String toupiao_title=req.getParameter("toupiao_title");
					String toupiao_start=req.getParameter("toupiao_start");
					String toupiao_end=req.getParameter("toupiao_end");
					String toupiao_move=req.getParameter("toupiao_move");
					ConpanyZone zone=new ConpanyZone();
					zone.setConpanyId(users.getConpanyId());
					zone.setContent(content);
					zone.setCreateDate(new Date());
					zone.setCreateUserId(users.getId());
					zone.setCreateUserName(users.getTrueName());
					zone.setGroupId(group.getId());
					zone.setIndexNum(1);
					zone.setTitle(title);
					boolean istoupiao=!(toupiaostr.equals("0")||toupiao_title.equals("-1"));
					zone.setTouPiao(istoupiao);
					zone.setZan(0);
					dao.add(zone);
					if(!filestr.equals("0")){
						String[] filestrlist=filestr.split(",");
						for(int i=0;i<filestrlist.length;i++){
							String[] filenamelink=filestrlist[i].split(":");
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setFileName(filenamelink[2]);
							image.setLink(filenamelink[0]+":"+filenamelink[1]);
							image.setMainId(zone.getId());
							image.setRet(false);
							image.setSystemFile("");
							image.setType("3");
							dao.add(image);
						}
					}
					if(!imgstr.equals("0")){
						String[] imgstrlist=imgstr.split(",");
						for(int i=0;i<imgstrlist.length;i++){
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setLink(imgstrlist[i]);
							image.setMainId(zone.getId());
							image.setRet(false);
							image.setSystemFile("");
							image.setType("1");
							dao.add(image);		
						}
					}
					if(!videostr.equals("0")){
						String[] videostrlist=videostr.split(",");
						for(int i=0;i<videostrlist.length;i++){
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setLink(videostrlist[i]);
							image.setMainId(zone.getId());
							image.setRet(false);
							image.setSystemFile("");
							image.setType("2");
							dao.add(image);		
						}
					}
					if(istoupiao){
						ConpanyZoneTouPiao toupiao=new ConpanyZoneTouPiao();
						toupiao.setConpanyId(users.getConpanyId());
						toupiao.setConpanyZoneId(zone.getId());
						toupiao.setCountTouPiao(0);
						toupiao.setCreateName(users.getTrueName());
						toupiao.setCreateUserId(users.getId());
						toupiao.setDuoXuan(Boolean.parseBoolean(toupiao_move));
						toupiao.setEndDate(DateUtil.toDateType(toupiao_end));
						toupiao.setStartDate(DateUtil.toDateType(toupiao_start));
						toupiao.setGroupId(zone.getGroupId());
						toupiao.setTitle(toupiao_title);
						dao.add(toupiao);
						String[] toupiaostrlist=toupiaostr.split(",");
						for(int i=0;i<toupiaostrlist.length;i++){
							ConpanyZoneTouPiaoItem titem=new ConpanyZoneTouPiaoItem();
							titem.setConpanyId(users.getConpanyId());
							titem.setConpanyZoneTouPiaoId(toupiao.getId());
							titem.setCountNum(0);
							titem.setGroupId(zone.getGroupId());
							titem.setImage("");
							titem.setName(toupiaostrlist[i]);
							dao.add(titem);
						}
						
					}
					List<KongJianDto> kongjian=new ArrayList<KongJianDto>();
					KongJianDto zoneDtobean=new KongJianDto();
					zoneDtobean.setConpanyId(zone.getConpanyId());
					zoneDtobean.setContent(zone.getContent());
					zoneDtobean.setCreateDate(zone.getCreateDate());
					zoneDtobean.setCreateUserId(zone.getCreateUserId());
					zoneDtobean.setCreateUserName(zone.getCreateUserName());
					zoneDtobean.setGroupId(zone.getGroupId());
					zoneDtobean.setId(zone.getId());
					zoneDtobean.setIndexNum(zone.getIndexNum());
					zoneDtobean.setTitle(zone.getTitle());
					zoneDtobean.setTouPiao(zone.isTouPiao());
					zoneDtobean.setZan(zone.getZan());
					List<Object> fileList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=3 and ret=false and mainId="+zone.getId());
					zoneDtobean.setFileList(fileList);
					List<Object> imageList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=1 and ret=false and mainId="+zone.getId());
					zoneDtobean.setImageList(imageList);
					List<Object> videoList=dao.getObjectList("ConpanyZoneImage", "where conpanyId="+users.getConpanyId()+" and type=2 and ret=false and mainId="+zone.getId());
					zoneDtobean.setVidioList(videoList);
					List<Object> zanList=dao.getObjectList("ConpanyZoneZan", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneid="+zone.getId());
					zoneDtobean.setZanList(zanList);
					if(zoneDtobean.isTouPiao()){
						
						List<Object> listTouPiao=dao.getObjectList("ConpanyZoneTouPiao", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneId="+zone.getId());
						if(listTouPiao.iterator().hasNext()){
							List<ConpanyZoneTouPiaoItemDto> toupiaoitemDto=new ArrayList<ConpanyZoneTouPiaoItemDto>();
							ConpanyZoneTouPiao ctoupiao=(ConpanyZoneTouPiao) listTouPiao.iterator().next();
							zoneDtobean.setToupiaoList(ctoupiao);
							List<Object> toupiaoItems=dao.getObjectList("ConpanyZoneTouPiaoItem", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneTouPiaoId="+ctoupiao.getId());
							Iterator<Object> itemi=toupiaoItems.iterator();
							while(itemi.hasNext()){
								ConpanyZoneTouPiaoItem toupiaoitem=(ConpanyZoneTouPiaoItem) itemi.next();
								ConpanyZoneTouPiaoItemDto itemdto=new ConpanyZoneTouPiaoItemDto();
								if(ctoupiao.getCountTouPiao()==0){
									itemdto.setBaifenbi(0);
								}else{
									itemdto.setBaifenbi((toupiaoitem.getCountNum()/ctoupiao.getCountTouPiao())*100);
								}
								itemdto.setConpanyId(toupiaoitem.getConpanyId());
								itemdto.setConpanyZoneTouPiaoId(toupiaoitem.getConpanyZoneTouPiaoId());
								itemdto.setCountNum(toupiaoitem.getCountNum());
								itemdto.setGroupId(toupiaoitem.getGroupId());
								itemdto.setId(toupiaoitem.getId());
								itemdto.setImage(toupiaoitem.getImage());
								itemdto.setName(toupiaoitem.getName());
								List<Object> sendUser=dao.getObjectList("ConpanyZoneTouPiaoItemSendUser", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneTouPiaoItemId="+toupiaoitem.getId()+" and conpanyZoneTouPiaoId="+ctoupiao.getId()+" order by createDate desc");
								itemdto.setSendUserlist(sendUser);
								toupiaoitemDto.add(itemdto);
							}
							zoneDtobean.setToupiaoItemList(toupiaoitemDto);
						}else{
							zoneDtobean.setToupiaoList(new ConpanyZoneTouPiao());
						}

					}
					zoneDtobean.setRetList(new ArrayList<ConpanyZoneRetDto>());
					kongjian.add(zoneDtobean);
					map.put("success", true);
					map.put("data", kongjian);
					map.put("id",zone.getId() );
				}else{
					map.put("success", false);
					map.put("info", "此组不存在");
				}
			}
			
		}
		return map;
	}
	@RequestMapping({ "/resendKongJianManager" })
	@ResponseBody
	public Map resendKongJianManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id=req.getParameter("id");
		if(null==id||"".equals(id)){
			map.put("success", false);
			map.put("info", "请先选择主题");
		}else{
			ConpanyZone zone=(ConpanyZone) dao.getObject(Long.parseLong(id), "ConpanyZone");
			if(zone==null){
				map.put("success", false);
				map.put("info", "此主题不存在");
			}else{
				if(zone.getConpanyId()==users.getConpanyId()){
					String filestr=req.getParameter("file");
					String imgstr=req.getParameter("img");
					String videostr=req.getParameter("video");
					String content=req.getParameter("content");
					List<ConpanyZoneRetDto> retdto=new ArrayList<ConpanyZoneRetDto>();
					ConpanyZoneRetDto dto=new ConpanyZoneRetDto();
					ConpanyZoneRet ret=new ConpanyZoneRet();
					ret.setConpanyId(users.getConpanyId());
					
					ret.setConpanyZoneId(zone.getId());
					ret.setContent(content);
					ret.setCreateDate(new Date());
					ret.setCreateUserId(users.getId());
					ret.setCreateUserName(users.getTrueName());
					ret.setGroupId(zone.getGroupId());
					ret.setIndexNum(0);
					dao.add(ret);
					List<Object> fileList=new ArrayList<Object>();
					if(!filestr.equals("0")){
						String[] filestrlist=filestr.split(",");
						for(int i=0;i<filestrlist.length;i++){
							String[] filenamelink=filestrlist[i].split(":");
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setFileName(filenamelink[2]);
							image.setLink(filenamelink[0]+":"+filenamelink[1]);
							image.setMainId(ret.getId());
							image.setRet(true);
							image.setSystemFile("");
							image.setType("3");
							dao.add(image);
							fileList.add(image);
						}
					}
					List<Object> imgList=new ArrayList<Object>();
					if(!imgstr.equals("0")){
						String[] imgstrlist=imgstr.split(",");
						for(int i=0;i<imgstrlist.length;i++){
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setLink(imgstrlist[i]);
							image.setMainId(ret.getId());
							image.setRet(true);
							image.setSystemFile("");
							image.setType("1");
							dao.add(image);		
							imgList.add(image);
						}
					}
					List<Object> videoList=new ArrayList<Object>();
					if(!videostr.equals("0")){
						String[] videostrlist=videostr.split(",");
						for(int i=0;i<videostrlist.length;i++){
							ConpanyZoneImage image=new ConpanyZoneImage();
							image.setConpanyId(users.getConpanyId());
							image.setLink(videostrlist[i]);
							image.setMainId(ret.getId());
							image.setRet(true);
							image.setSystemFile("");
							image.setType("2");
							dao.add(image);		
							videoList.add(image);
						}
					}
					dto.setConpanyId(ret.getConpanyId());
					dto.setConpanyZoneId(ret.getConpanyZoneId());
					dto.setContent(content);
					dto.setCreateDate(ret.getCreateDate());
					dto.setCreateUserId(ret.getCreateUserId());
					dto.setCreateUserName(ret.getCreateUserName());
					dto.setFileList(fileList);
					dto.setGroupId(zone.getGroupId());
					dto.setId(ret.getId());
					dto.setImageList(imgList);
					dto.setIndexNum(ret.getIndexNum());
					dto.setVidioList(videoList);
					retdto.add(dto);
					map.put("success", true);
					map.put("data", retdto);
					map.put("id", dto.getId());
					map.put("zoneId", zone.getId());
				}else{
					map.put("success", false);
					map.put("info", "此主题不存在");
				}
			}
		}
		return map;
	}
	@RequestMapping({ "/zan" })
	@ResponseBody
	public Map zan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id=req.getParameter("id");
		ConpanyZone zone=(ConpanyZone) dao.getObject(Long.parseLong(id), "ConpanyZone");
		if(zone==null){
			map.put("success", false);
			map.put("info", "此主题不存在");
		}else{
			if(users.getConpanyId()==zone.getConpanyId()){
				List<Object> zanlist=dao.getObjectList("ConpanyZoneZan", "where conpanyId="+users.getConpanyId()+" and conpanyZoneid="+zone.getId()+" and createUserId="+users.getId()+" and groupId="+zone.getGroupId());
				if(zanlist.size()>0){
					map.put("success", false);
					map.put("info", "您已经赞过该信息");
				}else{
					ConpanyZoneZan zan=new ConpanyZoneZan();
					zan.setConpanyId(users.getConpanyId());
					zan.setConpanyZoneid(zone.getId());
					zan.setCreateDate(new Date());
					zan.setCreateUserId(users.getId());
					zan.setCreateUserName(users.getTrueName());
					zan.setGroupId(zone.getGroupId());
					dao.add(zan);
					zone.setZan(zone.getZan()+1);
					dao.update(zone);
					map.put("success", true);
					map.put("num",zone.getZan());
					map.put("zanobj", zan);
				}
			}else{
				map.put("success", false);
				map.put("info", "此主题不存在");
			}
		}
		return map;
	}
	@RequestMapping({ "/deleteKongjian" })
	@ResponseBody
	public Map deleteKongjian(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id=req.getParameter("id");
		ConpanyZone zone=(ConpanyZone) dao.getObject(Long.parseLong(id), "ConpanyZone");
		if(zone==null){
			map.put("success", false);
			map.put("info", "此主题不存在");
		}else{
			List<Object> list=dao.getObjectList("SoftPermission", "where url='/hr/function/deleteKongjian'");
			SoftPermission s=(SoftPermission) list.iterator().next();
			List<Object> listsoftlinkrolels=dao.getObjectList("SoftPermissionLinkConpanyRole", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and softPermissionId="+s.getId());
			StringBuffer bu=new StringBuffer();
			Iterator i=listsoftlinkrolels.iterator();
			int ii=0;
			while(i.hasNext()){
				SoftPermissionLinkConpanyRole role=(SoftPermissionLinkConpanyRole)i.next();
				if(ii==0){
					bu.append(" and roleId=");
					bu.append(role.getRoleId());
				}else{
					bu.append(" or ");
					bu.append("roleId=");
					bu.append(role.getRoleId());
				}
				
				ii++;
			}
			List<Object> rolelinkuserls=dao.getObjectList("ConpanyUserLinkRole","where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and userId="+users.getId()+bu.toString());
			if(rolelinkuserls.size()>0){
				
				List<Object> zoneobj=dao.getObjectList("ConpanyZoneImage","where conpanyId="+users.getConpanyId()+" and ret=false and mainId="+zone.getId());
				Iterator<Object> zoneobji=zoneobj.iterator();
				while(zoneobji.hasNext()){
					ConpanyZoneImage zan=(ConpanyZoneImage) zoneobji.next();
					dao.delete(zan);
				}
				List<Object> huifubean=dao.getObjectList("ConpanyZoneRet", "where conpanyId="+users.getConpanyId()+" and conpanyZoneId="+zone.getId()+" and groupId="+zone.getGroupId());
				Iterator<Object> huifui=huifubean.iterator();
				while(huifui.hasNext()){
					ConpanyZoneRet huifu=(ConpanyZoneRet) huifui.next();
					List<Object> retobj=dao.getObjectList("ConpanyZoneImage","where conpanyId="+users.getConpanyId()+" and ret=true and mainId="+huifu.getId());
					Iterator<Object> retobji=retobj.iterator();
					while(retobji.hasNext()){
						ConpanyZoneImage zan=(ConpanyZoneImage) retobji.next();
						dao.delete(zan);
					}
					dao.delete(huifu);
				}
				if(zone.isTouPiao()){
					List<Object> toupiaoList=dao.getObjectList("ConpanyZoneTouPiao", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and conpanyZoneId="+zone.getId());
					ConpanyZoneTouPiao toubean=(ConpanyZoneTouPiao) toupiaoList.iterator().next();
					List<Object> itemobj=dao.getObjectList("ConpanyZoneTouPiaoItem","where conpanyId="+users.getConpanyId()+" and conpanyZoneTouPiaoId="+toubean.getId()+" and groupId="+zone.getGroupId());
					Iterator<Object> itemobjI=zoneobj.iterator();
					while(itemobjI.hasNext()){
						ConpanyZoneTouPiaoItem zan=(ConpanyZoneTouPiaoItem) itemobjI.next();
						dao.delete(zan);
					}
					dao.delete(toubean);
				}
				List<Object> zanben=dao.getObjectList("ConpanyZoneZan", "where conpanyId="+users.getConpanyId()+" and conpanyZoneid="+zone.getId()+" and groupId="+zone.getGroupId());
				Iterator<Object> zanbeni=huifubean.iterator();
				while(zanbeni.hasNext()){
					ConpanyZoneZan zan=(ConpanyZoneZan) zanbeni.next();
					dao.delete(zan);
				}
				dao.delete(zone);
				map.put("success", true);
				map.put("info", "成功");
				map.put("id", id);
			}else{
				map.put("success", false);
				map.put("info", "您没有此权限");
			}
		}
		return map;
	}
	@RequestMapping({ "/setIndexKongjian" })
	@ResponseBody
	public Map setIndexKongjian(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				"USER_OBJ");
		String id=req.getParameter("id");
		ConpanyZone zone=(ConpanyZone) dao.getObject(Long.parseLong(id), "ConpanyZone");
		if(zone==null){
			map.put("success", false);
			map.put("info", "此主题不存在");
		}else{
			List<Object> list=dao.getObjectList("SoftPermission", "where url='/hr/function/setIndexKongjian'");
			SoftPermission s=(SoftPermission) list.iterator().next();
			List<Object> listsoftlinkrolels=dao.getObjectList("SoftPermissionLinkConpanyRole", "where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and softPermissionId="+s.getId());
			StringBuffer bu=new StringBuffer();
			Iterator i=listsoftlinkrolels.iterator();
			int ii=0;
			while(i.hasNext()){
				SoftPermissionLinkConpanyRole role=(SoftPermissionLinkConpanyRole)i.next();
				if(ii==0){
					bu.append(" and roleId=");
					bu.append(role.getRoleId());
				}else{
					bu.append(" or ");
					bu.append("roleId=");
					bu.append(role.getRoleId());
				}
				
				ii++;
			}
			List<Object> rolelinkuserls=dao.getObjectList("ConpanyUserLinkRole","where conpanyId="+users.getConpanyId()+" and groupId="+zone.getGroupId()+" and userid="+users.getId()+bu.toString());
			if(rolelinkuserls.size()>0){
				
				String indexs=req.getParameter("indexs");
				zone.setIndexNum(Integer.parseInt(indexs)>999?999:Integer.parseInt(indexs));
				dao.update(zone);
				map.put("success", true);
				map.put("info", "成功");
			}else{
				map.put("success", false);
				map.put("info", "您没有此权限");
			}
		}
		return map;
	}
}