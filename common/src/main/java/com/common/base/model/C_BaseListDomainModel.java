package com.common.base.model;

import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;

/**
 * Created by ricky on 2016/05/30.
 * <p>
 * 通过网络请求获取原始数据，并返回View所需要的数据（domain）的model基类
 *
 * @param <T> 网络请求返回的原始数据
 * @param <P> View所需要的数据
 */
public abstract class C_BaseListDomainModel<T, P extends C_BaseBean> extends C_BaseListModel<C_DataWrapper<T>, P> {

}
