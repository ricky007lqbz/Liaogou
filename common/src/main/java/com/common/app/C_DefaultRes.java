package com.common.app;

/**
 * Created by ricky on 2016/08/19.
 * <p>
 * 默认资源
 */
public class C_DefaultRes {

    private int dfEmptyImgRes;
    private String dfEmptyMsg;
    private int dfErrorImgRes;
    private String dfErrorMsg;

    private static C_DefaultRes instance;

    public static C_DefaultRes getInstance() {
        if (instance == null) {
            instance = new C_DefaultRes();
        }
        return instance;
    }

    public void init(int dfEmptyImgRes,
                     String dfEmptyMsg,
                     int dfErrorImgRes,
                     String dfErrorMsg) {
        this.dfEmptyImgRes = dfEmptyImgRes;
        this.dfEmptyMsg = dfEmptyMsg;
        this.dfErrorImgRes = dfErrorImgRes;
        this.dfErrorMsg = dfErrorMsg;
    }

    /**
     * 获取默认的空页面显示的图片资源
     */
    public int getDfEmptyImgRes() {
        return dfEmptyImgRes;
    }

    /**
     * 获取空界面默认显示的文字提示
     */
    public String getDfEmptyMsg() {
        return dfEmptyMsg;
    }

    /**
     * 获取默认的空页面显示的图片资源
     */
    public int getDfErrorImgRes() {
        return dfErrorImgRes;
    }

    /**
     * 获取空界面默认显示的文字提示
     */
    public String getDfErrorMsg() {
        return dfErrorMsg;
    }
}
