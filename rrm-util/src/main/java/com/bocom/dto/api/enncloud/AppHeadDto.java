package com.bocom.dto.api.enncloud;

/**
 * @author Administrator
 *{"name":"bsp","message":"Deploy Failed: Retrive task status timeout","date":"Thu, 12 Jan 2017 03:13:15 GMT","progress":100,"url":"","namespace":"jingwuyun-test","status":-1}
 */
public class AppHeadDto {
	
	/**
	 *应用名称 
	 */
	private String Name;
	
	/**
	 *应用命名空间 
	 */
	private String Namespace;
	
	/**
	 *进度 
	 */
	private Integer Progress;
	
	/**
	 *状态  0 成功  -1不成功 
	 */
	private Integer Status;
	
	/**
	 *服务url 
	 */
	private String Appurl;
	
	/**
	 * 数据库地址
	 */
	private String Databaseurl; 
	
	/**
	 *日期 
	 */
	private String Date;
	
	/**
	 *信息 
	 */
	private String Message;

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

	
	public String getAppurl() {
		return Appurl;
	}

	public void setAppurl(String appurl) {
		Appurl = appurl;
	}

	public String getDatabaseurl() {
		return Databaseurl;
	}

	public void setDatabaseurl(String databaseurl) {
		Databaseurl = databaseurl;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNamespace() {
		return Namespace;
	}

	public void setNamespace(String namespace) {
		Namespace = namespace;
	}

	public Integer getProgress() {
		return Progress;
	}

	public void setProgress(Integer progress) {
		Progress = progress;
	}

}
