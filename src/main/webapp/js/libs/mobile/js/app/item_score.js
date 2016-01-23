var id_score_item="";
var scroll_score_item;
$('#section_container').on('pageinit','#item_score',function(){
scroll_score_item=J.Refresh('#item_score_list','pullUp', function(){

	})
})
$('#section_container').on('articleshow','#item_score',function(){
	var arr=window.location.href.split("=");
	if(arr.length<3){
		
		id_score_item=arr[1];
	}else{
		id_score_item=arr[2];
	}
	J.showMask("加载中....");
    $.ajax({
        url :ctx+"service/getScoreDuiHuan",
        timeout : 5000,
        data:{
        	id:id_score_item
            },
        success : function(html){
        	$("#score_image").attr("src",html.data.image);
        	$("#score_price").html("需要:"+html.data.score+"分");
        	$("#score_marks").html(html.data.content);
        	$("#score_num").html("剩余"+html.data.num);
        	scroll_score_item.scroller.refresh();
        	J.Popup.close();
        }
    })
	$("#insert_car_goods").bind("click",function(){
            J.confirm('提示','<div class="input-row"><input id="buy_goods_num" type="text" placeholder="输入数量"/></div>',
            	function(){
            		var num=$("#buy_goods_num").val();
            		var id=$("#goods_id").val();
            		J.showMask("提交中....");
                    $.ajax({
                        url :ctx+"service/UserToScoreduihuan",
                        timeout : 5000,
                        data:{
                        	num:num,
                        	id:id_score_item
                            },
                        success : function(html){
                        	J.Popup.close();
                        	J.showToast(html.info);
                        }
                    })
            	},
            	function(){
            		
            	});
	})
})