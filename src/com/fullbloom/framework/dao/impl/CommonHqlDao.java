package com.fullbloom.framework.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.fullbloom.framework.core.dbutil.HqlQuery;
import com.fullbloom.framework.core.dbutil.PagerUtil;

/**
 * HQL处理数据库
 * @author Administrator
 *
 */
@Repository
public class CommonHqlDao<T, PK extends Serializable> extends SupportDao {
	
	
	/**
	 * hql 更新数据
	 * @param hql
	 * @return
	 */
	public int update(String hql){
		Query querys = getSession().createQuery(hql);
		return querys.executeUpdate();
	}
	
	
	public int update(String hql,Object ... objs) {
		Query q = getSession().createQuery(hql);
		int index = 0;
		if(objs != null && objs.length>0){
			for(Object obj : objs){
				q.setParameter(index, obj);
				index ++;
			}
		}
		return q.executeUpdate();
	}
	
	

	public int execute(final String hql) {
		Query query = getSession().createQuery(hql);
		return query.executeUpdate();
	}
	
	/**
	 * 执行HQL
	 * @param hql
	 * @param values
	 * @return
	 */
	public int execute(final String hql, final Object ... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.executeUpdate();
	}
	
	
	
	
	
	
	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param hql
	 * @return
	 */
	public List<T> findList(final String hql) {
		Query query = getSession().createQuery(hql);
		List<T> list = query.list();		
		return list;
	}
	
	public <T> List<T> findList(String hql, Object... objs) {
		Query query = getSession().createQuery(hql);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		return (List<T>)query.list();
	}
	
	
	
	
	public List<T> findList(final String hql, int page, int rows) {
		Query query = getSession().createQuery(hql);

		int curPageNO = page;
		int offset = PagerUtil.getOffset(Integer.MAX_VALUE, curPageNO, rows);
		
		query.setFirstResult(offset);
		query.setMaxResults(rows);
		
		return query.list();
	}
	
	

	
	public List<T> findList(final String hql, int page, int rows,Object ... objs) {
		Query query = getSession().createQuery(hql);

		int curPageNO = page;
		int offset = PagerUtil.getOffset(Integer.MAX_VALUE, curPageNO, rows);
		
		query.setFirstResult(offset);
		query.setMaxResults(rows);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		return query.list();
	}
	
	
	public <T> T findOneEntity(Class<T> entityClass, final Serializable id) {
		String hql = "from "+entityClass.getName()+" where id=?";
		return findOneEntity(hql,id);
	}
	

	/**
	 * 通过hql查询唯一对象
	 * 
	 * @param <T>
	 * @param hql
	 * @return
	 */
	public <T> T findOneEntity(String hql) {
		T t = null;
		Query query = getSession().createQuery(hql);
		return (T) query.uniqueResult();
	}
	

	public <T> T findOneEntity(String hql,Object ... objs) {
		T t = null;
		Query query = getSession().createQuery(hql);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		return (T) query.uniqueResult();
	}
	
	
	/**
	 * 通过hql 查询语句查找HashMap对象
	 *
	 * @param hql
	 * @return
	 */
	public Map<Object, Object> findOneMap(String hql) {

		Query query = getSession().createQuery(hql);
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;
	}

	public Map<Object, Object> findOneMap(String hql, Object ... objs) {

		Query query = getSession().createQuery(hql);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;

	}
	
	

	
	
	/**
	 * hql 方式返回Query
	 * @param session
	 * @param hql
	 * @param objs
	 * @return
	 */
	public Query createQuery(Session session, String hql, Object... objs) {
		Query query = session.createQuery(hql);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		return query;
	}

	public Query createQuery( String hql, Object... objs) {
		Query query = getSession().createQuery(hql);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		return query;
	}
	
	

	
	
	

	/**
	 * 查询指定实体的总记录数
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> int getInteger(Class<T> clazz) {
		
		String hql = "select count(*) from " + clazz.getName();
		return ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
	}
	
	
	
	public int getInteger(String hql) {
		return ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
	}

	
	public int getInteger(String hql,Object ... objs) {
		Query query = getSession().createQuery(hql);
		
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		
		return ((Number)query.uniqueResult()).intValue();
	}
	
	
	public Long getLong(String hql) {
		return (Long)getSession().createQuery(hql).uniqueResult();
	}	

	
	public Long getLong(String hql,Object ... objs) {
		Query query = getSession().createQuery(hql);
		
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		
		return (Long)query.uniqueResult();
	}


	/**
	 * 获取分页记录HqlQuery
	 * @param hqlQuery
	 * @param needParameter 是否需要参数
	 * @return
     */
	@SuppressWarnings("unchecked")
	public List getPageList(final HqlQuery hqlQuery, final boolean needParameter) {
		int allCounts = 0;
		String countHQL = null;
		if(hqlQuery.getQueryString().toUpperCase().startsWith("SELECT ")){
			countHQL = "select count(*) "+ hqlQuery.getQueryString().substring(hqlQuery.getQueryString().toUpperCase().indexOf("FROM "));
		}else {
			countHQL = "select count(*) "+hqlQuery.getQueryString();
		}
		
		if(countHQL.toUpperCase().indexOf("ORDER BY")>0){
			countHQL = countHQL.substring(0, countHQL.toUpperCase().lastIndexOf("ORDER BY"));
		}
		
		Query queryCount = getSession().createQuery(countHQL);
		if (needParameter) {
//			queryCount.setParameters(hqlQuery.getParam(), (Type[]) hqlQuery.getTypes());

			for (int i = 0; i < hqlQuery.getParam().length; i++) {
				queryCount.setParameter(i, hqlQuery.getParam()[i]);
			}
		}
		
		
		
		allCounts = ((Number)queryCount.uniqueResult()).intValue();
		
		hqlQuery.setTotal(allCounts);
		Query query = getSession().createQuery(hqlQuery.getQueryString());

		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());

		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		if (hqlQuery.getParam() != null) {
			for (int i = 0; i < hqlQuery.getParam().length; i++) {
				query.setParameter(i, hqlQuery.getParam()[i]);
			}
		}
		List list = query.list();
		hqlQuery.setResults(list);
		return list;
	}	
	



}
