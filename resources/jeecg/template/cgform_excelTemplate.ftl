

/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.entity;
<#else>
package ${entityPackage};
</#if>


import com.fullbloom.interfaces.anno.EntityAnnotation;
import com.fullbloom.interfaces.BaseExcel;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelIgnore;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;


import java.math.BigDecimal;
import java.util.Date;

<#list imports as import1>
import ${import1}; 
</#list>	


/**   
 * @Description: ${ftl_description}
 * @author onlineGenerator
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
@ExcelTarget("${entityName}Excel")
public class ${entityName}Excel extends BaseExcel implements java.io.Serializable {

	<#assign orderNum = 1 />
	
	<#list columns as po>	
	<#if po.dbName != jeecg_table_id>
	<#if po.isShowList != 'N'>	
	<#assign dictionarys = po.dictionarys />
	<#if ( (dictionarys?length>1) && (dictionarys?index_of(',',1)==-1) ) >
	//todo dictionary replace
	</#if>		
	@Excel(name = "${po.content}", width = ${po.fieldLength}, orderNum = "${orderNum}")
	<#assign orderNum = orderNum + 1 />	
	<#if po.javaFullType?starts_with("java") >
	private String ${po.javaName};
	<#else>
	private String ${po.javaName}Name;
	</#if>	
	
	</#if>	
	</#if>	
	</#list>	
		
	<#list columns as po>		
	<#if po.dbName != jeecg_table_id>
	<#if po.isShowList != 'N'>	
	<#assign jname = po.javaName />
	<#if !po.javaFullType?starts_with("java") >
		<#assign jname = po.javaName + 'Name' />
	</#if>
		
	public String get${jname?cap_first}(){
		return this.${jname};
	}
	public void set${jname} (String ${jname}){
		this.${jname} = ${jname};
	}		
	</#if>
	</#if>
	</#list>
}
