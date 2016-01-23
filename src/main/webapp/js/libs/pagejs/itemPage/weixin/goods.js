var weixin_good_queryData_serch_page=1;
var weixin_good_queryData_serch_b=true;
var weixin_good_add_edit;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#weixin_good_yishangjia").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_good_yishangjia : tmpl
		});
		var tmpl = $("#weixin_good_weishangjia").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_good_weishangjia : tmpl
		});
		var tmpl = $("#good_yishangjia_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			good_yishangjia_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#good_weishangjia_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			good_weishangjia_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#weixin_goods_selectType_Item").html().replace("<!--", "").replace("-->", "");
		$.templates({
			weixin_goods_selectType_Item : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
function weixin_goods_yishangjia(){
	$("#right_layout").html($.render.weixin_good_yishangjia());
}
function weixin_goods_weishangjia(){
	$("#right_layout").html($.render.weixin_good_weishangjia());
}
function weixin_goods_add(){
	$("#weixin_goods_add").modal("show");
	$("#weixin_goods_add_name").unbind("input");
	$("#weixin_goods_add_goodname").bind("input",function(){
		var shouzimu=pinyin.getCamelChars($("#weixin_goods_add_goodname").val());
		$("#weixin_goods_add_spell").val(shouzimu);
	})
	if(weixin_good_add_edit==undefined){
		weixin_good_add_edit = KindEditor.create('#weixin_goods_add_marks', { 
			uploadJson : ctx+'fileSrc/uploadImage',
			fileManagerJson : ctx+'fileSrc/getImageList',
			
			allowFileManager : true      });   //正确
	}
	$("#weixin_goods_add_enter").attr("onclick","weixin_goods_add_enter()");
}
function weixin_goods_add_enter(){
	var goodname=$("#weixin_goods_add_goodname").val();
	var goodsType=$("#weixin_goods_add_goodsType").val();
	var goodsSelectType=$("#weixin_goods_add_goodsSelectType").val();
	var goodsModel=$("#weixin_goods_add_goodsModel").val()
	var price=$("#weixin_goods_add_price").val()
	var inPrice=$("#weixin_goods_add_inPrice").val()
	var score=$("#weixin_goods_add_score").val()
	var goodsNum=$("#weixin_goods_add_goodsNum").val()
	var spell=$("#weixin_goods_add_spell").val()
	var image1=$("#weixin_goods_add_image1").val()
	var image2=$("#weixin_goods_add_image2").val()
	var image3=$("#weixin_goods_add_image3").val()
	var image4=$("#weixin_goods_add_image4").val()
	var marks=weixin_good_add_edit.html();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"weixin/goods/add",
		data:{
			goodname:goodname,
			goodsType:goodsType,
			goodsModel:goodsModel,
			price:price,
			inPrice:inPrice,
			score:score,
			goodsNum:goodsNum,
			spell:spell,
			image1:image1,
			image2:image2,
			image3:image3,
			image4:image4,
			marks:marks,
			goodsSelectType:goodsSelectType
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#weixin_goods_add').modal("hide");
				alertSuccess(msg.info,"queryDate_update_price_window_AlertDiv");
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					alertError(msg.info,"weixin_goods_alert");
				}else{
					inputAjaxTest(list,"weixin_goods_add_");
				}
			}
		}
	})
}
function weixin_goods_query_data(b,page){
	weixin_good_queryData_serch_b=b;
	var name=$("#weixin_goods_queryName").val();
	if(page!=undefined||page!=null){
		weixin_good_queryData_serch_page=page;
	}
	//已上架b是true 未上架b是false
	var i=$.layer({
    			type : 3
			});
	$.ajax({
		type : "POST",
		url :ctx+"weixin/goods/query",
		data:{
			b:b,
			name:name,
			nowpage:weixin_good_queryData_serch_page
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				if(b){
					$("#weixin_tbody").html($.render.good_yishangjia_queryData_window_tbody_tr(msg.data));
				}else{
					$("#weixin_tbody").html($.render.good_weishangjia_queryData_window_tbody_tr(msg.data));
				}
				var page_html="<li><a href=\"javascript:weixin_goods_query_data("+b+"','"+(parseInt(weixin_good_queryData_serch_page)-1)+"')\">«</a></li>";
				var startPage=weixin_good_queryData_serch_page>15?weixin_good_queryData_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=weixin_good_queryData_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:weixin_goods_query_data("+b+"','"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:weixin_goods_query_data("+b+"','"+(parseInt(weixin_good_queryData_serch_page)+1)+"')\">»</a></li>"
				$("#weixin_goods_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"weixin_goods_alert");
			}
		}
	})
}
function weixin_goods_down(id){

	var i=$.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url :ctx+"weixin/goods/xiajia",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_goods_query_data(weixin_good_queryData_serch_b,weixin_good_queryData_serch_page);
			}else{
				alertError(msg.info,"weixin_goods_alert");
			}
		}
	})
}
function weixin_goods_up(id){

	var i=$.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url :ctx+"weixin/goods/shangjia",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_goods_query_data(weixin_good_queryData_serch_b,weixin_good_queryData_serch_page);
			}else{
				alertError(msg.info,"weixin_goods_alert");
			}
		}
	})
}
function weixin_goods_delete(id){
	var i=$.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url :ctx+"weixin/goods/delete",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				weixin_goods_query_data(weixin_good_queryData_serch_b,weixin_good_queryData_serch_page);
			}else{
				alertError(msg.info,"weixin_goods_alert");
			}
		}
	})
}
function queryWeixinGoodsType(){
	var i=$.layer({
		type : 3
	});
	$.ajax({
		type : "POST",
		url :ctx+"weixin/goods/query",
		success : function(msg) {
			layer.close(i);
			
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#weixin_goods_select_type").modal("show");
				$("#weixin_goods_seletType_list").html($.render.weixin_goods_selectType_Item(msg.data));
			}else{
				alertError(msg.info,"weixin_goods_alert");
			}
		}
	})
}
function selectType(id,name){
	$("#weixin_goods_add_goodsSelectType").val(name);
	$("#weixin_goods_select_type").modal("hide");
}