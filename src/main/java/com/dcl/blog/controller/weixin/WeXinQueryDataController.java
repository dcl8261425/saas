package com.dcl.blog.controller.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.Vote;
import com.dcl.blog.model.VoteItem;
import com.dcl.blog.model.WebPublicMessage;
import com.dcl.blog.model.WeiXin;
import com.dcl.blog.model.WeiXinAutoReSendItem;
import com.dcl.blog.model.WeiXinAutoReSendMenu;
import com.dcl.blog.model.WeiXinMenuTable;
import com.dcl.blog.model.WeiXinReSend;
import com.dcl.blog.model.dto.VoteDTO;
import com.dcl.blog.model.dto.VoteItemDTO;
import com.dcl.blog.model.weixin.auto.AotuSendImageMessage;
import com.dcl.blog.model.weixin.auto.AotuSendImageTextMessage;
import com.dcl.blog.model.weixin.auto.AotuSendImageTextMessageContent;
import com.dcl.blog.model.weixin.auto.AotuSendImageTextMessageContentList;
import com.dcl.blog.model.weixin.auto.AotuSendMusicMessage;
import com.dcl.blog.model.weixin.auto.AotuSendMusicMessageContent;
import com.dcl.blog.model.weixin.auto.AotuSendTextMessage;
import com.dcl.blog.model.weixin.auto.AotuSendVideoMessage;
import com.dcl.blog.model.weixin.auto.AotuSendVoiceMessage;
import com.dcl.blog.model.weixin.receive.TextMessage;
import com.dcl.blog.model.wiexin.menu.WeiXinMenu;
import com.dcl.blog.model.wiexin.menu.WeiXinMenuButton;
import com.dcl.blog.model.wiexin.menu.WeiXinMenuSubButton;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.BeanXMLMapping;
import com.dcl.blog.util.DTOUtil;
import com.dcl.blog.util.HttpKit;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.WeiXinErrorCode;
import com.dcl.blog.util.WeiXinUrl;
import com.dcl.blog.util.moreuser.MoreUserManager;
import com.dcl.blog.util.text.JSONObject;

@Controller
@RequestMapping(value = "/weixin")
public class WeXinQueryDataController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeXinQueryDataController.class);
	private DaoService dao;

	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}

	@RequestMapping(value = "/renzheng", method = RequestMethod.GET)
	public void renzheng(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		List<WeiXin> weixin = dao
				.queryWeiXin(MoreUserManager.getAppShopId(req));
		if (weixin.size() > 0) {
			WeiXin wx = weixin.iterator().next();
			// 微信加密签名
			String signature = req.getParameter("signature");
			// 随机字符串
			String echostr = req.getParameter("echostr");
			// 时间戳
			String timestamp = req.getParameter("timestamp");
			// 随机数
			String nonce = req.getParameter("nonce");
			logger.info(signature + " " + echostr + " " + timestamp + " "
					+ nonce);
			String[] ArrTmp = { wx.getTokens(), timestamp, nonce };
			Arrays.sort(ArrTmp); // 字典序排序
			String bigStr = ArrTmp[0] + ArrTmp[1] + ArrTmp[2];
			bigStr = new String(DigestUtils.shaHex(bigStr));
			logger.info(bigStr);
			logger.info(signature);
			if (bigStr.equals(signature)) {
				try {
					res.getWriter().print(echostr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			try {
				res.getWriter().print("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/renzheng", method = RequestMethod.POST)
	public void renzheng2(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		try {
			String requertStr = "";
			res.setCharacterEncoding("UTF-8");
			String str = WeiXinUrl.readStreamParameter(req.getInputStream());
			logger.info(str);
			TextMessage text = (TextMessage) BeanXMLMapping.fromXML(str,
					TextMessage.class);
			PrintWriter p = res.getWriter();
			String msgType = text.getMsgType();
			logger.info(msgType);
			if (msgType.equals("text")) {
				logger.info(msgType);
				String content = text.getContent();

				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_TEXT, content,
								MoreUserManager.getAppShopId(req));
				logger.info("List<WeiXinAutoReSendMenu> list " + list.size());
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
						logger.info("wx " + wx.getId());
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						int r = random.nextInt(list.size());
						logger.info("text:query random:" + r);
						wx = list.get(r);
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						logger.info("wx " + i);
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					logger.info("hive_type_list  size " + hive_type_list.size());
					int numtext = new Random().nextInt(hive_type_list.size());
					logger.info("numtext" + numtext);
					int num = hive_type_list.get(numtext);
					logger.info("text:query random type:" + num + "  size "
							+ hive_type_list.size() + " numtext " + numtext);
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("image")) {
				String content = text.getContent();
				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_IMAGE, content,
								MoreUserManager.getAppShopId(req));
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						wx = list.get(random.nextInt(list.size()));
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					int num = hive_type_list.get(new Random()
							.nextInt(hive_type_list.size()));
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("voice")) {
				String content = text.getContent();
				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_VOICE, content,
								MoreUserManager.getAppShopId(req));
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						wx = list.get(random.nextInt(list.size()));
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					int num = hive_type_list.get(new Random()
							.nextInt(hive_type_list.size()));
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("video")) {
				String content = text.getContent();
				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_VIDEO, content,
								MoreUserManager.getAppShopId(req));
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						wx = list.get(random.nextInt(list.size()));
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					int num = hive_type_list.get(new Random()
							.nextInt(hive_type_list.size()));
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("location")) {
				String content = text.getContent();
				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_LOCATION, content,
								MoreUserManager.getAppShopId(req));
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						wx = list.get(random.nextInt(list.size()));
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					int num = hive_type_list.get(new Random()
							.nextInt(hive_type_list.size()));
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("link")) {
				String content = text.getContent();
				// 通过类型获取可返回项
				List<WeiXinAutoReSendMenu> list = dao
						.getWeiXinAutoReSendMenuIsUse(
								WeiXinAutoReSendMenu.TYPE_LINK, content,
								MoreUserManager.getAppShopId(req));
				if (list.size() > 0) {
					WeiXinAutoReSendMenu wx = null;
					if (list.size() == 1) {
						wx = list.iterator().next();
					} else {
						// 如果多于一个则随机
						Random random = new Random();
						wx = list.get(random.nextInt(list.size()));
					}
					List<WeiXinAutoReSendItem> list2 = dao
							.getWeiXinAutoReSendItem(wx.getId(),
									MoreUserManager.getAppShopId(req));
					List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
					List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
					List<Integer> hive_type_list = new ArrayList<Integer>();
					for (int i = 0; i < list2.size(); i++) {
						List<WeiXinReSend> listweiresend = dao
								.getWeiXinReSendById(
										list2.get(i).getResendid(),
										MoreUserManager.getAppShopId(req));
						if (listweiresend.size() > 0) {
							WeiXinReSend weiresend = listweiresend.iterator()
									.next();

							if (weiresend.getType() == WeiXinReSend.IMAGE) {
								imagelist.add(weiresend);
								hive_type_list.add(1);
							}
							if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
								imagetextlist.add(weiresend);
								hive_type_list.add(5);
							}
							if (weiresend.getType() == WeiXinReSend.MUSIC) {
								musiclist.add(weiresend);
								hive_type_list.add(4);
							}
							if (weiresend.getType() == WeiXinReSend.TEXT) {
								textlist.add(weiresend);
								hive_type_list.add(0);
							}
							if (weiresend.getType() == WeiXinReSend.VIDEO) {
								videolist.add(weiresend);
								hive_type_list.add(3);
							}
							if (weiresend.getType() == WeiXinReSend.VOICE) {
								voicelist.add(weiresend);
								hive_type_list.add(2);
							}
						}
					}
					int num = hive_type_list.get(new Random()
							.nextInt(hive_type_list.size()));
					switch (num) {
					case 0:
						if (textlist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(textlist.size());
							}
							WeiXinReSend resend = textlist.get(num2);
							AotuSendTextMessage msg = new AotuSendTextMessage();
							msg.setMsgType("<![CDATA[text]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setContent("<![CDATA[" + resend.getContent()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 1:
						if (imagelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(imagelist.size());
							}
							WeiXinReSend resend = imagelist.get(num2);
							AotuSendImageMessage msg = new AotuSendImageMessage();
							msg.setMsgType("<![CDATA[image]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setImage("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 2:
						if (voicelist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(voicelist.size());
							}
							WeiXinReSend resend = voicelist.get(num2);
							AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
							msg.setMsgType("<![CDATA[voice]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVoice("<![CDATA[" + resend.getMediaId()
									+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 3:
						if (videolist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(videolist.size());
							}
							WeiXinReSend resend = videolist.get(num2);
							AotuSendVideoMessage msg = new AotuSendVideoMessage();
							msg.setMsgType("<![CDATA[video]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setVideo("<![CDATA[" + resend.getMediaId()
									+ "]]>", "<![CDATA[" + resend.getTitle()
									+ "]]>",
									"<![CDATA[" + resend.getDescription()
											+ "]]>");
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 4:
						if (musiclist.size() > 0) {
							int num2 = 0;
							if (textlist.size() > 1) {
								num2 = new Random().nextInt(musiclist.size());
							}
							WeiXinReSend resend = musiclist.get(num2);
							AotuSendMusicMessage msg = new AotuSendMusicMessage();
							msg.setMsgType("<![CDATA[music]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
							music.setDescription("<![CDATA["
									+ resend.getDescription() + "]]>");
							music.setHqmusicurl("<![CDATA["
									+ resend.gethQMusicUrl() + "]]>");
							music.setMusicurl("<![CDATA["
									+ resend.getMusicURL() + "]]>");
							music.setThumb_media_id("<![CDATA["
									+ resend.getThumbMediaId() + "]]>");
							music.setTitle("<![CDATA[" + resend.getTitle()
									+ "]]>");
							msg.setMusic(music);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					case 5:
						if (imagetextlist.size() > 0) {
							AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
							msg.setMsgType("<![CDATA[news]]>");
							msg.setCreateTime(String.valueOf(new Date()
									.getTime()));
							msg.setFromUserName("<![CDATA["
									+ text.getToUserName() + "]]>");
							msg.setToUserName("<![CDATA["
									+ text.getFromUserName() + "]]>");
							msg.setArticleCount(String.valueOf(imagetextlist
									.size()));
							AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
							AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
									.size()];
							for (int ii = 0; ii < imagetextlist.size(); ii++) {
								WeiXinReSend resend = imagetextlist.get(ii);
								String url = resend.getUrl();
								String openid = "";
								if (url.indexOf("?") != -1) {
									openid = "&openid="
											+ text.getFromUserName();
								} else {
									openid = "?openid="
											+ text.getFromUserName();
								}
								if (url.indexOf("#") != -1) {
									url = url.split("#")[0] + openid + "#"
											+ url.split("#")[1];
								} else {
									url = url + openid;
								}
								logger.info(url);
								AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
								contents.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								contents.setPicUrl("<![CDATA["
										+ resend.getPicUrl() + "]]>");
								contents.setUrl("<![CDATA[" + url + "]]>");
								contents.setTitle("<![CDATA["
										+ resend.getTitle() + "]]>");
								arraytextimage[ii] = contents;
							}
							listmap.setItem(arraytextimage);
							msg.setArticles(listmap);
							String xml = BeanXMLMapping.toXML(msg);
							xml = BeanXMLMapping.getWeiXinXml(xml);
							logger.info(xml);
							p.println(xml);
							break;
						}
					default:
						p.println("");
						break;
					}
				} else {
					p.println("");
				}

			}
			if (msgType.equals("event")) {
				String event = text.getEvent();
				logger.info(event);
				if (event.equals("subscribe")) {
					List<WeiXinAutoReSendMenu> list = dao
							.getWeiXinAutoReSendMenuIsUse(
									WeiXinAutoReSendMenu.TYPE_EVENT,
									String.valueOf(WeiXinAutoReSendMenu.EVENT_SUBSCRIBE),
									MoreUserManager.getAppShopId(req));
					if (list.size() > 0) {
						WeiXinAutoReSendMenu wx = null;
						if (list.size() == 1) {
							wx = list.iterator().next();
						} else {
							// 如果多于一个则随机
							Random random = new Random();
							wx = list.get(random.nextInt(list.size()));
						}
						List<WeiXinAutoReSendItem> list2 = dao
								.getWeiXinAutoReSendItem(wx.getId(),
										MoreUserManager.getAppShopId(req));
						List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
						List<Integer> hive_type_list = new ArrayList<Integer>();
						for (int i = 0; i < list2.size(); i++) {
							List<WeiXinReSend> listweiresend = dao
									.getWeiXinReSendById(list2.get(i)
											.getResendid(), MoreUserManager
											.getAppShopId(req));
							if (listweiresend.size() > 0) {
								WeiXinReSend weiresend = listweiresend
										.iterator().next();

								if (weiresend.getType() == WeiXinReSend.IMAGE) {
									imagelist.add(weiresend);
									hive_type_list.add(1);
								}
								if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
									imagetextlist.add(weiresend);
									hive_type_list.add(5);
								}
								if (weiresend.getType() == WeiXinReSend.MUSIC) {
									musiclist.add(weiresend);
									hive_type_list.add(4);
								}
								if (weiresend.getType() == WeiXinReSend.TEXT) {
									textlist.add(weiresend);
									hive_type_list.add(0);
								}
								if (weiresend.getType() == WeiXinReSend.VIDEO) {
									videolist.add(weiresend);
									hive_type_list.add(3);
								}
								if (weiresend.getType() == WeiXinReSend.VOICE) {
									voicelist.add(weiresend);
									hive_type_list.add(2);
								}
							}
						}
						int num = hive_type_list.get(new Random()
								.nextInt(hive_type_list.size()));
						switch (num) {
						case 0:
							if (textlist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random()
											.nextInt(textlist.size());
								}
								WeiXinReSend resend = textlist.get(num2);
								AotuSendTextMessage msg = new AotuSendTextMessage();
								msg.setMsgType("<![CDATA[text]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setContent("<![CDATA["
										+ resend.getContent() + "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 1:
							if (imagelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(imagelist
											.size());
								}
								WeiXinReSend resend = imagelist.get(num2);
								AotuSendImageMessage msg = new AotuSendImageMessage();
								msg.setMsgType("<![CDATA[image]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setImage("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 2:
							if (voicelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(voicelist
											.size());
								}
								WeiXinReSend resend = voicelist.get(num2);
								AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
								msg.setMsgType("<![CDATA[voice]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVoice("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 3:
							if (videolist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(videolist
											.size());
								}
								WeiXinReSend resend = videolist.get(num2);
								AotuSendVideoMessage msg = new AotuSendVideoMessage();
								msg.setMsgType("<![CDATA[video]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVideo(
										"<![CDATA[" + resend.getMediaId()
												+ "]]>",
										"<![CDATA[" + resend.getTitle() + "]]>",
										"<![CDATA[" + resend.getDescription()
												+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 4:
							if (musiclist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(musiclist
											.size());
								}
								WeiXinReSend resend = musiclist.get(num2);
								AotuSendMusicMessage msg = new AotuSendMusicMessage();
								msg.setMsgType("<![CDATA[music]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
								music.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								music.setHqmusicurl("<![CDATA["
										+ resend.gethQMusicUrl() + "]]>");
								music.setMusicurl("<![CDATA["
										+ resend.getMusicURL() + "]]>");
								music.setThumb_media_id("<![CDATA["
										+ resend.getThumbMediaId() + "]]>");
								music.setTitle("<![CDATA[" + resend.getTitle()
										+ "]]>");
								msg.setMusic(music);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 5:
							if (imagetextlist.size() > 0) {
								AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
								msg.setMsgType("<![CDATA[news]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setArticleCount(String
										.valueOf(imagetextlist.size()));
								AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
								AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
										.size()];
								for (int ii = 0; ii < imagetextlist.size(); ii++) {
									WeiXinReSend resend = imagetextlist.get(ii);
									String url = resend.getUrl();
									String openid = "";
									if (url.indexOf("?") != -1) {
										openid = "&openid="
												+ text.getFromUserName();
									} else {
										openid = "?openid="
												+ text.getFromUserName();
									}
									if (url.indexOf("#") != -1) {
										url = url.split("#")[0] + openid + "#"
												+ url.split("#")[1];
									} else {
										url = url + openid;
									}
									logger.info(url);
									AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
									contents.setDescription("<![CDATA["
											+ resend.getDescription() + "]]>");
									contents.setPicUrl("<![CDATA["
											+ resend.getPicUrl() + "]]>");
									contents.setUrl("<![CDATA[" + url + "]]>");
									contents.setTitle("<![CDATA["
											+ resend.getTitle() + "]]>");
									arraytextimage[ii] = contents;
								}
								listmap.setItem(arraytextimage);
								msg.setArticles(listmap);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						default:
							p.println("");
							break;
						}
					} else {
						p.println("");
					}

				}
				if (event.equals("unsubscribe")) {
					List<WeiXinAutoReSendMenu> list = dao
							.getWeiXinAutoReSendMenuIsUse(
									WeiXinAutoReSendMenu.TYPE_EVENT,
									String.valueOf(WeiXinAutoReSendMenu.EVENT_UNSUBSCRIB),
									MoreUserManager.getAppShopId(req));
					if (list.size() > 0) {
						WeiXinAutoReSendMenu wx = null;
						if (list.size() == 1) {
							wx = list.iterator().next();
						} else {
							// 如果多于一个则随机
							Random random = new Random();
							wx = list.get(random.nextInt(list.size()));
						}
						List<WeiXinAutoReSendItem> list2 = dao
								.getWeiXinAutoReSendItem(wx.getId(),
										MoreUserManager.getAppShopId(req));
						List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
						List<Integer> hive_type_list = new ArrayList<Integer>();
						for (int i = 0; i < list2.size(); i++) {
							List<WeiXinReSend> listweiresend = dao
									.getWeiXinReSendById(list2.get(i)
											.getResendid(), MoreUserManager
											.getAppShopId(req));
							if (listweiresend.size() > 0) {
								WeiXinReSend weiresend = listweiresend
										.iterator().next();

								if (weiresend.getType() == WeiXinReSend.IMAGE) {
									imagelist.add(weiresend);
									hive_type_list.add(1);
								}
								if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
									imagetextlist.add(weiresend);
									hive_type_list.add(5);
								}
								if (weiresend.getType() == WeiXinReSend.MUSIC) {
									musiclist.add(weiresend);
									hive_type_list.add(4);
								}
								if (weiresend.getType() == WeiXinReSend.TEXT) {
									textlist.add(weiresend);
									hive_type_list.add(0);
								}
								if (weiresend.getType() == WeiXinReSend.VIDEO) {
									videolist.add(weiresend);
									hive_type_list.add(3);
								}
								if (weiresend.getType() == WeiXinReSend.VOICE) {
									voicelist.add(weiresend);
									hive_type_list.add(2);
								}
							}
						}
						int num = hive_type_list.get(new Random()
								.nextInt(hive_type_list.size()));
						switch (num) {
						case 0:
							if (textlist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random()
											.nextInt(textlist.size());
								}
								WeiXinReSend resend = textlist.get(num2);
								AotuSendTextMessage msg = new AotuSendTextMessage();
								msg.setMsgType("<![CDATA[text]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setContent("<![CDATA["
										+ resend.getContent() + "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 1:
							if (imagelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(imagelist
											.size());
								}
								WeiXinReSend resend = imagelist.get(num2);
								AotuSendImageMessage msg = new AotuSendImageMessage();
								msg.setMsgType("<![CDATA[image]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setImage("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 2:
							if (voicelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(voicelist
											.size());
								}
								WeiXinReSend resend = voicelist.get(num2);
								AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
								msg.setMsgType("<![CDATA[voice]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVoice("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 3:
							if (videolist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(videolist
											.size());
								}
								WeiXinReSend resend = videolist.get(num2);
								AotuSendVideoMessage msg = new AotuSendVideoMessage();
								msg.setMsgType("<![CDATA[video]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVideo(
										"<![CDATA[" + resend.getMediaId()
												+ "]]>",
										"<![CDATA[" + resend.getTitle() + "]]>",
										"<![CDATA[" + resend.getDescription()
												+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 4:
							if (musiclist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(musiclist
											.size());
								}
								WeiXinReSend resend = musiclist.get(num2);
								AotuSendMusicMessage msg = new AotuSendMusicMessage();
								msg.setMsgType("<![CDATA[music]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
								music.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								music.setHqmusicurl("<![CDATA["
										+ resend.gethQMusicUrl() + "]]>");
								music.setMusicurl("<![CDATA["
										+ resend.getMusicURL() + "]]>");
								music.setThumb_media_id("<![CDATA["
										+ resend.getThumbMediaId() + "]]>");
								music.setTitle("<![CDATA[" + resend.getTitle()
										+ "]]>");
								msg.setMusic(music);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 5:
							if (imagetextlist.size() > 0) {
								AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
								msg.setMsgType("<![CDATA[news]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setArticleCount(String
										.valueOf(imagetextlist.size()));
								AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
								AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
										.size()];
								for (int ii = 0; ii < imagetextlist.size(); ii++) {
									WeiXinReSend resend = imagetextlist.get(ii);
									String url = resend.getUrl();
									String openid = "";
									if (url.indexOf("?") != -1) {
										openid = "&openid="
												+ text.getFromUserName();
									} else {
										openid = "?openid="
												+ text.getFromUserName();
									}
									if (url.indexOf("#") != -1) {
										url = url.split("#")[0] + openid + "#"
												+ url.split("#")[1];
									} else {
										url = url + openid;
									}
									logger.info(url);
									AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
									contents.setDescription("<![CDATA["
											+ resend.getDescription() + "]]>");
									contents.setPicUrl("<![CDATA["
											+ resend.getPicUrl() + "]]>");
									contents.setUrl("<![CDATA[" + url + "]]>");
									contents.setTitle("<![CDATA["
											+ resend.getTitle() + "]]>");
									arraytextimage[ii] = contents;
								}
								listmap.setItem(arraytextimage);
								msg.setArticles(listmap);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						default:
							p.println("");
							break;
						}
					} else {
						p.println("");
					}

				}
				if (event.equals("LOCATION")) {
					List<WeiXinAutoReSendMenu> list = dao
							.getWeiXinAutoReSendMenuIsUse(
									WeiXinAutoReSendMenu.TYPE_EVENT,
									String.valueOf(WeiXinAutoReSendMenu.EVENT_LOCATION),
									MoreUserManager.getAppShopId(req));
					if (list.size() > 0) {
						WeiXinAutoReSendMenu wx = null;
						if (list.size() == 1) {
							wx = list.iterator().next();
						} else {
							// 如果多于一个则随机
							Random random = new Random();
							wx = list.get(random.nextInt(list.size()));
						}
						List<WeiXinAutoReSendItem> list2 = dao
								.getWeiXinAutoReSendItem(wx.getId(),
										MoreUserManager.getAppShopId(req));
						List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
						List<Integer> hive_type_list = new ArrayList<Integer>();
						for (int i = 0; i < list2.size(); i++) {
							List<WeiXinReSend> listweiresend = dao
									.getWeiXinReSendById(list2.get(i)
											.getResendid(), MoreUserManager
											.getAppShopId(req));
							if (listweiresend.size() > 0) {
								WeiXinReSend weiresend = listweiresend
										.iterator().next();

								if (weiresend.getType() == WeiXinReSend.IMAGE) {
									imagelist.add(weiresend);
									hive_type_list.add(1);
								}
								if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
									imagetextlist.add(weiresend);
									hive_type_list.add(5);
								}
								if (weiresend.getType() == WeiXinReSend.MUSIC) {
									musiclist.add(weiresend);
									hive_type_list.add(4);
								}
								if (weiresend.getType() == WeiXinReSend.TEXT) {
									textlist.add(weiresend);
									hive_type_list.add(0);
								}
								if (weiresend.getType() == WeiXinReSend.VIDEO) {
									videolist.add(weiresend);
									hive_type_list.add(3);
								}
								if (weiresend.getType() == WeiXinReSend.VOICE) {
									voicelist.add(weiresend);
									hive_type_list.add(2);
								}
							}
						}
						int num = hive_type_list.get(new Random()
								.nextInt(hive_type_list.size()));
						switch (num) {
						case 0:
							if (textlist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random()
											.nextInt(textlist.size());
								}
								WeiXinReSend resend = textlist.get(num2);
								AotuSendTextMessage msg = new AotuSendTextMessage();
								msg.setMsgType("<![CDATA[text]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setContent("<![CDATA["
										+ resend.getContent() + "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 1:
							if (imagelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(imagelist
											.size());
								}
								WeiXinReSend resend = imagelist.get(num2);
								AotuSendImageMessage msg = new AotuSendImageMessage();
								msg.setMsgType("<![CDATA[image]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setImage("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 2:
							if (voicelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(voicelist
											.size());
								}
								WeiXinReSend resend = voicelist.get(num2);
								AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
								msg.setMsgType("<![CDATA[voice]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVoice("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 3:
							if (videolist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(videolist
											.size());
								}
								WeiXinReSend resend = videolist.get(num2);
								AotuSendVideoMessage msg = new AotuSendVideoMessage();
								msg.setMsgType("<![CDATA[video]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVideo(
										"<![CDATA[" + resend.getMediaId()
												+ "]]>",
										"<![CDATA[" + resend.getTitle() + "]]>",
										"<![CDATA[" + resend.getDescription()
												+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 4:
							if (musiclist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(musiclist
											.size());
								}
								WeiXinReSend resend = musiclist.get(num2);
								AotuSendMusicMessage msg = new AotuSendMusicMessage();
								msg.setMsgType("<![CDATA[music]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
								music.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								music.setHqmusicurl("<![CDATA["
										+ resend.gethQMusicUrl() + "]]>");
								music.setMusicurl("<![CDATA["
										+ resend.getMusicURL() + "]]>");
								music.setThumb_media_id("<![CDATA["
										+ resend.getThumbMediaId() + "]]>");
								music.setTitle("<![CDATA[" + resend.getTitle()
										+ "]]>");
								msg.setMusic(music);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 5:
							if (imagetextlist.size() > 0) {
								AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
								msg.setMsgType("<![CDATA[news]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setArticleCount(String
										.valueOf(imagetextlist.size()));
								AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
								AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
										.size()];
								for (int ii = 0; ii < imagetextlist.size(); ii++) {
									WeiXinReSend resend = imagetextlist.get(ii);
									String url = resend.getUrl();
									String openid = "";
									if (url.indexOf("?") != -1) {
										openid = "&openid="
												+ text.getFromUserName();
									} else {
										openid = "?openid="
												+ text.getFromUserName();
									}
									if (url.indexOf("#") != -1) {
										url = url.split("#")[0] + openid + "#"
												+ url.split("#")[1];
									} else {
										url = url + openid;
									}
									logger.info(url);
									AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
									contents.setDescription("<![CDATA["
											+ resend.getDescription() + "]]>");
									contents.setPicUrl("<![CDATA["
											+ resend.getPicUrl() + "]]>");
									contents.setUrl("<![CDATA[" + url + "]]>");
									contents.setTitle("<![CDATA["
											+ resend.getTitle() + "]]>");
									arraytextimage[ii] = contents;
								}
								listmap.setItem(arraytextimage);
								msg.setArticles(listmap);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						default:
							p.println("");
							break;
						}
					} else {
						p.println("");
					}

				}
				if (event.equals("CLICK")) {
					int key = Integer.parseInt(text.getEventKey());
					List<WeiXinAutoReSendMenu> list = dao
							.getWeiXinAutoReSendMenuIsUse(
									WeiXinAutoReSendMenu.TYPE_EVENT,
									String.valueOf(WeiXinAutoReSendMenu.EVENT_CLICK),
									MoreUserManager.getAppShopId(req));
					logger.info(list.size() + "   " + key);
					List<WeiXinAutoReSendMenu> list3 = new ArrayList<WeiXinAutoReSendMenu>();
					for (int iii = 0; iii < list.size(); iii++) {
						if (list.get(iii).getWeixin_keys() == key) {
							logger.info(list.get(iii).getWeixin_keys() + "    "
									+ key);
							list3.add(list.get(iii));
						}
					}
					list = list3;
					logger.info(list.size() + "");
					if (list.size() > 0) {
						WeiXinAutoReSendMenu wx = null;
						if (list.size() == 1) {
							wx = list.iterator().next();
						} else {
							// 如果多于一个则随机
							Random random = new Random();
							wx = list.get(random.nextInt(list.size()));
						}
						List<WeiXinAutoReSendItem> list2 = dao
								.getWeiXinAutoReSendItem(wx.getId(),
										MoreUserManager.getAppShopId(req));
						List<WeiXinReSend> textlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagelist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> musiclist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> imagetextlist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> videolist = new ArrayList<WeiXinReSend>();
						List<WeiXinReSend> voicelist = new ArrayList<WeiXinReSend>();
						List<Integer> hive_type_list = new ArrayList<Integer>();
						for (int i = 0; i < list2.size(); i++) {
							List<WeiXinReSend> listweiresend = dao
									.getWeiXinReSendById(list2.get(i)
											.getResendid(), MoreUserManager
											.getAppShopId(req));
							if (listweiresend.size() > 0) {
								WeiXinReSend weiresend = listweiresend
										.iterator().next();

								if (weiresend.getType() == WeiXinReSend.IMAGE) {
									imagelist.add(weiresend);
									hive_type_list.add(1);
								}
								if (weiresend.getType() == WeiXinReSend.IMAGE_TEXT) {
									imagetextlist.add(weiresend);
									hive_type_list.add(5);
								}
								if (weiresend.getType() == WeiXinReSend.MUSIC) {
									musiclist.add(weiresend);
									hive_type_list.add(4);
								}
								if (weiresend.getType() == WeiXinReSend.TEXT) {
									textlist.add(weiresend);
									hive_type_list.add(0);
								}
								if (weiresend.getType() == WeiXinReSend.VIDEO) {
									videolist.add(weiresend);
									hive_type_list.add(3);
								}
								if (weiresend.getType() == WeiXinReSend.VOICE) {
									voicelist.add(weiresend);
									hive_type_list.add(2);
								}
							}
						}
						int num = hive_type_list.get(new Random()
								.nextInt(hive_type_list.size()));
						switch (num) {
						case 0:
							if (textlist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random()
											.nextInt(textlist.size());
								}
								WeiXinReSend resend = textlist.get(num2);
								AotuSendTextMessage msg = new AotuSendTextMessage();
								msg.setMsgType("<![CDATA[text]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setContent("<![CDATA["
										+ resend.getContent() + "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 1:
							if (imagelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(imagelist
											.size());
								}
								WeiXinReSend resend = imagelist.get(num2);
								AotuSendImageMessage msg = new AotuSendImageMessage();
								msg.setMsgType("<![CDATA[image]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setImage("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 2:
							if (voicelist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(voicelist
											.size());
								}
								WeiXinReSend resend = voicelist.get(num2);
								AotuSendVoiceMessage msg = new AotuSendVoiceMessage();
								msg.setMsgType("<![CDATA[voice]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVoice("<![CDATA[" + resend.getMediaId()
										+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 3:
							if (videolist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(videolist
											.size());
								}
								WeiXinReSend resend = videolist.get(num2);
								AotuSendVideoMessage msg = new AotuSendVideoMessage();
								msg.setMsgType("<![CDATA[video]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setVideo(
										"<![CDATA[" + resend.getMediaId()
												+ "]]>",
										"<![CDATA[" + resend.getTitle() + "]]>",
										"<![CDATA[" + resend.getDescription()
												+ "]]>");
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 4:
							if (musiclist.size() > 0) {
								int num2 = 0;
								if (textlist.size() > 1) {
									num2 = new Random().nextInt(musiclist
											.size());
								}
								WeiXinReSend resend = musiclist.get(num2);
								AotuSendMusicMessage msg = new AotuSendMusicMessage();
								msg.setMsgType("<![CDATA[music]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								AotuSendMusicMessageContent music = new AotuSendMusicMessageContent();
								music.setDescription("<![CDATA["
										+ resend.getDescription() + "]]>");
								music.setHqmusicurl("<![CDATA["
										+ resend.gethQMusicUrl() + "]]>");
								music.setMusicurl("<![CDATA["
										+ resend.getMusicURL() + "]]>");
								music.setThumb_media_id("<![CDATA["
										+ resend.getThumbMediaId() + "]]>");
								music.setTitle("<![CDATA[" + resend.getTitle()
										+ "]]>");
								msg.setMusic(music);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						case 5:
							if (imagetextlist.size() > 0) {
								AotuSendImageTextMessage msg = new AotuSendImageTextMessage();
								msg.setMsgType("<![CDATA[news]]>");
								msg.setCreateTime(String.valueOf(new Date()
										.getTime()));
								msg.setFromUserName("<![CDATA["
										+ text.getToUserName() + "]]>");
								msg.setToUserName("<![CDATA["
										+ text.getFromUserName() + "]]>");
								msg.setArticleCount(String
										.valueOf(imagetextlist.size()));
								AotuSendImageTextMessageContentList listmap = new AotuSendImageTextMessageContentList();
								AotuSendImageTextMessageContent[] arraytextimage = new AotuSendImageTextMessageContent[imagetextlist
										.size()];
								for (int ii = 0; ii < imagetextlist.size(); ii++) {
									WeiXinReSend resend = imagetextlist.get(ii);
									String url = resend.getUrl();
									String openid = "";
									if (url.indexOf("?") != -1) {
										openid = "&openid="
												+ text.getFromUserName();
									} else {
										openid = "?openid="
												+ text.getFromUserName();
									}
									if (url.indexOf("#") != -1) {
										url = url.split("#")[0] + openid + "#"
												+ url.split("#")[1];
									} else {
										url = url + openid;
									}
									logger.info(url);
									AotuSendImageTextMessageContent contents = new AotuSendImageTextMessageContent();
									contents.setDescription("<![CDATA["
											+ resend.getDescription() + "]]>");
									contents.setPicUrl("<![CDATA["
											+ resend.getPicUrl() + "]]>");
									contents.setUrl("<![CDATA[" + url + "]]>");
									contents.setTitle("<![CDATA["
											+ resend.getTitle() + "]]>");
									arraytextimage[ii] = contents;
								}
								listmap.setItem(arraytextimage);
								msg.setArticles(listmap);
								String xml = BeanXMLMapping.toXML(msg);
								xml = BeanXMLMapping.getWeiXinXml(xml);
								logger.info(xml);
								p.println(xml);
								break;
							}
						default:
							p.println("");
							break;
						}
					} else {
						p.println("");
					}

				}
			}
			p.flush();
			p.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getWeXinInfo")
	@ResponseBody
	public Map getWeXinInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXin> list = dao.queryWeiXin(MoreUserManager.getAppShopId(req));
		WeiXin weixin;
		if (list.size() <= 0) {
			weixin = new WeiXin();
			weixin.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(weixin);
		} else {
			weixin = list.iterator().next();
		}
		map.put("success", true);
		map.put("data", weixin);

		return map;
	}

	@RequestMapping(value = "/updateWeXinInfo")
	@ResponseBody
	public Map updateWeXinInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		String appid = req.getParameter("appid");
		String appSecret = req.getParameter("appSecret");
		String tokens = req.getParameter("tokens");
		List<WeiXin> list = dao.queryWeiXin(MoreUserManager.getAppShopId(req));
		WeiXin weixin;
		if (list.size() <= 0) {
			weixin = new WeiXin();
			weixin.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(weixin);
		} else {
			weixin = list.iterator().next();
			weixin.setAppId(appid);
			weixin.setAppSecret(appSecret);
			weixin.setTokens(tokens);
			weixin.setName(name);

			dao.update(weixin);
		}
		map.put("success", true);
		map.put("info", "设定成功");

		return map;
	}

	@RequestMapping(value = "/updataMenuToWeiXin")
	@ResponseBody
	public Map updataMenuToWeiXin(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXin> list = dao.queryWeiXin(MoreUserManager.getAppShopId(req));
		if (list.size() <= 0) {
			map.put("success", false);
			map.put("info", "错误！没有设定微信的基本信息");
		} else {
			WeiXin weixin = list.iterator().next();
			if (weixin.getAppId() == null || weixin.getAppId().equals("")) {
				map.put("success", false);
				map.put("info", "错误！没有设定微信的基本信息");
			} else {
				WeiXinMenu weixinmenubu = new WeiXinMenu();
				List<WeiXinMenuTable> listmenu = dao.queryWeiXinMenu(0,
						MoreUserManager.getAppShopId(req));
				List<WeiXinMenuButton> list_button_p = new ArrayList<WeiXinMenuButton>();
				for (int i = 0; i < listmenu.size(); i++) {
					WeiXinMenuTable weixintable = listmenu.get(i);
					WeiXinMenuButton weixinbu_p = new WeiXinMenuButton();
					weixinbu_p.setName(weixintable.getName());
					if (!weixintable.isKey_is() && !weixintable.isUrl_is()) {
						List<WeiXinMenuTable> winxinList_c = dao
								.queryWeiXinMenu(weixintable.getId(),
										MoreUserManager.getAppShopId(req));
						List<WeiXinMenuSubButton> list_c = new ArrayList<WeiXinMenuSubButton>();
						for (int ii = 0; ii < winxinList_c.size(); ii++) {
							WeiXinMenuTable weixintable_c = winxinList_c
									.get(ii);
							WeiXinMenuSubButton weixin_c = new WeiXinMenuSubButton();
							weixin_c.setName(weixintable_c.getName());
							if (weixintable_c.isKey_is()) {
								weixin_c.setKey(weixintable_c.getKeys_s());
								weixin_c.setType(weixintable_c.getType());
							} else if (weixintable_c.isUrl_is()) {
								weixin_c.setUrl(weixintable_c.getUrls_s());
								weixin_c.setType(weixintable_c.getType());
							}
							list_c.add(weixin_c);
						}
						weixinbu_p.setSub_button(list_c);
						list_button_p.add(weixinbu_p);
					} else if (weixintable.isKey_is()) {
						weixinbu_p.setKey(weixintable.getKeys_s());
						weixinbu_p.setType(weixintable.getType());
						list_button_p.add(weixinbu_p);
					} else if (weixintable.isUrl_is()) {
						weixinbu_p.setUrl(weixintable.getUrls_s());
						weixinbu_p.setType(weixintable.getType());
						list_button_p.add(weixinbu_p);
					}
				}
				weixinmenubu.setButton(list_button_p);
				JSONObject jsonweixinMenu = new JSONObject(weixinmenubu);
				logger.info(jsonweixinMenu.toString());
				try {
					String access_json = HttpKit.get(WeiXinUrl.getAccess_token(
							weixin.getAppId(), weixin.getAppSecret()));
					JSONObject jsonobj = new JSONObject(access_json);
					String access_token = (String) jsonobj.get("access_token");
					logger.info(access_token);
					JSONObject jsonrequest = new JSONObject(HttpKit.post(
							WeiXinUrl.createMenu(access_token),
							jsonweixinMenu.toString()));
					int str = jsonrequest.getInt("errcode");
					logger.info(str + "");
					if (str == 0) {
						map.put("success", true);
						map.put("info", "创建完成");
					} else {
						map.put("success", false);
						map.put("info",
								"创建失败！原因：" + WeiXinErrorCode.getString(str));
					}
				} catch (Exception e) {
					map.put("success", false);
					map.put("info", "创建失败，请查看是否在微信公众账号中设置的参数是否正确。");
				}
			}
		}

		return map;
	}

	@RequestMapping(value = "/getMenu")
	@ResponseBody
	public Map getMenu(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		long idlong = 0;
		if (id != null) {
			idlong = Long.parseLong(id);
		}
		List<WeiXinMenuTable> list = dao.queryWeiXinMenu(idlong,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/addMenu")
	@ResponseBody
	public Map addMenu(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String key = req.getParameter("key");
		long idlong = 0;
		if (id != null) {
			idlong = Long.parseLong(id);
		}
		List<WeiXinMenuTable> list = dao.queryWeiXinMenu(idlong,
				MoreUserManager.getAppShopId(req));
		if ((list.size() < 3 && idlong == 0)
				|| (list.size() < 7 && idlong != 0)) {
			WeiXinMenuTable wei = new WeiXinMenuTable();
			wei.setName(name);
			wei.setPid(idlong);
			if (url != null && !url.trim().equals("")) {
				wei.setUrl_is(true);
				wei.setUrls_s(url);
				wei.setKey_is(false);
				wei.setType("view");
			} else if (key != null && !key.trim().equals("")) {
				wei.setKey_is(true);
				wei.setKeys_s(key);
				wei.setUrl_is(false);
				wei.setType("click");
			} else {
				wei.setUrl_is(false);
				wei.setKey_is(false);
			}
			wei.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(wei);
			map.put("success", true);
			map.put("data", wei);
			map.put("info", "添加完成");
		} else {
			map.put("success", false);
			map.put("info", "一级菜单最多添加3个二级菜单最多7个");
		}

		return map;
	}

	@RequestMapping(value = "/deleteMenu")
	@ResponseBody
	public Map deleteMenu(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		long idlong = 0;
		if (id != null) {
			idlong = Long.parseLong(id);
		} else {
			map.put("success", false);
			map.put("info", "删除错误");
			return map;
		}
		List<WeiXinMenuTable> list = dao.queryWeiXinMenuById(idlong,
				MoreUserManager.getAppShopId(req));
		if (list.iterator().hasNext()) {
			WeiXinMenuTable wei = list.iterator().next();
			if (wei.getPid() == 0) {
				List<WeiXinMenuTable> lis = dao.queryWeiXinMenu(wei.getId(),
						MoreUserManager.getAppShopId(req));
				Iterator<WeiXinMenuTable> i = lis.iterator();
				while (i.hasNext()) {
					dao.delete(i.next());
				}
			}
			dao.delete(wei);
			map.put("success", true);
			map.put("info", "删除成功");
			return map;
		} else {
			map.put("success", false);
			map.put("info", "删除错误,该菜单不存在");
		}

		return map;
	}

	@RequestMapping(value = "/editMenu")
	@ResponseBody
	public Map editMenu(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String key = req.getParameter("key");
		long idlong = 0;
		if (id != null) {
			idlong = Long.parseLong(id);
		}
		List<WeiXinMenuTable> list = dao.queryWeiXinMenuById(idlong,
				MoreUserManager.getAppShopId(req));
		if (list.iterator().hasNext()) {
			WeiXinMenuTable wei = list.iterator().next();
			wei.setName(name);
			if (url != null && !url.trim().equals("")) {
				wei.setUrl_is(true);
				wei.setUrls_s(url);
				wei.setKey_is(false);
				wei.setType("view");
				wei.setKeys_s("");
			} else if (key != null && !key.trim().equals("")) {
				wei.setKey_is(true);
				wei.setKeys_s(key);
				wei.setUrl_is(false);
				wei.setType("click");
				wei.setUrls_s("");
			} else {
				wei.setUrl_is(false);
				wei.setKey_is(false);
			}
			dao.update(wei);
			map.put("success", true);
			map.put("info", "更新完成");
		} else {
			map.put("success", false);
			map.put("info", "该菜单不存在");
		}

		return map;
	}

	@RequestMapping(value = "/getMenuItem")
	@ResponseBody
	public Map getMenuItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");

		long idlong = 0;
		if (id != null) {
			idlong = Long.parseLong(id);
		}
		List<WeiXinMenuTable> list = dao.queryWeiXinMenuById(idlong,
				MoreUserManager.getAppShopId(req));
		if (list.iterator().hasNext()) {
			WeiXinMenuTable wei = list.iterator().next();
			map.put("success", true);
			map.put("data", wei);
		} else {
			map.put("success", false);
			map.put("info", "该菜单不存在");
		}

		return map;
	}

	@RequestMapping(value = "/getImage")
	@ResponseBody
	public Map getImage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinReSend> data = dao.queryWeiXinImage(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);

		return map;
	}

	@RequestMapping(value = "/getVoice")
	@ResponseBody
	public Map getVoice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		List<WeiXinReSend> data = dao.queryWeiXinVoice(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);
		return map;
	}

	@RequestMapping(value = "/getVideo")
	@ResponseBody
	public Map getVideo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		List<WeiXinReSend> data = dao.queryWeiXinVideo(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);

		return map;
	}

	@RequestMapping(value = "/getText")
	@ResponseBody
	public Map getText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinReSend> data = dao.queryWeiXinText(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);

		return map;
	}

	@RequestMapping(value = "/getMusic")
	@ResponseBody
	public Map getMusic(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinReSend> data = dao.queryWeiXinMusic(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);

		return map;
	}

	@RequestMapping(value = "/getImageText")
	@ResponseBody
	public Map getImageText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinReSend> data = dao.queryWeiXinImageText(MoreUserManager
				.getAppShopId(req));
		map.put("success", true);
		map.put("data", data);

		return map;
	}

	@RequestMapping(value = "/addImage")
	@ResponseBody
	public Map upLoadImage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setType(WeiXinReSend.IMAGE);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/addVideo")
	@ResponseBody
	public Map upLoadVideo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setTitle(title);
		wr.setDescription(description);
		wr.setType(WeiXinReSend.VIDEO);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/addVoice")
	@ResponseBody
	public Map upLoadVoice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setType(WeiXinReSend.VOICE);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/addText")
	@ResponseBody
	public Map addText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String content = req.getParameter("content");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setContent(content);
		wr.setType(WeiXinReSend.TEXT);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/addMusic")
	@ResponseBody
	public Map addMusic(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String hQMusicUrl = req.getParameter("hQMusicUrl");
		String thumbMediaId = req.getParameter("thumbMediaId");
		String musicURL = req.getParameter("musicURL");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setDescription(description);
		wr.sethQMusicUrl(hQMusicUrl);
		wr.setMusicURL(musicURL);
		wr.setThumbMediaId(thumbMediaId);
		wr.setTitle(title);
		wr.setType(WeiXinReSend.MUSIC);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/addImageText")
	@ResponseBody
	public Map addImageText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String picUrl = req.getParameter("picUrl");
		String url = req.getParameter("url");
		WeiXinReSend wr = new WeiXinReSend();
		wr.setName(name);
		wr.setDescription(description);
		wr.setPicUrl(picUrl);
		wr.setUrl(url);
		wr.setType(WeiXinReSend.IMAGE_TEXT);
		wr.setTitle(title);
		wr.setConpanyId(MoreUserManager.getAppShopId(req));
		dao.add(wr);
		map.put("success", true);
		map.put("info", "添加成功");

		return map;
	}

	@RequestMapping(value = "/getWeiXinReSend")
	@ResponseBody
	public Map getWeiXinReSend(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		if (list.size() <= 0) {
			map.put("success", false);
			map.put("info", "没有该数据");
		} else {
			map.put("success", true);
			map.put("obj", list.iterator().next());
		}

		return map;
	}

	@RequestMapping(value = "/updateImage")
	@ResponseBody
	public Map updateImage(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setType(WeiXinReSend.IMAGE);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/updateVideo")
	@ResponseBody
	public Map updateVideo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setTitle(title);
		wr.setDescription(description);
		wr.setType(WeiXinReSend.VIDEO);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/updateVoice")
	@ResponseBody
	public Map updateVoice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String mediaId = req.getParameter("mediaId");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setMediaId(mediaId);
		wr.setType(WeiXinReSend.VOICE);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/updateText")
	@ResponseBody
	public Map updateText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String content = req.getParameter("content");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setContent(content);
		wr.setType(WeiXinReSend.TEXT);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/updateMusic")
	@ResponseBody
	public Map updateMusic(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String hQMusicUrl = req.getParameter("hQMusicUrl");
		String thumbMediaId = req.getParameter("thumbMediaId");
		String musicURL = req.getParameter("musicURL");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setDescription(description);
		wr.sethQMusicUrl(hQMusicUrl);
		wr.setMusicURL(musicURL);
		wr.setThumbMediaId(thumbMediaId);
		wr.setTitle(title);
		wr.setType(WeiXinReSend.MUSIC);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/updateImageText")
	@ResponseBody
	public Map updateImageText(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String picUrl = req.getParameter("picUrl");
		String url = req.getParameter("url");
		WeiXinReSend wr = list.iterator().next();
		wr.setName(name);
		wr.setDescription(description);
		wr.setPicUrl(picUrl);
		wr.setUrl(url);
		wr.setType(WeiXinReSend.IMAGE_TEXT);
		wr.setTitle(title);
		dao.update(wr);
		map.put("success", true);
		map.put("info", "更新成功");

		return map;
	}

	@RequestMapping(value = "/deleteReSend")
	@ResponseBody
	public Map deleteReSend(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		String id = req.getParameter("id");
		List<WeiXinReSend> list = dao.getWeiXinReSendById(Long.parseLong(id),
				MoreUserManager.getAppShopId(req));

		WeiXinReSend wr = list.iterator().next();

		dao.delete(wr);
		map.put("success", true);
		map.put("info", "删除成功");

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Text")
	@ResponseBody
	public Map getAutoReSend_Text(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_TEXT,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Image")
	@ResponseBody
	public Map getAutoReSend_Image(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_IMAGE,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Link")
	@ResponseBody
	public Map getAutoReSend_Link(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_LINK,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Location")
	@ResponseBody
	public Map getAutoReSend_Location(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_LOCATION,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Event")
	@ResponseBody
	public Map getAutoReSend_Event(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_EVENT,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Video")
	@ResponseBody
	public Map getAutoReSend_Video(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_VIDEO,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/getAutoReSend_Voice")
	@ResponseBody
	public Map getAutoReSend_Voice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenu(
				WeiXinAutoReSendMenu.TYPE_VOICE,
				MoreUserManager.getAppShopId(req));
		map.put("success", true);
		map.put("data", list);

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Text")
	@ResponseBody
	public Map addAutoReSend_Text(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_TEXT);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Image")
	@ResponseBody
	public Map addAutoReSend_Image(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_IMAGE);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Link")
	@ResponseBody
	public Map addAutoReSend_Link(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_LINK);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Location")
	@ResponseBody
	public Map addAutoReSend_Location(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_LOCATION);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Event")
	@ResponseBody
	public Map addAutoReSend_Event(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			String event = req.getParameter("event");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setWeixin_keys(Long.parseLong(content));
			menu.setName(name);
			menu.setWeixin_events(event);
			menu.setContent(content);
			menu.setType(WeiXinAutoReSendMenu.TYPE_EVENT);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		}catch (NumberFormatException ex) {
			// TODO: handle exception
			map.put("success", false);
			map.put("info", "检测内容只能是数字");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Video")
	@ResponseBody
	public Map addAutoReSend_Video(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_VIDEO);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/addAutoReSend_Voice")
	@ResponseBody
	public Map addAutoReSend_Voice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = new WeiXinAutoReSendMenu();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_VOICE);
			menu.setUses(false);
			menu.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(menu);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/getAutoReSend")
	@ResponseBody
	public Map getAutoReSend(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenuById(
					Long.parseLong(id), MoreUserManager.getAppShopId(req));
			if (list.size() > 0) {
				map.put("success", true);
				map.put("obj", list.iterator().next());
			} else {
				map.put("success", false);
				map.put("info", "获取失败,可能已经不存在");
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "获取失败");
		}

		return map;
	}

	@RequestMapping(value = "/addWeiXinInfoToAutoResend")
	@ResponseBody
	public Map addWeiXinInfoToAutoResend(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("info_name");
			String info_name = req.getParameter("info_name");
			String info_id = req.getParameter("info_id");
			WeiXinAutoReSendItem weixin = new WeiXinAutoReSendItem();
			weixin.setResendid(Long.parseLong(info_id));
			weixin.setName(name);
			weixin.setAoturesendId(Long.parseLong(id));
			weixin.setConpanyId(MoreUserManager.getAppShopId(req));
			dao.add(weixin);
			map.put("success", true);
			map.put("info", "添加成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}

		return map;
	}

	@RequestMapping(value = "/getAutoReSendItem")
	@ResponseBody
	public Map getAutoReSendItem(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			List<WeiXinAutoReSendItem> list = dao.getWeiXinAutoReSendItem(
					Long.parseLong(id), MoreUserManager.getAppShopId(req));
			map.put("success", true);
			map.put("data", list);
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "获取失败");
		}

		return map;
	}

	@RequestMapping(value = "/deleteWeiXinInfoToAutoResend")
	@ResponseBody
	public Map deleteWeiXinInfoToAutoResend(Model model,
			HttpServletRequest req, HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			List<WeiXinAutoReSendItem> list = dao.getWeiXinAutoReSendItemById(
					Long.parseLong(id), MoreUserManager.getAppShopId(req));
			dao.delete(list.iterator().next());
			map.put("success", true);
			map.put("info", "删除成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "删除失败");
		}

		return map;
	}

	@RequestMapping(value = "/deleteWeiXinInfo")
	@ResponseBody
	public Map deleteWeiXinInfo(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			List<WeiXinAutoReSendMenu> list = dao.getWeiXinAutoReSendMenuById(
					Long.parseLong(id), MoreUserManager.getAppShopId(req));
			dao.delete(list.iterator().next());
			List<WeiXinAutoReSendItem> list2 = dao.getWeiXinAutoReSendItem(
					Long.parseLong(id), MoreUserManager.getAppShopId(req));
			Iterator i = list2.iterator();
			while (i.hasNext()) {
				dao.delete(i.next());
			}
			map.put("success", true);
			map.put("info", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("info", "删除失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Text")
	@ResponseBody
	public Map updateAutoReSend_Text(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_TEXT);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Image")
	@ResponseBody
	public Map updateAutoReSend_Image(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_IMAGE);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Link")
	@ResponseBody
	public Map updateAutoReSend_Link(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_LINK);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Location")
	@ResponseBody
	public Map updateAutoReSend_Location(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_LOCATION);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Event")
	@ResponseBody
	public Map updateAutoReSend_Event(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			String event = req.getParameter("event");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setWeixin_keys(Long.parseLong(content));
			menu.setName(name);
			menu.setWeixin_events(event);
			menu.setContent(content);
			menu.setType(WeiXinAutoReSendMenu.TYPE_EVENT);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Video")
	@ResponseBody
	public Map updateAutoReSend_Video(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_VIDEO);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}

		return map;
	}

	@RequestMapping(value = "/updateAutoReSend_Voice")
	@ResponseBody
	public Map updateAutoReSend_Voice(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setContent(content);
			menu.setName(name);
			menu.setType(WeiXinAutoReSendMenu.TYPE_VOICE);
			menu.setId(Long.parseLong(id));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}
		return map;
	}

	@RequestMapping(value = "/updateUse")
	@ResponseBody
	public Map updateUse(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();

		try {
			String id = req.getParameter("id");
			String value = req.getParameter("value");
			WeiXinAutoReSendMenu menu = dao
					.getWeiXinAutoReSendMenuById(Long.parseLong(id),
							MoreUserManager.getAppShopId(req)).iterator()
					.next();
			menu.setUses(Boolean.parseBoolean(value));
			dao.update(menu);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}
		return map;
	}

	@RequestMapping(value = "/sendWenzhang")
	@ResponseBody
	public Map sendWenzhang(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		try {
			String wenzhangname = req.getParameter("wenzhangname");
			String wenzhangcontent = req.getParameter("wenzhangcontent");
			String strjson = req.getParameter("strjson");
			VoteDTO votedto = null;
			if (!strjson.equals("1")) {
				votedto = DTOUtil.getVoteDTO(strjson);
			}
			WebPublicMessage web = new WebPublicMessage();
			web.setConpanyId(users.getConpanyId());
			web.setContent(wenzhangcontent);
			web.setStartdate(new Date());
			web.setLinkaddress("http://www.xt12306.com/weixin/public/wenzhang?conpanyId="
					+ users.getConpanyId() + "&wenzhangId=");
			web.setName(wenzhangname);
			web.setLooknum(0);
			dao.add(web);

			if (votedto != null) {
				Vote vote = new Vote();
				long conpanyId = users.getConpanyId();
				vote.setConpanyId(conpanyId);
				vote.setEndDate(votedto.getEndDate());
				vote.setName(wenzhangname);
				vote.setOnes(votedto.isOnes());
				vote.setPublics(votedto.isPublics());
				vote.setStardate(votedto.getStartDate());
				vote.setWenzhangId(web.getId());
				List<VoteItem> list2 = new ArrayList<VoteItem>();
				Iterator<VoteItemDTO> i = votedto.getVoteItem().iterator();
				while (i.hasNext()) {
					VoteItemDTO itemDto = i.next();
					VoteItem item = new VoteItem();
					item.setName(itemDto.getName());
					item.setConpanyId(users.getConpanyId());
					item.setNum(0);
					item.setVote(vote);
					list2.add(item);
				}
				vote.setVoteItem(list2);
				dao.add(vote);
				web.setVote(true);
			} else {
				web.setVote(false);
			}
			web.setLinkaddress(web.getLinkaddress() + web.getId());
			dao.update(web);
			map.put("success", true);
			map.put("info", "添加成功");

		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "添加失败");
		}
		return map;
	}

	@RequestMapping(value = "/getWenzhang")
	@ResponseBody
	public Map getWenzhang(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		try {
			String id = req.getParameter("id");
			WebPublicMessage web = (WebPublicMessage) dao.getObject(
					Long.parseLong(id), "WebPublicMessage",
					users.getConpanyId());
			Vote vote = null;
			List<Object> list2 = dao.getObjectList("Vote", "where wenzhangId="
					+ id + " and ConpanyId=" + users.getConpanyId());
			if (list2.iterator().hasNext()) {
				vote = (Vote) list2.iterator().next();
			}
			map.put("success", true);
			map.put("obj", web);
			map.put("obj2", vote);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("info", "查找失败");
		}
		return map;
	}

	@RequestMapping(value = "/getVoteUserList")
	@ResponseBody
	public Map getVoteUserList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num = dao.getObjectListNum("VoteUser", "where voteId=" + id);
		List list = dao.getObjectListPage("VoteUser", "where voteId=" + id,
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num / Integer.parseInt(countNum) + 1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/updateWenzhang")
	@ResponseBody
	public Map updateWenzhang(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		try {
			String id = req.getParameter("id");
			String wenzhangname = req.getParameter("wenzhangname");
			String wenzhangcontent = req.getParameter("wenzhangcontent");
			String strjson = req.getParameter("strjson");
			VoteDTO votedto = null;
			if (!strjson.equals("1")) {
				votedto = DTOUtil.getVoteDTO(strjson);
			}
			WebPublicMessage web = (WebPublicMessage) dao.getObject(
					Long.parseLong(id), "WebPublicMessage",
					users.getConpanyId());
			web.setContent(wenzhangcontent);
			web.setName(wenzhangname);
			if (votedto == null) {
				if (web.isVote()) {
					List<Object> list2 = dao.getObjectList("Vote",
							"where wenzhangId=" + web.getId()
									+ " and ConpanyId=" + users.getConpanyId());
					if (list2.iterator().hasNext()) {
						dao.delete(list2.iterator().next());
					}
				}
			} else {
				if (web.isVote()) {
					Vote vote = null;
					List<Object> list2 = dao.getObjectList("Vote",
							"where wenzhangId=" + id + " and ConpanyId="
									+ users.getConpanyId());
					if (list2.iterator().hasNext()) {
						vote = (Vote) list2.iterator().next();
						vote.setEndDate(votedto.getEndDate());
						vote.setName(wenzhangname);
						vote.setOnes(votedto.isOnes());
						vote.setPublics(votedto.isPublics());
						vote.setStardate(votedto.getStartDate());
						dao.update(vote);
					} else {
						vote = new Vote();
						long conpanyId = users.getConpanyId();
						vote.setConpanyId(conpanyId);
						vote.setEndDate(votedto.getEndDate());
						vote.setName(wenzhangname);
						vote.setOnes(votedto.isOnes());
						vote.setPublics(votedto.isPublics());
						vote.setStardate(votedto.getStartDate());
						vote.setWenzhangId(web.getId());
						dao.add(vote);
					}

					List<VoteItem> list3 = new ArrayList<VoteItem>();
					Iterator<VoteItemDTO> i = votedto.getVoteItem().iterator();
					while (i.hasNext()) {

						VoteItemDTO itemDto = i.next();
						VoteItem item = (VoteItem) dao.getObject(
								itemDto.getId(), "VoteItem",
								users.getConpanyId());
						if (item != null) {
							item.setName(itemDto.getName());
							dao.update(item);
						} else {
							item = new VoteItem();
							item.setName(itemDto.getName());
							item.setConpanyId(users.getConpanyId());
							item.setNum(0);
							item.setVote(vote);
							dao.add(item);
						}
						list3.add(item);
					}
					// 删除选项
					for (int ii = 0; ii < vote.getVoteItem().size(); ii++) {
						VoteItem voteitems = vote.getVoteItem().get(ii);
						boolean isext = false;
						for (int iii = 0; iii < list3.size(); iii++) {
							VoteItem voteitems2 = list3.get(iii);
							if (voteitems2.getId() == voteitems.getId()) {
								isext = true;
							}
						}
						if (!isext) {
							dao.delete(voteitems);
						}
					}
				} else {
					Vote vote = new Vote();
					long conpanyId = users.getConpanyId();
					vote.setConpanyId(conpanyId);
					vote.setEndDate(votedto.getEndDate());
					vote.setName(wenzhangname);
					vote.setOnes(votedto.isOnes());
					vote.setPublics(votedto.isPublics());
					vote.setStardate(votedto.getStartDate());
					vote.setWenzhangId(web.getId());
					List<VoteItem> list2 = new ArrayList<VoteItem>();
					Iterator<VoteItemDTO> i = votedto.getVoteItem().iterator();
					while (i.hasNext()) {
						VoteItemDTO itemDto = i.next();
						VoteItem item = new VoteItem();
						item.setName(itemDto.getName());
						item.setConpanyId(users.getConpanyId());
						item.setNum(0);
						item.setVote(vote);
						list2.add(item);
					}
					vote.setVoteItem(list2);
					dao.add(vote);
					web.setVote(true);
				}
			}
			dao.update(web);
			map.put("success", true);
			map.put("info", "更新成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("info", "更新失败");
		}
		return map;
	}

	@RequestMapping(value = "/getWenzhangList")
	@ResponseBody
	public Map getWenzhangList(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String name = req.getParameter("name");
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		String nowpage = req.getParameter("nowpage");
		String countNum = req.getParameter("countNum");
		if (nowpage == null) {
			nowpage = "1";
		}
		if (countNum == null) {
			countNum = "30";
		}
		long num = dao.getObjectListNum("WebPublicMessage", "where conpanyId="
				+ users.getConpanyId() + " and name like '%" + name + "%'");
		List list = dao.getObjectListPage("WebPublicMessage",
				"where conpanyId=" + users.getConpanyId() + " and name like '%"
						+ name + "%'order by startdate desc",
				Integer.parseInt(nowpage), Integer.parseInt(countNum));
		map.put("pagenum", num / Integer.parseInt(countNum) + 1);
		map.put("success", true);
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/deleteWenzhang")
	@ResponseBody
	public Map deleteWenzhang(Model model, HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap<String, Object>();
		String id = req.getParameter("id");
		ConpanyUser users = (ConpanyUser) req.getSession().getAttribute(
				SessionString.USER_OBJ);
		try {
			WebPublicMessage wm = (WebPublicMessage) dao.getObject(
					Long.parseLong(id), "WebPublicMessage",
					users.getConpanyId());
			if (wm == null) {
				map.put("success", false);
				map.put("info", "删除失败。文章不存在，或者已经删除。尝试刷新页面");
			} else {
				dao.delete(wm);
				map.put("success", true);
				map.put("info", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("success", false);
			map.put("info", "删除失败。");
		}

		return map;
	}
}
