var hr_addcustemmer_upload_Idimage;
var hr_addcustemmer_upload_image
$(function () {
	$(document).ready(function(){
		initbindEvent();
		$('#good_addData_window').modal("show");
	})
	function initTemp(){
		
	}
	function initbindEvent(){
		
	}
});
function hr_main_addcustemmer_enter(){
	var username=$("#hr_main_addcustemmer_username").val();
	var password=$("#hr_main_addcustemmer_password").val();
	var name=$("#hr_main_addcustemmer_name").val();
	var phone=$("#hr_main_addcustemmer_phone").val();
	var email=$("#hr_main_addcustemmer_email").val();
	var useLogin=$("#hr_main_addcustemmer_useLogin").val();
	var image=$("#hr_main_addcustemmer_image_texts").val();
	var idimage=$("#hr_main_addcustemmer_idimage_texts").val();
	var stute=$("#hr_main_addcustemmer_stute").val();
	var idnum=$("#hr_main_addcustemmer_idnum").val()
	var sex=$("#hr_main_addcustemmer_sex").val();
	var address=$("#hr_main_addcustemmer_address").val();
	var price=$("#hr_main_addcustemmer_price").val();
	var marks=$("#hr_main_addcustemmer_marks").val();
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"hr/function/addCustemmer",
		data:{
			username:username,
			password:password,
			name:name,
			phone:phone,
			email:email,
			useLogin:useLogin,
			image:image,
			idimage:idimage,
			stute:stute,
			idnum:idnum,
			sex:sex,
			address:address,
			price:price,
			marks:marks
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				alertSuccess(msg.info,"alertDiv");
				$('#good_addData_window').modal("hide");
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					
					alertError(msg.info,"Hr_add_window_AlertDiv");
					
				}else{
					inputAjaxTest(list,"hr_main_addcustemmer_");
				}
			}
		}
    })
}
function hr_main_addcustemmer_select_idimageFile(){
	$("#hr_main_addcustemmer_idimage_text_file").click();
}
function hr_main_addcustemmer_select_imageFile(){
	$("#hr_main_addcustemmer_image_text_file").click();
}
function hr_main_addcustemmrt_idimageFileChange(){
	var value=$("#hr_main_addcustemmer_idimage_text_file").val();
	$("#hr_main_addcustemmer_idimage_texts").val(value);
	$("#hr_main_addcustemmer_idimage_text_propress").show();
	$("#hr_main_addcustemmer_idimage_text_input").hide();
	$("#hr_main_addcustemmer_idimage_text_propress_val").attr("style","width:0%")
	$("#hr_main_addcustemmer_idimage_text_propress_val").html("下载进度:0%")
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'hr/page/fileManager/uploadIdImage',
			fileElementId:'hr_main_addcustemmer_idimage_text_file',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					//$("#hr_main_addcustemmer_idimage_text_input").show();
					$("#hr_main_addcustemmer_idimage_texts").val(json.url);
					//$("#hr_main_addcustemmer_idimage_text_propress_val").attr("style","width:100%")
					//$("#hr_main_addcustemmer_idimage_text_propress_val").html("上传进度:100%")
					//$("#hr_main_addcustemmer_idimage_text_propress").hide();
					$("#hr_main_addcustemmer_idimage_img_a").show();
					$("#hr_main_addcustemmer_idimage_img").attr("src",ctx+"hr/page/fileManager/getIdImage?filename="+json.url);
				}else{
					$("#hr_main_addcustemmer_idimage_text_input").show();
					$("#hr_main_addcustemmer_idimage_texts").val(json.info);
					$("#hr_main_addcustemmer_idimage_text_propress").hide();
					clearInterval(hr_addcustemmer_upload_Idimage);
				}
			}
		}
	)
	 hr_addcustemmer_upload_Idimage=setInterval(function (){
		 $.ajax({
				type : "POST",
				url : ctx+"hr/page/fileManager/IduploadImagePro",
				success : function(msg) {
					$("#hr_main_addcustemmer_idimage_text_propress_val").attr("style","width:"+msg.data+"%")
					$("#hr_main_addcustemmer_idimage_text_propress_val").html("上传进度:"+msg.data+"%")
					if(msg.data=='100'){
						$("#hr_main_addcustemmer_idimage_text_input").show();
						$("#hr_main_addcustemmer_idimage_text_propress").hide();
						clearInterval(hr_addcustemmer_upload_Idimage);
					}
				}
		 })
	 },100); 
}
function hr_main_addcustemmrt_imageFileChange(){
	var value=$("#hr_main_addcustemmer_image_text_file").val();
	$("#hr_main_addcustemmer_image_texts").val(value);
	$("#hr_main_addcustemmer_iamge_text_propress").show();
	$("#hr_main_addcustemmer_iamge_text_input").hide();
	$("#hr_main_addcustemmer_iamge_text_propress_val").attr("style","width:0%")
	$("#hr_main_addcustemmer_iamge_text_propress_val").html("上传进度:0%")
	$.ajaxFileUpload
	(
		{
			type : "POST",
			//dataType:"json",
			url:ctx+'hr/page/fileManager/uploadImage',
			fileElementId:'hr_main_addcustemmer_image_text_file',
			success: function (data)
			{
				json=eval("("+$(data).text()+")");//获取成为json 因为ajaxFileUpload无法自动转换为json
					
				if(json.success){
					$("#hr_main_addcustemmer_image_texts").val(json.url);
					$("#hr_main_addcustemmer_iamge_img_a").show();
					$("#hr_main_addcustemmer_iamge_img").attr("src",ctx+"hr/page/fileManager/getImage?filename="+json.url);
				}else{
					$("#hr_main_addcustemmer_iamge_text_input").show();
					$("#hr_main_addcustemmer_image_texts").val(json.info);
					$("#hr_main_addcustemmer_iamge_text_propress").hide();
					clearInterval(hr_addcustemmer_upload_image);
				}
			}
		}
	)
	 hr_addcustemmer_upload_image=setInterval(function (){
		 $.ajax({
				type : "POST",
				url : ctx+"hr/page/fileManager/uploadImagePro",
				success : function(msg) {
					$("#hr_main_addcustemmer_iamge_text_propress_val").attr("style","width:"+msg.data+"%")
					$("#hr_main_addcustemmer_iamge_text_propress_val").html("上传进度:"+msg.data+"%")
					if(msg.data=='100'){
						$("#hr_main_addcustemmer_iamge_text_propress").hide();
						$("#hr_main_addcustemmer_iamge_text_input").show();
						clearInterval(hr_addcustemmer_upload_image);
					}
				}
		 })
	 },100); 
}
