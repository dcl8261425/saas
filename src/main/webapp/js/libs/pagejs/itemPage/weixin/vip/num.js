var weixin_vip_num=1;
$(function () {
$(document).ready(function(){
		initTemp();
		initbindEvent();
		weixin_vip_query(1);
	})
	function initTemp(){
		var tmpl = $("#weixin_vip_list_item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_vip_list_item : tmpl
		});

	};

	function initbindEvent(){
		
	}
})
function weixin_vip_query(page){
	if(page!=undefined){
		weixin_vip_num=page;
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/public/getvipzhongjiangjuan",
		data:{
			nowpage:weixin_vip_num
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				$("#weixin_vip_content").html($.render.weixin_vip_list_item(msg.data));
			}else{
				
			}
		}
	})
}
function weixin_vip_up(){
	if(weixin_vip_num>1){
		weixin_vip_num=weixin_vip_num-1;
	}
	weixin_vip_query();
}
function weixin_vip_next(){
		weixin_vip_num=weixin_vip_num+1;
		weixin_vip_query();
}
$.views.converters("JavaSTojsDate", function(val) {
	 var date=new Date(val);
	 var y=date.getFullYear();
	 var m=date.getMonth();
	 var d=date.getDate();
	 var s=y+"-"+(m+1)+"-"+d;
	 if(s=='1970-1-1'){
		return '未设置或未结束';
	}else{
		 return s;
	}
});