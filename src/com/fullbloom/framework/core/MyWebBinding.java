package com.fullbloom.framework.core;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class MyWebBinding implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
			binder.registerCustomEditor(Date.class, new DateConvertEditor());
			binder.registerCustomEditor(Integer.class, new IntConverEditor());
	}

}
