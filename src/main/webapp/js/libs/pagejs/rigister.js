$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		_init_area();//启动地址搜索
		$("#conpanyType_but").bind("click",function(){
			
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"getHangye",
				data:{
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$("#rigister_a_content").html($.render.rigister_a(msg.data));
						$('#AddLinkMan_window_add_but').modal("show");
					}else{
						
					}
				}
		    })
			
		})
		$("#getTest").bind("click",function(){
			var phone=$("#softAdminPhone").val();
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url :  ctx+"conpany/getTest",
				data:{
					phone:phone
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						ui.alert(msg.info,2000,false);
					}else{
						ui.alert(msg.info,2000,false);
					}
				}
		    })
			
		})
	})
	function initTemp(){
		var tmpl = $("#rigister_success").html().replace("<!--", "").replace("-->", "");
		$.templates({
			rigister_success : tmpl
		});
		var tmpl = $("#rigister_a").html().replace("<!--", "").replace("-->", "");
		$.templates({
			rigister_a : tmpl
		});
	}
	function initbindEvent(){
		$("#rigister").bind("click",function(){
			var s_province=$("#s_province").val();
			var s_city=$("#s_city").val();
			var s_county=$("#s_county").val();
		    var conpanyName=$("#conpanyName").val();
			var softAdminName=$("#softAdminName").val();
			var softAdminPhone=$("#softAdminPhone").val();
			var conpanyType=$("#conpanyType").val();
			var conpanyType_id=$("#conpanyType_id").val();
			var conpanyAdminEmail=$("#conpanyAdminEmail").val();
			var testcode=$("#testcode").val();
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"conpany/createConpany",
				data:{
					conpanyName:conpanyName,
					softAdminName:softAdminName,
					softAdminPhone:softAdminPhone,
					conpanyType:conpanyType,
					conpanyType_id:conpanyType_id,
					code:testcode,
					conpanyAdminEmail:conpanyAdminEmail,
					province:s_province,
					city:s_city,
					district:s_county
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						
						window.location=msg.url;
					}else{
						ui.alert(msg.info,2000,false);
					}
				}
		    })
		})
	}
})
function rigister_select_a(id,name){
	$("#conpanyType").val(name);
	$("#conpanyType_id").val(id);
	$('#AddLinkMan_window_add_but').modal("hide");
}