package com.bocom.dto;

import java.util.List;

public class AdvancedDto {

    private String startTime;

    private String endTime;

    private List<String> types;

    private List<SearchDto> searchInfo;

    private Integer pageNum;

    private Integer pageSize;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<SearchDto> getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(List<SearchDto> searchInfo) {
        this.searchInfo = searchInfo;
    }
}
