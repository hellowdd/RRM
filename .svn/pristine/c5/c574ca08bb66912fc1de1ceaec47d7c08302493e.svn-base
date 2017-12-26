package com.bocom.util;


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @说明 勇子sama的牛逼工具类
 * @工具包含范围 字符串
 */
public class StringUtils {
    
    private StringUtils() {

    }

    public static boolean isNullOrEmpty(String toTest) {
        return toTest == null || toTest.isEmpty();
    }

    public static boolean isNullOrEmpty(String... toTest) {
        for (String str : toTest) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String toTest) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(toTest);
        return isNum.matches();
    }

    public static boolean isNumber(String... toTest) {
        for (String str : toTest) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return isNum.matches();
            }
        }
        return true;
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /**
     * 生成随机数<br>
     * GUID： 即Globally Unique Identifier（全球唯一标识符） 也称作 UUID(Universally Unique
     * IDentifier) 。
     *
     * 所以GUID就是UUID。
     *
     * GUID是一个128位长的数字，一般用16进制表示。算法的核心思想是结合机器的网卡、当地时间、一个随即数来生成GUID。
     *
     * 从理论上讲，如果一台机器每秒产生10000000个GUID，则可以保证（概率意义上）3240年不重复。
     *
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
