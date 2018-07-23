//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.generate;

import freemarker.template.Configuration;

import java.io.IOException;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;

import com.fullbloom.cg.utils.CgFormUtil;




public class BaseCodeFactory {
	
	
	
	
	
    protected String packageStyle;

    public BaseCodeFactory() {
    }

    public Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), CodeResourceUtil.FREEMARKER_CLASSPATH);
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    public Configuration getConfigurationUserDefined() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), CodeResourceUtil.FREEMARKER_CLASSPATH_USERDEFINED);
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    public String getCodePathServiceStyle(String path, String type, String entityPackage, String entityName) {
        StringBuilder str = new StringBuilder();
        if(!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is null");
        } else {
            String codeType = null;
            if(type.endsWith("Validate")){
            	codeType = type;
            }else {
            	codeType = ((BaseCodeFactory.CodeType)Enum.valueOf(BaseCodeFactory.CodeType.class, type)).getValue();
            }
            
            str.append(path);
            if(!"jsp".equals(type) && !"jspList".equals(type) && !"js".equals(type) && !"jsList".equals(type) && !"jsp_add".equals(type) && !"jsp_update".equals(type)) {
                str.append(CodeResourceUtil.CODEPATH);
            } else {
                str.append(CodeResourceUtil.JSPPATH);
            }
            
            
            if("CheckUsed".equalsIgnoreCase(codeType)) {            	
            	String prodname = CgFormUtil.getProdByEntity(entityPackage);
            	String packagename = "com.fullbloom.interfaces.checkused"+(StringUtils.isNotEmpty(prodname)?("."+prodname):"");
            	str.append(StringUtils.lowerCase((packagename.replaceAll("\\.", "/"))));
                str.append("/");  
                str.append("I"+entityName+codeType+".java");  
                return str.toString();
            }else if(codeType.endsWith("Validate")){
                str.append(StringUtils.lowerCase((entityPackage.replaceAll("\\.", "/"))));
                str.append("/");
                str.append("service/validate/"+codeType+".java");
                return str.toString();
            }

            str.append(StringUtils.lowerCase((entityPackage.replaceAll("\\.", "/"))));
            str.append("/");
            if("Action".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("action/"));
            } else if("ServiceImpl".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service/impl/"));
            } else if("Service".equalsIgnoreCase(codeType)) {
                str.append("service/I");
            }else if("ExcelController".equalsIgnoreCase(codeType)) {
                str.append("controller/");
            }else if("ExcelService".equalsIgnoreCase(codeType)) {
                str.append("service/I");
            } else if("ExcelServiceImpl".equalsIgnoreCase(codeType)) {
                str.append("service/impl/");
            } else if("Excel".equalsIgnoreCase(codeType)) {
                str.append("entity/");
            } else if(!"List".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase(codeType)+"/");
            }

            
            String jsName;
            if(!"jsp".equals(type) && !"jspList".equals(type)) {
                if(!"jsp_add".equals(type) && !"jspList_add".equals(type)) {
                    if(!"jsp_update".equals(type) && !"jspList_update".equals(type)) {
                        if(!"js".equals(type) && !"jsList".equals(type)) {
                            str.append(StringUtils.capitalize(entityName));
                            str.append(codeType);
                            str.append(".java");
                        } else {
                            jsName = StringUtils.capitalize(entityName);
                            str.append(CodeStringUtils.getInitialSmall(jsName));
                            str.append(codeType);
                            str.append(".js");
                        }
                    } else {
                        jsName = StringUtils.capitalize(entityName);
                        str.append(CodeStringUtils.getInitialSmall(jsName));
                        str.append(codeType);
                        str.append("-update.jsp");
                    }
                } else {
                    jsName = StringUtils.capitalize(entityName);
                    str.append(CodeStringUtils.getInitialSmall(jsName));
                    str.append(codeType);
                    str.append("-add.jsp");
                }
            } else {
                jsName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jsName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public String getCodePathProjectStyle(String path, String type, String entityPackage, String entityName) {
        StringBuilder str = new StringBuilder();
        if(!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is null");
        } else {
            String codeType = ((BaseCodeFactory.CodeType)Enum.valueOf(BaseCodeFactory.CodeType.class, type)).getValue();
            str.append(path);
            if(!"jsp".equals(type) && !"jspList".equals(type) && !"js".equals(type) && !"jsList".equals(type) && !"jsp_add".equals(type) && !"jsp_update".equals(type)) {
                str.append(CodeResourceUtil.CODEPATH);
            } else {
                str.append(CodeResourceUtil.JSPPATH);
            }
            
            
            if("CheckUsed".equalsIgnoreCase(codeType)) {            	
            	String prodname = CgFormUtil.getProdByEntity(entityPackage);
            	String packagename = "com.fullbloom.interfaces.checkused"+(StringUtils.isNotEmpty(prodname)?("."+prodname):"");
            	str.append(StringUtils.lowerCase((packagename.replaceAll("\\.", "/"))));
                str.append("/");  
                str.append("I"+entityName+codeType+".java");  
                return str.toString();
            }


            if("Action".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("action/"));
            } else if("ServiceImpl".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service/impl/"));
            } else if("Service".equalsIgnoreCase(codeType)) {
                str.append("service/");
            }else if("ExcelController".equalsIgnoreCase(codeType)) {
                str.append("controller/");
            }else if("ExcelService".equalsIgnoreCase(codeType)) {
                str.append("service/");
            } else if("ExcelServiceImpl".equalsIgnoreCase(codeType)) {
                str.append("service/impl/");
            } else if("Excel".equalsIgnoreCase(codeType)) {
                str.append("entity/");
            } else if(!"List".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase(codeType)+"/");
            }


            str.append(StringUtils.lowerCase((entityPackage.replaceAll("\\.", "/"))));
            str.append("/");
            
            String jsName;
            if(!"jsp".equals(type) && !"jspList".equals(type)) {
                if(!"jsp_add".equals(type) && !"jspList_add".equals(type)) {
                    if(!"jsp_update".equals(type) && !"jspList_update".equals(type)) {
                        if(!"js".equals(type) && !"jsList".equals(type)) {
                        	
                        	if(str.indexOf("/service/")!=-1) str.append("I");
                        	
                            str.append(StringUtils.capitalize(entityName));
                            str.append(codeType);
                            str.append(".java");
                        } else {
                            jsName = StringUtils.capitalize(entityName);
                            str.append(CodeStringUtils.getInitialSmall(jsName));
                            str.append(codeType);
                            str.append(".js");
                        }
                    } else {
                        jsName = StringUtils.capitalize(entityName);
                        str.append(CodeStringUtils.getInitialSmall(jsName));
                        str.append(codeType);
                        str.append("-update.jsp");
                    }
                } else {
                    jsName = StringUtils.capitalize(entityName);
                    str.append(CodeStringUtils.getInitialSmall(jsName));
                    str.append(codeType);
                    str.append("-add.jsp");
                }
            } else {
                jsName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jsName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public String getPackageStyle() {
        return this.packageStyle;
    }

    public void setPackageStyle(String packageStyle) {
        this.packageStyle = packageStyle;
    }

    public static enum CodeType {
        serviceImpl("ServiceImpl"),
        dao("Dao"),
        service("Service"),
        controller("Controller"),
        page("Page"),
        entity("Entity"),
        jsp(""),
        jsp_add(""),
        jsp_update(""),
        js(""),
        jsList("List"),
        jspList("List"),
        jspSubList("SubList"),

        checkUsed("CheckUsed"),
        
        excelService("ExcelService"),
        excelServiceImpl("ExcelServiceImpl"),
        excelController("ExcelController"),
        excel("Excel"),
        
        ;

        private String type;

        private CodeType(String type) {
            this.type = type;
        }

        public String getValue() {
            return this.type;
        }
    }
}
