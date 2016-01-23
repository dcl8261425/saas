$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#rigister_success").html().replace("<!--", "").replace("-->", "");
		$.templates({
			rigister_success : tmpl
		});
	}
	function initbindEvent(){
		$("#rigister").bind("click",function(){
			var admin=$("#admin").val();
			var password=$("#password").val();
			var repassword=$("#repassword").val();
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"rigisterAdminOfConpany",
				data:{
					admin:admin,
					password:password,
					repassword:repassword
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$("#loginWindow").html($.render.rigister_success(msg));
						ui.alert(msg.info,20000,false);
					}else{
						ui.alert(msg.info,2000,false);
					}
				}
		    })
		})
	}
})