$('#section_container').on('pageinit','#zhaopinxinxi',function(){
	scroll_good_item=J.Refresh('#item_goods_list','pullUp', function(){
		scroll_good_item.scroller.refresh();
	})
})
$('#section_container').on('articleshow','#zhaopinxinxi',function(){
	scroll_good_item.scroller.refresh();
	$("img").attr("width","100%");
})