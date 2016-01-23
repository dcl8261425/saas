package com.dcl.blog.controller.weixin;

import java.util.Date;
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

import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.LinkmanMoneyInfo;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.model.VIPSet;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/weixin/vip")
public class VIPController {
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
	@RequestMapping(value = "/getVip")
	@ResponseBody
	public Map getVip(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			try{
				ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				String nowpage = req.getParameter("nowpage");
				String countNum = req.getParameter("countNum");
				String name = req.getParameter("name");
				if (nowpage == null) {
					nowpage = "1";
				}
				if (countNum == null) {
					countNum = "30";
				}
				long num=dao.getObjectListNum("LinkManList", "where linkManPhone like '%"+name+"%' and conpanyId="+users.getConpanyId());
				List list=dao.getObjectListPage("LinkManList", "where linkManPhone like '%"+name+"%' and conpanyId="+users.getConpanyId(), Integer.parseInt(nowpage), Integer.parseInt(countNum));
				map.put("pagenum", num/Integer.parseInt(countNum)+1);
				map.put("success", true);
				map.put("data", list);
				return map;
			}catch(Exception e){
				map.put("success", false);
				map.put("info","获取失败");
			}
		return map;
	}	
	@RequestMapping(value = "/getVipList")
	@ResponseBody
	public Map getVipList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", dao.getVipSetList(MoreUserManager.getAppShopId(req)));
		return map;
	}

	@RequestMapping(value = "/addVipList")
	@ResponseBody
	public Map addVipList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
		long num=dao.getObjectListNum("VIPSet", "where conpanyId="+user.getConpanyId());
		if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new Date()))){
			if(num>=2){
				map.put("success", false);
				map.put("info","创建失败，免费用户只可以创建两个会员等级，付费用户不限");
				return map;
			}
		}
			String marks = req.getParameter("marks");
			String sroce = req.getParameter("sroce");
			String name = req.getParameter("name");
			VIPSet vip = new VIPSet();
			vip.setMarks(marks);
			vip.setName(name);
			vip.setScore(Long.parseLong(sroce));
			vip.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(vip);
			map.put("success", true);
			map.put("info", "完成");
		return map;
	}

	@RequestMapping(value = "/addscoreToUser")
	@ResponseBody
	public Map addscoreToUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String id = req.getParameter("id");
			String sroce = req.getParameter("sroce");
			LinkManList user=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList", MoreUserManager.getAppShopId(req));
			user.setLinkManScore(DoubleUtil.add(user.getLinkManScore() , Double.parseDouble(sroce)));
			user.setLinkManMaxScore(DoubleUtil.add(user.getLinkManMaxScore(), Double.parseDouble(sroce)));
			List<VIPSet> list = dao.getVipSetList(MoreUserManager.getAppShopId(req));
			VIPSet vip = null;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getScore() <= user.getLinkManMaxScore()) {
					if (vip != null) {
						if (vip.getScore() < list.get(i).getScore()) {
							vip = list.get(i);
						}
					} else {
						vip = list.get(i);
					}
				}
			}
			if (vip != null&&user.getVipidNum()!=vip.getId()) {
				user.setVipidNum(vip.getId());
				user.setVipLevel(vip.getName());
				user.setVipMarks(vip.getMarks());
				String content="vip等级已变动成为:"+user.getVipLevel()+" "+dao.getMessageSetContent(users.getConpanyId(), MessageSet.ADD_SCORE, user, Double.parseDouble(sroce), 0.0, null,null);
				dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
			}else{
				String content=dao.getMessageSetContent(users.getConpanyId(), MessageSet.ADD_SCORE, user, Double.parseDouble(sroce), 0.0, null,null);
				dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
			}
			dao.update(user);
			map.put("success", true);
			map.put("info", "完成");
		return map;
	}
	@RequestMapping(value = "/jianscoreToUser")
	@ResponseBody
	public Map jianscoreToUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Map<String, Object> map = new HashMap<String, Object>();
			String id = req.getParameter("id");
			String sroce = req.getParameter("sroce");
			LinkManList user=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList", MoreUserManager.getAppShopId(req));
			user.setLinkManScore(DoubleUtil.sub(user.getLinkManScore(), Double.parseDouble(sroce)));
			user.setLinkManMaxScore(DoubleUtil.sub(user.getLinkManMaxScore(), Double.parseDouble(sroce)));
			List<VIPSet> list = dao.getVipSetList(MoreUserManager.getAppShopId(req));
			VIPSet vip = null;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getScore() <= user.getLinkManMaxScore()) {
					if (vip != null) {
						if (vip.getScore() < list.get(i).getScore()) {
							vip = list.get(i);
						}
					} else {
						vip = list.get(i);
					}
				}
			}
			if (vip != null) {
				user.setVipidNum(vip.getId());
				user.setVipLevel(vip.getName());
				user.setVipMarks(vip.getMarks());
				String content="vip等级已变动成为:"+user.getVipLevel()+" "+dao.getMessageSetContent(users.getConpanyId(), MessageSet.REDUCE_SCORE, user, Double.parseDouble(sroce), 0.0, null,null);
				dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.REDUCE_SCORE,false);
			}else{
				String content=dao.getMessageSetContent(users.getConpanyId(), MessageSet.REDUCE_SCORE, user, Double.parseDouble(sroce), 0.0, null,null);
				dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.REDUCE_SCORE,false);
			}
			dao.update(user);
			map.put("success", true);
			map.put("info", "完成");
		return map;
	}
	@RequestMapping(value = "/getHuiyuanxinxi")
	@ResponseBody
	public Map getHuiyuanxinxi(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		LinkManList user = (LinkManList) dao.getObject(Long.parseLong(id),"LinkManList",MoreUserManager.getAppShopId(req));
		Object obj = dao.getObject(user.getVipidNum(), "VIPSet",MoreUserManager.getAppShopId(req));
		if (obj != null) {
			VIPSet vip = (VIPSet) obj;
			map.put("data", vip);
			map.put("success", true);
			map.put("info", "完成");
		} else {
			map.put("success", false);
			map.put("info", "该用户还没有vip等级，或系统没有设定vip等级");
		}

		return map;
	}

	@RequestMapping(value = "/getVipInfo")
	@ResponseBody
	public Map getVipInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		Object obj = dao.getObject(Long.parseLong(id), "VIPSet",MoreUserManager.getAppShopId(req));
		if (obj != null) {
			VIPSet vip = (VIPSet) obj;
			map.put("data", vip);
			map.put("success", true);
			map.put("info", "完成");
		} else {
			map.put("success", false);
			map.put("info", "没有找到");
		}

		return map;
	}

	@RequestMapping(value = "/updateVipList")
	@ResponseBody
	public Map updateVipList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String sroce = req.getParameter("sroce");
			String marks = req.getParameter("marks");
			Object obj = dao.getObject(Long.parseLong(id), "VIPSet",MoreUserManager.getAppShopId(req));
			if (obj != null) {
				VIPSet vip = (VIPSet) obj;
				vip.setMarks(marks);
				vip.setName(name);
				vip.setScore(Long.parseLong(sroce));
				dao.update(vip);
				map.put("data", vip);
				map.put("success", true);
				map.put("info", "完成");
			} else {
				map.put("success", false);
				map.put("info", "没有找到");
			}
	
		return map;
	}

	@RequestMapping(value = "/getScoreDuiHuanList")
	@ResponseBody
	public Map getScoreDuiHuanList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		map.put("data", dao.getScoreDuihuansList(MoreUserManager.getAppShopId(req),1,100));
		map.put("success", true);
		}catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping(value = "/addScoreDuiHuanList")
	@ResponseBody
	public Map addScoreDuiHuanList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			String marks = req.getParameter("marks");
			String sroce = req.getParameter("sroce");
			String name = req.getParameter("name");
			String image = req.getParameter("image");
			String num = req.getParameter("num");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
			long nums=dao.getObjectListNum("ScoreDuihuan", "where conpanyId="+user.getConpanyId());
			if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new Date()))){
				if(nums>=6){
					map.put("success", false);
					map.put("info","创建失败，免费用户只可以创建6个积分兑换物，付费用户不限");
					return map;
				}
			}
			ScoreDuihuan vip = new ScoreDuihuan();
			vip.setContent(marks);
			vip.setName(name);
			vip.setImage(image);
			vip.setScore(Long.parseLong(sroce));
			vip.setNum(Integer.parseInt(num));
			vip.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(vip);
			map.put("success", true);
			map.put("info", "完成");
		return map;
	}

	@RequestMapping(value = "/getScoreDuiHuan")
	@ResponseBody
	public Map getScoreDuiHuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		Object obj = dao.getObject(Long.parseLong(id), "ScoreDuihuan",MoreUserManager.getAppShopId(req));
		if (obj != null) {
			ScoreDuihuan vip = (ScoreDuihuan) obj;
			map.put("data", vip);
			map.put("success", true);
			map.put("info", "完成");
		} else {
			map.put("success", false);
			map.put("info", "没有找到");
		}

		return map;
	}

	@RequestMapping(value = "/updateScoreDuiHuan")
	@ResponseBody
	public Map updateScoreDuiHuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String sroce = req.getParameter("sroce");
			String marks = req.getParameter("marks");
			String image = req.getParameter("image");
			String num = req.getParameter("num");
			Object obj = dao.getObject(Long.parseLong(id), "ScoreDuihuan",MoreUserManager.getAppShopId(req));
			if (obj != null) {
				ScoreDuihuan vip = (ScoreDuihuan) obj;
				vip.setContent(marks);
				vip.setName(name);
				vip.setScore(Long.parseLong(sroce));
				vip.setImage(image);
				vip.setNum(Integer.parseInt(num));
				dao.update(vip);
				map.put("data", vip);
				map.put("success", true);
				map.put("info", "完成");
			} else {
				map.put("success", false);
				map.put("info", "没有找到");
			}
		return map;
	}

	@RequestMapping(value = "/deleteScoreDuiHuan")
	@ResponseBody
	public Map deleteScoreDuiHuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
			String id = req.getParameter("id");
			Object obj = dao.getObject(Long.parseLong(id), "ScoreDuihuan",MoreUserManager.getAppShopId(req));
			if (obj != null) {
				ScoreDuihuan vip = (ScoreDuihuan) obj;
				dao.delete(vip);
				map.put("success", true);
				map.put("info", "成功");
			} else {
				map.put("success", false);
				map.put("info", "没有找到");
			}
		return map;
	}
	@RequestMapping(value = "/addpriceToUser")
	@ResponseBody
	public Map addpriceToUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		String money = req.getParameter("money");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		LinkManList user=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList", MoreUserManager.getAppShopId(req));
		Conpany conpany=(Conpany) dao.getObject(users.getConpanyId(), "Conpany");
		if(user!=null){
			user.setMoney(DoubleUtil.add(user.getMoney(), Double.parseDouble(money)));
			dao.update(user);
			UserTable ut=(UserTable) dao.getObject(user.getUserTableId(), "UserTable");
			LinkmanMoneyInfo info=new LinkmanMoneyInfo();
			info.setConpanyId(users.getConpanyId());
			info.setConpanyName(conpany.getConpanyName());
			info.setCreateManId(users.getId());
			info.setCreateManName(users.getTrueName());
			info.setLinkmanId(user.getId());
			info.setLinkmanName(user.getLinkManName());
			info.setLinkmanPhone(user.getLinkManPhone());
			info.setMoney(Double.parseDouble(money));
			info.setStartDate(new Date());
			info.setUserTableId(ut.getId());
			info.setUserTableName(ut.getTrueName());
			dao.add(info);
			String content=dao.getMessageSetContent(users.getConpanyId(), MessageSet.ADD_PRICE, user, 0.0, Double.parseDouble(money), null,null);
			dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_PRICE,false);
			map.put("success", true);
			map.put("info", "添加成功");
		}else{
			map.put("success", false);
			map.put("info", "出错了，该会员不存在");
		}
		return map;
	}
	@RequestMapping(value = "/jianpriceToUser")
	@ResponseBody
	public Map jianpriceToUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		String money = req.getParameter("money");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		LinkManList user=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList", MoreUserManager.getAppShopId(req));
		if(user!=null){
			user.setMoney(DoubleUtil.sub(user.getMoney(), Double.parseDouble(money)));
			dao.update(user);
			String content=dao.getMessageSetContent(users.getConpanyId(), MessageSet.REDUCE_PRICE, user, 0.0, Double.parseDouble(money), null,null);
			dao.getSendMessage(user.getLinkManPhone(),content, users.getConpanyId(), MessageSet.REDUCE_PRICE,false);
			map.put("success", true);
			map.put("info", "减少成功");
		}else{
			map.put("success", false);
			map.put("info", "出错了，该会员不存在");
		}
		return map;
	}
	public LinkManList getUser(String openid,HttpServletRequest req) {
		List<LinkManList> userlist = dao
				.queryUserModelByOpenid(openid == null ? "-1" : openid,MoreUserManager.getAppShopId(req));
		if (userlist.size() == 0) {
			return null;
		} else {
			return userlist.iterator().next();
		}
	}
}
