/*!
 * 用于创建输入框的插件
 * Author：董成龙
 * Date: 2013-8-31
 */
(function($){
	/**
	 * 用于管理所有输入框实例
	 */
	$.dcl_input = {
		dcl_inputArray:new Array(),
		type:"text",
		defaultText:"请输入字符串",
		id:"",
		isOverWriteId:false,
		getInputObjById:function(id){
			obj=$.dcl_input.dcl_inputArray[id];
			//获取对应的对象，用于取得设定好的值
			if(obj==null){
				throw new Error("该对象的id 没有找到请查看是否id为:"+this.id+"的dcl_input对象已经被注销");
			}
			return obj;
		},
		createInput:function(opt){
			//初始化参数默认值
			type=this.type;
			defaultText=this.defaultText;
			id=this.id;
			isOverWriteId=opt["isOverWriteId"];
			//检测是否允许覆盖已存在的元素
			if(opt["isOverWriteId"]==null||opt["isOverWriteId"]==undefined||opt["isOverWriteId"]==""){
				isOverWriteId=$.dcl_input.isOverWriteId;
			}
			if(opt["id"]==null||opt["id"]==undefined||opt["id"]=="")
			{
				throw new Error("初始化dcl_input对象时没有设置id");
				return ;
			}else if($.dcl_input.dcl_inputArray[opt["id"]]!=null&&!isOverWriteId){
				throw new Error("该id已经被创建过，请确认是否重复创建了相同id的input控件  id="+opt["id"]);
				return ;
			}else{
				//替换id的默认值
				id=opt["id"];
			}
			if(!(opt["defaultText"]==null||opt["defaultText"]==undefined||opt["defaultText"]=="")){
				defaultText=opt["defaultText"];
			}
			//如果是文本类型的输入框，只绑定默认的字符串和获得焦点和溢出焦点事件
			if(opt["type"]=="text"){
				$("#"+id).val(defaultText);
				$("#"+id).attr("style","color:rgb(200,200,200);width: 99%;");
				$("#"+id).bind("focus",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==obj["defaultText"]){
						$("#"+this.id).attr("style","width: 99%;");
						this.value="";
					}
				})
				$("#"+id).bind("blur",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==""){
						$("#"+this.id).attr("style","color:rgb(200,200,200);width: 99%;");
						this.value=obj["defaultText"];
					}
				})
			}
			if(opt["type"]=="select"){
				
			}
			//如果是密码类型的输入框，只绑定默认的字符串和获得焦点和溢出焦点事件，并在没有输入字符串的时候设置密码框类型为text以显示提示文字
			if(opt["type"]=="password"){
				$("#"+id).attr("type","text");
				$("#"+id).val(defaultText);
				$("#"+id).attr("style","color:rgb(200,200,200);width: 99%;");
				$("#"+id).bind("focus",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==obj["defaultText"]){
						$("#"+this.id).attr("style","width: 99%;");
						this.type="password";
						this.value="";
					}
				})
				$("#"+id).bind("blur",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==""){
						this.type="text";
						$("#"+this.id).attr("style","color:rgb(200,200,200);width: 99%;");
						this.value=obj["defaultText"];
					}
				})
			}
			if(opt["type"]=="rigi_password"){
				$("#"+id).attr("type","text");
				$("#"+id).val(defaultText);
				$("#"+id).attr("style","color:rgb(200,200,200);width: 80%;");
				$("#"+id).bind("focus",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==obj["defaultText"]){
						$("#"+this.id).attr("style","width: 80%;");
						this.type="password";
						this.value="";
					}
				})
				$("#"+id).bind("blur",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==""){
						this.type="text";
						$("#"+this.id).attr("style","color:rgb(200,200,200);width: 80%;");
						this.value=obj["defaultText"];
					}
				})
				
			}
			if(opt["type"]=="rigi_text"){
				$("#"+id).val(defaultText);
				$("#"+id).attr("style","color:rgb(200,200,200);width: 80%;");
				$("#"+id).bind("focus",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==obj["defaultText"]){
						$("#"+this.id).attr("style","width: 80%;");
						this.value="";
					}
				})
				$("#"+id).bind("blur",function(){
					obj=$.dcl_input.getInputObjById(this.id);
					if(this.value==""){
						$("#"+this.id).attr("style","color:rgb(200,200,200);width: 80%;");
						this.value=obj["defaultText"];
					}
				})
			}
			obj={
				type:type,
				defaultText:defaultText,
				id:id,
				reInit:function(){
					$("#"+this.id).val(this.defaultText);
				}
			}
			$.dcl_input.dcl_inputArray[id]=obj;
			return obj;
		}
	}
})(jQuery)