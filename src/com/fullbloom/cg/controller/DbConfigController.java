package com.fullbloom.cg.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fullbloom.cg.entity.DbConfigEntity;
import com.fullbloom.cg.service.IDbConfigService;

import com.fullbloom.framework.core.AjaxJson;
import com.fullbloom.framework.core.dbutil.DataGrid;
import com.fullbloom.framework.util.MyBeanUtils;
import com.fullbloom.framework.util.ResourceUtil;
import com.fullbloom.framework.util.TagUtil;
import com.fullbloom.web.entity.TsUser;

@Controller
@RequestMapping("/dbConfigController")
public class DbConfigController extends BaseController {
	
	@Autowired
	private IDbConfigService dbConfigService; 
	
	

	

	@RequestMapping(params = "toDbConfig")
	public String toRegiste(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		return "cg/dbconfig-list";
	}
	
	
	

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public Map<String,Object> datagrid(DbConfigEntity dc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TsUser user = ResourceUtil.getSessionUser();
		dbConfigService.datagrid(dc, null, user, dataGrid);
		return TagUtil.toJsonObjByTablePlugin(dataGrid);
	}
	
	
	@RequestMapping(params = "checkUnique")
    @ResponseBody
    public AjaxJson checkUnique(DbConfigEntity ckENtity) throws Exception {		 
		 boolean isExist = dbConfigService.isExist(ckENtity);
		 if(isExist) return new AjaxJson(false,"该数据库已经配置过了！");
		 else return AjaxJson.successAjax;	     
	 }
	
	

	@RequestMapping(params = "toEdit")
	public String toEdit(DbConfigEntity dc,HttpServletRequest request,HttpServletResponse response) {
		
		if(StringUtils.isNotEmpty(dc.getId())){
			DbConfigEntity dbConfigEntity = dbConfigService.getEntityById(dc);
			request.setAttribute("entity", dbConfigEntity);
		}else {
			request.setAttribute("entity", dc);
		}
		return "cg/dbconfig-edit";
	}
	
	
	

	@RequestMapping(params = "doSave")
	@ResponseBody
	public AjaxJson doSave(DbConfigEntity dc,HttpServletRequest request, HttpServletResponse response) {
		try{
			
			DbConfigEntity ckEntity = new DbConfigEntity();
			ckEntity.setDbUrl(dc.getDbUrl());
			ckEntity.setId(dc.getId());
			boolean isExist = dbConfigService.isExist(ckEntity);
			if(isExist) return new AjaxJson(false,"该数据库已经配置过了！");
			
			if(StringUtils.isNotEmpty(dc.getId())){
				DbConfigEntity entity = dbConfigService.getEntityById(dc);
				MyBeanUtils.copyBeanNotNull2Bean(dc, entity);
				dbConfigService.updateEntity(entity);
			}else {
				dbConfigService.saveEntity(dc);
			}
		}catch(Exception e){
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	

	@RequestMapping(params = "doDelete")
	@ResponseBody
	public AjaxJson doDelete(DbConfigEntity dc,HttpServletRequest request, HttpServletResponse response) {
		try{
			dbConfigService.deleteEntity(dc);
		}catch(Exception e){
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	

}
