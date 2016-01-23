package com.dcl.blog.util.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class emailimpl{
	 private JavaMailSender javaMailSender;// 发送邮件的工具类,提供get、set方法,实现依赖注入  
	  
	    private String systemEmail;  

	public void sendMail(String to, String subject, String htmlText) throws AccountEmailException{
		// TODO Auto-generated method stub
		  try {  
	            MimeMessage msg = javaMailSender.createMimeMessage();// 将要发送的邮件  
	            
	            MimeMessageHelper msgHelper = new MimeMessageHelper(msg, "UTF-8");  
	            msgHelper.setFrom(systemEmail);// 设置邮件的发送地址  
	            msgHelper.setTo(to);// 设置邮件收件地址  
	            msgHelper.setSubject(subject);// 设置邮件主题  
	            msgHelper.setText(htmlText, true);// 设置邮件内容  
	            javaMailSender.send(msg);// 执行发送邮件  
	        } catch (MessagingException e) {  
	            throw new AccountEmailException("邮件发送失败", e);  
	        }  
	}
	public void sendInfoDateEmail(String to,String html) throws AccountEmailException{
		sendMail(to,"海达聚商网络----通知",html);
	}
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
