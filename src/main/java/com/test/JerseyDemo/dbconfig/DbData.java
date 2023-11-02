package com.test.JerseyDemo.dbconfig;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="db-data")
public class DbData {
	
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private String dbDriver;
	private String dbFactory;
	
	
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPass() {
		return dbPass;
	}
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	public String getDbDriver() {
		return dbDriver;
	}
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	public String getDbFactory() {
		return dbFactory;
	}
	public void setDbFactory(String dbFactory) {
		this.dbFactory = dbFactory;
	}
	

	

}
