package com.dcl.blog.dao;

import java.util.List;



public interface Dao extends PermissionDao,WindowLayoutDao,GoodsDao,HrDao,MessageDao,WeixinDao,VWifiDao,PhoneMessageDao{
	public void update(Object obj);
	public long add(Object obj);
	public void delete(Object obj);
	public Object getObject(long id,String tableName);
	public Object getObject(long id,String tableName,long conpanyId);
	public List<Object> getObjectList(String table,String where);
	public long getObjectListNum(String table,String where);
	public List<Object> getObjectListPage(String table,String where,int nowpage,int countRow);
	public List<Object> getObjectListBySql(String sql);
	public void deleteList(String sql,String tableName);
	public List<Object> getObjectListByeSql(String sql) ;
 }
