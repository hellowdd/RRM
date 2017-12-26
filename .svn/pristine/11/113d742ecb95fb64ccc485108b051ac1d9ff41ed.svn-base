package com.bocom.dao;

import com.bocom.domain.Operation;
import com.bocom.dto.OperationDto;

import java.util.List;
import java.util.Map;

public interface OperationDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Operation record);

    int addOperation(Operation record);

    Operation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);

    List<OperationDto> queryOperation(Map map);
}