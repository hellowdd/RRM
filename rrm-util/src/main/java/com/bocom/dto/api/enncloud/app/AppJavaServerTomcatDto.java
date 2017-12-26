package com.bocom.dto.api.enncloud.app;



public class AppJavaServerTomcatDto {
	
	/**
	 *server version
	 */
	private String version /*= TomcatVersionEnum.V_7_0_69.getVersion()*/;
	
	/**
	 *server configuration
	 */
	private String config;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	
}
