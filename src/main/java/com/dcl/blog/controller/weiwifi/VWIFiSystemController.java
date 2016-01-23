package com.dcl.blog.controller.weiwifi;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ChanceList;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.Device;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.model.UserTableLinkLinkMan;
import com.dcl.blog.model.VWiFi;
import com.dcl.blog.model.VipGonggao;
import com.dcl.blog.model.WeiXin;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.MessageClient;
import com.dcl.blog.util.RandomNum;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping(value = "/wifidogController")
public class VWIFiSystemController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}

	@RequestMapping(value = "/phoneTest")
	@ResponseBody
	public Map phoneTest(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try {
			String phone = req.getParameter("phone");
			String conpanyId = req.getParameter("conpanyId");
			List<Object> list = dao.getObjectList("UserTable", "where phone='"
					+ phone + "'");
			Conpany conpany = (Conpany) dao.getObject(
					Long.parseLong(conpanyId), "Conpany");
			List<Object> objlist = dao.getObjectList("LinkManList",
					"where linkManPhone='" + phone + "' and conpanyId="
							+ conpanyId);
			if (objlist.size() > 0) {
				map.put("success", false);
				map.put("info", "此手机号已经是会员，请登录即可。");
				return map;
			} else {
				String suiji = RandomNum.getSuiji(6);
				req.getSession().setAttribute("testCode", suiji);
				MessageClient mc = new MessageClient();
				mc.sendSMSInfo(phone, conpany.getConpanyName() + ":wifi验证码---"
						+ suiji + ",由青柠聚商提供，您的店铺也可以注册哦.www.xt12306.com." + "【"
						+ conpany.getConpanyName() + "】");
				map.put("success", true);
				map.put("info", "请等待您的短信验证码");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("success", false);
			map.put("info", "出问题了");
		}
		return map;
	}

	@RequestMapping(value = "/wifiVip", method = RequestMethod.GET)
	public String rigister(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId = (String) req.getSession().getAttribute("conpanyId");
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		Object b = req.getAttribute("login");
		if (b != null) {
			boolean login = (Boolean) b;
			if (login) {
				
				model.addAttribute("conpanyId", conpanyId);
				String openid=MoreUserManager.getOpenId(req, dao);
				if(openid==null||openid.equals("")){
					model.addAttribute("rigister", false);
				}else{
					model.addAttribute("rigister", true);
				}
			}
			model.addAttribute("login", login);
		
		} else {
			model.addAttribute("login", false);
		}
		if (lm != null) {
			if (lm.getVipidNum() == 0) {
				lm.setVipLevel("目前积分数无法达到第一个等级");
				lm.setVipMarks("目前积分数无法达到第一个等级");
			}
			model.addAttribute("success", true);
			model.addAttribute("linkman", lm);
			
		} else {
			model.addAttribute("success", false);
		}
		String mac = (String) req.getSession().getAttribute("mac");
		model.addAttribute("guanggao","http://h5server.ejamad.com/h5/js/append.js?cooid=fceffcejjjdjlkhl&amp;uid="+mac+"&amp;ver=1");
		if("mei".equals(conpany.getGuanggao())){
			model.addAttribute("isguanggao",false);
		}else{
			model.addAttribute("isguanggao",true);
		}
		return "vwifi/wifiVip";
	}

	@RequestMapping(value = "/ping")
	public void ping(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		String sys_uptime = req.getParameter("sys_uptime");
		String sys_memfree = req.getParameter("sys_memfree");
		String conpanyId = req.getParameter("conpanyId");
		String tokens = req.getParameter("tokens");
		String sys_load = req.getParameter("sys_load");
		String wifidog_uptime = req.getParameter("wifidog_uptime");
		VWiFi wifi = dao.getVWifi(Long.parseLong(conpanyId), tokens);
		// 判断是否开启
		if (wifi != null && !wifi.isUseUp()) {
			// 开启后记录当前硬件信息
			wifi.setSys_load(Float.parseFloat(sys_load));
			wifi.setSys_memfree(Integer.parseInt(sys_memfree));
			wifi.setSys_uptime(Integer.parseInt(sys_uptime));
			wifi.setWifidog_uptime(Integer.parseInt(wifidog_uptime));
			dao.update(wifi);
			ServletOutputStream os;
			try {
				os = res.getOutputStream();
				os.write(getPongStr(tokens, Long.parseLong(conpanyId))
						.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			ServletOutputStream os;
			try {
				os = res.getOutputStream();
				os.write("该路由器设备没有开启。".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/auth")
	public void auth(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		// 该接口在用户认证后每隔一段时间调用一次
		// 在此处可以通过mac检测是否不让上网了
		String incoming = req.getParameter("incoming");
		String token = req.getParameter("token");
		String mac = req.getParameter("mac");
		String ip = req.getParameter("ip");
		String outgoing = req.getParameter("outgoing");
		String conpanyId = req.getParameter("conpanyId");
		String tokens = req.getParameter("tokens");
		String stage = req.getParameter("stage");
		if (stage.equals("conters")) {
			try {
				res.getOutputStream().write("Auth: 1".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String phone = ((Map<String, String>) req.getSession()
					.getServletContext().getAttribute("phone")).get(mac);
			VWiFi wifi = dao.getVWifi(Long.parseLong(conpanyId), tokens);

			if (wifi != null && !wifi.isUseUp()) {
				List<Object> linkmain = dao.getObjectList("LinkManList",
						"where linkManPhone='" + phone + "' and conpanyId="
								+ conpanyId);
				if (linkmain.size() > 0) {
					LinkManList linkmainobj = (LinkManList) linkmain.iterator()
							.next();
					Device device = dao.getDevice(Long.parseLong(conpanyId),
							tokens, mac);
					if (device == null) {
						device = new Device();
						device.setAp_id(wifi.getId());
						device.setConpanyId(Long.parseLong(conpanyId));
						device.setIncoming(Integer.parseInt(incoming) / 1024 / 1024);
						device.setLinkmainName(linkmainobj.getLinkManName());
						device.setIp(ip);
						device.setMac(mac);
						device.setOutgoing(Integer.parseInt(outgoing) / 1024 / 1024);
						device.setToken(token);
						device.setTokens(tokens);
						device.setNoLogin(false);
						device.setEndDate(new Date());
						device.setCountNum(1);
						dao.add(device);
					} else {
						device.setOutgoing(Integer.parseInt(outgoing) / 1024 / 1024);
						device.setIncoming(Integer.parseInt(incoming) / 1024 / 1024);
						device.setIp(ip);
						device.setToken(token);
						if (!device.getToken().equals(token)) {
							device.setCountNum(device.getCountNum() + 1);
						}
						device.setEndDate(new Date());
						dao.update(device);
					}
					if (!device.isNoLogin()) {
						try {
							res.getOutputStream().write("Auth: 1".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try {
							res.getOutputStream().write("Auth: 0".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					try {
						res.getOutputStream().write("Auth: 0".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				try {
					res.getOutputStream().write("Auth: 0".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return;
	}

	@RequestMapping(value = "/portal")
	public void portal(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		// 登录成功或返回的内容，以及跳转的页面
		String conpanyId = req.getParameter("conpanyId");
		String tokens = req.getParameter("tokens");
		req.getSession().setAttribute("conpanyId", conpanyId);
		try {
			LinkManList lm1 = MoreUserManager.getLinkManList(req, dao);
			UserTable ut1 = MoreUserManager.getUserObject(req, dao);
			if (lm1 == null && ut1 == null) {
				if (conpanyId != null) {
					
					req.setAttribute("conpanyId", conpanyId);
					String openid=MoreUserManager.getOpenId(req, dao);
					if(openid!=null&&lm1!=null&&!openid.equals(lm1.getOpenid())){
						lm1.setOpenid(openid);
						dao.update(lm1);
					}
					String phone = req.getParameter("phone");
					String password = req.getParameter("password");
					if (phone != null && !"".equals(phone)) {
						List<Object> linkmain = dao.getObjectList(
								"LinkManList", "where linkManPhone='" + phone
										+ "' and conpanyId=" + conpanyId);
						long token = System.currentTimeMillis();
						if (linkmain.size() > 0) {
							List<Object> list = dao.getObjectList("UserTable",
									"where phone='" + phone.trim() + "'");
							LinkManList lk = (LinkManList) linkmain.iterator()
									.next();
							UserTable ut = (UserTable) list.iterator().next();
							if (ut.getPassword().equals(password)) {
								MoreUserManager.setLinkManList(lk, req);
								MoreUserManager.setUserTable(ut, req);
								req.setAttribute("login", false);
								req.getRequestDispatcher(
										"/wifidogController/wifiVip").forward(
										req, res);

							} else {
								req.setAttribute("login", true);
								req.getRequestDispatcher(
										"/wifidogController/wifiVip").forward(
										req, res);
								req.setAttribute("message", "密码错误");
							}
						} else {
							req.setAttribute("login", true);
							req.getRequestDispatcher(
									"/wifidogController/wifiVip").forward(req,
									res);
							req.setAttribute("message", "不是会员");
						}
					}else{
						req.setAttribute("login", true);
					}
				} else {
					req.setAttribute("login", false);
				}
			} else {
				req.setAttribute("login", false);
			}
			
			req.getRequestDispatcher("/wifidogController/wifiVip").forward(req,
					res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/login")
	public String login(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		// 第一次打开浏览器时跳出的登录页面
		String gw_address = req.getParameter("gw_address");
		String gw_port = req.getParameter("gw_port");
		String gw_id = req.getParameter("gw_id");
		String conpanyId = req.getParameter("conpanyId");
		String tokens = req.getParameter("tokens");
		String mac = req.getParameter("mac");
		String url = req.getParameter("url");
		VWiFi wifi = dao.getVWifi(Long.parseLong(conpanyId), tokens);
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		List<Object> list=dao.getObjectListPage("VipGonggao", "where conpanyId="+conpany.getId(),1,1);
		if(list.size()>0){
			VipGonggao vg=(VipGonggao) list.iterator().next();
			model.addAttribute("gonggao", vg);
		}else{
			model.addAttribute("gonggao", null);
		}
		/*List<Object> linkMan=dao.getObjectList("LinkManList", "where mac='"+mac+"' and conpanyId="+conpanyId);
		if(linkMan.size()>0){
			LinkManList lk=(LinkManList) linkMan.iterator().next();
			UserTable ut=(UserTable) dao.getObject(lk.getUserTableId(), "UserTable");
			MoreUserManager.setLinkManList(lk, req);
			MoreUserManager.setUserTable(ut, req);
			model.addAttribute("macIsLogin",true);
			long token = System.currentTimeMillis();
			String redirectUrl = "http://" + gw_address + ":" + gw_port
					+ "/wifidog/auth?" + "token=" + token + "&url=" + url;
			
			model.addAttribute("link", redirectUrl);
			model.addAttribute("info", "登陆成功等待跳转，跳转完毕后在关闭此页面.");
		}else{
			model.addAttribute("macIsLogin",false);
		}*/
		model.addAttribute("macIsLogin",false);
		req.getSession().setAttribute("mac", mac);
		model.addAttribute("guanggao","http://h5server.ejamad.com/h5/js/append.js?cooid=fceffcejjjdjlkhl&amp;uid="+mac+"&amp;ver=1");
		if("mei".equals(conpany.getGuanggao())){
			model.addAttribute("isguanggao",false);
		}else{
			model.addAttribute("isguanggao",true);
		}
				
		model.addAttribute("gw_address", gw_address);
		model.addAttribute("gw_port", gw_port);
		model.addAttribute("gw_id", gw_id);
		model.addAttribute("conpanyId", conpanyId);
		model.addAttribute("tokens", tokens);
		model.addAttribute("mac", mac);
		model.addAttribute("url", url);
		model.addAttribute("iswebrigister", wifi.isWebRigister());
		req.getSession().setAttribute("conpanyId", conpanyId);
		return "vwifi/VWifiLogin";
	}

	@RequestMapping(value = "/gw_message")
	public String gw_message(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		// 第一次打开浏览器时跳出的登录页面
		return "vwifi/VWifiLogin";
	}

	@RequestMapping(value = "/loginValidate")
	@ResponseBody
	public Map loginValidate(Model model, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		// 登录验证
		Map retMap = new HashMap<String, Object>();
		String gw_address = req.getParameter("gw_address");
		String gw_port = req.getParameter("gw_port");
		String gw_id = req.getParameter("gw_id");
		String conpanyId = req.getParameter("conpanyId");
		String tokens = req.getParameter("tokens");
		String mac = req.getParameter("mac");
		String url = req.getParameter("url");
		String phone = req.getParameter("phone");
		String password = req.getParameter("password");
		req.getSession().setAttribute("conpanyId", conpanyId);
		List<Object> linkmain = dao
				.getObjectList("LinkManList", "where linkManPhone='" + phone
						+ "' and conpanyId=" + conpanyId);
		long token = System.currentTimeMillis();
		if (linkmain.size() > 0) {
			Map<String, String> map = (Map<String, String>) req.getSession()
					.getServletContext().getAttribute("phone");
			if (map == null) {
				map = new HashMap<String, String>();
				req.getSession().getServletContext().setAttribute("phone", map);
			}
			List<Object> list = dao.getObjectList("UserTable", "where phone='"
					+ phone.trim() + "'");

			LinkManList lk = (LinkManList) linkmain.iterator().next();
			UserTable ut = (UserTable) list.iterator().next();
			if (ut.getPassword().equals(password)) {
				List<Object> linkMan=dao.getObjectList("LinkManList", "where mac='"+mac+"' and conpanyId="+conpanyId);
				if(linkMan.size()>0){
					LinkManList lml=(LinkManList) linkMan.iterator().next();
					lml.setMac("");
					dao.update(lml);
				}
				lk.setMac(mac);
				dao.update(lk);
				MoreUserManager.setLinkManList(lk, req);
				MoreUserManager.setUserTable(ut, req);
				map.put(mac, phone);
				String redirectUrl = "http://" + gw_address + ":" + gw_port
						+ "/wifidog/auth?" + "token=" + token + "&url=" + url;
				retMap.put("success", true);
				retMap.put("link", redirectUrl);
				retMap.put("info", "登陆成功等待跳转，跳转完毕后在关闭此页面.");
				return retMap;
			} else {
				retMap.put("info", "密码错误！");
				retMap.put("success", false);
				return retMap;
			}
		} else {

			retMap.put("success", false);
			retMap.put("info", "您不是会员！");
			return retMap;
		}
	}

	public String getPongStr(String tokens, long conpanyId) {

		String pongStr = "Pong";
		/**
		 * 返回Pong 格式为: Pong+空格+result=json字符串
		 */
		return pongStr;
	}

	@RequestMapping(value = "/rigisterWifiVip")
	@ResponseBody
	public Map rigisterHuiyuan(Model model, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		Map retMap = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId = req.getParameter("conpanyId");
		String name = req.getParameter("name");
		String gw_address = req.getParameter("gw_address");
		String gw_port = req.getParameter("gw_port");
		String gw_id = req.getParameter("gw_id");
		String tokens = req.getParameter("tokens");
		String mac = req.getParameter("mac");
		String url = req.getParameter("url");
		String phone = req.getParameter("phone");
		String testCode = req.getParameter("testCode");
		req.getSession().setAttribute("conpanyId", conpanyId);
		if (!req.getSession().getAttribute("testCode").equals(testCode)) {
			retMap.put("success", false);
			retMap.put("info", "验证码错误");
			return retMap;
		} else {
			retMap.put("success", false);
			retMap.put("info", "验证码正确");
		}
		List<Object> list = dao.getObjectList("UserTable", "where phone='"
				+ phone + "'");

		Conpany conpany = (Conpany) dao.getObject(Long.parseLong(conpanyId),
				"Conpany");
		long num = dao.getObjectListNum("LinkManList", "where conpanyId="
				+ conpany.getId());
		/*
		 * if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new
		 * Date()))){ if(num>=100){
		 * res.getOutputStream().write("此店铺服务器费已到期，无法提供联网服务".getBytes()); return
		 * ; } }
		 */
		if (lm == null) {
			List<Object> objlist = dao.getObjectList("LinkManList",
					"where linkManPhone='" + phone + "' and conpanyId="
							+ conpanyId);

			if (objlist.size() > 0) {
				retMap.put("success", false);
				retMap.put("info", "已是会员请直接登陆");
				return retMap;
			}
		}
		if (lm == null) {
			if (list.size() > 0) {
				ut = (UserTable) list.get(0);
			} else {
				ut = new UserTable();
				ut.setPassword("0000");
				ut.setPhone(phone);
				ut.setTrueName(name);
				dao.add(ut);

			}
			ChanceList cl = new ChanceList();
			cl.setConpanyId(Long.parseLong(conpanyId));
			cl.setCreateDate(new Date());
			cl.setCreateManId(0);
			cl.setCreateManMark("来wifi端");
			cl.setCreayeManName("来wifi端");
			cl.setCustomerAddress("");
			cl.setCustomerLevel(1);
			cl.setCustomerMark("来wifi端");
			cl.setCustomerName(name);
			cl.setCustomerType("来wifi端");
			cl.setLastBuy(null);
			cl.setNotesUserId(0);
			cl.setNotesUserName(null);
			cl.setState(1);
			dao.add(cl);
			lm = new LinkManList();
			lm.setAddUserId(0);
			lm.setAddUserName("用户自来来wifi端");
			lm.setChanceListId(cl.getId());
			lm.setConpanyId(cl.getConpanyId());
			lm.setLinkManBirthday(null);
			lm.setLinkManJob("");
			lm.setLinkManMark("来wifi端");
			lm.setLinkManMaxScore(0);
			lm.setLinkManName(name);
			lm.setLinkManPhone(phone);
			lm.setChanceListName(cl.getCreayeManName());
			lm.setLinkManScore(0);
			lm.setLinkManSex("未知");
			lm.setOpenid(MoreUserManager.getOpenId(req, dao));
			lm.setUserTableId(ut.getId());
			lm.setVipidNum(0);
			String openid=MoreUserManager.getOpenId(req, dao);
			lm.setOpenid(openid);
			List<Object> linkMan=dao.getObjectList("LinkManList", "where mac='"+mac+"' and conpanyId="+conpanyId);
			if(linkMan.size()>0){
				LinkManList lml=(LinkManList) linkMan.iterator().next();
				lml.setMac("");
				dao.update(lml);
			}
			lm.setMac(mac);
			dao.add(lm);
			UserTableLinkLinkMan ull = new UserTableLinkLinkMan();
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
			Map<String, String> map = (Map<String, String>) req.getSession()
					.getServletContext().getAttribute("phone");
			if (map == null) {
				map = new HashMap<String, String>();
				req.getSession().getServletContext().setAttribute("phone", map);
			}
			map.put(mac, phone);
			long token = System.currentTimeMillis();
			String redirectUrl = "http://" + gw_address + ":" + gw_port
					+ "/wifidog/auth?" + "token=" + token + "&url=" + url;
			retMap.put("success", true);
			retMap.put("info", "注册成功等待跳转，跳转完毕后在关闭此页面.");
			retMap.put("link", redirectUrl);
			return retMap;
		} else {
			retMap.put("success", false);
			retMap.put("info", "已是会员请直接登陆");
			return retMap;
		}

	}
}
