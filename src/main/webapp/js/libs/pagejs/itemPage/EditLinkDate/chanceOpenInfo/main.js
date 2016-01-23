var Edit_chanceOpenInfo_Query_page=1
var Edit_chanceOpenInfo_QueryItem_page=1;
var Edit_chanceOpenInfo_Query_page_main=1;
var Edit_chanceOpenInfo_Query_id=0;
$(function () {
	$(document).ready(function(){
		initTemp();
	})
	function initTemp(){
		var tmpl = $("#Edit_chanceOpenInfo_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_chanceOpenInfo_window_tbody_tr : tmpl
		})
		var tmpl = $("#Edit_chanceOpenInfo_window_look_info_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_chanceOpenInfo_window_look_info_window : tmpl
		});
		var tmpl = $("#Edit_chanceOpenInfo_window_look_info_window_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_chanceOpenInfo_window_look_info_window_tr : tmpl
		});
		var tmpl = $("#Edit_chanceOpenInfo_window_tbody_tr_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_chanceOpenInfo_window_tbody_tr_main : tmpl
		});
	}
	
})
function Edit_chanceOpenInfo_initDiv(){
	$('#Edit_chanceOpenInfo_window').modal("show");
	alertWarning("只能从分配给自己的机会中查询","Edit_chanceOpenInfo_AlertDiv");
	Edit_chanceOpenInfo_query_function_window_main();
}
function Edit_chanceOpenInfo_query_function_window(page){
	var name=$("#Edit_chanceOpenInfo_window_query_Name").val();
	if(page!=undefined||page!=null){
		Edit_chanceOpenInfo_Query_page=page;
	}
	addLoadData_div("Edit_chanceOpenInfo_Window_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryNotes",
		data:{
			chanceId:Edit_chanceOpenInfo_Query_id,
			ChanceName:name,
			nowpage:Edit_chanceOpenInfo_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#Edit_chanceOpenInfo_window_tbody").html($.render.Edit_chanceOpenInfo_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:Edit_chanceOpenInfo_query_function_window('"+(parseInt(Edit_chanceOpenInfo_Query_page)-1)+"')\">«</a></li>";
				var startPage=Edit_chanceOpenInfo_Query_page>15?Edit_chanceOpenInfo_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=Edit_chanceOpenInfo_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:Edit_chanceOpenInfo_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:Edit_chanceOpenInfo_query_function_window('"+(parseInt(Edit_chanceOpenInfo_Query_page)+1)+"')\">»</a></li>"
				$("#Edit_chanceOpenInfo_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"Edit_chanceOpenInfo_AlertDiv");
			}
			removeloadData_div("Edit_chanceOpenInfo_Window_Body");
		}
	})
}
function Edit_chanceOpenInfo_add_function_window(){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/startNotes",
		data:{
			chanceId:addlinkman_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"Edit_chanceOpenInfo_AlertDiv");
				Edit_chanceOpenInfo_query_function_window(1);
			}else{
				alertError(msg.info,"Edit_chanceOpenInfo_AlertDiv");
			}
		}
	})
}
function Edit_chanceOpenInfo_look_show(id,page){
	$("#Edit_chanceOpenInfo_window_add_window_div").html($.render.Edit_chanceOpenInfo_window_look_info_window());
	$('#Edit_chanceOpenInfo_window_look_info_window_window').modal("show");
	Edit_chanceOpenInfo_look(id,page)
}

function Edit_chanceOpenInfo_look(id,page){
	if(page!=undefined||page!=null){
		Edit_chanceOpenInfo_QueryItem_page=page;
	}
	addLoadData_div("Edit_chanceOpenInfo_Window_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryNotesItem",
		data:{
			NotesId:id,
			nowpage:Edit_chanceOpenInfo_QueryItem_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){

				$("#Edit_chanceOpenInfo_window_look_tbody").html($.render.Edit_chanceOpenInfo_window_look_info_window_tr(msg.data));
				var page_html="<li><a href=\"javascript:Edit_chanceOpenInfo_look("+id+",'"+(parseInt(Edit_chanceOpenInfo_QueryItem_page)-1)+"')\">«</a></li>";
				var startPage=Edit_chanceOpenInfo_QueryItem_page>15?Edit_chanceOpenInfo_QueryItem_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=Edit_chanceOpenInfo_QueryItem_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:Edit_chanceOpenInfo_look("+id+",'"+ii+"')\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:Edit_chanceOpenInfo_look("+id+",'"+(parseInt(Edit_chanceOpenInfo_QueryItem_page)+1)+"')\">»</a></li>"
				$("#Edit_chanceOpenInfo_window_look_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"Edit_chanceOpenInfo_AlertDiv");
			}
			removeloadData_div("Edit_chanceOpenInfo_Window_Body");
		}
	})

}
function Edit_chanceOpenInfo_add(id){
	$("#Edit_chanceOpenInfo_window_add_window_div").html($.render.Edit_chanceOpenInfo_window_add_info_window());
	$('#Edit_chanceOpenInfo_window_add_info_window_window').modal("show");
	$("#Edit_chanceOpenInfo_window_enter_add_but").attr("onclick","Edit_chanceOpenInfo_add_enter("+id+")");
}
function Edit_chanceOpenInfo_add_enter(id){
	var Title=$("#Edit_chanceOpenInfo_window_Title").val();
	var Marks=$("#Edit_chanceOpenInfo_window_Marks").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/addNotesItem",
		data:{
			NotesId:id,
			Title:Title,
			Marks:Marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#Edit_chanceOpenInfo_window_add_info_window_window').modal("hide");
				alertSuccess(msg.info,"Edit_chanceOpenInfo_Add_AlertDiv");
				Edit_chanceOpenInfo_query_function_window(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"Edit_chanceOpenInfo_Add_AlertDiv");
				}else{
					inputAjaxTest(list,"Edit_chanceOpenInfo_window_");
				}
			}
		}
	})
}
function look_Notes_Info_marks(id){
	$("#"+id).toggle();
}
function Edit_chanceOpenInfo_Item_select(id,name){
	if(Edit_Item_ElementId_text!=undefined||Edit_Item_ElementId_text!=null){
		$("#"+Edit_Item_ElementId_id).val(id);
		$("#"+Edit_Item_ElementId_text).val(name);
	}else{
		var str="<a id='NoteItem_"+id+Edit_chance_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'NoteItem','NoteItem_"+id+Edit_chance_add_num+"')\">"+name+"</a>"
		Edit_chance_add_num++;
		$("#"+Edit_Item_ElementId_id).val($("#"+Edit_Item_ElementId_id).val()+str);
	}
	$('#Edit_chanceOpenInfo_window_look_info_window_window').modal("hide");
	$('#Edit_chanceOpenInfo_window').modal("hide");
}
function Edit_chanceOpenInfo_select(id,name){
	if(Edit_Item_ElementId_text!=undefined||Edit_Item_ElementId_text!=null){
		$("#"+Edit_Item_ElementId_id).val(id);
		$("#"+Edit_Item_ElementId_text).val(name);
	}else{
		var str="<a id='Note_"+id+Edit_chance_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'Note','Note_"+id+Edit_chance_add_num+"')\">"+name+"</a>"
		Edit_chance_add_num++;
		$("#"+Edit_Item_ElementId_id).val($("#"+Edit_Item_ElementId_id).val()+str);
	}
	$('#Edit_chanceOpenInfo_window').modal("hide");
}
function Edit_chanceOpenInfo_query_function_window_main(page){
	if(page!=undefined||page!=null){
		Edit_chanceOpenInfo_Query_page_main=page;
	}
	var ChanceName=$("#Edit_chanceOpenInfo_window_query_Name_main").val();
	addLoadData_div("Edit_chanceOpenInfo_Window_Body_main");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryToMyChance",
		data:{
			ChanceName:ChanceName,
			nowpage:Edit_chanceOpenInfo_Query_page_main,
			countNum:3
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#Edit_chanceOpenInfo_window_tbody_main").html($.render.Edit_chanceOpenInfo_window_tbody_tr_main(msg.data));
				var page_html="<li><a href=\"javascript:Edit_chanceOpenInfo_query_function_window_main('"+(parseInt(Edit_chanceOpenInfo_Query_page_main)-1)+"')\">«</a></li>";
				var startPage=Edit_chanceOpenInfo_Query_page_main>15?Edit_chanceOpenInfo_Query_page_main-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=Edit_chanceOpenInfo_Query_page_main==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:Edit_chanceOpenInfo_query_function_window_main('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:Edit_chanceOpenInfo_query_function_window_main('"+(parseInt(Edit_chanceOpenInfo_Query_page_main)+1)+"')\">»</a></li>"
				$("#Edit_chanceOpenInfo_window_tbody_page_main").html(page_html);
			}else{
				alertError(msg.info,"Edit_chanceOpenInfo_AlertDiv");
			}
			removeloadData_div("Edit_chanceOpenInfo_Window_Body_main");
		}
	})
}
function Edit_chanceOpenInfo_look_show_main(id){
	Edit_chanceOpenInfo_Query_id=id;
	Edit_chanceOpenInfo_query_function_window();
}