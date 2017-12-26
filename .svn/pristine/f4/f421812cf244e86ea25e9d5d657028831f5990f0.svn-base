package com.bocom.dto.api.enncloud.app;

import java.util.List;

/**
 * "name":{"type":"string"},"namespace":{"type":"string","enum":["jingwuyun-test","jingwuyun"]},"java":{"type":"object","title":"javaApp","properties":{"jdk":{"type":"object","title":"jdk","properties":{"version":{"type":"string","description":"jdk version","enum":["7u79","8u112"]},"configParam":{"type":"string","description":"jdk configuration"}}},"server":{"type":"object","title":"server","properties":{"tomcat":{"type":"object","title":"tomcat","properties":{"version":{"type":"string","description":"server version","enum":["7.0.69","8.0.39"]},"config":{"type":"string","description":"server configuration"}}}}}}},"database":{"type":"object","title":"database","properties":{"mysql":{"type":"object","title":"mysql","properties":{"version":{"type":"string","description":"database version","enum":["5.6.35","5.7.17"]},"storage":{"type":"string","description":"database storage"},"rootPassword":{"type":"string","description":"password of root","default":"root"},"initScript":{"type":"string","format":"url","description":"database initialization script accessable url"},"availability":{"type":"string","description":"whether need high availability","default":"normal","enum":["normal","high"]}}}}},"assets":{"type":"array","items":{"type":"object","title":"assets","properties":{"type":{"type":"string","description":"assets type","default":"war","enum":["war"]},"file":{"type":"string","format":"url","description":"assets file accessable url"}}}},"resource":{"type":"string","description":"low is 0.5 core 1G memory 5G disk\nmedium is 1 core 2G memory 10G  disk\nhigh is 2 core 4G memory 20G disk\n","default":"medium","enum":["low","medium","high"]}
 * @author Administrator
 *
 */
public class AppDto {
	
	/**
	 *应用名称 
	 */
	private String name;
	
	/**
	 *应用命名空间 
	 */
	private String namespace;
	
	/**
	 *java 
	 */
	private AppJavaDto java;
	
	/**
	 *database 
	 */
	private DatabaseDto database;
	
	/**
	 * assets
	 */
	private List<AssetsDto> assets;
	
	/**
	 *low is 0.5 core 1G memory 5G disk\nmedium is 1 core 2G memory 10G  disk\nhigh is 2 core 4G memory 20G disk\n 
	 */
	private String resource/* = ResourceEnum.LOW.getProperty()*/;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public AppJavaDto getJava() {
		return java;
	}

	public void setJava(AppJavaDto java) {
		this.java = java;
	}

	public DatabaseDto getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseDto database) {
		this.database = database;
	}

	public List<AssetsDto> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetsDto> assets) {
		this.assets = assets;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}	
	
}
