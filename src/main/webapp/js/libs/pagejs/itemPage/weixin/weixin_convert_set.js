$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_convert_game").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_convert_game : tmpl
		});
		var tmpl = $("#weixin_convert_score").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_convert_score : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function weixin_convert_score(){
	$("#right_layout").html($.render.weixin_convert_score());
}
function weixin_convert_game(){
	$("#right_layout").html($.render.weixin_convert_game());
}
function weixin_convert_score_but(){
	var num=$("#score_num").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/CodeConvert/jifenTogoodManager",
		data:{
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#num_name").html("名字："+msg.ScoreDuihuan.name);
				$("#num_content").html("内容："+msg.ScoreDuihuan.content);
				$("#num_num").html("数量："+msg.score.num);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_convert_alert");
				}else{
					inputAjaxTest(list,"score_");
				}
			}
		}
	})
}
function weixin_convert_game_but(){
	var num=$("#huodong_num").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/CodeConvert/huodongTogoodManager",
		data:{
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#num_name").html("名字："+msg.Awards.content);
				$("#num_content").html("内容："+msg.Awards.marks);
				$("#num_startDate").html("开始时间:"+toDate(msg.NumLibs.startDate));
				$("#num_endDate").html("结束时间:"+toDate(msg.NumLibs.endDate));
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_convert_alert");
				}else{
					inputAjaxTest(list,"huodong_");
				}
			}
		}
	})
}