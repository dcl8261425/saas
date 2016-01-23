package com.dcl.blog.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SystemLevel;

public class SessionFilter extends OncePerRequestFilter {
	private DaoService dao=null;
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try{
			WebApplicationContext wac =   WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			if(dao==null){
				dao=wac.getBean(DaoService.class);
				HttpSession se=request.getSession();
			}
			//需要检查验证码的url
			String[] codeurl={"createConpany"};
			String fromurl=request.getHeader("referer");
			String toUrl=request.getRequestURL().toString();
			if(toUrl.indexOf("/img/")!=-1||toUrl.indexOf("/js/")!=-1||toUrl.indexOf("/css/")!=-1||toUrl.indexOf("error")!=-1){
				filterChain.doFilter(request, response); 
				return ;
			}
			boolean isTestcode=false;
			for(int i=0;i<codeurl.length;i++){
				if(toUrl.indexOf(codeurl[i])!=-1){
					isTestcode=true;
				}
			}
			/**
			 * 检测验证码
			 */
			if(isTestcode&&!SystemLevel.testCode(request)){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("success", false);
				map.put("info", "验证码错误");
				request.setAttribute("map", map);
				request.getRequestDispatcher("/error")
	            .forward(request, response);//这是内部跳转
				return ;
			}else{
				filterChain.doFilter(request, response);  
				return ;
			}
			}catch(Exception exc){
				exc.printStackTrace();
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("success", false);
				map.put("info", "出错了，错误信息是:"+exc.getMessage());
				request.setAttribute("map", map);
				request.getRequestDispatcher("/error")
	            .forward(request, response);//这是内部跳转
				return ;
			}
	}

}
