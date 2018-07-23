package com.fullbloom.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fullbloom.framework.service.impl.AbstractServiceImpl;
import com.fullbloom.framework.util.PasswordUtil;
import com.fullbloom.web.entity.TsFunction;
import com.fullbloom.web.entity.TsUser;
import com.fullbloom.web.enums.EnumUserStatus;
import com.fullbloom.web.service.ISystemService;

@Service
public class SystemServiceImpl extends AbstractServiceImpl implements ISystemService {
	
	

	@Override
	public void addUser(TsUser user) throws Exception{
		this.commonDao.save(user);
	}

	@Override
	public void updateUser(TsUser user) throws Exception{
		this.commonDao.updateEntitie(user);
	}

	@Override
	public List<TsUser> getUser(TsUser user) throws Exception{
		StringBuilder hql = new StringBuilder("from TsUser where 1=1 ");
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(user.getUserName())){
			hql = hql.append(" and userName = ? ");
			params.add(user.getUserName());
		}
		

		if(StringUtils.isNotEmpty(user.getName())){
			hql = hql.append(" and name = ? ");
			params.add(user.getName());
		}


		if(StringUtils.isNotEmpty(user.getIp())){
			hql = hql.append(" and ip = ? ");
			params.add(user.getIp());
		}
		
		return this.commonDao.findListByHql(hql.toString(), params.toArray());
		
	}

	@Override
	public TsUser checkUserExits(TsUser user) throws Exception {		

		String password = PasswordUtil.encrypt(user.getUserName(), user.getUserPassword(), PasswordUtil.getStaticSalt());
		String hql = "from TsUser u where u.userName =? and u.userPassword=? and u.status =? ";
		List<String> params = new ArrayList<String>();
		params.add(user.getUserName());
		params.add(password);
		params.add(EnumUserStatus.UNLOCK.getCode());
		
		List<TsUser> list = this.commonDao.findListByHql(hql, params.toArray());
		
		if(list == null || list.isEmpty()) return null;
		else return list.get(0);
		
	}

	@Override
	public List<TsFunction> getFunctions(TsUser user) throws Exception {
		String hql = "from TsFunction ";
		return this.commonDao.findListByHql(hql);
	}
	
	

	@Override
	public <T> List<T> getList(String hql, Object ... objs){
		return this.commonDao.findListByHql(hql,objs);
	}

}
