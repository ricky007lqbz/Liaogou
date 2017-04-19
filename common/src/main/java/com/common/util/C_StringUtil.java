package com.common.util;

import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ziwu on 2016/6/6.
 */
public class C_StringUtil {

    /**
     * 多层参数
     *
     * @param url
     * @return ?width=300&height=300/100x100
     */
    public static String[] getUrlParams(String url) {
        if (url == null || url.length() == 0)
            return null;
        try {
            URL u = new URL(url);
            String params = u.getQuery();
            if (params != null && params.length() > 0) {
                if (params.contains("?")) {
                    params = params.substring(params.lastIndexOf("?") + 1);
                }
                if (params.contains("/")) {
                    params = params.split("/")[0];
                }
                if (params.contains("&")) {
                    return params.split("&");
                }
                if (params.contains("%26")) {
                    return params.split("%26");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多层参数
     *
     * @return ?width=300&height=300/100x100
     */
    public static float getUrlParamValues(String param) {
        try {
            if (param.contains("=")) {
                return Float.parseFloat(param.split("=")[1]);
            } else if (param.contains("x")) {
                return Float.parseFloat(param.split("x")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 根据 url 描述的高宽比例  算ImageView 高宽（高宽固定最大值）
     *
     * @return
     */
    public static int[] getImageViewSizeBySize(String url, int max) {
        if ((url == null || url.trim().length() == 0))
            return new int[]{max, max};
        int ivWidth;
        int ivHeight;
        String[] params = getUrlParams(url);
        if (params == null)
            return new int[]{max, max};
        float bmpWidth = getUrlParamValues(params[0]); //  图片宽
        float bmpHeight = getUrlParamValues(params[1]); // 图片高

        if (bmpWidth >= bmpHeight) {
            if (bmpWidth == 0) {
                ivWidth = ivHeight = max;
            } else {
                ivWidth = max;
                ivHeight = (int) (ivWidth * bmpHeight / bmpWidth);
            }
        } else {
            if (bmpHeight == 0) {
                ivWidth = ivHeight = max;
            } else {
                ivHeight = max;
                ivWidth = (int) (bmpWidth * ivHeight / bmpHeight);
            }
        }
        return new int[]{ivWidth, ivHeight};
    }
}
