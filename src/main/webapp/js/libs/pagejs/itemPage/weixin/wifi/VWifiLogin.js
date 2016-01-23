var imageNowpage=1;
var videoNowpage=1;
var textNowpage=1;
$(function () {
	$(document).ready(function(){
		initTemp();
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
	}
})
function loadImage(now){
	if(now!=null&&now!=undefined){
		imageNowpage=now;
	}
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
				$("#imageMessageContent").html($.render.image_item(msg.data));
			}else{
				alert("出错");
			}
		}
    })
}
function loadVideo(now){
	if(now!=null&&now!=undefined){
		videoNowpage=now;
	}
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
				$("#videpMessageContent").html($.render.video_item(msg.data));
			}else{
				alert("出错");
			}
		}
    })
}
function loadText(now){
	if(now!=null&&now!=undefined){
		textNowpage=now;
	}
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
				$("#textMessageContent").html($.render.text_item(msg.data));
			}else{
				alert("出错");
			}
		}
    })
}