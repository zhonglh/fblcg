package com.fullbloom.web.service;

import java.util.List;

import com.fullbloom.web.entity.TsFunction;
import com.fullbloom.web.entity.TsUser;

public interface ISystemService {
	

	public void addUser(TsUser user) throws Exception;
	

	public void updateUser(TsUser user) throws Exception;

	public List<TsUser> getUser(TsUser user) throws Exception;
	

	
	public TsUser checkUserExits(TsUser user) throws Exception;
	
	public List<TsFunction> getFunctions(TsUser user) throws Exception;
	
	
	public <T> List<T> getList(String hql, Object ... objs);
	

}
