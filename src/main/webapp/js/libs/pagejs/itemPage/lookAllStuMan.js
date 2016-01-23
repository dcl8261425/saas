$(function () {
	$(document).ready(function(){
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
	}
})
function lookAllStuMan_meeting(){

	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/meeting",
		data:{
			type:'test'
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				if(msg.isup){
					$("#lookAllStu_meeting_window").modal("show");
				}else{
					$("#lookAllStu_meeting_down_window").modal("show");
				}
			}else{
				alertError(msg.info,"content");
			}
		}
	})
}
function lookAllStu_meeting_out(){
	$("#lookAllStu_meeting_out_div").show();
	

}
function lookAllStu_meeting_but(){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/meeting",
		data:{
			type:'up'
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				alertSuccess(msg.info,"lookAllStu_meeting_alert_div");
			}else{
				alertError(msg.info,"lookAllStu_meeting_alert_div");
			}
		}
	})
}
function lookAllStu_meeting_out_but(){
	var marks=$("#lookAllStu_meeting_out_input").val();
	if(marks.trim()==""){
		alertError("请填写外出备注","lookAllStu_meeting_alert_div");
		return;
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/meeting",
		data:{
			marks:marks,
			type:'up'
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				alertSuccess(msg.info,"lookAllStu_meeting_alert_div");
			}else{
				alertError(msg.info,"lookAllStu_meeting_alert_div");
			}
		}
	})
}
function lookAllStu_meeting_down_but(){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/meeting",
		data:{
			type:'down'
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				alertSuccess(msg.info,"lookAllStu_meeting_down_alert_div");
			}else{
				alertError(msg.info,"lookAllStu_meeting_down_alert_div");
			}
		}
	})
}