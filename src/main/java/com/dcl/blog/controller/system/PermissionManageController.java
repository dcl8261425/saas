package com.dcl.blog.controller.system;

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

import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
/**
 * 公司的管理controller
 * 
 * @author Administrator
 * lookupPremissionAll
 */
@Controller
@RequestMapping("/Permission")
public class PermissionManageController {
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionManageController.class);
	private DaoService dao;
	public static List<Map> data;
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
	 * 查看所有权限
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookupPremissionAll")
	@ResponseBody
	public Map lookupPremissionAll(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String name=req.getParameter("name");
			String where="";
			if(name!=null&&name!=""){
				where="where functionName like '%"+name+"%'";
			}
			List<Object> list=dao.getObjectList("SoftPermission", where);
			map.put("data", list);
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 查看主功能项权限
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookupPremissionMain")
	@ResponseBody
	public Map lookupPremissionMain(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id=req.getParameter("id");
		if(id==null||id.equals("")){
			id="0";
		}
		try{
			String name=req.getParameter("name");
			String where="";
			where="where uplevel="+id;
			if(name!=null&&name!=""){
				where=where+" and functionName like '%"+name+"%'";
			}
			List<Object> list=dao.getObjectList("SoftPermission", where);
			map.put("data", list);
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
}
