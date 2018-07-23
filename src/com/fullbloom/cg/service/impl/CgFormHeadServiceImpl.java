package com.fullbloom.cg.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullbloom.cg.entity.CgFormHeadEntity;
import com.fullbloom.cg.service.ICgFormFieldService;
import com.fullbloom.cg.service.ICgFormHeadService;
import com.fullbloom.cg.service.ICgFormIndexService;
import com.fullbloom.framework.core.base.BaseServiceImpl;
import com.fullbloom.framework.util.ResourceUtil;
import com.fullbloom.web.entity.TsUser;

@SuppressWarnings("serial")
@Service("cgFormHeadServiceImpl")
public class CgFormHeadServiceImpl extends BaseServiceImpl<CgFormHeadEntity> implements ICgFormHeadService {

	@Autowired
	private ICgFormFieldService cgFormFieldService;
	
	@Autowired
	private ICgFormIndexService cgFormIndexService;

	@Override
	public void deleteEntity(CgFormHeadEntity cgFormHeadEntity){
		CgFormHeadEntity entity = null;
		if(StringUtils.isEmpty(cgFormHeadEntity.getTableName())){
			entity = getEntityById(cgFormHeadEntity);
		}else {
			entity = cgFormHeadEntity;
		}
		
		if(cgFormHeadEntity.getColumns() != null && !cgFormHeadEntity.getColumns() .isEmpty()){
			cgFormFieldService.deleteEntity(cgFormHeadEntity.getColumns());
		}		

		if(cgFormHeadEntity.getIndexes() != null && !cgFormHeadEntity.getIndexes() .isEmpty()){
			cgFormIndexService.deleteEntity(cgFormHeadEntity.getIndexes());
		}
		
		this.commonDao.delete(entity);
		
	}
	

	@Override
	protected String getVagueHQL(List<Object> params, CgFormHeadEntity t, Object otherSearch, Object user) {
		TsUser user1 = ResourceUtil.getSessionUser();
		StringBuilder sb = new  StringBuilder("from CgFormHeadEntity where createBy = ? ");
		params.add(user1.getId());
		
		return sb.toString();
		
	}

	@Override
	protected String getHQL(List<Object> params, CgFormHeadEntity t, Object otherSearch, Object user) {
		TsUser user1 = ResourceUtil.getSessionUser();
		StringBuilder sb = new  StringBuilder("from CgFormHeadEntity where createBy = ?  ");
		params.add(user1.getId());
		
		return sb.toString();
	}

	@Override
	protected String getOrderBy(List<Object> params, CgFormHeadEntity t, Object otherSearch, Object user) {		
		return null;
	}
	

	
}
