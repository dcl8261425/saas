package com.dcl.blog.controller.crm;

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
@RequestMapping("/crm/page")
public class CRMMainController {
	private static final Logger logger = LoggerFactory
			.getLogger(CRMMainController.class);
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
		return "banckManager/crm/crm";
	}
	/**	
	 *我创建的客户机会页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/myCreateCustomChance")
	public String myCreateCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/myCreateCustomChance";
	}
	/**	
	 *分配给我的客户机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/toMyCustomChance")
	public String toMyCustomChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/toMyCustomChance";
	}
	/**	
	 *我的客户管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/MyCustomManager")
	public String MyCustomManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/MyCustomManager";
	}
	/**	
	 *创建客户弹出窗
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createChanceWindow")
	public String createChanceWindow(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/function/createChance/Main";
	}
	/**	
	 *创建客户弹出窗
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addLinkMan")
	public String addLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/function/addLinkMan/Main";
	}
	/**	
	 *开发流程记录
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/chanceOpenInfo")
	public String chanceOpenInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/function/chanceOpenInfo/Main";
	}
	/**	
	 *开发流程建议
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/chanceOpenMarks")
	public String chanceOpenMarks(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/function/chanceOpenMarks/Main";
	}
	/**	
	 * 选择员工
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/selectCustemor")
	public String selectCustemor(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/EditLinkData/QueryGroupUser/QueryGroupUser";
	}
	/**	
	 *选择联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/selectLinkMan")
	public String selectLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/EditLinkData/addLinkMan/Main";
	}
	/**	
	 *选择开发
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/selectNotes")
	public String selectNotes(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/EditLinkData/chanceOpenInfo/Main";
	}
	/**	
	 *查看所有机会列表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryAllChanceList")
	public String queryAllChanceList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/crm/queryAllChanceList";
	}
}
