package com.common.base.fragment;

import com.common.base.delegate.C_PtrRecycleViewDelegate;
import com.common.bean.C_BaseBean;

/**
 * Created by ricky on 2016/08/22.
 * <p>
 * 装载了list的fragment
 * <p>
 * Ds 原始数据(集合) C_Wrapper 包裹的数据
 * <p>
 * UItem  UI 对应绑定的数据类型
 */
public abstract class C_BaseListPtrFragment<Ds, UItem extends C_BaseBean>
        extends C_BaseListFragment<Ds, UItem, C_PtrRecycleViewDelegate> {

    @Override
    protected Class<C_PtrRecycleViewDelegate> getDelegateClass() {
        return C_PtrRecycleViewDelegate.class;
    }
}
