var GoodSource_box_serch_page=1;
var GoodSource_box_linkman_window_page=1;
var GoodSource_box_linkman_id;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#GoodSource_box_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_tbody_tr : tmpl
		});
		var tmpl = $("#GoodSource_box_add_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_add_window_temp : tmpl
		});
		var tmpl = $("#GoodSource_box_linkman_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_linkman_window_temp : tmpl
		});
		var tmpl = $("#GoodSource_box_linkman_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_linkman_window_tbody_tr : tmpl
		});
		var tmpl = $("#GoodSource_box_linkman_add_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_linkman_add_window : tmpl
		});
	}
	function initbindEvent(){
		
	}
	GoodSource_box_serch();
});
function GoodSource_box_serch(page){
	var name=$("#GoodSource_box_name").val();
	if(page!=undefined||page!=null){
		GoodSource_box_serch_page=page;
	}
	addLoadData_div("GoodSource_box");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoodsSource",
		data:{

			name:name,
			nowpage:GoodSource_box_serch_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#GoodSource_box_tbody").html($.render.GoodSource_box_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:GoodSource_box_serch('"+(parseInt(GoodSource_box_serch_page)-1)+"')\">«</a></li>";
				var startPage=GoodSource_box_serch_page>15?GoodSource_box_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=GoodSource_box_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:GoodSource_box_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:GoodSource_box_serch('"+(parseInt(GoodSource_box_serch_page)+1)+"')\">»</a></li>"
				$("#GoodSource_box_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"GoodSource_box_alertDiv");
			}
			removeloadData_div("GoodSource_box");
		}
	})
}
function GoodSource_box_show_addWindow(){
	$("#window").html($.render.GoodSource_box_add_window_temp());
	$("#GoodSource_box_add_window").modal("show");
}
function GoodSource_box_add_window_enter_add(){
	var name=$("#GoodSource_box_add_window_goodsSoucename").val();
	var address=$("#GoodSource_box_add_window_address").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/addGoodSource",
		data:{
			goodsSoucename:name,
			address:address
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#GoodSource_box_add_window').modal("hide");
				alertSuccess(msg.info,"GoodSource_box_alertDiv");
				GoodSource_box_serch();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"GoodSource_box_add_window_temp_AlertDiv");
				}else{
					inputAjaxTest(list,"GoodSource_box_add_window_");
				}
			}
		}
	})
}
function GoodSource_box_delete(id){
	ui.confirm("您是否确定要删除此供应商信息",function(b){
		if(b){
				var i=$.layer({
				    type : 3
				});
			    $.ajax({
					type : "POST",
					url : ctx+"goods/function/deleteGoodsSource",
					data:{
						id:id
					},
					success : function(msg) {
						layer.close(i);
						if(msg.success==null||msg.success==undefined||msg.success==true){
							alertSuccess(msg.info,"GoodSource_box_alertDiv");
							GoodSource_box_serch(1);
						}else{
							alertError(msg.info,"GoodSource_box_alertDiv");
						}
					}
			    })
		}else{
			
		}
	},true);
}
function GoodSource_box_update(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/getGoodsSource",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#window").html($.render.GoodSource_box_add_window_temp());
				$("#GoodSource_box_add_window").modal("show");
				$("#GoodSource_box_add_window_goodsSoucename").val(msg.obj.name);
				$("#GoodSource_box_add_window_address").val(msg.obj.address);
				$("#GoodSource_box_add_window_enter_add_but").attr("onclick","GoodSource_box_update_enter("+id+")");
			}else{
				alertError(msg.info,"GoodSource_box_alertDiv");
			}
		}
	})
}
function GoodSource_box_update_enter(id){
	var name=$("#GoodSource_box_add_window_goodsSoucename").val();
	var address=$("#GoodSource_box_add_window_address").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/updateGoodSource",
		data:{
			id:id,
			goodsSoucename:name,
			address:address
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#GoodSource_box_add_window").modal("hide");
				alertSuccess(msg.info,"GoodSource_box_alertDiv");
				GoodSource_box_serch(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"GoodSource_box_add_window_temp_AlertDiv");
				}else{
					inputAjaxTest(list,"GoodSource_box_add_window_");
				}
			}
		}
	})
}
function GoodSource_box_addlinkman(id){
	$("#window").html($.render.GoodSource_box_linkman_window_temp());
	$("#GoodSource_box_linkman_window").modal("show");
	GoodSource_box_linkman_id=id;
	GoodSource_box_linkman_window_serch();
}
function GoodSource_box_linkman_window_serch(page){
	var name=$("#GoodSource_box_linkman_window_name").val();
	if(page!=undefined||page!=null){
		GoodSource_box_linkman_window_page=page;
	}
	addLoadData_div("GoodSource_box_linkman_window_box");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoodsSourceLinkman",
		data:{
			id:GoodSource_box_linkman_id,
			name:name,
			nowpage:GoodSource_box_linkman_window_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#GoodSource_box_linkman_window_tbody").html($.render.GoodSource_box_linkman_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:GoodSource_box_linkman_window_serch('"+(parseInt(GoodSource_box_linkman_window_page)-1)+"')\">«</a></li>";
				var startPage=GoodSource_box_linkman_window_page>15?GoodSource_box_linkman_window_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=GoodSource_box_linkman_window_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:GoodSource_box_linkman_window_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:GoodSource_box_linkman_window_serch('"+(parseInt(GoodSource_box_linkman_window_page)+1)+"')\">»</a></li>"
				$("#GoodSource_box_linkman_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"GoodSource_box_linkman_window_temp_AlertDiv");
			}
			removeloadData_div("GoodSource_box_linkman_window_box");
		}
	})
}
function GoodSource_box_linkman_window_show_addWindow(){
	$("#window2").html($.render.GoodSource_box_linkman_add_window());
	$("#GoodSource_box_linkman_add_but_window").modal("show");
	$('#GoodSource_box_linkman_linkManBirthday').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
}
function GoodSource_box_linkman_add_function_window_enter_add(){
	var name=$("#GoodSource_box_linkman_linkname").val();
	var phone=$("#GoodSource_box_linkman_phone").val();
	var linkManBirthday=$("#GoodSource_box_linkman_linkManBirthday_text").val();
	
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/addGoodSourceLinkMan",
		data:{
			id:GoodSource_box_linkman_id,
			linkManBirthday:linkManBirthday,
			linkname:name,
			phone:phone
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#GoodSource_box_linkman_add_but_window").modal("hide");
				alertSuccess(msg.info,"GoodSource_box_linkman_window_temp_AlertDiv");
				GoodSource_box_linkman_window_serch(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"GoodSource_box_linkman_AlertDiv");
				}else{
					inputAjaxTest(list,"GoodSource_box_linkman_");
				}
			}
		}
	})
}
function GoodSource_box_linkman_update(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/getGoodsSourceLinkMan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#window2").html($.render.GoodSource_box_linkman_add_window());
				$("#GoodSource_box_linkman_add_but_window").modal("show");
				$('#GoodSource_box_linkman_linkManBirthday').datepicker({
					 language: 'zh-CN',
					 format: 'yyyy-mm-dd'
				});
				$("#GoodSource_box_linkman_linkname").val(msg.obj.name);
				$("#GoodSource_box_linkman_phone").val(msg.obj.phone);
				$("#GoodSource_box_linkman_linkManBirthday_text").val(JavaSTojsDate(msg.obj.linkManBirthday));
				$("#GoodSource_box_linkman_add_but").attr("onclick","GoodSource_box_linkman_update_enter("+id+")");
			}else{
				alertError(msg.info,"GoodSource_box_linkman_window_box");
			}
		}
	})
}
function GoodSource_box_linkman_update_enter(id){
	
	var name=$("#GoodSource_box_linkman_linkname").val();
	var phone=$("#GoodSource_box_linkman_phone").val();
	var linkManBirthday=$("#GoodSource_box_linkman_linkManBirthday_text").val();
	
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/updateGoodSourceLinkMan",
		data:{
			id:id,
			linkManBirthday:linkManBirthday,
			linkname:name,
			phone:phone
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#GoodSource_box_linkman_add_but_window").modal("hide");
				alertSuccess(msg.info,"GoodSource_box_linkman_window_temp_AlertDiv");
				GoodSource_box_linkman_window_serch(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"GoodSource_box_linkman_AlertDiv");
				}else{
					inputAjaxTest(list,"GoodSource_box_linkman_");
				}
			}
		}
	})
}
function GoodSource_box_linkman_delete(id){
	ui.confirm("您是否确定要删除此联系人信息",function(b){
		if(b){
				var i=$.layer({
				    type : 3
				});
			    $.ajax({
					type : "POST",
					url : ctx+"goods/function/deleteGoodsSourceLinkman",
					data:{
						id:id
					},
					success : function(msg) {
						layer.close(i);
						if(msg.success==null||msg.success==undefined||msg.success==true){
							alertSuccess(msg.info,"GoodSource_box_linkman_AlertDiv");
							GoodSource_box_linkman_window_serch(1);
						}else{
							alertError(msg.info,"GoodSource_box_linkman_AlertDiv");
						}
					}
			    })
		}else{
			
		}
	},true);
}