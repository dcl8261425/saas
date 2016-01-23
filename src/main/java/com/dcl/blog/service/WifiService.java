package com.dcl.blog.service;

import java.util.List;

import com.dcl.blog.model.Device;
import com.dcl.blog.model.VWiFi;

public interface WifiService {
	public VWiFi getVWifi(long conpanyId,String tokens);
	public List<VWiFi> getConpanyVWifi(long conpanyId);
	public List<Device> getDeviceByVWifi(long conpanyId,String tokens);
	public Device getDevice(long conpanyId,String tokens,String mac);
}
