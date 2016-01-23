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

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.WeixinOrder;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/weixin/order")
public class WeixinOrderController {
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
	@RequestMapping(value = "/look")
	@ResponseBody
	public Map look(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String num2 = req.getParameter("name");
		String song = req.getParameter("song");
		if(Boolean.parseBoolean(song)){
			song="is not null";
			
		}else{
			song="is null";
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		if(num2==null){
			num2="";
		}
		long num=dao.getObjectListNum("WeixinOrder", "where conpanyId="+users.getConpanyId()+" and num like '%"+num2+"%' and endDate "+song);
		List list=dao.getObjectListPage("WeixinOrder", "where conpanyId="+users.getConpanyId()+" and num like '%"+num2+"%'  and endDate "+song+" order by startdate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map delete(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String num = req.getParameter("num");
		return map;
	}
	@RequestMapping(value = "/enter")
	@ResponseBody
	public Map update(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			String id = req.getParameter("id");
			WeixinOrder order=(WeixinOrder) dao.getObject(Long.parseLong(id), "WeixinOrder",users.getConpanyId());
			order.setEndDate(new Date());
			dao.update(order);
			map.put("success", true);
			map.put("info", "发货成功");
		}catch (Exception e) {
			// TODO: handle exception
			map.put("success", false);
			map.put("info", "发货失败");
		}
		return map;
	}
}
