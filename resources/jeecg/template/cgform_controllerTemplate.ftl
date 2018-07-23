/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.controller;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.service.I${entityName}Service;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.I${entityName}Service;
</#if>
import org.apache.log4j.Logger;


import org.apache.commons.lang3.StringUtils;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.utils.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import org.jeecgframework.web.system.pojo.base.TSUser;
import com.fullbloom.util.data.MyBeanUtils;
import com.fullbloom.interfaces.checkused.${prodName}.I${entityName}CheckUsed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<#-- 列为文件类型的文件代码生成 -->
/**   
 * @Title: Controller  
 * @Description: ${ftl_description}
 * @author onlineGenerator
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/${entityName?uncap_first}Controller")
public class ${entityName}Controller extends BaseController {
	
	private static final Logger logger = Logger.getLogger(${entityName}Controller.class);

	@Autowired
	private I${entityName}Service ${entityName?uncap_first}Service;
	
	


	/**
	 * ${ftl_description}列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public String list(HttpServletRequest request) {
		return "${entityPackage?replace(".","/")}/${entityName?uncap_first}List";
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
    @ResponseBody
	public void datagrid(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		 ${entityName?uncap_first}Service.datagrid(${entityName?uncap_first}, null, user, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	<#if indexs?size != 0 > 
	
	@RequestMapping(params = "checkAllUnique")
    @ResponseBody
    public AjaxJson checkAllUnique(${entityName}Entity ${entityName?uncap_first}) throws Exception {		 
		 boolean isExist =  this.isExist(${entityName?uncap_first});
		 if(isExist) return new AjaxJson(false,"数据重复！");
		 else return AjaxJson.successAjax;	     
	 }	
	 
	@RequestMapping(params = "checkUnique")
    @ResponseBody
    public AjaxJson checkUnique(${entityName}Entity ${entityName?uncap_first}) throws Exception {		 
		 boolean isExist =  ${entityName?uncap_first}Service.isExist(${entityName?uncap_first});
		 if(isExist) return new AjaxJson(false,"数据重复！");
		 else return AjaxJson.successAjax;	     
	 }
	 
	 private boolean isExist(${entityName}Entity ${entityName?uncap_first}) throws Exception {
	 
	 		${entityName}Entity ckEntity = null;
			boolean isExist = false;
			
			<#list indexs as index1 >
			ckEntity = new ${entityName}Entity();				
			<#list index1.fields as field1 >
			<#if field1.javaName != 'deleteFlag' >
			
			ckEntity.set${field1.javaName?cap_first}(${entityName?uncap_first}.get${field1.javaName?cap_first}());
			</#if>	
			</#list>						
			ckEntity.setId(${entityName?uncap_first}.getId());
			isExist = ${entityName?uncap_first}Service.isExist(ckEntity);
			if(isExist) return isExist;
			
			</#list>
			
			return isExist;
	 }
	 
	 </#if>
	 
	 
	 
	@RequestMapping(params = "doSave")
	@ResponseBody
	public AjaxJson doSave(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request, HttpServletResponse response) {
		try{			
			
			<#if indexs?exists && indexs?size != 0 > 
			if(isExist(${entityName?uncap_first}) ) return new AjaxJson(false,"数据重复！");
	 		</#if>
	 		
			
			if(StringUtils.isNotEmpty(${entityName?uncap_first}.getId())){
				${entityName}Entity entity = ${entityName?uncap_first}Service.getEntityById(${entityName?uncap_first});
				MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}, entity);
				${entityName?uncap_first}Service.updateEntity(entity);
			}else {
				${entityName?uncap_first}Service.saveEntity(${entityName?uncap_first});
			}
		}catch(Exception e){
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	

	@RequestMapping(params = "doDelete")
	@ResponseBody
	public AjaxJson doDelete(String ids,HttpServletRequest request, HttpServletResponse response) {
		try{
		
			${entityName}Entity ${entityName?uncap_first} = new ${entityName}Entity();
			${entityName?uncap_first}.setId(ids);
			
			String isUse = ${entityName?uncap_first}Service.checkUsed(${entityName?uncap_first});
			
			if(StringUtils.isEmpty(isUse)){                    
				${entityName?uncap_first}Service.deleteEntity(${entityName?uncap_first});
            }else {
            
            	AjaxJson ajaxJson = new AjaxJson(false,"删除失败,"+isUse);
                return ajaxJson;
            }
			
		}catch(Exception e){
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}


}
