var kongjian_groupid=0;
var kongjian_send_window_type;
var kongjian_send_window_reSend;
var kongjian_send_window_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		
	})
	function initTemp(){
		var tmpl = $("#kongjian_group_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			kongjian_group_item : tmpl
		});
		var tmpl = $("#kongjian_content_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			kongjian_content_item : tmpl
		});
		var tmpl = $("#kongjian_huifu_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			kongjian_huifu_item : tmpl
		});
	}
	function initbindEvent(){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"hr/function/getHuDongKongJian",
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#groupDiv").html($.render.kongjian_group_item(msg.data));
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
	}
})
function kongjian_clickGroup(groupid){
	if(groupid==0||groupid==undefined||groupid==null){
		return ;
	}
	if(kongjian_groupid!=groupid&&groupid!=""){
		
		kongjian_send_window_page=1;
		$("#kongjian_content").html("");
	}else{
		kongjian_send_window_page++;
	}
	if(groupid!=undefined&&groupid!=null){
		kongjian_groupid=groupid
	}
	$("#kongjian_load_mover").html("正在加载。。。。。。");
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"hr/function/getHuDongKongJian",
			data:{
				groupId:kongjian_groupid,
				nowpage:kongjian_send_window_page
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length<8){
						$("#kongjian_load_mover").html("已经到头");
					}else{
						$("#kongjian_load_mover").html("加载更多");
					}
					$("#kongjian_content").append($.render.kongjian_content_item(msg.data));
					//选项卡滑动切换通用
					jQuery(function(){jQuery(".hoverTag .chgBtn").hover(function(){jQuery(this).parent().find(".chgBtn").removeClass("chgCutBtn");jQuery(this).addClass("chgCutBtn");var cutNum=jQuery(this).parent().find(".chgBtn").index(this);jQuery(this).parents(".hoverTag").find(".chgCon").hide();jQuery(this).parents(".hoverTag").find(".chgCon").eq(cutNum).show();})})

					//选项卡点击切换通用
					jQuery(function(){jQuery(".clickTag .chgBtn").click(function(){jQuery(this).parent().find(".chgBtn").removeClass("chgCutBtn");jQuery(this).addClass("chgCutBtn");var cutNum=jQuery(this).parent().find(".chgBtn").index(this);jQuery(this).parents(".clickTag").find(".chgCon").hide();jQuery(this).parents(".clickTag").find(".chgCon").eq(cutNum).show();})})
					
					//图库弹出层
					$(".mskeLayBg").height($(document).height());
					$(".mskeClaose").click(function(){$(".mskeLayBg,.mskelayBox").hide()});
					$(".msKeimgBox li").click(function(){$(".mske_html").html($(this).find(".hidden").html());$(".mskeLayBg").show();$(".mskelayBox").fadeIn(300)});
					$(".mskeTogBtn").click(function(){$(".msKeimgBox").toggleClass("msKeimgBox2");$(this).toggleClass("mskeTogBtn2")});
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
}
function kongjian_onclick_showItem(type,id){
	var typestr="";
	if(type==1){
		typestr="toupiao";
	}
	if(type==2){
		typestr="liuyan";
	}
	if(type==3){
		typestr="image";
	}
	if(type==4){
		typestr="shipin";
		   

	}
	if(type==5){
		typestr="file";
		   

	}
	if($("#kongjian_"+typestr+"_"+id).is(":visible")){
		$("#kongjian_"+typestr+"_"+id).hide();
	}else{
		$("#kongjian_"+typestr+"_"+id).show();
		
	}
}
function kongjian_sendMessage_window_show(type,id){
	if(kongjian_groupid==0){
		alertError("请先选择组","alertDiv");
		return "";
	}
	$('#kongjian_toupiao_endDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#kongjian_toupiao_startDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd',
	});
	    var more=$('#kongjian_toupiao_move').bootstrapSwitch('state');
	$("#kongjian_sendMessage").modal("show");
	$("#kongjian_send_image_content").html("");
	$("#kongjian_send_file_content").html("");
	$("#kongjian_send_video_content").html("");
	$("#kongjian_send_ToupiaoItem_content").html("");
	if(type=='1'){
		$("#kongjian_send_title_text").show();
		$("#hr_komgjian_send_toupiao").show();
		kongjian_send_window_type='1';
	}else if(type=='2'){
		$("#kongjian_send_title_text").hide();
		$("#hr_komgjian_send_toupiao").hide();
		kongjian_send_window_type='2';
		kongjian_send_window_reSend=id;
	}
}
function kongjian_send_updateimage(){
	$("#kongjian_send_image_upload_input").click();
}
function kongjian_send_image_upload_input_change(){
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'fileSrc/uploadImage_kongjian',
			fileElementId:'kongjian_send_image_upload_input',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					var imageidNum=Math.floor(Math.random()*100);
					$("#kongjian_send_image_content").html($("#kongjian_send_image_content").html()+" <img id='image"+imageidNum+"'class='kongjian_image' src='"+json.url+"' width='100' height='100'/> <a id='image"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('image"+imageidNum+"')\"><small class='text-muted'>删除</small></a>")
				}else{
					alertError(json.message,"kongjian_sendMessage_alert");
				}
			}
		}
	)
}
function kongjian_send_updateimageAddress(){
	$("#kongjian_sendMessage_imageAddress_window").modal("show");
}
function kongjian_sendMessage_imageAddress_window_addImage(){
	var imageidNum=Math.floor(Math.random()*100);
	var url=$("#kongjian_sendMessage_imageAddress_window_addImage_url").val();
	$("#kongjian_send_image_content").html($("#kongjian_send_image_content").html()+" <img id='image"+imageidNum+"' class='kongjian_image' src='"+url+"' width='100' height='100'/> <a id='image"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('image"+imageidNum+"')\"><small class='text-muted'>删除</small></a>")
	$("#kongjian_sendMessage_imageAddress_window").modal("hide");
	$("#kongjian_sendMessage_imageAddress_window_addImage_url").val("");
}
function kongjian_send_lookimageAddress(){
	var kongjian_send_lookimageAddress_fileManager=function(url){
		var imageidNum=Math.floor(Math.random()*100);
		$("#kongjian_send_image_content").html($("#kongjian_send_image_content").html()+" <img id='image"+imageidNum+"' class='kongjian_image' src='"+url+"' width='100' height='100'/> <a id='image"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('image"+imageidNum+"')\"><small class='text-muted'>删除</small></a>")

	}
	fileManager_window(kongjian_send_lookimageAddress_fileManager,"img");
}
function kongjian_sendMessage_imageAddress_window_RemoveImage(id){
	$("#"+id).remove();
	$("#"+id+"deleteButton").remove();
}
function kongjian_send_updatefile(){
	$("#kongjian_send_file_upload_input").click();
}
function kongjian_send_file_upload_input_change(){
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'fileSrc/uploadFile',
			fileElementId:'kongjian_send_file_upload_input',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					var imageidNum=Math.floor(Math.random()*100);
					$("#kongjian_send_file_content").html($("#kongjian_send_file_content").html()+"<a id='file"+imageidNum+"' fileName='"+json.filename+"' class='kongjian_file' href='"+json.url+"'>"+json.url+"</a> <a id='file"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('file"+imageidNum+"')\"><small class='text-muted'>删除</small> <br/></a>")
				
				}else{
					alertError(json.message,"kongjian_sendMessage_alert");
				}
			}
		}
	)
}
function kongjian_send_updatefileAddress(){
	$("#kongjian_sendMessage_fileAddress_window").modal("show");
}
function kongjian_sendMessage_fileAddress_window_addFile(){
	var imageidNum=Math.floor(Math.random()*100);
	var url=$("#kongjian_sendMessage_fileAddress_window_addfile_url").val();
	$("#kongjian_send_file_content").html($("#kongjian_send_file_content").html()+"<a id='file"+imageidNum+"' fileName='"+url+"' class='kongjian_file' href='"+url+"'>"+url+"</a> <a id='file"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('file"+imageidNum+"')\"><small class='text-muted'>删除</small> <br/></a>")
	$("#kongjian_sendMessage_fileAddress_window").modal("hide");
	$("#kongjian_sendMessage_imageAddress_window_addfile_url").val("");
}
function kongjian_send_LookfileAddress(){
	var kongjian_send_LookfileAddress_fileManager=function(url,fileName){
		var imageidNum=Math.floor(Math.random()*100);
		$("#kongjian_send_file_content").html($("#kongjian_send_file_content").html()+"<a id='file"+imageidNum+"' class='kongjian_file' fileName='"+fileName+"' href='"+url+"'>"+url+"</a> <a id='file"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('file"+imageidNum+"')\"><small class='text-muted'>删除</small> <br/></a>")
	};
	fileManager_window(kongjian_send_LookfileAddress_fileManager,"file");
}
function kongjian_send_updateVideo(){
	$("#kongjian_send_video_upload_input").click();
}
function kongjian_send_video_upload_input_change(){
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'fileSrc/uploadVideo',
			fileElementId:'kongjian_send_video_upload_input',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					var imageidNum=Math.floor(Math.random()*100);
					$("#kongjian_send_video_content").html($("#kongjian_send_video_content").html()+ "<video id='video"+imageidNum+"' class='video-js vjs-default-skin' controls preload='none'  width='100%' height='100' width='100'  data-setup='{}'><source class='kongjian_video' src='"+json.url+"' type='video/mp4' /></video> <a id='video"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('video"+imageidNum+"')\"><small class='text-muted'>删除</small></a>")
				}else{
					alertError(json.message,"kongjian_sendMessage_alert");
				}
			}
		}
	)
}
function kongjian_send_updateVideoAddress(){
	$("#kongjian_sendMessage_videoAddress_window").modal("show");
}
function kongjian_sendMessage_videoAddress_window_addVideo(){
	var imageidNum=Math.floor(Math.random()*100);
	var url=$("#kongjian_sendMessage_videoAddress_window_addvideo_url").val();
	$("#kongjian_send_video_content").html($("#kongjian_send_video_content").html()+ "<video id='video"+imageidNum+"' class='video-js vjs-default-skin' controls preload='none'  width='100%' height='100' width='100' data-setup='{}'><source class='kongjian_video'  src='"+url+"' type='video/mp4' /></video> <a id='video"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('video"+imageidNum+"')\"><small class='text-muted'>删除</small></a>");
	$("#kongjian_sendMessage_videoAddress_window").modal("hide");
	$("#kongjian_sendMessage_imageAddress_window_addvideo_url").val("");
	
}
function kongjian_send_LookVideoAddress(){
	var kongjian_send_LookVideoAddress_fileManager=function(url){
		var imageidNum=Math.floor(Math.random()*100);
		$("#kongjian_send_video_content").html($("#kongjian_send_video_content").html()+ "<video id='video"+imageidNum+"' class='video-js vjs-default-skin' controls preload='none'  width='100%' height='100' width='100' poster='http://video-js.zencoder.com/oceans-clip.png' data-setup='{}'><source class='kongjian_video'  src='"+url+"' type='video/mp4' /></video> <a id='video"+imageidNum+"deleteButton' href=\"javascript:kongjian_sendMessage_imageAddress_window_RemoveImage('video"+imageidNum+"')\"><small class='text-muted'>删除</small></a>");

	}
	fileManager_window(kongjian_send_LookVideoAddress_fileManager,"video");
}
function kongjian_send_createItem(){
	var imageidNum=Math.floor(Math.random()*100);
	var url=$("#kongjian_sendMessage_ToupiaoItem_window_input_name").val();
	var str='<div class="col-md-4" id="kongjian_toupiao_item'+imageidNum+'">'+
	'<div class="alert alert-success alert-dismissable">'+
        '<button type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="kongjian_toupiao_item_delete(\'kongjian_toupiao_item'+imageidNum+'\')">×</button>'+
      '<lable class="kongjian_toupiao_item">'+url+'</lable>'+
    '</div>'+
    '</div>';
	$("#kongjian_send_ToupiaoItem_content").html($("#kongjian_send_ToupiaoItem_content").html()+ str);
	$("#kongjian_sendMessage_ToupiaoItem_window").modal("hide");
	$("#kongjian_sendMessage_ToupiaoItem_window_input_name").val("");
    
}
function kongjian_toupiao_item_delete(id){
	$("#"+id).remove();
}
function kongjian_send_createItem_window(){
	$("#kongjian_sendMessage_ToupiaoItem_window").modal("show");
}
function kongjian_sendMessage_window_send(){
	if(kongjian_send_window_type==1){
		var img=$(".kongjian_image");
		var file=$(".kongjian_file");
		var video=$(".kongjian_video");
		var toupiao=$(".kongjian_toupiao_item");
		var toupiao_title=$("#kongjian_send_toupiao_title").val();
		var toupiao_start=$("#kongjian_toupiao_startDate_data").val();
		var toupiao_end=$("#kongjian_toupiao_endDate_data").val();
		var toupiao_move=$("#kongjian_toupiao_move").bootstrapSwitch('state');
		var title=$("#kongjian_send_title").val();
		var content=$("#kongjian_send_content").val();
		var imgstr='';
		var filestr='';
		var videostr='';
		var toupiaostr='';
		if(title==''){
			alertError("标题错误，请填写","kongjian_sendMessage_alert");
			return;
		}
		if(content==''){
			alertError("标题错误，请填写","kongjian_sendMessage_alert");
			return;
		}
			for(var i=0;i<img.length;i++){
				
				if(imgstr!=""){
					imgstr=imgstr+","
				}
				imgstr=imgstr+img[i].src;
			}
			
			for(var i=0;i<file.length;i++){
				if(filestr!=""){
					filestr=filestr+","
				}
				filestr=filestr+file[i].href+":"+$(file[i]).attr("fileName");
			}
			
			for(var i=0;i<video.length;i++){
				if(videostr!=""){
					videostr=videostr+","
				}
				videostr=videostr+video[i].src
			}
			for(var i=0;i<toupiao.length;i++){
				if(toupiaostr!=""){
					toupiaostr=toupiaostr+","
				}
				toupiaostr=toupiaostr+$(toupiao[i]).html()
			}
		
			if(imgstr==''){
				imgstr='0'
			}
			if(filestr==''){
				filestr='0'	
			}
			if(videostr==''){
				videostr='0'
			}
			if(toupiaostr==''){
				toupiaostr='0'
			}
			if(toupiao_title==""){
				toupiao_title="-1"
			}
			if(toupiao_start==""){
				toupiao_start="-1"
			}
			if(toupiao_end==""){
				toupiao_end="-1"
			}
			var i=$.layer({
			    type : 3
			});
		    $.ajax({
				type : "POST",
				url : ctx+"hr/function/sendKongJianManager",
				data:{
					title:title,
					content:content,
					file:filestr,
					img:imgstr,
					video:videostr,
					toupiao:toupiaostr,
					toupiao_title:toupiao_title,
					toupiao_start:toupiao_start,
					toupiao_end:toupiao_end,
					toupiao_move:toupiao_move,
					groupId:kongjian_groupid
					
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						$("#kongjian_sendMessage").modal("hide");
						$("#kongjian_content").prepend($.render.kongjian_content_item(msg.data));
						$("#kongjian_content_item_"+msg.id).hide();
						$("#kongjian_content_item_"+msg.id).slideToggle("slow");
					}else{
						alertError(msg.info,"alertDiv");
					}
				}
		    })
		
	}else if(kongjian_send_window_type==2){
		
		var img=$(".kongjian_image");
		var file=$(".kongjian_file");
		var video=$(".kongjian_video");
		var content=$("#kongjian_send_content").val();
		var imgstr='';
		var filestr='';
		var videostr='';
		if(content==''){
			alertError("标题错误，请填写","kongjian_sendMessage_alert");
			return;
		}
		for(var i=0;i<img.length;i++){
			if(imgstr!=""){
				imgstr=imgstr+","
			}
			imgstr=imgstr+img[i].src
		}
		for(var i=0;i<file.length;i++){
			if(filestr!=""){
				filestr=filestr+","
			}
			filestr=filestr+file[i].href+":"+$(file[i]).attr("fileName");
		}
		
		for(var i=0;i<video.length;i++){
			if(videostr!=""){
				videostr=videostr+","
			}
			videostr=videostr+video[i].src
		}
		if(imgstr==''){
			imgstr='0'
		}
		if(filestr==''){
			filestr='0'	
		}
		if(videostr==''){
			videostr='0'
		}
		var i=$.layer({
		    type : 3
		});
		 $.ajax({
				type : "POST",
				url : ctx+"hr/function/resendKongJianManager",
				data:{
					content:content,
					file:filestr,
					img:imgstr,
					video:videostr,
					id:kongjian_send_window_reSend
					
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						$("#kongjian_sendMessage").modal("hide");
						$("#chat_box_group_"+msg.zoneId).prepend($.render.kongjian_huifu_item(msg.data));
						$("#kongjian_huifu_item_"+msg.id).hide();
						$("#kongjian_huifu_item_"+msg.id).slideToggle("slow");
					}else{
						alertError(msg.info,"alertDiv");
					}
				}
		    })
	}
}
function kongjian_delete_item(id){
	var i=$.layer({
	    type : 3
	});
	 $.ajax({
			type : "POST",
			url : ctx+"hr/function/deleteKongjian",
			data:{
				id:id
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					
					$("#kongjian_content_item_"+msg.id).slideToggle("slow");
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
}
function kongjian_index_item_window_show(id){
	$("#kongjian_setindex_window").modal("show");
	$("#kongjian_index_item_button").attr("onclick","kongjian_index_item("+id+")");
	
}
function kongjian_index_item(id){
	var kongjian_index_item_index=$("#kongjian_index_item_index").val();
	var i=$.layer({
	    type : 3
	});
	 $.ajax({
			type : "POST",
			url : ctx+"hr/function/setIndexKongjian",
			data:{
				id:id,
				indexs:kongjian_index_item_index
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#kongjian_setindex_window").modal("hide");
					
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
}
function kongjian_zan_item(id){
	var i=$.layer({
	    type : 3
	});
	 $.ajax({
			type : "POST",
			url : ctx+"hr/function/zan",
			data:{
				id:id
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#kongjian_zan_list_"+id).hide();
					$("#kongjian_zan_list_"+id).append(msg.zanobj.createUserName);
					$("#kongjian_zan_list_"+id).slideToggle("slow");
					$("#kongjian_zan_item_"+id).hide();
					$("#kongjian_zan_item_"+id).html(msg.num)
					$("#kongjian_zan_item_"+id).slideToggle("slow");
				}else{
					alertError(msg.info,"alertDiv");
				}
			}
	    })
}