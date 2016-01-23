$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		window_layout_query("goods/window/");
	}
});
function good_main_queryDate(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/queryData_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh(msg)
			
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
function good_main_addDate(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/addData_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh(msg)
			
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}