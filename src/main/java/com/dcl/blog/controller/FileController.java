package com.dcl.blog.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
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

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.helper.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.controller.mode.FileUploadStatus;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.model.ConpanyZoneImage;
import com.dcl.blog.model.ImageList;
import com.dcl.blog.model.dto.ImageDTO;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.Code;
import com.dcl.blog.util.DateUtil;
import com.dcl.blog.util.FileManager;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
/**
 * 负责文件的加载
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/fileSrc")
public class FileController {
	private DaoService dao;
	private static final Logger logger = LoggerFactory
			.getLogger(FileController.class);
	@Resource
	public void setDao(DaoService dao) {
		this.dao = dao;
	}
	/**
	 * 获取验证码图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/codeImage")
	public void getCodeImage(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		Code code = new Code();
		Map<String, Object> map = code.getImage();
		// 验证码
		req.getSession().setAttribute(SessionString.SEESION_CODE,
				map.get(Code.CODE));
		ServletOutputStream sos = res.getOutputStream();
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		ImageIO.write((BufferedImage) map.get(Code.IMG), "jpeg", sos);
		sos.flush();
		sos.close();
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
	public Map uploadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(users==null){
			map.put("error", 1);
			map.put("message", "请登录后再上传");
		 return map;
		}
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_INFOIMAGE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//最大文件大小
		long maxSize = 2000000;
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			map.put("error", 1);
			map.put("message", "请选择文件。");
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
			  	 map.put("error", 1);
				 map.put("message", "上传文件大小超过限制。最高位2m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  	 map.put("success", 1);
				 map.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
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
			   
			   ImageList ima=new ImageList();
			   ima.setStartDate(new Date());
			   ima.setLinkaddress(savePath+newFileName);
			   ima.setFilesize(item.getSize());
			   ima.setConpanyId(users.getConpanyId());
			   ima.setFiletype(fileExt);
			   ima.setUserId(users.getId());
			   ima.setUserName(users.getTrueName());
			   ima.setFileName(fileName);
			   ima.setType("1");
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getLinkaddress())));
			   ima.setMd5(md5);
			   dao.add(ima);
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/weixin/public/getImage?id="+ima.getId();
			   ima.setWwwLinkAddress(basePath);
			   dao.update(ima);
			   map.put("error", 0);
			   map.put("url",basePath);
			  return map;
		  }catch(Exception e){
			  map.put("error", 1);
			  map.put("message", "上传文件失败。");
			  return map;
		  }
		 }
		}
		return map;
	}
	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage_kongjian")
	@ResponseBody
	public Map uploadImage_kongjian(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(users==null){
			map.put("success", false);
			map.put("message", "请登录后再上传");
		 return map;
		}
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_INFOIMAGE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//最大文件大小
		long maxSize = 5000000;
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
				 map.put("message", "上传图片大小超过限制。最高位5m");
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
			   ImageList ima=new ImageList();
			   ima.setStartDate(new Date());
			   ima.setLinkaddress(savePath+newFileName);
			   ima.setFilesize(item.getSize());
			   ima.setConpanyId(users.getConpanyId());
			   ima.setFiletype(fileExt);
			   ima.setUserId(users.getId());
			   ima.setUserName(users.getTrueName());
			   ima.setFileName(fileName);
			   ima.setType("1");
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getLinkaddress())));
			   ima.setMd5(md5);
			   dao.add(ima);
			   
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/weixin/public/getImage?id="+ima.getId();
			   ima.setWwwLinkAddress(basePath);
			   dao.update(ima);
			   map.put("url",basePath);
			   map.put("systemLink",ima.getLinkaddress());
			   map.put("filename", fileName);
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
		
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(users==null){
			map.put("success", false);
			map.put("message", "请登录后再上传");
		 return map;
		}
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_VIDEO+File.separatorChar;
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
			   ImageList ima=new ImageList();
			   ima.setStartDate(new Date());
			   ima.setLinkaddress(savePath+newFileName);
			   ima.setFilesize(item.getSize());
			   ima.setConpanyId(users.getConpanyId());
			   ima.setFiletype(fileExt);
			   ima.setUserId(users.getId());
			   ima.setFileName(fileName);
			   ima.setType("2");
			   ima.setUserName(users.getTrueName());
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getLinkaddress())));
			   ima.setMd5(md5);
			   dao.add(ima);
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/weixin/public/getVideo?id="+ima.getId();
			   ima.setWwwLinkAddress(basePath);
			   dao.update(ima);
			   map.put("success", true);
			   map.put("url",basePath);
			   map.put("systemLink",ima.getLinkaddress());
			   map.put("filename", fileName);
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
	 * 上传文件
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Map uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(users==null){
			map.put("success", false);
			map.put("message", "请登录后再上传");
		 return map;
		}
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_FILE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "doc,txt,ppt,psd,mm,xls,java,js,jar,war,html,class,xml,pdf");
		//最大文件大小
		long maxSize = 5000000;
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			map.put("success", false);
			map.put("message", "请选择文件。");
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
				 map.put("message", "上传文件大小超过限制。最高位5m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  map.put("success", false);
				 map.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
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
			   ImageList ima=new ImageList();
			   ima.setStartDate(new Date());
			   ima.setLinkaddress(savePath+newFileName);
			   ima.setFilesize(item.getSize());
			   ima.setConpanyId(users.getConpanyId());
			   ima.setFiletype(fileExt);
			   ima.setFileName(fileName);
			   ima.setUserId(users.getId());
			   ima.setUserName(users.getTrueName());
			   ima.setType("3");
			   String md5=DigestUtils.md5Hex(new FileInputStream(new File(ima.getLinkaddress())));
			   ima.setMd5(md5);
			   dao.add(ima);
			   String path = request.getContextPath();
			   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/weixin/public/getFile?id="+ima.getId();
			   ima.setWwwLinkAddress(basePath);
			   dao.update(ima);
			   map.put("success", true);
			   map.put("url",basePath);
			   map.put("systemLink",ima.getLinkaddress());
			   map.put("filename", fileName);
			  return map;
		  }catch(Exception e){
			  map.put("success", false);
			  map.put("message", "上传文件失败。");
			  return map;
		  }
		 }
		}
		return map;
	}
	/**
	 * 查询图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/getImageList")
	@ResponseBody
	public Map getImageList(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		if(users==null){
			map.put("error", 1);
			map.put("message", "请登录后再查看");
		 return map;
		}
		List<Object> list=dao.getObjectList("ImageList", "where conpanyId="+users.getConpanyId());
		Iterator i=list.iterator();
		List<ImageDTO> list2=new ArrayList<ImageDTO>();
		int num=0;
		 String path = request.getContextPath();
		  
		while(i.hasNext()){
			 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/weixin/public/getImage?id=";
			ImageList im=(ImageList) i.next();
			ImageDTO dto=new ImageDTO();
			basePath=basePath+im.getId();
			dto.setDatetime(DateUtil.formatDateYYYY_MM_DD(im.getStartDate()));
			dto.setFilename(basePath);
			dto.setDir_path(basePath);
			dto.setIs_dir(false);
			dto.setIs_photo(true);
			dto.setHas_file(false);
			dto.setFiletype(im.getFiletype());
			dto.setFilesize(im.getFilesize());
			list2.add(dto);
			num++;
		}
		map.put("moveup_dir_path", "");
		map.put("current_dir_path", "");
		map.put("current_url", "");
		map.put("total_count", num);
		map.put("file_list", list2);
		return map;
	}
}
