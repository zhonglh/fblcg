<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  
  <#assign datagridname = entityName?uncap_first + 'List' />
  <t:datagrid name="${datagridname}" checkbox="false" fitColumns="true" title="" actionUrl="${entityName?uncap_first}Controller.do?datagrid" idField="id" fit="true" queryMode="group">
  
  	<t:dgCol title="ID"  field="id"  hidden="true" ></t:dgCol>
  
  	<#list columns as po>  	
  	<#assign key=po.dbName?lower_case >
  	
  	<#if po.isShowList =='Y'>
		<t:dgCol title="${po.content}"  field="<#if po.javaFullType?starts_with('java')>${po.javaName}<#else>${po.javaName}.id</#if>" width="${po.fieldLength?c}"  <#if po.javaType == 'Date'>formatter="yyyy-MM-dd"</#if>
		query="<#if po.isQuery=='Y'>true<#else>false</#if>" queryMode="${po.queryMode}" <#if po.dictionarys?length != 0>dictionary="${po.dictionarys}"</#if>
		<#if po.isShow == 'Y' >
			<#if po.showType == 'combobox' >
			extendParams="editor:{
							type:'${po.showType}',
							options:{
								valueField:'code',
								textField:'name',
								<#if indexOnField?seq_contains(po.dbName?lower_case) >validType:['validate_${fieldName_indexName[key]}'],</#if>
								required:<#if po.isNull == 'N' >true<#else>false</#if>,
								editable:false,
								method:'get',
								url:'todo url'}}}" />
			<#elseif po.showType == 'combogrid' >
			extendParams="editor:{type:'combogrid',
	                            options:{
	                                panelWidth: 600,
	                                panelHeight: 350,
	                                striped: true,
	                                emptyMsg:'没有记录',
                                    loadMsg:'正在努力查询...',
	                                rownumbers:true,
	                                pagination:true,
	                                idField: 'id',
	                                textField: 'name',
	                                validType:['valid${po.javaName?cap_first}'<#if indexOnField?seq_contains(po.dbName?lower_case) >,'validate_${fieldName_indexName[key]}'</#if>],
								    required:<#if po.isNull == 'N' >true<#else>false</#if>,
	                                editable:true,
	                                mode: 'remote',
	                                url:'todo url',
	                                method: 'get',
	                                columns: [[
	                                    {field:'id',title:'id',width:80,hidden:true},
	                                     {field:'name',title:'名称',width:200}
	                                ]],
	                                fitColumns: true,
	                                onLoadSuccess : function(data){
									}
							}}"	/>					
			<#elseif po.showType == 'combotree' >
			extendParams="editor:{
							type:'combotree',
							options:{
								valueField:'id',
								textField:'name',
								method:'get',
								url:'todo url',
								<#if indexOnField?seq_contains(po.dbName?lower_case) >validType:['validate_${fieldName_indexName[key]}'],</#if>
								required:<#if po.isNull == 'N' >true<#else>false</#if>,
								editable:false,
								onBeforeSelect : function(node){
										var t = $(this).tree;
										var isLeaf = t('isLeaf', node.target);
										if (!isLeaf) {
											//tip('请选择叶子节点');
											return false;
										}
								}
							}}}" />  			
			<#else>
				extendParams="editor:{type:'${po.showType}',options:{required:<#if po.isNull == 'N' >true<#else>false</#if>,<#if po.showType == 'datebox'>editable="false",</#if> validType:[<#if indexOnField?seq_contains(po.dbName?lower_case) >'validate_${fieldName_indexName[key]}',</#if>'length[<#if po.isNull == 'N' >1<#else>0</#if>,${po.length?c}]']<#if po.pointLength?exists && po.pointLength != 0 >,precision:${po.pointLength}</#if>}}, "/>
			</#if>		
		<#else>
			>
		</#if>	
	<#else>
		<#if indexAllFields?seq_contains(po.dbName?lower_case) >
		<t:dgCol   field="<#if po.javaFullType?starts_with('java')>${po.javaName}<#else>${po.javaName}.id</#if>"  hidden="true" ></t:dgCol>
  		</#if>
  	</#if>
  	</#list>
  

	<t:dgToolBar title="添加" icon="icon-add" operationCode="btn-add" url="" funname="appendRow"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="icon-edit" operationCode="btn-edit" url="" funname="editRow"></t:dgToolBar>
    <t:dgToolBar title="保存" icon="icon-save" operationCode="btn-save" url="${entityName?uncap_first}Controller.do?doSave" funname="saveRow"></t:dgToolBar>
    <t:dgToolBar title="取消" icon="icon-cancel" operationCode="btn-cancel" url="" funname="cancel"></t:dgToolBar>
    <t:dgToolBar title="删除" icon="icon-remove" operationCode="btn-delete"  url="${entityName?uncap_first}Controller.do?doDelete" funname="deleteALLSelect"></t:dgToolBar>
    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="btn-import"></t:dgToolBar>
    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="btn-export"></t:dgToolBar>
    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="btn-templete"></t:dgToolBar>


  </t:datagrid>
  </div>
 </div>	
 <script type="text/javascript">
 
 	$(document).ready(function(){
 		//给时间控件加上样式
 		<#list columns as po>
 		<#if (po.javaType == 'Date' &&  po.queryMode=="single" &&  po.isShowList=="Y") >
 			$("#${entityName?uncap_first}Listtb").find("input[name='${po.javaName}']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 		</#if>
		</#list>
 	});
 	
 	$(document).ready(function () {
	
		$.extend($.fn.combogrid.defaults.rules, {
		
			<#list columns as po>  	
			<#if po.showType == 'combogrid' && po.isNull == 'N' >
			valid${po.javaName?cap_first}: {
			    validator: function (value) {
			       
			            var field = $("#${datagridname}).datagrid('getEditor', {
	                        index: 0,
	                        field: '${po.javaName}.id'
	                    });
	                    var v = $(field.target).combobox('getValue');    
	                    var t = $(field.target).combobox('getText');                    
	                    return (v == null || v == undefined || v == "") && (t != null && t != undefined && t != "")  ;			       
			    },
			    message: '请选择正确的数据！'
			}<#if !po_has_next>,</#if>
			
	    	</#if>
			</#list>
		
		});
 	

    });
 	
 	<#if indexs?size != 0>
 	$(document).ready(function () {
 	
	<#list indexs as index1>
	
		$.extend($.fn.${index1.fields[index1.fields?size-1].showType}.defaults.rules, {
			validate_${index1.indexName}: {
			
 				validator: function (value) {
                    var json = $("#${datagridname}").datagrid('getSelected');
                    var index1=getEditIndex("${datagridname}");
                    if(index1 == undefined) index1 = $("#${datagridname}").datagrid('getRowIndex',json);
                    
                    var id = json.id;
                    var param = {};
                    if(id != undefined ) {
                        param.id = id;
                    }
                    
                    <#list index1.fields as field1>
                    <#if field1.isShow=='Y'>
	                    var ${field1.javaName}_Obj = $("#${datagridname}").datagrid('getEditor', {index: index1, field: '<#if field1.javaFullType?starts_with('java')>${field1.javaName}<#else>${field1.javaName}.id</#if>'});
	                    <#if field1.showType=='validatebox'>
	                    var ${field1.javaName}_Val = $(${field1.javaName}_Obj.target).val();
	                    <#else>
	                    var ${field1.javaName}_Val = $(${field1.javaName}_Obj.target).${field1.showType}('getValue');
	                    </#if>
	                    <#if field1.javaFullType?starts_with('java')>
	                   	param.${field1.javaName} = ${field1.javaName}_Val;
	                    <#else>
	                    param.[${field1.javaName}.id] = ${field1.javaName}_Val;
	                    </#if>
	                 <#else>
						<#if field1.javaFullType?starts_with('java')>
	                   	param.${field1.javaName} = json.${field1.javaName};
	                    <#else>
	                    param.[${field1.javaName}.id] = json.[${field1.javaName}.id];
	                    </#if>
                    </#if>
                    
                    </#list>

                    var resultJson = ajaxSyncData('${entityName?uncap_first}Controller.do?checkUnique',param);
                    return resultJson.success;
                },
                //todo According to the business to rewrite the information
                message: '数据重复'			
		 	}
		});
	</#list>
	});
	</#if>
 
 
  	//导入
    function ImportXls() {
        openuploadwin('Excel导入', '${entityName?uncap_first}ExcelController.do?upload',"${entityName?uncap_first}List");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("${entityName?uncap_first}ExcelController.do?exportXls","${entityName?uncap_first}List");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("${entityName?uncap_first}ExcelController.do?exportXlsByT","${entityName?uncap_first}List");
    }
 
 </script>