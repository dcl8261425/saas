var window_outorder_look_tr_eleid="";
$(function () {
	$(document).ready(function(){
		initTemp();
		window_outOrder_query();
	})
	function initTemp(){
		var tmpl = $("#outOrder_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			outOrder_queryData_window_tbody_tr : tmpl
		});
	}
	var tmpl = $("#outOrder_queryData_window_look_td").html().replace("<!--", "").replace("-->", "");
	$.templates({
		outOrder_queryData_window_look_td : tmpl
	});

});
function window_outOrder_query(){
	addLoadData_div("outOrder_layout_window");
    $.ajax({
		type : "POST",
		url : ctx+"goods/function/queryOrder",
		data:{
			name:"",
			startDate:getNowFormatDate(true),
			endDate:getNowFormatDate(),
			nowpage:1,
			countNum:10
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#outOrder_queryData_window_tbody").html($.render.outOrder_queryData_window_tbody_tr(msg.data));
				
			}else{
					alertError(msg.info,"goods_window_layout_outOrders_alert_div");
			}
			removeloadData_div("outOrder_layout_window");
		}
    })
}
function window_outOrder_box_inOrder(id){
	ui.confirm("确定入库？",function(b){
		if(b){
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"goods/function/OrderInStore",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						if(inOrder_query_type=="num"){
							inorder_query_num();
						}else{
							inorder_query_name();
						}
					}else{
						alertError(msg.info,"goods_window_layout_outOrders_alert_div");
					}
				}
			})
		}
	})
}
/**
 *删除订单
 */
function window_outOrder_box_delete(id){
	ui.confirm("确定删除?",function(b){
		if(b){
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"goods/function/deleteOrder",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						window_outOrder_query();
					}else{
						alertError(msg.info,"goods_window_layout_outOrders_alert_div");
					}
				}
			})
		}
	})
}
function window_outOrder_box_look(id,eleid){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryOrderItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				if(window_outorder_look_tr_eleid!="");{
					$("#"+window_outorder_look_tr_eleid).hide();
				}
				$("#"+eleid).html($.render.outOrder_queryData_window_look_td(msg));
				$("#"+eleid).show();
				window_outorder_look_tr_eleid=eleid;
			}else{
				alertError(msg.info,"goods_window_layout_outOrders_alert_div");
			}
		}
	})
}