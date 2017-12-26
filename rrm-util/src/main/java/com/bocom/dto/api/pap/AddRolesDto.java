package com.bocom.dto.api.pap;

import java.util.List;

import com.bocom.dto.deploy.AppRoleDto;


public class AddRolesDto {
	
	private String id;
	
	private String appId;
	
	private String version;
	
	private List<AppRoleDto> appRole;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<AppRoleDto> getAppRole() {
		return appRole;
	}

	public void setAppRole(List<AppRoleDto> appRole) {
		this.appRole = appRole;
	}

}
