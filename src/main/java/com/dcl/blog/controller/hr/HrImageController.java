package com.dcl.blog.controller.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcl.blog.controller.mode.FileUploadStatus;
import com.dcl.blog.model.ConpanyUser;
import com.dcl.blog.service.DaoService;
import com.dcl.blog.util.FileManager;
import com.dcl.blog.util.SessionString;
import com.dcl.blog.util.email.emailimpl;
@Controller
@RequestMapping("/hr/page/fileManager")
public class HrImageController {
	private static final Logger logger = LoggerFactory
			.getLogger(HrImageController.class);
	private DaoService dao;
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
	 * 上传用户图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadIdImage")
	@ResponseBody
	public Map IduploadFile(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		Map<String, Object> map = new HashMap<String, Object>();
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_ID_IMAGE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//最大文件大小
		long maxSize = 2000000;
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
				 map.put("info", "上传文件大小超过限制。最高位2m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  	 map.put("success", false);
				 map.put("info", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
				 return map;
		  }
		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		  String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		  FileUploadStatus fus=new FileUploadStatus();
		  fus.setFileName(newFileName);
		  fus.setFileNameCountSize(fileSize);
		  fus.setPath(savePath);
		  request.getSession().setAttribute("IdImageFileUpLoad", fus);
		  try{
		   File uploadedFile = new File(savePath, newFileName);
		   item.write(uploadedFile);
		   
		  }catch(Exception e){
		  // out.println(getError("上传文件失败。"));
			  map.put("success", false);
			  map.put("info", "上传文件失败。");
			  return map;
		  }
		  map.put("success", true);
		  map.put("url", newFileName);
		  return map;
		 }
		}
		return map;
	}
	/**
	 * 查看用户图片上传进度
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/IduploadImagePro")
	@ResponseBody
	public Map IduploadImagePro(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		FileUploadStatus fileup=(FileUploadStatus) request.getSession().getAttribute("IdImageFileUpLoad");
		File uploadedFile = new File(fileup.getPath(), fileup.getFileName());
		long val=uploadedFile.length()/fileup.getFileNameCountSize()*100;
		map.put("data", val);
		return map;
	}
	/**
	 * 获取身份证图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getIdImage")
	@ResponseBody
	public void IdImage(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		String fileName=req.getParameter("filename");
		fileName=new String(fileName.getBytes("iso-8859-1"),"utf-8");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_ID_IMAGE+File.separatorChar+fileName);
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[1024];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
	/**
	 * 上传用户图片
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage")
	@ResponseBody
	public Map uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ConpanyUser users=(ConpanyUser) request.getSession().getAttribute(SessionString.USER_OBJ);
		Map<String, Object> map = new HashMap<String, Object>();
		String savePath = FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_IMAGE+File.separatorChar;
		//String saveUrl  = request.getContextPath() + "/fileSrc/getOrgImg";
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//最大文件大小
		long maxSize = 2000000;
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
				 map.put("info", "上传文件大小超过限制。最高位2m");
				 return map;
		  }
		  //检查扩展名
		  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
			  	// out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			  	 map.put("success", false);
				 map.put("info", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。");
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
		   
		  }catch(Exception e){
		  // out.println(getError("上传文件失败。"));
			  map.put("success", false);
			  map.put("info", "上传文件失败。");
			  return map;
		  }
		  map.put("success", true);
		  map.put("url", newFileName);
		  return map;
		 }
		}
		return map;
	}
	/**
	 * 查看用户图片上传进度
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImagePro")
	@ResponseBody
	public Map uploadImagePro(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		FileUploadStatus fileup=(FileUploadStatus) request.getSession().getAttribute("ImageFileUpLoad");
		File uploadedFile = new File(fileup.getPath(), fileup.getFileName());
		long val=uploadedFile.length()/fileup.getFileNameCountSize()*100;
		map.put("data", val);
		return map;
	}
	/**
	 * 获取员工图片
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	@RequestMapping("/getImage")
	@ResponseBody
	public void getImage(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		String fileName=req.getParameter("filename");
		fileName=new String(fileName.getBytes("iso-8859-1"),"utf-8");
		ConpanyUser users=(ConpanyUser) req.getSession().getAttribute(SessionString.USER_OBJ);
		File file=new File(FileManager.SAAS+users.getConpanyId()+File.separatorChar+FileManager.CUSTEMMER_IMAGE+File.separatorChar+fileName);
		InputStream in=new FileInputStream(file);
		byte[] buffer = new byte[1024];
		ServletOutputStream sos = res.getOutputStream();
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
		      sos.write(buffer, 0, i);
		}
		sos.flush();
		sos.close();
		in.close();
	}
}
