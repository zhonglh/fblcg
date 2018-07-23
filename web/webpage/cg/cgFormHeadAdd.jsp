<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
	session.setAttribute("lang", "zh-cn");
%>


<!DOCTYPE html>
<html lang="cn">


<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>福布罗代码生成平台</title>

<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min.css?v=4.1.0" rel="stylesheet">
<link href="css/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
	
	
<link rel="stylesheet" href="css/layer.css" id="layui_layer_skinlayercss">
<link rel="stylesheet" href="css/layer.ext.css" id="layui_layer_skinlayerextcss">

<link href="css/toastr.min.css" rel="stylesheet">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
</head>

</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="ibox float-e-margins">

			<div class="ibox-content">
				<div class="row row-lg">

					<div class="col-sm-12">
						<!-- Example Events -->
						
						
						    <table id="tables">
						    
					    	</table>



						<div class="btn-group hidden-xs" id="exampleTableEventsToolbar"	role="group">
						
							
							<button type="button" id="docg" class="btn btn-outline btn-default" title='你确定要从这个表中读取吗?' url="" suburl='cgFormHeadController.do?doAdds' onClick="ajaxConfirm4Page('tables',this);">
								<i class="glyphicon glyphicon-wrench	" aria-hidden="true"></i>确认添加
							</button>
							
							
							                           
								<select id="dbId" name="dbId" class="form-control">								
									<c:forEach var="dbConfig"   items="${dbConfigs }"   varStatus="status1">
									<option value="${dbConfig.id }"  <c:if test="${dbConfig.id ==  dbId }">selected="selected"</c:if> hassubinfo="true">${dbConfig.dbUrl}</option>
									</c:forEach>
								</select>
						
					
						
						</div>


					</div>
				</div>
			</div>



		</div>
	</div>


	<script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
    <script src="js/content.min.js?v=1.0.0"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="js/demo/bootstrap-table-demo.min.js"></script>
    <script src="js/layer.min.js"></script>

	<script src="plug-in/ajax/AjaxUtil.js"></script>
	<script src="plug-in/ajax/curd.js"></script>
	<script src="plug-in/toastr/toastr.min.js"></script>
	<script src="plug-in/toastr/toastr.js"></script>
	<script type="text/javascript">
	
	
		
		var columns1 = [{
	        field: 'aa',
	        title: 'ID',
	        checkbox:true,
	        width:80
	    },{
	        field: 'id',
	        visible:false
	    },{
	        field: 'name',
	        title: '表名称',
	        width:300
	    } ];
		
		var $bt ;
		var $url ;
		
		function init(tablename,url){
			$url = url;
			$bt = $('#'+tablename).bootstrapTable({
			    url: url,
			    toolbar:'#exampleTableEventsToolbar',
			    dataType:'json',
			    pagination: false,
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
			    singleSelect:false,
			    showHeader:true,
			    showFooter:false,
			    height: getHeight()-100
			});
			
		}
		
		function changeUrl(dbId){
			$("#docg").attr('url', $("#docg").attr('suburl')+'&dbId='+dbId)
		}
		
		changeUrl('${dbId}');
		

		init('tables','cgFormHeadController.do?toAddDatagrid&dbId=${dbId}');
		
		$(window).resize(function () {
			$bt.bootstrapTable('resetView');
	    });

		$(parent).resize(function () {
			$bt.bootstrapTable('resetView');
	    });
		
		$("#dbId").change(function(){
			clkDb(this);
		});
		
		function clkDb(dbConfig){			
			var dbId = $(dbConfig).val();				
			changeUrl(dbId);
			$bt.bootstrapTable('refresh', {url: 'cgFormHeadController.do?toAddDatagrid&dbId='+dbId});  
			
		}
		
		
		
		
	
	</script>
	
	

</body>
</html>