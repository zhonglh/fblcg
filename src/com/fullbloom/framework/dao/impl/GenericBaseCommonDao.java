package com.fullbloom.framework.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.fullbloom.framework.core.AjaxJson;
import com.fullbloom.framework.core.dbutil.CriteriaQuery;
import com.fullbloom.framework.core.dbutil.DataGridReturn;
import com.fullbloom.framework.core.dbutil.DataTableReturn;
import com.fullbloom.framework.core.dbutil.HqlQuery;
import com.fullbloom.framework.core.dbutil.PageList;
import com.fullbloom.framework.core.dbutil.SqlQuery;
import com.fullbloom.framework.dao.IGenericBaseCommonDao;

/**
 * 
 * 类描述： DAO层泛型基类
 * 
 * 张代浩 @date： 日期：2012-12-7 时间：上午10:16:48
 * 
 * @param <T>
 * @param <PK>
 * @version 1.0
 */
@SuppressWarnings("hiding")
public abstract class GenericBaseCommonDao<T, PK extends Serializable> implements IGenericBaseCommonDao {
	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger.getLogger(GenericBaseCommonDao.class);

	@Autowired
	private CommonQbcDao<T,PK> commonQbcDao;
	
	@Autowired
	private CommonHqlDao<T,PK> commonHqlDao;
	
	@Autowired
	private CommonSqlDao<T,PK> commonSqlDao;
	
	

	@Override
	public Session getSession() {
		return commonQbcDao.getSession();
	}
	
	@Override
	public void clearSession(){
		getSession().clear();
	}
	

	/**
	 * 获得该类的属性和类型
	 * 
	 * @param clz 注解的实体类
	 */
	@Override
	public <T> void getProperty(Class clz) {
		commonQbcDao.getProperty(clz);
	}



	/**
	 * 获取所有数据表
	 * 
	 * @return
	 */
	@Override
	public Integer getAllDbTableSize() {
		return commonQbcDao.getAllDbTableSize();
	}



	
	
	
	
	
	
	
	

	



	
	/**
	 * 根据传入的实体持久化对象
	 */
	@Override
	public <T> Serializable save(T entity) {
		try {
			Serializable id = getSession().save(entity);		
			getSession().flush();
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}

	}
	

	/**
	 * 批量保存数据
	 * 
	 * @param <T>
	 * @param entitys
	 *            要持久化的临时实体对象集合
	 */
	@Override
	public <T> void batchSave(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if(i%20==0){
				getSession().flush();
				getSession().clear();
			}
		}
		
		getSession().flush();
		getSession().clear();
		
	}

	/**
	 * 批量更新数据
	 * @param entitys
	 * @param <T>
	 */
	public <T> void  batchUpdate(List<T> entitys){
		for (int i = 0; i < entitys.size(); i++) {
			getSession().update(entitys.get(i));
			if(i%20==0){
				getSession().flush();
				getSession().clear();
			}
		}

		getSession().flush();
		getSession().clear();
	}


	/**
	 * 根据传入的实体添加或更新对象
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 */
	@Override
	public <T> void saveOrUpdate(T entity) {
		try {
			getSession().saveOrUpdate(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}
	
	


	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	@Override
	public <T> void updateEntitie(T pojo) {
		getSession().update(pojo);		
		getSession().flush();
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param className
	 * @param object
	 */
	@Override
	public <T> void updateEntitie(String className, Object object) {
		getSession().update(className, object);
		getSession().flush();
	}

	/**
	 * 根据主键更新实体
	 */
	@Override
	public <T> void updateEntityById(Class entityName, Serializable id) {
		updateEntitie(findOne(entityName, id));
		getSession().flush();
	}	

	
	
	/**
	 * 根据传入的实体删除对象
	 */
	@Override
	public <T> void delete(T entity) {
		try {
			getSession().delete(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}

	/**
	 * 根据主键删除指定的实体
	 * 
	 * @param entityName
	 * @param id
	 */
	@Override
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		delete(findOne(entityName, id));
	}

	/**
	 * 删除全部的实体
	 * 
	 * @param <T>
	 * 
	 * @param entitys
	 */
	@Override
	public <T> void deleteAllEntitie(Collection<T> entitys) {
		int i=0;
		for (Object entity : entitys) {
			this.delete(entity);
			i++;
			if(i%20==0){
				getSession().flush();
				getSession().clear();
			}
		}
		getSession().flush();
		getSession().clear();
	}
	
	


	
	

	
	

	@Override
	public <T> T loadOne(Class<T> entityClass, final Serializable id){
		return commonHqlDao.findOneEntity(entityClass, id);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据Id获取对象。
	 */
	@Override
	public <T> T findOne(Class<T> entityClass, final Serializable id) {
		return commonQbcDao.findOne(entityClass, id);
	}

	/**
	 * 根据主键获取实体并加锁。
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	@Override
	public <T> T findOneEntity(Class entityName, Serializable id) {
		return commonQbcDao.findOneEntity(entityName, id);
	}
	
	/**
	 * 根据实体名字获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public <T> T findOneEntity(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return commonQbcDao.findOneEntity(entityClass, propertyName, value);
	}


	/**
	 * 通过hql查询唯一对象
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public <T> T findOneEntityByHql(String hql) {
		
		return commonHqlDao.findOneEntity(hql);
	}

	@Override
	public <T> T findOneEntityByHql(String hql,Object ... objs) {		
		return commonHqlDao.findOneEntity(hql,objs);
	}




	/**
	 * 通过hql 查询语句查找HashMap对象
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public Map<Object, Object> findOneMapByHql(String hql) {
		return commonHqlDao.findOneMap(hql);
	}
	

	@Override
	public Map<Object, Object> findOneMap(String hql, Object ... objs) {
		return commonHqlDao.findOneMap(hql,objs);
	}
	
	
	
	
	/**
	 * 通过hql查询唯一对象
	 * 
	 * @param sql
	 * @param clz
	 * @return
	 */
	@Override
	public <T> T findOneEntityBySql(String sql, Class<T> clz) {
		
		return commonSqlDao.findOne(sql, clz);
	}

	@Override
	public <T> T findOneEntityBySql(String sql,Class<T> clz,Object ... objs) {
		
		return commonSqlDao.findOne(sql,clz,objs);
	}
	
	

	@Override
	public Map<String, Object> findOneMapBySql(String sql) {
		return commonSqlDao.findOneMap(sql);
	}
	
	public Map<String, Object> findOneMapBySql(String sql, Object... objs) {
		return commonSqlDao.findOneMap(sql,objs);
	}
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 通过sql更新记录
	 *
	 * @param sql
	 * @return
	 */
	@Override
	public int executeBySql(final String sql) {
		return commonSqlDao.update(sql);
	}

	@Override
	public Integer executeBySql(String sql, List<Object> param) {
		return commonSqlDao.execute(sql,param);
	}

	@Override
	public Integer executeBySql(String sql, Object... param) {
		return commonSqlDao.execute(sql,param);
	}

	@Override
	public Integer executeBySql(String sql, Map<String, Object> param) {
		return commonSqlDao.execute(sql,param);
	}

	@Override
	public Object executeSqlReturnKey(final String sql, Map<String, Object> param) {
		
		return commonSqlDao.executeSqlReturnKey(sql, param);
	}

	

	/**
	 * 根据hql更新
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public int executeByHql(String hql){		
		return commonHqlDao.update(hql);
	}
	

	@Override
	public Integer executeByHql(String hql,Object ... objs) {
		return commonHqlDao.update(hql,objs);
	}
	
	
	

	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 根据属性名和属性值查询. 有排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param isAsc
	 * @return
	 */
	@Override
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
		Assert.hasText(propertyName);
		return commonQbcDao.findList(entityClass, propertyName, value, isAsc);
	}
	

	/**
	 * 根据实体模版查找
	 * 
	 * @param entityName
	 * @param exampleEntity
	 * @return
	 */
	@Override
	public List findList(final String entityName, final Object exampleEntity) {
		return commonQbcDao.findList(entityName, exampleEntity);
	}
	

	/**
	 * 按属性查找对象列表.
	 */
	@Override
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return commonQbcDao.findList(entityClass, propertyName, value);
	}
	

	/**
	 * 根据CriteriaQuery获取List
	 * 
	 * @param cq
	 * @param ispage
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(final CriteriaQuery cq, Boolean ispage) {
		return commonQbcDao.findList(cq, ispage);
	}
	
	
	
	

	


	

	@Override
	public <T> List<T> findAllList(final Class<T> entityClass) {
		return commonQbcDao.loadAll(entityClass);
	}

	/**
	 * 离线查询
	 */
	@Override
	public <T> List<T> findListByDetached(DetachedCriteria dc) {
		return commonQbcDao.findByDetached(dc);
	}
	
	public <T> List<T> findList(DetachedCriteria dc, int firstResult, int maxResult) {
		return commonQbcDao.findList(dc, firstResult, maxResult);
	}
	
	
	/**
	 * 分页查询
	 */
	@Override
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {	
		return commonQbcDao.getPageList(cq, isOffset);
	}
	
	
	/**
	 * 返回easyui datagrid DataGridReturn模型对象
	 */
	@Override
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonQbcDao.getDataGridReturn(cq, isOffset);	
	}
	
	


	/**
	 * 返回JQUERY datatables DataTableReturn模型对象
	 */
	@Override
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonQbcDao.getDataTableReturn(cq, isOffset);
	}

	
	
	
	
	
	
	
	
	

	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public List<T> findListByHql(final String hql) {
		return commonHqlDao.findList(hql);

	}
	
	/**
	 * HQL查询
	 * @param hql
	 * @param objs
	 * @return
	 */
	@Override
	public <T> List<T> findListByHql(final String hql, final Object ... objs) {		
		return commonHqlDao.findList(hql, objs);
	}
	

	/**
	 * 获取分页记录HqlQuery
	 * 
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPageListByHql(final HqlQuery hqlQuery, final boolean needParameter) {
		commonHqlDao.getPageList(hqlQuery, needParameter);
		return hqlQuery.getResults();
	}	
	
	

	/**
	 * 获取分页记录HqlQuery
	 * 
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getPageByHql(final HqlQuery hqlQuery, final boolean needParameter) {
		commonHqlDao.getPageList(hqlQuery, needParameter);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	/**
	 * 通过sql查询语句查找对象
	 *
	 * @param sql
	 * @return
	 */
	@Override
	public List<T> findListBySql(final String sql) {
		return commonSqlDao.findList(sql);
	}

	@Override
	public <T> List<T> findListBySql(String sql, Class<T> clazz){
		return commonSqlDao.findList(sql,clazz);
	}

	@Override
	public <T> List<T> findListBySql(String sql, Class<T> clazz, Object... objs){
		return commonSqlDao.findList(sql,clazz,objs);
	}


	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Override
	public <T> List<T> findListBySql(String sql, int page, int rows, Class<T> clazz) {
		return commonSqlDao.findList(sql,page,rows,clazz);
	}

	@Override
	public <T> List<T> findListBySql(String sql, int page, int rows, Class<T> clazz,Object... objs){
		return commonSqlDao.findList(sql,page,rows,clazz,objs);
	}	

	
	

	@Override
	public List<Map<String, Object>> findListBySql(String sql, Object... objs) {
		return commonSqlDao.findList(sql, objs);
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	@Override
	public List<Map<String, Object>> findListBySql(String sql, int page, int rows) {
		return commonSqlDao.findList(sql,page,rows);
	}

	/**
	 * 
	 */
	@Override
	public List<Map<String, Object>> findListBySql(String sql, int page, int rows, Object... objs) {
		return commonSqlDao.findList(sql,page,rows,objs);
	}


	/**
	 * 获取分页记录SqlQuery
	 * 
	 * @param sqlQuery
	 * @param isToEntity
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageList getPageListBySql(final SqlQuery sqlQuery, final boolean isToEntity) {
		return commonSqlDao.getPageList(sqlQuery, isToEntity);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	/**
	 * 
	 * @param hql
	 * @return
	 */
	@Override
	public Long getLongByHql(String hql){
		return commonHqlDao.getLong(hql);
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	@Override
	public Long getLongByHql(String hql, Object ... objs){
		return commonHqlDao.getLong(hql,objs);		
	}

	
	
	

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	@Override
	public Long getLongBySql(String sql) {
		return commonSqlDao.getLong(sql);
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	@Override
	public Long getLongBySql(String sql, Object ... objs) {
		return commonSqlDao.getLong(sql,objs);
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 使用指定的检索标准获取满足标准的记录数
	@Override
	public int getInteger(DetachedCriteria criteria) {
		return commonQbcDao.getInteger(criteria);
	}

	
	
	

	@Override
	public Integer getIntegerBySql(String sql) {
		return commonSqlDao.getInteger(sql);
	}
	

	@Override
	public Integer getIntegerBySql(String sql, Object... param) {
		return commonSqlDao.getInteger(sql,param);
	}
	
	

	/**
	 * 查询指定实体的总记录数
	 * 
	 * @param clazz
	 * @return
	 */
	@Override
	public <T> int getIntegerByHql(Class<T> clazz) {
		return commonHqlDao.getInteger(clazz);
	}

	@Override
	public int getIntegerByHql(String hql) {
		return commonHqlDao.getInteger(hql);
	}

	@Override
	public int getIntegerByHql(String hql, Object ... objs) {
		return commonHqlDao.getInteger(hql,objs);
	}
	
	
	



	
	
	
	
	
	
	
	
	
	


	@Override
	public AjaxJson execProc(String procName, final Object... params) {		
		return commonSqlDao.execProc(procName, params);		
	}

	@Override
	public <T> List<T> findListByProc(String procName, String... params) {
		return commonSqlDao.findListByProc(procName, params);
	}


	



}
