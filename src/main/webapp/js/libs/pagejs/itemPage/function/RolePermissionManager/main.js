var RolePermissionManager_select_role_id=0;
var RolePermissionManager_select_cliend_id=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		RoleManagerinitbindEvent();
		initDiv();
	})
	function initTemp(){
		var tmpl = $("#roleAndPermissionManager_Role_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			roleAndPermissionManager_Role_content : tmpl
		});
		var tmpl = $("#roleAndPermissionManager_permission_content_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			roleAndPermissionManager_permission_content_tr : tmpl
		});
		var tmpl = $("#roleAndPermissionManager_addPermission_content_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			roleAndPermissionManager_addPermission_content_tr : tmpl
		});
		var tmpl = $("#roleAndPermissionManager_addPermission_content_tr_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			roleAndPermissionManager_addPermission_content_tr_main : tmpl
		});
	}

	function initDiv(){
		$('#roleAndPermissionManager_window').modal("show");
		$("#roleAndPermissionManager_role_search_but").click();
		//alertSuccess("权限管理页面成功加载","lookGroupUser_body");
		$("#roleAndPermissionManager_role_search_but").click();
	}
});
function RoleManagerinitbindEvent(){
	//角色搜索按钮
	$("#roleAndPermissionManager_role_search_but").unbind("click");
	$("#roleAndPermissionManager_role_search_but").bind("click",function(){
		
		var roleName=$("#roleAndPermissionManager_role_search_but_input").val();
		addLoadData_div("roleAndPermissionManager_role_body");
		$.ajax({
			type : "POST",
			url : ctx+"RolePermission/lookupRoleAll",
			data:{
				groupId:selectLookGroupRolePermission_id,
				roleName:roleName
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#roleAndPermissionManager_role_table_content_tbody").html($.render.roleAndPermissionManager_Role_content(msg.data));
				}else{
					alertError(msg.info,"roleAndPermissionManager_role_content");
				}
				removeloadData_div("roleAndPermissionManager_role_body");
			}
		})
	})
	//角色添加按钮
	$("#roleAndPermissionManager_role_add").unbind("click");
	$("#roleAndPermissionManager_role_add").bind("click",function(){
		$('#roleAndPermissionManager_createRole_window').modal("show");
		$("#roleAndPermissionManager_createRole_window_create").unbind("click");
		roleAndPermissionManager_role_add_enter();

	})
	//权限搜索按钮
	$("#roleAndPermissionManager_permission_search_but").unbind("click");
	$("#roleAndPermissionManager_permission_search_but").bind("click",function(){
		if(RolePermissionManager_select_role_id==0){
			alertError("错误，请先选择角色。点击查看权限后才可以增加权限","roleAndPermissionManager_role_content");
			RoleManagerinitbindEvent();
			return ;
		}
		var name=$("#roleAndPermissionManager_permission_search_but_input").val();
		addLoadData_div("roleAndPermissionManager_permission_content");
		$.ajax({
			type : "POST",
			url : ctx+"RolePermission/lookupPremissionByRole",
			data:{
				groupId:selectLookGroupRolePermission_id,
				roleId:RolePermissionManager_select_role_id,
				name:name
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#roleAndPermissionManager_permission_table_content_tbody").html($.render.roleAndPermissionManager_permission_content_tr(msg.data));
				}else{
					alertError(msg.info,"roleAndPermissionManager_role_content");
					RoleManagerinitbindEvent();
				}
				removeloadData_div("roleAndPermissionManager_permission_content");
			}
		})
	})
	//权限添加按钮
	$("#roleAndPermissionManager_permission_addPermission").unbind("click");
	$("#roleAndPermissionManager_permission_addPermission").bind("click",function(){
		if(RolePermissionManager_select_role_id==0){
			alertError("错误，请先选择角色。点击查看权限后才可以增加权限","roleAndPermissionManager_role_content");
			RoleManagerinitbindEvent();
			return ;
		}
		$('#roleAndPermissionManager_addPermission_window').modal("show");
		$("#roleAndPermissionManager_addPermission_search_but_main").unbind("click");
		bindPermissionSerch_main();
		$("#roleAndPermissionManager_addPermission_search_but_main").click();
	})
	
}
//删除角色
function roleAndPermissionManager_deleteRole_selected(id){
	ui.confirm("是否删除该角色，如果删除该角色，则会影响很多拥有此角色的公司员工",function(b){
		if(b){
			$.ajax({
				type : "POST",
				url : ctx+"RolePermission/deleteRole",
				data:{
					roleId:id,
					groupId:selectLookGroupRolePermission_id
				},
				success : function(msg) {
					if(msg.success){
						$("#roleAndPermissionManager_role_search_but").click();
					}else{
						alertError(msg.info,"roleAndPermissionManager_role_content");
						RoleManagerinitbindEvent();
					}
				}
			})
		}
	})
}
//查看角色权限
function roleAndPermissionManager_lookRolePermission_selected(id){
	RolePermissionManager_select_role_id=id;
	$("#roleAndPermissionManager_permission_search_but").click();
}
//删除权限
function roleAndPermissionManager_deletePrimission_selected(id){
	ui.confirm("是否删除该权限",function(b){
		if(b){
	$.ajax({
		type : "POST",
		url : ctx+"RolePermission/deletePermissionToRole",
		data:{
			roleId:RolePermissionManager_select_role_id,
			groupId:selectLookGroupRolePermission_id,
			permissionId:id
		},
		success : function(msg) {
			if(msg.success){
				alertSuccess(msg.info,"roleAndPermissionManager_role_content");
				RoleManagerinitbindEvent();
				$("#roleAndPermissionManager_permission_search_but").click();
			}else{
				alertError(msg.info,"roleAndPermissionManager_role_content");
				RoleManagerinitbindEvent();
			}
		}
	})
		}
	})
}
//绑定添加角色
function roleAndPermissionManager_role_add_enter(){
	$("#roleAndPermissionManager_createRole_window_create").bind("click",function(){
		var name=$("#roleAndPermissionManager_createRole_window_roleName").val();
		var marks=$("#roleAndPermissionManager_createRole_window_roleMarks").val();
		$.ajax({
			type : "POST",
			url : ctx+"RolePermission/addRole",
			data:{
				roleName:name,
				roleMarks:marks,
				groupId:selectLookGroupRolePermission_id
			},
			success : function(msg) {
				if(msg.success){
					$('#roleAndPermissionManager_createRole_window').modal("toggle");
					$("#roleAndPermissionManager_role_search_but").click();
				}else{
					var list=msg.list;
					if(list==undefined||list==null){
						alertError(msg.info,"roleAndPermissionManager_createRole_window_body");
						roleAndPermissionManager_role_add_enter();
					}else{
						inputAjaxTest(list,"roleAndPermissionManager_createRole_window_");
					}
				}
			}
		})
	})
}
function bindPermissionSerch(){
	$("#roleAndPermissionManager_addPermission_search_but").bind("click",function(){
		//alertError("222","roleAndPermissionManager_addPermission_window_body");
		//addLoadData_div("roleAndPermissionManager_addPermission_window_body_content");
		//removeloadData_div("roleAndPermissionManager_addPermission_window_body_content");
		var name=$("#roleAndPermissionManager_addPermission_search_but_input").val();
		addLoadData_div("roleAndPermissionManager_addPermission_window_body_content");
		$.ajax({
			type : "POST",
			url : ctx+"Permission/lookupPremissionMain",
			data:{
				groupId:selectLookGroupRolePermission_id,
				name:name,
				id:RolePermissionManager_select_cliend_id
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#roleAndPermissionManager_addPermission_table_content_tbody").html($.render.roleAndPermissionManager_addPermission_content_tr(msg.data));
				}else{
					alertError(msg.info,"roleAndPermissionManager_addPermission_window_body");
					$("#roleAndPermissionManager_addPermission_search_but").unbind("click");
					bindPermissionSerch();
				}
				removeloadData_div("roleAndPermissionManager_addPermission_window_body_content");
			}
		})
	})
}
/**
 * 选择添加权限
 * @param id
 */
function roleAndPermissionManager_addPermission_selected(id,isAll){
	if(isAll==undefined||isAll==null||isAll==""){
		isAll=false;
	}
	$.ajax({
		type : "POST",
		url : ctx+"RolePermission/addPermissionToRole",
		data:{
			roleId:RolePermissionManager_select_role_id,
			groupId:selectLookGroupRolePermission_id,
			permissionId:id,
			isAll:isAll
		},
		success : function(msg) {
			if(msg.success){
				$('#roleAndPermissionManager_addPermission_window').modal("hide");
				$("#roleAndPermissionManager_permission_search_but").click();
				alertSuccess(msg.info,"roleAndPermissionManager_role_content");
				RoleManagerinitbindEvent();
			}else{
				alertError(msg.info,"roleAndPermissionManager_addPermission_window_body");
				bindPermissionSerch();
			}
		}
	})
}
function bindPermissionSerch_main(){
	$("#roleAndPermissionManager_addPermission_search_but_main").bind("click",function(){
		//alertError("222","roleAndPermissionManager_addPermission_window_body");
		//addLoadData_div("roleAndPermissionManager_addPermission_window_body_content");
		//removeloadData_div("roleAndPermissionManager_addPermission_window_body_content");
		var name=$("#roleAndPermissionManager_addPermission_search_but_input_main").val();
		addLoadData_div("roleAndPermissionManager_addPermission_window_body_content_main");
		$.ajax({
			type : "POST",
			url : ctx+"Permission/lookupPremissionMain",
			data:{
				groupId:selectLookGroupRolePermission_id,
				name:name
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#roleAndPermissionManager_addPermission_table_content_tbody_main").html($.render.roleAndPermissionManager_addPermission_content_tr_main(msg.data));
				}else{
					alertError(msg.info,"roleAndPermissionManager_addPermission_window_body");
					$("#roleAndPermissionManager_addPermission_search_but_main").unbind("click");
					bindPermissionSerch_main();
				}
				removeloadData_div("roleAndPermissionManager_addPermission_window_body_content_main");
			}
		})
	})
}
/**
 * 查询子权限
 * @param id
 */
function roleAndPermissionManager_addPermission_selected_main(id){
	$("#roleAndPermissionManager_addPermission_search_but").unbind("click");
	bindPermissionSerch();
	RolePermissionManager_select_cliend_id=id;
	$("#roleAndPermissionManager_addPermission_search_but").click();
}