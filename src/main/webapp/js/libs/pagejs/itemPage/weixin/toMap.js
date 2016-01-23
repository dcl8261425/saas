$(document).ready(function() {
// 百度地图API功能
	var position_option = {
            enableHighAccuracy: true,
            maximumAge: 30000,
            timeout: 20000
        };
	function getPositionError(error) {
		    switch (error.code) {
		        case error.TIMEOUT:
		            alert("连接超时，请重试");
		            break;
		        case error.PERMISSION_DENIED:
		            alert("您拒绝了使用位置共享服务，查询已取消");
		            break;
		        case error.POSITION_UNAVAILABLE:
		            alert("获取位置信息失败");
		            break;
		    }
		}
	var map;
	var geolocation = new BMap.Geolocation();
	navigator.geolocation.getCurrentPosition(function(position){
		var lat = position.coords.latitude;//+0.0008;
        var lng = position.coords.longitude;//-0.0034;
        var point = new BMap.Point(lng ,lat);
		map = new BMap.Map("allmap",{minZoom:12,maxZoom:30}); // 创建Map实例,设置地图允许的最小/大级别
		map.centerAndZoom(point,8); 
		map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

		var iii = window.parent.weixin_model_reflash_open();
		BMap.Convertor.translate(point,0,function(point2){
			var marker = new BMap.Marker(point2);
			map.addOverlay(marker);
			map.setCenter(point2);
			var data=window.parent.mapData;
			for(var i2=0;i2<data.length;i2++){
				//addMapPoint(data[i2].map_x,data[i2].map_y);
				var myP1 = new BMap.Point(data[i2].map_x,data[i2].map_y);   
				var walking = new BMap.WalkingRoute(map, {renderOptions:{map: map, autoViewport: true}});
				walking.search(point2, myP1);

			}
			
			window.parent.weixin_model_reflash_close(); 
		}); 
		
	},getPositionError,position_option)

	function addMapPoint(x,y){
		var point = new BMap.Point(x ,y);
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);               // 将标注添加到地图中
	}
	});