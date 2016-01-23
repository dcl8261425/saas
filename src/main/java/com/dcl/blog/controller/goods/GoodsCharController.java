package com.dcl.blog.controller.goods;

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
import com.dcl.blog.model.StoreHouseDateLog;
import com.dcl.blog.model.chars.PieCharNoColor;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
@Controller
@RequestMapping("/goods/function/char")
public class GoodsCharController {
	private static final Logger logger = LoggerFactory
			.getLogger(GoodsCharController.class);
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
	 * 获取销售图表数据
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/querySaleChat")
	@ResponseBody
	public Map querySaleChat(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String id=req.getParameter("id");
		Date end=DateUtil.toDateType(endDate);
		Calendar calend=Calendar.getInstance();
		calend.setTime(end);
		calend.add(Calendar.DAY_OF_MONTH, 1);
		endDate=DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List list=dao.getSalesData(startDate, endDate,Long.parseLong(id),users.getConpanyId());
		Iterator i=list.iterator();
		List<String> names=new ArrayList<String>();
		List<Double> values=new ArrayList<Double>();
		List<PieCharNoColor> PieChar=new ArrayList<PieCharNoColor>();
		double countNum=0;
		while(i.hasNext()){
			Object[] onjs=(Object[]) i.next();
			double num=(Double) onjs[0];
			String chanceName=(String) onjs[4];
			names.add(chanceName);
			values.add(num);
			countNum=countNum+num;
		}
		
		for(int ii=0;ii<names.size();ii++){
			double value=values.get(ii);
			String name=names.get(ii);
			PieCharNoColor p=new PieCharNoColor();
			p.setY((float) (value/countNum*100));
			p.setName(name);
			PieChar.add(p);
		}
		map.put("values", values);
		map.put("names", names);
		map.put("pieValue", PieChar);
		map.put("success", true);
		return map;
	}
	/**
	 * 获取进货图表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryInGoods")
	@ResponseBody
	public Map queryInGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String id=req.getParameter("id");
		Date start=DateUtil.toDateType(startDate);
		Date end=DateUtil.toDateType(endDate);
		Calendar calend=Calendar.getInstance();
		calend.setTime(end);
		calend.add(Calendar.DAY_OF_MONTH, 1);
		endDate=DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List list=dao.getInGoodsData(startDate, endDate,Long.parseLong(id),users.getConpanyId());
		Iterator i=list.iterator();
		List<String> names=new ArrayList<String>();
		List<Double> values=new ArrayList<Double>();
		List<PieCharNoColor> PieChar=new ArrayList<PieCharNoColor>();
		double countNum=0;
		while(i.hasNext()){
			Object[] onjs=(Object[]) i.next();
			double num=(Double) onjs[0];
			String chanceName=(String) onjs[4];
			names.add(chanceName);
			values.add(num);
			countNum=countNum+num;
		}
		for(int ii=0;ii<names.size();ii++){
			double value=values.get(ii);
			String name=names.get(ii);
			PieCharNoColor p=new PieCharNoColor();
			p.setY((float) (value/countNum*100));
			p.setName(name);
			PieChar.add(p);
		}
		map.put("values", values);
		map.put("names", names);
		map.put("pieValue", PieChar);
		map.put("success", true);
		return map;
	}
	/**
	 * 获取价格趋势
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/priceChar")
	@ResponseBody
	public Map priceChar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String id=req.getParameter("id");
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
		List<String> date=new ArrayList<String>();
		List<Double> num=new ArrayList<Double>();
		List<Double> inprice=new ArrayList<Double>();
		List<Double> price=new ArrayList<Double>();
		List<Double> profit=new ArrayList<Double>();
		if(y){
			//如果开始时间和结束时间年不相同则按照月查
			calstart.set(Calendar.DAY_OF_MONTH,1);
			calend.set(Calendar.DAY_OF_MONTH, 1);
			calend.add(Calendar.MONTH, 1);
			for(;calend.after(calstart);){
				String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
				nowtime1=nowtime1.substring(2)+"月";
				String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
				calstart.add(Calendar.MONTH, 1);
				String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
				List listDate=dao.getGoodsPriceAndInPrice(nowtime, aftertime, Long.parseLong(id),users.getConpanyId());
				if(listDate.size()!=0){
				for(int i=0;i<listDate.size();i++){
					Object[] onjs=(Object[]) listDate.get(i);
					num.add((Double) onjs[0]);
					price.add((Double) onjs[1]);
					inprice.add((Double) onjs[2]);
					profit.add((Double) (((Float) onjs[1]-(Float) onjs[2])*(Double) onjs[0]));
					date.add(nowtime1);
				}
				}else{
					if(num.size()!=0){
					num.add(0.0);
					price.add(price.get(price.size()-1));
					inprice.add(inprice.get(inprice.size()-1));
					profit.add(0.0);
					date.add(nowtime1);
					}
				}

			}
		}else if(m){
			//如果开始时间和结束时间月不相同则按照月查
			calstart.set(Calendar.DAY_OF_MONTH,1);
			calend.set(Calendar.DAY_OF_MONTH, 1);
			calend.add(Calendar.MONTH, 1);
			for(;calend.after(calstart);){
				String nowtime1=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1);
				nowtime1=nowtime1.substring(2)+"月";
				String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
				calstart.add(Calendar.MONTH, 1);
				String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
				List listDate=dao.getGoodsPriceAndInPrice(nowtime, aftertime, Long.parseLong(id),users.getConpanyId());
				if(listDate.size()!=0){
				for(int i=0;i<listDate.size();i++){
					Object[] onjs=(Object[]) listDate.get(i);
					num.add((Double) onjs[0]);
					price.add((Double) onjs[1]);
					inprice.add((Double) onjs[2]);
					profit.add((Double) (((Double) onjs[1]-(Double) onjs[2])*(Double) onjs[0]));
					date.add(nowtime1);
				}
				}else{
					if(num.size()!=0){
					num.add(0.0);
					price.add(price.get(price.size()-1));
					inprice.add(inprice.get(inprice.size()-1));
					profit.add(0.0);
					date.add(nowtime1);
					}
				}
			}
			
		}else{
			//以上都不成立则按照日输出结果
			calend.add(Calendar.DAY_OF_MONTH, 1);
			for(;calend.after(calstart);){
				String nowtime1=calstart.get(Calendar.DAY_OF_MONTH)+"日";
				String nowtime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+(calstart.get(Calendar.DAY_OF_MONTH));
				calstart.add(Calendar.DAY_OF_MONTH, 1);
				String aftertime=calstart.get(Calendar.YEAR)+"-"+(calstart.get(Calendar.MONTH)+1)+"-"+calstart.get(Calendar.DAY_OF_MONTH);
				List listDate=dao.getGoodsPriceAndInPrice(nowtime, aftertime, Long.parseLong(id),users.getConpanyId());
				if(listDate.size()!=0){
					for(int i=0;i<listDate.size();i++){
						Object[] onjs=(Object[]) listDate.get(i);
						num.add((Double) onjs[0]);
						price.add((Double) onjs[1]);
						inprice.add((Double) onjs[2]);
						profit.add((Double) (((Double) onjs[1]-(Double) onjs[2])*(Double) onjs[0]));
						date.add(nowtime1);
					}
				}else{
					if(num.size()!=0){
					num.add(0.0);
					price.add(price.get(price.size()-1));
					inprice.add(inprice.get(inprice.size()-1));
					profit.add(0.0);
					date.add(nowtime1);
					}
				}
			}
		}
		map.put("num", num);
		map.put("price", price);
		map.put("inprice", inprice);
		map.put("profit", profit);
		map.put("date", date);
		map.put("success", true);
		return map;
	}
	/**
	 * 获取商品库存图表
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryStoreHouseChat")
	@ResponseBody
	public Map queryStoreHouseChat(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List<StoreHouseDateLog> list=dao.getStoreHouseDateLogChar(Long.parseLong(id),users.getConpanyId());
		Iterator<StoreHouseDateLog> i=list.iterator();
		List<String> storeHouseName=new ArrayList<String>();
		List<Double> storeHouseNum=new ArrayList<Double>();
		List<Double> storeHouseCount=new ArrayList<Double>();
		List<PieCharNoColor> PieChar=new ArrayList<PieCharNoColor>();
		double conuntNum=0;
		while (i.hasNext()) {
			StoreHouseDateLog s=i.next();
			storeHouseName.add(s.getStoreHoseName());
			storeHouseNum.add(s.getNum());
			storeHouseCount.add(s.getCountnum());
			conuntNum=conuntNum+s.getNum();
		}
		for(int ii=0;ii<storeHouseNum.size();ii++){
			double num=storeHouseNum.get(ii);
			String name=storeHouseName.get(ii);
			PieCharNoColor p=new PieCharNoColor();
			if(num==0&&conuntNum==0){
				p.setY(0);
			}else{
				p.setY((float) (num/conuntNum*100));
			}
			p.setName(name);
			PieChar.add(p);
		}
		map.put("success", true);
		map.put("hName", storeHouseName);
		map.put("hNum", storeHouseNum);
		map.put("hCount", storeHouseCount);
		map.put("hPer", PieChar);
		return map;
	}
}
