var groupList_table_nowpage=1;
var groupList_nowGroupId=0;
var gourpList_Name="";
var selectLookGroupUser_id=0;//查看组用户时代表组id
var selectLookGroupRolePermission_id=0;//管理角色以及角色权限时代表组用户
var updateGroupId=0;//修改组时用的id
$(function () {
	$(document).ready(function(){
		/*addLoadData_div("systemPermissionList");
		removeloadData_div("systemPermissionList");*/
		initTemp();
		initDiv();
		
	})
	function initTemp(){
		var tmpl = $("#groupList_table_content").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupList_table_content : tmpl
		});
		var tmpl = $("#groupList_createGroup").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupList_createGroup : tmpl
		});	
		var tmpl = $("#groupList_GroupInfo").html().replace("<!--", "").replace("-->", "");
		$.templates({
			groupList_GroupInfo : tmpl
		});	
	}
	

});
function ajaxReadData_groupList_table_content_tbody(num,obj){
	if(num<1){
		num=1;
	}
	groupList_table_nowpage=num;
	$("#grouppageItem_"+num).attr("class","active");
	$("#group_search_but").click();
}
/**
 * 查看组用户
 * @param id
 */
function SystemPermission_lookGroupUser(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"function/GroupUserManager",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				selectLookGroupUser_id=id;
				
				functionLayoutRefresh(msg)
			}else{
				alertError(msg.info,"content");
				initbindEvent();
			}
		}
    })
}
/**
 * 查看组角色和权限
 * @param id
 */
function SystemPermission_lookGroupRolePermission(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"function/RolePermissionManager",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				selectLookGroupRolePermission_id=id;
				functionLayoutRefresh(msg)
			}else{

				alertError(msg.info,"content");
				initbindEvent();
			}
		}
    })
}
/**
 * 查看组的子组
 * @param id
 */
function SystemPermission_lookGroupChildGroup(id){
	groupList_nowGroupId=id;
	initDiv();
}
/**
 * 查看组信息，修改组信息
 * @param id
 */
function SystemPermission_lookGroupInfo(id){
	updateGroupId=id;
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"Group/getGroupInfo",
		data:{
			groupId:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#window").html($.render.groupList_GroupInfo())
				$("#groupList_GroupInfo_window").modal("toggle");
				$("#groupList_GroupInfo_id").val(msg.data.id);
				$("#groupList_GroupInfo_groupName").val(msg.data.groupName);
				$("#groupList_GroupInfo_groupMarks").val(msg.data.groupMarks);
				$("#groupList_GroupInfo_create").unbind("click");
				$("#groupList_GroupInfo_create").bind("click",function(){
					//更新按钮
					var id=$("#groupList_GroupInfo_id").val();
					var name=$("#groupList_GroupInfo_groupName").val();
					var marks=$("#groupList_GroupInfo_groupMarks").val();
					var i=$.layer({
					    type : 3
					});
				    $.ajax({
						type : "POST",
						url : ctx+"Group/updateGroupInfo",
						data:{
							groupId:id,
							groupName:name,
							groupMarks:marks
						},
						success : function(msg) {
							layer.close(i);
							if(msg.success==null||msg.success==undefined||msg.success==true){
								$("#groupList_GroupInfo_window").modal("toggle");
								alertSuccess(msg.info,"alertDiv");
								initDiv();
							}else{
								var list=msg.list;
								if(list==undefined||list==null){
									alertError(msg.info,"createGroup_body");
								}else{
									inputAjaxTest(list,"groupList_GroupInfo_");
								}
								$("#groupList_GroupInfo_id").val(id);
								$("#groupList_GroupInfo_groupName").val(name);
								$("#groupList_GroupInfo_groupMarks").val(marks);
								
							}
						}
				    })
				})
			}else{
				alertError(msg.info,"content");
				initbindEvent();
			}
		}
    })
}
/**
 * 删除组
 * @param id
 */
function SystemPermission_deleteGroup(id){
	ui.confirm("是否删除",function(b){
		if(b){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"Group/deleteGroup",
		data:{
			groupId:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"content");
				initbindEvent();
				$("#group_search_but").click()
			}else{
				alertError(msg.info,"content");
				initbindEvent();
			}
		}
    })
		}
	})
}
function initbindEvent(){
	$("#group_search_but").bind("click",function(){
		$("#group_search_but").unbind("click");
		$("#groupList_table_addGroup").unbind("click");
		gourpList_Name=$("#group_search_but_input").val();
		initDiv();
	});
	$("#groupList_table_addGroup").unbind("click");
	$("#groupList_table_addGroup").bind("click",function(){
		
		$("#window").html($.render.groupList_createGroup())
		$('#groupList_createGroup_window').modal("toggle");
		alertInfo("注意此创建组是在您浏览的当前组下进行创建的子组.创建人在该组拥有所有在其父组拥有的所有权限","createGroup_body");
		$("#groupList_createGroup_create").unbind("click");
		$("#groupList_createGroup_create").bind("click",function(){
			var groupname=$("#groupList_createGroup_groupName").val();
			var groupMarks=$("#groupList_createGroup_groupMarks").val();
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"Group/createGroup",
				data:{
					groupName:groupname,
					groupMarks:groupMarks,
					groupId:groupList_nowGroupId
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$('#groupList_createGroup_window').modal("toggle");
						alertSuccess(msg.info,"alertDiv");
						initDiv();
					}else{
						var list=msg.list;
						if(list==undefined||list==null){
							alertError(msg.info,"createGroup_body");
						}else{
							inputAjaxTest(list,"groupList_createGroup_");
						}
					}
				}
			})
		})
	})
}
function initDiv(){
	addLoadData_div("systemPermissionList");
	 $.ajax({
			type : "POST",
			url : ctx+"Group/getAllGroup",
			data:{
				groupName:gourpList_Name,
				nowpage:groupList_table_nowpage,
				groupId:groupList_nowGroupId
			},
			success : function(msg) {

				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#groupList_table_content_tbody").html($.render.groupList_table_content(msg.data));
					//生成分页代码
					var page_html="<li><a href=\"javascript:ajaxReadData_groupList_table_content_tbody('"+(parseInt(groupList_table_nowpage)-1)+"')\">«</a></li>";
					var startPage=groupList_table_nowpage>15?groupList_table_nowpage-14:1;
					var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
					for(var ii=startPage;ii<=endPage;ii++){
						var classtype=groupList_table_nowpage==ii?"active":"";
						page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:ajaxReadData_groupList_table_content_tbody('"+ii+"',this)\">"+ii+"</a></li>";
					}
					page_html=page_html+"<li><a href=\"javascript:ajaxReadData_groupList_table_content_tbody('"+(parseInt(groupList_table_nowpage)+1)+"')\">»</a></li>"
					$("#groupList_table_page_tbody").html(page_html);
					groupList_nowGroupId=msg.nowgroup;
					removeloadData_div("systemPermissionList");
				}else{
					alertError(msg.info,"content");
					removeloadData_div("systemPermissionList");
				}
				//在此处初始化事件才可用。
				initbindEvent();
			}
	    })
}