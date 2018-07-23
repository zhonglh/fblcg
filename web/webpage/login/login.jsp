<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
  session.setAttribute("lang","zh-cn");
%>
<!DOCTYPE html>
<html lang="cn">

<head>

</head>


<head>
  
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>福布罗代码生成平台</title>

    <link rel="shortcut icon" href="img/favicon.ico"> 
    <link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="css/animate.min.css" rel="stylesheet">
    
    <link href="css/toastr.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">CG</h1>
            </div>
            <h3>欢迎使用福布罗代码生成平台</h3>
            <form class="m-t" role="form" class="form-horizontal"  check="loginController.do?checkUser"  action="loginController.do?login" >
                <div class="form-group">
                    <input type="text" id="userName" name="userName" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="密码" required="">
                </div>
                <button type="button" id="but_login"  onclick="beforeSubmit()"  class="btn btn-primary block full-width m-b">登 录</button>
                <p class="text-muted text-center">  <a href="register.html">注册一个新账号</a>
                </p>

            </form>
        </div>
    </div>
  





<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/toastr/toastr.min.js"></script>
<script type="text/javascript" src="plug-in/toastr/toastr.js"></script>
<script type="text/javascript" src="plug-in/ajax/AjaxUtil.js"></script>
<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>

<script type="text/javascript">
function beforeSubmit(){
	
	var d = checkUser();
	if(!d.success){
		toastr.error("用户名密码错误，请重新输入");
		return false;
	}else {
		var actionurl=$('form').attr('action');
		window.location.href = actionurl;
	}
}


function checkUser(){
	
	var checkurl=$('form').attr('check');
	   
	var formData = new Object();
	var data=$(":input").each(function() {
		formData[this.name] =$("#"+this.name ).val();
	});	    
	
	return ajaxSyncData(checkurl,formData);
    
}
</script>

</body>
</html>