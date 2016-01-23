
var Edit_AddLinkManWindow_Query_page=1;
var Custmor_Edit_AddLinkManWindow_Query_page=1;
var Edit_linkManId;
var Edit_AddLinkManWindow_Query_linkman=true;
var Edit_AddLinkManWindow_Query_alone_linkman=false;
$(function () {
	$(document).ready(function(){
		initTemp();
	})
	function initTemp(){
		var tmpl = $("#Edit_AddLinkMan_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_AddLinkMan_window_tbody_tr : tmpl
		});
		var tmpl = $("#Custmor_Edit_AddLinkMan_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Custmor_Edit_AddLinkMan_window_tbody_tr : tmpl
		});
	}
})
function Edit_AddLinkManWindow_initDiv(){
		$('#Edit_AddLinkMan_window').modal("show");

		Custmor_Edit_AddLinkManList_query_function_window();
		
	}
/**
 * 弹出添加框
 */
function Edit_AddLinkManList_add_function_window(){
	$("#Edit_AddLinkMan_window_add_window_div").html($.render.Edit_AddLinkMan_window_add_window());
	$('#Edit_AddLinkMan_window_add_but').modal("show");
	$('#Edit_AddLinkMan_window_input_linkManBirthday .input-group.date').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
}
/**
 * 查询联系人
 */
function Edit_AddLinkManList_query_function_window(page){
	if(!Edit_AddLinkManWindow_Query_linkman){
		alertError("不允许选择联系人","Edit_AddLinkManWindow_AlertDiv");
		return;
	}
	var name=$("#Edit_AddLinkMan_window_query_Name").val();
	if(page!=undefined||page!=null){
		Edit_AddLinkManWindow_Query_page=page;
	}
	addLoadData_div("Edit_AddLinkManWindow_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryLinkMan",
		data:{
			chanceId:Edit_linkManId,
			ChanceName:name,
			nowpage:Edit_AddLinkManWindow_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#Edit_AddLinkMan_window_tbody").html($.render.Edit_AddLinkMan_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:Edit_AddLinkManList_query_function_window('"+(parseInt(Edit_AddLinkManWindow_Query_page)-1)+"')\">«</a></li>";
				var startPage=Edit_AddLinkManWindow_Query_page>15?Edit_AddLinkManWindow_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=Edit_AddLinkManWindow_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:Edit_AddLinkManList_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:Edit_AddLinkManList_query_function_window('"+(parseInt(Edit_AddLinkManWindow_Query_page)+1)+"')\">»</a></li>"
				$("#Edit_AddLinkMan_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"Edit_AddLinkManWindow_AlertDiv");
			}
			removeloadData_div("Edit_AddLinkManWindow_Body");
		}
	})
}
/**
 * 查询客户
 */
function Custmor_Edit_AddLinkManList_query_function_window(page){
	var name=$("#Custmor_Edit_AddLinkMan_window_query_Name").val();
	if(page!=undefined||page!=null){
		Edit_AddLinkManWindow_Query_page=page;
	}
	addLoadData_div("Edit_AddLinkManWindow_customer_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryAllChance",
		data:{
			ChanceName:name,
			nowpage:Custmor_Edit_AddLinkManWindow_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#Custmor_Edit_AddLinkMan_window_tbody").html($.render.Custmor_Edit_AddLinkMan_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:Custmor_Edit_AddLinkManList_query_function_window('"+(parseInt(Custmor_Edit_AddLinkManWindow_Query_page)-1)+"')\">«</a></li>";
				var startPage=Custmor_Edit_AddLinkManWindow_Query_page>15?Custmor_Edit_AddLinkManWindow_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=Custmor_Edit_AddLinkManWindow_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:Custmor_Edit_AddLinkManList_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:Custmor_Edit_AddLinkManList_query_function_window('"+(parseInt(Custmor_Edit_AddLinkManWindow_Query_page)+1)+"')\">»</a></li>"
				$("#Custmor_Edit_AddLinkMan_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"Edit_AddLinkManWindow_AlertDiv");
			}
			removeloadData_div("Edit_AddLinkManWindow_customer_Body");
		}
	})
}
function Edit_queryLinkMan(id){
	Edit_linkManId=id;
	Edit_AddLinkManList_query_function_window();
}
function Edit_enter_customer(id,name){
	if(Edit_AddLinkManWindow_Query_alone_linkman){
		alertError("只允许选择联系人，购物积分直接添加至联系人。","Edit_AddLinkManWindow_AlertDiv");
		return "";
	}
	if(Edit_Item_ElementId_text!=undefined||Edit_Item_ElementId_text!=null){
		$("#"+Edit_Item_ElementId_id).val(id);
		$("#"+Edit_Item_ElementId_text).val(name);
	}else{
		var str="<a id='linkman_"+id+Edit_linkman_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'custemor','linkman_"+id+Edit_linkman_add_num+"')\">"+name+"</a>"
		Edit_linkman_add_num=Edit_linkman_add_num+1;
		$("#"+Edit_Item_ElementId_id).val($("#"+Edit_Item_ElementId_id).val()+str);
	}
	$("#Edit_AddLinkMan_window").modal("hide");
}
function Edit_enter_LinkMan(id,name){
	if(Edit_Item_ElementId_text!=undefined||Edit_Item_ElementId_text!=null){
		$("#"+Edit_Item_ElementId_id).val(id);
		$("#"+Edit_Item_ElementId_text).val(name);
	}else{
		var str="<a  id='linkman_"+id+Edit_linkman_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'linkman','linkman_"+id+Edit_linkman_add_num+"')\">"+name+"</a>"
		Edit_linkman_add_num=Edit_linkman_add_num+1;
		$("#"+Edit_Item_ElementId_id).val($("#"+Edit_Item_ElementId_id).val()+str);
	}
	$("#Edit_AddLinkMan_window").modal("hide");
}