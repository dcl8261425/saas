var weixin_jifenduihuan_page=1;
var weixin_jifenduihuan_id;
$(function () {
	$(document).ready(function(){
		initTemp();
		jifenduihuan_query_data();
	})
	function initTemp(){
		var tmpl = $("#jifenduihuan_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"jifenduihuan_tr" : tmpl
		});
		var tmpl = $("#jifenduihuan_duihuan_num_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			"jifenduihuan_duihuan_num_tr" : tmpl
		});
	}
})
//$.render.GoodSource_box_tbody_tr(msg.data)
function jifenduihuan_query_data(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/jifenduihuan_data",
		data:{
			nowpage:weixin_jifenduihuan_page,
			countNum:10
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.data.length>0){
					$("#jifenduihuan_body").html($.render.jifenduihuan_tr(msg.data));
				}else{
					
				}
			}else{
				
			}
		}
	})
}
function jifenduihuan_page_next(){
	weixin_jifenduihuan_page=weixin_jifenduihuan_page+1;
	jifenduihuan_query_data();
}
function jifenduihuan_page_up(){
	weixin_jifenduihuan_page=weixin_jifenduihuan_page-1;
	jifenduihuan_query_data();
}
function weixin_jifenduihuan_duihuan_window_open(id){
	weixin_jifenduihuan_id=id;
	$("#jifenduihuan_duihuan_alert_body").html($.render.jifenduihuan_duihuan_num_tr());
	$("#weixin_jifenduihuan_duihuan_window_enter").attr("href","javascript:weixin_jifenduihuan_duihuan_window_enter()");
	$('.cd-popup').addClass('is-visible');
}
function weixin_jifenduihuan_duihuan_window_close(){
			$('.cd-popup').removeClass('is-visible');
}
function weixin_jifenduihuan_duihuan_window_enter(){
	$('.cd-popup').removeClass('is-visible');
	var num=$("#jifenduihuan_duihuan_num").val();
	//alert(weixin_jifenduihuan_id)
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/UserToScoreduihuan",
		data:{
			id:weixin_jifenduihuan_id,
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#jifenduihuan_duihuan_alert_body").html(msg.info);
				$("#weixin_jifenduihuan_duihuan_window_enter").attr("href","javascript:weixin_jifenduihuan_duihuan_window_close()");
				$('.cd-popup').addClass('is-visible');
			}else{
				$("#jifenduihuan_duihuan_alert_body").html(msg.info);
				$("#weixin_jifenduihuan_duihuan_window_enter").attr("href","javascript:weixin_jifenduihuan_duihuan_window_close()");
				$('.cd-popup').addClass('is-visible');
			}
		}
	})
}
