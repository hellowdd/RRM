package com.bocom.dto.api;

import java.util.List;

public class AppAddListDto {

	private List<AppAddDto> appIds;// 应用ID列表

	public List<AppAddDto> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<AppAddDto> appIds) {
		this.appIds = appIds;
	}

}
