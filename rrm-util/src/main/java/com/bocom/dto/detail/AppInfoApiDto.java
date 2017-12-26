package com.bocom.dto.detail;

import java.util.Date;


public class AppInfoApiDto {
	
	private String id;// 主键
	
	private String appId;// 应用ID
	
	private String appName;// 应用名称
	
	private String appDesc;// 应用描述
	
	private String applyUser;// 申请人
	
	private String applyUserName;// 申请人名称
	
	private String applyOrg;// 申请人所属部门
	
	private String applyOrgName;// 申请人所属部门名称
	
	private Date applyTime;// 创建时间
	
	private Integer status;// 区分appid是否被使用

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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyOrg() {
		return applyOrg;
	}

	public void setApplyOrg(String applyOrg) {
		this.applyOrg = applyOrg;
	}

	public String getApplyOrgName() {
		return applyOrgName;
	}

	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
