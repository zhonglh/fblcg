package com.fullbloom.framework.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.fullbloom.framework.util.ContextHolderUtils;
import com.fullbloom.framework.util.ResourceUtil;
import com.fullbloom.web.entity.TsUser;
import com.fullbloom.web.vo.Client;

/**
 * 对在线用户的管理
 * 
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class ClientManager {

	private static ClientManager instance = new ClientManager();

	private Map<String,Client> map = new ConcurrentHashMap<String, Client>();

	private ClientManager() {

	}

	public static ClientManager getInstance() {
		return instance;
	}

	/**
	 * 用户登录，向session中增加用户信息
	 * 
	 * @param sessionId
	 * @param client
	 */
	public void addClinet(String sessionId, Client client) {		
		ContextHolderUtils.getSession().setAttribute(sessionId, client);
		ContextHolderUtils.getSession().setAttribute(ResourceUtil.LOCAL_CLINET_USER, client.getTsUser());
		map.put(sessionId, client);
	}

	/**
	 * 用户退出登录 从Session中删除用户信息 sessionId
	 */
	public void removeClinet(String sessionId) {
		ContextHolderUtils.getSession().removeAttribute(sessionId);
		map.remove(sessionId);
	}

	/**
	 * 根据sessionId 得到Client 对象
	 * 
	 * @param sessionId
	 */
	public Client getClient(String sessionId) {
		if(sessionId == null) return null;
		if (!StringUtils.isEmpty(sessionId) && ContextHolderUtils.getSession().getAttribute(sessionId) != null) {
			return (Client) ContextHolderUtils.getSession().getAttribute(sessionId);
		} else {
			return map.get(sessionId);
		}
	}

	/**
	 * 得到Client 对象
	 */
	public Client getClient() {
		String sessionId = ContextHolderUtils.getSession().getId();
		if(sessionId == null) return null;
		if (!StringUtils.isEmpty(sessionId) && ContextHolderUtils.getSession().getAttribute(sessionId) != null) {
			return (Client) ContextHolderUtils.getSession().getAttribute(sessionId);
		} else {
			return map.get(sessionId);
		}
	}

}
