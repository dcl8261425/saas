$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#messageInfo_main").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"messageinfo/main",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("短信设置页面成功加载","message_set_alert");
						phoneMessage();
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#messageInfo_vipsend").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"messageinfo/vipsend",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("会员发信页面成功加载","message_set_alert");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#messageInfo_messagetemp").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"messageinfo/messagetemp",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("信息模板页面成功加载","message_set_alert");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		$("#messageInfo_messagelog").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"messageinfo/messagelog",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						layoutRefresh(msg)
						alertSuccess("信息记录页面成功加载","message_set_alert");
					}else{
						alertError(msg.info,"content");
					}
				}
		    })
		})
		
	}
});