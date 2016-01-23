var inOrder_box_serch_page=1;
var inGoods_line=1;
var now_td="";
var now_print=false;
var data;
var inOrder_query_type;
var inOrder_query_startDate="";
var inOrder_query_endDate="";
var inOrder_query_name="";
var inOrder_query_num="";
$(function () {
	$(document).ready(function(){
		initTemp();
		initbindEvent();
		document.onkeydown = function() {
            if (event.keyCode == 9) {  //如果是其它键，换上相应在ascii 码即可。
            	 if(now_td!=undefined&&now_td!=""&&now_td!=null){
            		 var trNum=now_td.split("_")[1];
 	            	 var tdNum=now_td.split("_")[2];
 	            	if(parseInt(trNum)<parseInt(inGoods_line)-1){
						trNum=parseInt(trNum)+1;
	            		$("#line_"+trNum+"_1").click();
	            	}else{
	            		var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line});
						inGoods_line++;
						$("#line_"+trNum).after(html);
						trNum=parseInt(trNum)+1;
	            		$("#line_"+trNum+"_1").click();
	            	}
            	 }
            	 return false;
            }
            if(event.keyCode>=37&&event.keyCode<=40){//上下左右键移动
	            if(now_td!=undefined&&now_td!=""&&now_td!=null){
	            	var trNum=now_td.split("_")[1];
	            	var tdNum=now_td.split("_")[2];
		            if(event.keyCode==37){
		            	//左
		            	if(tdNum!="1"){
		            		tdNum=parseInt(tdNum)-1;
		            		$("#line_"+trNum+"_"+tdNum).click();
		            	}
		            }
					if(event.keyCode==38){
						//上
						if(trNum!="1"){
							trNum=parseInt(trNum)-1;
		            		$("#line_"+trNum+"_"+tdNum).click();
		            	}
					}
					if(event.keyCode==39){
						//右
						if(tdNum!="11"){
							tdNum=parseInt(tdNum)+1;
		            		$("#line_"+trNum+"_"+tdNum).click();
		            	}
					}
					if(event.keyCode==40){
						//下
						var num=(inGoods_line-1)+"";
						if(trNum!=num){
							trNum=parseInt(trNum)+1;
		            		$("#line_"+trNum+"_"+tdNum).click();
		            	}
					}
					return false;
	            }
	        }
		}
	})
	function initTemp(){
		var tmpl = $("#create_inOrder_queryData_inOder1_layout_body_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_inOder1_layout_body_tr : tmpl
		});
		var tmpl = $("#inOrder_queryData_inOder1_layout").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inOrder_queryData_inOder1_layout : tmpl
		});
		var tmpl = $("#create_inOrder_queryData_inOder1_layout").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_inOder1_layout : tmpl
		});
		var tmpl = $("#inGoods_edit_input_goods").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inGoods_edit_input_goods : tmpl
		});
		var tmpl = $("#inGoods_edit_input_edit").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inGoods_edit_input_edit : tmpl
		});
		var tmpl = $("#inGoods_edit_input_goodsSource").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inGoods_edit_input_goodsSource : tmpl
		});
		var tmpl = $("#inGoods_edit_input_StoreHouse").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inGoods_edit_input_StoreHouse : tmpl
		});
		var tmpl = $("#inOrder_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			inOrder_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#create_inOrder_queryData_order_table").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_order_table : tmpl
		});
		var tmpl = $("#create_inOrder_queryData_order_table_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_order_table_tr : tmpl
		});
		var tmpl = $("#create_inOrder_queryData_order_table_edit").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_order_table_edit : tmpl
		});
		var tmpl = $("#create_inOrder_queryData_order_table_tr_edit").html().replace("<!--", "").replace("-->", "");
		$.templates({
			create_inOrder_queryData_order_table_tr_edit : tmpl
		});
	}
	function initbindEvent(){
		
	}
})
/**
 * 弹出查询计划
 */
function inOder1_layout(){
	$("#hide_function_black_but").click();
	$("#inOrder_layout").html($.render.inOrder_queryData_inOder1_layout());
	$('#inOrder_queryData_Body_char_startDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#inOrder_queryData_Body_char_endDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd',
	});
	$('#inOrder_queryData_Body_startDate_text').val(getNowFormatDate(true));
	$('#inOrder_queryData_Body_endDate_text').val(getNowFormatDate());
}
/**
 * 弹出创建计划
 */
function create_inOder1_layout(){
	$("#hide_function_black_but").click();
	$("#inOrder_layout").html($.render.create_inOrder_queryData_inOder1_layout());
	$("#inOrder_userName").val(userName);
	inGoods_line=1;
	now_td=undefined;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;
	$("#create_inOrder_queryData_Body_tbody").html($("#create_inOrder_queryData_Body_tbody").html()+$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line}));
	inGoods_line++;

}
/**
 * 添加行
 * @param line
 */
function create_inOder1_layout_add(line_obj){
	var line=parseInt($(line_obj).parent().parent().attr("id").split("_")[1]);
	var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":line});
	line_down_move(line);
	$("#line_"+(line+1)).before(html);
	inGoods_line++;
}
/**
 * 删除行
 * @param line
 */
function create_inOder1_layout_delete(line_obj){
	var line=parseInt($(line_obj).parent().parent().attr("id").split("_")[1]);
	if(inGoods_line>2){
		$("#line_"+line).remove();
		inGoods_line--;
		line_up_move(line);

	}else{
		alertError("最低要保留一行","alertDiv");
	}
}
/**
 * 根据名称以及日期查询
 */
function inorder_query_name(page){
	inOrder_query_type="name"
	var name=$("#inOrder_queryData_Body_query_name").val();
	var startDate=$("#inOrder_queryData_Body_startDate_text").val();
	var endDate=$("#inOrder_queryData_Body_endDate_text").val();
	if(page!=undefined||page!=null){
		inOrder_box_serch_page=page;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/function/queryInOrder",
		data:{
			name:name,
			startDate:startDate,
			endDate:endDate,
			nowpage:inOrder_box_serch_page,
			countNum:10
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#inOrder_queryData_window_tbody").html($.render.inOrder_queryData_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:inorder_query_name('"+(parseInt(inOrder_box_serch_page)-1)+"')\">«</a></li>";
				var startPage=inOrder_box_serch_page>15?inOrder_box_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=inOrder_box_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:inorder_query_name('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:inorder_query_name('"+(parseInt(inOrder_box_serch_page)+1)+"')\">»</a></li>"
				$("#inOrder_queryData_Body_tbody_page").html(page_html);
			}else{
					alertError(msg.info,"alertDiv");
			}
		}
    })
}
/**
 * 根据编号查询
 */
function inorder_query_num(page){
	inOrder_query_type="num"
	var num=$("#inOrder_queryData_Body_query_num").val();
	if(num==""){
		num="0";
	}
	if(page!=undefined||page!=null){
		inOrder_box_serch_page=page;
	}
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/function/queryInOrder",
		data:{
			num:num,
			nowpage:inOrder_box_serch_page,
			countNum:10
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#inOrder_queryData_window_tbody").html($.render.inOrder_queryData_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:inorder_query_name('"+(parseInt(inOrder_box_serch_page)-1)+"')\">«</a></li>";
				var startPage=inOrder_box_serch_page>15?inOrder_box_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=inOrder_box_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:inorder_query_name('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:inorder_query_name('"+(parseInt(inOrder_box_serch_page)+1)+"')\">»</a></li>"
				$("#inOrder_queryData_Body_tbody_page").html(page_html);
			}else{
					alertError(msg.info,"alertDiv");
			}
		}
    })
}
/**
 * 行向上移动 当删除行时 
 * @param line
 */
function line_up_move(line){
	var line_=now_td.split("_")[0];
	var line_num=parseInt(now_td.split("_")[1])-1;
	var line_num_num=now_td.split("_")[2];
	$("#"+now_td+"_div").attr("id",line_+"_"+line_num+"_"+line_num_num+"_div");
	$("#"+now_td+"_input").attr("id",line_+"_"+line_num+"_"+line_num_num+"_input");
	now_td=line_+"_"+line_num+"_"+line_num_num;
	var lines=line+1;
	while(lines){
		if($("#line_"+lines).html()==null||$("#line_"+lines).html()==undefined){
			break;
		}
		$("#line_"+lines+">td:first").html(lines-1);
		$("#line_"+lines).attr("id","line_"+(lines-1));
		for(var i=1;i<=12;i++){
			$("#line_"+lines+"_"+i).attr("id","line_"+(lines-1)+"_"+i)
			if(i==8){
				if($("#line_"+lines+"_8_id").html()!=undefined){
					$("#line_"+lines+"_8_id").attr("id","line_"+(lines-1)+"_8_id");
				}
			}
			if(i==9){
				if($("#line_"+lines+"_9_id").html()!=undefined){
					$("#line_"+lines+"_9_id").attr("id","line_"+(lines-1)+"_9_id");
				}
			}
		}
		lines++;
	}
}
/**
 * 行向下移动 当添加新行时
 * @param line
 */
function line_down_move(line){
	var line_=now_td.split("_")[0];
	var line_num=parseInt(now_td.split("_")[1])+1;
	var line_num_num=now_td.split("_")[2];
	$("#"+now_td+"_div").attr("id",line_+"_"+line_num+"_"+line_num_num+"_div");
	$("#"+now_td+"_input").attr("id",line_+"_"+line_num+"_"+line_num_num+"_input");
	now_td=line_+"_"+line_num+"_"+line_num_num;
	var lines=inGoods_line-1;
	while(lines>=line){
		if($("#line_"+lines).html()==null||$("#line_"+lines).html()==undefined){
			break;
		}
		$("#line_"+lines+">td:first").html(lines+1);
		$("#line_"+lines).attr("id","line_"+(lines+1));
		for(var i=0;i<=12;i++){
			$("#line_"+lines+"_"+i).attr("id","line_"+(lines+1)+"_"+i)
			if(i==8){
				if($("#line_"+lines+"_8_id").html()!=undefined){
					$("#line_"+lines+"_8_id").attr("id","line_"+(lines+1)+"_8_id");
				}
			}
			if(i==9){
				if($("#line_"+lines+"_9_id").html()!=undefined){
					$("#line_"+lines+"_9_id").attr("id","line_"+(lines+1)+"_9_id");
				}
			}
		}
		lines--;
	}
}
/**
 * 点击table时使用
 * @param td_obj
 */
function Order_edit_td(td_obj,type){
	var td=$(td_obj).attr("id");

	if(now_td!=td||now_print){
		var value=$("#"+now_td+"_input").val();
		var upid=$("#"+now_td+"_input").parent().parent().parent().attr('id');
		if(upid!=undefined){
			if(upid.split("_").length==3){
				var lines_num=now_td.split("_")[1];
				var shouzimu=pinyin.getCamelChars($("#"+now_td+"_input").val());
				$("#line_"+lines_num+"_6").html(shouzimu);
				$("#"+now_td+"_input").parent().parent().parent().html(value);
			}else{
				if($("#"+now_td+"_id").val()!=null&&$("#"+now_td+"_id").val()!=undefined){
					var html='<label style="display: none;" id="'+now_td+'_id">'+$("#"+now_td+"_id").val()+'</label>';
					$("#"+now_td+"_input").parent().parent().html(value+html);
				}else{
					$("#"+now_td+"_input").parent().parent().html(value);
				}
			}
		}
		now_td=td;
		if($("#"+td+"_input").val()==null||$("#"+td+"_input").val()==undefined){
			if(type=='goods'){
				var input_html=$.render.inGoods_edit_input_goods({"id":td,value:$("#"+td).html()});
				//var input_html="<input class='form-control input-sm' style=\"border:1px solid #d6dee3;width:100%;height:34px;line-height:22px;margin:-8px -8px;padding:6px 4px;\"  id=\""+td+"_input\" value='"+$("#"+td).html()+"'\"/>"
				$("#"+td).html(input_html);
				$("#"+td+"_input").typeahead({
					minLength:1,
					 source:  function (query, process) {
					        var parameter = {query: query};
					        $.ajax({
					    		type : "POST",
					    		url : ctx+"goods/function/queryGoods",
					    		data:{
					    			name:query,
					    			spell_query:"",
					    			nowpage:1,
					    			countNum:5
					    		},
					    		success : function(msg) {
					    			if(msg.success==null||msg.success==undefined||msg.success==true){
					    				data=msg.data;
					    				if(msg.data.length>0){
					    					
						    				var data2=new Array();
						    				for(var i=0;i<msg.data.length;i++){
						    					data2[i]=msg.data[i].id+"-"+msg.data[i].goodsName+"-"+msg.data[i].goodsType+"-"+msg.data[i].goodsModel;
						    				}
						    				process(data2);
					    				}else{
					    					process(data);
					    				}
					    			}else{
					    				
					    			}
					    		}
					        })
					        
					    }
			         , updater: function(item) {
			        	 var id=item.split("-")[0];
			        	 var name=item.split("-")[1];
			        	 var type=item.split("-")[2];
			        	 var model=item.split("-")[3];
			        	 var line=now_td.split("_")[0]+"_"+now_td.split("_")[1];
			        	 var obj;
			        	 for(var i=0;i<data.length;i++){
			        		 if(id==data[i].id){
			        			 obj=data[i];
			        		 }
			        	 }
			        	 $("#"+line+"_2").html(type);
			        	
			        	 $("#"+line+"_3").html(obj.price);
			        	 
			        	 $("#"+line+"_5").html(obj.score);
			        	 
			        	 $("#"+line+"_6").html(obj.spell);
			        	
			        	 $("#"+line+"_7").html(model);
			        	 
							$("#"+line+"_4").html(0.0);
							$("#"+line+"_11").html(1);
							$("#"+line+"_12").html(0.0);
			             return name;
			         }
				})
				$("#"+td+"_input").focus();
				$("#"+td+"_input").change(function(){
					var lines_num=td.split("_")[1];
					var shouzimu=pinyin.getCamelChars($("#"+td+"_input").val());
					$("#line_"+lines_num+"_6").html(shouzimu);
				});
				$("#"+td+"_input").blur(function(){
					var lines_num=td.split("_")[1];
					var shouzimu=pinyin.getCamelChars($("#"+td+"_input").val());
					$("#line_"+lines_num+"_6").html(shouzimu);
				})
				 $("#"+td+"_input").bind('keypress',function(event){
					 if(event.keyCode == "13")    
			         {
						 var num=parseInt($(this).parent().parent().parent().attr("id").split("_")[2]);
						if(num==10){
							if($(this).parent().parent().parent().parent().next().attr("id")==null||$(this).parent().parent().parent().parent().next().attr("id")==undefined){
								var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line});
								inGoods_line++;
								$(this).parent().parent().parent().parent().after(html);
							}else{

								$("#"+$(this).parent().parent().parent().parent().next().attr("id")+"_1").click();
							}
						}else{
							$(this).parent().parent().parent().next().click();
						}
			         }
				 })
			}else if(type=='edit'){
				var input_html=$.render.inGoods_edit_input_edit({"id":td,value:$("#"+td).html()});
				//var input_html="<input class='form-control input-sm' style=\"border:1px solid #d6dee3;width:100%;height:34px;line-height:22px;margin:-8px -8px;padding:6px 4px;\"  id=\""+td+"_input\" value='"+$("#"+td).html()+"'\"/>"
				$("#"+td).html(input_html);
				$("#"+td+"_input").focus();
				$("#"+td+"_input").blur(function(){
					
				})
				 $("#"+td+"_input").bind('keypress',function(event){
					 var num=parseInt($(this).parent().parent().attr("id").split("_")[2]);
					 if(event.keyCode == "13")    
			         {
						if(num==11){
							if($(this).parent().parent().parent().next().attr("id")==null||$(this).parent().parent().parent().next().attr("id")==undefined){
								var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line});
								inGoods_line++;
								$(this).parent().parent().parent().after(html);
							}else{
								$("#"+$(this).parent().parent().parent().next().attr("id")+"_1").click();
							}
						}else{
							$(this).parent().parent().next().click();
						}
			            
			           
			         }else if(num==4||num==11){
			        	 if((event.keyCode<=57&&event.keyCode>=48)||event.keyCode==46){
			        		/*var nowline="line_"+$(this).parent().parent().attr("id").split("_")[1];
			        		alert($("#"+nowline+"_4_input").html())
			        		$("#"+nowline+"_12").html(parsefloat($("#"+nowline+"_4").html())*parsefloat($("#"+nowline+"_11").html()))*/
				     	}else{
				     		
				     		return false;
				     	}
			         }
			         else if(num==3||num==5){
			        	 if((event.keyCode<=57&&event.keyCode>=48)||event.keyCode==46){
			        		 
				     	}else{
				     		
				     		return false;
				     	}
			         }
				 })
			}else if(type=='goodsSource'){
				var input_html=$.render.inGoods_edit_input_goodsSource({"id":td,value:$("#"+td).html()});
				//var input_html="<input class='form-control input-sm' style=\"border:1px solid #d6dee3;width:100%;height:34px;line-height:22px;margin:-8px -8px;padding:6px 4px;\"  id=\""+td+"_input\" value='"+$("#"+td).html()+"'\"/>"
				$("#"+td).html(input_html);
				$("#"+td+"_input").focus();
				$("#"+td+"_input").blur(function(){
				
				})
				$("#"+td+"_input").typeahead({
					minLength:1,
					 source:  function (query, process) {
					        var parameter = {query: query};
					        $.ajax({
					    		type : "POST",
					    		url : ctx+"goods/function/queryGoodsSource",
					    		data:{
					    			name:query,
					    			spell_query:"",
					    			nowpage:1,
					    			countNum:5
					    		},
					    		success : function(msg) {
					    			if(msg.success==null||msg.success==undefined||msg.success==true){
					    				data=msg.data;
					    				if(msg.data.length>0){
					    					
						    				var data2=new Array();
						    				for(var i=0;i<msg.data.length;i++){
						    					data2[i]=msg.data[i].name;
						    				}
						    				process(data2);
					    				}else{
					    					process(data);
					    				}
					    			}else{
					    				
					    			}
					    		}
					        })
					        
					    }
			         , updater: function(item) {
			        	 
			        	 $("#"+now_td+"_input").val(item);
			             return item;
			         }
				})
				  $("#"+td+"_input").bind('keypress',function(event){
					 if(event.keyCode == "13")    
			         {
						 var num=parseInt($(this).parent().parent().attr("id").split("_")[2]);
						if(num==10){
							if($(this).parent().parent().parent().next().attr("id")==null||$(this).parent().parent().parent().next().attr("id")==undefined){
								var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line});
								inGoods_line++;
								$(this).parent().parent().parent().after(html);
							}else{
								$("#"+$(this).parent().parent().parent().next().attr("id")+"_1").click();
							}
						}else{
						
							$(this).parent().parent().next().click();
						}
			            
			           
			         }
				 })
			}else if(type=='StoreHouse'){
				var id=$("#"+td+"_id").html();
				$("#"+td+"_id").remove();
				var input_html=$.render.inGoods_edit_input_StoreHouse({"id":td,value:$("#"+td).html()});
				//var input_html="<input class='form-control input-sm' style=\"border:1px solid #d6dee3;width:100%;height:34px;line-height:22px;margin:-8px -8px;padding:6px 4px;\"  id=\""+td+"_input\" value='"+$("#"+td).html()+"'\"/>"
				$("#"+td).html(input_html);
				$("#"+td+"_id").val(id);
				$("#"+td+"_input").focus();
				$("#"+td+"_input").blur(function(){
					
				})
				$("#"+td+"_input").typeahead({
					minLength:0,
					 source:  function (query, process) {
					        var parameter = {query: query};
					        $.ajax({
					    		type : "POST",
					    		url : ctx+"goods/function/queryStoreHouse",
					    		data:{
					    			name:query,
					    			spell_query:"",
					    			nowpage:1,
					    			countNum:5
					    		},
					    		success : function(msg) {
					    			if(msg.success==null||msg.success==undefined||msg.success==true){
					    				data=msg.data;
					    				if(msg.data.length>0){
					    					
						    				var data2=new Array();
						    				for(var i=0;i<msg.data.length;i++){
						    					data2[i]=msg.data[i].id+"-"+msg.data[i].name;
						    				}
						    				process(data2);
					    				}else{
					    					process(data);
					    				}
					    			}else{
					    				
					    			}
					    		}
					        })
					        
					    }
			         , updater: function(item) {
			        	 var id=item.split("-")[0];
			        	 var name=item.split("-")[1];
			        	 $("#"+now_td+"_id").val(id);
			        	 $("#"+now_td+"_input").val(name);
			             return name;
			         }
				})
				 $("#"+td+"_input").bind('keypress',function(event){
					 if(event.keyCode == "13")    
			         {
						 var num=parseInt($(this).parent().parent().attr("id").split("_")[2]);
						if(num==10){
							if($(this).parent().parent().parent().next().attr("id")==null||$(this).parent().parent().parent().next().attr("id")==undefined){
								var html=$.render.create_inOrder_queryData_inOder1_layout_body_tr({"inGoods_line":inGoods_line});
								inGoods_line++;
								$(this).parent().parent().parent().after(html);
							}else{
								$("#"+$(this).parent().parent().parent().next().attr("id")+"_1").click();
							}
						}else{
							$(this).parent().parent().next().click();
						}
			            
			           
			         }
				 })
			}
		}else{
			
		}
	}else{
		
	}
}
/**
 * 打印选项
 */
function inOrder_queryData_line_print(colNum,is){
	if($(is).attr("class")=="label label-default"){
		$(is).attr("class","label label-success no-print");
		$(is).parent().attr("class","");
		for(var i=1;i<=inGoods_line;i++){
			$("#line_"+i+"_"+colNum).attr("class","");
		}
	}else{
		$(is).attr("class","label label-default");
		$(is).parent().attr("class","no-print");
		for(var i=1;i<=inGoods_line;i++){
			$("#line_"+i+"_"+colNum).attr("class","no-print");
		}
	}
}
/**
隐藏功能块
*/
function hide_function_black(id,obj){
	if(!$('#'+id).is(':visible')){
		$("#"+id).show(1000)
		$(obj).html("<i class=\"fa fa-minus-square\"></i>")
		$(obj).attr("class","btn");
	}else{
		$("#"+id).hide(1000)
		$(obj).html("<i class=\"fa fa-plus-square\"></i>")
		$(obj).attr("class","btn btn-danger");
	}
}
function inGoods_queryGoods(text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/queryData_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh(msg);
				is_select=true;
				return_function=function(obj){
					
					var name=obj.goodsName;
					var type=obj.goodsType;
					var price=obj.price;
					var score=obj.score;
					var spell=obj.spell;
					var model=obj.goodsModel;
					var inPrice=obj.inPrice;
					
					$("#"+now_td+"_input").val(name);
					var id=$("#"+now_td+"_input").parent().parent().parent().parent().attr("id");
					$("#"+id+"_2").html(type);
					
					$("#"+id+"_3").html(price);
					
					$("#"+id+"_5").html(score);
					
					$("#"+id+"_6").html(spell);
					
					$("#"+id+"_7").html(model);
					
					$("#"+id+"_4").html(0.0);
					$("#"+id+"_11").html(1);
					$("#"+id+"_12").html(0.0);
					$("#good_addData_window_goodsModel").val(model);
				}
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function inGoods_Storehouse(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_StoreHouse_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				StoreHouse_box_EditLinkDate_element_text=now_td+"_input";
				StoreHouse_box_EditLinkDate_element_id=now_td+"_id";
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function inGoods_GoodSource(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_GoodSource_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				GoodSource_box_EditLinkDate_element_text=now_td+"_input";
				GoodSource_box_EditLinkDate_element_id=now_td+"_id";
				GoodSource_box_select_type_linkman=false;
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
/**
 * 打印
 */
function print_inOrder(){
	var value=$("#"+now_td+"_input").val();
	if(now_td!=undefined||now_td!=null){
		if(now_td.split("_")[2]=="1"){
			$("#"+now_td+"_input").parent().parent().parent().html(value);
		}else{
			$("#"+now_td+"_input").parent().parent().html(value);
		}
	}
	now_print=true;
	window.print();
}
function inGoods_Storehouse_all(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_StoreHouse_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				StoreHouse_box_is_table=true;
				StoreHouse_box_is_table_function=function(id,name){
					for(var ii=0;ii<inGoods_line;ii++){
						if($("#line_"+ii+"_1").html()!=""){
							if(now_td=="line_"+ii+"_9"){
								var value=$("#"+now_td+"_input").val();
								var html='<label style="display: none;" id="'+now_td+'_id">'+$("#"+now_td+"_id").val()+'</label>';
								$("#"+now_td+"_input").parent().parent().html(value+html);
								now_td="";
							}
							var html='<label style="display: none;" id="'+"line_"+ii+"_9"+'_id">'+id+'</label>';
							$("#line_"+ii+"_9").html(name+html);
						}
					}
				}
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function inGoods_GoodSource_all(id,text){
	var i=$.layer({
	    type : 3
	});
    $.ajax({
		type : "POST",
		url : ctx+"goods/page/Edit_GoodSource_window",
		data:{
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				functionLayoutRefresh2(msg)
				//给成员赋值
				GoodSource_box_is_table=true;
				GoodSource_box_select_type_linkman=false;
				GoodSource_box_is_table_function=function(id,name){
					for(var ii=0;ii<inGoods_line;ii++){
						if($("#line_"+ii+"_1").html()!=""){
							if(now_td=="line_"+ii+"_8"){
								var value=$("#"+now_td+"_input").val();
								var html='<label style="display: none;" id="'+now_td+'_id">'+$("#"+now_td+"_id").val()+'</label>';
								$("#"+now_td+"_input").parent().parent().html(value+html);
								now_td=undefined;
							}
							var html='<label style="display: none;" id="'+"line_"+ii+"_8"+'_id">'+id+'</label>';
							$("#line_"+ii+"_8").html(name+html);
						}
					}
				}
			}else{
				alertError(msg.info,"alertDiv");
			}
		}
    })
}
function inOrder_getTableDate(){
	var value=$("#"+now_td+"_input").val();
	if(now_td!=""&&now_td!=undefined&&now_td!=null){
		if(now_td.split("_")[2]=="1"){
			$("#"+now_td+"_input").parent().parent().parent().html(value);
		}else{
			if($("#"+now_td+"_id").val()!=null&&$("#"+now_td+"_id").val()!=undefined){
				var html='<label style="display: none;" id="'+now_td+'_id">'+$("#"+now_td+"_id").val()+'</label>';
				$("#"+now_td+"_input").parent().parent().html(value+html);
			}else{
				$("#"+now_td+"_input").parent().parent().html(value);
			}
		}
	}
	now_print=true;
	var data2="[";
	var isSuccess=true;
	for(var i=1;i<inGoods_line;i++){
		var line_="line_"+i;
		if($("#line_"+i+"_1").html()!=""){
			data2=data2+"{"
			for(var ii=1;ii<13;ii++){
					if(($("#line_"+i+"_"+ii).html()==""||$("#line_"+i+"_"+ii).html()=="请填写内容")&&ii!=10){
							if(ii==11){
								$("#line_"+i+"_"+ii).html("1");
							}else{
								$("#line_"+i+"_"+ii).html("请填写内容");
								isSuccess=false;
							}
					}else{
						if(ii==9){
							if($("#line_"+i+"_"+ii+"_id").html()==""||$("#line_"+i+"_"+ii+"_id").html()==""){
								$("#line_"+i+"_"+ii).html("请选择");
								isSuccess=false;
							}
						}
						//当是最后一个的时候 对象内的属性结尾没有逗号
						switch(ii){
							case 1:
								data2=data2+"name:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 2:
								data2=data2+"type:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 3:
								data2=data2+"price:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 4:
								data2=data2+"inprice:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 5:
								data2=data2+"score:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 6:
								data2=data2+"spell:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 7:
								data2=data2+"model:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 8:
								data2=data2+"goodRource:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 9:
								data2=data2+"storeHouse:'"+$("#line_"+i+"_"+ii+"_id").html()+"',";
								break;
							case 10:
								data2=data2+"marks:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 11:
								data2=data2+"num:'"+$("#line_"+i+"_"+ii).html()+"',";
								break;
							case 12:
								data2=data2+"countPrice:'"+$("#line_"+i+"_"+ii).html()+"'";
								break;
						}
					}
				}
				data2=data2+"},"
		}
		
		
	}
	data2=data2+"]"
	if(isSuccess){

		return data2;
	}else{
		alertError("请把内容补全","alertDiv");
		return "";
	}
}
function inGoods_box_save(type){
	var data2=inOrder_getTableDate();
	if(data2!=""){
		var name=$("#inOrder_box_name").val();
		var id=$("#orderNum_id").html()==""?"0":$("#orderNum_id").html();
		if(name==""||name=="请填写计划名称"){
			$("#inOrder_box_name").val("请填写计划名称");
		}else{
			var i=$.layer({
			    type : 3
			});
			$.ajax({
				type : "POST",
				url : ctx+"goods/function/createInOrder",
				data:{
					name:name,
					data:data2,
					id:id,
					type:type
				},
				success : function(msg) {
					layer.close(i);
					if(msg.success){
						$("#orderNum").html(msg.orderNum);
						$("#orderNum_id").html(msg.orderId);
					}else{
						var list=msg.list;
						if(list==undefined||list==null){
							alertError(msg.info,"alertDiv");
						}else{
							inputAjaxTest(list,"GoodSource_box_add_window_");
						}
					}
				}
			})
		}
	}
}
function inGoods_changeNum_input(obj){
	var lineNum=$(obj).parent().attr("id");
	var inprice="";
	var num="";
	if($("#"+lineNum+"_4_input").val()==undefined){
		inprice=$("#"+lineNum+"_4").html();
		num=$("#"+lineNum+"_11_input").val()
	}else{
		inprice=$("#"+lineNum+"_4_input").val();
		num=$("#"+lineNum+"_11").html();
	}
	if(inprice==""){
		inprice="0"
	}
	if(num==""){
		num="0"
	}
	$("#"+lineNum+"_12").html(parseFloat(inprice)*parseInt(num));
	var countNum=0;
	for(var i=1;i<inGoods_line;i++){
		if($("#line_"+i+"_12").html()!=""){
			countNum=countNum+parseFloat($("#line_"+i+"_12").html());
		}
	}
	$("#inOrder_countPrice").val(countNum);
}
/**
 * 查看订单
 */
function inOrder_box_look(id){
	inOrder_query_startDate=$("#inOrder_queryData_Body_startDate_text").val();
	inOrder_query_endDate=$("#inOrder_queryData_Body_endDate_text").val();
	inOrder_query_name=$("#inOrder_queryData_Body_query_name").val();
	inOrder_query_num=$("#inOrder_queryData_Body_query_num").val();
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
				inGoods_line=msg.data.length+1;
				if(msg.obj.state==0){
					$("#inOrder_layout").html($.render.create_inOrder_queryData_order_table_edit());
					$("#create_inOrder_queryData_order_table_edit_tbody").html($.render.create_inOrder_queryData_order_table_tr_edit(msg.data));
					$("#inOrder_userName").val(msg.obj.createUserName);
					
				}else{
					$("#inOrder_layout").html($.render.create_inOrder_queryData_order_table());
					$("#create_inOrder_queryData_order_table_tbody").html($.render.create_inOrder_queryData_order_table_tr(msg.data));
					$("#inOrder_userName").val(msg.obj.createUserName);
					$("#inOrder_box_name").attr("disabled","disabled");
				}
				var countPrice=0.0;
				for(var ii=0;ii<msg.data.length;ii++){
					countPrice=countPrice+msg.data[ii].goodsNum*msg.data[ii].goodsinPrice;
				}
				$("#inOrder_box_name").val(msg.obj.name);
				$("#orderNum").html(msg.obj.orderNum);
				$("#orderNum_id").html(msg.obj.id);
				$("#inOrder_countPrice").val(countPrice);

			}else{
				alertError(msg.info,"alertDiv");
			}
		}
	})
}
/**
 * 入库订单
 */
function inOrder_box_inOrder(id){
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
						if(inOrder_query_type=="num"){
							inorder_query_num();
						}else{
							inorder_query_name();
						}
					}else{
						alertError(msg.info,"alertDiv");
					}
				}
			})
		}
	})
}
/**
 *删除订单
 */
function inOrder_box_delete(id){
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
						alertSuccess(msg.info,"alertDiv");
						if(inOrder_query_type=="num"){
							inorder_query_num();
						}else{
							inorder_query_name();
						}
					}else{
						alertError(msg.info,"alertDiv");
					}
				}
			})
		}
	})
}
function inGoods_box_look_return(){
	inOder1_layout();
	$("#inOrder_queryData_Body_startDate_text").val(inOrder_query_startDate);
	$("#inOrder_queryData_Body_endDate_text").val(inOrder_query_endDate);
	$("#inOrder_queryData_Body_query_name").val(inOrder_query_name);
	$("#inOrder_queryData_Body_query_num").val(inOrder_query_num);
	if(inOrder_query_type=="num"){
		inorder_query_num();
	}else{
		inorder_query_name();
	}
}