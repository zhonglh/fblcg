



/**
 * 异步方式请求
 * @param url
 * @param callback
 */
function ajaxASync(url,callback){
	var options = {url:url,callback:callback,async:true};
	ajax_Url(options);	
}

function ajaxASyncData(url,data,callback){
	var options = {url:url,callback:callback,async:true,data:data};
	ajax_Url(options);	
}

/**
 * 同步方式请求
 * @param url
 */
function ajaxSync(url){
	var options = {url:url,async:false};
	return ajax_Url(options);	
}
function ajaxSyncData(url,data){
	var options = {url:url,async:false,data:data};
	return ajax_Url(options);	
}



function ajax_Url(op){
	
	var async = op.async;
	
	
	
	if(!async){
		//同步请求 

		var j ;
		$.ajax({
			type : 	op.type || 'POST',		//默认为POST
			url : 	op.url,
			data : 	op.data,
			cache: 	op.cache || false,
			async : false,		
			success : function(data) {
				try{
					var d = $.parseJSON(data);				
					j =  d;		
				}catch(e){					
					top.location.href="./loginController.do?login";
				}
			},
			error: ajaxError
		});
	
		return j;
	
	}else{
		//异步步请求 
		$.ajax({
			type : 	op.type || 'POST',		//默认为POST
			url : 	op.url,
			data : 	op.data,
			cache: 	op.cache || false,
			async : true,		
			success : function(data) {
				var d ; 
				try{
					d = $.parseJSON(data);	
				}catch(e){					
					top.location.href="./loginController.do?login";						
				}
				
				try{
					if (op.callback != null && op.callback != undefined &&  jQuery.isFunction(op.callback)){ 
						op.callback(d);		
					}
				}catch(e){
				}
			},
			error: ajaxError
		});
		
		
	}
	
}





function ajaxError(xhr, ajaxOptions, thrownError){	
		//xhr.responseText
		tip("操作失败，请登录后重试！");
}

