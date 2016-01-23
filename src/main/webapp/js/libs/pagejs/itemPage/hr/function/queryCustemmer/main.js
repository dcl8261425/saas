var hr_queryCustemmer_serch_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		$('#hr_queryCustemmer_window').modal("show");
	})
	function initTemp(){
		var tmpl = $("#hr_queryCustemmer_window_tbody_tr_select").html().replace("<!--", "").replace("-->", "");
		$.templates({
			hr_queryCustemmer_window_tbody_tr_select : tmpl
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
					$("#hr_queryCustemmer_window_tbody").html($.render.hr_queryCustemmer_window_tbody_tr_select(msg.data));
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
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"hr/function/lookCustemmerInfo",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#hr_queryCustemmer_window_username").html(msg.obj.username);
				$("#hr_queryCustemmer_window_price").html(msg.obj.price);
				$("#hr_queryCustemmer_window_address").html(msg.obj.address);
				$("#hr_queryCustemmer_window_stute").html(msg.obj.state);
				$("#hr_queryCustemmer_window_idnum").html(msg.obj.idNum);
				$("#hr_queryCustemmer_window_marks").html(msg.obj.marks);
				$("#hr_main_infoCustemmer_iamge").attr("src",ctx+"hr/page/fileManager/getImage?filename="+msg.obj.image);
				$("#hr_main_infoCustemmer_idimage").attr("src",ctx+"hr/page/fileManager/getIdImage?filename="+msg.obj.idImage);
				$("#hr_queryCustemmer_info_window").modal("show");
			}else{
				alertError(msg.info,"hr_queryCustemmer_AlertDiv");
			}
		}
	})
}