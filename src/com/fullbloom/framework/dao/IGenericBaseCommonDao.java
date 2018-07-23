package com.fullbloom.framework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.fullbloom.framework.core.AjaxJson;
import com.fullbloom.framework.core.dbutil.CriteriaQuery;
import com.fullbloom.framework.core.dbutil.DataGridReturn;
import com.fullbloom.framework.core.dbutil.DataTableReturn;
import com.fullbloom.framework.core.dbutil.HqlQuery;
import com.fullbloom.framework.core.dbutil.PageList;
import com.fullbloom.framework.core.dbutil.SqlQuery;

/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 * 张代浩
 * @date： 日期：2012-12-8 时间：下午05:37:33
 * @version 1.0
 */
public interface IGenericBaseCommonDao {
	

	public Session getSession() ;

	public void clearSession();

	public <T> void getProperty(Class clz) ;
	
	
	
	
	/**
	 * 获取所有数据库表
	 * @return
	 */
	public Integer getAllDbTableSize();
	
	
	
	/**
	 * 根据传入的实体持久化对象
	 */
	public <T> Serializable save(T entity);
	

	/**
	 * 批量保存数据
	 * 
	 * @param <T>
	 * @param entitys
	 *  要持久化的临时实体对象集合
	 */
	public <T> void batchSave(List<T> entitys);

	/**
	 * 批量更新数据
	 * @param entitys
	 * @param <T>
     */
	public <T> void  batchUpdate(List<T> entitys);

	
	/**
	 * 根据传入的实体添加或更新对象
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 */
	public <T> void saveOrUpdate(T entity);
	
	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo);
	

	public <T> void updateEntitie(String className, Object object) ;

	public <T> void updateEntityById(Class entityName, Serializable id);
	
	

	/**
	 *  删除实体
	 * @param entitie
	 */
	public <T> void delete(T entitie);
	
	/**
	 * 删除实体
	 * @param entityName
	 * @param id
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id);

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 慎用，该方法是在新的Session来查询
	 * 用于日志处理
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T loadOne(Class<T> entityClass, final Serializable id);
	

	/**
	 * 根据Id获取对象。
	 */
	public <T> T findOne(Class<T> entityClass, final Serializable id) ;
	
	/**
	 * 根据主键获取实体并加锁。
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T findOneEntity(Class entityName, Serializable id) ;
	
	/**
	 * 根据实体名字获取唯一记录
	 *
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Deprecated
	public <T> T findOneEntity(Class<T> entityClass, String propertyName, Object value) ;

	/**
	 * 通过hql查询唯一对象
	 *
	 * @param <T>
	 * @param hql
	 * @return
	 */
	public <T> T findOneEntityByHql(String hql) ;

	public <T> T findOneEntityByHql(String hql,Object ... objs) ;
	
	


	/**
	 * 通过hql 查询语句查找HashMap对象
	 *
	 * @param hql
	 * @return
	 */
	public Map<Object, Object> findOneMapByHql(String hql) ;

	public Map<Object, Object> findOneMap(String hql, Object ... objs) ;
	
	/**
	 * 通过hql查询唯一对象
	 * 
	 * @param sql
	 * @param clz
	 * @return
	 */
	public <T> T findOneEntityBySql(String sql, Class<T> clz) ;

	public <T> T findOneEntityBySql(String sql,Class<T> clz,Object ... objs) ;

	public Map<String, Object> findOneMapBySql(String sql) ;

	public Map<String, Object> findOneMapBySql(String sql, Object... objs) ;
	
	
	
	
	
	/**
	 * 通过sql更新记录
	 *
	 * @param sql
	 * @return
	 */
	public int executeBySql(final String sql) ;
	
	public Integer executeBySql(String sql, List<Object> param) ;

	public Integer executeBySql(String sql, Object... param) ;

	public Integer executeBySql(String sql, Map<String, Object> param);

	public Object executeSqlReturnKey(final String sql, Map<String, Object> param);


	/**
	 * 根据hql更新
	 * 
	 * @param hql
	 * @return
	 */
	public int executeByHql(String hql);

	public Integer executeByHql(String hql,Object ... objs) ;
	


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
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object value, boolean isAsc) ;
	

	/**
	 * 根据实体模版查找
	 * 
	 * @param entityName
	 * @param exampleEntity
	 * @return
	 */
	public List findList(final String entityName, final Object exampleEntity);
	


	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findList(Class<T> entityClass, String propertyName, Object value) ;
	

	
	/**
	 * 根据CriteriaQuery获取List
	 * 
	 * @param cq
	 * @param ispage
	 * @return
	 */
	public <T> List<T> findList(final CriteriaQuery cq, Boolean ispage) ;


	public <T> List<T> findAllList(final Class<T> entityClass) ;
	

	/**
	 * 离线查询
	 */
	public <T> List<T> findListByDetached(DetachedCriteria dc) ;

	public <T> List<T> findList(DetachedCriteria dc, int firstResult, int maxResult) ;

	/**
	 * 分页查询
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) ;

	
	/**
	 * 返回easyui datagrid DataGridReturn模型对象
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq, final boolean isOffset) ;
	


	/**
	 * 返回JQUERY datatables DataTableReturn模型对象
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);
	
	
	
	
	
	
	
	
	


	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param hql
	 * @return
	 */
	public <T> List<T> findListByHql(final String hql);
	
	

	/**
	 * HQL查询
	 * @param hql
	 * @param objs
	 * @return
	 */
	public <T> List<T> findListByHql(final String hql, final Object ... objs) ;
	
	

	/**
	 * 获取分页记录HqlQuery
	 * 
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	public List getPageListByHql(final HqlQuery hqlQuery, final boolean needParameter);
	

	/**
	 * 获取分页记录HqlQuery
	 * 
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	public void getPageByHql(final HqlQuery hqlQuery, final boolean needParameter);
	
	
	
	
	

	
	
	/**
	 * 通过sql查询语句查找对象
	 * 
	 * @param <T>
	 * @param sql
	 * @return
	 */
	public <T> List<T> findListBySql(final String sql);
	
	public <T> List<T> findListBySql(String sql, Class<T> clazz);

	public <T> List<T> findListBySql(String sql, Class<T> clazz, Object... objs);

	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T> List<T> findListBySql(String sql, int page, int rows, Class<T> clazz) ;

	public <T> List<T> findListBySql(String sql, int page, int rows, Class<T> clazz,Object... objs);

	public List<Map<String, Object>> findListBySql(String sql, Object... objs);

	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	public List<Map<String, Object>> findListBySql(String sql, int page, int rows);

	public List<Map<String, Object>> findListBySql(String sql, int page, int rows, Object... objs);
	


	/**
	 * 获取分页记录SqlQuery
	 * 
	 * @param sqlQuery
	 * @param isToEntity
	 * @return
	 */
	public PageList getPageListBySql(final SqlQuery sqlQuery, final boolean isToEntity);
	
	
	
	
	
	
	
	
	
	
	

	public Long getLongByHql(String hql);


	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	public Long getLongByHql(String hql, Object ... objs);
	

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	public Long getLongBySql(String sql) ;
	

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	public Long getLongBySql(String sql, Object ... objs);

	// 使用指定的检索标准获取满足标准的记录数
	public int getInteger(DetachedCriteria criteria);
	

	public Integer getIntegerBySql(String sql);

	public Integer getIntegerBySql(String sql, Object... param);
	


	/**
	 * 查询指定实体的总记录数
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> int getIntegerByHql(Class<T> clazz);
	
	
	
	

	public int getIntegerByHql(String hql) ;
	

	public int getIntegerByHql(String hql, Object ... objs) ;
	
	
	
	
	
	

	public AjaxJson execProc(String procName, final Object... params);
	
	

	public <T> List<T> findListByProc(String procName, String... params);
	
	
	
	
	
	
	
}
