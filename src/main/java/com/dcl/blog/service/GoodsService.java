package com.dcl.blog.service;

import java.util.List;

import com.dcl.blog.model.GoodsSource;
import com.dcl.blog.model.GoodsTable;
import com.dcl.blog.model.InOrderItem;
import com.dcl.blog.model.OrdersItem;
import com.dcl.blog.model.StoreHouse;
import com.dcl.blog.model.StoreHouseDateLog;



public interface GoodsService extends PermissionService,WindowLayoutService{
	public GoodsTable getGoods(String name,String type,String model,long conpanyId);
	public GoodsTable addGoods(GoodsTable goods);
	public GoodsTable  addReduceGoods(GoodsTable goods);
	/**
	 * 查询销售数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getSalesData(String startDate,String endDate,long goodsId,long conpanyId);
	/**
	 * 查询进货数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getInGoodsData(String startDate,String endDate,long goodsId,long conpanyId);
	/**
	 * 查询各个时期的价格
	 */
	public List getGoodsPriceAndInPrice(String startDate,String endDate,long goodsId,long conpanyId);
	public StoreHouseDateLog getStoreHouseDateLog(long goodsid,long storeHouseId,long conpanyId);
	/**
	 * 查询每个仓库的库存量
	 */
	public List<StoreHouseDateLog> getStoreHouseDateLogChar(long goodsId,long conpanyId);
	/**
	 * 删除订单项
	 */
	public void deleteInOrder(long orderId,long conpanyId);
	/**
	 * 删除订单项
	 */
	public void deleteOrder(long orderId,long conpanyId);
	/**
	 *查找订单项
	 */
	public List<InOrderItem> getInOrderItems(long orderId,long conpanyId);
	/**
	 *查找销售订单项
	 */
	public List<OrdersItem> getOrderItems(long orderId,long conpanyId);
	public GoodsSource getGoodsSource(String storeName,long conpanyId,boolean iscreate);
	public StoreHouse getStoreHouse(String storeName,long conpanyId,boolean iscreate);
}
