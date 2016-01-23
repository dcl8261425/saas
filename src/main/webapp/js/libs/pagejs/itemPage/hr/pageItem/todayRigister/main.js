$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		hr_window_meeting_query(getNowFormatDate());
	})
	function initTemp(){
		var tmpl = $("#hr_window_meeting_body_today_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_window_meeting_body_today_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function hr_window_meeting_query(date){
	
	addLoadData_div("hr_window_meeting_today");
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getCustemmerMeetingInfo",
		data:{
			date:date
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_window_meeting_body_today").html($.render.hr_window_meeting_body_today_tr(msg.data));
			}else{
				alertError(msg.info,"hr_window_meeting_body_today_alert");
			}
			removeloadData_div("hr_window_meeting_today");
		}
    })
}