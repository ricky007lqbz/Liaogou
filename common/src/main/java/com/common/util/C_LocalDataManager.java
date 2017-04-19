package com.common.util;

/**
 * Created by ricky on 2016/06/28.
 * <p/>
 * 本地保存的数据的管理类
 */
public class C_LocalDataManager {

    //---------------------七牛相关-------------------------//
    private static final String NAME_QI_NIU = "name_qi_niu";
    private static final String KEY_QI_NIU_DOMAIN = "key_qi_niu_domain";
    private static final String KEY_QI_NIU_TOKEN = "key_qi_niu_token";

    //获取本地保存七牛域名
    public static String getQiNiuDomain() {
        return C_LocalShareHelper.getInstance(NAME_QI_NIU).getString(KEY_QI_NIU_DOMAIN, "");
    }

    //本地保存七牛域名
    public static void putQiNiuDomain(String domain) {
        C_LocalShareHelper.getInstance(NAME_QI_NIU).putString(KEY_QI_NIU_DOMAIN, domain);
    }

    //获取本地保存七牛token
    public static String getQiNiuToken() {
        return C_LocalShareHelper.getInstance(NAME_QI_NIU).getString(KEY_QI_NIU_TOKEN, "");
    }

    //本地保存七牛token
    public static void putQiNiuToken(String token) {
        C_LocalShareHelper.getInstance(NAME_QI_NIU).putString(KEY_QI_NIU_TOKEN, token);
    }

    //-----------------消息通知（系统、活动、私信）相关----------------------//
    public static final String NAME_MESSAGE = "name_message";
    public static final String KEY_MESSAGE_SYS_TIMESTAMP = "key_message_sys_timestamp";
    public static final String KEY_MESSAGE_EVENTS_TIMESTAMP = "key_message_events_timestamp";

    //本地保存系统消息查看的时间戳
    public static void putSysTimestamp(long timestamp) {
        C_LocalShareHelper.getInstance(NAME_MESSAGE).putLong(KEY_MESSAGE_SYS_TIMESTAMP, timestamp);
    }

    //获取本地保存系统消息查看的时间戳
    public static long getSysTimestamp() {
        return C_LocalShareHelper.getInstance(NAME_MESSAGE).getLong(KEY_MESSAGE_SYS_TIMESTAMP, System.currentTimeMillis());
    }

    //本地保存活动消息查看的时间戳
    public static void putEventsTimestamp(long timestamp) {
        C_LocalShareHelper.getInstance(NAME_MESSAGE).putLong(KEY_MESSAGE_EVENTS_TIMESTAMP, timestamp);
    }

    //本地保存活动消息查看的时间戳
    public static long getEventsTimestamp() {
        return C_LocalShareHelper.getInstance(NAME_MESSAGE).getLong(KEY_MESSAGE_EVENTS_TIMESTAMP, System.currentTimeMillis());
    }

    public static void clearMsgTimestamp() {
        C_LocalShareHelper.getInstance(NAME_MESSAGE).clear();
    }

    //----------------------第一次启动标识----------------------//
    public static final String NAME_FIRST_LAUNCHER = "name_first_launcher";
    public static final String KEY_FIRST_LAUNCHER = "key_first_launcher";

    //本地保存是否是第一次启动的标识
    public static void putFirstLauncherBoolean() {
        C_LocalShareHelper.getInstance(NAME_FIRST_LAUNCHER).putBoolean(KEY_FIRST_LAUNCHER, false);
    }

    //获取本地保存是否第一次启动的标识
    public static boolean getFirstLauncherBoolean() {
        return C_LocalShareHelper.getInstance(NAME_FIRST_LAUNCHER).getBoolean(KEY_FIRST_LAUNCHER, true);
    }

    public static final String NAME_APP = "app_version_code";
    /**
     * 标记app升级变化
     * <p/>
     * （当前启动app执行信息更新后的版本号/当前启动app执行信息更新前的旧版本号）
     */
    public static final String KEY_VERSION_CODE = "app_version_code";

    public static final String KEY_VERSION_NAME = "app_version_name"; //更新版本名
    public static final String KEY_VERSION_INFO = "app_version_info"; // 更新版本信息
    public static final String KEY_DOWNLOAD_URL = "app_version_download_url"; // 下载链接
    public static final String KEY_UPGRADE_VERSION_CODE = "app_upgrade_version_code"; // 更新版本
    public static final String KEY_UPGRADE_STATUS = "app_upgrade_status"; // 更新版本文件路径

    public static final String KEY_VERIFY_STATUS = "app_verify_status"; // app 审核状态

    /**
     * 保存的是更新前的版本号
     *
     * @param type
     */
    public static void putAppPreVersionCode(int type) {
        C_LocalShareHelper.getInstance(NAME_APP).putInt(KEY_VERSION_CODE, type);
    }

    /**
     * @return app 版本号   更新后保存的版本号
     */
    public static int getAppPreVersionCode() {
        return C_LocalShareHelper.getInstance(NAME_APP).getInt(KEY_VERSION_CODE, 1);
    }

    /**
     * app 更新下载链接点
     *
     * @param url
     */
    public static void putAppDownloadUrl(String url) {
        C_LocalShareHelper.getInstance(NAME_APP).putString(KEY_DOWNLOAD_URL, url);
    }

    public static String getAppDownloadUrl() {
        return C_LocalShareHelper.getInstance(NAME_APP).getString(KEY_DOWNLOAD_URL, null);
    }


    public static void putAppUpgradeVersionName(String name) {
        C_LocalShareHelper.getInstance(NAME_APP).putString(KEY_VERSION_NAME, name);
    }

    public static String getAppUpgradeVersionName() {
        return C_LocalShareHelper.getInstance(NAME_APP).getString(KEY_VERSION_NAME, null);
    }


    public static void putAppUpgradeVersionInfo(String info) {
        C_LocalShareHelper.getInstance(NAME_APP).putString(KEY_VERSION_INFO, info);
    }

    public static String getAppUpgradeVersionInfo() {
        return C_LocalShareHelper.getInstance(NAME_APP).getString(KEY_VERSION_INFO, null);
    }


    public static void putAppUpgradeVersionCode(String code) {
        C_LocalShareHelper.getInstance(NAME_APP).putString(KEY_UPGRADE_VERSION_CODE, code);
    }

    public static String getAppUpgradeVersionCode() {
        return C_LocalShareHelper.getInstance(NAME_APP).getString(KEY_UPGRADE_VERSION_CODE, null);
    }

    public static void putAppVerifyStatus(boolean isVerified) {
        C_LocalShareHelper.getInstance(NAME_APP).putBoolean(KEY_VERIFY_STATUS, isVerified);
    }

    public static boolean getAppVerifyStatus() {
        return C_LocalShareHelper.getInstance(NAME_APP).getBoolean(KEY_VERIFY_STATUS, false);
    }


    //===================cookie相关======================//
    //网络cookie本地保存名称
    private static final String NAME_COOKIE = "name_cookie";
    private static final String KEY_COOKIE = "key_cookie";

    //本地保存cookie
    public static void putCookie(String cookies) {
        C_LocalShareHelper.getInstance(NAME_COOKIE).putString(KEY_COOKIE, cookies);
    }

    //读取本地保存的cookie
    public static String getCookies() {
        return C_LocalShareHelper.getInstance(NAME_COOKIE).getString(KEY_COOKIE, "");
    }

    //读取本地保存的cookie
    public static void clearCookies() {
        C_LocalShareHelper.getInstance(NAME_COOKIE).clear();
    }

    //======================设备id保存===========================//
    private static final String NAME_DEVICE = "name_device";
    private static final String KEY_DEVICE_ID = "key_device_id";

    //本地保存key_device_id
    public static void putDeviceId(String deviceId) {
        C_LocalShareHelper.getInstance(NAME_DEVICE).putString(KEY_DEVICE_ID, deviceId);
    }

    //读取本地保存的key_device_id
    public static String getDeviceId() {
        return C_LocalShareHelper.getInstance(NAME_DEVICE).getString(KEY_DEVICE_ID, "0");
    }
}
