package com.bocom.dto.list;

import java.util.Date;

public class UploadRecordDto {
	
	/**
	 * 应用标识
	 */
	private String appIdentify;
	
	/**
	 * 应用说明
	 */
	private String systemExplain;
	
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	
	/**
	 * 上传说明
	 */
	private String uploadExplain;
	
	/**
	 * 上传人
	 */
	private String uploadPeoName;
	
	/**
	 * 上传结果
	 */
	private String uploadResult;
	
	/**
	 * 状态
	 */
	private String statusName;

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

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadExplain() {
		return uploadExplain;
	}

	public void setUploadExplain(String uploadExplain) {
		this.uploadExplain = uploadExplain;
	}

	public String getUploadPeoName() {
		return uploadPeoName;
	}

	public void setUploadPeoName(String uploadPeoName) {
		this.uploadPeoName = uploadPeoName;
	}

	public String getUploadResult() {
		return uploadResult;
	}

	public void setUploadResult(String uploadResult) {
		this.uploadResult = uploadResult;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}

