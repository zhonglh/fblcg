package com.fullbloom.framework.core;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class IntConverEditor extends PropertyEditorSupport {
	

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			
				if(text == null || text.trim().isEmpty()){
					setValue(null);
				}else {
					setValue(Integer.parseInt(text));
				}
			
		} else {
			setValue(null);
		}
	}

}
