$(function () {
	$(document).ready(function(){
		initTemp();
		hr_window_custemr_top_query();
	})
	function initTemp(){
		var tmpl = $("#hr_window_custemmer_look_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_window_custemmer_look_tr : tmpl
		});
	}
	
});
function hr_window_custemr_top_query(){
	addLoadData_div("hr_window_custemmer_top");
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/queryCustemmer",
		data:{
			trueName:"",
			nowpage:1,
			countNum:10
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#hr_window_custemmer_look_tbody").html($.render.hr_window_custemmer_look_tr(msg.data));
				
			}else{
				alertError(msg.info,"hr_window_custemmer_look");
			}
			removeloadData_div("hr_window_custemmer_top");
		}
	})
}