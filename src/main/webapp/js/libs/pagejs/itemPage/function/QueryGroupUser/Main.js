var queryGroupUser_window_groupId=0;
var queryGroupUser_out_text_elentId;
var queryGroupUser_out_id_elentId;
(function () {
	$(document).ready(function(){
		initTemp();
		queryGroupUserEventBind();
		initDiv();
	})
	function initTemp(){
		var tmpl = $("#queryGroupUser_window_queryGroup_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			queryGroupUser_window_queryGroup_tr : tmpl
		});
		var tmpl = $("#queryGroupUser_window_queryGroupUserName_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			queryGroupUser_window_queryGroupUserName_tr : tmpl
		});
	}
	function initDiv(){
		
		$('#queryGroupUser_window').modal("show");
		queryGroupUser_window_queryGroupName_but();
	}
})();
function queryGroupUserEventBind(){

}
function queryGroupUser_window_queryGroupName_but(){
	var groupName=$("#queryGroupUser_window_queryGroupName").val();
	addLoadData_div("myCreateCustomChance_div");
	$.ajax({
		type : "POST",
		url : ctx+"Group/getUserInGroup",
		data:{
			groupName:groupName
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#queryGroupUser_window_group_tbody").html($.render.queryGroupUser_window_queryGroup_tr(msg.data));
			}else{
				alertError(msg.info,"queryGroupUser_window_alert");
			}
			removeloadData_div("myCreateCustomChance_div");
			
		}
	})
}
function queryGroupUser_window_queryGroupUserName_but(id){
	var trueName=$("#queryGroupUser_window_queryGroupUserName").val();
	if((id!=undefined&&id!=null)||queryGroupUser_window_groupId!=0){
		if(id!=undefined&&id!=null){
			queryGroupUser_window_groupId=id;
		}
		addLoadData_div("queryGroupUser_window_User_box");
		$.ajax({
			type : "POST",
			url : ctx+"Group/getGroupConpanyUser",
			data:{
				groupId:queryGroupUser_window_groupId,
				trueName:trueName
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#queryGroupUser_window_user_tbody").html($.render.queryGroupUser_window_queryGroupUserName_tr(msg.data));
				}else{
					alertError(msg.info,"queryGroupUser_window_alert");
				}
				removeloadData_div("queryGroupUser_window_User_box");
				
			}
		})
	}else{
		alertError("请选择要查看的组在进行搜索","queryGroupUser_window_alert");
	}
}
function queryGroupUser_window_enter_but(id,text){
	$("#"+queryGroupUser_out_text_elentId).val(text);
	$("#"+queryGroupUser_out_id_elentId).val(id);
	$('#queryGroupUser_window').modal("hide");
}