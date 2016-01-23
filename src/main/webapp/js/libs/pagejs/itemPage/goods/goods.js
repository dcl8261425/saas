$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#goods_main").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/main",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("概览页面成功加载","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#goods_queryData").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/queryData",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("库存查询页面成功加载","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#goods_inGoods").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/inGoods",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("进货页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#goods_outGoods").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/outGoods",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("出货页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#goods_storehouse").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/storehouse",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("仓库管理页面成功加载","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#goods_GoodSource").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"goods/page/GoodSource",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("仓库管理页面成功加载","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
	}
});