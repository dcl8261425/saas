var id_goods_item="";
var scroll_good_item;
$('#section_container').on('pageinit','#item_goods',function(){
	scroll_good_item=J.Refresh('#item_goods_list','pullUp', function(){

	})
})
$('#section_container').on('articleshow','#item_goods',function(){
	var arr=window.location.href.split("=");
	if(arr.length<3){
		
		id_goods_item=arr[1];
	}else{
		id_goods_item=arr[2];
	}
	J.showMask("加载中....");
    $.ajax({
        url :ctx+"service/getGoodsById",
        timeout : 5000,
        data:{
        	id:id_goods_item
            },
        success : function(html){
        	$("#goods_image").attr("src",html.data.image);
        	$("#pifa_price").html("批发价:"+html.data.price_pifa+"元");
        	$("#price").html("批发价:"+html.data.price+"元");
        	$("#pifa_num").html("起批数:"+html.data.price_pifa_num);
        	$("#goods_function").html(html.data.functions);
        	$("#goods_marks").html(html.data.marks);
        	scroll_good_item.scroller.refresh();
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
                        url :ctx+"service/addBuyCar",
                        timeout : 5000,
                        data:{
                        	num:num,
                        	id:id_goods_item
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