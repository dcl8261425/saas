var Edit_Item_ElementId_id;
var Edit_Item_ElementId_text;
var Edit_linkman_add_num = 1;
var Edit_chance_add_num = 1;
var Edit_user_add_num = 1;
var Edit_good_add_num=1;
var Edit_up_Open_item;
/*
 * 主要快捷浏览
 */
$(function() {
	$(document).ready(function() {

	})
})
/**
 * 当鼠标指向链接处点击时
 */
function selectEditStringLinkData(id, type, element) {
	if (Edit_up_Open_item == element) {
		Edit_hide(Edit_up_Open_item);
		Edit_up_Open_item = "";
	}
	Edit_hide(Edit_up_Open_item);
	Edit_up_Open_item = element;
	$('#' + element).popover(
			{
				placement : 'bottom',
				content : "<div id='Edit_" + element + "_div'>正在加载数据....</div>",
				title : "<div style=\"width:240px;\">" + "<button onclick=\"Edit_hide('" + element
						+ "')\" class=\"btn btn-danger btn-sm refresh-btn\" data-toggle=\"tooltip\" title=\"Reload\">"
						+ "<i class=\"fa fa-times\">" + "</i>" + "</button>" + "</div>",
				html : true,
				animation : false,
				trigger : "click"
			});
	$('#' + element).popover('show');

	/**
	 * 初始化 联系人
	 */
	$
			.ajax({
				type : "POST",
				url : ctx + "crm/function/getObject",
				data : {
					id : id,
					type : type
				},
				success : function(msg) {
					if (msg.success == null || msg.success == undefined || msg.success == true) {
						
						if (type == "custemor") {
							var html = "<div class=\"box box-primary\" >"
									+ "<div class=\"box-body no-padding\">"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
									+ "名称"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.customerName
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "地址"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.customerAddress
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "类别"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.customerType
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "级别"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ (msg.obj.customerLevel==1?"普通客户":msg.obj.customerLevel==2?"中等客户":msg.obj.customerLevel==3?"重级客户":msg.obj.customerLevel==4?"特级客户":"没有设置")
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm \">"
									+ "备注"
									+ "</button>"
									+ "</div>"
									+ "<textarea rows=\"4\" disabled=\"disabled\" cols=\"80\" class=\"form-control input-sm\" >"
									+ msg.obj.customerMark
									+ "</textarea>"
									+

									"</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "备注"
									+ "</button>"
									+ "</div>"
									+ "<textarea rows=\"4\" disabled=\"disabled\" cols=\"80\" class=\"form-control input-sm\" >"
									+ msg.obj.createManMark
									+ "</textarea>"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "指派"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ (msg.obj.NotesUserName == undefined ? "未指派" : msg.obj.NotesUserName)
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "创建"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.creayeManName + "\">" + "</div>" +

									"<div>" + "</div>" +

									"</div>" + "</div>";
							$('#Edit_' + element + "_div").html(html);
						} else if (type == "linkman") {
							var html = "<div class=\"box box-primary\" >"
									+ "<div class=\"box-body no-padding\">"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
									+ "名称"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.linkManName
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "性别"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.linkManSex
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "电话"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.linkManPhone
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "职位"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.linkManJob
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm \">"
									+ "备注"
									+ "</button>"
									+ "</div>"
									+ "<textarea rows=\"4\" disabled=\"disabled\" cols=\"80\" class=\"form-control input-sm\" >"
									+ msg.obj.linkManMark
									+ "</textarea>"
									+

									"</div>"
									+

									"<div>"
									+ "</div>"
									+

									"<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "生日"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ JavaSTojsDate(msg.obj.linkManBirthday)
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "积分"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.linkManScore
									+ "\">"
									+ "</div>"
									+

									"<div>"
									+ "</div>"
									+ "<div class=\"row\">"
									+ "<div class=\"col-xs-12\">"
									+ "<div class=\"input-group\">"
									+ "<div class=\"input-group-btn\">"
									+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
									+ "添加"
									+ "</button>"
									+ "</div>"
									+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
									+ msg.obj.addUserName + "\">" + "</div>" +

									"<div>" + "</div>" + "</div>" + "</div>";
							$('#Edit_' + element + "_div").html(html);
						} else if (type == "NoteItem") {
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "标题"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.notesTitle
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "作者"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.notesUserName
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "地址"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ 
								(msg.obj.notesAddress==null?"设备没有地址记录功能":msg.obj.notesAddress)
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "设备"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ (msg.obj.notesDriver==null?"计算机":msg.obj.notesDriver)
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ 

								"<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "日期"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ JavaSTojsDate(msg.obj.notesDate)
								+ "\">"
								+ "</div>"
								+


								"<div>" + "</div>" + "</div>" + "</div>";
						$('#Edit_' + element + "_div").html(html);
						} else if (type == "Note") {
							var list=msg.data;
							var htmlobj="";
							for(var i=0;i<list.length;i++){
								var title=list[i].notesTitle;
								var mark=list[i].notesMark;
								htmlobj=htmlobj+"<li>"+title+"</li>";
								htmlobj=htmlobj+"<div>"+mark+"</div>";
							}
							var html = "<div class=\"box box-primary\" style=\"max-height: 400px;overflow: auto;\">"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
									+"<ul class=\"todo-list\" >"
									+htmlobj
									+"</ul>"
								+ "</div>"
								+ "</div>"
								+ "</div>"
								+ "</div>";
								$('#Edit_' + element + "_div").html(html);
						} else if (type == "user") {
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "名 称"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.trueName
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "email"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.email
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "可 用"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ (msg.obj.useLogin?"可登陆":" 不可登陆")
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "电 话"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ (msg.obj.phone==null?"未填写":msg.obj.phone)
								+ "\">"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "地 址"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ (msg.obj.address==null?"未填写":msg.obj.address)
								+ "\">"
								+ "</div>"
								+"<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "类 型"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ (msg.obj.state==null?"未填写":msg.obj.state)
								+ "\">"
								+ "</div>"
								"<div>"+

								"</div>" + "</div>";
								$('#Edit_' + element + "_div").html(html);
						}else if(type == "goodSource"){
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "名 称"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.name
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "地 址"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.address
								+ "\">"
								+ "</div>"
								
								"<div>"+

								"</div>" + "</div>";
						$('#Edit_' + element + "_div").html(html);
						} else if(type == "StoreHouse"){
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "名 称"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.name
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "管 理"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.managerUserName
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+

								"<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "电 话"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.tal
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "地 址"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.address
								+ "\">"
								+ "</div>"
								+
								"<div>" + "</div>" + "</div>" + "</div>";
						$('#Edit_' + element + "_div").html(html);
						}else if(type == "goodsourceLinkMan"){
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "名 称"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.name
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "电 话"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.phone
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+

								"<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "生 日"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ JavaSTojsDate(msg.obj.linkManBirthday)
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "公 司"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.goodsSourceName
								+ "\">"
								+ "</div>"
								+
								"<div>" + "</div>" + "</div>" + "</div>";
						$('#Edit_' + element + "_div").html(html);
						}else if(type == "good"){
							var html = "<div class=\"box box-primary\" >"
								+ "<div class=\"box-body no-padding\">"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\"  class=\"btn btn-danger btn-sm\">"
								+ "名 称"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.goodsName
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "类 别"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.goodsType
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+

								"<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "售 价"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.price
								+ "\">"
								+ "</div>"
								+

								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "积 分"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.score
								+ "\">"
								+ "</div>"
								+
								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "拼 音"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.spell
								+ "\">"
								+ "</div>"
								+								"<div>"
								+ "</div>"
								+ "<div class=\"row\">"
								+ "<div class=\"col-xs-12\">"
								+ "<div class=\"input-group\">"
								+ "<div class=\"input-group-btn\">"
								+ "<button type=\"button\" class=\"btn btn-danger btn-sm\">"
								+ "型 号"
								+ "</button>"
								+ "</div>"
								+ "<input type=\"text\" disabled=\"disabled\" class=\"form-control input-sm\" value=\""
								+ msg.obj.goodsModel
								+ "\">"
								+ "</div>"
								+
								"<div>" + "</div>" + "</div>" + "</div>";
						$('#Edit_' + element + "_div").html(html);
						} 
					}else {
						$('#Edit_' + element + "_div").html("");
						alertError(msg.info, 'Edit_' + element + "_div");
					}
				}
			})
	// $('#Edit_'+element+"_div").html("加载成功");
}
/**
 * 添加联系人
 */
function Edit_add_linkman(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'linkman');

}
/**
 * 添加开发记录
 */
function Edit_add_Note(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'Note');

}
/**
 * 添加初始员工
 */
function Edit_add_custemor(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'custemor');

}
/**
 * 添加初始库房
 */
function Edit_add_StoreHouse(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'storehouse');

}
/**
 * 添加初始供货商
 */
function Edit_add_GoodSource(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'goodsource');

}
/**
 * 添加初始商品
 */
function Edit_add_Good(elementId, elementId2) {
	initSelectWindow(elementId, elementId2, 'good');

}
function initSelectWindow(elementId, elementId2, type) {
	if (type == 'linkman') {

		/**
		 * 初始化 联系人
		 */
		var i2 = $.layer({
			type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx + "crm/page/selectLinkMan",
			data : {},
			success : function(msg) {
				layer.close(i2);
				if (msg.success == null || msg.success == undefined || msg.success == true) {
					functionLayout_select(msg, "select_page1");
					Edit_Item_ElementId_id = elementId;
					Edit_Item_ElementId_text = elementId2;
					Edit_AddLinkManWindow_initDiv();
				} else {
					alertError(msg.info, "content");
				}
			}
		})
	}
	/**
	 * 初始化 开发记录
	 */
	if (type == 'Note') {
		var i2 = $.layer({
			type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx + "crm/page/selectNotes",
			data : {},
			success : function(msg) {
				layer.close(i2);
				if (msg.success == null || msg.success == undefined || msg.success == true) {
					functionLayout_select(msg, "select_page2");
					Edit_Item_ElementId_id = elementId;
					Edit_Item_ElementId_text = elementId2;
					Edit_chanceOpenInfo_initDiv();
				} else {
					alertError(msg.info, "content");
				}
			}
		})
	}
	if (type == 'custemor') {
		/**
		 * 初始化员工
		 */
		var i3 = $.layer({
			type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx + "crm/page/selectCustemor",
			data : {},
			success : function(msg) {
				layer.close(i3);
				if (msg.success == null || msg.success == undefined || msg.success == true) {
					functionLayout_select(msg, "select_page3");
					Edit_Item_ElementId_id = elementId;
					Edit_Item_ElementId_text = elementId2;
					Edit_queryGroupUser_initDiv();
				} else {
					alertError(msg.info, "content");
				}
			}
		})
	}
	if (type == 'storehouse') {
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"goods/page/Edit_StoreHouse_window",
			data:{
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					functionLayout_select(msg, "select_page4");
					//给成员赋值
					StoreHouse_box_EditLinkDate_element_id=elementId;
				}else{
					alertError(msg.info, "content");
				}
			}
	    })
	}
	if (type == 'goodsource') {
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"goods/page/Edit_GoodSource_window",
			data:{
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					functionLayout_select(msg, "select_page5");
					//给成员赋值
					GoodSource_box_EditLinkDate_element_id=elementId;
					GoodSource_box_select_type_linkman=true;
				}else{
					alertError(msg.info, "content");
				}
			}
	    })
	}
	if (type == 'good') {
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"goods/page/queryData_window",
			data:{
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					functionLayoutRefresh2(msg);
					is_select=true;
					return_function=function(obj){
						var id=obj.id;
						var name=obj.goodsName;
						var type=obj.goodsType;
						var price=obj.price;
						var score=obj.score;
						var spell=obj.spell;
						var model=obj.goodsModel;
						var str="<a id='Good_box_"+id+Edit_good_add_num+"' href=\"javascript:selectEditStringLinkData("+id+",'good','Good_box_"+id+Edit_good_add_num+"')\">"+name+"</a>"
						Edit_good_add_num++;
						$("#"+elementId).val($("#"+elementId).val()+str);
						$("#good_addData_window_goodsModel").val(model);
					}
				}else{
					alertError(msg.info,"good_addData_window_AlertDiv");
				}
			}
	    })
	}
}
/**
 * 渲染功能项3 展示第三层时使用
 * 
 * @param htmlcontent
 */
function functionLayout_select(htmlcontent, id) {
	$("#" + id).html(htmlcontent);
}
function Edit_hide(id) {
	$('#' + id).popover('destroy');
}