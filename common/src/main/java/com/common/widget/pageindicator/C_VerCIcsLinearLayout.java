package com.common.widget.pageindicator;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 
 * @author Administrator
 *
 *         垂直的indicator容器
 */
public class C_VerCIcsLinearLayout extends C_IcsLinearLayout {
	public C_VerCIcsLinearLayout(Context context, int themeAttr) {
		super(context, themeAttr);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,
				MATCH_PARENT));
	}
}
