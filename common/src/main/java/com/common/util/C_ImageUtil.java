package com.common.util;

import android.content.Context;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by ricky on 2016/08/31.
 * <p>
 * 图片尺寸相关的工具类
 */
public class C_ImageUtil {
    /**
     * @param context
     * @param url
     * @return
     */
    public static int[] getLargerHeightImageViewSize(Context context, String url, int maxHeight) {
        String[] params = getUrlParams(url);
        // WARP_CONETENT 大图超出屏幕范围?
        if (params == null)
            return new int[]{ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT};
        float width = getUrlParamValues(params[0]);
        float height = getUrlParamValues(params[1]);
        if (width != 0 && maxHeight != 0) {
            float scale = (float) width / (float) maxHeight;
            height = (int) (height / scale);
            width = maxHeight;
        }
        return new int[]{(int) width, (int) height};
    }

    /**
     * value 值是字符串
     *
     * @param param
     * @return width=100
     */
    public static String getUrlParamValue(String param) {
        if (param != null && param.contains("=")) {
            return param == null ? "" : param.split("=")[1];
        }
        return "";
    }

    /**
     * 将get请求的参数部分做解析成map
     *
     * @param urlString
     * @return
     * @author 谢治娴
     */
    public static HashMap<String, String> getUrlParamMaps(String urlString) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (urlString != null && urlString.contains("?") && urlString.contains("=")) {
            String paramString = urlString.substring(urlString.indexOf("?") + 1);
            String[] paramStr = paramString.split("&");
            for (int i = 0; i < paramStr.length; i++) {
                String[] param = paramStr[i].split("=");
                map.put(param[0], param[1]);
            }
        }
        return map;
    }

    // 非合法url，自定义url 参数截取 （qiumiaction://squareattitudelist?id=100168）
    public static String getUrlParam(String url) {
        if (url == null || url.length() == 0)
            return null;
        String[] str = url.split("\\?");
        if (str.length > 1)
            return url.split("\\?")[1];
        return null;
    }

    // public

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
     * 获取原始图片
     */
    public static String getOrigImgUrl(String url) {
        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }
        if (url.contains("/header")) {
            url = url.replaceAll("/header", "");
        }
        return url;
    }
}
