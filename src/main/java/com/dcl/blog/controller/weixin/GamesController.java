package com.dcl.blog.controller.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.Game;
import com.dcl.blog.model.GameConpany;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.SessionString;

@Controller
@RequestMapping(value = "/gamesController")
public class GamesController {
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	@RequestMapping(value = "/getGame")
	@ResponseBody
	public Map getGame(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		if(id!=null){
			
			Game game=(Game) dao.getObject(Long.parseLong(id), "Game");
			if(game!=null){
				List<Object> list=dao.getObjectList("GameConpany", "where conpanyId="+users.getConpanyId()+" and gameId="+game.getId());
				if(list.size()<=0){
					GameConpany congame=new GameConpany();
					congame.setConpanyId(users.getConpanyId());
					congame.setGameId(game.getId());
					congame.setGameImage1(game.getGameImage1());
					congame.setGameImage2(game.getGameImage2());
					congame.setGameImage3(game.getGameImage3());
					congame.setGameInfo(game.getGameInfo());
					congame.setGameMarks(game.getGameMarks());
					congame.setGameServiceAddress(game.getGameServiceAddress());
					congame.setIndexNum(1);
					congame.setName(game.getName());
					congame.setOpenUse(true);
					congame.setPaming(game.isPaming());
					congame.setVipRunNum(0);
					dao.add(congame);
					game.setShopAddNum(game.getShopAddNum()+1);
					dao.update(game);
					map.put("info", "添加成功");
					map.put("success", true);
				}else{
					map.put("info", "此游戏已经添加过了，无法重复添加");
					map.put("success", false);
				}
			}else{
				map.put("info", "您选中的游戏不存在，无法操作");
				map.put("success", false);
			}
		}else{
			try {
				map.put("data", dao.getObjectList("Game", ""));
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("success", false);
			}
		}
		return map;
	}
	@RequestMapping(value = "/getConpanyGame")
	@ResponseBody
	public Map getConpanyGame(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		String id=req.getParameter("id");
		if(id!=null){
			GameConpany game=(GameConpany) dao.getObject(Long.parseLong(id), "GameConpany");
			if(game!=null){
				game.setOpenUse(!game.isOpenUse());
				dao.update(game);
				map.put("info", "修改成功");
				map.put("now", game.isOpenUse());
				map.put("success", true);
			}else{
				map.put("info", "您选中的游戏不存在，无法打开");
				map.put("success", false);
			}
		}else{
			try {
				map.put("data", dao.getObjectList("GameConpany", "where conpanyId="+users.getConpanyId()));
			
				map.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("success", false);
			}
		}
		return map;
	}
}
