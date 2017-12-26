package com.bocom.domain;

import java.util.Date;

public class AppSwitch {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 源ID 外键appInfo表
	 */
	private String appSourceRefId;
	/**
	 * 目标ID 外键appInfo表
	 */
	private String appTargetRefId;
	/**
	 * 原ID 版本
	 */
	private String appSourceVersion;
	/**
	 *目标ID 版本
	 */
	private String appTargetVersion;
	/**
	 *应用ID
	 */
	private String appId;
	/**
	 *应用名称
	 */
	private String appName;
	/**
	 * 状态   1:切换中   2:切换成功    3:切换失败
	 */
	private Integer status;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建人部门
	 */
	private String createByOrg;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 完成时间
	 */
	private Date finishTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppSourceRefId() {
		return appSourceRefId;
	}

	public void setAppSourceRefId(String appSourceRefId) {
		this.appSourceRefId = appSourceRefId;
	}

	public String getAppTargetRefId() {
		return appTargetRefId;
	}

	public void setAppTargetRefId(String appTargetRefId) {
		this.appTargetRefId = appTargetRefId;
	}

	public String getAppSourceVersion() {
		return appSourceVersion;
	}

	public void setAppSourceVersion(String appSourceVersion) {
		this.appSourceVersion = appSourceVersion;
	}

	public String getAppTargetVersion() {
		return appTargetVersion;
	}

	public void setAppTargetVersion(String appTargetVersion) {
		this.appTargetVersion = appTargetVersion;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateByOrg() {
		return createByOrg;
	}

	public void setCreateByOrg(String createByOrg) {
		this.createByOrg = createByOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public AppSwitch() {
		super();
	}

	public AppSwitch(String appSourceRefId, String appTargetRefId,
			Integer status, String createBy, String createByOrg,
			Date createTime, Date finishTime) {
		super();
		this.appSourceRefId = appSourceRefId;
		this.appTargetRefId = appTargetRefId;
		this.status = status;
		this.createBy = createBy;
		this.createByOrg = createByOrg;
		this.createTime = createTime;
		this.finishTime = finishTime;
	}

}
