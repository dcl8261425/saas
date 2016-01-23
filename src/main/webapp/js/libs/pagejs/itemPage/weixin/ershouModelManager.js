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
		var tmpl = $("#ershou_type_list_content_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			ershou_type_list_content_item : tmpl
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
		url : ctx + "ErShouModelController/getErShou",
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
		url : ctx + "ErShouModelController/getErShou",
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
		url : ctx + "ErShouModelController/getErShou",
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
function query_ErShou_type(){
	$("#query_ErShou_type").modal("show");
	query_ershou_Type();
}
function query_ershou_Type() {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "ErShouModelController/getErShou",
		data : {
			type : 'queryType'
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#ershou_type_list_content").html($.render.ershou_type_list_content_item(msg.data))
			} else {
				
			}
		}
	})
}
function add_ErShou_window(){
	$("#query_ErShou_type_add").modal("show");
	$("#addErshouTypeWindow_button").attr("onclick","add_ErShou_window_enter()");
}
function add_ErShou_window_enter(){
	var name=$("#ershou_type_name").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "ErShouModelController/getErShou",
		data : {
			type : "addtype",
			name:name
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#query_ErShou_type_add").modal("hide");
				$("#ershou_type_name").val("");
				query_ershou_Type();
			} else {
				
			}
		}
	})
}
function update_ErShou_window(id){
	$("#query_ErShou_type_add").modal("show");
	$("#addErshouTypeWindow_button").attr("onclick","update_ErShou_window_enter("+id+")")
}
function update_ErShou_window_enter(id){
	var name=$("#ershou_type_name").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "ErShouModelController/getErShou",
		data : {
			type : "updateType",
			id:id,
			name:name
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#query_ErShou_type_add").modal("hide");
				$("#ershou_type_name").val("");
				query_ershou_Type();
			} else {
				
			}
		}
	})
}
function delete_ErShou_enter(id){
	
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "ErShouModelController/getErShou",
		data : {
			type : "deltype",
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				query_ershou_Type();
			} else {
				
			}
		}
	})
}