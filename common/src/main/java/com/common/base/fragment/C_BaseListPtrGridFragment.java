package com.common.base.fragment;

import com.common.base.delegate.C_PtrGridRecycleViewDelegate;
import com.common.bean.C_BaseBean;

/**
 * Created by ricky on 2016/08/22.
 * <p>
 * 装载了list(grid)的fragment
 */
public abstract class C_BaseListPtrGridFragment<T, P extends C_BaseBean>
        extends C_BaseListFragment<T, P, C_PtrGridRecycleViewDelegate> {

    @Override
    protected Class<C_PtrGridRecycleViewDelegate> getDelegateClass() {
        return C_PtrGridRecycleViewDelegate.class;
    }
}
