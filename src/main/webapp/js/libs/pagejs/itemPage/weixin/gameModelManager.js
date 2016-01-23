$(function() {
	$(document).ready(function() {
		initTemp();
		getConpanyGame(1);
	})
	function initTemp() {
		var tmpl = $("#conpany_game_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			conpany_game_item : tmpl
		});
		var tmpl = $("#game_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			game_item : tmpl
		});
		
	}
});
function getConpanyGame(nowpage) {
	if (nowpage==1||nowpage=="1") {
		$("#conpany_game").html("")
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gamesController/getConpanyGame",
		data : {
			nowpage : nowpage
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#conpany_game").append($.render.conpany_game_item(msg.data))
			} else {

			}
		}
	})
}
function getGame(nowpage) {
	if (nowpage==1||nowpage=="1") {
		$("#all_game").html("")
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gamesController/getGame",
		data : {
			nowpage : nowpage
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				$("#all_game").append($.render.game_item(msg.data))
				
			} else {

			}
		}
	})
}
function games_open_game(id){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gamesController/getConpanyGame",
		data : {
			id : id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				alertSuccess(msg.info,"game_alert");
				var str=""
				if(msg.now){
					str="关闭"
				}else{
					str="开启"			
				}
				$("#games_open_game"+id).html("<i class='fa fa-arrow-circle-o-right'></i> "+str);
			} else {
				alertError(msg.info,"game_alert");
			}
		}
	})
}
function games_add_game(id,obj){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "gamesController/getGame",
		data : {
			id : id
		},
		success : function(msg) {
			layer.close(i);
			if (msg.success == null || msg.success == undefined || msg.success == true) {
				alertSuccess(msg.info,"game_alert");
				$("#games_add_game"+id).html("<i class='fa fa-arrow-circle-o-right'></i> 添加完成");
			} else {
				alertError(msg.info,"game_alert");
			}
		}
	})
}