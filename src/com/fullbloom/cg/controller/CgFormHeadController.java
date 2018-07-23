package com.fullbloom.cg.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.codegenerate.enums.EnumDbType;
import org.jeecgframework.codegenerate.enums.EnumJavaType;
import org.jeecgframework.codegenerate.generate.CgformCodeGenerate;
import org.jeecgframework.codegenerate.generate.GenerateEntity;
import org.jeecgframework.codegenerate.pojo.Columnt;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.DbConfig;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fullbloom.cg.entity.CgFormFieldEntity;
import com.fullbloom.cg.entity.CgFormHeadEntity;
import com.fullbloom.cg.entity.CgFormIndexEntity;
import com.fullbloom.cg.entity.DbConfigEntity;
import com.fullbloom.cg.service.ICgFormFieldService;
import com.fullbloom.cg.service.ICgFormHeadService;
import com.fullbloom.cg.service.ICgFormIndexService;
import com.fullbloom.cg.service.IDbConfigService;
import com.fullbloom.cg.utils.CgFormFieldUtil;
import com.fullbloom.cg.utils.CgFormUtil;
import com.fullbloom.framework.core.AjaxJson;
import com.fullbloom.framework.core.dbutil.DataGrid;
import com.fullbloom.framework.core.enums.EnumEasyUIEditor;
import com.fullbloom.framework.util.FileUtil;
import com.fullbloom.framework.util.MyBeanUtils;
import com.fullbloom.framework.util.PublicUtil;
import com.fullbloom.framework.util.ResourceUtil;
import com.fullbloom.framework.util.StringSort;
import com.fullbloom.framework.util.StringUtil;
import com.fullbloom.framework.util.TagUtil;
import com.fullbloom.framework.util.file.FileKit;
import com.fullbloom.framework.util.file.ZipKit;
import com.fullbloom.web.entity.TsUser;

@Controller
@RequestMapping("/cgFormHeadController")
public class CgFormHeadController extends BaseController {
	
	@Autowired
	private ICgFormFieldService cgFormFieldService;
	@Autowired
	private ICgFormIndexService cgFormIndexService;
	@Autowired
	private ICgFormHeadService cgFormHeadService;
	

	@Autowired
	private IDbConfigService dbConfigService;
	
	
	

	/**
	 * 自动生成表属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgFormHeadList")
	public ModelAndView cgFormHeadList(HttpServletRequest request) {
		return new ModelAndView("cg/cgFormHeadList");
	}	
	
	/**
	 * 
	 * @param cgFormHead
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public Map<String,Object> datagrid(CgFormHeadEntity cgFormHead, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		cgFormHeadService.datagrid(cgFormHead, null, null, dataGrid);
		return TagUtil.toJsonObjByTablePlugin(dataGrid);
	}
	
	
	@RequestMapping(params = "toAdd")
	public ModelAndView toAdd(HttpServletRequest request) {
		
		List<DbConfigEntity> dbConfigs = dbConfigService.getList(null,null,null);
		
		String dbId = request.getParameter("dbId");
		if(StringUtils.isEmpty(dbId)){
			if(dbConfigs != null && !dbConfigs.isEmpty()) {
				dbId = dbConfigs.get(0).getId();
			}
		}
		request.setAttribute("dbId",dbId );
		
		request.setAttribute("dbConfigs", dbConfigs);
		return new ModelAndView("cg/cgFormHeadAdd");
	}

	@RequestMapping(params = "toAddDatagrid")
	@ResponseBody
	public Map<String,Object> toAddDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", null);
		map.put("total",0);
		
		List<Map<String, String>> indexs = new ArrayList<Map<String, String>>();
		
		String tableName = request.getParameter("tableName");
		String dbId = request.getParameter("dbId");
		
		if(StringUtils.isEmpty(dbId)) return map;
		
		DbConfigEntity dbConfigEntity = new DbConfigEntity();
		dbConfigEntity.setId(dbId);
		dbConfigEntity = dbConfigService.getEntityById(dbConfigEntity);		
		DbConfig dbContig = new DbConfig();
		try {
			MyBeanUtils.copyBean2Bean( dbContig,dbConfigEntity);
		} catch (Exception e) {
		}

		List<String> list = new ArrayList<String>();
		try {
			list = new JeecgReadTable().readAllTableNames(dbContig);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		Collections.sort(list,new StringSort(dataGrid.getOrder()));
		
		List<CgFormHeadEntity> tables = cgFormHeadService.getList(null,null,null);
		if(tables != null && tables.size()>0){
			for(CgFormHeadEntity table : tables){
				String tablename = table.getSchema() == null ? table.getTableName() : (table.getSchema() + "." +table.getTableName());
				list.remove(  tablename );
			}
		}
		
		

		for (String item : list) {
			
			if(StringUtil.isNotEmpty(tableName) && item.indexOf(tableName)!=-1){
				continue;
			}
			
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("id", item);
			map1.put("name", item);
			indexs.add(map1);
		}
		
		
		map.put("rows", indexs);
		map.put("total",indexs.size());

		
		return map;
		
	}

	

	@RequestMapping(params = "toEdit")
	public String toEdit(CgFormHeadEntity dc,HttpServletRequest request,HttpServletResponse response) {
		
		List<DbConfigEntity> dbConfigs = dbConfigService.getList(null,null,null);
		request.setAttribute("dbConfigs", dbConfigs);
		
		
		request.setAttribute("javaTypes", EnumJavaType.getAll());
		request.setAttribute("dbTypes", EnumDbType.getAll());
		request.setAttribute("easyuis", EnumEasyUIEditor.getAll());
		
		if(StringUtils.isNotEmpty(dc.getId())){
			CgFormHeadEntity cgFormHeadEntity = cgFormHeadService.getEntityById(dc);
			request.setAttribute("entity", cgFormHeadEntity);
			
			CgFormFieldEntity cgFormFieldEntity = new CgFormFieldEntity();
			CgFormHeadEntity table = new CgFormHeadEntity();
			table.setId(dc.getId());
			cgFormFieldEntity.setTable(table);
			List<CgFormFieldEntity> columns = cgFormFieldService.getList(cgFormFieldEntity, null, null);
			for(CgFormFieldEntity column : columns){
				if(StringUtils.isEmpty(column.getJavaType())){
					EnumJavaType enumJavaType = EnumDbType.getJavaType(column.getType(), String.valueOf(column.getLength()), String.valueOf(column.getPointLength()));
					if(enumJavaType!=null) column.setJavaType(enumJavaType.getFullname());
				}
			}
			request.setAttribute("columns", columns);

			
			CgFormIndexEntity cgFormIndexEntity = new CgFormIndexEntity();
			cgFormIndexEntity.setTable(table);
			List<CgFormIndexEntity> indexs = cgFormIndexService.getList(cgFormIndexEntity, null, null);
			if(indexs == null || indexs.isEmpty()){
				if(indexs == null) indexs = new ArrayList<CgFormIndexEntity>();
				indexs.add(new CgFormIndexEntity());
				indexs.add(new CgFormIndexEntity());
				indexs.add(new CgFormIndexEntity());
			}else {
				indexs.add(new CgFormIndexEntity());
				indexs.add(new CgFormIndexEntity());
				indexs.add(new CgFormIndexEntity());
			}
			request.setAttribute("indexs", indexs);
		}
		return "cg/cgFormHeadEdit";
	}
	
	

	@RequestMapping(params = "doAdds")
	@ResponseBody
	public AjaxJson doAdds(String id, String dbId ,HttpServletRequest request, HttpServletResponse response) {
		String[] ids = id.split(";");
		AjaxJson ajaxJson = null;
		for(int i=0;i<ids.length;i++){
			ajaxJson = doAdd(ids[i],dbId,request,response);
			if(!ajaxJson.isSuccess()) break;
		}
		return ajaxJson;
	}
	

	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(String id, String dbId ,HttpServletRequest request, HttpServletResponse response) {
		
		TsUser user = ResourceUtil.getSessionUser();
		try{

			CgFormHeadEntity dc = new CgFormHeadEntity(); 
			String[] st = id.split("\\.");
			if(st.length == 1) dc.setTableName(st[0]);
			else if(st.length == 2){
				 dc.setSchema(st[0]);
				 dc.setTableName(st[1]);
			}
			
			DbConfigEntity dbConfigEntity = new DbConfigEntity();
			dbConfigEntity.setId(dbId);
			DbConfig dbConfig = this.getDbContig(dbConfigEntity);
			
			
			String[] ui_filter_fields = CodeResourceUtil.JEECG_GENERATE_UI_FILTER_FIELDS.toLowerCase().split(",");

			
			List<Columnt> list = new JeecgReadTable().readOriginalTableColumn(dbConfig,id);
			
			CgFormHeadEntity cgFormHead = new CgFormHeadEntity();
			cgFormHead.setDbConfigEntity(dbConfigEntity);
			cgFormHead.setJformType(1);
			cgFormHead.setIsCheckbox("Y");
			cgFormHead.setIsDbSynch("Y");
			cgFormHead.setIsTree("N");
			cgFormHead.setQuerymode("group");
			cgFormHead.setSchema(dc.getSchema());
			cgFormHead.setTableName(dc.getTableName().toLowerCase());
			cgFormHead.setIsPagination("Y");
			cgFormHead.setContent(dc.getTableName());
			cgFormHead.setJformVersion("1");
			
			cgFormHead.setCreateBy(user.getId());
			cgFormHead.setCreateName(user.getName());
			cgFormHead.setCreateDate(new Date());
			
			if(StringUtils.isEmpty(cgFormHead.getJformCategory())){
				cgFormHead.setJformCategory("bdfl_ptbd");
			}
			
			List<CgFormFieldEntity> columnsList = new ArrayList<CgFormFieldEntity>();
			for (int k = 0; k < list.size(); k++) {
				Columnt columnt = list.get(k);
				
				
				CgFormFieldEntity cgFormField = new CgFormFieldEntity();
				
				cgFormField.setDbName(columnt.getFieldName().toLowerCase());

				cgFormField.setIsNull(columnt.getNullable());

				cgFormField.setType(columnt.getDbType()); 
				
				
				if (StringUtil.isNotEmpty(columnt.getFiledComment()))
					cgFormField.setContent(columnt.getFiledComment());
				else
					cgFormField.setContent(columnt.getFieldName());
				
				String length = columnt.getCharmaxLength();
				if(StringUtils.isEmpty(length) || length.equals("0")){		
					length = columnt.getPrecision();
					if(!StringUtils.isEmpty(columnt.getScale()) && !columnt.getScale().equals("0")){
					cgFormField.setPointLength(Integer.parseInt(columnt.getScale()));
					}
				}
				
				if(!StringUtils.isEmpty(length) && !length.equals("0"))
					cgFormField.setLength(Integer.parseInt(length));
				else 
					cgFormField.setLength(10);
				

				cgFormField.setOrderNum(k + 2);
				cgFormField.setQueryMode("group");
				cgFormField.setFieldLength(120);
				
				cgFormField.setIsKey("N");
				cgFormField.setIsShow("Y");
				cgFormField.setIsShowList("Y");
				
				
				if("id".equalsIgnoreCase(cgFormField.getDbName())){
					String[] pkTypeArr = {"int","integer","long"};
					String idFiledType = cgFormField.getType().toLowerCase();
					if(Arrays.asList(pkTypeArr).contains(idFiledType)){
						//如果主键是数字类型,则设置为自增长
						cgFormHead.setJformPkType("NATIVE");
					}else{
						//否则设置为UUID
						cgFormHead.setJformPkType("UUID");
					}
					cgFormField.setIsKey("Y");
					cgFormField.setIsShow("N");
					cgFormField.setIsShowList("N");
				}
				
				
				columnsList.add(cgFormField);
			}
			
			//cgFormHead.setColumns(columnsList);
			
			cgFormHeadService.saveEntity(cgFormHead);
			
			for(CgFormFieldEntity cgFormField : columnsList ){
				PublicUtil.judgeCheckboxValue(cgFormField, "isNull,isShow,isShowList,isQuery,isKey,queryable");
				cgFormField.setTable(cgFormHead);
				
				CgFormFieldUtil.init(cgFormField);				
				CgFormFieldUtil.initShow(cgFormField,ui_filter_fields);
				CgFormFieldUtil.initShowType(cgFormField);
				CgFormFieldUtil.initOther(cgFormField);
				
				cgFormFieldService.saveEntity(cgFormField);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	

	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CgFormHeadEntity dc,HttpServletRequest request, HttpServletResponse response) {

		TsUser user = ResourceUtil.getSessionUser();
		
		try{
				CgFormHeadEntity entity = cgFormHeadService.getEntityById(dc);
				entity.setDbConfigEntity(dc.getDbConfigEntity());
				entity.setIsCheckbox(dc.getIsCheckbox());
				entity.setIsPagination(dc.getIsPagination());
				entity.setQuerymode(dc.getQuerymode());
				entity.setSchema(dc.getSchema());
				entity.setTableName(dc.getTableName());
				entity.setContent(dc.getContent());
				entity.setPackageName(dc.getPackageName());
				

				entity.setUpdateBy(user.getId());
				entity.setUpdateName(user.getName());
				entity.setUpdateDate(new Date());
				
				cgFormHeadService.updateEntity(entity);
				
				Set<String> indexFieldSet = new HashSet<String>();
				
				if(dc.getIndexes() != null && !dc.getIndexes().isEmpty()){					
					for(int i=0; i < dc.getIndexes().size() ; i++){	
						CgFormIndexEntity cgFormIndexEntity = dc.getIndexes().get(i);		
						if(StringUtil.isEmpty(cgFormIndexEntity.getIndexField()) || StringUtil.isEmpty(cgFormIndexEntity.getIndexName())){
							if(StringUtils.isNotEmpty(cgFormIndexEntity.getId())){
								cgFormIndexService.deleteEntity(cgFormIndexEntity);
							}
							continue;
						}
						
						
						
						if(StringUtils.isEmpty(cgFormIndexEntity.getId())){
							cgFormIndexEntity.setTable(entity);
							cgFormIndexService.saveEntity(cgFormIndexEntity);
						}else {
							CgFormIndexEntity cgFormIndex = cgFormIndexService.getEntityById(cgFormIndexEntity);
							MyBeanUtils.copyBeanNotNull2Bean(cgFormIndexEntity, cgFormIndex);
							cgFormIndexService.updateEntity(cgFormIndex);
						}
						
						String indexFields[] = cgFormIndexEntity.getIndexField().split(",");
						for(String indexField : indexFields){
							if(StringUtils.isNotEmpty(indexField)){
								indexFieldSet.add(indexField.toLowerCase());
							}
						}
						
						
					}								
				}
				
				

				if(entity.getColumns() != null && !entity.getColumns().isEmpty()){
					for(int i=0; i < entity.getColumns().size() ; i++){
						CgFormFieldEntity cgFormFieldEntity = entity.getColumns().get(i);
						MyBeanUtils.copyBeanNotNull2Bean(dc.getColumns().get(i), cgFormFieldEntity);						
						CgFormFieldUtil.init(cgFormFieldEntity,indexFieldSet);	
						//CgFormUtil.initShowType(cgFormFieldEntity);
						CgFormFieldUtil.initOther(cgFormFieldEntity);
						if(
							(
								cgFormFieldEntity.getDictionarys() != null && 
								cgFormFieldEntity.getDictionarys().indexOf(",")>0 && 
								cgFormFieldEntity.getJavaFullType().startsWith("java")
							) ||
							(
								!cgFormFieldEntity.getJavaFullType().startsWith("java") &&
								(StringUtils.isEmpty(cgFormFieldEntity.getDictionarys()) || cgFormFieldEntity.getDictionarys().indexOf(",") == -1 )
								
							)
						){
							return new AjaxJson(false, cgFormFieldEntity.getContent()+"的  JAVA类型或者字典信息  设置错误!");
						}
					}
					//批量保存列设置
					cgFormFieldService.updateEntity(entity.getColumns());
				}
			
		}catch(Exception e){
			e.printStackTrace();
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	

	@RequestMapping(params = "doDelete")
	@ResponseBody
	public AjaxJson doDelete(CgFormHeadEntity dc,HttpServletRequest request, HttpServletResponse response) {
		try{
			
			cgFormHeadService.deleteEntity(dc);
		}catch(Exception e){
			return AjaxJson.errorAjax;
		}
		
		return AjaxJson.successAjax;
	}
	
	
	
	/**
	 * 生成代码
	 * @param dc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "gc")
	@ResponseBody
	public AjaxJson gc(CgFormHeadEntity cgFormHead,GenerateEntity generateEntity ,HttpServletRequest request, HttpServletResponse response) {
				
		CreateFileProperty createFileProperty = new CreateFileProperty ();	
		

		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			cgFormHead = cgFormHeadService.getEntityById(cgFormHead);
			CgFormUtil.getCgformConfig(cgFormHead, generateEntity);	
		}else{
			return new AjaxJson(false,"表单不存在");
		}
		
		if(StringUtils.isEmpty(cgFormHead.getPackageName())){
			return new AjaxJson(false,"请先填写完整的包名");
		}
		
		AjaxJson j =  new AjaxJson();		
		try {			
			CgformCodeGenerate generate = new CgformCodeGenerate(createFileProperty,generateEntity);			
			generate.generateToFile();	
		} catch (Exception e1) {
			e1.printStackTrace();
			j.setMsg(e1.getMessage());
			return j;
		}
		return AjaxJson.successAjax;
		
	}
	

	@RequestMapping(params = "download")
	public void download(CgFormHeadEntity cgFormHead,GenerateEntity generateEntity ,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		TsUser user = ResourceUtil.getSessionUser();

		String filePath = "code"+user.getId()+".zip";
		String zipPath = CodeResourceUtil.getProject_path()+"/"+filePath;
		String sourcePath = CodeResourceUtil.getProject_path()+"/"+user.getId();
		
		File f = new File(zipPath);
		if(f.exists()) f.delete();
		
		ZipKit.doZip (sourcePath, CodeResourceUtil.getProject_path(), filePath);
		
		FileKit.deleteFolder(sourcePath);
		
		String storeName = zipPath;  
        String realName = "code.zip";  
        String contentType = "application/octet-stream";  
  
        FileUtil.download(request, response, storeName, contentType,  realName);  
  
		
		
	}
	
	
	private DbConfig getDbContig (DbConfigEntity dbConfigEntity){
		DbConfig dbContig = new DbConfig();
		try {
			if(StringUtils.isEmpty(dbConfigEntity.getDbUrl())){
				dbConfigEntity = dbConfigService.getEntityById(dbConfigEntity);
			}
			MyBeanUtils.copyBean2Bean( dbContig,dbConfigEntity);
		} catch (Exception e) {
			return null;
		}
		return dbContig;
	}
	
	
	
	
	
}
