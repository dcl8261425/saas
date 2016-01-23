package com.dcl.blog.controller.crm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.chars.DateChar;
import com.dcl.blog.model.chars.PieChar;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/crm/function/char")
public class CRMCharController {
	private static final Logger logger = LoggerFactory
			.getLogger(CRMCharController.class);
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
	/**
	 * 查询图表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map createChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String type=req.getParameter("type");
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		Date start=DateUtil.toDateType(startDate);
		Date end=DateUtil.toDateType(endDate);
		Calendar calstart=Calendar.getInstance();
		calstart.setTime(start);
		Calendar calend=Calendar.getInstance();
		calend.setTime(end);
		//用于判断是用月显示，还是用天显示
		boolean y=false;//true 是按照月
		boolean m=false;
		int sy=calstart.get(Calendar.YEAR);
		int sm=calstart.get(Calendar.MONTH);
		int sd=calstart.get(Calendar.DAY_OF_MONTH);
		int ey=calend.get(Calendar.YEAR);
		int em=calend.get(Calendar.MONTH);
		int ed=calend.get(Calendar.DAY_OF_MONTH);
		if(sy!=ey){
			y=true;
		}
		if(sm!=em){
			m=true;
		}

		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		
		if(type.equals("1")){
			List<DateChar> list=new ArrayList<DateChar>();
			DateChar date1=new DateChar();
			date1.setName("普通机会");
			DateChar date2=new DateChar();
			date2.setName("优质机会");
			DateChar date3=new DateChar();
			date3.setName("已成客户");
			DateChar date4=new DateChar();
			date4.setName("已流失");
			DateChar date5=new DateChar();
			date5.setName("超过三个月无购物");
			List<Integer> list1=new ArrayList<Integer>();
			List<Integer> list2=new ArrayList<Integer>();
			List<Integer> list3=new ArrayList<Integer>();
			List<Integer> list4=new ArrayList<Integer>();
			List<Integer> list5=new ArrayList<Integer>();
			List<String> date=new ArrayList<String>();
			//机会状态
			if(y){
				//如果开始时间和结束时间年不相同则按照月查
				calstart.set(Calendar.DAY_OF_MONTH,1);
				calend.set(Calendar.DAY_OF_MONTH, 1);
				calend.add(Calendar.MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
					nowtime1=nowtime1.substring(2)+"月";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					calstart.add(Calendar.MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					int num1=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num2=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num3=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num4=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=5 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list1.add(num1);
					list2.add(num2);
					list3.add(num3);
					list4.add(num4);
					list5.add(num5);
					date.add(nowtime1);
				}
			}else if(m){
				//如果开始时间和结束时间月不相同则按照月查
				calstart.set(Calendar.DAY_OF_MONTH,1);
				calend.set(Calendar.DAY_OF_MONTH, 1);
				calend.add(Calendar.MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
					nowtime1=nowtime1.substring(2)+"月";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					calstart.add(Calendar.MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					int num1=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num2=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num3=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num4=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=5 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list1.add(num1);
					list2.add(num2);
					list3.add(num3);
					list4.add(num4);
					list5.add(num5);
					date.add(nowtime1);
				}
				
			}else{
				//以上都不成立则按照日输出结果
				calend.add(Calendar.DAY_OF_MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.DAY_OF_MONTH)+"日";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
					calstart.add(Calendar.DAY_OF_MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+calstart.get(Calendar.DAY_OF_MONTH);
					int num1=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num2=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num3=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num4=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='state' and state=5 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list1.add(num1);
					list2.add(num2);
					list3.add(num3);
					list4.add(num4);
					list5.add(num5);
					date.add(nowtime1);
				}
			}
			date1.setData(list1);
			date2.setData(list2);
			date3.setData(list3);
			date4.setData(list4);
			date5.setData(list5);
			list.add(date1);
			list.add(date2);
			list.add(date3);
			list.add(date4);
			list.add(date5);
			map.put("success", true);
			map.put("data", list);
			map.put("date", date);
			map.put("info", "统计在月/日客户状态。可以看出公司潜在客户和客户各种状态的增长。");
		}else if(type.equals("2")){
			
			//机会增量
			DateChar date5=new DateChar();
			date5.setName("创建量");
			List<Integer> list5=new ArrayList<Integer>();
			List<String> date=new ArrayList<String>();
			int num1=0;
			int num2=0;
			int num3=0;
			int num4=0;
			if(y){
				//如果开始时间和结束时间年不相同则按照月查
				calstart.set(Calendar.DAY_OF_MONTH,1);
				calend.set(Calendar.DAY_OF_MONTH, 1);
				calend.add(Calendar.MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
					nowtime1=nowtime1.substring(2)+"月";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					calstart.add(Calendar.MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					 num1=num1+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num2=num2+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num3=num3+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num4=num4+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list5.add(num5);
					date.add(nowtime1);
				}

			}else if(m){
				//如果开始时间和结束时间月不相同则按照月查
				calstart.set(Calendar.DAY_OF_MONTH,1);
				calend.set(Calendar.DAY_OF_MONTH, 1);
				calend.add(Calendar.MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
					nowtime1=nowtime1.substring(2)+"月";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					calstart.add(Calendar.MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));;
					 num1=num1+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num2=num2+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num3=num3+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num4=num4+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list5.add(num5);
					date.add(nowtime1);
				}
			}else{
				//以上都不成立则按照日输出结果
				calend.add(Calendar.DAY_OF_MONTH, 1);
				for(;calend.after(calstart);){
					String nowtime1=calstart.get(Calendar.DAY_OF_MONTH)+"日";
					String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
					calstart.add(Calendar.DAY_OF_MONTH, 1);
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+calstart.get(Calendar.DAY_OF_MONTH);
					 num1=num1+((int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=1 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'"));
					 num2=num2+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=2 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num3=num3+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=3 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					 num4=num4+(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and customerLevel=4 and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					int num5=(int) dao.getObjectListNum("ChanceListLog", "where eventType='create' and conpanyId="+users.getConpanyId()+" and createLogDate between '"+nowtime+"' and '"+aftertime+"'");
					list5.add(num5);
					date.add(nowtime1);
				}
			}
			float count=num1+num2+num3+num4;
			List<PieChar> countlist=new ArrayList<PieChar>();
			PieChar p1=new PieChar();
			p1.setColor("#2f7ed8");
			p1.setName("普通");
			if(num1!=0){
				num1=(int) (((float)num1)/count*100);
			}else{
				num1=0;
			}
			p1.setY(num1);
			PieChar p2=new PieChar();
			p2.setColor("#0d233a");
			p2.setName("中级");
			if(num2!=0){
				num2=(int) (((float)num2)/count*100);
			}else{
				num2=0;
			}
			p2.setY(num2);
			PieChar p3=new PieChar();
			p3.setColor("#8bbc21");
			p3.setName("重级");
			if(num3!=0){
				num3=(int) (((float)num3)/count*100);
			}else{
				num3=0;
			}
			p3.setY(num3);
			PieChar p4=new PieChar();
			p4.setColor("#910000");
			p4.setName("特级");
			if(num4!=0){
				num4=(int) (((float)num4)/count*100);
			}else{
				num4=0;
			}
			p4.setY(num4);
			countlist.add(p1);
			countlist.add(p2);
			countlist.add(p3);
			countlist.add(p4);
			date5.setData(list5);
			map.put("success", true);
			map.put("obj", date5);
			map.put("date", date);
			map.put("count", countlist);
			map.put("info", "查看一定时间内录入系统的客户量");
		}else if(type.equals("3")){
			//机会类型
			List<Long> list=new ArrayList<Long>();
			List<String> date=new ArrayList<String>();
				//以上都不成立则按照日输出结果
					calend.add(Calendar.DAY_OF_MONTH, 1);
					String nowtime=calend.get(Calendar.YEAR)+"-"+(calend.get(Calendar.MONTH)+1)+"-"+(calend.get(Calendar.DAY_OF_MONTH));
					String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+calstart.get(Calendar.DAY_OF_MONTH);
					List<Object> createList=dao.getObjectListBySql("select count(createManId),createManId,creayeManName from ChanceListLog where eventType='create' and conpanyId="+users.getConpanyId()+" and createLogDate between '"+aftertime+"' and '"+nowtime+"' group by createManId");
					Iterator<Object> i=createList.iterator();
					while(i.hasNext()){
							Object o=i.next();
							long numid= (Long) Array.get(o, 0);
							String name=(String) Array.get(o, 2);
							list.add(numid);
							date.add(name);
					}
					map.put("success", true);
					map.put("data", list);
					map.put("date", date);
					map.put("info", "查看一段时间内创建者排名。");
		}
		
		return map;
	}
}
