package com.bocom.service.api.impl;

import com.bocom.dto.TaskRrmReqDto;
import com.bocom.service.api.AcService;
import com.bocom.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AcServiceImpl implements AcService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${rest.AC.getTaskList.url}")
    private String getTaskList;

    @Value("${rest.pap.getProvince}")
    private String getProvince;

    @Autowired
    private HttpServletRequest request;


    /**
     * 获取专项信息
     *
     * @param cityCode
     * @param taskId
     * @return
     */
    @Override
    public ResponseVo getTaskInfo(String cityCode, String taskId) {
        logger.info("获取到的城市id==========================>" + cityCode);
        logger.info("获取到的任务id==========================>" + taskId);
        TaskRrmReqDto taskRrmReqDto = new TaskRrmReqDto();
        taskRrmReqDto.setDelFlag("0");
        taskRrmReqDto.setCityCode(cityCode);
        taskRrmReqDto.setId(taskId);
        Map<String, String> map = new HashMap<>();
        ResponseVo responseVo = new ResponseVo();
        ResponseVo papResponse = new ResponseVo();
        responseVo = HttpClientUtil.postBase64Vo(getTaskList, JsonUtil.toJSon(taskRrmReqDto));
        if (null != responseVo && responseVo.getSuccess()) {
            List<Map> list = (List<Map>) responseVo.getData();
            if (CollectionUtils.isNotEmpty(list)) {
                //通过迭代器删除list的数据
                Iterator<Map> it = list.iterator();
                while (it.hasNext()) {
                    Map taskMap = it.next();
                    String str = (String) taskMap.get("cityCode");
                    //通过城市code获取省市的信息
                    if (StringUtils.isNotEmpty(str)) {
                        String papStr = HttpClientUtil.getBase64(getProvince + "/" + str);
                        papResponse = JsonUtil.readValue(papStr, ResponseVo.class);
                        Map papMap = (Map) papResponse.getData();
                        String provinceCode = null;
                        if (null != (String) ((Map) papMap.get("province")).get("code")) {
                            provinceCode = (String) ((Map) papMap.get("province")).get("code");
                            taskMap.put("parentCode", provinceCode);
                        }
                    }

                }
            }
            return ResponseVoUtil.success(list);
        }
        return null;

    }
}
