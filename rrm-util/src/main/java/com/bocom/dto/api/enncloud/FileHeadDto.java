package com.bocom.dto.api.enncloud;

/**
 * @author Administrator
 *{"message":"Existing","date":"Thu, 12 Jan 2017 03:14:05 GMT","appnamespace":"jingwuyun-test","appname":"testapp","filename":"sample.war","status":0,"url":"http://10.19.132.184:30150/owncloud/kube-deploy/apps/jingwuyun-test/testapp/sample.war"}
 */
public class FileHeadDto {
	
	/**
	 *应用名称 
	 */
	private String Appname;
	
	/**
	 *应用命名空间 
	 */
	private String Appnamespace;
	
	/**
	 *文件名称 
	 */
	private String Filename;
	
	/**
	 *信息 
	 */
	private String Message;
	
	/**
	 *状态 
	 */
	private Integer Status;
	
	/**
	 *url 
	 */
	private String Url;
	
	/**
	 *日期 
	 */
	private String Date;

	public String getAppname() {
		return Appname;
	}

	public void setAppname(String appname) {
		Appname = appname;
	}

	public String getAppnamespace() {
		return Appnamespace;
	}

	public void setAppnamespace(String appnamespace) {
		Appnamespace = appnamespace;
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		Filename = filename;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
