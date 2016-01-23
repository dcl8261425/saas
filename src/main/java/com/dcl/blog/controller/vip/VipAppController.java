package com.dcl.blog.controller.vip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.controller.mode.FileUploadStatus;
import com.dcl.blog.controller.weixin.WeiXinMainController;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyScoreNum;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.GameConpany;
import com.dcl.blog.model.GameConpanyPaiMing;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.Orders;
import com.dcl.blog.model.OrdersItem;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.model.UserTable;
import com.dcl.blog.model.VIPSet;
import com.dcl.blog.model.VipErShou;
import com.dcl.blog.model.VipErShouReturnMessageOne;
import com.dcl.blog.model.VipErShouReturnMessageTwo;
import com.dcl.blog.model.VipErShouType;
import com.dcl.blog.model.VipImageManager;
import com.dcl.blog.model.VipImageMessage;
import com.dcl.blog.model.VipImageMessageRetOne;
import com.dcl.blog.model.VipImageMessageRetOneZan;
import com.dcl.blog.model.VipImageMessageRetTwo;
import com.dcl.blog.model.VipImageMessageZan;
import com.dcl.blog.model.VipLiuYanItem;
import com.dcl.blog.model.VipLiuYanList;
import com.dcl.blog.model.VipTextMessage;
import com.dcl.blog.model.VipTextMessageRetOne;
import com.dcl.blog.model.VipTextMessageRetOneZan;
import com.dcl.blog.model.VipTextMessageRetTwo;
import com.dcl.blog.model.VipTextMessageZan;
import com.dcl.blog.model.VipVideoManager;
import com.dcl.blog.model.VipVideoMessage;
import com.dcl.blog.model.VipVideoMessageRetOne;
import com.dcl.blog.model.VipVideoMessageRetOneZan;
import com.dcl.blog.model.VipVideoMessageRetTwo;
import com.dcl.blog.model.VipVideoMessageZan;
import com.dcl.blog.model.WeiXinGoods;
import com.dcl.blog.model.WeixinOrder;
import com.dcl.blog.model.dto.BuyCar;
import com.dcl.blog.model.dto.ErShouMessageDTO;
import com.dcl.blog.model.dto.VipImageDTO;
import com.dcl.blog.model.dto.VipImageMessageRetOneDTO;
import com.dcl.blog.model.dto.VipLiuYanListDTO;
import com.dcl.blog.model.dto.VipTextDTO;
import com.dcl.blog.model.dto.VipVideoDTO;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.FileManager;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
import com.dcl.blog.util.moreuser.MoreUserManager;

@Controller
@RequestMapping("/VipAppController")
public class VipAppController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeiXinMainController.class);
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
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map login(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String phone=req.getParameter("phone");
		String conpanyId=req.getParameter("conpanyId");
		String password=req.getParameter("password");
		req.getSession().setAttribute("conpanyId", conpanyId);
		List<Object> objlist=dao.getObjectList("LinkManList", "where conpanyId="+conpanyId+" and linkManPhone="+phone);
		if(objlist.size()>0){
			LinkManList lm=(LinkManList) objlist.iterator().next();
			UserTable ut=(UserTable) dao.getObject(lm.getUserTableId(), "UserTable");
			MoreUserManager.setLinkManList(lm, req);
			MoreUserManager.setUserTable(ut, req);
			if(ut.getPassword().equals(password)){
				
				map.put("success", true);
				map.put("info", "登录成功");
			}else{
				map.put("success", false);
				map.put("info", "密码不正确");
			}
		}else{
			map.put("success", false);
			map.put("info", "账号不正确");
		}
		return map;
	}
	@RequestMapping(value = "/image")
	@ResponseBody
	public Map image(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "10";
		}
		List<VipImageDTO> listVim=new ArrayList<VipImageDTO>();
		List<Object> list=dao.getObjectListPage("VipImageMessage", "where conpanyId="+conpanyId+" and pass=1"+" order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipImageMessage vipM=(VipImageMessage) i.next();
			VipImageDTO dto=new VipImageDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setGif(vipM.isGif());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setImageLink(vipM.getImageLink());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setFileName(vipM.getFileName());
			dto.setFileSize(vipM.getFileSize());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipImageMessageRetOne", "where conpanyId="+conpanyId+" and vipImageMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipImageMessageZan", "where vipImageMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/video")
	@ResponseBody
	public Map video(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "10";
		}
		List<VipVideoDTO> listVim=new ArrayList<VipVideoDTO>();
		List<Object> list=dao.getObjectListPage("VipVideoMessage", "where conpanyId="+conpanyId+" and pass=1"+" order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipVideoMessage vipM=(VipVideoMessage) i.next();
			VipVideoDTO dto=new VipVideoDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setId(vipM.getId());
			dto.setVideoLink(vipM.getVideoLink());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setFileName(vipM.getFileName());
			dto.setFileSize(vipM.getFileSize());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipVideoMessageRetOne", "where conpanyId="+conpanyId+" and vipVideoMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipVideoMessageZan", "where vipVideoMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/text")
	@ResponseBody
	public Map text(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "10";
		}
		List<VipTextDTO> listVim=new ArrayList<VipTextDTO>();
		List<Object> list=dao.getObjectListPage("VipTextMessage", "where conpanyId="+conpanyId+" and pass=1"+" order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipTextMessage vipM=(VipTextMessage) i.next();
			VipTextDTO dto=new VipTextDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipTextMessageRetOne", "where conpanyId="+conpanyId+" and vipTextMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipTextMessageZan", "where vipTextMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/imageItem")
	@ResponseBody
	public Map imageItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		
		String id=req.getParameter("id");
		List<VipImageDTO> listVim=new ArrayList<VipImageDTO>();
		List<Object> list=dao.getObjectList("VipImageMessage", "where id="+id);
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipImageMessage vipM=(VipImageMessage) i.next();
			VipImageDTO dto=new VipImageDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setGif(vipM.isGif());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setImageLink(vipM.getImageLink());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipImageMessageRetOne", "where conpanyId="+conpanyId+" and vipImageMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipImageMessageZan", "where vipImageMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/videoItem")
	@ResponseBody
	public Map videoItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		
		String id=req.getParameter("id");
		List<VipVideoDTO> listVim=new ArrayList<VipVideoDTO>();
		List<Object> list=dao.getObjectList("VipVideoMessage", "where id="+id);
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipVideoMessage vipM=(VipVideoMessage) i.next();
			VipVideoDTO dto=new VipVideoDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setVideoLink(vipM.getVideoLink());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipVideoMessageRetOne", "where conpanyId="+conpanyId+" and vipVideoMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipVideoMessageZan", "where vipVideoMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/textItem")
	@ResponseBody
	public Map textItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		List<VipTextDTO> listVim=new ArrayList<VipTextDTO>();
		List<Object> list=dao.getObjectList("VipTextMessage", "where id="+id);
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipTextMessage vipM=(VipTextMessage) i.next();
			VipTextDTO dto=new VipTextDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setLookNum(vipM.getLookNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setReturnNum(vipM.getReturnNum());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setZan(vipM.getZan());
			dto.setTitle(vipM.getTitle());
			List<Object> retlist=dao.getObjectListPage("VipTextMessageRetOne", "where conpanyId="+conpanyId+" and vipTextMessageId="+vipM.getId()+" order by zan desc,createDate desc",1,1);
			dto.setOneMsg(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipTextMessageZan", "where vipTextMessageId="+vipM.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/ershouItem")
	@ResponseBody
	public Map ershouItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		List<ErShouMessageDTO> listVim=new ArrayList<ErShouMessageDTO>();
		List<Object> list=null;
		list=dao.getObjectList("VipErShou", "where id="+id);
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipErShou vipM=(VipErShou) i.next();
			ErShouMessageDTO dto=new ErShouMessageDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setErShouTypeId(vipM.getErShouTypeId());
			dto.setErShouTypeName(vipM.getErShouTypeName());
			dto.setImage1(vipM.getImage1());
			dto.setImage2(vipM.getImage2());
			dto.setImage3(vipM.getImage3());
			dto.setImage4(vipM.getImage4());
			dto.setPrice(vipM.getPrice());
			dto.setTitle(vipM.getTitle());
			dto.setPhone(vipM.getPhone());
			List<Object> retlist=dao.getObjectListPage("VipErShouReturnMessageOne", "where conpanyId="+conpanyId+" and vipErShouMessageId="+vipM.getId()+" order by createDate desc",1,1);
			dto.setOneMsg(retlist);
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/game")
	@ResponseBody
	public Map game(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		List<Object> gc=dao.getObjectList("GameConpany", "where conpanyId="+conpanyId+" and openUse=true");
		map.put("success", true);
		map.put("data", gc);
		return map;
	}
	@RequestMapping(value = "/gamepaiming")
	@ResponseBody
	public Map gamepaiming(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		List<Object> gc=dao.getObjectListPage("GameConpanyPaiMing", "where conpanyGameId="+id+" and conpanyId="+conpanyId+" order by score desc",1,15);
		map.put("success", true);
		map.put("data", gc);
		return map;
	}
	@RequestMapping(value = "/submitgamepaiming",method=RequestMethod.POST)
	@ResponseBody
	public Map submitgamepaiming(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj==null){
			return map;
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		long id=(Long) req.getSession().getAttribute("gameId");
		String score=req.getParameter("score");
		List<Object> gc=dao.getObjectList("GameConpanyPaiMing", "where conpanyGameId="+id+" and conpanyId="+conpanyId+" and createVipId="+lm.getId()+" and createUserTableId="+ut.getId());
		if(gc.size()>0){
			GameConpanyPaiMing gcobj=(GameConpanyPaiMing) gc.iterator().next();
			if(gcobj.getScore()<Long.parseLong(score)){
				gcobj.setScore(Long.parseLong(score));
			}
			gcobj.setTouxiang(ut.getTouxiang());
			gcobj.setCreateVipName(lm.getLinkManName());
			dao.update(gcobj);
		}else{
			GameConpany gcy=(GameConpany) dao.getObject(id, "GameConpany");
			GameConpanyPaiMing gcobj=new GameConpanyPaiMing();
			gcobj.setConpanyGameId(id);
			gcobj.setTouxiang(ut.getTouxiang());
			gcobj.setConpanyId(conpany.getId());
			gcobj.setConpanyName(conpany.getConpanyName());
			gcobj.setCreateUserTableId(ut.getId());
			gcobj.setCreateVipId(lm.getId());
			gcobj.setCreateVipName(lm.getLinkManName());
			gcobj.setGameId(gcy.getGameId());
			gcobj.setScore(Long.parseLong(score));
			dao.add(gcobj);
		}
		map.put("success", true);
		map.put("data", gc);
		return map;
	}
	@RequestMapping(value = "/ershouType")
	@ResponseBody
	public Map ershouType(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		List<Object> list=dao.getObjectList("VipErShouType", "where conpanyId="+conpanyId);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	@RequestMapping(value = "/ershou")
	@ResponseBody
	public Map ershou(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("typeId");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "10";
		}
		List<ErShouMessageDTO> listVim=new ArrayList<ErShouMessageDTO>();
		List<Object> list=null;
		if(type==null||type.equals("0")||type.equals("")){
			list=dao.getObjectListPage("VipErShou", "where conpanyId="+conpanyId+" and pass=1"+" order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		}else{
			list=dao.getObjectListPage("VipErShou", "where conpanyId="+conpanyId+" and erShouTypeId="+type+" and pass=1"+" order by createDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		}
		
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipErShou vipM=(VipErShou) i.next();
			ErShouMessageDTO dto=new ErShouMessageDTO();
			dto.setCity(vipM.getCity());
			dto.setConpanyId(vipM.getConpanyId());
			dto.setConpanyName(vipM.getConpanyName());
			dto.setCountry(vipM.getCountry());
			dto.setCreateDate(vipM.getCreateDate());
			dto.setCreateVipId(vipM.getCreateVipId());
			dto.setCreateVipName(vipM.getCreateVipName());
			dto.setCreateVipPhone(vipM.getCreateVipPhone());
			dto.setTouXiangImage(vipM.getTouXiangImage());
			dto.setDistrict(vipM.getDistrict());
			dto.setHangyeId(vipM.getHangyeId());
			dto.setHangyeName(vipM.getHangyeName());
			dto.setId(vipM.getId());
			dto.setIndexNum(vipM.getIndexNum());
			dto.setMessage(vipM.getMessage());
			dto.setProvince(vipM.getProvince());
			dto.setUserTableId(vipM.getUserTableId());
			dto.setErShouTypeId(vipM.getErShouTypeId());
			dto.setErShouTypeName(vipM.getErShouTypeName());
			dto.setImage1(vipM.getImage1());
			dto.setImage2(vipM.getImage2());
			dto.setImage3(vipM.getImage3());
			dto.setImage4(vipM.getImage4());
			dto.setPrice(vipM.getPrice());
			dto.setTitle(vipM.getTitle());
			dto.setPhone(vipM.getPhone());
			List<Object> retlist=dao.getObjectListPage("VipErShouReturnMessageOne", "where conpanyId="+conpanyId+" and vipErShouMessageId="+vipM.getId()+" order by createDate desc",1,1);
			dto.setOneMsg(retlist);
			listVim.add(dto);
		}
		map.put("data", listVim);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/imageZan")
	@ResponseBody
	public Map imageZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipImageMessage mes=(VipImageMessage) dao.getObject(Long.parseLong(id), "VipImageMessage");
		List<Object> list=dao.getObjectList("VipImageMessageZan", "where vipImageMessageId="+mes.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId());
		if(list.size()>0){
			VipImageMessageZan zan=(VipImageMessageZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipImageMessageZan zan=new VipImageMessageZan();
			zan.setCreateUsrTableId(ut.getId());
			zan.setCreateVipId(lm.getId());
			zan.setCreateVipName(lm.getLinkManName());
			zan.setVipImageMessageId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/videoZan")
	@ResponseBody
	public Map videoZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipVideoMessage mes=(VipVideoMessage) dao.getObject(Long.parseLong(id), "VipVideoMessage");
		List<Object> list=dao.getObjectList("VipVideoMessageZan", "where vipVideoMessageId="+mes.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId());
		if(list.size()>0){
			VipVideoMessageZan zan=(VipVideoMessageZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipVideoMessageZan zan=new VipVideoMessageZan();
			zan.setCreateUsrTableId(ut.getId());
			zan.setCreateVipId(lm.getId());
			zan.setCreateVipName(lm.getLinkManName());
			zan.setVipVideoMessageId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/textZan")
	@ResponseBody
	public Map textZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipTextMessage mes=(VipTextMessage) dao.getObject(Long.parseLong(id), "VipTextMessage");
		List<Object> list=dao.getObjectList("VipTextMessageZan", "where vipTextMessageId="+mes.getId()+" and createVipId="+lm.getId()+" and createUsrTableId="+ut.getId());
		if(list.size()>0){
			VipTextMessageZan zan=(VipTextMessageZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipTextMessageZan zan=new VipTextMessageZan();
			zan.setCreateUsrTableId(ut.getId());
			zan.setCreateVipId(lm.getId());	
			zan.setCreateVipName(lm.getLinkManName());
			zan.setVipTextMessageId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/imageLiuYan")
	@ResponseBody
	public Map imageLiuYan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("typeId");
		String id=req.getParameter("id");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		List<VipImageMessageRetOneDTO> oneDto=new ArrayList<VipImageMessageRetOneDTO>();
		List<Object> list=dao.getObjectListPage("VipImageMessageRetOne", "where vipImageMessageId="+id+" and conpanyId="+conpanyId+" order by zan desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipImageMessageRetOne one=(VipImageMessageRetOne) i.next();
			VipImageMessageRetOneDTO dto=new VipImageMessageRetOneDTO();
			dto.setConpanyId(one.getConpanyId());
			dto.setCreateDate(one.getCreateDate());
			dto.setCreateUserTableId(one.getCreateUserTableId());
			dto.setCreateVipId(one.getCreateVipId());
			dto.setCreateVipName(one.getCreateVipName());
			dto.setId(one.getId());
			dto.setMessage(one.getMessage());
			dto.setTouXiangImage(one.getTouXiangImage());
			dto.setVipImageMessageId(one.getVipImageMessageId());
			dto.setVipImageMessageName(one.getVipImageMessageName());
			dto.setZan(one.getZan());
			List<Object> retlist=dao.getObjectList("VipImageMessageRetTwo", "where conpanyId="+conpanyId+" and mainId="+one.getId());
			dto.setVipImageMessageRetTwoList(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipImageMessageRetOneZan", "where mainId="+one.getId()+" and createVipId="+lm.getId()+" and createUserTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			oneDto.add(dto);
		}
		map.put("success", true);
		map.put("data", oneDto);
		return map;
	}
	@RequestMapping(value = "/videoLiuyan")
	@ResponseBody
	public Map videoLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("typeId");
		String id=req.getParameter("id");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		List<VipImageMessageRetOneDTO> oneDto=new ArrayList<VipImageMessageRetOneDTO>();
		List<Object> list=dao.getObjectListPage("VipVideoMessageRetOne", "where vipVideoMessageId="+id+" and conpanyId="+conpanyId+" order by zan desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipVideoMessageRetOne one=(VipVideoMessageRetOne) i.next();
			VipImageMessageRetOneDTO dto=new VipImageMessageRetOneDTO();
			dto.setConpanyId(one.getConpanyId());
			dto.setCreateDate(one.getCreateDate());
			dto.setCreateUserTableId(one.getCreateUserTableId());
			dto.setCreateVipId(one.getCreateVipId());
			dto.setCreateVipName(one.getCreateVipName());
			dto.setId(one.getId());
			dto.setMessage(one.getMessage());
			dto.setTouXiangImage(one.getTouXiangImage());
			dto.setVipImageMessageId(one.getVipVideoMessageId());
			dto.setVipImageMessageName(one.getVipVideoMessageTitle());
			dto.setZan(one.getZan());
			List<Object> retlist=dao.getObjectList("VipVideoMessageRetTwo", "where conpanyId="+conpanyId+" and mainId="+one.getId());
			dto.setVipImageMessageRetTwoList(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipVideoMessageRetOneZan", "where mainId="+one.getId()+" and createVipId="+lm.getId()+" and createUserTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			oneDto.add(dto);
		}
		map.put("success", true);
		map.put("data", oneDto);
		return map;
	}
	@RequestMapping(value = "/textLiuyan")
	@ResponseBody
	public Map textLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("typeId");
		String id=req.getParameter("id");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		List<VipImageMessageRetOneDTO> oneDto=new ArrayList<VipImageMessageRetOneDTO>();
		List<Object> list=dao.getObjectListPage("VipTextMessageRetOne", "where vipTextMessageId="+id+" and conpanyId="+conpanyId+" order by zan desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipTextMessageRetOne one=(VipTextMessageRetOne) i.next();
			VipImageMessageRetOneDTO dto=new VipImageMessageRetOneDTO();
			dto.setConpanyId(one.getConpanyId());
			dto.setCreateDate(one.getCreateDate());
			dto.setCreateUserTableId(one.getCreateUserTableId());
			dto.setCreateVipId(one.getCreateVipId());
			dto.setCreateVipName(one.getCreateVipName());
			dto.setId(one.getId());
			dto.setMessage(one.getMessage());
			dto.setTouXiangImage(one.getTouXiangImage());
			dto.setVipImageMessageId(one.getVipTextMessageId());
			dto.setVipImageMessageName(one.getVipTextMessageName());
			dto.setZan(one.getZan());
			List<Object> retlist=dao.getObjectList("VipTextMessageRetTwo", "where conpanyId="+conpanyId+" and mainId="+one.getId());
			dto.setVipImageMessageRetTwoList(retlist);
			if(lm!=null&&ut!=null){
				List<Object> zanlist=dao.getObjectListPage("VipTextMessageRetOneZan", "where mainId="+one.getId()+" and createVipId="+lm.getId()+" and createUserTableId="+ut.getId(),1,1);
				if(zanlist.size()>0){
					dto.setZanUser(true);
				}else{
					dto.setZanUser(false);
				}
			}else{
				dto.setZanUser(false);
			}
			oneDto.add(dto);
		}
		map.put("success", true);
		map.put("data", oneDto);
		return map;
	}
	@RequestMapping(value = "/erShouLiuyan")
	@ResponseBody
	public Map erShouLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String type=req.getParameter("typeId");
		String id=req.getParameter("id");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		List<VipImageMessageRetOneDTO> oneDto=new ArrayList<VipImageMessageRetOneDTO>();
		List<Object> list=dao.getObjectListPage("VipErShouReturnMessageOne", "where vipErShouMessageId="+id+" and conpanyId="+conpanyId+" order by createDate desc", Integer.parseInt(nowpage), Integer.parseInt(countNum));
		Iterator<Object> i=list.iterator();
		while(i.hasNext()){
			VipErShouReturnMessageOne one=(VipErShouReturnMessageOne) i.next();
			VipImageMessageRetOneDTO dto=new VipImageMessageRetOneDTO();
			dto.setConpanyId(one.getConpanyId());
			dto.setCreateDate(one.getCreateDate());
			dto.setCreateUserTableId(one.getCreateUserTableId());
			dto.setCreateVipId(one.getCreateVipId());
			dto.setCreateVipName(one.getCreateVipName());
			dto.setId(one.getId());
			dto.setMessage(one.getMessage());
			dto.setTouXiangImage(one.getTouXiangImage());
			dto.setVipImageMessageId(one.getVipErShouMessageId());
			dto.setVipImageMessageName(one.getVipErShouMessageName());
			List<Object> retlist=dao.getObjectList("VipErShouReturnMessageTwo", "where conpanyId="+conpanyId+" and mainId="+one.getId());
			dto.setVipImageMessageRetTwoList(retlist);
			
			oneDto.add(dto);
		}
		map.put("success", true);
		map.put("data", oneDto);
		return map;
	}
	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage")
	@ResponseBody
	public Map uploadImage_kongjian(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LinkManList lm = MoreUserManager.getLinkManList(request, dao);
		UserTable ut = MoreUserManager.getUserObject(request, dao);
		String conpanyId="";
		Object obj=request.getSession().getAttribute("conpanyId");
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		String savePath = FileManager.SAAS+conpanyId+File.separatorChar+FileManager.VIP_IMAGE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//最大文件大小
		long maxSize = 10000000;
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			map.put("success", false);
			map.put("message", "请选择图片。");
		 return map;
		}
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			uploadDir.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
		 dirFile.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items=null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
		 FileItem item = (FileItem) itr.next();
		 String fileName = item.getName();
		 long fileSize = item.getSize();
		 if (!item.isFormField()) {
		  //检查文件大小
		  if(item.getSize() > maxSize){
		   //out.println(getError("上传文件大小超过限制。"));
			  map.put("success", false);
				 map.put("message", "上传图片大小超过限制。最高位10m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  map.put("success", false);
				 map.put("message", "上传图片扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
				 return map;
		  }
		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		  String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		  FileUploadStatus fus=new FileUploadStatus();
		  fus.setFileName(newFileName);
		  fus.setFileNameCountSize(fileSize);
		  fus.setPath(savePath);
		  request.getSession().setAttribute("ImageFileUpLoad", fus);
		  try{
			   File uploadedFile = new File(savePath, newFileName);
			   item.write(uploadedFile);
			   VipImageManager ima=new VipImageManager();
			   ima.setSystemLinkAddress(savePath+newFileName);
			   ima.setFileName(newFileName);
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getSystemLinkAddress())));
			   ima.setMd5(md5);
			   ima.setType(fileExt);
			   dao.add(ima);
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/VipAppController/getImage?id="+ima.getId();
			   map.put("url",basePath);
			   map.put("filename", newFileName);
			   map.put("success", true);
			  return map;
		  }catch(Exception e){
			  map.put("success", false);
			  map.put("message", "上传图片失败。");
			  return map;
		  }
		 }
		}
		return map;
	}
	/**
	 * 上传视频
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadVideo")
	@ResponseBody
	public Map uploadVideo(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LinkManList lm = MoreUserManager.getLinkManList(request, dao);
		UserTable ut = MoreUserManager.getUserObject(request, dao);
		String conpanyId="";
		Object obj=request.getSession().getAttribute("conpanyId");
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		String savePath = FileManager.SAAS+conpanyId+File.separatorChar+FileManager.VIP_VIDEO+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "mp4,mp3,swf,avi,3gp");
		//最大文件大小
		long maxSize = 10000000;
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			map.put("success", false);
			map.put("message", "请选择视频。");
		 return map;
		}
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			uploadDir.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
		 dirFile.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items=null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
		 FileItem item = (FileItem) itr.next();
		 String fileName = item.getName();
		 long fileSize = item.getSize();
		 if (!item.isFormField()) {
		  //检查文件大小
		  if(item.getSize() > maxSize){
		   //out.println(getError("上传文件大小超过限制。"));
			  map.put("success", false);
				 map.put("message", "上传视频大小超过限制。最高位10m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  map.put("success", false);
				 map.put("message", "上传视频扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
				 return map;
		  }
		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		  String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		  FileUploadStatus fus=new FileUploadStatus();
		  fus.setFileName(newFileName);
		  fus.setFileNameCountSize(fileSize);
		  fus.setPath(savePath);
		  request.getSession().setAttribute("ImageFileUpLoad", fus);
		  try{
			   File uploadedFile = new File(savePath, newFileName);
			   item.write(uploadedFile);
			   VipVideoManager ima=new VipVideoManager();
			   ima.setSystemLinkAddress(savePath+newFileName);
			   ima.setFileName(newFileName);
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getSystemLinkAddress())));
			   ima.setMd5(md5);
			   ima.setType(fileExt);
			   dao.add(ima);
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/VipAppController/getVideo?id="+ima.getId();
			   
			   map.put("success", true);
			   map.put("url",basePath);
			
			   map.put("filename", newFileName);
			  return map;
		  }catch(Exception e){
			  map.put("success", false);
			  map.put("message", "上传视频失败。");
			  return map;
		  }
		 }
		}
		return map;
	}
	/**
	 * 获取图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getImage")
	@ResponseBody
	public void getImage(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		String id=req.getParameter("id");
		VipImageManager image=(VipImageManager) dao.getObject(Long.parseLong(id), "VipImageManager");
		res.addHeader("Location",image.getFileName());
		res.addHeader("Content-Disposition", "attachment; filename=" + image.getFileName());
		
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(image.getSystemLinkAddress());
		res.addHeader("Content-Length",file.length()+"");
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[2048];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	/**
	 * 获取视频
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getVideo")
	@ResponseBody
	public void getVideo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("video/mp4");
		String id=req.getParameter("id");
		
		VipVideoManager image=(VipVideoManager) dao.getObject(Long.parseLong(id), "VipVideoManager");
		res.addHeader("Location",image.getFileName());
		res.addHeader("Content-Disposition", "attachment; filename=" + image.getFileName());
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(image.getSystemLinkAddress());
		res.addHeader("Content-Length",file.length()+"");
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[2048];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	@RequestMapping(value = "/addImage")
	@ResponseBody
	public Map addImage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		
		String address=req.getParameter("address");
		String text=req.getParameter("text");
		String title=req.getParameter("title");
		String id=address.split("=")[1];
		VipImageManager vimfile=(VipImageManager) dao.getObject(Long.parseLong(id), "VipImageManager");
		VipImageMessage vim=new VipImageMessage();
		vim.setCity(conpany.getCity());
		vim.setConpanyId(conpany.getId());
		vim.setConpanyName(conpany.getConpanyName());
		vim.setCountry(conpany.getCountry());
		vim.setCreateDate(new Date());
		vim.setCreateVipId(lm.getId());
		vim.setCreateVipName(lm.getLinkManName());
		vim.setCreateVipPhone(lm.getLinkManPhone());
		vim.setDistrict(conpany.getDistrict());
		vim.setGif(false);
		vim.setHangyeId(conpany.getHangyeId());
		vim.setHangyeName(conpany.getHangyeName());
		vim.setImageLink(address);
		vim.setIndexNum(0);
		vim.setLookNum(0);
		vim.setMessage(text);
		vim.setProvince(conpany.getProvince());
		vim.setReturnNum(0);
		vim.setTitle(title);
		vim.setPass(0);
		vim.setTouXiangImage(ut.getTouxiang());
		vim.setUserTableId(ut.getId());
		vim.setZan(0);
		vim.setFileName(vimfile.getFileName());
		File file=new File(vimfile.getSystemLinkAddress());
		vim.setFileSize(file.length());
		dao.add(vim);
		return map;
	}
	@RequestMapping(value = "/addVideo")
	@ResponseBody
	public Map addVideo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String address=req.getParameter("address");
		String text=req.getParameter("text");
		String title=req.getParameter("title");
		String id=address.split("=")[1];
		VipVideoManager vimfile=(VipVideoManager) dao.getObject(Long.parseLong(id), "VipVideoManager");
		VipVideoMessage vim=new VipVideoMessage();
		vim.setCity(conpany.getCity());
		vim.setConpanyId(conpany.getId());
		vim.setConpanyName(conpany.getConpanyName());
		vim.setCountry(conpany.getCountry());
		vim.setCreateDate(new Date());
		vim.setCreateVipId(lm.getId());
		vim.setCreateVipName(lm.getLinkManName());
		vim.setCreateVipPhone(lm.getLinkManPhone());
		vim.setDistrict(conpany.getDistrict());
		vim.setHangyeId(conpany.getHangyeId());
		vim.setHangyeName(conpany.getHangyeName());
		vim.setVideoLink(address);
		vim.setIndexNum(0);
		vim.setLookNum(0);
		vim.setMessage(text);
		vim.setProvince(conpany.getProvince());
		vim.setReturnNum(0);
		vim.setTitle(title);
		vim.setTouXiangImage(ut.getTouxiang());
		vim.setUserTableId(ut.getId());
		vim.setPass(0);
		vim.setZan(0);
		vim.setFileName(vimfile.getFileName());
		File file=new File(vimfile.getSystemLinkAddress());
		vim.setFileSize(file.length());
		dao.add(vim);
		return map;
	}
	@RequestMapping(value = "/addText")
	@ResponseBody
	public Map addText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String text=req.getParameter("text");
		String title=req.getParameter("title");
		VipTextMessage vim=new VipTextMessage();
		vim.setCity(conpany.getCity());
		vim.setConpanyId(conpany.getId());
		vim.setConpanyName(conpany.getConpanyName());
		vim.setCountry(conpany.getCountry());
		vim.setCreateDate(new Date());
		vim.setCreateVipId(lm.getId());
		vim.setCreateVipName(lm.getLinkManName());
		vim.setCreateVipPhone(lm.getLinkManPhone());
		vim.setDistrict(conpany.getDistrict());
		vim.setHangyeId(conpany.getHangyeId());
		vim.setHangyeName(conpany.getHangyeName());
		vim.setIndexNum(0);
		vim.setLookNum(0);
		vim.setMessage(text);
		vim.setProvince(conpany.getProvince());
		vim.setReturnNum(0);
		vim.setTitle(title);
		vim.setTouXiangImage(ut.getTouxiang());
		vim.setUserTableId(ut.getId());
		vim.setZan(0);
		vim.setPass(0);
		dao.add(vim);
		return map;
	}
	@RequestMapping(value = "/addErShou")
	@ResponseBody
	public Map addErShou(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String address=req.getParameter("address");
		String name=req.getParameter("name");
		String phone=req.getParameter("phone");
		String price=req.getParameter("price");
		String type=req.getParameter("type");
		String text=req.getParameter("text");
		String[] adds=address.split(",");
		VipErShou vim=new VipErShou();
		vim.setCity(conpany.getCity());
		vim.setConpanyId(conpany.getId());
		vim.setConpanyName(conpany.getConpanyName());
		vim.setCountry(conpany.getCountry());
		vim.setCreateDate(new Date());
		vim.setCreateVipId(lm.getId());
		vim.setCreateVipName(lm.getLinkManName());
		vim.setCreateVipPhone(lm.getLinkManPhone());
		vim.setDistrict(conpany.getDistrict());
		vim.setHangyeId(conpany.getHangyeId());
		vim.setHangyeName(conpany.getHangyeName());
		vim.setIndexNum(0);
		vim.setMessage(text);
		vim.setProvince(conpany.getProvince());
		vim.setTouXiangImage(ut.getTouxiang());
		vim.setUserTableId(ut.getId());
		vim.setPrice(price);
		VipErShouType v=(VipErShouType) dao.getObject(Long.parseLong(type), "VipErShouType");
		vim.setErShouTypeId(v.getId());
		String vname=v.getVipErShouTypeName();
		vim.setErShouTypeName(vname);
		vim.setTitle(name);
		vim.setPhone(phone);
		vim.setPass(0);
		if(adds.length>=1){
			vim.setImage1(adds[0]);
		}
		if(adds.length>=2){
			vim.setImage2(adds[1]);	
		}
		if(adds.length>=3){
			vim.setImage3(adds[2]);
		}
		if(adds.length>=4){
			vim.setImage4(adds[3]);
		}
		dao.add(vim);
		return map;
	}
	@RequestMapping(value = "/imageLiuyanZan")
	@ResponseBody
	public Map imageLiuyanZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipImageMessageRetOne mes=(VipImageMessageRetOne) dao.getObject(Long.parseLong(id), "VipImageMessageRetOne");
		List<Object> list=dao.getObjectList("VipImageMessageRetOneZan", "where mainId="+mes.getId()+" and createVipId="+lm.getId());
		if(list.size()>0){
			VipImageMessageRetOneZan zan=(VipImageMessageRetOneZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipImageMessageRetOneZan zan=new VipImageMessageRetOneZan();
			zan.setCreateUserTableId(ut.getId());
			zan.setCreateVipId(lm.getId());	
			zan.setCreateVipName(lm.getLinkManName());
			zan.setMainId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/videoLiuyanZan")
	@ResponseBody
	public Map videoLiuyanZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipVideoMessageRetOne mes=(VipVideoMessageRetOne) dao.getObject(Long.parseLong(id), "VipVideoMessageRetOne");
		List<Object> list=dao.getObjectList("VipVideoMessageRetOneZan", "where mainId="+mes.getId()+" and createVipId="+lm.getId());
		if(list.size()>0){
			VipVideoMessageRetOneZan zan=(VipVideoMessageRetOneZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipVideoMessageRetOneZan zan=new VipVideoMessageRetOneZan();
			zan.setCreateUserTableId(ut.getId());
			zan.setCreateVipId(lm.getId());	
			zan.setCreateVipName(lm.getLinkManName());
			zan.setMainId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/textLiuyanZan")
	@ResponseBody
	public Map textLiuyanZan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String id=req.getParameter("id");
		VipTextMessageRetOne mes=(VipTextMessageRetOne) dao.getObject(Long.parseLong(id), "VipTextMessageRetOne");
		List<Object> list=dao.getObjectList("VipTextMessageRetOneZan", "where mainId="+mes.getId()+" and createVipId="+lm.getId());
		if(list.size()>0){
			VipTextMessageRetOneZan zan=(VipTextMessageRetOneZan) list.iterator().next();
			dao.delete(zan);
			mes.setZan(mes.getZan()-1);
			dao.update(mes);
			map.put("zan",false);
		}else{
			VipTextMessageRetOneZan zan=new VipTextMessageRetOneZan();
			zan.setCreateUserTableId(ut.getId());
			zan.setCreateVipId(lm.getId());	
			zan.setCreateVipName(lm.getLinkManName());
			zan.setMainId(mes.getId());
			zan.setTouXiangImage(ut.getTouxiang());
			dao.add(zan);
			mes.setZan(mes.getZan()+1);
			dao.update(mes);
			map.put("zan",true);
		}
		
		map.put("success", true);
		map.put("num", mes.getZan());
		return map;
	}
	@RequestMapping(value = "/sendImageLiuyan")
	@ResponseBody
	public Map sendImageLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipImageMessage mes=(VipImageMessage) dao.getObject(Long.parseLong(id), "VipImageMessage");
		VipImageMessageRetOne one=new VipImageMessageRetOne();
		one.setConpanyId(conpany.getId());
		one.setCreateDate(new Date());
		one.setCreateUserTableId(ut.getId());
		one.setCreateVipId(lm.getId());
		one.setCreateVipName(lm.getLinkManName());
		one.setMessage(message);
		one.setTouXiangImage(ut.getTouxiang());
		one.setVipImageMessageId(mes.getId());
		one.setVipImageMessageName(mes.getTitle());
		dao.add(one);
		mes.setReturnNum(mes.getReturnNum()+1);
		dao.update(mes);
		map.put("success", true);
		map.put("obj", one);
		return map;
	}
	@RequestMapping(value = "/sendImageLiuyanTwo")
	@ResponseBody
	public Map sendImageLiuyanTwo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		String toVip=req.getParameter("toVip");
		if(toVip==null){
			toVip="0";
		}
		int toVipNum=Integer.parseInt(toVip);
		VipImageMessageRetOne mes=(VipImageMessageRetOne) dao.getObject(Long.parseLong(id), "VipImageMessageRetOne");
		if(toVipNum==ut.getId()){
			map.put("success", false);
			map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
			return map;
		}
		if(toVip.equals("0")){
			if(mes.getCreateVipId()==lm.getId()){
				map.put("success", false);
				map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
				return map;
			}
		}
		VipImageMessageRetTwo two=new VipImageMessageRetTwo();
		two.setConpanyId(conpany.getId());
		two.setCreateDate(new Date());
		two.setCreateUserTableId(ut.getId());
		two.setCreateVipId(lm.getId());
		two.setCreateVipName(lm.getLinkManName());
		two.setMainId(mes.getId());
		two.setMessage(message);
		
		two.setTouXiangImage(ut.getTouxiang());
		two.setVipImageMessageId(mes.getVipImageMessageId());
		two.setVipImageMessageName(mes.getVipImageMessageName());
		if(toVip.equals("0")){
				two.setToCreateUserTouXiang(mes.getTouXiangImage());
				two.setToCcreateUserTableId(mes.getCreateUserTableId());
				two.setToCreateVipId(mes.getCreateVipId());
				two.setToCreateVipName(mes.getCreateVipName());
		}else{
			LinkManList lm2=(LinkManList) dao.getObject(Integer.parseInt(toVip), "LinkManList");
			UserTable ut2=(UserTable)dao.getObject(lm2.getUserTableId(), "UserTable");
			two.setToCreateUserTouXiang(ut2.getTouxiang());
			two.setToCcreateUserTableId(ut2.getId());
			two.setToCreateVipId(lm2.getId());
			two.setToCreateVipName(lm2.getLinkManName());
		}
		dao.add(two);
		List<Object> liuyan=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+two.getToCcreateUserTableId()+" and vipMessageId="+two.getVipImageMessageId()+" and type="+1);
		if(liuyan.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipImageMessageId());
			item.setVipMessageTitle(mes.getVipImageMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(two.getToCcreateUserTableId());
			liuyanobj.setToVipId(two.getToCreateVipId());
			liuyanobj.setToVipName(two.getToCreateVipName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipImageMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipImageMessageName());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipImageMessageId());
			item.setVipMessageTitle(mes.getVipImageMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		List<Object> liuyan2=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+ut.getId()+" and vipMessageId="+two.getVipImageMessageId()+" and type="+1);
		if(liuyan2.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan2.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipImageMessageId());
			item.setVipMessageTitle(mes.getVipImageMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(ut.getId());
			liuyanobj.setToVipId(lm.getId());
			liuyanobj.setToVipName(lm.getLinkManName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipImageMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipImageMessageName());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipImageMessageId());
			item.setVipMessageTitle(mes.getVipImageMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		map.put("success", true);
		map.put("obj",two);
		return map;
	}
	@RequestMapping(value = "/sendTextLiuyan")
	@ResponseBody
	public Map sendTextLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipTextMessage mes=(VipTextMessage) dao.getObject(Long.parseLong(id), "VipTextMessage");
		VipTextMessageRetOne one=new VipTextMessageRetOne();
		one.setConpanyId(conpany.getId());
		one.setCreateDate(new Date());
		one.setCreateUserTableId(ut.getId());
		one.setCreateVipId(lm.getId());
		one.setCreateVipName(lm.getLinkManName());
		one.setMessage(message);
		one.setTouXiangImage(ut.getTouxiang());
		one.setVipTextMessageId(mes.getId());
		one.setVipTextMessageName(mes.getTitle());
		dao.add(one);
		mes.setReturnNum(mes.getReturnNum()+1);
		dao.update(mes);
		map.put("success", true);
		map.put("obj", one);
		return map;
	}
	@RequestMapping(value = "/sendTextLiuyanTwo")
	@ResponseBody
	public Map sendTextLiuyanTwo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipTextMessageRetOne mes=(VipTextMessageRetOne) dao.getObject(Long.parseLong(id), "VipTextMessageRetOne");
		String toVip=req.getParameter("toVip");
		if(toVip==null){
			toVip="0";
		}
		int toVipNum=Integer.parseInt(toVip);
		if(toVipNum==ut.getId()){
			map.put("success", false);
			map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
			return map;
		}
		if(toVip.equals("0")){
			if(mes.getCreateVipId()==lm.getId()){
				map.put("success", false);
				map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
				return map;
			}
		}
		VipTextMessageRetTwo two=new VipTextMessageRetTwo();
		two.setConpanyId(conpany.getId());
		two.setCreateDate(new Date());
		two.setCreateUserTableId(ut.getId());
		two.setCreateVipId(lm.getId());
		two.setCreateVipName(lm.getLinkManName());
		two.setMainId(mes.getId());
		two.setMessage(message);
		two.setTouXiangImage(ut.getTouxiang());
		two.setVipTextMessageId(mes.getVipTextMessageId());
		two.setVipTextMessageName(mes.getVipTextMessageName());
		if(toVip.equals("0")){
			two.setToCreateUserTouXiang(mes.getTouXiangImage());
			two.setToCcreateUserTableId(mes.getCreateUserTableId());
			two.setToCreateVipId(mes.getCreateVipId());
			two.setToCreateVipName(mes.getCreateVipName());
		}else{
			LinkManList lm2=(LinkManList) dao.getObject(Integer.parseInt(toVip), "LinkManList");
			UserTable ut2=(UserTable)dao.getObject(lm2.getUserTableId(), "UserTable");
			two.setToCreateUserTouXiang(ut2.getTouxiang());
			two.setToCcreateUserTableId(ut2.getId());
			two.setToCreateVipId(lm2.getId());
			two.setToCreateVipName(lm2.getLinkManName());
		}
		dao.add(two);
		List<Object> liuyan=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+two.getToCcreateUserTableId()+" and vipMessageId="+two.getVipTextMessageId()+" and type="+3);
		if(liuyan.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipTextMessageId());
			item.setVipMessageTitle(mes.getVipTextMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(two.getToCcreateUserTableId());
			liuyanobj.setToVipId(two.getToCreateVipId());
			liuyanobj.setToVipName(two.getToCreateVipName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipTextMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipTextMessageName());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipTextMessageId());
			item.setVipMessageTitle(mes.getVipTextMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		List<Object> liuyan2=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+ut.getId()+" and vipMessageId="+two.getVipTextMessageId()+" and type="+3);
		if(liuyan2.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan2.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipTextMessageId());
			item.setVipMessageTitle(mes.getVipTextMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(ut.getId());
			liuyanobj.setToVipId(lm.getId());
			liuyanobj.setToVipName(lm.getLinkManName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipTextMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipTextMessageName());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipTextMessageId());
			item.setVipMessageTitle(mes.getVipTextMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		map.put("success", true);
		map.put("obj",two);
		return map;
	}
	@RequestMapping(value = "/sendVideoLiuyan")
	@ResponseBody
	public Map sendVideoLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipVideoMessage mes=(VipVideoMessage) dao.getObject(Long.parseLong(id), "VipVideoMessage");
		VipVideoMessageRetOne one=new VipVideoMessageRetOne();
		one.setConpanyId(conpany.getId());
		one.setCreateDate(new Date());
		one.setCreateUserTableId(ut.getId());
		one.setCreateVipId(lm.getId());
		one.setCreateVipName(lm.getLinkManName());
		one.setMessage(message);
		one.setTouXiangImage(ut.getTouxiang());
		one.setVipVideoMessageId(mes.getId());
		one.setVipVideoMessageTitle(mes.getTitle());
		dao.add(one);
		mes.setReturnNum(mes.getReturnNum()+1);
		dao.update(mes);
		map.put("success", true);
		map.put("obj", one);
		return map;
	}
	@RequestMapping(value = "/sendVideoLiuyanTwo")
	@ResponseBody
	public Map sendVideoLiuyanTwo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipVideoMessageRetOne mes=(VipVideoMessageRetOne) dao.getObject(Long.parseLong(id), "VipVideoMessageRetOne");
		String toVip=req.getParameter("toVip");
		if(toVip==null){
			toVip="0";
		}
		int toVipNum=Integer.parseInt(toVip);
		if(toVipNum==ut.getId()){
			map.put("success", false);
			map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
			return map;
		}
		if(toVip.equals("0")){
			if(mes.getCreateVipId()==lm.getId()){
				map.put("success", false);
				map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
				return map;
			}
		}
		VipVideoMessageRetTwo two=new VipVideoMessageRetTwo();
		two.setConpanyId(conpany.getId());
		two.setCreateDate(new Date());
		two.setCreateUserTableId(ut.getId());
		two.setCreateVipId(lm.getId());
		two.setCreateVipName(lm.getLinkManName());
		two.setMainId(mes.getId());
		two.setMessage(message);
		two.setTouXiangImage(ut.getTouxiang());
		two.setVipVideoMessageId(mes.getVipVideoMessageId());
		two.setVipVideoMessageTitle(mes.getVipVideoMessageTitle());
		if(toVip.equals("0")){
			two.setToCreateUserTouXiang(mes.getTouXiangImage());
			two.setToCcreateUserTableId(mes.getCreateUserTableId());
			two.setToCreateVipId(mes.getCreateVipId());
			two.setToCreateVipName(mes.getCreateVipName());
		}else{
			LinkManList lm2=(LinkManList) dao.getObject(Integer.parseInt(toVip), "LinkManList");
			UserTable ut2=(UserTable)dao.getObject(lm2.getUserTableId(), "UserTable");
			two.setToCreateUserTouXiang(ut2.getTouxiang());
			two.setToCcreateUserTableId(ut2.getId());
			two.setToCreateVipId(lm2.getId());
			two.setToCreateVipName(lm2.getLinkManName());
		}
		dao.add(two);
		List<Object> liuyan=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+two.getToCcreateUserTableId()+" and vipMessageId="+two.getVipVideoMessageId()+" and type="+2);
		if(liuyan.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipVideoMessageId());
			item.setVipMessageTitle(mes.getVipVideoMessageTitle());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(two.getToCcreateUserTableId());
			liuyanobj.setToVipId(two.getToCreateVipId());
			liuyanobj.setToVipName(two.getToCreateVipName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipVideoMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipVideoMessageTitle());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipVideoMessageId());
			item.setVipMessageTitle(mes.getVipVideoMessageTitle());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		List<Object> liuyan2=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+ut.getId()+" and vipMessageId="+two.getVipVideoMessageId()+" and type="+2);
		if(liuyan2.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan2.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipVideoMessageId());
			item.setVipMessageTitle(mes.getVipVideoMessageTitle());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(ut.getId());
			liuyanobj.setToVipId(lm.getId());
			liuyanobj.setToVipName(lm.getLinkManName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipVideoMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipVideoMessageTitle());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipVideoMessageId());
			item.setVipMessageTitle(mes.getVipVideoMessageTitle());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		map.put("success", true);
		map.put("obj",two);
		return map;
	}
	@RequestMapping(value = "/sendErShouLiuyan")
	@ResponseBody
	public Map sendErShouLiuyan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipErShou mes=(VipErShou) dao.getObject(Long.parseLong(id), "VipErShou");
		VipErShouReturnMessageOne one=new VipErShouReturnMessageOne();
		one.setConpanyId(conpany.getId());
		one.setCreateDate(new Date());
		one.setCreateUserTableId(ut.getId());
		one.setCreateVipId(lm.getId());
		one.setCreateVipName(lm.getLinkManName());
		one.setMessage(message);
		one.setTouXiangImage(ut.getTouxiang());
		one.setVipErShouMessageId(mes.getId());
		one.setVipErShouMessageName(mes.getTitle());
		dao.add(one);
		map.put("success", true);
		map.put("obj", one);
		return map;
	}
	@RequestMapping(value = "/sendErShouLiuyanTwo")
	@ResponseBody
	public Map sendErShouLiuyanTwo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String id=req.getParameter("id");
		String message=req.getParameter("message");
		VipErShouReturnMessageOne mes=(VipErShouReturnMessageOne) dao.getObject(Long.parseLong(id), "VipErShouReturnMessageOne");
		String toVip=req.getParameter("toVip");
		if(toVip==null){
			toVip="0";
		}
		int toVipNum=Integer.parseInt(toVip);
		if(toVipNum==ut.getId()){
			map.put("success", false);
			map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
			return map;
		}
		if(toVip.equals("0")){
			if(mes.getCreateVipId()==lm.getId()){
				map.put("success", false);
				map.put("info", "亲，您不能回复自己的留言哦。您可以点击回复您留言的人名进行针对的回答。");
				return map;
			}
		}
		VipErShouReturnMessageTwo two=new VipErShouReturnMessageTwo();
		two.setConpanyId(conpany.getId());
		two.setCreateDate(new Date());
		two.setCreateUserTableId(ut.getId());
		two.setCreateVipId(lm.getId());
		two.setCreateVipName(lm.getLinkManName());
		two.setMainId(mes.getId());
		two.setMessage(message);
		two.setTouXiangImage(ut.getTouxiang());
		two.setVipErShouMessageId(mes.getVipErShouMessageId());
		two.setVipErShouMessageName(mes.getVipErShouMessageName());
		if(toVip.equals("0")){
			two.setToCreateUserTouXiang(mes.getTouXiangImage());
			two.setToCcreateUserTableId(mes.getCreateUserTableId());
			two.setToCreateVipId(mes.getCreateVipId());
			two.setToCreateVipName(mes.getCreateVipName());
		}else{
			LinkManList lm2=(LinkManList) dao.getObject(Integer.parseInt(toVip), "LinkManList");
			UserTable ut2=(UserTable)dao.getObject(lm2.getUserTableId(), "UserTable");
			two.setToCreateUserTouXiang(ut2.getTouxiang());
			two.setToCcreateUserTableId(ut2.getId());
			two.setToCreateVipId(lm2.getId());
			two.setToCreateVipName(lm2.getLinkManName());
		}
		dao.add(two);
		List<Object> liuyan=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+two.getToCcreateUserTableId()+" and vipMessageId="+two.getVipErShouMessageId()+" and type="+2);
		if(liuyan.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipErShouMessageId());
			item.setVipMessageTitle(mes.getVipErShouMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(two.getToCcreateUserTableId());
			liuyanobj.setToVipId(two.getToCreateVipId());
			liuyanobj.setToVipName(two.getToCreateVipName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipErShouMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipErShouMessageName());
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipErShouMessageId());
			item.setVipMessageTitle(mes.getVipErShouMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		List<Object> liuyan2=dao.getObjectList("VipLiuYanList", "where messageOneId="+mes.getId()+" and conpanyId="+conpany.getId()+" and toUserTableId="+ut.getId()+" and vipMessageId="+two.getVipErShouMessageId()+" and type="+2);
		if(liuyan2.size()>0){
			VipLiuYanList liuyanobj=(VipLiuYanList) liuyan2.iterator().next();
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipErShouMessageId());
			item.setVipMessageTitle(mes.getVipErShouMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
			liuyanobj.setCreateDate(new Date());
			dao.update(liuyanobj);
		}else{
			VipLiuYanList liuyanobj=new VipLiuYanList();
			liuyanobj.setConpanyId(conpany.getId());
			liuyanobj.setCreateDate(new Date());
			liuyanobj.setMessageOneId(mes.getId());
			liuyanobj.setToUserTableId(ut.getId());
			liuyanobj.setToVipId(lm.getId());
			liuyanobj.setToVipName(lm.getLinkManName());
			liuyanobj.setType(1);
			liuyanobj.setToVipTouxiang(ut.getTouxiang());
			liuyanobj.setVipMessageId(two.getVipErShouMessageId());
			liuyanobj.setVipMessageTitle(mes.getVipErShouMessageName());
			
			dao.add(liuyanobj);
			VipLiuYanItem item=new VipLiuYanItem();
			item.setConpanyId(conpany.getId());
			item.setFromUserTableId(ut.getId());
			item.setFromVipId(lm.getId());
			item.setFromVipName(lm.getLinkManName());
			item.setFromVipTouxiang(ut.getTouxiang());
			item.setMessageOneId(mes.getId());
			item.setSendDate(new Date());
			item.setVipLiuYanListid(liuyanobj.getId());
			item.setVipMessageId(two.getVipErShouMessageId());
			item.setVipMessageTitle(mes.getVipErShouMessageName());
			item.setMessage(message);
			item.setType(liuyanobj.getType());
			dao.add(item);
		}
		map.put("success", true);
		map.put("obj",two);
		return map;
	}
	@RequestMapping(value = "/getVipList")
	@ResponseBody
	public Map getVipList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		map.put("success", true);
		map.put("data", dao.getVipSetList(conpany.getId()));
		return map;
	}
	@RequestMapping(value = "/jifenduihuan_data")
	@ResponseBody
	public Map jifenduihuan_data(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "15";
		}
		map.put("data", dao.getScoreDuihuansList(
				conpany.getId(), Integer.parseInt(nowpage),
				Integer.parseInt(countNum)));
		return map;
	}
	@RequestMapping(value = "/UserToScoreduihuan")
	@ResponseBody
	public Map UserToScoreduihuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId;
		Object obj2=req.getSession().getAttribute("conpanyId");
		if(obj2 instanceof Long){
			conpanyId=obj2+"";
			
		}else{
			conpanyId=(String) obj2;
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		if (lm != null) {
			String id = req.getParameter("id");
			String num = req.getParameter("num");
			int numint=Integer.parseInt(num);
			if(numint<=0){
				map.put("success", false);
				map.put("info", "出错，兑换数量不能小于0");
				return map;
			}
			Object obj = dao.getObject(Long.parseLong(id), "ScoreDuihuan",
					conpany.getId());
			if (obj != null) {
				ScoreDuihuan vip = (ScoreDuihuan) obj;
				double userscore = lm.getLinkManScore();
				double score = vip.getScore() * Long.parseLong(num);
				if (userscore >= score) {
					if (vip.getNum() >= Long.parseLong(num)) {
						vip.setNum(vip.getNum() - Integer.parseInt(num));
						dao.update(vip);
						lm.setLinkManScore(DoubleUtil.sub(lm.getLinkManScore() , score));
						dao.update(lm);
						ScoreToGoodsList s = new ScoreToGoodsList();
						s.setXuliehao("sc" + new Date().getTime() + lm.getId()
								+ vip.getId());
						s.setName(vip.getName());
						s.setNum(Integer.parseInt(num));
						s.setScoreDuiHuanId(vip.getId());
						s.setUses(false);
						s.setUserid(ut.getId());
						s.setLinkmainId(lm.getId());
						s.setConpanyId(conpany.getId());
						dao.add(s);
						String content="您好,"+lm.getLinkManName()+"您积分兑换了<"+s.getName()+">,序列号为:"+s.getXuliehao()+dao.getMessageSetContent(lm.getConpanyId(), MessageSet.REDUCE_SCORE, lm, score,0.0, null,null);
						dao.getSendMessage(lm.getLinkManPhone(),content, lm.getConpanyId(), MessageSet.REDUCE_SCORE,false);
						map.put("data", s);
						map.put("success", true);
						map.put("info", "兑换完成.序列号为：" + s.getXuliehao() + "");
					} else {
						map.put("success", false);
						map.put("info", "此商品不足，不够您所兑换的数量");
					}
				} else {
					map.put("success", false);
					map.put("info", "您的积分不够兑换此商品");
				}

			} else {
				map.put("success", false);
				map.put("info", "没有找到要兑换的商品");
			}
		} else {
			map.put("success", false);
			map.put("info", "您没有绑定账户");
		}

		return map;
	}
	@RequestMapping(value = "/getjiaoliu")
	@ResponseBody
	public Map getjiaoliu(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "1";
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		List<VipLiuYanListDTO> dtolist=new ArrayList<VipLiuYanListDTO>();
		List<Object> list=dao.getObjectListPage("VipLiuYanList", "where conpanyId="+conpany.getId()+" and toUserTableId="+ut.getId()+" order by createDate",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		Iterator i=list.iterator();
		while(i.hasNext()){
			VipLiuYanList objs=(VipLiuYanList) i.next();
			VipLiuYanListDTO dto=new VipLiuYanListDTO();
			dto.setObj(objs);
			List<Object> data=dao.getObjectList("VipLiuYanItem","where vipLiuYanListid="+objs.getId());
			dto.setList(data);
			dtolist.add(dto);
		}
		map.put("success", true);
		map.put("data", dtolist);
		return map;
	}
	@RequestMapping(value = "/getGonggao")
	@ResponseBody
	public Map getGonggao(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "5";
		}
		Conpany conpany=(Conpany) dao.getObject(Long.parseLong(conpanyId), "Conpany");
		List<Object> list=dao.getObjectListPage("VipGonggao", "where conpanyId="+conpany.getId()+" order by sendDate desc",Integer.parseInt(nowpage),Integer.parseInt(countNum));
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	@RequestMapping(value = "/passwordChange")
	@ResponseBody
	public Map passwordChange(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String oldpassword=req.getParameter("oldpassword");
		String newpassword=req.getParameter("newpassword");
		String renewpassword=req.getParameter("renewpassword");
		if(ut.getPassword().equals(oldpassword)){
			if(newpassword.equals(renewpassword)){
				ut.setPassword(newpassword);
				dao.update(ut);
				map.put("success", true);
				map.put("info", "修改完成");
			}else{
				map.put("success", false);
				map.put("info", "错误，两次输入的新密码不一样");
			}
		}else{
			map.put("success", false);
			map.put("info", "错误，旧密码错误");
		}
		return map;
	}
	@RequestMapping(value = "/jifenDuiHuanjuanHuanJIfen")
	@ResponseBody
	public Map jifenDuiHuanjuanHuanJIfen(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String num=req.getParameter("num");
		List<Object> score=dao.getObjectList("ConpanyScoreNum", "where num="+num);
		if(score.size()>0){
			ConpanyScoreNum scoreobj=(ConpanyScoreNum) score.iterator().next();
			Orders object=(Orders) dao.getObject(scoreobj.getOrderId(),"Orders");
			object.setLinkmanId(lm.getId());
			object.setLinkmanName(lm.getLinkManName());
			object.setUserTableId(ut.getId());
			object.setPay(true);
			dao.update(object);
			lm.setLinkManScore(DoubleUtil.round(DoubleUtil.add(lm.getLinkManMaxScore(), scoreobj.getScore()), 3));
			lm.setLinkManMaxScore(DoubleUtil.round(DoubleUtil.add(lm.getLinkManMaxScore(),scoreobj.getScore()),3));
			List<VIPSet> listvip = dao.getVipSetList(lm.getConpanyId());
			VIPSet vip = null;
			for (int i = 0; i < listvip.size(); i++) {
				if (listvip.get(i).getScore() <= lm.getLinkManMaxScore()) {
					if (vip != null) {
						if (vip.getScore() < listvip.get(i).getScore()) {
							vip = listvip.get(i);
						}
					} else {
						vip = listvip.get(i);
					}
				}
			}
			if (vip!=null&&lm.getVipidNum()!=vip.getId()) {
				lm.setVipidNum(vip.getId());
				lm.setVipLevel(vip.getName());
				lm.setVipMarks(vip.getMarks());
				map.put("success", true);
				map.put("info", "兑换成功，vip等级改变");
			}else{
				map.put("success", true);
				map.put("info", "兑换成功");
			}
		}else{
			map.put("success", false);
			map.put("info", "该积分序列号不存在");
		}
		return map;
	}
	@RequestMapping(value = "/reflashInfo")
	@ResponseBody
	public Map reflashInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		MoreUserManager.getLinkManList(req,dao,true);
		lm = MoreUserManager.getLinkManList(req, dao);
		if(lm.getVipidNum()==0){
			lm.setVipLevel("目前积分数无法达到第一个等级");
			lm.setVipMarks("目前积分数无法达到第一个等级");
		}
		map.put("success", true);
		map.put("obj", lm);
		return map;
	}
	@RequestMapping(value = "/getvipDuihuanjuan")
	@ResponseBody
	public Map getvipDuihuanjuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "20";
		}
		List list = dao.getObjectListPage("ScoreToGoodsList", "where conpanyId="
				+ conpanyId
				+ " and linkmainId = " + lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getvipzhongjiangjuan")
	@ResponseBody
	public Map getvipzhongjiangjuan(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		List list = dao.getObjectListPage("NumLibs", "where conpanyId="
				+ conpanyId
				+ " and linkmanId = " + lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getVipOrderList")
	@ResponseBody
	public Map getVipOrderList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		List list = dao.getObjectListPage("Orders", "where conpanyId="
				+ conpanyId
				+ " and linkmanId = "+lm.getId()+" order by id desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/getVipOrderItemList")
	@ResponseBody
	public Map getVipOrderItemList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		Map map = new HashMap<String, Object>();
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		String id = req.getParameter("id");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		WeixinOrder wxg=null;
		List list = dao.getObjectList("WeixinOrder", "where conpanyId="
				+ conpanyId
				+ " and orderId = " + id);
		if(list.size()>0){
			wxg=(WeixinOrder) list.get(0);
		}
		List list2 = dao.getObjectList("OrdersItem", "where conpanyId="
				+ conpanyId
				+ " and inOrderId = " + id + "");
		map.put("obj", wxg);
		map.put("data", list2);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/goodType")
	@ResponseBody
	public Map goodType(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		List<Object> list=dao.getObjectList("WeiXinGoodsType", "where conpanyId="+conpanyId);
		map.put("success", true);
		map.put("data", list);
		return map;
	}
	@RequestMapping(value = "/getGoods")
	@ResponseBody
	public Map getGoods(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		String typeId=req.getParameter("typeId");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		if(typeId==null||typeId.equals("0")||typeId.equals("")){
			List<Object> list=dao.getObjectListPage("WeiXinGoods", "where conpanyId="+conpanyId,Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("data", list);
		}else{
			List<Object> list=dao.getObjectListPage("WeiXinGoods", "where conpanyId="+conpanyId+" and goodTypeId="+typeId,Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("data", list);
		}
		map.put("success", true);
		
		return map;
	}
	@RequestMapping(value = "/setUserInfo")
	@ResponseBody
	public Map setUserInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		String src=req.getParameter("src");
		String nicheng=req.getParameter("nicheng");
		if(src!=null&&!src.equals("-1")){
			ut.setTouxiang(src);
			dao.update(ut);
		}
		if(nicheng!=null&&!nicheng.equals("-1")){
			lm.setLinkManName(nicheng);
			dao.update(lm);
		}
		map.put("success", true);
		map.put("info", "完成");
		return map;
	}
	@RequestMapping(value = "/addCar")
	@ResponseBody
	public Map addCar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
			Map map = new HashMap<String, Object>();
			LinkManList lm = MoreUserManager.getLinkManList(req, dao);
			String id = req.getParameter("id");
			String conpanyId;
			Object obj=req.getSession().getAttribute("conpanyId");
			if(obj==null){
				obj=req.getParameter("conpanyId");
			}
			if(obj instanceof Long){
				conpanyId=obj+"";
			}else{
				conpanyId=(String) obj;
			}
			if(lm!=null){
			addCarSession(req, Long.parseLong(id), conpanyId);
			map.put("success", true);
			map.put("info", "添加成功，去购物车结算");
		}else{
			map.put("success", false);
			map.put("info", "您不是会员无法直接购物，请加入会员");
		}
		return map;
	}

	@RequestMapping(value = "/deleteCarItem")
	@ResponseBody
	public Map deleteCarItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String id = req.getParameter("id");
		String type = req.getParameter("type");
		String conpanyId;
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
		}else{
			conpanyId=(String) obj;
		}
		deleteCarSession(req, Long.parseLong(id), type, conpanyId);
		return map;
	}
	public boolean addCarSession(HttpServletRequest req, long goodsid,
			String conpanyId) {
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		if (buycar == null) {
			buycar = new BuyCar();
			Map<Long, List<Map<Long, OrdersItem>>> conpanyList = new HashMap<Long, List<Map<Long, OrdersItem>>>();
			buycar.setOrderList(conpanyList);
			buycar.setUserid(lm.getUserTableId());
			buycar.setUsername(lm.getLinkManName());
			buycar.setPhone(lm.getLinkManPhone());
			buycar.setMarks("");
			buycar.setAddress("");
			buycar.setLinkManId(lm.getId());
			req.getSession().setAttribute("buycar", buycar);
		}
		WeiXinGoods gt = null;
		if (conpanyId == null) {
			gt = (WeiXinGoods) dao.getObject(goodsid, "WeiXinGoods",
					Long.parseLong(conpanyId));
		} else {
			gt = (WeiXinGoods) dao.getObject(goodsid, "WeiXinGoods",
					Long.parseLong(conpanyId));
		}
		if (gt != null && gt.isUseShow()) {
			Map<Long, List<Map<Long, OrdersItem>>> conpanyList = buycar
					.getOrderList();
			GoodsTable gts = null;
			if (conpanyId == null) {
				gts = (GoodsTable) dao.getObject(gt.getGoodsId(), "GoodsTable",
						Long.parseLong(conpanyId));
			} else {
				gts = (GoodsTable) dao.getObject(gt.getGoodsId(), "GoodsTable",
						Long.parseLong(conpanyId));
			}
			OrdersItem order = null;
			List<Map<Long, OrdersItem>> orderList = null;
			if (conpanyId == null) {
				orderList = conpanyList.get(Long.parseLong(conpanyId));
			} else {
				orderList = conpanyList.get(Long.parseLong(conpanyId));
			}
			if (orderList == null) {
				orderList = new ArrayList<Map<Long, OrdersItem>>();
				conpanyList.put(Long.parseLong(conpanyId), orderList);
			}
			for (int ii = 0; ii < orderList.size(); ii++) {
				Map<Long, OrdersItem> map2 = orderList.get(ii);
				order = map2.get(goodsid);
			}
			if (order == null) {
				order = new OrdersItem();
				order.setCodeid(gts.getCodeid());
				order.setConpanyId(gt.getConpanyId());
				order.setCreateDate(new Date());
				order.setGoodsId(gts.getId());
				order.setGoodsinPrice(gts.getInPrice());
				order.setGoodsModel(gts.getGoodsModel());
				order.setGoodsName(gts.getGoodsName());
				order.setGoodsNum(1);
				order.setGoodsSourceId(0);
				order.setGoodsSourceName("");
				order.setGoodsToStorehouseId(0);
				order.setGoodsToStorehouseName("");
				order.setGoodsType(gts.getGoodsType());
				order.setInOrderId(gt.getId());
				order.setMarks("");
				order.setPrice(gts.getPrice());
				order.setScore(gts.getScore());
				order.setSpell(gts.getSpell());
				Map<Long, OrdersItem> map = new HashMap<Long, OrdersItem>();
				map.put(goodsid, order);
				orderList.add(map);

			} else {
				order.setGoodsNum(order.getGoodsNum() + 1);
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean deleteCarSession(HttpServletRequest req, long goodsid,
			String type, String conpanyId) {
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		if (buycar == null) {
			return false;
		} else {
			if (type != null) {
				Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
						.getOrderList();
				List<Map<Long, OrdersItem>> ordersItemList = orderlist.get(Long
						.parseLong(conpanyId));
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					if (map2.get(goodsid) != null) {
						map2.remove(goodsid);
					}
				}
			} else {
				Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
						.getOrderList();
				List<Map<Long, OrdersItem>> ordersItemList = orderlist.get(Long
						.parseLong(conpanyId));
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					if (map2.get(goodsid) != null) {
						OrdersItem orderItem = map2.get(goodsid);
						orderItem.setGoodsNum(orderItem.getGoodsNum() - 1);
					}
				}
			}
		}
		return true;
	}
	@RequestMapping(value = "/getCar")
	@ResponseBody
	public Map getCar(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		if (buycar == null) {
			map.put("success", false);
		} else {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
					.getOrderList();
			Set<Long> set = orderlist.keySet();
			Iterator<Long> i = set.iterator();
			while (i.hasNext()) {
				Long conpanid = i.next();
				Conpany conpany = (Conpany) dao.getObject(conpanid, "Conpany");
				List<Map<Long, OrdersItem>> ordersItemList = orderlist
						.get(conpanid);
				Map<String, Object> carConpany = new HashMap<String, Object>();
				List<OrdersItem> list2 = new ArrayList<OrdersItem>();
				for (int ii = 0; ii < ordersItemList.size(); ii++) {
					Map<Long, OrdersItem> map2 = ordersItemList.get(ii);
					Set<Long> set2 = map2.keySet();
					if(set2.iterator().hasNext()){
						OrdersItem OrdersItem2 = map2.get(set2.iterator().next());
						list2.add(OrdersItem2);
					}
				}
				carConpany.put("data", list2);
				carConpany.put("conpany", conpany);
				list.add(carConpany);
			}
			map.put("success", true);
			map.put("data", list);
		}
		return map;
	}
	@RequestMapping(value = "/submitOrder")
	@ResponseBody
	public Map submitOrder(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String conpanyId="";
		Object obj=req.getSession().getAttribute("conpanyId");
		if(obj==null){
			obj=req.getParameter("conpanyId");
		}
		if(obj instanceof Long){
			conpanyId=obj+"";
			
		}else{
			conpanyId=(String) obj;
		}
		LinkManList lm = MoreUserManager.getLinkManList(req, dao);
		UserTable ut = MoreUserManager.getUserObject(req, dao);
		String address=req.getParameter("address");
		String phone=req.getParameter("phone");
		String mark=req.getParameter("mark");
		StringBuffer orderContentMessage=new StringBuffer();
		BuyCar buycar = (BuyCar) req.getSession().getAttribute("buycar");
		Map<Long, List<Map<Long, OrdersItem>>> orderlist = buycar
				.getOrderList();
		List<Map<Long, OrdersItem>> orderList=orderlist.get(Long.parseLong(conpanyId));
		Orders order=new Orders();
		WeixinOrder wxorder=new WeixinOrder();
		//迭代出订单并且保存。
		List<Object> objlist=dao.getObjectList("LinkManList", "where userTableId="+ut.getId()+" and conpanyId="+conpanyId);
		double countPrice=0;
		LinkManList link=null;
		if(objlist.size()>0){
			link=(LinkManList) objlist.get(0);
		}
		if(link!=null){
			MessageSet set2=dao.getMessageSet(link.getConpanyId());
			order.setChanceId(link.getChanceListId());
			order.setChanceName(link.getChanceListName());
			order.setConpanyId(link.getConpanyId());
			order.setCountPrice(0);
			order.setCreateDate(new Date());
			order.setCreateUser(0);
			order.setCreateUserName("微信订单");
			order.setInStoreUser(0);
			order.setInStoreUserName("订单");
			order.setLinkmanId(link.getId());
			order.setLinkmanName(link.getLinkManName());
			order.setMarks("微信订单");
			order.setOrderNum("");
			order.setPay(false);
			order.setState(0);
			order.setTitle("微信："+link.getLinkManName()+"订单");
			dao.add(order);
			String orderNum="HDORDER_"+order.getId()+link.getConpanyId()+link.getId()+new Date().getTime();
			order.setOrderNum(orderNum);
			dao.update(order);
			wxorder.setAddress(address);
			wxorder.setLinkManId(link.getId());
			wxorder.setMarks(mark);
			wxorder.setNum(orderNum);
			wxorder.setOrderId(order.getId());
			wxorder.setPhone(phone);
			wxorder.setStartDate(new Date());
			wxorder.setUsername(ut.getTrueName());
			wxorder.setUserid(ut.getId());
			wxorder.setConpanyId(link.getConpanyId());
			dao.add(wxorder);
			for(int i=0;i<orderList.size();i++){
				Map<Long,OrdersItem> map2=orderList.get(i);
				Set set=map2.keySet();
				Iterator i2=set.iterator();
				while(i2.hasNext()){
					OrdersItem item=map2.get(i2.next());
					item.setInOrderId(order.getId());
					item.setCreateDate(new Date());
					item.setMarks("微信购物");
					dao.add(item);
					countPrice=DoubleUtil.add(countPrice, DoubleUtil.mul(item.getPrice(), item.getGoodsNum()));
					orderContentMessage.append("[名称-").append(item.getGoodsName()).append(",数量-").append(item.getGoodsNum()).append(",单价-").append(item.getPrice()).append("]");
				}
			}
			order.setCountPrice(countPrice);
			dao.update(order);
			String content=dao.getMessageSetContent(lm.getConpanyId(), MessageSet.ORDER_INFO_TO_USER, lm, 0.0, countPrice, order,orderContentMessage.toString());
			dao.getSendMessage(lm.getLinkManPhone(),content, lm.getConpanyId(), MessageSet.ORDER_INFO_TO_USER,false);
			orderList.clear();
			map.put("success", true);
		}else{
			map.put("info", "您没有关注此店面微信，请先关注微信后再进行购物");
			map.put("success", false);
		}
		
		return map;
	}
}
