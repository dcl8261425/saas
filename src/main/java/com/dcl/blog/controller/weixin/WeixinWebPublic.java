package com.dcl.blog.controller.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.Awards;
import com.dcl.blog.model.ChanceList;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyAddress;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.Games;
import com.dcl.blog.model.GamesAwardsList;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.ImageList;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.NumLibs;
import com.dcl.blog.model.Orders;
import com.dcl.blog.model.OrdersItem;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.model.UserGamesNum;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.model.UserTableLinkLinkMan;
import com.dcl.blog.model.Vote;
import com.dcl.blog.model.VoteItem;
import com.dcl.blog.model.VoteUser;
import com.dcl.blog.model.WebPublicMessage;
import com.dcl.blog.model.WeiXinGoods;
import com.dcl.blog.model.WeiXinWebHtml;
import com.dcl.blog.model.WeixinOrder;
import com.dcl.blog.model.WeixinWebSelect;
import com.dcl.blog.model.dto.BuyCar;
import com.dcl.blog.model.dto.VoteItemDTO;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/weixin/public")
public class WeixinWebPublic {
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
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		List<Object> weixin = dao.getObjectList("WeixinWebSelect",
				"where conpanyId=" + MoreUserManager.getAppShopId(req));
		if (weixin.size() > 0) {
			WeixinWebSelect ws = (WeixinWebSelect) weixin.get(0);
			WeiXinWebHtml htmls = (WeiXinWebHtml) dao.getObject(
					ws.getWeixinWebHtmlId(), "WeiXinWebHtml");
			Conpany conpany = (Conpany) dao.getObject(
					MoreUserManager.getAppShopId(req), "Conpany");
			model.addAttribute("conpany", conpany);
			if (htmls != null) {
				model.addAttribute("html", htmls.getHtmls());
				return "/mobel/mobel";
			} else {
				model.addAttribute("html", "该商户没有设置微网站");
				return "/mobel/Nomobel";
			}
		} else {
			model.addAttribute("html", "该商户不存在");
			return "/mobel/Nomobel";
		}

	}


	@RequestMapping(value = "/vip")
	public String vip(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		if (lm != null) {
			if(lm.getVipidNum()==0){
				lm.setVipLevel("目前积分数无法达到第一个等级");
				lm.setVipMarks("目前积分数无法达到第一个等级");
			}
			model.addAttribute("success", true);
			model.addAttribute("linkman", lm);
		} else {
			model.addAttribute("success", false);
		}
		return "/mobel/vip";
	}

	@RequestMapping(value = "/goods")
	public String goods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/good";
	}

	@RequestMapping(value = "/map")
	public String map(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/map";
	}

	@RequestMapping(value = "/guaguaka")
	public String guaguaka(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Games game = dao.getGames("刮刮卡", MoreUserManager.getAppShopId(req));
		if (game == null) {
			game = new Games();
			game.setMarks("刮刮卡");
			game.setName("刮刮卡");
			game.setNum(3);
			game.setUses(false);
			game.setZhongjianggailu(3);
			game.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(game);
		}
		List<GamesAwardsList> list = dao.getGamesAwardsList(game.getId(),
				MoreUserManager.getAppShopId(req));
		model.addAttribute("jiangpin", list);
		return "/mobel/guaguaka";
	}

	@RequestMapping(value = "/jifenduihuan")
	public String jifenduihuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);

		return "/mobel/jifenduihuan";
	}

	@RequestMapping(value = "/jifenduihuan_data")
	@ResponseBody
	public Map jifenduihuan_data(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "10";
		}
		map.put("data", dao.getScoreDuihuansList(
				MoreUserManager.getAppShopId(req), Integer.parseInt(nowpage),
				Integer.parseInt(countNum)));
		return map;
	}

	@RequestMapping(value = "/goods_data")
	@ResponseBody
	public Map goods_data(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		if (name == null) {
			name = "";
		}
		List list = dao.getObjectListPage("WeiXinGoods", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and useShow = true and goodsName like '%" + name + "%'",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/goodsItem")
	public String goodsItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		String id = req.getParameter("id");
		WeiXinGoods weixin = (WeiXinGoods) dao.getObject(Long.parseLong(id),
				"WeiXinGoods", MoreUserManager.getAppShopId(req));
		if (weixin != null || weixin.isUseShow()) {
			model.addAttribute("obj", weixin);
		} else {
			model.addAttribute("obj", null);
		}
		return "/mobel/goodItem";
	}

	@RequestMapping(value = "/UserToScoreduihuan")
	@ResponseBody
	public Map UserToScoreduihuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);

		if (lm != null) {
			String id = req.getParameter("id");
			String num = req.getParameter("num");
			Object obj = dao.getObject(Long.parseLong(id), "ScoreDuihuan",
					MoreUserManager.getAppShopId(req));
			if (obj != null) {
				ScoreDuihuan vip = (ScoreDuihuan) obj;
				double userscore = lm.getLinkManScore();
				double score = vip.getScore() * Long.parseLong(num);
				if (userscore >= score) {
					if (vip.getNum() >= Long.parseLong(num)) {
						vip.setNum(vip.getNum() - Integer.parseInt(num));
						dao.update(vip);
						lm.setLinkManScore(DoubleUtil.sub(lm.getLinkManScore() , score));
						dao.update(lm);
						ScoreToGoodsList s = new ScoreToGoodsList();
						s.setXuliehao("sc" + new Date().getTime() + lm.getId()
								+ vip.getId());
						s.setName(vip.getName());
						s.setNum(Integer.parseInt(num));
						s.setScoreDuiHuanId(vip.getId());
						s.setUses(false);
						s.setUserid(ut.getId());
						s.setLinkmainId(lm.getId());
						s.setConpanyId(MoreUserManager.getAppShopId(req));
						dao.add(s);
						String content="您好,"+lm.getLinkManName()+"您积分兑换了<"+s.getName()+">,序列号为:"+s.getXuliehao()+dao.getMessageSetContent(lm.getConpanyId(), MessageSet.REDUCE_SCORE, lm, score,0.0, null,null);
						dao.getSendMessage(lm.getLinkManPhone(),content, lm.getConpanyId(), MessageSet.REDUCE_SCORE,false);
						map.put("data", s);
						map.put("success", true);
						map.put("info", "兑换完成.序列号为：" + s.getXuliehao() + "");
					} else {
						map.put("success", false);
						map.put("info", "此商品不足，不够您所兑换的数量");
					}
				} else {
					map.put("success", false);
					map.put("info", "您的积分不够兑换此商品");
				}

			} else {
				map.put("success", false);
				map.put("info", "没有找到要兑换的商品");
			}
		} else {
			map.put("success", false);
			map.put("info", "您没有绑定账户");
		}

		return map;
	}

	@RequestMapping(value = "/dazhuanpan")
	public String dazhuanpan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		if (lm == null) {
			model.addAttribute("isHiveUser", false);
		} else {
			model.addAttribute("isHiveUser", true);
		}
		Games game = dao.getGames("大转盘", MoreUserManager.getAppShopId(req));
		if (game == null) {
			game = new Games();
			game.setMarks("大转盘");
			game.setName("大转盘");
			game.setNum(3);
			game.setUses(true);
			game.setZhongjianggailu(3);
			game.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(game);
		}
		List<GamesAwardsList> list = dao.getGamesAwardsList(game.getId(),
				MoreUserManager.getAppShopId(req));
		model.addAttribute("jiangpin", list);
		return "/mobel/dazhuanpan";
	}

	@RequestMapping(value = "/tomap")
	public String tomap(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/mapIfram";
	}

	@RequestMapping(value = "/vip_order")
	public String vip_order(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/vip/order";
	}

	@RequestMapping(value = "/vip_order_data")
	@ResponseBody
	public Map vip_order_data(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return map;
	}

	@RequestMapping(value = "/vip_num")
	public String vip_num(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/vip/num";
	}
	@RequestMapping(value = "/vip_scroe")
	public String vip_scroe(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/vip/score";
	}
	@RequestMapping(value = "/vip_num_data")
	@ResponseBody
	public Map vip_num_data(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return map;
	}

	@RequestMapping(value = "/querymap")
	@ResponseBody
	public Map querymap(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		Conpany conpany = (Conpany) dao.getObject(
				MoreUserManager.getAppShopId(req), "Conpany");
		List<ConpanyAddress> list = dao.getConpanyAddressByConpanyId(conpany
				.getId());
		map.put("success", true);
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/getDaZhuanPanUser")
	@ResponseBody
	public Map getDaZhuanPanUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		try {
			Games game = dao.getGames("大转盘", MoreUserManager.getAppShopId(req));
			if (game == null) {
				map.put("info", "大转盘暂时未开通！");
				map.put("success", false);
				return map;
			} else {
				if (game.isUses()) {
					
					boolean isRight = true;
					if (lm != null) {
						if(game.isScore()){
							if(game.getScoreNum()>lm.getLinkManScore()){
								map.put("info", "店主大转盘打开了积分抽奖模式，每"+game.getScoreNum()+"积分，抽奖一次，您的积分是"+lm.getLinkManScore()+"不足抽奖");
								map.put("success", false);
								return map;
							}else{
								lm.setLinkManScore(DoubleUtil.round(DoubleUtil.sub(lm.getLinkManScore(), game.getScoreNum()), 3));
								
							}
						}
						UserGamesNum num = dao.getUserGamesNum(game.getId(),
								lm.getId(), MoreUserManager.getAppShopId(req));
						if (num == null) {
							num = new UserGamesNum();
							num.setGameId(game.getId());
							num.setNum(0);
							num.setUserid(lm.getId());
							num.setConpanyId(MoreUserManager.getAppShopId(req));
							dao.add(num);
						}

						if (num.getNum() >= game.getNum()) {

							if (!DateUtil.validateDateIsNowDate(num
									.getLastGameTime())) {
								isRight = false;
								map.put("info", "您今天大转盘次数已满，不能再次使用，请明天再来！");
								map.put("success", false);
								return map;
							} else {
								num.setNum(0);
							}
							if (num.getLastGameTime() == null) {
								isRight = true;
							}
						}
						if (isRight) {
							List<GamesAwardsList> list = dao
									.getGamesAwardsList(game.getId(),
											MoreUserManager.getAppShopId(req));
							if (list.size() < 3) {
								map.put("info", "管理员未吧奖品项上架！");
								map.put("success", false);
							} else {
								int numtext = new Random().nextInt(game
										.getZhongjianggailu()) + 1;
								GamesAwardsList g = null;
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).getValue() == numtext) {
										g = list.get(i);
									}
								}
								// 获取奖品
								if (g != null) {
									if (g.getNum() > 0) {
										dao.update(lm);
										Awards a = (Awards) dao.getObject(g
												.getAwardsid(), "Awards",
												MoreUserManager
														.getAppShopId(req));
										NumLibs numlib = new NumLibs();
										numlib.setContent(a.getContent());
										numlib.setAwardsId(a.getId());
										numlib.setStartDate(g.getStartDate());
										numlib.setEndDate(g.getEndDate());
										numlib.setUses(false);
										numlib.setUserid(ut.getId());
										numlib.setLinkmanId(lm.getId());
										numlib.setXuliehao("dzp"
												+ new Date().getTime()
												+ lm.getId() + numtext);
										numlib.setConpanyId(MoreUserManager
												.getAppShopId(req));
										dao.add(numlib);
										num.setLastGameTime(new Date());
										num.setNum(num.getNum() + 1);
										num.setConpanyId(MoreUserManager
												.getAppShopId(req));
										dao.update(num);
										g.setNum(g.getNum() - 1);
										g.setConpanyId(MoreUserManager
												.getAppShopId(req));
										dao.update(g);
										map.put("success", true);
										map.put("xuliehao",
												numlib.getXuliehao());
										map.put("data", numtext);
										map.put("jiangpin", a.getContent());
									} else {
										map.put("success", false);
										map.put("info", "您抽中了" + g.getContent()
												+ "但是数量已经抽完。所以下次请早。谢谢");
									}
								} else {
									num.setLastGameTime(new Date());
									num.setNum(num.getNum() + 1);
									dao.update(num);
									map.put("data", numtext);
									map.put("success", true);
								}
							}
						}
					} else {
						map.put("success", false);
						map.put("info", "用户还没有成为vip");
					}

				} else {
					map.put("info", "大转盘没有开启");
					map.put("success", false);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("info", "系统异常请重新尝试");
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping(value = "/getguaguakaRun")
	@ResponseBody
	public Map getguaguakaRun(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		try {
			Games game = dao.getGames("刮刮卡", MoreUserManager.getAppShopId(req));
			if (game == null) {
				map.put("info", "刮刮卡未开通！");
				map.put("success", false);
			} else {
				if (game.isUses()) {

					boolean isRight = true;
					if (lm != null) {
						if(game.isScore()){
							if(game.getScoreNum()>lm.getLinkManScore()){
								map.put("info", "店主打开了积分抽奖模式，每"+game.getScoreNum()+"积分，抽奖一次，您的积分是"+lm.getLinkManScore()+"不足抽奖");
								map.put("success", false);
								return map;
							}else{
								lm.setLinkManScore(DoubleUtil.round(DoubleUtil.sub(lm.getLinkManScore(), game.getScoreNum()), 3));
							
							}
						}
						UserGamesNum num = dao.getUserGamesNum(game.getId(),
								lm.getId(), MoreUserManager.getAppShopId(req));
						if (num == null) {
							num = new UserGamesNum();
							num.setGameId(game.getId());
							num.setNum(0);
							num.setUserid(lm.getId());
							num.setConpanyId(MoreUserManager.getAppShopId(req));
							dao.add(num);
						}

						if (num.getNum() >= game.getNum()) {

							if (!DateUtil.validateDateIsNowDate(num
									.getLastGameTime())) {
								isRight = false;
								map.put("info", "您今天刮刮卡次数已满，不能再次使用，请明天再来！");
								map.put("success", false);
								return map;
							} else {
								num.setNum(0);
							}
							if (num.getLastGameTime() == null) {
								isRight = true;
							}
						}
						if (isRight) {
							List<GamesAwardsList> list = dao
									.getGamesAwardsList(game.getId(),
											MoreUserManager.getAppShopId(req));
							int numtext = new Random().nextInt(game
									.getZhongjianggailu());
							GamesAwardsList g = null;
							if (list.size() > numtext) {
								g = list.get(numtext);
							}
							// 获取奖品
							if (g != null) {
								if (g.getNum() > 0) {
									dao.update(lm);
									Awards a = (Awards) dao.getObject(
											g.getAwardsid(), "Awards",
											MoreUserManager.getAppShopId(req));
									NumLibs numlib = new NumLibs();
									numlib.setContent(a.getContent());
									numlib.setAwardsId(a.getId());
									numlib.setStartDate(g.getStartDate());
									numlib.setEndDate(g.getEndDate());
									numlib.setUses(false);
									numlib.setUserid(ut.getId());
									numlib.setLinkmanId(lm.getId());
									numlib.setXuliehao("dzp"
											+ new Date().getTime() + lm.getId()
											+ numtext);
									numlib.setConpanyId(MoreUserManager
											.getAppShopId(req));
									dao.add(numlib);
									num.setLastGameTime(new Date());
									num.setNum(num.getNum() + 1);
									dao.update(num);
									map.put("success", true);
									map.put("xuliehao", numlib.getXuliehao());
									map.put("data", numtext);
									map.put("jiangpin", a.getContent());
								} else {
									map.put("success", false);
									map.put("info", "您抽中了" + g.getContent()
											+ "但是数量已经抽完。所以下次请早。谢谢");
								}
							} else {
								num.setLastGameTime(new Date());
								num.setNum(num.getNum() + 1);
								dao.update(num);
								map.put("info", "您没有中奖");
								map.put("success", false);
							}
						}
					} else {
						map.put("success", false);
						map.put("info", "用户还没有成为vip");
					}
				} else {
					map.put("info", "刮刮卡没有开启");
					map.put("success", false);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("info", "系统异常请重新尝试");
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping(value = "/addCar")
	@ResponseBody
	public Map addCar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
			Map map = new HashMap<String, Object>();
			LinkManList lm = MoreUserManager.getLinkManList(req, dao);
			String id = req.getParameter("id");
			String conpanyId = req.getParameter("conpanyIdbuycar");
			if(lm!=null){
			addCarSession(req, Long.parseLong(id), conpanyId);
			map.put("success", true);
			map.put("info", "添加成功，去购物车结算");
		}else{
			map.put("success", false);
			map.put("info", "您不是会员无法直接购物，请加入会员");
		}
		return map;
	}

	@RequestMapping(value = "/deleteCarItem")
	@ResponseBody
	public Map deleteCarItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String id = req.getParameter("id");
		String type = req.getParameter("type");
		String conpanyId = req.getParameter("conpanyIdbuycar");
		deleteCarSession(req, Long.parseLong(id), type, conpanyId);
		return map;
	}

	@RequestMapping(value = "/buycar")
	public String buycar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		return "/mobel/buycar";
	}

	@RequestMapping(value = "/getCar")
	@ResponseBody
	public Map getCar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		if (buycar == null) {
			map.put("success", false);
		} else {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
					.getOrderList();
			Set<Long> set = orderlist.keySet();
			Iterator<Long> i = set.iterator();
			while (i.hasNext()) {
				Long conpanid = i.next();
				Conpany conpany = (Conpany) dao.getObject(conpanid, "Conpany");
				List<Map<Long, OrdersItem>> ordersItemList = orderlist
						.get(conpanid);
				Map<String, Object> carConpany = new HashMap<String, Object>();
				List<OrdersItem> list2 = new ArrayList<OrdersItem>();
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					Set<Long> set2 = map2.keySet();
					if(set2.iterator().hasNext()){
						OrdersItem OrdersItem2 = map2.get(set2.iterator().next());
						list2.add(OrdersItem2);
					}
				}
				carConpany.put("data", list2);
				carConpany.put("conpany", conpany);
				list.add(carConpany);
			}
			map.put("success", true);
			map.put("data", list);
		}
		return map;
	}
	@RequestMapping(value = "/reflashInfo")
	@ResponseBody
	public Map reflashInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		MoreUserManager.getLinkManList(req,dao,true);
		lm = MoreUserManager.getLinkManList(req, dao);
		if(lm.getVipidNum()==0){
			lm.setVipLevel("目前积分数无法达到第一个等级");
			lm.setVipMarks("目前积分数无法达到第一个等级");
		}
		map.put("success", true);
		map.put("obj", lm);
		return map;
	}
	@RequestMapping(value = "/submitOrder")
	@ResponseBody
	public Map submitOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId=req.getParameter("buycarConpanyId");
		String address=req.getParameter("address");
		String phone=req.getParameter("phone");
		String mark=req.getParameter("mark");
		StringBuffer orderContentMessage=new StringBuffer();
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
				.getOrderList();
		List<Map<Long, OrdersItem>> orderList=orderlist.get(Long.parseLong(conpanyId));
		Orders order=new Orders();
		WeixinOrder wxorder=new WeixinOrder();
		//迭代出订单并且保存。
		List<Object> objlist=dao.getObjectList("LinkManList", "where userTableId="+ut.getId()+" and conpanyId="+conpanyId);
		double countPrice=0;
		LinkManList link=null;
		if(objlist.size()>0){
			link=(LinkManList) objlist.get(0);
		}
		if(link!=null){
			MessageSet set2=dao.getMessageSet(link.getConpanyId());
			order.setChanceId(link.getChanceListId());
			order.setChanceName(link.getChanceListName());
			order.setConpanyId(link.getConpanyId());
			order.setCountPrice(0);
			order.setCreateDate(new Date());
			order.setCreateUser(0);
			order.setCreateUserName("微信订单");
			order.setInStoreUser(0);
			order.setInStoreUserName("订单");
			order.setLinkmanId(link.getId());
			order.setLinkmanName(link.getLinkManName());
			order.setMarks("微信订单");
			order.setOrderNum("");
			order.setPay(false);
			order.setState(0);
			order.setTitle("微信："+link.getLinkManName()+"订单");
			dao.add(order);
			String orderNum="HDORDER_"+order.getId()+link.getConpanyId()+link.getId()+new Date().getTime();
			order.setOrderNum(orderNum);
			dao.update(order);
			wxorder.setAddress(address);
			wxorder.setLinkManId(link.getId());
			wxorder.setMarks(mark);
			wxorder.setNum(orderNum);
			wxorder.setOrderId(order.getId());
			wxorder.setPhone(phone);
			wxorder.setStartDate(new Date());
			wxorder.setUsername(ut.getTrueName());
			wxorder.setUserid(ut.getId());
			wxorder.setConpanyId(link.getConpanyId());
			dao.add(wxorder);
			for(int i=0;i<orderList.size();i++){
				Map<Long,OrdersItem> map2=orderList.get(i);
				Set set=map2.keySet();
				Iterator i2=set.iterator();
				while(i2.hasNext()){
					OrdersItem item=map2.get(i2.next());
					item.setInOrderId(order.getId());
					item.setCreateDate(new Date());
					item.setMarks("微信购物");
					dao.add(item);
					countPrice=DoubleUtil.add(countPrice, DoubleUtil.mul(item.getPrice(), item.getGoodsNum()));
					orderContentMessage.append("[名称-").append(item.getGoodsName()).append(",数量-").append(item.getGoodsNum()).append(",单价-").append(item.getPrice()).append("]");
				}
			}
			order.setCountPrice(countPrice);
			dao.update(order);
			String content=dao.getMessageSetContent(lm.getConpanyId(), MessageSet.ORDER_INFO_TO_USER, lm, 0.0, countPrice, order,orderContentMessage.toString());
			dao.getSendMessage(lm.getLinkManPhone(),content, lm.getConpanyId(), MessageSet.ORDER_INFO_TO_USER,false);
			orderList.clear();
			map.put("success", true);
		}else{
			map.put("info", "您没有关注此店面微信，请先关注微信后再进行购物");
			map.put("success", false);
		}
		
		return map;
	}
	public boolean addCarSession(HttpServletRequest req, long goodsid,
			String conpanyId) {
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		if (buycar == null) {
			buycar = new BuyCar();
			Map<Long, List<Map<Long, OrdersItem>>> conpanyList = new HashMap<Long, List<Map<Long, OrdersItem>>>();
			buycar.setOrderList(conpanyList);
			buycar.setUserid(lm.getUserTableId());
			buycar.setUsername(lm.getLinkManName());
			buycar.setPhone(lm.getLinkManPhone());
			buycar.setMarks("");
			buycar.setAddress("");
			buycar.setLinkManId(lm.getId());
			req.getSession().setAttribute("buycar", buycar);
		}
		WeiXinGoods gt = null;
		if (conpanyId == null) {
			gt = (WeiXinGoods) dao.getObject(goodsid, "WeiXinGoods",
					MoreUserManager.getAppShopId(req));
		} else {
			gt = (WeiXinGoods) dao.getObject(goodsid, "WeiXinGoods",
					Long.parseLong(conpanyId));
		}
		if (gt != null && gt.isUseShow()) {
			Map<Long, List<Map<Long, OrdersItem>>> conpanyList = buycar
					.getOrderList();
			GoodsTable gts = null;
			if (conpanyId == null) {
				gts = (GoodsTable) dao.getObject(gt.getGoodsId(), "GoodsTable",
						MoreUserManager.getAppShopId(req));
			} else {
				gts = (GoodsTable) dao.getObject(gt.getGoodsId(), "GoodsTable",
						Long.parseLong(conpanyId));
			}
			OrdersItem order = null;
			List<Map<Long, OrdersItem>> orderList = null;
			if (conpanyId == null) {
				orderList = conpanyList.get(MoreUserManager.getAppShopId(req));
			} else {
				orderList = conpanyList.get(Long.parseLong(conpanyId));
			}
			if (orderList == null) {
				orderList = new ArrayList<Map<Long, OrdersItem>>();
				conpanyList.put(MoreUserManager.getAppShopId(req), orderList);
			}
			for (int ii = 0; ii < orderList.size(); ii++) {
				Map<Long, OrdersItem> map2 = orderList.get(ii);
				order = map2.get(goodsid);
			}
			if (order == null) {
				order = new OrdersItem();
				order.setCodeid(gts.getCodeid());
				order.setConpanyId(gt.getConpanyId());
				order.setCreateDate(new Date());
				order.setGoodsId(gts.getId());
				order.setGoodsinPrice(gts.getInPrice());
				order.setGoodsModel(gts.getGoodsModel());
				order.setGoodsName(gts.getGoodsName());
				order.setGoodsNum(1);
				order.setGoodsSourceId(0);
				order.setGoodsSourceName("");
				order.setGoodsToStorehouseId(0);
				order.setGoodsToStorehouseName("");
				order.setGoodsType(gts.getGoodsType());
				order.setInOrderId(gt.getId());
				order.setMarks("");
				order.setPrice(gts.getPrice());
				order.setScore(gts.getScore());
				order.setSpell(gts.getSpell());
				Map<Long, OrdersItem> map = new HashMap<Long, OrdersItem>();
				map.put(goodsid, order);
				orderList.add(map);

			} else {
				order.setGoodsNum(order.getGoodsNum() + 1);
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean deleteCarSession(HttpServletRequest req, long goodsid,
			String type, String conpanyId) {
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		if (buycar == null) {
			return false;
		} else {
			if (type != null) {
				Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
						.getOrderList();
				List<Map<Long, OrdersItem>> ordersItemList = orderlist.get(Long
						.parseLong(conpanyId));
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					if (map2.get(goodsid) != null) {
						map2.remove(goodsid);
					}
				}
			} else {
				Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
						.getOrderList();
				List<Map<Long, OrdersItem>> ordersItemList = orderlist.get(Long
						.parseLong(conpanyId));
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					if (map2.get(goodsid) != null) {
						OrdersItem orderItem = map2.get(goodsid);
						orderItem.setGoodsNum(orderItem.getGoodsNum() - 1);
					}
				}
			}
		}
		return true;
	}
	@RequestMapping(value = "/getVipOrderList")
	@ResponseBody
	public Map getVipOrderList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		List list = dao.getObjectListPage("Orders", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and linkmanId = "+lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getVipOrderItemList")
	@ResponseBody
	public Map getVipOrderItemList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		WeixinOrder wxg=null;
		List list = dao.getObjectList("WeixinOrder", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and orderId = " + id);
		if(list.size()>0){
			wxg=(WeixinOrder) list.get(0);
		}
		List list2 = dao.getObjectList("OrdersItem", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and inOrderId = " + id + "");
		map.put("obj", wxg);
		map.put("data", list2);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getvipDuihuanjuan")
	@ResponseBody
	public Map getvipDuihuanjuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		if (name == null) {
			name = "";
		}
		List list = dao.getObjectListPage("ScoreToGoodsList", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and linkmainId = " + lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getvipzhongjiangjuan")
	@ResponseBody
	public Map getvipzhongjiangjuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		List list = dao.getObjectListPage("NumLibs", "where conpanyId="
				+ MoreUserManager.getAppShopId(req)
				+ " and linkmanId = " + lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/rigister")
	@ResponseBody
	public Map rigister(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		
		String phone=req.getParameter("phone");
		String name=req.getParameter("name");
		List<Object> list=dao.getObjectList("UserTable", "where phone="+phone);
		Map map = new HashMap<String, Object>();
		Conpany conpany=(Conpany) dao.getObject(MoreUserManager.getAppShopId(req), "Conpany");
		/*long num=dao.getObjectListNum("LinkManList","where conpanyId="+conpany.getId());
		if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new Date()))){
			if(num>=100){
				map.put("success", false);
				map.put("info","创建失败，该店铺是非付费店铺。会员已经达到上限，上限为100位");
				return map;
			}
		}*/
		if(lm==null){
			List<Object> objlist=dao.getObjectList("LinkManList", "where linkManPhone='"+phone+"' and conpanyId="+MoreUserManager.getAppShopId(req));
			
			if(objlist.size()>0){
				req.getSession().setAttribute("login_weixin_openid_lm", objlist.iterator().next());
				map.put("success", false);
				map.put("stute", 1);
				map.put("info","加入失败，您已经是该店会员，需要从新登录。");
				return map;
			}
		}
		if(lm==null){
			if(list.size()>0){
				ut=(UserTable) list.get(0);
			}else{
				ut=new UserTable();
				ut.setPassword("0000");
				ut.setPhone(phone);
				ut.setTrueName(name);
				dao.add(ut);
				
			}
			ChanceList cl=new ChanceList();
			cl.setConpanyId(MoreUserManager.getAppShopId(req));
			cl.setCreateDate(new Date());
			cl.setCreateManId(0);
			cl.setCreateManMark("来自微信");
			cl.setCreayeManName("微信端");
			cl.setCustomerAddress("");
			cl.setCustomerLevel(1);
			cl.setCustomerMark("来自微信");
			cl.setCustomerName(name);
			cl.setCustomerType("来自微信");
			cl.setLastBuy(null);
			cl.setNotesUserId(0);
			cl.setNotesUserName(null);
			cl.setState(1);
			dao.add(cl);
			lm=new LinkManList();
			lm.setAddUserId(0);
			lm.setAddUserName("用户自来-微信微端");
			lm.setChanceListId(cl.getId());
			lm.setConpanyId(cl.getConpanyId());
			lm.setLinkManBirthday(null);
			lm.setLinkManJob("");
			lm.setLinkManMark("来自微信");
			lm.setLinkManMaxScore(0);
			lm.setLinkManName(name);
			lm.setLinkManPhone(phone);
			lm.setChanceListName(cl.getCreayeManName());
			lm.setLinkManScore(0);
			lm.setLinkManSex("未知");
			lm.setOpenid(MoreUserManager.getOpenId(req, dao));
			lm.setUserTableId(ut.getId());
			lm.setVipidNum(0);
			dao.add(lm);
			UserTableLinkLinkMan ull=new UserTableLinkLinkMan();
			ull.setChanceId(cl.getId());
			ull.setChanceName(cl.getCustomerName());
			ull.setConpanId(conpany.getId());
			ull.setChanceName(conpany.getConpanyName());
			ull.setLinkDate(new Date());
			ull.setLinkmanId(lm.getId());
			ull.setLinkmanName(lm.getLinkManName());
			ull.setUsertableid(ut.getId());
			ull.setUsertableUserName(ut.getTrueName());
			dao.add(ull);
			MoreUserManager.setLinkManList(lm, req);
			MoreUserManager.setUserTable(ut, req);
			map.put("success", true);
			map.put("info", "注册成功");
		}else{
			map.put("success", false);
			map.put("info", "错误，您已经是会员无需再次注册");
		}
		return map;
	}
	@RequestMapping(value = "/wenzhang")
	public String wenzhang(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId=req.getParameter("conpanyId");
		String wenzhangId=req.getParameter("wenzhangId");
		Map map = new HashMap<String, Object>();
		WebPublicMessage wm=(WebPublicMessage) dao.getObject(Long.parseLong(wenzhangId), "WebPublicMessage",Long.parseLong(conpanyId));
		wm.setLooknum(wm.getLooknum()+1);
		dao.update(wm);
		model.addAttribute("html", wm.getContent());
		model.addAttribute("title", wm.getName());
		model.addAttribute("id", wm.getId());
		model.addAttribute("isVote", wm.isVote());
		if(wm.isVote()){
			List<Object> list=dao.getObjectListBySql("from Vote where wenzhangId="+wenzhangId);
			if(list.size()>=1){
				Vote vote=(Vote) list.get(0);
				model.addAttribute("Vote_vip", vote.isPublics());
				model.addAttribute("Vote_duoxuan", vote.isOnes());
				model.addAttribute("Vote_startDate", vote.getStardate());
				model.addAttribute("Vote_endDate", vote.getEndDate());
				model.addAttribute("islogin", lm!=null);
				List<VoteItemDTO> listdto=new ArrayList<VoteItemDTO>();
				Iterator<VoteItem> i=vote.getVoteItem().iterator();
				int num=0;
				while(i.hasNext()){
					VoteItem item=i.next();
					VoteItemDTO itemdto=new VoteItemDTO();
					itemdto.setName(item.getName());
					itemdto.setId(item.getId());
					itemdto.setConpanyId(item.getConpanyId());
					itemdto.setNum(item.getNum());
					num+=item.getNum();
					listdto.add(itemdto);
				}
				for(int ii=0;ii<listdto.size();ii++){
					VoteItemDTO dto=listdto.get(ii);
					if(dto.getNum()>0){
						float num2=((float)dto.getNum()/(float)num)*100;
						dto.setBaifenbi(num2);
					}else{
						dto.setBaifenbi(0);
					}
				}
				model.addAttribute("Vote_item", listdto);
				
			}
		}
		Conpany conpany = (Conpany) dao.getObject(
				MoreUserManager.getAppShopId(req), "Conpany");
		model.addAttribute("conpany", conpany);
		return "/mobel/wenzhang";
	}
	/**
	 * 获取图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getImage")
	@ResponseBody
	public void getImage(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		String id=req.getParameter("id");
		ImageList image=(ImageList) dao.getObject(Long.parseLong(id), "ImageList");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(image.getLinkaddress());
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[2048];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	/**
	 * 获取图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getVideo")
	@ResponseBody
	public void getVideo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("video/mp4");
		String id=req.getParameter("id");
		
		ImageList image=(ImageList) dao.getObject(Long.parseLong(id), "ImageList");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(image.getLinkaddress());
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[2048];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	/**
	 * 获取图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getFile")
	@ResponseBody
	public void getFile(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		
		res.setDateHeader("Expires", 0);
		String id=req.getParameter("id");
		ImageList image=(ImageList) dao.getObject(Long.parseLong(id), "ImageList");
		res.setContentType(image.getFiletype());
		res.addHeader("Content-Disposition", "attachment; filename=" +image.getId()+"."+image.getFiletype());
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(image.getLinkaddress());
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[10048];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	@RequestMapping(value = "/passwordChange")
	@ResponseBody
	public Map passwordChange(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String oldpassword=req.getParameter("oldpassword");
		String newpassword=req.getParameter("newpassword");
		String renewpassword=req.getParameter("renewpassword");
		if(ut.getPassword().equals(oldpassword)){
			if(newpassword.equals(renewpassword)){
				ut.setPassword(newpassword);
				dao.update(ut);
				map.put("success", true);
				map.put("info", "修改完成");
			}else{
				map.put("success", false);
				map.put("info", "错误，两次输入的新密码不一样");
			}
		}else{
			map.put("success", false);
			map.put("info", "错误，旧密码错误");
		}
		return map;
	}
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map login(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String password=req.getParameter("password");
		lm=(LinkManList) req.getSession().getAttribute("login_weixin_openid_lm");
		List<Object> list=dao.getObjectList("UserTable", "where phone="+lm.getLinkManPhone());
		ut=(UserTable) list.get(0);
		if(ut.getPassword().equals(password)){
			lm.setOpenid(MoreUserManager.getOpenId(req, dao));
			dao.update(lm);
			map.put("success", true);
			map.put("info", "登录成功");
		}else{
			map.put("success", false);
			map.put("info", "错误，密码错误");
		}
		return map;
	}
	@RequestMapping(value = "/toupiao")
	@ResponseBody
	public Map toupiao(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String itemid=req.getParameter("ItemId");
		VoteItem item=(VoteItem) dao.getObject(Long.valueOf(itemid), "VoteItem");
		if(item.getVote().isPublics()){
			if(lm==null){
				map.put("success", false);
				map.put("info", "出错了,您没有登录无法投票");
				return map;
			}
		}
			List list=dao.getObjectListBySql("from VoteUser where voteItemId="+item.getId() +" and phone='"+lm.getLinkManPhone()+"'");
			if(list.size()>0){
				map.put("success", false);
				map.put("info", "出错了,您已经对此项投过票。");
				return map;
			}else{
				
				if(!item.getVote().isOnes()){
					List list2=dao.getObjectListBySql("from VoteUser where voteId="+item.getVote().getId() +" and phone='"+lm.getLinkManPhone()+"'");
					if(list2.size()>0){
						map.put("success", false);
						map.put("info", "出错了,本次投票只能投一个选项。");
						return map;
					}
				}
				VoteUser user=new VoteUser();
				user.setLinkmenId(lm.getId());
				user.setName(lm.getLinkManName());
				user.setPhone(lm.getLinkManPhone());
				user.setVoteId(item.getVote().getId());
				user.setVoteItemName(item.getName());
				user.setVoteItemId(item.getId());
				dao.add(user);
				item.setNum(item.getNum()+1);
				dao.update(item);
			}
		return map;
	}
	@RequestMapping(value = "/toupiao2")
	@ResponseBody
	public Map toupiao2(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String itemid=req.getParameter("ItemId");
		String conpanyId=req.getParameter("conpanyId");
		String phone=req.getParameter("phone");
		String name=req.getParameter("name");
		if(phone.length()!=11){
			map.put("success", false);
			map.put("info", "手机号输入错误。");
			return map;
		}
		List<Object> list=dao.getObjectList("UserTable", "where phone="+phone);
		Conpany conpany=(Conpany) dao.getObject(Long.valueOf(conpanyId), "Conpany");
		long num=dao.getObjectListNum("LinkManList","where conpanyId="+conpany.getId());
		if(lm==null){
			List<Object> objlist=dao.getObjectList("LinkManList", "where linkManPhone='"+phone+"' and conpanyId="+Long.valueOf(conpanyId));
			if(objlist.size()>0){
				lm=(LinkManList) objlist.iterator().next();
				List<Object> list3=dao.getObjectList("UserTable", "where phone="+lm.getLinkManPhone());
				ut=(UserTable) list3.get(0);
				MoreUserManager.setLinkManList(lm, req);
				MoreUserManager.setUserTable(ut, req);
			}
		}
		if(lm==null){
			if(list.size()>0){
				ut=(UserTable) list.get(0);
			}else{
				ut=new UserTable();
				ut.setPassword("0000");
				ut.setPhone(phone);
				ut.setTrueName(name);
				dao.add(ut);
				
			}
			ChanceList cl=new ChanceList();
			cl.setConpanyId(Long.valueOf(conpanyId));
			cl.setCreateDate(new Date());
			cl.setCreateManId(0);
			cl.setCreateManMark("来自投票");
			cl.setCreayeManName("投票");
			cl.setCustomerAddress("");
			cl.setCustomerLevel(1);
			cl.setCustomerMark("来自投票");
			cl.setCustomerName(name);
			cl.setCustomerType("来自投票");
			cl.setLastBuy(null);
			cl.setNotesUserId(0);
			cl.setNotesUserName(null);
			cl.setState(1);
			dao.add(cl);
			lm=new LinkManList();
			lm.setAddUserId(0);
			lm.setAddUserName("用户自来-投票");
			lm.setChanceListId(cl.getId());
			lm.setConpanyId(cl.getConpanyId());
			lm.setLinkManBirthday(null);
			lm.setLinkManJob("");
			lm.setLinkManMark("来自投票");
			lm.setLinkManMaxScore(0);
			lm.setLinkManName(name);
			lm.setLinkManPhone(phone);
			lm.setChanceListName(cl.getCreayeManName());
			lm.setLinkManScore(0);
			lm.setLinkManSex("未知");
			lm.setOpenid(MoreUserManager.getOpenId(req, dao));
			lm.setUserTableId(ut.getId());
			lm.setVipidNum(0);
			dao.add(lm);
			UserTableLinkLinkMan ull=new UserTableLinkLinkMan();
			ull.setChanceId(cl.getId());
			ull.setChanceName(cl.getCustomerName());
			ull.setConpanId(conpany.getId());
			ull.setChanceName(conpany.getConpanyName());
			ull.setLinkDate(new Date());
			ull.setLinkmanId(lm.getId());
			ull.setLinkmanName(lm.getLinkManName());
			ull.setUsertableid(ut.getId());
			ull.setUsertableUserName(ut.getTrueName());
			dao.add(ull);
			MoreUserManager.setLinkManList(lm, req);
			MoreUserManager.setUserTable(ut, req);
		}
		VoteItem item=(VoteItem) dao.getObject(Long.valueOf(itemid), "VoteItem");
		if(item.getVote().isPublics()){
			if(lm==null){
				map.put("success", false);
				map.put("info", "出错了,您没有登录无法投票");
				return map;
			}
		}
			list=dao.getObjectListBySql("from VoteUser where voteItemId="+item.getId() +" and phone='"+lm.getLinkManPhone()+"'");
			if(list.size()>0){
				map.put("success", false);
				map.put("info", "出错了,您已经对此项投过票。");
				return map;
			}else{
				
				if(!item.getVote().isOnes()){
					List list2=dao.getObjectListBySql("from VoteUser where voteId="+item.getVote().getId() +" and phone='"+lm.getLinkManPhone()+"'");
					if(list2.size()>0){
						map.put("success", false);
						map.put("info", "出错了,本次投票只能投一个选项。");
						return map;
					}
				}
				VoteUser user=new VoteUser();
				user.setLinkmenId(lm.getId());
				user.setName(lm.getLinkManName());
				user.setPhone(lm.getLinkManPhone());
				user.setVoteId(item.getVote().getId());
				user.setVoteItemName(item.getName());
				user.setVoteItemId(item.getId());
				dao.add(user);
				item.setNum(item.getNum()+1);
				dao.update(item);
				map.put("success", true);
				map.put("info", "成功");
			}
		return map;
	}
}
