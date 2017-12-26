package com.bocom.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VideoUtil {

    private static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    private VideoUtil() {

    }

    /**
     * 获取视频的关键帧
     *
     * @param srcFile  视频地址
     * @param destFile 保存关键帧的地址
     */
    public static void getKeys(String srcFile, String destFile) throws Exception {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("ffmpeg -i ");
//        stringBuffer.append(srcFile);
//        stringBuffer.append(" -vf selcet='eq(pict_type\\,I)' ");
//        stringBuffer.append("-vsync 2 ");
//        stringBuffer.append("-f image2 ");
//        stringBuffer.append(destFile);
//        stringBuffer.append("/keys-%d.jpeg");
//
//        String str = ExecCommondUtil.execCommond(stringBuffer.toString());


        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(srcFile);
        cmd.add("-vf");
        cmd.add("selcet='eq(pict_type\\,I)'");
        cmd.add("-vsync");
        cmd.add("2");
        cmd.add("-f");
        cmd.add("image2");
        cmd.add(destFile + "/keys-%d.jpeg");
        ExecCommondUtil.execCommond(cmd);


    }

    /**
     * 根据时间获取视频的图片
     *
     * @param srcFile  视频位置
     * @param destFile 保存的位置
     * @param time     时间，格式为00:00:00
     * @throws Exception
     */
    public static void getImgByTime(String srcFile, String destFile, String time) throws Exception {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("ffmpeg -i ");
//        stringBuffer.append(srcFile);
//        stringBuffer.append(" -ss ");
//        stringBuffer.append(time);
//        stringBuffer.append(" -frames:v 1 ");
//        stringBuffer.append(destFile);
//        String str = ExecCommondUtil.execCommond(stringBuffer.toString());

        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(srcFile);
        cmd.add("-ss");
        cmd.add(time);
        cmd.add("-frames:v");
        cmd.add("1");
        cmd.add(destFile);
        ExecCommondUtil.execCommond(cmd);
    }


    public static String getVideoInfo(String videoPath) {

        List<String> cmd = new ArrayList<>();
        cmd.add("ffprobe");
        cmd.add("-v");
        cmd.add("quiet");
        cmd.add("-print_format");
        cmd.add("json");
        cmd.add("-show_streams");
        cmd.add(videoPath);
        String str = ExecCommondUtil.execCommond(cmd);
        logger.info("获取到的视频信息========>" + str);
        Map map = JsonUtil.readValue(str, Map.class);
        List<Map> list = (List<Map>) map.get("streams");
        for (Map map1 : list) {
            if (null != map1.get("coded_width")) {
                return (int) map1.get("coded_width") + "*" + (int) map1.get("coded_height");
            }
        }
        return null;

    }

    public static String getVideoInfoBit(String videoPath) throws Exception {
        List<String> cmd = new ArrayList<>();
        cmd.add("ffprobe");
        cmd.add("-v");
        cmd.add("quiet");
        cmd.add("-print_format");
        cmd.add("json");
        cmd.add("-show_streams");
        cmd.add("-show_format");
        cmd.add(videoPath);
        String str = ExecCommondUtil.execCommond(cmd);
        logger.info("获取到的视频信息========>" + str);
        if (StringUtils.isNullOrEmpty(str)) {
            return "2048K";
        }
        JSONObject format = JSONObject.fromObject(str);
        if (null == format) {
            return "2048K";
        }
        JSONObject info = format.getJSONObject("format");
        return StringUtils.isNullOrEmpty(info.getString("bit_rate")) ? "2048k" : info.getString("bit_rate");

    }


    /**
     * 给视频加上水印
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param text     水印图片路径
     * @throws Exception 异常
     */

    public static String addWaterMarkOnVideo(String srcFile, String destFile, String text) {
        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add(srcFile);
        cmd.add("-vf");
        String logoString = "movie=" + text + " [watermark];[in][watermark] overlay=10:10[out]";
        cmd.add(logoString);
        cmd.add("-strict");
        cmd.add("-2");
        cmd.add("-q:v");
        cmd.add("4");
        cmd.add(destFile);
        System.out.println("命令  ：  ");
        for (String str : cmd) {
            System.out.print("  " + str);
        }
        String convertedTime = ExecCommondUtil.exec(cmd);
        logger.debug("execCommond  time is  ====== " + convertedTime);
        return returnSecond(convertedTime);//获取转换时间
    }

    /**
     * 如果返回不是null的值就是成功(值为转换用时单位:秒)
     *
     * @param instr
     * @return
     */
    public static String returnSecond(String instr) {
        String returnValue = null;
        if (null != instr) {
            String[] a = instr.split("\\.");
            String[] b = a[0].split(":");
            int returnNumber = 0;
            if (null != instr && b[0].length() != 0) {
                returnNumber = Integer.valueOf(b[0]) * 60 * 60 + Integer.valueOf(b[1]) * 60 + Integer.valueOf(b[2]);
                returnValue = String.valueOf(returnNumber);
            } else {
                returnValue = null;
            }
        }
        return returnValue;
    }


}
