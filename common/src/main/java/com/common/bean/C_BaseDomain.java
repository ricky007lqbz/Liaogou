package com.common.bean;

import android.text.TextUtils;

/**
 * Created by ricky on 2016/08/17.
 * <p>
 * 最基本的domain（数据转换层）
 */
public class C_BaseDomain<T> extends C_BaseBean {

    /**
     * 原始数据
     */
    protected T _raw_data;

    /**
     * domain通用的文字name
     */
    protected String name;

    public T get_raw_data() {
        return _raw_data;
    }

    public void set_raw_data(T _raw_data) {
        this._raw_data = _raw_data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 把图片后面的压缩协议去掉
     * eg http://img.lanzd.com/o_1ao3s52cgme2m8k1jtm1mbp1t8u4o.png!80x80
     * return http://img.lanzd.com/o_1ao3s52cgme2m8k1jtm1mbp1t8u4o.png
     */
    public static String getOriginalImgUrl(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            return imgUrl.replaceAll("![0-9]{1,4}x[0-9]{1,4}", "");
        }
        return "";
    }

    public static String replaceAllSpace(String text) {
        String str = "";
        if (!TextUtils.isEmpty(text)) {
            str = text.replaceAll(" ", "");
        }
        return str;
    }
}
