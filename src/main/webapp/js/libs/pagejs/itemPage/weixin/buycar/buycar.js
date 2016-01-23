var buycar_conpanyId=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		query_buycar_list();
	})
	function initTemp(){
		var tmpl = $("#buycar_list_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"buycar_list_item" : tmpl
		});
		var tmpl = $("#buycar_list_item_null").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"buycar_list_item_null" : tmpl
		});
	}
})
function query_buycar_list(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/getCar",
		data:{
		
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#buycar_list").html($.render.buycar_list_item(msg.data));
			}else{
				$("#buycar_list").html($.render.buycar_list_item_null());
			}
		}
	})
}
function buycarjiannum(id,conpanyId){
	var num=parseInt($("#buycarNum_"+id).html());
	if(num>1){
		var i = $.layer({
			type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+"weixin/public/deleteCarItem",
			data:{
				id:id,
				conpanyIdbuycar:conpanyId
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#buycarNum_"+id).html(num-1);
				}else{
					
				}
			}
		})
	}
}
function buycaraddnum(id,conpanyId){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/addCar",
		data:{
			id:id,
			conpanyIdbuycar:conpanyId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var num=parseInt($("#buycarNum_"+id).html());
				$("#buycarNum_"+id).html(num+1);
			}else{
				
			}
		}
	})
	
}
function buycardelete(id,conpanyId){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/deleteCarItem",
		data:{
			id:id,
			type:"delete",
			conpanyIdbuycar:conpanyId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#buyar_row_"+id).remove();
			}else{
				
			}
		}
	})
}
function submitBuyCar(conpanyid){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/submitOrder",
		data:{
			buycarConpanyId:conpanyid
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#buyar_"+conpanyid).remove();
			}else{
				
			}
		}
	})
}
function submitBuyCar_enter(){
	var address=$("#buyCar_address").val();
	var phone=$("#buyCar_phone").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/submitOrder",
		data:{
			buycarConpanyId:buycar_conpanyId,
			address:address,
			phone:phone
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#buyar_"+buycar_conpanyId).remove();
			}else{
				
			}
			weixin_buycar_window_close();
		}
	})
}
function weixin_buycar_window_open(id){
	buycar_conpanyId=id;
	$('#buyCar_alert').addClass('is-visible');
}
function weixin_buycar_window_close(){
	$('#buyCar_alert').removeClass('is-visible');
}