package com.dcl.blog.controller.weiwifi;

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
import com.dcl.blog.model.Device;
import com.dcl.blog.model.VWiFi;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

@Controller
@RequestMapping(value = "/wifiController")
public class WeiWifiController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "vwifi/manager";
	}
	@RequestMapping(value = "/addDevice")
	@ResponseBody
	public Map addDevice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String name=req.getParameter("name");
		String tokens=req.getParameter("tokens");
		String open=req.getParameter("open");
		VWiFi vwifi=new VWiFi();
		vwifi.setName(name);
		vwifi.setTokens(tokens);
		vwifi.setUseUp(!Boolean.parseBoolean(open));
		vwifi.setConpanyId(users.getConpanyId());
		dao.add(vwifi);
		map.put("success", true);
		map.put("info", "添加完成");
		return map;
	}
	@RequestMapping(value = "/updateDevice")
	@ResponseBody
	public Map updateDevice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String name=req.getParameter("name");
		String tokens=req.getParameter("tokens");
		String open=req.getParameter("open");
		String id=req.getParameter("id");
		VWiFi vwifi=(VWiFi) dao.getObject(Long.parseLong(id), "VWiFi",users.getConpanyId());
		if(vwifi!=null){
			vwifi.setName(name);
			vwifi.setTokens(tokens);
			vwifi.setUseUp(!Boolean.parseBoolean(open));
			dao.update(vwifi);
			map.put("success", true);
			map.put("info", "修改完成");
		}else{
			map.put("success", false);
			map.put("info", "找不到该信息，修改失败");
		}
		return map;
	}
	@RequestMapping(value = "/getDeviceList")
	@ResponseBody
	public Map getDeviceList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		map.put("data", dao.getConpanyVWifi(users.getConpanyId()));
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/deleteDevice")
	@ResponseBody
	public Map deleteDevice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		VWiFi vwifi=(VWiFi) dao.getObject(Long.parseLong(id), "VWiFi",users.getConpanyId());
		if(vwifi!=null){
			dao.delete(vwifi);
			map.put("success", true);
			map.put("info", "删除完成");
		}else{
			map.put("success", false);
			map.put("info", "找不到该信息，删除失败");
		}
		return map;
	}
	
	@RequestMapping(value = "/getMainPageContent")
	@ResponseBody
	public Map getMainPageContent(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		return map;
	}
	@RequestMapping(value = "/setMainPageContent")
	@ResponseBody
	public Map setMainPageContent(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		
		return map;
	}
	@RequestMapping(value = "/lookLinkDevice")
	@ResponseBody
	public Map lookLinkDevice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("Device", "where conpanyId="+users.getConpanyId()+" and ap_id = '"+id+"'");
		List list=dao.getObjectListPage("Device", "where conpanyId="+users.getConpanyId()+" and ap_id = '"+id+"'order by endDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 禁止和允许 用户用网
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addMac")
	@ResponseBody
	public Map addMac(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id=req.getParameter("id");
		try{
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Device device=(Device) dao.getObject(Long.parseLong(id), "Device",users.getConpanyId());
		device.setNoLogin(!device.isNoLogin());
		dao.update(device);
		map.put("data", device.isNoLogin());
		map.put("success", true);
		}catch (Exception e) {
			map.put("info", "转换时出现错误");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 禁止和允许 用户通过wifi页面注册会员
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addWifiRigister")
	@ResponseBody
	public Map addWifiRigister(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		VWiFi vwifi=(VWiFi) dao.getObject(Long.parseLong(id), "VWiFi",users.getConpanyId());
		if(vwifi!=null){
			vwifi.setWebRigister(!vwifi.isWebRigister());
			dao.update(vwifi);
			map.put("data", vwifi.isWebRigister());
			map.put("success", true);
			map.put("info", "修改完成");
		}else{
			map.put("success", false);
			map.put("info", "找不到该信息，修改失败");
		}
		return map;
	}
}
