package com.dcl.blog.controller.hr;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.MapInfo;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/hr/location")
public class LocationController {
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
	@RequestMapping(value = "/locationUp")
	@ResponseBody
	public Map IduploadImagePro(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyUser user = (ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		String longitude=request.getParameter("longitude");
		String latitude=request.getParameter("latitude");
		if(user!=null){
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(new Date());
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date satrDate = cal.getTime();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date endDate=cal.getTime();
			MapInfo map2=null;
			List<Object> objList=dao.getObjectListBySql("from MapInfo where conpanyId="+user.getConpanyId()+" and conpanyUserId="+user.getId()+" and createDate between '"+DateUtil.formatDateYYYY_MM_DD(satrDate)+"' and '"+DateUtil.formatDateYYYY_MM_DD(endDate)+"'");
			if(objList.size()>0){
				map2=(MapInfo) objList.iterator().next();
			}else{
				map2=new MapInfo();
				map2.setConpanyId(user.getConpanyId());
				map2.setConpanyUserId(user.getId());
				map2.setConpanyUserName(user.getUsername());
				map2.setCreateDate(satrDate);
				map2.setMapLocation("");
				dao.add(map2);
			}
			StringBuffer buf=new StringBuffer();
			if(map2.getMapLocation().equals("")){
				buf.append(longitude).append(",").append(latitude);
			}else{
				buf.append(map2.getMapLocation()).append(";").append(longitude).append(",").append(latitude);
			}
			map2.setMapLocation(buf.toString());
			dao.update(map2);
			map.put("success", true);
			map.put("info", "成功");
		}else{
			map.put("success", false);
			map.put("info", "没有登录");
		}
		return map;
	}
}
