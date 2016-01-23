package com.dcl.blog.dao;

import java.util.List;

import com.dcl.blog.model.Device;
import com.dcl.blog.model.VWiFi;

public interface VWifiDao {
	public VWiFi getVWifi(long conpanyId,String tokens);
	public List<VWiFi> getConpanyVWifi(long conpanyId);
	public List<Device> getDeviceByVWifi(long conpanyId,String tokens);
	public Device getDevice(long conpanyId,String tokens,String mac);
	
}
