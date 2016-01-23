var conpanyId;
var mapData;
var refalsh;
$(document).ready(function() {
	conpanyId = $("#conpanyId").val();
	var url=window.location.href;
	var s=url.split("#");
	if(s.length>=2){
		if(s[1]=='dazhuanpan'){
			weixin_model_dazhuanpan_but();
		}
		if(s[1]=='guaguaka'){
			weixin_model_guagua_but();
		}
		if(s[1]=='vip'){
			weixin_model_vip_but();
		}
		if(s[1]=='gouwu'){
			weixin_model_shop_but();
		}
		if(s[1]=='jifen'){
			weixin_model_jifenduihuan_but();
		}
		if(s[1]=='map'){
			weixin_model_map_but();
		}
	}
});
function weixin_model_map_but() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/querymap?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			if (msg.success == true) {
				mapData = msg.data;
				var ii = $.layer({
					type : 3
				});
				$.ajax({
					type : "POST",
					url : ctx + "weixin/public/map?conpanyId=" + conpanyId,
					data : {},
					success : function(msg) {
						layer.close(ii);
						$("#content").html(msg);
					}
				})
			}
		}
	})
}
function weixin_model_vip_but() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/vip?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_model_guagua_but() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/guaguaka?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_model_dazhuanpan_but() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/dazhuanpan?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_model_shop_but() {
	var i = $.layer({
		type : 3
	});

	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/goods?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_model_jifenduihuan_but(){
	var i = $.layer({
		type : 3
	});

	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/jifenduihuan?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_model_buycar_but(){

	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/buycar?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			$("#content").html(msg);
		}
	})
}
function weixin_model_reflash_open(){
	refalsh = $.layer({
		type : 3
	});
}
function weixin_model_reflash_close(){
	layer.close(refalsh);
}