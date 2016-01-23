var StoreHouse_box_serch_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		StoreHouse_box_serch();
	})
	function initTemp(){
		var tmpl = $("#StoreHouse_box_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			StoreHouse_box_tbody_tr : tmpl
		});
		var tmpl = $("#StoreHouse_box_add_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			StoreHouse_box_add_window_temp : tmpl
		});
	}
	function initbindEvent(){
		
	}
});
function StoreHouse_box_serch(page){
	var name=$("#StoreHouse_box_name").val();
	if(page!=undefined||page!=null){
		StoreHouse_box_serch_page=page;
	}
	addLoadData_div("StoreHouse_box");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryStoreHouse",
		data:{
			name:name,
			nowpage:StoreHouse_box_serch_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#StoreHouse_box_tbody").html($.render.StoreHouse_box_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:StoreHouse_box_serch('"+(parseInt(StoreHouse_box_serch_page)-1)+"')\">«</a></li>";
				var startPage=StoreHouse_box_serch_page>15?StoreHouse_box_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=StoreHouse_box_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:StoreHouse_box_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:StoreHouse_box_serch('"+(parseInt(StoreHouse_box_serch_page)+1)+"')\">»</a></li>"
				$("#StoreHouse_box_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"StoreHouse_box_alertDiv");
			}
			removeloadData_div("StoreHouse_box");
		}
	})
}
function StoreHouse_box_show_addWindow(){
	$("#window").html($.render.StoreHouse_box_add_window_temp());
	$("#StoreHouse_box_add_window").modal("show");
}
function StoreHouse_box_add_window_enter_add(){
	var Storehouseame=$("#StoreHouse_box_add_window_Storehouseame").val();
	var address=$("#StoreHouse_box_add_window_address").val();
	var tel=$("#StoreHouse_box_add_window_tel").val();
	var userid=$("#StoreHouse_box_add_window_input_userid_userid").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/createStoreHouse",
		data:{
			Storehouseame:Storehouseame,
			address:address,
			tel:tel,
			userid:userid
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#StoreHouse_box_add_window').modal("hide");
				alertSuccess(msg.info,"StoreHouse_box_alertDiv");
				StoreHouse_box_serch();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"StoreHouse_box_add_window_temp_AlertDiv");
				}else{
					inputAjaxTest(list,"StoreHouse_box_add_window_");
				}
			}
		}
	})
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
function StoreHouse_box_clear(){
	$("#StoreHouse_box_add_window_input_userid_userid").val("");
	$("#StoreHouse_box_add_window_input_userid_text").val("");
}
function StoreHouse_box_update(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/getStoreHouse",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#window").html($.render.StoreHouse_box_add_window_temp());
				$("#StoreHouse_box_add_window").modal("show");
				$("#StoreHouse_box_add_window_Storehouseame").val(msg.obj.name);
				$("#StoreHouse_box_add_window_address").val(msg.obj.address);
				$("#StoreHouse_box_add_window_tel").val(msg.obj.tal);
				$("#StoreHouse_box_add_window_input_userid_text").val(msg.obj.managerUserName);
				$("#StoreHouse_box_add_window_input_userid_userid").val(msg.obj.managerUserId);
				$("#StoreHouse_box_add_window_enter_add_but").attr("onclick","StoreHouse_box_update_enter("+id+")");
			}else{
				alertError(msg.info,"StoreHouse_box_alertDiv");
			}
		}
	})
}
function StoreHouse_box_update_enter(id){
	var Storehouseame=$("#StoreHouse_box_add_window_Storehouseame").val();
	var address=$("#StoreHouse_box_add_window_address").val();
	var tel=$("#StoreHouse_box_add_window_tel").val();
	var userid=$("#StoreHouse_box_add_window_input_userid_userid").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/updateStoreHouse",
		data:{
			id:id,
			Storehouseame:Storehouseame,
			address:address,
			tel:tel,
			userid:userid
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#StoreHouse_box_add_window").modal("hide");
				alertSuccess(msg.info,"StoreHouse_box_alertDiv");
				StoreHouse_box_serch(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"StoreHouse_box_add_window_temp_AlertDiv");
				}else{
					inputAjaxTest(list,"StoreHouse_box_add_window_");
				}
			}
		}
	})
}
function StoreHouse_box_delete(id){
	ui.confirm("您是否确定要删除此仓库信息",function(b){
		if(b){
				var i=$.layer({
				    type : 3
				});
			    $.ajax({
					type : "POST",
					url : ctx+"goods/function/deleteStoreHouse",
					data:{
						id:id
					},
					success : function(msg) {
						layer.close(i);
						if(msg.success==null||msg.success==undefined||msg.success==true){
							alertSuccess(msg.info,"StoreHouse_box_alertDiv");
							StoreHouse_box_serch(1);
						}else{
							alertError(msg.info,"StoreHouse_box_alertDiv");
						}
					}
			    })
		}else{
			
		}
	},true);
}