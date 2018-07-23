package com.fullbloom.cg.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fullbloom.cg.entity.CgFormFieldEntity;
import com.fullbloom.cg.service.ICgFormFieldService;
import com.fullbloom.framework.core.base.BaseServiceImpl;

@SuppressWarnings("serial")
@Service("cgFormFieldServiceImpl")
public class CgFormFieldServiceImpl extends BaseServiceImpl<CgFormFieldEntity> implements ICgFormFieldService {


	@Override
	protected String getVagueHQL(List<Object> params, CgFormFieldEntity t, Object otherSearch, Object user) {
		
		StringBuilder sb = new  StringBuilder("from CgFormFieldEntity where 1=1 ");
		if(t.getTable() != null && StringUtils.isNotEmpty(t.getTable().getId())){
			sb = sb.append("and table.id = ? ");
			params.add(t.getTable().getId());
		}
		
		return sb.toString();
		
	}

	@Override
	protected String getHQL(List<Object> params, CgFormFieldEntity t, Object otherSearch, Object user) {
		StringBuilder sb = new  StringBuilder("from CgFormFieldEntity where 1=1 ");
		if(t.getTable() != null && StringUtils.isNotEmpty(t.getTable().getId())){
			sb = sb.append("and table.id = ? ");
			params.add(t.getTable().getId());
		}
		
		return sb.toString();
	}

	@Override
	protected String getOrderBy(List<Object> params, CgFormFieldEntity t, Object otherSearch, Object user) {		
		return null;
	}
	
}