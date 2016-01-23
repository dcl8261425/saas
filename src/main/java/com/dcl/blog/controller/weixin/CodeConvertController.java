package com.dcl.blog.controller.weixin;

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

import com.dcl.blog.model.Awards;
import com.dcl.blog.model.NumLibs;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/weixin/CodeConvert")
public class CodeConvertController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeiXinFunctionController.class);
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
	@RequestMapping(value = "/jifenTogoodManager")
	@ResponseBody
	public Map jifenTogoodManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			String num = req.getParameter("num");
			ScoreToGoodsList score = dao.getScoreNum(num,MoreUserManager.getAppShopId(req));
			if (score != null) {
				if (score.isUses()) {
					map.put("success", false);
					map.put("info", "该序列号已经使用过。");
				} else {
					ScoreDuihuan a = (ScoreDuihuan) dao.getObject(
							score.getScoreDuiHuanId(), "ScoreDuihuan",MoreUserManager.getAppShopId(req));
					score.setUses(true);
					dao.update(score);
					map.put("success", true);
					map.put("score", score);
					map.put("ScoreDuihuan", a);
				}

			} else {
				map.put("success", false);
				map.put("info", "该序列号不存在");
			}
		return map;
	}

	@RequestMapping(value = "/huodongTogoodManager")
	@ResponseBody
	public Map huodongTogoodManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		
			String num = req.getParameter("num");
			List<NumLibs> score = dao.getNAwardsNum(num,MoreUserManager.getAppShopId(req));

			if (score.size() > 0) {
				NumLibs nus = score.iterator().next();
				if (nus.isUses()) {
					map.put("success", false);
					map.put("info", "该序列号已经使用过");
				} else {
					if (DateUtil.betweenNowDate(nus.getStartDate(),
							nus.getEndDate())) {
						Awards a = (Awards) dao.getObject(
								nus.getAwardsId(), "Awards",MoreUserManager.getAppShopId(req));
						nus.setUses(true);
						dao.update(nus);
						map.put("success", true);
						map.put("Awards", a);
						map.put("NumLibs", nus);
					} else {
						map.put("success", false);
						map.put("info",
								"兑换日期没有到，或已经过了，兑换日期为"
										+ DateUtil.formatDateYYYY_MM_DD(nus
												.getStartDate())
										+ "至"
										+ DateUtil.formatDateYYYY_MM_DD(nus
												.getEndDate()));
					}
				}
			} else {
				map.put("success", false);
				map.put("info", "该序列号不存在");
			}
		
		return map;
	}

}
