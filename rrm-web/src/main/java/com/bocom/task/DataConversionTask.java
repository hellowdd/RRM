package com.bocom.task;

import com.bocom.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableScheduling
public class DataConversionTask {

    private static final Logger logger = LoggerFactory.getLogger(DataConversionTask.class);

    //定时任务，每天凌晨一点执行,执行删除文件
    @Scheduled(cron = "0 0 1 * * ?")
    public void doJob() {
        try {
            logger.info("==========================执行定时任务====================================");
            String path = System.getProperty("springmvc.root.rrm");
            //每天定时删除临时文件夹下的所有文件
            File dirFile = new File(path + "/transImg");
            File[] listFile = dirFile.listFiles();
            //循环删除文件夹下的文件
            for (File file : listFile) {
                if (file.isDirectory()) {
                    FileUtil.deleteDir(file);
                } else {
                    file.delete();
                }
            }
            logger.info("===================================定时任务结束=================================");
        } catch (Exception e) {
            logger.info("定时任务出现异常{}", e);
        }
    }
}
