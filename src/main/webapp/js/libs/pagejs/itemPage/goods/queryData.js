var good_queryData_serch_page=1;
var good_queryData_window_page=1;
var good_queryData_window_id=0;
var good_queryData_window_id_charType=1;
$(function () {
	$(document).ready(function(){
		initbindEvent();
		initTemp();
		good_queryData_box_serch();
	})
	function initTemp(){
		var tmpl = $("#good_queryData_window_tbody_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			good_queryData_window_tbody_tr : tmpl
		});
		var tmpl = $("#queryDate_log_window_body_tr").html().replace("<!--", "").replace("-->", "");
		$.templates({
			queryDate_log_window_body_tr : tmpl
		});
	}
	function initbindEvent(){
	
	}
	
});
function good_queryData_box_serch(page){
	var name=$("#good_queryData_window_query_Name").val();
	var spell=$("#good_queryData_window_query_spell").val();
	if(page!=undefined||page!=null){
		good_queryData_serch_page=page;
	}
	addLoadData_div("good_queryData_Body");
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoods",
		data:{
			name:name,
			spell_query:spell,
			nowpage:good_queryData_serch_page,
			countNum:15
		},
		success : function(msg) {
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#good_queryData_window_tbody").html($.render.good_queryData_window_tbody_tr(msg.data));
				var page_html="<li><a href=\"javascript:good_queryData_box_serch('"+(parseInt(good_queryData_serch_page)-1)+"')\">«</a></li>";
				var startPage=good_queryData_serch_page>15?good_queryData_serch_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=good_queryData_serch_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:good_queryData_box_serch('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:good_queryData_box_serch('"+(parseInt(good_queryData_serch_page)+1)+"')\">»</a></li>"
				$("#good_queryData_window_tbody_page").html(page_html);
			}else{
				alertError(msg.info,"good_queryData_AlertDiv");
			}
			removeloadData_div("good_queryData_Body");
		}
	})
}
function good_queryData_window_update_price(id,price){
	$("#queryDate_update_price_window").modal("show");
	$("#queryDate_update_price_window_priceorg").val(price);
	$("#queryDate_update_price_window_id").val(id);
}
function good_queryData_window_update_price_enter(){
	var updateprice=$("#queryDate_update_price_window_updateprice").val();
	var id=$("#queryDate_update_price_window_id").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/updatePrice",
		data:{
			updateprice:updateprice,
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$('#queryDate_update_price_window').modal("hide");
				alertSuccess(msg.info,"content");
				good_queryData_box_serch();
			}else{
				var list=msg.list;
				if(list==undefined||list==null){
					
					alertError(msg.info,"queryDate_update_price_window_AlertDiv");
					
				}else{
					inputAjaxTest(list,"queryDate_update_price_window_");
				}
			}
		}
	})
}
function good_queryData_window_Chat(id,name){
	$("#chat_goods_name").html(name);
	good_queryData_window_id=id;
	$("#queryDate_GoodsChat_window").modal("show");
	$('#queryDate_GoodsChat_window_char_startDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd'
	});
	$('#queryDate_GoodsChat_window_char_endDate').datepicker({
		 language: 'zh-CN',
		 format: 'yyyy-mm-dd',
	});
	$('#queryDate_GoodsChat_window_startDate_text').val(getNowFormatDate(true));
	$('#queryDate_GoodsChat_window_endDate_text').val(getNowFormatDate());
	queryDate_GoodsChat_window_char_type_set();
}
function queryDate_GoodsChat_window_char_query(){
	if(good_queryData_window_id_charType==1){
		queryDate_GoodsChat_window_char_type_set();
	}
	if(good_queryData_window_id_charType==2){
		queryDate_GoodsChat_window_char_type_set2();
	}
	if(good_queryData_window_id_charType==3){
		queryDate_GoodsChat_window_char_type_set3();
	}
	if(good_queryData_window_id_charType==4){
		queryDate_GoodsChat_window_char_type_set4();
	}
}
/**
 * 图表一
 * @param id
 */
function queryDate_GoodsChat_window_char_type_set(){
	good_queryData_window_id_charType=1;
	$("#queryDate_GoodsChat_window_char_type_set_1").attr("class","btn btn-danger");
	$("#queryDate_GoodsChat_window_char_type_set_2").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_3").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_4").attr("class","btn btn-primary");

	var startDate=$("#queryDate_GoodsChat_window_startDate_text").val();
	var endDate=$("#queryDate_GoodsChat_window_endDate_text").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/char/querySaleChat",
		data:{
			startDate:startDate,
			endDate:endDate,
			id:good_queryData_window_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				good_queryData_window_chat("销售统计",msg.names,msg.values,msg.pieValue,"销售客户比例");
			}else{	
					alertError(msg.info,"queryDate_GoodsChat_window_char_alert_div");
			}
		}
	})
	
}
function queryDate_GoodsChat_window_char_type_set2(){
	good_queryData_window_id_charType=2;
	$("#queryDate_GoodsChat_window_char_type_set_1").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_2").attr("class","btn btn-danger");
	$("#queryDate_GoodsChat_window_char_type_set_3").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_4").attr("class","btn btn-primary");
	var startDate=$("#queryDate_GoodsChat_window_startDate_text").val();
	var endDate=$("#queryDate_GoodsChat_window_endDate_text").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/char/queryInGoods",
		data:{
			startDate:startDate,
			endDate:endDate,
			id:good_queryData_window_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				good_queryData_window_chat("进货统计",msg.names,msg.values,msg.pieValue,"进货厂商比例");
			}else{	
					alertError(msg.info,"queryDate_GoodsChat_window_char_alert_div");
			}
		}
	})
}
function queryDate_GoodsChat_window_char_type_set3(){
	good_queryData_window_id_charType=3;
	$("#queryDate_GoodsChat_window_char_type_set_1").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_2").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_3").attr("class","btn btn-danger");
	$("#queryDate_GoodsChat_window_char_type_set_4").attr("class","btn btn-primary");
	var startDate=$("#queryDate_GoodsChat_window_startDate_text").val();
	var endDate=$("#queryDate_GoodsChat_window_endDate_text").val();
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/char/priceChar",
		data:{
			startDate:startDate,
			endDate:endDate,
			id:good_queryData_window_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				good_queryData_window_chat2("价格趋势",msg.date,msg.price,msg.inprice,msg.num,msg.profit);
			}else{	
					alertError(msg.info,"queryDate_GoodsChat_window_char_alert_div");
			}
		}
	})
	
}
function queryDate_GoodsChat_window_char_type_set4(){
	good_queryData_window_id_charType=4;
	$("#queryDate_GoodsChat_window_char_type_set_1").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_2").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_3").attr("class","btn btn-primary");
	$("#queryDate_GoodsChat_window_char_type_set_4").attr("class","btn btn-danger");
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/char/queryStoreHouseChat",
		data:{
			id:good_queryData_window_id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				good_queryData_window_chat3("仓库存放量",msg.hName,msg.hNum,msg.hCount,msg.hPer,"仓库存放比例");
			}else{	
					alertError(msg.info,"queryDate_GoodsChat_window_char_alert_div");
			}
		}
	})
	
}
function good_queryData_window_chat(title,names,data1,data2,title2){
	$('#container').highcharts({                                      
        chart: {                                                          
        },                                                                
        title: {                                                          
            text: title                                    
        },                                                                
        xAxis: {                                                          
            categories:names
        },                                                                
        tooltip: {                                                        
            formatter: function() {                                       
                var s;                                                    
                if (this.point.name) { // the pie chart                   
                    s = ''+                                               
                        this.point.name +': '+ this.y +' %';         
                } else {                                                  
                    s = ''+                                               
                        this.x  +': '+ this.y;                            
                }                                                         
                return s;                                                 
            }                                                             
        },                                                                
        labels: {                                                         
            items: [{                                                     
                html: title2,                          
                style: {                                                  
                    left: '25px',                                         
                    top: '3px',                                           
                    color: 'black'                                        
                }                                                         
            }]                                                            
        },                                                                
        series: [{                                                        
            type: 'column',                                               
            name: title,                                                 
            data: data1                                         
        },{                                                              
            type: 'pie',                                                  
            name: title2,                                    
            data: data2,                                                           
            center: [30, 40],                                            
            size: 70,                                                    
            showInLegend: false,                                          
            dataLabels: {                                                 
                enabled: false                                            
            }                                                             
        }]                                                                
    });                                     
}
function good_queryData_window_chat3(title,names,data1,data2,data3,title2){
	$('#container').highcharts({                                      
        chart: {                                                          
        },                                                                
        title: {                                                          
            text: title                                    
        },                                                                
        xAxis: {                                                          
            categories:names
        },                                                                
        tooltip: {                                                        
            formatter: function() {                                       
                var s;                                                    
                if (this.point.name) { // the pie chart                   
                    s = ''+                                               
                        this.point.name +': '+ this.y +' %';         
                } else {                                                  
                    s = ''+                                               
                        this.x  +': '+ this.y;                            
                }                                                         
                return s;                                                 
            }                                                             
        },                                                                
        labels: {                                                         
            items: [{                                                     
                html: title2,                          
                style: {                                                  
                    left: '25px',                                         
                    top: '3px',                                           
                    color: 'black'                                        
                }                                                         
            }]                                                            
        },                                                                
        series: [{                                                        
            type: 'column',                                               
            name: '库存量',                                                 
            data: data1                                         
        },{                                                        
            type: 'column',                                               
            name: '历史库存最大值',                                                 
            data: data2                                         
        },{                                                              
            type: 'pie',                                                  
            name: title2,                                    
            data: data3,                                                           
            center: [30, 40],                                            
            size: 70,                                                    
            showInLegend: false,                                          
            dataLabels: {                                                 
                enabled: false                                            
            }                                                             
        }]                                                                
    });                                     
}
function good_queryData_window_chat2(title,categories,price,inprice,num,profit){
	$('#container').highcharts({                                          
        chart: {
        	
        },                                                                
        title: {                                                          
            text: title                                    
        },                                                                
        xAxis: {                                                          
            categories:categories
        },                                                                                                                                                            
        series: [
                 {                                                        
            type: 'column',                                               
            name: "销量",                                                 
            data: num                                         
        },{                                                              
            type: 'spline',                                               
            name: '进价',                                              
            data: inprice,                               
            marker: {                                                     
            	lineWidth: 2,                                               
            	lineColor: Highcharts.getOptions().colors[1],               
            	fillColor: 'white'                                          
            }                                                             
        },{                                                              
            type: 'spline',                                               
            name: '销售价',                                              
            data: price,                               
            marker: {                                                     
            	lineWidth: 2,                                               
            	lineColor: Highcharts.getOptions().colors[2],               
            	fillColor: 'white'                                          
            }                                                             
        },{                                                              
            type: 'spline',                                               
            name: '总毛利润',                                              
            data: profit,                               
            marker: {                                                     
            	lineWidth: 2,                                               
            	lineColor: Highcharts.getOptions().colors[3],               
            	fillColor: 'white'                                          
            }                                                             
        }]                                                                
    });                                     
}
function good_queryData_window_Log(id){
	$("#queryDate_log_window").modal("show");
	good_queryData_window_id=id;
	good_queryData_window_Log_query();
}
function good_queryData_window_Log_query(page){
	if(page!=undefined||page!=null){
		good_queryData_window_page=page;
	}
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/queryGoodsLog",
		data:{
			id:good_queryData_window_id,
			nowpage:good_queryData_window_page,
			countNum:15
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){

				$("#queryDate_log_window_body").html($.render.queryDate_log_window_body_tr(msg.data));
				var page_html="<li><a href=\"javascript:good_queryData_window_Log_query('"+(parseInt(good_queryData_window_page)-1)+"')\">«</a></li>";
				var startPage=good_queryData_window_page>15?good_queryData_window_page-14:1;
				var endPage=(startPage+30)<msg.pagenum?(startPage+30-1):msg.pagenum;
				for(var ii=startPage;ii<=endPage;ii++){
					var classtype=good_queryData_window_page==ii?"active":"";
					page_html=page_html+"<li class=\""+classtype+"\" id=\"grouppageItem_"+ii+"\"><a href=\"javascript:good_queryData_window_Log_query('"+ii+"',this)\">"+ii+"</a></li>";
				}
				page_html=page_html+"<li><a href=\"javascript:good_queryData_window_Log_query('"+(parseInt(good_queryData_window_page)+1)+"')\">»</a></li>"
				$("#queryDate_log_window_page").html(page_html);
			}else{
					alertError(msg.info,"queryDate_log_window_AlertDiv");
			}
		}
	})
}
function good_queryData_window_Info(id){
	var i=$.layer({
	    type : 3
	});
	$.ajax({
		type : "POST",
		url : ctx+"goods/function/getGoods",
		data:{
			id:id
		},
		success : function(msg) {
			layer.close(i);
			if(msg.success==null||msg.success==undefined||msg.success==true){
				$("#queryDate_info_window").modal("show");
				$("#queryDate_info_window_totalInPrice").html(msg.obj.totalInPrice);
				$("#queryDate_info_window_totalPrice").html(msg.obj.totalPrice);
				$("#queryDate_info_window_inPrice").html(msg.obj.inPrice);
				$("#queryDate_info_window_salesNum").html(msg.obj.salesNum);
			}else{
					alertError(msg.info,"good_queryData_AlertDiv");
			}
		}
	})
}