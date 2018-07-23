/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

<#if packageStyle == "service">
package ${entityPackage}.controller;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.entity.${entityName}Excel;
import ${entityPackage}.service.I${entityName}Service;
import ${entityPackage}.service.I${entityName}ExcelService;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.${entityName}Excel;
import ${entityPackage}.I${entityName}Service;
import ${entityPackage}.I${entityName}ExcelService;
</#if>
import org.apache.log4j.Logger;


import org.apache.commons.lang3.StringUtils;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.core.utils.ResourceUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author onlineGenerator
 * @Title: Controller
 * @Description: ${ftl_description}
 * @date ${ftl_create_time}
 * @version V1.0
 */
@Scope("prototype")
@Controller
@RequestMapping("/${entityName?uncap_first}ExcelController")
public class ${entityName}ExcelController extends BaseController {

    @Autowired
    private I${entityName}ExcelService ${entityName?uncap_first}ExcelService;
    @Autowired
    private I${entityName}Service ${entityName?uncap_first}Service;

    /**
     * 导入界面, 上传Excel文件页面
     * @param req
     * @return String
     */
    @RequestMapping(params = "upload")
    public String upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "${entityName?uncap_first}ExcelController");
        return "common/upload/pub_excel_upload";
    }

   /**
     * 导入excel
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request) {
        AjaxJson ajaxJson = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            try {
                List<${entityName}Excel> list = ExcelImportUtil.importExcel(file.getInputStream(), ${entityName}Excel.class, params);
                if (list != null && list.size() > 0) {

                    String logId = ${entityName?uncap_first}ExcelService.importExcel(file.getOriginalFilename(), list);

                    if (logId == null) {
                        ajaxJson.setMsg("文件导入成功！");
                    } else {
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("文件导入失败！");
                        ajaxJson.setObj(logId);
                    }
                } else {
                    ajaxJson.setMsg("文件导入失败,请先添加需导入的数据！");
                    ajaxJson.setObj(1);
                    ajaxJson.setSuccess(false);
                    return ajaxJson;
                }
            } catch (Exception e) {
                ajaxJson.setMsg("文件导入失败！");
                logger.error(e.getMessage(), e);
                break;
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ajaxJson;
    }
    
    
    /**
     * 导出excel
     * @param ${entityName?uncap_first}Entity
     * @param dataGrid
     * @param modelMap
     * @throws IOException
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(${entityName}Entity ${entityName?uncap_first}Entity, DataGrid dataGrid, ModelMap modelMap) throws IOException {
       	TSUser user = ResourceUtil.getSessionUserName();
		${entityName?uncap_first}Service.datagrid(${entityName?uncap_first}Entity, null, user, dataGrid);
        List<${entityName}Excel> ${entityName?uncap_first}Excels = this.${entityName?uncap_first}ExcelService.exportXls(dataGrid);
        modelMap.put(NormalExcelConstants.FILE_NAME, "${ftl_description}");
        modelMap.put(NormalExcelConstants.CLASS, ${entityName}Excel.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("${ftl_description}列表", "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, ${entityName?uncap_first}Excels);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(ModelMap modelMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        modelMap.put(TemplateExcelConstants.FILE_NAME, "${ftl_description}列表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("export/template/${entityName?uncap_first}.xls"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
}