package com.dcl.blog.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ChanceList;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserLinkRole;
import com.dcl.blog.model.GroupConpanyLinkUser;
import com.dcl.blog.model.Message;
import com.dcl.blog.model.Role;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.model.SoftPermissionLinkConpanyRole;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.WeiXinAutoReSendItem;
import com.dcl.blog.model.WeiXinAutoReSendMenu;
import com.dcl.blog.model.WeiXinReSend;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.EmailUtil;
import com.dcl.blog.util.MD5Util;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.StringUtil;
import com.dcl.blog.util.email.AccountEmailException;
import com.dcl.blog.util.email.emailimpl;
/**
 * 主页控制
 * 
 * @author Administrator
 * 
 */
@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);
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
	public void initPermission(){
		
		//**********************组管理****************//
		
		long pid;
		SoftPermission soft=null;
		List list=dao.getObjectList("SoftPermission", "where url='/Group'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("组管理权限");
			soft.setMarks("组管理，勾选此项，则此项底下所有功能都被勾选，很多权限都是相辅相成的。");
			soft.setUrl("/Group");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/Group/createGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("创建组/部门权限权限");
			soft.setMarks("允许授权用于创建企业的组织结构如：人事部，业务部等等，是指在本组之下创建，不可以超越用户所在组");
			soft.setUrl("/Group/createGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/getUserInGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取用户所在组权限");
			soft.setMarks("允许授权用于查询一个组员用户都参与了那些组，需要配合查看组用户列表权限，此功能才有实际效果");
			soft.setUrl("/Group/getUserInGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/getAllGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取所有组列表权限");
			soft.setMarks("允许授权获取公司的所有组列表");
			soft.setUrl("/Group/getAllGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/getGroupConpanyUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取一个组的所有员工列表权限");
			soft.setMarks("允许授权获取一个组的所有员工包括子组的员工列表");
			soft.setUrl("/Group/getGroupConpanyUser");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/getGroupChildGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取一个组下的所有分组权限");
			soft.setMarks("允许授权获取一个组下的所有分组列表");
			soft.setUrl("/Group/getGroupChildGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/insertConpanyUserToGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("向组内添加一个成员权限");
			soft.setMarks("允许授权向一个组内添加成员");
			soft.setUrl("/Group/insertConpanyUserToGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/deleteConpanyUserFormGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("从组中删除成员权限");
			soft.setMarks("允许授权从组中删除成员");
			soft.setUrl("/Group/deleteConpanyUserFormGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/deleteGroup'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除组");
			soft.setMarks("删除组权限，允许用户删除该组");
			soft.setUrl("/Group/deleteGroup");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Group/updateGroupInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("编辑组信息");
			soft.setMarks("允许编辑组信息，修改组名称，备注");
			soft.setUrl("/Group/updateGroupInfo");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//**********************组管理 结束****************//
		//**********************权限管理****************//
		list=dao.getObjectList("SoftPermission", "where url='/Permission'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("系统权限管理");
			soft.setMarks("系统的权限管理");
			soft.setUrl("/Permission");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/Permission/lookupPremissionAll'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看系统权限的权限");
			soft.setMarks("允许授权查看系统权限，在给员工分配允许其对其他用户的角色分配权限时设置，否则该员工无法给其他员工设置权限");
			soft.setUrl("/Permission/lookupPremissionAll");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/Permission/lookupPremissionMain'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看系统权限的权限【主要】");
			soft.setMarks("允许授权查看系统权限，在给员工分配允许其对其他用户的角色分配权限时设置，否则该员工无法给其他员工设置权限【重要】");
			soft.setUrl("/Permission/lookupPremissionMain");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//**********************权限管理 结束****************//
		//**********************向角色分配权限的权限****************//
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("所在组角色以及权限管理");
			soft.setMarks("向某一角色设置该权限后，该角色的用户将拥有给其他用户分配权限的功能");
			soft.setUrl("/RolePermission");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/addPermissionToRole'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("向角色添加权限的权限");
			soft.setMarks("允许授权用户向其他角色添加权限");
			soft.setUrl("/RolePermission/addPermissionToRole");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/deletePermissionToRole'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("移除所在组角色的权限");
			soft.setMarks("允许授权用户从所在组角色删除权限");
			soft.setUrl("/RolePermission/deletePermissionToRole");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/addRole'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("添加所在组角色");
			soft.setMarks("允许授权用户创建所在组新角色");
			soft.setUrl("/RolePermission/addRole");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/deleteRole'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除所在组的角色");
			soft.setMarks("允许授权用户删除所在组角色");
			soft.setUrl("/RolePermission/deleteRole");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/lookupRoleAll'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看所有角色");
			soft.setMarks("允许授权用户查看一个组的所有角色");
			soft.setUrl("/RolePermission/lookupRoleAll");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/lookupRoleByUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看一个用户在该组内担当的角色");
			soft.setMarks("授权允许用户查看一个用户在该组内担当的角色");
			soft.setUrl("/RolePermission/lookupRoleByUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/lookupPremissionByRole'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看角色的权限列表");
			soft.setMarks("授权允许用户查看角色的权限列表");
			soft.setUrl("/RolePermission/lookupPremissionByRole");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/addRoleToConpanyUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("添加角色到用户");
			soft.setMarks("授权允许添加角色到用户");
			soft.setUrl("/RolePermission/addRoleToConpanyUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/RolePermission/deleteRoleToConpanyUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除角色到用户");
			soft.setMarks("授权允许删除角色到用户");
			soft.setUrl("/RolePermission/deleteRoleToConpanyUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//**********************向角色分配权限的权限 结束****************//
		//**********************crm权限*******************************//
		list=dao.getObjectList("SoftPermission", "where url='/crm/page'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户机会功能的权限");
			soft.setMarks("拥有此权限，选择此则拥有所有crm的权限，一般不建议直接用此操作");
			soft.setUrl("/crm/page");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/createChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("创建机会");
			soft.setMarks("授权用户拥有创建机会");
			soft.setUrl("/crm/function/createChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改机会");
			soft.setMarks("授权用户拥有修改机会");
			soft.setUrl("/crm/function/updateChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/DeleteChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除机会");
			soft.setMarks("授权用户拥有删除机会");
			soft.setUrl("/crm/function/DeleteChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/allocationChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("分配机会");
			soft.setMarks("授权用户拥有分配机会");
			soft.setUrl("/crm/function/allocationChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/createFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("创建流程");
			soft.setMarks("授权用户拥有创建流程");
			soft.setUrl("/crm/function/createFlow");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改流");
			soft.setMarks("授权用户拥有修改流");
			soft.setUrl("/crm/function/updateFlow");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/deleteFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除流");
			soft.setMarks("授权用户拥有删除流");
			soft.setUrl("/crm/function/deleteFlow");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/createFlowItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("创建流程项");
			soft.setMarks("授权用户拥有创建流程项");
			soft.setUrl("/crm/function/createFlowItem");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateFlowItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("更新流节点");
			soft.setMarks("授权用户拥有更新流节点");
			soft.setUrl("/crm/function/updateFlowItem");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/forwordFlowItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("从定向流节点");
			soft.setMarks("授权用户拥有从定向流节点");
			soft.setUrl("/crm/function/forwordFlowItem");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateLinkMan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("更新联系人");
			soft.setMarks("授权用户拥有更新联系人");
			soft.setUrl("/crm/function/updateLinkMan");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/changeState'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("改变状态");
			soft.setMarks("授权用户拥有改变状态");
			soft.setUrl("/crm/function/changeState");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryMyChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询自己创建的机会");
			soft.setMarks("授权用户查询自己创建的机会");
			soft.setUrl("/crm/function/queryMyChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/getChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看单个机会详情的权限");
			soft.setMarks("如：自己创建的机会，也需要此权限，强烈建议每个员工或每隔业务人员都拥有此人员");
			soft.setUrl("/crm/function/getChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改单个机会详情的权限");
			soft.setMarks("授权一个用户有修改单个机会详情的权限，其他人创建的");
			soft.setUrl("/crm/function/updateChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/allocationChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("机会指定人");
			soft.setMarks("授权一个用户有机会指定人权限，其他人创建的");
			soft.setUrl("/crm/function/allocationChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/DeleteChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除一个机会的权限");
			soft.setMarks("授权一个用户有删除一个机会的权限，不管是自己的还是其他人创建的");
			soft.setUrl("/crm/function/DeleteChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/updateMyCreateChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改用户自己创建的机会");
			soft.setMarks("授权用户拥有修改用户自己创建的机会权限");
			soft.setUrl("/crm/function/updateMyCreateChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/allocationMyCreateChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("分配自己创建的机会");
			soft.setMarks("授权一个角色拥有分配自己创建的机会权限。");
			soft.setUrl("/crm/function/allocationMyCreateChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/DeleteMyCreateChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除自己创造的机会");
			soft.setMarks("授权一个用户有删除自己创造的机会的权限");
			soft.setUrl("/crm/function/DeleteMyCreateChance");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryLinkMan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询机会/客户的联系人权限");
			soft.setMarks("授权一个用户有查询机会/客户的联系人的权限");
			soft.setUrl("/crm/function/queryLinkMan");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/addLinkMan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("添加联系人权限");
			soft.setMarks("授权一个用户有添加联系人的权限");
			soft.setUrl("/crm/function/addLinkMan");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/getLinkManById'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取一个联系人的详细信息权限");
			soft.setMarks("授权一个用户有获取一个联系人的详细信息的权限");
			soft.setUrl("/crm/function/getLinkManById");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/deleteLinkMan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除联系人权限");
			soft.setMarks("授权一个用户有删除一个联系人的权限");
			soft.setUrl("/crm/function/deleteLinkMan");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryNotes'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询开发记录");
			soft.setMarks("授权一个用户有查询开发记录的权限");
			soft.setUrl("/crm/function/queryNotes");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/startNotes'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("对一个机会进行创建一个开发记录");
			soft.setMarks("对一个机会进行创建一个开发记录，拥有此权限则可以给任何一个机会创建记录");
			soft.setUrl("/crm/function/startNotes");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/addNotesItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("向开发记录添加纪录项");
			soft.setMarks("向开发记录添加纪录项，在创建开发记录后，想开发记录添加记录的权限");
			soft.setUrl("/crm/function/addNotesItem");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryNotesItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询开发记录详情");
			soft.setMarks("查看开发记录添加纪录项，查询开发记录详情权限");
			soft.setUrl("/crm/function/queryNotesItem");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryAllChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询所有机会的权限");
			soft.setMarks("查询所有机会的权限，在编辑信息时插入客户信息和机会信息时会也会用到");
			soft.setUrl("/crm/function/queryAllChance");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询建议流程的权限");
			soft.setMarks("查询建议流程的权限，在编辑信息时插入客户信息和机会信息时会也会用到");
			soft.setUrl("/crm/function/queryFlow");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/createFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("流程创建");
			soft.setMarks("创建建议流程的权限");
			soft.setUrl("/crm/function/createFlow");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryFlowItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看建议流程");
			soft.setMarks("查看建议流程的权限");
			soft.setUrl("/crm/function/queryFlowItem");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/deleteFlow'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("删除建议流程");
			soft.setMarks("删除建议流程的权限");
			soft.setUrl("/crm/function/deleteFlow");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryMyCustemor'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询由自己参与或创建的客户查询");
			soft.setMarks("查询由自己参与或创建的客户查询的权限");
			soft.setUrl("/crm/function/queryMyCustemor");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/queryToMyChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询分配给自己的机会");
			soft.setMarks("查询分配给自己的机会权限");
			soft.setUrl("/crm/function/queryToMyChance");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/char/query'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询图表权限");
			soft.setMarks("查询图表权限权限，主要用于概览界面的小窗体");
			soft.setUrl("/crm/function/char/query");
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//**********************crm权限结束*******************************//
		//**********************库存管理权限*******************************//
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("库存管理权限");
			soft.setMarks("拥有此权限，选择此则拥有所有库存管理的权限，一般不建议直接用此操作");
			soft.setUrl("/goods/function/");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/addGoods'"); if(list.size()<1){;
		soft.setFunctionName("添加商品");
		soft.setMarks("授权用户拥有添加商品");
		soft.setUrl("/goods/function/addGoods");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/addGoodSource'"); if(list.size()<1){;
		soft.setFunctionName("添加供货商");
		soft.setMarks("授权用户拥有添加供货商权限");
		soft.setUrl("/goods/function/addGoodSource");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryGoodsSource'"); if(list.size()<1){;
		soft.setFunctionName("查找供货商");
		soft.setMarks("授权用户拥有查找供货商权限");
		soft.setUrl("/goods/function/queryGoodsSource");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/deleteGoodsSource'"); if(list.size()<1){;
		soft.setFunctionName("删除供货商");
		soft.setMarks("授权用户拥有删除供货商权限");
		soft.setUrl("/goods/function/deleteGoodsSource");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/getGoodsSource'"); if(list.size()<1){;
		soft.setFunctionName("查看供货商详情");
		soft.setMarks("授权用户拥有查看供货商详情权限");
		soft.setUrl("/goods/function/getGoodsSource");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/updateGoodSource'"); if(list.size()<1){;
		soft.setFunctionName("更新供货商详情");
		soft.setMarks("授权用户拥有更新供货商详情权限");
		soft.setUrl("/goods/function/updateGoodSource");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/createOrder'"); if(list.size()<1){;
		soft.setFunctionName("创建销售订单");
		soft.setMarks("授权用户拥有创建销售订单");
		soft.setUrl("/goods/function/createOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryOrder'"); if(list.size()<1){;
		soft.setFunctionName("查询销售订单");
		soft.setMarks("授权用户拥有创建销售订单");
		soft.setUrl("/goods/function/queryOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/deleteOrder'"); if(list.size()<1){;
		soft.setFunctionName("删除销售订单");
		soft.setMarks("授权用户拥有删除销售订单");
		soft.setUrl("/goods/function/deleteOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryOrderItem'"); if(list.size()<1){;
		soft.setFunctionName("查看销售订单内容");
		soft.setMarks("授权用户拥有查看销售订单内容");
		soft.setUrl("/goods/function/queryOrderItem");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryInOrderItem'"); if(list.size()<1){;
		soft.setFunctionName("查看进货订单内容");
		soft.setMarks("授权用户拥有查看进货订单内容");
		soft.setUrl("/goods/function/queryInOrderItem");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/OrderInStore'"); if(list.size()<1){;
		soft.setFunctionName("入库销售订单权限");
		soft.setMarks("授权用户拥有入库销售订单权限");
		soft.setUrl("/goods/function/OrderInStore");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/InOrderInStore'"); if(list.size()<1){;
		soft.setFunctionName("入库进货单");
		soft.setMarks("授权用户拥有入库进货单");
		soft.setUrl("/goods/function/InOrderInStore");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/createStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("创建仓库");
		soft.setMarks("授权用户拥有创建仓库");
		soft.setUrl("/goods/function/createStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/updateStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("更新仓库");
		soft.setMarks("授权用户拥有更新仓库");
		soft.setUrl("/goods/function/updateStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/deleteStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("删除仓库");
		soft.setMarks("授权用户拥有删除仓库");
		soft.setUrl("/goods/function/deleteStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/StoreHouseToStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("合并仓库");
		soft.setMarks("授权用户拥有合并仓库");
		soft.setUrl("/goods/function/StoreHouseToStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/createInOrder'"); if(list.size()<1){;
		soft.setFunctionName("创建进货单");
		soft.setMarks("授权用户拥有创建进货单");
		soft.setUrl("/goods/function/createInOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/deleteInOrder'"); if(list.size()<1){;
		soft.setFunctionName("删除进货单");
		soft.setMarks("授权用户拥有删除进货单");
		soft.setUrl("/goods/function/deleteInOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryInOrder'"); if(list.size()<1){;
		soft.setFunctionName("查找进货单");
		soft.setMarks("授权用户拥有查找进货单");
		soft.setUrl("/goods/function/queryInOrder");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryInOrderItem'"); if(list.size()<1){;
		soft.setFunctionName("查看进货单内容");
		soft.setMarks("授权用户拥有查看进货单内容");
		soft.setUrl("/goods/function/queryInOrderItem");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/addGoodSourceLinkMan'"); if(list.size()<1){;
		soft.setFunctionName("添加供货商联系人");
		soft.setMarks("授权用户拥有添加供货商联系人权限");
		soft.setUrl("/goods/function/addGoodSourceLinkMan");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryGoodsSourceLinkman'"); if(list.size()<1){;
		soft.setFunctionName("查询供货商联系人");
		soft.setMarks("授权用户拥有查询供货商联系人权限");
		soft.setUrl("/goods/function/queryGoodsSourceLinkman");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/updateGoodSourceLinkMan'"); if(list.size()<1){;
		soft.setFunctionName("更新供应商联系人权限");
		soft.setMarks("授权用户拥有更新供应商联系人权限");
		soft.setUrl("/goods/function/updateGoodSourceLinkMan");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/deleteGoodsSourceLinkman'"); if(list.size()<1){;
		soft.setFunctionName("删除供货商联系人");
		soft.setMarks("授权用户拥有删除供货商联系人");
		soft.setUrl("/goods/function/deleteGoodsSourceLinkman");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/getGoodsSourceLinkMan'"); if(list.size()<1){;
		soft.setFunctionName("查询供货商联系人详情");
		soft.setMarks("授权用户拥有查询供货商联系人详情权限");
		soft.setUrl("/goods/function/getGoodsSourceLinkMan");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("查询仓库");
		soft.setMarks("授权用户拥有查询查询仓库权限");
		soft.setUrl("/goods/function/queryStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/getStoreHouse'"); if(list.size()<1){;
		soft.setFunctionName("获取单个仓库信息");
		soft.setMarks("授权用户拥有获取单个仓库信息权限");
		soft.setUrl("/goods/function/getStoreHouse");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryGoods'"); if(list.size()<1){;
		soft.setFunctionName("查询库存");
		soft.setMarks("查询库存信息权限");
		soft.setUrl("/goods/function/queryGoods");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/getGoods'"); if(list.size()<1){;
		soft.setFunctionName("获取单个货物信息");
		soft.setMarks("获取单个货物信息");
		soft.setUrl("/goods/function/getGoods");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/updatePrice'"); if(list.size()<1){;
		soft.setFunctionName("修改商品价格");
		soft.setMarks("修改商品价格权限");
		soft.setUrl("/goods/function/updatePrice");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/queryGoodsLog'"); if(list.size()<1){;
		soft.setFunctionName("查看商品操作日志权限");
		soft.setMarks("查看商品操作日志权限");
		soft.setUrl("/goods/function/queryGoodsLog");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/char/querySaleChat'"); if(list.size()<1){;
		soft.setFunctionName("查看货物的销售图表权限");
		soft.setMarks("查看货物的销售图表权限");
		soft.setUrl("/goods/function/char/querySaleChat");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/char/queryInGoods'"); if(list.size()<1){;
		soft.setFunctionName("查看货物的进货统计图表权限");
		soft.setMarks("查看货物的进货统计图表权限");
		soft.setUrl("/goods/function/char/queryInGoods");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/char/priceChar'"); if(list.size()<1){;
		soft.setFunctionName("查看货物的价格趋势统计图表权限");
		soft.setMarks("查看货物的价格趋势统计图表权限");
		soft.setUrl("/goods/function/char/priceChar");	
		soft.setUplevel(pid);
		dao.add(soft);}
		list=dao.getObjectList("SoftPermission", "where url='/goods/function/char/queryStoreHouseChat'"); if(list.size()<1){;
		soft.setFunctionName("查询货物储存仓库图表权限");
		soft.setMarks("查询货物储存仓库图表权限");
		soft.setUrl("/goods/function/char/queryStoreHouseChat");	
		soft.setUplevel(pid);
		dao.add(soft);}
		//**********************库存管理权限结束*******************************//
		//************************系统通用权限***********************//
		list=dao.getObjectList("SoftPermission", "where url='/Sys'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("系统通用权限");
			soft.setMarks("系统通用权限");
			soft.setUrl("/Sys");
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/crm/function/getObject'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看信息连接项权限");
			soft.setMarks("在一些系统信息里，查看如，联系人，开发记录，等");
			soft.setUrl("/crm/function/getObject");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//***********************************系统通用权限结束**********************//
		//************************hr权限开始***********************//
		list=dao.getObjectList("SoftPermission", "where url='/hr/page'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理");
			soft.setMarks("绩效考核，工资核算，签到考勤，员工账号等");
			soft.setUrl("/hr/page");	
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/addCustemmer'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("添加员工账户");
			soft.setMarks("添加员工账户权限");
			soft.setUrl("/hr/function/addCustemmer");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/queryCustemmer'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查询员工信息");
			soft.setMarks("查询员工信息---不包含工资状况。工资状况有专有权限");
			soft.setUrl("/hr/function/queryCustemmer");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/lookCustemmerInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("查看员工详情");
			soft.setMarks("查看员工详情，如工资，身份证号，等信息");
			soft.setUrl("/hr/function/lookCustemmerInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/updateCustemmerInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改员工信息权限");
			soft.setMarks("修改员工详情，如工资，身份证号，等信息");
			soft.setUrl("/hr/function/updateCustemmerInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/getCustemmerMeetingInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取全公司员工签到信息");
			soft.setMarks("查看签到状况");
			soft.setUrl("/hr/function/getCustemmerMeetingInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/getUpdateMeetingInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("修改签到状况");
			soft.setMarks("修改员工签到状况权限");
			soft.setUrl("/hr/function/getUpdateMeetingInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/getMeetingSet'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("设置签到时间");
			soft.setMarks("设置签到时间权限");
			soft.setUrl("/hr/function/getMeetingSet");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/getMeetingSetInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取签到时间信息以及历史");
			soft.setMarks("获取签到时间信息以及历史");
			soft.setUrl("/hr/function/getMeetingSetInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/getPerForMance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("获取一段时间的绩效信息");
			soft.setMarks("获取一段时间的绩效信息");
			soft.setUrl("/hr/function/getPerForMance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/meeting'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("签到权限");
			soft.setMarks("使员工有签到权限");
			soft.setUrl("/hr/function/meeting");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/waiqinGroupManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->查询当前组外勤线路权限");
			soft.setMarks("员工管理->查询当前组外勤线路权限，在一个组内分配此权限则此权限，则此角色可以访问该组下所有外勤人员的外出线路");
			soft.setUrl("/hr/function/waiqinGroupManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/deleteKongjian'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->互动空间->删除权限");
			soft.setMarks("员工管理->互动空间->删除该组的一条动态");
			soft.setUrl("/hr/function/deleteKongjian");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/function/setIndexKongjian'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->互动空间->排位权重");
			soft.setMarks("员工管理->互动空间->该组动态的排位权重，排位值越高越靠前");
			soft.setUrl("/hr/function/setIndexKongjian");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		//************************hr权限 结束***********************//
		//************************页面访问权限***********************//
		list=dao.getObjectList("SoftPermission", "where url='/page'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("允许访问的页面管理");
			soft.setMarks("角色允许访问的页面管理");
			soft.setUrl("/page");	
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/crm/page/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->客户机会概览页面");
			soft.setMarks("客户管理->客户机会概览页面权限");
			soft.setUrl("/crm/page/main");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/page/myCreateCustomChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->我创建的客户机会页面");
			soft.setMarks("客户管理->我创建的客户机会页面权限");
			soft.setUrl("/crm/page/myCreateCustomChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/page/toMyCustomChance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->分配给我的客户机会页面");
			soft.setMarks("客户管理->分配给我的客户机会页面权限");
			soft.setUrl("/crm/page/toMyCustomChance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/page/MyCustomManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->我的客户管理页面");
			soft.setMarks("客户管理->我的客户管理页面权限");
			soft.setUrl("/crm/page/MyCustomManager");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->仓库概况页面");
			soft.setMarks("仓库管理->仓库概况页面权限");
			soft.setUrl("/goods/page/main");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/queryData'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->库存查询页面");
			soft.setMarks("仓库管理->库存查询页面权限");
			soft.setUrl("/goods/page/queryData");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/inGoods'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->进货管理页面");
			soft.setMarks("仓库管理->进货管理页面权限");
			soft.setUrl("/goods/page/inGoods");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/outGoods'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->销售管理页面");
			soft.setMarks("仓库管理->销售管理页面权限");
			soft.setUrl("/goods/page/outGoods");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/storehouse'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->仓库信息页面");
			soft.setMarks("仓库管理->仓库信息页面权限");
			soft.setUrl("/goods/page/storehouse");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/goods/page/GoodSource'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->进货商信息页面");
			soft.setMarks("仓库管理->进货商信息页面权限");
			soft.setUrl("/goods/page/GoodSource");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->概览页面");
			soft.setMarks("员工管理->概览页面权限");
			soft.setUrl("/hr/page/main");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/custemr'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->员工账号管理页面");
			soft.setMarks("员工管理->员工账号管理权限");
			soft.setUrl("/hr/page/custemr");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/meeting'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->签到管理页面");
			soft.setMarks("员工管理->签到管理权限");
			soft.setUrl("/hr/page/meeting");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/performance'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->绩效管理页面");
			soft.setMarks("员工管理->绩效管理权限");
			soft.setUrl("/hr/page/performance");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/itempage/systemPermission'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("系统权限管理->权限管理页面");
			soft.setMarks("系统权限管理->权限管理页面权限");
			soft.setUrl("/itempage/systemPermission");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->概览页面");
			soft.setMarks("微信管理->概览页面权限");
			soft.setUrl("/weixin/page/main");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_UserId_Set'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->微信公众设置页面");
			soft.setMarks("微信管理->微信公众设置页面权限");
			soft.setUrl("/weixin/page/weixin_UserId_Set");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_VIP_set'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员制度设置页面");
			soft.setMarks("微信管理->会员制度设置页面权限");
			soft.setUrl("/weixin/page/weixin_VIP_set");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_Game_set'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->活动与游戏管理页面");
			soft.setMarks("微信管理->活动与游戏管理页面权限");
			soft.setUrl("/weixin/page/weixin_Game_set");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_convert_set'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->序列号兑换页面");
			soft.setMarks("微信管理->序列号兑换页面权限");
			soft.setUrl("/weixin/page/weixin_convert_set");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_Model_set'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->模板设置页面");
			soft.setMarks("微信管理->模板设置页面权限");
			soft.setUrl("/weixin/page/weixin_Model_set");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_Model_set_map'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->设置店铺地址页面");
			soft.setMarks("微信管理->设置店铺地址页面");
			soft.setUrl("/weixin/page/weixin_Model_set_map");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_model_goodsManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->微信商品管理");
			soft.setMarks("微信管理->商品展示管理");
			soft.setUrl("/weixin/page/weixin_model_goodsManager");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/weixin_model_order'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->微信订单页面");
			soft.setMarks("微信管理->微信订单页面");
			soft.setUrl("/weixin/page/weixin_model_order");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/crm/page/queryAllChanceList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->查询所有客户机会页面");
			soft.setMarks("客户管理->查询所有客户机会页面");
			soft.setUrl("/crm/page/queryAllChanceList");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员wifi");
			soft.setMarks("微信管理->会员wifi页面");
			soft.setUrl("/wifiController/main");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/messageinfo/main'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("短信管理->短信设置");
			soft.setMarks("短信管理->短信设置，设置都在什么情况下自动发短信，以及发送什么短信，页面权限");
			soft.setUrl("/messageinfo/main");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/messageinfo/vipsend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("短信管理->会员短信");
			soft.setMarks("短信管理->向会员群发短信的页面权限");
			soft.setUrl("/messageinfo/vipsend");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/messageinfo/messagetemp'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("短信管理->短信模板");
			soft.setMarks("短信管理->短信模板的设置，比如用户在下了订单后，自动回复的模板，页面");
			soft.setUrl("/messageinfo/messagetemp");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/messageinfo/messagelog'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("短信管理->短信发送的历史");
			soft.setMarks("短信管理->短信发送的历史页面");
			soft.setUrl("/messageinfo/messagelog");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/waiqin'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("员工管理->外勤页面");
			soft.setMarks("员工管理->外勤页面，外勤人员管理");
			soft.setUrl("/hr/page/waiqin");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/hr/page/kongjian'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("客户管理->互动空间页面");
			soft.setMarks("客户管理->互动空间页面 类似qq空间，基于组与组的");
			soft.setUrl("/hr/page/kongjian");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/imageModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->图片交流模块管理");
			soft.setMarks("微信管理->图片交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/imageModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/videoModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->视频交流模块管理");
			soft.setMarks("微信管理->视频交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/videoModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/textModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->文字交流模块管理");
			soft.setMarks("微信管理->文字交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/textModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/gameModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->游戏交流模块管理");
			soft.setMarks("微信管理->游戏交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/gameModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/ershouModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->二手交流模块管理");
			soft.setMarks("微信管理->二手交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/ershouModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/page/gonggaoModelManager'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->公告交流模块管理");
			soft.setMarks("微信管理->公告交流模块管理，类似qq空间");
			soft.setUrl("/weixin/page/gonggaoModelManager");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		//************************页面访问权限结束***********************//
		//************************微信权限开始***********************//
		list=dao.getObjectList("SoftPermission", "where url='/weixin'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信的权限设置");
			soft.setMarks("角色允许操作微信的权限，如设置微信公众账号，如设置微信微网站模板");
			soft.setUrl("/weixin");	
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getWeXinInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取微信账户的信息");
			soft.setMarks("微信管理->获取微信账户的信息，如tokens，appId，appSecret");
			soft.setUrl("/weixin/getWeXinInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateWeXinInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->设置微信账户的信息");
			soft.setMarks("微信管理->设置微信账户的信息，如tokens，appId，appSecret");
			soft.setUrl("/weixin/updateWeXinInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updataMenuToWeiXin'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->设置同步至微信账户的菜单");
			soft.setMarks("微信管理->设置同步至微信账户的菜单，在设置完菜单功能后拥有此权限即可同步菜单到微信公众账号，菜单能腾讯微信规定必须是认证用户才可以使用,每年认证费用300元腾讯收取.");
			soft.setUrl("/weixin/updataMenuToWeiXin");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getMenu'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看微信公众账号菜单权限");
			soft.setMarks("微信管理->查看微信公众账号菜单权限，此权限主要用于使角色拥有查看微信目前预先设置的菜单，菜单功能腾讯微信规定必须是认证用户才可以使用,每年认证费用300元腾讯收取.");
			soft.setUrl("/weixin/getMenu");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addMenu'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加微信公众账号菜单权限");
			soft.setMarks("微信管理->添加微信公众账号菜单权限，此权限主要用于使角色拥有添加微信目前预先设置的菜单，设置好后通过同步功能提交至微信平台，菜单功能腾讯微信规定必须是认证用户才可以使用,每年认证费用300元腾讯收取.");
			soft.setUrl("/weixin/addMenu");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/deleteMenu'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除微信公众账号菜单权限");
			soft.setMarks("微信管理->删除微信公众账号菜单权限，此权限主要用于使角色拥有删除微信目前预先设置的菜单，设置好后通过同步功能提交至微信平台，菜单功能腾讯微信规定必须是认证用户才可以使用,每年认证费用300元腾讯收取.");
			soft.setUrl("/weixin/deleteMenu");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/editMenu'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->编辑微信公众账号菜单权限");
			soft.setMarks("微信管理->编辑微信公众账号菜单权限，此权限主要用于使角色拥有编辑微信目前预先设置的菜单，设置好后通过同步功能提交至微信平台，菜单功能腾讯微信规定必须是认证用户才可以使用,每年认证费用300元腾讯收取.");
			soft.setUrl("/weixin/editMenu");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getMenuItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->编辑微信公众账号菜单权限");
			soft.setMarks("微信管理->查看微信公众账号菜单详情权限，此权限主要用于使角色拥有查看微信目前预先设置的菜单的详情，设置好后通过同步功能提交至微信平台.");
			soft.setUrl("/weixin/getMenuItem");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getImage'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看图片资源权限");
			soft.setMarks("微信管理->查看图片资源权限，查看预先上传定义的图片资源.");
			soft.setUrl("/weixin/getImage");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getVoice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看声音资源权限");
			soft.setMarks("微信管理->查看声音资源权限，查看预先上传定义的声音资源.这个只针对服务号。才可用。");
			soft.setUrl("/weixin/getVoice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getVideo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看视频资源权限");
			soft.setMarks("微信管理->查看视频资源权限，查看预先上传定义的视频资源.这个只针对服务号。才可用。");
			soft.setUrl("/weixin/getVideo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看文本资源权限");
			soft.setMarks("微信管理->查看文本资源权限，查看预先定义的文本资源。");
			soft.setUrl("/weixin/getText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getMusic'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看音乐资源权限");
			soft.setMarks("微信管理->查看音乐资源权限，查看预先上传定义的音乐资源.这个只针对服务号。才可用。");
			soft.setUrl("/weixin/getMusic");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getImageText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看图片+文本资源权限");
			soft.setMarks("微信管理->查看图片+文本资源权限，查看预先定义的图片+文本一起出现的资源。");
			soft.setUrl("/weixin/getImageText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addImage'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加图片资源权限");
			soft.setMarks("微信管理->添加图片资源权限，添加一张图片资源。");
			soft.setUrl("/weixin/addImage");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addVideo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加视频资源权限");
			soft.setMarks("微信管理->添加视频资源权限。这个只针对服务号。才可用。");
			soft.setUrl("/weixin/addVideo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addVoice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加声音资源权限");
			soft.setMarks("微信管理->添加声音资源权限。这个只针对服务号。才可用。");
			soft.setUrl("/weixin/addVoice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加文本资源权限");
			soft.setMarks("微信管理->添加文本资源权限。");
			soft.setUrl("/weixin/addText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addMusic'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加音乐资源权限");
			soft.setMarks("微信管理->添加音乐资源权限。这个只针对服务号。才可用。");
			soft.setUrl("/weixin/addMusic");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addImageText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加文本+图片资源权限");
			soft.setMarks("微信管理->添加文本+图片资源权限。");
			soft.setUrl("/weixin/addImageText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getWeiXinReSend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看单个已定义的资源信息");
			soft.setMarks("微信管理->查看单个已定义的资源信息");
			soft.setUrl("/weixin/getWeiXinReSend");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateImage'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改图片资源的权限");
			soft.setMarks("微信管理->修改图片资源的权限");
			soft.setUrl("/weixin/updateImage");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateVideo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改视频资源的权限");
			soft.setMarks("微信管理->修改视频资源的权限");
			soft.setUrl("/weixin/updateVideo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateVoice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改声音资源的权限");
			soft.setMarks("微信管理->修改声音资源的权限");
			soft.setUrl("/weixin/updateVoice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改文本资源的权限");
			soft.setMarks("微信管理->修改文本资源的权限");
			soft.setUrl("/weixin/updateText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateMusic'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改音乐资源的权限");
			soft.setMarks("微信管理->修改音乐资源的权限");
			soft.setUrl("/weixin/updateMusic");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateImageText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改文本+图片资源的权限");
			soft.setMarks("微信管理->修改文本+图片资源的权限");
			soft.setUrl("/weixin/updateImageText");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/deleteReSend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除已定义的资源");
			soft.setMarks("微信管理->删除已定义的资源，用于删除已定义的资源");
			soft.setUrl("/weixin/deleteReSend");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Text'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取文本自动回复列表");
			soft.setMarks("微信管理->获取文本自动回复列表，当微信平台上的关注者发送文字时，自动回复的信息列表");
			soft.setUrl("/weixin/getAutoReSend_Text");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Image'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取图片自动回复列表");
			soft.setMarks("微信管理->获取图片自动回复列表，当微信平台上的关注者发送图片时，自动回复的信息列表，腾讯规定只针对服务号。");
			soft.setUrl("/weixin/getAutoReSend_Image");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Link'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取网址自动回复列表");
			soft.setMarks("微信管理->获取网址自动回复列表，当微信平台上的关注者发送网址时，自动回复的信息列表，腾讯规定只针对服务号。");
			soft.setUrl("/weixin/getAutoReSend_Link");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Location'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取地理位置自动回复列表");
			soft.setMarks("微信管理->获取地理位置自动回复列表，当微信平台上的关注者发送地理位置时，自动回复的信息列表，腾讯规定只针对服务号。");
			soft.setUrl("/weixin/getAutoReSend_Location");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Event'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取手指点击菜单时自动回复列表");
			soft.setMarks("微信管理->获取手指点击菜单时自动回复列表，此菜单必须定义为<<事件>>类型,才可触发此自动回复，而且检测您预先在菜单内设置的检测字。菜单：腾讯规定只有认证的订阅号或服务号才可以开通。");
			soft.setUrl("/weixin/getAutoReSend_Event");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Video'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取视频自动回复列表");
			soft.setMarks("微信管理->获取视频自动回复列表，当微信端的用户发送视频时自动回复。只针对服务号可用");
			soft.setUrl("/weixin/getAutoReSend_Video");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend_Voice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取语音自动回复列表");
			soft.setMarks("微信管理->获取语音自动回复列表，当微信端的用户发送视频时自动回复。只针对服务号可用");
			soft.setUrl("/weixin/getAutoReSend_Voice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Text'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：文本");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：文本");
			soft.setUrl("/weixin/addAutoReSend_Text");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Image'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：图片");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：图片");
			soft.setUrl("/weixin/addAutoReSend_Image");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Link'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：网址链接");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：网址链接");
			soft.setUrl("/weixin/addAutoReSend_Link");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Location'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：地理位置");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：地理位置");
			soft.setUrl("/weixin/addAutoReSend_Location");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Event'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：事件");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：事件,如菜单设置类型为事件，则在此可以得到自动回复。");
			soft.setUrl("/weixin/addAutoReSend_Event");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Video'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：视频");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：视频。");
			soft.setUrl("/weixin/addAutoReSend_Video");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addAutoReSend_Voice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加自动回复——用户发送类型：语音");
			soft.setMarks("微信管理->添加自动回复——用户发送类型：语音。");
			soft.setUrl("/weixin/addAutoReSend_Voice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看设定的自动回复详情");
			soft.setMarks("微信管理->查看设定的自动回复详情");
			soft.setUrl("/weixin/getAutoReSend");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/addWeiXinInfoToAutoResend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->向已设定的自动回复添加已经定义好的资源");
			soft.setMarks("微信管理->向已设定的自动回复添加已经定义好的资源，如：文本，图文，音乐等等。。。部分只支持服务号，或认证的订阅号");
			soft.setUrl("/weixin/addWeiXinInfoToAutoResend");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getAutoReSendItem'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取设定自动回复的资源");
			soft.setMarks("微信管理->获取设定自动回复的资源");
			soft.setUrl("/weixin/getAutoReSendItem");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/deleteWeiXinInfoToAutoResend'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除设定自动回复的资源");
			soft.setMarks("微信管理->删除设定自动回复的资源");
			soft.setUrl("/weixin/deleteWeiXinInfoToAutoResend");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/deleteWeiXinInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除自动回复");
			soft.setMarks("微信管理->删除自动回复");
			soft.setUrl("/weixin/deleteWeiXinInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Text'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：文本");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：文本");
			soft.setUrl("/weixin/updateAutoReSend_Text");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Image'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：图片");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：图片");
			soft.setUrl("/weixin/updateAutoReSend_Image");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Link'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：网址链接");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：网址链接");
			soft.setUrl("/weixin/updateAutoReSend_Link");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Location'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：地理位置");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：地理位置");
			soft.setUrl("/weixin/updateAutoReSend_Location");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Event'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：事件");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：事件,如菜单设置类型为事件，则在此可以得到自动回复。");
			soft.setUrl("/weixin/updateAutoReSend_Event");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Video'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：视频");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：视频");
			soft.setUrl("/weixin/updateAutoReSend_Video");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateAutoReSend_Voice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改自动回复——用户发送类型：语音");
			soft.setMarks("微信管理->修改自动回复——用户发送类型：语音");
			soft.setUrl("/weixin/updateAutoReSend_Voice");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateUse'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->自动回复开启与关闭");
			soft.setMarks("微信管理->自动回复开启与关闭");
			soft.setUrl("/weixin/updateUse");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/sendWenzhang'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->创建文章");
			soft.setMarks("微信管理->创建文章");
			soft.setUrl("/weixin/sendWenzhang");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/gamesController/getGame'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->游戏模块->查看以及添加新游戏");
			soft.setMarks("微信管理->游戏模块->查看以及添加新游戏");
			soft.setUrl("/gamesController/getGame");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/gonggaoController/getGonggao'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->公告->查询公告，添加，删除公告");
			soft.setMarks("微信管理->游戏模块->查询公告，添加，删除公告");
			soft.setUrl("/gonggaoController/getGonggao");
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/ImageModelController/getImage'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->图片模块->查询，删除,审核");
			soft.setMarks("微信管理->图片模块->查询，删除,审核");
			soft.setUrl("/ImageModelController/getImage");
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/ErShouModelController/getErShou'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->格子铺模块->查询，删除,审核");
			soft.setMarks("微信管理->格子铺模块->查询，删除,审核");
			soft.setUrl("/ErShouModelController/getErShou");
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/VideoModelController/getVideo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->视频模块->查询，删除,审核");
			soft.setMarks("微信管理->视频模块->查询，删除,审核");
			soft.setUrl("/VideoModelController/getVideo");
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/TextModelController/getText'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->文字模块->查询，删除,审核");
			soft.setMarks("微信管理->文字模块->查询，删除,审核");
			soft.setUrl("/TextModelController/getText");
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/gamesController/getConpanyGame'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->游戏模块->查看已经添加的游戏");
			soft.setMarks("微信管理->游戏模块->查看已经添加的游戏");
			soft.setUrl("/gamesController/getConpanyGame");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getWenzhang'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查询单个文章详情");
			soft.setMarks("微信管理->查询单个文章详情-在修改文章时需要使用");
			soft.setUrl("/weixin/getWenzhang");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/updateWenzhang'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改更新文章");
			soft.setMarks("微信管理->修改更新文章权限");
			soft.setUrl("/weixin/updateWenzhang");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getWenzhangList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查询文章列表");
			soft.setMarks("微信管理->查询文章列表权限");
			soft.setUrl("/weixin/getWenzhangList");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/deleteWenzhang'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除文章的权限");
			soft.setMarks("微信管理->删除文章的权限");
			soft.setUrl("/weixin/deleteWenzhang");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/addDevice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->添加路由设备");
			soft.setMarks("微信管理->添加路由设备");
			soft.setUrl("/wifiController/addDevice");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/addWifiRigister'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改wifi登录方式");
			soft.setMarks("微信管理->修改wifi登录方式");
			soft.setUrl("/wifiController/addWifiRigister");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/updateDevice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->修改路由设备");
			soft.setMarks("微信管理->修改路由设备");
			soft.setUrl("/wifiController/updateDevice");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/deleteDevice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->删除路由设备");
			soft.setMarks("微信管理->删除路由设备");
			soft.setUrl("/wifiController/deleteDevice");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
		}
/*		list=dao.getObjectList("SoftPermission", "where url='/wifiController/getMainPageContent'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看路由设备登录窗口内容");
			soft.setMarks("微信管理->查看路由设备登录窗口内容，如店内活动，广告等等");
			soft.setUrl("/wifiController/getMainPageContent");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/setMainPageContent'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->设置路由设备登录窗口内容");
			soft.setMarks("微信管理->设置路由设备登录窗口内容，如店内活动，广告等等");
			soft.setUrl("/wifiController/setMainPageContent");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}*/
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/lookLinkDevice'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->查看链接路由的设备");
			soft.setMarks("微信管理->查看链接路由的设备");
			soft.setUrl("/wifiController/lookLinkDevice");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/getDeviceList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->获取路由列表");
			soft.setMarks("微信管理->获取路由列表，您添加的路由列表");
			soft.setUrl("/wifiController/getDeviceList");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		list=dao.getObjectList("SoftPermission", "where url='/wifiController/addMac'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->给上网设备禁网/允许");
			soft.setMarks("微信管理->给上网设备禁网/允许");
			soft.setUrl("/wifiController/addMac");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
			
		}
		//************************微信权限结束***********************//
		//************************微信活动权限开始***********************//
		list=dao.getObjectList("SoftPermission", "where url='/gameController'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限");
			soft.setMarks("微信游戏活动权限，如：大转盘，刮刮卡。。等等");
			soft.setUrl("/gameController");	
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/gameController/getAwards'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->奖品->查看奖品列表的权限");
			soft.setMarks("微信游戏活动权限->奖品->查看奖品列表的权限");
			soft.setUrl("/gameController/getAwards");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/addAwards'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->奖品->添加奖品的权限");
			soft.setMarks("微信游戏活动权限->奖品->添加奖品的权限");
			soft.setUrl("/gameController/addAwards");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/deleteAwards'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->奖品->删除奖品的权限");
			soft.setMarks("微信游戏活动权限->奖品->删除奖品的权限");
			soft.setUrl("/gameController/deleteAwards");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/addDazhuanpan1'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->大转盘奖品1设置权限");
			soft.setMarks("微信游戏活动权限->大转盘->大转盘奖品1设置权限");
			soft.setUrl("/gameController/addDazhuanpan1");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/addDazhuanpan2'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->大转盘奖品2设置权限");
			soft.setMarks("微信游戏活动权限->大转盘->大转盘奖品2设置权限");
			soft.setUrl("/gameController/addDazhuanpan2");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/addDazhuanpan3'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->大转盘奖品3设置权限");
			soft.setMarks("微信游戏活动权限->大转盘->大转盘奖品3设置权限");
			soft.setUrl("/gameController/addDazhuanpan3");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/getDaZhuanpanInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->获取大转盘当前信息权限");
			soft.setMarks("微信游戏活动权限->大转盘->获取大转盘当前信息权限");
			soft.setUrl("/gameController/getDaZhuanpanInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/updateGameDaZhuanPan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->开启与关闭大转盘权限");
			soft.setMarks("微信游戏活动权限->大转盘->开启与关闭大转盘权限");
			soft.setUrl("/gameController/updateGameDaZhuanPan");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/setDazhuanpanJilv'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->设置大转盘几率");
			soft.setMarks("微信游戏活动权限->大转盘->设置大转盘几率");
			soft.setUrl("/gameController/setDazhuanpanJilv");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/setDazhuanpanNum'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->大转盘->设置大转盘每天微信关注者可玩次数");
			soft.setMarks("微信游戏活动权限->大转盘->设置大转盘每天每位微信关注者可玩次数");
			soft.setUrl("/gameController/setDazhuanpanNum");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/setguaguakaJilv'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->设置刮刮卡几率");
			soft.setMarks("微信游戏活动权限->刮刮卡->设置刮刮卡几率");
			soft.setUrl("/gameController/setguaguakaJilv");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/setguaguakaNum'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->设置刮刮卡每位微信关注者可以使用次数");
			soft.setMarks("微信游戏活动权限->刮刮卡->设置刮刮卡每位微信关注者可以使用次数");
			soft.setUrl("/gameController/setguaguakaNum");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/updateGameguaguaka'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->刮刮卡开启关闭权限");
			soft.setMarks("微信游戏活动权限->刮刮卡->刮刮卡开启关闭权限");
			soft.setUrl("/gameController/updateGameguaguaka");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/getguaguaka'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->获取刮刮卡设置信息权限");
			soft.setMarks("微信游戏活动权限->刮刮卡->获取刮刮卡设置信息权限");
			soft.setUrl("/gameController/getguaguaka");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/addAwardsToguaguaka'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->给刮刮卡设置奖品权限");
			soft.setMarks("微信游戏活动权限->刮刮卡->给刮刮卡设置奖品权限");
			soft.setUrl("/gameController/addAwardsToguaguaka");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/gameController/deleteGuaguaka'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信游戏活动权限->刮刮卡->删除刮刮卡奖品权限");
			soft.setMarks("微信游戏活动权限->刮刮卡->删除刮刮卡奖品权限");
			soft.setUrl("/gameController/deleteGuaguaka");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		//************************微信活动权限结束***********************//
		//***************************VIP权限************************//
		list=dao.getObjectList("SoftPermission", "where url='/vip'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("会员管理的权限设置");
			soft.setMarks("主要有：添加积分 获取会员列表 查看会员等级 设置 积分兑换 等等");
			soft.setUrl("/vip");	
			soft.setUplevel(0);
			dao.add(soft);
		}else{
			soft=(SoftPermission) list.iterator().next();
		}
		pid=soft.getId();
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getVip'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->查看会员列表");
			soft.setMarks("微信管理->会员管理的权限设置->查看会员列表，获取会员列表的权限");
			soft.setUrl("/weixin/vip/getVip");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getVipList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->查看已设置的会员等级列表");
			soft.setMarks("微信管理->会员管理的权限设置->查看已设置的会员等级列表，目前已经设置的会员等级");
			soft.setUrl("/weixin/vip/getVipList");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/addVipList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->添加新的会员等级权限");
			soft.setMarks("微信管理->会员管理的权限设置->添加新的会员等级权限。");
			soft.setUrl("/weixin/vip/addVipList");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/addscoreToUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->给用户添加积分");
			soft.setMarks("微信管理->会员管理的权限设置->给用户添加积分。");
			soft.setUrl("/weixin/vip/addscoreToUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/addpriceToUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->给用户充值权限");
			soft.setMarks("微信管理->会员管理的权限设置->给用户充值权限。");
			soft.setUrl("/weixin/vip/addpriceToUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/jianscoreToUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->给用户减少积分");
			soft.setMarks("微信管理->会员管理的权限设置->给用户减少积分。");
			soft.setUrl("/weixin/vip/jianscoreToUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/jianpriceToUser'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->给用户减少储值钱数权限");
			soft.setMarks("微信管理->会员管理的权限设置->给用户减少储值钱数权限。");
			soft.setUrl("/weixin/vip/jianpriceToUser");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getHuiyuanxinxi'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->查看会员的级别信息");
			soft.setMarks("微信管理->会员管理的权限设置->查看会员的级别信息。");
			soft.setUrl("/weixin/vip/getHuiyuanxinxi");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getVipInfo'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->查看级别信息");
			soft.setMarks("微信管理->会员管理的权限设置->查看级别信息。在修改级别信息中要使用到");
			soft.setUrl("/weixin/vip/getVipInfo");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/updateVipList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->修改等级信息");
			soft.setMarks("微信管理->会员管理的权限设置->修改等级信息。修改达到级别的条件，修改级别描述");
			soft.setUrl("/weixin/vip/updateVipList");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getScoreDuiHuanList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->获取积分兑换列表");
			soft.setMarks("微信管理->会员管理的权限设置->获取积分兑换列表");
			soft.setUrl("/weixin/vip/getScoreDuiHuanList");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/addScoreDuiHuanList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->向兑换列表中添加新的兑换品");
			soft.setMarks("微信管理->会员管理的权限设置->向兑换列表中添加新的兑换品");
			soft.setUrl("/weixin/vip/addScoreDuiHuanList");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/getScoreDuiHuan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->查看兑换物详情");
			soft.setMarks("微信管理->会员管理的权限设置->查看兑换物详情，修改等事件中需要用到");
			soft.setUrl("/weixin/vip/getScoreDuiHuan");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/updateScoreDuiHuan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->修改兑换物详情");
			soft.setMarks("微信管理->会员管理的权限设置->修改兑换物详情");
			soft.setUrl("/weixin/vip/updateScoreDuiHuan");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/vip/deleteScoreDuiHuan'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->会员管理的权限设置->删除兑换物详情");
			soft.setMarks("微信管理->会员管理的权限设置->删除兑换物详情");
			soft.setUrl("/weixin/vip/deleteScoreDuiHuan");	
			soft.setUplevel(pid);
			dao.add(soft);
		}
		list=dao.getObjectList("SoftPermission", "where url='/weixin/getVoteUserList'");
		if(list.size()<1){
			soft=new SoftPermission();
			soft.setFunctionName("微信管理->文章管理->查看投票人");
			soft.setMarks("微信管理->文章管理->查看投票人");
			soft.setUrl("/weixin/getVoteUserList");	
			soft.setUplevel(pid);
			dao.add(soft);
			List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
			Iterator<Object> i=object.iterator();
			while(i.hasNext()){
				Role role=(Role) i.next();
				SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
				rolelinksoft.setConpanyId(role.getConpanyId());
				rolelinksoft.setFunctionName(soft.getFunctionName());
				rolelinksoft.setGroupId(role.getGroupid());
				rolelinksoft.setRoleId(role.getId());
				rolelinksoft.setRoleName(role.getName());
				rolelinksoft.setSoftPermissionId(soft.getId());
				dao.add(rolelinksoft);
			}
		}
		//************************会员权限结束***********************//
		//************************活动序列号积分序列号权限开始***********************//
				list=dao.getObjectList("SoftPermission", "where url='/weixin/CodeConvert'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("序列号兑换权限");
					soft.setMarks("兑换权限，当用户用积分，或者玩活动中奖获得序列号后到店进行兑换时使用");
					soft.setUrl("/weixin/CodeConvert");	
					soft.setUplevel(0);
					dao.add(soft);
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/weixin/CodeConvert/jifenTogoodManager'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("序列号兑换->积分序列号兑换");
					soft.setMarks("序列号兑换->积分序列号兑换，检测当用户使用积分兑换的序列号使用此权限");
					soft.setUrl("/weixin/CodeConvert/jifenTogoodManager");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/CodeConvert/huodongTogoodManager'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("序列号兑换->活动序列号兑换");
					soft.setMarks("序列号兑换->活动序列号兑换，检测当用户使用参与活动后获取的序列号使用此权限");
					soft.setUrl("/weixin/CodeConvert/huodongTogoodManager");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				//************************会员权限结束***********************//
				//*************************微网站设计*********************//
				//************************微信活动权限开始***********************//
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/web'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信微网站管理");
					soft.setMarks("微信微网站设计，创建模板，店铺地址设置等等");
					soft.setUrl("/weixin/function/web");	
					soft.setUplevel(0);
					dao.add(soft);
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/saveWeixinWeb'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信微网站管理权限->保存微网站设计");
					soft.setMarks("微信微网站管理权限->保存微网站设计");
					soft.setUrl("/weixin/function/saveWeixinWeb");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/getWeixinWeb'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信微网站管理权限->查看微信微网站设定好的模板");
					soft.setMarks("微信微网站管理权限->查看微信微网站设定好的模板");
					soft.setUrl("/weixin/function/getWeixinWeb");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/useWeixinWeb'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信微网站管理权限->选择使用模板权限");
					soft.setMarks("微信微网站管理权限->选择使用模板权限");
					soft.setUrl("/weixin/function/useWeixinWeb");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/deleteWeixinWeb'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信管理->删除模板");
					soft.setMarks("微信管理->删除模板权限");
					soft.setUrl("/weixin/function/deleteWeixinWeb");	
					soft.setUplevel(pid);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
					
				}
				//************************微网站设计结束*******************//
				//************************设置地理位置权限开始***********************//
				list=dao.getObjectList("SoftPermission", "where url='/weixin/map'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("地理位置权限");
					soft.setMarks("设置地理位置等信息的权限");
					soft.setUrl("/weixin/map");	
					soft.setUplevel(0);
					dao.add(soft);
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/saveMap'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("地理位置权限->设置地理位置");
					soft.setMarks("地理位置权限->设置地理位置，付费用户可以设置多店位置");
					soft.setUrl("/weixin/function/saveMap");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/getMaps'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("地理位置权限->获取设置的地理位置");
					soft.setMarks("获取地理已经设置定地理位置，如果是付费用户则可以设置多个店的地址。");
					soft.setUrl("/weixin/function/getMaps");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/function/deleteMap'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("地理位置权限->删除地理位置");
					soft.setMarks("删除地理位置之后可以重置地理位置，如选错，迁址");
					soft.setUrl("/weixin/function/deleteMap");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				//************************设置地理位置权限结束***********************//
				//************************设置微信货物管理权限开始***********************//
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限");
					soft.setMarks("微信部分商品管理权限");
					soft.setUrl("/weixin/goods");	
					soft.setUplevel(0);
					dao.add(soft);
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/add'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->添加新商品权限");
					soft.setMarks("微信商品管理权限->允许角色添加新的微信商品。添加微信商品同时会在库存增加商品（进销存只有付费用户可以使用）");
					soft.setUrl("/weixin/goods/add");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/delete'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->删除商品权限");
					soft.setMarks("删除微信商品，在库存中则不会删除（进销存只有付费用户可以使用）");
					soft.setUrl("/weixin/goods/delete");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/update'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->更新商品权限");
					soft.setMarks("修改微信商品信息的权限");
					soft.setUrl("/weixin/goods/update");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/query'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->查询商品权限");
					soft.setMarks("查询商品权限");
					soft.setUrl("/weixin/goods/query");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/shangjia'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->上架商品的权限");
					soft.setMarks("上架商品的权限，上架后微信用户则可以看到");
					soft.setUrl("/weixin/goods/shangjia");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/goods/xiajia'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信商品管理权限->下架商品的权限");
					soft.setMarks("下架权限，商品下架后微信端则看不到");
					soft.setUrl("/weixin/goods/xiajia");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				//************************设置微信货物管理权限结束***********************//
				//************************微信订单管理权限****************//
				list=dao.getObjectList("SoftPermission", "where url='/weixin/order'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信订单管理权限");
					soft.setMarks("微信订单管理权限");
					soft.setUrl("/weixin/order");	
					soft.setUplevel(0);
					dao.add(soft);
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/weixin/order/delete'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信订单->取消订单权限");
					soft.setMarks("微信订单->取消订单权限");
					soft.setUrl("/weixin/order/delete");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/order/enter'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信订单->确定订单已发货权限");
					soft.setMarks("微信订单->确定订单已发货权限");
					soft.setUrl("/weixin/order/enter");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				list=dao.getObjectList("SoftPermission", "where url='/weixin/order/look'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("微信订单->确定订单查询权限");
					soft.setMarks("微信订单->确定订单查询权限");
					soft.setUrl("/weixin/order/look");	
					soft.setUplevel(pid);
					dao.add(soft);
				}
				//************************短信****************//
				list=dao.getObjectList("SoftPermission", "where url='/messageinfo'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("短信管理");
					soft.setMarks("短信管理");
					soft.setUrl("/messageinfo");	
					soft.setUplevel(0);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
				}else{
					soft=(SoftPermission) list.iterator().next();
				}
				pid=soft.getId();
				list=dao.getObjectList("SoftPermission", "where url='/messageinfo/setboolean'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("短信管理->短信设置权限");
					soft.setMarks("短信管理->短信设置权限");
					soft.setUrl("/messageinfo/setboolean");	
					soft.setUplevel(pid);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
					
				}
				list=dao.getObjectList("SoftPermission", "where url='/messageinfo/getMessageSet'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("短信管理->查看短信设置权限");
					soft.setMarks("短信管理->查看短信设置权限");
					soft.setUrl("/messageinfo/getMessageSet");	
					soft.setUplevel(pid);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
					
				}
				list=dao.getObjectList("SoftPermission", "where url='/messageinfo/setTemp'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("短信管理->设置短信模板");
					soft.setMarks("短信管理->设置短信模板权限");
					soft.setUrl("/messageinfo/setTemp");	
					soft.setUplevel(pid);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
					
				}
				list=dao.getObjectList("SoftPermission", "where url='/messageinfo/sendVipMessage'");
				if(list.size()<1){
					soft=new SoftPermission();
					soft.setFunctionName("短信管理->群发vip短信权限");
					soft.setMarks("短信管理->群发vip短信权限");
					soft.setUrl("/messageinfo/sendVipMessage");	
					soft.setUplevel(pid);
					dao.add(soft);
					List<Object> object=dao.getObjectList("Role", "where conpanyAdminRole=true");
					Iterator<Object> i=object.iterator();
					while(i.hasNext()){
						Role role=(Role) i.next();
						SoftPermissionLinkConpanyRole rolelinksoft=new SoftPermissionLinkConpanyRole();
						rolelinksoft.setConpanyId(role.getConpanyId());
						rolelinksoft.setFunctionName(soft.getFunctionName());
						rolelinksoft.setGroupId(role.getGroupid());
						rolelinksoft.setRoleId(role.getId());
						rolelinksoft.setRoleName(role.getName());
						rolelinksoft.setSoftPermissionId(soft.getId());
						dao.add(rolelinksoft);
					}
					
				}
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initIndex(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		
		return "index";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,Object> map = (Map) req.getAttribute("map");
		if(map==null){
			model.addAttribute("message", "1"); 
			return "login";
		}else{
			model.addAttribute("message", map.get("info"));
			return "login";
		}
	}
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public Map loginAjax(Model model, HttpServletRequest req,
			HttpServletResponse res) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Map<String,Object> map =new HashMap<String, Object>();
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		if(username==null||username.trim().equals("")){
			map.put("success", false);
			map.put("info", "账号错误");
			return map;
		}
		if(password==null||password.trim().equals("")){
			map.put("success", false);
			map.put("info", "密码错误");
			return map;
		}
		ConpanyUser user=dao.getConpanyUserByUserName(username);
		if(user==null){
			map.put("success", false);
			map.put("info", "登录失败,账号错误");
			return map;
		}
		if(!user.isUseLogin()){
			map.put("success", false);
			map.put("info", "登录失败,账号被管理员封号");
			return map;
		}
		if(!user.isFreeAcccunt()){
			if(user.getAccuntEndDate()==null){
				map.put("success", false);
				map.put("info", "登录失败,此账号未付费开通");
				return map;
			}else if(!(user.getAccuntEndDate().after(new Date()))){
				map.put("success", false);
				map.put("info", "登录失败,此账号已到期，请续费");
				return map;
			}
		}
		if(MD5Util.validPasswd(password, user.getPassword())){
			map.put("success", true);
			map.put("info", "登录成功");
			Conpany conpany=(Conpany) dao.getObject(user.getConpanyId(), "Conpany");
			req.getSession().setAttribute(SessionString.USER_OBJ, user);
			req.getSession().setAttribute(SessionString.CONPANY_OBJ, conpany);
		}else{
			map.put("success", false);
			map.put("info", "登录失败,密码错误");
		}
		return map;
	}
	@RequestMapping(value = "/backManager")
	public String backManager(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List<SoftPermission> list=dao.getPageSoftPermissions(users.getId(), users.getConpanyId());
		Iterator i=list.iterator();
		boolean crm=false;
		boolean goods=false;
		boolean hr=false;
		boolean system=false;
		boolean weixin=false;
		boolean message=false;
		Conpany conpany=(Conpany) dao.getObject(users.getConpanyId(), "Conpany");
		if(conpany.isPayConpany()&&conpany.getEndDate().after(new Date())){
		while(i.hasNext()){
				SoftPermission soft=(SoftPermission) i.next();
				if("/crm/page/main".equals(soft.getUrl())){
					model.addAttribute("crm1", true);
					crm=true;
				}
				if("/crm/page/myCreateCustomChance".equals(soft.getUrl())){
					model.addAttribute("crm2", true);
					crm=true;
				}
				if("/crm/page/toMyCustomChance".equals(soft.getUrl())){
					model.addAttribute("crm3", true);
					crm=true;
				}
				if("/crm/page/MyCustomManager".equals(soft.getUrl())){
					model.addAttribute("crm4", true);
					crm=true;
				}
				if("/crm/page/queryAllChanceList".equals(soft.getUrl())){
					model.addAttribute("crm5", true);
					crm=true;
				}
				if("/goods/page/main".equals(soft.getUrl())){
					model.addAttribute("good1", true);
					goods=true;
				}
				if("/goods/page/queryData".equals(soft.getUrl())){
					model.addAttribute("good2", true);
					goods=true;
				}
				if("/goods/page/inGoods".equals(soft.getUrl())){
					model.addAttribute("good3", true);
					goods=true;
				}
				if("/goods/page/outGoods".equals(soft.getUrl())){
					model.addAttribute("good4", true);
					goods=true;
				}
				if("/goods/page/GoodSource".equals(soft.getUrl())){
					model.addAttribute("good5", true);
					goods=true;
				}
				if("/goods/page/storehouse".equals(soft.getUrl())){
					model.addAttribute("good6", true);
					goods=true;
				}
				if("/hr/page/main".equals(soft.getUrl())){
					model.addAttribute("hr1", true);
					hr=true;
				}
				if("/hr/page/custemr".equals(soft.getUrl())){
					model.addAttribute("hr2", true);
					hr=true;
				}
				if("/hr/page/performance".equals(soft.getUrl())){
					model.addAttribute("hr3", true);
					hr=true;
				}
				if("/hr/page/meeting".equals(soft.getUrl())){
					model.addAttribute("hr4", true);
					hr=true;
				}
				if("/hr/page/waiqin".equals(soft.getUrl())){
					model.addAttribute("hr5", true);
					hr=true;
				}
				if("/hr/page/kongjian".equals(soft.getUrl())){
					model.addAttribute("hr6", true);
					hr=true;
				}
				if("/itempage/systemPermission".equals(soft.getUrl())){
					model.addAttribute("sys1", true);
					system=true;
				}
				if("/weixin/page/main".equals(soft.getUrl())){
					model.addAttribute("weixin1", true);
					weixin=true;
				}
				if("/weixin/page/weixin_UserId_Set".equals(soft.getUrl())){
					model.addAttribute("weixin2", true);
					weixin=true;
				}
				if("/weixin/page/weixin_VIP_set".equals(soft.getUrl())){
					model.addAttribute("weixin3", true);
					weixin=true;
				}
				if("/weixin/page/weixin_Game_set".equals(soft.getUrl())){
					model.addAttribute("weixin4", true);
					weixin=true;
				}
				if("/weixin/page/weixin_convert_set".equals(soft.getUrl())){
					model.addAttribute("weixin5", true);
					weixin=true;
				}
				if("/weixin/page/weixin_Model_set".equals(soft.getUrl())){
					model.addAttribute("weixin6", true);
					weixin=true;
				}
				if("/weixin/page/weixin_Model_set_map".equals(soft.getUrl())){
					model.addAttribute("weixin7", true);
					weixin=true;
				}
				
				if("/weixin/page/weixin_model_goodsManager".equals(soft.getUrl())){
					model.addAttribute("weixin8", true);
					weixin=true;
				}
				if("/weixin/page/weixin_model_order".equals(soft.getUrl())){
					model.addAttribute("weixin9", true);
					weixin=true;
				}
				if("/wifiController/main".equals(soft.getUrl())){
					model.addAttribute("weixin10", true);
					weixin=true;
				}
				if("/weixin/page/imageModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin11", true);
					weixin=true;
				}
				if("/weixin/page/videoModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin12", true);
					weixin=true;
				}
				if("/weixin/page/textModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin13", true);
					weixin=true;
				}
				if("/weixin/page/ershouModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin14", true);
					weixin=true;
				}
				if("/weixin/page/gameModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin15", true);
					weixin=true;
				}
				if("/weixin/page/gonggaoModelManager".equals(soft.getUrl())){
					model.addAttribute("weixin16", true);
					weixin=true;
				}
				if("/messageinfo/main".equals(soft.getUrl())){
					model.addAttribute("message1", true);
					message=true;
				}
				if("/messageinfo/vipsend".equals(soft.getUrl())){
					model.addAttribute("message2", true);
					message=true;
				}
				if("/messageinfo/messagetemp".equals(soft.getUrl())){
					model.addAttribute("message3", true);
					message=true;
				}
				if("/messageinfo/messagelog".equals(soft.getUrl())){
					model.addAttribute("message4", true);
					message=true;
				}
			}
		}else{
			model.addAttribute("crm1", false);
			model.addAttribute("crm2", false);
			model.addAttribute("crm3", false);
			model.addAttribute("crm4", false);
			model.addAttribute("crm5", false);
			crm=false;
			model.addAttribute("good1", false);
			model.addAttribute("good2", false);
			model.addAttribute("good3", false);
			model.addAttribute("good4", false);
			model.addAttribute("good5", false);
			model.addAttribute("good6", false);
			goods=false;
			model.addAttribute("hr1", false);
			model.addAttribute("hr2", false);
			model.addAttribute("hr3", false);
			model.addAttribute("hr4", false);
			model.addAttribute("hr5", false);
			model.addAttribute("hr6", false);
			hr=false;
			model.addAttribute("sys1", false);
			system=false;
			model.addAttribute("weixin1", true);
			model.addAttribute("weixin2", true);
			model.addAttribute("weixin3", true);
			model.addAttribute("weixin4", true);
			model.addAttribute("weixin5", true);
			model.addAttribute("weixin6", true);
			model.addAttribute("weixin7", true);
			model.addAttribute("weixin8", true);
			model.addAttribute("weixin9", true);
			model.addAttribute("weixin10", true);
			model.addAttribute("weixin11", true);
			model.addAttribute("weixin12", true);
			model.addAttribute("weixin13", true);
			model.addAttribute("weixin14", true);
			model.addAttribute("weixin15", true);
			model.addAttribute("weixin16", true);
			weixin=true;
			model.addAttribute("message1", false);
			model.addAttribute("message2", false);
			model.addAttribute("message3", false);
			model.addAttribute("message4", false);
			message=false;
		}
		if(crm){
			model.addAttribute("crm", true);
		}else{
			model.addAttribute("crm", false);
		}
		if(goods){
			model.addAttribute("goods", true);
		}else{
			model.addAttribute("goods", false);
		}
		if(hr){
			model.addAttribute("hr", true);
		}else{
			model.addAttribute("hr", false);
		}
		if(system){
			model.addAttribute("system", true);
		}else{
			model.addAttribute("system", false);
		}
		if(weixin){
			model.addAttribute("weixin", true);
		}else{
			model.addAttribute("weixin", false);
		}
		if(message){
			model.addAttribute("messageInfo", true);
		}else{
			model.addAttribute("messageInfo", false);
		}
		model.addAttribute("user", users);
		model.addAttribute("conpany", req.getSession().getAttribute(SessionString.CONPANY_OBJ));
		List<Message> listme=dao.getMessageNotRead(users.getId(), users.getConpanyId());
		model.addAttribute("msgNum", listme.size());
		model.addAttribute("msg", listme);
		return "banckManager/manpage";
	}
	@RequestMapping(value = "/backManagerajax")
	@ResponseBody
	public Map backManagerajax(Model model1, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> model=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List<SoftPermission> list=dao.getPageSoftPermissions(users.getId(), users.getConpanyId());
		Iterator i=list.iterator();
		
		boolean crm=false;
		List<Map<String,Object>> listcrm=new ArrayList<Map<String, Object>>();
		boolean goods=false;
		List<Map<String,Object>> listgoods=new ArrayList<Map<String, Object>>();
		boolean hr=false;
		List<Map<String,Object>> listhr=new ArrayList<Map<String, Object>>();
		boolean system=false;
		List<Map<String,Object>> listsystem=new ArrayList<Map<String, Object>>();
		boolean weixin=false;
		List<Map<String,Object>> listweixin=new ArrayList<Map<String, Object>>();
		boolean message=false;
		List<Map<String,Object>> listmessage=new ArrayList<Map<String, Object>>();
		Conpany conpany=(Conpany) dao.getObject(users.getConpanyId(), "Conpany");
		if(conpany.isPayConpany()&&conpany.getEndDate().after(new Date())){
		while(i.hasNext()){
				SoftPermission soft=(SoftPermission) i.next();
			/*	if("/crm/page/main".equals(soft.getUrl())){
					
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "crm1");
					map.put("marks", "客户机会概览");
					listcrm.add(map);
					crm=true;
				}*/
				if("/crm/page/myCreateCustomChance".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "crm2");
					map.put("marks", "我创建客户机会");
					listcrm.add(map);
					crm=true;
				}
				if("/crm/page/toMyCustomChance".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "crm3");
					map.put("marks", "分配给我的客户机会");
					listcrm.add(map);
					crm=true;
				}
				if("/crm/page/MyCustomManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "crm4");
					map.put("marks", "我的客户管理");
					listcrm.add(map);
					crm=true;
				}
				if("/crm/page/queryAllChanceList".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "crm5");
					map.put("marks", "全部客户机");
					listcrm.add(map);
					crm=true;
				}
				/*if("/goods/page/main".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good1");
					map.put("marks", "库存概况");
					listgoods.add(map);
					goods=true;
				}*/
				if("/goods/page/queryData".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good2");
					map.put("marks", "库存查询");
					listgoods.add(map);
					goods=true;
				}
				if("/goods/page/inGoods".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good3");
					map.put("marks", "进货管理");
					listgoods.add(map);
					goods=true;
				}
				if("/goods/page/outGoods".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good4");
					map.put("marks", "销售管理");
					listgoods.add(map);
					goods=true;
				}
				if("/goods/page/GoodSource".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good5");
					map.put("marks", "进货商信息");
					listgoods.add(map);
					goods=true;
				}
				if("/goods/page/storehouse".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "good6");
					map.put("marks", "仓库信息");
					listgoods.add(map);
					goods=true;
				}
				if("/hr/page/main".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr1");
					map.put("marks", "添加员工账号");
					listhr.add(map);
					hr=true;
				}
				if("/hr/page/custemr".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr2");
					map.put("marks", "员工账号管理");
					listhr.add(map);
					hr=true;
				}
				if("/hr/page/performance".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr3");
					map.put("marks", "绩效管理");
					listhr.add(map);
					hr=true;
				}
				if("/hr/page/meeting".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr4");
					map.put("marks", "签到管理");
					listhr.add(map);
					hr=true;
				}
				if("/hr/page/waiqin".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr5");
					map.put("marks", "外勤管理");
					listhr.add(map);
					hr=true;
				}
				if("/hr/page/kongjian".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "hr6");
					map.put("marks", "互动空间");
					listhr.add(map);
					hr=true;
				}
				if("/itempage/systemPermission".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "sys1");
					map.put("marks", "权限管理");
					listsystem.add(map);
					system=true;
				}
				if("/weixin/page/main".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin1");
					map.put("marks", "概览");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_UserId_Set".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin2");
					map.put("marks", "微信公众账号设置");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_VIP_set".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin3");
					map.put("marks", "会员管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_Game_set".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin4");
					map.put("marks", "活动与游戏管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_convert_set".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin5");
					map.put("marks", "序列号兑换");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_Model_set".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin6");
					map.put("marks", "模板设置");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_Model_set_map".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin7");
					map.put("marks", "店铺地址设置");
					listweixin.add(map);
					weixin=true;
				}
				
				if("/weixin/page/weixin_model_goodsManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin8");
					map.put("marks", "微信商品管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/weixin_model_order".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin9");
					map.put("marks", "微信订单管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/wifiController/main".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin10");
					map.put("marks", "会员wifi功能");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/imageModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin11");
					map.put("marks", "图片交流模块管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/videoModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin12");
					map.put("marks", "视频交流模块管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/textModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin13");
					map.put("marks", "文字交流模块管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/ershouModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin14");
					map.put("marks", "格子铺模块管理");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/gameModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin15");
					map.put("marks", "游戏模块");
					listweixin.add(map);
					weixin=true;
				}
				if("/weixin/page/gonggaoModelManager".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "weixin16");
					map.put("marks", "公告模块");
					listweixin.add(map);
					weixin=true;
				}
				if("/messageinfo/main".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "message1");
					map.put("marks", "短信设置");
					listmessage.add(map);
					message=true;
				}
				if("/messageinfo/vipsend".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "message2");
					map.put("marks", "会员发信");
					listmessage.add(map);
					message=true;
				}
				if("/messageinfo/messagetemp".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "message3");
					map.put("marks", "短信模板");
					listmessage.add(map);
					message=true;
				}
				/*if("/messageinfo/messagelog".equals(soft.getUrl())){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("isuse", true);
					map.put("name", "message4");
					map.put("marks", "历史信息");
					listmessage.add(map);
					message=true;
				}*/
			}
		}else{
			Map<String,Object> crmmap1=new HashMap<String, Object>();
			crmmap1.put("isuse", false);
			crmmap1.put("name", "crm1");
			listcrm.add(crmmap1);
			Map<String,Object> crmmap2=new HashMap<String, Object>();
			crmmap2.put("isuse", false);
			crmmap2.put("name", "crm2");
			listcrm.add(crmmap2);
			Map<String,Object> crmmap3=new HashMap<String, Object>();
			crmmap3.put("isuse", false);
			crmmap3.put("name", "crm3");
			listcrm.add(crmmap3);
			Map<String,Object> crmmap4=new HashMap<String, Object>();
			crmmap4.put("isuse", false);
			crmmap4.put("name", "crm4");
			listcrm.add(crmmap4);
			Map<String,Object> crmmap5=new HashMap<String, Object>();
			crmmap5.put("isuse", false);
			crmmap5.put("name", "crm5");
			listcrm.add(crmmap5);
			crm=false;
			Map<String,Object> goodmap1=new HashMap<String, Object>();
			goodmap1.put("isuse", false);
			goodmap1.put("name", "good1");
			listgoods.add(goodmap1);
			Map<String,Object> goodmap2=new HashMap<String, Object>();
			goodmap2.put("isuse", false);
			goodmap2.put("name", "good2");
			listgoods.add(goodmap2);
			Map<String,Object> goodmap3=new HashMap<String, Object>();
			goodmap3.put("isuse", false);
			goodmap3.put("name", "good3");
			listgoods.add(goodmap3);
			Map<String,Object> goodmap4=new HashMap<String, Object>();
			goodmap4.put("isuse", false);
			goodmap4.put("name", "good4");
			listgoods.add(goodmap4);
			Map<String,Object> goodmap5=new HashMap<String, Object>();
			goodmap5.put("isuse", false);
			goodmap5.put("name", "good5");
			listgoods.add(goodmap5);
			Map<String,Object> goodmap6=new HashMap<String, Object>();
			goodmap6.put("isuse", false);
			goodmap6.put("name", "good6");
			listgoods.add(goodmap6);
			goods=false;
			Map<String,Object> hrmap1=new HashMap<String, Object>();
			hrmap1.put("isuse", false);
			hrmap1.put("name", "hr1");
			listhr.add(hrmap1);
			Map<String,Object> hrmap2=new HashMap<String, Object>();
			hrmap2.put("isuse", false);
			hrmap2.put("name", "hr2");
			listhr.add(hrmap2);
			Map<String,Object> hrmap3=new HashMap<String, Object>();
			hrmap3.put("isuse", false);
			hrmap3.put("name", "hr3");
			listhr.add(hrmap3);
			Map<String,Object> hrmap4=new HashMap<String, Object>();
			hrmap4.put("isuse", false);
			hrmap4.put("name", "hr4");
			listhr.add(hrmap4);
			Map<String,Object> hrmap5=new HashMap<String, Object>();
			hrmap5.put("isuse", false);
			hrmap5.put("name", "hr5");
			hrmap5.put("marks", "外勤管理");
			listhr.add(hrmap5);
			Map<String,Object> hrmap6=new HashMap<String, Object>();
			hrmap6.put("isuse", false);
			hrmap6.put("name", "hr6");
			hrmap6.put("marks", "互动空间");
			listhr.add(hrmap6);
			hr=false;
			
			Map<String,Object> sysmap1=new HashMap<String, Object>();
			sysmap1.put("isuse", false);
			sysmap1.put("name", "sys1");
			listsystem.add(sysmap1);
			system=false;
			Map<String,Object> weixinmap1=new HashMap<String, Object>();
			weixinmap1.put("isuse", false);
			weixinmap1.put("name", "weixin1");
			listweixin.add(weixinmap1);
			Map<String,Object> weixinmap2=new HashMap<String, Object>();
			weixinmap2.put("isuse", false);
			weixinmap2.put("name", "weixin2");
			listweixin.add(weixinmap2);
			Map<String,Object> weixinmap3=new HashMap<String, Object>();
			weixinmap3.put("isuse", false);
			weixinmap3.put("name", "weixin3");
			listweixin.add(weixinmap3);
			Map<String,Object> weixinmap4=new HashMap<String, Object>();
			weixinmap4.put("isuse", false);
			weixinmap4.put("name", "weixin4");
			listweixin.add(weixinmap4);
			Map<String,Object> weixinmap5=new HashMap<String, Object>();
			weixinmap5.put("isuse", false);
			weixinmap5.put("name", "weixin5");
			listweixin.add(weixinmap5);
			Map<String,Object> weixinmap6=new HashMap<String, Object>();
			weixinmap6.put("isuse", false);
			weixinmap6.put("name", "weixin6");
			listweixin.add(weixinmap6);
			Map<String,Object> weixinmap7=new HashMap<String, Object>();
			weixinmap7.put("isuse", false);
			weixinmap7.put("name", "weixin7");
			listweixin.add(weixinmap7);
			Map<String,Object> weixinmap8=new HashMap<String, Object>();
			weixinmap8.put("isuse", false);
			weixinmap8.put("name", "weixin8");
			listweixin.add(weixinmap8);
			Map<String,Object> weixinmap9=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin9");
			listweixin.add(weixinmap9);
			Map<String,Object> weixinmap10=new HashMap<String, Object>();
			weixinmap10.put("isuse", false);
			weixinmap10.put("name", "weixin10");
			listweixin.add(weixinmap10);
			Map<String,Object> weixinmap11=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin11");
			listweixin.add(weixinmap11);
			Map<String,Object> weixinmap12=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin12");
			listweixin.add(weixinmap12);
			Map<String,Object> weixinmap13=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin13");
			listweixin.add(weixinmap13);
			Map<String,Object> weixinmap14=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin14");
			listweixin.add(weixinmap14);
			Map<String,Object> weixinmap15=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin15");
			listweixin.add(weixinmap15);
			Map<String,Object> weixinmap16=new HashMap<String, Object>();
			weixinmap9.put("isuse", false);
			weixinmap9.put("name", "weixin16");
			listweixin.add(weixinmap16);
			weixin=true;
			Map<String,Object> messagemap1=new HashMap<String, Object>();
			messagemap1.put("isuse", false);
			messagemap1.put("name", "message1");
			listmessage.add(messagemap1);
			Map<String,Object> messagemap2=new HashMap<String, Object>();
			messagemap2.put("isuse", false);
			messagemap2.put("name", "message2");
			listmessage.add(messagemap2);
			Map<String,Object> messagemap3=new HashMap<String, Object>();
			messagemap3.put("isuse", false);
			messagemap3.put("name", "message3");
			listmessage.add(messagemap3);
			/*Map<String,Object> messagemap4=new HashMap<String, Object>();
			messagemap4.put("isuse", false);
			messagemap4.put("name", "message4");
			listmessage.add(messagemap4);*/
			
			message=false;
		}
		
		if(crm){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listcrm);
			model.put("crm", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listcrm);
			model.put("crm", map);
		}
		if(goods){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listgoods);
			model.put("goods", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listgoods);
			model.put("goods", map);
		}
		if(hr){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listhr);
			model.put("hr", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listhr);
			model.put("hr", map);
		}
		if(system){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listsystem);
			model.put("system", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listsystem);
			model.put("system", map);
		}
		if(weixin){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listweixin);
			model.put("weixin", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listweixin);
			model.put("weixin", map);
		}
		if(message){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", true);
			map.put("list", listmessage);
			model.put("messageInfo", map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("isuse", false);
			map.put("list", listmessage);
			model.put("messageInfo", map);
		}
		model.put("user", users);
		model.put("conpany", req.getSession().getAttribute(SessionString.CONPANY_OBJ));
		List<Message> listme=dao.getMessageNotRead(users.getId(), users.getConpanyId());
		model.put("msgNum", listme.size());
		model.put("msg", listme);
		return model;
	}
	@RequestMapping(value = "/rigister", method = RequestMethod.GET)
	public String rigister(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "rigister";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		req.getSession().invalidate();
		return "index";
	}
	/**
	 * 激活
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/jihuo", method = RequestMethod.GET)
	public String jihuo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		String jihuoid=req.getParameter("jihuoid");
		if(StringUtil.isStringIsNull(jihuoid)){
			model.addAttribute("errorInfo", "该激活号不存在。");
			return "error";
		}
		Object obj=dao.getObject(Long.parseLong(jihuoid), "Conpany");
		if(obj==null){
			model.addAttribute("errorInfo", "该激活号不存在。");
			return "error";
		}
		Conpany con=(Conpany)obj;
		if(con.isUseConpany()){
			model.addAttribute("errorInfo", "该账号已经激活过了");
			return "error";
		}
		req.getSession().setAttribute("conpany", con);
		return "jihuo";
	}
	/**
	 * 注册一个公司的管理员账号
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 * @throws AccountEmailException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/rigisterAdminOfConpany")
	@ResponseBody
	public Map rigisterAdminOfConpany(Model model, HttpServletRequest req,
			HttpServletResponse res) throws AccountEmailException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Map<String,Object> map =new HashMap<String, Object>();
		
		Conpany conpany =(Conpany) req.getSession().getAttribute("conpany");
		if(conpany==null){
			map.put("success", false);
			map.put("info", "公司信息错误。请重新访问并尝试");
			return map;
		}
		if(conpany.isUseConpany()){
			map.put("success", false);
			map.put("info", "已经激活过了");
			return map;
		}
		String admin=req.getParameter("admin");
		String password=req.getParameter("password");
		String repassword=req.getParameter("repassword");
		if(StringUtil.isStringIsNull(admin)){
			map.put("success", false);
			map.put("info", "账号填写错误");
			return map;
		}
		if(StringUtil.isStringIsNull(password)){
			map.put("success", false);
			map.put("info", "密码填写错误");
			return map;
		}
		if(StringUtil.isStringIsNull(repassword)){
			map.put("success", false);
			map.put("info", "重复输入密码填写错误");
			return map;
		}
		if(!password.equals(repassword)){
			map.put("success", false);
			map.put("info", "两次密码输入不一致");
			return map;
		}
		String username=admin+"@"+conpany.getId()+".com";
		if(dao.getConpanyUserByUserName(username)==null){
			//注册用户
			ConpanyUser conpanyUser=new ConpanyUser();
			conpanyUser.setUsername(username);
			conpanyUser.setFreeAcccunt(true);
			conpanyUser.setTrueName("admin");
			conpanyUser.setUseLogin(true);
			conpanyUser.setEmail(conpany.getConpanyAdminEmail());
			conpanyUser.setAccuntStartDate(new Date());
			conpanyUser.setPassword(MD5Util.getEncryptedPwd(password));
			conpanyUser.setConpanyId(conpany.getId());
			conpanyUser.setConpanyAdmin(true);
			
			dao.add(conpanyUser);
			conpany.setUseConpany(true);
			conpany.setPayConpany(true);
			Calendar calendar=Calendar.getInstance();   
			calendar.setTime(new Date()); 
			calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+90);//让日期加1  
			conpany.setEndDate(calendar.getTime());
			//更新激活状态
			dao.update(conpany);
			//通过用户创建公司总组
			ConpanyGroup group=new ConpanyGroup();
			group.setConpanyId(conpany.getId());
			group.setCreateConpanyGroupUserId(conpanyUser.getId());
			group.setCreateConpanyGroupUserTrueName(conpanyUser.getTrueName());
			group.setGroupMarks("公司主组");
			group.setGroupName(conpany.getConpanyName());
			group.setUpLevelConpanyGroup(0);
			group.setUserNum(1);
			dao.add(group);
			//创建一个超级管理员角色
			Role role=new Role();
			role.setConpanyId(conpany.getId());
			role.setGroupid(group.getId());
			role.setGroupName(group.getGroupName());
			role.setMarks("系统管理员，拥有软件一切权限");
			role.setName("超级管理员");
			role.setConpanyAdminRole(true);
			dao.add(role);
			//把用户加入组内
			GroupConpanyLinkUser glu=new GroupConpanyLinkUser();
			glu.setConpanyId(conpany.getId());
			glu.setConpanyUserId(conpanyUser.getId());
			glu.setConpanyUserName(conpanyUser.getUsername());
			glu.setConpanyUserTrueName(conpanyUser.getTrueName());
			glu.setGroupId(group.getId());
			glu.setGroupName(group.getGroupName());
			dao.add(glu);
			//通过组，用户，角色，把用户和组连接在一起
			ConpanyUserLinkRole  culr=new ConpanyUserLinkRole();
			culr.setConpanyId(conpany.getId());
			culr.setConpanyUserName(conpanyUser.getUsername());
			culr.setConpanyUserTrueName(conpanyUser.getTrueName());
			culr.setGroupId(group.getId());
			culr.setRoleId(role.getId());
			culr.setRoleName(role.getName());
			culr.setUserid(conpanyUser.getId());
			dao.add(culr);
			//想角色内添加所有系统权限
			List plist=dao.getObjectList("SoftPermission", "");
			Iterator i=plist.iterator();
			while(i.hasNext()){
				SoftPermission s=(SoftPermission)i.next();
				SoftPermissionLinkConpanyRole splcr=new SoftPermissionLinkConpanyRole();
				splcr.setConpanyId(conpany.getId());
				splcr.setFunctionName(s.getFunctionName());
				splcr.setGroupId(group.getId());
				splcr.setRoleId(role.getId());
				splcr.setRoleName(role.getName());
				splcr.setSoftPermissionId(s.getId());
				dao.add(splcr);
			}
			map.put("success", true);
			map.put("info", "完成，您的账户名为:"+username+",也可以去邮箱内检查.");
			map.put("username",username);
			//email.sendInfoDateEmail(conpany.getConpanyAdminEmail(), EmailUtil.EmailContent("您的账户名为:"+username));
			WeiXinAutoReSendMenu menu=new WeiXinAutoReSendMenu();
			menu.setConpanyId(conpany.getId());
			menu.setType(WeiXinAutoReSendMenu.TYPE_EVENT);
			menu.setWeixin_events(String.valueOf(WeiXinAutoReSendMenu.EVENT_SUBSCRIBE));
			menu.setUses(true);
			menu.setWeixin_keys(1);
			menu.setName("关注时自动回复");
			dao.add(menu);
			WeiXinAutoReSendMenu menu2=new WeiXinAutoReSendMenu();
			menu2.setConpanyId(conpany.getId());
			menu2.setType(WeiXinAutoReSendMenu.TYPE_TEXT);
			menu2.setUses(true);
			menu2.setContent("1");
			menu2.setName("用户输入\"1\"时");
			dao.add(menu2);
			//大转盘
			WeiXinAutoReSendItem dazhuanpan7=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan8=new WeiXinAutoReSendItem();
			WeiXinReSend resend5=new WeiXinReSend();
			resend5.setConpanyId(conpany.getId());
			resend5.setDescription("当输入1时，将重复显示菜单");
			resend5.setName("【1】主页");
			resend5.setPicUrl("http://www.xt12306.com/img/zhuye.jpg");
			resend5.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId());
			resend5.setTitle("【1】主页");
			resend5.setType(6);
			dao.add(resend5);
			dazhuanpan7.setConpanyId(conpany.getId());
			dazhuanpan7.setAoturesendId(menu.getId());
			dazhuanpan7.setName(resend5.getName());
			dazhuanpan7.setResendid(resend5.getId());
			dao.add(dazhuanpan7);
			dazhuanpan8.setConpanyId(conpany.getId());
			dazhuanpan8.setAoturesendId(menu2.getId());
			dazhuanpan8.setName(resend5.getName());
			dazhuanpan8.setResendid(resend5.getId());
			dao.add(dazhuanpan8);
			WeiXinAutoReSendItem dazhuanpan=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan2=new WeiXinAutoReSendItem();
			WeiXinReSend resend2=new WeiXinReSend();
			resend2.setConpanyId(conpany.getId());
			resend2.setDescription("当输入1时，将重复显示菜单");
			resend2.setName("【2】大转盘");
			resend2.setPicUrl("http://www.xt12306.com/img/dazhuanpan.jpg");
			resend2.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#dazhuanpan");
			resend2.setTitle("【2】大转盘");
			resend2.setType(6);
			dao.add(resend2);
			dazhuanpan.setConpanyId(conpany.getId());
			dazhuanpan.setAoturesendId(menu.getId());
			dazhuanpan.setName(resend2.getName());
			dazhuanpan.setResendid(resend2.getId());
			dao.add(dazhuanpan);
			dazhuanpan2.setConpanyId(conpany.getId());
			dazhuanpan2.setAoturesendId(menu2.getId());
			dazhuanpan2.setName(resend2.getName());
			dazhuanpan2.setResendid(resend2.getId());
			dao.add(dazhuanpan2);
			//刮刮卡
			WeiXinAutoReSendItem dazhuanpan3=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan4=new WeiXinAutoReSendItem();
			WeiXinReSend resend3=new WeiXinReSend();
			resend3.setConpanyId(conpany.getId());
			resend3.setDescription("当输入1时，将重复显示菜单");
			resend3.setName("【3】刮刮卡");
			resend3.setPicUrl("http://www.xt12306.com/img/guaguaka.jpg");
			resend3.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#guaguaka");
			resend3.setTitle("【3】刮刮卡");
			resend3.setType(6);
			dao.add(resend3);
			dazhuanpan3.setConpanyId(conpany.getId());
			dazhuanpan3.setAoturesendId(menu.getId());
			dazhuanpan3.setName(resend3.getName());
			dazhuanpan3.setResendid(resend3.getId());
			dao.add(dazhuanpan3);
			dazhuanpan4.setConpanyId(conpany.getId());
			dazhuanpan4.setAoturesendId(menu2.getId());
			dazhuanpan4.setName(resend3.getName());
			dazhuanpan4.setResendid(resend3.getId());
			dao.add(dazhuanpan4);
			//会员
			WeiXinAutoReSendItem dazhuanpan5=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan6=new WeiXinAutoReSendItem();
			WeiXinReSend resend4=new WeiXinReSend();
			resend4.setConpanyId(conpany.getId());
			resend4.setDescription("当输入1时，将重复显示菜单");
			resend4.setName("【4】会员中心");
			resend4.setPicUrl("http://www.xt12306.com/img/youhuijuan.jpg");
			resend4.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#vip");
			resend4.setTitle("【4】会员中心");
			resend4.setType(6);
			dao.add(resend4);
			dazhuanpan5.setConpanyId(conpany.getId());
			dazhuanpan5.setAoturesendId(menu.getId());
			dazhuanpan5.setName(resend4.getName());
			dazhuanpan5.setResendid(resend4.getId());
			dao.add(dazhuanpan5);
			dazhuanpan6.setConpanyId(conpany.getId());
			dazhuanpan6.setAoturesendId(menu2.getId());
			dazhuanpan6.setName(resend4.getName());
			dazhuanpan6.setResendid(resend4.getId());
			dao.add(dazhuanpan6);
			
			WeiXinAutoReSendItem dazhuanpan9=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan10=new WeiXinAutoReSendItem();
			WeiXinReSend resend6=new WeiXinReSend();
			resend6.setConpanyId(conpany.getId());
			resend6.setDescription("当输入1时，将重复显示菜单");
			resend6.setName("【5】在线购物");
			resend6.setPicUrl("http://www.xt12306.com/img/zaixiangouwu.jpg");
			resend6.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#gouwu");
			resend6.setTitle("【5】在线购物");
			resend6.setType(6);
			dao.add(resend6);
			dazhuanpan9.setConpanyId(conpany.getId());
			dazhuanpan9.setAoturesendId(menu.getId());
			dazhuanpan9.setName(resend6.getName());
			dazhuanpan9.setResendid(resend6.getId());
			dao.add(dazhuanpan9);
			dazhuanpan10.setConpanyId(conpany.getId());
			dazhuanpan10.setAoturesendId(menu2.getId());
			dazhuanpan10.setName(resend6.getName());
			dazhuanpan10.setResendid(resend6.getId());
			dao.add(dazhuanpan10);
			WeiXinAutoReSendItem dazhuanpan11=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan12=new WeiXinAutoReSendItem();
			WeiXinReSend resend7=new WeiXinReSend();
			resend7.setConpanyId(conpany.getId());
			resend7.setDescription("当输入1时，将重复显示菜单");
			resend7.setName("【6】积分兑换");
			resend7.setPicUrl("http://www.xt12306.com/img/jifenduihuan.jpg");
			resend7.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#jifen");
			resend7.setTitle("【6】积分兑换");
			resend7.setType(6);
			dao.add(resend7);
			dazhuanpan11.setConpanyId(conpany.getId());
			dazhuanpan11.setAoturesendId(menu.getId());
			dazhuanpan11.setName(resend7.getName());
			dazhuanpan11.setResendid(resend7.getId());
			dao.add(dazhuanpan11);
			dazhuanpan12.setConpanyId(conpany.getId());
			dazhuanpan12.setAoturesendId(menu2.getId());
			dazhuanpan12.setName(resend7.getName());
			dazhuanpan12.setResendid(resend7.getId());
			dao.add(dazhuanpan12);
			WeiXinAutoReSendItem dazhuanpan13=new WeiXinAutoReSendItem();
			WeiXinAutoReSendItem dazhuanpan14=new WeiXinAutoReSendItem();
			WeiXinReSend resend8=new WeiXinReSend();
			resend8.setConpanyId(conpany.getId());
			resend8.setDescription("当输入1时，将重复显示菜单");
			resend8.setName("【7】到店导航");
			resend8.setPicUrl("http://www.xt12306.com/img/map.jpg");
			resend8.setUrl("http://www.xt12306.com/weixin/public/main?conpanyId="+conpany.getId()+"#map");
			resend8.setTitle("【7】到店导航");
			resend8.setType(6);
			dao.add(resend8);
			dazhuanpan13.setConpanyId(conpany.getId());
			dazhuanpan13.setAoturesendId(menu.getId());
			dazhuanpan13.setName(resend8.getName());
			dazhuanpan13.setResendid(resend8.getId());
			dao.add(dazhuanpan13);
			dazhuanpan14.setConpanyId(conpany.getId());
			dazhuanpan14.setAoturesendId(menu2.getId());
			dazhuanpan14.setName(resend8.getName());
			dazhuanpan14.setResendid(resend8.getId());
			dao.add(dazhuanpan14);
			//创建微信初始仓库，为免费用户准备
			StoreHouse sh=new StoreHouse();
			sh.setAddress("微信添加商品时自动存储此库存");
			sh.setConpanyId(conpany.getId());
			sh.setManagerUserId(conpanyUser.getId());
			sh.setManagerUserName(conpanyUser.getUsername());
			sh.setName("微信仓库");
			sh.setTal("无");
			dao.add(sh);
		}else{
			map.put("success", false);
			map.put("info", "该用户名已存在");
		}
		return map;
	}
	@RequestMapping(value = "/error")
	@ResponseBody
	public Map error(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,Object> map = (Map) req.getAttribute("map");
		return map;
	}
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		initPermission();
		return "index";
	}
	/**
	 * 查询最新消息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryMessage")
	@ResponseBody
	public Map queryMessage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		List<Message> listme=dao.getMessageNotRead(users.getId(), users.getConpanyId());
		map.put("num", listme.size());
		map.put("data", listme);
		map.put("success", true);
		return map;
	}
	/**
	 * 点击查看消息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/onclickMessage")
	@ResponseBody
	public Map onclickMessage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		Message mes=(Message) dao.getObject(Long.parseLong(id), "Message", users.getConpanyId());
		mes.setReades(true);
		dao.update(mes);
		map.put("obj", mes);
		map.put("success", true);
		if(mes.getTypes()==0){
			
		}
		if(mes.getTypes()==1){
			ChanceList cl=(ChanceList) dao.getObject(mes.getContentid(), "ChanceList",mes.getConpanyId());
			map.put("content1", cl);
		}
		return map;
	}
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		initPermission();
		return "/banckManager/weixin/mapIfram";
	}
	/**
	 * 点击查看消息
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getHangye")
	@ResponseBody
	public Map getHangye(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		List list=dao.getObjectList("Hangye", "");
		map.put("data", list);
		map.put("success", true);
		return map;
	}
	@RequestMapping(value = "/changePassword")
	public String changePassword(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		return "banckManager/passwordChange";
	}
	@RequestMapping(value = "/ajaxchangePassword")
	@ResponseBody
	public Map ajaxchangePassword(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map=new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String old=req.getParameter("old");
		String newp=req.getParameter("newp");
		String renewp=req.getParameter("renewp");
		try {
			if(MD5Util.validPasswd(old, users.getPassword())){
				if(newp.equals(renewp)){
					users.setPassword(MD5Util.getEncryptedPwd(newp));
					dao.update(users);
					map.put("info", "修改成功");
					map.put("success", true);
				}else{
					map.put("info", "新密码两次输入不一样");
					map.put("success", false);
				}
			}else{
				map.put("info", "老密码不正确");
				map.put("success", false);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
}
