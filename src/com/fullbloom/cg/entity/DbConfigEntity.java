package com.fullbloom.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fullbloom.web.entity.IdEntity;



@Entity
@Table(name = "cgform_db")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DbConfigEntity extends IdEntity implements java.io.Serializable{
	
	public String dbType;
	public String dbUrl;
	public String dbUsername;
	public String dbPassword;
	
	

	@Column(name = "DB_TYPE")
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	

	@Column(name = "DB_URL")
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	

	@Column(name = "DB_USERNAME")
	public String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	@Column(name = "DB_PASSWORD")
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	
	

}
