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
						
						
						    <table id="cgFormHeads">
						    
					    	</table>



						<div class="btn-group hidden-xs" id="exampleTableEventsToolbar"	role="group">						
							
							<button id="b1"  msg="从现有数据库中选取表信息" type="button" class="btn btn-outline btn-default" url='cgFormHeadController.do?toAdd' onClick="createWin('cgFormHeads',this,900,600);">
								<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
							</button>							
						
							<button id="b2"  msg="编辑表格信息" type="button" class="btn btn-outline btn-default" url='cgFormHeadController.do?toEdit' onClick="updateWin('cgFormHeads',this);">
								<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
							</button>								
							
							<button id="b3" type="button" msg="删除无用数据"  class="btn btn-outline btn-default" confirm_message='你确定要删除选择的项目吗?' url='cgFormHeadController.do?doDelete' onClick="ajaxConfirm('cgFormHeads',this);">
								<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
							</button>
							
							<button id="b4" type="button" msg="将表格信息转化为对应的代码，可 减轻一半的工作量 ！" class="btn btn-outline btn-default"  url='cgFormHeadController.do?gc' onClick="ajaxConfirm('cgFormHeads',this,'download()');">
								<i class="glyphicon glyphicon-film" aria-hidden="true"></i>
							</button>
								
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
	        field: 'dbType',
	        title: '数据库类型',
	        width:80
	    },{
	        field: 'schema',
	        title: 'schema',
	        width:80
	    }, {
	        field: 'tableName',
	        title: '表名',
	        width:150
	    },  {
	        field: 'content',
	        title: '表描述',
	        width:600
	    } ];
		
		init('cgFormHeads','cgFormHeadController.do?datagrid');
		
		


		
		$(".btn-outline").mouseover(function(){
				var message = $(this).attr("msg");
				if(message == null || message == "" || message == undefined ) return ;
				var id = $(this).attr("id");
				layer.tips(message,'#'+id, {
				  tips: [1, '#3595CC'],
				  time: 2000
				});
		});
	function refreshTable() {
		$('#cgFormHeads').bootstrapTable('refresh');
	}
	</script>
	
	

</body>
</html>