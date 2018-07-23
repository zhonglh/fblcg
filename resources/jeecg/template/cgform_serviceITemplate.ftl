/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.service;
import ${entityPackage}.entity.${entityName}Entity;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
</#if>

import org.jeecgframework.core.common.service.IBaseService;

public interface I${entityName}Service extends IBaseService<${entityName}Entity>{
	
	public String checkUsed(${entityName}Entity ${entityName?uncap_first}Entity) ;

}

