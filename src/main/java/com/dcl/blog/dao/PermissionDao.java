package com.dcl.blog.dao;

import java.util.List;
import java.util.Map;

import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.Role;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.model.SoftPermissionLinkConpanyRole;

/**
 * 权限检查的接口
 * @author Administrator
 *
 */
public interface PermissionDao {
	/**
	 * 直接通过user对象和和组id获取该员工的所有角色所拥有的权限
	 * @param user
	 * @return
	 */
	public List<SoftPermission> getSoftPermissionByConpanyUser(ConpanyUser user,long groupid);
	/**
	 * 通过用户和组id获取角色
	 * @param user
	 * @return
	 */
	public List<Role> getRoleByConpanyUser(ConpanyUser user,long groupid);
	/**
	 * 通过角色获取权限
	 * @param user
	 * @return
	 */
	public List<SoftPermission> getSoftPermissionByRole(Role role,String name);
	/**
	 * 通过角色获取用户
	 * @param role
	 * @return
	 */
	public List<ConpanyUser> getConpanyUserByRole(Role role);
	/**
	 * 添加权限到角色
	 * @param permissionId
	 * @param roleid
	 */
	public Map<String,Object> addPermissionToRole(long permissionId,long roleid,long conpanyid);
	/**
	 * 从角色中删除权限
	 * @param permissionId
	 * @param roleid
	 */
	public Map<String,Object> deletePermissionToRole(long permissionId,long roleid,long conpanyid);
	/**
	 * 添加角色到用户
	 * @param roleid
	 * @param userid
	 */
	public Map<String,Object> addRoleToConpanyUser(long roleid,long userid,long conpanyid,long groupId);
	/**
	 * 从用户中删除某一角色
	 * @param roleid
	 * @param userid
	 */
	public Map<String,Object> deleteRoleToConpanyUser(long roleid,long userid,long conpanyid,long groupId);
	/**
	 * 添加用户到组
	 * @param userid
	 * @param groupid
	 */
	public Map<String,Object> addConpanyUserToGroup(long userid,long groupid,long conpanyid);
	/**
	 * 从组中删除用户
	 * @param userid
	 * @param groupid
	 */
	public Map<String,Object> deleteConpanyUserToGroup(long userid,long groupid,long conpanyid);
	/**
	 * 通过账号获取用户名
	 * @param username
	 * @return
	 */
	public ConpanyUser getConpanyUserByUserName(String username);
	/**
	 * 获取组的全部角色
	 */
	public List<Role> getConpanyGroupUserRole(long conpanyid,long groupId,String roleName);
	/**
	 * 删除角色--连带删除角色拥有的权限
	 */
	public List<SoftPermissionLinkConpanyRole> getRolelinkPermissionList(long groupId,long roleId);
	/**
	 * 删除组
	 */
	public Map<String,Object> deleteGroup(long groupId) throws Exception;
	/**
	 * 通过用户id获取所有用户参与的组
	 */
	public List<ConpanyGroup> getUserOfGroups(long userId);
	/**
	 * 通过用户id和组名获取所有用户参与的组
	 */
	public List<ConpanyGroup> getUserOfGroupsByUserIdAndGroupName(long userId,String groupName);
	/**
	 * 获取最高组--企业组对象
	 */
	public List<ConpanyGroup> getTopOneGroup(long conpanyId);
	/**
	 * 获取页面权限
	 */
	public List<SoftPermission> getPageSoftPermissions(long user,long conpanyId);
} 
