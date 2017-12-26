package com.bocom.domain;

public class TdUserApplist {
	//主键
    private Integer id;

    //用户ID
    private String userId;

    //应用Id
    private String appId;

    //权重 越小越优先
    private Integer priority;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}