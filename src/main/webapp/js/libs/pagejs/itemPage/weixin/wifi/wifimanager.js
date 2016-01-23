$(function () {
	$(document).ready(function(){
		initTemp();
		weixin_wifi_query();
	})
	function initTemp(){
		var tmpl = $("#weixin_wifi_add_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wifi_add_window_temp : tmpl
		});
		var tmpl = $("#weixin_wifi_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wifi_tr : tmpl
		});
		var tmpl = $("#weixin_wifi_lookClient_window_temp").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wifi_lookClient_window_temp : tmpl
		});
		var tmpl = $("#weixin_wifi_lookClient_window_temp_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_wifi_lookClient_window_temp_tr : tmpl
		});
	}
})
function weixin_wifi_add(){
	$("#window").html($.render.weixin_wifi_add_window_temp());
	$("#weixin_wifi_add_window").modal("show");
	$('#addwifiopen_closs_wifi').bootstrapSwitch('state',true);
}
function weixin_wifi_add_enter(id){
	if(id==undefined){
		id=0;
		url="addDevice";
	}else{
		url="updateDevice";
	}
	var name=$('#weixin_wifi_name').val();
	var tokens=$('#weixin_wifi_tokens').val();
	var open=$('#addwifiopen_closs_wifi').bootstrapSwitch('state');
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"wifiController/"+url,
		data:{
			id:id,
			name:name,
			tokens:tokens,
			open:open
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_wifi_add_window").modal("hide");
				alertSuccess(msg.info,"weixin_wifi_add");
				weixin_wifi_query();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_wifi_add");
				}else{
					inputAjaxTest(list,"weixin_wifi_");
				}
			}
		}
    })
}
function weixin_wifi_query(){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"wifiController/getDeviceList",
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_tbody").html($.render.weixin_wifi_tr(msg.data));
			}else{
					alertError(msg.info,"wifi_weixin_alert");
			}
		}
    })
}
function weixin_wifi_change(b,name,tokens,id){
	$("#window").html($.render.weixin_wifi_add_window_temp());
	$("#weixin_wifi_add_window").modal("show");
	$('#addwifiopen_closs_wifi').bootstrapSwitch('state',!b);
	$('#weixin_wifi_name').val(name);
	$('#weixin_wifi_tokens').val(tokens);
	$("#wifi_add_button").attr("onclick","weixin_wifi_add_enter("+id+")");
}
function delete_wifi_info(){
	ui.confirm("删除wifi设备节点",function(b){
		if(b){
			var i=$.layer({
		    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"wifiController/deleteDevice",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						alertSuccess(msg.info,"wifi_weixin_alert");
					}else{
						alertError(msg.info,"wifi_weixin_alert");
					}
				}
			})
		}else{
			
		}
	},true);
}
function weixin_wifi_query_client_show(id,page){
	$("#window").html($.render.weixin_wifi_lookClient_window_temp());
	$("#weixin_wifi_lookClient_window").modal("show");
	weixin_wifi_query_client(id,page);
}
function weixin_wifi_query_client(id,page){
	
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"wifiController/lookLinkDevice",
		data:{
			id:id,
			nowpage:page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
			
				$("#weixin_wifi_lookClient_window_body").html($.render.weixin_wifi_lookClient_window_temp_tr(msg.data));
				var page_html="<li><a href=\"javascript:weixin_wifi_query_client('"+id+"','"+(parseInt(page)-1)+"')\">«</a></li>";
				var startPage=page>15?page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:weixin_wifi_query_client('"+id+"','"+ii+"')\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:weixin_wifi_query_client('"+id+"','"+(parseInt(page)+1)+"')\">»</a></li>"
				$("#weixin_wifi_lookClient_window_page").html(page_html);
			}else{
				inputAjaxTest(msg.info,"wifi_weixin_alert");
			}
		}
	})
}
function weixin_wifi_client_openClose(id){

ui.confirm("是否转变该用户设备启用状态",function(b){
		if(b){
			var i=$.layer({
		    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"wifiController/addMac",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$("#weixin_wifi_openclose_but_"+id).html(msg.data?"开":"关");
					}else{
						alertError(msg.info,"wifi_weixin_alert");
					}
				}
			})
		}else{
			
		}
	},true);
}
function weixin_wifi_open_wifirigister(id){

	ui.confirm("是否转换wifi登录方式。",function(b){
			if(b){
				var i=$.layer({
			    type : 3
				});
				$.ajax({
					type : "POST",
					url : ctx+"wifiController/addWifiRigister",
					data:{
						id:id
					},
					success : function(msg) {
						layer.close(i);
						if(msg.success){
							$("#weixin_wifi_open_wifirigister_"+id).html(msg.data?"开":"关");
						}else{
							alertError(msg.info,"wifi_weixin_alert");
						}
					}
				})
			}else{
				
			}
		},true);
	}