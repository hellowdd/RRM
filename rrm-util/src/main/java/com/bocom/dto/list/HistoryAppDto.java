package com.bocom.dto.list;

import java.util.Date;

public class HistoryAppDto {
	
	/**
	 * 应用标识
	 */
	private String appIdentify;
	
	/**
	 * 应用说明
	 */
	private String systemExplain;
	
	/**
	 * 版本
	 */
	private String systemVersion;
	
	
	/**
	 * 最后操作人名称
	 */
	private String deployPeoName;
	
	/**
	 * 更新时间
	 */
	private Date deployEndTime;

	public String getAppIdentify() {
		return appIdentify;
	}

	public void setAppIdentify(String appIdentify) {
		this.appIdentify = appIdentify;
	}

	public String getSystemExplain() {
		return systemExplain;
	}

	public void setSystemExplain(String systemExplain) {
		this.systemExplain = systemExplain;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getDeployPeoName() {
		return deployPeoName;
	}

	public void setDeployPeoName(String deployPeoName) {
		this.deployPeoName = deployPeoName;
	}

	public Date getDeployEndTime() {
		return deployEndTime;
	}

	public void setDeployEndTime(Date deployEndTime) {
		this.deployEndTime = deployEndTime;
	}

}
