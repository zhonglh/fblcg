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
    <div class="animated">
       
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" onSubmit="javaScript:return false;" id="signupForm" action="dbConfigController.do?doSave">
                        
                        	<input type="hidden" id="id" name="id" value="${entity.id }"/>
                        
                            <div class="form-group">
                                <label class="col-sm-3 control-label">数据库类型：</label>
                                <div class="col-sm-8">
                                   
                                <select id="dbType" name="dbType"  class="form-control">
                                    <option value="oracle"  <c:if test="${entity.dbType ==  'oracle' }">selected="selected"</c:if> hassubinfo="true">oracle</option>
                                    <option value="db2" 	<c:if test="${entity.dbType ==  'db2' }">selected="selected"</c:if> hassubinfo="true">db2</option>
                                    <option value="mysql" 	<c:if test="${entity.dbType ==  'mysql' }">selected="selected"</c:if> hassubinfo="true">mysql</option>
                                </select>
                           
                                   
                                   
                                </div>
                            </div>
                        
                        
                        
                            <div class="form-group">
                                <label class="col-sm-3 control-label">数据库用户名：</label>
                                <div class="col-sm-8">
                                    <input id="dbUsername" name="dbUsername" value="${entity.dbUsername }" class="form-control" type="text" aria-required="true" aria-invalid="true" required="true" class="error">
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">数据库密码：</label>
                                <div class="col-sm-8">
                                    <input id="dbPassword" name="dbPassword" value="${entity.dbPassword }" class="form-control" required="true" type="password">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">确认密码：</label>
                                <div class="col-sm-8">
                                    <input id="confirm_password" name="confirm_password" value="${entity.dbPassword }" class="form-control" required="true" type="password">
                                    <!-- <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span> -->
                                </div>
                            </div>
                            
                           <div class="form-group">
                                <label class="col-sm-3 control-label">数据库地址：</label>
                                <div class="col-sm-8">
                                    <input id="dbUrl" name="dbUrl" value="${entity.dbUrl }" class="form-control" type="text" aria-required="true"  required="true" aria-invalid="true" class="error">
                                </div>
                            </div>
                            
                            
                            
                            <!-- 
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" class="checkbox" id="agree" name="agree"> 我已经认真阅读并同意《H+使用协议》
                                        </label>
                                    </div>
                                </div>
                            </div>
                             -->
                            
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" onclick="submitForm()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
               
    </div>


	<script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
    <script src="js/content.min.js?v=1.0.0"></script>
    
    
    <script src="js/plugins/validate/jquery.validate.min.js"></script>
    <script src="js/plugins/validate/messages_zh.min.js"></script>
    <script src="js/demo/form-validate-demo.min.js"></script>
	<script src="plug-in/ajax/AjaxUtil.js"></script>
    
    <script src="js/layer.min.js"></script>
    
    
	<script src="plug-in/toastr/toastr.min.js"></script>
	<script src="plug-in/toastr/toastr.js"></script>

	<script type="text/javascript">

	
	
	$( "#signupForm" ).validate({
		  rules: {
		    password: "required",
		    confirm_password: {
		      equalTo: "#dbPassword"
		    }
		  }
	});
	
	function submitForm(){
		
		if( !$('form').valid() ) return false;

		var actionurl=$('form').attr('action');
		   
		var formData = $('form').serializeArray();
		
		var ajax =  ajaxSyncData(actionurl,formData);
		
		if(ajax.success){	
			
			topShwoMessage("info",ajax.msg);
			top.iframe10.window.refreshTable();
			window.setTimeout(function(){top.layer.closeAll();},2000);
			
		}else {
			topShwoMessage("error",ajax.msg);
		}
	}
	
	</script>

</body>
</html>