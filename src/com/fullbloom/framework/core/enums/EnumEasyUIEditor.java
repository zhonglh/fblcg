package com.fullbloom.framework.core.enums;

import java.util.ArrayList;
import java.util.List;

public enum EnumEasyUIEditor {	
	text,
	textarea,
	checkbox,
	numberbox,
	validatebox,
	datebox,
	combobox,
	combotree,
	combogrid
	;
	
	


	public static List<String> getAll(){	
		List<String> list = new ArrayList<String>();
		for(EnumEasyUIEditor enumEasyUIEditor : EnumEasyUIEditor.values() ){
			list.add(enumEasyUIEditor.name());
		}		
		return list;		
	}
	
	
}
