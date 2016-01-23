$(function () {
	$(document).ready(function(){
		
	})
})
function changePassword(){
	var old=$("#old_pasword").val();
	var newp=$("#new_password").val();
	var renewp=$("#new_repassword").val();
	if(newp==renewp){
		var i=$.layer({
		    type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+"ajaxchangePassword",
			data:{
				old:old,
				newp:newp,
				renewp:renewp
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
						alertSuccess(msg.info,"passwordChange_alert");
				}else{
						alertError(msg.info,"passwordChange_alert");
				}
			}
		})
	}else{
		alertError("两次输入密码不一致","passwordChange_alert");
	}
}