package com.dcl.blog.util;

import javax.servlet.http.HttpServletRequest;


public class SystemLevel {
	/**
	 * 检验验证码
	 * @param req
	 * @return
	 */
	public static boolean testCode(HttpServletRequest req){

		Object sessCode = req.getSession().getAttribute(
				SessionString.SEESION_CODE);
		boolean issuccessCode = false;
		if(sessCode!=null){
			String code = req.getParameter("code");
			
			if (code != null && sessCode != null) {
				code = code.toLowerCase();
				if (((String) sessCode).equals(code)) {
					issuccessCode = true;
					req.getSession().removeAttribute(SessionString.SEESION_CODE);	
					
				} else {
					issuccessCode = false;

				}
			} else {
				issuccessCode = false;
				
			}
			
		}
		return issuccessCode;

	}
	
}
