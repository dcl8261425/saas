package com.dcl.blog.util;

import org.springframework.validation.BindingResult;
/**
 * 日志的格式化输出以后用于检测用户行为。分析日志。hadoop
 * @author Administrator
 *
 */
public class LogFormat {
	/**
	 * 生成 提交表单时的错误日志
	 * @param errorFormName
	 * @param errorForm
	 * @param result
	 * @return
	 */
	public static String Log(String errorFormName,Object errorForm,BindingResult result){
		StringBuffer strb=new StringBuffer();
		strb.append("{errorName:")
		.append(errorFormName)
		.append(",errorForm:")
		.append(errorForm.toString())
		.append(",errorInfo:")
		.append(result.toString());
		return strb.toString();
	}
	/**
	 * 保存bean的时候生成的日志
	 * @param beanName
	 * @param object
	 * @return
	 */
	public static String saveBeanLog(String beanName,Object object){
		StringBuffer strb=new StringBuffer();
		strb.append("save:").append(beanName).append(",info:").append(object.toString());
		return strb.toString();
	}
	/**
	 * 更新bean的时候生成的日志
	 * @param beanName
	 * @param object
	 * @return
	 */
	public static String updateBeanLog(String beanName,Object object){
		StringBuffer strb=new StringBuffer();
		strb.append("update:").append(beanName).append(",info:").append(object.toString());
		return strb.toString();
	}
	/**
	 * 删除bean的时候生成的日志
	 * @param beanName
	 * @param object
	 * @return
	 */
	public static String deleteBeanLog(String beanName,Object object){
		StringBuffer strb=new StringBuffer();
		strb.append("delete:").append(beanName).append(",info:").append(object.toString());
		return strb.toString();
	}
	/**
	 * 查询bean的时候生成的日志
	 * @param beanName
	 * @param object
	 * @return
	 */
	public static String queryBeanLog(String beanName,Object object){
		StringBuffer strb=new StringBuffer();
		strb.append("query:").append(beanName).append(",info:").append(object.toString());
		return strb.toString();
	}
}
