package com.fullbloom.cg.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fullbloom.cg.entity.DbConfigEntity;
import com.fullbloom.cg.service.IDbConfigService;
import com.fullbloom.framework.core.base.BaseServiceImpl;


@Service
@SuppressWarnings("serial")
public class DbConfigServiceImpl extends BaseServiceImpl<DbConfigEntity> implements IDbConfigService {

	
	
	protected String getHQL(List<Object> params,DbConfigEntity t, Object otherSearch, Object user){

		StringBuilder sb = new StringBuilder(" from DbConfigEntity where 1=1 ");
		
		if(t != null){
			if(StringUtils.isNotEmpty(t.getDbUrl())){
				sb = sb.append(" and dbUrl = ? ");
				params.add(t.getDbUrl());
			}

			if(StringUtils.isNotEmpty(t.getDbType())){
				sb = sb.append(" and dbType = ? ");
				params.add(t.getDbType());
			}
			
		}
		
		return sb.toString();
	
	}
	
	

	protected String getVagueHQL(List<Object> params,DbConfigEntity t, Object otherSearch, Object user){

		StringBuilder sb = new StringBuilder(" from DbConfigEntity where 1=1 ");
		
		if(t != null){
			if(StringUtils.isNotEmpty(t.getDbUrl())){
				sb = sb.append(" and dbUrl like ? ");
				params.add("%"+t.getDbUrl() + "%");
			}

			if(StringUtils.isNotEmpty(t.getDbType())){
				sb = sb.append(" and dbType like ? ");
				params.add("%"+t.getDbType() + "%");
			}
			
		}
		
		return sb.toString();
	
	}



	@Override
	protected String getOrderBy(List<Object> params, DbConfigEntity t, Object otherSearch, Object user) {
		return null;
	}
	
	
	
}
