package com.bocom.dao;

import com.bocom.domain.ResourceStatus;

import java.util.List;

public interface ResourceStatusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceStatus record);

    int insertSelective(ResourceStatus record);

    ResourceStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceStatus record);

    int updateByPrimaryKey(ResourceStatus record);

    List<ResourceStatus> queryResourceStatus(ResourceStatus record);
}