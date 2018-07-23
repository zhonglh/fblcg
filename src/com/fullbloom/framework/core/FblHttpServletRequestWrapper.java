package com.fullbloom.framework.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FblHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	List<String> excludeNames;

	public FblHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	

	public FblHttpServletRequestWrapper(HttpServletRequest request,List<String> excludeNames) {
		super(request);
		this.excludeNames = excludeNames;
	}
	
	public String getParameter(String name){
		String val = super.getParameter(name);
		if(excludeNames != null && !excludeNames.contains(name)){
			val =  process(val);
		}
		return val;
	}

	public String[] getParameterValues(String name){
		String[] val = super.getParameterValues(name);
		if(excludeNames != null && !excludeNames.contains(name)){
			val =  process(val);
		}
		return val;
	}
	
	public Map<String, String[]> getParameterMap(){
		Map<String, String[]> map = super.getParameterMap();
		return process(map);
		
	
		
	}
	
	
	private String process(String val){
		if(val == null || val.isEmpty()) return val;
		else return val.trim();
	}
	
	
	
	private String[] process(String[] values) {
        if (values != null && values.length > 0) {
            int len = values.length;
            for (int i = 0; i < len; i++) {
                values[i] = process(values[i]);
            }
        }
        return values;
    }
	

	private Map<String,String[]> process(Map<String,String[]> map) {
		if(map == null || map.isEmpty()) return map;
		for(String key : map.keySet()){
			if(excludeNames == null || excludeNames.contains(key)) continue;
			
			String[] values = map.get(key);
			process(values);
		}
		
		return map;
		
	}
	
}
