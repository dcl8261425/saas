var Edit_queryGroupUser_window_groupId=0;
var Edit_queryGroupUser_out_text_elentId;
var Edit_queryGroupUser_out_id_elentId;

(function () {
	$(document).ready(function(){
		initTemp();
	})
	function initTemp(){
		var tmpl = $("#Edit_queryGroupUser_window_queryGroup_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_queryGroupUser_window_queryGroup_tr : tmpl
		});
		var tmpl = $("#Edit_queryGroupUser_window_queryGroupUserName_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			Edit_queryGroupUser_window_queryGroupUserName_tr : tmpl
		});
	}
	
})();
function Edit_queryGroupUser_initDiv(){
	
	$('#Edit_queryGroupUser_window').modal("show");
	Edit_queryGroupUser_window_queryGroupName_but();
}
function Edit_queryGroupUser_window_queryGroupName_but(){
	var groupName=$("#Edit_queryGroupUser_window_queryGroupName").val();
	addLoadData_div("myCreateCustomChance_div");
	$.ajax({
		type : "POST",
		url : ctx+"Group/getUserInGroup",
		data:{
			groupName:groupName
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#Edit_queryGroupUser_window_group_tbody").html($.render.Edit_queryGroupUser_window_queryGroup_tr(msg.data));
			}else{
				alertError(msg.info,"Edit_queryGroupUser_window_alert");
			}
			removeloadData_div("myCreateCustomChance_div");
			
		}
	})
}
function Edit_queryGroupUser_window_queryGroupUserName_but(id){
	var trueName=$("#Edit_queryGroupUser_window_queryGroupUserName").val();
	if((id!=undefined&&id!=null)||Edit_queryGroupUser_window_groupId!=0){
		if(id!=undefined&&id!=null){
			Edit_queryGroupUser_window_groupId=id;
		}
		addLoadData_div("Edit_queryGroupUser_window_User_box");
		$.ajax({
			type : "POST",
			url : ctx+"Group/getGroupConpanyUser",
			data:{
				groupId:Edit_queryGroupUser_window_groupId,
				trueName:trueName
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#Edit_queryGroupUser_window_user_tbody").html($.render.Edit_queryGroupUser_window_queryGroupUserName_tr(msg.data));
				}else{
					alertError(msg.info,"Edit_queryGroupUser_window_alert");
				}
				removeloadData_div("Edit_queryGroupUser_window_User_box");
				
			}
		})
	}else{
		alertError("请选择要查看的组在进行搜索","Edit_queryGroupUser_window_alert");
	}
}

function Edit_queryGroupUser_window_enter_select(id,name){
	if(Edit_Item_ElementId_text!=undefined||Edit_Item_ElementId_text!=null){
		$("#"+Edit_Item_ElementId_id).val(id);
		$("#"+Edit_Item_ElementId_text).val(name);
	}else{
		var str="<a id='user_"+id+Edit_user_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'user','user_"+id+Edit_user_add_num+"')\">"+name+"</a>"
		Edit_user_add_num++;
		$("#"+Edit_Item_ElementId_id).val($("#"+Edit_Item_ElementId_id).val()+str);
	}
	$('#Edit_queryGroupUser_window').modal("hide");
}