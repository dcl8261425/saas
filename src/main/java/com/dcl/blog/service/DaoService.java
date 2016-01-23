package com.dcl.blog.service;

import java.util.List;




public interface DaoService extends PermissionService,WindowLayoutService,GoodsService,HrService,MessageService,WeixinService,WifiService,PhoneMessageService{
	public void update(Object obj);
	public long add(Object obj);
	public void delete(Object obj);
	public Object getObject(long id,String tableName,long conpanyId);
	public Object getObject(long id,String tableName);
	public List<Object> getObjectList(String table,String where);
	public long getObjectListNum(String table,String where);
	public List<Object> getObjectListPage(String table,String where,int nowpage,int countRow);
	public List<Object> getObjectListBySql(String sql);
	public void deleteList(String sql,String tableName);
	public List<Object> getObjectListByeSql(String sql) ;
}
