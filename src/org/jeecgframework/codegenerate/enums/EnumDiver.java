package org.jeecgframework.codegenerate.enums;

public enum EnumDiver {
	
	mysql("com.mysql.jdbc.Driver"),
	oracle("oracle.jdbc.driver.OracleDriver"),
	db2("com.ibm.db2.jcc.DB2Driver"),
	sqlserver("com.microsoft.jdbc.sqlserver.SQLServerDriver"),
	postgre("org.postgresql.Driver");
	
	
	String driver;

	private EnumDiver(String driver) {
		this.driver = driver;
	}
	
	
	
	public static String  getDriver(String type){
		String dbtype = type.toLowerCase();
		for(EnumDiver enumDiver : EnumDiver.values()){
			if(enumDiver.name() .equals(dbtype)){
				return enumDiver.driver;
			}
		}
		return null;
	}
	

}
