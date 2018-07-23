	var Select = {
		del : function(obj, e) {
			if ((e.keyCode || e.which || e.charCode) == 8) {
				var opt = obj.options[0];
				opt.text = opt.value = opt.value.substring(0,
						opt.value.length > 0 ? opt.value.length - 1 : 0);
			}
		},
		write : function(obj, e) {
			if ((e.keyCode || e.which || e.charCode) == 8)
				return;
			var opt = obj.options[0];
			opt.selected = "selected";
			opt.text = opt.value += String.fromCharCode(e.charCode || e.which
					|| e.keyCode);
		}
	}
	
	
	function download(){
		var form=$("<form>");// 定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action",'cgFormHeadController.do?download');// 请求url
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","rows");// 设置属性的名字
		input1.attr("value","test");// 设置属性的值
		$("body").append(form);// 将表单放置在web中
		form.append(input1);
		form.submit();// 表单提交
	}

	function openWin(tableid,title,u,id,width,height){
		
		var w = '850px';
		var h = '400px';
		if(width) w = width;
		if(height) h  = height;
		var url = u;
		if(id != null && id != undefined ){
			url += "&id="+id;
		}
		top.layer.open({
			type: 2,
		    title: title, 
		    shadeClose: true,
		    shade: 0.4,
		    maxmin: true, //开启最大化最小化按钮
		    area: [w, h],
			content: url 
		});
		
	}
	
	
	function createWin(tableid,this1,width,height){

		var title = $(this1).attr('title');
		var url = $(this1).attr('url');
		openWin(tableid,title,url,null,width,height);
		
	}
	
	function updateWin(tableid,this1,width,height){

		var title = $(this1).attr('title');
		var url = $(this1).attr('url');
		var row = getSelect(tableid);
		if(row != null && row.id != null){
			openWin(tableid,title,url,row.id,width,height);
		}
		
		
	}
	
	function ajaxConfirm(tableid,this1 , callback){
		
		var row = getSelect(tableid);
		
		if(row == null || row.id == null ||  row.id == undefined ){
			topShwoMessage("error","请先选择数据！");
			return ;
		}
		
		var title = $(this1).attr('confirm_message') || '您确定要做此操作吗?';
		
		layer.confirm(title, {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			
			window.setTimeout(function(){
				
				var url = $(this1).attr('url');				
				var ajax =  ajaxSyncData(url + "&id="+row.id);
				if(ajax.success){
					layer.closeAll('dialog');
					topShwoMessage("info",ajax.msg);
					refreshTable();
					if(callback) eval(callback);
				}else {
					topShwoMessage("error",ajax.msg);
				}
			
				
				
			},1000);

				
		}, function(){
			
		    
		});	
		

	}
	
	
	

	function ajaxConfirm4Page(tableid,this1){
		
		var row = getMultiSelect(tableid);
		
		if(row == null || row.length <= 0 ||  row[0].id == undefined ){
			topShwoMessage("error","请先选择数据！");
			return ;
		}
		
		var title = $(this1).attr('confirm_message') || '您确定要做此操作吗?';
		
		var ids = "";
		for(var i=0;i<row.length;i++){
			ids += ";"+ row[i].id;
		}
		ids  =ids.substring(1);
		
		layer.confirm(title, {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			
			window.setTimeout(function(){
				
				var url = $(this1).attr('url');				
				var ajax =  ajaxSyncData(url + "&id="+ids);
				if(ajax.success){
					topShwoMessage("info",ajax.msg);
					//refreshParentTable();
					top.iframe20.window.refreshTable();
					window.setTimeout(function(){top.layer.closeAll();},2000);
				}else {
					topShwoMessage("error",ajax.msg);
				}
			
				
				
			},1000);

				
		}, function(){
			
		    
		});
		

		
		

	}

	
	
	function getSelect(tableid){
		var rows = $('#'+tableid).bootstrapTable('getSelections');
		if(rows == null || rows.length <=0) return null;
		else return rows[0];
	}
	

	
	function getMultiSelect(tableid){
		var rows = $('#'+tableid).bootstrapTable('getSelections');
		if(rows == null || rows.length <=0) return null;
		else return rows;
	}
	
    function getHeight() {
        var h = $(window).height() ;
        return h;
    }
	
    
    
    var $bt ;
    var $url ;
    
    
    function refreshTable(){
    	
    	$bt.bootstrapTable('refresh', {url: $url});  
    }  
    
    
    
    function refreshParentTable(){
    	
    	try{
    	
	    	
	    	var iframes = $(window.parent.document).contents().find("iframe");
	        var index='';
	        $.each(iframes,function(n,v) {
	
	            var showstyle = $(v).attr('style');
	            if (showstyle=='display: inline;') {
	                index = n;
	            }
	        });
	        iframes[index].contentWindow.refresh();
        
    	}catch(e){
    		
    	}
    	
    }
	
	function init(tablename,url){
		$url = url;
		$bt = $('#'+tablename).bootstrapTable({
		    url: url,
		    toolbar:'#exampleTableEventsToolbar',
		    dataType:'json',
		    pagination:true,
		    selectItemName:"id",
		    sidePagination:'server',
		    columns: columns1,
		    idField:'id',
		    showPaginationSwitch:true,
		    pageNumber:1,
		    pageSize:10,
		    pageList:[10, 25, 50, 100],
		    searchOnEnterKey:true,
		    showRefresh:true,
		    showToggle:true,
		    striped:true,
		    clickToSelect:true,
		    singleSelect:true,
		    showHeader:true,
		    showFooter:false,
		    height: getHeight()-100
		});
		
	}
	
	

	
