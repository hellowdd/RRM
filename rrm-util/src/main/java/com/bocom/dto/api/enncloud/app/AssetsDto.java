package com.bocom.dto.api.enncloud.app;



public class AssetsDto {
	
	/**
	 * assets type
	 */
	private String type /*= AssetsTypeEnum.WAR.getType()*/;
	
	/**
	 * assets file accessable url
	 */
	private String file;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
