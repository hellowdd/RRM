/**  
 * Project Name:bsp-util  
 * File Name:QueryCategory.java  
 * Package Name:com.bocom.dto.api.arcm  
 * Date:2017年4月17日下午1:10:23  
 * Copyright (c) 2017, Mr-Wei@bocom.com All Rights Reserved.  
 *  
 */

package com.bocom.dto.api.arcm;

/**
 * ClassName:查询应用类别信息<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年4月17日 下午1:10:23 <br/>
 * 
 * @author Mr-Wei
 * @version
 * @since JDK 1.7
 * @see
 */
public class QueryCategory {
	private String id;
	private String name;
	private String aliasName;
	private String category;
	
	public String getId() {
	
		return id;
	}
	public void setId(String id) {
	
		this.id = id;
	}
	public String getName() {
	
		return name;
	}
	public void setName(String name) {
	
		this.name = name;
	}
	public String getAliasName() {
	
		return aliasName;
	}
	public void setAliasName(String aliasName) {
	
		this.aliasName = aliasName;
	}
	public String getCategory() {
	
		return category;
	}
	public void setCategory(String category) {
	
		this.category = category;
	}
}
