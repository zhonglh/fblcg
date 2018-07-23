package com.fullbloom.framework.core.base;

import java.util.List;

import com.fullbloom.framework.core.dbutil.DataGrid;
import com.fullbloom.web.entity.IdEntity;

public interface IBaseService<T extends IdEntity> extends java.io.Serializable {
	
	public void datagrid(T t,Object otherSearch, Object user, DataGrid dataGrid);
	
	public List<T> getList(T t,Object otherSearch, Object user);
	
	public boolean isExist(T t);	
	
	public T getEntityById(T t);
	
	

	public void saveEntity(T t);
	
	
	public void updateEntity(T t);
	

	public void deleteEntity(T t);
	

	public void saveEntity(List<T> ts);
	
	
	public void updateEntity(List<T> ts);
	

	public void deleteEntity(List<T> ts);
	

}
