package com.bocom.dto.api.enncloud.app;


public class DatabaseMysqlDto {
	
	/**
	 *jdk version 
	 */
	private String version /*= MysqlVersionEnum.V_5_6_35.getVersion()*/;
	
	/**
	 *database storage
	 */
	private String storage;
	
	/**
	 *password of root
	 */
	private String rootPassword;
	
	/**
	 *database initialization script accessable url
	 */
	private String initScript;
	
	private String customConf;
	
	/**
	 *whether need high availability
	 */
	private String availability /*= MysqlAvailabilityEnum.NORMAL.getProperty()*/;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getRootPassword() {
		return rootPassword;
	}

	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}
	
	

	public String getCustomConf() {
	
		return customConf;
	}

	public void setCustomConf(String customConf) {
	
		this.customConf = customConf;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
}
