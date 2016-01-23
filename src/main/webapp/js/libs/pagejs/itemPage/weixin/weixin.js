$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#weixin_main").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/main",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("概览页面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_UserId_Set").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_UserId_Set",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("微信公众账号设置界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_VIP_set").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_VIP_set",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("会员制度设置页面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Game_set").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_Game_set",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("活动与游戏管理界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_convert_set").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_convert_set",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("序列号兑换界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_set").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_Model_set",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("模板设置界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_set_map").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_Model_set_map",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("地址设置界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
				$("#weixin_Model_goods_list").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_model_goodsManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("微信商品管理界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
$("#weixin_Model_order_list").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/weixin_model_order",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("微信订单管理界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#passwordChange").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"changePassword",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("修改密码界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
				$("#weixin_Model_wifi_list").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"wifiController/main",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("wifi页面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
						$("#weixin_Model_image").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/imageModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("图片模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_video").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/videoModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("视频模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_text").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/textModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("文本模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_game").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/gameModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("游戏模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_ershou").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/ershouModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("格子铺模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#weixin_Model_gonggao").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"weixin/page/gonggaoModelManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("公告模块读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
	}
});