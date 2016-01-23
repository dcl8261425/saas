package com.dcl.blog.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

public class PermissionFilter extends OncePerRequestFilter {
	private DaoService dao = null;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			if (dao == null) {
				dao = wac.getBean(DaoService.class);
				HttpSession se = request.getSession();
			}
			// 不需要权限的url
			String[] codeurl = {"/ajaxchangePassword","/changePassword","/weixin/renzheng","/getHangye","/getHangye","/map","/gameController/getguaguakaRun","/gameController/getDaZhuanPanUser","/weixin/renzheng","/init", "/exit", "/loginAjax",
					"/rigisterAdminOfConpany", "/", "/Group/getGroupInfo",
					"/conpany/createConpany","/conpany/getTest", "/fileSrc/getOrgImg", "/fileSrc/uploadImage_kongjian", "/fileSrc/uploadVideo", "/fileSrc/uploadFile",
					"/fileSrc/uploadImage","/fileSrc/codeImage", "/jihuo","/fileSrc/getImageList",
					"/login", "/rigister", "/productintroduction",
					"/customCase", "/priceInf", "/itservice", "/error","/hr/location/locationUp" };
			String fromurl = request.getHeader("referer");
			String toUrl = request.getServletPath();
			String hostUrl = request.getScheme() + "://"
					+ request.getServerName();
			String path = request.getContextPath();
			if(toUrl.indexOf("/VipAppController")!=-1){
				filterChain.doFilter(request, response);
				return;
			}
			if(toUrl.indexOf("/weixin/public")!=-1){
				filterChain.doFilter(request, response);
				return;
			}
			if(toUrl.indexOf("/wifidogController")!=-1){
				filterChain.doFilter(request, response);
				return;
			}
			if (toUrl.indexOf("/img/") != -1 || toUrl.indexOf("/js/") != -1
					|| toUrl.indexOf("/css/") != -1) {
				filterChain.doFilter(request, response);
				return;
			}
			if(toUrl.indexOf("/game/") != -1){
				if (toUrl.indexOf(".html")==-1) {
					filterChain.doFilter(request, response);
					return;
				}
				String gameId=request.getParameter("gameId");
				request.getSession().setAttribute("gameId", Long.parseLong(gameId));
				filterChain.doFilter(request, response);
				return;
			}
			boolean isTestcode = false;
			for (int i = 0; i < codeurl.length; i++) {
				if (toUrl.equals(codeurl[i])) {
					isTestcode = true;
				}
			}
			if (isTestcode) {
				filterChain.doFilter(request, response);
				return;
			}
			// 除了以上url不用登录，剩下的url都需要登录后再操作，也就是需要权限了。全系统任何一个url的访问都需要权限。
			ConpanyUser user = (ConpanyUser) request.getSession().getAttribute(
					SessionString.USER_OBJ);
			if (user == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", false);
				map.put("info", "出错了，错误信息是:您没有登录，或登录超时，请登陆后在访问，<a href=\""
						+ hostUrl + path + "/login\">登录</a>");
				map.put("stute", 1);
				request.setAttribute("map", map);
				String app=request.getParameter("app");
				
				// 判断是否是ajax请求，是的话就提示框，不是的话则跳转
				if (isAjaxRequest(request)||app!=null) {
					request.getRequestDispatcher("/error").forward(request,
							response);// 这是内部跳转
					return;
				} else {
					request.getRequestDispatcher("/login").forward(request,
							response);// 这是内部跳转
					return;
				}
			} else {
				if(toUrl.indexOf("/hr/page/fileManager")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				if(toUrl.indexOf("/hr/function/getHuDongKongJian")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				if(toUrl.indexOf("/fileManager")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				if(toUrl.indexOf("/hr/function/sendKongJianManager")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				if(toUrl.indexOf("/hr/function/resendKongJianManager")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				if(toUrl.indexOf("/hr/function/zan")!=-1){
					filterChain.doFilter(request, response);
					return;
				}
				String groupId = request.getParameter("groupId");
				long groupidLong = 0;
				if (groupId == null || groupId.equals("0")) {
					// 这里如果出现违规访问的话则从这里处理，如在管理权限以及管理组方面出现没有传递组id
					/*
					 * if(toUrl.indexOf("/Group/")!=-1||toUrl.indexOf("/Permission/"
					 * )!=-1||toUrl.indexOf("/RolePermission/")!=-1){
					 * //组管理，权限管理，角色管理 需要传送 组id如果没有则判断为非法访问 Map<String, Object>
					 * map=new HashMap<String, Object>(); map.put("info",
					 * "出错了，非法访问"); map.put("success", false);
					 * request.setAttribute("map", map);
					 * request.getRequestDispatcher("/error") .forward(request,
					 * response);//这是内部跳转 return ; }
					 */
					//获取所有组
					List<SoftPermission> listsoft=(List<SoftPermission>) request.getSession().getAttribute("SoftPermissionNogroupId");
					boolean ispass=false;
					if(listsoft==null){
						listsoft=new ArrayList<SoftPermission>();
						List listconpObj = dao.getUserOfGroups(user.getId());
						Iterator i = listconpObj.iterator();
						//迭代，查看在所有组内是否有此权限
						while (i.hasNext()) {
							ConpanyGroup cg = (ConpanyGroup) i.next();
							groupidLong = cg.getId();
							List<SoftPermission> list = dao
									.getSoftPermissionByConpanyUser(user,
											groupidLong);
							
							for (int ii=0;ii<list.size();ii++) {
								SoftPermission sftp=list.get(ii);
								if (sftp.getUrl().equals(toUrl)) {
									ispass=true;
								}
							}
							listsoft.addAll(list);
						}
						 request.getSession().setAttribute("SoftPermissionNogroupId",listsoft);
						if(ispass){
							filterChain.doFilter(request, response);
							return;
						}
					}else{
						
							for (int i=0;i<listsoft.size();i++) {
								SoftPermission sftp=listsoft.get(i);
								if (sftp.getUrl().equals(toUrl)) {
									ispass=true;
								}
							}
						if(ispass){
							filterChain.doFilter(request, response);
							return;
						}
					}
					int i222 = toUrl.indexOf("/function/");
					if (toUrl.indexOf("/hr/window") != -1
							|| toUrl.indexOf("/goods/window") != -1
							|| toUrl.indexOf("/crm/window/") != -1
							|| toUrl.indexOf("/LayoutWindow") != -1
							|| toUrl.indexOf("/backManager") != -1
							|| toUrl.indexOf("/itempage/lookAllStu") != -1
							|| toUrl.indexOf("/function/") == 0
							|| toUrl.indexOf("/crm/page/createChanceWindow") != -1
							|| toUrl.indexOf("/crm/page/addLinkMan") != -1
							|| toUrl.indexOf("/crm/page/chanceOpenInfo") != -1
							|| toUrl.indexOf("/crm/page/chanceOpenMarks") != -1
							|| toUrl.indexOf("/crm/page/selectCustemor") != -1
							|| toUrl.indexOf("/crm/page/selectLinkMan") != -1
							|| toUrl.indexOf("/crm/page/selectNotes") != -1
							|| toUrl.indexOf("/goods/page/queryData_window") != -1
							|| toUrl.indexOf("/goods/page/addData_window") != -1
							|| toUrl.indexOf("/goods/page/Edit_StoreHouse_window") != -1
							|| toUrl.indexOf("/goods/page/Edit_GoodSource_window") != -1
							|| toUrl.indexOf("/hr/page/addCustemmer") != -1
							|| toUrl.indexOf("/hr/page/queryCustemmer") != -1
							|| toUrl.indexOf("/queryMessage") != -1
							|| toUrl.indexOf("/onclickMessage") != -1
							) {
						filterChain.doFilter(request, response);
						return;
					} else {

						Map<String, Object> map = new HashMap<String, Object>();
						List<Object> listp = dao.getObjectList(
								"SoftPermission", "where url='" + toUrl + "'");
						if (listp.size() > 0) {
							SoftPermission s = (SoftPermission) listp
									.iterator().next();
							map.put("info", "出错了，您没有使用《" + s.getFunctionName()
									+ "》的权限");
						} else {
							map.put("info", "出错了，系统没有该权限功能");
						}
						map.put("success", false);
						request.setAttribute("map", map);
						request.getRequestDispatcher("/error").forward(request,
								response);// 这是内部跳转
						return;
					}
				} else {
					groupidLong = Long.parseLong(groupId);
				}
				// 权限检查
				List<SoftPermission> list = dao.getSoftPermissionByConpanyUser(
						user, groupidLong);
				Iterator<SoftPermission> i = list.iterator();
				while (i.hasNext()) {
					if (i.next().getUrl().equals(toUrl)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
				if (toUrl.indexOf("/hr/window") != -1
						|| toUrl.indexOf("/goods/window") != -1
						|| toUrl.indexOf("/crm/window/") != -1
						|| toUrl.indexOf("/LayoutWindow") != -1
						|| toUrl.indexOf("/backManager") != -1
						|| toUrl.indexOf("/itempage/lookAllStu") != -1
						|| toUrl.indexOf("/function/") == 0
						|| toUrl.indexOf("/crm/page/createChanceWindow") != -1
						|| toUrl.indexOf("/crm/page/addLinkMan") != -1
						|| toUrl.indexOf("/crm/page/chanceOpenInfo") != -1
						|| toUrl.indexOf("/crm/page/chanceOpenMarks") != -1
						|| toUrl.indexOf("/crm/page/selectCustemor") != -1
						|| toUrl.indexOf("/crm/page/selectLinkMan") != -1
						|| toUrl.indexOf("/crm/page/selectNotes") != -1
						|| toUrl.indexOf("/goods/page/queryData_window") != -1
						|| toUrl.indexOf("/goods/page/addData_window") != -1
						|| toUrl.indexOf("/goods/page/Edit_StoreHouse_window") != -1
						|| toUrl.indexOf("/goods/page/Edit_GoodSource_window") != -1
						|| toUrl.indexOf("/hr/page/addCustemmer") != -1
						|| toUrl.indexOf("/hr/page/queryCustemmer") != -1
						|| toUrl.indexOf("/queryMessage") != -1
						|| toUrl.indexOf("/onclickMessage") != -1
						){
					filterChain.doFilter(request, response);
					return;
				} else {

					Map<String, Object> map = new HashMap<String, Object>();
					List<Object> listp = dao.getObjectList("SoftPermission",
							"where url='" + toUrl + "'");
					if (listp.size() > 0) {
						SoftPermission s = (SoftPermission) listp.iterator()
								.next();
						map.put("info", "出错了，您没有使用《" + s.getFunctionName()
								+ "》的权限");
					} else {
						map.put("info", "出错了，系统没有该权限功能");
					}
					map.put("success", false);
					request.setAttribute("map", map);
					request.getRequestDispatcher("/error").forward(request,
							response);// 这是内部跳转
					return;
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("info", "出错了，错误信息是:" + exc.getMessage());
			request.setAttribute("map", map);
			request.getRequestDispatcher("/error").forward(request, response);// 这是内部跳转
			return;
		}
	}

	/**
	 * isAjaxRequest:判断请求是否为Ajax请求. <br/>
	 * 
	 * @author chenzhou
	 * @param request
	 *            请求对象
	 * @return boolean
	 * @since JDK 1.6
	 */
	public boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
		return isAjax;
	}
}
