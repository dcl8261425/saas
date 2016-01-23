$(function() {
	$(document).ready(function() {

		initTemp();

	})
	function initTemp() {

	}
})
function vipMessage_sendNum(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "weixin/vip/getScoreDuiHuanList",
		data : {
		},
		success : function(msg) {
			layer.close(i);
				var tmpl = $("#vipMessage_sendNum_temp").html().replace("<!--", "").replace("-->", "");
				$.templates({
					vipMessage_sendNum_temp : tmpl
				});
				$("#phone_message_content").html($.render.vipMessage_sendNum_temp());
				var tmpl = $("#vipMessage_sendMessages_temp_option").html().replace("<!--", "").replace("-->", "");
				$.templates({
					vipMessage_sendMessages_temp_option : tmpl
				});
				$("#phonemessageTemp_addScore_input_value_select").html($.render.vipMessage_sendMessages_temp_option(msg.data));
		}
	})
}
function vipMessage_sendMessages(){
	var tmpl = $("#vipMessage_sendMessages_temp").html().replace("<!--", "").replace("-->", "");
	$.templates({
		vipMessage_sendMessages_temp : tmpl
	});
	$("#phone_message_content").html($.render.vipMessage_sendMessages_temp());
}
function vipMessage_sendNum_enter(){
	var select=$("#phonemessageTemp_addScore_input_value_select").val();
	var value=$("#phonemessageTemp_addScore_input_value").val();
	var num=$("#phonemessageTemp_addScore_input_num").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/sendVipMessage",
		data : {
			type : 1,
			value:value,
			select:select,
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				alertSuccess(msg.info, "message_set_alert");
			} else {
				alertError(msg.info, "message_set_alert");
			}
		}
	})
}
function vipMessage_sendMessages_enter(){
	
	var value=$("#phonemessageTemp_addScore_input_value").val();
	var num=$("#phonemessageTemp_addScore_input_num").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/sendVipMessage",
		data : {
			type : 2,
			value:value,
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				alertSuccess(msg.info, "message_set_alert");
			} else {
				alertError(msg.info, "message_set_alert");
			}
		}
	})
}