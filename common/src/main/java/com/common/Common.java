package com.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by ricky on 2016/08/17.
 * <p/>
 * 通用的常量
 */
public class Common {

    public static void init(int dfCircleImg, int dfImg, String apiLogin) {
        image.DF_CIRCLE_IMG = dfCircleImg;
        image.DF_IMG = dfImg;
        API_LOGIN = apiLogin;

    }

    public static final int CHOOSE_PHOTO_LIMIT = 9;

    public static String PACKAGE_NAME = "";

    /*app的名称*/
    public static final String APP_NAME = "";
    /*登录接口关键字(用于判断是否保存cookie)*/
    public static String API_LOGIN;

    /*是否处于debug模式下*/
    public static boolean IS_DEBUG = true;

    /* SD卡对应项目目录名称 */
    public static final String SDCARD_NAME = Environment
            .getExternalStorageDirectory() + File.separator;

    /* 球迷路径 */
    public static final String APP_DIR_NAME = "qiumi";

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    /* 拍照缓存目录 + 下载图片目录 */
    public static final String ROOT_PATH = SDCARD_NAME
            + APP_DIR_NAME + File.separator;

    /**
     * 图片相关的常量
     */
    public static final class image {
        //图片下载路径
        public static final String IMAGE_DOWNLOAD_PATH = ROOT_PATH;
        //图片缓存路径
        public static final String IMAGE_CACHE_PATH = ROOT_PATH + "imageCache" + File.separator;
        //默认加载的圆形图片
        public static int DF_CIRCLE_IMG = R.color.full_transparent;
        //默认加载的一般图片
        public static int DF_IMG = R.color.bg_df_img;
    }

    /**
     * 网络相关的常量
     */
    public static final class net {
        //服务返回数据成功的code
        public static final int CODE_SUCCESS = 0;
        //服务器异常
        public static final int CODE_ERROR = 999;
        //缓存
        public static final int CODE_CACHE = 304;
        //域名
        public static String HOST = "https://api.hisoccer.cn";
        //网络缓存
        public static final String HTTP_CATCH_PATH = ROOT_PATH + "httpCache" + File.separator;
        //网络超时
        public static final int TIME_OUT = 5000;
        //（主）单独请求队列（带加载下一页的请求）
        public static final int SINGLE_QUEUE = -1;
        //（次）第一个请求队列
        public static final int FIRST_QUEUE = 0x11111;
        //（次）第二个请求队列
        public static final int SECOND_QUEUE = 0x11112;
        //（次）第三个请求队列
        public static final int THIRD_QUEUE = 0x11113;
        //每页加载的页数
        public static final int PAGE_SIZE = 20;
    }

    /**
     * 键值对相关
     */
    public static final class key {
        //标题
        public static final String TITLE = "title";
        //id
        public static final String ID = "id";
        //String
        public static final String STRING = "string";
        //bean
        public static final String BEAN = "bean";
        //int
        public static final String INT = "int";
        //boolean
        public static final String BOOLEAN = "boolean";
        //boolean
        public static final String ARRAY = "array";
        // 是否要取数据
        public static final String REQ = "req";

        public final static String ACITON = "action";
        public final static String COLOR = "color";
        public final static String TAG = "tag";
    }

    /**
     * list item位置相关
     */
    public static final class position_type {
        //上
        public static final int TOP = 1;
        //中
        public static final int CENTER = 0;
        //下
        public static final int BOTTOM = 2;
        //单个
        public static final int SINGLE = 3;
    }

    /**
     * list item对应的view type
     */
    public static final class view_type {
        //挂顶
        public static final int PINNED = -100;
        // 默认 item 类型
        public static final int DEFAULT = 0;
        //第二种 item 类型
        public static final int SECOND = 1;
        //第三种 item 类型
        public static final int THIRD = 2;
        //组的顶部
        public static final int TOP = 3;
        //组的底部
        public static final int BOTTOM = 4;

        public static final int FOURTH = 5;

        public static final int FIFTH = 6;

        /* Group分组列表项标志*/
        //父项
        public final static int PARENT = 1;
        //子项
        public final static int CHILDREN = 0;
    }
}
