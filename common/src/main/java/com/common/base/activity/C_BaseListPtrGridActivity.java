package com.common.base.activity;

import com.common.base.delegate.C_PtrGridRecycleViewDelegate;
import com.common.bean.C_BaseBean;

/**
 * Created by ricky on 2016/09/01.
 * <p>
 * 装载了list(grid)的activity
 */
public abstract class C_BaseListPtrGridActivity<T, P extends C_BaseBean> extends C_BaseListActivity<T, P, C_PtrGridRecycleViewDelegate> {
    @Override
    protected Class<C_PtrGridRecycleViewDelegate> getDelegateClass() {
        return C_PtrGridRecycleViewDelegate.class;
    }

}
