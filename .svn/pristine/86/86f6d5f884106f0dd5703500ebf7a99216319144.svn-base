package com.bocom.controller.rest;

import com.bocom.dto.ResourceInfoDto;
import com.bocom.service.OperationService;
import com.bocom.service.ResourceInfoService;
import com.bocom.util.*;
import com.bocom.util.img.ImgUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 图片相关处理动作
 *
 * @author QY
 */
@Controller
@RequestMapping("/manager/img")
public class ImgHandleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpSession session;
    @Resource
    private HttpServletResponse response;

    private final static String IMAGE_FOLDER = "transImg/";// 处理过后图片保存的文件夹
    @Autowired
    private ResourceInfoService resourceInfoService;
    @Autowired
    private OperationService operationService;

    /**
     * 图片下载
     */
    @RequestMapping("/download")
    @ResponseBody
    public void download() {
        ResponseVo resultVo;
        try {
            resultVo = new ResponseVo();
            String path = session.getServletContext().getRealPath("/");//资源目录路径
            File file = new File(path + IMAGE_FOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            String url = request.getParameter("url");
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String height = request.getParameter("height");
            String width = request.getParameter("width");

            if (StringUtils.isNullOrEmpty(url, name, type, height, width)) {
                resultVo.setMessage("参数不对，请重试！");
                resultVo.setSuccess(false);
            }
            String fileId = "";
            if (url.indexOf("http") != -1) {
                fileId = url.substring(url.indexOf("group"), url.length());
            }
            Map map = new HashMap();
            map.put("storagePath", fileId);
            List<ResourceInfoDto> dtos = resourceInfoService.queryServiceInfo(map);
            if(dtos != null && dtos.size()>0){
                operationService.addOperation(dtos.get(0).getId());
            }
            //获取图片的扩展名
            String suffix = url.substring(url.lastIndexOf('.') + 1);
            //图片访问路径若是项目内则拼接上项目路径，不是则保持原样
            url = url.startsWith("http") ? url : path + url;
            String tempImgForType = url;
            String targetImg;

            //后缀与需要装换格式相同
            if (suffix.equalsIgnoreCase(type)) {
                //相同时候， gif图片 直接下载 否则调整分辨率
                if (suffix.equalsIgnoreCase(ImgUtils.IMAGE_TYPE_GIF)) {
                    targetImg = path + IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + suffix;
                    new FastDfsUtil().downloadFile(url.substring(url.lastIndexOf("group")), targetImg);
                } else {
                    targetImg = path + IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + suffix;
                    //调整分辨率
                    ImgUtils.scaleByWH(tempImgForType, targetImg, Integer.parseInt(height),
                            Integer.parseInt(width), false);
                }
            } else {
                tempImgForType = path + IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' +
                        suffix;
                //转换格式(PNG直接转换成jpg会失真，故先转换成gif，再将gif转成jpg)
                if (suffix.equalsIgnoreCase(ImgUtils.IMAGE_TYPE_PNG) && type.equalsIgnoreCase(ImgUtils
                        .IMAGE_TYPE_JPG)) {
                    String tempImg = IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' +
                            ImgUtils.IMAGE_TYPE_GIF;
                    ImgUtils.convert(url, ImgUtils.IMAGE_TYPE_GIF, path + tempImg);
                    ImgUtils.convert(path + tempImg, ImgUtils.IMAGE_TYPE_PNG, tempImgForType);
                } else {
                    ImgUtils.convert(url, type, tempImgForType);
                }
                targetImg = path + IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + suffix;
                //调整分辨率
                ImgUtils.scaleByWH(tempImgForType, targetImg, Integer.parseInt(height),
                        Integer.parseInt(width), false);
            }

            byte[] fileByte = FileUtil.fileToByte(ImgUtils.getISImg(targetImg));

            boolean waterFlag = false;
            if (!UserUtils.isAdmin(session)) {
                waterFlag = FileUtil.addWaterMarkOnFile(fileByte, targetImg, targetImg, UserUtils.getUserName
                        (request.getSession()));
            }

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            // 设置response的Header
            response.setContentType("application/x-msdownload");
            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            name = name.replace('.' + suffix, "") + '.' + type;
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name,
                    "UTF-8"));
            if (waterFlag) {
                toClient.write(FileUtil.fileToByte(ImgUtils.getISImg(targetImg)));
            } else {
                toClient.write(fileByte);
            }
            toClient.flush();
            toClient.close();
            FileUtil.deleteDir(new File(targetImg));
        } catch (Exception e) {
            logger.error("img download error ", e);
        }
    }


    /**
     * 分辨率调整
     *
     * @return
     */
    @RequestMapping("/transScale")
    @ResponseBody
    public ResponseVo transScale() {
        ResponseVo resultVo;
        try {
            resultVo = new ResponseVo();
            String path = session.getServletContext().getRealPath("/");//资源目录路径
            File file = new File(path + IMAGE_FOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            String url = request.getParameter("url");
            String height = request.getParameter("height");
            String width = request.getParameter("width");
            if (StringUtils.isNullOrEmpty(url, width, height)) {
                resultVo.setMessage("参数不对，请重试！");
                resultVo.setSuccess(false);
            }
            //获取图片的扩展名
            String suffix = url.substring(url.lastIndexOf('.') + 1);
            String targetImg = IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + suffix;
            //图片访问路径若是项目内则拼接上项目路径，不是则保持原样
            url = url.startsWith("http") ? url : path + url;
            //调整分辨率
            ImgUtils.scaleByWH(url, path + targetImg, Integer.parseInt(height), Integer.parseInt(width), true);
            //返回项目内的图片路径
            resultVo.setData("/" + targetImg);
        } catch (Exception e) {
            logger.error("img transScale error ", e);
            resultVo = new ResponseVo(e);
        }
        return resultVo;
    }
//
//    /**
//     * 格式转换
//     *
//     * @return
//     */
//    @RequestMapping("/transType")
//    @ResponseBody
//    public ResponseVo transType() {
//        ResponseVo resultVo;
//        try {
//            resultVo = new ResponseVo();
//            String path = session.getServletContext().getRealPath("/");//资源目录路径
//            File file = new File(path + IMAGE_FOLDER);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            String url = request.getParameter("url");
//            String type = request.getParameter("type");
//            if (StringUtils.isNullOrEmpty(url, type)) {
//                resultVo.setMessage("参数不对，请重试！");
//                resultVo.setSuccess(false);
//            }
//            //获取图片的扩展名
//            String suffix = url.substring(url.lastIndexOf('.') + 1);
//            String targetImg = IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + type;
//            //图片访问路径若是项目内则拼接上项目路径，不是则保持原样
//            url = url.startsWith("http") ? url : path + url;
//            //转换格式(PNG直接转换成jpg会失真，故先转换成gif，再将gif转成jpg)
//            if (suffix.equalsIgnoreCase(ImgUtils.IMAGE_TYPE_PNG) && type.equalsIgnoreCase(ImgUtils.IMAGE_TYPE_JPG)) {
//                String tempImg = IMAGE_FOLDER + UUID.randomUUID().toString().replaceAll("-", "") + '.' + ImgUtils
//                        .IMAGE_TYPE_GIF;
//                ImgUtils.convert(url, ImgUtils.IMAGE_TYPE_GIF, path + tempImg);
//                ImgUtils.convert(path + tempImg, ImgUtils.IMAGE_TYPE_PNG, path + targetImg);
//            } else {
//                ImgUtils.convert(url, type, path + targetImg);
//            }
//            //返回项目内的图片路径
//            resultVo.setData("/" + targetImg);
//        } catch (Exception e) {
//            logger.error("img transType error ", e);
//            resultVo = new ResponseVo(e);
//        }
//        return resultVo;
//    }

}
