package com.dcl.blog.controller.weixin;

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
@RequestMapping("/weixin/page")
public class WeiXinMainController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeiXinMainController.class);
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
	 *概览
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin";
	}
	/**	
	 *微信公众账号设置
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_UserId_Set")
	public String weixin_UserId_Set(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixinuserId_set";
	}
	/**	
	 *微信公众账号设置
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_VIP_set")
	public String weixin_VIP_set(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin_vip_set";
	}
	/**	
	 *活动与游戏管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_Game_set")
	public String weixin_Game_set(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin_game_set";
	}
	/**	
	 *序列号兑换
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_convert_set")
	public String weixin_convert_set(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin_convert_set";
	}
	/**	
	 *末班设置
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_Model_set")
	public String weixin_Model_set(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin_Model_set";
	}
	/**	
	 *所在位置设置
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_Model_set_map")
	public String weixin_Model_set_map(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/weixin_Model_set_map";
	}
	/**	
	 *微信商品管理页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_model_goodsManager")
	public String weixin_model_goodsManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/goods";
	}
	/**	
	 *微信订单页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/weixin_model_order")
	public String weixin_model_order(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/order";
	}
	/**	
	 * 图片模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/imageModelManager")
	public String imageModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/imageModelManager";
	}
	/**	
	 *视频模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/videoModelManager")
	public String videoModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/videoModelManager";
	}
	/**	
	 *文本模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/textModelManager")
	public String textModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/textModelManager";
	}
	/**	
	 *游戏模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/gameModelManager")
	public String gameModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/gameModelManager";
	}
	/**	
	 *格子铺模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/ershouModelManager")
	public String ershouModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/ershouModelManager";
	}
	/**	
	 *公告模块管理
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/gonggaoModelManager")
	public String gonggaoModelManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/weixin/gonggaoModelManager";
	}
}
