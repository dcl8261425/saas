var weixin_good_page=1;
var weixin_good_id;
$(function () {
	$(document).ready(function(){
		initTemp();
		good_query_data();
		
	})
	function initTemp(){
		var tmpl = $("#good_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"good_tr" : tmpl
		});
	}
})
//$.render.GoodSource_box_tbody_tr(msg.data)
function good_query_data(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/goods_data",
		data:{
			nowpage:weixin_good_page,
			countNum:30
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.data.length>0){
					$("#good_body").html($.render.good_tr(msg.data));
				}else{
					weixin_good_page=weixin_good_page-1;
				}
			}else{
				
			}
		}
	})
}
function good_page_next(){
	weixin_good_page=weixin_good_page+1;
	good_query_data();
}
function good_page_up(){
	if(weixin_good_page>0){
	weixin_good_page=weixin_good_page-1;
	good_query_data();
	}
}
function showItem(conpanyId,id){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/goodsItem",
		data:{
			id:id,
			conpanyId:conpanyId
		},
		success : function(msg) {
			layer.close(i);
			$("#good_body").hide();
			$("#good_body_page").hide();
			$("#good_body_item_content").show();
			$("#good_body_item_content").html(msg);
		}
	})
}
function addCar(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/addCar",
		data:{
			id:weixin_good_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#addcar').removeClass('is-visible');
				weixin_alert_open(msg.info);
			}else{
				$('#addcar').removeClass('is-visible');
				weixin_alert_error_open(msg.info);
			}
			
		}
	})
}
function backGoodsList(){
	$("#good_body_item_content").hide();
	$("#good_body_item_content").html("");
	$("#good_body").show();
	$("#good_body_page").show();
}
function weixin_good_window_open(id){
	weixin_good_id=id;
	$('#addcar').addClass('is-visible');
}
function weixin_good_window_close(){
	$('#addcar').removeClass('is-visible');
}
function weixin_alert_open(msg){
	$("#good_alert_body").html(msg);
	$('#alert').addClass('is-visible');
}
function weixin_alert_close(){
	$('#alert').removeClass('is-visible');
}
function weixin_alert_error_open(msg){
	$("#good_alert_error_body").html(msg);
	$('#alert_error').addClass('is-visible');
}
function weixin_alert_error_close(){
	$('#alert_error').removeClass('is-visible');
	
}