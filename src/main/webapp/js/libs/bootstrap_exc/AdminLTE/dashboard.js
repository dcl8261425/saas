/**
 * 提示成功
 * @param message
 */
function alertSuccess(message,elementid){
	var html="<div class=\"alert alert-success alert-dismissable no-print\"><i class=\"fa fa-check\"></i><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button> <b>成功：</b>"+message+"</div>"
	$("#"+elementid).html(html+$("#"+elementid).html());
}
/**
 * 输入栏提示成功
 * @param message
 * @param elementid
 */
function inputSuccess(message,elementid){
	var html="<label id=\""+elementid+"_info\" class=\"control-label no-print\" for=\"inputSuccess\"><i class=\"fa fa-check\"></i>"+message+"</label>"
	$("#"+elementid).attr("class","form-group has-success");
	if($("#"+elementid+"_info")!=null||$("#"+elementid+"_info")!=undefined){
		$("#"+elementid+"_info").remove();
	}
	$("#"+elementid).prepend(html);
}
/**
 * 输入栏提示错误
 * @param message
 * @param elementid
 */
function inputError(message,elementid){
	var html="<label id=\""+elementid+"_info\" class=\"control-label no-print\" for=\"inputError\"><i class=\"fa fa-times-circle-o\"></i>"+message+"</label>"
	$("#"+elementid).attr("class","form-group has-error");
	if($("#"+elementid+"_info")!=null||$("#"+elementid+"_info")!=undefined){
		$("#"+elementid+"_info").remove();
	}
	$("#"+elementid).prepend(html);
}
/**
 * 输入栏提示警告
 * @param message
 * @param elementid
 */
function inputWarning(message,elementid){
	var html="<label id=\""+elementid+"_info\" class=\"control-label no-print\" for=\"inputWarning\"><i class=\"fa fa-warning\"></i>"+message+"</label>"
	$("#"+elementid).attr("class","form-group has-warning");
	if($("#"+elementid+"_info")!=null||$("#"+elementid+"_info")!=undefined){
		$("#"+elementid+"_info").remove();
	}
	$("#"+elementid).prepend(html);
}
/**
 * 提示警告
 * @param message
 */
function alertWarning(message,elementid){
	var html="<div class=\"alert alert-warning alert-dismissable no-print\"><i class=\"fa fa-warning\"></i><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button> <b>提示：</b>"+message+"</div>"
	$("#"+elementid).html(html+$("#"+elementid).html());
}
/**
 * 提示错误
 * @param message
 */
function alertError(message,elementid){
	var html="<div class=\"alert alert-danger alert-dismissable no-print\"><i class=\"fa fa-ban\"></i><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button> <b>错误：</b>"+message+"</div>"
	$("#"+elementid).html(html+$("#"+elementid).html());
}
/**
 * 提示注意信息
 * @param message
 */
function alertInfo(message,elementid){
	var html="<div class=\"alert alert-info alert-dismissable no-print\"><i class=\"fa fa-info\"></i><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button> <b>提示：</b>"+message+"</div>"
	$("#"+elementid).html(html+$("#"+elementid).html());
}
/**
 * 向主显示区域刷新html
 * @param message
 */
function layoutRefresh(htmlcontent){
	$("#html_content").html(htmlcontent);
}
/**
 * 添加正在加载层
 * @param message
 */
function addLoadData_div(elementIds){
	var html="<div class=\"overlay\"></div><div class=\"loading-img\"></div>"
	$("#"+elementIds).append(html);
}
/**
 * 移出正在加载层
 * @param elementIds
 */
function removeloadData_div(elementIds){
	$("#"+elementIds+" > .overlay").remove();
	$("#"+elementIds+"> .loading-img").remove();
}                 
/**
 * 表单检测，并显示出提示
 */
function inputAjaxTest(list,prefix){
	for(i=0;i<list.length;i++){
		var name=list[i].name;
		var info=list[i].info;
		var b=list[i].success;
		if($("#"+prefix+name)!=null||$("#"+prefix+name)!=undefined){
			var elementid=$("#"+prefix+name).parent().attr("id");
			if(b){
				inputSuccess(info,elementid);
			}else{
				inputError(info,elementid);
			}
		}
	}
}
/**
 * 渲染功能项展示第1层时使用
 * @param htmlcontent
 */
function functionLayoutRefresh(htmlcontent){
	$("#function_page").html(htmlcontent);
}
/**
 * 渲染功能项2展示第2层时使用
 * @param htmlcontent
 */
function functionLayoutRefresh2(htmlcontent){
	$("#function_page2").html(htmlcontent);
}
/**
 * 渲染功能项3 展示第三层时使用
 * @param htmlcontent
 */
function functionLayoutRefresh3(htmlcontent){
	$("#function_page2").html(htmlcontent);
}
/**
 * 渲染功能项4 展示第4层时使用
 * @param htmlcontent
 */
function functionLayoutRefresh4(htmlcontent){
	$("#function_page4").html(htmlcontent);
}
/**
 * 添加预览块 左
 */
function  add_window_layout_right_1(url){
	var i2=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+url,
		data:{
		},
		success : function(msg) {
			layer.close(i2);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#right_layout").append(msg);
			}else{
				alertError(msg.info,"content");
			}
			
		}
    })
}
function add_window_layout_right(url,mainUrl){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"LayoutWindow/add",
		data:{
			url:url,
			right:1,
			left:0,
			mainUrl:mainUrl
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				add_window_layout_right_1(url);
				$("#window_list_layout").modal("toggle");
			}else{
				alertError(msg.info,"content");
				$("#window_list_layout").modal("toggle");
			}

		}
	})
	
}

/**
 * 添加预览块 右
 */
function add_window_layout_left_1(url){
	var i2=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+url,
		data:{
		},
		success : function(msg) {
			layer.close(i2);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#left_layout").append(msg);
			}else{
				alertError(msg.info,"content");
			}
			

		}
    })
}
function add_window_layout_left(url,mainUrl){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"LayoutWindow/add",
		data:{
			url:url,
			right:0,
			left:1,
			mainUrl:mainUrl
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				add_window_layout_left_1(url);
				$("#window_list_layout").modal("toggle");
			}else{
				alertError(msg.info,"content");
				$("#window_list_layout").modal("toggle");
			}
		}
	})
	
}
/**
 * 读出概览块列表并弹出框
 * @param url
 */
function window_list_show(url){
	var tmpl = $("#window_list_layout_tr").html().replace("<!--", "").replace("-->", "");
	$.templates({
		window_list_layout_tr : tmpl
	});	
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+url,
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#window_list_layout").modal("toggle");
				$("#window_list_layout_tbody").html($.render.window_list_layout_tr(msg.data));
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
function window_layout_close(obj,url){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"LayoutWindow/delete",
		data:{
			url:url
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$(obj).parent().parent().parent().hide(1000,function(){
					$(obj).parent().parent().parent().html("");
				});
				
			}else{
				alertError(msg.info,"content");
			}
		}
    })
	
}
/**
 * 读出用户的某个概览的全部已添加模块
 * @param url
 */
function window_layout_query(mainUrl){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"LayoutWindow/query",
		data:{
			mainUrl:mainUrl
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var data=msg.data;
				for(var ii=0;ii<data.length;ii++){
					if(data[ii].lefts){
						add_window_layout_left_1(data[ii].urls);
					}else{
						add_window_layout_right_1(data[ii].urls);
					}
				}
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
/**
 * 格式化当前时间
 * @returns {String}
 */
function getNowFormatDate(b) 
{ 
var day = new Date(); 
var Year = 0; 
var Month = 0; 
var Day = 0; 
var CurrentDate = ""; 
//初始化时间 
//Year= day.getYear();//有火狐下2008年显示108的bug 
Year= day.getFullYear();//ie火狐下都可以 
Month= day.getMonth()+1; 
if(b){
	Day = day.getDate()-day.getDate()+1;
}else{
	Day = day.getDate();
}
//Hour = day.getHours(); 
// Minute = day.getMinutes(); 
// Second = day.getSeconds(); 
CurrentDate += Year + "-"; 
if (Month >= 10 ) 
{ 
CurrentDate += Month + "-"; 
} 
else 
{ 
CurrentDate += "0" + Month + "-"; 
} 
if (Day >= 10 ) 
{ 
CurrentDate += Day ; 
} 
else 
{ 
CurrentDate += "0" + Day ; 
} 
return CurrentDate; 
} 
function fileManager_window(functionName,type){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"fileManager/main",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh4(msg);
				fileManager_window_return_function=functionName;
				fileManager_window_type=type;
				$('#fileManager_window').modal("show");
				//三个按钮，如果不为''的话则只显示被选择的类别
				if(fileManager_window_type=='file'){
					$("#fileManager_window_selectType_button_img").hide();
					$("#fileManager_window_selectType_button_file").show();
					$("#fileManager_window_selectType_button_file").addClass("active");
					
					$("#fileManager_window_selectType_button_video").hide();
				}
				if(fileManager_window_type=='img'){
					$("#fileManager_window_selectType_button_img").show();
					$("#fileManager_window_selectType_button_img").addClass("active");
					$("#fileManager_window_selectType_button_file").hide();
					$("#fileManager_window_selectType_button_video").hide();
				}
				if(fileManager_window_type=='video'){
					$("#fileManager_window_selectType_button_img").hide();
					$("#fileManager_window_selectType_button_file").hide();
					$("#fileManager_window_selectType_button_video").show();
					$("#fileManager_window_selectType_button_video").addClass("active");
				}
				if(fileManager_window_type==''){
					$("#fileManager_window_selectType_button_file").addClass("active");
					fileManager_window_type="file";
				}
				fileManager_window_selectType(fileManager_window_type);
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
