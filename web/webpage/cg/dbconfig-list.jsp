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
						
						
						    <table id="dbconfigs">
						    
					    	</table>



						<div class="btn-group hidden-xs" id="exampleTableEventsToolbar"	role="group">						
							
								<button id="a1" msg="增加数据库配置"  type="button" class="btn btn-outline btn-default" url='dbConfigController.do?toEdit' onClick="createWin('dbconfigs',this);">
									<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
								</button>
							
								<button id="a2" msg="编辑数据库配置"  type="button" class="btn btn-outline btn-default" url='dbConfigController.do?toEdit' onClick="updateWin('dbconfigs',this);">
									<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
								</button>
								
								<button id="a3" type="button" msg="删除不需要的数据库配置" class="btn btn-outline btn-default" confirm_message='你确定要删除选择的项目吗?' url='dbConfigController.do?doDelete' onClick="ajaxConfirm('dbconfigs',this);">
									<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
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
    }, {
        field: 'dbType',
        title: '数据库类型',
        width:80
    }, {
        field: 'dbUsername',
        title: '数据库名称',
        width:80
    }, {
        field: 'dbPassword',
        title: '数据库密码',
        width:80
    }, {
        field: 'dbUrl',
        title: '数据库地址',
        width:700
    } ];
	
	init('dbconfigs','dbConfigController.do?datagrid');
	

	
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
		$('#dbconfigs').bootstrapTable('refresh');
	}
	
	</script>

</body>
</html>