package com.bocom.service;

import com.bocom.domain.ResourceInfo;
import com.bocom.dto.AdvancedDto;
import com.bocom.dto.ResourceInfoDto;
import com.bocom.util.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ResourceInfoService {

    List<ResourceInfoDto> queryResource(Map map);

    ResponseVo queryResourceSize(Map map);

    int updateResourceInfo(ResourceInfo resourceInfo);

    /**
     * 删除资源信息
     * @param resourceInfo
     * @return
     */
    ResponseVo deleteResource(ResourceInfo resourceInfo);

    ResponseVo uploadFile(MultipartFile fileList, ResourceInfo resourceInfo);

    ResponseVo  uploadVideoFile(MultipartFile fileList, ResourceInfo resourceInfo);

    ResponseVo getVideoImg(Integer id,String videoTime) throws Exception;

    List<ResourceInfoDto> advancedSearch(AdvancedDto advancedDto) throws ParseException;

    List<ResourceInfoDto> queryServiceInfo(Map map);

    List<ResourceInfoDto> queryByResource(Map map);
}
