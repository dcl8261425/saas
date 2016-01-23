var weixin_vip_num=1;
$(function () {
$(document).ready(function(){
		initTemp();
		initbindEvent();
		weixin_vip_query(1);
	})
	function initTemp(){
		var tmpl = $("#weixin_vip_list_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_list_item : tmpl
		});
		var tmpl = $("#weixin_vip_list_item_table").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_list_item_table : tmpl
		});
	};

	function initbindEvent(){
		
	}
})
function weixin_vip_query(page){
	if(page!=undefined){
		weixin_vip_num=page;
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/getVipOrderList",
		data:{
			nowpage:weixin_vip_num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_vip_content").html($.render.weixin_vip_list_item(msg.data));
			}else{
				
			}
		}
	})
}
function weixin_vip_up(){
	if(weixin_vip_num>1){
		weixin_vip_num=weixin_vip_num-1;
	}
	weixin_vip_query();
}
function weixin_vip_next(){
		weixin_vip_num=weixin_vip_num+1;
		weixin_vip_query();
}
function weixin_vip_order_look_info(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/getVipOrderItemList",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#rowTable_"+id).html($.render.weixin_vip_list_item_table(msg));
			}else{
				
			}
		}
	})
}