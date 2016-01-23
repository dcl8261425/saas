var good_queryData_serch_page=1;
var is_select=false;
var return_function;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		$('#good_queryData_window').modal("show");
	})
	function initTemp(){
		var tmpl = $("#good_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			good_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#good_queryData_window_tbody_tr_select").html().replace("<!--", "").replace("-->", "");
		$.templates({
			good_queryData_window_tbody_tr_select : tmpl
		});
	}
	function initbindEvent(){
	}
});
function good_queryData_box_serch(page){
	var name=$("#good_queryData_window_query_Name").val();
	var spell=$("#good_queryData_window_query_spell").val();
	if(page!=undefined||page!=null){
		good_queryData_serch_page=page;
	}
	addLoadData_div("good_queryData_Body");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoods",
		data:{
			name:name,
			spell_query:spell,
			nowpage:good_queryData_serch_page,
			countNum:10
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(is_select){
					$("#good_queryData_window_tbody").html($.render.good_queryData_window_tbody_tr_select(msg.data));
				}else{
					$("#good_queryData_window_tbody").html($.render.good_queryData_window_tbody_tr(msg.data));
				}
				var page_html="<li><a href=\"javascript:good_queryData_box_serch('"+(parseInt(good_queryData_serch_page)-1)+"')\">«</a></li>";
				var startPage=good_queryData_serch_page>15?good_queryData_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=good_queryData_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:good_queryData_box_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:good_queryData_box_serch('"+(parseInt(good_queryData_serch_page)+1)+"')\">»</a></li>"
				$("#good_queryData_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"good_queryData_AlertDiv");
			}
			removeloadData_div("good_queryData_Body");
		}
	})
}
function good_queryData_window_select(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/getGoods",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.info!=null||msg.info!=undefined){
					alertSuccess(msg.info,"good_queryData_AlertDiv");
				}
				return_function.call(this,msg.obj);
				$('#good_queryData_window').modal("hide");
			}else{
				alertError(msg.info,"good_queryData_AlertDiv");
			}
		}
	})
}