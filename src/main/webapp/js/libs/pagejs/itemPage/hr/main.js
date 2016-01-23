$(function () {
	$(document).ready(function(){
		initbindEvent();
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		window_layout_query("hr/window/");
	}
});
function hr_main_addCustemmer(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/page/addCustemmer",
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
function hr_main_queryCustemmer(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/page/queryCustemmer",
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
