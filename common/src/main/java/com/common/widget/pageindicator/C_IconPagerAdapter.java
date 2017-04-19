package com.common.widget.pageindicator;

/**
 * 
 * @author Administrator
 * 
 *         tab 设置图标的接口
 */
public interface C_IconPagerAdapter {
	/**
	 * Get icon representing the page at {@code index} in the adapter.
	 */
	int getIconResId(int index);

	// From PagerAdapter
	int getCount();
}
