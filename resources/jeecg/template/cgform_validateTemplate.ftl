<#assign dictionarys = theColumn.dictionarys />
/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */
<#if packageStyle == "service">
package ${entityPackage}.service.validate;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.entity.${entityName}Excel;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.${entityName}Excel;
</#if>

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.fullbloom.util.ValidateUtli;
import com.fullbloom.platform.SpringUtil;
import com.fullbloom.interfaces.IExcelValidate;
<#if !theColumn.javaFullType?starts_with("java") >
import ${theColumn.javaFullType};
</#if>

<#if ( (dictionarys?length>1) && (dictionarys?index_of(',',1)==-1) ) >	
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
</#if>



<#assign jname = theColumn.javaName />
<#if !theColumn.javaFullType?starts_with("java") >
<#assign jname = theColumn.javaName + 'Name' />
</#if>

public class ${jname?cap_first}Validate implements IExcelValidate<${entityName}Excel,${entityName}Entity>{

	<#if !theColumn.javaFullType?starts_with("java") >
	private I${theColumn.javaType?replace('Entity','')}Service  businessService;
	Map<String,${theColumn.javaType}> map = new HashMap<String,${theColumn.javaType}>();
	
	public ${jname?cap_first}Validate(Map<String,${theColumn.javaType?cap_first}> map){
		this.map = map;
		 businessService = SpringUtil.getBean(I${theColumn.javaType?replace("Entity","")}Service.class);	
	}	
		
	/**
	 * 获取 ${theColumn.javaType?cap_first} 对象
	 */
	private ${theColumn.javaType} get${theColumn.javaType}(String name){
		${theColumn.javaType} ${theColumn.javaType?uncap_first} = new ${theColumn.javaType}();
		//todo  rewrite according to the actual situation
		${theColumn.javaType?uncap_first}.setName(name);
		List<${theColumn.javaType}> list =  businessService.getList(${theColumn.javaType?uncap_first},null,null);
		if(list != null && list.size() >0 ) return list.get(0);
		else return null;
	}
	</#if>

	@Override
	public void validate(${entityName}Excel excel, ${entityName}Entity entity) {	
	
	
		if(excel.get${jname?cap_first}() == null || excel.get${jname?cap_first}().isEmpty()){			
		<#if theColumn.isNull == "Y" >
			return ;
		<#else>
			excel.setErrorMessage(excel.getErrorMessage()+"${theColumn.content}不能为空！"+spacemark);
			excel.setSuccess(false);
			return ;
		</#if>
		}
		
		
		<#if !theColumn.javaFullType?starts_with("java") >
		
		${theColumn.javaType} ${theColumn.javaName} = map.get(excel.get${jname?cap_first}());
		if(${theColumn.javaName} != null){
			entity.set${theColumn.javaName?cap_first}(${theColumn.javaName});
			return ;
		}
		
		${theColumn.javaName} = get${theColumn.javaType?cap_first}(excel.get${jname?cap_first}());
		if(${theColumn.javaName} == null){			
			excel.setErrorMessage(excel.getErrorMessage()+"${theColumn.content}错误！"+spacemark);
			excel.setSuccess(false);
		}else {
			map.put(excel.get${jname?cap_first}(),${theColumn.javaName});
			entity.set${theColumn.javaName?cap_first}(${theColumn.javaName});
			return ;
		}			
		
		<#else>		
		
		
		
		<#if  (dictionarys?length == 0) >	
		//校验长度
		if(excel.get${jname?cap_first}().length() > ${theColumn.length}){
			excel.setErrorMessage(excel.getErrorMessage()+"${theColumn.content}长度超出！"+spacemark);
			excel.setSuccess(false);
			return ;
		}		
		</#if>
		
		<#if theColumn.javaType != "String" >
		//检查数据格式
		<#if theColumn.pointLength?exists && theColumn.pointLength != 0 >
		${theColumn.javaType} ${jname} = ValidateUtli.vaildate${theColumn.javaType}(excel.get${jname?cap_first}(),${theColumn.length},${theColumn.pointLength});
		<#else>
		${theColumn.javaType} ${jname} = ValidateUtli.vaildate${theColumn.javaType}(excel.get${jname?cap_first}());
		</#if>
		
		
		if(${jname} == null){
			excel.setErrorMessage(excel.getErrorMessage()+"${theColumn.content}数据格式错误！"+spacemark);
			excel.setSuccess(false);
			return ;
		}
		
		entity.set${theColumn.javaName?cap_first}(${jname});
		<#else>
		
		<#if ( (dictionarys?length>1) && (dictionarys?index_of(',',1)==-1) ) >		
		//字典校验			
		TSType tsType = null;
		try{
			tsType = TSTypegroup.allTypes4Code.get("${dictionarys}".toUpperCase()).get(excel.get${jname?cap_first}());
		}catch(Exceptoin e){
		}
		if(tsType == null){
				ftpAccountBookExcel.setErrorMessage(ftpAccountBookExcel.getErrorMessage()+"${theColumn.content}数据格式错误"+spacemark);
				ftpAccountBookExcel.setSuccess(false);
				return ;
		}
		</#if>	
		entity.set${theColumn.javaName?cap_first}(excel.get${jname?cap_first}());
		</#if>
		
		</#if>
		
		
		
	}
}