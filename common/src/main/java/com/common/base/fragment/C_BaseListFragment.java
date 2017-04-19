package com.common.base.fragment;

import com.common.base.delegate.C_BaseRecycleViewDelegate;
import com.common.base.model.C_BaseListDomainModel;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;

/**
 * Created by ricky on 2016/08/22.
 * <p>
 * 装载了list的fragment
 *
 * 如果服务器返回的数据是DataWrapper装载的，就用这个fragment
 */
public abstract class C_BaseListFragment<T, P extends C_BaseBean, Q extends C_BaseRecycleViewDelegate>
        extends C_BaseListFragment2<C_DataWrapper<T>, P, Q> {

    @Override
    protected abstract C_BaseListDomainModel<T, P> getModel();
}
