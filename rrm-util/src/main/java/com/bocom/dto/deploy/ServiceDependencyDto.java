package com.bocom.dto.deploy;

public class ServiceDependencyDto {
	
	/**
	 * 
	 */
	private String serviceId;
	
	/**
	 * 
	 */
	private Boolean need;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Boolean getNeed() {
		return need;
	}

	public void setNeed(Boolean need) {
		this.need = need;
	}

}
