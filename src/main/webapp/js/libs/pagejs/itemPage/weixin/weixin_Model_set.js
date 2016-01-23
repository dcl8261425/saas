var weixin_model_row_num=1;
var weixin_model_manager_page=1;
var image_list_num=0;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_model_createNewModel").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_model_createNewModel : tmpl
		});
		var tmpl = $("#weixin_model_row").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_model_row : tmpl
		});
		var tmpl = $("#weixin_model_col").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_model_col : tmpl
		});


	}
	function initbindEvent(){
		
	}
})
function weixin_model_createModel(){
	$("#weixin_model_body").html($.render.weixin_model_createNewModel());
}
function weixin_model_create_layout(){
	$("#weixin_model_create_layout").modal("show");
}
function weixin_model_create_layout_add_new_row(){

	$("#weixin_model_create_layout_main_row").html($("#weixin_model_create_layout_main_row").html()+$.render.weixin_model_row({"num":weixin_model_row_num}));
	weixin_model_row_num++;
}
function weixin_model_create_layout_enter(){
	$("#weixin_model_create_layout_main_row .create_col").remove();
	//$("#weixin_model_create_layout_main_row .delete_col").remove();
	$("#main_layout").append($("#weixin_model_create_layout_main_row").html());
	$("#weixin_model_create_layout_main_row").html("");
	$("#weixin_model_create_layout").modal("hide");
	$("#main_layout .row-tr").sortable({
	        placeholder: "sort-highlight",
	        connectWith: ".row-tr",
	        forcePlaceholderSize: true,
	        zIndex: 999999
	    }).disableSelection();
}
function weixin_model_row_create_col(id){
	var num=0;
	var num2=0;
	for(var i=0;i<=3;i++){
		if($("#col_"+id+"_"+i).length>0){
			num++;
		}
	}
	if(num>3){
		ui.alert("每行最多创建四列。",2000,false);
		return ;
	}
	if(num==0){
		num2=12;
	}
	if(num==1){
		num2=6;
		$("#col_"+id+"_0").attr("class","col-xs-6 row-tr sort-highlight no-padding");
	}
	if(num==2){
		num2=4;
		$("#col_"+id+"_0").attr("class","col-xs-4 row-tr sort-highlight no-padding");
		$("#col_"+id+"_1").attr("class","col-xs-4 row-tr sort-highlight no-padding");
	}
	if(num==3){
		num2=3;
		$("#col_"+id+"_0").attr("class","col-xs-3 row-tr sort-highlight no-padding");
		$("#col_"+id+"_1").attr("class","col-xs-3 row-tr sort-highlight no-padding");
		$("#col_"+id+"_2").attr("class","col-xs-3 row-tr sort-highlight no-padding");
	}
	$("#row_"+id).html($("#row_"+id).html()+$.render.weixin_model_col({"num":num,"id":id,"num2":num2}));
}
function weixin_model_row_delete(id){
	$("#row_"+id).remove();
}
function weixin_model_col_delete(num,id){
	var row_num=$("#row_"+id+" .row-tr").length;
	if(row_num==1){
		$("#col_"+id+"_"+num).remove();
	}
	if(row_num==2){
		if(num==0){
			$("#col_"+id+"_1").attr("class","col-xs-12 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1 button").attr("onclick","weixin_model_col_delete(0,"+id+")");
			$("#col_"+id+"_1").attr("id","col_"+id+"_0");
		}
		if(num==1){
			$("#col_"+id+"_0").attr("class","col-xs-12 row-tr sort-highlight no-padding");
		}
		$("#col_"+id+"_"+num).remove();
	}
	if(row_num==3){
		if(num==0){
			$("#col_"+id+"_1").attr("class","col-xs-6 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1 button").attr("onclick","weixin_model_col_delete(0,"+id+")");
			$("#col_"+id+"_1").attr("id","col_"+id+"_0");
			$("#col_"+id+"_2").attr("class","col-xs-6 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2 button").attr("onclick","weixin_model_col_delete(1,"+id+")");
			$("#col_"+id+"_2").attr("id","col_"+id+"_1");
			
		}	
		if(num==1){
			$("#col_"+id+"_0").attr("class","col-xs-6 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2").attr("class","col-xs-6 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2 button").attr("onclick","weixin_model_col_delete(1,"+id+")");
			$("#col_"+id+"_2").attr("id","col_"+id+"_1");
		}
		if(num==2){
			$("#col_"+id+"_0").attr("class","col-xs-6 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1").attr("class","col-xs-6 row-tr sort-highlight no-padding");
		}
		$("#col_"+id+"_"+num).remove();
	}
	if(row_num==4){
		if(num==0){
			$("#col_"+id+"_1").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1 button").attr("onclick","weixin_model_col_delete(0,"+id+")");
			$("#col_"+id+"_1").attr("id","col_"+id+"_0");
			$("#col_"+id+"_2").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2 button").attr("onclick","weixin_model_col_delete(1,"+id+")");
			$("#col_"+id+"_2").attr("id","col_"+id+"_1");
			$("#col_"+id+"_3").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_3 button").attr("onclick","weixin_model_col_delete(2,"+id+")");
			$("#col_"+id+"_3").attr("id","col_"+id+"_2");

		}
		if(num==1){
			$("#col_"+id+"_0").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2 button").attr("onclick","weixin_model_col_delete(1,"+id+")");
			$("#col_"+id+"_2").attr("id","col_"+id+"_1");
			$("#col_"+id+"_3").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_3 button").attr("onclick","weixin_model_col_delete(2,"+id+")");
			$("#col_"+id+"_3").attr("id","col_"+id+"_2");
		}
		if(num==2){
			$("#col_"+id+"_0").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_3").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_3 button").attr("onclick","weixin_model_col_delete(2,"+id+")");
			$("#col_"+id+"_3").attr("id","col_"+id+"_2");
		}
		if(num==3){
			$("#col_"+id+"_0").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_1").attr("class","col-xs-4 row-tr sort-highlight no-padding");
			$("#col_"+id+"_2").attr("class","col-xs-4 row-tr sort-highlight no-padding");
		}
		$("#col_"+id+"_"+num).remove();
	}
	
}
function weixin_model_create_dazhuanpan(){
	$("#weixin_model_create_dazhuanpan").modal("show");
	initWeixin_model_dazhuanpan();
}
function initWeixin_model_dazhuanpan(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("大转盘模块(点击此模块进入大转盘模块)");
	var tmpl = $("#weixin_model_dazhuanpan").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_input").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_input : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_dazhuanpan());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_input());
	$("#weixin_model_main").find("#weixin_model_tem_layout").attr("onclick","weixin_model_dazhuanpan_but()");
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
}
function initWeixin_model_guaguaka(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("刮刮卡模块(点击此模块进入刮刮卡界面)");
	var tmpl = $("#weixin_model_dazhuanpan").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_input").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_input : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_dazhuanpan());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_input())
	$("#weixin_model_main").find("#weixin_model_tem_layout").attr("onclick","weixin_model_guagua_but()");
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
}
function initWeixin_model_vip(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("会员模块(点击此模块进入会员界面)");
	var tmpl = $("#weixin_model_dazhuanpan").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_input").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_input : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_dazhuanpan());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_input())
	$("#weixin_model_main").find("#weixin_model_tem_layout").attr("onclick","weixin_model_vip_but()");
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
}
function initWeixin_model_jifenduihuan(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("积分兑换模块(点击进入积分兑换页面)");
	var tmpl = $("#weixin_model_jifenduihuan").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_jifenduihuan : tmpl
	});
	var tmpl = $("#weixin_model_jifenduihuan_set_input").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_jifenduihuan_set_input : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_jifenduihuan());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_jifenduihuan_set_input())
	$("#weixin_model_main").find("#weixin_model_tem_layout").attr("onclick","weixin_model_jifenduihuan_but()");
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
}
function initWeixin_model_goodsShop(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("商店模块(点击此模块进入货物界面)");
	var tmpl = $("#weixin_model_dazhuanpan").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_input").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_input : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_dazhuanpan());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_input())
	$("#weixin_model_main").find("#weixin_model_tem_layout").attr("onclick","weixin_model_shop_but()");
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
}
function initWeixin_model_imageModel(){
	$("#weixin_model_set_icon").hide();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("图片模块(点击此模块进入自己填写的链接地址)");
	var tmpl = $("#weixin_model_imageModel").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_imageModel : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_imageModel").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_imageModel : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_imageModel());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_imageModel())
	$('#open_closs_mohu').bootstrapSwitch('state',true);
	$('#open_closs_mohu').on("switchChange",function(e,data){
		var value=data.value;
		if(value){
			$("#weixin_model_main").find("#weixin_model_imageModel_img").attr("class",$("#weixin_model_imageModel_img").attr("class")+" weixin-model-image-mohu");
		}else{
			var str=$("#weixin_model_main").find("#weixin_model_imageModel_img").attr("class").split("weixin-model-image-mohu");
			var str2="";
			for(var i=0;i<str.length;i++){
				str2=str2+str[i];
			}
			$("#weixin_model_main").find("#weixin_model_imageModel_img").attr("class",str2);
		}
	})
	$("#weixin_imagemodel_linkTo").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageModel_linktoLink").attr("href",$("#weixin_imagemodel_linkTo").val());
	})
	$("#weixin_imagemodel_link").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageModel_img").attr("src",$("#weixin_imagemodel_link").val());
	})
	$("#weixin_imagemodel_str1").bind("input",function(){
		$("#weixin_model_main").find("#weixin_imagemodel_str1_lable").html($("#weixin_imagemodel_str1").val());
	})
	$("#weixin_imagemodel_str2").bind("input",function(){
		$("#weixin_model_main").find("#weixin_imagemodel_str2_lable").html($("#weixin_imagemodel_str2").val());
	})
	$("#weixin_imagemodel_color1").bind("input",function(){
		$("#weixin_model_main").find("#weixin_imagemodel_str1_lable").attr("style","color:"+$("#weixin_imagemodel_color1").val()+";");
	})
	$("#weixin_imagemodel_color2").bind("input",function(){
		$("#weixin_model_main").find("#weixin_imagemodel_str2_lable").attr("style","color:"+$("#weixin_imagemodel_color2").val()+";");
	})
	$("#weixin_imagemodel_color1").minicolors({
		control: $(this).attr('data-control') || 'hue',
		defaultValue: $(this).attr('data-defaultValue') || '',
		inline: $(this).attr('data-inline') === 'true',
		letterCase: $(this).attr('data-letterCase') || 'lowercase',
		opacity: $(this).attr('data-opacity'),
		position: $(this).attr('data-position') || 'bottom left',
		change: function(hex, opacity) {
			if( !hex ) return;
			if( opacity ) hex += ', ' + opacity;
			try {
				console.log(hex);
			} catch(e) {}
		},
		theme: 'bootstrap'
	});
	$("#weixin_imagemodel_color2").minicolors({
		control: $(this).attr('data-control') || 'hue',
		defaultValue: $(this).attr('data-defaultValue') || '',
		inline: $(this).attr('data-inline') === 'true',
		letterCase: $(this).attr('data-letterCase') || 'lowercase',
		opacity: $(this).attr('data-opacity'),
		position: $(this).attr('data-position') || 'bottom left',
		change: function(hex, opacity) {
			if( !hex ) return;
			if( opacity ) hex += ', ' + opacity;
			try {
				console.log(hex);
			} catch(e) {}
		},
		theme: 'bootstrap'
	});
}
function initWeixin_model_mapModel(){
	$("#weixin_model_set_icon").show();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("到店地图模块(方便用户找到您的店铺)");
	var tmpl = $("#weixin_model_mapModel").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_mapModel : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_mapModel").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_mapModel : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_mapModel());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_mapModel());
	$("#weixin_dazhuanpan_title").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_title_input").html($("#weixin_dazhuanpan_title").val());
	})
	$("#weixin_dazhuanpan_content").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_content_input").html($("#weixin_dazhuanpan_content").val());
	})
	$("#weixin_dazhuanpan_botten").bind("input",function(){
		$("#weixin_model_main").find("#weixin_dazhuanpan_botten_input").html($("#weixin_dazhuanpan_botten").val());
	})
	$("#weixin_dazhuanpan_icon_size").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_tem_layout_ion").attr('style',"font-size: "+$("#weixin_dazhuanpan_icon_size").val()+"px;");
	})
	
}
function weixin_model_create_dazhuanpan_enter(){

		var isexet=false;
		var html=$("#weixin_model_web_add_model").find("#weixin_model_main").html();
		$("#main_layout .row-tr").each(function(){
			if($(this).html().trim()==""&&!isexet){
				$(this).html(html);
				isexet=true;
			}
		})
		$("#weixin_model_create_dazhuanpan").modal("hide");
}
function weixin_model_change_block_color(color){
	$("#weixin_model_web_add_model").find("#weixin_model_tem_layout").attr("class","small-box "+color);
}
function weixin_model_change_block_icon(color){
	$("#weixin_model_web_add_model").find("#weixin_model_tem_layout_ion").attr("class",color);
}
function weixin_model_save_model(){
	$("#main_layout .delete_col").remove();
	$("#main_layout .row-tr").attr("style","");
	$("#main_layout .row-tr").each(function(){
		var str=$(this).attr("class").split("sort-highlight");
		var str2="";
		for(var i=0;i<str.length;i++){
			str2=str2+str[i];
		}
		$(this).attr("class",str2);
	})
	var html=$("#main_layout").html();
	var arrayHtml=html.split("isimagetagtoendtag=\"\"");
	var html2="";
	for(var i=0;i<arrayHtml.length;i++){
		if(html2==""){
			html2=arrayHtml[i];
		}else{
			html2=html2+"/"+arrayHtml[i];
		}
	}
	if(html2!=""){
		html=html2;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/saveWeixinWeb",
		data:{
			html:html
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#main_layout").html("");
				ui.alert(msg.info,2000,false);
			}else{
				ui.alert(msg.info,2000,false);
			}
		}
    })
}
function weixin_model_manager(){
	var tmpl = $("#weixin_model_managerModel").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_managerModel : tmpl
	});
	
	$("#weixin_model_body").html($.render.weixin_model_managerModel());
	weixin_model_manager_pages(1);
}
function weixin_model_manager_pages(page){	
	if(page!=undefined||page!=null){
		weixin_model_manager_page=page;
	}
	var tmpl = $("#weixin_model_managerModel_body").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_managerModel_body : tmpl
	});
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/getWeixinWeb",
		data:{
			nowpage:weixin_model_manager_page,
			countNum:9
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#right_layout").html($.render.weixin_model_managerModel_body(msg.data));
			}else{
				alertError(msg.info,"weixin_model_manager_alert");
			}
		}
    })
	
}
function weixin_model_manager_delete(id){	
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/deleteWeixinWeb",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_model_manager_pages(1);
			}else{
				alertError(msg.info,"weixin_model_manager_alert");
			}
		}
    })
	
}
function weixin_model_manager_use(id){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"weixin/function/useWeixinWeb",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"weixin_model_manager_alert");
			}else{
				alertError(msg.info,"weixin_model_manager_alert");
			}
		}
    })
}
function initWeixin_model_imageList(){
	image_list_num=image_list_num+1;
	$("#weixin_model_set_icon").hide();
	$("#weixin_model_set_color").hide();
	$("#weixin_model_selet_model_button").html("图片轮询");
	var tmpl = $("#weixin_model_imageList").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_imageList : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_imageList").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_imageList : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_imageList({num:image_list_num}));
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_imageList())
	$('.carousel').carousel();
//1
	$("#weixin_model_imageList_1_link_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_1_link").attr("href",$("#weixin_model_imageList_1_link_input").val());
	})
	$("#weixin_model_imageList_1_image_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_1_image").attr("src",$("#weixin_model_imageList_1_image_input").val());
	})
	$("#weixin_model_imageList_1_text_1_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_1_text_1").html($("#weixin_model_imageList_1_text_1_input").val());
	})
	$("#weixin_model_imageList_1_text_2_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_1_text_2").html($("#weixin_model_imageList_1_text_2_input").val());
	})
	//2
	$("#weixin_model_imageList_2_link_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_2_link").attr("href",$("#weixin_model_imageList_2_link_input").val());
	})
	$("#weixin_model_imageList_2_image_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_2_image").attr("src",$("#weixin_model_imageList_2_image_input").val());
	})
	$("#weixin_model_imageList_2_text_1_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_2_text_1").html($("#weixin_model_imageList_2_text_1_input").val());
	})
	$("#weixin_model_imageList_2_text_2_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_2_text_2").html($("#weixin_model_imageList_2_text_2_input").val());
	})
	//3
	$("#weixin_model_imageList_3_link_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_3_link").attr("href",$("#weixin_model_imageList_3_link_input").val());
	})
	$("#weixin_model_imageList_3_image_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_3_image").attr("src",$("#weixin_model_imageList_3_image_input").val());
	})
	$("#weixin_model_imageList_3_text_1_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_3_text_1").html($("#weixin_model_imageList_3_text_1_input").val());
	})
	$("#weixin_model_imageList_3_text_2_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_3_text_2").html($("#weixin_model_imageList_3_text_2_input").val());
	})
	//4
	$("#weixin_model_imageList_4_link_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_4_link").attr("href",$("#weixin_model_imageList_4_link_input").val());
	})
	$("#weixin_model_imageList_4_image_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_4_image").attr("src",$("#weixin_model_imageList_4_image_input").val());
	})
	$("#weixin_model_imageList_4_text_1_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_4_text_1").html($("#weixin_model_imageList_4_text_1_input").val());
	})
	$("#weixin_model_imageList_4_text_2_input").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imageList_4_text_2").html($("#weixin_model_imageList_4_text_2_input").val());
	})
}
function initWeixin_model_imageText(){
	$("#weixin_model_set_icon").hide();
	$("#weixin_model_set_color").show();
	$("#weixin_model_selet_model_button").html("添加图文板块");
	var tmpl = $("#weixin_model_imageText").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_imageText : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_imagetext").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_imagetext : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_imageText());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_imagetext());
	$("#weixin_model_imagetext_text").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imagetext_text_layout").html($("#weixin_model_imagetext_text").val());
	})
	$("#weixin_model_imagetext_linkurl").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imagetext_linkurl_layout").attr("href",$("#weixin_model_imagetext_linkurl").val());
	})
	$("#weixin_model_imagetext_text_color").bind("input",function(){
		$("#weixin_model_main").find("#weixin_model_imagetext_text_layout").attr("style","border:0;padding:0px 10px;font-size:16px;font-weight:normal;line-height:26px;text-align:center;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;color:"+$("#weixin_model_imagetext_text_color").val()+";");
	})
	$("#weixin_model_imagetext_text_color").minicolors({
		control: $(this).attr('data-control') || 'hue',
		defaultValue: $(this).attr('data-defaultValue') || '',
		inline: $(this).attr('data-inline') === 'true',
		letterCase: $(this).attr('data-letterCase') || 'lowercase',
		opacity: $(this).attr('data-opacity'),
		position: $(this).attr('data-position') || 'bottom left',
		change: function(hex, opacity) {
			if( !hex ) return;
			if( opacity ) hex += ', ' + opacity;
			try {
				console.log(hex);
			} catch(e) {}
		},
		theme: 'bootstrap'
	});
}
function initWeixin_model_music(){
	$("#weixin_model_set_icon").hide();
	$("#weixin_model_set_color").hide();
	$("#weixin_model_selet_model_button").html("添加背景音乐");
	var tmpl = $("#weixin_model_music").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_music : tmpl
	});
	var tmpl = $("#weixin_model_dazhuanpan_set_music").html().replace("<!--", "").replace("-->", "");
	$.templates({
		weixin_model_dazhuanpan_set_music : tmpl
	});
	$("#weixin_model_web_add_model").find("#weixin_model_main").html($.render.weixin_model_music());
	$("#weixin_model_set_input_layout").html($.render.weixin_model_dazhuanpan_set_music());
	$("#weixin_model_music_link").bind("input",function(){
		$("#weixin_model_main").find("#audiocontainer").html("<audio id='bgsound' loop='loop' autoplay='autoplay'><source id='bgsound_source' src='"+$("#weixin_model_music_link").val()+"' isimagetagToEndTag> </audio>");
	})
}