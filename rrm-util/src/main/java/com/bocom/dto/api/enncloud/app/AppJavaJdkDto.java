package com.bocom.dto.api.enncloud.app;


public class AppJavaJdkDto {
	
	/**
	 *jdk version 
	 */
	private String version /*= JdkVersionEnum.V_7U79.getVersion()*/;
	
	/**
	 *jdk configuration 
	 */
	private String configParam;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getConfigParam() {
		return configParam;
	}

	public void setConfigParam(String configParam) {
		this.configParam = configParam;
	}
	
}
