package com.common.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ricky on 2015/12/02.
 * <p/>
 * 数字处理工具
 */
public class C_NumUtil {

    public static String setTextLargeNum(int num) {

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        if (num > 10000) {
            float tCount = 1f * num / 10000;
            return decimalFormat.format(tCount) + "W";
        } else if (num > 1000 && num <= 10000) {
            float tCount = 1f * num / 1000;
            return decimalFormat.format(tCount) + "K";
        } else if (num >= 0 && num <= 1000) {
            return String.valueOf(num);
        }
        return "0";
    }

    /**
     * 判别手机是否为正确手机号码；
     * 号码段分配如下：。
     * 电信号段:133/153/180/181/189/177
     * 联通号段:130/131/132/155/156/185/186/145/176
     * 移动号段:134/135/136/137/138/139/150/151/152/157/158/159/182/183/184/187/188/147/178
     * 虚拟运营商:170
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-3|5-9]|8[0-9]|7[0|6-8])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判别身份证号码是否为正确身份证号码；
     * 号码段分配如下：
     * 15位数字
     * 18位数字
     * 17位数字+最后一位字母
     */
    public static boolean isIdCardNum(String idCard) {
        Pattern p = Pattern
                .compile("^(^\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
        return p.matcher(idCard).matches();
    }

    /**
     * 判断是否是存数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 根据总数和当前的数据获取百分数
     *
     * @param df    保留小数点
     * @param num   数据
     * @param total 总数
     * @return str百分数 (xx.x)
     */
    public static String getPercentAVG(DecimalFormat df, int num, int total) {
        if (total == 0) {
            return df.format(0);
        }
        double avg = Math.round(num * 100 / (float) total);
        return df.format(avg);
    }

    public static double getPercentAVG(int num, int total) {
        if (total == 0) {
            return 0;
        }
        return Math.round(num * 100 / (float) total);
    }

    /**
     * 根据总数和当前的数据获取平均数
     *
     * @param df    保留小数点
     * @param num   数据
     * @param count 总数
     * @return str平均数 (0.xx)
     */
    public static String getAVG(DecimalFormat df, int num, int count) {
        if (count == 0) {
            return df.format(0);
        }
        double avg = Math.round(num / (float) count);
        return df.format(avg);
    }

    /**
     * 解析Map<String, String>的value值string，返回int
     *
     * @param params 参数
     * @param key    key值
     * @return int
     */
    public static int parasIntFromString(Map<String, String> params, String key) {
        if (TextUtils.isEmpty(params.get(key))) {
            return 0;
        }
        return Integer.parseInt(params.get(key));
    }

    /**
     * 解析Map<String, String>的value值string，返回long
     *
     * @param params 参数
     * @param key    key值
     * @return long
     */
    public static long parasLongFromString(Map<String, String> params, String key) {
        if (TextUtils.isEmpty(params.get(key))) {
            return 0;
        }
        return Long.parseLong(params.get(key));
    }
}
