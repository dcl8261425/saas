package com.dcl.blog.model.dto;

import java.util.Date;

public class ImageDTO {
	private boolean is_dir;
	private boolean has_file;
	private long filesize;
	private String dir_path;
	private boolean is_photo;
	private String filetype;
	private String filename;
	private String datetime;
	public boolean isIs_dir() {
		return is_dir;
	}
	public void setIs_dir(boolean is_dir) {
		this.is_dir = is_dir;
	}
	public boolean isHas_file() {
		return has_file;
	}
	public void setHas_file(boolean has_file) {
		this.has_file = has_file;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getDir_path() {
		return dir_path;
	}
	public void setDir_path(String dir_path) {
		this.dir_path = dir_path;
	}
	public boolean isIs_photo() {
		return is_photo;
	}
	public void setIs_photo(boolean is_photo) {
		this.is_photo = is_photo;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
}
