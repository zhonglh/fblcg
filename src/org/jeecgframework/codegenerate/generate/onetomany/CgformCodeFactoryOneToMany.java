//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate.onetomany;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.generate.BaseCodeFactory;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

public class CgformCodeFactoryOneToMany extends BaseCodeFactory {
    private ICallBack callBack;
    private String projectPath;

    public CgformCodeFactoryOneToMany() {
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

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("./").getPath());
    }

    public String getTemplatePath() {
        String path = this.getClassPath() + CodeResourceUtil.TEMPLATEPATH;
        return path;
    }

    public String getCodePath(String type, String entityPackage, String entityName) {
        String path = this.getProjectPath();
        String codePath = "";
        if(this.packageStyle != null && CodeResourceUtil.PACKAGE_SERVICE_STYLE.equals(this.packageStyle)) {
            codePath = this.getCodePathServiceStyle(path, type, entityPackage, entityName);
        } else {
            codePath = this.getCodePathProjectStyle(path, type, entityPackage, entityName);
        }

        return codePath;
    }

    public void invoke(String templateFileName, String type) throws TemplateException, IOException {
        new HashMap();
        Map data = this.callBack.execute();
        this.generateFile(templateFileName, type, data);
    }

    public void invokeUserDefined(String templateFileName, String type) throws TemplateException, IOException {
        new HashMap();
        Map data = this.callBack.execute();
        this.generateFileUserDefined(templateFileName, type, data);
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
