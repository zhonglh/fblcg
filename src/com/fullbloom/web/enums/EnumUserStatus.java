package com.fullbloom.web.enums;

public enum EnumUserStatus {

	LOCK("0","禁用"),
	UNLOCK("1","正常"),
	ADMIN("2","管理员");
	
	String code;
	String name;
	private EnumUserStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
