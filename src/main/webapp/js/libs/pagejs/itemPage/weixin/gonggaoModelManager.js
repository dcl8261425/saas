$(document).ready(function() {
	$(document).ready(function(){
		initTemp();
		query_gonggao(1);
	})
	function initTemp() {
		var tmpl = $("#gonggao_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			gonggao_item : tmpl
		});
	}
});
function query_gonggao(nowpage) {
	if (nowpage==1||nowpage=="1") {
		$("#conpany_gonggao").html("")
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gonggaoController/getGonggao",
		data : {
			nowpage : nowpage
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#conpany_gonggao").append($.render.gonggao_item(msg.data))
			} else {

			}
		}
	})
}
function add_gonggao_show(){
	$("#add_gonggao").modal("show");
}
function delete_gonggao(id) {
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gonggaoController/getGonggao",
		data : {
			type : 'del',
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				alertSuccess(msg.info,"game_alert");
				query_gonggao(1);
			} else {
				alertError(msg.info,"game_alert");
			}
		}
	})
}
function add_gonggao() {
	var message=$("#gonggao_content").val();
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gonggaoController/getGonggao",
		data : {
			type : 'add',
			message:message
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				alertSuccess(msg.info,"game_alert");
				query_gonggao(1);
				$("#add_gonggao").modal("hide");
			} else {
				alertError(msg.info,"game_alert");
			}
		}
	})
}