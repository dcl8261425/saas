var StoreHouse_box_EditLinkDate_serch_page=1;
var StoreHouse_box_EditLinkDate_element_id;
var StoreHouse_box_EditLinkDate_element_text;
var StoreHouse_box_is_table=false;
var StoreHouse_box_is_table_function;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		$('#StoreHouse_box_EditLinkDat_window').modal("show");
	})
	function initTemp(){
		var tmpl = $("#StoreHouse_box_EditLinkDate_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			StoreHouse_box_EditLinkDate_tbody_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
});
function StoreHouse_box_EditLinkDate_serch(page){
	var name=$("#StoreHouse_box_EditLinkDate_name").val();
	if(page!=undefined||page!=null){
		StoreHouse_box_EditLinkDate_serch_page=page;
	}
	addLoadData_div("StoreHouse_box_EditLinkDate");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryStoreHouse",
		data:{
			name:name,
			nowpage:StoreHouse_box_EditLinkDate_serch_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#StoreHouse_box_EditLinkDate_tbody").html($.render.StoreHouse_box_EditLinkDate_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:StoreHouse_box_EditLinkDate_serch('"+(parseInt(StoreHouse_box_EditLinkDate_serch_page)-1)+"')\">«</a></li>";
				var startPage=StoreHouse_box_EditLinkDate_serch_page>15?StoreHouse_box_EditLinkDate_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=StoreHouse_box_EditLinkDate_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:StoreHouse_box_EditLinkDate_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:StoreHouse_box_EditLinkDate_serch('"+(parseInt(StoreHouse_box_EditLinkDate_serch_page)+1)+"')\">»</a></li>"
				$("#StoreHouse_box_EditLinkDate_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"StoreHouse_box_EditLinkDate_alertDiv");
			}
			removeloadData_div("StoreHouse_box_EditLinkDate");
		}
	})
}
function StoreHouse_box_EditLinkDate_select(id,name){
	if(!StoreHouse_box_is_table){
		if(StoreHouse_box_EditLinkDate_element_text!=undefined||StoreHouse_box_EditLinkDate_element_text!=null){
			$("#"+StoreHouse_box_EditLinkDate_element_id).val(id);
			$("#"+StoreHouse_box_EditLinkDate_element_text).val(name);
		}else{
			var str="<a id='StoreHouse_"+id+Edit_chance_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'StoreHouse','StoreHouse_"+id+Edit_chance_add_num+"')\">"+name+"</a>"
			Edit_chance_add_num++;
			$("#"+StoreHouse_box_EditLinkDate_element_id).val($("#"+StoreHouse_box_EditLinkDate_element_id).val()+str);
		}
	}else{
		StoreHouse_box_is_table_function.call(id,id,name);
	}
	$('#StoreHouse_box_EditLinkDat_window').modal("hide");
}