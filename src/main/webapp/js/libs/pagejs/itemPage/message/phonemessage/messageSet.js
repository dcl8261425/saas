$(function() {
	$(document).ready(function() {

		initTemp();

	})
	function initTemp() {

	}
})
function phoneMessage() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "messageinfo/getMessageSet",
		data : {},
		success : function(msg) {
			layer.close(i);
			if (msg.success) {
				$('#message_addScore_open').bootstrapSwitch('state', msg.obj.addscoreToVip);
				$('#message_addPrice_open').bootstrapSwitch('state', msg.obj.addPriceToVip);
				$('#message_reduceScore_open').bootstrapSwitch('state', msg.obj.reducescoreToVip);
				$('#message_reducePrice_open').bootstrapSwitch('state', msg.obj.reducePriceToVip);
				$('#message_weixininfo_open').bootstrapSwitch('state', msg.obj.winxinInfoToUser);
				$('#message_order_open').bootstrapSwitch('state', msg.obj.orderToUser);
				$('#message_yuding_open').bootstrapSwitch('state', msg.obj.yudingToUser);
				$("#message_num").html("当前剩余短信数量 "+msg.obj.num+"条");
				$("#message_addScore_open_num").html("已发送"+msg.obj.addscoreToVipnum+"条");
				$("#message_addPrice_open_num").html("已发送"+msg.obj.addPriceToVipnum+"条");
				$("#message_reduceScore_open_num").html("已发送"+msg.obj.reducesscoreToVipnum+"条");
				$("#message_reducePrice_open_num").html("已发送"+msg.obj.reducesPriceToVipnum+"条");
				$("#message_weixininfo_open_num").html("已发送"+msg.obj.winxinInfoToUsernum+"条");
				$("#message_order_open_num").html("已发送"+msg.obj.orderToUsernum+"条");
				$("#message_yuding_open_num").html("已发送"+msg.obj.yudingToUsernum+"条");
				$("#message_qianming").val(msg.obj.qianMing);
				$('#message_qianming_but').on("click", function() {
					var value =$('#message_qianming').val();
					var type = 99;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_addScore_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 1;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_addPrice_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 2;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_reduceScore_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 3;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_reducePrice_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 4;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_weixininfo_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 5;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_order_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 6;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
				$('#message_yuding_open').on("switchChange", function(e, data) {
					var value = data.value;
					var type = 7;
					var ii = $.layer({
						type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx + "messageinfo/setboolean",
						data : {
							value : value,
							type : type
						},
						success : function(msg) {
							layer.close(ii);
							if (!msg.success) {
								alertError(msg.info, "message_set_alert");
							}
						}
					})
				})
			}else{
				alertError(msg.info, "message_set_alert");
			}
		}
	})
}