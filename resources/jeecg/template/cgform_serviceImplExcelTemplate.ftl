/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */
<#if packageStyle == "service">
package ${entityPackage}.service.impl;
import ${entityPackage}.entity.${entityName}Entity;
import ${entityPackage}.entity.${entityName}Excel;
import ${entityPackage}.service.I${entityName}Service;
import ${entityPackage}.service.I${entityName}ExcelService;
import ${entityPackage}.service.validate.*;
<#else>
package ${entityPackage};
import ${entityPackage}.${entityName}Entity;
import ${entityPackage}.${entityName}Excel;

import ${entityPackage}.I${entityName}Service;
import ${entityPackage}.I${entityName}ExcelService;
import ${entityPackage}.validate.*;
</#if>

import com.fullbloom.ftp.common.excellog.entity.ImportExcelDetailEntity;
import com.fullbloom.ftp.common.excellog.service.ImportExcelMainServiceI;

<#list columns as po>			
<#if po.dbName != jeecg_table_id>
<#if po.isShowList != 'N'  && po.isShow != 'N'>
<#if !po.javaFullType?starts_with('java')>	
import ${po.javaFullType} ;
</#if>
</#if>
</#if>
</#list>

import com.fullbloom.util.data.MyBeanUtils;
import com.fullbloom.interfaces.IExcelValidate;
import com.fullbloom.interfaces.enums.EnumDeleteState;
import com.fullbloom.interfaces.enums.EnumModume;
import com.fullbloom.util.BusinessUtil;
import com.fullbloom.util.data.StringUtil;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ${entityName}ExcelServiceImpl extends AbstractServiceImpl implements I${entityName}ExcelService {



    @Autowired
    private ImportExcelMainServiceI importExcelMainService;
    
    @Autowired
    private I${entityName}Service ${entityName?uncap_first}Service;

    @Override
    public List<${entityName}Excel> exportXls(DataGrid dataGrid) {
        List<${entityName}Excel> excels = new ArrayList<${entityName}Excel>();
        List<${entityName}Entity> list = dataGrid.getResults();

        if (list != null && list.size() > 0) {
            for (${entityName}Entity entity : list) {
                ${entityName}Excel excel = new ${entityName}Excel();
                try {
                    MyBeanUtils.copyBeanNotNull2Bean(entity,excel);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
                //todo deal with other matters 
                excels.add(excel);
                
            }
        }
        return excels;
    }
    
    
    /**
     * 组织校验类
     */
    private List<IExcelValidate<${entityName}Excel,${entityName}Entity>> getValidates(){
    
    	List<IExcelValidate<${entityName}Excel,${entityName}Entity>> validates = new ArrayList<IExcelValidate<${entityName}Excel,${entityName}Entity>>();
    	
		<#list columns as po>			
		<#if po.dbName != jeecg_table_id>
		<#if po.isShowList != 'N'  && po.isShow != 'N'>		
		
		<#assign jname = po.javaName />
		<#if !po.javaFullType?starts_with("java") >
		<#assign jname = po.javaName + 'Name' />
		</#if>
		<#if po.javaFullType?starts_with('java')>		
		${jname?cap_first}Validate  ${jname}Validate = new ${jname?cap_first}Validate();
		validates.add(${jname}Validate);
		<#else>
		Map<String,${po.javaType}> ${jname}Map = new HashMap<String,${po.javaType}>();
		${jname?cap_first}Validate  ${jname}Validate = new ${jname?cap_first}Validate(${jname}Map);
		validates.add(${jname}Validate);
		</#if>		
		</#if>
		</#if>
		</#list>
		
		<#list indexs as index1>
		
		Map<String,${entityName}Entity> map${index1_index + 1} = new HashMap<String,${entityName}Entity>();
		Unique${index1_index + 1}Validate unique${index1_index + 1}Validate = new Unique${index1_index + 1}Validate(map${index1_index + 1});
		validates.add(unique${index1_index + 1}Validate);		
		</#list>
    	
    	return validates;
    }
    
    
    /**
     * 导入Excel
     *
     * @param filename
     * @param list
     * @return
     */
    @Override
    public String importExcel(String filename, List<${entityName}Excel> list) throws Exception {

		List<IExcelValidate<${entityName}Excel,${entityName}Entity>> validates = this.getValidates();

        int index = 1;
        boolean isAllRight = true;
        List<${entityName}Entity> entitys = new ArrayList<${entityName}Entity>();
        for (${entityName}Excel excel : list) {
            excel.setRowno(index);
            ${entityName}Entity entity = new ${entityName}Entity();
            //todo   Set the initial value , example Department , User and other hidden value 
            
            for (IExcelValidate<${entityName}Excel, ${entityName}Entity> validate : validates) {
                validate.validate(excel, entity);
            }
            if (!excel.isSuccess)
                isAllRight = false;
            else {
                entity.setDeleteFlag(EnumDeleteState.Normal.getCode());
                entitys.add(entity);
            }
            index++;
        }
        if (isAllRight) {
            this.${entityName?uncap_first}Service.saveEntity(entitys);
            return null;
        } else {
            List<ImportExcelDetailEntity> detailEntitys = new ArrayList<ImportExcelDetailEntity>();
            for (${entityName}Excel excel : list) {
                if (excel.isSuccess)  continue;
                ImportExcelDetailEntity detailEntity = new ImportExcelDetailEntity();
                detailEntity.setRownumber(excel.getRowno());
                detailEntity.setMessage(excel.getErrorMessage());
                detailEntitys.add(detailEntity);
            }
            return importExcelMainService.saveImportExcelInfo(filename, EnumModume.Modume${entityName}.getId(), detailEntitys);
        }
    }






}

