package com.fullbloom.framework.core.dbutil;


/**
 * 数据库存储过程工具类
 * @author zhonglihong
 * @date 2016年3月11日 下午4:02:49
 */
public class ProcUtil {
	

	/**
	 * 根据存储过程名称和参数， 计算出对应的sql语句
	 * @param procName
	 * @param params
	 * @return
	 */
	public static String computeProcSql(String procName, final Object... params) {
		String procedure = "call " + procName + "(";
		
		if(params != null && params.length >0){
			
			for (Object obj : params) {
				if(obj instanceof Number){
					procedure = procedure + obj+" ,";
				}else {
					procedure = procedure + "'"+obj.toString()+"',";
				}
			}

			procedure = procedure.substring(0, procedure.length() - 1);
		}

		procedure = procedure + ")";
		return procedure;
	}
	

}
