package com.dcl.blog.controller.goods;

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
@RequestMapping("/goods/window")
public class GoodsWindowPage {
	private static final Logger logger = LoggerFactory
			.getLogger(GoodsWindowPage.class);
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
		map1.put("name", "最近出货单");
		map1.put("url", "goods/window/outOrder");
		map1.put("mark", "展示最近的几个出货单子");
		map1.put("num", "1");
		map1.put("mainUrl", "goods/window/");
		Map map2 = new HashMap<String, Object>();
		map2.put("name", "最近进货单");
		map2.put("url", "goods/window/inOrder");
		map2.put("mark", "展示最近的几个进货单子");
		map2.put("num", "2");
		map2.put("mainUrl", "goods/window/");
		list.add(map1);
		list.add(map2);
		map.put("data", list);
		map.put("info", "获取客户关系->功能窗口列表成功");
		return map;
	}
	/**	
	 *最近出货单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/outOrder")
	public String myCreateCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/pageItem/outOrder";
	}
	/**	
	 *最近进货单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/inOrder")
	public String toMyCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/pageItem/inOrder";
	}
}
