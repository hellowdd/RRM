package com.bocom.domain;

public class TcNavigationTemp {
	//主键
    private Integer id;

    //导航模板名称
    private String navigationTempName;

    //导航模板描述
    private String navigationTempDesc;

    //创建人ID
    private String createrId;

    //创建人名称
    private String createrName;

    //创建人部门Id
    private String officeId;

    //创建人部门名称
    private String officeName;

    //标志位
    private Boolean delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNavigationTempName() {
        return navigationTempName;
    }

    public void setNavigationTempName(String navigationTempName) {
        this.navigationTempName = navigationTempName;
    }

    public String getNavigationTempDesc() {
        return navigationTempDesc;
    }

    public void setNavigationTempDesc(String navigationTempDesc) {
        this.navigationTempDesc = navigationTempDesc;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}