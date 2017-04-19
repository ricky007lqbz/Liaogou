package com.common.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.common.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ricky on 2016/06/14.
 * <p/>
 * 文字处理工具类
 */
public class C_TextUtil {

    /**
     * 设置匹配文字颜色的方法
     *
     * @param originalText 原始字符串
     * @param matchingText 搜索字符串
     * @param color        颜色
     */
    public static SpannableStringBuilder getMatchedStringBuilder(String originalText, String matchingText, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);
        if (TextUtils.isEmpty(originalText) || TextUtils.isEmpty(matchingText)) {
            return builder;
        }
        String temp = originalText;
        int lastStart;
        while (temp.contains(matchingText)) {
            lastStart = temp.lastIndexOf(matchingText);
            if (lastStart != -1) {
                temp = temp.substring(0, lastStart);
                builder.setSpan(new ForegroundColorSpan(color),
                        lastStart, lastStart + matchingText.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }
    /**
     * 设置匹配文字颜色的方法
     *
     * @param originalText 原始字符串
     * @param matchingText 搜索字符串
     *
     * 使用默认颜色
     */
    public static SpannableStringBuilder getMatchedStringBuilder(String originalText, String matchingText) {
        return getMatchedStringBuilder(originalText, matchingText, C_ResUtil.getSrcColor(R.color.color_main));
    }

    /**
     * 设置传入文字的颜色
     *
     * @param originalText 原始字符串
     * @param color        颜色
     */
    public static SpannableStringBuilder getMatchedStringBuilder(String originalText, int color) {
        return getMatchedStringBuilder(originalText, originalText, color);
    }

    /**
     * 设置匹配文字颜色的方法(一个文字段)
     *
     * @param text     原始文字
     * @param startStr 开始的字符串
     * @param endStr   结束的字符串
     * @param color    定义的文字颜色
     */
    public static SpannableStringBuilder getSpecifiedStringBuilder(String text, String startStr, String endStr, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(startStr) || TextUtils.isEmpty(endStr)) {
            return builder;
        }
        String temp = text;
        int lastStart;
        int lastEnd;
        while (temp.contains(endStr)) {
            lastEnd = temp.lastIndexOf(endStr);
            if (lastEnd != -1) {
                temp = temp.substring(0, lastEnd);
                lastStart = temp.lastIndexOf(startStr);
                if (lastStart != -1) {
                    temp = temp.substring(0, lastStart);
                    builder.setSpan(new ForegroundColorSpan(color),
                            lastStart, lastEnd + endStr.length() - 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    public static String getSpecifiedText(String startStr, String endStr, String text) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(startStr) || TextUtils.isEmpty(endStr)) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        String temp = text;
        while (temp.contains(startStr)) {
            int start = temp.indexOf(startStr);
            temp = temp.substring(start);
            if (temp.contains(endStr)) {
                int end = temp.indexOf(endStr);
                str.append(temp.subSequence(0, end)).append(" ");
                temp = temp.substring(end + 1);
            } else {
                return str.append(temp).toString();
            }
        }
        return str.toString();
    }

    /**
     * 网络get请求，后缀格式转换
     *
     * @param map 输入map数据
     * @return 返回String
     */
    public static String mapToString(Map<String, String> map) {
        StringBuilder s = new StringBuilder("?");
        Set keys = map.keySet();

        String value = null;
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            try {
                value = map.get(key);
                if (value == null) value = NULL_STRING;
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            s.append(key).append("=").append(value).append("&");
        }
        return s.substring(0, s.length() - 1);
    }

    private final static String NULL_STRING = "";

    /**
     * 网络get请求，后缀格式转换成 - - 格式的
     */
    public static String mapToBarUrlParam(Map<String, String> map, List<String> list) {
        StringBuilder sb = new StringBuilder("/");
        String value = null;
        for (String key : list) {
            try {
                value = map.get(key);
                if (value == null) value = NULL_STRING;
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(value).append("-");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static boolean isHasSpecificSymbol(String text) {
        String[] regs = new String[]{
                "@",
                "#",
                "\\",
                "/",
                " ",
                "\n",
                "\t",
                "\\l",
        };
        for (String reg : regs) {
            if (text.contains(reg)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 设置匹配文字颜色的方法
     *
     * @param originalText 原始字符串
     * @param matchingText 搜索字符串
     * @param color        颜色
     * @return SpannableStringBuilder
     */
    public static void setMatchedTextColor(TextView tv, String originalText, String matchingText, int color) {
        if (TextUtils.isEmpty(originalText) || TextUtils.isEmpty(matchingText)) {
            tv.setText(originalText);
            return;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(originalText);
        String temp = originalText;
        int lastStart = -1;
        while (temp.contains(matchingText)) {
            lastStart = temp.lastIndexOf(matchingText);
            if (lastStart != -1) {
                temp = temp.substring(0, lastStart);
                builder.setSpan(new ForegroundColorSpan(color),
                        lastStart, lastStart + matchingText.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(builder);
    }

}
