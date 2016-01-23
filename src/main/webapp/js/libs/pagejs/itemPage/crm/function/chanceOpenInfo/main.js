var chanceOpenInfo_Query_page=1
var chanceOpenInfo_QueryItem_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		
		initDiv();
	})
	function initTemp(){
		var tmpl = $("#chanceOpenInfo_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenInfo_window_tbody_tr : tmpl
		});
		var tmpl = $("#chanceOpenInfo_window_add_info_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenInfo_window_add_info_window : tmpl
		});
		var tmpl = $("#chanceOpenInfo_window_look_info_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenInfo_window_look_info_window : tmpl
		});
		var tmpl = $("#chanceOpenInfo_window_look_info_window_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenInfo_window_look_info_window_tr : tmpl
		});
	}
	function initDiv(){
		$('#chanceOpenInfo_window').modal("show");
		chanceOpenInfo_query_function_window();
	}
})
function chanceOpenInfo_query_function_window(page){
	var name=$("#chanceOpenInfo_window_query_Name").val();
	if(page!=undefined||page!=null){
		chanceOpenInfo_Query_page=page;
	}
	addLoadData_div("chanceOpenInfo_Window_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryNotes",
		data:{
			chanceId:addlinkman_id,
			ChanceName:name,
			nowpage:chanceOpenInfo_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#chanceOpenInfo_window_tbody").html($.render.chanceOpenInfo_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:chanceOpenInfo_query_function_window('"+(parseInt(chanceOpenInfo_Query_page)-1)+"')\">«</a></li>";
				var startPage=chanceOpenInfo_Query_page>15?chanceOpenInfo_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=chanceOpenInfo_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:chanceOpenInfo_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:chanceOpenInfo_query_function_window('"+(parseInt(chanceOpenInfo_Query_page)+1)+"')\">»</a></li>"
				$("#chanceOpenInfo_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"chanceOpenInfo_AlertDiv");
			}
			removeloadData_div("chanceOpenInfo_Window_Body");
		}
	})
}
function chanceOpenInfo_add_function_window(){
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
				alertSuccess(msg.info,"chanceOpenInfo_AlertDiv");
				chanceOpenInfo_query_function_window(1);
			}else{
				alertError(msg.info,"chanceOpenInfo_AlertDiv");
			}
		}
	})
}
function chanceOpenInfo_look_show(id,page){
	$("#chanceOpenInfo_window_add_window_div").html($.render.chanceOpenInfo_window_look_info_window());
	$('#chanceOpenInfo_window_look_info_window_window').modal("show");
	chanceOpenInfo_look(id,page)
}

function chanceOpenInfo_look(id,page){
	if(page!=undefined||page!=null){
		chanceOpenInfo_QueryItem_page=page;
	}
	addLoadData_div("chanceOpenInfo_Window_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryNotesItem",
		data:{
			NotesId:id,
			nowpage:chanceOpenInfo_QueryItem_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){

				$("#chanceOpenInfo_window_look_tbody").html($.render.chanceOpenInfo_window_look_info_window_tr(msg.data));
				var page_html="<li><a href=\"javascript:chanceOpenInfo_look("+id+",'"+(parseInt(chanceOpenInfo_QueryItem_page)-1)+"')\">«</a></li>";
				var startPage=chanceOpenInfo_QueryItem_page>15?chanceOpenInfo_QueryItem_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=chanceOpenInfo_QueryItem_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:chanceOpenInfo_look("+id+",'"+ii+"')\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:chanceOpenInfo_look("+id+",'"+(parseInt(chanceOpenInfo_QueryItem_page)+1)+"')\">»</a></li>"
				$("#chanceOpenInfo_window_look_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"chanceOpenInfo_AlertDiv");
			}
			removeloadData_div("chanceOpenInfo_Window_Body");
		}
	})

}
function chanceOpenInfo_add(id){
	$("#chanceOpenInfo_window_add_window_div").html($.render.chanceOpenInfo_window_add_info_window());
	$('#chanceOpenInfo_window_add_info_window_window').modal("show");
	$("#chanceOpenInfo_window_enter_add_but").attr("onclick","chanceOpenInfo_add_enter("+id+")");
}
function chanceOpenInfo_add_enter(id){
	var Title=$("#chanceOpenInfo_window_Title").val();
	var Marks=$("#chanceOpenInfo_window_Marks").val();
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
				$('#chanceOpenInfo_window_add_info_window_window').modal("hide");
				alertSuccess(msg.info,"chanceOpenInfo_Add_AlertDiv");
				chanceOpenInfo_query_function_window(1);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"chanceOpenInfo_Add_AlertDiv");
				}else{
					inputAjaxTest(list,"chanceOpenInfo_window_");
				}
			}
		}
	})
}
function look_Notes_Info_marks(id){
	$("#"+id).toggle();
}