//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.codegenerate.enums.EnumJavaType;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;
import org.jeecgframework.codegenerate.util.CodeDateUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;
import org.jeecgframework.codegenerate.util.NonceUtils;
import org.jeecgframework.codegenerate.util.def.FtlDef;

import com.fullbloom.cg.entity.CgFormFieldEntity;
import com.fullbloom.cg.entity.CgFormIndexEntity;
import com.fullbloom.cg.utils.CgFormFieldUtil;
import com.fullbloom.cg.utils.CgFormUtil;

import freemarker.template.TemplateException;

public class CgformCodeGenerate implements ICallBack {
    private static final Log log = LogFactory.getLog(CgformCodeGenerate.class);
    private String entityPackage = "test";
    private String entityName = "Person";
    private String schemaName = "";
    private String tableName = "person";
    private String ftlDescription = "";
    private String primaryKeyPolicy = "uuid";
    private String sequenceCode = "";
    private String[] foreignKeys;
    public static int FIELD_ROW_NUM = 1;
    private String cgformJspHtml;
    private SubTableEntity sub;
    private GenerateEntity subG;
    private CreateFileProperty subFileProperty;
    private String policy;
    private String[] array;
    private GenerateEntity cgformConfig;
    private static CreateFileProperty createFileProperty = new CreateFileProperty();

    public CgformCodeGenerate() {
    }

    public CgformCodeGenerate(CreateFileProperty createFileProperty2, GenerateEntity generateEntity) {
        this.entityName = generateEntity.getEntityName();
        this.schemaName = generateEntity.getSchemaName();
        this.entityPackage = generateEntity.getEntityPackage();
        this.tableName = generateEntity.getTableName();
        this.ftlDescription = generateEntity.getFtlDescription();
        FIELD_ROW_NUM = 1;
        createFileProperty = createFileProperty2;
        createFileProperty.setJspMode(createFileProperty2.getJspMode());
        this.primaryKeyPolicy = generateEntity.getPrimaryKeyPolicy();
        this.sequenceCode = "";
        this.cgformConfig = generateEntity;
    }

    public CgformCodeGenerate(SubTableEntity sub, GenerateEntity subG, CreateFileProperty subFileProperty, String policy, String[] array) {
        this.entityName = subG.getEntityName();
        this.schemaName = subG.getSchemaName();
        this.entityPackage = subG.getEntityPackage();
        this.tableName = subG.getTableName();
        this.ftlDescription = subG.getFtlDescription();
        createFileProperty = subFileProperty;
        FIELD_ROW_NUM = 1;
        this.primaryKeyPolicy = policy;
        this.sequenceCode = "";
        this.cgformConfig = subG;
        this.foreignKeys = array;
        this.sub = sub;
        this.subG = subG;
        this.subFileProperty = subFileProperty;
        this.policy = policy;
    }

    public Map<String, Object> execute() {
        HashMap data = new HashMap();
        HashMap fieldMeta = new HashMap();
        data.put("bussiPackage", CodeResourceUtil.bussiPackage);
        data.put("entityPackage", this.entityPackage);
        data.put("entityName", this.entityName);
        data.put("schemaName", this.schemaName);
        data.put("tableName", this.tableName);
        data.put("ftl_description", this.ftlDescription);
        data.put(FtlDef.JEECG_TABLE_ID, CodeResourceUtil.JEECG_GENERATE_TABLE_ID);
        data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, this.primaryKeyPolicy);
        data.put(FtlDef.JEECG_SEQUENCE_CODE, this.sequenceCode);
        data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
        data.put("foreignKeys", this.foreignKeys);
        data.put(FtlDef.FIELD_REQUIRED_NAME, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM)?Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM):-1));
        data.put(FtlDef.SEARCH_FIELD_NUM, Integer.valueOf(StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM)?Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM):-1));
        data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));
        
        String prodName = CgFormUtil.getProdByEntity(this.entityPackage);

        data.put("prodName", prodName);

        try {
        	
        	List<CgFormIndexEntity> indexs = this.cgformConfig.getCgFormHead().getIndexes();
        	if(indexs == null) indexs = new ArrayList<CgFormIndexEntity>();        	
            List<CgFormFieldEntity> columns =  this.cgformConfig.getCgFormHead().getColumns();
        	if(columns == null) columns = new ArrayList<CgFormFieldEntity>();        	
            
            
            Iterator pageColumns = columns.iterator();

            while(pageColumns.hasNext()) {
                CgFormFieldEntity i$ = (CgFormFieldEntity)pageColumns.next();
               

                String fieldNameV = i$.getJavaName();
                fieldMeta.put(fieldNameV, fieldNameV.toUpperCase());
            }

            ArrayList pageColumns1 = new ArrayList();
            Iterator i$1 = columns.iterator();
            
            Set<String> importClass = new HashSet<String>();

            while(i$1.hasNext()) {
                CgFormFieldEntity cf1 = (CgFormFieldEntity)i$1.next();
                if(StringUtils.isNotEmpty(cf1.getIsShow()) && "Y".equalsIgnoreCase(cf1.getIsShow())) {
                    pageColumns1.add(cf1);
                }
                if(EnumJavaType.getEnumJavaType(cf1.getJavaFullType()) == null){
                	importClass.add( cf1.getJavaFullType() );
                }
            }
            
            
            //保存的索引(unique类型)中用到的所有列
            List<String> indexAllFields = new ArrayList<String>();
            //触发索引的列名
            List<String> indexOnField = new ArrayList<String>();
            Map<String, String> fieldName_indexName = new HashMap<String, String>();
            
            
            for(CgFormIndexEntity cgFormIndexEntity :indexs){
            	if(StringUtils.isEmpty(cgFormIndexEntity.getIndexField())) continue;
            	String[] cols = cgFormIndexEntity.getIndexField().split(",");
            	cgFormIndexEntity.setFields(new ArrayList<CgFormFieldEntity>());
            	for(String col : cols){
            		
            		if(StringUtils.isEmpty(col) || "delete_flag".equalsIgnoreCase(col)  || "id".equalsIgnoreCase(col)) continue;
            		indexAllFields.add(col.toLowerCase());
            		
            		for(CgFormFieldEntity cgFormFieldEntity : columns){
            			if(cgFormFieldEntity.getDbName().equalsIgnoreCase(col)){
            				cgFormIndexEntity.getFields().add(cgFormFieldEntity);
            				break;
            			}
            		}
            	}
            	
            	Collections.sort(cgFormIndexEntity.getFields(), new Comparator<CgFormFieldEntity>(){
					@Override
					public int compare(CgFormFieldEntity o1, CgFormFieldEntity o2) {
						int index1 = o1.getOrderNum();
						int index2 = o1.getOrderNum();
						
						if("N".equals(o1.getIsShow()))  index1 = 0;
						if("N".equals(o2.getIsShow()))  index2 = 0;						
						
						return index1 > index2 ? -1 : (index1 == index2 ? 0 : 1);
					}            		
            	});
            	
            	String indexFieldName = cgFormIndexEntity.getFields().get(0).getDbName().toLowerCase();
            	indexOnField.add(indexFieldName);
            	fieldName_indexName.put(indexFieldName, cgFormIndexEntity.getIndexName());
            	
            }
            
            data.put("imports",Arrays.asList( importClass.toArray() ));            
            data.put("cgformConfig", this.cgformConfig);
            data.put("indexs", indexs);

            data.put("indexAllFields", indexAllFields);
            data.put("indexOnField", indexOnField);
            data.put("fieldName_indexName", fieldName_indexName);
            
            
            
            data.put("fieldMeta", fieldMeta);
            data.put("columns", columns);
            data.put("pageColumns", pageColumns1);
            data.put("packageStyle", this.cgformConfig.getPackageStyle());
            
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        long serialVersionUID1 = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID1));
        return data;
    }

    public void generateToFile() throws TemplateException, IOException {
        log.info("----jeecg---Code----Generation----[单表模型:" + this.tableName + "]------- 生成中。。。");
        CgformCodeFactory codeFactory = new CgformCodeFactory();
        codeFactory.setProjectPath(this.cgformConfig.getProjectPath());
        codeFactory.setPackageStyle(this.cgformConfig.getPackageStyle());
        if(this.cgformConfig.getCgFormHead().getJformType().intValue() == 1) {
            codeFactory.setCallBack(new CgformCodeGenerate(createFileProperty, this.cgformConfig));
        } else {
            codeFactory.setCallBack(new CgformCodeGenerate(this.sub, this.subG, this.subFileProperty, "uuid", this.foreignKeys));
        }

        if(createFileProperty.isJspFlag()) {
                codeFactory.invoke("cgform_jspListTemplate.ftl", "jspList");
                /*codeFactory.invoke("cgform_jsListEnhanceTemplate.ftl", "jsList");
                codeFactory.invoke("cgform_jsEnhanceTemplate.ftl", "js");*/            
        }

        codeFactory.invoke("cgform_checkUsedTemplate.ftl", "checkUsed");
        
        codeFactory.invoke("cgform_excelTemplate.ftl", "excel");
        codeFactory.invoke("cgform_controllerExcelTemplate.ftl", "excelController");        
        codeFactory.invoke("cgform_serviceExcelTemplate.ftl", "excelService");
        codeFactory.invoke("cgform_serviceImplExcelTemplate.ftl", "excelServiceImpl");
        
        for(CgFormFieldEntity cgFormFieldEntity : this.cgformConfig.getCgFormHead().getColumns()){
        	if("Y".equals(cgFormFieldEntity.getIsShow()) && "Y".equals(cgFormFieldEntity.getIsShowList())){
        		String valiClz = StringUtils.capitalize(cgFormFieldEntity.getJavaName())+"Validate";
        		if(!cgFormFieldEntity.getJavaFullType().startsWith("java"))
        			valiClz = StringUtils.capitalize(cgFormFieldEntity.getJavaName())+"NameValidate";
        		
                codeFactory.invoke("cgform_validateTemplate.ftl", valiClz );
        	}
        }
        
        int index = 1;
        for(CgFormIndexEntity cgFormIndexEntity : this.cgformConfig.getCgFormHead().getIndexes()){
        	String valiClz = "Unique"+(index)+"Validate";
            codeFactory.invoke("cgform_uniqueValidateTemplate.ftl", valiClz );
            index++;
        }
        

        if(createFileProperty.isServiceImplFlag()) {
            codeFactory.invoke("cgform_serviceImplTemplate.ftl", "serviceImpl");
        }

        if(createFileProperty.isServiceIFlag()) {
            codeFactory.invoke("cgform_serviceITemplate.ftl", "service");
        }

        if(createFileProperty.isActionFlag()) {
            codeFactory.invoke("cgform_controllerTemplate.ftl", "controller");
        }

        if(createFileProperty.isEntityFlag()) {
            codeFactory.invoke("cgform_entityTemplate.ftl", "entity");
        }

        log.info("----jeecg----Code----Generation-----[单表模型：" + this.tableName + "]------ 生成完成。。。");
    }

    public void generateToFileUserDefined() throws TemplateException, IOException {
        log.info("----jeecg---Code----Generation----[单表模型:" + this.tableName + "]------- 生成中。。。");
        CgformCodeFactory codeFactory = new CgformCodeFactory();
        codeFactory.setProjectPath(this.cgformConfig.getProjectPath());
        codeFactory.setPackageStyle(this.cgformConfig.getPackageStyle());
        if(this.cgformConfig.getCgFormHead().getJformType().intValue() == 1) {
            codeFactory.setCallBack(new CgformCodeGenerate(createFileProperty, this.cgformConfig));
        } else {
            codeFactory.setCallBack(new CgformCodeGenerate(this.sub, this.subG, this.subFileProperty, "uuid", this.foreignKeys));
        }

        String path = createFileProperty.getJspMode().replace(".", "/");
        log.info("----jeecg------path----[" + path + "]-------");
        if(createFileProperty.isJspFlag()) {
            if(path.endsWith("onetomany")) {
                codeFactory.invokeUserDefined(path + "/cgform_jspSubTemplate.ftl", "jspList");
            } else {
                codeFactory.invokeUserDefined(path + "/cgform_jspTemplate_add.ftl", "jsp_add");
                codeFactory.invokeUserDefined(path + "/cgform_jspTemplate_update.ftl", "jsp_update");
                codeFactory.invokeUserDefined(path + "/cgform_jspListTemplate.ftl", "jspList");
                codeFactory.invokeUserDefined(path + "/cgform_jsListEnhanceTemplate.ftl", "jsList");
                codeFactory.invokeUserDefined(path + "/cgform_jsEnhanceTemplate.ftl", "js");
            }
        }

        if(createFileProperty.isServiceImplFlag()) {
            codeFactory.invokeUserDefined(path + "/cgform_serviceImplTemplate.ftl", "serviceImpl");
        }

        if(createFileProperty.isServiceIFlag()) {
            codeFactory.invokeUserDefined(path + "/cgform_serviceITemplate.ftl", "service");
        }

        if(createFileProperty.isActionFlag()) {
            codeFactory.invokeUserDefined(path + "/cgform_controllerTemplate.ftl", "controller");
        }

        if(createFileProperty.isEntityFlag()) {
            codeFactory.invokeUserDefined(path + "/cgform_entityTemplate.ftl", "entity");
        }

        log.info("----jeecg----Code----Generation-----[单表模型：" + this.tableName + "]------ 生成完成。。。");
    }

    public GenerateEntity getCgformConfig() {
        return this.cgformConfig;
    }

    public void setCgformConfig(GenerateEntity cgformConfig) {
        this.cgformConfig = cgformConfig;
    }

    public String getCgformJspHtml() {
        return this.cgformJspHtml;
    }

    public void setCgformJspHtml(String cgformJspHtml) {
        this.cgformJspHtml = cgformJspHtml;
    }

	public String getSchemaName() {
		return schemaName;
	}
    

    static {
        createFileProperty.setActionFlag(true);
        createFileProperty.setServiceIFlag(true);
        createFileProperty.setJspFlag(true);
        createFileProperty.setServiceImplFlag(true);
        createFileProperty.setJspMode("01");
        createFileProperty.setPageFlag(true);
        createFileProperty.setEntityFlag(true);
    }



}
