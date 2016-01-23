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
import com.dcl.blog.model.ConpanyAddress;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.WeiXinWebHtml;
import com.dcl.blog.model.WeixinWebSelect;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/weixin/function")
public class WeiXinFunctionController {
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
	@RequestMapping(value = "/saveWeixinWeb")
	@ResponseBody
	public Map saveWeixinWeb(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				long num=dao.queryWeixinWebHtml("",user.getConpanyId());
				Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
				if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new Date()))){
					if(num>=1){
						map.put("success", false);
						map.put("info","创建失败，免费用户只可以创建一中模板。付费用户可以创建6个模板");
						return map;
					}
				}
				if(num>=6){
					map.put("success", false);
					map.put("info","创建失败，只能创建6个模板。如需创建新模板请删除一个模板。");
				}else{
					String html=req.getParameter("html");
					html=html.replace("width=\"100%\">", "width=\"100%\"></img>");
					WeiXinWebHtml weixin=new WeiXinWebHtml();
					weixin.setConpanyId(user.getConpanyId());
					weixin.setConpanyName(conpany.getConpanyName());
					weixin.setCreateUserId(user.getId());
					weixin.setCreateUserName(user.getTrueName());
					weixin.setPrivates(true);
					weixin.setName(new Date()+""+user.getConpanyId()+user.getId());
					weixin.setHtmls(html);
					dao.add(weixin);
					map.put("success", true);
					map.put("info","创建成功");
				}
			}catch(Exception e){
				map.put("success", false);
				map.put("info","创建失败");
			}
		
		return map;
	}
	@RequestMapping(value = "/getWeixinWeb")
	@ResponseBody
	public Map getWeixinWeb(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				String nowpage = req.getParameter("nowpage");
				String countNum = req.getParameter("countNum");
				String name = req.getParameter("name");
				if (nowpage == null) {
					nowpage = "1";
				}
				if (countNum == null) {
					countNum = "30";
				}
				if(name==null){
					name="";
				}
				long num=dao.queryWeixinWebHtml(name,user.getConpanyId());
				List list=dao.queryWeixinWebHtml(name,user.getConpanyId(),Integer.parseInt(nowpage), Integer.parseInt(countNum));
				map.put("pagenum", num/Integer.parseInt(countNum)+1);
				map.put("success", true);
				map.put("data", list);
			}catch(Exception e){
				map.put("success", false);
				map.put("info","读取数据失败");
			}
		
		return map;
	}
	@RequestMapping(value = "/deleteWeixinWeb")
	@ResponseBody
	public Map deleteWeixinWeb(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id=req.getParameter("id");
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				WeiXinWebHtml weixinweb=(WeiXinWebHtml) dao.getObject(Long.parseLong(id), "WeiXinWebHtml",user.getConpanyId());
				if(weixinweb==null){
					map.put("success", false);
					map.put("info","删除失败,该模板不存在或已删除");
				}else{
					dao.delete(weixinweb);
					map.put("success", true);
					map.put("info","删除成功");
				}
			}catch(Exception e){
				map.put("success", false);
				map.put("info","删除失败");
			}
		
		return map;
	}
	@RequestMapping(value = "/useWeixinWeb")
	@ResponseBody
	public Map useWeixinWeb(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id=req.getParameter("id");
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				WeiXinWebHtml weixinweb=(WeiXinWebHtml) dao.getObject(Long.parseLong(id), "WeiXinWebHtml",user.getConpanyId());
				if(weixinweb==null){
					map.put("success", false);
					map.put("info","选择失败,该模板不存在或已删除");
				}else{
					WeixinWebSelect sele=null;
					List<Object> weixin=dao.getObjectList("WeixinWebSelect", "where conpanyId="+user.getConpanyId());
					if(weixin.size()<1){
						sele=new WeixinWebSelect();
						sele.setWeixinWebHtmlId(weixinweb.getId());
						sele.setConpanyId(user.getConpanyId());
						
						dao.add(sele);
					}else{
						sele=(WeixinWebSelect) weixin.get(0);
						sele.setWeixinWebHtmlId(weixinweb.getId());
						dao.update(sele);
					}

					map.put("success", true);
					map.put("info","选择成功");
				}
			}catch(Exception e){
				map.put("success", false);
				map.put("info","选择失败");
			}
		
		return map;
	}
	@RequestMapping(value = "/saveMap")
	@ResponseBody
	public Map saveMap(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			String x=req.getParameter("x");
			String y=req.getParameter("y");
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
				if(conpany.isPayConpany()&&conpany.getEndDate().after(new Date())){
					ConpanyAddress ca=new ConpanyAddress();
					ca.setConpanyId(user.getConpanyId());
					ca.setConpanyName(conpany.getConpanyName());
					ca.setMap_x(x);
					ca.setMap_y(y);
					ca.setPhone(conpany.getSoftAdminPhone());
					dao.add(ca);
					map.put("success", true);
					map.put("info", "地址添加成功");
				}else{
					List<ConpanyAddress> list=dao.getConpanyAddressByConpanyId(user.getConpanyId());
					if(list.size()>=1){
						map.put("success", false);
						map.put("info", "免费用户只可以设置一个店面的地理位置");
					}else{
						ConpanyAddress ca=new ConpanyAddress();
						ca.setConpanyId(user.getConpanyId());
						ca.setConpanyName(conpany.getConpanyName());
						ca.setMap_x(x);
						ca.setMap_y(y);
						ca.setPhone(conpany.getSoftAdminPhone());
						dao.add(ca);
						map.put("success", true);
						map.put("info", "地址添加成功");
					}
				}
			}catch (Exception e) {
				map.put("success", false);
				map.put("info","位置设置错误，是否未从地图中选择。");
			}
		return map;
	}
	@RequestMapping(value = "/getMaps")
	@ResponseBody
	public Map getMaps(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				List<ConpanyAddress> list=dao.getConpanyAddressByConpanyId(user.getConpanyId());
				map.put("success", true);
				map.put("data", list);
			}catch (Exception e) {
				map.put("success", false);
				map.put("info","查询已设置地理位置出错");
			}
		return map;
	}
	@RequestMapping(value = "/deleteMap")
	@ResponseBody
	public Map deleteMap(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
			String id=req.getParameter("id");
			try{
				ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
				ConpanyAddress ca=(ConpanyAddress) dao.getObject(Long.parseLong(id), "ConpanyAddress", user.getConpanyId());
				if(ca!=null){
					dao.delete(ca);
					map.put("success", true);
					map.put("info","删除已设置地理位置成功");
				}else{
					map.put("success", false);
					map.put("info","删除已设置地理位置出错");
				}
			}catch (Exception e) {
				// TODO: handle exception
				map.put("success", false);
				map.put("info","删除已设置地理位置出错");
			}
		return map;
	}
}
