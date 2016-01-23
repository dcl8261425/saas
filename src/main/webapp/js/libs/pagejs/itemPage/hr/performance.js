$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		$('#hr_performance_date_start').datepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd'
		});
		$('#hr_performance_date_start_text').val(getNowFormatDate(true));
		$('#hr_performance_date_end').datepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd'
		});
		$('#hr_performance_date_end_text').val(getNowFormatDate());
	})
	function initTemp(){
		var tmpl = $("#hr_performance_table_body_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_performance_table_body_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function hr_performance_query(){
	var startDate=$("#hr_performance_date_start_text").val();
	var endDate=$("#hr_performance_date_end_text").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getPerForMance",
		data:{
			startDate:startDate,
			endDate:endDate
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_performance_tbody").html($.render.hr_performance_table_body_tr(msg.data));
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}