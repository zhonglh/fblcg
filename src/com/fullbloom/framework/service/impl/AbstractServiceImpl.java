package com.fullbloom.framework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fullbloom.framework.dao.ICommonDao;

/**
 * 公共的服务类
 * @author Administrator
 *
 */
public class AbstractServiceImpl {

	public Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	public ICommonDao commonDao = null;

	


}
