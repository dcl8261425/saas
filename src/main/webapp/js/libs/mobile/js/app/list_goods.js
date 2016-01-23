var page_good=1;
var scroll_good_list;
$('#section_container').on('pageinit','#shangpinzhanshi',function(){
	scroll_good_list=J.Refresh( '#down_refresh_goods','pullUp', function(){
	      var scroll = this;
	      refresh_goods(scroll);
	            
	})
})
$('#section_container').on('articleshow','#shangpinzhanshi',function(){
	page_good=1;
	refresh_goods();
	$("#search_goods").unbind("click");
	$("#search_goods").bind("click",function(){
		page_good=1;
		refresh_goods();
	})
})
function refresh_goods(scroll){
	var name=$("#goods_neme").val();

	if(scroll==undefined){
		 $.ajax({
	         url :ctx+"service/getGoods",
	         contentType:"application/x-www-form-urlencoded; charset=UTF-8",
             type:"post",
	         timeout : 9000,
	         data:{
	       	  nowpage:page_good,
	       	  name:name
	         },
	         success : function(msg){
	       	var html = '';
		            for(var i=0;i<msg.data.length;i++){
		                html += '<li data-icon="next" data-selected="selected">'+
		                	'<a href="#item_goods?id='+msg.data[i].id+'" class="one-part"  data-target="section">'+
		                	'<div class="tag">零售:'+msg.data[i].price+'元/批发:'+msg.data[i].price_pifa+'元</div>'+
		                '<img alt="" style="width: 50px;height: 50px;" src="'+msg.data[i].image+'"/>'+
		                '<div style="position: absolute;padding:1px 5px;top: 40%;left: 20%;">'+
	 	            msg.data[i].name+
	 	            '</div>'+
	 	            '</a></li>'
		            }
		          page_good=page_good+1;
		            $('#list_data').html(html);
		            scroll_good_list.scroller.refresh();
		            if(msg.data.length==0){
	  	            	J.showToast('加载成功,但是已经到底了，亲','success');
	  	            }else{
	  	            	J.showToast('加载成功','success');
	  	            }
	         }
		 });
		}else{
		      $.ajax({
	              url :ctx+"service/getGoods",
	              timeout : 9000,
	              contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	              type:"post",
	              data:{
	            	  nowpage:page_good,
	            	  name:name
	              },
	              success : function(msg){
	            	var html = '';
	  	            for(var i=0;i<msg.data.length;i++){
	  	                html += '<li data-icon="next" data-selected="selected">'+
	  	              '<a href="#item_goods?id='+msg.data[i].id+'" class="one-part"  data-target="section">'+
	  	                	'<div class="tag">零售:'+msg.data[i].price+'元/批发:'+msg.data[i].price_pifa+'元</div>'+
	  	                '<img alt="" style="width: 50px;height: 50px;" src="'+msg.data[i].image+'"/>'+
	  	                '<div style="position: absolute;padding:1px 5px;top: 40%;left: 20%;">'+
		  	            msg.data[i].name+
		  	            '</div>'+
		  	            '</a></li>'
	  	            }
	  	            page_good=page_good+1;
	  	            $('#list_data').append(html);
	  	            scroll.refresh();
	  	            if(msg.data.length==0){
	  	            	J.showToast('加载成功,但是已经到底了，亲','success');
	  	            }else{
	  	            	J.showToast('加载成功','success');
	  	            }
	              }
	          })
		}
}