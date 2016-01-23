var weixin_game_selectGoods_id;
var weixin_game_selectGoods_text;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_game_goods").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_goods : tmpl
		});
		var tmpl = $("#weixin_game_dazhuanpan").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_dazhuanpan : tmpl
		});
		var tmpl = $("#weixin_game_guaguaka").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_guaguaka : tmpl
		});
		var tmpl = $("#weixin_game_guaguaka_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_guaguaka_tr : tmpl
		});
		var tmpl = $("#weixin_game_goods_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_goods_tr : tmpl
		});
		var tmpl = $("#weixin_game_selectGoods_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_game_selectGoods_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function weixin_game_goods(){
	$("#weixin_game_set_content").html($.render.weixin_game_goods());
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"gameController/getAwards",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_tbody").html($.render.weixin_game_goods_tr(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_game_dazhuanpan(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"gameController/getDaZhuanpanInfo",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_game_set_content").html($.render.weixin_game_dazhuanpan());
				$('#weixin_dazhuanpan_content .input-group.date').datepicker({
					 language: 'zh-CN',
					 format: 'yyyy-mm-dd'
				});
				$("#weixin_dazhuanpan_jilv").val(msg.jilv);
				$("#weixin_dazhuanpan_cushu").val(msg.num);
				$("#weixin_dazhuanpan_score").val(msg.scoreNum);
				for(var iii=0;iii<msg.data.length;iii++){
					var ii=iii+1;
					$("#d"+ii+"_text_text").val(msg.data[iii].content);
					$("#d"+ii+"_id_text").val(msg.data[iii].awardsid);
					$("#startDate"+ii).val(JavaSTojsDate(msg.data[iii].startDate));
					$("#endDate"+ii).val(JavaSTojsDate(msg.data[iii].endDate));
					$("#d"+ii+"_num").val(msg.data[iii].num);
				}
				if(msg.use){
					$('#open_closs_dazhuanpan').bootstrapSwitch('state',true);
				}else{
					$('#open_closs_dazhuanpan').bootstrapSwitch('state', false);
				}
				if(msg.score){
					$('#open_score_dazhuanpan').bootstrapSwitch('state',true);
					$("#weixin_dazhuanpan_score").removeAttr("disabled");
				}else{
					$('#open_score_dazhuanpan').bootstrapSwitch('state', false);
					$("#weixin_dazhuanpan_score").attr("disabled","disabled");
				}
				$('#open_score_dazhuanpan').on("switchChange",function(e,data){
					var value=data.value;
					if(value){
						$("#weixin_dazhuanpan_score").removeAttr("disabled");
					}else{
						$("#weixin_dazhuanpan_score").attr("disabled","disabled");
					}
					var ii=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"gameController/updateGameDaZhuanPan",
						data:{
							value:value,
							type:2
						},
						success : function(msg) {
							layer.close(ii);
						}
					})
				})
				$('#open_closs_dazhuanpan').on("switchChange",function(e,data){
					var value=data.value;
					var ii=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"gameController/updateGameDaZhuanPan",
						data:{
							value:value,
							type:1
						},
						success : function(msg) {
							layer.close(ii);
						}
					})
				})
				
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
	
}
function weixin_game_guaguaka(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"gameController/getguaguaka",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_game_set_content").html($.render.weixin_game_guaguaka());
				if(msg.use){
					$('#open_closs_dazhuanpan').bootstrapSwitch('state',true);
				}else{
					$('#open_closs_dazhuanpan').bootstrapSwitch('state', false);
				}
				$("#weixin_guaguaka_jilv").val(msg.jilv);
				$("#weixin_guaguaka_cishu").val(msg.num);
				$("#weixin_dazhuanpan_score").val(msg.scoreNum);
				$('#open_closs_dazhuanpan').on("switchChange",function(e,data){
					var value=data.value;
					var ii=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"gameController/updateGameguaguaka",
						data:{
							value:value,
							type:1
						},
						success : function(msg) {
							layer.close(ii);
						}
					})
				})
				if(msg.score){
					$('#open_score_dazhuanpan').bootstrapSwitch('state',true);
					$("#weixin_dazhuanpan_score").removeAttr("disabled");
				}else{
					$('#open_score_dazhuanpan').bootstrapSwitch('state', false);
					$("#weixin_dazhuanpan_score").attr("disabled","disabled");
				}
				$('#open_score_dazhuanpan').on("switchChange",function(e,data){
					var value=data.value;
					if(value){
						$("#weixin_dazhuanpan_score").removeAttr("disabled");
					}else{
						$("#weixin_dazhuanpan_score").attr("disabled","disabled");
					}
					var ii=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"gameController/updateGameguaguaka",
						data:{
							value:value,
							type:2
						},
						success : function(msg) {
							layer.close(ii);
						}
					})
				})
				$("#weixin_tbody").html($.render.weixin_game_guaguaka_tr(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
	

}
function weixin_game_addGoods(){
	$("#weixin_game_addGoods").modal("show");
}
function weixin_game_addGoods_enter(){
	var content=$("#score_content").val();
	var marks=$("#score_marks").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/addAwards",
		data:{
			content:content,
			marks:marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_game_addGoods").modal("hide");
				weixin_game_goods();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_addGoods_alert");
				}else{
					inputAjaxTest(list,"score_");
				}
			}
		}
	})
}
function weixin_game_goods_delete(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/deleteAwards",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				weixin_game_goods();
			}else{
					alertError(msg.info,"weixin_game_addGoods_alert");
			}
		}
	})
}
function weixin_game_dazhuanpan_gailv(){
	var value=$("#weixin_dazhuanpan_jilv").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setDazhuanpanJilv",
		data:{
			value:value
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_guaguaka_score(){
	var value=$("#weixin_dazhuanpan_score").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setguaguakaJilv",
		data:{
			value:value,
			type:"score"
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_dazhuanpan_score(){
	var value=$("#weixin_dazhuanpan_score").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setDazhuanpanJilv",
		data:{
			value:value,
			type:"score"
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_dazhuanpan_num(){
	var value=$("#weixin_dazhuanpan_cushu").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setDazhuanpanNum",
		data:{
			value:value
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_guaguaka_gailv(){
	var value=$("#weixin_guaguaka_jilv").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setguaguakaJilv",
		data:{
			value:value
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_guaguaka_num(){
	var value=$("#weixin_guaguaka_cishu").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/setguaguakaNum",
		data:{
			value:value
		},
		success : function(msg) {
			layer.close(ii);
		
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					ui.alert("错误,请填写",2000,false);
				}
			}
		}
	})
}
function weixin_game_dahuznapan_set1(){
	var text=$("#d1_text_text").val();
	var id=$("#d1_id_text").val();
	var startdate=$("#startDate1").val();
	var enddate=$("#endDate1").val();
	if(startdate=="奖品兑换时间-开始 "){
		startdate="";
	}
	if(enddate=="奖品兑换时间-结束 "){
		enddate="";
	}
	var num=$("#d1_num").val();
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/addDazhuanpan1",
		data:{
			text:text,
			id:id,
			startdate:startdate,
			enddate:enddate,
			num:num
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"d1_");
				}
			}
		}
	})
}
function weixin_game_dahuznapan_set2(){
	var text=$("#d2_text_text").val();
	var id=$("#d2_id_text").val();
	var startdate=$("#startDate2").val();
	var enddate=$("#endDate2").val();
	var num=$("#d2_num").val();
	if(startdate=="奖品兑换时间-开始 "){
		startdate="";
	}
	if(enddate=="奖品兑换时间-结束 "){
		enddate="";
	}
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/addDazhuanpan2",
		data:{
			text:text,
			id:id,
			startdate:startdate,
			enddate:enddate,
			num:num
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"d2_");
				}
			}
		}
	})
}
function weixin_game_dahuznapan_set3(){
	var text=$("#d3_text_text").val();
	var id=$("#d3_id_text").val();
	var startdate=$("#startDate3").val();
	var enddate=$("#endDate3").val();
	var num=$("#d3_num").val();
	if(startdate=="奖品兑换时间-开始 "){
		startdate="";
	}
	if(enddate=="奖品兑换时间-结束 "){
		enddate="";
	}
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/addDazhuanpan3",
		data:{
			text:text,
			id:id,
			startdate:startdate,
			enddate:enddate,
			num:num
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"d3_");
				}
			}
		}
	})
}
function weixin_game_selectGoods(id,text){

	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"gameController/getAwards",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_game_selectGoods_id=id;
				weixin_game_selectGoods_text=text;
				$("#weixin_game_selectGoods").modal("show");
				$("#weixin_selectGoods_tbody").html($.render.weixin_game_selectGoods_tr(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_game_selectGoods_select(id,text){
	$("#"+weixin_game_selectGoods_id).val(id);
	$("#"+weixin_game_selectGoods_text).val(text);
	$("#weixin_game_selectGoods").modal("hide");
}
function weixin_game_guaguaka_delete(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/deleteGuaguaka",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				weixin_game_guaguaka()
			}else{
					alertError(msg.info,"weixin_game_alert");
			}
		}
	})
}
function weixin_game_guaguaka_addGoods(){

	$("#weixin_game_guaguaka_addGoods").modal("show");
	$('#guaguaka_addgoods_content .input-group.date').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
}
function weixin_game_guaguaka_addGoods_enter(){
	var text=$("#d3_text_text").val();
	var id=$("#d3_id_text").val();
	var startdate=$("#startDate3").val();
	var enddate=$("#endDate3").val();
	var num=$("#d3_num").val();
	if(startdate=="奖品兑换时间-开始 "){
		startdate="";
	}
	if(enddate=="奖品兑换时间-结束 "){
		enddate="";
	}
	var ii=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"gameController/addAwardsToguaguaka",
		data:{
			text:text,
			id:id,
			startdate:startdate,
			enddate:enddate,
			num:num
		},
		success : function(msg) {
			layer.close(ii);
			if(msg.success){
				ui.alert(msg.info,2000,false);
				weixin_game_guaguaka();
				$("#weixin_game_guaguaka_addGoods").modal("hide");
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"d3_");
				}
			}
		}
	})
}