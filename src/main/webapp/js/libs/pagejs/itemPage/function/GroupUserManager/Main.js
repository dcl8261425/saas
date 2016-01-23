var function_groupSelectUser_nowpage=1;
var function_groupSelectUser_countNum=30;
var function_groupuser_nowpage=1;
var function_groupuser_countNum=30;
var roleSeletManager_id=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		Group_UserManager_initbindEvent();
		Group_UserManager_initDiv();
	})
	function initTemp(){
		var tmpl = $("#groupUpUserList_table_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupUpUserList_table_content : tmpl
		});
		var tmpl = $("#groupUserList_table_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupUserList_table_content : tmpl
		});
		var tmpl = $("#groupUserRoleList_table_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupUserRoleList_table_content : tmpl
		});
		var tmpl = $("#groupRoleList_table_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupRoleList_table_content : tmpl
		});
	}

	function Group_UserManager_initDiv(){
		$('#groupList_lookGroupUser_window').modal("show");
		$("#group_userTrueName_search_but").click();
	}
});
/**
 * 添加组用户界面的列表中选择一个用户的按钮
 * @param id
 */
function selectUpGroupUser_selected(id){
	addLoadData_div("lookUpGroupUser");
	$.ajax({
		type : "POST",
		url : ctx+"Group/insertConpanyUserToGroup",
		data:{
			groupId:selectLookGroupUser_id,
			userId:id
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#groupList_lookCanManagerUser_window').modal("hide");
				$("#group_userTrueName_search_but").click();
			}else{
				alertError(msg.info,"lookUpGroupUser_content");
				$('#groupList_table_userTrueName_addUser').click();
			}
			removeloadData_div("lookUpGroupUser");
			
		}
	})
	
}
/**
 * 删除用户的角色按钮
 */
function deleteGroupUserRole_selected(id){
	ui.confirm("是否删除",function(b){
		if(b){
	addLoadData_div("lookGroupUserRole_content");
	$.ajax({
		type : "POST",
		url : ctx+"RolePermission/deleteRoleToConpanyUser",
		data:{
			roleid:id,
			groupId:selectLookGroupUser_id,
			userId:roleSeletManager_id
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"lookGroupUser_content");
				Group_UserManager_initbindEvent();
				ManagerGroupUserRole_selected(roleSeletManager_id);
			}else{
				alertError(msg.info,"lookGroupUser_content");
				Group_UserManager_initbindEvent();
			}
			removeloadData_div("lookGroupUserRole_content");
			
		}
	})
		}
	})
}
/**
 * 添加组用户界面的翻页
 */
function ajaxReadData_lookUpGroupUser_table_content_tbody(num){
	if(num<1){
		num=1;
	}
	function_groupSelectUser_nowpage=num;
	$("#groupUpSelectUserPageItem_"+num).attr("class","active");
	$("#lookUpGroupUser_but_search").click();
}
/**
 * 组用户列表的翻页
 */
function ajaxReadData_GroupUser_table_content_tbody(num){
	if(num<1){
		num=1;
	}
	function_groupSelectUser_nowpage=num;
	$("#groupuserPageItem_"+num).attr("class","active");
	$("#group_userTrueName_search_but").click();
}
/**
 * 从组中删除用户
 * @param id
 */
function deleteGroupUser_selected(id){
	ui.confirm("是否删除",function(b){
		if(b){
			$.ajax({
				type : "POST",
				url : ctx+"Group/deleteConpanyUserFormGroup",
				data:{
					userId:id,
					groupId:selectLookGroupUser_id
				},
				success : function(msg) {
					if(msg.success==null||msg.success==undefined||msg.success==true){
						alertSuccess(msg.info,"lookGroupUser_content");
						$("#group_userTrueName_search_but").click();
						Group_UserManager_initbindEvent();
					}else{
						alertError(msg.info,"lookGroupUser_content");
						Group_UserManager_initbindEvent();
					}
				}
			})
		}
	})
}
/**
 * 查看组用户所扮演的角色
 * @param id
 */
function ManagerGroupUserRole_selected(id){
	addLoadData_div("lookGroupUserRole_content");
	roleSeletManager_id=id; 
	$.ajax({
		type : "POST",
		url : ctx+"RolePermission/lookupRoleByUser",
		data:{
			userId:id,
			groupId:selectLookGroupUser_id
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#groupListRole_table_content_tbody").html($.render.groupUserRoleList_table_content(msg.data));
			}else{
				alertError(msg.info,"lookGroupUser_content");
				Group_UserManager_initbindEvent();
			}
			removeloadData_div("lookGroupUserRole_content");
			
		}
	})
}
//初始化时间，拿出来是为了在点击删除后从新初始化事件
function Group_UserManager_initbindEvent(){
	//组内添加用户按钮，弹出上一组的用户列表
	$("#groupList_table_userTrueName_addUser").bind("click",function(){
		$('#groupList_lookCanManagerUser_window').modal("show");
		$('#lookUpGroupUser_but_search').unbind("click");
		/**
		 * 绑定添加用户弹出框的查询按钮
		 * 主要用于数据的翻页等等
		 */
		$('#lookUpGroupUser_but_search').bind("click",function(){
			addLoadData_div("lookUpGroupUser");
			var truename=$("#group_lookUpGroupUserTrueName_search_but_input").val();
		    $.ajax({
				type : "POST",
				url : ctx+"Group/getGroupConpanyUser",
				data:{
					trueName:truename,
					nowpage:function_groupSelectUser_nowpage,
					countNum:function_groupSelectUser_countNum,
					groupId:groupList_nowGroupId
				},
				success : function(msg) {
					if(msg.success==null||msg.success==undefined||msg.success==true){
						removeloadData_div("lookUpGroupUser");
						$("#lookUpGroupUser_table_content_tbody").html($.render.groupUpUserList_table_content(msg.data));
						var page_html="<li><a href=\"javascript:ajaxReadData_lookUpGroupUser_table_content_tbody('"+(parseInt(function_groupSelectUser_nowpage)-1)+"')\">«</a></li>";
						var startPage=function_groupSelectUser_nowpage>15?function_groupSelectUser_nowpage-14:1;
						var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
						for(var ii=startPage;ii<=endPage;ii++){
							var classtype=function_groupSelectUser_nowpage==ii?"active":"";
							page_html=page_html+"<li class=\""+classtype+"\" id=\"groupUpSelectUserPageItem_"+ii+"\"><a href=\"javascript:ajaxReadData_lookUpGroupUser_table_content_tbody('"+ii+"',this)\">"+ii+"</a></li>";
						}
						page_html=page_html+"<li><a href=\"javascript:ajaxReadData_lookUpGroupUser_table_content_tbody('"+(parseInt(function_groupSelectUser_nowpage)+1)+"')\">»</a></li>"
						$("#lookUpGroupUser_table_content_page").html(page_html);

					}else{
						alertError(msg.info,"lookUpGroupUser_content");
						removeloadData_div("lookUpGroupUser");
						 Group_UserManager_initbindEvent();
					}
				}
		    })
		});
		$('#lookUpGroupUser_but_search').click();
	})
	$('#group_userTrueName_search_but').unbind("click");
	//查看本组用户的 点击查询按钮
	$("#group_userTrueName_search_but").bind("click",function(){
			addLoadData_div("lookGroupUser_body");
			var truename=$("#group_userTrueName_search_but_input").val();
		    $.ajax({
				type : "POST",
				url : ctx+"Group/getGroupConpanyUser",
				data:{
					trueName:truename,
					nowpage:function_groupuser_nowpage,
					countNum:function_groupuser_countNum,
					groupId:selectLookGroupUser_id
				},
				success : function(msg) {
					if(msg.success==null||msg.success==undefined||msg.success==true){
						removeloadData_div("lookGroupUser_body");
						$("#lookGroupUser_table_content_tbody").html($.render.groupUserList_table_content(msg.data));
						var page_html="<li><a href=\"javascript:ajaxReadData_GroupUser_table_content_tbody('"+(parseInt(function_groupSelectUser_nowpage)-1)+"')\">«</a></li>";
						var startPage=function_groupSelectUser_nowpage>15?function_groupSelectUser_nowpage-14:1;
						var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
						for(var ii=startPage;ii<=endPage;ii++){
							var classtype=function_groupSelectUser_nowpage==ii?"active":"";
							page_html=page_html+"<li class=\""+classtype+"\" id=\"groupuserPageItem_"+ii+"\"><a href=\"javascript:ajaxReadData_GroupUser_table_content_tbody('"+ii+"',this)\">"+ii+"</a></li>";
						}
						page_html=page_html+"<li><a href=\"javascript:ajaxReadData_GroupUser_table_content_tbody('"+(parseInt(function_groupSelectUser_nowpage)+1)+"')\">»</a></li>"
						$("#lookGroupUser_table_content_page").html(page_html);

					}else{
						alertError(msg.info,"lookGroupUser_content");
						removeloadData_div("lookGroupUser_body");
						 Group_UserManager_initbindEvent();
					}
				}
		    })
		
	})
	//角色添加界面弹出
	$("#groupList_userRoleName_table_addRole").bind("click",function(){
		if(roleSeletManager_id==0){
			alertError("用户没有选择，请在组用户列表中选择一个用户，点击管理角色后才可以向该用户添加角色。","lookGroupUser_content");
			Group_UserManager_initbindEvent();
		}else{
		$('#groupList_lookGroupRole_window').modal("show");
		$('#lookUpGroupRole_but_search').unbind("click");
		/*
		 * 点击搜索出现的页面
		 */
		bindGroupRoleSerch_button();
		$('#lookUpGroupRole_but_search').click();
		}
	})
}
function bindGroupRoleSerch_button(){
	$('#lookUpGroupRole_but_search').bind("click",function(){
		var roleName=$("#group_lookUpGroupRole_roleName_search_but_input").val();
		addLoadData_div("groupRoleList_content");
		$.ajax({
			type : "POST",
			url : ctx+"RolePermission/lookupRoleAll",
			data:{
				groupId:selectLookGroupUser_id,
				roleName:roleName
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#lookUpGroupRole_table_content_tbody").html($.render.groupRoleList_table_content(msg.data));
				}else{
					alertError(msg.info,"groupRoleList");
					bindGroupRoleSerch_button();
				}
				removeloadData_div("groupRoleList_content");

			}
			
		})
	});
}
/**
 * 绑定添加角色内的列表中"添加"按钮
 */
function addGroupUserRole_selected(id){
	addLoadData_div("groupRoleList_content");
	$.ajax({
		type : "POST",
		url : ctx+"RolePermission/addRoleToConpanyUser",
		data:{
			roleid:id,
			groupId:selectLookGroupUser_id,
			userId:roleSeletManager_id
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#lookUpGroupRole_but_search').click();
				$('#groupList_lookGroupRole_window').modal("hide");
				ManagerGroupUserRole_selected(roleSeletManager_id);
			}else{
				alertError(msg.info,"groupRoleList");
				bindGroupRoleSerch_button();
			}
			removeloadData_div("groupRoleList_content");
		}
	})
}