package com.dcl.blog.controller.goods;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.dcl.blog.model.ConpanyScoreNum;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.GoodsLog;
import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsSourceLinkMan;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.InOrder;
import com.dcl.blog.model.InOrderItem;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.Orders;
import com.dcl.blog.model.OrdersItem;
import com.dcl.blog.model.Performance;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.StoreHouseDateLog;
import com.dcl.blog.model.VIPSet;
import com.dcl.blog.model.dto.InOrderDTO;
import com.dcl.blog.model.dto.OrderDTO;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DTOUtil;
import com.dcl.blog.util.DataTypeTestUtil;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.RandomNum;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;

@Controller
@RequestMapping("/goods/function")
public class GoodsFunctionController {
	private static final Logger logger = LoggerFactory
			.getLogger(GoodsFunctionController.class);
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
	 * 添加商品
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addGoods")
	@ResponseBody
	public Map addGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=null;
		Map stringtest=new HashMap();
		stringtest.put("goodname", DataTypeTestUtil.STRING);
		stringtest.put("goodsNum", DataTypeTestUtil.FLOAT);
		stringtest.put("goodsType", DataTypeTestUtil.STRING);
		stringtest.put("price", DataTypeTestUtil.FLOAT);
		stringtest.put("inPrice", DataTypeTestUtil.FLOAT);
		stringtest.put("score", DataTypeTestUtil.FLOAT);
		stringtest.put("spell", DataTypeTestUtil.STRING);
		stringtest.put("goodsModel", DataTypeTestUtil.STRING);
		stringtest.put("sourseId", DataTypeTestUtil.LONG);
		stringtest.put("storehouseId", DataTypeTestUtil.LONG);
		map=DataTypeTestUtil.testDate(req, stringtest);
		if((Boolean)map.get("success")){
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String goodsName=req.getParameter("goodname");
			String goodsNum=req.getParameter("goodsNum");
			String goodsType=req.getParameter("goodsType");
			String Price=req.getParameter("price");
			String inPrice	=req.getParameter("inPrice");
			String score=req.getParameter("score");
			String Spell	=req.getParameter("spell");
			String goodsModel=req.getParameter("goodsModel");
			String goodsSourceId=req.getParameter("sourseId");
			String storehouseId=req.getParameter("storehouseId");
			GoodsTable t=new GoodsTable();
			t.setGoodsModel(goodsModel);
			t.setGoodsName(goodsName);
			t.setGoodsNum(Double.parseDouble(goodsNum));
			t.setGoodsType(goodsType);
			t.setInPrice(Float.parseFloat(inPrice));
			t.setPrice(Float.parseFloat(Price));
			t.setSalesNum(0);
			t.setScore(Double.parseDouble(score));
			t.setSpell(Spell);
			t.setTotalInPrice(Float.parseFloat(inPrice)*Float.parseFloat(goodsNum));
			t.setTotalPrice(Float.parseFloat(Price)*Float.parseFloat(goodsNum));
			t.setConpanyId(users.getConpanyId());
			t=dao.addGoods(t);
			if(t!=null){
				goodsLog(users,t, GoodsLog.ACTION_ADD, goodsSourceId, storehouseId, goodsNum, inPrice,null);
				map.put("success", true);
				map.put("info", "添加成功");
			}else{
				map.put("success", false);
				map.put("info", "添加失败");
			}
		}
		return map;
	}
	/**
	 * 修改价格
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updatePrice")
	@ResponseBody
	public Map updatePrice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		String updateprice=req.getParameter("updateprice");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsTable g=(GoodsTable) dao.getObject(Long.parseLong(id), "GoodsTable",users.getConpanyId());
	
		if(g!=null){
			Map stringtest=new HashMap();
			stringtest.put("updateprice", DataTypeTestUtil.FLOAT);
			stringtest.put("id", DataTypeTestUtil.INT);
			map=DataTypeTestUtil.testDate(req, stringtest);
			boolean b=(Boolean) map.get("success");
			if(b){
				g.setPrice(Float.parseFloat(updateprice));
				g.setTotalPrice(Float.parseFloat(updateprice)*g.getGoodsNum());
				dao.update(g);
				goodsLog(users,g, GoodsLog.ACTION_UPDATE_PRICE, null, null, null, null,null);
				map.put("success", true);
				map.put("info", "修改成功");
			}
		}
		return map;
	}
	/**
	 * 查询商品日志
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryGoodsLog")
	@ResponseBody
	public Map queryGoodsLog(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("GoodsLog", "where conpanyId="+users.getConpanyId()+" and goodsId = '"+id+"'");
		List list=dao.getObjectListPage("GoodsLog", "where conpanyId="+users.getConpanyId()+" and goodsId = '"+id+"'order by startdate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 创建订单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createOrder")
	@ResponseBody
	public Map createOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String Name=req.getParameter("name");
		if(Name==null||Name.equals("")){
			Name=DateUtil.formatDateYYYY_MM_DD(new Date());
		}
		String data=req.getParameter("data");
		String id=req.getParameter("id");
		String type=req.getParameter("type");
		String chance=req.getParameter("chanceid");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String orderNum="";
		Orders order;
		ChanceList chanceobj=null;
		LinkManList lk=null;
		if(chance!=null||!chance.equals("0")){
			lk=(LinkManList) dao.getObject(Long.parseLong(chance), "LinkManList", users.getConpanyId());
		}
		if(lk==null){
			Iterator i=dao.getObjectList("LinkManList", "where conpanyId="+users.getConpanyId()+" and linkManName='未指定客户' and addUserId=-1").iterator();
			
			if(i.hasNext()){
				lk=(LinkManList) i.next();
			}else{
				chanceobj=new ChanceList();
				chanceobj.setConpanyId(users.getConpanyId());
				chanceobj.setCreateManId(-1);
				chanceobj.setCreateDate(new Date());
				chanceobj.setCreateManMark("系统创建");
				chanceobj.setCreayeManName("系统");
				chanceobj.setCustomerAddress("系统");
				chanceobj.setCustomerLevel(1);
				chanceobj.setCustomerMark("默认");
				chanceobj.setCustomerName("默认");
				chanceobj.setCustomerType("默认");
				chanceobj.setLastBuy(new Date());
				chanceobj.setNotesUserId(0);
				chanceobj.setNotesUserName("系统");
				chanceobj.setState(1);
				dao.add(chanceobj);
				lk=new LinkManList();
				lk.setAddUserId(-1);
				lk.setAddUserName("系统");
				lk.setChanceListId(chanceobj.getId());
				lk.setChanceListName(chanceobj.getCustomerName());
				lk.setConpanyId(chanceobj.getConpanyId());
				lk.setLinkManBirthday(new Date());
				lk.setLinkManJob("系统创建");
				lk.setLinkManMark("系统创建，创建订单时没有指定vip则自动放在此信息上");
				lk.setLinkManMaxScore(0);
				lk.setLinkManName("未指定客户");
				lk.setLinkManPhone("0");
				lk.setLinkManScore(0);
				lk.setLinkManSex("1");
				lk.setMoney(0);
				lk.setOpenid("");
				lk.setUserTableId(-1);
				lk.setVipId("-1");
				lk.setVipidNum(-1);
				lk.setVipLevel("");
				lk.setVipMarks("");
				dao.add(lk);
			}
		}
		if(chanceobj==null){
			chanceobj=(ChanceList) dao.getObject(lk.getChanceListId(), "ChanceList", users.getConpanyId());
		}
		if(chanceobj==null){
			map.put("success", false);
			map.put("info", "找不到您指定的客户");
			return map;
		}
		
		List<OrderDTO> list=DTOUtil.getOrdersDTO(data);
		StringBuffer orderContentMessage=new StringBuffer();
		if(id.equals("")||id==null||id.equals("0")){
			order=new Orders();
			order.setCreateDate(new Date());
			order.setTitle(Name);
			order.setState(0);
			order.setCreateUser(users.getId());
			order.setChanceId(chanceobj.getId());
			order.setLinkmanId(lk.getId());
			order.setLinkmanName(lk.getLinkManName());
			order.setUserTableId(lk.getUserTableId());
			order.setChanceName(chanceobj.getCustomerName());
			order.setPay(false);
			order.setCreateUserName(users.getTrueName());
			order.setMarks("");
			order.setConpanyId(users.getConpanyId());
			dao.add(order);
			orderNum="HDORDER_"+order.getId()+users.getConpanyId()+users.getId()+new Date().getTime();
			order.setOrderNum(orderNum);
			order.setCreateUser(users.getId());
			order.setCreateUserName(users.getTrueName());
			dao.update(order);
		}else{
			order=(Orders) dao.getObject(Long.parseLong(id), "Orders",users.getConpanyId());
			if(order.getState()==0){
				orderNum=order.getOrderNum();
				dao.deleteOrder(Long.parseLong(id),users.getConpanyId());
				order.setTitle(Name);
				dao.update(order);
			}else{
				map.put("success", false);
				map.put("info", "该订单已入库，不能修改");
				return map;
			}
		}
		String msg="";
		if(type.equals("store")){
			//用于判断是否所属仓库库存不足
			for(int i=0;i<list.size();i++){
				OrderDTO dto=list.get(i);
				GoodsTable goods=dao.getGoods(dto.getName(), dto.getType(), dto.getModel(), users.getConpanyId());
				if(goods==null){
					continue;
				}
				StoreHouse sh=(StoreHouse) dao.getObject(dto.getStoreHouse(), "StoreHouse",users.getConpanyId());
				if(sh==null){
					sh=dao.getStoreHouse("系统默认", users.getConpanyId(), true);
				}
				StoreHouseDateLog log=dao.getStoreHouseDateLog(goods.getId(), sh.getId(),users.getConpanyId());
				
				if(log.getNum()<=dto.getNum()){
					if(type.equals("store")){
						type="save";
						msg="只保存成功，未入库：<br/>";
					}
					msg=msg+"在仓库：《"+log.getStoreHoseName()+"》中，货物：《"+log.getGoodsName()+"》的库存量只有："+log.getNum()+"了，无法出库的.<br/>";
				}
			}
		}
		double countPrice=0;
		double countscore=0;
		for(int i=0;i<list.size();i++){
			OrderDTO dto=list.get(i);
			OrdersItem orderitem=new OrdersItem();
			GoodsTable goods=dao.getGoods(dto.getName(), dto.getType(), dto.getModel(), users.getConpanyId());
			//保存订单项
			if(goods==null){
				orderitem.setCodeid("");
				orderitem.setGoodsinPrice(dto.getInprice());
				orderitem.setGoodsModel(dto.getModel());
				orderitem.setCreateDate(new Date());
				orderitem.setGoodsId(0);
				orderitem.setGoodsName(dto.getName());
				orderitem.setGoodsNum(dto.getNum());
				GoodsSource gs=dao.getGoodsSource(dto.getGoodRource(), users.getConpanyId(), false);
				if(gs==null){
					 gs=dao.getGoodsSource("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsSourceId(gs.getId());
				orderitem.setGoodsSourceName(gs.getName());
				StoreHouse sh=(StoreHouse) dao.getObject(dto.getStoreHouse(), "StoreHouse",users.getConpanyId());
				if(sh==null){
					sh=dao.getStoreHouse("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsToStorehouseId(sh.getId());
				orderitem.setGoodsToStorehouseName(sh.getName());
				orderitem.setGoodsType(dto.getType());
				orderitem.setInOrderId(order.getId());
				orderitem.setPrice(dto.getPrice());
				orderitem.setScore(dto.getScore());
				orderitem.setSpell(dto.getSpell());
				orderitem.setMarks(dto.getMarks());
			}else{
				orderitem.setCodeid(goods.getCodeid());
				orderitem.setGoodsinPrice(dto.getInprice());
				orderitem.setGoodsModel(goods.getGoodsModel());
				orderitem.setCreateDate(new Date());
				orderitem.setGoodsId(goods.getId());
				orderitem.setGoodsName(goods.getGoodsName());
				orderitem.setGoodsNum(dto.getNum());
				GoodsSource gs=dao.getGoodsSource(dto.getGoodRource(), users.getConpanyId(), false);
				if(gs==null){
					gs=dao.getGoodsSource("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsSourceId(gs.getId());
				orderitem.setGoodsSourceName(gs.getName());
				StoreHouse sh=(StoreHouse) dao.getObject(dto.getStoreHouse(), "StoreHouse",users.getConpanyId());
				if(sh==null){
					sh=dao.getStoreHouse("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsToStorehouseId(sh.getId());
				orderitem.setGoodsToStorehouseName(sh.getName());
				orderitem.setGoodsType(goods.getGoodsType());
				orderitem.setInOrderId(order.getId());
				orderitem.setPrice(dto.getPrice());
				orderitem.setScore(dto.getScore());
				orderitem.setSpell(goods.getSpell());
				orderitem.setMarks(dto.getMarks());
			}
			countPrice=DoubleUtil.add(countPrice, dto.getCountPrice());
			countscore= DoubleUtil.round(DoubleUtil.add(countscore,(DoubleUtil.mul(dto.getScore(), orderitem.getGoodsNum()))), 3);
			orderitem.setConpanyId(users.getConpanyId());
			orderContentMessage.append("[名称-").append(orderitem.getGoodsName()).append(",数量-").append(orderitem.getGoodsNum()).append(",单价-").append(orderitem.getPrice()).append("]");
			dao.add(orderitem);
			
			//入库并写入库日志
			if(type.equals("store")){
					
					if(goods==null){
						//目前库存没有该货物的话则创建该货物在该仓库，之后再减除库存
						goods=new GoodsTable();
						goods.setCodeid("");
						goods.setConpanyId(users.getConpanyId());
						goods.setGoodsModel(orderitem.getGoodsModel());
						goods.setGoodsName(orderitem.getGoodsName());
						goods.setGoodsNum(orderitem.getGoodsNum());
						goods.setGoodsType(orderitem.getGoodsType());
						goods.setInPrice(orderitem.getGoodsinPrice());
						goods.setPrice(orderitem.getPrice());
						goods.setSalesNum(0);
						goods.setScore(orderitem.getScore());
						goods.setSpell(orderitem.getSpell());
						goods.setTotalInPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(), orderitem.getGoodsinPrice()), 5));
						goods.setTotalPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getPrice()),5));
						goods=dao.addGoods(goods);
						orderitem.setGoodsId(goods.getId());
						dao.update(orderitem);
						goodsLog(users,goods, GoodsLog.ACTION_ADD, orderitem.getGoodsSourceId()+"", orderitem.getGoodsToStorehouseId()+"", orderitem.getGoodsNum()+"", orderitem.getGoodsinPrice()+"",chanceobj);
					}
					//写日志
						goods=new GoodsTable();
						goods.setCodeid("");
						goods.setConpanyId(users.getConpanyId());
						goods.setGoodsModel(orderitem.getGoodsModel());
						goods.setGoodsName(orderitem.getGoodsName());
						goods.setGoodsNum(orderitem.getGoodsNum());
						goods.setGoodsType(orderitem.getGoodsType());
						goods.setInPrice(orderitem.getGoodsinPrice());
						goods.setPrice(orderitem.getPrice());
						goods.setSalesNum(0);
						goods.setScore(orderitem.getScore());
						goods.setSpell(orderitem.getSpell());
						goods.setTotalInPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getGoodsinPrice()),5));
						goods.setTotalPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getPrice()),5));
						goods=dao.addReduceGoods(goods);
						goodsLog(users,goods, GoodsLog.ACTION_REDUCE, orderitem.getGoodsSourceId()+"", orderitem.getGoodsToStorehouseId()+"", orderitem.getGoodsNum()+"", orderitem.getGoodsinPrice()+"",chanceobj);
			}
		}

		order.setCountPrice(countPrice);
		if(type.equals("store")){
			if(chanceobj.getState()!=3){
				chanceobj.setState(3);
				dao.update(chanceobj);
			}
			Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), chanceobj.getCreateManId(), chanceobj.getCreayeManName());
			pf.setMyCreateChanceBuyNum(pf.getMyCreateChanceBuyNum()+1);
			pf.setMyCreateChanceBuyCountPrice(DoubleUtil.add(pf.getMyCreateChanceBuyCountPrice(), order.getCountPrice()));
			dao.update(pf);
			if(chanceobj.getNotesUserId()!=0){
				Performance pf2=dao.getPerformanceByToDayUser(users.getConpanyId(), chanceobj.getNotesUserId(), chanceobj.getNotesUserName());
				pf2.setToChanceBuyNum(pf2.getToChanceBuyNum()+1);
				pf2.setToChanceBuyCountPrice(DoubleUtil.add(pf2.getToChanceBuyCountPrice(), order.getCountPrice()));
				dao.update(pf2);
			}
			order.setState(1);
			order.setInStoreUser(users.getId());
			order.setInStoreUserName(users.getTrueName());
			//添加积分，并检测是否提升会员等级
			if(lk.getLinkManName().equals("未指定客户")&&lk.getAddUserId()==-1){
				ConpanyScoreNum num=new ConpanyScoreNum();
				num.setConpanyId(users.getConpanyId());
				num.setOrderId(order.getId());
				num.setOrderNum(orderNum);
				num.setScore(countscore);
				dao.add(num);
				String numstr="";
				boolean b=true;
				while (b) 
				{
					numstr=RandomNum.getSuiji(11);
					List<Object> listss=dao.getObjectListBySql("from ConpanyScoreNum where conpanyId="+users.getConpanyId()+" and num='"+numstr+"'");
					if(listss.size()<=0){
						b=false;
					}
				}
				num.setNum(numstr);
				dao.update(num);
				map.put("scoreNum", num.getNum());
			}else{
				lk.setLinkManScore(DoubleUtil.round(DoubleUtil.add(lk.getLinkManMaxScore(), countscore), 3));
				lk.setLinkManMaxScore(DoubleUtil.round(DoubleUtil.add(lk.getLinkManMaxScore(),countscore),3));
				List<VIPSet> listvip = dao.getVipSetList(users.getConpanyId());
				VIPSet vip = null;
				for (int i = 0; i < listvip.size(); i++) {
					if (listvip.get(i).getScore() <= lk.getLinkManMaxScore()) {
						if (vip != null) {
							if (vip.getScore() < listvip.get(i).getScore()) {
								vip = listvip.get(i);
							}
						} else {
							vip = listvip.get(i);
						}
					}
				}
				
				MessageSet set=dao.getMessageSet(users.getConpanyId());
				if (vip!=null&&lk.getVipidNum()!=vip.getId()) {
					lk.setVipidNum(vip.getId());
					lk.setVipLevel(vip.getName());
					lk.setVipMarks(vip.getMarks());
					if(dao.getAllowSendMessage(users.getConpanyId(), MessageSet.ADD_PRICE)||dao.getAllowSendMessage(users.getConpanyId(), MessageSet.REDUCE_PRICE)){
						if(lk.getLinkManPhone()!=null&&lk.getLinkManPhone().length()==11){
							if(orderContentMessage.length()>200){
								String content="vip等级已变动成为:"+lk.getVipLevel()+",订单号:"+order.getOrderNum()+","+orderContentMessage.substring(0, 200)+"..,总价："+countPrice+set.getQianMing();
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}else{
								String content="vip等级已变动成为:"+lk.getVipLevel()+",订单号:"+order.getOrderNum()+","+orderContentMessage.toString()+",总价："+countPrice+set.getQianMing();
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}
							
						}else{
							dao.update(lk);
							map.put("info", "保存并入库完成,但是会员手机号不正确，无法发送信息");
							dao.update(order);
							map.put("success", true);
							map.put("orderNum", orderNum);
							map.put("orderId", order.getId());
							return map;
						}
					}
				}else{
					if(dao.getAllowSendMessage(users.getConpanyId(), MessageSet.ADD_PRICE)||dao.getAllowSendMessage(users.getConpanyId(), MessageSet.REDUCE_PRICE)){
						if(lk.getLinkManPhone()!=null&&lk.getLinkManPhone().length()==11){
							if(orderContentMessage.length()>200){
								String content="订单号:"+order.getOrderNum()+","+orderContentMessage.substring(0, 200)+"..,总价："+countPrice+"【"+set.getQianMing()+"】";
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}else{
								String content="订单号:"+order.getOrderNum()+","+orderContentMessage.toString()+",总价：【"+countPrice+"【"+set.getQianMing()+"】";
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}
						}else{
							dao.update(lk);
							map.put("info", "保存并入库完成,但是会员手机号不正确，无法发送信息");
							dao.update(order);
							map.put("success", true);
							map.put("orderNum", orderNum);
							map.put("orderId", order.getId());
							return map;
						}
					}
				}
			}
			dao.update(lk);
			map.put("info", "保存并入库完成");
		}else{
			if(msg.equals("")){
				map.put("info", "保存完成");
			}else{
				map.put("info", msg);
			}
		}
		dao.update(order);

		map.put("success", true);
		map.put("orderNum", orderNum);
		map.put("orderId", order.getId());
		return map;
	}
	/**
	 * 查询订单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryOrder")
	@ResponseBody
	public Map queryOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String num=req.getParameter("num");
		String name=req.getParameter("name");
		String chanceid=req.getParameter("chanceid");
		String endDate=req.getParameter("endDate");
		String startDate=req.getParameter("startDate");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");

		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		if(num!=null){
			if(num.equals("0")){
				map.put("success", false);
				map.put("info", "请填写编号");
				return map;
			}
			long num2=dao.getObjectListNum("Orders", "where conpanyId="+users.getConpanyId()+" and orderNum = '"+num+"'");
			List list=dao.getObjectListPage("Orders", "where conpanyId="+users.getConpanyId()+" and orderNum = '"+num+"'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num2/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}else{
			if(name==null){
				name="";
			}
			if(startDate==null||startDate.trim().equals("")){
				startDate="1988-01-01";
			}
			if(endDate==null||endDate.trim().equals("")){
				endDate=DateUtil.formatDateYYYY_MM_DD(new Date());
			}
			Date end=DateUtil.toDateType(endDate);
			Calendar calend=Calendar.getInstance();
			calend.setTime(end);
			calend.add(Calendar.DAY_OF_MONTH, 1);
			endDate=DateUtil.formatDateYYYY_MM_DD(calend.getTime());
			long num2;
			List list;
			if(chanceid==null||chanceid.equals("")||chanceid.equals("0")){
				num2=dao.getObjectListNum("Orders", "where conpanyId="+users.getConpanyId()+" and title like '%"+name+"%' and createDate between '"+startDate+"' and '"+endDate+"'");
				list=dao.getObjectListPage("Orders", "where conpanyId="+users.getConpanyId()+" and title like '%"+name+"%' and createDate between '"+startDate+"' and '"+endDate+"' order by createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			}else{
				num2=dao.getObjectListNum("Orders", "where conpanyId="+users.getConpanyId()+" and title like '%"+name+"%' and ChanceId="+chanceid+" and createDate between '"+startDate+"' and '"+endDate+"'");
				list=dao.getObjectListPage("Orders", "where conpanyId="+users.getConpanyId()+" and title like '%"+name+"%' and ChanceId="+chanceid+" and createDate between '"+startDate+"' and '"+endDate+"' order by createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			}
			map.put("pagenum", num2/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}
		return map;
	}
	/**
	 * 删除订单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteOrder")
	@ResponseBody
	public Map deleteOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		try{
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Orders in=(Orders) dao.getObject(Long.parseLong(id), "Orders",users.getConpanyId());
			if(in!=null){
				if(in.getState()==0){
					dao.deleteOrder(in.getId(), users.getConpanyId());
					dao.delete(in);
					map.put("success", true);
					map.put("info", "删除成功");
				}else{
					map.put("success", false);
					map.put("info", "订单已入库，不能删除");
				}
			}else{
				map.put("success", false);
				map.put("info", "没有找到该订单。请重新查询是否订单被删除");
			}
		}catch (Exception e) {
			map.put("success", false);
			map.put("info", "删除失败，遇到未知问题");
		}
		return map;
	}
	/**
	 *	查看销售订单内容
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryOrderItem")
	@ResponseBody
	public Map queryOrderItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Orders in=(Orders) dao.getObject(Long.parseLong(id), "Orders", users.getConpanyId());
		if(in!=null){
			List<OrdersItem> list=dao.getOrderItems(Long.parseLong(id), users.getConpanyId());
			map.put("success", true);
			map.put("data", list);
			map.put("obj",in);
		}else{
			map.put("success", false);
			map.put("info", "没有找到该订单。请重新查询是否订单被删除");
		}
		return map;
	}
	/**
	 *入库销售订单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/OrderInStore")
	@ResponseBody
	public Map OrderInStore(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		Orders in=(Orders) dao.getObject(Long.parseLong(id), "Orders",users.getConpanyId());
		if(in!=null){
			String msg="";
			double conutScore=0;
			double countPrice=0;
			StringBuffer orderContentMessage=new StringBuffer();
			if(in.getState()==0){
				List<OrdersItem> list=dao.getOrderItems(Long.parseLong(id), users.getConpanyId());
				for(int i=0;i<list.size();i++){
					OrdersItem dto=list.get(i);
					GoodsTable goods=dao.getGoods(dto.getGoodsName(), dto.getGoodsType(), dto.getGoodsModel(), users.getConpanyId());
					if(goods==null){
						continue;
					}
					StoreHouse sh=(StoreHouse) dao.getObject(dto.getGoodsToStorehouseId(), "StoreHouse",users.getConpanyId());
					if(sh==null){
						map.put("success", false);
						map.put("info", "请查看是否有货物出库仓库未选择");
						return map;
					}
					if(dto.getGoodsSourceId()==0){
						map.put("success", false);
						map.put("info", "请查看是否有货物供货商未选择");
						return map;
					}
					StoreHouseDateLog log=dao.getStoreHouseDateLog(goods.getId(), sh.getId(),users.getConpanyId());
					if(log.getNum()<dto.getGoodsNum()){
						if(msg.equals("")){
							msg="未入库：<br/>";
						}
						msg=msg+"在仓库：《"+log.getStoreHoseName()+"》中，货物：《"+log.getGoodsName()+"》的库存量只有："+log.getNum()+"了，无法出库的.<br/>";
					}
				}
				ChanceList chanceobj=(ChanceList) dao.getObject(in.getChanceId(), "ChanceList", users.getConpanyId());
				LinkManList lk=(LinkManList) dao.getObject(in.getLinkmanId(), "LinkManList",users.getConpanyId());
				if(msg.equals("")){
					for(int i=0;i<list.size();i++){
						OrdersItem orderitem=list.get(i);
						GoodsTable goods=new GoodsTable();
						goods.setCodeid("");
						goods.setConpanyId(users.getConpanyId());
						goods.setGoodsModel(orderitem.getGoodsModel());
						goods.setGoodsName(orderitem.getGoodsName());
						goods.setGoodsNum(orderitem.getGoodsNum());
						goods.setGoodsType(orderitem.getGoodsType());
						goods.setInPrice(orderitem.getGoodsinPrice());
						goods.setPrice(orderitem.getPrice());
						goods.setSalesNum(0);
						goods.setScore(orderitem.getScore());
						goods.setSpell(orderitem.getSpell());
						goods.setTotalInPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getGoodsinPrice()),5));
						goods.setTotalPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(), orderitem.getPrice()),5));
						goods=dao.addReduceGoods(goods);
						orderitem.setGoodsId(goods.getId());
						countPrice=DoubleUtil.add(countPrice, DoubleUtil.mul(orderitem.getPrice(), orderitem.getGoodsNum()));
						conutScore=DoubleUtil.round(DoubleUtil.mul(conutScore+orderitem.getScore(),orderitem.getGoodsNum()), 3);
						orderContentMessage.append("[名称-").append(orderitem.getGoodsName()).append(",数量-").append(orderitem.getGoodsNum()).append(",单价-").append(orderitem.getPrice()).append("]");
						dao.update(orderitem);
						goodsLog(users,goods, GoodsLog.ACTION_REDUCE, orderitem.getGoodsSourceId()+"", orderitem.getGoodsToStorehouseId()+"", orderitem.getGoodsNum()+"", orderitem.getGoodsinPrice()+"",chanceobj);
					}
					
					in.setInStoreUser(users.getId());
					in.setInStoreUserName(users.getTrueName());
					in.setState(1);
					
					
					if(chanceobj.getState()!=3){
						chanceobj.setState(3);
						dao.update(chanceobj);
						
					}
					Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), chanceobj.getCreateManId(), chanceobj.getCreayeManName());
					pf.setMyCreateChanceBuyNum(pf.getMyCreateChanceBuyNum()+1);
					pf.setMyCreateChanceBuyCountPrice(DoubleUtil.round(DoubleUtil.add(pf.getMyCreateChanceBuyCountPrice(), in.getCountPrice()),5));
					dao.update(pf);
					if(chanceobj.getNotesUserId()!=0){
						Performance pf2=dao.getPerformanceByToDayUser(users.getConpanyId(), chanceobj.getNotesUserId(), chanceobj.getNotesUserName());
						pf2.setToChanceBuyNum(pf2.getToChanceBuyNum()+1);
						pf2.setToChanceBuyCountPrice(DoubleUtil.round(DoubleUtil.add(pf2.getToChanceBuyCountPrice(),in.getCountPrice()), 5));
						dao.update(pf2);
					}
					dao.update(in);
					lk.setLinkManScore(DoubleUtil.round(DoubleUtil.add(lk.getLinkManMaxScore(),conutScore), 3));
					lk.setLinkManMaxScore(DoubleUtil.round(DoubleUtil.add(lk.getLinkManMaxScore(),conutScore), 3));
					List<VIPSet> listvip = dao.getVipSetList(users.getConpanyId());
					VIPSet vip = null;
					for (int i = 0; i < listvip.size(); i++) {
						if (listvip.get(i).getScore() <= lk.getLinkManMaxScore()) {
							if (vip != null) {
								if (vip.getScore() < listvip.get(i).getScore()) {
									vip = listvip.get(i);
								}
							} else {
								vip = listvip.get(i);
							}
						}
					}
					MessageSet set=dao.getMessageSet(users.getConpanyId());
					if (vip!=null&&lk.getVipidNum()!=vip.getId()) {
						lk.setVipidNum(vip.getId());
						lk.setVipLevel(vip.getName());
						lk.setVipMarks(vip.getMarks());
						if(dao.getAllowSendMessage(users.getConpanyId(), MessageSet.ADD_PRICE)||dao.getAllowSendMessage(users.getConpanyId(), MessageSet.REDUCE_PRICE)){
							if(lk.getLinkManPhone()!=null&&lk.getLinkManPhone().length()==11){
								if(orderContentMessage.length()>200){
									String content="vip等级已变动成为:"+lk.getVipLevel()+",订单号:"+in.getOrderNum()+","+orderContentMessage.substring(0, 200)+"..,总价："+countPrice+set.getQianMing();
									dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
								}else{
									String content="vip等级已变动成为:"+lk.getVipLevel()+",订单号:"+in.getOrderNum()+","+orderContentMessage.toString()+",总价："+countPrice+set.getQianMing();
									dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
								}
							}else{
								dao.update(lk);
								map.put("info", "入库成功,但是会员手机号不正确，无法发送信息");
								dao.update(in);
								map.put("success", true);
								map.put("orderId", in.getId());
								return map;
							}
						}
					}else{
						
						if(lk.getLinkManPhone()!=null&&lk.getLinkManPhone().length()==11){
							if(orderContentMessage.length()>200){
								String content="订单号:"+in.getOrderNum()+","+orderContentMessage.substring(0, 200)+"..,总价："+countPrice+"【"+set.getQianMing()+"】";
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}else{
								String content="订单号:"+in.getOrderNum()+","+orderContentMessage.toString()+",总价："+countPrice+"【"+set.getQianMing()+"】";
								dao.getSendMessage(lk.getLinkManPhone(),content, users.getConpanyId(), MessageSet.ADD_SCORE,false);
							}
						}else{
							map.put("info", "入库成功,但是会员手机号不正确，无法发送信息");
							dao.update(in);
							map.put("success", true);
							return map;
						}
					}
					dao.update(lk);
					map.put("success", true);
					map.put("info", "入库成功");
				}else{
					map.put("success", false);
					map.put("info", msg);
				}
			}else{
				map.put("success", false);
				map.put("info", "订单已入库，不能重复入库");
			}
		}else{
			map.put("success", false);
			map.put("info", "没有找到该订单。请重新查询是否订单被删除");
		}
		return map;
	}
	/**
	 *创建进货计划
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createInOrder")
	@ResponseBody
	public Map createInOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String Name=req.getParameter("name");
		String data=req.getParameter("data");
		String id=req.getParameter("id");
		String type=req.getParameter("type");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String orderNum="";
		InOrder order;
		System.out.println(data);
		List<InOrderDTO> list=DTOUtil.getOrderDTO(data);
		if(id.equals("")||id==null||id.equals("0")){
			order=new InOrder();
			order.setCreateDate(new Date());
			order.setName(Name);
			order.setState(0);
			order.setConpanyId(users.getConpanyId());
			dao.add(order);
			orderNum="HDINORDER_"+order.getId()+users.getConpanyId()+users.getId()+new Date().getTime();
			order.setOrderNum(orderNum);
			order.setCreateUser(users.getId());
			order.setCreateUserName(users.getTrueName());
			dao.update(order);
		}else{
			order=(InOrder) dao.getObject(Long.parseLong(id), "InOrder",users.getConpanyId());
			if(order.getState()==0){
			orderNum=order.getOrderNum();
			dao.deleteInOrder(Long.parseLong(id),users.getConpanyId());
			order.setName(Name);
			dao.update(order);
			}else{
				map.put("success", false);
				map.put("info", "该订单已入库，不能修改");
				return map;
			}
		}
		for(int i=0;i<list.size();i++){
			InOrderDTO dto=list.get(i);
			InOrderItem orderitem=new InOrderItem();
			GoodsTable goods=dao.getGoods(dto.getName(), dto.getType(), dto.getModel(), users.getConpanyId());
			//保存订单项
			if(goods==null){
				orderitem.setCodeid("");
				orderitem.setGoodsinPrice(dto.getInprice());
				orderitem.setGoodsModel(dto.getModel());
				orderitem.setCreateDate(new Date());
				orderitem.setGoodsId(0);
				orderitem.setGoodsName(dto.getName());
				orderitem.setGoodsNum(dto.getNum());
				GoodsSource gs=dao.getGoodsSource(dto.getGoodRource(), users.getConpanyId(), false);
				if(gs==null){
					 gs=dao.getGoodsSource("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsSourceId(gs.getId());
				orderitem.setGoodsSourceName(gs.getName());
				StoreHouse sh=(StoreHouse) dao.getObject(dto.getStoreHouse(), "StoreHouse",users.getConpanyId());
				if(sh==null){
					sh=dao.getStoreHouse("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsToStorehouseId(sh.getId());
				orderitem.setGoodsToStorehouseName(sh.getName());
				orderitem.setGoodsType(dto.getType());
				orderitem.setInOrderId(order.getId());
				orderitem.setPrice(dto.getPrice());
				orderitem.setScore(dto.getScore());
				orderitem.setSpell(dto.getSpell());
				orderitem.setMarks(dto.getMarks());
			}else{
				orderitem.setCodeid(goods.getCodeid());
				orderitem.setGoodsinPrice(dto.getInprice());
				orderitem.setGoodsModel(goods.getGoodsModel());
				orderitem.setCreateDate(new Date());
				orderitem.setGoodsId(goods.getId());
				orderitem.setGoodsName(goods.getGoodsName());
				orderitem.setGoodsNum(dto.getNum());
				GoodsSource gs=dao.getGoodsSource(dto.getGoodRource(), users.getConpanyId(), false);
				if(gs==null){
					 gs=dao.getGoodsSource("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsSourceId(gs.getId());
				orderitem.setGoodsSourceName(gs.getName());
				StoreHouse sh=(StoreHouse) dao.getObject(dto.getStoreHouse(), "StoreHouse",users.getConpanyId());
				if(sh==null){
					sh=dao.getStoreHouse("系统默认", users.getConpanyId(), true);
				}
				orderitem.setGoodsToStorehouseId(sh.getId());
				orderitem.setGoodsToStorehouseName(sh.getName());
				orderitem.setGoodsType(goods.getGoodsType());
				orderitem.setInOrderId(order.getId());
				orderitem.setPrice(goods.getPrice());
				orderitem.setScore(goods.getScore());
				orderitem.setSpell(goods.getSpell());
				orderitem.setMarks(dto.getMarks());
			}
			orderitem.setConpanyId(users.getConpanyId());
			dao.add(orderitem);
			//入库并写入库日志
			if(type.equals("store")){
					goods=new GoodsTable();
					goods.setCodeid("");
					goods.setConpanyId(users.getConpanyId());
					goods.setGoodsModel(orderitem.getGoodsModel());
					goods.setGoodsName(orderitem.getGoodsName());
					goods.setGoodsNum(orderitem.getGoodsNum());
					goods.setGoodsType(orderitem.getGoodsType());
					goods.setInPrice(orderitem.getGoodsinPrice());
					goods.setPrice(orderitem.getPrice());
					goods.setSalesNum(0);
					goods.setScore(orderitem.getScore());
					goods.setSpell(orderitem.getSpell());
					goods.setTotalInPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getGoodsinPrice()), 5));
					goods.setTotalPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getPrice()), 5));
					goods=dao.addGoods(goods);
					goodsLog(users,goods, GoodsLog.ACTION_ADD, orderitem.getGoodsSourceId()+"", orderitem.getGoodsToStorehouseId()+"", orderitem.getGoodsNum()+"", orderitem.getGoodsinPrice()+"",null);
					
			}
		}
		if(type.equals("store")){
			order.setState(1);
			order.setInStoreUser(users.getId());
			order.setInStoreUserName(users.getTrueName());
			dao.update(order);
		}
		map.put("success", true);
		map.put("orderNum", orderNum);
		map.put("orderId", order.getId());
		return map;
	}
	/**
	 *	查看订单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryInOrder")
	@ResponseBody
	public Map queryInOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String num=req.getParameter("num");
		String name=req.getParameter("name");
		String endDate=req.getParameter("endDate");
		String startDate=req.getParameter("startDate");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		
		if(num!=null){
			if(num.equals("0")){
				map.put("success", false);
				map.put("info", "请填写编号");
				return map;
			}
			long num2=dao.getObjectListNum("InOrder", "where conpanyId="+users.getConpanyId()+" and orderNum = '"+num+"'");
			List list=dao.getObjectListPage("InOrder", "where conpanyId="+users.getConpanyId()+" and orderNum = '"+num+"'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num2/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}else{
			if(name==null){
				name="";
			}
			if(startDate==null||startDate.trim().equals("")){
				startDate="1988-01-01";
			}
			if(endDate==null||endDate.trim().equals("")){
				endDate=DateUtil.formatDateYYYY_MM_DD(new Date());
			}
			Date end=DateUtil.toDateType(endDate);
			Calendar calend=Calendar.getInstance();
			calend.setTime(end);
			calend.add(Calendar.DAY_OF_MONTH, 1);
			endDate=DateUtil.formatDateYYYY_MM_DD(calend.getTime());
			long num2=dao.getObjectListNum("InOrder", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%' and createDate between '"+startDate+"' and '"+endDate+"'");
			List list=dao.getObjectListPage("InOrder", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%' and createDate between '"+startDate+"' and '"+endDate+"' order by createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num2/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}
		return map;
	}
	/**
	 *	查看订单内容
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryInOrderItem")
	@ResponseBody
	public Map queryInOrderItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		InOrder in=(InOrder) dao.getObject(Long.parseLong(id), "InOrder", users.getConpanyId());
		if(in!=null){
			List<InOrderItem> list=dao.getInOrderItems(Long.parseLong(id), users.getConpanyId());
			map.put("success", true);
			map.put("data", list);
			map.put("obj",in);
		}else{
			map.put("success", false);
			map.put("info", "没有找到该订单。请重新查询是否订单被删除");
		}
		return map;
	}
	/**
	 *删除进货计划
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteInOrder")
	@ResponseBody
	public Map deleteInOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		try{
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		InOrder in=(InOrder) dao.getObject(Long.parseLong(id), "InOrder",users.getConpanyId());
			if(in!=null){
				if(in.getState()==0){
					dao.deleteInOrder(in.getId(), users.getConpanyId());
					dao.delete(in);
					map.put("success", true);
					map.put("info", "删除成功");
				}else{
					map.put("success", false);
					map.put("info", "订单已入库，不能删除");
				}
			}else{
				map.put("success", false);
				map.put("info", "没有找到该订单。请重新查询是否订单被删除");
			}
		}catch (Exception e) {
			map.put("success", false);
			map.put("info", "删除失败，遇到未知问题");
		}
		return map;
	}
	/**
	 *入库进货单
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/InOrderInStore")
	@ResponseBody
	public Map InOrderInStore(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		InOrder in=(InOrder) dao.getObject(Long.parseLong(id), "InOrder",users.getConpanyId());
		if(in!=null){
			if(in.getState()==0){
				List<InOrderItem> list=dao.getInOrderItems(Long.parseLong(id), users.getConpanyId());
				for(int i=0;i<list.size();i++){
					InOrderItem orderitem=list.get(i);
					GoodsTable goods=new GoodsTable();
					goods.setCodeid("");
					goods.setConpanyId(users.getConpanyId());
					goods.setGoodsModel(orderitem.getGoodsModel());
					goods.setGoodsName(orderitem.getGoodsName());
					goods.setGoodsNum(orderitem.getGoodsNum());
					goods.setGoodsType(orderitem.getGoodsType());
					goods.setInPrice(orderitem.getGoodsinPrice());
					goods.setPrice(orderitem.getPrice());
					goods.setSalesNum(0);
					goods.setScore(orderitem.getScore());
					goods.setSpell(orderitem.getSpell());
					goods.setTotalInPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getGoodsinPrice()), 5));
					goods.setTotalPrice(DoubleUtil.round(DoubleUtil.mul(orderitem.getGoodsNum(),orderitem.getPrice()), 5));
					goods=dao.addGoods(goods);
					goodsLog(users,goods, GoodsLog.ACTION_ADD, orderitem.getGoodsSourceId()+"", orderitem.getGoodsToStorehouseId()+"", orderitem.getGoodsNum()+"", orderitem.getGoodsinPrice()+"",null);
				}
				in.setInStoreUser(users.getId());
				in.setInStoreUserName(users.getTrueName());
				in.setState(1);
				dao.update(in);
				map.put("success", true);
				map.put("info", "入库成功");
			}else{
				map.put("success", false);
				map.put("info", "订单已入库，不能重复入库");
			}
		}else{
			map.put("success", false);
			map.put("info", "没有找到该订单。请重新查询是否订单被删除");
		}
		return map;
	}
	/**
	 *添加供货商
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addGoodSource")
	@ResponseBody
	public Map addGoodSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String name=req.getParameter("goodsSoucename");
		String address=req.getParameter("address");
		GoodsSource s=new GoodsSource();
		s.setAddress(address);
		s.setName(name);
		s.setConpanyId(users.getConpanyId());
		dao.add(s);
		map.put("info", "添加成功");
		map.put("success", true);
		return map;
	}
	/**
	 *	查找供货商
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryGoodsSource")
	@ResponseBody
	public Map queryGoodsSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("name");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("GoodsSource", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'");
		List list=dao.getObjectListPage("GoodsSource", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 *	删除供货商
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteGoodsSource")
	@ResponseBody
	public Map deleteGoodsSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsSource s=(GoodsSource) dao.getObject(Long.parseLong(id), "GoodsSource",users.getConpanyId());
		if(s!=null){
			dao.deleteList("GoodsSourceLinkMan", "where goodsSourceid="+id);
			dao.delete(s);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除失败，该供货商不存在");
		}
		return map;
	}
	/**
	 *	获取单个
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGoodsSource")
	@ResponseBody
	public Map getGoodsSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsSource s=(GoodsSource) dao.getObject(Long.parseLong(id), "GoodsSource",users.getConpanyId());
		if(s!=null){
			map.put("success", true);
			map.put("obj", s);
		}else{
			map.put("success", false);
			map.put("info", "该供货商不存在");
		}
		return map;
	}
	/**
	 *更新供货商
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateGoodSource")
	@ResponseBody
	public Map updateGoodSource(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		String name=req.getParameter("goodsSoucename");
		String address=req.getParameter("address");
		GoodsSource s=(GoodsSource) dao.getObject(Long.parseLong(id), "GoodsSource",users.getConpanyId());
		if(users.getConpanyId()==s.getConpanyId()){
			s.setAddress(address);
			s.setName(name);
			dao.update(s);
			map.put("info", "更新成功");
			map.put("success", true);
		}else{
			map.put("info", "更新失败，找不到该数据");
			map.put("success", false);
		}
		
		return map;
	}
	/**
	 * 查询联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryGoodsSourceLinkman")
	@ResponseBody
	public Map queryGoodsSourceLinkman(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("name");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("GoodsSourceLinkMan", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'");
		List list=dao.getObjectListPage("GoodsSourceLinkMan", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 *添加供货商联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addGoodSourceLinkMan")
	@ResponseBody
	public Map addGoodSourceLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		String linkManBirthday=req.getParameter("linkManBirthday");
		String linkname=req.getParameter("linkname");
		String phone=req.getParameter("phone");
		GoodsSource gs=(GoodsSource) dao.getObject(Long.parseLong(id), "GoodsSource",users.getConpanyId());
		if(gs!=null){
			if(gs.getConpanyId()==users.getConpanyId()){
				GoodsSourceLinkMan s=new GoodsSourceLinkMan();
				s.setConpanyId(users.getConpanyId());
				s.setGoodsSourceid(gs.getId());
				s.setGoodsSourceName(gs.getName());
				s.setLinkManBirthday(linkManBirthday==null||linkManBirthday.equals("联系人生日")?null:DateUtil.toDateType(linkManBirthday));
				s.setName(linkname);
				s.setPhone(phone);
				dao.add(s);
				map.put("info", "添加成功");
				map.put("success", true);
			}else{
				map.put("info", "没有该数据");
				map.put("success", false);
			}
		}else{
			map.put("info", "没有该数据");
			map.put("success", false);
		}
		return map;
	}
	/**
	 *更新供货商联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateGoodSourceLinkMan")
	@ResponseBody
	public Map updateGoodSourceLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		String linkManBirthday=req.getParameter("linkManBirthday");
		String linkname=req.getParameter("linkname");
		String phone=req.getParameter("phone");
		GoodsSourceLinkMan s=(GoodsSourceLinkMan) dao.getObject(Long.parseLong(id), "GoodsSourceLinkMan",users.getConpanyId());
		if(users.getConpanyId()==s.getConpanyId()){
			s.setLinkManBirthday(linkManBirthday==null||linkManBirthday.equals("联系人生日")||linkManBirthday.equals("未设置或未结束")?null:DateUtil.toDateType(linkManBirthday));
			s.setName(linkname);
			s.setPhone(phone);
			dao.update(s);
			map.put("info", "更新成功");
			map.put("success", true);
		}else{
			map.put("info", "更新失败，找不到该数据");
			map.put("success", false);
		}
		
		return map;
	}
	/**
	 *	删除供货商联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteGoodsSourceLinkman")
	@ResponseBody
	public Map deleteGoodsSourceLinkman(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsSourceLinkMan s=(GoodsSourceLinkMan) dao.getObject(Long.parseLong(id), "GoodsSourceLinkMan",users.getConpanyId());
		if(s!=null){
			dao.delete(s);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除失败，该联系人不存在");
		}
		return map;
	}
	/**
	 *	获取单个
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGoodsSourceLinkMan")
	@ResponseBody
	public Map getGoodsSourceLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsSourceLinkMan s=(GoodsSourceLinkMan) dao.getObject(Long.parseLong(id), "GoodsSourceLinkMan",users.getConpanyId());
		if(s!=null){
			map.put("success", true);
			map.put("obj", s);
		}else{
			map.put("success", false);
			map.put("info", "该联系人不存在");
		}
		return map;
	}
	/**
	 * 查询仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryStoreHouse")
	@ResponseBody
	public Map queryStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("name");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("StoreHouse", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'");
		List list=dao.getObjectListPage("StoreHouse", "where conpanyId="+users.getConpanyId()+" and name like '%"+name+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 创建仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createStoreHouse")
	@ResponseBody
	public Map createStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("Storehouseame");
		String address=req.getParameter("address");
		String tel=req.getParameter("tel");
		String userid=req.getParameter("userid");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		ConpanyUser usersIds=(ConpanyUser)dao.getObject(Long.parseLong(userid), "ConpanyUser",users.getConpanyId());
		if(usersIds!=null){
			StoreHouse s=new StoreHouse();
			s.setName(name);
			s.setAddress(address);
			s.setManagerUserId(usersIds.getId());
			s.setManagerUserName(usersIds.getTrueName());
			s.setTal(tel);
			s.setConpanyId(users.getConpanyId());
			dao.add(s);
			map.put("info", "添加成功");
			map.put("success", true);
		}else{
			map.put("info", "您选定的保管人不存在");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 更新仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateStoreHouse")
	@ResponseBody
	public Map updateStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String name=req.getParameter("Storehouseame");
		String address=req.getParameter("address");
		String tel=req.getParameter("tel");
		String userid=req.getParameter("userid");
		String id=req.getParameter("id");
		StoreHouse s=(StoreHouse) dao.getObject(Long.parseLong(id), "StoreHouse",users.getConpanyId());
		if(s!=null||users.getConpanyId()==s.getConpanyId()){
			ConpanyUser usersIds=(ConpanyUser)dao.getObject(Long.parseLong(userid), "ConpanyUser",users.getConpanyId());
			if(usersIds!=null){
				s.setName(name);
				s.setAddress(address);
				s.setManagerUserId(usersIds.getId());
				s.setManagerUserName(usersIds.getTrueName());
				s.setTal(tel);
				s.setConpanyId(users.getConpanyId());
				dao.update(s);
				map.put("info", "添加成功");
				map.put("success", true);
			}else{
				map.put("info", "您选定的保管人不存在");
				map.put("success", false);
			}
		}else{
			map.put("info", "更新失败，找不到该数据");
			map.put("success", false);
		}
		return map;
	}
	/**
	 *删除仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteStoreHouse")
	@ResponseBody
	public Map deleteStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		StoreHouse s=(StoreHouse) dao.getObject(Long.parseLong(id), "StoreHouse",users.getConpanyId());
		if(s!=null){
			dao.delete(s);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除失败，该联系人不存在");
		}
		return map;
	}
	/**
	 *合并仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/StoreHouseToStoreHouse")
	@ResponseBody
	public Map StoreHouseToStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String storeHouseId=req.getParameter("storeHouseId");
		String toStoreHouseId=req.getParameter("toStoreHouseId");
		return map;
	}
	/**
	 *	获取单个仓库信息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getStoreHouse")
	@ResponseBody
	public Map getStoreHouse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		StoreHouse s=(StoreHouse) dao.getObject(Long.parseLong(id), "StoreHouse",users.getConpanyId());
		if(s!=null){
			map.put("success", true);
			map.put("obj", s);
		}else{
			map.put("success", false);
			map.put("info", "该联系人不存在");
		}
		return map;
	}
	/**
	 * 查询仓库
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryGoods")
	@ResponseBody
	public Map queryGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String codeid=req.getParameter("codeid");

		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		if(codeid==null){
			String name=req.getParameter("name");
			String spell=req.getParameter("spell_query");
			spell=spell.trim();
			if(!spell.equals("")){
				spell=spell.toLowerCase();
			}
			long num=dao.getObjectListNum("GoodsTable", "where conpanyId="+users.getConpanyId()+" and goodsName like '%"+name+"%'"+" and spell like '%"+spell+"%'");
			List list=dao.getObjectListPage("GoodsTable", "where conpanyId="+users.getConpanyId()+" and goodsName like '%"+name+"%'"+" and spell like '%"+spell+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}else{
			long num=dao.getObjectListNum("GoodsTable", "where conpanyId="+users.getConpanyId()+" and codeid = '"+codeid+"'");
			List list=dao.getObjectListPage("GoodsTable", "where conpanyId="+users.getConpanyId()+" and codeid = '"+codeid+"'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}
		return map;
	}
	/**
	 *	获取单个货物信息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGoods")
	@ResponseBody
	public Map getGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		GoodsTable s=(GoodsTable) dao.getObject(Long.parseLong(id), "GoodsTable",users.getConpanyId());
		if(s!=null){
			map.put("success", true);
			map.put("obj", s);
		}else{
			map.put("success", false);
			map.put("info", "该商品不存在");
		}
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
			goodslog.setSalesNum(Float.parseFloat(goodsNum));
			GoodsSource gs=(GoodsSource) dao.getObject(Long.parseLong(goodsSourceId), "GoodsSource",user.getConpanyId());
			goodslog.setGoodsSourceId(gs.getId());
			goodslog.setGoodsSourceName(gs.getName());
			StoreHouse sh=(StoreHouse) dao.getObject(Long.parseLong(storehouseId), "StoreHouse",user.getConpanyId());
			goodslog.setGoodsToStorehouseId(sh.getId());
			goodslog.setGoodsToStorehouseName(sh.getName());
			StoreHouseDateLog log=dao.getStoreHouseDateLog(t.getId(), sh.getId(),user.getConpanyId());
			log.setNum(DoubleUtil.sub(log.getNum(), Float.parseFloat(goodsNum)));
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
