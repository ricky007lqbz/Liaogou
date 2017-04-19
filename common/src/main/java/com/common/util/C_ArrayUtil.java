package com.common.util;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ricky on 2016/08/11.
 * <p>
 * 数组、list相关处理的工具类
 */
public class C_ArrayUtil {

    /**
     * 获取list里的某项数据（防止数组越界）
     */
    public static <T, P> P getItem(Map<T, P> map, T key, P dfValue) {
        if (!isEmpty(map)) {
            if (map.get(key) != null) {
                return map.get(key);
            }
        }
        return dfValue;
    }

    /**
     * 获取list里的某项数据（防止数组越界）
     */
    public static <T> T getItem(List<T> list, int position) {
        if (!isEmpty(list)) {
            int size = list.size();
            if (position >= 0 && position < size) {
                return list.get(position);
            }
        }
        return null;
    }

    /**
     * 获取list最后项的数据
     */
    public static <T> T getLastItem(@NonNull List<T> list) {
        int size = list.size();
        return list.get(size - 1);
    }

    /**
     * 获取list最后项的数据
     */
    public static <T> T getLastItem(@NonNull T[] array) {
        int size = array.length;
        return array[size - 1];
    }

    /**
     * 根据item获取其所在list里的position
     */
    public static <T> int getPosition(List<T> list, T item) {
        int i = 0;
        for (T t : list) {
            if (t == item) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 获取T[]里的某项数据（防止数组越界）
     */
    public static <T> T getItem(T[] array, int position) {
        if (array != null) {
            int length = array.length;
            if (position >= 0 && position < length) {
                return array[position];
            }
        }
        return null;
    }

    /**
     * 判断list是否为null或空
     */
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断Map是否为null或空
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断SparseArray是否为null或空
     */
    public static boolean isEmpty(SparseArray array) {
        return array == null || array.size() <= 0;
    }

    /**
     * 判断Map是否为null或空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    /**
     * 获取list的item数量
     */
    public static int getCount(List list) {
        return isEmpty(list) ? 0 : list.size();
    }

    /**
     * 获取T[]的item数量
     */
    public static <T> int getCount(T[] array) {
        return isEmpty(array) ? 0 : array.length;
    }
}
