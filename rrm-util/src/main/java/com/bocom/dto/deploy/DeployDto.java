package com.bocom.dto.deploy;

import java.util.List;

public class DeployDto {
	
	//数据信息
	private List<DbDto> db;
	
	//应用信息
	private AppDto app;
	
	private AppendInfo appendInfo;

	public AppendInfo getAppendInfo() {
	
		return appendInfo;
	}

	public void setAppendInfo(AppendInfo appendInfo) {
	
		this.appendInfo = appendInfo;
	}

	public List<DbDto> getDb() {
		return db;
	}

	public void setDb(List<DbDto> db) {
		this.db = db;
	}

	public AppDto getApp() {
		return app;
	}

	public void setApp(AppDto app) {
		this.app = app;
	}

	
	

}
