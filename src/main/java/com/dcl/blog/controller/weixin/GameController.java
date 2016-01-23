package com.dcl.blog.controller.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.Awards;
import com.dcl.blog.model.Games;
import com.dcl.blog.model.GamesAwardsList;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.NumLibs;
import com.dcl.blog.model.UserGamesNum;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping(value = "/gameController")
public class GameController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
/**
 * 获取奖品列表
 * @param model
 * @param req
 * @param res
 * @return
 */
	@RequestMapping(value = "/getAwards")
	@ResponseBody
	public Map getGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try {
			map.put("data", dao.getAwards(MoreUserManager.getAppShopId(req)));
			map.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("success", false);
		}
		return map;
	}
/**
 * 添加奖品
 * @param model
 * @param req
 * @param res
 * @return
 */
	@RequestMapping(value = "/addAwards")
	@ResponseBody
	public Map addGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
			String content = req.getParameter("content");
			String marks = req.getParameter("marks");
			try {
				Awards awards = new Awards();
				awards.setContent(content);
				awards.setMarks(marks);
				awards.setConpanyId(MoreUserManager.getAppShopId(req));
				dao.add(awards);
				map.put("info", "添加完成");
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("success", false);
			}
		return map;
	}

	@RequestMapping(value = "/deleteAwards")
	@ResponseBody
	public Map deleteAwards(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		
			try {
				dao.delete(dao.getObject(Long.parseLong(id), "Awards",MoreUserManager.getAppShopId(req)));
				map.put("info", "删除完成");
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("success", false);
			}
		return map;
	}

	@RequestMapping(value = "/addDazhuanpan1")
	@ResponseBody
	public Map addDazhuanpan1(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String text = req.getParameter("text");
		String id = req.getParameter("id");
		String enddate = req.getParameter("enddate");
		String startdate = req.getParameter("startdate");
		String num=req.getParameter("num");
		
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if (game == null) {
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				List<GamesAwardsList> list = dao.getGamesAwardsList(game
						.getId(),MoreUserManager.getAppShopId(req));
				GamesAwardsList g = null;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getValue() == 1) {
						g = list.get(i);
					}
				}
				if (g == null) {
					g = new GamesAwardsList();
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(1);
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setNum(Integer.parseInt(num));
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.add(g);
				} else {
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(1);
					g.setNum(Integer.parseInt(num));
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.update(g);
				}
				map.put("info", "修改完成");
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}

	@RequestMapping(value = "/addDazhuanpan2")
	@ResponseBody
	public Map addDazhuanpan2(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String text = req.getParameter("text");
		String id = req.getParameter("id");
		String enddate = req.getParameter("enddate");
		String startdate = req.getParameter("startdate");
		String num=req.getParameter("num");
		
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if (game == null) {
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				List<GamesAwardsList> list = dao.getGamesAwardsList(game
						.getId(),MoreUserManager.getAppShopId(req));
				GamesAwardsList g = null;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getValue() == 2) {
						g = list.get(i);
					}
				}
				if (g == null) {
					g = new GamesAwardsList();
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(2);
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setNum(Integer.parseInt(num));
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.add(g);
				} else {
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(2);
					g.setNum(Integer.parseInt(num));
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.update(g);
				}
				map.put("info", "修改完成");
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}

	@RequestMapping(value = "/addDazhuanpan3")
	@ResponseBody
	public Map addDazhuanpan3(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String text = req.getParameter("text");
		String id = req.getParameter("id");
		String enddate = req.getParameter("enddate");
		String startdate = req.getParameter("startdate");
		String num=req.getParameter("num");
		
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if (game == null) {
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				List<GamesAwardsList> list = dao.getGamesAwardsList(game
						.getId(),MoreUserManager.getAppShopId(req));
				GamesAwardsList g = null;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getValue() == 3) {
						g = list.get(i);
					}
				}
				if (g == null) {
					g = new GamesAwardsList();
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(3);
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setNum(Integer.parseInt(num));
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.add(g);
				} else {
					g.setGamesid(game.getId());
					g.setAwardsid(Long.parseLong(id));
					g.setValue(3);
					g.setNum(Integer.parseInt(num));
					g.setStartDate(DateUtil.toDateType(startdate));
					g.setEndDate(DateUtil.toDateType(enddate));
					g.setContent(text);
					g.setConpanyId(MoreUserManager.getAppShopId(req));
					dao.update(g);
				}
				map.put("info", "修改完成");
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}

	
	@RequestMapping(value = "/getDaZhuanpanInfo")
	@ResponseBody
	public Map getDaZhuanpanInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				List<GamesAwardsList> list=dao.getGamesAwardsList(game.getId(),MoreUserManager.getAppShopId(req));
				map.put("data",list);
				map.put("success", true);
				map.put("use", game.isUses());
				map.put("jilv", game.getZhongjianggailu());
				map.put("num", game.getNum());
				map.put("score", game.isScore());
				map.put("scoreNum", game.getScoreNum());
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}
	@RequestMapping(value = "/updateGameDaZhuanPan")
	@ResponseBody
	public Map updateGameDaZhuanPan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String use=req.getParameter("value");
		String type=req.getParameter("type");
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				if(type.equals("1")){
					game.setUses(Boolean.parseBoolean(use));
					dao.update(game);
				}else{
					game.setScore(Boolean.parseBoolean(use));
					dao.update(game);
				}
				map.put("info","成功");
				map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		
		
		return map;
	}
	@RequestMapping(value = "/setDazhuanpanJilv")
	@ResponseBody
	public Map setDazhuanpanJilv(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String value=req.getParameter("value");
		String type=req.getParameter("type");
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
					
				}
				if(type!=null&&type.equals("score")){
					game.setScoreNum(DoubleUtil.round(Double.parseDouble(value), 3));
					dao.update(game);
					map.put("info","完成");
					map.put("success", true);
				}else{
					int jilv=Integer.parseInt(value);
					if(jilv<3){
						jilv=3;
					}
					game.setZhongjianggailu(jilv);
					dao.update(game);
					map.put("info","完成");
					map.put("success", true);
				}
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		
		
		return map;
	}
	@RequestMapping(value = "/setDazhuanpanNum")
	@ResponseBody
	public Map setDazhuanpanNum(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String value=req.getParameter("value");
			try {
				Games game = dao.getGames("大转盘",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("大转盘");
					game.setName("大转盘");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				game.setNum(Integer.parseInt(value));
				dao.update(game);
				map.put("info","完成");
				map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		
		
		return map;
	}
	@RequestMapping(value = "/setguaguakaJilv")
	@ResponseBody
	public Map setguaguakaJilv(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String value=req.getParameter("value");
		String type=req.getParameter("type");
			try {
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				if(type!=null&&type.equals("score")){
					game.setScoreNum(DoubleUtil.round(Double.parseDouble(value), 3));
					dao.update(game);
					map.put("info","完成");
					map.put("success", true);
				}else{
					int jilv=Integer.parseInt(value);
					if(jilv<3){
						jilv=3;
					}
					game.setZhongjianggailu(jilv);
					dao.update(game);
					map.put("info","完成");
					map.put("success", true);
				}
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		
		return map;
	}
	@RequestMapping(value = "/setguaguakaNum")
	@ResponseBody
	public Map setguaguakaNum(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String value=req.getParameter("value");
			try {
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				game.setNum(Integer.parseInt(value));
				dao.update(game);
				map.put("info","完成");
				map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		
		return map;
	}
	@RequestMapping(value = "/updateGameguaguaka")
	@ResponseBody
	public Map updateGameguaguaka(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String use=req.getParameter("value");
		String type=req.getParameter("type");
			try {
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				if(type.equals("1")){
					game.setUses(Boolean.parseBoolean(use));
					dao.update(game);
				}else{
					game.setScore(Boolean.parseBoolean(use));
					dao.update(game);
				}
				map.put("info","完成");
				map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}
	@RequestMapping(value = "/getguaguaka")
	@ResponseBody
	public Map getguaguaka(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
			try {
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				List<GamesAwardsList> list=dao.getGamesAwardsList(game.getId(),MoreUserManager.getAppShopId(req));
				map.put("data",list);
				map.put("success", true);
				map.put("use", game.isUses());
				map.put("jilv", game.getZhongjianggailu());
				map.put("num", game.getNum());
				map.put("score", game.isScore());
				map.put("scoreNum", game.getScoreNum());
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}
	@RequestMapping(value = "/deleteGuaguaka")
	@ResponseBody
	public Map deleteGuaguaka(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
			try {
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				String id=req.getParameter("id");
				GamesAwardsList gad=(GamesAwardsList) dao.getObject(Long.parseLong(id), "GamesAwardsList", MoreUserManager.getAppShopId(req));
				if(gad!=null){
					dao.delete(gad);
					map.put("info","删除成功");
					map.put("success", true);
				}else{
					map.put("info","要删除的奖品不存在");
					map.put("success", false);
				}
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}
	@RequestMapping(value = "/addAwardsToguaguaka")
	@ResponseBody
	public Map addAwardsToguaguaka(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		String value=req.getParameter("value");
			try {
				String text = req.getParameter("text");
				String id = req.getParameter("id");
				String enddate = req.getParameter("enddate");
				String startdate = req.getParameter("startdate");
				String num = req.getParameter("num");
				Games game = dao.getGames("刮刮卡",MoreUserManager.getAppShopId(req));
				if(game==null){
					game = new Games();
					game.setMarks("刮刮卡");
					game.setName("刮刮卡");
					game.setNum(3);
					game.setUses(true);
					game.setZhongjianggailu(3);
					game.setConpanyId(MoreUserManager.getAppShopId(req));
					game.setScore(false);
					game.setScoreNum(0);
					dao.add(game);
				}
				GamesAwardsList g=new GamesAwardsList();
				g.setGamesid(game.getId());
				g.setAwardsid(Long.parseLong(id));
				g.setContent(text);
				g.setStartDate(DateUtil.toDateType(startdate));
				g.setEndDate(DateUtil.toDateType(enddate));
				g.setValue(0);
				g.setNum(Integer.parseInt(num));
				g.setConpanyId(MoreUserManager.getAppShopId(req));
				dao.add(g);
				map.put("info","完成");
				map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info","系统异常请重新尝试");
				map.put("success", false);
			}
		return map;
	}
	
	public static String getOpenid(HttpServletRequest req,Model model){
		Object openid=req.getSession().getAttribute("openid");
		String openid2=null;
		if(openid==null){
			openid2=req.getParameter("openid");
			req.getSession().setAttribute("openid", openid2);
		}else{
			openid2=(String)openid;
		}
		model.addAttribute("openid", openid2==null?"":openid);
		return openid2;
	}
}
