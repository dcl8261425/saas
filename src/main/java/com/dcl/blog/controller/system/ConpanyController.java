package com.dcl.blog.controller.system;

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
import com.dcl.blog.model.Hangye;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.EmailUtil;
import com.dcl.blog.util.MessageClient;
import com.dcl.blog.util.RandomNum;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.StringUtil;
import com.dcl.blog.util.email.emailimpl;
/**
 * 公司的管理controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/conpany")
public class ConpanyController {
	private static final Logger logger = LoggerFactory
			.getLogger(ConpanyController.class);
	private DaoService dao;
	public static List<Map> data;
	private emailimpl email;
	@Resource
	public void setEmail(emailimpl email) {
		this.email = email;
	}
	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	@RequestMapping(value = "/createConpany")
	@ResponseBody
	public Map createConpany(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		//req.getSession().getAttribute(SessionString.SEESION_CODE);
		Map map = new HashMap<String, Object>();
		try{
			String province=req.getParameter("province");
			String city=req.getParameter("city");
			String district=req.getParameter("district");
			String conpanyName=req.getParameter("conpanyName");
			String softAdminName=req.getParameter("softAdminName");
			String softAdminPhone=req.getParameter("softAdminPhone");
			String conpanyType=req.getParameter("conpanyType");
			String conpanyType_id=req.getParameter("conpanyType_id");
			String conpanyAdminEmail=req.getParameter("conpanyAdminEmail");
			String conpanyPinYin=req.getParameter("conpanyPinYin");
			if(!StringUtil.isEmail(conpanyAdminEmail)){
				map.put("info", "email错误，请重新填写。");
				map.put("success", false);
				return map;
			}
			if(!StringUtil.isphone(softAdminPhone)){
				map.put("info", "手机错误请重新填写");
				map.put("success", false);
				return map;
			}
			if(StringUtil.isStringIsNull(softAdminName)){
				map.put("info", "管理员名称错误请重新填写");
				map.put("success", false);
				return map;
			}
			Hangye hg=StringUtil.isHangyeIsNull(conpanyType_id,req,dao);
			if(StringUtil.isHangyeIsNull(conpanyType_id,req,dao)==null){
				map.put("info", "行业类别错误请从新填写");
				map.put("success", false);
				return map;
			}
			if(StringUtil.isStringIsNull(conpanyName)){
				map.put("info", "公司名称错误请重新填写");
				map.put("success", false);
				return map;
			}
			if(StringUtil.isStringIsNull(district)||district.equals("市、县级市")){
				map.put("info", "市，县级市名称错误请重新填写");
				map.put("success", false);
				return map;
			}
			if(StringUtil.isStringIsNull(city)||city.equals("地级市")){
				map.put("info", "地级市名称错误请重新填写");
				map.put("success", false);
				return map;
			}
			if(StringUtil.isStringIsNull(province)||province.equals("省份")){
				map.put("info", "省份名称错误请重新填写");
				map.put("success", false);
				return map;
			}
			Conpany con=new Conpany();
			con.setConpanyAdminEmail(conpanyAdminEmail);
			con.setConpanyName(conpanyName);
			con.setConpanyRigister(new Date());
			con.setConpanyType(hg.getHangyeName());
			con.setNowUserNum(1);
			con.setSoftAdminName(softAdminName);
			con.setSoftAdminPhone(softAdminPhone);
			con.setHangyeName(hg.getHangyeName());
			con.setHangyeId(hg.getId());
			con.setUseConpany(false);
			con.setPayConpany(false);
			con.setDistrict(district);
			con.setCity(city);
			con.setProvince(province);
			con.setCountry("中国");
			String path = req.getContextPath();
			try{
			dao.add(con);
			//email.sendInfoDateEmail(conpanyAdminEmail, EmailUtil.rigisterEmail(req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/jihuo?jihuoid="+con.getId()));
			map.put("id", con.getId());
			map.put("url", req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/jihuo?jihuoid="+con.getId());
			map.put("success", true);
			}catch (Exception e) {
				// TODO: handle exception
				map.put("info", "出错啦，您的邮件是否填写正确？错误的话是不能注册的哟。");
				map.put("success", false);
				dao.delete(con);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	@RequestMapping(value = "/getTest")
	@ResponseBody
	public Map getTest(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		//req.getSession().getAttribute(SessionString.SEESION_CODE);
		Map map = new HashMap<String, Object>();
		String phone = req.getParameter("phone");
		MessageClient mc = new MessageClient();
		String suiji = RandomNum.getSuiji(6);
		req.getSession().setAttribute(SessionString.SEESION_CODE, suiji+"");
		try {
			mc.sendSMSInfo(phone, "验证码:"+ suiji + "由青柠聚商提供.帮您提供一整套wifi,app,微信,解决方案【青柠网络】");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("success", true);
		map.put("info", "请等待您的短信验证码");
		return map;
	}
}
