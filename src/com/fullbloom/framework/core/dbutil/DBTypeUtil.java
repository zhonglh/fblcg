package com.fullbloom.framework.core.dbutil;




import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.fullbloom.framework.util.ApplicationContextUtil;

/**
 * 
 * @author  张代浩
 *
 */
public class DBTypeUtil {
	private static Logger log = Logger.getLogger(DBTypeUtil.class);
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDBType(){
		String retStr="";
		ApplicationContext ctx = ApplicationContextUtil.getContext();
		if (ctx==null) {
			 return null;
		}else{
			org.springframework.orm.hibernate4.LocalSessionFactoryBean sf = (org.springframework.orm.hibernate4.LocalSessionFactoryBean)ctx.getBean("&sessionFactory");
			String dbdialect = sf.getHibernateProperties().getProperty("hibernate.dialect");
			log.debug(dbdialect);
			
			if (dbdialect.equals("org.hibernate.dialect.MySQLDialect")) {
				retStr="mysql";
			}else if (dbdialect.contains("Oracle")) {//oracle有多个版本的方言
				retStr = "oracle";
			}else if (dbdialect.equals("org.hibernate.dialect.SQLServerDialect")) {
				retStr = "sqlserver";
			}else if (dbdialect.equals("org.hibernate.dialect.PostgreSQLDialect")) {
				retStr = "postgres";
			}else if (dbdialect.equals("org.hibernate.dialect.DB2Dialect")) {
				retStr = "db2";
			}
			return retStr;
		}
	}
}

