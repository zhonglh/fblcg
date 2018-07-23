/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.service.impl;

import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.service.I${entityName}Service;
<#else>
package ${entityPackage};

import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.I${entityName}Service;
</#if>

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import com.fullbloom.interfaces.checkused.${prodName}.I${entityName}CheckUsed;

import com.fullbloom.util.BusinessUtil;
import com.fullbloom.interfaces.enums.EnumDeleteState;
import com.fullbloom.platform.SpringUtil;
import org.jeecgframework.core.common.service.impl.BaseServiceImpl;



@Service
@SuppressWarnings("serial")
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}Entity> implements I${entityName}Service {

	
	
	protected String getHQL(List<Object> params,${entityName}Entity t, Object otherSearch, Object user){

		StringBuilder sb = new StringBuilder(" from ${entityName}Entity where deleteFlag = ? ");
		params.add(EnumDeleteState.Normal.getCode());
		if(t != null){
	
		<#list columns as po>			
		<#if po.queryable == 'Y'>				
			<#if po.javaFullType == 'java.lang.String'>
				if(StringUtils.isNotEmpty(t.get${po.javaName?cap_first}())){				
					sb = sb.append(" and ${po.javaName} = ? ");
					params.add(t.get${po.javaName?cap_first}());					
				}
			<#else>					
				<#if po.javaFullType?starts_with("java") >
				if(t.get${po.javaName?cap_first}() != null){				
					sb = sb.append(" and ${po.javaName} = ? ");
					params.add(t.get${po.javaName?cap_first}());					
				}
				<#else>						
				if(t.get${po.javaName?cap_first}() != null && t.get${po.javaName?cap_first}().getId() != null){				
					sb = sb.append(" and ${po.javaName}.id = ? ");
					params.add(t.get${po.javaName?cap_first}().getId());					
				}
				</#if>
			</#if>			
		</#if>			
		</#list>		
			
		}
		
		return sb.toString();
	
	}
	
	

	protected String getVagueHQL(List<Object> params,${entityName}Entity t, Object otherSearch, Object user){

		StringBuilder sb = new StringBuilder(" from ${entityName}Entity where deleteFlag = ? ");
		params.add(EnumDeleteState.Normal.getCode());
		
		if(t != null){
		
		<#list columns as po>			
		<#if po.queryable == 'Y'>
			<#if po.javaFullType == 'java.lang.String'>	
				if(StringUtils.isNotEmpty(t.get${po.javaName?cap_first}())){
					<#if (po.showType == 'text' || po.showType == 'textarea' || po.showType == 'validatebox') && po.isShowList == 'Y' >
					sb = sb.append(" and ${po.javaName} like ? ");
					params.add("%"+t.get${po.javaName?cap_first}() + "%");
					<#else>
					sb = sb.append(" and ${po.javaName} = ? ");
					params.add(t.get${po.javaName?cap_first}());
					</#if>
				}
			<#else>				
				<#if po.javaFullType?starts_with("java") >		
				if(t.get${po.javaName?cap_first}() != null){								
					sb = sb.append(" and ${po.javaName} = ? ");
					params.add(t.get${po.javaName?cap_first}());
				}
				<#else>						
				if(t.get${po.javaName?cap_first}() != null && t.get${po.javaName?cap_first}().getId() != null){		
					sb = sb.append(" and ${po.javaName}.id = ? ");
					params.add(t.get${po.javaName?cap_first}().getId());
				}
				</#if>
			</#if>						
		</#if>			
		</#list>	
					
		}
		
		return sb.toString();
	
	}



	@Override
	protected String getOrderBy(List<Object> params, ${entityName}Entity t, Object otherSearch, Object user) {
		//todo according to the order of business settings , example ' order by code '
		return null;
	}
	
	
	@Override
    public String checkUsed(${entityName}Entity ${entityName?uncap_first}Entity) {
        String message = "";
        List<String> list = new ArrayList<String>();
        
		Map<String,I${entityName}CheckUsed> map  = SpringUtil.getBeansOfType(I${entityName}CheckUsed.class);
		
        if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, I${entityName}CheckUsed> entry : map.entrySet()) {
                try {
                    message = entry.getValue().${entityName?uncap_first}CheckUsed(${entityName?uncap_first}Entity.getId());
                    list.add(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            message = BusinessUtil.reorganizeMessage(list);
        }
        return message;
    }
	
	
	@Override
    public void deleteEntity(${entityName}Entity ${entityName?uncap_first}Entity)  {
    
		Map<String,I${entityName}CheckUsed> map  = SpringUtil.getBeansOfType(I${entityName}CheckUsed.class);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, I${entityName}CheckUsed> entry : map.entrySet()) {
                try {
                    entry.getValue().${entityName?uncap_first}AutoDeleteById(${entityName?uncap_first}Entity.getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    
    	//todo  If you want to physically delete, you can call  'super.deleteEntity(entity)'
        String sql = "update ${schemaName}.${tableName} c set c.DELETE_FLAG = ("
                + "select MAX(b.DELETE_FLAG)+1 from ${schemaName}.${tableName} b " 
                <#if indexs?exists && (indexs?size == 1)> 
                + "where 1 = 1  <#list indexs[0].fields as field1 ><#if field1.javaName != 'deleteFlag' > and c.${field1.dbName} = b.${field1.dbName} </#if></#list> "
                </#if>                
                + ") where c.id = ?";
        this.commonDao.executeBySql(sql, ${entityName?uncap_first}Entity.getId());
    }
	
	
}