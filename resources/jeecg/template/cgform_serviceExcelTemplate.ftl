/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */
<#if packageStyle == "service">
package ${entityPackage}.service;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.entity.${entityName}Excel;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.${entityName}Excel;
</#if>

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface I${entityName}ExcelService {
	
	/**
	 * 导入excel
	 * @param originalFilename
	 * @return list
	 */
	String importExcel(String originalFilename, List<${entityName}Excel> list) throws Exception;
	
	
	
	/**
	 * 导出Exceld
	 * @param dataGrid
	 * @param 
	 * @return
	 */
	public List<${entityName}Excel> exportXls( DataGrid dataGrid);
	
}
