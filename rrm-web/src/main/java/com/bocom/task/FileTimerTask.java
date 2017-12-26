package com.bocom.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bocom.config.WebAppInitializer;
import com.bocom.util.DateUtil;

public class FileTimerTask extends TimerTask {
	
	private static final Logger logger = LoggerFactory.getLogger(FileTimerTask.class);
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void run() {
        try {
            //在这里写你要执行的内容
        	logger.info("执行当前时间"+formatter.format(Calendar.getInstance().getTime()));
        	deletefileDirOverFourHours();
       } catch (Exception e) {
    	   logger.info("-------------解析信息发生异常--------------");
       }
	}
	

	private void deletefileDirOverFourHours(){
        File zip = new File(WebAppInitializer.get("myself.imageFlie.dir"));
        if(zip.isDirectory()){
        	deleteFileOverFourHours(zip);
        }
		
	}
	
	/**
	 * 删除超过4小时的临时目录和文件
	 * @param dir
	 * @return
	 */
	private void deleteFileOverFourHours(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
        	File sonFlie;
            for (int i=0; i<children.length; i++) {
            	sonFlie = new File(dir, children[i]);
            	if(DateUtil.getNow().getTime() - sonFlie.lastModified()>14400000){//14400000为4小时
            		deleteDir(sonFlie);
                }
            }    	
        }
	}
	
	private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
	}
	

	
}
