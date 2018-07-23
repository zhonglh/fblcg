/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.entity;
<#else>
package ${entityPackage};
</#if>

import com.fullbloom.interfaces.enums.EnumDeleteState;
import com.fullbloom.interfaces.anno.EntityAnnotation;
import com.fullbloom.interfaces.entity.IdEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Entity
@Table(name = "${tableName}", schema = "${schemaName}")
<#if cgformConfig.cgFormHead.jformPkType?if_exists?html == "SEQUENCE">
@SequenceGenerator(name="SEQ_GEN", sequenceName="${cgformConfig.cgFormHead.jformPkSequence}")  
</#if>
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ${entityName}Entity extends IdEntity implements java.io.Serializable {
	<#list columns as po>
		
	<#if po.dbName != jeecg_table_id>
	<#if po.isShow != 'N'>
	<#assign dictionarys = po.dictionarys />	
	@EntityAnnotation(item_name="${po.content}" <#if ( (dictionarys?length>1) && (dictionarys?index_of(',',1)==-1) ) > , type_group="${po.dictionarys}"</#if> )
	</#if>
	<#if po.dbName?lower_case == 'delete_flag'>
	private Integer deleteFlag = EnumDeleteState.Normal.getCode();
	<#else>
	private <#if po.javaFullType=='java.sql.Blob'>byte[]<#else>${po.javaType}</#if> ${po.javaName};
	</#if>	
	</#if>	
	</#list>		
	
	<#list columns as po>		
	<#if po.javaName != jeecg_table_id>	
	
	
	${po.javaAnnotation}
	public <#if po.javaFullType=='java.sql.Blob'>byte[]<#else>${po.javaType}</#if> get${po.javaName?cap_first}(){
		return this.${po.javaName};
	}
	public void set${po.javaName?cap_first}(<#if po.javaFullType=='java.sql.Blob'>byte[]<#else>${po.javaType}</#if> ${po.javaName}){
		this.${po.javaName} = ${po.javaName};
	}	
	</#if>
	</#list>
}
