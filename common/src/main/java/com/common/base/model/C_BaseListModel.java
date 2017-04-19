package com.common.base.model;

import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;

import java.util.List;

/**
 * Created by ricky on 2016/05/30.
 * <p>
 * 通过网络请求获取原始数据，并返回View所需要的数据（domain）的model基类
 *
 * @param <T> 网络请求返回的原始数据
 * @param <P> View所需要的数据
 */
public abstract class C_BaseListModel<T, P extends C_BaseBean> extends C_BaseDomainModel<T, C_DataWrapper<List<P>>> {

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 加载上一页;
     */
    public void onPrePage(P p) {

    }

    /**
     * 加载下一页;
     */
    public void onNextPage(P p) {

    }
}
