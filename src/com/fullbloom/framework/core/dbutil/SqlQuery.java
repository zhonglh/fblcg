package com.fullbloom.framework.core.dbutil;

import java.util.Map;

import org.hibernate.type.Type;

public class SqlQuery extends HqlQuery {

	public SqlQuery(String sql, Object[] param, Map<String, Object> map) {
		super(sql,param,map);
	}

	public SqlQuery(String sql, Map<String, Object> map) {
		super(sql,map);
	}
	
	public SqlQuery(String myaction) {
		super(myaction);
	}



	public SqlQuery(String myaction, String sql, Object[] param, Type[] types) {

		super(myaction,sql,param,types);
	}
	public SqlQuery(String sql,DataGrid dataGrid) {
		super(sql,dataGrid);
	}

}
