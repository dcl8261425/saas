var AddLinkManWindow_Query_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initDiv();
	})
	function initTemp(){
		var tmpl = $("#AddLinkMan_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			AddLinkMan_window_tbody_tr : tmpl
		});
		var tmpl = $("#AddLinkMan_window_add_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			AddLinkMan_window_add_window : tmpl
		});
	}
	function initDiv(){
		$('#AddLinkMan_window').modal("show");
		AddLinkManList_query_function_window();
		
	}
})
/**
 * 弹出添加框
 */
function AddLinkManList_add_function_window(){
	$("#AddLinkMan_window_add_window_div").html($.render.AddLinkMan_window_add_window());
	$('#AddLinkMan_window_add_but').modal("show");
	$('#AddLinkMan_window_input_linkManBirthday .input-group.date').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
}
/**
 * 查询联系人
 */
function AddLinkManList_query_function_window(page){
	var name=$("#AddLinkMan_window_query_Name").val();
	if(page!=undefined||page!=null){
		AddLinkManWindow_Query_page=page;
	}
	addLoadData_div("AddLinkManWindow_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryLinkMan",
		data:{
			chanceId:addlinkman_id,
			ChanceName:name,
			nowpage:AddLinkManWindow_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#AddLinkMan_window_tbody").html($.render.AddLinkMan_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:AddLinkManList_query_function_window('"+(parseInt(AddLinkManWindow_Query_page)-1)+"')\">«</a></li>";
				var startPage=AddLinkManWindow_Query_page>15?AddLinkManWindow_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=AddLinkManWindow_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:AddLinkManList_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:AddLinkManList_query_function_window('"+(parseInt(AddLinkManWindow_Query_page)+1)+"')\">»</a></li>"
				$("#AddLinkMan_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"AddLinkManWindow_AlertDiv");
			}
			removeloadData_div("AddLinkManWindow_Body");
		}
	})
}
//确认添加
function AddLinkManList_add_function_window_enter_add(){
	var linkManName=$("#AddLinkMan_window_linkManName").val();
	var linkManPhone=$("#AddLinkMan_window_linkManPhone").val();
	var linkManJob=$("#AddLinkMan_window_linkManJob").val();
	var linkManSex=$("#AddLinkMan_window_linkManSex").val();
	var linkManBirthday=$("#AddLinkMan_window_linkManBirthday_text").val();
	var linkManMark=$("#AddLinkMan_window_linkManMark").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/addLinkMan",
		data:{
			chanceId:addlinkman_id,
			linkManName:linkManName,
			linkManPhone:linkManPhone,
			linkManSex:linkManSex,
			linkManJob:linkManJob,
			linkManMark:linkManMark,
			linkManBirthday:linkManBirthday
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#AddLinkMan_window_add_but').modal("hide");
				alertSuccess(msg.info,"AddLinkManWindow_AlertDiv");
				AddLinkManList_query_function_window(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"AddLinkManWindow_Add_AlertDiv");
				}else{
					inputAjaxTest(list,"AddLinkMan_window_");
				}
			}
		}
	})
}
function AddLinMan_look(id){

	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/function/getLinkManById",
		data:{
			ChanceName:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				AddLinkManList_add_function_window();
				$("#AddLinkMan_window_linkManName").attr("disabled","disabled");
				$("#AddLinkMan_window_linkManPhone").attr("disabled","disabled");
				$("#AddLinkMan_window_linkManJob").attr("disabled","disabled");;
				$("#AddLinkMan_window_linkManSex").attr("disabled","disabled");
				$("#AddLinkMan_window_linkManBirthday_text").attr("disabled","disabled");
				$("#AddLinkMan_window_linkManMark").attr("disabled","disabled");
				$("#AddLinkMan_window_linkManBirthday_text_button").attr("style","display: none");
				$("#AddLinkMan_window_linkManName").val(msg.obj.linkManName);
				$("#AddLinkMan_window_linkManPhone").val(msg.obj.linkManPhone);
				$("#AddLinkMan_window_linkManJob").val(msg.obj.linkManJob);
				$("#AddLinkMan_window_linkManSex").val(msg.obj.linkManSex);
				$("#AddLinkMan_window_linkManBirthday_text").val(JavaSTojsDate(msg.obj.linkManBirthday));
				$("#AddLinkMan_window_linkManMark").val(msg.obj.linkManMark);
				$("#AddLinkMan_window_enter_add_but").text("确定");
				$("#AddLinkMan_window_enter_add_but").attr("onclick","AddLinMan_close_window()");
				AddLinkManList_query_function_window(1);
			}else{
				alertError(msg.info,"AddLinkManWindow_AlertDiv");
			}
		}
    })
	
}
function AddLinMan_delete(id){
	ui.confirm("您是否确定要删除此联系人信息",function(b){
		if(b){
				var i=$.layer({
				    type : 3
				});
			    $.ajax({
					type : "POST",
					url : ctx+"crm/function/deleteLinkMan",
					data:{
						id:id
					},
					success : function(msg) {
						layer.close(i);
						if(msg.success==null||msg.success==undefined||msg.success==true){
							alertSuccess(msg.info,"AddLinkManWindow_AlertDiv");
							AddLinkManList_query_function_window(1);
						}else{
							alertError(msg.info,"AddLinkManWindow_AlertDiv");
						}
					}
			    })
		}else{
			
		}
	},true);
}
function AddLinMan_update(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/function/getLinkManById",
		data:{
			ChanceName:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				AddLinkManList_add_function_window();
				$("#AddLinkMan_window_linkManName").val(msg.obj.linkManName);
				$("#AddLinkMan_window_linkManPhone").val(msg.obj.linkManPhone);
				$("#AddLinkMan_window_linkManJob").val(msg.obj.linkManJob);
				$("#AddLinkMan_window_linkManSex").val(msg.obj.linkManSex);
				$("#AddLinkMan_window_linkManBirthday_text").val(JavaSTojsDate(msg.obj.linkManBirthday));
				$("#AddLinkMan_window_linkManMark").val(msg.obj.linkManMark);
				$("#AddLinkMan_window_enter_add_but").text("确定");
				$("#AddLinkMan_window_enter_add_but").attr("onclick","AddLinMan_update_enter_update("+id+")");
			}else{
				alertError(msg.info,"AddLinkManWindow_AlertDiv");
			}
		}
    })
}
function AddLinMan_update_enter_update(id){
	var linkManName=$("#AddLinkMan_window_linkManName").val();
	var linkManPhone=$("#AddLinkMan_window_linkManPhone").val();
	var linkManJob=$("#AddLinkMan_window_linkManJob").val();
	var linkManSex=$("#AddLinkMan_window_linkManSex").val();
	var linkManBirthday=$("#AddLinkMan_window_linkManBirthday_text").val();
	var linkManMark=$("#AddLinkMan_window_linkManMark").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/updateLinkMan",
		data:{
			id:id,
			chanceId:addlinkman_id,
			linkManName:linkManName,
			linkManPhone:linkManPhone,
			linkManSex:linkManSex,
			linkManJob:linkManJob,
			linkManMark:linkManMark,
			linkManBirthday:linkManBirthday
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#AddLinkMan_window_add_but').modal("hide");
				alertSuccess(msg.info,"AddLinkManWindow_AlertDiv");
				AddLinkManList_query_function_window(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"AddLinkManWindow_Add_AlertDiv");
				}else{
					inputAjaxTest(list,"AddLinkMan_window_");
				}
			}
		}
	})
}
function AddLinMan_close_window(){
	$('#AddLinkMan_window_add_but').modal("hide");
}