package com.bocom.domain;

public class TrUserNavigation {
	//主键
    private Integer id;

	//用户ID
    private String userId;

	//用户名称
    private String userName;

	//所属部门Id
    private String officeId;

	//所属部门名称
    private String officeName;

	//导航模板ID
    private String navigationTempId;

	//标志位 
    private Boolean delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getNavigationTempId() {
        return navigationTempId;
    }

    public void setNavigationTempId(String navigationTempId) {
        this.navigationTempId = navigationTempId;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}