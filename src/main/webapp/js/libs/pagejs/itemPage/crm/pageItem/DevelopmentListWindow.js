var rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page=1;
var addlinkman_id;
$(function () {
	$(document).ready(function(){
		var tmpl = $("#crm_window_layout_toDevelopent_window_line").html().replace("<!--", "").replace("-->", "");
		$.templates({
			crm_window_layout_toDevelopent_window_line : tmpl
		});
		var tmpl = $("#crm_window_layout_toDevelopent_window_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			crm_window_layout_toDevelopent_window_tr : tmpl
		});
		var tmpl = $("#crm_window_layout_toDevelopent_window_look").html().replace("<!--", "").replace("-->", "");
		$.templates({
			crm_window_layout_toDevelopent_window_look : tmpl
		});
		var tmpl = $("#crm_window_layout_toDevelopent_window_add").html().replace("<!--", "").replace("-->", "");
		$.templates({
			crm_window_layout_toDevelopent_window_add : tmpl
		});
		addLoadData_div("crm_window_layout_toDevelopent_window");
		$.ajax({
			type : "POST",
			url : ctx+"crm/function/queryToMyChance",
			data:{
				ChanceName:"",
				nowpage:1,
				countNum:13
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#crm_window_layout_toDevelopent_window_content").html($.render.crm_window_layout_toDevelopent_window_line(msg.data));
				}else{
					alertError(msg.info,"crm_window_layout_toDevelopent_window_alert_div");
				}
				removeloadData_div("crm_window_layout_toDevelopent_window");
			}
		})
	})
});
function crm_window_layout_toDevelopent_log(id){
	addlinkman_id=id;
	$("#crm_window_layout_toDevelopent_window_div").html($.render.crm_window_layout_toDevelopent_window_add());
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
			chanceId:id,
			NotesId:0,
			Title:Title,
			Marks:Marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#chanceOpenInfo_window_add_info_window_window').modal("hide");
				alertSuccess(msg.info,"chanceOpenInfo_Add_AlertDiv");
				
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
function crm_window_layout_toDevelopent_log_show(id,page){
	$("#crm_window_layout_toDevelopent_window_div").html($.render.crm_window_layout_toDevelopent_window_look());
	crm_window_layout_toDevelopent_look(id,page)
}
function crm_window_layout_toDevelopent_look(id,page){
	if(page!=undefined||page!=null){
		rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page=page;
	}
	addLoadData_div("crm_window_layout_toDevelopent_window");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryNotesItem",
		data:{
			chanceId:id,
			NotesId:0,
			nowpage:rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page,
			countNum:15
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){

				$("#chanceOpenInfo_window_look_tbody").html($.render.crm_window_layout_toDevelopent_window_tr(msg.data));
				var page_html="<li><a href=\"javascript:crm_window_layout_toDevelopent_look("+id+",'"+(parseInt(rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page)-1)+"')\">«</a></li>";
				var startPage=rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page>15?rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:crm_window_layout_toDevelopent_look("+id+",'"+ii+"')\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:crm_window_layout_toDevelopent_look("+id+",'"+(parseInt(rm_window_layout_toDevelopent_chanceOpenInfo_QueryItem_page)+1)+"')\">»</a></li>"
				$("#chanceOpenInfo_window_look_tbody_page").html(page_html);
				$('#chanceOpenInfo_window_look_info_window_window').modal("show");
			}else{
				$("#crm_window_layout_toDevelopent_window_alert_div").html("");
				alertError(msg.info,"crm_window_layout_toDevelopent_window_alert_div");
			}
			removeloadData_div("crm_window_layout_toDevelopent_window");
		}
	})
}
function look_Notes_Info_marks(id){
	$("#"+id).toggle();
}
