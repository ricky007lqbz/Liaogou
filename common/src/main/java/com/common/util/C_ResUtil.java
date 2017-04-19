package com.common.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.common.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Ziwu on 2016/5/20.
 * <p/>
 * <p/>
 * 资源文件工具
 */
public class C_ResUtil {

    private static Resources mResource;

    public static void init(Context context) {
        mResource = context.getResources();
    }

    public static Resources getResource() {
        if (mResource == null) {
            throw new RuntimeException("C_ResUtil should init first");
        }
        return mResource;
    }

    /**
     * @param id R.string.*
     * @return String res
     */
    public static String getSrcString(int id) {
        return getResource().getString(id);

    }

    /**
     * @param color R.color.*
     * @return int color
     */
    public static int getSrcColor(int color) {
        return getResource().getColor(color);
    }


    public static Drawable getDrawable(int drawable) {
        if (drawable <= 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResource().getDrawable(drawable);
        } else {
            return getResource().getDrawable(drawable);
        }
    }


    /**
     * @param sp dimens 资源文件 R.dimens
     * @return 字体大小
     */
    public static int getTextSize(int sp) {
        return (int) getResource().getDimension(sp);
    }

    /**
     * @param dimen dimens 资源文件 R.dimens
     * @return dimen
     */
    public static int getDimens(int dimen) {
        if (dimen == 0) {
            return 0;
        }
        return (int) getResource().getDimension(dimen);
    }

    /**
     * #话题颜色#
     */
    public static ColorStateList getSpanColor() {
        ColorStateList cslLink = null;
        XmlResourceParser xmlColor = getResource().getXml(R.color.c_topic_text_color);
        try {
            cslLink = ColorStateList.createFromXml(getResource(), xmlColor);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return cslLink;
    }

    /**
     * #话题颜色#
     */
    public static ColorStateList getC1C1dColor() {
        ColorStateList cslLink = null;
        XmlResourceParser xmlColor = getResource().getXml(R.color.c_selector_color_first);
        try {
            cslLink = ColorStateList.createFromXml(getResource(), xmlColor);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return cslLink;
    }


    /**
     * #话题颜色#
     */
    public static ColorStateList getG7G7dColor() {
        ColorStateList cslLink = null;
        XmlResourceParser xmlColor = getResource().getXml(R.color.c_selector_text_white);
        try {
            cslLink = ColorStateList.createFromXml(getResource(), xmlColor);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return cslLink;
    }
}
