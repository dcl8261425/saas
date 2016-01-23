$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#hr_main").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/main",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("概览页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_custemr").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/custemr",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("员工账户页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_price").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/price",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("工资页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_meeting").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/meeting",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("签到页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_performance").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/performance",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("绩效页面成功加载","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_waiqin").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/waiqin",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("在这里您可以看您的外勤人员的外出线路","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#hr_kongjian").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/page/kongjian",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("在这里可以跟您的团队沟通","alertDiv");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
	}
});