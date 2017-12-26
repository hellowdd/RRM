package com.bocom.dto.img;

public class ImageListDto {
	
	private String url;	// fastdfs下载的地址 
	
	private String name;	// 实际的名称（不含后缀）

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
