package com.dcl.blog.controller.crm;

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
@RequestMapping("/crm/window")
public class CRMWindowPage {
	private static final Logger logger = LoggerFactory
			.getLogger(CRMWindowPage.class);
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
		map1.put("name", "客户机会统计");
		map1.put("url", "crm/window/CustomChanceProbabilityWindow");
		map1.put("mark", "客户机会统计类个人，公司总体，等信息的汇总");
		map1.put("num", "1");
		map1.put("mainUrl", "crm/window/");
		Map map2 = new HashMap<String, Object>();
		map2.put("name", "开发计划列表");
		map2.put("url", "crm/window/DevelopmentListWindow");
		map2.put("mark", "最近的几个开发机会显示");
		map2.put("num", "2");
		map2.put("mainUrl", "crm/window/");
		list.add(map1);
		list.add(map2);
		map.put("data", list);
		map.put("info", "获取客户关系->功能窗口列表成功");
		return map;
	}
	/**	
	 *客户机会统计
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/CustomChanceProbabilityWindow")
	public String myCreateCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/pageItem/CustomChanceProbabilityWindow";
	}
	/**	
	 *开发计划列表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/DevelopmentListWindow")
	public String toMyCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/pageItem/DevelopmentListWindow";
	}
}
