package com.fullbloom.framework.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestParameterFilter implements Filter {


    private List<String> excludeNames;

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {        
        request = new FblHttpServletRequestWrapper((HttpServletRequest) request,excludeNames);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        String exclude = config.getInitParameter("exclude");
        if (exclude != null && exclude.length() > 0) {
            excludeNames = Arrays.asList(exclude.split(","));
        }
    }

    /**
     * 
     * @return 不处理的对象。
     */
    public List<String> getExcludeNames() {
        return excludeNames;
    }

}