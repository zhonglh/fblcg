//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

import com.fullbloom.cg.entity.CgFormFieldEntity;
import com.fullbloom.cg.entity.CgFormIndexEntity;
import com.fullbloom.framework.util.ResourceUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CgformCodeFactory extends BaseCodeFactory {
    private ICallBack callBack;
    private String projectPath;

    public CgformCodeFactory() {
    }

    public void generateFile(String templateFileName, String type, Map data) throws TemplateException, IOException {
        try {
            String e = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = this.getCodePath(type, e, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = this.getConfiguration().getTemplate(templateFileName);
            FileUtils.forceMkdir(new File(fileDir + "/"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        } catch (TemplateException var10) {
            var10.printStackTrace();
            throw var10;
        } catch (IOException var11) {
            var11.printStackTrace();
            throw var11;
        }
    }

    public void generateFileUserDefined(String templateFileName, String type, Map data) throws TemplateException, IOException {
        try {
            String e = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = this.getCodePath(type, e, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = this.getConfigurationUserDefined().getTemplate(templateFileName);
            FileUtils.forceMkdir(new File(fileDir + "/"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        } catch (TemplateException var10) {
            var10.printStackTrace();
            throw var10;
        } catch (IOException var11) {
            var11.printStackTrace();
            throw var11;
        }
    }

    public String getProjectPath() {
        return this.projectPath;
    }

    public String getClassPath() {
        String path = this.getClass().getResource("/").getPath();
        return path;
    }

    public String getTemplatePath() {
        String path = this.getClassPath() + CodeResourceUtil.TEMPLATEPATH;
        return path;
    }

    public String getCodePath(String type, String entityPackage, String entityName) {
        String path = this.getProjectPath() + ResourceUtil.getSessionUser().getId() + "/";
        String codePath = "";
        if(this.packageStyle != null && CodeResourceUtil.PACKAGE_SERVICE_STYLE.equals(this.packageStyle)) {
            codePath = this.getCodePathServiceStyle(path, type, entityPackage, entityName);
        } else {
            codePath = this.getCodePathProjectStyle(path, type, entityPackage, entityName);
        }

        return codePath;
    }

    public void invoke(String templateFileName, String type) throws TemplateException, IOException {
        
        Map data = this.callBack.execute();
        processVakidate(type,data);
        this.generateFile(templateFileName, type, data);
    }

    public void invokeUserDefined(String templateFileName, String type) throws TemplateException, IOException {
        
        Map data = this.callBack.execute();
        processVakidate(type,data);
        this.generateFileUserDefined(templateFileName, type, data);
    }
    
    
    

    public void invokeNotFlt(String html, String type) throws TemplateException, IOException {
        
        Map data = this.callBack.execute();
        processVakidate(type,data);
        try {
            String e = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = this.getCodePath(type, e, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            FileUtils.forceMkdir(new File(fileDir + "/"));
            Configuration cfg = new Configuration();
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate(entityName, html);
            cfg.setTemplateLoader(loader);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(entityName);
            FileUtils.forceMkdir(new File(fileDir + "/"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        } catch (IOException var12) {
            var12.printStackTrace();
            throw var12;
        }
    }
    
    /**
     * 处理校验逻辑， 将要校验对应的列放入
     * @param type
     * @param data
     */
    private void processVakidate(String type, Map data){
        if(type.endsWith("Validate")){
        	if(type.startsWith("Unique")){
        		//process unique index
        		data.put("valiType", type);
        		String number = type.replaceFirst("Validate", "").replaceFirst("Unique", "");
        		int num = Integer.parseInt(number);
        		CgFormIndexEntity cgFormIndexEntity =  ((List<CgFormIndexEntity>)data.get("indexs")).get(num-1);
        		data.put("theIndex", cgFormIndexEntity );
        		
        		String indexFields = "";
        		for(CgFormFieldEntity cgFormFieldEntity : cgFormIndexEntity.getFields()){
        			if(!cgFormFieldEntity.getJavaName().equals("deleteFlag"))
        			indexFields += cgFormFieldEntity.getContent() + "+";
        		}
        		indexFields = indexFields.substring(0,indexFields.length()-1);
        		data.put("indexFields", indexFields );
        	}else {
        		//process column
	    		String fieldName =  StringUtils.substringBeforeLast(type, "Validate");
	        	List<CgFormFieldEntity> columns = (List<CgFormFieldEntity>)data.get("columns");
	        	for(CgFormFieldEntity  column : columns){
	        		String fn = column.getJavaName();
	        		if(!column.getJavaFullType().startsWith("java")) fn = fn + "Name";
	        		if(fieldName.equalsIgnoreCase(fn)){
	        			data.put("theColumn", column);
	        			break;
	        		}
	        	}
        	}
        }
    }

    public ICallBack getCallBack() {
        return this.callBack;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
