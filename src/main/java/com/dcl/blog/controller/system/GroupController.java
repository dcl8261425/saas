package com.dcl.blog.controller.system;

import java.util.ArrayList;
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

import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserLinkRole;
import com.dcl.blog.model.GroupConpanyLinkUser;
import com.dcl.blog.model.Role;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.model.SoftPermissionLinkConpanyRole;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
/**
 * 公司的管理controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/Group")
public class GroupController {
	private static final Logger logger = LoggerFactory
			.getLogger(GroupController.class);
	private DaoService dao;
	public static List<Map> data;
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
	 * 创建组
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/createGroup")
	@ResponseBody
	public Map<String, Object> createGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String groupId=req.getParameter("groupId");
			String groupName=req.getParameter("groupName");
			String groupMarks=req.getParameter("groupMarks");
			long groupidLong=0;
			if(groupId==null||groupId.equals("0")){
				List<?> listconpObj=dao.getObjectList("ConpanyGroup", "where conpanyId="+user.getConpanyId()+" and upLevelConpanyGroup=0");
				ConpanyGroup cg=(ConpanyGroup)listconpObj.iterator().next();
				groupidLong=cg.getId();
			}else{
				groupidLong=Long.parseLong(groupId);
			}
			ConpanyGroup group=new ConpanyGroup();
			group.setConpanyId(user.getConpanyId());
			group.setCreateConpanyGroupUserId(user.getId());
			group.setCreateConpanyGroupUserName(user.getUsername());
			group.setCreateConpanyGroupUserTrueName(user.getTrueName());
			group.setGroupMarks(groupMarks);
			group.setGroupName(groupName);
			group.setUpLevelConpanyGroup(groupidLong);
			group.setUserNum(1);
			dao.add(group);
			List<SoftPermission> list=dao.getSoftPermissionByConpanyUser(user, groupidLong);
			Role role=new Role();
			role.setConpanyId(user.getConpanyId());
			role.setGroupid(group.getId());
			role.setGroupName(group.getGroupName());
			role.setMarks("组管理员，创建者的角色，拥有创建者所有的的权限");
			role.setName("组创建者");
			role.setConpanyAdminRole(true);
			dao.add(role);
			Iterator<SoftPermission> si=list.iterator();
			while(si.hasNext()){
				SoftPermission sp=si.next();
				SoftPermissionLinkConpanyRole slr=new SoftPermissionLinkConpanyRole();
				slr.setConpanyId(user.getConpanyId());
				slr.setFunctionName(sp.getFunctionName());
				slr.setGroupId(group.getId());
				slr.setRoleId(role.getId());
				slr.setRoleName(role.getName());
				slr.setSoftPermissionId(sp.getId());
				dao.add(slr);
			}
			ConpanyUserLinkRole userLinkrole=new ConpanyUserLinkRole();
			userLinkrole.setConpanyId(role.getConpanyId());
			userLinkrole.setConpanyUserName(user.getUsername());
			userLinkrole.setGroupId(group.getId());
			userLinkrole.setRoleId(role.getId());
			userLinkrole.setRoleName(role.getName());
			userLinkrole.setUserid(user.getId());
			userLinkrole.setConpanyUserTrueName(user.getTrueName());
			dao.add(userLinkrole);
			GroupConpanyLinkUser gclu=new GroupConpanyLinkUser();
			gclu.setConpanyId(user.getConpanyId());
			gclu.setConpanyUserId(user.getId());
			gclu.setConpanyUserName(user.getUsername());
			gclu.setConpanyUserTrueName(user.getTrueName());
			gclu.setGroupId(group.getId());
			gclu.setGroupName(group.getGroupName());
			dao.add(gclu);
			map.put("info", "创建成功,请继续操作组吧");
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 获取用户的组
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getUserInGroup")
	@ResponseBody
	public Map<String, Object> getMyGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String groupName=req.getParameter("groupName");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			if(groupName==null||groupName.trim().equals("")){
				groupName="";
			}
			List<ConpanyGroup> list=dao.getUserOfGroupsByUserIdAndGroupName(user.getId(), groupName);
			map.put("success", true);
			map.put("data", list);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 获取该用户公司的所有分组
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getAllGroup")
	@ResponseBody
	public Map<String, Object> getAllGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String nowpage = req.getParameter("nowpage");
			String countNum = req.getParameter("countNum");
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "30";
			}
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			//如果没有传递组标示，则自动找到二级组。
			String groupId=req.getParameter("groupId");
			String groupName=req.getParameter("groupName");
			long groupidLong=0;
			if(groupId==null||groupId.equals("0")){
				List<?> listconpObj=dao.getObjectList("ConpanyGroup", "where conpanyId="+user.getConpanyId()+" and upLevelConpanyGroup=0");
				ConpanyGroup cg=(ConpanyGroup)listconpObj.iterator().next();
				groupidLong=cg.getId();
			}else{
				groupidLong=Long.parseLong(groupId);
			}
			long num=dao.getObjectListNum("ConpanyGroup", "where conpanyId="+user.getConpanyId()+" and upLevelConpanyGroup="+groupidLong+" and groupName like '%"+groupName+"%'");
			List<?> list=dao.getObjectListPage("ConpanyGroup", "where conpanyId="+user.getConpanyId()+" and upLevelConpanyGroup="+groupidLong+" and groupName like '%"+groupName+"%'",Integer.parseInt(nowpage),Integer.parseInt(countNum));
			map.put("data",list);
			map.put("pagenum", num/Integer.parseInt(countNum)+1);
			map.put("success", true);
			map.put("nowgroup", groupidLong);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 获取一个组的有所公司员工
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGroupConpanyUser")
	@ResponseBody
	public Map<String, Object> getGroupConpanyUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String nowpage = req.getParameter("nowpage");
			String countNum = req.getParameter("countNum");
			if (nowpage == null) {
				nowpage = "1";
			}
			if (countNum == null) {
				countNum = "30";
			}
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			String groupId=req.getParameter("groupId");
			String groupName=req.getParameter("groupName");
			long groupidLong=0;
			if(groupId==null||groupId.equals("0")){
				map.put("info", "出错啦，您是不是选错组了，该组不存在？亲。");
				map.put("success", false);
				return map;
			}else{
				groupidLong=Long.parseLong(groupId);
			}
			String treuName=req.getParameter("trueName");
			if(treuName==null){
				treuName="";
			}
			long num=dao.getObjectListNum("GroupConpanyLinkUser", "where conpanyUserTrueName like '%"+treuName+"%' and groupId="+groupidLong);
			List<ConpanyUser> userlist=new ArrayList<ConpanyUser>();
			List<Object> listpage=dao.getObjectListPage("GroupConpanyLinkUser", "where conpanyUserTrueName like '%"+treuName+"%' and groupId="+groupidLong, Integer.parseInt(nowpage),Integer.parseInt(countNum));
			Iterator<Object> i=listpage.iterator();
			//循环出所有用户
			while(i.hasNext()){
				GroupConpanyLinkUser u=(GroupConpanyLinkUser) i.next();
				ConpanyUser cu=(ConpanyUser) dao.getObject(u.getConpanyUserId(), "ConpanyUser");
				cu.setPassword("*************");
				userlist.add(cu);
			}
			map.put("data", userlist);
			map.put("success", true);
			map.put("pagenum", num/Integer.parseInt(countNum)+1);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 获取一个组下的所有子分组
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGroupChildGroup")
	@ResponseBody
	public Map<String, Object> getGroupChildGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 向组中添加成员
	 * @param model
	 * @param req
	 * @param res
	 * @returne
	 */
	@RequestMapping(value = "/insertConpanyUserToGroup")
	@ResponseBody
	public Map<String, Object> insertConpanyUserToGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String userId=req.getParameter("userId");
			String groupId=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			//通过 组id 用户id 公司id 从组合用户连接表内查找出数据 ，应该是唯一的
			ConpanyUser users=(ConpanyUser)dao.getObject(Long.parseLong(userId), "ConpanyUser");
			
			ConpanyGroup group=(ConpanyGroup)dao.getObject(Long.parseLong(groupId), "ConpanyGroup");
			if(users==null){
				map.put("info", "您操作的用户不存在");
				map.put("success", false);
				return map;
			}
			if(group==null){
				map.put("info", "您操作的组不存在");
				map.put("success", false);
				return map;
			}
			return dao.addConpanyUserToGroup(users.getId(), group.getId(), user.getConpanyId());
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 从组中删除成员
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteConpanyUserFormGroup")
	@ResponseBody
	public Map<String, Object> deleteConpanyUserFormGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String userId=req.getParameter("userId");
			String groupId=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			//通过 组id 用户id 公司id 从组合用户连接表内查找出数据 ，应该是唯一的
			ConpanyUser users=(ConpanyUser)dao.getObject(Long.parseLong(userId), "ConpanyUser");
			
			ConpanyGroup group=(ConpanyGroup)dao.getObject(Long.parseLong(groupId), "ConpanyGroup");
			if(users==null){
				map.put("info", "您操作的用户不存在");
				map.put("success", false);
				return map;
			}
			if(group==null){
				map.put("info", "您操作的组不存在");
				map.put("success", false);
				return map;
			}
			if(group.getCreateConpanyGroupUserId()==users.getId()){
				map.put("info", "您要删除的是创建人，创建人不能够被移出");
				map.put("success", false);
				return map;
			}
			List<Object> list=dao.getObjectList("GroupConpanyLinkUser", "where groupId="+groupId+" and conpanyUserId="+userId+" and conpanyId="+user.getConpanyId());
			Iterator i=list.iterator();
			if(i.hasNext()){
				GroupConpanyLinkUser glu=(GroupConpanyLinkUser)i.next();
				List<Object> list2=dao.getObjectList("ConpanyUserLinkRole", "where groupId="+groupId+" and userid="+userId+" and conpanyId="+user.getConpanyId());
				Iterator i2=list2.iterator();
				while(i2.hasNext()){
					dao.delete(i2.next());
				}
				dao.delete(glu);
			}
			map.put("info", "移除成功。若人物还在组内请刷新列表");
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 删除组
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteGroup")
	@ResponseBody
	public Map<String, Object> deleteGroup(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String groupId=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			ConpanyGroup cg=(ConpanyGroup) dao.getObject(Long.parseLong(groupId), "ConpanyGroup",user.getConpanyId());
			if(cg.getUpLevelConpanyGroup()==0){
				map.put("info", "出错啦，公司主组无法删除");
				map.put("success", false);
			}else{
				return dao.deleteGroup(Long.parseLong(groupId));
			}
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 获取单一组信息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getGroupInfo")
	@ResponseBody
	public Map<String, Object> getGroupInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String groupId=req.getParameter("groupId");
			ConpanyGroup group=(ConpanyGroup)dao.getObject(Long.parseLong(groupId), "ConpanyGroup");
			map.put("success", true);
			map.put("data", group);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 *更新组信息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/updateGroupInfo")
	@ResponseBody
	public Map<String, Object> updateGroupInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String userId=req.getParameter("userId");
			String groupId=req.getParameter("groupId");
			String groupName=req.getParameter("groupName");
			String groupMarks=req.getParameter("groupMarks");
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			List<Role> list=dao.getConpanyGroupUserRole(users.getConpanyId(), Long.parseLong(groupId), "");
			List<Object> listpage=dao.getObjectList("GroupConpanyLinkUser", "where groupId="+groupId);
			ConpanyGroup group=(ConpanyGroup) dao.getObject(Long.parseLong(groupId), "ConpanyGroup",users.getConpanyId());
			Iterator<Role> i=list.iterator();
			while(i.hasNext()){
				Role role=i.next();
				role.setGroupName(groupName);
				dao.update(role);
			}
			Iterator i2=listpage.iterator();
			while(i2.hasNext()){
				GroupConpanyLinkUser glu=(GroupConpanyLinkUser)i2.next();
				glu.setGroupName(groupName);
				dao.update(glu);
			}
			group.setGroupName(groupName);
			group.setGroupMarks(groupMarks);
			dao.update(group);
			map.put("info", "更新成功");
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	
}
