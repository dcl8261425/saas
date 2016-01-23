var chanceOpenMarks_Query_page=1;
$(function () {
	$(document).ready(function(){
		initTemp();
		
		initDiv();
	})
	function initTemp(){
		var tmpl = $("#chanceOpenMarks_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenMarks_window_tbody_tr : tmpl
		});
		var tmpl = $("#chanceOpenMarks_window_add_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenMarks_window_add_window : tmpl
		});
		var tmpl = $("#chanceOpenMarks_window_look_window").html().replace("<!--", "").replace("-->", "");
		$.templates({
			chanceOpenMarks_window_look_window : tmpl
		});
	}
	function initDiv(){
		$('#chanceOpenMarks_window').modal("show");
		chanceOpenMarks_query_function_window();
	}
})
function chanceOpenMarks_window_add_window(){
	$('#chanceOpenMarks_window').modal("hide");
	
	ui.prompt("请输入标题",function(b){
		if(b){
			$("#chanceOpenMarks_window_add_window_div").html($.render.chanceOpenMarks_window_add_window());
			alertWarning("当鼠标移到相应的工具出稍等1秒及显示功能，编辑时，在节点双击字符，即可编辑。","chanceOpenMarks_window_add_window_alert");
			$('#chanceOpenMarks_window_add_but').modal("show");
				var property={
					width:960,
					height:500,
					headBtns:[],
					toolBtns:["start round","end","task","node","chat","state","plug","join","fork","complex"],
					haveHead:true,
					initLabelText:b,
					haveTool:true,
					haveGroup:true,
					useOperStack:true
				};
				var remark={
					cursor:"选择指针",
					direct:"转换连线",
					start:"开始结点",
					end:"结束结点",
					task:"任务结点",
					node:"自动结点",
					chat:"决策结点",
					state:"状态结点",
					plug:"附加插件",
					fork:"分支结点",
					join:"联合结点",
					complex:"复合结点",
					group:"组织划分框编辑开关"
				};
				demo=$.createGooFlow($("#chanceOpenMarks_window_add_window_Demo"),property);
				demo.setNodeRemarks(remark);
				$("#chanceOpenMarks_submit").bind("click",function(){
					var flowDate=JSON.stringify(demo.exportData());
					var i=$.layer({
					    type : 3
					});
					$.ajax({
						type : "POST",
						url : ctx+"crm/function/createFlow",
						data:{
							chanceId:addlinkman_id,
							name:b,
							jsonString:flowDate
						},
						success : function(msg) {
							layer.close(i);
							if(msg.success==null||msg.success==undefined||msg.success==true){
								$('#chanceOpenMarks_window_add_but').modal("hide");
								alertSuccess(msg.info,"content");
							}else{
								alertError(msg.info,"chanceOpenMarks_window_add_window_alert");
							}
						}
					})
				})
				//demo.onItemDel=function(id,type){
				//	return confirm("确定要删除该单元吗?");
				//}
				//jsondata={"nodes":{"demo_node_9":{"name":"桂中区","left":51,"top":29,"type":"start round","width":24,"height":24,"alt":true},"demo_node_10":{"name":"桂北区","left":50,"top":80,"type":"start round","width":24,"height":24,"alt":true},"demo_node_11":{"name":"桂西区","left":50,"top":129,"type":"start round","width":24,"height":24,"alt":true},"demo_node_12":{"name":"桂北区","left":51,"top":180,"type":"start round","width":24,"height":24,"alt":true},"demo_node_13":{"name":"n夺","left":205,"top":108,"type":"complex mix","width":86,"height":24,"alt":true},"demo_node_14":{"name":"桂东区","left":49,"top":230,"type":"start round","width":24,"height":24,"alt":true}},"lines":{},"areas":{}};
				//demo.loadData(jsondata);
		}else{
			
		}
	},true)
	
}
function chanceOpenMarks_query_function_window(page){
	var name=$("#chanceOpenMarks_window_query_Name").val();
	if(page!=undefined||page!=null){
		chanceOpenMarks_Query_page=page;
	}
	addLoadData_div("chanceOpenMarks_Window_Body");
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryFlow",
		data:{
			chanceId:addlinkman_id,
			name:name,
			nowpage:chanceOpenMarks_Query_page,
			countNum:30
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#chanceOpenMarks_window_tbody").html($.render.chanceOpenMarks_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:chanceOpenMarks_query_function_window('"+(parseInt(chanceOpenMarks_Query_page)-1)+"')\">«</a></li>";
				var startPage=chanceOpenMarks_Query_page>15?chanceOpenMarks_Query_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=chanceOpenMarks_Query_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:chanceOpenMarks_query_function_window('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:chanceOpenMarks_query_function_window('"+(parseInt(chanceOpenMarks_Query_page)+1)+"')\">»</a></li>"
				$("#chanceOpenMarks_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"chanceOpenMarks_AlertDiv");
			}
			removeloadData_div("chanceOpenMarks_Window_Body");
		}
	})
}
function chanceOpenMarks_look(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"crm/function/queryFlowItem",
		data:{
			id:id
		},
		success : function(msg) {
		layer.close(i);
		if(msg.success==null||msg.success==undefined||msg.success==true){
			$("#chanceOpenMarks_window_add_window_div").html($.render.chanceOpenMarks_window_look_window());
			$('#chanceOpenMarks_window_look_but').modal("show");
			var name=msg.obj.name;
			var json_map_str=msg.obj.jsonflow;
			var json_map_json=eval('(' + json_map_str + ')');
			var property={
				width:960,
				height:500,
				headBtns:[],
				toolBtns:["start round","end","task","node","chat","state","plug","join","fork","complex"],
				haveHead:true,
				initLabelText:name,
				haveTool:true,
				haveGroup:true,
				useOperStack:true
			};
			var remark={
				cursor:"选择指针",
				direct:"转换连线",
				start:"开始结点",
				end:"结束结点",
				task:"任务结点",
				node:"自动结点",
				chat:"决策结点",
				state:"状态结点",
				plug:"附加插件",
				fork:"分支结点",
				join:"联合结点",
				complex:"复合结点",
				group:"组织划分框编辑开关"
			};
			demo=$.createGooFlow($("#chanceOpenMarks_window_add_window_Demo"),property);
			demo.setNodeRemarks(remark);
			demo.loadData(json_map_json);
			$("#chanceOpenMarks_look").bind("click",function(){
				$('#chanceOpenMarks_window_look_but').modal("hide");
			})
		}else{
			alertError(msg.info,"chanceOpenMarks_AlertDiv");
		}
	}
})
}
function chanceOpenMarks_delete(id){
	ui.confirm("您是否确定要删除此建议流程？",function(b){
		if(b){
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"crm/function/deleteFlow",
				data:{
					id:id
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success==null||msg.success==undefined||msg.success==true){
						alertSuccess(msg.info,"chanceOpenMarks_AlertDiv");
						chanceOpenMarks_query_function_window(chanceOpenMarks_Query_page);
					}else{
						alertError(msg.info,"chanceOpenMarks_AlertDiv");
					}
			    }
			})
		}else{
			
		}
	})
}