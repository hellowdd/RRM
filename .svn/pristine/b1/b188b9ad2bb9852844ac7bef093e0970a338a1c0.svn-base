package com.bocom.service.impl;

import com.bocom.dao.OperationDao;
import com.bocom.dao.ResourceInfoDao;
import com.bocom.dao.ResourceInfoExtendDao;
import com.bocom.dao.ResourceStatusDao;
import com.bocom.domain.Operation;
import com.bocom.domain.ResourceInfo;
import com.bocom.domain.ResourceInfoExtend;
import com.bocom.domain.ResourceStatus;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.enums.FileStatus;
import com.bocom.enums.OperationEnum;
import com.bocom.service.TextServcie;
import com.bocom.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TextServcieImpl implements TextServcie {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceInfoDao resourceInfoDao;

    @Autowired
    private ResourceStatusDao resourceStatusDao;

    @Autowired
    private ResourceInfoExtendDao resourceInfoExtendDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private File2PdfUtil file2PdfUtil;

    @Value("${myself.imageFlie.dir}")
    private String imageFlie;

    @Value("${fastDFS.http.url}")
    private String fastDFSUrl;


    /**
     * 上传文本文件
     *
     * @param file
     * @param resourceInfo
     * @return
     */
    @Override
    public ResponseVo uploadTextFile(MultipartFile file, ResourceInfo resourceInfo) {
        int resourceId = 0;
        try {
            logger.info("获得到的上传文件的参数为=============>" + resourceInfo.getTaskName());
            SessionUserInfo sessionUserInfo = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            resourceInfo.setUploadPeopleId(sessionUserInfo.getUserId() + "");
            resourceInfo.setUploadPeopleName(sessionUserInfo.getUserName());
            //循环上传文件
            String MD5 = FileUtil.getFileMD5(file);
            //校验MD5是否已经上传
            ResourceStatus resourceStatus = new ResourceStatus();
            resourceStatus.setResourceMd5(MD5);
            List<ResourceStatus> list = resourceStatusDao.queryResourceStatus(resourceStatus);
            if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
                return ResponseVoUtil.success(list.size());
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String path = imageFlie + File.separator + uuid;
            new File(path).mkdirs();
            //将文件上传到本地
            FileUtil.saveFileFromInputStream(file.getInputStream(), path, file.getOriginalFilename());
            //插入信息到操作表中
            resourceInfo.setResourceName(file.getOriginalFilename());
            resourceInfo.setResourceFileSize(file.getSize());
            resourceInfo.setUploadPeopleId(sessionUserInfo.getUserId() + "");
            resourceInfo.setUploadPeopleName(sessionUserInfo.getUserName());
            resourceInfo.setSourceIp(IpUtil.getIp(request));
            resourceInfoDao.insertSelective(resourceInfo);
            int id = resourceInfo.getId();
            resourceId = id;
            //此时在文件状态表中插入信息，表明正在上传中
            resourceStatus.setResourceId(id);
            resourceStatus.setStatus(FileStatus.uploading.getUploadCode());
            resourceStatusDao.insertSelective(resourceStatus);
            //将文件转为pdf
            FastDfsUtil fastDfsUtil = new FastDfsUtil();
            if (!file.getOriginalFilename().endsWith("pdf")) {
                if (!file2PdfUtil.file2Pdf(path + File.separator + file.getOriginalFilename(), path + File
                        .separator +
                        file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "" +
                        ".pdf")) {
                    throw new Exception("转换异常");
                }
                //将转化的pdf文件上传到fastdfs
                String pdfPath = fastDfsUtil.uploadFile(path + File.separator +
                        file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + ".pdf");
                resourceInfo.setThumbnailPath(pdfPath);
            }
            Operation operation = new Operation();
            operation.setOperationPeople(sessionUserInfo.getUserId() + "");
            operation.setOperationPeopleName(sessionUserInfo.getUserName());
            operation.setOperationPeopleOrgCode(sessionUserInfo.getOrgCode());
            operation.setOperationTime(new Date());
            operation.setOperationPeopleOrgName(sessionUserInfo.getOrgName());
            operation.setOperationType(OperationEnum.upload.getOperationTypeCode());
            operation.setOperationResource(resourceId);
            operationDao.addOperation(operation);
            //使用fastdfs上传文件
            String storagePath = fastDfsUtil.uploadFile(file.getBytes(), file
                    .getOriginalFilename());
            logger.info("storagePath:==", storagePath);
            resourceInfo.setStoragePath(fastDFSUrl + File.separator + storagePath);
            if (file.getOriginalFilename().endsWith("pdf")) {
                resourceInfo.setThumbnailPath(storagePath);
            }
            //到此上传完毕
            //获取文本文件的信息,将信息存入扩展信息表
            ResourceInfoExtend resourceInfoExtend = new ResourceInfoExtend();
            resourceInfoExtend.setResourceId(resourceId);
            String text = ParseText.getTextInfo(storagePath);
            resourceInfoExtend.setVideoKeys(text);
            resourceInfoExtendDao.insertSelective(resourceInfoExtend);
            resourceStatus.setStatus(FileStatus.uploadSuccess.getUploadCode());
            resourceStatusDao.updateByPrimaryKeySelective(resourceStatus);
            resourceInfo.setStoragePath(storagePath);
            resourceInfoDao.updateResourceInfo(resourceInfo);
            //上传完毕删除临时文件夹
            File dirFile = new File(path);
            FileUtil.deleteDir(dirFile);
            return ResponseVoUtil.success("上传成功");
        } catch (Exception e) {
            logger.error("上传文件出错============》", e);
            //表明上传失败，将上传状态变为上传失败
            if (resourceId != 0) {
                ResourceStatus resourceStatus = new ResourceStatus();
                resourceStatus.setResourceId(resourceId);
                resourceStatus.setStatus(FileStatus.uploadError.getUploadCode());
                resourceStatusDao.updateByPrimaryKeySelective(resourceStatus);
            }
            return ResponseVoUtil.fail("上传文件失败", e);

        }
    }
}
