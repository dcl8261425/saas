package com.dcl.blog.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
/**
 * 用于检测非法字符的输入
 * @author Administrator
 *
 */
public class InputContentFilter extends OncePerRequestFilter {
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
			
			String[] codeurl={"createConpany"};
			String fromurl=request.getHeader("referer");
			String toUrl=request.getRequestURL().toString();
			if(toUrl.indexOf("/wifidogController")!=-1){
				filterChain.doFilter(request, response);
				return;
			}
			boolean isteshuzifu=false;
			if(toUrl.indexOf("/img/")!=-1||toUrl.indexOf("/js/")!=-1||toUrl.indexOf("/css/")!=-1||toUrl.indexOf("error")!=-1){
				filterChain.doFilter(request, response); 
				return ;
			}else{
				//检测参数是否有非法参数
				Enumeration e=request.getParameterNames();
				boolean b=false;
				Map<String, Object> map=new HashMap<String, Object>();
				List<Map> list=new ArrayList<Map>();
				while (e.hasMoreElements()) {
					Map<String, Object> map2=new HashMap<String, Object>();
					String s=(String) e.nextElement();
					String content=request.getParameter(s);
					content=content.replace("'", "&#39;");
					content=content.replace("<", "&lt;");
					content=content.replace(">", "&gt;");
					if(content.indexOf("<--")!=-1||content.indexOf("-->")!=-1||content.indexOf("<-")!=-1||content.indexOf("<-")!=-1){
						b=true;
						map2.put("info", "请不要使用非法字符如:<--或-->以及还有\"");
						map2.put("name", s);
						map2.put("success", false);
						list.add(map2);
						isteshuzifu=true;
					}else{

							if(content.trim().equals("")&&!s.equals("groupName")&&!s.equals("trueName")&&!s.equals("ChanceName")&&!s.equals("roleName")&&!s.equals("name")&&!s.equals("spell_query")){
								map2.put("info", "不能不填写任何数据就提交，如不填写请写《略》");
								map2.put("name", s);
								map2.put("success", false);
								list.add(map2);
								b=true;
							}else{
								map2.put("info", "正确");
								map2.put("name", s);
								map2.put("success", true);
								list.add(map2);
							}
						
					}
				}
				if(toUrl.indexOf("/fileSrc/getImageList")!=-1&&!isteshuzifu){
					filterChain.doFilter(request, response); 
					return ;
				}
				if(b){
					map.put("list", list);
					map.put("success", false);
					request.setAttribute("map", map);
					request.getRequestDispatcher("/error")
		            .forward(request, response);//这是内部跳转
					return ;
				}else{
					
				}
				filterChain.doFilter(request, response); 
				return ;
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
