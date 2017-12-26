package com.bocom.service.impl;

import com.bocom.dao.OperationDao;
import com.bocom.dao.ResourceInfoDao;
import com.bocom.dao.ResourceInfoExtendDao;
import com.bocom.dao.ResourceStatusDao;
import com.bocom.domain.Operation;
import com.bocom.domain.ResourceInfo;
import com.bocom.domain.ResourceInfoExtend;
import com.bocom.domain.ResourceStatus;
import com.bocom.dto.AdvancedDto;
import com.bocom.dto.ResourceInfoDto;
import com.bocom.dto.SearchDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.enums.FileStatus;
import com.bocom.enums.OperationEnum;
import com.bocom.service.ResourceInfoService;
import com.bocom.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ResourceInfoServiceImpl implements ResourceInfoService {

    private Logger logger = LoggerFactory.getLogger(ResourceInfoServiceImpl.class);

    @Autowired
    private ResourceInfoDao resourceInfoDao;

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private ResourceInfoExtendDao resourceInfoExtendDao;

    @Autowired
    private ResourceStatusDao resourceStatusDao;

    @Autowired
    private HttpServletRequest request;

    @Value("${myself.imageFlie.dir}")
    private String imageFlie;

    @Value("${fastDFS.http.url}")
    private String fastDFSUrl;


    @Override
    public List<ResourceInfoDto> queryResource(Map map) {
        PageUtil.dealPage(map);
        List<ResourceInfoDto> list = resourceInfoDao.queryResource(map);
        for (ResourceInfoDto resourceInfoDto : list) {
            if (resourceInfoDto.getThumbnailPath() != null) {
                resourceInfoDto.setThumbnailPath(fastDFSUrl + "/" + resourceInfoDto.getThumbnailPath());
            }
            if (null != resourceInfoDto.getStoragePath()) {
                resourceInfoDto.setStoragePath(fastDFSUrl + "/" + resourceInfoDto.getStoragePath());
            }
            if (null != resourceInfoDto.getVideoCover()) {
                resourceInfoDto.setVideoCover(fastDFSUrl + "/" + resourceInfoDto.getVideoCover());
            }

        }
        return list;
    }

    /**
     * 通知资源类型数量接口
     *
     * @param map
     * @return
     */
    @Override
    public ResponseVo queryResourceSize(Map map) {
        //首先获取总共有多少
        List<ResourceInfoDto> allList = resourceInfoDao.queryResource(map);
        int resourceSize = 0;
        if (CollectionUtils.isNotEmpty(allList)) {
            resourceSize = allList.size();
        }
        //计算当前时间的前一天
        Date startTime = DateUtil.getDate(new Date(), -1);
        Date endTime = DateUtil.getDate(new Date(), 0);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<ResourceInfoDto> updateList = resourceInfoDao.queryResource(map);
        int updateSize = 0;
        if (CollectionUtils.isNotEmpty(updateList)) {
            updateSize = updateList.size();
        }
        Map<String, Object> reMap = new HashMap();
        reMap.put("resourceSize", resourceSize);
        reMap.put("updateSize", updateSize);
        reMap.put("cas", map.get("sessionid"));
        return ResponseVoUtil.success(reMap);
    }

    @Override
    public int updateResourceInfo(ResourceInfo resourceInfo) {
        return resourceInfoDao.updateResourceInfo(resourceInfo);
    }

    @Override
    public ResponseVo deleteResource(ResourceInfo resourceInfo) {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        int id = resourceInfo.getId();
        Map map = new HashMap();
        map.put("id", id);
        //根据id查询出resource的详细信息
        List<ResourceInfoDto> resourceInfoDtoList = resourceInfoDao.queryResource(map);
        ResourceInfoDto resourceInfoDto = resourceInfoDtoList.get(0);
        //根据获取的信息来删除文件
        String storagePath = resourceInfoDto.getStoragePath();
        fastDfsUtil.deleteFile(storagePath);
        //判断是否有缩略图,有就删除
        String thumbnailPath = resourceInfoDto.getThumbnailPath();
        if (null != thumbnailPath) {
            fastDfsUtil.deleteFile(thumbnailPath);
        }
        if (null != resourceInfoDto.getVideoCover()) {
            fastDfsUtil.deleteFile(resourceInfoDto.getVideoCover());
        }
        //删除对应的数据
        resourceInfo.setDelFlag("1");
        resourceInfoDao.updateResourceInfo(resourceInfo);
        ResourceStatus resourceStatus = new ResourceStatus();
        resourceStatus.setStatus(FileStatus.delete.getUploadCode());
        resourceStatus.setResourceId(id);
        resourceStatusDao.updateByPrimaryKeySelective(resourceStatus);
        return ResponseVoUtil.success("删除成功");
    }

    @Override
    public ResponseVo uploadFile(MultipartFile file, ResourceInfo resourceInfo) {
        int resourceId = 0;
        //循环上传文件
        try {
            logger.info("获得到的上传文件的参数为=============>" + resourceInfo.getTaskName());
            SessionUserInfo sessionUserInfo = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            resourceInfo.setUploadPeopleId(sessionUserInfo.getUserId() + "");
            resourceInfo.setUploadPeopleName(sessionUserInfo.getUserName());

            String MD5 = FileUtil.getFileMD5(file);
            //校验MD5是否已经上传
            ResourceStatus resourceStatus = new ResourceStatus();
            resourceStatus.setResourceMd5(MD5);
            List<ResourceStatus> list = resourceStatusDao.queryResourceStatus(resourceStatus);
            if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
                return ResponseVoUtil.success(list.size());
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            resourceInfo.setResourceName(file.getOriginalFilename());
            resourceInfo.setResourceFileSize(file.getSize());
            resourceInfo.setUploadPeopleId(sessionUserInfo.getUserId() + "");
            resourceInfo.setUploadPeopleName(sessionUserInfo.getUserName());
            resourceInfo.setSourceIp(IpUtil.getIp(request));
            resourceInfoDao.insertSelective(resourceInfo);
            int id = resourceInfo.getId();
            resourceId = id;
            //插入信息到操作表中
            Operation operation = new Operation();
            operation.setOperationPeople(sessionUserInfo.getUserId() + "");
            operation.setOperationPeopleName(sessionUserInfo.getUserName());
            operation.setOperationPeopleOrgCode(sessionUserInfo.getOrgCode());
            operation.setOperationTime(new Date());
            operation.setOperationPeopleOrgName(sessionUserInfo.getOrgName());
            operation.setOperationType(OperationEnum.upload.getOperationTypeCode());
            operation.setOperationResource(resourceId);
            operationDao.addOperation(operation);
            //此时在文件状态表中插入信息，表明正在上传中
            resourceStatus.setResourceId(id);
            resourceStatus.setStatus(FileStatus.uploading.getUploadCode());
            resourceStatusDao.insertSelective(resourceStatus);
            //使用fastdfs上传文件
            FastDfsUtil fastDfsUtil = new FastDfsUtil();
            String storagePath = fastDfsUtil.uploadFile(file.getBytes(), file
                    .getOriginalFilename());
            logger.info("storagePath:==", storagePath);
            resourceInfo.setStoragePath(fastDFSUrl + File.separator + storagePath);
            //生成缩略图,先从fastdfs上下载到本地
            String tempPath = imageFlie + File.separator + uuid;
            logger.info("tempPath:==", tempPath);
            new File(imageFlie + File.separator + uuid).mkdirs();
            fastDfsUtil.downloadFile(storagePath, tempPath + File.separator + file.getOriginalFilename());
            new ImageUtil().thumbnailImage(tempPath + File.separator + file.getOriginalFilename(), 216, 216);
            resourceInfo.setResolutionRatio(FileUtil.readImg(new File(tempPath + File.separator + file
                    .getOriginalFilename())));
            //将缩略图上传到fastdfs
            File thumbFile = new File(tempPath + File.separator + "thumb_" + file.getOriginalFilename());
            String thumbPath = fastDfsUtil.uploadFile(tempPath + File.separator +
                    "thumb_" + file.getOriginalFilename());
            resourceInfo.setThumbnailPath(fastDFSUrl + File.separator + storagePath);
            //到此上传完毕
            resourceStatus.setStatus(FileStatus.uploadSuccess.getUploadCode());
            resourceStatusDao.updateByPrimaryKeySelective(resourceStatus);
            resourceInfo.setStoragePath(storagePath);
            resourceInfo.setThumbnailPath(thumbPath);
            resourceInfoDao.updateResourceInfo(resourceInfo);
            //上传完毕删除临时文件夹
            File dirFile = new File(tempPath);
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

    /**
     * 上传视频文件
     *
     * @param file
     * @param resourceInfo
     * @return
     */
    @Override
    public ResponseVo uploadVideoFile(MultipartFile file, ResourceInfo resourceInfo) {
        int resourceId = 0;
        //循环上传文件
        try {
            logger.info("获得到的上传文件的参数为=============>" + resourceInfo.getTaskName());
            SessionUserInfo sessionUserInfo = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            resourceInfo.setUploadPeopleId(sessionUserInfo.getUserId() + "");
            resourceInfo.setUploadPeopleName(sessionUserInfo.getUserName());
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
            //获取视频的分辨率
            String ratio = VideoUtil.getVideoInfo(path + File.separator + file.getOriginalFilename());
            resourceInfo.setResolutionRatio(ratio);
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
            //获取视频的封面帧
            VideoUtil.getImgByTime(path + File.separator + file.getOriginalFilename(), path + File.separator + "cover" +
                    ".jpg", "00:00:00");
            //将封面帧上传fastdfs
            FastDfsUtil fastDfsUtil = new FastDfsUtil();
            String videoCover = fastDfsUtil.uploadFile(path + File.separator + "cover.jpg");
            ResourceInfoExtend resourceInfoExtend = new ResourceInfoExtend();
            resourceInfoExtend.setResourceId(resourceId);
            resourceInfoExtend.setVideoCover(videoCover);
            resourceInfoExtendDao.insertSelective(resourceInfoExtend);
            //插入信息到操作表中
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
            //到此上传完毕
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

    //根据时间获取视频的截图
    @Override
    public ResponseVo getVideoImg(Integer id, String videoTime) throws Exception {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        //首先根据id找到视频的fastdfs地址
        List<ResourceInfoDto> list = resourceInfoDao.queryResource(map);
        ResourceInfoDto resourceInfoDto = list.get(0);
        String storagePath = resourceInfoDto.getStoragePath();
        String filePath = fastDFSUrl + "/" + storagePath;
        //下载该文件到服务器
        String tempPath = imageFlie + File.separator + uuid;
        new File(tempPath).mkdirs();
        String[] urlname = resourceInfoDto.getStoragePath().split("/");
        int len = urlname.length - 1;
        String uname = urlname[len];//获取文件名
//        fastDfsUtil.downloadFile(storagePath, tempPath + File.separator + uname);
        VideoUtil.getImgByTime(filePath, tempPath + File
                        .separator + "videokey.jpg",
                videoTime);
        //通过fastdfs上传图片
        String str = fastDfsUtil.uploadFile(tempPath + File
                .separator + "videokey.jpg");
        ResourceInfo resourceInfo = new ResourceInfo();
        ResourceInfoExtend resourceInfoExtend = new ResourceInfoExtend();
        resourceInfoExtend.setResourceId(id);
        resourceInfoExtend.setVideoCover(str);
        resourceInfoExtendDao.updateByPrimaryKeySelective(resourceInfoExtend);
        //删除对应的临时文件夹
        FileUtil.deleteDir(new File(tempPath));
        return ResponseVoUtil.success(fastDFSUrl + "/" + str);

    }

    /**
     * 高级搜索
     *
     * @param advancedDto
     * @return
     */
    @Override
    public List<ResourceInfoDto> advancedSearch(AdvancedDto advancedDto) throws ParseException {
        PageUtil.dealPage1(advancedDto);
        //首先获取需要查询哪些类型的数据
        List<String> listType = advancedDto.getTypes();
        //获取需要查询的类型，以or拼接
        StringBuffer typeSb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(listType)) {
            typeSb.append(" and (");
            for (int i = 0; i < listType.size(); i++) {
                if (i == 0) {
                    typeSb.append("  a.resource_type=" + listType.get(i) + " ");
                } else {
                    typeSb.append(" or a.resource_type=" + listType.get(i) + " ");
                }
            }


        }
        //获取需要查询的参数信息
        StringBuffer infoSb = new StringBuffer();
        List<SearchDto> searchInfoList = advancedDto.getSearchInfo();
        if (CollectionUtils.isNotEmpty(searchInfoList)) {
            infoSb.append(" and (");
            for (int i = 0; i < searchInfoList.size(); i++) {
                if (i == 0) {
                    infoSb.append(jointString(searchInfoList.get(i).getResourceQueryType(), searchInfoList.get(i)
                                    .getIsDim(),
                            searchInfoList.get(i).getInfo()));
                } else {
                    if (searchInfoList.get(i).getAndOr().equals("or")) {
                        typeSb.append(" or " + jointString(searchInfoList.get(i).getResourceQueryType(), searchInfoList
                                .get(i)
                                .getIsDim(), searchInfoList.get(i).getInfo()));
                    } else {
                        infoSb.append(" " + searchInfoList.get(i).getAndOr() + " ");
                        infoSb.append(jointString(searchInfoList.get(i).getResourceQueryType(), searchInfoList.get(i)
                                .getIsDim(), searchInfoList.get(i).getInfo()));
                    }

                }

            }
            typeSb.append(" )");
            infoSb.append(")");
        }

        //调用dao方法
        Map map = new HashMap();
        map.put("queryInfo", typeSb.toString() + infoSb.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date startTime = sdf.parse(advancedDto.getStartTime() + "-01-01 00:00:00");
        map.put("startTime", DateUtil.getDate(startTime, 0));
        Date endTime = sdf.parse(Integer.parseInt(advancedDto.getEndTime()) + 1 + "-01-01 00:00:00");
        map.put("endTime", DateUtil.getDate(endTime, 0));
        List<ResourceInfoDto> list = resourceInfoDao.advancedSearch(map);
        for (ResourceInfoDto resourceInfoDto : list) {
            if (StringUtils.isNotEmpty(resourceInfoDto.getStoragePath())) {
                resourceInfoDto.setStoragePath(fastDFSUrl + "/" + resourceInfoDto.getStoragePath());
            }
            if (StringUtils.isNotEmpty(resourceInfoDto.getThumbnailPath())) {
                resourceInfoDto.setThumbnailPath(fastDFSUrl + "/" + resourceInfoDto.getThumbnailPath());
            }
        }
        return list;
    }

    /**
     * 查询服务信息
     *
     * @param map
     * @return
     */
    @Override
    public List<ResourceInfoDto> queryServiceInfo(Map map) {
        PageUtil.dealPage(map);
        //如果不是管理员
        HttpSession session = request.getSession();
        SessionUserInfo sessionUserInfo = (SessionUserInfo) session
                .getAttribute("sessionUserInfo");
//        if (!UserUtils.isAdmin(session)) {
//            map.put("uploadPeopleId", sessionUserInfo.getUserId());
//        }

        return resourceInfoDao.queryServiceInfo(map);
    }

    @Override
    public List<ResourceInfoDto> queryByResource(Map map) {
        PageUtil.dealPage(map);
        List<ResourceInfoDto> list = resourceInfoDao.queryByResource(map);
        for (ResourceInfoDto resourceInfoDto : list) {
            if (resourceInfoDto.getThumbnailPath() != null) {
                resourceInfoDto.setThumbnailPath(fastDFSUrl + "/" + resourceInfoDto.getThumbnailPath());
            }
            if (null != resourceInfoDto.getStoragePath()) {
                resourceInfoDto.setStoragePath(fastDFSUrl + "/" + resourceInfoDto.getStoragePath());
            }
            if (null != resourceInfoDto.getVideoCover()) {
                resourceInfoDto.setVideoCover(fastDFSUrl + "/" + resourceInfoDto.getVideoCover());
            }

        }
        return list;
    }

    //拼接查询的字符串
    public String jointString(String key, String isDim, String info) {
        StringBuffer sb = new StringBuffer();
        if (isDim.equals("0")) {
            sb.append("a." + key + " like '%");
            sb.append(info);
            sb.append("%'");
        } else {
            if (key.equals("resource_key")) {
                sb.append(" find_in_set " + "( " + info + "," + "resource_key)");
            } else {
                sb.append("a." + key + "= '" + info + "'");
            }

        }
        return sb.toString();
    }


}
