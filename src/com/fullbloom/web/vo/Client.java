package com.fullbloom.web.vo;

import java.util.Date;
import java.util.List;

import com.fullbloom.web.entity.TsFunction;
import com.fullbloom.web.entity.TsUser;

public class Client {
	
	private TsUser tsUser;
	private List<TsFunction> functions;
	private String ip;
	private Date loginTime;
	public TsUser getTsUser() {
		return tsUser;
	}
	public void setTsUser(TsUser tsUser) {
		this.tsUser = tsUser;
	}
	public List<TsFunction> getFunctions() {
		return functions;
	}
	public void setFunctions(List<TsFunction> functions) {
		this.functions = functions;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	


	
}
