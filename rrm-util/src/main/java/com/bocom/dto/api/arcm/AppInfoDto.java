package com.bocom.dto.api.arcm;

import java.util.List;

public class AppInfoDto {
	/**
	 * 应用信息
	 */
	private AppInfo appInfo;
	/**
	 * 应用界面信息
	 */
	private List<AppPage> appPages;
	/**
	 * 应用角色信息
	 */
	private List<AppRole> appRoles;
	/**
	 * 应用服务信息
	 */
	private List<AppService> appServices;
	/**
	 * 应用资源信息
	 */
	private List<AppResource> appResources;
	/**
	 * 应用部署信息
	 */
	private List<AppDeploy> appDeployments;
	

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	public List<AppPage> getAppPages() {
		return appPages;
	}

	public void setAppPages(List<AppPage> appPages) {
		this.appPages = appPages;
	}

	public List<AppRole> getAppRoles() {
		return appRoles;
	}

	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	public List<AppService> getAppServices() {
		return appServices;
	}

	public void setAppServices(List<AppService> appServices) {
		this.appServices = appServices;
	}

	public List<AppResource> getAppResources() {
		return appResources;
	}

	public void setAppResources(List<AppResource> appResources) {
		this.appResources = appResources;
	}

	public List<AppDeploy> getAppDeployments() {
		return appDeployments;
	}

	public void setAppDeployments(List<AppDeploy> appDeployments) {
		this.appDeployments = appDeployments;
	}

	
	
}
