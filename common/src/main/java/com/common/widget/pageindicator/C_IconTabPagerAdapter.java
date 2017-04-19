package com.common.widget.pageindicator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 *
 * @author Administrator
 * 
 *         带Icon的 viewpager 的适配器
 */
public class C_IconTabPagerAdapter extends FragmentPagerAdapter implements
        C_IconPagerAdapter {
	private Context context;
	private List<Integer> list;
	private FragmentManager fm;

	public C_IconTabPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public C_IconTabPagerAdapter(FragmentManager fm, Context context, List list) {
		this(fm);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getIconResId(int index) {
		return list.get(index);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return false;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fm.findFragmentById(arg0);
	}

}
