//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jeecgframework.codegenerate.enums.EnumDbType;
import org.jeecgframework.codegenerate.enums.EnumDiver;
import org.jeecgframework.codegenerate.enums.EnumJavaType;
import org.jeecgframework.codegenerate.pojo.Columnt;
import org.jeecgframework.codegenerate.pojo.DbConfig;
import org.jeecgframework.codegenerate.pojo.TableConvert;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;


public class JeecgReadTable {
	private static final Log log = LogFactory.getLog(JeecgReadTable.class);
	private static final long serialVersionUID = -5324160085184088010L;
	private Connection conn;
	private Statement stmt;
	private String sql;
	private ResultSet rs;

	public JeecgReadTable() {
	}

	public List<String> readAllTableNames(DbConfig dbContig) throws SQLException {
		List<String> tableNames = new ArrayList<String>();

		try {
			Class.forName(EnumDiver.getDriver(dbContig.getDbType()));
			this.conn = DriverManager.getConnection(dbContig.getDbUrl(), dbContig.getDbUsername(), dbContig.getDbPassword());
			this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			

			String databaseType = dbContig.getDbType().toLowerCase();
			if (databaseType.equals("mysql")) {

				this.sql = "select concat(TABLE_SCHEMA,'.',TABLE_NAME) table_name from information_schema.tables where table_schema not in ('information_schema','test' )";
			}

			if (databaseType.equals("oracle")) {
				this.sql = " select (owner || '.' || table_name) as  table_name from all_tables colstable where owner  like 'FBL%' ";
			}

			if (databaseType.equals("postgresql")) {
				this.sql = "SELECT distinct c.relname AS  table_name FROM pg_class c";
			}

			if (databaseType.equals("sqlserver")) {
				this.sql = "select distinct c.name as  table_name from sys.objects c ";
			}

			this.rs = this.stmt.executeQuery(this.sql);

			while (this.rs.next()) {
				String e = this.rs.getString(1);
				tableNames.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (this.stmt != null) {
					this.stmt.close();
					this.stmt = null;
					System.gc();
				}

				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
					System.gc();
				}
			} catch (SQLException var10) {
				throw var10;
			}

		}

		return tableNames;
	}

	public List<Columnt> readTableColumn(DbConfig dbContig,String st) throws Exception {
		List<Columnt> columntList = new ArrayList<Columnt>();

		Columnt ch = null;
		try {

			String[] sts = st.split("\\.");
			Class.forName(EnumDiver.getDriver(dbContig.getDbType()));
			this.conn = DriverManager.getConnection(dbContig.getDbUrl(), dbContig.getDbUsername(), dbContig.getDbPassword());

			this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String databaseType = dbContig.getDbType().toLowerCase();
			
			if (databaseType.equals("mysql")) {
				this.sql = 
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns "
								+ "where table_name = '"+sts[1]+"' and table_schema = '"+sts[0]+"'";
			}

			if (databaseType.equals("oracle")) {
				this.sql = 
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable "
						+ "from all_tab_cols colstable  inner join all_col_comments commentstable  "
						+ "on colstable.column_name = commentstable.column_name  "
						+ "and colstable.table_name = commentstable.table_name "
						+ "and colstable.owner = commentstable.owner "
						+ "where colstable.table_name = commentstable.table_name  "
						+ "and colstable.table_name = '"+sts[1]+"' and colstable.owner = '"+sts[0]+"'";
			}

			/**
			if (databaseType.equals("postgresql")) {
				this.sql = MessageFormat.format(
						"SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}

			if (databaseType.equals("sqlserver")) {
				this.sql = MessageFormat.format(
						"select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Precision\'\'\') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Scale\'\'\') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then \'\'\'y\'\'\' else \'\'\'n\'\'\' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type=\'\'\'U\'\'\' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}
			*/

			this.rs = this.stmt.executeQuery(this.sql);
			this.rs.last();
			int rsList = this.rs.getRow();
			if (rsList <= 0) {
				throw new Exception("该表不存在或者表中没有字段");
			}

			ch = new Columnt();
			
			ch.setFieldName(this.rs.getString(1).toLowerCase());
			

			
			
			ch.setPrecision(this.rs.getString(4));
			ch.setScale(this.rs.getString(5));
			ch.setCharmaxLength(this.rs.getString(6));
			ch.setNullable(TableConvert.getNullAble(this.rs.getString(7)));
			

			ch.setDbType(EnumDbType.getDbType(this.rs.getString(2).toLowerCase()).name());
			
			ch.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? ch.getFieldName() : this.rs.getString(3));
			String[] ui_filter_fields = new String[0];
			if (CodeResourceUtil.JEECG_GENERATE_UI_FILTER_FIELDS != null) {
				ui_filter_fields = CodeResourceUtil.JEECG_GENERATE_UI_FILTER_FIELDS.toLowerCase().split(",");
			}

			if (!CodeResourceUtil.JEECG_GENERATE_TABLE_ID.equals(ch.getFieldName())
					&& !CodeStringUtils.isIn(ch.getFieldName().toLowerCase(), ui_filter_fields)) {
				columntList.add(ch);
			}

			while (this.rs.previous()) {
				Columnt po = new Columnt();

					po.setFieldName(this.rs.getString(1).toLowerCase());
				

				if (!CodeResourceUtil.JEECG_GENERATE_TABLE_ID.equals(po.getFieldName())
						&& !CodeStringUtils.isIn(po.getFieldName().toLowerCase(), ui_filter_fields)) {
					

					po.setDbType(EnumDbType.getDbType(this.rs.getString(2).toLowerCase()).name());
					
					po.setPrecision(this.rs.getString(4));
					po.setScale(this.rs.getString(5));
					po.setCharmaxLength(this.rs.getString(6));
					po.setNullable(TableConvert.getNullAble(this.rs.getString(7)));
					po.setFiledComment(
							StringUtils.isBlank(this.rs.getString(3)) ? po.getFieldName() : this.rs.getString(3));
					columntList.add(po);
				}
			}
		} catch (ClassNotFoundException var16) {
			throw var16;
		} catch (SQLException var17) {
			throw var17;
		} finally {
			try {
				if (this.stmt != null) {
					this.stmt.close();
					this.stmt = null;
					System.gc();
				}

				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
					System.gc();
				}
			} catch (SQLException var15) {
				throw var15;
			}

		}

		ArrayList<Columnt> var19 = new ArrayList<Columnt>();

		for (int i = columntList.size() - 1; i >= 0; --i) {
			ch = (Columnt) columntList.get(i);
			var19.add(ch);
		}

		return var19;
	}

	public List<Columnt> readOriginalTableColumn(DbConfig dbContig,String st) throws Exception {
		List<Columnt> columntList = new ArrayList<Columnt>();

		Columnt ch = null;
		try {
			
			String[] sts = st.split("\\.");
			Class.forName(EnumDiver.getDriver(dbContig.getDbType()));
			this.conn = DriverManager.getConnection(dbContig.getDbUrl(), dbContig.getDbUsername(), dbContig.getDbPassword());

			this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String databaseType = dbContig.getDbType().toLowerCase();
			
			if (databaseType.equals("mysql")) {
				this.sql = 
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns "
						+ "where table_name = '"+sts[1]+"' and table_schema = '"+sts[0]+"'";
			}

			if (databaseType.equals("oracle")) {
				this.sql = 
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable "
						+ "from all_tab_cols colstable  inner join all_col_comments commentstable  "
						+ "on colstable.column_name = commentstable.column_name  "
						+ "and colstable.table_name = commentstable.table_name "
						+ "and colstable.owner = commentstable.owner "
						+ "where colstable.table_name = commentstable.table_name  "
						+ "and colstable.table_name = '"+sts[1]+"' and colstable.owner = '"+sts[0]+"'";
			}

			/**
			if (databaseType.equals("postgresql")) {
				this.sql = MessageFormat.format(
						"SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}

			if (databaseType.equals("sqlserver")) {
				this.sql = MessageFormat.format(
						"select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Precision\'\'\') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Scale\'\'\') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then \'\'\'y\'\'\' else \'\'\'n\'\'\' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type=\'\'\'U\'\'\' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}
			*/

			this.rs = this.stmt.executeQuery(this.sql);
			this.rs.last();
			int rsList = this.rs.getRow();
			if (rsList <= 0) {
				throw new Exception("该表不存在或者表中没有字段");
			}

			ch = new Columnt();
			
			ch.setFieldName(this.rs.getString(1).toLowerCase());
			

			

			ch.setPrecision(TableConvert.getNullString(this.rs.getString(4)));
			ch.setScale(TableConvert.getNullString(this.rs.getString(5)));			
			ch.setCharmaxLength(TableConvert.getNullString(this.rs.getString(6)));
			ch.setNullable(TableConvert.getNullAble(this.rs.getString(7)));
			
			
			ch.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? ch.getFieldName() : this.rs.getString(3));
			
			ch.setDbType(EnumDbType.getDbType(this.rs.getString(2).toLowerCase()).name());
			columntList.add(ch);

			while (this.rs.previous()) {
				Columnt po = new Columnt();

				po.setFieldName(this.rs.getString(1).toLowerCase());
				

				po.setPrecision(TableConvert.getNullString(this.rs.getString(4)));
				po.setScale(TableConvert.getNullString(this.rs.getString(5)));
				po.setCharmaxLength(TableConvert.getNullString(this.rs.getString(6)));
				po.setNullable(TableConvert.getNullAble(this.rs.getString(7)));

				po.setDbType(EnumDbType.getDbType(this.rs.getString(2).toLowerCase()).name());
				
				po.setFiledComment(
						StringUtils.isBlank(this.rs.getString(3)) ? po.getFieldName() : this.rs.getString(3));
				columntList.add(po);
			}
		} catch (ClassNotFoundException var15) {
			throw var15;
		} catch (SQLException var16) {
			throw var16;
		} finally {
			try {
				if (this.stmt != null) {
					this.stmt.close();
					this.stmt = null;
					System.gc();
				}

				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
					System.gc();
				}
			} catch (SQLException var14) {
				throw var14;
			}

		}

		List<Columnt> var18 = new ArrayList<Columnt>();

		for (int i = columntList.size() - 1; i >= 0; --i) {
			ch = (Columnt) columntList.get(i);
			var18.add(ch);
		}

		return var18;
	}

	/**
	public boolean checkTableExist(DbConfig dbContig,String tableName) {
		try {
			
			Class.forName(EnumDiver.getDriver(dbContig.getDbType()));
			this.conn = DriverManager.getConnection(dbContig.getDbUrl(), dbContig.getDbUsername(), dbContig.getDbPassword());

			this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String databaseType = dbContig.getDbType().toLowerCase();
			
			if (databaseType.equals("mysql")) {
				this.sql = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = \'"
						+ tableName.toUpperCase() + "\'" + " and table_schema = \'" + CodeResourceUtil.DATABASE_NAME
						+ "\'";
			}

			if (databaseType.equals("oracle")) {
				this.sql = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = \'"
						+ tableName.toUpperCase() + "\'";
			}

			if (databaseType.equals("postgresql")) {
				this.sql = MessageFormat.format(
						"SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}

			if (databaseType.equals("sqlserver")) {
				this.sql = MessageFormat.format(
						"select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Precision\'\'\') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,\'\'\'Scale\'\'\') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then \'\'\'y\'\'\' else \'\'\'n\'\'\' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type=\'\'\'U\'\'\' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}",
						new Object[] { TableConvert.getV(tableName.toLowerCase()) });
			}

			this.rs = this.stmt.executeQuery(this.sql);
			this.rs.last();
			int e = this.rs.getRow();
			return e > 0;
		} catch (Exception var3) {
			var3.printStackTrace();
			return false;
		}
	}
	*/


	private String formatDataType(String dataType, String precision, String scale) {		
		EnumJavaType jt =  EnumDbType.getJavaType(dataType, precision, scale);
		if(jt == null){
			throw new RuntimeException(dataType+"类型不兼容");
		}else {
			return jt.getFullname();
		}
		
		
	}
}
