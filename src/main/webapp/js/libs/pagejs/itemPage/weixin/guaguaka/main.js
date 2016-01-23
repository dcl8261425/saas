var guaguakaNum=0;
var huojiangText="";
(function(bodyStyle) {
        bodyStyle.mozUserSelect = 'none';
        bodyStyle.webkitUserSelect = 'none';

        var img = new Image();
        var canvas = document.querySelector('canvas');
        canvas.style.backgroundColor='transparent';

        img.addEventListener('load', function(e) {
            var ctx2;
            var w = img.width,
                h = img.height;
            var offsetX = canvas.offsetLeft,
                offsetY = canvas.offsetTop;
            var mousedown = false;
            function layer(ctx2) {
                ctx2.fillStyle = 'gray';
                ctx2.fillRect(0, 0, w, h);
            }

            function eventDown(e){
                e.preventDefault();
                mousedown=true;
            }

            function eventUp(e){
                e.preventDefault();
                mousedown=false;
                if(guaguakaNum>100){
					$("#weixin_jifenduihuan_duihuan_window_enter").attr("href","javascript:weixin_jifenduihuan_duihuan_window_close()");
					$('.cd-popup').addClass('is-visible');
					$("#jifenduihuan_duihuan_alert_body").html(huojiangText);
    					
    					
    			}else{
    				$("#weixin_jifenduihuan_duihuan_window_enter").attr("href","javascript:weixin_jifenduihuan_duihuan_window_close()");
					$('.cd-popup').addClass('is-visible');
					$("#jifenduihuan_duihuan_alert_body").html(huojiangText);
    			}
            }

            function eventMove(e){
                e.preventDefault();
                if(mousedown) {
                    if(e.changedTouches){
                        e=e.changedTouches[e.changedTouches.length-1];
                    }
                    var x = (e.clientX + document.body.scrollLeft || e.pageX) - offsetX || 0,
                        y = (e.clientY + document.body.scrollTop || e.pageY) - offsetY || 0;
                    y=y-90;
                    guaguakaNum=guaguakaNum+1;
                    with(ctx2) {
                        beginPath()
                        arc(x, y, 5, 0, Math.PI * 2);
                        fill();
                    }
                }
            }
            canvas.width=w;
            canvas.height=h;
            canvas.style.backgroundImage='url('+img.src+')';
            ctx2=canvas.getContext('2d');
            ctx2.fillStyle='transparent';
            ctx2.fillRect(0, 0, w, h);
            layer(ctx2);

            ctx2.globalCompositeOperation = 'destination-out';

            canvas.addEventListener('touchstart', eventDown);
            canvas.addEventListener('touchend', eventUp);
            canvas.addEventListener('touchmove', eventMove);
            canvas.addEventListener('mousedown', eventDown);
            canvas.addEventListener('mouseup', eventUp);
            canvas.addEventListener('mousemove', eventMove);
        });
        $.ajax({
			type : "POST",
			url : ctx+"weixin/public/getguaguakaRun",
			success : function(msg) {
				if(msg.success){
					huojiangText=msg.jiangpin+".序列号："+msg.xuliehao
					img.src = ctx+'img/zhongjiang.png';
				}else{
					huojiangText=msg.info;
					img.src =  ctx+'img/weizhongjiang.jpg';
				}
				canvas.style.backgroundImage='url('+img.src+')'; 
			}
		})
    })(document.body.style);
  $(document).ready(function(){
	  $(".play").click(animate).mouseover(function(){
	  	$(".play").css({"background-color":"#A55"});
	  }).mouseout(function(){
	  	$(".play").css({"background-color":"#060"});
	  });
	});
	function animate(){
		  
	}
	function weixin_jifenduihuan_duihuan_window_close(){
		$('.cd-popup').removeClass('is-visible');
}