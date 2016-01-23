package com.dcl.blog.dao;

import java.util.List;

import com.dcl.blog.model.Awards;
import com.dcl.blog.model.ConpanyAddress;
import com.dcl.blog.model.Games;
import com.dcl.blog.model.GamesAwardsList;
import com.dcl.blog.model.LinkManList;
import com.dcl.blog.model.NumLibs;
import com.dcl.blog.model.ScoreDuihuan;
import com.dcl.blog.model.ScoreToGoodsList;
import com.dcl.blog.model.UserGamesNum;
import com.dcl.blog.model.VIPSet;
import com.dcl.blog.model.WeiXin;
import com.dcl.blog.model.WeiXinAutoReSendItem;
import com.dcl.blog.model.WeiXinAutoReSendMenu;
import com.dcl.blog.model.WeiXinMenuTable;
import com.dcl.blog.model.WeiXinReSend;
import com.dcl.blog.model.WeiXinUser;
import com.dcl.blog.model.WeiXinWebHtml;

public interface WeixinDao {
	public List<WeiXin> queryWeiXin(long conpanyId);
	public List<WeiXinMenuTable> queryWeiXinMenu(long id,long conpanyId);
	public List<WeiXinMenuTable> queryWeiXinMenuById(long id,long conpanyId);
	public List<WeiXinUser> queryWeiXinUser(String name,long conpanyId);
	public List<WeiXinUser> queryWeiXinUser(long id,long conpanyId);
	public List<WeiXinReSend> queryWeiXinImage(long conpanyId);
	public List<WeiXinReSend> queryWeiXinVoice(long conpanyId);
	public List<WeiXinReSend> queryWeiXinVideo(long conpanyId);
	public List<WeiXinReSend> queryWeiXinText(long conpanyId);
	public List<WeiXinReSend> queryWeiXinMusic(long conpanyId);
	public List<WeiXinReSend> queryWeiXinImageText(long conpanyId);
	public List<WeiXinReSend> getWeiXinReSendById(long id,long conpanyId);
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItemById(long id,long conpanyId);
	public List<WeiXinAutoReSendItem> getWeiXinAutoReSendItem(long aoturesendId,long conpanyId);
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,String Content,long conpanyId);
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuIsUse(long type,String Content,long conpanyId);
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenu(long type,long conpanyId);
	public List<WeiXinAutoReSendMenu> getWeiXinAutoReSendMenuById(long id,long conpanyId);
	/**
	 * 获取游戏
	 */
	public Games getGames(long id,long conpanyId);
	/**
	 * 获取游戏通过游戏名称
	 */
	public Games getGames(String name,long conpanyId);
	/**
	 * 获取开启的游戏
	 */
	public List<Games> getUseGamesList(long conpanyId);
	/**
	 * 获取游戏
	 */
	public List<Games> getGamesList(long conpanyId);
	/**
	 * 获取游奖奖项列表
	 */
	public List<Awards> getAwards(long conpanyId);
	/**
	 * 获取某个游戏的奖品库
	 */
	public List<GamesAwardsList> getGamesAwardsList(long gameid,long conpanyId);
	/**
	 * 	根据序列号查看获得奖品
	 */
	public List<NumLibs> getNAwardsNum(String num,long conpanyId);
	/**
	 * 	根据序列号查看积分兑换的奖品
	 */
	public ScoreToGoodsList getScoreNum(String num,long conpanyId);
	/**
	 * 根据游戏id和用户id获取当天游戏次数
	 */
	public UserGamesNum getUserGamesNum(long gameid,long userid,long conpanyId);
	/**
	 * 获取vip列表
	 */
	public List<VIPSet> getVipSetList(long conpanyId);
	/**
	 * 获取积分兑换列表
	 */
	public List<ScoreDuihuan> getScoreDuihuansList(long conpanyId,int nowpage,int countNum);
	/**
	 * 通过用户id获取积分兑换列表
	 */
	public List<ScoreToGoodsList> getUserScoreDuihuan(long userid,long conpanyId);
	/**
	 * 通过用户id获取中奖列表
	 */
	public List<NumLibs> getUserNumLibs(long userid,long conpanyId);
	/**
	 * 通过openid查找账户
	 * @param username
	 * @param password
	 * @return
	 */
	public List<LinkManList> queryUserModelByOpenid(String openid,long conpanyId);
	public List<WeiXinWebHtml> queryWeixinWebHtml(String name,long conpanyId,int nowpage,int countNum);
	public Long queryWeixinWebHtml(String name,long conpanyId);
	/**
	 * 获取一个公司地理位置
	 * 
	 */
	public List<ConpanyAddress> getConpanyAddressByConpanyId(long conpanyId);
	/**
	 * 获取最近地理位置 20条
	 * 
	 */
	public List<ConpanyAddress> getConpanyAddressByXY(String x,String y,int nowpage,int countNum);
}
