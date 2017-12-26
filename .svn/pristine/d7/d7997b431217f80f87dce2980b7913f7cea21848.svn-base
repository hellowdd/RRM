package com.bocom.controller.rest;

import com.bocom.domain.ResourceInfo;
import com.bocom.service.ImageFormatService;
import com.bocom.service.ResourceInfoService;
import com.bocom.service.api.PapService;
import com.bocom.util.JsonUtil;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
@RequestMapping("/manager/video")
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceInfoService resourceInfoService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ImageFormatService imageFormatService;

    @Autowired
    private PapService papService;

    @Value("${myself.imageFlie.dir}")
    private String fileDir;

    /**
     * 上传视频文件
     *
     * @return
     */
    @RequestMapping("/uploadVideoFile")
    public void uploadFile(@RequestParam MultipartFile fileList, @RequestParam(required = false) String resourceName,
                           @RequestParam(required = false) String resourceType, @RequestParam(required = false)
                                   String resourceKey,
                           @RequestParam(required = false) String resourcePlace, @RequestParam(required = false)
                                   String taskYear,
                           @RequestParam(required = false) String adminDivision, @RequestParam(required = false)
                                   String taskName,@RequestParam(required = false)
                                   String taskId, HttpServletResponse response) {
        // 首先根据文件数组操作文件
        try {
            ResourceInfo resourceInfo = new ResourceInfo();
            resourceInfo.setResourceName(resourceName);
            resourceInfo.setResourceKey(resourceKey);
            resourceInfo.setResourceType(resourceType);
            resourceInfo.setResourcePlace(resourcePlace);
            resourceInfo.setTaskYear(taskYear);
            resourceInfo.setAdminDivision(adminDivision);
            resourceInfo.setTaskName(taskName);
            resourceInfo.setTaskId(taskId);
            writePrint(resourceInfoService.uploadVideoFile(fileList, resourceInfo), response);
        } catch (Exception e) {
            logger.info("上传视频文件出错======》", e);
            return;
        }

    }

    /**
     * 根据时间获取视频图片
     *
     * @return
     */
    @RequestMapping("/getVideoImg")
    @ResponseBody
    public ResponseVo getVideoImg(@RequestParam Integer id, @RequestParam String videoTime) {
        // 首先根据文件数组操作文件
        try {
            ResponseVo util = resourceInfoService.getVideoImg(id, videoTime);
            return util;
        } catch (Exception e) {
            logger.error("截取视频图片===============>",e);
            return ResponseVoUtil.fail("截取视频图片失败", e);
        }

    }

    /**
     * 解决ie11下，上传文件出现下载情况
     *
     * @param responseVo
     * @param resp
     */
    private static void writePrint(ResponseVo responseVo, HttpServletResponse resp) {
        resp.setContentType("text/plain; charset=UTF-8");
        try {
            String json = JsonUtil.toJSon(responseVo);
            resp.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
