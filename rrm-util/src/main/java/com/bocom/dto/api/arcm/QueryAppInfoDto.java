package com.bocom.dto.api.arcm;


/**
 * Created by jinsiwei on 2016/12/9.
 */
public class QueryAppInfoDto {

    private String id;

    private String appId;

    private String appName;

    private String appDesc;

    private String logoWeb;

    private String logoApp;

    private String version;

    private String storeLocation;

    private String appOrg;

    private String appOrgName;

    private String uploadBy;

    private String uploadByName;

    private String deployNodeNum;

    private String appCategory;

    /**
     * @Description 新增pap不同角色站点导航显示不同，业务分类
     */
    private String bizCategory;

    private int appType;

    private String appIntegrateType;

    private String aaType;

    private String url;

    private String runStatus;

    private String status;

    private String createBy;

    private String createTime;
    
//    private String companyId;
//    private String companyCode;
//    private String companyName;
//
//    private String releaseBy;
//    private Integer releaseTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getLogoWeb() {
        return logoWeb;
    }

    public void setLogoWeb(String logoWeb) {
        this.logoWeb = logoWeb;
    }

    public String getLogoApp() {
        return logoApp;
    }

    public void setLogoApp(String logoApp) {
        this.logoApp = logoApp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getAppOrg() {
        return appOrg;
    }

    public void setAppOrg(String appOrg) {
        this.appOrg = appOrg;
    }

    public String getAppOrgName() {
        return appOrgName;
    }

    public void setAppOrgName(String appOrgName) {
        this.appOrgName = appOrgName;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public String getUploadByName() {
        return uploadByName;
    }

    public void setUploadByName(String uploadByName) {
        this.uploadByName = uploadByName;
    }

    public String getDeployNodeNum() {
        return deployNodeNum;
    }

    public void setDeployNodeNum(String deployNodeNum) {
        this.deployNodeNum = deployNodeNum;
    }

    public String getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }

    public String getAaType() {
        return aaType;
    }

    public void setAaType(String aaType) {
        this.aaType = aaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppIntegrateType() {
        return appIntegrateType;
    }

    public void setAppIntegrateType(String appIntegrateType) {
        this.appIntegrateType = appIntegrateType;
    }

    public String getBizCategory() {
        return bizCategory;
    }

    public void setBizCategory(String bizCategory) {
        this.bizCategory = bizCategory;
    }
}
