var hr_queryCustemmer_serch_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#hr_queryCustemmer_tbody_tr_select").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_queryCustemmer_tbody_tr_select : tmpl
		});
	}
	function initbindEvent(){
	}
});
function hr_queryCustemmer_box_serch(page){
	var name=$("#hr_queryCustemmer_window_query_Name").val();
	if(page!=undefined||page!=null){
		hr_queryCustemmer_serch_page=page;
	}
	addLoadData_div("hr_queryCustemmer_Body");
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/queryCustemmer",
		data:{
			trueName:name,
			nowpage:hr_queryCustemmer_serch_page,
			countNum:10
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
					$("#hr_queryCustemmer_tbody").html($.render.hr_queryCustemmer_tbody_tr_select(msg.data));
				var page_html="<li><a href=\"javascript:hr_queryCustemmer_box_serch('"+(parseInt(hr_queryCustemmer_serch_page)-1)+"')\">«</a></li>";
				var startPage=hr_queryCustemmer_serch_page>15?hr_queryCustemmer_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=hr_queryCustemmer_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:hr_queryCustemmer_box_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:hr_queryCustemmer_box_serch('"+(parseInt(hr_queryCustemmer_serch_page)+1)+"')\">»</a></li>"
				$("#hr_queryCustemmer_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"hr_queryCustemmer_AlertDiv");
			}
			removeloadData_div("hr_queryCustemmer_Body");
		}
	})
}

function hr_queryCustemmer_window_select(id){
	addLoadData_div("hr_queryCustemmer_Body_info");
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/lookCustemmerInfo",
		data:{
			id:id
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_queryCustemmer_window_username").html(msg.obj.username);
				$("#hr_queryCustemmer_window_price").html(msg.obj.price);
				$("#hr_queryCustemmer_window_address").html(msg.obj.address);
				$("#hr_queryCustemmer_window_stute").html(msg.obj.state);
				$("#hr_queryCustemmer_window_idnum").html(msg.obj.idNum);
				$("#hr_queryCustemmer_window_marks").html(msg.obj.marks);
				$("#hr_queryCustemmer_window_trueName").html(msg.obj.trueName);
				$("#hr_queryCustemmer_window_email").html(msg.obj.email);
				$("#hr_queryCustemmer_window_sex").html(msg.obj.sex?"男":"女");
				$("#hr_queryCustemmer_window_useLogin").html(msg.obj.useLogin?"可用":"不可用");
				$("#hr_queryCustemmer_window_phone").html(msg.obj.phone);
				$("#hr_queryCustemmer_window_username").html(msg.obj.username);
				$("#hr_look_user_id").val(msg.obj.id);
				$("#hr_queryCustemmer_window_price_tag").show();
				$("#hr_queryCustemmer_window_address_tag").show();
				$("#hr_queryCustemmer_window_stute_tag").show();
				$("#hr_queryCustemmer_window_idnum_tag").show();
				$("#hr_queryCustemmer_window_marks_tag").show();
				$("#hr_queryCustemmer_window_trueName_tag").show();
				$("#hr_queryCustemmer_window_email_tag").show();
				$("#hr_queryCustemmer_window_sex_tag").show();
				$("#hr_queryCustemmer_window_useLogin_tag").show();
				$("#hr_queryCustemmer_window_phone_tag").show();
				$("#hr_main_infoCustemmer_iamge").attr("src",ctx+"hr/page/fileManager/getImage?filename="+msg.obj.image);
				$("#hr_main_infoCustemmer_idimage").attr("src",ctx+"hr/page/fileManager/getIdImage?filename="+msg.obj.idImage);
			}else{
				alertError(msg.info,"hr_queryCustemmer_AlertDiv");
			}
			removeloadData_div("hr_queryCustemmer_Body_info");
		}
	})
}
function hr_update_custemmer(id){
	if(id=="trueName"){
		var value=$("#hr_queryCustemmer_window_trueName").html();
		$("#hr_queryCustemmer_window_trueName").html("<input id='hr_queryCustemmer_window_trueName_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_trueName_input").val(value);
		$("#hr_queryCustemmer_window_trueName_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_trueName_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_trueName").html(values);
		})
		$("#hr_queryCustemmer_window_trueName_input").focus();
	}
	if(id=="email"){
		var value=$("#hr_queryCustemmer_window_email").html();
		$("#hr_queryCustemmer_window_email").html("<input id='hr_queryCustemmer_window_email_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_email_input").val(value);
		$("#hr_queryCustemmer_window_email_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_email_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_email").html(values);
		})
		$("#hr_queryCustemmer_window_email_input").focus();
	}
	if(id=="sex"){
		var value=$("#hr_queryCustemmer_window_sex").html();
		$("#hr_queryCustemmer_window_sex").html("<select id='hr_queryCustemmer_window_sex_input' class='form-control input-sm'><option value='true'>男</option><option value='false'>女</option></select>");
		$("#hr_queryCustemmer_window_sex_input").val(value);
		$("#hr_queryCustemmer_window_sex_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_sex_input").val();
			hr_update_custemmer_ajax(id,values);
			if(values=='true'){
				values="男";
			}else{
				values="女";
			}
			$("#hr_queryCustemmer_window_sex").html(values);
			
		})
		$("#hr_queryCustemmer_window_sex_input").focus();
	}
	if(id=="price"){
		var value=$("#hr_queryCustemmer_window_price").html();
		$("#hr_queryCustemmer_window_price").html("<input id='hr_queryCustemmer_window_price_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_price_input").val(value);
		$("#hr_queryCustemmer_window_price_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_price_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_price").html(values);
		})
		$("#hr_queryCustemmer_window_price_input").focus();
	}
	if(id=="address"){
		var value=$("#hr_queryCustemmer_window_address").html();
		$("#hr_queryCustemmer_window_address").html("<input id='hr_queryCustemmer_window_address_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_address_input").val(value);
		$("#hr_queryCustemmer_window_address_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_address_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_address").html(values);
		})
		$("#hr_queryCustemmer_window_address_input").focus();
	}
	if(id=="stute"){
		var value=$("#hr_queryCustemmer_window_stute").html();
		$("#hr_queryCustemmer_window_stute").html("<input id='hr_queryCustemmer_window_stute_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_stute_input").val(value);
		$("#hr_queryCustemmer_window_stute_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_stute_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_stute").html(values);
		})
		$("#hr_queryCustemmer_window_stute_input").focus();
	}
	if(id=="idnum"){
		var value=$("#hr_queryCustemmer_window_idnum").html();
		$("#hr_queryCustemmer_window_idnum").html("<input id='hr_queryCustemmer_window_idnum_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_idnum_input").val(value);
		$("#hr_queryCustemmer_window_idnum_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_idnum_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_idnum").html(values);
		})
		$("#hr_queryCustemmer_window_idnum_input").focus();
	}
	if(id=="useLogin"){
		var value=$("#hr_queryCustemmer_window_useLogin").html();
		$("#hr_queryCustemmer_window_useLogin").html("<select id='hr_queryCustemmer_window_useLogin_input' class='form-control input-sm'><option value='true'>可用</option><option value='false'>不可用</option></select>");
		$("#hr_queryCustemmer_window_useLogin_input").val(value);
		$("#hr_queryCustemmer_window_useLogin_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_useLogin_input").val();
			hr_update_custemmer_ajax(id,values);
			if(values=='true'){
				values="可用";
			}else{
				values="不可用";
			}
			$("#hr_queryCustemmer_window_useLogin").html(values);
		})
		$("#hr_queryCustemmer_window_useLogin_input").focus();
	}
	if(id=="phone"){
		var value=$("#hr_queryCustemmer_window_phone").html();
		$("#hr_queryCustemmer_window_phone").html("<input id='hr_queryCustemmer_window_phone_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_phone_input").val(value);
		$("#hr_queryCustemmer_window_phone_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_phone_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_phone").html(values);
		})
		$("#hr_queryCustemmer_window_phone_input").focus();
	}
	if(id=="marks"){
		var value=$("#hr_queryCustemmer_window_marks").html();
		$("#hr_queryCustemmer_window_marks").html("<input id='hr_queryCustemmer_window_marks_input' class='form-control input-sm'/>");
		$("#hr_queryCustemmer_window_marks_input").val(value);
		$("#hr_queryCustemmer_window_marks_input").blur(function(){
			var values=$("#hr_queryCustemmer_window_marks_input").val();
			hr_update_custemmer_ajax(id,values);
			$("#hr_queryCustemmer_window_marks").html(values);
		})
		$("#hr_queryCustemmer_window_marks_input").focus();
	}
}
function hr_update_custemmer_ajax(name,value){
	var id=$("#hr_look_user_id").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/updateCustemmerInfo",
		data:{
			id:id,
			name:name,
			value:value
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
					
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					
					alertError(msg.info,"hr_queryCustemmer_AlertDiv");
					
				}else{
					inputAjaxTest(list,"hr_queryCustemmer_window_");
				}
			}
		}
    })
}