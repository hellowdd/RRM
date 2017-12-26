package com.bocom.controller.api;


import com.bocom.domain.ResourceInfo;
import com.bocom.dto.AdvancedDto;
import com.bocom.dto.ResourceInfoDto;
import com.bocom.service.ResourceInfoService;
import com.bocom.service.TextServcie;
import com.bocom.util.JsonUtil;
import com.bocom.util.PageUtil;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/app")
public class ApiFileController {

    private static Logger logger = LoggerFactory.getLogger(ApiFileController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ResourceInfoService resourceInfoService;

    @Autowired
    private TextServcie textServcie;

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping("/uploadFile")
    public void uploadFile(@RequestParam MultipartFile file, @RequestParam(required = false) String resourceName,
                           @RequestParam(required = false) String resourceType, @RequestParam(required = false)
                                   String resourceKey,
                           @RequestParam(required = false) String resourcePlace, @RequestParam(required = false)
                                   String taskYear,
                           @RequestParam(required = false) String adminDivision, @RequestParam(required = false)
                                   String taskName, @RequestParam(required = false)
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
            if (resourceType.equals("1")) {
                writePrint(resourceInfoService.uploadFile(file, resourceInfo), response);
            }
            if (resourceType.equals("0")) {
                writePrint(resourceInfoService.uploadVideoFile(file, resourceInfo), response);
            }
            if (resourceType.equals("2")) {
                writePrint(textServcie.uploadTextFile(file, resourceInfo), response);
            }
        } catch (Exception e) {
            logger.error("上传文件出错====================>", e);
        }

    }

    private static void writePrint(ResponseVo responseVo, HttpServletResponse resp) {
        resp.setContentType("text/plain; charset=UTF-8");
        try {
            String json = JsonUtil.toJSon(responseVo);
            resp.getWriter().print(json);
        } catch (IOException e) {
            logger.error("上传文件出错====================>", e);
        }
    }

    /**
     * 高级搜索
     *
     * @param advancedDto
     * @return
     */
    @RequestMapping("/advancedSearch")
    @ResponseBody
    public Map advancedSearch(@RequestBody AdvancedDto advancedDto) {
        PageInfo pageInfo = null;
        try {
            PageUtil.setParams2(request, advancedDto);
            List<ResourceInfoDto> list = resourceInfoService.advancedSearch(advancedDto);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            logger.error("系统异常{}", e);
        }
        return PageUtil.covertMap(new Object[]{"page"}, new Object[]{pageInfo});
    }
    /**
     * 删除上传的资源
     *
     * @return
     */
    @RequestMapping("/deleteResource")
    @ResponseBody
    public ResponseVo deleteResource(String ids) {
        try {
            List<String> idList = new ArrayList<>();
            if (null == ids) {
                return ResponseVoUtil.fail("失败", "参数不对");
            }
            if (ids.indexOf(",") != -1) {
                String[] idArray = ids.split(",");
                idList = Arrays.asList(idArray);
            } else {
                idList.add(ids);
            }
            for (String id : idList) {
                ResourceInfo resourceInfo = new ResourceInfo();
                resourceInfo.setId(Integer.parseInt(id));
                logger.info("deleteResource==============================>" + resourceInfo.getId());
                resourceInfoService.deleteResource(resourceInfo);
            }
            return ResponseVoUtil.success("成功");
        } catch (Exception e) {
            logger.info("deleteResource==============> 出错" + e);
            return ResponseVoUtil.fail("删除信息错误", e);
        }

    }
    /**
     * 查询资源,带分页
     *
     * @return
     */
    @RequestMapping("/queryResource")
    @ResponseBody
    public Map queryResource(@RequestBody Map map) {
        PageInfo pageInfo = null;
        try {
            logger.info("queryResource传递的参数为==============================>" + map);
            PageUtil.setParams1(request, map);
            List<ResourceInfoDto> list = resourceInfoService.queryResource(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            logger.info("queryResource==============> 出错" + e);
        }
        return PageUtil.covertMap(new Object[]{"page"}, new Object[]{pageInfo});
    }
}
