var weixinUserId_appid_input=false;
var setUserId_AppSecret_isboolean=false;
var setUserId_Tokens_isboolean=false;
var setUserId_name_isboolean=false;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		
		var i=$.layer({
		    type : 3
		});
	    $.ajax({
			type : "POST",
			url : ctx+"weixin/getWeXinInfo",
			success : function(msg) {
				layer.close(i);
				if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#weixinUserId_name").html(msg.data.name);
					$("#weixinUserId_appid").html(msg.data.appId);
					$("#weixinUserId_AppSecret").html(msg.data.appSecret);
					$("#weixinUserId_Tokens").html(msg.data.tokens);
				}else{
				
						alertError(msg.info,"weixin_setUserid_alert");
				}
			}
	    })
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
	}
})
function weixinUserId_name(){
	var value=$("#weixinUserId_name").html();
	$("#weixinUserId_name").html("<input id='weixinUserId_name_input' class='form-control input-sm'/>");
	$("#weixinUserId_name_input").val(value);
	$("#weixinUserId_name_input").blur(function(){
		var values=$("#weixinUserId_name_input").val();
		if(values.trim()!=""){
			$("#weixinUserId_name").html(values);
			setUserId_name_isboolean=true;
		}else{
			$("#weixinUserId_name_input").val("不可以不填写");
			setUserId_name_isboolean=false;
		}
	})
	$("#weixinUserId_name_input").focus();
}
function weixinUserId_appid(){
	var value=$("#weixinUserId_appid").html();
	$("#weixinUserId_appid").html("<input id='weixinUserId_appid_input' class='form-control input-sm'/>");
	$("#weixinUserId_appid_input").val(value);
	$("#weixinUserId_appid_input").blur(function(){
		var values=$("#weixinUserId_appid_input").val();
		if(values.trim()!=""){
			$("#weixinUserId_appid").html(values);
			weixinUserId_appid_input=true;
		}else{
			$("#weixinUserId_appid_input").val("不可以不填写");
			weixinUserId_appid_input=false;
		}
	})
	$("#weixinUserId_appid_input").focus();
}
function weixinUserId_AppSecret(){
	var value=$("#weixinUserId_AppSecret").html();
	$("#weixinUserId_AppSecret").html("<input id='weixinUserId_AppSecret_input' class='form-control input-sm'/>");
	$("#weixinUserId_AppSecret_input").val(value);
	$("#weixinUserId_AppSecret_input").blur(function(){
		var values=$("#weixinUserId_AppSecret_input").val();
		if(values.trim()!=""){
			$("#weixinUserId_AppSecret").html(values);
			setUserId_AppSecret_isboolean=true;
		}else{
			$("#weixinUserId_AppSecret_input").val("不可以不填写");
			setUserId_AppSecret_isboolean=false;
		}
	})
	$("#weixinUserId_AppSecret_input").focus();
}
function weixinUserId_Tokens(){
	var value=$("#weixinUserId_Tokens").html();
	$("#weixinUserId_Tokens").html("<input id='weixinUserId_Tokens_input' class='form-control input-sm'/>");
	$("#weixinUserId_Tokens_input").val(value);
	$("#weixinUserId_Tokens_input").blur(function(){
		var values=$("#weixinUserId_Tokens_input").val();
		if(values.trim()!=""){
			$("#weixinUserId_Tokens").html(values);
			setUserId_Tokens_isboolean=true;
		}else{
			$("#weixinUserId_Tokens_input").val("不可以不填写");
			setUserId_Tokens_isboolean=false;
		}
	})
	$("#weixinUserId_Tokens_input").focus();
}

function weixinUserId_set(){
	
	var appid=$("#weixinUserId_appid").html();
	var appSecret=$("#weixinUserId_AppSecret").html();
	var tokens=$("#weixinUserId_Tokens").html();
	var name=$("#weixinUserId_name").html();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/updateWeXinInfo",
		data:{
			appid:appid,
			appSecret:appSecret,
			tokens:tokens,
			name:name
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"weixin_setUserid_alert");
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					
					alertError(msg.info,"weixin_setUserid_alert");
					
				}else{
					
				}
			}
		}
    })
}