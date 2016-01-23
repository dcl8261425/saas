package com.dcl.blog.controller.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/messageinfo")
public class MessageController {
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
	 * 短信设置
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "message/messageSet";
	}

	/**
	 * 会员短信
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/vipsend")
	public String vipsend(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "message/vipMessage";
	}

	/**
	 * 短信模板
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/messagetemp")
	public String messagetemp(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "message/messageTemp";
	}

	/**
	 * 历史信息
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/messagelog")
	public String messagelog(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "message/messageInfo";
	}

	@RequestMapping(value = "/setboolean")
	@ResponseBody
	public Map setboolean(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String volue = req.getParameter("value");
		String type = req.getParameter("type");
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		if (volue != null && type != null) {
			MessageSet set = dao.getMessageSet(users.getConpanyId());
			int typeint = Integer.parseInt(type);
			switch (typeint) {
			case MessageSet.ADD_PRICE:
				set.setAddPriceToVip(Boolean.parseBoolean(volue));
				break;
			case MessageSet.ADD_SCORE:
				set.setAddscoreToVip(Boolean.parseBoolean(volue));
				break;
			case MessageSet.ORDER_INFO_TO_USER:
				set.setOrderToUser(Boolean.parseBoolean(volue));
				break;
			case MessageSet.REDUCE_PRICE:
				set.setReducePriceToVip(Boolean.parseBoolean(volue));
				break;
			case MessageSet.REDUCE_SCORE:
				set.setReducescoreToVip(Boolean.parseBoolean(volue));
				break;
			case MessageSet.WEIXIN_INFO_TO_USER:
				set.setWinxinInfoToUser(Boolean.parseBoolean(volue));
				break;
			case MessageSet.YUDING_INFO_TO_USER:
				set.setYudingToUser(Boolean.parseBoolean(volue));
				break;
			case 99:
				set.setQianMing(volue);
				break;
			default:
				break;
			}
			dao.update(set);
			map.put("success", true);
			map.put("info", "成功");
		} else {
			map.put("success", false);
			map.put("info", "错误，参数错误");
		}
		return map;
	}

	@RequestMapping(value = "/setTemp")
	@ResponseBody
	public Map setTemp(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String volue = req.getParameter("value");
		String type = req.getParameter("type");
		String methed = req.getParameter("methed");
		String phone = req.getParameter("phone");
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		MessageSet set = dao.getMessageSet(users.getConpanyId());
		int typeint = Integer.parseInt(type);
		if (methed.equals("add")) {
		volue=volue.replace("【", "[").replace("】", "]");
		}
		switch (typeint) {
		case MessageSet.ADD_PRICE:
			if (methed.equals("add")) {
				set.setAddPriceToVipContent(volue);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.ADD_SCORE:
			if (methed.equals("add")) {
				set.setAddscoreToVipContent(volue);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.ORDER_INFO_TO_USER:
			if (methed.equals("add")) {
				set.setOrderToUserContent(volue);
				set.setOrderToUserPhone(phone);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.REDUCE_PRICE:
			if (methed.equals("add")) {
				set.setReducesPriceToVipContent(volue);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.REDUCE_SCORE:
			if (methed.equals("add")) {
				set.setReducesscoreToVipContent(volue);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.WEIXIN_INFO_TO_USER:
			if (methed.equals("add")) {
				set.setWinxinInfoToUserContent(volue);
				set.setWinxinInfoToUserPhone(phone);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		case MessageSet.YUDING_INFO_TO_USER:
			if (methed.equals("add")) {
				set.setYudingToUserContent(volue);
				set.setYudingToUserPhone(phone);
				dao.update(set);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("obj", set);
				map.put("success", true);
				map.put("info", "成功");
			}
			break;
		default:
			break;
		}

		return map;
	}

	@RequestMapping(value = "/getMessageSet")
	@ResponseBody
	public Map getMessageSet(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		MessageSet mset = dao.getMessageSet(users.getConpanyId());
		map.put("success", true);
		map.put("obj", mset);
		return map;
	}

	@RequestMapping(value = "/sendVipMessage")
	@ResponseBody
	public Map sendVipMessage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		MessageSet mset = dao.getMessageSet(users.getConpanyId());
		String value = req.getParameter("value");
		String select = req.getParameter("select");
		String type = req.getParameter("type");
		String num = req.getParameter("num");
		int numint = 0;
		int typeint = 0;
		long selectLong = 0;
		try {
			typeint = Integer.parseInt(type);
			if (typeint == 1) {
				// 发送兑换券
				try {
					numint = Integer.parseInt(num);
				} catch (Exception e) {
					map.put("success", false);
					map.put("info", "发送储量填写正数。");
					return map;
				}
				try {
					selectLong = Long.parseLong(select);
				} catch (Exception e) {
					map.put("success", false);
					map.put("info", "请选择兑换的奖品");
					return map;
				}
				ScoreDuihuan sd = (ScoreDuihuan) dao.getObject(selectLong,
						"ScoreDuihuan", users.getConpanyId());
				// 因为不能超过100行，所以进行分割
				int fornum = 0;// 没100行循环一次，这个参数是循环几次
				int maxHang = 0;// 最大的返回行数
				int lastHang = 0;// 如果是560则循环到底6次的时候只返回60行，相当于取余运算
				if (numint > 100) {
					maxHang = 100;
					fornum = numint / 100;
					lastHang = numint - (fornum * 100);
					if (lastHang > 0) {
						// 如果有多余的则多循环一次
						fornum = fornum + 1;
					}
				} else {
					fornum = 1;
					maxHang = numint;
					lastHang = numint;
				}
				for (int ii = 1; ii <= fornum; ii++) {
					if (ii == fornum && lastHang > 0) {
						maxHang = lastHang;
					}
					List list = dao.getObjectListPage("LinkManList",
							"where conpanyId=" + users.getConpanyId(), ii,
							maxHang);
					StringBuffer phone = new StringBuffer();
					StringBuffer content = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						LinkManList lml = (LinkManList) list.get(i);
						if (lml.getLinkManPhone() != null
								&& lml.getLinkManPhone().length() == 11) {
							ScoreToGoodsList s = new ScoreToGoodsList();
							s.setXuliehao("sc" + new Date().getTime()
									+ lml.getId() + sd.getId());
							s.setName(sd.getName());
							s.setNum(Integer.parseInt(num));
							s.setScoreDuiHuanId(sd.getId());
							s.setUses(false);
							s.setUserid(lml.getUserTableId());
							s.setLinkmainId(lml.getId());
							s.setConpanyId(MoreUserManager.getAppShopId(req));
							dao.add(s);
							if (phone.length() == 0) {
								phone.append(lml.getLinkManPhone());
								content.append(value).append("兑换券名:")
										.append(sd.getName()).append("序列号:")
										.append(s.getXuliehao())
										.append("【" + mset.getQianMing() + "】");
							} else {
								phone.append(",").append(lml.getLinkManPhone());
								content.append("{|}").append(value)
										.append("兑换券名:").append(sd.getName()).append(",")
										.append("序列号:").append(s.getXuliehao())
										.append("【" + mset.getQianMing() + "】");
							}
						}
					}
					int meitiaozi=content.toString().length() / numint;
					int meitiaotiaoshu=meitiaozi/60+1;
					int tiaoshu =meitiaotiaoshu* numint;
					if (mset.getNum() < numint) {
						map.put("success", false);
						map.put("info", "条数不足，大概需要:" + tiaoshu + "条");
						return map;
					} else {
						dao.getSendMessage(phone.toString(),
								content.toString(), users.getConpanyId(),
								MessageSet.QUNSEND_INFO,true);
					}
				}

			}
			if (typeint == 2) {
				// 发送普通信息
				try {
					numint = Integer.parseInt(num);
				} catch (Exception e) {
					map.put("success", false);
					map.put("info", "发送数量填写正数。");
					return map;
				}
				int fornum = 0;// 没100行循环一次，这个参数是循环几次
				int maxHang = 0;// 最大的返回行数
				int lastHang = 0;// 如果是560则循环到底6次的时候只返回60行，相当于取余运算
				if (numint > 1000) {
					maxHang = 1000;
					fornum = numint / 1000;
					lastHang = numint - (fornum * 1000);
					if (lastHang > 0) {
						// 如果有多余的则多循环一次
						fornum = fornum + 1;
					}
				} else {
					fornum = 1;
					maxHang = numint;
					lastHang = numint;
				}
				for (int ii = 1; ii <= fornum; ii++) {
					if (ii == fornum && lastHang > 0) {
						maxHang = lastHang;
					}
					List list = dao.getObjectListPage("LinkManList",
							"where conpanyId=" + users.getConpanyId(), ii,
							maxHang);
					StringBuffer phone = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						LinkManList lml = (LinkManList) list.get(i);
						if (lml.getLinkManPhone() != null
								&& lml.getLinkManPhone().length() == 11) {

							if (phone.length() == 0) {
								phone.append(lml.getLinkManPhone());

							} else {
								phone.append(",").append(lml.getLinkManPhone());

							}
						}
					}
					int meitiaozi=value.toString().length() / numint;
					int meitiaotiaoshu=meitiaozi/60+1;
					int tiaoshu =meitiaotiaoshu* numint;
					if (mset.getNum() < numint) {
						map.put("success", false);
						map.put("info", "条数不足，大概需要:" + tiaoshu + "条");
						return map;
					} else {
						dao.getSendMessage(phone.toString(),
								value + "【" + mset.getQianMing() + "】",
								users.getConpanyId(), MessageSet.QUNSEND_INFO,false);
					}
				}
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "类别错误");
			return map;
		}

		map.put("success", true);
		map.put("info", "发送成功，后台在逐步发送");
		return map;
	}
}
