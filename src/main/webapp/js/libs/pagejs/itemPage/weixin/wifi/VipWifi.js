var imageNowpage=1;
var imageEnd=false;
var imageNowLiuYanpage=1;
var imageNowLiuYanEnd=false;
var imageNowLiuYanId=0;

var videoNowpage=1;
var videoEnd=false;
var videoNowLiuYanpage=1;
var videoNowLiuYanEnd=false;
var videoNowLiuYanId=0;

var textNowpage=1;
var textEnd=false;
var textNowLiuYanpage=1;
var textNowLiuYanEnd=false;
var textNowLiuYanId=0;

var ErShouEnd=false;
var ErShouNowpage=1;
var ErShoutype=0;
var ErShouNowLiuYanpage=1;
var ErShouNowLiuYanEnd=false;
var ErShouNowLiuYanId=0;

var timeTestMessage=0;
var timeTestMessageTimeobj;
$(function () {
	$(document).ready(function(){
		initTemp();
		loadImage(1);
		if(guanggao){
			myBanner();
		}
	})
	function initTemp(){
		var tmpl = $("#image_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			image_item : tmpl
		});
		var tmpl = $("#video_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			video_item : tmpl
		});
		var tmpl = $("#text_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			text_item : tmpl
		});
		var tmpl = $("#game_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			game_item : tmpl
		});
		var tmpl = $("#gezi_type_menu").html().replace("<!--", "").replace("-->", "");
		$.templates({
			gezi_type_menu : tmpl
		});
		var tmpl = $("#gezi_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			gezi_item : tmpl
		});
		var tmpl = $("#shopvipLevenContent_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopvipLevenContent_item : tmpl
		});
		var tmpl = $("#shopJiaoLiu_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopJiaoLiu_item : tmpl
		});
		var tmpl = $("#shopinfo_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopinfo_item : tmpl
		});
		var tmpl = $("#shopMenuType_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopMenuType_item : tmpl
		});
		var tmpl = $("#shopMenu_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopMenu_item : tmpl
		});
		var tmpl = $("#image_xiangqing_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			image_xiangqing_main : tmpl
		});
		var tmpl = $("#video_xiangqing_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			video_xiangqing_main : tmpl
		});
		var tmpl = $("#text_xiangqing_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			text_xiangqing_main : tmpl
		});
		var tmpl = $("#ershou_xiangqing_main").html().replace("<!--", "").replace("-->", "");
		$.templates({
			ershou_xiangqing_main : tmpl
		});
		var tmpl = $("#image_xiangqing_liuyan").html().replace("<!--", "").replace("-->", "");
		$.templates({
			image_xiangqing_liuyan : tmpl
		});
		var tmpl = $("#video_xiangqing_liuyan").html().replace("<!--", "").replace("-->", "");
		$.templates({
			video_xiangqing_liuyan : tmpl
		});
		var tmpl = $("#text_xiangqing_liuyan").html().replace("<!--", "").replace("-->", "");
		$.templates({
			text_xiangqing_liuyan : tmpl
		});
		var tmpl = $("#ershou_xiangqing_liuyan").html().replace("<!--", "").replace("-->", "");
		$.templates({
			ershou_xiangqing_liuyan : tmpl
		});
		var tmpl = $("#gezi_type_menu_add").html().replace("<!--", "").replace("-->", "");
		$.templates({
			gezi_type_menu_add : tmpl
		});
		var tmpl = $("#game_paihangbang_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			game_paihangbang_item : tmpl
		});
		var tmpl = $("#shopvipLevenContent_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			shopvipLevenContent_item : tmpl
		});
		var tmpl = $("#score_to_goods_list_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			score_to_goods_list_item : tmpl
		});
		var tmpl = $("#JiFenDuiHuanNumList_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			JiFenDuiHuanNumList_item : tmpl
		});
		var tmpl = $("#getZhongJiangNumList_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			getZhongJiangNumList_item : tmpl
		});
		var tmpl = $("#order_item_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			order_item_item : tmpl
		});
		var tmpl = $("#order_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			order_item : tmpl
		});
		var tmpl = $("#buycar_list_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			buycar_list_item : tmpl
		});
	}
})
/**
 * 图片的点赞
 */
function imageZan(id,eleId){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/imageZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var color=$("#"+eleId+id).attr("color");
				if(msg.zan==false){
					$("#"+eleId+id).attr("color","");
				}else{
					$("#"+eleId+id).attr("color","red");
					
				}
				$("#"+eleId+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
/**
 * 图片查看详情
 */
function imageLook(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/imageItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#imageShow_main").html($.render.image_xiangqing_main(msg.data));
				$('#image_xiangqing').modal("show");
				$("#imageLiuYan_content").html("");
				loadImageLiuYan(1,id);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
/**
 * 查询图片
 * @param now
 */
function loadImage(now){
	if(now==1){
		$("#imageMessageContent").html("");
	}
	$(window).unbind('scroll');
	
	if(now!=null&&now!=undefined){
		imageNowpage=now;
	}
	if(!imageEnd){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"VipAppController/image",
			data:{
				nowpage:imageNowpage
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length>0){
						if(msg.data.length<10){
							imageEnd=true;
						}
						$("#imageMessageContent").append($.render.image_item(msg.data));
						 Gifffer();
						$(window).bind('scroll',function(){
							 if($(window).scrollTop()+$(window).height()>=$(document).height()-30)
								{
								 	if(!imageEnd){
								 		loadImage(imageNowpage+1);
								 	}
								}
						});
						
					}else{
						imageEnd=true;
						$("#imageMessageContent").append('<div class="col-md-12" style="text-align: center;">已经没有其他内容</div>');
					}
				}else{
					alert("出错");
				}
			}
	    })
	}
}
/**
 * 视频的点赞
 */
function videoZan(id,eleId){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/videoZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var color=$("#"+eleId+id).attr("color");
				if(msg.zan==false){
					$("#"+eleId+id).attr("color","");
				}else{
					$("#"+eleId+id).attr("color","red");
					
				}
				$("#"+eleId+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
/**
 * 视频查看详情
 */
function videoLook(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/videoItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#videoShow_main").html($.render.video_xiangqing_main(msg.data));
				$('#video_xiangqing').modal("show");
				$("#videoLiuYan_content").html("");
				loadVideoLiuYan(1,id);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function loadVideo(now){
	if(now==1){
		$("#videpMessageContent").html("");
	}
	$(window).unbind('scroll');
	
	if(now!=null&&now!=undefined){
		videoNowpage=now;
	}
	if(!videoEnd){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"VipAppController/video",
			data:{
				nowpage:videoNowpage
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length>0){
						if(msg.data.length<10){
							videoEnd=true;
						}
						$("#videpMessageContent").append($.render.video_item(msg.data));
						$(window).bind('scroll',function(){
							 if($(window).scrollTop()+$(window).height()>=$(document).height()-30)
								{
								 if(!videoEnd){
									 loadVideo(videoNowpage+1);
								 }
								}
						});
					}else{
	
						videoEnd=true;
						$("#videpMessageContent").append('<div class="col-md-12" style="text-align: center;">已经没有其他内容</div>');
					}
				}else{
					alert("出错");
				}
			}
	    })
	}
}
/**
 * 文本的点赞
 */
function textZan(id,eleId){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/textZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var color=$("#"+eleId+id).attr("color");
				if(msg.zan==false){
					$("#"+eleId+id).attr("color","");
				}else{
					$("#"+eleId+id).attr("color","red");
					
				}
				$("#"+eleId+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
/**
 * 文本
 */
function textLook(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/textItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#textShow_main").html($.render.text_xiangqing_main(msg.data));
				$('#text_xiangqing').modal("show");
				$("#textLiuYan_content").html("");
				loadTextLiuYan(1,id);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function loadText(now){
	if(now==1){
		$("#textMessageContent").html("");
	}
	$(window).unbind('scroll');
	
	if(now!=null&&now!=undefined){
		textNowpage=now;
	}
	 if(!textEnd){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"VipAppController/text",
			data:{
				nowpage:textNowpage
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length>0){
						if(msg.data.length<10){
							textEnd=true;
						}
						$("#textMessageContent").append($.render.text_item(msg.data));
						$(window).bind('scroll',function(){
							 if($(window).scrollTop()+$(window).height()>=$(document).height()-30)
								{
								 if(!textEnd){
									 loadText(textNowpage+1);
								 }
								}
						});
					}else{
						textEnd=true;
						$("#textMessageContent").append('<div class="col-md-12" style="text-align: center;">已经没有其他内容</div>');
					}
				}else{
					alert("出错");
				}
			}
	    })
	}
}
function loadImageLiuYan(nowpage,id){
	//var imageNowLiuYanEnd=false;
	if(nowpage!=null&&nowpage!=undefined){
		imageNowLiuYanpage=nowpage;
	}
	if(id!=null&&id!=undefined&&imageNowLiuYanId!=id){
		imageNowLiuYanId=id;
		$("#imageLiuYan_content").html("");
		imageNowLiuYanpage=1;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/imageLiuYan",
		data:{
			nowpage:imageNowLiuYanpage,
			id:imageNowLiuYanId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#imageLiuYan_content").append($.render.image_xiangqing_liuyan(msg.data));
				if(msg.data.length<20){
					$("#imageLiuYan_content_jixu").attr("onclick","");
					$("#imageLiuYan_content_jixu").html("哇咔咔，你已经到了回声尽头(ˇˍˇ) ～");
				}else{
					var nextPage=imageNowLiuYanpage+1;
					$("#imageLiuYan_content_jixu").html("点击我就能继续看哦");
					$("#imageLiuYan_content_jixu").attr("onclick","loadImageLiuYan("+nextPage+","+imageNowLiuYanId+")");
				}
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function loadVideoLiuYan(nowpage,id){
	if(nowpage!=null&&nowpage!=undefined){
		videoNowLiuYanpage=nowpage;
	}
	if(id!=null&&id!=undefined&&videoNowLiuYanId!=id){
		videoNowLiuYanId=id;
		$("#videoLiuYan_content").html("");
		videoNowLiuYanpage=1;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/videoLiuyan",
		data:{
			nowpage:videoNowLiuYanpage,
			id:videoNowLiuYanId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#videoLiuYan_content").append($.render.video_xiangqing_liuyan(msg.data));
				if(msg.data.length<20){
					$("#videoLiuYan_content_jixu").attr("onclick","");
					$("#videoLiuYan_content_jixu").html("哇咔咔，你已经到了回声尽头(ˇˍˇ) ～");
				}else{
					var nextPage=videoNowLiuYanpage+1;
					$("#videoLiuYan_content_jixu").html("点击我就能继续看哦");
					$("#videoLiuYan_content_jixu").attr("onclick","loadVideoLiuYan("+nextPage+","+videoNowLiuYanId+")");
				}
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function loadTextLiuYan(nowpage,id){
	if(nowpage!=null&&nowpage!=undefined){
		textNowLiuYanpage=nowpage;
	}
	if(id!=null&&id!=undefined&&textNowLiuYanId!=id){
		textNowLiuYanId=id;
		$("#textLiuYan_content").html("");
		textNowLiuYanpage=1;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/textLiuyan",
		data:{
			nowpage:textNowLiuYanpage,
			id:textNowLiuYanId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#textLiuYan_content").append($.render.text_xiangqing_liuyan(msg.data));
				if(msg.data.length<20){
					$("#textLiuYan_content_jixu").attr("onclick","");
					$("#textLiuYan_content_jixu").html("哇咔咔，你已经到了回声尽头(ˇˍˇ) ～");
				}else{
					var nextPage=textNowLiuYanpage+1;
					$("#textLiuYan_content_jixu").html("点击我就能继续看哦");
					$("#textLiuYan_content_jixu").attr("onclick","loadTextLiuYan("+nextPage+","+textNowLiuYanId+")");
				}
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}

function loadErShouType(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/ershouType",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
				$("#gezi_type_menu_content").html('<button class="btn bg-purple btn-flat margin btn-sm" onclick="loadErShou(0,1)">全部</button>'+$.render.gezi_type_menu(msg.data));
				loadErShou(0,1);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
/**
 * 格子铺详情
 */
function ershouLook(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/ershouItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#ershouShow_main").html($.render.ershou_xiangqing_main(msg.data));
				$('#ershou_xiangqing').modal("show");
				$("#ershouLiuYan_content").html("");
				loadErShouLiuYan(1,id);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function loadErShou(typeId,nowpage){
	$(window).unbind('scroll');
	if(nowpage!=null&&nowpage!=undefined&&nowpage!=0){
		ErShouNowpage=nowpage;
	}
	if(typeId!=null&&typeId!=undefined&&nowpage!=0&&typeId!=ErShoutype){
		ErShoutype=typeId;
		ErShouEnd=false;
		$("#gezi_content").html("");
	}
	 if(!ErShouEnd){
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"VipAppController/ershou",
			data:{
				typeId:ErShoutype,
				nowpage:ErShouNowpage
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length>0){
						if(msg.data.length<10){
							ErShouEnd=true;
						}
						$("#gezi_content").append($.render.gezi_item(msg.data));
						$(window).bind('scroll',function(){
							 if($(window).scrollTop()+$(window).height()>=$(document).height()-30)
								{
								 if(!ErShouEnd){
									 loadErShou(ErShoutype,ErShouNowpage+1);
								 }
								}
						});
						$("#geziMessageContent").html("");
					}else{
						ErShouEnd=true;
						$("#geziMessageContent").html('<div class="col-md-12" style="text-align: center;">已经没有其他内容</div>');
					}
				
				}else{
					alert("出错,或登录超时，或内容已被删除");
				}
			}
	    })
	 }
}
function loadErShouLiuYan(nowpage,id){
	if(nowpage!=null&&nowpage!=undefined){
		ErShouNowLiuYanpage=nowpage;
	}
	if(id!=null&&id!=undefined&&textNowLiuYanId!=id){
		ErShouNowLiuYanId=id;
		$("#ershouLiuYan_content").html("");
		ErShouNowLiuYanpage=1;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/erShouLiuyan",
		data:{
			nowpage:ErShouNowLiuYanpage,
			id:ErShouNowLiuYanId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#ershouLiuYan_content").append($.render.ershou_xiangqing_liuyan(msg.data));
				if(msg.data.length<20){
					$("#ershouLiuYan_content_jixu").attr("onclick","");
					$("#ershouLiuYan_content_jixu").html("已到头");
				}else{
					var nextPage=ErShouNowLiuYanpage+1;
					$("#ershouLiuYan_content_jixu").html("继续查看");
					$("#ershouLiuYan_content_jixu").attr("onclick","loadErShouLiuYan("+nextPage+","+textNowLiuYanId+")");
				}
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function showAddImage(){
	$('#image_add_text').modal("show");
	$("#showAddImage_upImage_src").attr("src","");
	$("#image_add_title_input").val("");
	$("#image_add_text_input").val("");
}
function showAddImage_upImage(){
	$("#showAddImage_upImage").click();
}
function showAddImage_upImage_ajax(){
	var i=$.layer({
	    type : 3
	});
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'VipAppController/uploadImage',
			fileElementId:'showAddImage_upImage',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
				layer.close(i);
				if(json.success){
					
					$("#showAddImage_upImage_src").attr("src",json.url);
				}else{
					alert(json.message,"kongjian_sendMessage_alert");
				}
			}
		}
	)
}
/**
 * 添加按钮
 */
function showAddImage_submit(id){
	var address=$("#showAddImage_upImage_src").attr("src");
	var title=$("#image_add_title_input").val();
	var text=$("#image_add_text_input").val();
	if(address.trim()==""){
		alert("请选择图片或者拍摄图片");
		return;
	}
	if(title.trim()==""){
		alert("请填写标题");
		return;
	}
	if(text.trim()==""){
		alert("请填写内容");
		return;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/addImage",
		data:{
			address:address,
			title:title,
			text:text
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert("已发布，等待审核,您可以叫店主赶紧审核。。");
				$('#image_add_text').modal("hide");
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function showAddVideo(){
	$('#video_add_text').modal("show");
	$("#addvideoSrc").attr("src","");
	$("#video_add_title_input").val("");
	$("#video_add_text_input").val("");
}
function showAddVideo_upVideo(){
	$("#showAddVideo_upVideo").click();
}
function showAddVideo_upVideo_ajax(){
	var i=$.layer({
	    type : 3
	});
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'VipAppController/uploadVideo',
			fileElementId:'showAddVideo_upVideo',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
				layer.close(i);
				if(json.success){
					$("#showAddVideo_upVideo_src").html("<video class='video-js vjs-default-skin' controls preload='none'  width='100%'  data-setup='{}'><source id='addvideoSrc' class='kongjian_video' src='"+json.url+"' type='video/mp4' /></video> ")
					
				}else{
					alert(json.message,"kongjian_sendMessage_alert");
				}
			}
		}
	)
}
/**
 * 添加按钮
 */
function showAddVideo_submit(id){
	var address=$("#addvideoSrc").attr("src");
	var title=$("#video_add_title_input").val();
	var text=$("#video_add_text_input").val();
	if(address.trim()==""){
		alert("请选择视频或者拍摄视频");
		return;
	}
	if(title.trim()==""){
		alert("请填写标题");
		return;
	}
	if(text.trim()==""){
		alert("请填写内容");
		return;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/addVideo",
		data:{
			address:address,
			title:title,
			text:text
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert("已发布，等待审核,您可以叫店主赶紧审核。。");
				$('#video_add_text').modal("hide");
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function showAddText(){
	$('#text_add_text').modal("show");
	$("#text_add_title_input").val("");
	$("#text_add_text_input").val("");
}
/**
 * 添加按钮
 */
function showAddText_submit(id){
	var title=$("#text_add_title_input").val();
	var text=$("#text_add_text_input").val();
	if(title.trim()==""){
		alert("请填写标题");
		return;
	}
	if(text.trim()==""){
		alert("请填写内容");
		return;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/addText",
		data:{
			title:title,
			text:text
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert("已发布，等待审核,您可以叫店主赶紧审核。。");
				$('#text_add_text').modal("hide");
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function showAddErshou(){
	$('#ershou_add_text').modal("show");
	$("#showAddErShou_upErShou_src").html("");
	$("#ershou_add_name").val("");
	$("#ershou_add_phone").val("");
	$("#ershou_add_price").val("");
	$("#ershou_add_text2").val("");
	query_ershou_add_type();
}
function showAddErShou_upErShou(){
	$("#showAddErShou_upErShou").click();
}
function showAddErShou_upErShou_ajax(){
	if($("#showAddErShou_upErShou_src img").length<4){
		var i=$.layer({
		    type : 3
		});
		$.ajaxFileUpload
		(
			{
				type : "POST",
				//dataType:"json",
				url:ctx+'VipAppController/uploadImage',
				fileElementId:'showAddErShou_upErShou',
				success: function (data)
				{
					json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					layer.close(i);
					if(json.success){
						
							var imageidNum=Math.floor(Math.random()*100);
							$("#showAddErShou_upErShou_src").append('<img src="'+json.url+'" id="showAddImage_upImage_src'+imageidNum+'" width="100%"/>')
						
					}else{
						alert(json.message,"kongjian_sendMessage_alert");
					}
				}
			}
		)
	}else{
		alert("最多上传4张图片");
	}
}
/**
 * 添加按钮
 */
function showAddErShou_submit(){
	var imgs=$("#showAddErShou_upErShou_src img");
	var address="";
	for(var i=0;i<imgs.length;i++){
		if(i!=0){
			address=address+",";
		}
		address=address+imgs[i].src;
	}
	var name=$("#ershou_add_name").val();
	var phone=$("#ershou_add_phone").val();
	var price=$("#ershou_add_price").val();
	var text=$("#ershou_add_text2").val();
	var type=$("#ershou_add_type_select").val();
	if(type.trim()==""){
		alert("请选择类别，哎呦喂如果没有类别的话可能是店铺老板没有设置哦。那就不能发表了");
		return;
	}
	if(address.trim()==""){
		alert("请选择图片或者拍摄图片");
		return;
	}
	if(name.trim()==""){
		alert("请填写名称");
		return;
	}
	if(phone.trim()==""){
		alert("请填写电话");
		return;
	}
	if(price.trim()==""){
		alert("请填写电话");
		return;
	}
	alert(text);
	if(text.trim()==""){
		alert("请填写内容");
		return;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/addErShou",
		data:{
			address:address,
			name:name,
			phone:phone,
			price:price,
			text:text,
			type:type
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert("已发布，等待审核,您可以叫店主赶紧审核。。");
				$('#ershou_add_text').modal("hide");
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
	
}
function query_ershou_add_type(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/ershouType",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#ershou_add_type_show").html($.render.gezi_type_menu_add(msg.data));
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function gezi_type_menu_add_select(id,obj){
	$("#ershou_add_type_show button").each(function(i){
		$(this).attr("class","btn bg-purple btn-flat margin btn-sm");
	} );
	$(obj).attr("class","btn bg-orange btn-flat margin btn-sm");
	$("#ershou_add_type_select").val(id);
}
function liuyan_image_zan(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/imageLiuyanZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.zan==false){
					$("#imageMessageRetzan"+id).attr("color","");
				}else{
					$("#imageMessageRetzan"+id).attr("color","red");
				}
				$("#imageMessageRetzan"+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
function liuyan_video_zan(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/videoLiuyanZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
				if(msg.zan==false){
					$("#videoMessageRetzan"+id).attr("color","");
				}else{
					$("#videoMessageRetzan"+id).attr("color","red");
					
				}
				$("#videoMessageRetzan"+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
function liuyan_text_zan(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/textLiuyanZan",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
				if(msg.zan==false){
					$("#textMessageRetzan"+id).attr("color","");
				}else{
					$("#textMessageRetzan"+id).attr("color","red");
					
				}
				$("#textMessageRetzan"+id).html('<i class="fa  fa-thumbs-up"></i> '+msg.num);
			}else{
			
			}
		}
    })
}
function return_image_function(id){
	var message=$("#return_image_text").val();
	$("#return_image_text").val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendImageLiuyan",
		data:{
			message:message,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<div class="row">'
					+'<div class="col-md-12">'
					+'<table width="100%">'
					+'<tbody><tr>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'

					+'	<img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"/>'
					+'</td>'
					+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+'</td>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'
					+'<font  id="imageMessageRetzan'+msg.obj.id+'" style="cursor: pointer;" onclick="liuyan_image_zan('+msg.obj.id+')">'
					+'<i class="fa  fa-thumbs-up"></i> 0'
					+'</font>'
					+'</td>'
					+'</tr>'
					+'<tr>'
					+'<td rowspan="2" width="50%" style="font-size: 8px;">'+msg.obj.message+'</td>'
					+'</tr>'
					+'<tr id="image_xiangqing_liuyan_ret_'+msg.obj.id+'">'
					+'</tr>'
					+'</tbody></table>'
					+'</div>'
					+'<br>'
					+'<div class="col-md-12">'
					+'<div class="input-group" style="width:100%;">'
					+'<input class="form-control" placeholder="请输入回复" id="return_image_text_two'+msg.obj.id+'">'
					+'<div class="input-group-btn">'
					+'<button class="btn btn-success" onclick="return_image_function_two('+msg.obj.id+')">'
					+'<i class="fa fa-plus"></i>'
					+'</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>'+'<hr />';
					$("#imageLiuYan_content").prepend(html);
			}else{
			
			}
		}
    })
	
}
function return_video_function(id){
	var message=$("#return_video_text").val();
	$("#return_video_text").val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendVideoLiuyan",
		data:{
			message:message,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<div class="row">'
					+'<div class="col-md-12">'
					+'<table width="100%">'
					+'<tbody><tr>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'

					+'	<img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"/>'
					+'</td>'
					+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+'</td>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'
					+'<font  id="videoMessageRetzan'+msg.obj.id+'" style="cursor: pointer;" onclick="liuyan_video_zan('+msg.obj.id+')">'
					+'<i class="fa  fa-thumbs-up"></i> 0'
					+'</font>'
					+'</td>'
					+'</tr>'
					+'<tr>'
					+'<td rowspan="2" width="50%" style="font-size: 8px;">'+msg.obj.message+'</td>'
					+'</tr>'
					+'<tr id="video_xiangqing_liuyan_ret_'+msg.obj.id+'">'
					+'</tr>'
					+'</tbody></table>'
					+'</div>'
					+'<br>'
					+'<div class="col-md-12">'
					+'<div class="input-group" style="width:100%;">'
					+'<input class="form-control" placeholder="请输入回复" id="return_video_text_two'+msg.obj.id+'">'
					+'<div class="input-group-btn">'
					+'<button class="btn btn-success" onclick="return_video_function_two('+msg.obj.id+')">'
					+'<i class="fa fa-plus"></i>'
					+'</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>'+'<hr />';
					$("#videoLiuYan_content").prepend(html);
			}else{
			
			}
		}
    })
}
function return_text_function(id){
	var message=$("#return_text_text").val();
	$("#return_text_text").val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendTextLiuyan",
		data:{
			message:message,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<div class="row">'
					+'<div class="col-md-12">'
					+'<table width="100%">'
					+'<tbody><tr>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'

					+'	<img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"/>'
					+'</td>'
					+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+'</td>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'
					+'<font  id="textMessageRetzan'+msg.obj.id+'" style="cursor: pointer;" onclick="liuyan_text_zan('+msg.obj.id+')">'
					+'<i class="fa  fa-thumbs-up"></i> 0'
					+'</font>'
					+'</td>'
					+'</tr>'
					+'<tr>'
					+'<td rowspan="2" width="50%" style="font-size: 8px;">'+msg.obj.message+'</td>'
					+'</tr>'
					+'<tr id="text_xiangqing_liuyan_ret_'+msg.obj.id+'">'
					+'</tr>'
					+'</tbody></table>'
					+'</div>'
					+'<br>'
					+'<div class="col-md-12">'
					+'<div class="input-group" style="width:100%;">'
					+'<input class="form-control" placeholder="请输入回复" id="return_text_text_two'+msg.obj.id+'">'
					+'<div class="input-group-btn">'
					+'<button class="btn btn-success" onclick="return_text_function_two('+msg.obj.id+')">'
					+'<i class="fa fa-plus"></i>'
					+'</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>'+'<hr />';
					$("#textLiuYan_content").prepend(html);
			}else{
			
			}
		}
    })
}
function return_ershou_function(id){
	var message=$("#return_ershou_text").val();
	$("#return_ershou_text").val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendErShouLiuyan",
		data:{
			message:message,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<div class="row">'
					+'<div class="col-md-12">'
					+'<table width="100%">'
					+'<tbody><tr>'
					+'<td rowspan="3" width="20%" style="text-align: center;">'

					+'	<img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"/>'
					+'</td>'
					+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+'</td>'

					+'</tr>'
					+'<tr>'
					+'<td rowspan="2" width="50%" style="font-size: 8px;">'+msg.obj.message+'</td>'
					+'</tr>'
					+'<tr id="ershou_xiangqing_liuyan_ret_'+msg.obj.id+'">'
					+'</tr>'
					+'</tbody></table>'
					+'</div>'
					+'<br>'
					+'<div class="col-md-12">'
					+'<div class="input-group" style="width:100%;">'
					+'<input class="form-control" placeholder="请输入回复" id="return_ershou_text_two'+msg.obj.id+'">'
					+'<div class="input-group-btn">'
					+'<button class="btn btn-success" onclick="return_ershou_function_two('+msg.obj.id+')">'
					+'<i class="fa fa-plus"></i>'
					+'</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>'+'<hr />';
					$("#ershouLiuYan_content").prepend(html);
			}else{
			
			}
		}
    })
}
function return_image_function_two(id){
	var toVip=$("#ret_toVip_image_toVip_id"+id).val();
	if(toVip==""){
		toVip=0;
	}
	var message=$("#return_image_text_two"+id).val();
	$("#return_image_text_two"+id).val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendImageLiuyanTwo",
		data:{
			message:message,
			id:id,
			toVip:toVip
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<tr>'
				+'<td></td>'
				+'<td>'
				+'<hr>'
				+'<table width="100%">'
				+'<tbody><tr>'
				+'<td rowspan="3" width="20%" style="text-align: center;"><img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"></td>'
				+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+' 对 '+msg.obj.toCreateVipName+' 说:</td>'
				+'</tr>'
				+'<tr>'
				+'<td rowspan="2" width="50%" style="font-size: 5px;">'+msg.obj.message+'</td>'
				+'</tr>'
				+'<tr>'
				+'</tr>'
				+'</tbody></table>'
				+'</td>'
				+'</tr>'
				$("#image_xiangqing_liuyan_ret_"+id).after(html);
			}else{
				alert(msg.info);
			}
		}
    })
}
function return_video_function_two(id){
	var toVip=$("#ret_toVip_video_toVip_id"+id).val();
	if(toVip==""){
		toVip=0;
	}
	var message=$("#return_video_text_two"+id).val();
	$("#return_video_text_two"+id).val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendVideoLiuyanTwo",
		data:{
			message:message,
			toVip:toVip,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<tr>'
				+'<td></td>'
				+'<td>'
				+'<hr>'
				+'<table width="100%">'
				+'<tbody><tr>'
				+'<td rowspan="3" width="20%" style="text-align: center;"><img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"></td>'
				+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+' 对 '+msg.obj.toCreateVipName+' 说:</td>'
				+'</tr>'
				+'<tr>'
				+'<td rowspan="2" width="50%" style="font-size: 5px;">'+msg.obj.message+'</td>'
				+'</tr>'
				+'<tr>'
				+'</tr>'
				+'</tbody></table>'
				+'</td>'
				+'</tr>'
				$("#video_xiangqing_liuyan_ret_"+id).after(html);
			}else{
				alert(msg.info);
			}
		}
    })
}
function return_text_function_two(id){
	var toVip=$("#ret_toVip_text_toVip_id"+id).val();
	if(toVip==""){
		toVip=0;
	}
	var message=$("#return_text_text_two"+id).val();
	$("#return_text_text_two"+id).val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendTextLiuyanTwo",
		data:{
			message:message,
			id:id,
			toVip:toVip
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<tr>'
				+'<td></td>'
				+'<td>'
				+'<hr>'
				+'<table width="100%">'
				+'<tbody><tr>'
				+'<td rowspan="3" width="20%" style="text-align: center;"><img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"></td>'
				+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+' 对 '+msg.obj.toCreateVipName+' 说:</td>'
				+'</tr>'
				+'<tr>'
				+'<td rowspan="2" width="50%" style="font-size: 5px;">'+msg.obj.message+'</td>'
				+'</tr>'
				+'<tr>'
				+'</tr>'
				+'</tbody></table>'
				+'</td>'
				+'</tr>'
				$("#text_xiangqing_liuyan_ret_"+id).after(html);
			}else{
				alert(msg.info);
			}
		}
    })
}
function return_ershou_function_two(id){
	var toVip=$("#ret_toVip_ershou_toVip_id"+id).val();
	if(toVip==""){
		toVip=0;
	}
	var message=$("#return_ershou_text_two"+id).val();
	$("#return_ershou_text_two"+id).val("");
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/sendErShouLiuyanTwo",
		data:{
			message:message,
			id:id,
			toVip:toVip
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl=""
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<tr>'
				+'<td></td>'
				+'<td>'
				+'<hr>'
				+'<table width="100%">'
				+'<tbody><tr>'
				+'<td rowspan="3" width="20%" style="text-align: center;"><img class="media-object" data-src="holder.js/64x64" alt="64x64" src="'+imgurl+'" data-holder-rendered="true" style="width: 42px; height: 42px;"></td>'
				+'<td style="font-size: 5px;" width="50%">'+msg.obj.createVipName+' 对 '+msg.obj.toCreateVipName+' 说:</td>'
				+'</tr>'
				+'<tr>'
				+'<td rowspan="2" width="50%" style="font-size: 5px;">'+msg.obj.message+'</td>'
				+'</tr>'
				+'<tr>'
				+'</tr>'
				+'</tbody></table>'
				+'</td>'
				+'</tr>'
				$("#ershou_xiangqing_liuyan_ret_"+id).after(html);
			}else{
				alert(msg.info);
			}
		}
    })
}
/**
游戏

**/

function game(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/game",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){

				$("#gameContent").html($.render.game_item(msg.data));
			}else{
			
			}
		}
    })
}
/**
 * 排行
 */
function gamePaihang(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/gamepaiming",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var html=' <tr >'
				+' <th>头像</th>'
                +' <th>排名</th>'
                +' <th>名字</th>'
                +' <th>分数</th>'
                +'  </tr>';
				$("#paiming_window_list").html(html+$.render.game_paihangbang_item(msg.data));
				$('#game_paming').modal("show");
			}else{
			
			}
		}
    })
}
/**
 * 会员
 */
function vip(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/getVipList",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#shopvipLevenContent").html($.render.shopvipLevenContent_item(msg.data));
			}else{
			
			}
		}
    })
}
/**
 *积分兑换列表显示
 */
function score_to_goods_list(nowpage){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/jifenduihuan_data",
		data:{
			nowpage:nowpage
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(msg.data.length>=15){
					$("#score_to_goods_list_content").append($.render.score_to_goods_list_item(msg.data));
					$('#score_to_goods_list').modal("show");
					nowpage=nowpage+1;
					$("#score_to_goods_list_loadMover").attr("onclick","score_to_goods_list("+nowpage+")");
				}else{
					$("#score_to_goods_list_content").append($.render.score_to_goods_list_item(msg.data));
					$("#score_to_goods_list_loadMover").attr("onclick","");
					$("#score_to_goods_list_loadMover").html("已经到最后了╮(╯▽╰)╭");
					$('#score_to_goods_list').modal("show");
				}
			}else{
			
			}
		}
    })
}
function DuiHuanJiangPin(id){
	var num=$("#scoreNum"+id).val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/UserToScoreduihuan",
		data:{
			num:num,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert(msg.info);
			}else{
				alert(msg.info);
			}
		}
    })
}
function jiaoliu(page){
	if(page==1){
		$("#vipJiaolIuContent").html("");
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/getjiaoliu",
		data:{
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#vipJiaolIuContent").append($.render.shopJiaoLiu_item(msg.data));
				var nextPage=page+1;
				$("#vipJiaolIu_next").attr("onclick","jiaoliu("+nextPage+")");
			}else{
				
			}
		}
    })
}
function toVipJiaoLiu(vipid,retId,type,name,mesId){
	//toVip message id
	$("#ret_toVip_jiaoliu_name"+retId+"_mesId_"+mesId).html(name);
	$("#ret_toVip_jiaoliu_id"+retId+"_mesId_"+mesId).val(retId);
	$("#ret_toVip_jiaoliu_toVip_id"+retId+"_mesId_"+mesId).val(vipid);
	$("#ret_toVip_jiaoliu_toVip_button"+retId+"_mesId_"+mesId).attr("onclick","toVipJiaoLiu_button("+retId+","+vipid+","+type+","+mesId+")");
}
function toVipJiaoLiu_button(id,toVip,type,mesId){
	var message=$("#ret_toVip_jiaoliu_toVip_content"+id+"_mesId_"+mesId).val();
	var url="";
	switch(type){
	case 1:
		url="VipAppController/sendImageLiuyanTwo";
		break;
	case 2:
		url="VipAppController/sendVideoLiuyanTwo";
		break;
	case 3:
		url="VipAppController/sendTextLiuyanTwo";
		break;
	case 4:
		url="VipAppController/sendErShouLiuyanTwo";
		break;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+url,
		data:{
			message:message,
			id:id,
			toVip:toVip
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				var imgurl="";
				if(msg.obj.touXiangImage==null||msg.obj.touXiangImage=="")
				{
					imgurl="/img/touxiang.jpg";
				}else{
					imgurl=msg.obj.touXiangImage;
				}
				var html='<div class="item">'
				+'<img src="'+imgurl+'" alt="user image" class="online" />'
				+'<p class="message">'
				+'<a href="javascript:toVipJiaoLiu('+msg.obj.createVipId+','+msg.obj.mainId+','+msg.obj.type+',\''+msg.obj.createVipName+'\')" class="name"> <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 刚刚</small>'
				+msg.obj.createVipName
				+'</a> '+msg.obj.message+''
				+'</p>'
				+'</div>'
				$("#ret_toVip_jiaoliu_toVip_item_content"+id).append(html);
				$("#ret_toVip_jiaoliu_toVip_content"+id).val("");
			}else{
				alert(msg.info);
			}
		}
    })
}
function toVipid(vipid,retId,type,name){
	switch(type){
		case 1:
			$("#ret_toVip_image_name"+retId).html(name);
			$("#ret_toVip_image_id"+retId).val(retId);
			$("#ret_toVip_image_toVip_id"+retId).val(vipid);
			break;
		case 2:
			$("#ret_toVip_video_name"+retId).html(name);
			$("#ret_toVip_video_id"+retId).val(retId);
			$("#ret_toVip_video_toVip_id"+retId).val(vipid);
			break;
		case 3:
			$("#ret_toVip_text_name"+retId).html(name);
			$("#ret_toVip_text_id"+retId).val(retId);
			$("#ret_toVip_text_toVip_id"+retId).val(vipid);
			break;
		case 4:
			$("#ret_toVip_ershou_name"+retId).html(name);
			$("#ret_toVip_ershou_id"+retId).val(retId);
			$("#ret_toVip_ershou_toVip_id"+retId).val(vipid);
			break;
	}
}
function gonggao(page){
	if(page==1){
		$("#shopinfoContent").html("");
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/getGonggao",
		data:{
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#shopinfoContent").append($.render.shopinfo_item(msg.data));
			}else{
				alert(msg.info);
			}
		}
    })
}
function show_update_password(){
	$('#update_password').modal("show");
}
function show_update_password_enter(){
	var oldpassword=$("#oldpassword").val();
	var newpassword=$("#newpassword").val();
	var renewpassword=$("#renewpassword").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/passwordChange",
		data:{
			oldpassword:oldpassword,
			newpassword:newpassword,
			renewpassword:renewpassword
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#update_password').modal("hide");
				alert(msg.info);
			}else{
				alert(msg.info);
			}
		}
    })
}
function show_update_password_cel(){
	$('#update_password').modal("hide");
}
function show_duihuanjifen(){
	$('#duihuanjifen_window').modal("show");
}
function show_duihuanjifen_enter(){
	var num=$("#jifenNum").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/jifenDuiHuanjuanHuanJIfen",
		data:{
			num:num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#duihuanjifen_window').modal("hide");
				alert(msg.info);
			}else{
				alert(msg.info);
			}
		}
    })
}
function show_duihuanjifen_cel(){
	$('#duihuanjifen_window').modal("hide");
}
function weixin_vip_reflash(){
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VipAppController/reflashInfo",
		data : {},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_vip_level").html("vip等级:"+msg.obj.vipLevel);
				$("#weixin_vip_marks").html("待遇:"+msg.obj.vipMarks);
				$("#weixin_vip_score").html(msg.obj.linkManScore);
				$("#weixin_vip_price").html("余额:"+msg.obj.money);
			}else{
				
			}
		}
	})
}
function getZhongJiangNumList_show(page){
	$('#getZhongJiangNumList_window').modal("show");
	getZhongJiangNumList_show_query(page);
	
}
function getZhongJiangNumList_show_query(page){
	if(page==1){
		$("#getZhongJiangNumList_window_content").html("");
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VipAppController/getvipzhongjiangjuan",
		data : {
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#getZhongJiangNumList_window_content").append($.render.getZhongJiangNumList_item(msg.data));
				var page2=page+1;
				$("#JiFenDuiHuanNumList_window_content_mover").attr("onclick","getZhongJiangNumList_show_query("+page2+")")
			}else{
				alert(msg.info);
			}
		}
	})
}
function JiFenDuiHuanNumList_show(page){
	$('#JiFenDuiHuanNumList_window').modal("show");
	JiFenDuiHuanNumList_show_query(page);
}
function JiFenDuiHuanNumList_show_query(page){
	if(page==1){
		$("#JiFenDuiHuanNumList_window_content").html("");
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VipAppController/getvipDuihuanjuan",
		data : {
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#JiFenDuiHuanNumList_window_content").append($.render.JiFenDuiHuanNumList_item(msg.data));
				var page2=page+1;
				$("#getZhongJiangNumList_window_content_mover").attr("onclick","JiFenDuiHuanNumList_show_query("+page2+")")
			}else{
				alert(msg.info);
			}
		}
	})
}
function orderShow(page){
	$('#orderWindow').modal("show");
	orderShow_query(page)
}
function orderShow_query(page){
	if(page==1){
		$("#orderWindow_content").html("");
	}
	var i = $.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx + "VipAppController/getVipOrderList",
		data : {
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#orderWindow_content").append($.render.order_item(msg.data));
				var page2=page+1;
				$("#orderWindow_content_mover").attr("onclick","orderShow_query("+page2+")")
			}else{
				alert(msg.info);
			}
		}
	})
}
function weixin_vip_order_look_info(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"VipAppController/getVipOrderItemList",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#rowTable_"+id).html($.render.order_item_item(msg));
			}else{
				
			}
		}
	})
}
function loadGoodsType(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/goodType",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				
				$("#shopMenuTypeContent").html('<button class="btn bg-purple btn-flat margin btn-sm" onclick="loadErShou(0,1)">全部</button>'+$.render.shopMenuType_item(msg.data));
				loadGoods(0,1);
			}else{
				alert("出错,或登录超时，或内容已被删除");
			}
		}
    })
}
function loadGoods(typeId,nowpage){
		if(nowpage==1){
			$("#shopMenuContent").html("");
		}
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"VipAppController/getGoods",
			data:{
				typeId:typeId,
				nowpage:nowpage
			},
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(msg.data.length>0){
						$("#shopMenuContent").append($.render.shopMenu_item(msg.data));
						var page2=nowpage+1;
						$("#shopMenuContent_mover").attr("onclick","loadGoods("+typeId+","+page2+")");
					}else{
						$("#shopMenuContent_mover").html('<div class="col-md-12" style="text-align: center;">已经没有其他内容</div>');
						$("#shopMenuContent_mover").attr("onclick","");
					}
				
				}else{
					alert("出错,或登录超时，或内容已被删除");
				}
			}
	    })
}
function rigister(){
	var phone=$("#phone").val();
	var conpanyId=$("#conpanyId").val();
	
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"wifidogController/phoneTest",
		data:{
			phone:phone,
			conpanyId:conpanyId
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#inputTestCode").show()
				$("#startRigister").show();
				$("#getCode").attr("disabled","disabled");
				timeTestMessageTimeobj=setInterval(function(){
					$("#getCode").val("还剩"+(60-timeTestMessage)+"秒");
				    	timeTestMessage++;
				    	if(timeTestMessage==60){
				    		timeTestMessage=0;
				    		$("#getCode").val("获取验证码");
				    		$("#getCode").removeAttr("disabled");
				    		clearInterval(timeTestMessageTimeobj);
						}
				    },1000);
			}else{
				alert(msg.info,4000,false);
			}
		}
    })
}
function rigister_start(){
	var gw_address=$("#gw_address").val();
	var gw_port=$("#gw_port").val();
	var phone=$("#gw_id").val();
	var mac=$("#mac").val();
	var url=$("#url").val();
	var dev_id=$("#dev_id").val();
	var conpanyId=$("#conpanyId").val();
	var tokens=$("#tokens").val();
	var phone=$("#phone").val();
	var name=$("#name").val();
	var inputTestCode=$("#inputTestCode").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"wifidogController/rigisterWifiVip",
		data:{
			gw_address:gw_address,
			gw_port:gw_port,
			phone:phone,
			mac:mac,
			url:url,
			dev_id:dev_id,
			conpanyId:conpanyId,
			tokens:tokens,
			phone:phone,
			name:name,
			testCode:inputTestCode
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert(msg.info,4000,false);
				window.location.href=window.location.href;
			}else{
				alert(msg.info,4000,false);
			}
		}
	});
}
function touxiang_name(){
	$('#touxiangnicheng_window').modal("show");
}
function touxiang_upImage(){
	$("#touxiang_upImage").click();
}
function touxiang_upImage_ajax(){
	var i=$.layer({
	    type : 3
	});
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'VipAppController/uploadImage',
			fileElementId:'touxiang_upImage',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
				layer.close(i);
				if(json.success){
					
					$("#touxiang_upImage_src").attr("src",json.url);
				}else{
					alert(json.message);
				}
			}
		}
	)
}
function touxiang_name_close(){
	$('#touxiangnicheng_window').modal("hide");
}
function touxiang_name_submit(){
	var src=$("#touxiang_upImage_src").attr("src");
	var nicheng=$("#nicheng").val();
	if(src==""){
		src=-1;
	}
	if(nicheng==""){
		nicheng=-1;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/setUserInfo",
		data:{
			src:src,
			nicheng:nicheng
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert(msg.message);
				$('#touxiangnicheng_window').modal("hide");
				$("#touxiang_upImage_src").attr("src","");
				$("#nicheng").val("");
			}else{
				alert(msg.message);
			}
		}
    })
}
function addGouWuChe_close(){
	$('#addgouwuche').modal("hide");
}
function addGouWuChe_open(id){
	$('#addgouwuche').modal("show");
	$("#add_gouwuche_button").attr("onclick","addGouwuche_ajax("+id+")")
}
function addGouwuche_ajax(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/addCar",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alert(msg.info);
				$('#addgouwuche').modal("hide");
			}else{
				alert(msg.info);
			}
		}
    })
}
function gouwuchewindow(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/getCar",
		success : function(msg) {
			layer.close(i);
			if(msg.data.length>0){
				$("#buycar_list_Content").html($.render.buycar_list_item(msg.data));
			}else{
				var html='<div class="row">'+
				'<div  class="col-xs-3"></div>'+
				'<div  class="col-xs-6" style="text-align: center;">物车内没有东西哦</div>'+
				'<div  class="col-xs-3"></div>'+
				'</div>';
				$("#buycar_list_Content").html(html);
			}
			$('#gouwuche_window').modal("show");
		}
    })
}
function gouwucheSubmit(){
	var address=$("#buyCar_address").val();
	var phone=$("#buyCar_phone").val();
	var mark=$("#buyCar_mark").val();
	if(address==""){
		alert("请填写地址");
		return ;
	}
	if(phone==""){
		alert("请填写电话");
		return ;
	}
	if(mark==""){
		mark="无";
		return ;	
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"VipAppController/submitOrder",
		data:{
			address:address,
			phone:phone,
			mark:mark
		},
		success : function(msg) {
			layer.close(i);
			
			$('#gouwuche_window').modal("hide");
		}
    })
}