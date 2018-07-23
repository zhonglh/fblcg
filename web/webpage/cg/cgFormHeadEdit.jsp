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

<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="css/plugins/steps/jquery.steps.css" rel="stylesheet">


<link rel="stylesheet" href="css/layer.css"
	id="layui_layer_skinlayercss">
<link rel="stylesheet" href="css/layer.ext.css"
	id="layui_layer_skinlayerextcss">

<link href="css/toastr.min.css" rel="stylesheet">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
</head>

</head>


<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">


		<form id="form" action="cgFormHeadController.do?doUpdate" class="wizard-big">
			<input type="hidden" id="id" name="id" value="${entity.id }">
			<h1>库表信息</h1>
			<fieldset>
				<h2>库表信息</h2>
				<div class="row">
					<div class="col-sm-6">
					
					                        
						<div class="form-group">
							<label>数据库：</label>                                   
							<select id="dbId" name="dbConfigEntity.id"  class="form-control">
								
								<c:forEach var="dbConfig"   items="${dbConfigs }"   varStatus="status12">
									<option value="${dbConfig.id}" <c:if test="${dbConfig.id ==  entity.dbConfigEntity.id }">selected="selected"</c:if>>${dbConfig.dbUrl}</option>
											
								</c:forEach>
								
							</select>
						</div>
					
						<div class="form-group">
							<label>Schema *</label> <input id="schema" name="schema" value="${entity.schema }" type="text" class="form-control required">
						</div>
						<div class="form-group">
							<label>表名 *</label> <input id="tableName" name="tableName" value="${entity.tableName }"  type="text" class="form-control required">
						</div>
						<div class="form-group">
							<label>表描述 *</label> <input id="content" name="content" value="${entity.content }"  type="text" class="form-control required">
						</div>
						
					</div>
					
					
					
					<div class="col-sm-6">	
						
					                        
						<div class="form-group">
							<label>显示复选框：</label>                                   
							<select id="isCheckbox" name="isCheckbox"  class="form-control">
								<option value="Y"  <c:if test="${entity.isCheckbox ==  'Y' }">selected="selected"</c:if> hassubinfo="true">是</option>
								<option value="N"  <c:if test="${entity.isCheckbox ==  'N' }">selected="selected"</c:if> hassubinfo="true">否</option>
							</select>
						</div>
					                        
						<div class="form-group">
							<label>是否分页：</label>                                   
							<select id="isPagination" name="isPagination"  class="form-control">
								<option value="Y"  <c:if test="${entity.isPagination ==  'Y' }">selected="selected"</c:if> hassubinfo="true">是</option>
								<option value="N"  <c:if test="${entity.isPagination ==  'N' }">selected="selected"</c:if> hassubinfo="true">否</option>
							</select>
						</div>
						
					                        
						<div class="form-group">
							<label>查询模式：</label>                                   
							<select id="querymode" name="querymode"  class="form-control">
								<option value="single"  <c:if test="${entity.querymode ==  'single' }">selected="selected"</c:if> hassubinfo="true">单表查询</option>
								<option value="group"   <c:if test="${entity.querymode ==  'group' }">selected="selected"</c:if>  hassubinfo="true">组合查询</option>
							</select>
						</div>
						
						<div class="form-group">
							<label>包名(全路径) *</label> <input id="packageName" name="packageName" value="${entity.packageName }"  type="text" class="form-control required">
						</div>
						
					</div>
					<div class="col-sm-4">
						<div class="text-center">
							<div style="margin-top: 20px">
								<i class="fa fa-sign-in"
									style="font-size: 180px; color: #e5e5e5"></i>
							</div>
						</div>
					</div>
				</div>

			</fieldset>
			
			<h1>数据库属性</h1>
			<fieldset>
				<h2>数据库属性</h2>
				<div class="row">
					<div class="col-xs-12">
						
						<table width="100%">
						
							<thead>
							
								<th width="80">序号</th>
								<th width="350">字段名称</th>
								<th width="350">字段备注</th>
								<th width="350">字段长度</th>
								<th width="350">小数点</th>
								<th width="350">字段类型</th>		
								<th width="150">是否主键</th>		
								<th width="150">可以为空</th>		
													
							</thead>
						
							<tbody>
							<c:forEach var="column"   items="${columns }"   varStatus="status1">
								<tr>
								<td>${status1.index+1 }</td>
								<td><input type="text" id="columns[${status1.index }].dbName" name="columns[${status1.index }].dbName" value="${column.dbName }"  /></td>
								<td><input type="text" id="columns[${status1.index }].content" name="columns[${status1.index }].content" value="${column.content }" /></td>
								
								<td><input type="text" id="columns[${status1.index }].length" name="columns[${status1.index }].length" value="${column.length }" /></td>
								<td><input type="text" id="columns[${status1.index }].pointLength" name="columns[${status1.index }].pointLength" value="${column.pointLength }" /></td>
								<td>
									<select name="columns[${status1.index}].type" >
											
											<c:forEach var="dbType"   items="${dbTypes }"  >
											<option value="${dbType }" <c:if test="${column.type ==  dbType }">selected="selected"</c:if>>${dbType }</option>
											</c:forEach>
									</select>
								</td>								
							
								<td>
									<select name="columns[${status1.index }].isKey" >
										<option value="Y" <c:if test="${column.isKey ==  'Y' }">selected="selected"</c:if>>是</option>
										<option value="N" <c:if test="${column.isKey ==  'N' }">selected="selected"</c:if>>否</option>
									</select>
								</td>
								
								
								<td>
									<select name="columns[${status1.index }].isNull" >
										<option value="Y" <c:if test="${column.isNull ==  'Y' }">selected="selected"</c:if>>是</option>
										<option value="N" <c:if test="${column.isNull ==  'N' }">selected="selected"</c:if>>否</option>
									</select>
								</td>
								
								
								</tr>
							</c:forEach>
							</tbody>
						
						</table>
						
					</div>
					
						
				</div>

			</fieldset>


			<h1>页面属性</h1>
			<fieldset>
				<h2>页面属性</h2>
				<div class="row">
					<div class="col-xs-12">
						
						<table width="100%">
						
							<thead>							
								<th width="80">序号</th>
								<th width="350">字段名称</th>
								<th width="350">字段备注</th>								
								<th width="350">是否显示</th>
								<th width="350">是否可编辑</th>
								<th width="350">控件类型</th>
								<th width="350">控件长度</th>
								<th width="350">是否查询</th>
								<th width="350">查询类型</th>					
							</thead>
						
							<tbody>
							<c:forEach var="column"   items="${columns }"   varStatus="status1">
								<tr>
									<td>${status1.index+1 }</td>
									<td><input type="text" id="dbName1" name="dbName1" value="${column.dbName }" readonly="readonly" /></td>
									<td><input type="text" id="content1" name="content1" value="${column.content }" readonly="readonly" /></td>
									
									
									
									<td>
										<select name="columns[${status1.index }].isShowList" >
											<option value="Y" <c:if test="${column.isShowList ==  'Y' }">selected="selected"</c:if>>是</option>
											<option value="N" <c:if test="${column.isShowList ==  'N' }">selected="selected"</c:if>>否</option>
										</select>
									</td>
									
									<td>
										<select name="columns[${status1.index }].isShow" >
											<option value="Y" <c:if test="${column.isShow ==  'Y' }">selected="selected"</c:if>>是</option>
											<option value="N" <c:if test="${column.isShow ==  'N' }">selected="selected"</c:if>>否</option>
										</select>
									</td>							
									
									
									<td>
										<select name="columns[${status1.index }].showType" >
											<c:forEach var="easyui"   items="${easyuis }"  >
											<option value="${easyui }" <c:if test="${column.showType ==  easyui }">selected="selected"</c:if>>${easyui }</option>
											</c:forEach>										
										</select>
									</td>
									
									<td><input type="text" id="columns[${status1.index }].fieldLength" name="columns[${status1.index }].fieldLength" value="${column.fieldLength }" /></td>
									
									
									
									<td>
										<select name="columns[${status1.index }].isQuery" >
											<option value="Y" <c:if test="${column.isQuery ==  'Y' }">selected="selected"</c:if>>是</option>
											<option value="N" <c:if test="${column.isQuery ==  'N' }">selected="selected"</c:if>>否</option>
										</select>
									</td>
									
									<td>
										<select name="columns[${status1.index }].queryMode" >
											<option value="single" <c:if test="${column.queryMode ==  'single' }">selected="selected"</c:if>>普通查询</option>
											<option value="group"  <c:if test="${column.queryMode ==  'group'  }">selected="selected"</c:if>>范围查询</option>
										</select>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						
						</table>
						
					</div>
					
						
				</div>

			</fieldset>


			<h1>数据校验</h1>
			<fieldset>
				<h2>数据校验</h2>
				<div class="row">
					<div class="col-xs-12">
						
						<table width="100%">
						
							<thead>
								<th width="80">序号</th>
								<th width="350">字段名称</th>
								<th width="350">字段备注</th>
								<th width="350">校验规则</th>
								<th width="350">字典Code</th>		
								<th width="350">Java类型</th>						
							</thead>
						
							<tbody>
							<c:forEach var="column"   items="${columns }"   varStatus="status1">
								<tr>
									<td>${status1.index+1 }</td>
									<td><input type="text" id="dbName1" name="dbName1" value="${column.dbName }" readonly="readonly" /></td>
									<td><input type="text" id="content1" name="content1" value="${column.content }" readonly="readonly" /></td>
									
									<td><input type="text" id="columns[${status1.index }].fieldValidType" name="columns[${status1.index }].fieldValidType" value="${column.fieldValidType }" /></td>
									<td><input type="text" id="columns[${status1.index }].dictionarys" name="columns[${status1.index }].dictionarys" value="${column.dictionarys }" /></td>
							
									
									<td>
										<select id="javaFullType" name="columns[${status1.index }].javaFullType" onkeydown="Select.del(this,event)" onkeypress="Select.write(this,event)">
											<option value="" ></option>
											<option value="${column.javaFullType }" selected="selected">${column.javaFullType }</option>
											<c:forEach var="javaType"   items="${javaTypes }"  >
											<option value="${javaType }" <c:if test="${column.javaFullType ==  javaType }">selected="selected"</c:if>>${javaType }</option>
											</c:forEach>				
										</select>
									</td>
								</tr>									
							</c:forEach>
							</tbody>
						
						</table>						
					</div>						
				</div>

			</fieldset>
			
			
			


			<h1>约束限制</h1>
			<fieldset>
				<h2>数据校验</h2>
				<div class="row">
					<div class="col-xs-12">
						
						<table width="100%">
						
							<thead>
								<th width="80">序号</th>
								<th width="350">约束名称</th>
								<th width="350">约束字段</th>					
							</thead>
						
							<tbody>
							<c:forEach var="index1"   items="${indexs }"   varStatus="status1">
								<tr>
									<td>${status1.index+1 }</td>					
									<td>
										<input type="hidden" id="indexes[${status1.index }].id" name="indexes[${status1.index }].id" value="${index1.id }" />
										<input type="text" id="indexes[${status1.index }].indexName" name="indexes[${status1.index }].indexName" value="${index1.indexName }" /></td>
									<td><input type="text" id="indexes[${status1.index }].indexField" name="indexes[${status1.index }].indexField" value="${index1.indexField }" /></td>
								</tr>
								
							</c:forEach>
							</tbody>
						
						</table>
						
					</div>
					
						
				</div>

			</fieldset>
			


		</form>




	</div>


	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.6"></script>
	<script src="js/content.min.js?v=1.0.0"></script>
	<script src="js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script
		src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script src="js/demo/bootstrap-table-demo.min.js"></script>
	<script src="js/layer.min.js"></script>

	<script src="js/plugins/staps/jquery.steps.min.js"></script>
	<script src="js/plugins/validate/jquery.validate.min.js"></script>
	<script src="js/plugins/validate/messages_zh.min.js"></script>

	<script src="plug-in/ajax/AjaxUtil.js"></script>
	<script src="plug-in/ajax/curd.js"></script>
	<script src="plug-in/toastr/toastr.min.js"></script>
	<script src="plug-in/toastr/toastr.js"></script>




	<script>
		var form = $("#form");
		
		

		form.steps(
				{
					headerTag : "h1",
					bodyTag : "fieldset",
					transitionEffect : "slideLeft",
					onCanceled : function(event) {
						top.layer.closeAll();
					},
					onStepChanging : function(event, currentIndex, newIndex) {
						// Allways allow previous action even if the current form is not valid!
						if (currentIndex > newIndex) {
							return true;
						}
						// Forbid next action on "Warning" step if the user is to young
						if (newIndex === 3 && Number($("#age-2").val()) < 18) {
							return false;
						}
						// Needed in some cases if the user went back (clean up)
						if (currentIndex < newIndex) {
							// To remove error styles
							form.find(".body:eq(" + newIndex + ") label.error")
									.remove();
							form.find(".body:eq(" + newIndex + ") .error")
									.removeClass("error");
						}
						form.validate().settings.ignore = ":disabled,:hidden";
						return form.valid();
					},
					onStepChanged : function(event, currentIndex, priorIndex) {
						// Used to skip the "Warning" step if the user is old enough.
						if (currentIndex === 2
								&& Number($("#age-2").val()) >= 18) {
							form.steps("next");
						}
						// Used to skip the "Warning" step if the user is old enough and wants to the previous step.
						if (currentIndex === 2 && priorIndex === 3) {
							form.steps("previous");
						}
					},
					onFinishing : function(event, currentIndex) {
						form.validate().settings.ignore = ":disabled";
						return form.valid();
					},
					onFinished : function(event, currentIndex) {
						

						var actionurl=$('form').attr('action');
						   
						var formData = $('form').serializeArray();
						
						var ajax =  ajaxSyncData(actionurl,formData);
						
						if(ajax.success){	
							topShwoMessage("info",ajax.msg);

							window.setTimeout(function(){top.layer.closeAll();},2000);
							top.iframe20.window.refreshTable();
							
						}else {
							topShwoMessage("error",ajax.msg);
						}
						
					}
				}).validate({
			errorPlacement : function errorPlacement(error, element) {
				element.before(error);
			},
			rules : {
				confirm : {
					equalTo : "#password"
				}
			}
		});
		
		
		$('table').bootstrapTable({height:300});

	</script>

</body>
</html>