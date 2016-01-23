/*
参数说明
w:宽度
h:高度
l:左侧距离
t:顶部距离
bg:默认底色
cbg:鼠标划过后底色

*参考单元格宽高：127，边距：12（可根据自己意思自由设定。只要好看就行）

@ auther:flc
@ time:2014-02-20 09:41:05
@ qq:3407725
@ site:www.flccent.com
*/

//模块介绍
$(function(){
	$(".s-mod ul").fadeIn(300,function(){$(".s-mod .s-mod-loding").remove();})	
	$(".s-mod ul .s-mod-item").each(function(){
		var i=$(this);
		var o={
			w:1*i.attr("w"),
			h:1*i.attr("h"),
			l:1*i.attr("l"),
			t:1*i.attr("t"),
			bg:i.attr("bg"),
			cbg:i.attr("cbg"),
			speed:600
		};
		var wrap=i.find(".s-mod-wrap");
		var def=i.find(".s-mod-def");
		var cur=i.find(".s-mod-cur");
		i.css({width:o.w,height:o.h,left:o.l,top:o.t});
		wrap.css({width:o.w,height:o.h});
		def.css({width:(o.w-24),height:o.h,backgroundColor:o.bg});
		cur.css({width:(o.w-24),height:o.h,backgroundColor:o.cbg,top:o.h});
		
		def.find("span").css({paddingTop:((o.h-def.find("span").height())/2)})
		cur.find("span").css({paddingTop:((o.h-cur.find("span").height())/2)})
		
		//滑动效果
		i.hover(function(){
			def.stop().animate({top:-o.h},o.speed,"easeOutBounce")
			cur.stop().animate({top:0},o.speed,"easeOutBounce")
		},function(){
			def.stop().animate({top:0},o.speed,"easeOutBounce")
			cur.stop().animate({top:o.h},o.speed,"easeOutBounce")
		})
	})
})