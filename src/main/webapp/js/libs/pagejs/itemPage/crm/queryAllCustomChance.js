var myCreateCustomChance_page=1;
var createChanceAdd_window_type;
var createChance_id;
var createChance_Window_functionType;
var addlinkman_id;
var addlinkman_type;
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
	})
	function initTemp(){
		var tmpl = $("#myCreateCustomChance_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			myCreateCustomChance_tbody_tr : tmpl
		});
	}
	function initbindEvent(){
		query_myCreateChance();
	}
});
function query_myCreateChance(page){
	if(page!=undefined||page!=null){
		myCreateCustomChance_page=page;
	}
	var ChanceName=$("#myCreateCustomChance_query_ChanceName").val();
	addLoadData_div("myCreateCustomChance_div");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryAllChance",
		data:{
			ChanceName:ChanceName,
			nowpage:myCreateCustomChance_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#myCreateCustomChance_tbody").html($.render.myCreateCustomChance_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:query_myCreateChance('"+(parseInt(myCreateCustomChance_page)-1)+"')\">«</a></li>";
				var startPage=myCreateCustomChance_page>15?myCreateCustomChance_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=myCreateCustomChance_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:query_myCreateChance('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:query_myCreateChance('"+(parseInt(myCreateCustomChance_page)+1)+"')\">»</a></li>"
				$("#myCreateCustomChance_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"myCreateCustomChance_div_alert");
			}
			removeloadData_div("myCreateCustomChance_div");
		}
	})
}
function createChance(type,id,functionType){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/page/createChanceWindow",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				createChanceAdd_window_type=type;
				createChance_id=id;
				createChance_Window_functionType=functionType;
				functionLayoutRefresh(msg);
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
function addLinkMan(id,type){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/page/addLinkMan",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				addlinkman_id=id;
				addlinkman_type=type;
				functionLayoutRefresh(msg);
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
function deleteMyCreateChance(id){
	ui.confirm("您是否确定要删除此机会。机会沟通记录，以及联系人等信息都会被清空",function(b){
		if(b){
			var i=$.layer({
		    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"crm/function/DeleteChance",
				data:{
					ChanceId:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$('#createChance_window').modal("hide");
						alertSuccess(msg.info,"content");
						if( typeof query_myCreateChance === 'function' ){
							query_myCreateChance();
						}
					}else{
						var list=msg.list;
						if(list==undefined||list==null){
							alertError(msg.info,"content");
						}else{
							inputAjaxTest(list,"createChance_");
						}
					}
				}
			})
		}else{
			
		}
	},true);
	
}
function chanceOpenInfo(id,type){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/page/chanceOpenInfo",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				addlinkman_id=id;
				functionLayoutRefresh(msg);
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}
function chanceOpenMarks(id,type){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"crm/page/chanceOpenMarks",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				addlinkman_id=id;
				functionLayoutRefresh(msg);
			}else{
				alertError(msg.info,"content");
			}
		}
    })
}