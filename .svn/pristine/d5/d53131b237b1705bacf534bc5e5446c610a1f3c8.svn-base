package com.bocom.controller.rest;

import com.bocom.domain.ResourceInfo;
import com.bocom.service.ResourceInfoService;
import com.bocom.service.TextServcie;
import com.bocom.util.FastDfsUtil;
import com.bocom.util.FileUtil;
import com.bocom.util.JsonUtil;
import com.bocom.util.ParseText;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import com.bocom.util.UserUtils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/manager/text")
public class TextController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceInfoService resourceInfoService;

    @Autowired
    private TextServcie textServcie;
    private final static String IMAGE_FOLDER = "transImg/";// 处理过后图片保存的文件夹
    @Resource
    private HttpSession session;
    @Value("${cas.serverName}")
    private String serverName;
    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping("/uploadTextFile")
    public void uploadTextFile(@RequestParam MultipartFile fileList, @RequestParam(required = false) String
            resourceName,
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
            writePrint(textServcie.uploadTextFile(fileList, resourceInfo), response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 防止ie下上传文件返回的结果出现下载情况
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
    
    @RequestMapping("/downloadText")
    @ResponseBody
    public  ResponseVo downUploadRecord(HttpServletRequest request, HttpServletResponse response) {
        String filePath=request.getParameter("path");
        String resourcePath = "";
        try {
            if(filePath.indexOf("http")!=-1){
                resourcePath=filePath.substring(filePath.indexOf("group"),filePath.length());
            }
//            // 获取文件的二进制数组
//            byte[] buffer = new FastDfsUtil().downloadFile(filePath);
//            String baseBuffer = Base64.encodeBase64String(buffer);
//            
//            response.getOutputStream().write(Base64.decodeBase64(baseBuffer));
            
//            response.setStatus(HttpServletResponse.SC_OK);  
//            response.setContentType("application/pdf;charset=UTF-8");  
//            FileInputStream input = new FileInputStream("F:/test/"+uname);
//            InputStream input = new BufferedInputStream(filePath.getInputStream());  
//            byte buffBytes[] = new byte[1024];  
//            OutputStream out = response.getOutputStream();  
//            int read = 0;    
//            while ((read = input.read(buffBytes)) != -1) {    
//                out.write(buffBytes, 0, read);    
//            }  
//            out.flush();    
//            out.close(); 
          if(!ParseText.exists(filePath)){
              return ResponseVoUtil.fail("文件地址有误，请重试！", "文件地址有误，请重试！");
          }
          String path = session.getServletContext().getRealPath("/");//资源目录路径
//          ParseText.downUrlTxt("", filePath,path+IMAGE_FOLDER);
          File savePath = new File(path+IMAGE_FOLDER);
          if (!savePath.exists()) {
              savePath.mkdir();
          }
          String[] urlname = filePath.split("/");
          int len = urlname.length - 1;
          String uname = urlname[len];//获取文件名
          String file = path+IMAGE_FOLDER+uname;
          if (!new FastDfsUtil().downloadFile(resourcePath, file)) {
              throw new RuntimeException("Cannot download text : " + resourcePath);
          }
//          if (!UserUtils.isAdmin(request.getSession())) {
//              FileUtil.addWaterMarkOnFile(FileUtil.fileToByte(new File(file)), file, file,
//                      UserUtils.getUserName(request.getSession()));
//          }
          return ResponseVoUtil.success(serverName +"/"+ request.getContextPath() + "/"+IMAGE_FOLDER + uname);
        } catch (Exception e) {
            logger.error("系统异常{}", e);
        }
        return ResponseVoUtil.fail("获取失败", "获取失败");
    }
    

}
