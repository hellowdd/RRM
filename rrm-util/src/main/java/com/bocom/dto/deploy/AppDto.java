package com.bocom.dto.deploy;

import java.util.List;

public class AppDto {
	
	/**
	 * 
	 */
	private String container;
	
	/**
	 * 
	 */
	private String appId;
	
	/**
	 * 
	 */
	private String logoWeb;
	
	/**
	 * 
	 */
	private String logoApp;
	
	/**
	 * 
	 */
	private String initPath;
	
	/**
	 * 
	 */
	private String version;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String fileName;
	
	/**
	 * 
	 */
	private Integer deployNodeNum;
	
	/**
	 * 
	 */
	private String appCategory;
	
	/**
	 * 
	 */
	private String aaType;
	
	/**
	 * 
	 */
	private DependencyDto dependency;
	
	/**
	 * 
	 */
	private List<AppPageDto> appPages;
	
	/**
	 * 
	 */
	private List<AppRoleDto> appRoleList;
	
	private List<AppMsgDto> appMsgList;
	
	
	/**
	 * 
	 */
	private Integer sequence;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getLogoWeb() {
		return logoWeb;
	}

	public void setLogoWeb(String logoWeb) {
		this.logoWeb = logoWeb;
	}

	public String getLogoApp() {
		return logoApp;
	}

	public void setLogoApp(String logoApp) {
		this.logoApp = logoApp;
	}

	public String getInitPath() {
		return initPath;
	}

	public void setInitPath(String initPath) {
		this.initPath = initPath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getDeployNodeNum() {
		return deployNodeNum;
	}

	public void setDeployNodeNum(Integer deployNodeNum) {
		this.deployNodeNum = deployNodeNum;
	}

	public String getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}

	public String getAaType() {
		return aaType;
	}

	public void setAaType(String aaType) {
		this.aaType = aaType;
	}

	public DependencyDto getDependency() {
		return dependency;
	}

	public void setDependency(DependencyDto dependency) {
		this.dependency = dependency;
	}

	public List<AppPageDto> getAppPages() {
		return appPages;
	}

	public void setAppPages(List<AppPageDto> appPages) {
		this.appPages = appPages;
	}

	public List<AppRoleDto> getAppRoleList() {
		return appRoleList;
	}

	public void setAppRoleList(List<AppRoleDto> appRoleList) {
		this.appRoleList = appRoleList;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<AppMsgDto> getAppMsgList() {
		return appMsgList;
	}

	public void setAppMsgList(List<AppMsgDto> appMsgList) {
		this.appMsgList = appMsgList;
	}
	
	
	
}
