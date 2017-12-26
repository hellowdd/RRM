package com.bocom.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

public class ExecCommondUtil {

    /**
     * 发送linux命令
     *
     * @param str 需要发送的命令
     * @return 返回的结果
     * @throws Exception
     */
    private static String execCommond(String str) throws Exception {
        StringBuffer sb = new StringBuffer();
        InputStream in;
        System.out.println("命令：" + str);
        Process pro = Runtime.getRuntime().exec(str);
        pro.waitFor();
        in = pro.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while ((line = read.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();

    }

    public static String execCommond(List<String> cmd) {
        System.out.println("命令 ： " + JsonUtil.toJSon(cmd));
        Process proc = null;
        BufferedReader stdout = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmd);
            builder.redirectErrorStream(true);
            proc = builder.start();
            stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            StringBuilder resuleStr = new StringBuilder();
            while ((line = stdout.readLine()) != null) {
                resuleStr.append(line);
            }
            return resuleStr.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                proc.waitFor();
                stdout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String exec(List<String> cmd) {
        String convertedTime = null;
        Process proc = null;
        BufferedReader stdout = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmd);
            builder.redirectErrorStream(true);
            proc = builder.start();
            stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            List<String> returnStringList = new LinkedList<>();
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
                returnStringList.add(dealString(line));
            }
            String info = "";
            for (int i = returnStringList.size() - 1; i >= 0; i--) {
                if (null != returnStringList.get(i) && returnStringList.get(i).startsWith("frame=")) {
                    info = returnStringList.get(i);
                    break;
                }
            }
            if (null != info) {
                convertedTime = info.split("time=")[1].split("bitrate=")[0].trim();
            }
        } catch (IndexOutOfBoundsException ex) {
            convertedTime = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                proc.waitFor();
                stdout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertedTime;
    }

    public static String dealString(String str) {
        Matcher m = java.util.regex.Pattern.compile("^frame=.*").matcher(str);
        String msg = "";
        while (m.find()) {
            msg = m.group();
        }
        return msg;
    }


}
