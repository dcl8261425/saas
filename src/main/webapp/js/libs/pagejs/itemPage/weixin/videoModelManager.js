$(document).ready(function() {
	$(document).ready(function(){
		initTemp();
		query_image(1);
	})
	function initTemp() {
		var tmpl = $("#image_item_model").html().replace("<!--", "").replace("-->", "");
		$.templates({
			image_item_model : tmpl
		});
	}
});
function query_image(nowpage) {
	if (nowpage==1||nowpage=="1") {
		$("#conpany_image").html("")
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VideoModelController/getVideo",
		data : {
			nowpage : nowpage
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#conpany_image").append($.render.image_item_model(msg.data))
			} else {
				
			}
		}
	})
}
function image_model_pass(id){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VideoModelController/getVideo",
		data : {
			type : "pass",
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				query_image(1);
			} else {
				
			}
		}
	})
}
function image_model_nopass(id){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VideoModelController/getVideo",
		data : {
			type : "nopass",
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				query_image(1);
			} else {
				
			}
		}
	})
}