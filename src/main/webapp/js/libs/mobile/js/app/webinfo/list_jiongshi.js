var page_zhaopin=1;
var scroll_zhaopin_list;
var zhaopin_type=0;
$('#section_container').on('pageinit','#zhaopinxinxi',function(){
	
})
$('#section_container').on('articleshow','#zhaopinxinxi',function(){
	scroll_zhaopin_list=J.Refresh( '#down_refresh_goods','pullUp', function(){
	      var scroll = this;
	      refresh_zhaopin(scroll);

	})
  var asides = J.Page.loadContent('webinfo/typeSelect.html?num=6');
  var $asides = $(asides);
  $('#aside_container').append($asides);
	page_zhaopin=1;
	refresh_zhaopin();
	$("#search_goods").unbind("click");
	$("#search_goods").bind("click",function(){
		page_zhaopin=1;
		refresh_zhaopin();
	})
})
	function refresh_zhaopin(scroll){
		var name=$("#goods_neme").val();
		if(scroll==undefined){
			 $.ajax({
		         url :ctx+"jiongshi/getJiongShi",
		         timeout : 9000,
		         contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		         type:"post",
		         data:{
		       	  nowpage:page_zhaopin,
		       	  name:name,
		       	  type:zhaopin_type
		         },
		         success : function(msg){
		       	var html = '';
			            for(var i=0;i<msg.data.length;i++){
			                html += '<li data-icon="next" data-selected="selected">'+
			                	'<a href="'+ctx+'modile/webinfo/jiongshixinxiItem.html?id='+msg.data[i].id+'" class="one-part"  data-target="link">'+
			                '<div >'+
		 	            msg.data[i].title+
		 	            '</div>'+
		 	            '</a></li>'
			            }
			          page_zhaopin=page_zhaopin+1;
			            $('#list_data').html(html);
			            scroll_zhaopin_list.scroller.refresh();
			            if(msg.data.length==0){
		  	            	J.showToast('加载成功,但是已经到底了，亲','success');
		  	            }else{
		  	            	J.showToast('加载成功','success');
		  	            }
		         }
			 });
			}else{
			      $.ajax({
		              url :ctx+"jiongshi/getJiongShi",
		              timeout : 9000,
		              contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		              type:"post",
		              data:{
		            	  nowpage:page_zhaopin,
		            	  name:name,
		            	  type:zhaopin_type
		              },
		              success : function(msg){
		            	var html = '';
		  	            for(var i=0;i<msg.data.length;i++){
		  	                html += '<li data-icon="next" data-selected="selected">'+
		  	              '<a href="'+ctx+'modile/webinfo/jiongshixinxiItem.html?id='+msg.data[i].id+'" class="one-part"  data-target="link">'+
		  	                '<div ">'+
			  	            msg.data[i].title+
			  	            '</div>'+
			  	            '</a></li>'
		  	            }
		  	            page_zhaopin=page_zhaopin+1;
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
function selectType_ZhaoPin(id){
	if(id!=0){
		zhaopin_type=id;
	}else{
		zhaopin_type=0;
	}
	page_zhaopin=1;
	refresh_zhaopin();
	Jingle.Menu.hide();
}

