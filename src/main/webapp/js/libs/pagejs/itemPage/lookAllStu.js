$(function () {
	$(document).ready(function(){
		initbindEvent();
		$("#lookAllStu").click();
		setInterval(function (){
			 $.ajax({
					type : "get",
					url : ctx,
					data:{
					},
					success : function(msg) {
						
					}
			    })
		},1200000)
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
		$("#lookAllStu").bind("click",function(){
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"itempage/lookAllStu",
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
	}
});