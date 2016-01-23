var weixin_map_data;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"weixin/function/getMaps",
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#weixin_map_main_content").after($.render.weixin_map_add_new_tr(msg.data));
					weixin_map_data=msg.data;
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
	})
	function initTemp(){
		var tmpl = $("#weixin_map_add_new").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_map_add_new : tmpl
		});
		var tmpl = $("#weixin_map_add_new_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_map_add_new_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function weixin_map_set_addNewMap(){
	$("#weixin_map_main_content").after($.render.weixin_map_add_new());
}
function weixin_map_delete_set(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/deleteMap",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_Model_set_map").click();
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function weixin_map_delete_set_no_id(obj){
	$(obj).parent().parent().remove();
}
function weixin_map_addenter(obj){
	var x=$($(obj).parent().parent().find("div").get(3)).html();
	var y=$($(obj).parent().parent().find("div").get(4)).html();
	if(x==""||y==""){
		ui.alert("请在地图上选择您店铺的位置，谢谢",2000,false);
		return ;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/saveMap",
		data:{
			x:x,
			y:y
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_Model_set_map").click();
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function weixin_map_load(){
	for(var ii=0;ii<weixin_map_data.length;ii++){
		 $(window.parent.document).contents().find("#myFrame")[0].contentWindow.addMapPoint(weixin_map_data[ii].map_x,weixin_map_data[ii].map_y);
	}
}