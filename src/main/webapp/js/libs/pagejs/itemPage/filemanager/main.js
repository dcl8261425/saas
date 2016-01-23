var fileManager_window_return_function;
var fileManager_window_type;
var fileManager_window_type_nowpage;
$(function () {
	$(document).ready(function(){
		initTemp();
		initDiv();
		fileManager_window_type_nowpage=1;
	})
	function initTemp(){
		
	}
	function initDiv(){
		
	}
})
function fileManager_window_selectType(type){
	fileManager_window_type=type;
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"fileManager/getList",
		data:{
			type:type,
			nowpage:fileManager_window_type_nowpage,
			countNum:40
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var contentStr="";
				if(type=='img'){
					
					for(var ii=0;ii<msg.data.length;ii++){
						var imageidNum=Math.floor(Math.random()*100);
						var size;
						var m=parseInt(parseInt(msg.data[ii].filesize)/1024/1024);
						if(m>0){
							size=m+"m";
						}else{
							size=parseInt(parseInt(msg.data[ii].filesize)/1024)+"kb";
						}
						
						contentStr=contentStr+'<div class="col-md-3" id="img_'+imageidNum+'">'+
		           		'<div class="row">'+
		               		'<div class="col-md-12" onclick="fileManager_window_return_url(\''+msg.data[ii].wwwLinkAddress+'\',\''+msg.data[ii].fileName+'\')" style="cursor: pointer;">'+
		               		'<img src="'+msg.data[ii].wwwLinkAddress+'" width="60px" height="60px"/>'+
		               		'</div>'+
		               		'<div class="col-md-12" >'+
		               		msg.data[ii].fileName+
		               		'</div>'+
		               		'<div class="col-md-12">'+
		               		size+
		               		'</div>'+
		               		'<div class="col-md-12" onclick="fileManager_window_deleteFile(\''+msg.data[ii].id+'\',\'img_'+imageidNum+'\')" style="cursor: pointer;">'+
		               			"删除"+
		               		'</div>'+
		           		'</div>'+
		           	'</div>';
					}
				}
				if(type=='file'){
					for(var ii=0;ii<msg.data.length;ii++){
						var imageidNum=Math.floor(Math.random()*100);
						var size;
						var m=parseInt(parseInt(msg.data[ii].filesize)/1024/1024);
						if(m>0){
							size=m+"m";
						}else{
							size=parseInt(parseInt(msg.data[ii].filesize)/1024)+"kb";
						}
						contentStr=contentStr+'<div class="col-md-3" id="img_'+imageidNum+'">'+
		           		'<div class="row" >'+
		               		
		               		'<div class="col-md-12" onclick="fileManager_window_return_url(\''+msg.data[ii].wwwLinkAddress+'\',\''+msg.data[ii].fileName+'\')" style="cursor: pointer;">'+
		               		msg.data[ii].fileName+
		               		'</div>'+
		               		'<div class="col-md-12">'+
		               		size+
		               		'</div>'+
		               		'<div class="col-md-12" onclick="fileManager_window_deleteFile(\''+msg.data[ii].id+'\',\'img_'+imageidNum+'\')" style="cursor: pointer;">'+
	               			"删除"+
	               			'</div>'+
		           		'</div>'+
		           	'</div>';
					}
				}
				if(type=='video'){
					for(var ii=0;ii<msg.data.length;ii++){
						var imageidNum=Math.floor(Math.random()*100);
						var size;
						var m=parseInt(parseInt(msg.data[ii].filesize)/1024/1024);
						if(m>0){
							size=m+"m";
						}else{
							size=parseInt(parseInt(msg.data[ii].filesize)/1024)+"kb";
						}
						contentStr=contentStr+'<div class="col-md-3" id="img_'+imageidNum+'">'+
		           		'<div class="row">'+
		               		'<div class="col-md-12"  onclick="fileManager_window_return_url(\''+msg.data[ii].wwwLinkAddress+'\',\''+msg.data[ii].fileName+'\')" style="cursor: pointer;">'+
		               		msg.data[ii].fileName+
		               		'</div>'+
		               		'<div class="col-md-12">'+
		               		size+
		               		'</div>'+
		               		'<div class="col-md-12" onclick="fileManager_window_deleteFile(\''+msg.data[ii].id+'\',\'img_'+imageidNum+'\')" style="cursor: pointer;">'+
	               			"删除"+
	               			'</div>'+
		           		'</div>'+
		           	'</div>';
					}
				}
				$("#fileManager_showImage").html(contentStr);
			
			}else{
				alertError(msg.info,"fileManager_alert");
			}
		}
    })
}
function fileManager_uploadFile_delete(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"fileManager/getList",
		data:{
			type:type,
			nowpage:fileManager_window_type_nowpage,
			countNum:40
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
			}else{
				alertError(msg.info,"fileManager_alert");
			}
		}
	})
}
function fileManager_uploadFile_upload(){
	$("#fileManager_uploadFile").click();
}
function fileManager_uploadFile_change(){
	var url="";
	if(fileManager_window_type=='file'){
		url=ctx+'fileSrc/uploadFile';
	}else if(fileManager_window_type=='img'){
		url=ctx+'fileSrc/uploadImage_kongjian';
	}else if(fileManager_window_type=='video'){
		url=ctx+'fileSrc/uploadVideo';
	}else{
		alertError("请先选择类型","fileManager_alert");
		return ;
	}
	
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:url,
			fileElementId:'fileManager_uploadFile',
			success: function (data)
			{
				
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					
					alertSuccess("上传成功","fileManager_alert");
					$('#fileManager_window').modal("hide");
					fileManager_window_return_function.call(json.url,json.url,json.filename);
					
				}else{
					alertError(json.message,"fileManager_alert");
				}
			}
		}
	)
}
function fileManager_window_deleteFile(id,elementId){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"fileManager/deleteFile",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"fileManager_alert");
				$("#"+elementId).remove();
			}else{
				alertError(msg.info,"fileManager_alert");
			}
		}
	})
}
function fileManager_window_return_url(url,fileName){
	fileManager_window_return_function.call(url,url,fileName);
	$('#fileManager_window').modal("hide");
}