package com.common.base.activity;

import com.common.base.delegate.C_BaseRecycleViewDelegate;
import com.common.base.model.C_BaseListDomainModel;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;

/**
 * Created by ricky on 2016/05/24.
 * <p/>
 * 基于RecycleView和activity的presenter基类
 *
 * @param <T> 网络返回的列表项对应原始数据类型
 * @param <P> view所需要的数据类型
 * @param <Q> 需要显示的View (extends C_BaseRecycleViewDelegate)
 */
public abstract class C_BaseListActivity<T, P extends C_BaseBean, Q extends C_BaseRecycleViewDelegate>
        extends C_BaseListActivity2<C_DataWrapper<T>, P, Q> {

    @Override
    protected abstract C_BaseListDomainModel<T, P> getModel();
}
