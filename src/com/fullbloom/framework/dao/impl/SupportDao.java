package com.fullbloom.framework.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class SupportDao {
	
	/**
	 * 初始化Log4j实例
	 */
	public final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 **/
	@Autowired
	@Qualifier("sessionFactory")
	public SessionFactory sessionFactory;
	
	
	@Autowired
	@Qualifier("jdbcTemplate")
	public JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

}
