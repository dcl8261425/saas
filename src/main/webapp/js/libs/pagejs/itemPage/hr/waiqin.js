var waiqin_groupIds=0;
var waiqin_userid=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
			
	})
	function initTemp(){
		var tmpl = $("#groupDiv_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupDiv_item : tmpl
		});
		var tmpl = $("#groupDiv_table_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupDiv_table_item : tmpl
		});
		var tmpl = $("#show_waiqin_look_window_map").html().replace("<!--", "").replace("-->", "");
		$.templates({
			show_waiqin_look_window_map : tmpl
		});
	}
	function initbindEvent(){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"hr/function/waiqinGroupManager",
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#groupDiv").html($.render.groupDiv_item(msg.data));
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
	}
})
function waiqin_queryGroupUser(groupid){
	if(groupid==0||groupid==undefined||groupid==null){
		return ;
	}
	if(groupid!=undefined&&groupid!=null){
		waiqin_groupIds=groupid
	}
	var name=$("#trueName").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/waiqinGroupManager",
		data:{
			groupid:waiqin_groupIds,
			trueName:name
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_waiqin_tbody").html($.render.groupDiv_table_item(msg.data));
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function show_waiqin_look_window(id){
	waiqin_userid=id;
	$('#waiqin_look_window_startDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#waiqin_look_window_startDate_text').val(getNowFormatDate(true));
	$('#waiqin_look_window_endDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#waiqin_look_window_endDate_text').val(getNowFormatDate());
	$("#waiqin_look_map").modal("show");
}
function show_waiqin_look_window_query(){
	var startDate=$('#waiqin_look_window_startDate_text').val();
	var endDate=$('#waiqin_look_window_endDate_text').val()
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/waiqinGroupManager",
		data:{
			userid:waiqin_userid,
			startDate:startDate,
			endDate:endDate
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_waiqin_look_window_tbody").html($.render.show_waiqin_look_window_map(msg.data));
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function show_waiqin_look_window_map(str){
	var strs=str.split(";");
	for(var i=0;i<strs.length;i++){
		var strss=strs[i].split(",");
		var x=strss[0];
		var y=strss[1];
		 $(window.parent.document).contents().find("#myFrame")[0].contentWindow.addMapPoint(x,y);
	}
	

}
