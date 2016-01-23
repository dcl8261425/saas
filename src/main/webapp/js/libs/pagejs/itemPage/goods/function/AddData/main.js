$(function () {
	$(document).ready(function(){
		initbindEvent();
		$('#good_addData_window').modal("show");
	})
	function initTemp(){
		
	}
	function initbindEvent(){
	}
});
function good_addData_window_enter_add(){
	var goodsName=$("#good_addData_window_goodname_value").val();
	var goodsNum=$("#good_addData_window_goodsNum").val();
	var goodsType=$("#good_addData_window_goodsType").val();
	var price=$("#good_addData_window_price").val();
	var inPrice=$("#good_addData_window_inPrice").val();
	var score=$("#good_addData_window_score").val();
	var Spell=$("#good_addData_window_spell").val();
	var goodsModel=$("#good_addData_window_goodsModel").val();
	var goodsSourceId=$("#good_addData_window_input_SourseId_id").val();
	var storehouseId=$("#good_addData_window_input_StorehouseId_id").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/addGoods",
		data:{
			goodname:goodsName,
			goodsNum:goodsNum,
			goodsType:goodsType,
			price:price,
			inPrice:inPrice,
			score:score,
			spell:Spell,
			goodsModel:goodsModel,
			sourseId:goodsSourceId,
			storehouseId:storehouseId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#good_addData_window').modal("hide");
				alertSuccess(msg.info,"content");
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					
					alertError(msg.info,"content");
					
				}else{
					inputAjaxTest(list,"good_addData_window_");
				}
			}
		}
	})
}
function good_addData_window_query_Storehouse(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_StoreHouse_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				StoreHouse_box_EditLinkDate_element_text=text;
				StoreHouse_box_EditLinkDate_element_id=id;
			}else{
				alertError(msg.info,"good_addData_window_AlertDiv");
			}
		}
    })
}
function good_addData_window_query_Storehouse_clear(){
	$("#good_addData_window_input_StorehouseId_id").val("");
	$("#good_addData_window_input_StorehouseId_text").val("");
}
function good_addData_window_query_GoodSource(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_GoodSource_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				GoodSource_box_EditLinkDate_element_text=text;
				GoodSource_box_EditLinkDate_element_id=id;
				GoodSource_box_select_type_linkman=false;
			}else{
				alertError(msg.info,"good_addData_window_AlertDiv");
			}
		}
    })
}
function good_addData_window_query_GoodSource_clear(){
	$("#good_addData_window_input_SourseId_text").val("");
	$("#good_addData_window_input_SourseId_id").val("");
}
function good_addData_window_query_goods(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/queryData_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg);
				is_select=true;
				return_function=function(obj){
					var name=obj.goodsName;
					var type=obj.goodsType;
					var price=obj.price;
					var score=obj.score;
					var spell=obj.spell;
					var model=obj.goodsModel;
					$("#good_addData_window_goodname_value").attr("disabled","disabled");
					$("#good_addData_window_goodsType").attr("disabled","disabled");
					$("#good_addData_window_price").attr("disabled","disabled");
					$("#good_addData_window_score").attr("disabled","disabled");
					$("#good_addData_window_spell").attr("disabled","disabled");
					$("#good_addData_window_goodsModel").attr("disabled","disabled");
					$("#good_addData_window_goodname_value").val(name);
					$("#good_addData_window_goodsType").val(type);
					$("#good_addData_window_price").val(price);
					$("#good_addData_window_score").val(score);
					$("#good_addData_window_spell").val(spell);
					$("#good_addData_window_goodsModel").val(model);
				}
			}else{
				alertError(msg.info,"good_addData_window_AlertDiv");
			}
		}
    })
}