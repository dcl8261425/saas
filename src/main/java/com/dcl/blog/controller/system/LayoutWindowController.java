package com.dcl.blog.controller.system;

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

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.WindowLayout;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/LayoutWindow")
public class LayoutWindowController {
	private static final Logger logger = LoggerFactory
			.getLogger(LayoutWindowController.class);
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
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map add(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String url=req.getParameter("url");
		WindowLayout w=dao.getWindowLayoutByUserIdAndUrl(user.getId(), url);
		if(w!=null){
			map.put("success", false);
			map.put("info", "添加未成功，因为已经添加过此功能块");
		}else{
			w=new WindowLayout();
			w.setConpanyId(user.getConpanyId());
			w.setUrls(url);
			String mainUrl=req.getParameter("mainUrl");
			w.setMainUrl(mainUrl);
			String right=req.getParameter("right");
			String left=req.getParameter("left");
			w.setLefts(Integer.parseInt(left)==1);
			w.setRights(Integer.parseInt(right)==1);
			w.setUserid(user.getId());
			dao.add(w);
			map.put("success", true);
		}
		return map;
	}
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map query(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String mainUrl=req.getParameter("mainUrl");
		List<WindowLayout> list=dao.getWindowLayoutByUser(user.getId(), mainUrl);
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map delete(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String url=req.getParameter("url");
		WindowLayout w=dao.getWindowLayoutByUserIdAndUrl(user.getId(), url);
		if(w!=null){
			dao.delete(w);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除未成功，未添加此功能块，或已删除");
		}
		return map;
	}
}
