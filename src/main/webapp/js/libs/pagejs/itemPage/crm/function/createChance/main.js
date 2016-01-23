
$(function () {
	$(document).ready(function(){
		initTemp();
		createChanceinitEventBind();
		initDiv();
	})
	function initTemp(){
		
	}
	function initDiv(){
		$('#createChance_window').modal("show");
		if(createChanceAdd_window_type=='add'){
			
		}else if(createChanceAdd_window_type=='update'){
			$("#createChance_NotesUserId_text").attr("disabled","disabled");
			$("#createChance_NotesUserId_id_but").attr("disabled","disabled");
			$("#createChance_NotesUserId_id_clear").attr("disabled","disabled");
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/function/getChance",
				data:{
					ChanceName:createChance_id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						$("#createChance_CustomerName").val(msg.obj.customerName);
						$("#createChance_CustomerAddress").val(msg.obj.customerAddress);
						$("#createChance_CustomerType").val(msg.obj.customerType);
						$("#createChance_CustomerLevel").val(msg.obj.customerLevel);
						$("#createChance_CustomerMark").val(msg.obj.customerMark);
						$("#createChance_CreateManMark").val(msg.obj.createManMark);
						$("#createChance_NotesUserId_id").val(msg.obj.notesUserId);
						$("#createChance_NotesUserId_text").val(msg.obj.notesUserName);
						$("#createChance_State").val(msg.obj.state);
					}else{
						alertError(msg.info,"createChanceWindowAlertDiv");
					}
				}
		    })
			$("#createChance_window_enter_add_but").text("更新");
		}else if(createChanceAdd_window_type=='look'){
			$("#createChance_CustomerName").attr("disabled","disabled");
			$("#createChance_CustomerAddress").attr("disabled","disabled");
			$("#createChance_CustomerType").attr("disabled","disabled");;
			$("#createChance_CustomerLevel").attr("disabled","disabled");
			$("#createChance_CustomerMark").attr("disabled","disabled");
			$("#createChance_CreateManMark").attr("disabled","disabled");
			$("#createChance_NotesUserId_id").attr("disabled","disabled");
			$("#createChance_NotesUserId_text").attr("disabled","disabled");
			$("#createChance_State").attr("disabled","disabled");
			$("#createChance_NotesUserId_id_but").attr("disabled","disabled");
			$("#createChance_NotesUserId_id_clear").attr("disabled","disabled");
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/function/getChance",
				data:{
					ChanceName:createChance_id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						$("#createChance_CustomerName").val(msg.obj.customerName);
						$("#createChance_CustomerAddress").val(msg.obj.customerAddress);
						$("#createChance_CustomerType").val(msg.obj.customerType);
						$("#createChance_CustomerLevel").val(msg.obj.customerLevel);
						$("#createChance_CustomerMark").val(msg.obj.customerMark);
						$("#createChance_CreateManMark").val(msg.obj.createManMark);
						$("#createChance_NotesUserId_id").val(msg.obj.notesUserId);
						$("#createChance_NotesUserId_text").val(msg.obj.notesUserName);
						$("#createChance_State").val(msg.obj.state);
					}else{
						alertError(msg.info,"createChanceWindowAlertDiv");
					}
				}
		    })
			$("#createChance_window_enter_add_but").text("确定");
		}else if(createChanceAdd_window_type=='NotesUser'){
			$("#createChance_CustomerName").attr("disabled","disabled");
			$("#createChance_CustomerAddress").attr("disabled","disabled");
			$("#createChance_CustomerType").attr("disabled","disabled");;
			$("#createChance_CustomerLevel").attr("disabled","disabled");
			$("#createChance_CustomerMark").attr("disabled","disabled");
			$("#createChance_CreateManMark").attr("disabled","disabled");
			$("#createChance_State").attr("disabled","disabled");
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/function/getChance",
				data:{
					ChanceName:createChance_id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						$("#createChance_CustomerName").val(msg.obj.customerName);
						$("#createChance_CustomerAddress").val(msg.obj.customerAddress);
						$("#createChance_CustomerType").val(msg.obj.customerType);
						$("#createChance_CustomerLevel").val(msg.obj.customerLevel);
						$("#createChance_CustomerMark").val(msg.obj.customerMark);
						$("#createChance_CreateManMark").val(msg.obj.createManMark);
						$("#createChance_NotesUserId_id").val(msg.obj.notesUserId);
						$("#createChance_NotesUserId_text").val(msg.obj.notesUserName);
						$("#createChance_State").val(msg.obj.state);
					}else{
						alertError(msg.info,"createChanceWindowAlertDiv");
					}
				}
		    })
			$("#createChance_window_enter_add_but").text("确定指定");
		}
	}
});
function createChanceinitEventBind(){
	
}
/**
 * 传第一个标示来表示 是添加还是修改，还是指定接收人，这样方便使用同一个js文件
 * @param type
 */
function createChanceAdd(){
	var CustomerName=$("#createChance_CustomerName").val();
	var CustomerAddress=$("#createChance_CustomerAddress").val();
	var CustomerType=$("#createChance_CustomerType").val();
	var CustomerLevel=$("#createChance_CustomerLevel").val();
	var CustomerMark=$("#createChance_CustomerMark").val();
	var CreateManMark=$("#createChance_CreateManMark").val();
	var createChance_NotesUserId_id=$("#createChance_NotesUserId_id").val();
	var createChance_NotesUserId=$("#createChance_NotesUserId_text").val();
	var State=$("#createChance_State").val();
	if(createChance_NotesUserId==null||createChance_NotesUserId==""){
		createChance_NotesUserId_id=0;
	}
	if(createChanceAdd_window_type=='add'){
		var i=$.layer({
		    type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+"crm/function/createChance",
			data:{
				CustomerName:CustomerName,
				CustomerAddress:CustomerAddress,
				CustomerType:CustomerType,
				CustomerLevel:CustomerLevel,
				CustomerMark:CustomerMark,
				CreateManMark:CreateManMark,
				NotesUserId:createChance_NotesUserId_id,
				State:State
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success){
					$('#createChance_window').modal("hide");
					alertSuccess(msg.info,"content");
					if( typeof query_myCreateChance === 'function' ){
						query_myCreateChance();
					}
				}else{
					var list=msg.list;
					if(list==undefined||list==null){
						alertError(msg.info,"createChanceWindowAlertDiv");
					}else{
						inputAjaxTest(list,"createChance_");
					}
				}
			}
		})
	}else if(createChanceAdd_window_type=='update'){
		var url="";
		if(createChance_Window_functionType!=null&&createChance_Window_functionType!=undefined){
			if(createChance_Window_functionType=="my"){
				url="crm/function/updateMyCreateChance";
			}else{
				url="crm/function/updateChance";
			}
		}else{
			url="crm/function/updateChance";
		}
		var i=$.layer({
		    type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+url,
			data:{
				CustomerName:CustomerName,
				CustomerAddress:CustomerAddress,
				CustomerType:CustomerType,
				CustomerLevel:CustomerLevel,
				CustomerMark:CustomerMark,
				CreateManMark:CreateManMark,
				ChanceId:createChance_id,
				NotesUserId:createChance_NotesUserId_id,
				State:State
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success){
					$('#createChance_window').modal("hide");
					alertSuccess(msg.info,"content");
					if( typeof query_myCreateChance === 'function' ){
						query_myCreateChance();
					}
				}else{
					var list=msg.list;
					if(list==undefined||list==null){
						alertError(msg.info,"createChanceWindowAlertDiv");
						
					}else{
						inputAjaxTest(list,"createChance_");
					}
				}
			}
		})
	}else if(createChanceAdd_window_type=='look'){
		$('#createChance_window').modal("hide");
	}else if(createChanceAdd_window_type=='NotesUser'){
		var url="";
		if(createChance_Window_functionType!=null&&createChance_Window_functionType!=undefined){
			if(createChance_Window_functionType=="my"){
				url="crm/function/allocationMyCreateChance";
			}else{
				url="crm/function/allocationChance";
			}
		}else{
			url="crm/function/allocationChance";
		}
		var i=$.layer({
		    type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+url,
			data:{
				ChanceId:createChance_id,
				NotesUserId:createChance_NotesUserId_id
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success){
					$('#createChance_window').modal("hide");
					alertSuccess(msg.info,"content");
					if( typeof query_myCreateChance === 'function' ){
						query_myCreateChance();
					}
					$('#createChance_window').modal("hide");
				}else{
					var list=msg.list;
					if(list==undefined||list==null){
						alertError(msg.info,"createChanceWindowAlertDiv");
					}else{
						inputAjaxTest(list,"createChance_");
					}
				}
			}
		})

	}
}
function queryGroupUser(idElementid,textElementId){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"function/queryGroupUser",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				queryGroupUser_out_text_elentId=textElementId;
				queryGroupUser_out_id_elentId=idElementid;
			}else{
				alertError(msg.info,"createChanceWindowAlertDiv");
			}
		}
    })
}
function queryGroupUser_clear(id,text){
	$("#"+queryGroupUser_out_text_elentId).val("");
	$("#"+queryGroupUser_out_id_elentId).val("");
}