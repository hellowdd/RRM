package com.bocom.controller.rest;

import com.bocom.service.api.AcService;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/app")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AcService acService;

    /**
     * 获取任务信息
     *
     * @return
     */
    @RequestMapping("/getTaskInfo")
    @ResponseBody
    public ResponseVo getTaskInfo(String cityCode, String taskId) {
        try {
            return acService.getTaskInfo(cityCode, taskId);
        } catch (Exception e) {
            logger.error("获取任务详情出错=======>", e);
            return ResponseVoUtil.fail("获取专项信息错误", e);
        }
    }
}
