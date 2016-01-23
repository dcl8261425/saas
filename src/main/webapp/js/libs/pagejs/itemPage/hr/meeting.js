$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		hr_meeting_query(getNowFormatDate());
		$('#hr_meeting_date').datepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd'
		});
		$('#hr_meeting_date_text').val(getNowFormatDate());
	})
	function initTemp(){
		var tmpl = $("#hr_meeting_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_meeting_tr : tmpl
		});
		var tmpl = $("#hr_meeting_set_table_body_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_meeting_set_table_body_tr : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function hr_meeting_query(date){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getCustemmerMeetingInfo",
		data:{
			date:date
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_meeting_body").html($.render.hr_meeting_tr(msg.data));
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function hr_meeting_chanage(type,id,valueid){
	if(type=='stute'){
		$("#"+id).html("<select class='form-control pull-right' id='"+id+"_input'>" +
				"<option class='form-control pull-right' value='0'>未签到</option>"+
				"<option class='form-control pull-right' value='1'>迟早</option>"+
				"<option class='form-control pull-right' value='2'>早退</option>"+
				"<option class='form-control pull-right' value='3'>迟到加早退</option>"+
				"<option class='form-control pull-right' value='4'>外出</option>"+
				"<option class='form-control pull-right' value='5'>正常签到</option>"+
				"</select>");
		$("#"+id+'_input').blur(function(){
			var value=$("#"+id+'_input').val();
			if(value==0){
				$("#"+id).html("未签到");
			}
			if(value==1){
				$("#"+id).html("迟早");
			}
			if(value==2){
				$("#"+id).html("早退");
			}
			if(value==3){
				$("#"+id).html("迟到加早退");
			}
			if(value==4){
				$("#"+id).html("外出");
			}
			hr_meeting_chanage_ajax("stute",value,valueid);
		})
		$("#"+id+'_input').focus();
	}
	if(type=='marks'){
		var value=$("#"+id).html();
		$("#"+id).html("<input class='form-control pull-right' id='"+id+"_input' value='"+value+"'></input>");
		$("#"+id+'_input').blur(function(){
			var value=$("#"+id+'_input').val();
			hr_meeting_chanage_ajax("marks",value,valueid);
			$("#"+id).html(value);
			
		})
		$("#"+id+'_input').focus();
		
	}
}
function hr_meeting_chanage_ajax(type,value,valueid){
	if(value.trim()==""){
		alertError("内容不能为空，所以未更新","alertDiv");
		return;
	}
	var id=$("#hr_look_user_id").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getUpdateMeetingInfo",
		data:{
			type:type,
			value:value,
			id:valueid
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function hr_meeting_manage_set(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getMeetingSetInfo",
		data:{
			
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.obj!=null){
					$("#hr_heeting_set_up").val(msg.obj.startDate);
					$("#hr_heeting_set_down").val(msg.obj.endDate);
					$("#hr_meeting_is_ip_"+msg.obj.iptest).attr("selected","selected");
				}
				$("#hr_meeting_set_table_body").html($.render.hr_meeting_set_table_body_tr(msg.data));
				$("#hr_meeting_set_window").modal("show");
				$('#hr_meeting_set_up_but').clockpicker();
				$('#hr_meeting_set_down_but').clockpicker();
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
	
}
function hr_meeting_set_enter(){
	var uptime=$("#hr_heeting_set_up").val();
	var downtime=$("#hr_heeting_set_down").val();
	var ip=$("#hr_meeting_is_ip").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/getMeetingSet",
		data:{
			uptime:uptime,
			downtime:downtime,
			ip:ip
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"hr_meeting_set_AlertDiv");
			}else{
				alertError(msg.info,"hr_meeting_set_AlertDiv");
			}
		}
    })
}
function hr_meeting_query_but(){
	hr_meeting_query($('#hr_meeting_date_text').val());
}