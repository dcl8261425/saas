package com.dcl.blog.controller.hr;

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

@Controller
@RequestMapping("/hr/page")
public class HRMainController {
	private static final Logger logger = LoggerFactory
			.getLogger(HRMainController.class);
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
	 *主页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/hr";
	}
	/**	
	 *员工账号管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/custemr")
	public String queryData(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/hr_custemr";
	}
	/**	
	 *	签到
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/meeting")
	public String outGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/hr_meeting";
	}
	/**	
	 *	绩效
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/performance")
	public String storehouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/hr_performance";
	}
	/**	
	 *	添加新员工账号 快捷框
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addCustemmer")
	public String addCustemmer(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/function/addCustemer/main";
	}
	/**	
	 *	查询员工信息 快捷框
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryCustemmer")
	public String queryCustemmer(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/function/queryCustemmer/main";
	}
	/**	
	 *	外勤
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/waiqin")
	public String waiqin(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		String map=req.getParameter("map");
		if(map!=null){
			return "banckManager/hr/mapIfram";
		}else{
			return "banckManager/hr/hr_waiqin";
		}
	}
	/**	
	 *	互动空间
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/kongjian")
	public String kongjian(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/hr/hr_kongjian";
	}
	
}
