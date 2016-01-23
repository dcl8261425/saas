var GoodSource_box_EditLinkDate_serch_page=1;
var GoodSource_box_EditLinkDate_element_id;
var GoodSource_box_EditLinkDate_element_text;
var GoodSource_box_EditLinkDate_linkman_window_page=1;
var GoodSource_box_EditLinkDate_linkman_id;
var GoodSource_box_select_type_linkman=true;//是否叫用户选择联系人
var GoodSource_box_is_table=false;
var GoodSource_box_is_table_function;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		$("#GoodSource_box_EditLinkDate_window").modal("show");
	})
	function initTemp(){
		var tmpl = $("#GoodSource_box_EditLinkDate_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_EditLinkDate_tbody_tr : tmpl
		});
		var tmpl = $("#GoodSource_box_EditLinkDate_linkman_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_EditLinkDate_linkman_window_temp : tmpl
		});
		var tmpl = $("#GoodSource_box_EditLinkDate_linkman_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			GoodSource_box_EditLinkDate_linkman_window_tbody_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}

});
function GoodSource_box_EditLinkDate_serch(page){
	var name=$("#GoodSource_box_EditLinkDate_name").val();
	if(page!=undefined||page!=null){
		GoodSource_box_EditLinkDate_serch_page=page;
	}
	addLoadData_div("GoodSource_box_EditLinkDate");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoodsSource",
		data:{

			name:name,
			nowpage:GoodSource_box_EditLinkDate_serch_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#GoodSource_box_EditLinkDate_tbody").html($.render.GoodSource_box_EditLinkDate_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:GoodSource_box_EditLinkDate_serch('"+(parseInt(GoodSource_box_EditLinkDate_serch_page)-1)+"')\">«</a></li>";
				var startPage=GoodSource_box_EditLinkDate_serch_page>15?GoodSource_box_EditLinkDate_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=GoodSource_box_EditLinkDate_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:GoodSource_box_EditLinkDate_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:GoodSource_box_EditLinkDate_serch('"+(parseInt(GoodSource_box_EditLinkDate_serch_page)+1)+"')\">»</a></li>"
				$("#GoodSource_box_EditLinkDate_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"Edit_GoodSource_box_EditLinkDate_window_alert");
			}
			removeloadData_div("GoodSource_box_EditLinkDate");
		}
	})
}

function GoodSource_box_EditLinkDate_addlinkman(id){
	if(GoodSource_box_select_type_linkman){
		$("#GoodSource_box_EditLinkDate_window_window").html($.render.GoodSource_box_EditLinkDate_linkman_window_temp());
		$("#GoodSource_box_EditLinkDate_linkman_window").modal("show");
		GoodSource_box_EditLinkDate_linkman_id=id;
		GoodSource_box_EditLinkDate_linkman_window_serch();
	}else{
		alertError("不可选择联系人","Edit_GoodSource_box_EditLinkDate_window_alert");
	}
	
}
function GoodSource_box_EditLinkDate_linkman_window_serch(page){
	var name=$("#GoodSource_box_EditLinkDate_linkman_window_name").val();
	if(page!=undefined||page!=null){
		GoodSource_box_EditLinkDate_linkman_window_page=page;
	}
	addLoadData_div("GoodSource_box_EditLinkDate_linkman_window_box");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoodsSourceLinkman",
		data:{
			id:GoodSource_box_EditLinkDate_linkman_id,
			name:name,
			nowpage:GoodSource_box_EditLinkDate_linkman_window_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#GoodSource_box_EditLinkDate_linkman_window_tbody").html($.render.GoodSource_box_EditLinkDate_linkman_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:GoodSource_box_EditLinkDate_linkman_window_serch('"+(parseInt(GoodSource_box_EditLinkDate_linkman_window_page)-1)+"')\">«</a></li>";
				var startPage=GoodSource_box_EditLinkDate_linkman_window_page>15?GoodSource_box_EditLinkDate_linkman_window_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=GoodSource_box_EditLinkDate_linkman_window_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:GoodSource_box_EditLinkDate_linkman_window_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:GoodSource_box_EditLinkDate_linkman_window_serch('"+(parseInt(GoodSource_box_EditLinkDate_linkman_window_page)+1)+"')\">»</a></li>"
				$("#GoodSource_box_EditLinkDate_linkman_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"GoodSource_box_EditLinkDate_linkman_window_temp_AlertDiv");
			}
			removeloadData_div("GoodSource_box_EditLinkDate_linkman_window_box");
		}
	})
}
function GoodSource_box_EditLinkDate_select(id,name){
	if(!GoodSource_box_is_table){
		if(GoodSource_box_EditLinkDate_element_text!=undefined||GoodSource_box_EditLinkDate_element_text!=null){
			$("#"+GoodSource_box_EditLinkDate_element_id).val(id);
			$("#"+GoodSource_box_EditLinkDate_element_text).val(name);
		}else{
			var str="<a id='GoodSource_box_"+id+Edit_chance_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'goodSource','GoodSource_box_"+id+Edit_chance_add_num+"')\">"+name+"</a>"
			Edit_chance_add_num++;
			$("#"+GoodSource_box_EditLinkDate_element_id).val($("#"+GoodSource_box_EditLinkDate_element_id).val()+str);
		}
	}else{
		GoodSource_box_is_table_function.call(id,id,name);
	}
	$('#GoodSource_box_EditLinkDate_window').modal("hide");
}
function GoodSource_box_EditLinkDate_linkman_select(id,name){
		if(GoodSource_box_EditLinkDate_element_text!=undefined||GoodSource_box_EditLinkDate_element_text!=null){
			$("#"+GoodSource_box_EditLinkDate_element_id).val(id);
			$("#"+GoodSource_box_EditLinkDate_element_text).val(name);
		}else{
			var str="<a id='GoodSource_box_linkman_"+id+Edit_chance_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'goodsourceLinkMan','GoodSource_box_linkman_"+id+Edit_chance_add_num+"')\">"+name+"</a>"
			Edit_chance_add_num++;
			$("#"+GoodSource_box_EditLinkDate_element_id).val($("#"+GoodSource_box_EditLinkDate_element_id).val()+str);
		}
		$('#GoodSource_box_EditLinkDate_linkman_window').modal("hide");
		$('#GoodSource_box_EditLinkDate_window').modal("hide");

}