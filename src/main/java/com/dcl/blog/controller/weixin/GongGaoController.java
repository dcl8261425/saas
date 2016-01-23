package com.dcl.blog.controller.weixin;

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
import com.dcl.blog.model.VipGonggao;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

@Controller
@RequestMapping(value = "/gonggaoController")
public class GongGaoController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	@RequestMapping(value = "/getGonggao")
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
			List<Object> list=dao.getObjectListPage("VipGonggao", "where conpanyId="+users.getConpanyId()+" order by sendDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("success", true);
			map.put("data", list);
		}else if(type.equals("add")){
			String message=req.getParameter("message");
			String imageLink=req.getParameter("imageLink");
			String videoLink=req.getParameter("videoLink");
			VipGonggao gonggao=new VipGonggao();
			gonggao.setConpanyId(users.getConpanyId());
			gonggao.setGonggaoContent(message);
			gonggao.setImageLink(imageLink);
			gonggao.setSendDate(new Date());
			gonggao.setVideoLink(videoLink);
			dao.add(gonggao);
			map.put("success", true);
			map.put("info", "添加成功");
		}else if(type.equals("del")){
			String id=req.getParameter("id");
			List<Object> list=dao.getObjectList("VipGonggao", "where id="+id+" and conpanyId="+users.getConpanyId()+" order by sendDate desc");
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
