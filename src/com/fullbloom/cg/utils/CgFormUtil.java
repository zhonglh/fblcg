package com.fullbloom.cg.utils;

import org.jeecgframework.codegenerate.generate.GenerateEntity;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;

import com.fullbloom.cg.entity.CgFormHeadEntity;

public class CgFormUtil {
	
	

	public static final String ENTER_MARK = "\r\n";
	
	public static void getCgformConfig(CgFormHeadEntity cgFormHead, GenerateEntity generateEntity)  {
		
		
		generateEntity.setCgFormHead(cgFormHead);
		generateEntity.setEntityName(CodeStringUtils.formatFieldCapital(cgFormHead.getTableName()));
		
		generateEntity.setEntityPackage(cgFormHead.getPackageName());
		
		generateEntity.setFieldRowNum(2);
		
		generateEntity.setProjectPath(CodeResourceUtil.getProject_path());
		
		generateEntity.setPackageStyle("service");
		
		generateEntity.setTableName(cgFormHead.getTableName());
		
		generateEntity.setSchemaName(cgFormHead.getSchema());
		
		generateEntity.setFtlDescription(cgFormHead.getContent());
		
		
	}
	
	public static String getProdByEntity(String entityPackage){
		if(entityPackage == null || entityPackage.isEmpty()) return "";
		String prodName = "";
		String[] ss = entityPackage.split("\\.");
        if(ss.length > 2) prodName = ss[2];
        else prodName = ss[ss.length-1];
        return prodName;
	}

}
