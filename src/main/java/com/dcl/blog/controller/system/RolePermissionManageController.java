 package com.dcl.blog.controller.system;

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
 * lookupPremissionAll
 */
@Controller
@RequestMapping("/RolePermission")
public class RolePermissionManageController {
	private static final Logger logger = LoggerFactory
			.getLogger(RolePermissionManageController.class);
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
	 * 添加权限到角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addPermissionToRole")
	@ResponseBody
	public Map addPermissionToRole(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String isAll=req.getParameter("isAll");
			String permissionId=req.getParameter("permissionId");
			String roleId=req.getParameter("roleId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			boolean isall;
			if(isAll==null||isAll==""){
				isall=false;
			}else{
				isall=Boolean.parseBoolean(isAll);
			}
			if(isall){
				List<Object> list=dao.getObjectList("SoftPermission", "where uplevel="+permissionId);
				Iterator i=list.iterator();
				while(i.hasNext()){
					SoftPermission sp=(SoftPermission) i.next();
					map=dao.addPermissionToRole(sp.getId(), Long.parseLong(roleId), user.getConpanyId());
					if((Boolean)map.get("success")==false){
						return map;
					}
				}
			}else{
				map=dao.addPermissionToRole(Long.parseLong(permissionId), Long.parseLong(roleId), user.getConpanyId());
			}
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 删除权限到角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deletePermissionToRole")
	@ResponseBody
	public Map deletePermissionToRole(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String permissionId=req.getParameter("permissionId");
			String roleId=req.getParameter("roleId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			map=dao.deletePermissionToRole(Long.parseLong(permissionId), Long.parseLong(roleId), user.getConpanyId());
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 添加角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addRole")
	@ResponseBody
	public Map addRole(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String name=req.getParameter("roleName");
			String marks=req.getParameter("roleMarks");
			String groupId=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			ConpanyGroup group=(ConpanyGroup)dao.getObject(Long.parseLong(groupId), "ConpanyGroup",user.getConpanyId());
			Role role=new Role();
			role.setConpanyId(user.getConpanyId());
			role.setGroupid(group.getId());
			role.setGroupName(group.getGroupName());
			role.setMarks(marks);
			role.setName(name);
			dao.add(role);
			map.put("info", "添加完成");
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 删除角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteRole")
	@ResponseBody
	public Map deleteRole(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String roleId=req.getParameter("roleId");
			String groupId=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			Role role=(Role)dao.getObject(Long.parseLong(roleId), "Role",user.getConpanyId());
			if(role!=null){
				if(role.isConpanyAdminRole()){
					map.put("info", "该角色不可以删除，因为拥有本组的最大权限");
					map.put("success", false);
					return map;
				}
				List<SoftPermissionLinkConpanyRole> list=dao.getRolelinkPermissionList(Long.parseLong(groupId), role.getId());
				Iterator<SoftPermissionLinkConpanyRole> i=list.iterator();
				while(i.hasNext()){
					dao.delete(i.next());
				}
				dao.delete(role);
				map.put("info", "删除成功");
				map.put("success", true);
			}else{
				map.put("info", "角色不存在，删除错误");
				map.put("success", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 查看所有角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookupRoleAll")
	@ResponseBody
	public Map lookupRoleAll(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String groupId=req.getParameter("groupId");
			String roleName=req.getParameter("roleName");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			List<Role> role=dao.getConpanyGroupUserRole(user.getConpanyId(), Long.parseLong(groupId), roleName);
			map.put("data", role);
			map.put("success", true);
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 查看一个用户在该组内担当的角色
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookupRoleByUser")
	@ResponseBody
	public Map lookupRoleByUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String userid=req.getParameter("userId");
			String groupid=req.getParameter("groupId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			ConpanyUser user2=(ConpanyUser) dao.getObject(Long.parseLong(userid), "ConpanyUser",user.getConpanyId());
			ConpanyGroup group=(ConpanyGroup) dao.getObject(Long.parseLong(groupid), "ConpanyGroup",user.getConpanyId());
			if(user2!=null&&group!=null){
				List<Role> list=dao.getRoleByConpanyUser(user2, Long.parseLong(groupid));
				map.put("data", list);
				map.put("success", true);
			}else{
				map.put("info", "出错啦，操作的组或用户不存在。");
				map.put("success", false);
				return map;
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 查看一角色的所有权限
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/lookupPremissionByRole")
	@ResponseBody
	public Map lookupPremissionByRole(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String name=req.getParameter("name");
			String groupId=req.getParameter("groupId");
			String roleId=req.getParameter("roleId");
			ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			Role role =(Role) dao.getObject(Long.parseLong(roleId), "Role",users.getConpanyId());
			if(role.getGroupid()==Long.parseLong(groupId)){
				List<SoftPermission> list=dao.getSoftPermissionByRole(role, name);
				map.put("data", list);
				map.put("success", true);
			}else{
				map.put("info", "出错啦，该组你您没有权限。");
				map.put("success", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 添加角色到用户
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/addRoleToConpanyUser")
	@ResponseBody
	public Map addRoleToConpanyUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String roleid=req.getParameter("roleid");
			String groupId=req.getParameter("groupId");
			String userId=req.getParameter("userId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			return dao.addRoleToConpanyUser(Long.parseLong(roleid), Long.parseLong(userId),user.getConpanyId(), Long.parseLong(groupId));
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 删除角色到用户
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/deleteRoleToConpanyUser")
	@ResponseBody
	public Map deleteRoleToConpanyUser(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		try{
			String roleid=req.getParameter("roleid");
			String groupId=req.getParameter("groupId");
			String userId=req.getParameter("userId");
			ConpanyUser user=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
			return dao.deleteRoleToConpanyUser(Long.parseLong(roleid), Long.parseLong(userId), user.getConpanyId(),Long.parseLong(groupId));
		}catch (Exception e) {
			map.put("info", "出错啦，是不是您的信息没有填写正确能？亲。");
			map.put("success", false);
		}
		return map;
	}
}
