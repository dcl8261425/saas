$(document).ready(function() {
	$(document).ready(function(){
		$("#hujiao").hide();
	})
	
});
function weixin_vip_order(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/vip_order?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_vip_num(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/vip_num?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_vip_Score(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/vip_scroe?conpanyId=" + conpanyId,
		data : {},
		success : function(msg) {
			layer.close(i);
			$("#content").html(msg);
		}
	})
}
function weixin_vip_reflash(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/reflashInfo",
		data : {},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_vip_level").html("vip等级:"+msg.obj.vipLevel);
				$("#weixin_vip_marks").html("待遇:"+msg.obj.vipMarks);
				$("#weixin_vip_score").html(msg.obj.linkManScore);
				$("#weixin_vip_price").html("余额:"+msg.obj.money);
			}else{
				
			}
		}
	})
}
function weixin_vip_rigister_window_open(){
	$('#weixin_vip_rigister_alert').addClass('is-visible');
}
function weixin_vip_rigister_window_open_enter(){
	var name=$("#vip_name").val();
	var phone=$("#vip_phone").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/rigister",
		data : {
			name:name,
			phone:phone
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				 location.reload() 
			}else{
				if(msg.stute==1){
					$('#weixin_vip_password_login_alert').addClass('is-visible');
					$("#weixin_vip_login_text").html(msg.info);
				}else{
					$("#weixin_viprigister_alert_body").html(msg.info);
					$('#weixin_vip_rigister_message_alert').addClass('is-visible');
				}
			}
			$('#weixin_vip_rigister_alert').removeClass('is-visible');
		}
	})
	
}
function weixin_vip_rigister_window_close(){
	$('#weixin_vip_rigister_alert').removeClass('is-visible');
}
function weixin_vip_rigister_message_window_close(){
	$('#weixin_vip_rigister_message_alert').removeClass('is-visible');
}
function weixin_vip_setPassWord_show(){
	$('#weixin_vip_password_alert').addClass('is-visible');
}
function weixin_vip_setPassWord_close(){
	$('#weixin_vip_password_alert').removeClass('is-visible');
}
function weixin_vip_setPassWord_enter(){
	var oldpassword=$("#oldpassword").val();
	var newpassword=$("#newpassword").val();
	var renewpassword=$("#renewpassword").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/passwordChange",
		data : {
			oldpassword:oldpassword,
			newpassword:newpassword,
			renewpassword:renewpassword
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_password_alert').addClass('is-visible');
				 location.reload() 
				 
			}else{
				$("#weixin_vip_password_text").html(msg.info);
				
			}
		}
	})
	
}
function weixin_vip_login_close(){
	$('#weixin_vip_password_login_alert').removeClass('is-visible');
}
function weixin_vip_login_enter(){
	var password=$("#password").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/public/login",
		data : {
			password:password
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_password_alert').addClass('is-visible');
				 location.reload() 
			}else{
				$("#weixin_vip_login_text").html(msg.info);
			}
		}
	})
	
}