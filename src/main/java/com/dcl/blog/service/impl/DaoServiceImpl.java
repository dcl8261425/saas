package com.dcl.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dcl.blog.dao.Dao;
import com.dcl.blog.model.Awards;
import com.dcl.blog.model.ConpanyAddress;
import com.dcl.blog.model.ConpanyGroup;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyUserMeeting;
import com.dcl.blog.model.Device;
import com.dcl.blog.model.Games;
import com.dcl.blog.model.GamesAwardsList;
import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.InOrderItem;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.Meeting;
import com.dcl.blog.model.Message;
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
import com.dcl.blog.service.DaoService;
@Service("DaoService")
@Repository
public class DaoServiceImpl implements DaoService {
	private Dao dao;
	@Resource
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
		dao.update(obj);
	}

	@Override
	public long add(Object obj) {
		// TODO Auto-generated method stub
		return dao.add(obj);
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		dao.delete(obj);
	}

	@Override
	public Object getObject(long id, String tableName) {
		// TODO Auto-generated method stub
		return dao.getObject(id, tableName);
	}

	@Override
	public List<SoftPermission> getSoftPermissionByConpanyUser(ConpanyUser user,long groupid) {
		// TODO Auto-generated method stub
		return dao.getSoftPermissionByConpanyUser(user,groupid);
	}

	@Override
	public List<Role> getRoleByConpanyUser(ConpanyUser user,long groupid) {
		// TODO Auto-generated method stub
		return dao.getRoleByConpanyUser(user,groupid);
	}

	@Override
	public List<SoftPermission> getSoftPermissionByRole(Role role,String name) {
		// TODO Auto-generated method stub
		return dao.getSoftPermissionByRole(role,name);
	}

	@Override
	public List<ConpanyUser> getConpanyUserByRole(Role role) {
		// TODO Auto-generated method stub
		return dao.getConpanyUserByRole(role);
	}

	@Override
	public Map<String, Object> addPermissionToRole(long permissionId,
			long roleid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.addPermissionToRole(permissionId, roleid, conpanyid);
	}

	@Override
	public Map<String, Object> deletePermissionToRole(long permissionId,
			long roleid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.deletePermissionToRole(permissionId, roleid, conpanyid);
	}

	@Override
	public Map<String, Object> addRoleToConpanyUser(long roleid, long userid,
			long conpanyid,long groupId) {
		// TODO Auto-generated method stub
		return dao.addRoleToConpanyUser(roleid, userid, conpanyid,groupId);
	}

	@Override
	public Map<String, Object> deleteRoleToConpanyUser(long roleid,
			long userid, long conpanyid,long groupId) {
		// TODO Auto-generated method stub
		return dao.deleteRoleToConpanyUser(roleid, userid, conpanyid,groupId);
	}

	@Override
	public Map<String, Object> addConpanyUserToGroup(long userid, long groupid,
			long conpanyid) {
		// TODO Auto-generated method stub
		return dao.addConpanyUserToGroup(userid, groupid, conpanyid);
	}

	@Override
	public Map<String, Object> deleteConpanyUserToGroup(long userid,
			long groupid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.deleteConpanyUserToGroup(userid, groupid, conpanyid);
	}

	@Override
	public ConpanyUser getConpanyUserByUserName(String username) {
		// TODO Auto-generated method stub
		return dao.getConpanyUserByUserName(username);
	}

	@Override
	public List<Object> getObjectList(String table, String where) {
		// TODO Auto-generated method stub
		return dao.getObjectList(table, where);
	}

	@Override
	public long getObjectListNum(String table, String where) {
		// TODO Auto-generated method stub
		return dao.getObjectListNum(table, where);
	}

	@Override
	public List<Object> getObjectListPage(String table, String where,
			int nowpage, int countRow) {
		// TODO Auto-generated method stub
		return dao.getObjectListPage(table, where, nowpage, countRow);
	}

	@Override
	public List<Role> getConpanyGroupUserRole(long conpanyid, long groupId,String roleName) {
		// TODO Auto-generated method stub
		return dao.getConpanyGroupUserRole(conpanyid, groupId,roleName);
	}

	@Override
	public List<SoftPermissionLinkConpanyRole> getRolelinkPermissionList(
			long groupId, long roleId) {
		// TODO Auto-generated method stub
		return dao.getRolelinkPermissionList(groupId, roleId);
	}

	@Override
	public Map<String, Object> deleteGroup(long groupId) throws Exception {
		// TODO Auto-generated method stub
		return dao.deleteGroup(groupId);
	}

	@Override
	public WindowLayout getWindowLayoutByUserIdAndUrl(long userId, String url) {
		// TODO Auto-generated method stub
		return dao.getWindowLayoutByUserIdAndUrl(userId, url);
	}

	@Override
	public List<WindowLayout> getWindowLayoutByUser(long userId, String mainUrls) {
		// TODO Auto-generated method stub
		return dao.getWindowLayoutByUser(userId, mainUrls);
	}

	@Override
	public List<ConpanyGroup> getUserOfGroups(long userId) {
		// TODO Auto-generated method stub
		return dao.getUserOfGroups(userId);
	}

	@Override
	public List<ConpanyGroup> getUserOfGroupsByUserIdAndGroupName(long userId,
			String groupName) {
		// TODO Auto-generated method stub
		return dao.getUserOfGroupsByUserIdAndGroupName(userId, groupName);
	}

	@Override
	public List<Object> getObjectListBySql(String sql) {
		// TODO Auto-generated method stub
		return dao.getObjectListBySql(sql);
	}

	@Override
	public void deleteList(String sql,String tableName) {
		// TODO Auto-generated method stub
		dao.deleteList(sql,tableName);
	}

	@Override
	public GoodsTable addGoods(GoodsTable goods) {
		// TODO Auto-generated method stub
		return dao.addGoods(goods);
	}

	@Override
	public List getSalesData(String startDate, String endDate,long goodsId,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getSalesData(startDate, endDate,goodsId,conpanyId);
	}

	@Override
	public List getInGoodsData(String startDate, String endDate,long goodsId,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getInGoodsData(startDate, endDate,goodsId,conpanyId);
	}

	@Override
	public List getGoodsPriceAndInPrice(String startDate, String endDate,
			long goodsId,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGoodsPriceAndInPrice(startDate, endDate, goodsId,conpanyId);
	}

	@Override
	public StoreHouseDateLog getStoreHouseDateLog(long goodsid,
			long storeHouseId,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getStoreHouseDateLog(goodsid, storeHouseId,conpanyId);
	}

	@Override
	public List<StoreHouseDateLog> getStoreHouseDateLogChar(long goodsId,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getStoreHouseDateLogChar(goodsId,conpanyId);
	}

	@Override
	public void deleteInOrder(long orderId,long conpanyId) {
		// TODO Auto-generated method stub
		 dao.deleteInOrder(orderId,conpanyId);
	}

	@Override
	public GoodsTable getGoods(String name, String type, String model,long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGoods(name, type, model,conpanyId);
	}

	@Override
	public Object getObject(long id, String tableName, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getObject(id, tableName,conpanyId);
	}

	@Override
	public List<InOrderItem> getInOrderItems(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getInOrderItems(orderId, conpanyId);
	}

	@Override
	public GoodsTable  addReduceGoods(GoodsTable goods) {
		// TODO Auto-generated method stub
		return dao.addReduceGoods(goods);
	}

	@Override
	public void deleteOrder(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		dao.deleteOrder(orderId, conpanyId);
	}

	@Override
	public List<OrdersItem> getOrderItems(long orderId, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getOrderItems(orderId, conpanyId);
	}

	@Override
	public GoodsSource getGoodsSource(String storeName, long conpanyId,
			boolean iscreate) {
		// TODO Auto-generated method stub
		return dao.getGoodsSource(storeName, conpanyId, iscreate);
	}

	@Override
	public List<ConpanyGroup> getTopOneGroup(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getTopOneGroup(conpanyId);
	}

	@Override
	public List<ConpanyUser> getConpanyUser(long conpanyId, String trueName) {
		// TODO Auto-generated method stub
		return dao.getConpanyUser(conpanyId, trueName);
	}

	@Override
	public Long getConpanyUserNum(long conpanyId, String trueName) {
		// TODO Auto-generated method stub
		return dao.getConpanyUserNum(conpanyId, trueName);
	}

	@Override
	public List<ConpanyUser> getConpanyUserPage(long conpanyId,
			String trueName, int nowPage, int countRow) {
		// TODO Auto-generated method stub
		return dao.getConpanyUserPage(conpanyId, trueName, nowPage, countRow);
	}

	@Override
	public List<ConpanyUserMeeting> getConpanUserMeetingByDate(String date,
			long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getConpanUserMeetingByDate(date, conpanyId);
	}

	@Override
	public List<ConpanyUserMeeting> getConpanUserMeetingByDateAndUser(
			String date, long userId, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getConpanUserMeetingByDateAndUser(date, userId, conpanyId);
	}

	@Override
	public boolean getCreateConpanyCusteemerMeeting(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getCreateConpanyCusteemerMeeting(conpanyId);
	}

	@Override
	public List<Meeting> getMeetingListTopTen(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getMeetingListTopTen(conpanyId);
	}

	@Override
	public Meeting getMeetingListTop(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getMeetingListTop(conpanyId);
	}

	@Override
	public Performance getPerformanceByUser(long userId, long conpanyId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getPerformanceByUser(userId, conpanyId, startDate, endDate);
	}

	@Override
	public List<Performance> getPerformanceByAll(long conpanyId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getPerformanceByAll(conpanyId, startDate, endDate);
	}

	@Override
	public Performance getPerformanceByToDayUser(long conpanyId, long userId,
			String userTrueName) {
		// TODO Auto-generated method stub
		return dao.getPerformanceByToDayUser(conpanyId, userId, userTrueName);
	}

	@Override
	public List<SoftPermission> getPageSoftPermissions(long user, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getPageSoftPermissions(user, conpanyId);
	}

	@Override
	public List<Message> getMessage(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.getMessage(userid, conpanyid);
	}

	@Override
	public List<Message> getMessageNotRead(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.getMessageNotRead(userid, conpanyid);
	}

	@Override
	public List<Message> getMessageRead(long userid, long conpanyid) {
		// TODO Auto-generated method stub
		return dao.getMessageRead(userid, conpanyid);
	}

	@Override
	public List<WeiXin> queryWeiXin(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXin(conpanyId);
	}

	@Override
	public List<WeiXinMenuTable> queryWeiXinMenu(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinMenu(id, conpanyId);
	}

	@Override
	public List<WeiXinMenuTable> queryWeiXinMenuById(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinMenuById(id, conpanyId);
	}

	@Override
	public List<WeiXinUser> queryWeiXinUser(String name, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinUser(name, conpanyId);
	}

	@Override
	public List<WeiXinUser> queryWeiXinUser(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinUser(id, conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinImage(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinImage(conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinVoice(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinVoice(conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinVideo(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinVideo(conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinText(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinText(conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinMusic(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinMusic(conpanyId);
	}

	@Override
	public List<WeiXinReSend> queryWeiXinImageText(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeiXinImageText(conpanyId);
	}

	@Override
	public List<WeiXinReSend> getWeiXinReSendById(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinReSendById(id, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItemById(long id,
			long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendItemById(id, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItem(
			long aoturesendId, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendItem(aoturesendId, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,
			String Content, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendMenu(type, Content, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuIsUse(long type,
			String Content, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendMenuIsUse(type, Content, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,
			long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendMenu(type, conpanyId);
	}

	@Override
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuById(long id,
			long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getWeiXinAutoReSendMenuById(id, conpanyId);
	}

	@Override
	public Games getGames(long id, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGames(id, conpanyId);
	}

	@Override
	public Games getGames(String name, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGames(name, conpanyId);
	}

	@Override
	public List<Games> getUseGamesList(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getUseGamesList(conpanyId);
	}

	@Override
	public List<Games> getGamesList(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGamesList(conpanyId);
	}

	@Override
	public List<Awards> getAwards(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getAwards(conpanyId);
	}

	@Override
	public List<GamesAwardsList> getGamesAwardsList(long gameid, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getGamesAwardsList(gameid, conpanyId);
	}

	@Override
	public List<NumLibs> getNAwardsNum(String num, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getNAwardsNum(num, conpanyId);
	}

	@Override
	public ScoreToGoodsList getScoreNum(String num, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getScoreNum(num, conpanyId);
	}

	@Override
	public UserGamesNum getUserGamesNum(long gameid, long userid, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getUserGamesNum(gameid, userid, conpanyId);
	}

	@Override
	public List<VIPSet> getVipSetList(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getVipSetList(conpanyId);
	}

	@Override
	public List<ScoreDuihuan> getScoreDuihuansList(long conpanyId,int nowpage,int countNum) {
		// TODO Auto-generated method stub
		return dao.getScoreDuihuansList(conpanyId,nowpage,countNum);
	}

	@Override
	public List<ScoreToGoodsList> getUserScoreDuihuan(long userid,
			long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getUserScoreDuihuan(userid, conpanyId);
	}

	@Override
	public List<NumLibs> getUserNumLibs(long userid, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getUserNumLibs(userid, conpanyId);
	}

	@Override
	public List<LinkManList> queryUserModelByOpenid(String openid, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryUserModelByOpenid(openid, conpanyId);
	}

	@Override
	public List<WeiXinWebHtml> queryWeixinWebHtml(String name, long conpanyId,
			int nowpage, int countNum) {
		// TODO Auto-generated method stub
		return dao.queryWeixinWebHtml(name, conpanyId, nowpage, countNum);
	}

	@Override
	public Long queryWeixinWebHtml(String name, long conpanyId) {
		// TODO Auto-generated method stub
		return dao.queryWeixinWebHtml(name, conpanyId);
	}

	@Override
	public List<ConpanyAddress> getConpanyAddressByConpanyId(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getConpanyAddressByConpanyId(conpanyId);
	}

	@Override
	public List<ConpanyAddress> getConpanyAddressByXY(String x, String y,int nowpage,int countNum) {
		// TODO Auto-generated method stub
		return dao.getConpanyAddressByXY(x, y,nowpage,countNum);
	}

	@Override
	public VWiFi getVWifi(long conpanyId, String tokens) {
		// TODO Auto-generated method stub
		return  dao.getVWifi(conpanyId, tokens);
	}

	@Override
	public List<VWiFi> getConpanyVWifi(long conpanyId) {
		// TODO Auto-generated method stub
		return  dao.getConpanyVWifi(conpanyId);
	}

	@Override
	public List<Device> getDeviceByVWifi(long conpanyId, String tokens) {
		// TODO Auto-generated method stub
		return  dao.getDeviceByVWifi(conpanyId, tokens);
	}

	@Override
	public Device getDevice(long conpanyId, String tokens, String mac) {
		// TODO Auto-generated method stub
		return  dao.getDevice(conpanyId, tokens, mac);
	}



	@Override
	public MessageSet getMessageSet(long conpanyId) {
		// TODO Auto-generated method stub
		return dao.getMessageSet(conpanyId);
	}

	@Override
	public String getSendMessage(String phone,String content, long conpanyId, int type,boolean oneToOne) {
		// TODO Auto-generated method stub
			return dao.getSendMessage(phone,content, conpanyId, type,oneToOne);
		
	}

	@Override
	public boolean getAllowSendMessage(long conpanyId, int type) {
		// TODO Auto-generated method stub
		return dao.getAllowSendMessage(conpanyId, type);
	}

	@Override
	public String getMessageSetContent(long conpanyId, int type,
			LinkManList lk, double score, double price,Orders order,String weixinContent) {
		// TODO Auto-generated method stub
		return dao.getMessageSetContent(conpanyId, type, lk, score, price,order,weixinContent);
	}

	@Override
	public StoreHouse getStoreHouse(String storeName, long conpanyId,
			boolean iscreate) {
		// TODO Auto-generated method stub
		return dao.getStoreHouse(storeName, conpanyId, iscreate);
	}

	@Override
	public List<Object> getObjectListByeSql(String sql) {
		// TODO Auto-generated method stub
		return dao.getObjectListByeSql(sql);
	}
}
