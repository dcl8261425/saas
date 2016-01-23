var crm_window_layout_char_type=1;
$(function () {
	$(document).ready(function(){
		$('#crm_window_layout_char_startDate').datepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd'
		});
		$('#crm_window_layout_char_endDate').datepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd',
		});
		$('#crm_window_layout_char_startDate_text').val(getNowFormatDate(true));
		$('#crm_window_layout_char_endDate_text').val(getNowFormatDate());
		crm_window_layout_char_data_query();
	})
});
/**
 * 改变查询的条件
 * @param type
 */
function crm_window_layout_char_type_set(type){
	
	switch(type){
		case 1:
			crm_window_layout_char_type=type;
			$("#crm_window_layout_char_type_set_1").attr("class","btn btn-danger");
			$("#crm_window_layout_char_type_set_2").attr("class","btn btn-primary");
			$("#crm_window_layout_char_type_set_3").attr("class","btn btn-primary");
			break;
		case 2:
			crm_window_layout_char_type=type;
			$("#crm_window_layout_char_type_set_1").attr("class","btn btn-primary");
			$("#crm_window_layout_char_type_set_2").attr("class","btn btn-danger");
			$("#crm_window_layout_char_type_set_3").attr("class","btn btn-primary");
			
			break;
		case 3:
			crm_window_layout_char_type=type;
			$("#crm_window_layout_char_type_set_1").attr("class","btn btn-primary");
			$("#crm_window_layout_char_type_set_2").attr("class","btn btn-primary");
			$("#crm_window_layout_char_type_set_3").attr("class","btn btn-danger");
			break;
			
	}
	crm_window_layout_char_data_query();
}
/**
 * 执行查询
 */
function crm_window_layout_char_data_query(){
	var start=$("#crm_window_layout_char_startDate_text").val();
	var end=$("#crm_window_layout_char_endDate_text").val();
	addLoadData_div("crm_window_layout_char_window");
	if(start=="开始日期"||end=="结束日期"){
		alertError("请选择要生成数据表的时间","crm_window_layout_char_alert_div");
		removeloadData_div("crm_window_layout_char_window");
	}else{
		$.ajax({
			type : "POST",
			url : ctx+"crm/function/char/query",
			data:{
				type:crm_window_layout_char_type,
				startDate:start,
				endDate:end
			},
			success : function(msg) {
				if(msg.success==null||msg.success==undefined||msg.success==true){
					if(crm_window_layout_char_type==1){
						showChanceType(msg);
						$("#crm_window_layout_char_alert_div").html("");
						alertWarning(msg.info,"crm_window_layout_char_alert_div");
					}
					if(crm_window_layout_char_type==2){
						showChanceNum(msg);
						$("#crm_window_layout_char_alert_div").html("");
						alertWarning(msg.info,"crm_window_layout_char_alert_div");
					}
					if(crm_window_layout_char_type==3){
						showChanceCreate(msg);
						$("#crm_window_layout_char_alert_div").html("");
						alertWarning(msg.info,"crm_window_layout_char_alert_div");
					}

				}else{
					alertError(msg.info,"crm_window_layout_char_alert_div");
				}
				removeloadData_div("crm_window_layout_char_window");
			}
		})
	}
}
/**
 * 显示类别图表
 * @param data
 */
function showChanceType(msg){
	if(msg==undefined){
		
	}else{
	    $('#container').highcharts({
	    	
	        chart: {
	            type: 'column',
	        },
	        title: {
	            text: '机会的状态统计'
	        },
	        subtitle: {
	            text: '统计：机会的5种状态，在每年，每月，每天所改变的量'
	        },
	        xAxis: {
	            categories: msg.date
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '状态 (个数)'
	            }
	        },
	        tooltip: {
	        	 headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	             pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                 '<td style="padding:0"><b>{point.y} 个</b></td></tr>',
	             footerFormat: '</table>',
	             shared: true,
	             useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: msg.data
	    });
	}
}
function showChanceNum(msg){                                                        
	    $('#container').highcharts({                                          
	        chart: {                                                          
	        },                                                                
	        title: {                                                          
	            text: '客户机会的增长'                                     
	        },                                                                
	        xAxis: {                                                          
	            categories: msg.date
	        },            
	        yAxis: {
	            min: 0,
	            title: {
	                text: '个数'
	            }
	        },
	        tooltip: {                                                        
	            formatter: function() {                                       
	                var s;                                                    
	                if (this.point.name) { // the pie chart                   
	                    s = ''+                                               
	                        this.point.name +': '+ this.y +' %';         
	                } else {                                                  
	                    s = ''+                                               
	                        this.x  +': '+ this.y+"个";                            
	                }                                                         
	                return s;                                                 
	            }                                                             
	        },                                                                
	        labels: {                                                         
	            items: [{                                                     
	                html: '客户级别比例',                          
	                style: {                                                  
	                    left: '10px',                                         
	                    top: '0px',                                           
	                    color: 'black'                                        
	                }                                                         
	            }]                                                            
	        },                                                                
	        series: [{                                                        
	            type: 'column',                                               
	            name: '柱状图',                                                 
	            data: msg.obj.data                                        
	        }, {                                                              
	            type: 'spline',                                               
	            name: '线状图',                                              
	            data:msg.obj.data,                               
	            marker: {                                                     
	            	lineWidth: 2,                                               
	            	lineColor: Highcharts.getOptions().colors[3],               
	            	fillColor: 'white'                                          
	            }                                                             
	        }, {                                                              
	            type: 'pie',                                                  
	            name: '客户级别比例',                                    
	            data: msg.count,                                                           
	            center: [30, 40],                                            
	            size: 70,                                                    
	            showInLegend: false,                                          
	            dataLabels: {                                                 
	                enabled: false                                            
	            }                                                             
	        }]                                                                
	    });                                                                                                                                      				
}
function showChanceCreate(msg){   
	$(function () {
	    $('#container').highcharts({
	        chart: {
	            type: 'column',
	            margin: [ 50, 50, 100, 80]
	        },
	        title: {
	            text: '创建客户机会排行榜'
	        },
	        xAxis: {
	            categories: msg.date,
	            labels: {
	                rotation: -45,
	                align: 'right',
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '数量 (次)'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '添加了: <b>{point.y:.1f} 次</b>',
	        },
	        series: [{
	            name: 'Population',
	            data: msg.data,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                x: 4,
	                y: 10,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif',
	                    textShadow: '0 0 3px black'
	                }
	            }
	        }]
	    });
	});
}