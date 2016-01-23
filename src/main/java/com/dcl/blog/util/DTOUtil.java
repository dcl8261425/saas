package com.dcl.blog.util;

import java.util.ArrayList;
import java.util.List;

import com.dcl.blog.model.dto.InOrderDTO;
import com.dcl.blog.model.dto.OrderDTO;
import com.dcl.blog.model.dto.VoteDTO;
import com.dcl.blog.model.dto.VoteItemDTO;
import com.dcl.blog.util.text.JSONArray;
import com.dcl.blog.util.text.JSONObject;

public class DTOUtil {
	public static List<InOrderDTO> getOrderDTO(String data){
		List<InOrderDTO> list=new ArrayList<InOrderDTO>();
		System.out.println(data);
		JSONArray jsonobj = new JSONArray(data);
		for(int i=0;i<jsonobj.length();i++){
			JSONObject obj=jsonobj.getJSONObject(i);
			 Object name=obj.get("name");
			 Object type=obj.get("type");
			 Object price=obj.get("price");
			 Object inprice=obj.get("inprice");
			 Object score=obj.get("score");
			 Object spell=obj.get("spell");
			 Object model=obj.get("model");
			 Object goodRource=obj.get("goodRource");
			 Object storeHouse=obj.get("storeHouse");
			 Object marks=(String) obj.get("marks");
			 Object num=obj.get("num");
			 Object countPrice=obj.get("countPrice");
			 InOrderDTO order=new InOrderDTO();
			 order.setCountPrice(Float.parseFloat(((String) countPrice).trim()));
			 order.setGoodRource(((String) goodRource).trim());
			 order.setInprice(Float.parseFloat(((String) inprice).trim()));
			 order.setMarks(((String) marks).trim());
			 order.setModel(((String) model).trim());
			 order.setName(((String) name).trim());
			 order.setNum(Float.parseFloat(((String) num).trim()));
			 order.setPrice(Float.parseFloat(((String) price).trim()));
			 order.setScore(Double.parseDouble((String) score));
			 order.setSpell(((String)spell).trim().toLowerCase());
			 order.setStoreHouse(Long.parseLong(((String) storeHouse).trim()));
			 order.setType(((String)type).trim());
			 list.add(order);
		}
		return list;
		
	}
	public static List<OrderDTO> getOrdersDTO(String data){
		List<OrderDTO> list=new ArrayList<OrderDTO>();
		JSONArray jsonobj = new JSONArray(data);
		for(int i=0;i<jsonobj.length();i++){
			JSONObject obj=jsonobj.getJSONObject(i);
			 Object name=obj.get("name");
			 Object type=obj.get("type");
			 Object price=obj.get("price");
			 Object inprice=obj.get("inprice");
			 Object score=obj.get("score");
			 Object spell=obj.get("spell");
			 Object model=obj.get("model");
			 Object goodRource=obj.get("goodRource");
			 Object storeHouse=obj.get("storeHouse");
			 Object marks=(String) obj.get("marks");
			 Object num=obj.get("num");
			 Object countPrice=obj.get("countPrice");
			 OrderDTO order=new OrderDTO();
			 order.setCountPrice(Float.parseFloat(((String) countPrice).trim()));
			 order.setGoodRource(((String) goodRource).trim());
			 order.setInprice(Float.parseFloat(((String) inprice).trim()));
			 order.setMarks(((String) marks).trim());
			 order.setModel(((String) model).trim());
			 order.setName(((String) name).trim());
			 order.setNum(Float.parseFloat(((String) num).trim()));
			 order.setPrice(Float.parseFloat(((String) price).trim()));
			 order.setScore(Double.parseDouble(((String)score).trim()));
			 order.setSpell(((String)spell).trim().toLowerCase());
			 order.setStoreHouse(Long.parseLong(((String) storeHouse).trim()));
			 order.setType(((String)type).trim());
			 list.add(order);
		}
		return list;
		
	}
	public static VoteDTO getVoteDTO(String data){
		JSONArray jsonobj = new JSONArray(data);
			JSONObject obj=jsonobj.getJSONObject(0);
			 Object publics=obj.get("publics");
			 Object ones=obj.get("ones");
			 Object id=obj.get("id");
			 Object startDate=obj.get("startDate");
			 Object endDate=obj.get("endDate");
			 JSONArray json=obj.getJSONArray("voteItem");
			 List<VoteItemDTO> list2=new ArrayList<VoteItemDTO>();
			 for(int i=0;i<json.length();i++){
				 JSONObject object=json.getJSONObject(i);
				 VoteItemDTO dto=new VoteItemDTO();
				 String id2=(String) object.get("id");
				 String name=(String) object.get("name");
				 if(id2.trim().equals("")){
					 dto.setId(0);
				 }else{
					 dto.setId(Long.parseLong(id2));
				 }
				 dto.setName((String)name);
				 list2.add(dto);
			 }
			 VoteDTO vote=new VoteDTO();
			 if(id.toString().equals("")){
				 vote.setId(0);
			 }else{
				 vote.setId(Long.parseLong((String) id));
			 }
			 try{
				 if(!((String)startDate).equals("1")){
					 vote.setStartDate(DateUtil.toDateType((String)startDate));
				 }
			 }catch (Exception e) {
				// TODO: handle exception
				 vote.setStartDate(null);
			 }
			 try{
				 if(!((String)startDate).equals("1")){
					 vote.setEndDate(DateUtil.toDateType((String)endDate));
				 }
			 }catch (Exception e) {
				// TODO: handle exception
				 vote.setEndDate(null);
			 }
			 vote.setOnes(Boolean.parseBoolean((String)ones));
			 vote.setPublics(Boolean.parseBoolean((String)publics));
			 vote.setVoteItem(list2);
		return vote;
	}
}
