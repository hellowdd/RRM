package com.bocom.service.impl;

import com.bocom.dao.OperationDao;
import com.bocom.domain.Operation;
import com.bocom.dto.OperationDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.enums.OperationEnum;
import com.bocom.service.OperationService;
import com.bocom.util.PageUtil;
import com.bocom.util.UserUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private HttpServletRequest request;

    @Value("${fastDFS.http.url}")
    private String fastDFSUrl;

    @Override
    public List<OperationDto> queryOperation(Map map) {
        PageUtil.dealPage(map);
        SessionUserInfo sessionUserInfo =
                (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if(!UserUtils.isAdmin(request.getSession())){
            map.put("operationPeople",sessionUserInfo.getUserId());
        }
        List<OperationDto> list=operationDao.queryOperation(map);
        for(OperationDto operationDto:list){
            if(StringUtils.isNotEmpty(operationDto.getStoragePath())){
                operationDto.setStoragePath(fastDFSUrl+"/"+operationDto.getStoragePath());
            }
            if(StringUtils.isNotEmpty(operationDto.getThumbnailPath())){
                operationDto.setThumbnailPath(fastDFSUrl+"/"+operationDto.getThumbnailPath());
            }
        }
        return list;
    }
    @Override
    public int addOperation(int resourceId){
      //插入信息到操作表中
        SessionUserInfo sessionUserInfo =
                (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        Operation operation = new Operation();
        operation.setOperationPeople(sessionUserInfo.getUserId() + "");
        operation.setOperationPeopleName(sessionUserInfo.getUserName());
        operation.setOperationPeopleOrgCode(sessionUserInfo.getOrgCode());
        operation.setOperationTime(new Date());
        operation.setOperationPeopleOrgName(sessionUserInfo.getOrgName());
        operation.setOperationType(OperationEnum.download.getOperationTypeCode());
        operation.setOperationResource(resourceId);
        return operationDao.addOperation(operation);
        
    }
}
