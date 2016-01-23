package com.dcl.blog.controller.weixin;

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
import com.dcl.blog.model.VipTextMessage;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

@Controller
@RequestMapping(value = "/TextModelController")
public class TextController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	@RequestMapping(value = "/getText")
	@ResponseBody
	public Map getGonggao(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("type");
		if(type==null){
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "20";
			}
			List<Object> list=dao.getObjectListPage("VipTextMessage", "where conpanyId="+users.getConpanyId()+" and pass=0 order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("success", true);
			map.put("data", list);
		}else if(type.equals("pass")){
			String id=req.getParameter("id");
			List<Object> list=dao.getObjectList("VipTextMessage", "where id="+id+" and conpanyId="+users.getConpanyId()+" order by createDate desc");
			if(list.size()>0){
				VipTextMessage mes=(VipTextMessage) list.iterator().next();
				mes.setPass(1);
				dao.update(mes);
				map.put("success", true);
				map.put("info", "状态修改成功");
			}else{
				map.put("success", false);
				map.put("info", "该信息不存在");
			}
		}else if(type.equals("nopass")){
			String id=req.getParameter("id");
			List<Object> list=dao.getObjectList("VipTextMessage", "where id="+id+" and conpanyId="+users.getConpanyId()+" order by createDate desc");
			if(list.size()>0){
				VipTextMessage mes=(VipTextMessage) list.iterator().next();
				mes.setPass(2);
				dao.update(mes);
				map.put("success", true);
				map.put("info", "状态修改成功");
			}else{
				map.put("success", false);
				map.put("info", "该信息不存在");
			}
		}else if(type.equals("del")){
			String id=req.getParameter("id");
			List<Object> list=dao.getObjectList("VipTextMessage", "where id="+id+" and conpanyId="+users.getConpanyId()+" order by createDate desc");
			if(list.size()>0){
				dao.delete(list.iterator().next());
				map.put("success", true);
				map.put("info", "删除成功");
			}else{
				map.put("success", false);
				map.put("info", "您选择的公告不存在");
			}
		}
		return map;
	}
}
