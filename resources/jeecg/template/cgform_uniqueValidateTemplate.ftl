
/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */
<#if packageStyle == "service">
package ${entityPackage}.service.validate;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.entity.${entityName}Excel;
import ${entityPackage}.service.I${entityName}Service;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.${entityName}Excel;
import ${entityPackage}.I${entityName}Service;
</#if>

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.fullbloom.platform.SpringUtil;
import com.fullbloom.interfaces.IExcelValidate;




/**
 * 处理唯一索引  ${theIndex.indexName}
 */
public class ${valiType} implements IExcelValidate<${entityName}Excel,${entityName}Entity>{

	
	private I${entityName}Service  businessService = SpringUtil.getBean(I${entityName}Service.class);
	private String indexFieldNames = "(${indexFields})";
	
	Map<String,${entityName}Entity> map = new HashMap<String,${entityName}Entity>();
	
	public ${valiType}(Map<String,${entityName}Entity> map){
		this.map = map;			
	}
		
	/**
	 * 获取 ${entityName}Entity 对象
	 */
	private ${entityName}Entity get${entityName}Entity(${entityName}Entity entity){
		${entityName}Entity ce = new ${entityName}Entity();
		<#list theIndex.fields as po>	
		<#if po.javaName != "deleteFlag" >
		ce.set${po.javaName?cap_first}(entity.get${po.javaName?cap_first}());
		</#if>
		</#list>
		List<${entityName}Entity> list = businessService.getList(ce,null,null);
		if(list == null || list.size() == 0) return null;
		else return list.get(0);
	}
	

	@Override
	public void validate(${entityName}Excel excel, ${entityName}Entity entity) {	
		
	
		<#list theIndex.fields as po>	
		<#if po.javaName != "deleteFlag" >
		if(entity.get${po.javaName?cap_first}() == null <#if po.javaType == "String"> || entity.get${po.javaName?cap_first}().isEmpty() </#if> ){
			return ;
		}
		</#if>
		</#list>
		
		StringBuilder key_ = new StringBuilder("");
		<#list theIndex.fields as po>	
		<#if po.javaName != "deleteFlag" >
		key_.append("___");
		<#if po.javaType == "String">
		key_.append(entity.get${po.javaName?cap_first}());
		<#elseif po.javaType == "Date"> 
		key_.append(com.fullbloom.util.data.DateKit.fmtDateToYMD(entity.get${po.javaName?cap_first}()));
		<#elseif !po.javaFullType?starts_with("java")> 
		key_.append(entity.get${po.javaName?cap_first}().getId());
		<#else >
		key_.append(entity.get${po.javaName?cap_first}());
		</#if>
		</#if>
		</#list>
		String key = key_.toString();
		 ${entityName}Entity temp = map.get(key);
		 if(temp != null){
		 	excel.setErrorMessage(excel.getErrorMessage()+indexFieldNames+"数据重复！"+spacemark);
			excel.setSuccess(false);
			return ;
		 }
		 
		 temp = get${entityName}Entity(entity);
		 if(temp != null){
		 	map.put(key,temp);
		 	excel.setErrorMessage(excel.getErrorMessage()+indexFieldNames+"数据重复！"+spacemark);
			excel.setSuccess(false);
			return ;
		 }
		
		 map.put(key,entity);
	
		
		
		
	}
}