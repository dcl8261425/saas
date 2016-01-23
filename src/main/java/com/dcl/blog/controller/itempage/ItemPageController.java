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
@RequestMapping("/itempage")
public class ItemPageController {
	private static final Logger logger = LoggerFactory
			.getLogger(ItemPageController.class);
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
	 * 概述页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookAllStu")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/pageItem/lookAllStu";
	}
	/**
	 * 权限页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/systemPermission")
	public String systemPermission(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/pageItem/SystemPermission";
	}
}
