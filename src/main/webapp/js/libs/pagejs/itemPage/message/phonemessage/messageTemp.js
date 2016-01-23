$(function() {
	$(document).ready(function() {

		initTemp();

	})
	function initTemp() {

	}
})
function phonemessageTemp_addScore() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : 2,
			methed : "get",
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				var tmpl = $("#phonemessageTemp_addScore").html().replace("<!--", "").replace("-->", "");
				$.templates({
					phonemessageTemp_addScore : tmpl
				});
				$("#phone_message_content").html($.render.phonemessageTemp_addScore());
				$("#phonemessageTemp_addScore_input_value").html(msg.obj.addscoreToVipContent);
			} else {

				alertError(msg.info, "message_set_alert");

			}
		}
	})
}
function phonemessageTemp_RedouceScore() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : 2,
			methed : "get",
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				var tmpl = $("#phonemessageTemp_RedouceScore").html().replace("<!--", "").replace("-->", "");
				$.templates({
					phonemessageTemp_RedouceScore : tmpl
				});
				$("#phone_message_content").html($.render.phonemessageTemp_RedouceScore());
				$("#phonemessageTemp_RedouceScore_input_value").html(msg.obj.reducesscoreToVipContent);
			} else {

				alertError(msg.info, "message_set_alert");

			}
		}
	})
}
function phonemessageTemp_RedoucePrice() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : 2,
			methed : "get",
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				var tmpl = $("#phonemessageTemp_RedoucePrice").html().replace("<!--", "").replace("-->", "");
				$.templates({
					phonemessageTemp_RedoucePrice : tmpl
				});
				$("#phone_message_content").html($.render.phonemessageTemp_RedoucePrice());
				$("#phonemessageTemp_RedoucePrice_input_value").html(msg.obj.reducesPriceToVipContent);
			} else {
				alertError(msg.info, "message_set_alert");

			}
		}
	})
}
function phonemessageTemp_addPrice() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : 2,
			methed : "get",
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				var tmpl = $("#phonemessageTemp_addPrice").html().replace("<!--", "").replace("-->", "");
				$.templates({
					phonemessageTemp_addPrice : tmpl
				});
				$("#phone_message_content").html($.render.phonemessageTemp_addPrice());
				$("#phonemessageTemp_addPrice_input_value").html(msg.obj.addPriceToVipContent);
			} else {
				alertError(msg.info, "message_set_alert");

			}
		}
	})
}
function phonemessageTemp_orderToUser() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : 2,
			methed : "get",
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				var tmpl = $("#phonemessageTemp_orderToUser").html().replace("<!--", "").replace("-->", "");
				$.templates({
					phonemessageTemp_orderToUser : tmpl
				});
				$("#phone_message_content").html($.render.phonemessageTemp_orderToUser());
				$("#phonemessageTemp_orderToUser_input_value").html(msg.obj.orderToUserContent);
				$("#phonemessageTemp_orderToUser_input_phone").val(msg.obj.orderToUserPhone);
			} else {
				alertError(msg.info, "message_set_alert");

			}
		}
	})
}
function phonemessageTemp_submit(type,valueid,phoneid,alertid){
	var phone=1;
	if(phoneid!=null){
		phone=$("#"+phoneid).val();
	}
	var value=$("#"+valueid).val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/setTemp",
		data : {
			type : type,
			methed : "add",
			phone:phone,
			value:value
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				alertSuccess(msg.info,"message_set_alert");
			} else {
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"message_set_alert");
				}else{
					inputAjaxTest(list,alertid);
				}

			}
		}
	})
}