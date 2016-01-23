var weixin_vip_queryVip_iphone_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_vip_queryVIP").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_queryVIP : tmpl
		});
		var tmpl = $("#weixin_vip_createViPLevel").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_createViPLevel : tmpl
		});
		var tmpl = $("#weixin_vip_score_convert").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_score_convert : tmpl
		});
		var tmpl = $("#weixin_vip_queryVIP_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_queryVIP_tr : tmpl
		});
		var tmpl = $("#weixin_vip_createViPLevel_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_createViPLevel_tr : tmpl
		});
		var tmpl = $("#weixin_vip_score_convert_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_score_convert_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
/**
 * 会员管理部分 开始
 * @param page
 */
function weixin_vip_queryVIP(page){
	weixin_vip_queryVip_iphone_page=page;
	if($("#weixin_vip_queryVip_iphone").val()==null||$("#weixin_vip_queryVip_iphone").val()==undefined){
		$("#weixin_vip_layout").html($.render.weixin_vip_queryVIP());
	}
	var name=$("#weixin_vip_queryVip_iphone").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getVip",
		data:{
			name:name,
			nowpage:weixin_vip_queryVip_iphone_page,
			countNum:30
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_tbody").html($.render.weixin_vip_queryVIP_tr(msg.data));
				var page_html="<li><a href=\"javascript:weixin_vip_queryVIP('"+(parseInt(weixin_vip_queryVip_iphone_page)-1)+"')\">«</a></li>";
				var startPage=weixin_vip_queryVip_iphone_page>15?weixin_vip_queryVip_iphone_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=weixin_vip_queryVip_iphone_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:weixin_vip_queryVIP('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:weixin_vip_queryVIP('"+(parseInt(weixin_vip_queryVip_iphone_page)+1)+"')\">»</a></li>"
				$("#weixin_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_queryVIP_tr_addscore(id){
	$("#weixin_vip_addScore_num").val("");
	$("#weixin_vip_queryVIP_tr_addscore").modal("show");
	$("#weixin_vip_queryVIP_tr_addscore_enter").attr("onclick","weixin_vip_queryVIP_tr_addscore_enter("+id+")");
}
function weixin_vip_queryVIP_tr_addprice(id){
	$("#weixin_vip_addScore_num").val("");
	$("#weixin_vip_queryVIP_tr_addprice").modal("show");
	$("#weixin_vip_queryVIP_tr_addprice_enter").attr("onclick","weixin_vip_queryVIP_tr_addprice_enter("+id+")");
}
function weixin_vip_queryVIP_tr_jianscore(id){
	$("#weixin_vip_addScore_num").val("");
	$("#weixin_vip_queryVIP_tr_addscore").modal("show");
	$("#weixin_vip_queryVIP_tr_addscore_enter").attr("onclick","weixin_vip_queryVIP_tr_jianscore_enter("+id+")");
}
function weixin_vip_queryVIP_tr_jianprice(id){
	$("#weixin_vip_addScore_num").val("");
	$("#weixin_vip_queryVIP_tr_addprice").modal("show");
	$("#weixin_vip_queryVIP_tr_addprice_enter").attr("onclick","weixin_vip_queryVIP_tr_jianprice_enter("+id+")");
}
function weixin_vip_queryVIP_tr_favourable(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getHuiyuanxinxi",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_vip_queryVIP_tr_favourable").modal("show");
				$("#huiyuanjibie").html(msg.data.name);
				$("#xiangshouzhidu").html(msg.data.marks);
			}else{
					alertError(msg.info,"alert_Vip");
			}
		}
    })
	
}
function weixin_vip_queryVIP_tr_addscore_enter(id){
	var sroce=$("#weixin_vip_addScore_num").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/addscoreToUser",
		data:{
			id:id,
			sroce:sroce
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_vip_queryVIP_tr_addscore").modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_queryVIP(weixin_vip_queryVip_iphone_page);
			}else{
				$("#weixin_vip_queryVIP_tr_addscore").modal("hide");
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_queryVIP_tr_addprice_enter(id){
	var money=$("#weixin_vip_addprice_num").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/addpriceToUser",
		data:{
			id:id,
			money:money
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_vip_queryVIP_tr_addprice").modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_queryVIP(weixin_vip_queryVip_iphone_page);
			}else{
				$("#weixin_vip_queryVIP_tr_addprice").modal("hide");
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_queryVIP_tr_jianscore_enter(id){
	var sroce=$("#weixin_vip_addScore_num").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/jianscoreToUser",
		data:{
			id:id,
			sroce:sroce
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_vip_queryVIP_tr_addscore").modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_queryVIP(weixin_vip_queryVip_iphone_page);
			}else{
				$("#weixin_vip_queryVIP_tr_addscore").modal("hide");
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_queryVIP_tr_jianprice_enter(id){
	var money=$("#weixin_vip_addprice_num").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/jianpriceToUser",
		data:{
			id:id,
			money:money
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_vip_queryVIP_tr_addprice").modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_queryVIP(weixin_vip_queryVip_iphone_page);
			}else{
				$("#weixin_vip_queryVIP_tr_addprice").modal("hide");
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
/**
 * 会员管理部分 结束
 * @param page
 */
/**
 * 积分兑换部分 开始
 * @param page
 */
function weixin_vip_score_convert(){
	$("#weixin_vip_layout").html($.render.weixin_vip_score_convert());
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getScoreDuiHuanList",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_tbody").html($.render.weixin_vip_score_convert_tr(msg.data));
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_score_convert_tr_add(){
	$("#weixin_vip_score_convert_tr_add").modal("show");
	$("#score_name").val("");
	$("#score_image").val("");
	$("#score_sroce").val("");
	$("#score_marks").val("");
	$("#score_num").val("");
}
function weixin_vip_score_convert_tr_add_enter(){
	var name=$("#score_name").val();
	var image=$("#score_image").val();
	var sroce=$("#score_sroce").val();
	var marks=$("#score_marks").val();
	var num=$("#score_num").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/addScoreDuiHuanList",
		data:{
			name:name,
			sroce:sroce,
			marks:marks,
			image:image,
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_score_convert_tr_add').modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_score_convert();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"alert_Vip");
					$('#weixin_vip_score_convert_tr_add').modal("hide");
				}else{
					inputAjaxTest(list,"score_");
				}
			}
		}
	})
}
function weixin_vip_score_convert_tr_update(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getScoreDuiHuan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
					$("#weixin_vip_score_convert_tr_update").modal("show");
					$("#weixin_vip_score_convert_tr_update_enter").attr("onclick","weixin_vip_score_convert_tr_update_enter("+id+")")
					$("#update_score_name").val(msg.data.name);
					$("#update_score_image").val(msg.data.image);
					$("#update_score_sroce").val(msg.data.score);
					$("#update_score_marks").val(msg.data.content);
					$("#update_score_num").val(msg.data.num);
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
	})
}
function weixin_vip_score_convert_tr_delete(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/deleteScoreDuiHuan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				weixin_vip_score_convert();
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
	})
}
function weixin_vip_score_convert_tr_update_enter(id){
	var name=$("#update_score_name").val();
	var image=$("#update_score_image").val();
	var sroce=$("#update_score_sroce").val();
	var marks=$("#update_score_marks").val();
	var num=$("#update_score_num").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/updateScoreDuiHuan",
		data:{
			name:name,
			sroce:sroce,
			marks:marks,
			image:image,
			num:num,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_score_convert_tr_update').modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_score_convert();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					$('#weixin_vip_score_convert_tr_update').modal("hide");
					alertError(msg.info,"alert_Vip");
				}else{
					inputAjaxTest(list,"update_score_");
				}
			}
		}
	})
}
/**
 * 积分兑换部分 结束
 * @param page
 */
/**
 * 等级管理部分 开始
 * @param page
 */
function weixin_vip_createViPLevel(){
	$("#weixin_vip_layout").html($.render.weixin_vip_createViPLevel());
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getVipList",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_tbody").html($.render.weixin_vip_createViPLevel_tr(msg.data));
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
    })
}
function weixin_vip_create_viplevel(){
	$("#weixin_vip_createViPLevel_tr_add").modal("show");
	$("#vip_name").val("");
	$("#vip_sroce").val("");
	$("#vip_marks").val("");
}
function create_VIPLevel_enter(){
	var name=$("#vip_name").val();
	var sroce=$("#vip_sroce").val();
	var marks=$("#vip_marks").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/addVipList",
		data:{
			name:name,
			sroce:sroce,
			marks:marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_createViPLevel_tr_add').modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_createViPLevel();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"alert_Vip");
					$('#weixin_vip_createViPLevel_tr_add').modal("hide");
				}else{
					inputAjaxTest(list,"vip_");
				}
			}
		}
	})
}
function weixin_vip_createViPLevel_tr_update(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/getVipInfo",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
					$("#weixin_vip_createViPLevel_tr_update").modal("show");
					$("#weixin_vip_createViPLevel_tr_update_enter").attr("onclick","weixin_vip_createViPLevel_tr_update_enter("+id+")")
					$("#update_vip_name").val(msg.data.name);
					$("#update_vip_sroce").val(msg.data.score);
					$("#update_vip_marks").val(msg.data.marks);
			}else{
				alertError(msg.info,"alert_Vip");
			}
		}
	})
}
function weixin_vip_createViPLevel_tr_update_enter(id){
	var name=$("#update_vip_name").val();
	var sroce=$("#update_vip_sroce").val();
	var marks=$("#update_vip_marks").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/vip/updateVipList",
		data:{
			id:id,
			name:name,
			sroce:sroce,
			marks:marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$('#weixin_vip_createViPLevel_tr_update').modal("hide");
				alertSuccess(msg.info,"alert_Vip");
				weixin_vip_createViPLevel();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"alert_Vip");
				}else{
					inputAjaxTest(list,"update_vip_");
				}
			}
		}
	})
}
/**
 *  等级管理部分 结束
 * @param page
 */