package org.jeecgframework.codegenerate.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum EnumDbType {
	
	
	TINYINT,
	SMALLINT,
	SHORT,
	INTEGER,
	LONG,
	FLOAT,
	DOUBLE,
	NUMBER,
	NUMERIC,
	DECIMAL,
	CHAR,
	VARCHAR,
	VARCHAR2,
	DATE,
	DATETIME,
	TIMESTAMP,
	CLOB,
	BLOB,
	
	;
	
	
	public static EnumDbType getDbType(String dbtype){
		
		dbtype = dbtype.toUpperCase();
		
		if(dbtype.startsWith("INT")) return EnumDbType.INTEGER;
		else if(dbtype.startsWith("LONG")) return EnumDbType.LONG;
		else if(dbtype.startsWith("FLOAT")) return EnumDbType.FLOAT;
		else if(dbtype.startsWith("DOUBLE")) return EnumDbType.DOUBLE;
		else if(dbtype.startsWith("SHORT")) return EnumDbType.SHORT;
		else {
			
			for(EnumDbType enumDbType : EnumDbType.values()){
				if(dbtype.toUpperCase().startsWith(enumDbType.name())){
					return enumDbType;
				}
			}
			
			
			System.out.println(dbtype+"类型不兼容！");

			return null;
		}
		
		
	}

	public static EnumJavaType getJavaType(String dbtype, String precision, String scale ){
		EnumDbType enumDbType = getDbType(dbtype);
		if(enumDbType == null) return null;
		return getJavaType(enumDbType,precision,scale);
		
	}
	
	public static EnumJavaType getJavaType(EnumDbType enumDbType, String precision, String scale ){

		if(precision != null && precision.equals("null")) precision = null;
		if(scale != null && scale.equals("null")) scale = null;
		

		if(enumDbType == TINYINT) return EnumJavaType._Byte;
		if(enumDbType == SMALLINT) return EnumJavaType._Short;		
		if(enumDbType == SHORT) return EnumJavaType._Short;	
		
		
		if(enumDbType == CHAR) return EnumJavaType._String;
		if(enumDbType == VARCHAR) return EnumJavaType._String;
		if(enumDbType == VARCHAR2) return EnumJavaType._String;
		
		
		else if(enumDbType == INTEGER) return EnumJavaType._Integer;
		else if(enumDbType == LONG) return EnumJavaType._Long;
		else if(enumDbType == DOUBLE) return EnumJavaType._Double;	
		else if(enumDbType == FLOAT) return EnumJavaType._Float;		
		else if(enumDbType == NUMBER) {
			
			if(StringUtils.isNotEmpty(scale) && Integer.parseInt(scale) > 0){
				return EnumJavaType._BigDecimal;
			}else if(StringUtils.isNotEmpty(precision) && Integer.parseInt(precision) > 10){
				return EnumJavaType._Long;
			}else{
				return EnumJavaType._Integer;
			}
		}
		else if(enumDbType == NUMERIC) return EnumJavaType._BigDecimal;
		else if(enumDbType == DECIMAL) return EnumJavaType._BigDecimal;

		else if(enumDbType == DATE) return EnumJavaType._Date;
		else if(enumDbType == DATETIME) return EnumJavaType._Date;
		else if(enumDbType == TIMESTAMP) return EnumJavaType._Date;

		else if(enumDbType == CLOB) return EnumJavaType._Clob;
		else if(enumDbType == CLOB) return EnumJavaType._Bytes;
		
		else return null;
		
	}
	

	public static List<String> getAll(){	
		List<String> list = new ArrayList<String>();
		for(EnumDbType enumDbType : EnumDbType.values() ){
			list.add(enumDbType.name());
		}		
		return list;		
	}
	
	
	

}
