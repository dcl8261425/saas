var weixin_wenzhang_edit;
var weixin_main_resouse="";
var weixin_main_querytype="";
var weixin_wenzhang_page=1;
var weixin_wenzhang_vote_linkman_page=1;
var weixin_main_select_wenzhang_input;
var weixin_main_update_resource=false;
var weixin_main_update_resource_id=0;
var weixin_main_vote_item_num=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		weixin_wenzhang_edit=undefined;
	})
	function initTemp(){
		var tmpl = $("#weixin_menu_set").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_menu_set : tmpl
		});

		var tmpl = $("#weixin_autoResensDate_manager").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResensDate_manager : tmpl
		});
		var tmpl = $("#weixin_autoResend_manager").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResend_manager : tmpl
		});
		var tmpl = $("#weixin_main_menu_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_main_menu_main : tmpl
		});
		var tmpl = $("#weixin_main_menu_Item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_main_menu_Item : tmpl
		});
		var tmpl = $("#weixin_main_menu_content_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_main_menu_content_tr : tmpl
		});
		var tmpl = $("#weixin_main_menu_content_tr_edit").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_main_menu_content_tr_edit : tmpl
		});
		var tmpl = $("#weixin_autoResensDate_manager_table").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResensDate_manager_table : tmpl
		});
		var tmpl = $("#weixin_autoResensDate_manager_table_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResensDate_manager_table_tr : tmpl
		});
		var tmpl = $("#weixin_autoResend_manager_table").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResend_manager_table : tmpl
		});
		var tmpl = $("#weixin_autoResend_manager_table_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_autoResend_manager_table_tr : tmpl
		});
		var tmpl = $("#weixin_main_ResendManage_add_addItem_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_main_ResendManage_add_addItem_window_tbody_tr : tmpl
		});
		var tmpl = $("#weixin_message_manager").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_message_manager : tmpl
		});
		var tmpl = $("#weixin_wenzhang_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wenzhang_tr : tmpl
		});
		var tmpl = $("#weixin_wenzhang_tr_select").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wenzhang_tr_select : tmpl
		});
		var tmpl = $("#weixin_wenzhang_Vote_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wenzhang_Vote_tr : tmpl
		});
		//$("#weixin_weixin_layout").html($.render.groupUpUserList_table_content(msg.data));
	}
	function initbindEvent(){
		
	}
})
function weixin_menu_set(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getMenu",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_weixin_layout").html($.render.weixin_menu_set());
				$("#weixin_menu_body").before($.render.weixin_main_menu_main(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_menu_set_child(id){
	$(".weixin_menu_items_item").remove();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getMenu",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_menu_item_body_"+id).before($.render.weixin_main_menu_Item(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_menu_set_addMainMenu(id){
	$("#weixin_menu_add_main_content").html($.render.weixin_main_menu_content_tr());
	if(id!=undefined&&id!=null){
		$("#weixin_Menu_pid").val(id);
	}
}
function weixin_menu_set_addMainMenu_enter(){
	var name=$("#weixin_Menu_name").val();
	var key=$("#weixin_Menu_key").val();
	var url=$("#weixin_Menu_url").val();
	var id=$("#weixin_Menu_pid").val();
	var obj={
			name:name,
			id:id
		};
	if(key.trim()==""){
		key="0"
	}else{
		obj.key=key;
	}
	if(url.trim()==""){
		url="0"
	}else{
		obj.url=url;
	}
	if(name.trim()==""){
		ui.alert("错误,请填写,名字",2000,false);
		return ;
	}
	$("#weixin_Menu_url").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/addMenu",
		data:obj,
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(id==0){
					$("#weixin_menu_body").before($.render.weixin_main_menu_main(msg.data));
				}else{
					$("#weixin_menu_item_body_"+id).before($.render.weixin_main_menu_Item(msg.data));
					
				}
				$("#weixin_menu_add_main_content").html("");
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_autoResensDate_manager(){
	$("#weixin_weixin_layout").html($.render.weixin_autoResensDate_manager());
	weixin_info_bindEvent();
}
function weixin_autoResend_manager(){
	$("#weixin_weixin_layout").html($.render.weixin_autoResend_manager());
	weixin_info_bindEvent();
}
function weixin_menu_reflash(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/updataMenuToWeiXin",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"weixin_game_alert");
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_menu_main_delete(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/deleteMenu",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"weixin_game_alert");
				weixin_menu_set();
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_menu_main_edit(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getMenuItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_menu_add_main_content").html($.render.weixin_main_menu_content_tr_edit());
				if(id!=undefined&&id!=null){
					$("#weixin_Menu_pid").val(id);
					$("#weixin_Menu_name").val(msg.data.name);
					$("#weixin_Menu_key").val(msg.data.keys_s);
					$("#weixin_Menu_url").val(msg.data.urls_s);
				}
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_menu_main_edit_enter(){
	var name=$("#weixin_Menu_name").val();
	var key=$("#weixin_Menu_key").val();
	var url=$("#weixin_Menu_url").val();
	var id=$("#weixin_Menu_pid").val();
	var obj={
			name:name,
			id:id
		};
	if(key.trim()==""){
		key="0"
	}else{
		obj.key=key;
	}
	if(url.trim()==""){
		url="0"
	}else{
		obj.url=url;
	}
	if(name.trim()==""){
		ui.alert("错误,请填写,名字",2000,false);
		return ;
	}
	$("#weixin_Menu_url").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/editMenu",
		data:obj,
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"weixin_game_alert");
				$("#weixin_menu_add_main_content").html("");
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_main_ResourceManage(type){
	var url="";
	var addfunction="";
	weixin_main_resouse=type;
	if(type=='image'){
		url="getImage";
		addfunction="addImage";
	}
	if(type=='voice'){
		url="getVoice";
		addfunction="addVoice";
	}
	if(type=='video'){
		url="getVideo";
		addfunction="addVideo";
	}
	if(type=='text'){
		url="getText";
		addfunction="addText";
	}
	if(type=='music'){
		url="getMusic";
		addfunction="addMusic";
	}
	if(type=='imageText'){
		url="getImageText";
		addfunction="addImageText";
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_main_resource_manager_body").html($.render.weixin_autoResensDate_manager_table());
				$("#weixin_main_addNewResouse").attr("onclick","weixin_main_ResourceManage_"+addfunction+"()");
				$("#weixin_autoResensDate_manager_table_tbody").html($.render.weixin_autoResensDate_manager_table_tr(msg.data));
			}else{
					alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
//********************创建资源管理
function weixin_main_ResourceManage_addImage(){
	$("#weixin_main_ResourceManage_addImage").modal("show");
	weixin_main_update_resource=false;
}
function weixin_main_ResourceManage_addVoice(){
	$("#weixin_main_ResourceManage_addVoice").modal("show");
	weixin_main_update_resource=false;
}
function weixin_main_ResourceManage_addVideo(){
	$("#weixin_main_ResourceManage_addVideo").modal("show");
	weixin_main_update_resource=false;
}
function weixin_main_ResourceManage_addMusic(){
	$("#weixin_main_ResourceManage_addMusic").modal("show");
	weixin_main_update_resource=false;
}
function weixin_main_ResourceManage_addImageText(){
	$("#weixin_main_ResourceManage_addImageText").modal("show");
	weixin_main_update_resource=false;
}
function weixin_main_ResourceManage_addText(){
	$("#weixin_main_ResourceManage_addText").modal("show");
	weixin_main_update_resource=false;
}
//********************更新资源管理
function weixin_main_ResourceManage_update(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getWeiXinReSend",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var addfunction="";
				switch (msg.obj.type) {
				case 1:
					updatefunction="updateVideo";
					$("#weixin_Video_name").val(msg.obj.name);
					$("#weixin_Video_mediaId").val(msg.obj.mediaId);
					$("#weixin_Video_title").val(msg.obj.title);
					$("#weixin_Video_description").val(msg.obj.description);
					break;
				case 2:
					updatefunction="updateImage";
					$("#weixin_iamge_name").val(msg.obj.name);
					$("#weixin_iamge_mediaId").val(msg.obj.mediaId);
					break;
				case 3:
					updatefunction="updateVoice";
					$("#weixin_voice_name").val(msg.obj.name);
					$("#weixin_voice_mediaId").val(msg.obj.mediaId);
					break;
				case 4:
					updatefunction="updateText";
					$("#weixin_Text_name").val(msg.obj.name);
					$("#weixin_Text_content").val(msg.obj.content);
					break;
				case 5:
					updatefunction="updateMusic";
					$("#weixin_Music_name").val(msg.obj.name);
					$("#weixin_Music_title").val(msg.obj.title);
					$("#weixin_Music_description").val(msg.obj.description);
					$("#weixin_Music_musicURL").val(msg.obj.hQMusicUrl);
					$("#weixin_Music_hQMusicUrl").val(msg.obj.hQMusicUrl);
					$("#weixin_Music_thumbMediaId").val(msg.obj.thumbMediaId);
					break;
				case 6:
					updatefunction="updateImageText";
					$("#weixin_ImageText_name").val(msg.obj.name);
					$("#weixin_ImageText_title").val(msg.obj.title);
					$("#weixin_ImageText_description").val(msg.obj.description);
					$("#weixin_ImageText_picUrl").val(msg.obj.picUrl);
					$("#weixin_ImageText_url").val(msg.obj.url);
					break;
				default:
					ui.alert("型号不对，删除后从新添加",2000,false);
					return ;
					break;
				}
				eval("weixin_main_ResourceManage_"+updatefunction+"()");
				weixin_main_update_resource_id=id;
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function weixin_main_ResourceManage_updateImage(){
	$("#weixin_main_ResourceManage_addImage").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_updateVoice(){
	$("#weixin_main_ResourceManage_addVoice").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_updateVideo(){
	$("#weixin_main_ResourceManage_addVideo").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_updateMusic(){
	$("#weixin_main_ResourceManage_addMusic").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_updateImageText(){
	$("#weixin_main_ResourceManage_addImageText").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_updateText(){
	$("#weixin_main_ResourceManage_addText").modal("show");
	weixin_main_update_resource=true;
}
function weixin_main_ResourceManage_addImage_enter(){
	var url="";
	if(weixin_main_update_resource){
		url="updateImage";
	}else{
		url="addImage";
	}
	var name=$("#weixin_iamge_name").val(); 
	var mediaId=$("#weixin_iamge_mediaId").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		data:{
			name:name,
			mediaId:mediaId,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addImage").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addImage").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_iamge_");
				}
			}
		}
	})
	
}
function weixin_main_ResourceManage_addVoice_enter(){
	var url="";
	if(weixin_main_update_resource){
		url="updateVoice";
	}else{
		url="addVoice";
	}
	var name=$("#weixin_voice_name").val(); 
	var mediaId=$("#weixin_voice_mediaId").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		data:{
			name:name,
			mediaId:mediaId,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addVoice").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addVoice").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_voice_");
				}
			}
		}
	})
	
}
function weixin_main_ResourceManage_addVideo_enter(){
	var url="";
	if(weixin_main_update_resource){
		url="updateVideo";
	}else{
		url="addVideo";
	}
	var name=$("#weixin_Video_name").val(); 
	var mediaId=$("#weixin_Video_mediaId").val(); 
	var title=$("#weixin_Video_title").val(); 
	var description=$("#weixin_Video_description").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		data:{
			name:name,
			mediaId:mediaId,
			title:title,
			description:description,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addVideo").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addVideo").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_Video_");
				}
			}
		}
	})
	
}
function weixin_main_ResourceManage_addMusic_enter(){
	var url="";
	if(weixin_main_update_resource){
		url="updateMusic";
	}else{
		url="addMusic";
	}
	var name=$("#weixin_Music_name").val(); 
	var title=$("#weixin_Music_title").val(); 
	var description=$("#weixin_Music_description").val(); 
	var musicURL=$("#weixin_Music_musicURL").val(); 
	var hQMusicUrl=$("#weixin_Music_hQMusicUrl").val(); 
	var thumbMediaId=$("#weixin_Music_thumbMediaId").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		data:{
			name:name,
			title:title,
			description:description,
			musicURL:musicURL,
			hQMusicUrl:hQMusicUrl,
			thumbMediaId:thumbMediaId,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addMusic").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addMusic").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_Music_");
				}
			}
		}
	})
	
}
function weixin_main_ResourceManage_addImageText_enter(){
	var url2="";
	if(weixin_main_update_resource){
		url2="updateImageText";
	}else{
		url2="addImageText";
	}
	var name=$("#weixin_ImageText_name").val(); 
	var title=$("#weixin_ImageText_title").val(); 
	var description=$("#weixin_ImageText_description").val(); 
	var picUrl=$("#weixin_ImageText_picUrl").val(); 
	var url=$("#weixin_ImageText_url").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url2,
		data:{
			name:name,
			title:title,
			description:description,
			picUrl:picUrl,
			url:url,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addImageText").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addImageText").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_ImageText_");
				}
			}
		}
	})
	
}
function weixin_main_ResourceManage_addText_enter(){
	var url="";
	if(weixin_main_update_resource){
		url="updateText";
	}else{
		url="addText";
	}
	var name=$("#weixin_Text_name").val(); 
	var content=$("#weixin_Text_content").val(); 
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		data:{
			name:name,
			content:content,
			id:weixin_main_update_resource_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResourceManage_addText").modal("hide");
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResourceManage_addText").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_Text_");
				}
			}
		}
	})
	
}
function weixin_main_Resouse_delete(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/deleteReSend",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				weixin_main_ResourceManage(weixin_main_resouse)
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
	})
}
function weixin_main_ResendManage(type){
	var url="";
	var addfunction="";
	weixin_main_resouse=type;
	if(type=='image'){
		url="getAutoReSend_Image";
		addfunction="addAutoReSend_Image";
	}
	if(type=='voice'){
		url="getAutoReSend_Voice";
		addfunction="addAutoReSend_Voice";
	}
	if(type=='video'){
		url="getAutoReSend_Video";
		addfunction="addAutoReSend_Video";
	}
	if(type=='text'){
		url="getAutoReSend_Text";
		addfunction="addAutoReSend_Text";
	}
	if(type=='location'){
		url="getAutoReSend_Location";
		addfunction="addAutoReSend_Location";
	}
	if(type=='event'){
		url="getAutoReSend_Event";
		addfunction="addAutoReSend_Event";
	}
	if(type=='link'){
		url="getAutoReSend_Link";
		addfunction="addAutoReSend_Link";
	}
	weixin_main_querytype=type;
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_main_resource_manager_body").html($.render.weixin_autoResend_manager_table());
				$("#weixin_main_addNewResouse").attr("onclick","weixin_main_autoResend_addAutoReSend_window('"+addfunction+"','"+type+"')");
				$("#weixin_autoResendDate_manager_table_tbody").html($.render.weixin_autoResend_manager_table_tr(msg.data));
				$('input[type="checkbox"],[type="radio"]').not('#create-switch').bootstrapSwitch();
				$('.switch').on("switchChange",function(e,data){
					var value=data.value;
					var id=$(data.el).val();
					var ii=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"weixin/updateUse",
						data:{
							id:id,
							value:value
						},
						success : function(msg) {
							layer.close(ii);
							if(msg.success){
								
							}else{
								alertError(msg.info,"weixin_game_alert");
							}
						}
					})
				})
			}else{
					alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_main_autoResend_addAutoReSend_window(type,querytype){
	$("#weixin_main_ResendManage_add_window").modal("show");
	if(querytype=="event"){
		$("#input_weixin_main_resend_Text_event").attr("style","");
	}else{
		$("#input_weixin_main_resend_Text_event").attr("style","display: none;");
	}
	$("#weixin_main_resend_Text_name").val(""); 
	$("#weixin_main_resend_Text_content").val(""); 
	$("#weixin_main_resend_add_window_enter").attr("onclick","weixin_main_autoResend_addAutoReSend_Image_enter('"+type+"','"+querytype+"')");
}

function weixin_main_autoResend_addAutoReSend_Image_enter(addfunction,querytype){
	var name=$("#weixin_main_resend_Text_name").val(); 
	var content=$("#weixin_main_resend_Text_content").val(); 
	var event="0";
	if(querytype=="event"){
		event=$("#weixin_main_resend_Text_event").val();
		if(event==""){
			ui.alert("请选择事件类型，如菜单点击事件等。。",3000,false);
			return "";
		}
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+addfunction,
		data:{
			name:name,
			content:content,
			event:event
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResendManage_add_window").modal("hide");
				weixin_main_ResendManage(querytype);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResendManage_add_window").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_main_resend_Text_");
				}
			}
		}
	})
}
//删除自动回复
function weixin_main_Resend_delete(id){
	
	var id2=$("#weixin_s_id").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/deleteWeiXinInfo",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			weixin_main_ResendManage(weixin_main_querytype);
			ui.alert(msg.info,3000,false);
		}
	})
}
function weixin_main_ResendManage_add_addItem_window(id){
	
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getAutoReSendItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_main_resend_addResendItem_tbody").html($.render.weixin_main_ResendManage_add_addItem_window_tbody_tr(msg.data));
				$("#weixin_main_add_newResendItem").attr("onclick","weixin_main_add_newResendItem("+id+")");
				$("#weixin_main_add_reflash").attr("onclick","weixin_main_ResendManage_add_addItem_window("+id+")");
				$("#weixin_main_select_resendDate_name").val("");
				$("#weixin_main_select_resendDate_id").val("");
				$("#weixin_main_ResendManage_add_addItem_window").modal("show");
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_main_select_resendDate(){
	$("#weixin_main_ResendManage_add_addItem_queryItem_window").modal("show");
	$("#weixin_select_item_info_list li").each(function(){
		$(this).bind("click",function(){
			$("#weixin_select_item_info_list li").each(function(){
				$(this).attr("class","");
			})
			$(this).attr("class","active");
		})
	})
}
function weixin_main_addItem_query(type){
	var url="";
	if(type=='image'){
		url="getImage";
	}
	if(type=='voice'){
		url="getVoice";
	}
	if(type=='video'){
		url="getVideo";
	}
	if(type=='text'){
		url="getText";
	}
	if(type=='music'){
		url="getMusic";
	}
	if(type=='imageText'){
		url="getImageText";
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/"+url,
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var tmpl = $("#weixin_main_ResendManage_add_addItem_queryItem_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
				$.templates({
					weixin_main_ResendManage_add_addItem_queryItem_window_tbody_tr : tmpl
				});
				$("#weixin_main_ResendManage_add_addItem_queryItem_window_tbody").html($.render.weixin_main_ResendManage_add_addItem_queryItem_window_tbody_tr(msg.data));
			}else{
				alertError(msg.info,"weixin_game_alert");
			}
		}
    })
}
function weixin_main_select_resendDate_enter(name,id){
	$("#weixin_main_select_resendDate_name").val(name);
	$("#weixin_main_select_resendDate_id").val(id);
	$("#weixin_main_ResendManage_add_addItem_queryItem_window").modal("hide");
}
function weixin_main_add_newResendItem(id){
	var name=$("#weixin_main_select_resendDate_name").val();
	var id2=$("#weixin_main_select_resendDate_id").val();
	if(name.trim()==""){
		ui.alert("错误,请先选择您要添加的资源",2000,false);
		return;
	}
	if(id2.trim()==""||id2==0){
		ui.alert("错误,请先选择您要添加的资源",2000,false);
		return;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/addWeiXinInfoToAutoResend",
		data:{
			id:id,
			info_name:name,
			info_id:id2
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_main_ResendManage_add_addItem_window(id);
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function weixin_main_ResendManage_add_addItem_window_tbody_tr_delete(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/deleteWeiXinInfoToAutoResend",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_main_add_reflash").click();
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function weixin_info_bindEvent(){
	$("#weixin_info_list li").each(function(){
		$(this).bind("click",function(){
			$("#weixin_info_list li").each(function(){
				$(this).attr("class","");
			})
			$(this).attr("class","active");
		})
	})
}
function weixin_message_manager(id){
	$("#weixin_weixin_layout").html($.render.weixin_message_manager());
}
function weixin_message_manager_add_but(){

	$("#weixin_message_manager_add_but").modal("show");
	$("#weixin_message_manager_but_enter").attr("onclick","weixin_message_manager_add_but_enter()");
	if(weixin_wenzhang_edit==undefined){
		weixin_wenzhang_edit = KindEditor.create('#weixin_Text_wenzhangcontent', {
			uploadJson : ctx+'fileSrc/uploadImage',
			fileManagerJson : ctx+'fileSrc/getImageList',
			allowFileManager : true
		});   //正确
	}
	weixin_wenzhang_edit.html("");
	$("#weixin_Text_wenzhangname").val("");
	$('#Vote_open_closs_vip').bootstrapSwitch('state',true);
	$('#Vote_open_closs_more').bootstrapSwitch('state',true);
	$("#wenzhang_vote_body").html("");
	deleteWenzhangVote();
	
	/*$('#open_score_dazhuanpan').on("switchChange",function(e,data){
		var value=data.value;
		if(value){
			$("#weixin_dazhuanpan_score").removeAttr("disabled");
		}else{
			$("#weixin_dazhuanpan_score").attr("disabled","disabled");
		}
		var ii=$.layer({ 
		    type : 3
		});
		$.ajax({
			type : "POST",
			url : ctx+"gameController/updateGameDaZhuanPan",
			data:{
				value:value,
				type:2
			},
			success : function(msg) {
				layer.close(ii);
			}
		})
	})*/
}
function addWenzhangVote(){
	$("#wenzhangVoteDiv").show();
	$('#weixin_wenzhang_vote_start_date_g').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#weixin_wenzhang_vote_end_date_g').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd',
	});
}
function deleteWenzhangVote(){
	$("#wenzhangVoteDiv").hide();
}
function addWenzhangVoteItem(){
	weixin_main_vote_item_num=weixin_main_vote_item_num+1;
	$("#wenzhang_vote_body").append($.render.weixin_wenzhang_Vote_tr({"num":weixin_main_vote_item_num,"name":"","nums":0,"id":0}));
}
function deleteWenzhangVoteItem(num){
	$("#wenzhang_vote_tr_"+num).remove();
}
function weixin_message_manager_update_but(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getWenzhang",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_message_manager_add_but").modal("show");
				$("#wenzhang_vote_body").html("");
				deleteWenzhangVote();
				$("#weixin_message_manager_but_enter").attr("onclick","weixin_message_manager_update_but_enter("+id+")");
				if(weixin_wenzhang_edit==undefined){
					weixin_wenzhang_edit = KindEditor.create('#weixin_Text_wenzhangcontent', { 
						uploadJson : ctx+'fileSrc/uploadImage',
						fileManagerJson : ctx+'fileSrc/getImageList',
						
						allowFileManager : true      });   //正确
				}
				weixin_wenzhang_edit.html(msg.obj.content);
				$("#weixin_Text_wenzhangname").val(msg.obj.name);
				KindEditor.remove('#weixin_Text_wenzhangname');
				if(msg.obj.vote){
					addWenzhangVote();
					$("#lookVoteUser").attr("onclick","lookVoteLinkMan("+msg.obj2.id+")");
					if(msg.obj2.stardate!=null){
						$("#weixin_wenzhang_vote_start_date").val(JavaSTojsDate(msg.obj2.stardate));
					}
					if(msg.obj2.endDate!=null){
						$("#weixin_wenzhang_vote_end_date").val(JavaSTojsDate(msg.obj2.endDate));
					}
					if(msg.obj2.publics){
						$('#Vote_open_closs_vip').bootstrapSwitch('state',true);
					}else{
						$('#Vote_open_closs_vip').bootstrapSwitch('state',false);
					}
					if(msg.obj2.ones){
						$('#Vote_open_closs_more').bootstrapSwitch('state',true);
					}else{
						$('#Vote_open_closs_more').bootstrapSwitch('state',false);
					}
					weixin_main_vote_item_num=0;
					for(var i2=0;i2<msg.obj2.voteItem.length;i2++){
						$("#wenzhang_vote_body").append($.render.weixin_wenzhang_Vote_tr({"num":weixin_main_vote_item_num,"name":msg.obj2.voteItem[i2].name,"nums":msg.obj2.voteItem[i2].num,"id":msg.obj2.voteItem[i2].id}));
						weixin_main_vote_item_num++;
					}
				}
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function lookVoteLinkMan(id,page){
	if(page!=undefined){
		weixin_wenzhang_vote_linkman_page=page;
	}
	var tmpl = $("#wenzhang_vote_linkMan_body_tr").html().replace("<!--", "").replace("-->", "");
	$.templates({
		wenzhang_vote_linkMan_body_tr : tmpl
	});
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getVoteUserList",
		data:{
			name:name,
			nowpage:weixin_wenzhang_vote_linkman_page,
			countNum:10,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_message_manager_look_toupiaoRen").modal("show");
				$("#wenzhang_vote_linkMan_body").html($.render.wenzhang_vote_linkMan_body_tr(msg.data));
				var page_html="<li><a href=\"javascript:lookVoteLinkMan("+id+",'"+(parseInt(weixin_wenzhang_vote_linkman_page)-1)+"')\">«</a></li>";
				var startPage=weixin_wenzhang_vote_linkman_page>15?weixin_wenzhang_vote_linkman_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=weixin_wenzhang_vote_linkman_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" ><a href=\"javascript:lookVoteLinkMan("+id+"'"+ii+"')\"'>"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:lookVoteLinkMan("+id+",'"+(parseInt(weixin_wenzhang_vote_linkman_page)+1)+"')\">»</a></li>"
				$("#wenzhang_vote_linkMan_body_page").html(page_html);
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function weixin_message_manager_update_but_enter(id){
	var name=$("#weixin_Text_wenzhangname").val();
    var content=weixin_wenzhang_edit.html();
    var strjson="1";
    if(!$("#wenzhangVoteDiv").is(":hidden")){
 	  
 	    var vip = $('#Vote_open_closs_vip').bootstrapSwitch('state');
 	    var more=$('#Vote_open_closs_more').bootstrapSwitch('state');
 	    var startDate=$("#weixin_wenzhang_vote_start_date").val();
 	    var end=$("#weixin_wenzhang_vote_end_date").val();
 	   strjson="";
 	   strjson="[{'id':'','publics':'"+vip+"','ones':'"+more+"','startDate':'"+startDate+"','endDate':'"+end+"','voteItem':[";
 	   var inputList=$("#wenzhang_vote_body input");
 	   for(var i2=0;i2<inputList.length;i2++){
 		   var ids=$("#"+$(inputList[i2]).attr("id")+"_id").html();
 		   if(i2==0){
 			   strjson=strjson+"{'id':'"+ids+"',name:'"+$(inputList[i2]).val()+"'}";
 		   }else{
 			   strjson=strjson+",{'id':'"+ids+"',name:'"+$(inputList[i2]).val()+"'}";
 		   }
 	   }
 	   strjson=strjson+"]}]";
    }
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/updateWenzhang",
		data:{
			id:id,
			wenzhangname:name,
			wenzhangcontent:content,
			strjson:strjson
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_wenzhang_edit.html("");
				$("#weixin_Text_wenzhangname").val("");
				$("#weixin_message_manager_add_but").modal("hide");
				weixin_message_manager_query_but();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"weixin_Text_");
				}
			}
		}
    })
	
}
function weixin_message_manager_query_but(page,type){
	if(page!=undefined){
		weixin_wenzhang_page=page;
	}
	var name;
	if(type=='select'){
		name=$("#weixin_wenzhang_name_select").val();
	}else{
		name=$("#weixin_wenzhang_name").val();
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getWenzhangList",
		data:{
			name:name,
			nowpage:weixin_wenzhang_page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(type=='select'){
					$("#weixin_wenzhang_tbody_select").html($.render.weixin_wenzhang_tr_select(msg.data));
				}else{
					$("#weixin_wenzhang_tbody").html($.render.weixin_wenzhang_tr(msg.data));
				}
				var page_html="<li><a href=\"javascript:weixin_message_manager_query_but('"+(parseInt(weixin_wenzhang_vote_linkman_page)-1)+"','"+type+"')\">«</a></li>";
				var startPage=weixin_wenzhang_vote_linkman_page>15?weixin_wenzhang_vote_linkman_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=weixin_wenzhang_vote_linkman_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" ><a href=\"javascript:weixin_message_manager_query_but('"+ii+"','"+type+"')\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:weixin_message_manager_query_but('"+(parseInt(weixin_wenzhang_vote_linkman_page)+1)+"','"+type+"')\">»</a></li>"
				if(type=='select'){
					$("#weixin_wenzhang_tbody_page_select").html(page_html);
				}else{
					$("#weixin_wenzhang_tbody_page").html(page_html);
				}
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
	
}
function weixin_message_manager_add_but_enter(){
	var name=$("#weixin_Text_wenzhangname").val();
    var content=weixin_wenzhang_edit.html();

    var strjson="1";
   if(!$("#wenzhangVoteDiv").is(":hidden")){
	  
	    var vip = $('#Vote_open_closs_vip').bootstrapSwitch('state');
	    var more=$('#Vote_open_closs_more').bootstrapSwitch('state');
	    var startDate=$("#weixin_wenzhang_vote_start_date").val();
	    var end=$("#weixin_wenzhang_vote_end_date").val();
	   strjson="";
	   strjson="[{'id':'','publics':'"+vip+"','ones':'"+more+"','startDate':'"+startDate+"','endDate':'"+end+"','voteItem':[";
	   var inputList=$("#wenzhang_vote_body input");
	   for(var i=0;i<inputList.length;i++){
		   if(i==0){
			   strjson=strjson+"{'id':'',name:'"+$(inputList[i]).val()+"'}";
		   }else{
			   strjson=strjson+",{'id':'',name:'"+$(inputList[i]).val()+"'}";
		   }
	   }
	   strjson=strjson+"]}]";
   }
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/sendWenzhang",
		data:{
			wenzhangname:name,
			wenzhangcontent:content,
			strjson:strjson
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_wenzhang_edit.html("");
				$("#weixin_Text_wenzhangname").val("");
				$("#weixin_message_manager_add_but").modal("hide");
				weixin_message_manager_query_but();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"weixin_Text_");
				}
			}
		}
    })
	
}
function weixin_message_manager_delete_but_enter(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/deleteWenzhang",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_message_manager_query_but();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					ui.alert(msg.info,2000,false);
				}else{
					inputAjaxTest(list,"weixin_Text_");
				}
			}
		}
    })
	
}

function weixin_main_select_wenzhang_show(id){
	$("#weixin_main_wenzhang_select").modal("show");
	weixin_main_select_wenzhang_input=id;
}
function weixin_main_select_wenzhang_enter(linkadd){
	$("#"+weixin_main_select_wenzhang_input).val(linkadd);
	$("#weixin_main_wenzhang_select").modal("hide");
}
function weixin_main_autoResend_addAutoReSend_update_window(type,querytype,id){
	var addfunction="";
	switch (type) {
	case 1:
		addfunction="updateAutoReSend_Text";
		break;
	case 2:
		addfunction="updateAutoReSend_Image";
		break;
	case 3:
		addfunction="updateAutoReSend_Voice";
		break;
	case 4:
		addfunction="updateAutoReSend_Video";
		break;
	case 5:
		addfunction="updateAutoReSend_Location";
		break;
	case 6:
		addfunction="updateAutoReSend_Link";
		break;
	case 7:
		addfunction="updateAutoReSend_Event";
		querytype="event";
		break;
	default:
		ui.alert("类别错误，不能修改只能删除",false);
		return ;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/getAutoReSend",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_main_ResendManage_add_window").modal("show");
				if(msg.obj.type==7){
					$("#input_weixin_main_resend_Text_event").attr("style","");
					$("#weixin_main_resend_Text_event_"+msg.obj.weixin_events).attr("selected",true);
				}else{
					$("#input_weixin_main_resend_Text_event").attr("style","display: none;");
				}
				$("#weixin_main_resend_Text_name").val(msg.obj.name); 
				$("#weixin_main_resend_Text_content").val(msg.obj.content);
				$("#weixin_main_resend_add_window_enter").attr("onclick","weixin_main_autoResend_addAutoReSend_Image_update_enter('"+addfunction+"','"+querytype+"','"+id+"')");
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
	
}

function weixin_main_autoResend_addAutoReSend_Image_update_enter(addfunction,querytype,id){
	var name=$("#weixin_main_resend_Text_name").val(); 
	var content=$("#weixin_main_resend_Text_content").val(); 
	var event="0";
	if(querytype=="event"){
		event=$("#weixin_main_resend_Text_event").val();
		if(event==""){
			ui.alert("请选择事件类型，如菜单点击事件等。。",3000,false);
			return "";
		}
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/"+addfunction,
		data:{
			name:name,
			content:content,
			event:event,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_main_ResendManage_add_window").modal("hide");
				weixin_main_ResendManage(querytype);
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_game_alert");
					$("#weixin_main_ResendManage_add_window").modal("hide");
				}else{
					inputAjaxTest(list,"weixin_main_resend_Text_");
				}
			}
		}
	})
}