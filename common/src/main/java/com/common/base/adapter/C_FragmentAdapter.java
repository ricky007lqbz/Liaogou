package com.common.base.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.common.Common;
import com.common.base.fragment.C_BaseFragment;
import com.common.util.C_ArrayUtil;

/**
 * Created by ricky on 2016/05/26.
 * <p>
 * fragment ViewPage的adapter
 */
public class C_FragmentAdapter<T extends C_BaseFragment> extends FragmentPagerAdapter {

    private Context mContext;
    private Bundle[] mBundles;
    private SparseArray<T> mFragments;
    private String[] mTitles;
    private String[] mClasses;

    public C_FragmentAdapter(Context context, Bundle[] bundles, FragmentManager fm, String[] classes, String[] titles) {
        super(fm);
        this.mContext = context;
        this.mBundles = bundles;
        this.mFragments = new SparseArray<>();
        this.mTitles = titles;
        this.mClasses = classes;
    }

    public C_FragmentAdapter(Context context, FragmentManager fm, SparseArray<T> fragments, String[] titles) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //禁用系统自带的销毁功能
    }

    @Override
    public T getItem(int position) {
        T fragment = mFragments.get(position);
        if (null == fragment) {
            Bundle bundle = C_ArrayUtil.getItem(mBundles, position);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(Common.key.TITLE, C_ArrayUtil.getItem(mTitles, position));
            fragment = (T) Fragment.instantiate(mContext, C_ArrayUtil.getItem(mClasses, position), bundle);
            mFragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return C_ArrayUtil.getCount(mTitles);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null != mTitles ? mTitles[position] : "";
    }

}

