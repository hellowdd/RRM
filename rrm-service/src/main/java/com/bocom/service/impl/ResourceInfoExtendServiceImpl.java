package com.bocom.service.impl;

import com.bocom.dao.ResourceInfoExtendDao;
import com.bocom.domain.ResourceInfoExtend;
import com.bocom.service.ResourceInfoExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceInfoExtendServiceImpl implements ResourceInfoExtendService {

    @Autowired
    private ResourceInfoExtendDao resourceInfoExtendDao;

    @Override
    public int insertSelective(ResourceInfoExtend record) {
        return resourceInfoExtendDao.insertSelective(record);
    }
}
