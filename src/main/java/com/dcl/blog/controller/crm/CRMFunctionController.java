package com.dcl.blog.controller.crm;

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

import com.dcl.blog.model.ChanceFlow;
import com.dcl.blog.model.ChanceList;
import com.dcl.blog.model.ChanceListLog;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsSourceLinkMan;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.Message;
import com.dcl.blog.model.Notes;
import com.dcl.blog.model.NotesInfo;
import com.dcl.blog.model.Performance;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.model.UserTableLinkLinkMan;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/crm/function")
public class CRMFunctionController {
	private static final Logger logger = LoggerFactory
			.getLogger(CRMFunctionController.class);
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
	 * 创建机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createChance")
	@ResponseBody
	public Map createChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		try{
			
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String CustomerName=req.getParameter("CustomerName");
			String CustomerAddress=req.getParameter("CustomerAddress");
			String CustomerType=req.getParameter("CustomerType");
			String CustomerLevel=req.getParameter("CustomerLevel");
			String CustomerMark=req.getParameter("CustomerMark");
			String CreateManMark=req.getParameter("CreateManMark");
			String NotesUserId=req.getParameter("NotesUserId");
			String State=req.getParameter("State");
			ChanceList chance=new ChanceList();
			chance.setConpanyId(users.getConpanyId());
			chance.setCreateManId(users.getId());
			chance.setCreayeManName(users.getTrueName());
			chance.setCreateManMark(CreateManMark);
			chance.setCustomerAddress(CustomerAddress);
			chance.setCustomerType(CustomerType);
			chance.setCustomerName(CustomerName);
			chance.setCustomerLevel(Integer.parseInt(CustomerLevel));
			chance.setCustomerMark(CustomerMark);
			chance.setCreateDate(new Date());
			chance.setState(Integer.parseInt(State));
			if(!NotesUserId.equals("0")){
				ConpanyUser notesuser=(ConpanyUser) dao.getObject(Long.parseLong(NotesUserId), "ConpanyUser",users.getConpanyId());
				if(notesuser!=null){
					chance.setNotesUserId(notesuser.getId());
					chance.setNotesUserName(notesuser.getTrueName());
				}
					Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), notesuser.getId(), notesuser.getTrueName());
					pf.setToChanceNum(pf.getToChanceNum()+1);
					dao.update(pf);
			}
			dao.add(chance);
			ChanceListLog log=new ChanceListLog();
			log.setConpanyId(users.getConpanyId());
			log.setCreateManId(users.getId());
			log.setCreayeManName(users.getTrueName());
			log.setCustomerAddress(CustomerAddress);
			log.setCustomerType(CustomerType);
			log.setCustomerName(CustomerName);
			log.setCustomerLevel(Integer.parseInt(CustomerLevel));
			log.setCreateDate(new Date());
			log.setState(Integer.parseInt(State));
			log.setChanceId(chance.getId());
			if(!NotesUserId.equals("0")){
				ConpanyUser notesuser=(ConpanyUser) dao.getObject(Long.parseLong(NotesUserId), "ConpanyUser",users.getConpanyId());
				if(notesuser!=null){
					log.setNotesUserId(notesuser.getId());
					log.setNotesUserName(notesuser.getTrueName());
				}
			}
			log.setCreateLogDate(new Date());
			log.setEventType("state");
			dao.add(log);
			ChanceListLog log2=new ChanceListLog();
			log2.setConpanyId(users.getConpanyId());
			log2.setCreateManId(users.getId());
			log2.setCreayeManName(users.getTrueName());
			log2.setCustomerAddress(CustomerAddress);
			log2.setCustomerType(CustomerType);
			log2.setCustomerName(CustomerName);
			log2.setCustomerLevel(Integer.parseInt(CustomerLevel));
			log2.setCreateDate(new Date());
			log2.setState(Integer.parseInt(State));
			log2.setChanceId(chance.getId());
			if(!NotesUserId.equals("0")){
				ConpanyUser notesuser=(ConpanyUser) dao.getObject(Long.parseLong(NotesUserId), "ConpanyUser",users.getConpanyId());
				if(notesuser!=null){
					log2.setNotesUserId(notesuser.getId());
					log2.setNotesUserName(notesuser.getTrueName());
					Message mes=new Message();
					mes.setConpanyId(notesuser.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(chance.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(notesuser.getId());
					mes.setUsertrueName(notesuser.getTrueName());
					mes.setTitle("您被指派了新客户机会");
					mes.setTypes(1);
					dao.add(mes);
				}
			}
			log2.setCreateLogDate(new Date());
			log2.setEventType("create");
			dao.add(log2);
			Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), users.getId(), users.getTrueName());
			pf.setMyCreateChanceNum(pf.getMyCreateChanceNum()+1);
			dao.update(pf);
			map.put("success", true);
			map.put("info", "添加成功");
		}catch (Exception e) {
			map.put("success", false);
			map.put("info", "系统异常，请重新尝试");
		}
			return map;

	}
	/**
	 * 查询自己创建的机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryMyChance")
	@ResponseBody
	public Map queryMyChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String ChanceName = req.getParameter("ChanceName");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("ChanceList", "where createManId="+users.getId()+" and customerName like '%"+ChanceName+"%'");
		List list=dao.getObjectListPage("ChanceList", "where createManId="+users.getId()+" and customerName like '%"+ChanceName+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 查询分配给自己的机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryToMyChance")
	@ResponseBody
	public Map queryToMyChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String ChanceName = req.getParameter("ChanceName");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("ChanceList", "where notesUserId="+users.getId()+" and customerName like '%"+ChanceName+"%'");
		List list=dao.getObjectListPage("ChanceList", "where notesUserId="+users.getId()+" and customerName like '%"+ChanceName+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 由自己参与或创建的客户查询
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryMyCustemor")
	@ResponseBody
	public Map queryMyCustemor(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String ChanceName = req.getParameter("ChanceName");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("ChanceList", "where (notesUserId="+users.getId()+" or createManId="+users.getId()+") and state=3 and customerName like '%"+ChanceName+"%'");
		List list=dao.getObjectListPage("ChanceList", "where (notesUserId="+users.getId()+" or createManId="+users.getId()+") and state=3 and customerName like '%"+ChanceName+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 查询所有机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryAllChance")
	@ResponseBody
	public Map queryAllChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String ChanceName = req.getParameter("ChanceName");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("ChanceList", "where ConpanyId="+users.getConpanyId()+" and customerName like '%"+ChanceName+"%'");
		List list=dao.getObjectListPage("ChanceList", "where ConpanyId="+users.getConpanyId()+" and customerName like '%"+ChanceName+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 获取一个机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getChance")
	@ResponseBody
	public Map getChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String ChanceName = req.getParameter("ChanceName");
		List list=dao.getObjectList("ChanceList", "where id="+ChanceName+" and conpanyId="+users.getConpanyId());
		Iterator i =list.iterator();
		if(i.hasNext()){
			map.put("success", true);
			map.put("obj", i.next());
		}else{
			map.put("success", false);
			map.put("info", "没有找到该数据");
		}
		
		return map;
	}
	/**
	 * 修改机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateChance")
	@ResponseBody
	public Map updateChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String CustomerName=req.getParameter("CustomerName");
		String CustomerAddress=req.getParameter("CustomerAddress");
		String CustomerType=req.getParameter("CustomerType");
		String CustomerLevel=req.getParameter("CustomerLevel");
		String CustomerMark=req.getParameter("CustomerMark");
		String CreateManMark=req.getParameter("CreateManMark");
		String NotesUserId=req.getParameter("NotesUserId");
		String State=req.getParameter("State");
		String ChanceId=req.getParameter("ChanceId");
		ChanceListLog address=null;
		ChanceListLog level=null;
		ChanceListLog name=null;
		ChanceListLog type=null;
		ChanceListLog States=null;
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());
		if(cl!=null){
			cl.setCreateManMark(CreateManMark);
			if(!cl.getCustomerAddress().equals(CustomerAddress)){
				address=new ChanceListLog();
				address.setConpanyId(cl.getConpanyId());
				address.setCreateManId(cl.getCreateManId());
				address.setCreayeManName(cl.getCreayeManName());
				address.setCustomerAddress(CustomerAddress);
				address.setCustomerType(cl.getCustomerType());
				address.setCustomerName(cl.getCustomerName());
				address.setCustomerLevel(cl.getCustomerLevel());
				address.setCreateDate(cl.getCreateDate());
				address.setState(cl.getState());
				address.setCreateLogDate(new Date());
				address.setEventType("address");
				address.setLastBuy(cl.getLastBuy());
				address.setNotesUserId(cl.getNotesUserId());
				address.setNotesUserName(cl.getNotesUserName());
				address.setChanceId(cl.getId());
				cl.setCustomerAddress(CustomerAddress);
			}
			if(cl.getCustomerLevel()!=Integer.parseInt(CustomerLevel)){
				level=new ChanceListLog();
				level.setConpanyId(cl.getConpanyId());
				level.setCreateManId(cl.getCreateManId());
				level.setCreayeManName(cl.getCreayeManName());
				level.setCustomerAddress(cl.getCustomerAddress());
				level.setCustomerType(cl.getCustomerType());
				level.setCustomerName(cl.getCustomerName());
				level.setCustomerLevel(Integer.parseInt(CustomerLevel));
				level.setCreateDate(cl.getCreateDate());
				level.setState(cl.getState());
				level.setCreateLogDate(new Date());
				level.setEventType("level");
				level.setLastBuy(cl.getLastBuy());
				level.setNotesUserId(cl.getNotesUserId());
				level.setNotesUserName(cl.getNotesUserName());
				level.setChanceId(cl.getId());
				cl.setCustomerLevel(Integer.parseInt(CustomerLevel));
			}
			cl.setCustomerMark(CustomerMark);
			if(!cl.getCustomerName().equals(CustomerName)){
				name=new ChanceListLog();
				name.setConpanyId(cl.getConpanyId());
				name.setCreateManId(cl.getCreateManId());
				name.setCreayeManName(cl.getCreayeManName());
				name.setCustomerAddress(cl.getCustomerAddress());
				name.setCustomerType(cl.getCustomerType());
				name.setCustomerName(CustomerName);
				name.setCustomerLevel(cl.getCustomerLevel());
				name.setCreateDate(cl.getCreateDate());
				name.setState(cl.getState());
				name.setCreateLogDate(new Date());
				name.setEventType("name");
				name.setLastBuy(cl.getLastBuy());
				name.setNotesUserId(cl.getNotesUserId());
				name.setNotesUserName(cl.getNotesUserName());
				name.setChanceId(cl.getId());
				cl.setCustomerName(CustomerName);
			}
			if(!cl.getCustomerType().equals(CustomerType)){
				type=new ChanceListLog();
				type.setConpanyId(cl.getConpanyId());
				type.setCreateManId(cl.getCreateManId());
				type.setCreayeManName(cl.getCreayeManName());
				type.setCustomerAddress(cl.getCustomerAddress());
				type.setCustomerType(CustomerType);
				type.setCustomerName(cl.getCustomerName());
				type.setCustomerLevel(cl.getCustomerLevel());
				type.setCreateDate(cl.getCreateDate());
				type.setState(cl.getState());
				type.setCreateLogDate(new Date());
				type.setEventType("type");
				type.setLastBuy(cl.getLastBuy());
				type.setNotesUserId(cl.getNotesUserId());
				type.setNotesUserName(cl.getNotesUserName());
				type.setChanceId(cl.getId());
				cl.setCustomerType(CustomerType);
			}
			if(cl.getState()!=Integer.parseInt(State)){
				States=new ChanceListLog();
				States.setConpanyId(cl.getConpanyId());
				States.setCreateManId(cl.getCreateManId());
				States.setCreayeManName(cl.getCreayeManName());
				States.setCustomerAddress(cl.getCustomerAddress());
				States.setCustomerType(cl.getCustomerType());
				States.setCustomerName(cl.getCustomerName());
				States.setCustomerLevel(cl.getCustomerLevel());
				States.setCreateDate(cl.getCreateDate());
				States.setState(Integer.parseInt(State));
				States.setCreateLogDate(new Date());
				States.setEventType("state");
				States.setLastBuy(cl.getLastBuy());
				States.setNotesUserId(cl.getNotesUserId());
				States.setNotesUserName(cl.getNotesUserName());
				States.setChanceId(cl.getId());
				cl.setState(Integer.parseInt(State));
			}
			dao.update(cl);
			
			if(States!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(States);
				mes.setTitle("被指派的机会修改了状态,修改者:"+users.getTrueName()+"修改为:"+(States.getState()==1?"普通任务":States.getState()==2?"优质机会":States.getState()==3?"已成客户":States.getState()==4?"已流失":States.getState()==5?"超过3个月无购物":""));
				mes2.setTitle("创建的机会修改了状态,修改者:"+users.getTrueName()+"修改为:"+(States.getState()==1?"普通任务":States.getState()==2?"优质机会":States.getState()==3?"已成客户":States.getState()==4?"已流失":States.getState()==5?"超过3个月无购物":""));
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(type!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(type);
				mes.setTitle("被指派的机会修改了类别,修改者:"+users.getTrueName()+"修改为:"+type.getCustomerType());
				mes2.setTitle("创建的机会修改了类别,修改者:"+users.getTrueName()+"修改为:"+type.getCustomerType());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(name!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(name);
				mes.setTitle("被指派的机会修改了名称,修改者:"+users.getTrueName()+"修改为:"+name.getCustomerName());
				mes2.setTitle("创建的机会修改了名称,修改者:"+users.getTrueName()+"修改为:"+name.getCustomerName());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(level!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(level);
				mes.setTitle("被指派的机会修改了等级,修改者:"+users.getTrueName()+"修改为:"+(States.getCustomerLevel()==1?"普通客户":States.getState()==2?"中等客户":States.getState()==3?"重级客户":States.getState()==4?"特级客户":""));
				mes2.setTitle("创建的机会修改了等级,修改者:"+users.getTrueName()+"修改为:"+(States.getCustomerLevel()==1?"普通客户":States.getState()==2?"中等客户":States.getState()==3?"重级客户":States.getState()==4?"特级客户":""));
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(address!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(address);
				mes.setTitle("被指派的机会修改了地址,修改者:"+users.getTrueName()+"修改为:"+address.getCustomerAddress());
				mes2.setTitle("创建的机会修改了地址,修改者:"+users.getTrueName()+"修改为:"+address.getCustomerAddress());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			
			map.put("success", true);
			map.put("info", "修改成功");
			
		}else{
			map.put("success", false);
			map.put("info", "修改失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
		}
		return map;
	}
	/**
	 * 修改用户自己创建的机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateMyCreateChance")
	@ResponseBody
	public Map updateMyCreateChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String CustomerName=req.getParameter("CustomerName");
		String CustomerAddress=req.getParameter("CustomerAddress");
		String CustomerType=req.getParameter("CustomerType");
		String CustomerLevel=req.getParameter("CustomerLevel");
		String CustomerMark=req.getParameter("CustomerMark");
		String CreateManMark=req.getParameter("CreateManMark");
		String NotesUserId=req.getParameter("NotesUserId");
		String State=req.getParameter("State");
		String ChanceId=req.getParameter("ChanceId");
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());
		ChanceListLog address=null;
		ChanceListLog level=null;
		ChanceListLog name=null;
		ChanceListLog type=null;
		ChanceListLog States=null;
		if(cl.getCreateManId()!=users.getId()){
			map.put("success", false);
			map.put("info", "修改失败，次机会不是您创造的您没有权利修改此机会");
			return map;
		}
		if(cl!=null){
			cl.setCreateManMark(CreateManMark);
			if(!cl.getCustomerAddress().equals(CustomerAddress)){
				address=new ChanceListLog();
				address.setConpanyId(cl.getConpanyId());
				address.setCreateManId(cl.getCreateManId());
				address.setCreayeManName(cl.getCreayeManName());
				address.setCustomerAddress(CustomerAddress);
				address.setCustomerType(cl.getCustomerType());
				address.setCustomerName(cl.getCustomerName());
				address.setCustomerLevel(cl.getCustomerLevel());
				address.setCreateDate(cl.getCreateDate());
				address.setState(cl.getState());
				address.setCreateLogDate(new Date());
				address.setEventType("address");
				address.setLastBuy(cl.getLastBuy());
				address.setNotesUserId(cl.getNotesUserId());
				address.setNotesUserName(cl.getNotesUserName());
				address.setChanceId(cl.getId());
				cl.setCustomerAddress(CustomerAddress);
			}
			if(cl.getCustomerLevel()!=Integer.parseInt(CustomerLevel)){
				level=new ChanceListLog();
				level.setConpanyId(cl.getConpanyId());
				level.setCreateManId(cl.getCreateManId());
				level.setCreayeManName(cl.getCreayeManName());
				level.setCustomerAddress(cl.getCustomerAddress());
				level.setCustomerType(cl.getCustomerType());
				level.setCustomerName(cl.getCustomerName());
				level.setCustomerLevel(Integer.parseInt(CustomerLevel));
				level.setCreateDate(cl.getCreateDate());
				level.setState(cl.getState());
				level.setCreateLogDate(new Date());
				level.setEventType("level");
				level.setLastBuy(cl.getLastBuy());
				level.setNotesUserId(cl.getNotesUserId());
				level.setNotesUserName(cl.getNotesUserName());
				level.setChanceId(cl.getId());
				cl.setCustomerLevel(Integer.parseInt(CustomerLevel));
			}
			cl.setCustomerMark(CustomerMark);
			if(!cl.getCustomerName().equals(CustomerName)){
				name=new ChanceListLog();
				name.setConpanyId(cl.getConpanyId());
				name.setCreateManId(cl.getCreateManId());
				name.setCreayeManName(cl.getCreayeManName());
				name.setCustomerAddress(cl.getCustomerAddress());
				name.setCustomerType(cl.getCustomerType());
				name.setCustomerName(CustomerName);
				name.setCustomerLevel(cl.getCustomerLevel());
				name.setCreateDate(cl.getCreateDate());
				name.setState(cl.getState());
				name.setCreateLogDate(new Date());
				name.setEventType("name");
				name.setLastBuy(cl.getLastBuy());
				name.setNotesUserId(cl.getNotesUserId());
				name.setNotesUserName(cl.getNotesUserName());
				name.setChanceId(cl.getId());
				cl.setCustomerName(CustomerName);

			}
			if(!cl.getCustomerType().equals(CustomerType)){
				type=new ChanceListLog();
				type.setConpanyId(cl.getConpanyId());
				type.setCreateManId(cl.getCreateManId());
				type.setCreayeManName(cl.getCreayeManName());
				type.setCustomerAddress(cl.getCustomerAddress());
				type.setCustomerType(CustomerType);
				type.setCustomerName(cl.getCustomerName());
				type.setCustomerLevel(cl.getCustomerLevel());
				type.setCreateDate(cl.getCreateDate());
				type.setState(cl.getState());
				type.setCreateLogDate(new Date());
				type.setEventType("type");
				type.setLastBuy(cl.getLastBuy());
				type.setNotesUserId(cl.getNotesUserId());
				type.setNotesUserName(cl.getNotesUserName());
				type.setChanceId(cl.getId());
				cl.setCustomerType(CustomerType);

			}
			if(cl.getState()!=Integer.parseInt(State)){
				States=new ChanceListLog();
				States.setConpanyId(cl.getConpanyId());
				States.setCreateManId(cl.getCreateManId());
				States.setCreayeManName(cl.getCreayeManName());
				States.setCustomerAddress(cl.getCustomerAddress());
				States.setCustomerType(cl.getCustomerType());
				States.setCustomerName(cl.getCustomerName());
				States.setCustomerLevel(cl.getCustomerLevel());
				States.setCreateDate(cl.getCreateDate());
				States.setState(Integer.parseInt(State));
				States.setCreateLogDate(new Date());
				States.setEventType("state");
				States.setLastBuy(cl.getLastBuy());
				States.setNotesUserId(cl.getNotesUserId());
				States.setNotesUserName(cl.getNotesUserName());
				States.setChanceId(cl.getId());
				cl.setState(Integer.parseInt(State));

			}
			dao.update(cl);
			
			if(States!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(States);
				mes.setTitle("被指派的机会修改了状态,修改者:"+users.getTrueName()+"修改为:"+(States.getState()==1?"普通任务":States.getState()==2?"优质机会":States.getState()==3?"已成客户":States.getState()==4?"已流失":States.getState()==5?"超过3个月无购物":""));
				mes2.setTitle("创建的机会修改了状态,修改者:"+users.getTrueName()+"修改为:"+(States.getState()==1?"普通任务":States.getState()==2?"优质机会":States.getState()==3?"已成客户":States.getState()==4?"已流失":States.getState()==5?"超过3个月无购物":""));
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(type!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(type);
				mes.setTitle("被指派的机会修改了类别,修改者:"+users.getTrueName()+"修改为:"+type.getCustomerType());
				mes2.setTitle("创建的机会修改了类别,修改者:"+users.getTrueName()+"修改为:"+type.getCustomerType());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(name!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(name);
				mes.setTitle("被指派的机会修改了名称,修改者:"+users.getTrueName()+"修改为:"+name.getCustomerName());
				mes2.setTitle("创建的机会修改了名称,修改者:"+users.getTrueName()+"修改为:"+name.getCustomerName());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(level!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(level);
							mes.setTitle("被指派的机会修改了等级,修改者:"+users.getTrueName()+"修改为:"+(States.getCustomerLevel()==1?"普通客户":States.getState()==2?"中等客户":States.getState()==3?"重级客户":States.getState()==4?"特级客户":""));
				mes2.setTitle("创建的机会修改了等级,修改者:"+users.getTrueName()+"修改为:"+(States.getCustomerLevel()==1?"普通客户":States.getState()==2?"中等客户":States.getState()==3?"重级客户":States.getState()==4?"特级客户":""));
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			if(address!=null){
				Message mes=new Message();
				Message mes2=new Message();
				dao.add(address);
				mes.setTitle("被指派的机会修改了地址,修改者:"+users.getTrueName()+"修改为:"+address.getCustomerAddress());
				mes2.setTitle("创建的机会修改了地址,修改者:"+users.getTrueName()+"修改为:"+address.getCustomerAddress());
				if(cl.getNotesUserId()!=0){
					mes.setConpanyId(cl.getConpanyId());
					mes.setContent(mes.getTitle());
					mes.setContentid(cl.getId());
					mes.setCreateDate(new Date());
					mes.setReades(false);
					mes.setUserid(cl.getNotesUserId());
					mes.setUsertrueName(cl.getNotesUserName());
					mes.setTypes(1);
					dao.add(mes);
				}
				mes2.setConpanyId(cl.getConpanyId());
				mes2.setContent(mes2.getTitle());
				mes2.setContentid(cl.getId());
				mes2.setCreateDate(new Date());
				mes2.setReades(false);
				mes2.setUserid(cl.getCreateManId());
				mes2.setUsertrueName(cl.getCreayeManName());
				mes2.setTypes(1);
				dao.add(mes2);
			}
			
			map.put("success", true);
			map.put("info", "修改成功");
		}else{
			map.put("success", false);
			map.put("info", "修改失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
		}
		return map;
	}
	/**
	 * 删除机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/DeleteChance")
	@ResponseBody
	public Map DeleteChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String ChanceId=req.getParameter("ChanceId");
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());

		if(cl!=null){
			List list=dao.getObjectList("LinkManList", "where chanceListId="+cl.getId());
			Iterator i=list.iterator();
			while(i.hasNext()){
				dao.delete(i.next());
			}
			dao.delete(cl);
			ChanceListLog log=new ChanceListLog();
			log.setConpanyId(cl.getConpanyId());
			log.setCreateManId(cl.getCreateManId());
			log.setCreayeManName(cl.getCreayeManName());
			log.setCustomerAddress(cl.getCustomerAddress());
			log.setCustomerType(cl.getCustomerType());
			log.setCustomerName(cl.getCustomerName());
			log.setCustomerLevel(cl.getCustomerLevel());
			log.setCreateDate(cl.getCreateDate());
			log.setState(cl.getState());
			log.setCreateLogDate(new Date());
			log.setNotesUserId(cl.getNotesUserId());
			log.setNotesUserName(cl.getNotesUserName());
			log.setLastBuy(cl.getLastBuy());
			log.setEventType("delete");
			log.setChanceId(cl.getId());
			dao.add(log);
			Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), cl.getCreateManId(), cl.getCreayeManName());
			pf.setMyCreateChanceNum(pf.getMyCreateChanceNum()-1);
			dao.update(pf);
			Message mes=new Message();
			if(cl.getNotesUserId()!=0){
				mes.setConpanyId(cl.getConpanyId());
				mes.setContent("分配的机会被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
				mes.setTitle("分配的机会被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
				mes.setContentid(cl.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(cl.getNotesUserId());
				mes.setUsertrueName(cl.getNotesUserName());
				mes.setTypes(0);
				dao.add(mes);
				mes=new Message();
			}
			mes.setConpanyId(cl.getConpanyId());
			mes.setContent("创建的任务被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
			mes.setTitle("创建的任务被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
			mes.setContentid(cl.getId());
			mes.setCreateDate(new Date());
			mes.setReades(false);
			mes.setUserid(cl.getCreateManId());
			mes.setUsertrueName(cl.getCreayeManName());
			mes.setTypes(0);
			dao.add(mes);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
		}
		return map;
	}
	/**
	 * 删除自己创造的机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/DeleteMyCreateChance")
	@ResponseBody
	public Map DeleteMyCreateChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String ChanceId=req.getParameter("ChanceId");
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());
		if(cl.getCreateManId()!=users.getId()){
			map.put("success", false);
			map.put("info", "删除失败，次机会不是您创造的您没有权利修改此机会");
			return map;
		}
		if(cl!=null){
			//先删除联系人
			List list=dao.getObjectList("LinkManList", "where chanceListId="+cl.getId());
			Iterator i=list.iterator();
			while(i.hasNext()){
				dao.delete(i.next());
			}
			dao.delete(cl);
			ChanceListLog log=new ChanceListLog();
			log.setChanceId(cl.getId());
			log.setConpanyId(cl.getConpanyId());
			log.setCreateManId(cl.getCreateManId());
			log.setCreayeManName(cl.getCreayeManName());
			log.setCustomerAddress(cl.getCustomerAddress());
			log.setCustomerType(cl.getCustomerType());
			log.setCustomerName(cl.getCustomerName());
			log.setCustomerLevel(cl.getCustomerLevel());
			log.setCreateDate(cl.getCreateDate());
			log.setState(cl.getState());
			log.setCreateLogDate(new Date());
			log.setNotesUserId(cl.getNotesUserId());
			log.setNotesUserName(cl.getNotesUserName());
			log.setLastBuy(cl.getLastBuy());
			log.setEventType("delete");
			dao.add(log);
			Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), cl.getCreateManId(), cl.getCreayeManName());
			pf.setMyCreateChanceNum(pf.getMyCreateChanceNum()-1);
			dao.update(pf);
			Message mes=new Message();
			if(cl.getNotesUserId()!=0){
				mes.setConpanyId(cl.getConpanyId());
				mes.setContent("分配的机会被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
				mes.setTitle("分配的机会被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
				mes.setContentid(cl.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(cl.getNotesUserId());
				mes.setUsertrueName(cl.getNotesUserName());
				mes.setTypes(0);
				dao.add(mes);
				mes=new Message();
			}
			mes.setConpanyId(cl.getConpanyId());
			mes.setContent("创建的任务被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
			mes.setTitle("创建的任务被删除:"+cl.getCustomerName()+"删除人:"+users.getTrueName());
			mes.setContentid(cl.getId());
			mes.setCreateDate(new Date());
			mes.setReades(false);
			mes.setUserid(cl.getCreateManId());
			mes.setUsertrueName(cl.getCreayeManName());
			mes.setTypes(0);
			dao.add(mes);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "删除失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
		}
		return map;
	}
	/**
	 * 分配机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/allocationChance")
	@ResponseBody
	public Map allocationChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String NotesUserId=req.getParameter("NotesUserId");
		String ChanceId=req.getParameter("ChanceId");
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());
		ConpanyUser users_to=(ConpanyUser) dao.getObject(Long.parseLong(NotesUserId), "ConpanyUser",users.getConpanyId());
		if(users_to!=null){
			if(cl!=null){
				if(cl.getNotesUserId()!=0){
					Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), cl.getNotesUserId(), cl.getNotesUserName());
					pf.setToChanceNum(pf.getToChanceNum()-1);
					dao.update(pf);
				}
				cl.setNotesUserId(users_to.getId());
				cl.setNotesUserName(users_to.getTrueName());
				dao.update(cl);
				ChanceListLog log=new ChanceListLog();
				log.setChanceId(cl.getId());
				log.setConpanyId(cl.getConpanyId());
				log.setCreateManId(cl.getCreateManId());
				log.setCreayeManName(cl.getCreayeManName());
				log.setCustomerAddress(cl.getCustomerAddress());
				log.setCustomerType(cl.getCustomerType());
				log.setCustomerName(cl.getCustomerName());
				log.setCustomerLevel(cl.getCustomerLevel());
				log.setCreateDate(cl.getCreateDate());
				log.setState(cl.getState());
				log.setCreateLogDate(new Date());
				log.setNotesUserId(cl.getNotesUserId());
				log.setNotesUserName(cl.getNotesUserName());
				log.setLastBuy(cl.getLastBuy());
				log.setEventType("allocation");
				dao.add(log);
				Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), users_to.getId(), users_to.getTrueName());
				pf.setToChanceNum(pf.getToChanceNum()+1);
				dao.update(pf);
				Message mes=new Message();
				mes.setConpanyId(users_to.getConpanyId());
				mes.setContent(mes.getTitle());
				mes.setContentid(cl.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(users_to.getId());
				mes.setUsertrueName(users_to.getTrueName());
				mes.setTitle("您被指派了新客户机会");
				mes.setTypes(1);
				dao.add(mes);
				map.put("success", true);
				map.put("info", "指定成功");
			}else{
				map.put("success", false);
				map.put("info", "指定失败失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
			}
		}else{
			map.put("success", false);
			map.put("info", "指定失败失败，未找到被指定的用户。请从新确认是否有此用户。");
		}
		
		return map;
	}
	/**
	 * 分配自己创建的机会
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/allocationMyCreateChance")
	@ResponseBody
	public Map allocationMyCreateChance(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String NotesUserId=req.getParameter("NotesUserId");
		String ChanceId=req.getParameter("ChanceId");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(ChanceId), "ChanceList",users.getConpanyId());
		ConpanyUser users_to=(ConpanyUser) dao.getObject(Long.parseLong(NotesUserId), "ConpanyUser",users.getConpanyId());
		if(cl.getCreateManId()!=users.getId()){
			map.put("success", false);
			map.put("info", "指定失败，次机会不是您创造的您没有权利修改此机会");
			return map;
		}
		if(users_to!=null){
			if(cl!=null){
				if(cl.getNotesUserId()!=0){
					Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), cl.getNotesUserId(), cl.getNotesUserName());
					pf.setToChanceNum(pf.getToChanceNum()-1);
					dao.update(pf);
				}
				cl.setNotesUserId(users_to.getId());
				cl.setNotesUserName(users.getTrueName());
				dao.update(cl);
				ChanceListLog log=new ChanceListLog();
				log.setChanceId(cl.getId());
				log.setConpanyId(cl.getConpanyId());
				log.setCreateManId(cl.getCreateManId());
				log.setCreayeManName(cl.getCreayeManName());
				log.setCustomerAddress(cl.getCustomerAddress());
				log.setCustomerType(cl.getCustomerType());
				log.setCustomerName(cl.getCustomerName());
				log.setCustomerLevel(cl.getCustomerLevel());
				log.setCreateDate(cl.getCreateDate());
				log.setState(cl.getState());
				log.setCreateLogDate(new Date());
				log.setNotesUserId(cl.getNotesUserId());
				log.setNotesUserName(cl.getNotesUserName());
				log.setLastBuy(cl.getLastBuy());
				log.setEventType("allocation");
				dao.add(log);
				Performance pf=dao.getPerformanceByToDayUser(users.getConpanyId(), users_to.getId(), users_to.getTrueName());
				pf.setToChanceNum(pf.getToChanceNum()+1);
				dao.update(pf);
				Message mes=new Message();
				mes.setConpanyId(users_to.getConpanyId());
				mes.setContent(mes.getTitle());
				mes.setContentid(cl.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(users_to.getId());
				mes.setUsertrueName(users_to.getTrueName());
				mes.setTitle("您被指派了新客户机会");
				mes.setTypes(1);
				dao.add(mes);
				map.put("success", true);
				map.put("info", "指定成功");
			}else{
				map.put("success", false);
				map.put("info", "指定失败失败，未找到次机会。请从新确认是否有此机会，或是其他用户删除了");
			}
		}else{
			map.put("success", false);
			map.put("info", "指定失败失败，未找到被指定的用户。请从新确认是否有此用户。");
		}
		
		return map;
	}
	/**
	 * 创建流程
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createFlow")
	@ResponseBody
	public Map createFlow(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String chanceId=req.getParameter("chanceId");
		String name=req.getParameter("name");
		String jsonString=req.getParameter("jsonString");
		//FlowJson flowjson=FlowJsonUtil.getFlowJson(jsonString, name);
		try{
			ChanceFlow cf=new ChanceFlow();
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			cf.setCreateDate(new Date());
			cf.setCreateUserId(users.getId());
			cf.setCreateUserName(users.getTrueName());
			cf.setJsonflow(jsonString);
			cf.setName(name);
			cf.setConpanyId(users.getConpanyId());
			cf.setChanceId(Long.parseLong(chanceId));
			dao.add(cf);
			ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(chanceId),"ChanceList", users.getConpanyId());
			Message mes=new Message();
			mes.setConpanyId(users.getConpanyId());
			mes.setContent("同事："+users.getTrueName()+"对机会"+cl.getCustomerName()+"进行了开发建议.");
			mes.setContentid(cl.getId());
			mes.setCreateDate(new Date());
			mes.setReades(false);
			mes.setUserid(cl.getCreateManId());
			mes.setUsertrueName(cl.getCreayeManName());
			mes.setTitle("同事："+users.getTrueName()+"对机会"+cl.getCustomerName()+"进行了开发建议.");
			mes.setTypes(0);
			dao.add(mes);
			if(cl.getNotesUserId()!=0){
				mes=new Message();
				mes.setConpanyId(users.getConpanyId());
				mes.setContent("同事："+users.getTrueName()+"对机会"+cl.getCustomerName()+"进行了开发建议.");
				mes.setContentid(cl.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(cl.getNotesUserId());
				mes.setUsertrueName(cl.getNotesUserName());
				mes.setTitle("同事："+users.getTrueName()+"对机会"+cl.getCustomerName()+"进行了开发建议.");
				mes.setTypes(0);
				dao.add(mes);
			}
			map.put("success", true);
			map.put("info",name+"流程创建成功");
		}catch (Exception e) {
			// TODO: handle exception
			map.put("success", false);
			map.put("info", "创建失败");
		}
		return map;
	}
	/**
	 * 修改流
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateFlow")
	@ResponseBody
	public Map updateFlow(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("name");
		String flowId=req.getParameter("flowId");
		return map;
	}
	/**
	 * 查询单个流
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryFlowItem")
	@ResponseBody
	public Map queryFlowItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		ChanceFlow flow=(ChanceFlow) dao.getObject(Long.parseLong(id), "ChanceFlow",users.getConpanyId());
		if(flow!=null){
			map.put("success", true);
			map.put("obj",flow);
		}else{
			map.put("success", false);
			map.put("info", "查看失败，该建议不存在");
		}
		return map;
	}
	/**
	 * 删除流程
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteFlow")
	@ResponseBody
	public Map deleteFlow(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		ChanceFlow c=(ChanceFlow) dao.getObject(Long.parseLong(id), "ChanceFlow",users.getConpanyId());
		if(c==null){
			map.put("success", false);
			map.put("info", "查看失败，该建议不存在");
		}else{
			dao.delete(c);
			map.put("success", true);
			map.put("info", "删除成功");
		}
		return map;
	}
	/**
	 * 添加联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addLinkMan")
	@ResponseBody
	public Map addLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String chanceId=req.getParameter("chanceId");
		String linkManName=req.getParameter("linkManName");
		String linkManSex=req.getParameter("linkManSex");
		String linkManPhone=req.getParameter("linkManPhone");
		String linkManJob=req.getParameter("linkManJob");
		String linkManMark=req.getParameter("linkManMark");
		String linkManBirthday=req.getParameter("linkManBirthday");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		ChanceList c=(ChanceList) dao.getObject(Long.parseLong(chanceId), "ChanceList",users.getConpanyId());
		List<Object> objlist=dao.getObjectList("LinkManList", "where linkManPhone="+linkManPhone+" and conpanyId="+users.getConpanyId());
		if(objlist.size()>0){
			map.put("success", false);
			map.put("info", "该联系人电话已存在，有可能在其他机会里面。");
			return map;
		}
		if(c!=null){
			LinkManList list=new LinkManList();
			list.setAddUserId(users.getId());
			list.setAddUserName(users.getTrueName());
			list.setChanceListId(c.getId());
			list.setConpanyId(users.getConpanyId());
			if(!linkManBirthday.equals("联系人生日")){
			list.setLinkManBirthday(DateUtil.toDateType(linkManBirthday));
			}
			list.setLinkManJob(linkManJob);
			list.setLinkManMark(linkManMark);
			list.setLinkManName(linkManName);
			list.setChanceListName(c.getCustomerName());
			list.setLinkManPhone(linkManPhone);
			list.setLinkManSex(linkManSex);
			list.setLinkManScore(0);
			UserTable usertable=null;
			List<Object> utObjectlist=dao.getObjectList("UserTable", "where phone="+list.getLinkManPhone());
			if(utObjectlist.size()>0){
				usertable=(UserTable) utObjectlist.get(0);
			}else{
				usertable=new UserTable();
				usertable.setCode(0);
				usertable.setPassword("00000");
				usertable.setPhone(list.getLinkManPhone());
				usertable.setTrueName(list.getLinkManName());
				dao.add(usertable);
			}
			list.setUserTableId(usertable.getId());
			dao.add(list);
			UserTableLinkLinkMan ull=new UserTableLinkLinkMan();
			Conpany conpany=(Conpany) dao.getObject(MoreUserManager.getAppShopId(req), "Conpany");
			ull.setChanceId(c.getId());
			ull.setChanceName(c.getCustomerName());
			ull.setConpanId(conpany.getId());
			ull.setChanceName(conpany.getConpanyName());
			ull.setLinkDate(new Date());
			ull.setLinkmanId(list.getId());
			ull.setLinkmanName(list.getLinkManName());
			ull.setUsertableid(usertable.getId());
			ull.setUsertableUserName(usertable.getTrueName());
			dao.add(ull);
			map.put("success", true);
			map.put("info", "添加成功");
			Message mes=new Message();
			mes.setConpanyId(users.getConpanyId());
			mes.setContent("同事："+users.getTrueName()+",对机会"+c.getCustomerName()+",添加了新的联系人.");
			mes.setContentid(c.getId());
			mes.setCreateDate(new Date());
			mes.setReades(false);
			mes.setUserid(c.getCreateManId());
			mes.setUsertrueName(c.getCreayeManName());
			mes.setTitle("同事："+users.getTrueName()+",对机会"+c.getCustomerName()+",添加了新的联系人.");
			mes.setTypes(0);
			dao.add(mes);
			if(c.getNotesUserId()!=0){
				mes=new Message();
				mes.setConpanyId(users.getConpanyId());
				mes.setContent("同事："+users.getTrueName()+"对机会"+c.getCustomerName()+"添加了新的联系人.");
				mes.setContentid(c.getId());
				mes.setCreateDate(new Date());
				mes.setReades(false);
				mes.setUserid(c.getNotesUserId());
				mes.setUsertrueName(c.getNotesUserName());
				mes.setTitle("同事："+users.getTrueName()+"对机会"+c.getCustomerName()+"添加了新的联系人.");
				mes.setTypes(0);
				dao.add(mes);
			}
		}else{
			map.put("success", false);
			map.put("info", "该机会已不存在");
		}
		return map;
	}
	/**
	 * 更新联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateLinkMan")
	@ResponseBody
	public Map updateLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		String linkManName=req.getParameter("linkManName");
		String linkManSex=req.getParameter("linkManSex");
		String linkManPhone=req.getParameter("linkManPhone");
		String linkManJob=req.getParameter("linkManJob");
		String linkManMark=req.getParameter("linkManMark");
		String linkManBirthday=req.getParameter("linkManBirthday");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			LinkManList list=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList",users.getConpanyId());
		if(list!=null){
			if(!linkManBirthday.equals("联系人生日")){
			list.setLinkManBirthday(DateUtil.toDateType(linkManBirthday));
			}
			list.setLinkManJob(linkManJob);
			list.setLinkManMark(linkManMark);
			list.setLinkManName(linkManName);
			list.setLinkManPhone(linkManPhone);
			list.setLinkManSex(linkManSex);
			dao.update(list);
			map.put("success", true);
			map.put("info", "修改成功");

		}else{
			map.put("success", false);
			map.put("info", "该联系人已不存在");
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
	@RequestMapping(value = "/queryLinkMan")
	@ResponseBody
	public Map queryLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String linkManName=req.getParameter("ChanceName");
		String chanceId=req.getParameter("chanceId");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("LinkManList", "where chanceListId="+chanceId+" and linkManName like '%"+linkManName+"%'");
		List list=dao.getObjectListPage("LinkManList", "where chanceListId="+chanceId+" and linkManName like '%"+linkManName+"%'", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 删除联系人
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteLinkMan")
	@ResponseBody
	public Map deleteLinkMan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		LinkManList list=(LinkManList) dao.getObject(Long.parseLong(id), "LinkManList",users.getConpanyId());
		if(list!=null){
			dao.delete(list);
			map.put("success", true);
			map.put("info", "删除成功");
		}else{
			map.put("success", false);
			map.put("info", "该联系人已不存在");
		}
		return map;
	}
	/**
	 * 获取一个联系人的详细信息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getLinkManById")
	@ResponseBody
	public Map getLinkManById(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String linkmanId=req.getParameter("ChanceName");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		LinkManList link=(LinkManList) dao.getObject(Long.parseLong(linkmanId), "LinkManList",users.getConpanyId());
		if(link!=null){
			map.put("success", true);
			map.put("obj", link);
		}else{
			map.put("success", false);
			map.put("info", "错误，该联系人不存在");
		}
		return map;
	}
	/**
	 * 开始开发
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/startNotes")
	@ResponseBody
	public Map startNotes(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String chanceId=req.getParameter("chanceId");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Notes notes=new Notes();
		ChanceList c=(ChanceList) dao.getObject(Long.parseLong(chanceId), "ChanceList",users.getConpanyId());
		
		if(c!=null){
			List list=dao.getObjectList("Notes", "where chanceListId="+c.getId()+" and notesUserId = "+users.getId()+" and endDate=null");
			if(list.size()>0){
				map.put("success", false);
				map.put("info", "您有在本机会里有一个开发记录并没有结束，请结束后再操作。");
			}else{
				notes.setConpanyId(users.getConpanyId());
				notes.setChanceListId(c.getId());
				notes.setChanceListName(c.getCustomerName());
				notes.setStartDate(new Date());
				notes.setNotesUserId(users.getId());
				notes.setNotesUserName(users.getTrueName());
				notes.setUpdateDate(new Date());
				dao.add(notes);
				map.put("success", true);
				map.put("info", "创建成功，已在列表之中，请操作吧！");
			}
		}else{
			map.put("success", false);
			map.put("info", "错误，该机会不存在货已经被移除。");
		}
		return map;
	}
	/**
	 * 添加开发记录
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addNotesItem")
	@ResponseBody
	public Map addNotesItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String chanceId = req.getParameter("chanceId");
		String NotesId=req.getParameter("NotesId");//如果没有此id则从新创建一个新的之后进行添加
		String Title=req.getParameter("Title");
		String notesAddress=req.getParameter("notesAddress");
		String NotesDriver=req.getParameter("NotesDriver");
		String Marks=req.getParameter("Marks");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Notes note=(Notes) dao.getObject(Long.parseLong(NotesId), "Notes",users.getConpanyId());
		
		if(note!=null){
			NotesInfo info=new NotesInfo();
			if(notesAddress!=null){
				info.setNotesAddress(notesAddress);
			}
			if(NotesDriver!=null){
				info.setNotesDriver(NotesDriver);
			}
			info.setConpanyId(users.getConpanyId());
			info.setNotesDate(new Date());
			info.setNotesId(note.getId());
			info.setNotesMark(Marks);
			info.setNotesTitle(Title);
			info.setNotesUserId(users.getId());
			info.setNotesUserName(users.getTrueName());
			dao.add(info);
			note.setUpdateDate(new Date());
			dao.update(note);
			map.put("success", true);
			map.put("info", "添加成功");
		}else{
			if(chanceId!=null){
				ChanceList c=(ChanceList) dao.getObject(Long.parseLong(chanceId), "ChanceList",users.getConpanyId());
				List list=dao.getObjectList("Notes", "where chanceListId="+c.getId()+" and notesUserId = "+users.getId()+" and endDate=null");
				if(list.size()!=0){
					note=(Notes)list.iterator().next();
				}else{
					note=new Notes();
					note.setConpanyId(users.getConpanyId());
					note.setChanceListId(c.getId());
					note.setChanceListName(c.getCustomerName());
					note.setStartDate(new Date());
					note.setNotesUserId(users.getId());
					note.setNotesUserName(users.getTrueName());
					note.setUpdateDate(new Date());
					dao.add(note);
				}
				
				NotesInfo info=new NotesInfo();
				if(notesAddress!=null){
					info.setNotesAddress(notesAddress);
				}
				if(NotesDriver!=null){
					info.setNotesDriver(NotesDriver);
				}
				info.setConpanyId(users.getConpanyId());
				info.setNotesDate(new Date());
				info.setNotesId(note.getId());
				info.setNotesMark(Marks);
				info.setNotesTitle(Title);
				info.setNotesUserId(users.getId());
				info.setNotesUserName(users.getTrueName());
				dao.add(info);
				note.setUpdateDate(new Date());
				dao.update(note);
				map.put("success", true);
				map.put("info", "添加成功");
			}else{
				map.put("success", false);
				map.put("info", "错误，该开发记录不存在。");
			}
		}
		return map;
	}
	/**
	 * 查询开发记录
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryNotes")
	@ResponseBody
	public Map queryNotes(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String linkManName=req.getParameter("ChanceName");
		String chanceId=req.getParameter("chanceId");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num=dao.getObjectListNum("Notes", "where chanceListId="+chanceId+" and notesUserName like '%"+linkManName+"%'");
		List list=dao.getObjectListPage("Notes", "where chanceListId="+chanceId+" and notesUserName like '%"+linkManName+"%' order by updateDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	/**
	 * 查询开发记录详情
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryNotesItem")
	@ResponseBody
	public Map queryNotesItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String NotesId=req.getParameter("NotesId");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String chanceId = req.getParameter("chanceId");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		Notes info=(Notes) dao.getObject(Long.parseLong(NotesId), "Notes",users.getConpanyId());
		if(info!=null){
			long num=dao.getObjectListNum("NotesInfo", "where notesId="+info.getId());
			List list=dao.getObjectListPage("NotesInfo", "where notesId="+info.getId()+" order by notesDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
			map.put("pagenum", num/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("data", list);
		}else{
			ChanceList c=(ChanceList) dao.getObject(Long.parseLong(chanceId), "ChanceList",users.getConpanyId());
			if(c!=null){
				List list=dao.getObjectList("Notes", "where chanceListId="+c.getId()+" and notesUserId = "+users.getId()+" and endDate=null");
				if(list.size()!=0){
					Notes notes=(Notes)list.iterator().next();
					long num=dao.getObjectListNum("NotesInfo", "where notesId="+notes.getId());
					List list2=dao.getObjectListPage("NotesInfo", "where notesId="+notes.getId()+" order by notesDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
					map.put("pagenum", num/Integer.parseInt(countNum)+1);
					map.put("success", true);
					map.put("data", list2);
				}else{
					map.put("success", false);
					map.put("info", "您没有创建记录，需要创建记录后才可以查看。");
				}
			}else{
				map.put("success", false);
				map.put("info", "该记录不存在，或者已删除");
			}
		}
		return map;
	}
	/**
	 * 改变状态
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/changeState")
	@ResponseBody
	public Map changeState(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String state	=req.getParameter("state");
		String chanceId=req.getParameter("chanceId");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		ChanceList cl=(ChanceList) dao.getObject(Long.parseLong(chanceId), "ChanceList",users.getConpanyId());
		cl.setState(Integer.parseInt(state));
		dao.update(cl);
		ChanceListLog States=new ChanceListLog();
		States.setConpanyId(cl.getConpanyId());
		States.setCreateManId(cl.getCreateManId());
		States.setCreayeManName(cl.getCreayeManName());
		States.setCustomerAddress(cl.getCustomerAddress());
		States.setCustomerType(cl.getCustomerType());
		States.setCustomerName(cl.getCustomerName());
		States.setCustomerLevel(cl.getCustomerLevel());
		States.setCreateDate(cl.getCreateDate());
		States.setState(Integer.parseInt(state));
		States.setCreateLogDate(new Date());
		States.setEventType("state");
		States.setLastBuy(cl.getLastBuy());
		States.setNotesUserId(cl.getNotesUserId());
		States.setNotesUserName(cl.getNotesUserName());
		States.setChanceId(cl.getId());
		dao.add(state);
		return map;
	}
	/**
	 * 获取一些文档中引用的信息，比如联系人，客户，客户开发进程等等
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getObject")
	@ResponseBody
	public Map getObject(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String id=req.getParameter("id");
		String type=req.getParameter("type");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		if(type.equals("custemor")){
			ChanceList no=(ChanceList) dao.getObject(Long.parseLong(id), "ChanceList",users.getConpanyId());

			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，该联系人不存在");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，该联系人不存在");
			}
		}else if(type.equals("linkman")){
			LinkManList no=(LinkManList) dao.getObject(Long.parseLong(id),"LinkManList",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("Note")){
			Notes info=(Notes) dao.getObject(Long.parseLong(id), "Notes",users.getConpanyId());
			if(info!=null){
				if(info.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					List list=dao.getObjectList("NotesInfo", "where notesId="+info.getId()+" order by notesDate desc");
					map.put("success", true);
					map.put("data", list);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("NoteItem")){
			NotesInfo no=(NotesInfo) dao.getObject(Long.parseLong(id),"NotesInfo",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("user")){
			ConpanyUser no=(ConpanyUser) dao.getObject(Long.parseLong(id),"ConpanyUser",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("goodSource")){
			GoodsSource no=(GoodsSource) dao.getObject(Long.parseLong(id),"GoodsSource",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("StoreHouse")){
			StoreHouse no=(StoreHouse) dao.getObject(Long.parseLong(id),"StoreHouse",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("goodsourceLinkMan")){
			GoodsSourceLinkMan no=(GoodsSourceLinkMan) dao.getObject(Long.parseLong(id),"GoodsSourceLinkMan",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}else if(type.equals("good")){
			GoodsTable no=(GoodsTable) dao.getObject(Long.parseLong(id),"GoodsTable",users.getConpanyId());
			if(no!=null){
				if(no.getConpanyId()!=users.getConpanyId()){
					map.put("success", false);
					map.put("info", "错误，没有找到该数据");
				}else{
					map.put("success", true);
					map.put("obj", no);
				}
			}else{
				map.put("success", false);
				map.put("info", "错误，没有找到该数据");
			}
		}
		return map;
	}
	/**
	 * 查询建议流程
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryFlow")
	@ResponseBody
	public Map queryFlow(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		String name=req.getParameter("name");
		String chanceId=req.getParameter("chanceId");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		long num=dao.getObjectListNum("ChanceFlow", "where conpanyId="+users.getConpanyId()+" and chanceId="+chanceId+" and name like '%"+name+"%'");
		List list=dao.getObjectListPage("ChanceFlow", "where conpanyId="+users.getConpanyId()+" and chanceId="+chanceId+" and name like '%"+name+"%' order by createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num/Integer.parseInt(countNum)+1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
}
