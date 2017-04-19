package com.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by ricky on 2016/01/29.
 * <p/>
 * 通过sharePreference保存本地数据的工具类
 */
public class C_LocalShareHelper {

    private static final String TAG = "C_LocalShareHelper";

    private final static boolean isDebug = false;

    private static C_LocalShareHelper instance = null;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    private static Context mContext;

    public static void init(Context application) {
        mContext = application;
    }

    private C_LocalShareHelper() {

    }

    /**
     * 获得实体，并给editor赋值,使用默认模型：Context.MODE_PRIVATE
     *
     * @param name sharePreference的名字
     * @return C_LocalShareHelper
     */
    public static C_LocalShareHelper getInstance(String name) {
        return getInstance(name, Context.MODE_MULTI_PROCESS);
    }

    /**
     * 获得实体，并给editor赋值
     *
     * @param name sharePreference的名字
     * @param mode 分享模型
     * @return C_LocalShareHelper
     */
    public static C_LocalShareHelper getInstance(String name, int mode) {
        if (instance == null) {
            synchronized (C_LocalShareHelper.class) {
                if (instance == null) {
                    instance = new C_LocalShareHelper();
                }
            }
        }
        if (mContext == null) {
            throw new RuntimeException("C_LocalShareHelper should init first");
        }
        instance.sharedPreferences = mContext.getSharedPreferences(name, mode);
        instance.editor = instance.sharedPreferences.edit();
        return instance;
    }

    public void putString(String key, String value) {
        instance.editor.putString(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        instance.editor.putBoolean(key, value).commit();
    }

    public void putFloat(String key, float value) {
        instance.editor.putFloat(key, value).commit();
    }

    public void putInt(String key, int value) {
        instance.editor.putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        instance.editor.putLong(key, value).commit();
    }

    public void putStringSet(String key, Set<String> values) {
        instance.editor.putStringSet(key, values).commit();
    }

    public <T> void putData(String key, T data) {
        Gson gson = new Gson();
        instance.editor.putString(key, gson.toJson(data)).commit();
    }

    public void clear() {
        instance.editor.clear().commit();
    }

    public void remove(String key) {
        instance.editor.remove(key).commit();
    }

    public String getString(String key, String defValue) {
        return instance.sharedPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return instance.sharedPreferences.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return instance.sharedPreferences.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return instance.sharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return instance.sharedPreferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return instance.sharedPreferences.getStringSet(key, defValue);
    }

    public <T> T getData(String key, Type typeOfT) {
        Gson gson = new Gson();
        String result = instance.sharedPreferences.getString(key, "");
        return gson.fromJson(result, typeOfT);
    }
}