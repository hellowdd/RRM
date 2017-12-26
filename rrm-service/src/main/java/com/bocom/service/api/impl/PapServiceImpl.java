package com.bocom.service.api.impl;

import com.bocom.service.api.PapService;
import com.bocom.util.HttpClientUtil;
import com.bocom.util.JsonUtil;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PapServiceImpl implements PapService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${rest.pap.areaList}")
    private String areaList;

    @Override
    public ResponseVo areaList(String code) {
        logger.info("获取到的code值为=======================>" + code);
        String str = HttpClientUtil.getBase64(areaList + "/" + code);
        if (StringUtils.isNotEmpty(str)) {
            ResponseVo responseVo=JsonUtil.readValue(str,ResponseVo.class);
            if("0".equals(code)){
                List<Map> list=( List<Map>) responseVo.getData();
                List<Map> reList=new ArrayList<>();
                for(Map map:list){
                    if("0".equals((String)map.get("pcode"))){
                        reList.add(map);
                    }
                }

                return ResponseVoUtil.success(reList);

            }
            return responseVo;

        }
        return null;
    }
}
