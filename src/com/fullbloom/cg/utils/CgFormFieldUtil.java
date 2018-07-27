package com.fullbloom.cg.utils;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.codegenerate.enums.EnumDbType;
import org.jeecgframework.codegenerate.enums.EnumJavaType;
import org.jeecgframework.codegenerate.generate.GenerateEntity;
import org.jeecgframework.codegenerate.util.CodeStringUtils;

import com.fullbloom.cg.entity.CgFormFieldEntity;
import com.fullbloom.cg.entity.CgFormHeadEntity;
import com.fullbloom.framework.core.enums.EnumEasyUIEditor;

public class CgFormFieldUtil {
	
	
	
	/**
	private void formatFieldClassType(Columnt columnt) {
		String fieldType = columnt.getFieldType();
		String scale = columnt.getScale();
		columnt.setClassType("inputxt");
		if ("N".equals(columnt.getNullable())) {
			columnt.setOptionType("*");
		}

		if (!"datetime".equals(fieldType) && !fieldType.contains("time")) {
			if ("date".equals(fieldType)) {
				columnt.setClassType("easyui-datebox");
			} else if (fieldType.contains("int")) {
				columnt.setOptionType("n");
			} else if ("number".equals(fieldType)) {
				if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
					columnt.setOptionType("d");
				}
			} else if (!"float".equals(fieldType) && !"double".equals(fieldType) && !"decimal".equals(fieldType)) {
				if ("numeric".equals(fieldType)) {
					columnt.setOptionType("d");
				}
			} else {
				columnt.setOptionType("d");
			}
		} else {
			columnt.setClassType("easyui-datetimebox");
		}

	}
	*/
	
	
	public static void initShow(CgFormFieldEntity cgFormFieldEntity,String[] filters){
		if("Y".equals(cgFormFieldEntity.getIsKey())){
			cgFormFieldEntity.setIsShow("N");
			cgFormFieldEntity.setIsShowList("N");
			cgFormFieldEntity.setIsQuery("N");
		}else {
			if(CodeStringUtils.isIn(cgFormFieldEntity.getDbName().toLowerCase(), filters)){
				cgFormFieldEntity.setIsShow("N");
				cgFormFieldEntity.setIsShowList("N");
				cgFormFieldEntity.setIsQuery("N");
			}else {
				cgFormFieldEntity.setIsShow("Y");
				cgFormFieldEntity.setIsShowList("Y");	
				cgFormFieldEntity.setIsQuery("Y");
			}
		}		
	}
	
	public static void initShowType(CgFormFieldEntity cgFormFieldEntity){
		
		if(StringUtils.isEmpty(cgFormFieldEntity.getJavaFullType())) {
			return ;
		}
		
		if("Y".equals(cgFormFieldEntity.getIsShow()) && "Y".equals(cgFormFieldEntity.getIsShowList())){
			
			if(EnumJavaType._String.getFullname().equals( cgFormFieldEntity.getJavaFullType())){
				cgFormFieldEntity.setShowType(EnumEasyUIEditor.validatebox.name());
			}else if(
					EnumJavaType._Integer.getFullname().equals( cgFormFieldEntity.getJavaFullType()) ||
					EnumJavaType._Long.getFullname().equals( cgFormFieldEntity.getJavaFullType()) ||
					EnumJavaType._Short.getFullname().equals( cgFormFieldEntity.getJavaFullType()) ||
					EnumJavaType._BigDecimal.getFullname().equals( cgFormFieldEntity.getJavaFullType()) ||
					EnumJavaType._Double.getFullname().equals( cgFormFieldEntity.getJavaFullType()) ||
					EnumJavaType._Float.getFullname().equals( cgFormFieldEntity.getJavaFullType()) 
			){
				cgFormFieldEntity.setShowType(EnumEasyUIEditor.numberbox.name());
			}else if(EnumJavaType._Date.getFullname().equals( cgFormFieldEntity.getJavaFullType())){
				cgFormFieldEntity.setShowType(EnumEasyUIEditor.datebox.name());
			}else {
				cgFormFieldEntity.setShowType(EnumEasyUIEditor.combogrid.name());
			}
		}		
	}
	
	
	
	public static void initOther(CgFormFieldEntity cgFormFieldEntity){
		
		if("Y".equals(cgFormFieldEntity.getIsKey())){
			cgFormFieldEntity.setIsShow("N");
			cgFormFieldEntity.setIsShowList("N");
			cgFormFieldEntity.setIsQuery("N");
		}
		
		cgFormFieldEntity.setFieldLength(
				(cgFormFieldEntity.getLength() == null || cgFormFieldEntity.getLength() == 0) ?100:cgFormFieldEntity.getLength()
		);
		
		if("N".equals(cgFormFieldEntity.getIsNull())){
			if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
				cgFormFieldEntity.setExtendJson(cgFormFieldEntity.getExtendJson().replaceAll("required:true,", "") );
				cgFormFieldEntity.setExtendJson(cgFormFieldEntity.getExtendJson().replaceAll("required:false,", "") );
				cgFormFieldEntity.setExtendJson(cgFormFieldEntity.getExtendJson() + "required:true,");
			}else  {
				cgFormFieldEntity.setExtendJson("required:true,");
			}
		}
	}
	

	public static void init(CgFormFieldEntity cgFormFieldEntity){
		init(cgFormFieldEntity , null);
	}
	public static void init(CgFormFieldEntity cgFormFieldEntity, Set<String> indexFieldSet){
		
		initJava(cgFormFieldEntity);
		
		if(
				cgFormFieldEntity.getIsQuery().equals("Y") ||
				(indexFieldSet != null && indexFieldSet.contains(cgFormFieldEntity.getDbName().toLowerCase()) )
		){
			cgFormFieldEntity.setQueryable("Y");
		}else {
			cgFormFieldEntity.setQueryable("N");
		}		
	}	

	public static void initJava(CgFormFieldEntity cgFormFieldEntity){		
		
		if(StringUtils.isEmpty(cgFormFieldEntity.getJavaFullType())){
			EnumJavaType enumJavaType = EnumDbType.getJavaType(cgFormFieldEntity.getType(), String.valueOf(cgFormFieldEntity.getLength()), String.valueOf(cgFormFieldEntity.getPointLength()));
			if(enumJavaType!=null) {				
				cgFormFieldEntity.setJavaFullType(enumJavaType.getFullname());
			}
		}

		System.out.println("cgFormFieldEntity.getJavaFullType():"+cgFormFieldEntity.getJavaName());
		System.out.println("cgFormFieldEntity.getJavaFullType():"+cgFormFieldEntity.getDbName());
		System.out.println("cgFormFieldEntity.getJavaFullType():"+cgFormFieldEntity.getJavaFullType());
		String[] clzs = cgFormFieldEntity.getJavaFullType().split("\\.");
		cgFormFieldEntity.setJavaType(clzs[(clzs.length-1)]);
		cgFormFieldEntity.setJavaName(CodeStringUtils.formatField(cgFormFieldEntity.getDbName().toLowerCase()));	
		if(!cgFormFieldEntity.getJavaFullType().startsWith("java.")){
			//自定义类型
			if(cgFormFieldEntity.getJavaName().endsWith("Id")){
				String javaName = StringUtils.substringBeforeLast(cgFormFieldEntity.getJavaName(), "Id");
				cgFormFieldEntity.setJavaName(javaName);
			}
		}
		
		initAnnotation(cgFormFieldEntity);			
	}
	
	
	public static void initAnnotation(CgFormFieldEntity cgFormFieldEntity){
		
		StringBuilder sb = null;
		
		if(EnumJavaType.getEnumJavaType(cgFormFieldEntity.getJavaFullType()) != null){
		
			sb = new StringBuilder( "@Column(name =\"" );		
			sb = sb.append(cgFormFieldEntity.getDbName().toUpperCase() ).append("\"");
			sb = sb.append(",nullable="+(cgFormFieldEntity.getIsNull().equals("Y")?"true":"false") );
			if(!"Date".equals(cgFormFieldEntity.getJavaType())){
				sb = sb.append(",length="+cgFormFieldEntity.getLength() );		
			}
			sb  = sb.append(" ) ");			
		}else {
			sb = new StringBuilder( "@ManyToOne(fetch = FetchType.LAZY) " + CgFormUtil.ENTER_MARK);
			sb =  sb.append("	@JoinColumn(name =\""+ cgFormFieldEntity.getDbName().toUpperCase() +"\",referencedColumnName=\"id\") " + CgFormUtil.ENTER_MARK);
			sb =  sb.append("	@NotFound(action=NotFoundAction.IGNORE) " + CgFormUtil.ENTER_MARK);
			sb =  sb.append("	@ForeignKey(name=\"null\")" + CgFormUtil.ENTER_MARK);
			sb =  sb.append("	@JsonIgnore");			
		}		
		
		cgFormFieldEntity.setJavaAnnotation(sb.toString());
		
	}

	
	
}
