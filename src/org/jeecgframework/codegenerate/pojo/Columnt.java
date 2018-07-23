//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.pojo;

/**
 * 数据库对应的列
 * @author zhonglihong
 * @date 2016年11月12日 上午9:23:45
 */
public class Columnt {
    
    
    //数据库列名称, 小写
    private String fieldName;
    
    //列说明
    private String filedComment = "";
    
    
    //数据库类型
    private String dbType = "";
    
    
    //字符串最大长度
    private String charmaxLength = "";
    
    //数字最大长度
    private String precision;
    
    //小数点位置
    private String scale;
    
    //是否可以为空
    private String nullable;

    public Columnt() {
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getPrecision() {
        return this.precision;
    }

    public String getScale() {
        return this.scale;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

   
    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFiledComment() {
        return this.filedComment;
    }

    public void setFiledComment(String filedComment) {
        this.filedComment = filedComment;
    }


    public String getCharmaxLength() {
        return this.charmaxLength != null && !"0".equals(this.charmaxLength)?this.charmaxLength:"";
    }

    public void setCharmaxLength(String charmaxLength) {
        this.charmaxLength = charmaxLength;
    }



	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}
