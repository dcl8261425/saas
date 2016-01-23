var weixin_order_page=1;
var weixin_order_song;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_order_weisong").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_order_weisong : tmpl
		});
		var tmpl = $("#weixin_order_yisong").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"weixin_order_yisong" : tmpl
		});
		var tmpl = $("#weixin_yisong_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"weixin_yisong_window_tbody_tr" : tmpl
		});
		var tmpl = $("#weixin_weisong_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"weixin_weisong_window_tbody_tr" : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function weixin_order_weisong(){
	$("#right_layout").html($.render.weixin_order_weisong());
	
}
function weixin_order_yisong(){
	$("#right_layout").html($.render.weixin_order_yisong());
}
function weixin_order_query(song,page){
	var num=$("#weixin_order_num_input").val();
	weixin_order_song=song;
	weixin_order_page=page;
	var i=$.layer({
		type : 3
	});
$.ajax({
	type : "POST",
	url : ctx+"weixin/order/look",
	data:{
		song:song,
		name:num,
		nowpage:weixin_order_page,
		countNum:30
	},
	success : function(msg) {
		layer.close(i);
		if(msg.success==null||msg.success==undefined||msg.success==true){
			if(song){
				$("#weixin_tbody").html($.render.weixin_yisong_window_tbody_tr(msg.data));
			}else{
				
				$("#weixin_tbody").html($.render.weixin_weisong_window_tbody_tr(msg.data));
			}
			var page_html="<li><a href=\"javascript:weixin_order_query("+song+",'"+(parseInt(weixin_order_page)-1)+"')\">«</a></li>";
			var startPage=weixin_order_page>15?weixin_order_page-14:1;
			var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
			for(var ii=startPage;ii<=endPage;ii++){
				var classtype=weixin_order_page==ii?"active":"";
				page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:weixin_order_query("+song+",'"+ii+"')\">"+ii+"</a></li>";
			}
			page_html=page_html+"<li><a href=\"javascript:weixin_order_query("+song+",'"+(parseInt(weixin_order_page)+1)+"')\">»</a></li>"
			$("#weixin_tbody_page").html(page_html);
		}else{
			
		}
	}
})
}

function weixin_order_enter(id){
	var i=$.layer({
		type : 3
	});
$.ajax({
	type : "POST",
	url : ctx+"weixin/order/enter",
	data:{
		id:id
	},
	success : function(msg) {
		layer.close(i);
		if(msg.success==null||msg.success==undefined||msg.success==true){
			weixin_order_query(weixin_order_song,weixin_order_page);
			alertSuccess(msg.info,"weixin_convert_alert");
		}else{
			alertError(msg.info,"weixin_convert_alert");
		}
	}
})
}