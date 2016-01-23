package com.dcl.blog.controller.filemanager;

import java.io.File;
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
import com.dcl.blog.model.ImageList;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/fileManager")
public class FileManagerController {
	private static final Logger logger = LoggerFactory
			.getLogger(FileManagerController.class);
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
	 *主页面
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/filemanager/Main";
	}
	/**
	 * 文件列表
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map getList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
			Map map=new HashMap<String, Object>();
			String nowpage = req.getParameter("nowpage");
			String countNum = req.getParameter("countNum");
			String type = req.getParameter("type");
			if("file".equals(type)){
				type="3";
			}
			if("img".equals(type)){
				type="1";
			}
			if("video".equals(type)){
				type="2";
			}
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "12";
			}
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			List<Object> list=dao.getObjectListPage("ImageList", "where conpanyId="+users.getConpanyId()+" and userId="+users.getId()+" and type='"+type+"' order by id desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("data", list);
			map.put("nowpage", nowpage);
			map.put("success", true);
			return map;
	}
	/**
	 * 文件列表
	 */
	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public Map deleteFile(Model model, HttpServletRequest req,
			HttpServletResponse res) {
			Map map=new HashMap<String, Object>();
			String id=req.getParameter("id");
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			ImageList img=(ImageList) dao.getObject(Long.parseLong(id), "ImageList");
			if(img==null){
				map.put("info", "没有此文件，删除错误");
				map.put("success", false);
			}else{
				if(img.getConpanyId()==users.getConpanyId()){
					dao.delete(img);
					File file=new File(img.getLinkaddress());
					file.delete();
					map.put("info", "删除成功");
					map.put("success", true);
				}else{
					map.put("info", "没有此文件，删除错误");
					map.put("success", false);
				}
			}
			return map;
	}
}
