package com.fullbloom.framework.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fullbloom.framework.core.ClientManager;
import com.fullbloom.framework.core.dbutil.DBTypeUtil;
import com.fullbloom.web.entity.TsUser;
import com.fullbloom.web.vo.Client;

/**
 * 项目参数工具类
 * 
 */
public class ResourceUtil {

	public static final String LOCAL_CLINET_USER = "LOCAL_CLINET_USER";

	public static final TsUser getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		if (ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getTsUser();
		} else {
			TsUser u = (TsUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			Client client = new Client();
			client.setIp("");
			client.setLoginTime(new Date());
			client.setTsUser(u);
			ClientManager.getInstance().addClinet(session.getId(), client);
		}

		return null;
	}
	
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}
	
	/**
	 * 获取数据库类型
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static final String getJdbcUrl() {
		return DBTypeUtil.getDBType().toLowerCase();
	}

}
