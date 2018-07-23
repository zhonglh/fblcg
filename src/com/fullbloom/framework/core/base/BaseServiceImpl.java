package com.fullbloom.framework.core.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fullbloom.framework.core.dbutil.DataGrid;
import com.fullbloom.framework.core.dbutil.HqlQuery;
import com.fullbloom.framework.service.impl.AbstractServiceImpl;
import com.fullbloom.web.entity.IdEntity;

public abstract class BaseServiceImpl<T extends IdEntity> extends AbstractServiceImpl implements IBaseService<T > {

	@Override
	public void datagrid(T t,Object otherSearch, Object user, DataGrid dataGrid) {
		List<Object> params = new ArrayList<Object>();
		String hql = this.getVagueHQL(params,t,otherSearch,user);
		String orderby = this.getOrderBy(params, t, otherSearch, user);
		if(orderby != null && orderby.length() > 0) hql = (hql + orderby);
		
		HqlQuery hq = new HqlQuery(hql, dataGrid);
		hq.setParam(params.toArray());
		List<T> list = this.commonDao.getPageListByHql(hq, true);
		dataGrid.setTotal(hq.getTotal());
		dataGrid.setResults(list);		
	}

	@Override
	public List<T> getList(T t,Object otherSearch, Object user){		
		List<Object> params = new ArrayList<Object>();
		String hql = getHQL(params,t,otherSearch,user);		
		String orderby = this.getOrderBy(params, t, otherSearch, user);
		if(orderby != null && orderby.length() > 0) hql = (hql + orderby);
		
		return this.commonDao.findListByHql(hql, params.toArray());		
	}

	@Override
	public boolean isExist(T t) {
		
		List<Object> params = new ArrayList<Object>();
		String hql = getHQL(params,t,null,null);
		if(StringUtils.isNotEmpty(t.getId()) ){
			hql += " and id <> ?";
			params.add( t.getId() );
		}
		if(hql.startsWith("select")) hql = "select count(*) from ("+hql+")";
		else hql = "select count(*) "+hql;
		Integer count = this.commonDao.getIntegerByHql(hql, params.toArray());
		return count > 0;
	}
	

	@Override
	public T getEntityById( T  t) {
		if(t == null) return null;
		return (T) this.commonDao.findOne(t.getClass(), t.getId());
	}

	
	@Override
	public void saveEntity(List<T> ts) {
		if(ts != null && ts.size() >0 ){
			for(T t : ts){
				this.saveEntity(t);
			}
		}
		
	}

	@Override
	public void updateEntity(List<T> ts) {
		if(ts != null && ts.size() >0 ){
			for(T t : ts){
				this.updateEntity(t);
			}
		}
		
	}

	@Override
	public void deleteEntity(List<T> ts) {
		if(ts != null && ts.size() >0 ){
			for(T t : ts){
				this.deleteEntity(t);
			}
		}
		
	}



	@Override
	public void saveEntity(T t) {
		this.commonDao.save(t);
		
	}


	@Override
	public void updateEntity(T t) {
		this.commonDao.updateEntitie(t);
		
	}


	@Override
	public void deleteEntity(T t) {
		this.commonDao.delete(t);		
	}
	
	

	/**
	 * 获取模糊查询的HQL 和参数
	 * @param params
	 * @param t
	 * @param otherSearch
	 * @param user
	 * @return
	 */
	protected abstract String getVagueHQL(List<Object> params,T t, Object otherSearch, Object user) ;
	
	/**
	 * 获取精确查询的HQL 和参数
	 * @param params
	 * @param t
	 * @param otherSearch
	 * @param user
	 * @return
	 */
	protected abstract String getHQL(List<Object> params,T t, Object otherSearch, Object user);
	
	/**
	 * 获取排序HQL
	 * @param params
	 * @param t
	 * @param otherSearch
	 * @param user
	 * @return
	 */
	protected abstract String getOrderBy(List<Object> params,T t, Object otherSearch, Object user);

}
