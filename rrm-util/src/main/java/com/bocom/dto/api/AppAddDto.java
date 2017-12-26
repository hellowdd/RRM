package com.bocom.dto.api;


public class AppAddDto {

	private String appId;// 应用ID

	private Integer priority;// 排序优先级

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
