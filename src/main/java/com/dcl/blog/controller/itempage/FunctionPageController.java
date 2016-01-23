package com.dcl.blog.controller.itempage;

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

import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.email.emailimpl;
/**
 * 功能项的页面控制
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/function")
public class FunctionPageController {
	private static final Logger logger = LoggerFactory
			.getLogger(FunctionPageController.class);
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
	 * 组员管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/GroupUserManager")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/pageItem/function/GroupUserManager/Main";
	}
	/**
	 * 组员管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/RolePermissionManager")
	public String RolePermissionManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/pageItem/function/RolePermissionManager/Main";
	}
	/**
	 * 获取所参与的组和每个组的组员
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryGroupUser")
	public String queryGroupUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/pageItem/function/QueryGroupUser/QueryGroupUser";
	}
}
