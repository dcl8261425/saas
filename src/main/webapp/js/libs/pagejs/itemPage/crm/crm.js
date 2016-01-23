$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#crm_main").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/page/main",
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
		$("#crm_myCreateCustomChance").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/page/myCreateCustomChance",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("我创建的客户机会界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#crm_toMyCustomChance").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/page/toMyCustomChance",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("分配给我的机会页面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#crm_MyCustomManager").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/page/MyCustomManager",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("我的客户管理界面读取成功","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#crm_queryAllChanceList").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"crm/page/queryAllChanceList",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("全部客户机会管理页面","content");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
	}
});