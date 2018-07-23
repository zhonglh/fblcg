package org.jeecgframework.codegenerate.enums;

import java.util.ArrayList;
import java.util.List;

public enum EnumJavaType {

	_Short("java.lang.Short")	,
	_Integer("java.lang.Integer"),
	_Long("java.lang.Long"),
	_Float("java.lang.Float"),
	_Double("java.lang.Double"),
	_BigDecimal("java.math.BigDecimal"),
	
	_String("java.lang.String"),
	_Date("java.util.Date"),
	_Clob("java.sql.Clob"),
	_Byte("java.lang.Byte"),
	_Bytes("byte[]")	
	
	;
	
	
	
	private String fullname;

	private EnumJavaType(String fullname) {
		
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	public static EnumJavaType getEnumJavaType(String javaClass){		
		for(EnumJavaType enumJavaType : EnumJavaType.values() ){
			if(enumJavaType.getFullname() .equals(javaClass)){
				return enumJavaType;
			}
		}		
		return null;		
	}
	
	
	public static List<String> getAll(){	
		List<String> list = new ArrayList<String>();
		for(EnumJavaType enumJavaType : EnumJavaType.values() ){
			list.add(enumJavaType.getFullname());
		}		
		return list;		
	}
	
	
	
	
	
	

}
