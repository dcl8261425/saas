$('#section_container').on('pageinit','#setOrderLinkFangshi',function(){

})
$('#section_container').on('articleshow','#setOrderLinkFangshi',function(){
	 $('#bindWebUser_bind').tap(function(){
	      J.confirm('提示','确定地址，在支付后不能修改',
	    	    function(){
	    	  		var id=$("#setOrder_id").val();
	  	    		var address=$("#setOrder_address").val();
	  	    		var phone=$("#setOrder_phone").val();
	  	    		var zip_code=$("#setOrder_zip_code").val();
	    	  		J.showMask("提交中....");
	                $.ajax({
	                    url :ctx+"service/setOrderAddress",
	                    timeout : 8000,
	                    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	                    type:"post",
	                    data:{
	                    	id:id,
	                    	address:address,
	                    	phone:phone,
	                    	zip_code:zip_code
	                        },
	                    success : function(html){
	                    	J.Popup.close();
	                    	J.showToast(html.info);
	                    	if(html.success){

	                        }
	                    }
	                })
	    		},
	    		function(){

	            }
	      );
	  })
})