var window_inorder_look_tr_eleid="";
$(function () {
	$(document).ready(function(){
		initTemp();
		window_inOrder_query();
	})
	function initTemp(){
		var tmpl = $("#inOrder_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inOrder_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#inOrder_queryData_window_look_td").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inOrder_queryData_window_look_td : tmpl
		});
	}
	
});
function window_inOrder_query(){
	addLoadData_div("inOrder_layout_window");
    $.ajax({
		type : "POST",
		url : ctx+"goods/function/queryInOrder",
		data:{
			name:"",
			startDate:getNowFormatDate(true),
			endDate:getNowFormatDate(),
			nowpage:1,
			countNum:10
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#inOrder_queryData_window_tbody").html($.render.inOrder_queryData_window_tbody_tr(msg.data));
				
			}else{
					alertError(msg.info,"goods_window_layout_inOrders_alert_div");
			}
			removeloadData_div("inOrder_layout_window");
		}
    })
}
/**
 * 入库订单
 */
function window_inOrder_box_inOrder(id){
	ui.confirm("确定入库？",function(b){
		if(b){
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"goods/function/InOrderInStore",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						window_inOrder_query()
					}else{
						alertError(msg.info,"goods_window_layout_inOrders_alert_div");
					}
				}
			})
		}
	})
}
/**
 *删除订单
 */
function window_inOrder_box_delete(id){
	ui.confirm("确定删除?",function(b){
		if(b){
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"goods/function/deleteInOrder",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						window_inOrder_query();
					}else{
						alertError(msg.info,"goods_window_layout_inOrders_alert_div");
					}
				}
			})
		}
	})
}
function window_inOrder_box_look(id,eleid){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryInOrderItem",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success){
				if(window_inorder_look_tr_eleid!="");{
					$("#"+window_inorder_look_tr_eleid).hide();
				}
				$("#"+eleid).html($.render.inOrder_queryData_window_look_td(msg));
				$("#"+eleid).show();
				window_inorder_look_tr_eleid=eleid;
			}else{
				alertError(msg.info,"goods_window_layout_inOrders_alert_div");
			}
		}
	})
}