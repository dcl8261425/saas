var createChanceAdd_window_type;
$(function () {
	$(document).ready(function(){
		initbindEvent();
		
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		window_layout_query("crm/window/");
	}
});
function createChance(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/page/createChanceWindow",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				createChanceAdd_window_type="add";
				functionLayoutRefresh(msg)
			
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}