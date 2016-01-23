package com.dcl.blog.util.moreuser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

public class MoreUserManager {
	public static long getAppShopId(HttpServletRequest req){
		String appShopId=null;
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if(users!=null){
			appShopId=String.valueOf(users.getConpanyId());
		}else{
			appShopId=req.getParameter("conpanyId");
		}
		if(appShopId==null||appShopId.equals("undefined")){
			try{
				if(req.getSession().getAttribute("conpanyId")==null){
						users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
						if(users!=null){
							appShopId=String.valueOf(users.getConpanyId());
						}else{
							
						}
				}else{
					
					
						appShopId=String.valueOf((Long) req.getSession().getAttribute("conpanyId"));
				}
			}catch (Exception e) {
				appShopId=null;
			}
		}
		long id;
		try{
			id=Long.parseLong(appShopId);
			req.getSession().setAttribute("conpanyId", id);
		}catch(Exception exc){
			id=404;
		}
		return id;
	}
	public static UserTable getUserObject(HttpServletRequest req,DaoService dao){
		String appShopId=req.getParameter("openid");
		UserTable ut=null;
		if(appShopId==null)
		{
			Object obj=req.getSession().getAttribute("user_object");
			if(obj!=null){
				ut=(UserTable) obj;
			}
		}else{
			List<Object> objlist=dao.getObjectList("LinkManList", "where openid='"+appShopId+"' and conpanyId="+getAppShopId(req));
			if(objlist.size()>0){
				LinkManList lml=(LinkManList) objlist.get(0);
				ut=(UserTable) dao.getObject(lml.getUserTableId(), "UserTable");
				req.getSession().setAttribute("user_object",ut);
			}
		}
		return ut;
	}
	public static void setUserTable(UserTable ut,HttpServletRequest req){
		req.getSession().setAttribute("user_object",ut);
	}
	public static void setLinkManList(LinkManList ut,HttpServletRequest req){
		req.getSession().setAttribute("LinkMan",ut);
	}
	public static LinkManList getLinkManList(HttpServletRequest req,DaoService dao){
		String appShopId=req.getParameter("openid");
		LinkManList ut=null;
		if(appShopId==null){
			Object obj=req.getSession().getAttribute("LinkMan");
			if(obj!=null){
				ut=(LinkManList) obj;
			}
		}else{
			List<Object> objlist=dao.getObjectList("LinkManList", "where openid='"+appShopId+"' and conpanyId="+getAppShopId(req));
			if(objlist.size()>0){
				ut=(LinkManList) objlist.get(0);
				req.getSession().setAttribute("LinkMan",ut);
			}else{
				req.getSession().setAttribute("openid",appShopId);
			}
		}
		
		return ut;
	}
	public static String getOpenId(HttpServletRequest req,DaoService dao){
		String openid=(String) req.getSession().getAttribute("openid");
		return openid;
	}
	public static LinkManList getLinkManList(HttpServletRequest req,DaoService dao,boolean reflash){
		String appShopId=req.getParameter("openid");
		LinkManList ut=null;
		if(appShopId==null){
			Object obj=req.getSession().getAttribute("LinkMan");
			if(obj!=null){
				ut=(LinkManList) obj;
				if(reflash){
				List<Object> objlist=dao.getObjectList("LinkManList", "where openid='"+ut.getOpenid()+"' and conpanyId="+getAppShopId(req));
					if(objlist.size()>0){
						ut=(LinkManList) objlist.get(0);
						if(ut.getOpenid()==null||ut.getOpenid().equals("")){
							ut.setOpenid(appShopId);
							dao.update(ut);
						}
						req.getSession().setAttribute("LinkMan",ut);
					}
				}
			}
		}else{
			List<Object> objlist=dao.getObjectList("LinkManList", "where openid='"+appShopId+"' and conpanyId="+getAppShopId(req));
			if(objlist.size()>0){
				ut=(LinkManList) objlist.get(0);
				if(ut.getOpenid()==null||ut.getOpenid().equals("")){
					ut.setOpenid(appShopId);
					dao.update(ut);
				}
				req.getSession().setAttribute("LinkMan",ut);
			}
		}
		
		return ut;
	}
}
