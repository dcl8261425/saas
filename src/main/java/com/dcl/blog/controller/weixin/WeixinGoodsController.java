package com.dcl.blog.controller.weixin;

import java.util.Date;
import java.util.HashMap;
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

import com.dcl.blog.model.ChanceList;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.GoodsLog;
import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.StoreHouseDateLog;
import com.dcl.blog.model.WeiXinGoods;
import com.dcl.blog.model.WeiXinGoodsType;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DataTypeTestUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;


@Controller
@RequestMapping("/weixin/goods")
public class WeixinGoodsController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeiXinFunctionController.class);
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
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map jifenTogoodManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=null;
		Map stringtest=new HashMap();
		ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
		long nums=dao.getObjectListNum("WeiXinGoods", "where conpanyId="+user.getConpanyId());
		if(!(conpany.isPayConpany()&&conpany.getEndDate().after(new Date()))){
			if(nums>=20){
				map.put("success", false);
				map.put("info","创建失败，免费用户只可以创建20个在线商品，付费用户不限");
				return map;
			}
		}
		stringtest.put("goodname", DataTypeTestUtil.STRING);
		stringtest.put("goodsType", DataTypeTestUtil.STRING);
		stringtest.put("price", DataTypeTestUtil.FLOAT);
		stringtest.put("inPrice", DataTypeTestUtil.FLOAT);
		stringtest.put("score", DataTypeTestUtil.FLOAT);
		stringtest.put("spell", DataTypeTestUtil.STRING);
		stringtest.put("goodsModel", DataTypeTestUtil.STRING);
		stringtest.put("image1", DataTypeTestUtil.STRING);
		stringtest.put("image2", DataTypeTestUtil.STRING);
		stringtest.put("image3", DataTypeTestUtil.STRING);
		stringtest.put("image4", DataTypeTestUtil.STRING);
		stringtest.put("marks", DataTypeTestUtil.STRING);
		stringtest.put("goodsSelectType", DataTypeTestUtil.STRING);
		map=DataTypeTestUtil.testDate(req, stringtest);
		if((Boolean)map.get("success")){
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String goodsName=req.getParameter("goodname");
			String goodsType=req.getParameter("goodsType");
			String Price=req.getParameter("price");
			String inPrice	=req.getParameter("inPrice");
			String score=req.getParameter("score");
			String Spell	=req.getParameter("spell");
			String goodsModel=req.getParameter("goodsModel");
			String codeid=req.getParameter("codeid");
			String goodsSelectType=req.getParameter("goodsSelectType");
			WeiXinGoodsType type=null;
			if(goodsSelectType==null){
				goodsSelectType="未分类";
				
			}
			List list=dao.getObjectList("WeiXinGoodsType","where conpanyId="+users.getConpanyId()+" and typeName="+goodsSelectType.trim());
			if(list.size()>0){
				type=(WeiXinGoodsType) list.iterator().next();
			}else{
				type=new WeiXinGoodsType();
				type.setConpanyId(users.getConpanyId());
				type.setTypeName(goodsSelectType);
				dao.add(type);
			}
			if(codeid==null){
				codeid="";
			}
			GoodsTable t=new GoodsTable();
			t.setGoodsModel(goodsModel);
			t.setGoodsName(goodsName);
			t.setGoodsNum(0);
			t.setGoodsType(goodsType);
			t.setInPrice(Float.parseFloat(inPrice));
			t.setPrice(Float.parseFloat(Price));
			t.setSalesNum(0);
			t.setScore(Double.parseDouble(score));
			t.setSpell(Spell);
			t.setCodeid(codeid);
			t.setTotalInPrice(Float.parseFloat(inPrice)*0);
			t.setTotalPrice(Float.parseFloat(Price)*0);
			t.setConpanyId(users.getConpanyId());
			t=dao.addGoods(t);
			WeiXinGoods goodswei=new WeiXinGoods();
			goodswei.setConpanyId(users.getConpanyId());
			goodswei.setGoodsId(t.getId());
			goodswei.setGoodsModel(goodsModel);
			goodswei.setGoodsName(goodsName);
			goodswei.setGoodsType(goodsType);
			goodswei.setInPrice(Float.parseFloat(inPrice));
			goodswei.setPrice(Float.parseFloat(Price));
			goodswei.setMarks(req.getParameter("marks"));
			goodswei.setScore(Double.parseDouble(score));
			goodswei.setImage1(req.getParameter("image1"));
			goodswei.setImage2(req.getParameter("image2"));
			goodswei.setImage3(req.getParameter("image3"));
			goodswei.setImage4(req.getParameter("image4"));
			goodswei.setUseShow(true);
			goodswei.setGoodTypeId(type.getId());
			goodswei.setGoodTypeName(type.getTypeName());
			dao.add(goodswei);
			if(t!=null){
				map.put("success", true);
				map.put("info", "添加成功");
			}else{
				map.put("success", false);
				map.put("info", "添加失败");
			}
		}
		return map;
	}
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map delete(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Map<String, Object> map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		WeiXinGoods good=(WeiXinGoods) dao.getObject(Long.parseLong(id), "WeiXinGoods",users.getConpanyId());
		dao.delete(good);
		return map;
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map update(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String num = req.getParameter("num");
			
		return map;
	}
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map query(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String name=req.getParameter("name");
		String b=req.getParameter("b");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if(b==null){
			List list=dao.getObjectList("WeiXinGoodsType","where conpanyId="+users.getConpanyId());
			map.put("data", list);
			map.put("success", true);
			return map;
		}else{
			
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "30";
			}
			long num2=dao.getObjectListNum("WeiXinGoods", "where conpanyId="+users.getConpanyId()+" and useShow = "+b+" and goodsName like '%"+name+"%'");
			List list=dao.getObjectListPage("WeiXinGoods", "where conpanyId="+users.getConpanyId()+" and useShow = "+b+" and goodsName like '%"+name+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num2/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
			return map;
		}
	}
	
	@RequestMapping(value = "/goodsItem")
	@ResponseBody
	public Map goodsItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	@RequestMapping(value = "/shangjia")
	@ResponseBody
	public Map shangjia(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			WeiXinGoods good=(WeiXinGoods) dao.getObject(Long.parseLong(id), "WeiXinGoods",users.getConpanyId());
			good.setUseShow(true);
			dao.update(good);
			map.put("success", true);
			map.put("info", "上架成功");
		return map;
	}
	@RequestMapping(value = "/xiajia")
	@ResponseBody
	public Map xiajia(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			WeiXinGoods good=(WeiXinGoods) dao.getObject(Long.parseLong(id), "WeiXinGoods",users.getConpanyId());
			good.setUseShow(false);
			dao.update(good);
			map.put("success", true);
			map.put("info", "下架成功");
		return map;
	}
	public void goodsLog(ConpanyUser user,GoodsTable t,int action,String goodsSourceId,String storehouseId,String goodsNum,String inprice,ChanceList chance){
		if(action==GoodsLog.ACTION_ADD){
			GoodsLog goodslog=new GoodsLog();
			goodslog.setAction(action);
			goodslog.setConpanyId(t.getConpanyId());
			goodslog.setGoodsId(t.getId());
			goodslog.setGoodsinPrice(Float.parseFloat(inprice));
			goodslog.setGoodsNum(Float.parseFloat(goodsNum));
			GoodsSource gs=(GoodsSource) dao.getObject(Long.parseLong(goodsSourceId), "GoodsSource",user.getConpanyId());
			goodslog.setGoodsSourceId(gs.getId());
			goodslog.setGoodsSourceName(gs.getName());
			StoreHouse sh=(StoreHouse) dao.getObject(Long.parseLong(storehouseId), "StoreHouse",user.getConpanyId());
			goodslog.setGoodsToStorehouseId(sh.getId());
			goodslog.setGoodsToStorehouseName(sh.getName());
			StoreHouseDateLog log=dao.getStoreHouseDateLog(t.getId(), sh.getId(),user.getConpanyId());
			log.setCountnum(log.getCountnum()+Float.parseFloat(goodsNum));
			log.setNum(log.getNum()+Float.parseFloat(goodsNum));
			dao.update(log);
			goodslog.setStartdate(new Date());
			goodslog.setCreateManId(user.getId());
			goodslog.setCreateManName(user.getTrueName());
			dao.add(goodslog);
		}else if(action==GoodsLog.ACTION_REDUCE){
			GoodsLog goodslog=new GoodsLog();
			goodslog.setAction(action);
			goodslog.setConpanyId(t.getConpanyId());
			goodslog.setGoodsId(t.getId());
			goodslog.setGoodsinPrice(t.getInPrice());
			goodslog.setGoodsName(t.getGoodsName());
			goodslog.setSalesNum(t.getSalesNum());
			GoodsSource gs=(GoodsSource) dao.getObject(Long.parseLong(goodsSourceId), "GoodsSource",user.getConpanyId());
			goodslog.setGoodsSourceId(gs.getId());
			goodslog.setGoodsSourceName(gs.getName());
			StoreHouse sh=(StoreHouse) dao.getObject(Long.parseLong(storehouseId), "StoreHouse",user.getConpanyId());
			goodslog.setGoodsToStorehouseId(sh.getId());
			goodslog.setGoodsToStorehouseName(sh.getName());
			StoreHouseDateLog log=dao.getStoreHouseDateLog(t.getId(), sh.getId(),user.getConpanyId());
			log.setNum(log.getNum()-t.getSalesNum());
			dao.update(log);
			goodslog.setStartdate(new Date());
			goodslog.setPrice(t.getPrice());
			goodslog.setChanceId(chance.getId());
			goodslog.setChanceName(chance.getCustomerName());
			goodslog.setCreateManId(user.getId());
			goodslog.setCreateManName(user.getTrueName());
			dao.add(goodslog);
		}else if(action==GoodsLog.ACTION_UPDATE_PRICE){
			GoodsLog goodslog=new GoodsLog();
			goodslog.setAction(action);
			goodslog.setConpanyId(t.getConpanyId());
			goodslog.setGoodsId(t.getId());
			goodslog.setStartdate(new Date());
			goodslog.setPrice(t.getPrice());
			goodslog.setCreateManId(user.getId());
			goodslog.setCreateManName(user.getTrueName());
			dao.add(goodslog);
		}
	}
}
