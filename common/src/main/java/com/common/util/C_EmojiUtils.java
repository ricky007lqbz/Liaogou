package com.common.util;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.bean.C_EMOJI;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ricky on 2016/07/08.
 * <p/>
 * 表情转换工具
 */
public class C_EmojiUtils {

    private static C_EmojiUtils instance;
    private Map<String, Integer> emojiMap;
    private Pattern pattern;

    public C_EmojiUtils() {
        initEmojiMap();
    }

    private void initEmojiMap() {
        emojiMap = new HashMap<>();
        pattern = Pattern.compile("\\[[\\u4e00-\\u9fa5]{1,3}\\]");
        for (C_EMOJI emoji : C_EMOJI.values()) {
            emojiMap.put(emoji.getName(), emoji.getResId());
        }
    }

    public static C_EmojiUtils getInstance() {
        if (instance == null) {
            instance = new C_EmojiUtils();
        }
        return instance;
    }

    /**
     * 在EditTextView最后插入表情
     *
     * @param et        editTextView
     * @param emojiName 表情名字
     */
    public void insetEmojiToEditText(EditText et, String emojiName) {
        et.setSelection(et.length());
        SpannableString emoji = new SpannableString(emojiName);
        Drawable drawable = C_ResUtil.getDrawable(emojiMap.get(emojiName));
        //设置表情图片大小
        drawable.setBounds(0, 0, (int) (et.getTextSize() * 1.5), (int) (et.getTextSize() * 1.5));
        emoji.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM),
                0, emojiName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //在光标所在处插入表情符
        et.getText().insert(et.getSelectionStart(), emoji);
    }

    /**
     * 把文本中的所有
     *
     * @param tv textView
     * @param text String
     */
    public void replaceEmojiStrToEmoji(TextView tv, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            tv.setVisibility(View.GONE);
            return;
        }
        SpannableString builder = new SpannableString(text);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            if (emojiMap.get(matcher.group()) != null) {
                Drawable drawable = C_ResUtil.getDrawable(emojiMap.get(matcher.group()));
                //设置表情图片大小
                drawable.setBounds(0, 0, (int) (tv.getTextSize() * 1.5), (int) (tv.getTextSize() * 1.5));
                builder.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM),
                        matcher.start(), matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(builder);
    }
}
