package com.common.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.Common;
import com.common.R;
import com.common.base.listener.C_OnViewListener;
import com.common.util.C_ResUtil;
import com.common.util.C_ViewSetUtils;
import com.common.util.glide.C_ImageLoader;
import com.common.widget.C_LeftIconTextView;

import butterknife.ButterKnife;

/**
 * Created by ricky on 2016/05/27.
 * <p/>
 * 用来处理recyclerView每项item的Holder帮助抽象类
 */
public abstract class C_BaseHHelper<T, P extends C_BaseMultiRecyclerHolder> implements C_OnViewListener<T> {

    protected final static String goneText = "";

    public Context context;

    public C_BaseHHelper(Context context) {
        this.context = context;
    }

    public void initHolder(P holder) {
        ButterKnife.bind(this, holder.getConvertView());
        holder.registerOnViewListener(this);
    }

    public void refreshData(P holder, T item) {
        ButterKnife.bind(this, holder.getConvertView());
        holder.setItem(item);
    }

    public abstract int getLayoutId();

    public abstract void convert(int position, P holder, T item);

    @Override
    public void onClick(View v, int position, C_BaseMultiRecyclerHolder holder, T item) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event, int position, C_BaseMultiRecyclerHolder holder, T item) {
        return false;
    }

    @Override
    public void onTextChange(CharSequence s, int position, C_BaseMultiRecyclerHolder holder, T item) {

    }

    protected void setText(@NonNull TextView tv, String text) {
        setText(tv, text, "");
    }

    protected void setText(@NonNull TextView tv, String text, float lineSpace) {
        setText(tv, text, lineSpace, "");
    }

    protected void setText(@NonNull C_LeftIconTextView tv, String text) {
        setText(tv, text, "");
    }

    protected void setNumText(TextView tv, String text, String dfStr) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text) && !text.equals("0")) {
            tv.setText(text);
        } else {
            tv.setText(dfStr);
        }
    }

    protected void setNumTextDfGone(TextView tv, String text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text) && !text.equals("0")) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    protected void setText(TextView tv, String text, String dfStr) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(dfStr);
        }
    }

    protected void setText(C_LeftIconTextView tv, String text, String dfStr) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(dfStr);
        }
    }

    /**
     * 设置文本（带文本间距）
     */
    protected void setText(final TextView tv, String text, final float lineSpace, String dfStr) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.post(new Runnable() {
                @Override
                public void run() {
                    if (tv.getLineCount() > 1) {
                        tv.setLineSpacing(0, lineSpace);
                    } else {
                        tv.setLineSpacing(0, 1.0f);
                    }
                }
            });
        } else {
            tv.setText(dfStr);
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setTextDfGone(TextView tv, String text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setTextDfGone(final TextView tv, String text, final float lineSpace) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
            tv.post(new Runnable() {
                @Override
                public void run() {
                    if (tv.getLineCount() > 1) {
                        tv.setLineSpacing(0, lineSpace);
                    } else {
                        tv.setLineSpacing(0, 1.0f);
                    }
                }
            });
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setTextDfGone(C_LeftIconTextView tv, String text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setText(TextView tv, SpannableStringBuilder text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setText("");
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setText(C_LeftIconTextView tv, SpannableStringBuilder text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setText("");
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setTextDfGone(TextView tv, SpannableStringBuilder text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文字，如果文字为空，不显示TextView
     *
     * @param tv   TextView
     * @param text 文字
     */
    protected void setTextDfGone(C_LeftIconTextView tv, SpannableStringBuilder text) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    protected void setCircleImg(ImageView iv, String url, int defResId) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            C_ImageLoader.setCircleImageByUrl(context, iv, url, defResId);
        } else {
            iv.setImageResource(defResId);
        }
    }

    protected void setCircleImg(ImageView iv, String url) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            C_ImageLoader.setCircleImageByUrl(context, iv, url);
        } else {
            iv.setImageDrawable(null);
        }
    }

    protected void setRoundImg(@NonNull ImageView iv, String url) {
        C_ImageLoader.setRoundImageByUrl(context, iv, url, Common.image.DF_IMG, 2);
    }

    protected void setRoundImg(@NonNull ImageView iv, String url, int radiusDp) {
        C_ImageLoader.setRoundImageByUrl(context, iv, url, Common.image.DF_IMG, radiusDp);
    }

    protected void setImg(@NonNull ImageView iv, String url, int defResId) {
        C_ImageLoader.setImageByUrlCenterCrop(context, iv, url, defResId);
    }

    protected void setImg(@NonNull ImageView iv, String url) {
        setImg(iv, url, Common.image.DF_IMG);
    }

    protected void setImg(ImageView iv, int resId, int defResId) {
        if (iv == null) {
            return;
        }
        if (resId != 0) {
            iv.setImageResource(resId);
        } else {
            iv.setImageResource(defResId);
        }
    }

    protected void setImg(@NonNull ImageView iv, int resId) {
        setImg(iv, resId, Common.image.DF_IMG);
    }

    protected void setImgCenterCropDfGone(ImageView iv, String url) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlCenterCrop(context, iv, url);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    protected void setImgFitCenterDfGone(ImageView iv, String url) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlFitCenter(context, iv, url);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    protected void setImgFitWidthDfGone(ImageView iv, String url, int widthSet) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlFitWidth(context, iv, url, widthSet);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    protected void setGifFitWidthDfGone(ImageView iv, String url, int widthSet, int height, boolean isLoadUnderWifi) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setGifByUrlFitWidth(context, iv, url, Common.image.DF_IMG, widthSet, height, isLoadUnderWifi);
        } else {
            iv.setVisibility(View.GONE);
        }
    }


    protected void setImgFitMaxWidthDfGone(ImageView iv, String url, int maxWidthSet) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlFitMaxWidth(context, iv, url, maxWidthSet);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    /**
     * 以240dp为最大宽度加载图片
     */
    protected void setImgWith240dpDfGone(ImageView iv, String url) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlFitMaxWidth(context, iv, url, C_ResUtil.getDimens(R.dimen._240dp));
        } else {
            iv.setVisibility(View.GONE);
        }
    }


    /**
     * 以屏幕尺寸为最大宽度加载图片
     */
    protected void setImgFitScreenWidthDfGone(ImageView iv, String url) {
        if (iv == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            iv.setVisibility(View.VISIBLE);
            C_ImageLoader.setImageByUrlFitScreenWidth(context, iv, url);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置最顶项divider不可见
     */
    protected void setDividerTopGone(View divider, int positionType) {
        if (divider != null) {
            switch (positionType) {
                case Common.position_type.TOP:
                case Common.position_type.SINGLE:
                    divider.setVisibility(View.GONE);
                    break;
                default:
                    divider.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 设置最底项divider不可见
     */
    protected void setDividerBottomGone(View divider, int positionType) {
        if (divider != null) {
            switch (positionType) {
                case Common.position_type.BOTTOM:
                case Common.position_type.SINGLE:
                    divider.setVisibility(View.GONE);
                    break;
                default:
                    divider.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 设置仅最底项divider可见
     */
    protected void setDividerOnlyBottomVisible(View divider, int positionType) {
        if (divider != null) {
            switch (positionType) {
                case Common.position_type.BOTTOM:
                case Common.position_type.SINGLE:
                    divider.setVisibility(View.VISIBLE);
                    break;
                default:
                    divider.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 设置底部divider
     */
    protected void setDividerType(View divider, int positionType, int dfMarginLeft, int dfMarginTop, int dfMarginRight, int dfMarginBottom) {
        if (divider != null) {
            switch (positionType) {
                case Common.position_type.BOTTOM:
                case Common.position_type.SINGLE:
                    C_ViewSetUtils.setMargins(divider, 0, dfMarginTop, 0, dfMarginBottom);
                    break;
                case Common.position_type.CENTER:
                default:
                    C_ViewSetUtils.setMargins(divider, dfMarginLeft, dfMarginTop, dfMarginRight, dfMarginBottom);
                    break;
            }
        }
    }

    /**
     * 设置底部divider
     */
    protected void setDividerType(View divider, int positionType, int dfMarginStart, int dfMarginEnd) {
        setDividerType(divider, positionType, dfMarginStart, 0, dfMarginEnd, 0);
    }

    /**
     * 根据position设置item边距, 默认8dp
     */
    protected void setItemMargin(View view, int positionType) {
        setItemMargin(view, positionType, R.dimen._8dp);
    }

    /**
     * 根据position设置item边距
     */
    protected void setItemMargin(View view, int positionType, int marginDimes) {
        if (view != null) {
            int margin = C_ResUtil.getDimens(marginDimes);
            switch (positionType) {
                case Common.position_type.SINGLE:
                case Common.position_type.TOP:
                    C_ViewSetUtils.setMargins(view, margin, margin, margin, 0);
                    break;
                case Common.position_type.BOTTOM:
                case Common.position_type.CENTER:
                default:
                    C_ViewSetUtils.setMargins(view, margin, 0, margin, 0);
                    break;
            }
        }
    }

    /**
     * 根据position设置item背景
     */
    protected void setItemBgEmpty(View view) {
        if (view != null) {
            view.setBackgroundDrawable(null);
        }
    }

    /**
     * 根据bgResId设置item背景
     */
    protected void setItemBgRes(View view, int bgResId) {
        if (view != null) {
            view.setBackgroundResource(bgResId);
        }
    }

    /**
     * 根据position设置item背景
     */
    protected void setItemBg(View view, int positionType) {
        if (view != null) {
            switch (positionType) {
                case Common.position_type.SINGLE:
                    view.setBackgroundResource(R.drawable.c_r_all);
                    break;
                case Common.position_type.BOTTOM:
                    view.setBackgroundResource(R.drawable.c_r_bottom_no_top);
                    break;
                case Common.position_type.TOP:
                    view.setBackgroundResource(R.drawable.c_r_top_no_bottom);
                    break;
                case Common.position_type.CENTER:
                default:
                    view.setBackgroundResource(R.drawable.c_r_none_no_top_bottom);
                    break;
            }
        }
    }

    public static void setViewsVisival(final View... views) {
        for (View v : views) {
            if (v != null) {
                v.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void setViewsGone(final View... views) {
        for (View v : views) {
            if (v != null) {
                v.setVisibility(View.GONE);
            }
        }
    }

    public static void setViewsInvisival(final View... views) {
        for (View v : views) {
            if (v != null) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void setTextViewsEmpty(final TextView... textViews) {
        for (TextView tv : textViews) {
            if (tv != null) {
                tv.setText(goneText);
            }
        }
    }

    public static void setTextViewsEmpty(final C_LeftIconTextView... textViews) {
        for (C_LeftIconTextView tv : textViews) {
            if (tv != null) {
                tv.setText(goneText);
            }
        }
    }

    public static void setViewWidth(View view, int with) {
        if (view != null) {
            view.getLayoutParams().width = with;
        }
    }

    /**
     * 设置文本左边图片（如果传入的资源id为0则不设置图片）
     */
    public static void setTvLeftIcon(TextView tv, int resId) {
        if (tv != null) {
            tv.setCompoundDrawablesWithIntrinsicBounds(resId == 0 ? null : C_ResUtil.getDrawable(resId), null, null, null);
        }
    }

    /**
     * 设置文本右边图片（如果传入的资源id为0则不设置图片）
     */
    public static void setTvRightIcon(TextView tv, int resId) {
        if (tv != null) {
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null, resId == 0 ? null : C_ResUtil.getDrawable(resId), null);
        }
    }

    public interface ItemViewClickListener<T> {
        void onItemViewClick(View v, int position, T item);
    }

    public interface ItemDataChangeListener<T> {
        void onItemDataChange(int position, T item, int type);
    }
}
