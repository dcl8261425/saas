package com.dcl.blog.controller.goods;

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
@RequestMapping("/goods/page")
public class GoodsMainController {
	private static final Logger logger = LoggerFactory
			.getLogger(GoodsMainController.class);
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
		return "banckManager/goods/goods";
	}
	/**	
	 *库存状况页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryData")
	public String queryData(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/queryData";
	}
	/**	
	 *进货入库页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/inGoods")
	public String inGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/inGoods";
	}
	/**	
	 *	出库界面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/outGoods")
	public String outGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/outGoods";
	}
	/**	
	 *	仓库管理页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/storehouse")
	public String storehouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/storehouse";
	}
	/**	
	 *	供货商管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/GoodSource")
	public String GoodSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/GoodSource";
	}
	/**	
	 *	快速查询数据
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryData_window")
	public String queryData_window(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/function/queryData/Main";
	}
	/**	
	 *	快速添加货物
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addData_window")
	public String addData_window(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/goods/function/AddData/Main";
	}
	/**	
	 *	获取添加仓库信息弹出框
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/Edit_StoreHouse_window")
	public String Edit_StoreHouse_window(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/EditLinkData/StoreHouse/storehouse";
	}
	/**	
	 *	供货商弹出框
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/Edit_GoodSource_window")
	public String Edit_GoodSource_window(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/EditLinkData/GoodsSource/GoodsSource";
	}
}
