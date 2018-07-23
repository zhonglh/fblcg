package com.fullbloom.framework.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fullbloom.framework.core.AjaxJson;
import com.fullbloom.framework.core.dbutil.PageList;
import com.fullbloom.framework.core.dbutil.PagerUtil;
import com.fullbloom.framework.core.dbutil.ProcUtil;
import com.fullbloom.framework.core.dbutil.SqlQuery;
import com.fullbloom.framework.dao.jdbc.JdbcDao;
import com.fullbloom.framework.util.StringUtil;


/**
 * SQL处理
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
@Repository
public class CommonSqlDao<T, PK extends Serializable>  extends SupportDao {
	
	
	/**
	 * 通过sql更新记录
	 * @param sql
	 * @return 更新记录数
	 */
	public int update(final String sql) {
		logger.info("sql="+sql);
		Query querys = getSession().createSQLQuery(sql);
		return querys.executeUpdate();
	}
	
	
	public Integer update(String sql, List<Object> param) {
		logger.info("sql="+sql);
		return this.jdbcTemplate.update(sql, param);
	}
	
	
	public Integer execute(String sql) {
		logger.info("sql="+sql);
		return this.jdbcTemplate.update(sql);
	}
	
	public Integer execute(String sql, Object... param) {
		logger.info("sql="+sql);
		return this.jdbcTemplate.update(sql, param);
	}

	public Integer execute(String sql, Map<String, Object> param) {
		logger.info("sql="+sql);
		return this.namedParameterJdbcTemplate.update(sql, param);
	}

	public Object executeSqlReturnKey(final String sql, Map<String, Object> param) {
		logger.info("sql="+sql);
		Object keyValue = null;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource sqlp = new MapSqlParameterSource(param);
		this.namedParameterJdbcTemplate.update(sql, sqlp, keyHolder);
		if (StringUtil.isNotEmpty(keyHolder.getKey())) {
			keyValue = keyHolder.getKey().longValue();
		}
		return keyValue;
	}

	
	
	
	
	
	
	
	
	/**
	 * 执行存储过程
	 * @param procName
	 * @param params
	 */
	public AjaxJson execProc(String procName, final Object... params) {

		String procedure = ProcUtil.computeProcSql(procName, params);
		
		
		final String  procSQL = procedure;
		
		logger.info(procSQL);
		
		final AjaxJson json = new AjaxJson();
	
		
		this.getSession().doWork(new Work(){

			@Override
			public void execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				try{
					cs = conn.prepareCall("{ " + procSQL + "}");
					cs.execute(); 
					json.setSuccess(true);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
					json.setSuccess(false);
					json.setMsg(e.getMessage());
				}finally{
					try{
						if(cs!=null) cs.close();
					}catch(Exception e){
						logger.error(e);
					}
				}
			
			}
			
		});
		
		return json;
		
		
	}


	
	/**
	 * 执行查询类存储过程
	 * @param procName
	 * @param params
	 * @return
	 */
	public <T> List<T> findListByProc(String procName, String... params) {
		String procedure = "{call " + procName + "(";
		for (Object obj : params) {
			procedure = procedure + "?,";
		}
		procedure = procedure.substring(0, procedure.length() - 1);

		procedure = procedure + ")}";


		logger.info("sql="+procedure);
		
		Query querys = getSession().createSQLQuery(procedure);

		int i = 0;
		for (String obj : params) {
			querys.setString(i, (String) params[i]);
			i++;
		}

		return querys.list();
	}

	

	
	
	
	/**
	 * 通过sql查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public List<T> findList(final String sql) {
		logger.info("sql="+sql);
		Query querys = getSession().createSQLQuery(sql);
		return querys.list();
	}
	
	
	public <T> List<T> findList(String sql, Class<T> clazz){
		logger.info("sql="+sql);
		BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public List<Map<String, Object>> findList(String sql, Object... objs) {
		logger.info("sql="+sql);
		return this.jdbcTemplate.queryForList(sql, objs);
	}
	

	public <T> List<T> findList(String sql, Class<T> clazz, Object... objs){
		logger.info("sql="+sql);
		BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		return jdbcTemplate.query(sql, rowMapper, objs);
	}

	
	
	
	/**
	 * 分页查询数据
	 * @param sql
	 * @param page
	 * @param rows
	 * @return List
	 */
	public List<Map<String, Object>> findList(String sql, int page, int rows) {

		logger.info("sql="+sql);
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql);
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T> List<T> findList(String sql, int page, int rows, Class<T> clazz) {

		logger.info("sql="+sql);
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public <T> List<T> findList(String sql, int page, int rows, Class<T> clazz,Object... objs){
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		logger.info("sql="+sql);
		BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		return jdbcTemplate.query(sql, rowMapper, objs);
	}	
	

	public List<Map<String, Object>> findList(String sql, int page, int rows, Object... objs) {
		logger.info("sql="+sql);
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql, objs);
	}
	
	

	
	public <T> T findOne(String sql, Class<T> clz) {
		try {		
			logger.info("sql="+sql);
			BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clz);
			return jdbcTemplate.queryForObject(sql, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}	

	
	public <T> T findOne(String sql, Class<T> clz , Object ... objs) {
		try {		
			logger.info("sql="+sql);
			BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clz);
			return jdbcTemplate.queryForObject(sql, rowMapper,objs);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}	
	
	
	
	public Map<String, Object> findOneMap(String sql) {
		try {
			logger.info("sql="+sql);
			return this.jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}	
	
	public Map<String, Object> findOneMap(String sql, Object... objs) {
		try {
			logger.info("sql="+sql);
			return this.jdbcTemplate.queryForMap(sql, objs);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public Integer getInteger(String sql) {
		return this.jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 查询整数
	 * @param sql
	 * @param param
	 * @return
	 */
	public Integer getInteger(String sql, Object... param) {
		return this.jdbcTemplate.queryForInt(sql, param);
	}

	
	/**
	 * 查询记录数
	 * @param sql
	 * @return
	 */
	public Long getLong(String sql) {
		return this.jdbcTemplate.queryForLong(sql);
	}

	/**
	 * 查询记录数
	 * @param sql
	 * @param objs
	 * @return
	 */
	public Long getLong(String sql, Object ... objs) {
		return this.jdbcTemplate.queryForLong(sql, objs);
	}



	
	
	

	

	/**
	 * 获取分页记录SqlQuery
	 * 
	 * @param sqlQuery
	 * @param isToEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList getPageList(final SqlQuery sqlQuery, final boolean isToEntity) {		
		

		logger.info("sql="+sqlQuery.getQueryString());

		String sizeSql = "select count(*) from ("+sqlQuery.getQueryString()+")";
		
		int allCounts = 0;
		if(sqlQuery.getParam() == null || sqlQuery.getParam().length ==0){
			allCounts = this.getLong(sizeSql).intValue();
		}else {
			allCounts = this.getLong(sizeSql,sqlQuery.getParam()).intValue();
		}

		

		int curPageNO = sqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, sqlQuery.getPageSize());

		List<Map<String, Object>> list = null;

		if (!isToEntity || sqlQuery.getClass1() == null) {
			//类型为MAP
			if (sqlQuery.getParam() == null || sqlQuery.getParam().length == 0) {
				list = this.findList(sqlQuery.getQueryString(), sqlQuery.getCurPage(), sqlQuery.getPageSize());
			} else {
				list = this.findList(sqlQuery.getQueryString(), sqlQuery.getCurPage(), sqlQuery.getPageSize(), sqlQuery.getParam());
			}
		}else {
			//转换为对象
			if (sqlQuery.getParam() == null || sqlQuery.getParam().length == 0) {
				list = this.findList(sqlQuery.getQueryString(), sqlQuery.getCurPage(), sqlQuery.getPageSize(),sqlQuery.getClass1());
			} else {
				list = this.findList(sqlQuery.getQueryString(), sqlQuery.getCurPage(), sqlQuery.getPageSize(),sqlQuery.getClass1(), sqlQuery.getParam());
			}
		}
		return new PageList(sqlQuery, list, offset, curPageNO, allCounts);
	}
	
	
	



}
