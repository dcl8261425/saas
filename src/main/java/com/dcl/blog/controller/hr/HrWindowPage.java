package com.dcl.blog.controller.hr;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/hr/window")
public class HrWindowPage {
	private static final Logger logger = LoggerFactory
			.getLogger(HrWindowPage.class);
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
	/**	
	 *window列表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		List<Map> list=new ArrayList<Map>();
		Map map = new HashMap<String, Object>();
		Map map1 = new HashMap<String, Object>();
		map1.put("name", "最新进入的员工");
		map1.put("url", "hr/window/newCustemmer");
		map1.put("mark", "最新添加的员工");
		map1.put("num", "1");
		map1.put("mainUrl", "hr/window/");
		Map map2 = new HashMap<String, Object>();
		map2.put("name", "今日签到记录");
		map2.put("url", "hr/window/todayRigister");
		map2.put("mark", "今日考勤记录，今日考勤地点设置");
		map2.put("num", "2");
		map2.put("mainUrl", "hr/window/");
		list.add(map1);
		list.add(map2);
		map.put("data", list);
		map.put("info", "获取客户关系->功能窗口列表成功");
		return map;
	}
	/**	
	 *最新进入的员工
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/newCustemmer")
	public String myCreateCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/pageItem/newCustemmer/main";
	}
	/**	
	 *今日签到记录
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/todayRigister")
	public String toMyCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/pageItem/todayRigister/main";
	}
}
