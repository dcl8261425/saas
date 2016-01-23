package com.dcl.blog.util;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.dto.MessageXML;

public class MessageClient {
  

	   //通知类
    public static MessageXML sendSMSInfo(String mobile,String content2,MessageSet mes) throws Exception {
    	String mtUrl="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce";
        String uid = "1001@501128110001";		//用户名
        String pwd = "7677323B55AF3FA173F4099AA4288024";		//MD5 32位 密码

		//发送的内容
        //组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("&ac=" + uid);
        smsUrl.append("&authkey=" + pwd);
        smsUrl.append("&cgid=4044");
        smsUrl.append("&c=" +URLEncoder.encode( content2));
        smsUrl.append("&m=" + mobile);


        //发送http请求，并接收http响应
        String resStr = doPostRequest(smsUrl.toString());
        MessageXML xml=BeanXMLMapping.getXMLToMessageXML(resStr);
        return xml;

    }
    //通知类
    public static MessageXML sendSMSInfo(String mobile,String content2) throws Exception {
    	String mtUrl="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce";
        String uid = "1001@501128110001";		//用户名
        String pwd = "7677323B55AF3FA173F4099AA4288024";		//MD5 32位 密码

		//发送的内容
        //组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("&ac=" + uid);
        smsUrl.append("&authkey=" + pwd);
        smsUrl.append("&cgid=4044");
        smsUrl.append("&c=" +URLEncoder.encode( content2));
        smsUrl.append("&m=" + mobile);


        //发送http请求，并接收http响应
        String resStr = doPostRequest(smsUrl.toString());
        MessageXML xml=BeanXMLMapping.getXMLToMessageXML(resStr);
        return xml;

    }
    //群发类
    public static MessageXML sendSMSmarks(String mobile,String content2,MessageSet mes,boolean oneToOne) throws Exception {
    	String mtUrl="";
    	String uid="";
    	 String pwd ="";
    	 String port="";
    	if(oneToOne){
    		mtUrl="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendBatch";
    		uid= "1001@501128110001";		//用户名
    		pwd = "7677323B55AF3FA173F4099AA4288024";		//MD5 32位 密码
    		 port="4044";
    	}else{
    		mtUrl="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce";
    		 uid = "1001@501128110002";		//用户名
             pwd = "1DA84350CB9E20D2DAD182A398F1D12E";		//MD5 32位 密码
             port="4074";
    	}
    	
        
        String ecodeform = "GBK";

		//发送的内容
        //组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("&ac=" + uid);
        smsUrl.append("&authkey=" + pwd);
        smsUrl.append("&cgid="+port);
        smsUrl.append("&c=" + URLEncoder.encode(content2));
        smsUrl.append("&m=" + mobile);


        //发送http请求，并接收http响应
        String resStr = doPostRequest(smsUrl.toString());
        MessageXML xml=BeanXMLMapping.getXMLToMessageXML(resStr);
        return xml;


    }
    //验证码
    public static MessageXML sendSMSyanzheng(String mobile,String content2,MessageSet mes) throws Exception {
    	String mtUrl="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce";
        String uid = "1001@501128110003";		//用户名
        String pwd = "93E1CCC02BA14FA4AC951F8F0741EB7C";		//MD5 32位 密码
        String ecodeform = "GBK";

		//发送的内容
        //组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("&ac=" + uid);
        smsUrl.append("&authkey=" + pwd);
        smsUrl.append("&cgid=4073");
        smsUrl.append("&c=" + URLEncoder.encode(content2));
        smsUrl.append("&m=" + mobile);


        //发送http请求，并接收http响应
        String resStr = doPostRequest(smsUrl.toString());
        MessageXML xml=BeanXMLMapping.getXMLToMessageXML(resStr);
        return xml;


    }
    /**
     * 发送http GET请求，并返回http响应字符串
     * 
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);

        HttpMethod httpmethod = new GetMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }

    /**
     * 发送http POST请求，并返回http响应字符串
     * 
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doPostRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);
        
        HttpMethod httpmethod =  new PostMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }
    /**
     * 获取编号意义
     */
    public static String getNumInfo(int num){
    	switch (num) {
		case 1:
			return "操作成功";
		case 0:
			return "帐户格式不正确(正确的格式为:员工编号@企业编号)";
		case -1:
			return "服务器拒绝(速度过快、限时或绑定IP不对等)如遇速度过快可延时再发";
		case -2:
			return "密钥不正确";
		case -3:
			return "密钥已锁定";
		case -4:
			return "参数不正确(内容和号码不能为空，手机号码数过多，发送时间错误等)";
		case -5:
			return "无此帐户";
		case -6:
			return "帐户已锁定或已过期";
		case -7:
			return "帐户未开启接口发送";
		case -8:
			return "不可使用该通道组";
		case -9:
			return "帐户余额不足";
		case -10:
			return "内部错误";
		case -11:
			return "扣费失败";
		default:
			break;
		}
    	return "";
    }
}  