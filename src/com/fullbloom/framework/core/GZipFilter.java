package com.fullbloom.framework.core;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.p3.core.aop.ResMime;
import org.springframework.util.FileCopyUtils;

public class GZipFilter implements Filter {
	/*
    private static final Logger logger = Logger.getLogger(GZipFilter.class);
    private static final String STATIC_TEMPLATE_SOURCE = "online/template";
    private static final String STATIC_TEMPLATE_SOURCE_2 = "clzcontext/template";
    private static final String STATIC_TEMPLATE_SOURCE_3 = "/content/";
    private static final String STATIC_TEMPLATE_SOURCE_4 = "/plug-in-ui/";
    private static final String NO_FILTER = ".do";
    private static final String DIAN = ".";*/

    public GZipFilter() {
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest)request;
        String url = req.getRequestURI();
        String path = req.getContextPath();
        (new StringBuilder()).append(req.getScheme()).append("://").append(req.getServerName()).append(":").append(req.getServerPort()).append(path).toString();
        if((req.getRequestURI().indexOf("online/template") != -1 || req.getRequestURI().indexOf("clzcontext/template") != -1 || req.getRequestURI().indexOf("/content/") != -1 || req.getRequestURI().indexOf("/plug-in-ui/") != -1) && req.getRequestURI().indexOf(".") != -1 && req.getRequestURI().indexOf(".do") == -1) {
            if(url.startsWith(req.getContextPath())) {
                url = url.replaceFirst(req.getContextPath(), "");
            }

            response.reset();
            String s = ResMime.get(url.substring(url.lastIndexOf(".")).replace(".", ""));
            if(s != null) {
                response.setContentType(s);
            }

            ServletOutputStream os = null;
            InputStream is = null;

            try {
                is = this.getClass().getResourceAsStream(url);
                if(is != null) {
                    os = response.getOutputStream();
                    FileCopyUtils.copy(is, os);
                } else {
                    chain.doFilter(request, response);
                }
            } catch (IOException var25) {
                var25.printStackTrace();
            } finally {
                if(os != null) {
                    try {
                        os.close();
                    } catch (IOException var24) {
                        ;
                    }
                }

                if(is != null) {
                    try {
                        is.close();
                    } catch (IOException var23) {
                        ;
                    }
                }

            }
        } else {
            chain.doFilter(request, response);
        }       

    }
}
