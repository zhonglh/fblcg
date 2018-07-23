		/*
		toastr.options = {
	        "closeButton": false, //是否显示关闭按钮
	        "debug": false, //是否使用debug模式
	        "positionClass": "toast-top-full-width",//弹出窗的位置
	        "showDuration": "300",//显示的动画时间
	        "hideDuration": "1000",//消失的动画时间
	        "timeOut": "5000", //展现时间
	        "extendedTimeOut": "1000",//加长展示时间
	        "showEasing": "swing",//显示时的动画缓冲方式
	        "hideEasing": "linear",//消失时的动画缓冲方式
	        "showMethod": "fadeIn",//显示时的动画方式
	        "hideMethod": "fadeOut" //消失时的动画方式
        };*/
		
		
		toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "progressBar": true,
				  "positionClass": "toast-top-center", 
				  "onclick": null,
				  "showDuration": "400",
				  "hideDuration": "1000",
				  "timeOut": "7000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				};
		
		
		function topShwoMessage(type , msg ){
			
			if(type == 'success'){
				toastr.success(msg);
			}else  if(type == 'info'){
				toastr.info(msg);
			}else  if(type == 'warning'){
				toastr.warning(msg);
			}else  if(type == 'error'){
				toastr.error(msg);
			}
			
		}
		
		