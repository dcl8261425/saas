package com.dcl.blog.dao.impl;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dcl.blog.dao.BaseHibernateDao;
import com.dcl.blog.dao.Dao;
import com.dcl.blog.model.Awards;
import com.dcl.blog.model.Conpany;
import com.dcl.blog.model.ConpanyAddress;
import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserLinkRole;
import com.dcl.blog.model.ConpanyUserMeeting;
import com.dcl.blog.model.Device;
import com.dcl.blog.model.Games;
import com.dcl.blog.model.GamesAwardsList;
import com.dcl.blog.model.GoodsLog;
import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.GroupConpanyLinkUser;
import com.dcl.blog.model.InOrderItem;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.Meeting;
import com.dcl.blog.model.Message;
import com.dcl.blog.model.MessageSend;
import com.dcl.blog.model.MessageSet;
import com.dcl.blog.model.NumLibs;
import com.dcl.blog.model.Orders;
import com.dcl.blog.model.OrdersItem;
import com.dcl.blog.model.Performance;
import com.dcl.blog.model.Role;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.model.SoftPermission;
import com.dcl.blog.model.SoftPermissionLinkConpanyRole;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.StoreHouseDateLog;
import com.dcl.blog.model.UserGamesNum;
import com.dcl.blog.model.VIPSet;
import com.dcl.blog.model.VWiFi;
import com.dcl.blog.model.WeiXin;
import com.dcl.blog.model.WeiXinAutoReSendItem;
import com.dcl.blog.model.WeiXinAutoReSendMenu;
import com.dcl.blog.model.WeiXinMenuTable;
import com.dcl.blog.model.WeiXinReSend;
import com.dcl.blog.model.WeiXinUser;
import com.dcl.blog.model.WeiXinWebHtml;
import com.dcl.blog.model.WindowLayout;
import com.dcl.blog.model.dto.MessageXML;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.DoubleUtil;
import com.dcl.blog.util.MapUtil;
import com.dcl.blog.util.MessageClient;

@Repository
public class DaoImpl extends BaseHibernateDao implements Dao {

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
		getSession().update(obj);
	}

	@Override
	public long add(Object obj) {
		// TODO Auto-generated method stub
		return (Long) getSession().save(obj);
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		getSession().delete(obj);
	}

	@Override
	public Object getObject(long id, String tableName) {
		// TODO Auto-generated method stub
		Object object = null;
		List list = getSession().createQuery(
				"from " + tableName + " where id=" + id).list();
		if (list.iterator().hasNext()) {
			object = list.iterator().next();
		}
		return object;
	}

	@Override
	public List<SoftPermission> getSoftPermissionByConpanyUser(
			ConpanyUser user, long groupid) {
		// TODO Auto-generated method stub
		/*
		 * List<SoftPermission> perlist=new ArrayList<SoftPermission>();
		 * //先查询用户的角色 List<ConpanyUserLinkRole>
		 * list=getSession().createQuery("from ConpanyUserLinkRole where userid="
		 * +user.getId()+" and groupId="+groupid).list();
		 * Iterator<ConpanyUserLinkRole> i=list.iterator(); while(i.hasNext()){
		 * ConpanyUserLinkRole r=i.next(); //在查询角色的权限
		 * List<SoftPermissionLinkConpanyRole> list2=getSession().createQuery(
		 * "from SoftPermissionLinkConpanyRole where roleId="
		 * +r.getRoleId()+" and groupId="+groupid).list();
		 * Iterator<SoftPermissionLinkConpanyRole> i2=list2.iterator();
		 * while(i2.hasNext()){ SoftPermissionLinkConpanyRole s=i2.next();
		 * //在查询权限，并把权限放入返回集合 perlist.add((SoftPermission)
		 * getObject(s.getSoftPermissionId(), "SoftPermission")); } }
		 */
		// 改用sql直接查询。下面的查询也要改
		// List<SoftPermission>
		// perlist=getSession().createQuery("from SoftPermission where id in( select softPermissionId from SoftPermissionLinkConpanyRole where roleId in (select roleId from ConpanyUserLinkRole where userid="+user.getId()+" and groupid="+groupid+"))").list();
		List<SoftPermission> perlist = new ArrayList<SoftPermission>();
		List<Object> objects = getSession()
				.createQuery(
						"select s.id,s.functionName,s.uplevel,s.url,s.marks from SoftPermission s, SoftPermissionLinkConpanyRole  sr,ConpanyUserLinkRole cur where s.id=sr.softPermissionId and sr.roleId=cur.roleId and cur.userid="
								+ user.getId()
								+ " and cur.groupId="
								+ groupid
								+ "").list();
		Iterator i = objects.iterator();
		while (i.hasNext()) {
			Object[] object = (Object[]) i.next();
			SoftPermission soft = new SoftPermission();
			soft.setId((Long) object[0]);
			soft.setFunctionName((String) object[1]);
			soft.setUplevel((Long) object[2]);
			soft.setUrl((String) object[3]);
			soft.setMarks((String) object[4]);
			perlist.add(soft);
		}
		return perlist;
	}

	@Override
	public List<Role> getRoleByConpanyUser(ConpanyUser user, long groupid) {
		// TODO Auto-generated method stub
		List<Role> rolelist = new ArrayList<Role>();
		// 通过用户获取角色
		List<ConpanyUserLinkRole> list = getSession().createQuery(
				"from ConpanyUserLinkRole where userid=" + user.getId()
						+ " and groupId=" + groupid + " and conpanyid="
						+ user.getConpanyId()).list();
		Iterator<ConpanyUserLinkRole> i2 = list.iterator();
		while (i2.hasNext()) {
			ConpanyUserLinkRole c = i2.next();
			// 获取角色放入列表
			rolelist.add((Role) getObject(c.getRoleId(), "Role"));
		}
		return rolelist;
	}

	@Override
	public List<SoftPermission> getSoftPermissionByRole(Role role, String name) {
		// TODO Auto-generated method stub
		List<SoftPermission> perlist = new ArrayList<SoftPermission>();
		// 通过用户获取角色
		List<SoftPermissionLinkConpanyRole> list2 = getSession().createQuery(
				"from SoftPermissionLinkConpanyRole where roleId="
						+ role.getId() + " and functionName like '%" + name
						+ "%'").list();
		Iterator<SoftPermissionLinkConpanyRole> i2 = list2.iterator();
		while (i2.hasNext()) {
			SoftPermissionLinkConpanyRole s = i2.next();
			// 在查询权限，并把权限放入返回集合
			perlist.add((SoftPermission) getObject(s.getSoftPermissionId(),
					"SoftPermission"));
		}
		return perlist;
	}

	@Override
	public List<ConpanyUser> getConpanyUserByRole(Role role) {
		// TODO Auto-generated method stub

		List<ConpanyUser> rolelist = null;
		// 通过用户获取角色
		List<ConpanyUserLinkRole> list = getSession().createQuery(
				"from ConpanyUserLinkRole where roleId=" + role.getId()).list();
		Iterator<ConpanyUserLinkRole> i2 = list.iterator();
		while (i2.hasNext()) {
			ConpanyUserLinkRole c = i2.next();
			// 获取角色放入列表
			rolelist.add((ConpanyUser) getObject(c.getUserid(), "ConpanyUser"));
		}
		return rolelist;
	}

	@Override
	public Map<String, Object> addPermissionToRole(long permissionId,
			long roleid, long conpanyid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String info = "";
		SoftPermission per = (SoftPermission) getObject(permissionId,
				"SoftPermission");
		Role role = (Role) getObject(roleid, "Role");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		if (per == null) {
			success = false;
			info = "权限不存在";
		}
		if (role == null) {
			success = false;
			info = "角色不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (role.getConpanyId() == con.getId()) {
				List<SoftPermissionLinkConpanyRole> slr = getSession()
						.createQuery(
								"from SoftPermissionLinkConpanyRole where roleId="
										+ role.getId()
										+ " and softPermissionId="
										+ per.getId() + " and conpanyId="
										+ con.getId()).list();
				Iterator<SoftPermissionLinkConpanyRole> i = slr.iterator();
				if (i.hasNext()) {
					success = false;
					info = "该角色已经拥有此权限，不可再次添加";
				} else {
					SoftPermissionLinkConpanyRole s = new SoftPermissionLinkConpanyRole();
					s.setConpanyId(con.getId());
					s.setFunctionName(per.getFunctionName());
					s.setRoleId(role.getId());
					s.setRoleName(role.getName());
					s.setGroupId(role.getGroupid());
					s.setSoftPermissionId(permissionId);
					add(s);
					success = true;
					info = "添加成功";
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public Map<String, Object> deletePermissionToRole(long permissionId,
			long roleid, long conpanyid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		SoftPermission per = (SoftPermission) getObject(permissionId,
				"SoftPermission");
		Role role = (Role) getObject(roleid, "Role");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		String info = "";
		if (per == null) {
			success = false;
			info = "权限不存在";
		}
		if (role == null) {
			success = false;
			info = "角色不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (role.getConpanyId() == con.getId()) {
				List<SoftPermissionLinkConpanyRole> slr = getSession()
						.createQuery(
								"from SoftPermissionLinkConpanyRole where roleId="
										+ role.getId()
										+ " and softPermissionId="
										+ per.getId() + " and conpanyId="
										+ con.getId()).list();
				Iterator<SoftPermissionLinkConpanyRole> i = slr.iterator();
				if (i.hasNext()) {
					if (role.isConpanyAdminRole()) {
						info = "主角色权限无法删除权限。";
						success = false;
					} else {
						SoftPermissionLinkConpanyRole sp = i.next();
						delete(sp);
						info = "删除成功";
						success = true;
					}
				} else {
					info = "该角色本身不拥有此权限，删除错误。";
					success = false;
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public Map<String, Object> addRoleToConpanyUser(long roleid, long userid,
			long conpanyid, long groupId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String info = "";
		ConpanyUser per = (ConpanyUser) getObject(userid, "ConpanyUser");
		Role role = (Role) getObject(roleid, "Role");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		if (per == null) {
			success = false;
			info = "用户不存在";
		}
		if (role == null) {
			success = false;
			info = "角色不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (role.getConpanyId() == con.getId()
					&& per.getConpanyId() == con.getId()) {
				List<ConpanyUserLinkRole> slr = getSession().createQuery(
						"from ConpanyUserLinkRole where groupId=" + groupId
								+ " and roleId=" + role.getId()
								+ " and userid=" + per.getId()
								+ " and conpanyId=" + con.getId()).list();
				Iterator<ConpanyUserLinkRole> i = slr.iterator();
				if (i.hasNext()) {
					success = false;
					info = "该用户已担任此角色，无法重复担任。";
				} else {
					ConpanyUserLinkRole s = new ConpanyUserLinkRole();
					s.setConpanyId(con.getId());
					s.setConpanyUserName(per.getUsername());
					s.setConpanyUserTrueName(per.getTrueName());
					s.setUserid(per.getId());
					s.setRoleId(role.getId());
					s.setRoleName(role.getName());
					s.setGroupId(groupId);
					add(s);
					success = true;
					info = "添加成功";
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public Map<String, Object> deleteRoleToConpanyUser(long roleid,
			long userid, long conpanyid, long groupId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String info = "";
		ConpanyUser per = (ConpanyUser) getObject(userid, "ConpanyUser");
		Role role = (Role) getObject(roleid, "Role");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		ConpanyGroup cg = (ConpanyGroup) getObject(role.getGroupid(),
				"ConpanyGroup", per.getConpanyId());
		if (role.isConpanyAdminRole() && per.isConpanyAdmin()
				&& cg.getUpLevelConpanyGroup() == 0) {
			success = false;
			info = "该管理员角色不可以和管理员取消管理关系。";
		}
		if (per == null) {
			success = false;
			info = "用户不存在";
		}
		if (role == null) {
			success = false;
			info = "角色不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (role.getConpanyId() == con.getId()
					&& per.getConpanyId() == con.getId()) {
				;

				List<ConpanyUserLinkRole> slr = getSession().createQuery(
						"from ConpanyUserLinkRole where groupId=" + groupId
								+ " and roleId=" + role.getId()
								+ " and userid=" + per.getId()
								+ " and conpanyid=" + con.getId()).list();

				Iterator<ConpanyUserLinkRole> i = slr.iterator();
				if (i.hasNext()) {
					ConpanyUserLinkRole ulr = i.next();
					if (role.isConpanyAdminRole()) {
						List<ConpanyUserLinkRole> slr1 = getSession()
								.createQuery(
										"from ConpanyUserLinkRole where groupId="
												+ groupId + " and roleId="
												+ role.getId()
												+ " and conpanyid="
												+ con.getId()).list();
						if (slr1.size() > 1) {
							delete(ulr);
							success = true;
							info = "删除成功";
						} else {
							success = false;
							info = "删除失败，因为当前组内所剩余组创建者仅一名了，若删除，则对此组无法控制。";
						}
					} else {
						delete(ulr);
						success = true;
						info = "删除成功";
					}
				} else {
					success = false;
					info = "该用户本身没有此角色不能取消担任。";
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public Map<String, Object> addConpanyUserToGroup(long userid, long groupid,
			long conpanyid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String info = "";
		ConpanyUser per = (ConpanyUser) getObject(userid, "ConpanyUser");
		ConpanyGroup group = (ConpanyGroup) getObject(groupid, "ConpanyGroup");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		if (per == null) {
			success = false;
			info = "用户不存在";
		}
		if (group == null) {
			success = false;
			info = "该组不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (group.getConpanyId() == con.getId()
					&& per.getConpanyId() == con.getId()) {
				List<GroupConpanyLinkUser> slr = getSession()
						.createQuery(
								"from GroupConpanyLinkUser where groupId="
										+ group.getId() + " and conpanyUserId="
										+ per.getId() + " and conpanyid="
										+ con.getId()).list();
				Iterator<GroupConpanyLinkUser> i = slr.iterator();
				if (i.hasNext()) {
					success = false;
					info = "该成员已经在该组";
				} else {
					GroupConpanyLinkUser s = new GroupConpanyLinkUser();
					s.setConpanyId(con.getId());
					s.setConpanyUserId(per.getId());
					s.setConpanyUserName(per.getUsername());
					s.setConpanyUserTrueName(per.getTrueName());
					s.setGroupId(group.getId());
					s.setGroupName(group.getGroupName());
					add(s);
					success = true;
					info = "添加成功";
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public Map<String, Object> deleteConpanyUserToGroup(long userid,
			long groupid, long conpanyid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String info = "";
		ConpanyUser per = (ConpanyUser) getObject(userid, "ConpanyUser");
		ConpanyGroup group = (ConpanyGroup) getObject(groupid, "ConpanyGroup");
		Conpany con = (Conpany) getObject(conpanyid, "Conpany");
		if (per == null) {
			success = false;
			info = "用户不存在";
		}
		if (group == null) {
			success = false;
			info = "该组不存在";
		}
		if (con == null) {
			success = false;
			info = "公司不存在";
		}
		if (success) {
			if (group.getConpanyId() == con.getId()
					&& per.getConpanyId() == con.getId()) {
				List<GroupConpanyLinkUser> slr = getSession()
						.createQuery(
								"from GroupConpanyLinkUser where groupId="
										+ group.getId() + " and conpanyUserId="
										+ per.getId() + " and conpanyid="
										+ con.getId()).list();
				Iterator<GroupConpanyLinkUser> i = slr.iterator();
				if (i.hasNext()) {
					delete(i.next());
					success = true;
					info = "成员已从改组移出";
				} else {
					success = false;
					info = "该组中没有此成员，删除失败。";
				}
			} else {
				success = false;
				info = "您提供的公司数据有误，无法添加，请查看是否登录超时，请重新登录在操作";
			}
		}
		map.put("success", success);
		map.put("info", info);
		return map;
	}

	@Override
	public ConpanyUser getConpanyUserByUserName(String username) {
		// TODO Auto-generated method stub
		List<ConpanyUser> list = getSession().createQuery(
				"from ConpanyUser where username='" + username + "'").list();
		ConpanyUser user = null;
		if (list.iterator().hasNext()) {
			user = list.iterator().next();
		}
		return user;
	}

	@Override
	public List<Object> getObjectList(String table, String where) {
		// TODO Auto-generated method stub
		return getSession().createQuery("from " + table + " " + where).list();
	}

	@Override
	public long getObjectListNum(String table, String where) {
		// TODO Auto-generated method stub
		return (Long) getSession()
				.createQuery("select count(*) from " + table + " " + where)
				.list().iterator().next();
	}

	@Override
	public List<Object> getObjectListPage(String table, String where,
			int nowpage, int countRow) {
		// TODO Auto-generated method stub
		return getSession().createQuery("from " + table + " " + where)
				.setFirstResult((nowpage - 1) * countRow)
				.setMaxResults(countRow).list();
	}

	@Override
	public List<Role> getConpanyGroupUserRole(long conpanyid, long groupId,
			String roleName) {
		List<Role> rolelist = new ArrayList<Role>();
		// 通过用户获取角色
		List<Role> list = getSession().createQuery(
				"from Role where " + "groupid=" + groupId + " and conpanyid="
						+ conpanyid + " and name like '%" + roleName + "%'")
				.list();
		return list;
	}

	@Override
	public List<SoftPermissionLinkConpanyRole> getRolelinkPermissionList(
			long groupId, long roleId) {
		// TODO Auto-generated method stub
		List<SoftPermissionLinkConpanyRole> rolelist = new ArrayList<SoftPermissionLinkConpanyRole>();
		// 通过用户获取角色
		List<SoftPermissionLinkConpanyRole> list = getSession().createQuery(
				"from SoftPermissionLinkConpanyRole where " + "groupId="
						+ groupId + " and roleId=" + roleId).list();
		return list;
	}

	@Override
	public Map<String, Object> deleteGroup(long groupId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyGroup group = (ConpanyGroup) getObject(groupId, "ConpanyGroup");
		if (group != null) {
			if (group.getUpLevelConpanyGroup() != 0) {
				// 删除角色权限列表
				getSession().createQuery(
						"delete from SoftPermissionLinkConpanyRole where groupId="
								+ groupId).executeUpdate();
				// 删除角色列表
				getSession().createQuery(
						"delete from Role where groupid=" + groupId)
						.executeUpdate();
				// 删除角色和用户关联列表
				getSession().createQuery(
						"delete from ConpanyUserLinkRole where groupId="
								+ groupId).executeUpdate();
				// 删除组和用户关联列表
				getSession().createQuery(
						"delete from GroupConpanyLinkUser where groupId="
								+ groupId).executeUpdate();
				// 删除组
				delete(group);
				map.put("info", "删除成功");
				map.put("success", true);
			} else {
				map.put("info", "出错不能删除最高公司组");
				map.put("success", false);
			}
		} else {
			map.put("info", "该组不存在。删除失败");
			map.put("success", false);
		}
		return map;
	}

	@Override
	public WindowLayout getWindowLayoutByUserIdAndUrl(long userId, String url) {
		// TODO Auto-generated method stub
		List<WindowLayout> list = getSession().createQuery(
				"from WindowLayout where " + "userid=" + userId + " and urls='"
						+ url + "'").list();
		Iterator<WindowLayout> i = list.iterator();
		WindowLayout w = null;
		while (i.hasNext()) {
			w = i.next();
		}
		return w;
	}

	@Override
	public List<WindowLayout> getWindowLayoutByUser(long userId, String mainUrls) {
		// TODO Auto-generated method stub
		List<WindowLayout> list = getSession().createQuery(
				"from WindowLayout where " + "userid=" + userId
						+ " and mainUrl='" + mainUrls + "'").list();

		return list;
	}

	@Override
	public List<ConpanyGroup> getUserOfGroups(long userId) {
		// TODO Auto-generated method stub
		List<GroupConpanyLinkUser> slr = getSession().createQuery(
				"from GroupConpanyLinkUser where conpanyUserId=" + userId)
				.list();
		List<ConpanyGroup> list = new ArrayList<ConpanyGroup>();
		Iterator<GroupConpanyLinkUser> i = slr.iterator();
		while (i.hasNext()) {
			list.add((ConpanyGroup) getObject(i.next().getGroupId(),
					"ConpanyGroup"));
		}
		return list;
	}

	@Override
	public List<ConpanyGroup> getUserOfGroupsByUserIdAndGroupName(long userId,
			String groupName) {
		// TODO Auto-generated method stub
		List<GroupConpanyLinkUser> slr = getSession().createQuery(
				"from GroupConpanyLinkUser where conpanyUserId=" + userId
						+ " and groupName like '%" + groupName + "%'").list();
		List<ConpanyGroup> list = new ArrayList<ConpanyGroup>();
		Iterator<GroupConpanyLinkUser> i = slr.iterator();
		while (i.hasNext()) {
			list.add((ConpanyGroup) getObject(i.next().getGroupId(),
					"ConpanyGroup"));
		}
		return list;
	}

	@Override
	public List<Object> getObjectListBySql(String sql) {
		// TODO Auto-generated method stub
		return getSession().createQuery(sql).list();
	}
	@Override
	public List<Object> getObjectListByeSql(String sql) {
		// TODO Auto-generated method stub
		return getSession().createSQLQuery(sql).addEntity(ConpanyGroup.class).list();
	}
	@Override
	public void deleteList(String sql, String tableName) {
		// TODO Auto-generated method stub
		getSession().createSQLQuery("DELETE FROM " + tableName + sql);
	}

	@Override
	public GoodsTable addGoods(GoodsTable goods) {
		// TODO Auto-generated method stub
		boolean b = true;
		try {
			List GoodsList = null;
					if(goods.getCodeid()==null||goods.getCodeid().equals("")){
						GoodsList=getSession().createQuery(
								"from GoodsTable where goodsName='" + goods.getGoodsName()
										+ "' and goodsModel='" + goods.getGoodsModel()
										+ "' and goodsType='" + goods.getGoodsType()
										+ "' and conpanyId=" + goods.getConpanyId()).list();
					}else{
						GoodsList=getSession().createQuery(
								"from GoodsTable where goodsName='" + goods.getGoodsName()
										+ "' and goodsModel='" + goods.getGoodsModel()
										+ "' and goodsType='" + goods.getGoodsType()
										+ "' and conpanyId=" + goods.getConpanyId()
										+ " and codeid='"+goods.getCodeid()+"'").list();
						if(GoodsList.size()<=0){
							GoodsList=getSession().createQuery(
									"from GoodsTable where "
											+ "conpanyId=" + goods.getConpanyId()
											+ " and codeid='"+goods.getCodeid()+"'").list();
							if(GoodsList.size()<=0){
								GoodsList=getSession().createQuery(
										"from GoodsTable where goodsName='" + goods.getGoodsName()
												+ "' and goodsModel='" + goods.getGoodsModel()
												+ "' and goodsType='" + goods.getGoodsType()
												+ "' and conpanyId=" + goods.getConpanyId()).list();
							}else{
								GoodsList=getSession().createQuery(
										"from GoodsTable where goodsName='" + goods.getGoodsName()
												+ "' and goodsModel='" + goods.getGoodsModel()
												+ "' and goodsType='" + goods.getGoodsType()
												+ "' and conpanyId=" + goods.getConpanyId()).list();
								goods.setCodeid("");
							}
						}
					}
			Iterator i = GoodsList.iterator();
			if (i.hasNext()) {
				GoodsTable goos = (GoodsTable) i.next();
				double num = goos.getGoodsNum();
				goos.setGoodsNum(DoubleUtil.round(
						DoubleUtil.add(goos.getGoodsNum(), goods.getGoodsNum()),
						5));
				goos.setInPrice(DoubleUtil.round(DoubleUtil.div(
						DoubleUtil.add(
								(DoubleUtil.mul(goos.getInPrice(), num)),
								(DoubleUtil.mul(goods.getInPrice(),
										goods.getGoodsNum()))), goos
								.getGoodsNum()==0?1:goos
										.getGoodsNum(), 5), 5));
				goos.setTotalInPrice(DoubleUtil.round(
						DoubleUtil.add(goos.getTotalInPrice(),
								goods.getTotalInPrice()), 5));
				goos.setTotalPrice(DoubleUtil.round(
						DoubleUtil.add(goos.getTotalPrice(),
								goods.getTotalPrice()), 5));
				if(goods.getCodeid()!=null&&!goods.getCodeid().trim().equals("")){
					goos.setCodeid(goods.getCodeid());
				}
				update(goos);
				return goos;
			} else {
				add(goods);
				return goods;
			}
		} catch (Exception e) {
			e.printStackTrace();
			goods = null;
		}
		return goods;
	}

	@Override
	public List getSalesData(String startDate, String endDate, long goodsId,
			long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession()
				.createQuery(
						"select sum(salesNum) as num,goodsName,goodsId,chanceId,chanceName from GoodsLog where chanceId in(select DISTINCT chanceId from GoodsLog where chanceId!=0 and action="
								+ GoodsLog.ACTION_REDUCE
								+ " and conpanyId="
								+ conpanyId
								+ ") and goodsId="
								+ goodsId
								+ " and startdate between '"
								+ startDate
								+ "' and '"
								+ endDate
								+ "' and conpanyId="
								+ conpanyId + " group by chanceId").list();
		return list;
	}

	@Override
	public List getInGoodsData(String startDate, String endDate, long goodsId,
			long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession()
				.createQuery(
						"select sum(goodsNum) as num,goodsName,goodsId,goodsSourceId,goodsSourceName from GoodsLog where goodsSourceId in(select DISTINCT goodsSourceId from GoodsLog where goodsSourceId!=0 and action="
								+ GoodsLog.ACTION_ADD
								+ " and conpanyId="
								+ conpanyId
								+ ") and goodsId="
								+ goodsId
								+ " and startdate between '"
								+ startDate
								+ "' and '"
								+ endDate
								+ "' and conpanyId="
								+ conpanyId + " group by goodsSourceId").list();
		return list;
	}

	@Override
	public List getGoodsPriceAndInPrice(String startDate, String endDate,
			long goodsId, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"select sum(salesNum),price,goodsinPrice from GoodsLog where goodsId="
						+ goodsId + " and startdate between '" + startDate
						+ "' and '" + endDate + "' and action="
						+ GoodsLog.ACTION_REDUCE + " and conpanyId="
						+ conpanyId + " group by goodsinPrice,price").list();
	}

	@Override
	public StoreHouseDateLog getStoreHouseDateLog(long goodsid,
			long storeHouseId, long conpanyId) {
		// TODO Auto-generated method stub
		StoreHouseDateLog log = null;
		List logs = getSession().createQuery(
				"from StoreHouseDateLog where storeHouseId=" + storeHouseId
						+ " and goodsId=" + goodsid + " and conpanyId="
						+ conpanyId).list();
		if (logs.size() > 0) {
			log = (StoreHouseDateLog) logs.iterator().next();
		} else {
			GoodsTable g = (GoodsTable) getObject(goodsid, "GoodsTable");
			StoreHouse s = (StoreHouse) getObject(storeHouseId, "StoreHouse");
			if (g != null && s != null) {
				log = new StoreHouseDateLog();
				log.setConpanyId(g.getConpanyId());
				log.setGoodsId(g.getId());
				log.setGoodsName(g.getGoodsName());
				log.setNum(0);
				log.setCountnum(0);
				log.setStoreHoseName(s.getName());
				log.setStoreHouseId(s.getId());
				add(log);
			}
		}
		return log;
	}

	@Override
	public List<StoreHouseDateLog> getStoreHouseDateLogChar(long goodsId,
			long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from StoreHouseDateLog where goodsId=" + goodsId
						+ " and conpanyId=" + conpanyId).list();
	}

	@Override
	public void deleteInOrder(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		getSession().createQuery(
				"delete InOrderItem where inOrderId=" + orderId
						+ " and conpanyId=" + conpanyId).executeUpdate();
	}

	@Override
	public GoodsTable getGoods(String name, String type, String model,
			long conpanyId) {
		// TODO Auto-generated method stub
		List GoodsList = getSession().createQuery(
				"from GoodsTable where goodsName='" + name
						+ "' and goodsModel='" + model + "' and goodsType='"
						+ type + "' and conpanyId=" + conpanyId).list();
		if (GoodsList.size() > 0) {
			return (GoodsTable) GoodsList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Object getObject(long id, String tableName, long conpanyId) {
		// TODO Auto-generated method stub
		Object object = null;
		List list = getSession().createQuery(
				"from " + tableName + " where id=" + id + "and conpanyId="
						+ conpanyId).list();
		if (list.iterator().hasNext()) {
			object = list.iterator().next();
		}
		return object;
	}

	@Override
	public List<InOrderItem> getInOrderItems(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from InOrderItem where inOrderId=" + orderId
						+ "and conpanyId=" + conpanyId).list();
		return list;
	}

	@Override
	public List<OrdersItem> getOrderItems(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from OrdersItem where inOrderId=" + orderId + "and conpanyId="
						+ conpanyId).list();
		return list;
	}

	@Override
	public GoodsTable addReduceGoods(GoodsTable goods) {
		// TODO Auto-generated method stub
		boolean b = true;
		try {
			List GoodsList = getSession().createQuery(
					"from GoodsTable where goodsName='" + goods.getGoodsName()
							+ "' and goodsModel='" + goods.getGoodsModel()
							+ "' and goodsType='" + goods.getGoodsType()
							+ "' and conpanyId=" + goods.getConpanyId()).list();
			Iterator i = GoodsList.iterator();
			if (i.hasNext()) {
				GoodsTable goos = (GoodsTable) i.next();
				double num = goos.getGoodsNum();
				goos.setGoodsNum(goos.getGoodsNum() - goods.getGoodsNum());
				goos.setTotalInPrice(goos.getTotalInPrice()
						- goods.getTotalInPrice());
				goos.setTotalPrice(goos.getTotalPrice() - goods.getTotalPrice());
				goos.setSalesNum(goos.getSalesNum() + goods.getGoodsNum());
				update(goos);
				return goos;
			} else {
				goods.setGoodsNum(0);
				goods.setSalesNum(goods.getSalesNum());
				goods.setTotalInPrice(0);
				goods.setTotalPrice(0);
				add(goods);
				return goods;
			}
		} catch (Exception e) {
			e.printStackTrace();
			goods = null;
		}
		return goods;
	}

	@Override
	public void deleteOrder(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		getSession().createQuery(
				"delete OrdersItem where inOrderId=" + orderId
						+ " and conpanyId=" + conpanyId).executeUpdate();
	}

	@Override
	public GoodsSource getGoodsSource(String storeName, long conpanyId,
			boolean iscreate) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from GoodsSource where name='" + storeName
						+ "' and conpanyId=" + conpanyId).list();
		GoodsSource sh = null;
		if (list.size() != 0) {
			sh = (GoodsSource) list.get(0);
		} else {
			if (iscreate) {
				sh = new GoodsSource();
				sh.setConpanyId(conpanyId);
				sh.setName(storeName);
				sh.setAddress("");
				add(sh);
			}
		}
		return sh;
	}

	@Override
	public List<ConpanyGroup> getTopOneGroup(long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from ConpanyGroup where upLevelConpanyGroup=0 and conpanyId="
						+ conpanyId).list();
		return list;
	}

	@Override
	public List<ConpanyUser> getConpanyUser(long conpanyId, String trueName) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from ConpanyUser where trueName like '%" + trueName
						+ "%' and conpanyId=" + conpanyId).list();
		return list;
	}

	@Override
	public Long getConpanyUserNum(long conpanyId, String trueName) {
		// TODO Auto-generated method stub
		return (Long) getSession()
				.createQuery(
						"select count(*) from ConpanyUser where conpanyId="
								+ conpanyId + " and trueName like '%"
								+ trueName + "%'").list().iterator().next();
	}

	@Override
	public List<ConpanyUser> getConpanyUserPage(long conpanyId,
			String trueName, int nowPage, int countRow) {
		// TODO Auto-generated method stub

		return getSession()
				.createQuery(
						"from ConpanyUser where conpanyId=" + conpanyId
								+ " and trueName like '%" + trueName + "%'")
				.setFirstResult((nowPage - 1) * countRow)
				.setMaxResults(countRow).list();
	}

	@Override
	public List<ConpanyUserMeeting> getConpanUserMeetingByDate(String date,
			long conpanyId) {
		// TODO Auto-generated method stub
		Date end = DateUtil.toDateType(date);
		Calendar calend = Calendar.getInstance();
		calend.setTime(end);
		calend.add(Calendar.DAY_OF_MONTH, 1);
		String date2 = DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		return getSession().createQuery(
				"from ConpanyUserMeeting where createDate between '" + date
						+ "' and '" + date2 + "' and conpanyId=" + conpanyId)
				.list();
	}

	@Override
	public List<ConpanyUserMeeting> getConpanUserMeetingByDateAndUser(
			String date, long userId, long conpanyId) {
		// TODO Auto-generated method stub
		String nowDat = DateUtil.formatDateYYYY_MM_DD(new Date());
		return getSession().createQuery(
				"from ConpanyUserMeeting where createDate between '" + nowDat
						+ "' and '" + date + "' and conpanyId=" + conpanyId
						+ " and userId=" + userId).list();
	}

	@Override
	public boolean getCreateConpanyCusteemerMeeting(long conpanyId) {
		// TODO Auto-generated method stub
		Calendar calend = Calendar.getInstance();
		calend.setTime(new Date());
		calend.add(Calendar.DAY_OF_MONTH, 1);
		String startDate = DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		String endDate = DateUtil.formatDateYYYY_MM_DD(new Date());
		List list = getSession().createQuery(
				"from ConpanyUserMeeting where createDate between '" + endDate
						+ "' and '" + startDate + "' and conpanyId="
						+ conpanyId).list();
		if (list.size() <= 0) {
			List<ConpanyUser> listUser = getSession().createQuery(
					"from ConpanyUser where conpanyId=" + conpanyId).list();
			for (int i = 0; i < listUser.size(); i++) {
				ConpanyUser user = listUser.get(i);
				ConpanyUserMeeting meet = new ConpanyUserMeeting();
				meet.setCreateDate(new Date());
				meet.setConpanyId(conpanyId);
				meet.setConpanyUsertrueName(user.getTrueName());
				meet.setStute(0);
				meet.setStuteMarks("");
				meet.setUserId(user.getId());
				add(meet);
			}
		}
		return true;
	}

	@Override
	public List<Meeting> getMeetingListTopTen(long conpanyId) {
		// TODO Auto-generated method stub
		List list = getSession()
				.createQuery(
						"from Meeting where conpanyId=" + conpanyId
								+ " order by managerDate desc")
				.setFirstResult(0).setMaxResults(10).list();
		return list;
	}

	@Override
	public Meeting getMeetingListTop(long conpanyId) {
		// TODO Auto-generated method stub
		Meeting me = null;
		List list = getSession()
				.createQuery(
						"from Meeting where conpanyId=" + conpanyId
								+ " order by managerDate desc")
				.setFirstResult(0).setMaxResults(1).list();
		if (list.size() > 0) {
			me = (Meeting) list.get(0);
		}
		return me;
	}

	@Override
	public Performance getPerformanceByUser(long userId, long conpanyId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		Performance pf = null;
		Calendar calend = Calendar.getInstance();
		calend.setTime(DateUtil.toDateType(endDate));
		calend.add(Calendar.DAY_OF_MONTH, 1);
		endDate = DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		List list = getSession()
				.createQuery(
						"select sum(meetingNum),sum(myCreateChanceBuyCountPrice),sum(myCreateChanceBuyNum),sum(myCreateChanceNum),sum(toChanceBuyCountPrice),sum(toChanceBuyNum),sum(toChanceNum),userId,userTrueName,sum(meetingOutNum),sum(meetingLastNum),sum(meetingLeave),sum(meetingNumOut) from Performance where conpanyId="
								+ conpanyId
								+ " and userId="
								+ userId
								+ " and createDate between '"
								+ startDate
								+ "' and '" + endDate + "' group by userId")
				.list();
		if (list.size() > 0) {
			pf = new Performance();
			Object[] onjs = (Object[]) list.get(0);
			pf.setMeetingNum((Long) onjs[0]);
			pf.setMyCreateChanceBuyCountPrice((Double) onjs[1]);
			pf.setMyCreateChanceBuyNum((Long) onjs[2]);
			pf.setMyCreateChanceNum((Long) onjs[3]);
			pf.setToChanceBuyCountPrice((Double) onjs[4]);
			pf.setToChanceBuyNum((Long) onjs[5]);
			pf.setToChanceNum((Long) onjs[6]);
			pf.setUserId((Long) onjs[7]);
			pf.setUserTrueName((String) onjs[8]);
			pf.setMeetingOutNum((Long) onjs[9]);
			pf.setMeetingLastNum((Long) onjs[10]);
			pf.setMeetingLeave((Long) onjs[11]);
			pf.setMeetingNumOut((Long) onjs[12]);
		}
		return pf;
	}

	@Override
	public List<Performance> getPerformanceByAll(long conpanyId,
			String startDate, String endDate) {
		List<Performance> listp = new ArrayList<Performance>();
		Calendar calend = Calendar.getInstance();
		calend.setTime(DateUtil.toDateType(endDate));
		calend.add(Calendar.DAY_OF_MONTH, 1);
		endDate = DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		// TODO Auto-generated method stub
		List list = getSession()
				.createQuery(
						"select sum(meetingNum),sum(myCreateChanceBuyCountPrice),sum(myCreateChanceBuyNum),sum(myCreateChanceNum),sum(toChanceBuyCountPrice),sum(toChanceBuyNum),sum(toChanceNum),userId,userTrueName,sum(meetingOutNum),sum(meetingLastNum),sum(meetingLeave),sum(meetingNumOut) from Performance where conpanyId="
								+ conpanyId
								+ " and createDate between '"
								+ startDate
								+ "' and '"
								+ endDate
								+ "' group by userId").list();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			Performance pf = new Performance();
			Object[] onjs = (Object[]) i.next();
			pf.setMeetingNum((Long) onjs[0]);
			pf.setMyCreateChanceBuyCountPrice((Double) onjs[1]);
			pf.setMyCreateChanceBuyNum((Long) onjs[2]);
			pf.setMyCreateChanceNum((Long) onjs[3]);
			pf.setToChanceBuyCountPrice((Double) onjs[4]);
			pf.setToChanceBuyNum((Long) onjs[5]);
			pf.setToChanceNum((Long) onjs[6]);
			pf.setUserId((Long) onjs[7]);
			pf.setUserTrueName((String) onjs[8]);
			pf.setMeetingOutNum((Long) onjs[9]);
			pf.setMeetingLastNum((Long) onjs[10]);
			pf.setMeetingLeave((Long) onjs[11]);
			pf.setMeetingNumOut((Long) onjs[12]);
			listp.add(pf);
		}
		return listp;
	}

	@Override
	public Performance getPerformanceByToDayUser(long conpanyId, long userId,
			String userTrueName) {
		// TODO Auto-generated method stub
		Performance pf = null;
		Calendar calend = Calendar.getInstance();
		calend.setTime(new Date());
		calend.add(Calendar.DAY_OF_MONTH, 1);
		String endDate = DateUtil.formatDateYYYY_MM_DD(calend.getTime());
		String startDate = DateUtil.formatDateYYYY_MM_DD(new Date());
		List list = getSession().createQuery(
				"from Performance where conpanyId=" + conpanyId
						+ " and userId=" + userId + " and createDate between '"
						+ startDate + "' and '" + endDate + "'").list();
		if (list.size() > 0) {
			pf = (Performance) list.get(0);
		} else {
			pf = new Performance();
			pf.setConpanyId(conpanyId);
			pf.setUserId(userId);
			pf.setUserTrueName(userTrueName);
			pf.setCreateDate(new Date());
			pf.setMeetingNum(0);
			pf.setMyCreateChanceBuyCountPrice(0);
			pf.setMyCreateChanceBuyNum(0);
			pf.setMyCreateChanceNum(0);
			pf.setToChanceBuyCountPrice(0);
			pf.setToChanceBuyNum(0);
			pf.setToChanceNum(0);
			pf.setMeetingLastNum(0);
			pf.setMeetingLeave(0);
			pf.setMeetingOutNum(0);
			add(pf);
		}
		return pf;
	}

	@Override
	public List<SoftPermission> getPageSoftPermissions(long user, long conpanyId) {
		// TODO Auto-generated method stub
		List<SoftPermission> perlist = getSession()
				.createQuery(
						"from SoftPermission where id in( select softPermissionId from SoftPermissionLinkConpanyRole where roleId in (select roleId from ConpanyUserLinkRole where userid="
								+ user
								+ ")) and upLevel=(select id from SoftPermission where url='/page')")
				.list();
		return perlist;
	}

	@Override
	public List<Message> getMessage(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		List<Message> perlist = getSession().createQuery(
				"from Message where userid=" + userid + " and conpanyId="
						+ conpanyid).list();
		return perlist;
	}

	@Override
	public List<Message> getMessageNotRead(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		List<Message> perlist = getSession().createQuery(
				"from Message where userid=" + userid + " and conpanyId="
						+ conpanyid + " and reades=false").list();
		return perlist;
	}

	@Override
	public List<Message> getMessageRead(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		List<Message> perlist = getSession().createQuery(
				"from Message where userid=" + userid + " and conpanyId="
						+ conpanyid + " and reades=true").list();
		return perlist;
	}

	@Override
	public List<WeiXin> queryWeiXin(long conpanyId) {
		// TODO Auto-generated method stub
		List<WeiXin> list;
		list = getSession().createQuery(
				"from WeiXin where conpanyId=" + conpanyId + "").list();
		return list;
	}

	@Override
	public List<WeiXinMenuTable> queryWeiXinMenu(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinMenuTable where pid=" + id + " and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<WeiXinMenuTable> queryWeiXinMenuById(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinMenuTable where id=" + id + " and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<WeiXinUser> queryWeiXinUser(String name, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinUser where nickname like '%" + name
						+ "' and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinUser> queryWeiXinUser(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinUser where id = " + id + " and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinImage(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.IMAGE
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinVoice(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.VOICE
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinVideo(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.VIDEO
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinText(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.TEXT
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinMusic(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.MUSIC
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> queryWeiXinImageText(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where type = " + WeiXinReSend.IMAGE_TEXT
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinReSend> getWeiXinReSendById(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinReSend where id = " + id + " and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItemById(long id,
			long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinAutoReSendItem where id = " + id
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItem(
			long aoturesendId, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinAutoReSendItem where aoturesendId = "
						+ aoturesendId + " and conpanyId=" + conpanyId + "")
				.list();
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,
			String Content, long conpanyId) {
		// TODO Auto-generated method stub
		String sql = "from WeiXinAutoReSendMenu where type = " + type
				+ " and conpanyId=" + conpanyId + "";
		if (type == WeiXinAutoReSendMenu.TYPE_EVENT) {
			sql = sql + " and weixin_events='" + Content + "'";
		} else {
			sql = sql + " and content='" + Content + "'";
		}
		// TODO Auto-generated method stub
		return getSession().createQuery(sql).list();
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuIsUse(long type,
			String Content, long conpanyId) {
		String sql = "from WeiXinAutoReSendMenu where uses=true and type = "
				+ type + " and conpanyId=" + conpanyId + "";
		if (type == WeiXinAutoReSendMenu.TYPE_EVENT) {
			sql = sql + " and weixin_events='" + Content + "'";
		} else {
			sql = sql + " and content='" + Content + "'";
		}
		// TODO Auto-generated method stub
		return getSession().createQuery(sql).list();
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuById(long id,
			long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from WeiXinAutoReSendMenu where id = " + id
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,
			long conpanyId) {
		// TODO Auto-generated method stub
		String sql = "from WeiXinAutoReSendMenu where type = " + type
				+ " and conpanyId=" + conpanyId + "";
		// TODO Auto-generated method stub
		return getSession().createQuery(sql).list();
	}

	@Override
	public Games getGames(long id, long conpanyId) {
		// TODO Auto-generated method stub
		List<Games> list = getSession().createQuery(
				"from Games where id=" + id + " and conpanyId=" + conpanyId
						+ "").list();
		Games game = null;
		if (list.size() > 0) {
			game = list.iterator().next();
		}
		return game;
	}

	@Override
	public List<Awards> getAwards(long conpanyId) {
		// TODO Auto-generated method stub
		List<Awards> list = getSession().createQuery(
				"from Awards where conpanyId=" + conpanyId + "").list();
		return list;
	}

	@Override
	public List<GamesAwardsList> getGamesAwardsList(long gameid, long conpanyId) {
		// TODO Auto-generated method stub
		List<GamesAwardsList> list = getSession().createQuery(
				"from GamesAwardsList where gamesid=" + gameid
						+ " and conpanyId=" + conpanyId + "").list();
		return list;
	}

	@Override
	public List<NumLibs> getNAwardsNum(String num, long conpanyId) {
		// TODO Auto-generated method stub
		List<NumLibs> list = getSession().createQuery(
				"from NumLibs where xuliehao='" + num + "' and conpanyId="
						+ conpanyId + "").list();
		return list;
	}

	@Override
	public List<Games> getUseGamesList(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from Games where uses=true and conpanyId=" + conpanyId + "")
				.list();
	}

	@Override
	public List<Games> getGamesList(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from Games where conpanyId=" + conpanyId + "").list();
	}

	@Override
	public UserGamesNum getUserGamesNum(long gameid, long userid, long conpanyId) {
		// TODO Auto-generated method stub
		List<UserGamesNum> list = getSession().createQuery(
				"from UserGamesNum where gameId=" + gameid + " and userid="
						+ userid + " and conpanyId=" + conpanyId + "").list();
		UserGamesNum game = null;
		if (list.size() > 0) {
			game = list.iterator().next();
		}
		return game;
	}

	@Override
	public Games getGames(String name, long conpanyId) {
		// TODO Auto-generated method stub
		List<Games> list = getSession().createQuery(
				"from Games where name='" + name + "'" + " and conpanyId="
						+ conpanyId + "").list();
		Games game = null;
		if (list.size() > 0) {
			game = list.iterator().next();
		}
		return game;
	}

	@Override
	public List<VIPSet> getVipSetList(long conpanyId) {
		// TODO Auto-generated method stub
		List<VIPSet> list = getSession().createQuery(
				"from VIPSet where conpanyId=" + conpanyId + "").list();
		return list;
	}

	@Override
	public List<ScoreDuihuan> getScoreDuihuansList(long conpanyId, int nowpage,
			int countNum) {
		// TODO Auto-generated method stub
		List<ScoreDuihuan> list = getSession()
				.createQuery(
						"from ScoreDuihuan where conpanyId=" + conpanyId + "")
				.setFirstResult((nowpage - 1) * countNum)
				.setMaxResults(countNum).list();
		return list;
	}

	@Override
	public ScoreToGoodsList getScoreNum(String num, long conpanyId) {
		// TODO Auto-generated method stub
		List<ScoreToGoodsList> list = getSession().createQuery(
				"from ScoreToGoodsList where xuliehao='" + num
						+ "' and conpanyId=" + conpanyId + "").list();
		ScoreToGoodsList good = null;
		if (list.size() > 0) {
			good = list.iterator().next();
		}
		return good;
	}

	@Override
	public List<ScoreToGoodsList> getUserScoreDuihuan(long userid,
			long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from ScoreToGoodsList where userid=" + userid
						+ " and conpanyId=" + conpanyId + "").list();
	}

	@Override
	public List<NumLibs> getUserNumLibs(long userid, long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from NumLibs where userid=" + userid + " and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<LinkManList> queryUserModelByOpenid(String openid,
			long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from LinkManList where openid='" + openid + "' and conpanyId="
						+ conpanyId + "").list();
	}

	@Override
	public List<WeiXinWebHtml> queryWeixinWebHtml(String name, long conpanyId,
			int nowpage, int countNum) {
		// TODO Auto-generated method stub
		return getSession()
				.createQuery(
						"from WeiXinWebHtml where name like '%" + name
								+ "%' and conpanyId=" + conpanyId + "")
				.setFirstResult((nowpage - 1) * countNum)
				.setMaxResults(countNum).list();
	}

	@Override
	public Long queryWeixinWebHtml(String name, long conpanyId) {
		// TODO Auto-generated method stub
		return (Long) getSession()
				.createQuery(
						"select count(*) from WeiXinWebHtml where conpanyId="
								+ conpanyId + " and name like '%" + name + "%'")
				.list().iterator().next();
	}

	@Override
	public List<ConpanyAddress> getConpanyAddressByConpanyId(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from ConpanyAddress where conpanyId =" + conpanyId).list();
	}

	@Override
	public List<ConpanyAddress> getConpanyAddressByXY(String x, String y,
			int nowpage, int countNum) {
		// TODO Auto-generated method stub
		return getSession().createQuery(MapUtil.Distance(x, y))
				.setFirstResult((nowpage - 1) * countNum)
				.setMaxResults(countNum).list();
	}

	@Override
	public VWiFi getVWifi(long conpanyId, String tokens) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from VWiFi where conpanyId=" + conpanyId + " and tokens='"
						+ tokens + "'").list();
		if (list.iterator().hasNext()) {
			return (VWiFi) list.iterator().next();
		} else {
			return null;
		}

	}

	@Override
	public List<VWiFi> getConpanyVWifi(long conpanyId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from VWiFi where conpanyId=" + conpanyId).list();
	}

	@Override
	public List<Device> getDeviceByVWifi(long conpanyId, String tokens) {
		// TODO Auto-generated method stub
		return getSession().createQuery(
				"from Device where conpanyId=" + conpanyId + " and tokens='"
						+ tokens + "'").list();
	}

	@Override
	public Device getDevice(long conpanyId, String tokens, String mac) {
		// TODO Auto-generated method stub
		VWiFi wifi = getVWifi(conpanyId, tokens);
		if (wifi != null) {
			List list = getSession().createQuery(
					"from Device where conpanyId=" + conpanyId + " and ap_id='"
							+ wifi.getId() + "'" + " and mac='" + mac + "'")
					.list();
			if (list.iterator().hasNext()) {
				return (Device) list.iterator().next();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public MessageSet getMessageSet(long conpanyId) {
		// TODO Auto-generated method stub
		List<MessageSet> list = getSession().createQuery(
				"from MessageSet where conpanyId=" + conpanyId).list();
		MessageSet me = null;
		if (list.size() > 0) {
			me = list.iterator().next();
		} else {
			me = new MessageSet();
			me.setAddPriceToVip(false);
			me.setAddscoreToVip(false);
			me.setOrderToUser(false);
			me.setReducePriceToVip(false);
			me.setReducescoreToVip(false);
			me.setWinxinInfoToUser(false);
			me.setYudingToUser(false);
			me.setConpanyId(conpanyId);
			me.setNum(0);
			me.setAddPriceToVipContent("");
			me.setAddscoreToVipContent("");
			me.setOrderToUserContent("");
			me.setOrderToUserPhone("");
			me.setReducesPriceToVipContent("");
			me.setReducesscoreToVipContent("");
			me.setWinxinInfoToUserContent("");
			me.setWinxinInfoToUserPhone("");
			me.setYudingToUserContent("");
			me.setYudingToUserPhone("");
			add(me);
		}
		return me;
	}

	@Override
	public String getSendMessage(String phone, String content, long conpanyId,
			int type,boolean oneToOne) {
		if(!getAllowSendMessage(conpanyId, type)&&type!=MessageSet.QUNSEND_INFO){
			return "发送短信开关未开启";
		}
		try {

			MessageSet me = getMessageSet(conpanyId);
			if (me.getNum()>0) {
				MessageXML nums=null;
				if(type== MessageSet.QUNSEND_INFO){
					nums= MessageClient.sendSMSmarks(phone, content,me,oneToOne);
				}else{
					nums= MessageClient.sendSMSInfo(phone, content,me);
				}
				if (Integer.parseInt(nums.getResult()) == 1) {
					// TODO Auto-generated method stub
					int countnum=0;
					for(int i=0;i<nums.getItems().size();i++){
						countnum=countnum+Integer.parseInt(nums.getItems().get(i).getTotal());
					}
					MessageSend messageSend = new MessageSend();
					messageSend.setConpanyId(conpanyId);
					messageSend.setSendDate(new Date());
					if( MessageSet.QUNSEND_INFO==type){
						messageSend.setMessage("群发");
					}else{
					messageSend.setMessage(content);
					}
					messageSend.setSuccess(true);
					messageSend.setNum(countnum);
					add(messageSend);
						switch (type) {
						case MessageSet.ADD_PRICE:
							me.setAddPriceToVipnum(me.getAddPriceToVipnum()
									+ countnum);
							break;
						case MessageSet.ADD_SCORE:
							me.setAddscoreToVipnum(me.getAddscoreToVipnum()
									+ countnum);
							break;
						case MessageSet.ORDER_INFO_TO_USER:
							me.setOrderToUsernum(me.getOrderToUsernum() + countnum);
							break;
						case MessageSet.QUNSEND_INFO:

							break;
						case MessageSet.REDUCE_PRICE:
							me.setReducesPriceToVipnum(me
									.getReducesPriceToVipnum() + countnum);
							break;
						case MessageSet.REDUCE_SCORE:
							me.setReducesscoreToVipnum(me
									.getReducesscoreToVipnum() + countnum);
							break;
						case MessageSet.WEIXIN_INFO_TO_USER:
							me.setWinxinInfoToUsernum(me
									.getWinxinInfoToUsernum() + countnum);
							break;
						case MessageSet.YUDING_INFO_TO_USER:
							me.setYudingToUsernum(me.getYudingToUsernum() + countnum);
							break;
						default:
							break;
						}
						me.setNum(me.getNum()-countnum);
						update(me);
						return "发送成功";

				} else {
					return MessageClient.getNumInfo(Integer.parseInt(nums.getResult()));
				}
			} else {
				return "短信数量不足";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "发送过程中出现问题。";
		}

	}

	@Override
	public boolean getAllowSendMessage(long conpanyId, int type) {
		// TODO Auto-generated method stub
		List<MessageSet> list = getSession().createQuery(
				"from MessageSet where conpanyId=" + conpanyId).list();
		MessageSet me = null;
		if (list.size() > 0) {
			me = list.iterator().next();
			switch (type) {
			case MessageSet.ADD_PRICE:
				return me.isAddPriceToVip();

			case MessageSet.ADD_SCORE:

				return me.isAddscoreToVip();
			case MessageSet.ORDER_INFO_TO_USER:

				return me.isOrderToUser();
			case MessageSet.QUNSEND_INFO:

				break;
			case MessageSet.REDUCE_PRICE:

				return me.isReducePriceToVip();
			case MessageSet.REDUCE_SCORE:

				return me.isReducescoreToVip();
			case MessageSet.WEIXIN_INFO_TO_USER:

				return me.isWinxinInfoToUser();
			case MessageSet.YUDING_INFO_TO_USER:

				return me.isYudingToUser();
			default:
				break;
			}
			return false;
		} else {
			return false;
		}

	}

	@Override
	public String getMessageSetContent(long conpanyId, int type,
			LinkManList lk, double score, double price, Orders order,
			String weixinContent) {
		// TODO Auto-generated method stub
		MessageSet mes = getMessageSet(conpanyId);
		Conpany conpany = (Conpany) getObject(conpanyId, "Conpany");
		String content = "";
		switch (type) {
		case MessageSet.ADD_PRICE:
			content = mes.getAddPriceToVipContent();
			content=content.replace("@NOW_PRICE", String.valueOf(lk.getMoney()));
			content=content.replace("@ADD_PRICE", String.valueOf(price));
			content=content.replace("@ADD_BEFORE_PRICE",String.valueOf(DoubleUtil.sub(lk.getMoney(), price)));
			content=content.replace("@ADD_AFTER_PRICE", String.valueOf(lk.getMoney()));
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			content = content + "【" + mes.getQianMing() + "】";
			break;
		case MessageSet.ADD_SCORE:
			content = mes.getAddscoreToVipContent();
			content=content.replace("@NOW_SCORE", String.valueOf(lk.getLinkManScore()));
			content=content.replace("@ADD_SCORE", String.valueOf(score));
			content=content.replace("@ADD_BEFORE_SCORE",String.valueOf(DoubleUtil.sub(lk.getLinkManScore(), score)));
			content=content.replace("@ADD_AFTER_SCORE",
					String.valueOf(lk.getLinkManScore()));
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			if (order != null) {
				content = "订单号：" + order.getOrderNum();
			}
			content = content + "【" + mes.getQianMing() + "】";
			break;
		case MessageSet.ORDER_INFO_TO_USER:
			content = mes.getOrderToUserContent();
			content=content.replace("@CONTENT_ORDER_NAME", order.getOrderNum());
			content=content.replace("@CONTENT_ORDER_DATE",
					DateUtil.formatDateYYYY_MM_DD(order.getCreateDate()));
			content=content.replace("@CONTENT_ORDER_ITEM_LIST",weixinContent);
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			content = content + "【" + mes.getQianMing() + "】";
			break;
		case MessageSet.QUNSEND_INFO:

			break;
		case MessageSet.REDUCE_PRICE:
			content = mes.getReducesPriceToVipContent();
			content=content.replace("@NOW_PRICE", String.valueOf(lk.getMoney()));
			content=content.replace("@REDUCE_PRICE", String.valueOf(price));
			content=content.replace("@REDUCE_BEFORE_PRICE",
					String.valueOf(DoubleUtil.add(lk.getMoney(), price)));
			content=content.replace("@REDUCE_AFTER_PRICE",
					String.valueOf(lk.getMoney()));
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			if (order != null) {
				content =content+ "订单号：" + order.getOrderNum();
			}
			content = content + "【" + mes.getQianMing()+ "】";
			break;
		case MessageSet.REDUCE_SCORE:
			content = mes.getReducesscoreToVipContent();
			content=content.replace("@NOW_SCORE", String.valueOf(lk.getLinkManScore()));
			content=content.replace("@REDUCE_SCORE", String.valueOf(score));
			content=content.replace("@REDUCE_BEFORE_SCORE",
					String.valueOf(DoubleUtil.add(lk.getLinkManScore(), score)));
			content=content.replace("@REDUCE_AFTER_SCORE",
					String.valueOf(lk.getLinkManScore()));
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			if (weixinContent != null) {
				content = content+"兑换号：" + weixinContent;
			}
			content = content + "【" + mes.getQianMing()+ "】";
			break;
		case MessageSet.WEIXIN_INFO_TO_USER:
			content = mes.getWinxinInfoToUserContent();
			content=content.replace("@CONTENT_WEIXIN_CONTENT", weixinContent);
			content=content.replace("@VIP_NAME", lk.getLinkManName());
			content=content.replace("@VIP_PHONE", lk.getLinkManPhone());
			content = content + "【" + mes.getQianMing() + "】";
			break;
		case MessageSet.YUDING_INFO_TO_USER:
			content = mes.getYudingToUserContent();
			break;
		default:
			break;
		}
		return content;
	}

	@Override
	public StoreHouse getStoreHouse(String storeName, long conpanyId,
			boolean iscreate) {
		// TODO Auto-generated method stub
		List list = getSession().createQuery(
				"from StoreHouse where name='" + storeName
						+ "' and conpanyId=" + conpanyId).list();
		StoreHouse sh = null;
		if (list.size() != 0) {
			sh = (StoreHouse) list.get(0);
		} else {
			if (iscreate) {
				sh = new StoreHouse();
				sh.setConpanyId(conpanyId);
				sh.setName(storeName);
				sh.setAddress("");
				sh.setManagerUserName("");
				add(sh);
			}
		}
		return sh;
	}

}
