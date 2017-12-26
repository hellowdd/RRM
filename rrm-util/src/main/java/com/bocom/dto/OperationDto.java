package com.bocom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OperationDto {
    private Integer id;

    private String operationPeople;

    private String operationPeopleName;

    private String operationPeopleOrgCode;

    private String operationPeopleOrgName;

    private Date operationTime;

    private String operationType;

    private Integer operationResource;

    private String resourceName;

    private String resourceType;

    private String resourceKey;

    private Date resourceDate;

    private Long resourceFileSize;

    private String resourcePlace;

    private String resolutionRatio;

    private String taskYear;

    private String taskName;

    private String resourceModule;

    private String storagePath;

    private String thumbnailPath;

    private String sourceIp;

    private String adminDivision;

    private String status;

    private String uploadPeopleName;

    private String uploadPeopleId;

    private Date uploadTime;

    private String delFlag;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperationPeopleOrgName() {
        return operationPeopleOrgName;
    }

    public void setOperationPeopleOrgName(String operationPeopleOrgName) {
        this.operationPeopleOrgName = operationPeopleOrgName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getResourceDate() {
        return resourceDate;
    }

    public void setResourceDate(Date resourceDate) {
        this.resourceDate = resourceDate;
    }

    public Long getResourceFileSize() {
        return resourceFileSize;
    }

    public void setResourceFileSize(Long resourceFileSize) {
        this.resourceFileSize = resourceFileSize;
    }

    public String getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(String resolutionRatio) {
        this.resolutionRatio = resolutionRatio;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getAdminDivision() {
        return adminDivision;
    }

    public void setAdminDivision(String adminDivision) {
        this.adminDivision = adminDivision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadPeopleName() {
        return uploadPeopleName;
    }

    public void setUploadPeopleName(String uploadPeopleName) {
        this.uploadPeopleName = uploadPeopleName;
    }

    public String getUploadPeopleId() {
        return uploadPeopleId;
    }

    public void setUploadPeopleId(String uploadPeopleId) {
        this.uploadPeopleId = uploadPeopleId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationPeople() {
        return operationPeople;
    }

    public void setOperationPeople(String operationPeople) {
        this.operationPeople = operationPeople;
    }

    public String getOperationPeopleName() {
        return operationPeopleName;
    }

    public void setOperationPeopleName(String operationPeopleName) {
        this.operationPeopleName = operationPeopleName;
    }

    public String getOperationPeopleOrgCode() {
        return operationPeopleOrgCode;
    }

    public void setOperationPeopleOrgCode(String operationPeopleOrgCode) {
        this.operationPeopleOrgCode = operationPeopleOrgCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getOperationResource() {
        return operationResource;
    }

    public void setOperationResource(Integer operationResource) {
        this.operationResource = operationResource;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourcePlace() {
        return resourcePlace;
    }

    public void setResourcePlace(String resourcePlace) {
        this.resourcePlace = resourcePlace;
    }

    public String getTaskYear() {
        return taskYear;
    }

    public void setTaskYear(String taskYear) {
        this.taskYear = taskYear;
    }

    public String getResourceModule() {
        return resourceModule;
    }

    public void setResourceModule(String resourceModule) {
        this.resourceModule = resourceModule;
    }
}
