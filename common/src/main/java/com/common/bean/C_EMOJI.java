package com.common.bean;

public enum C_EMOJI {
//    HA_HA("[哈哈]", R.drawable.icon_emoji_1),
//    GAO_XING("[高兴]", R.drawable.icon_emoji_2),
//    WEI_ZHI("[未知]", R.drawable.icon_emoji_3),
//    QIN_YI_KOU("[亲一口]", R.drawable.icon_emoji_4),
//    FA_DAI("[发呆]", R.drawable.icon_emoji_5),
//    LIAN_HONG("[脸红]", R.drawable.icon_emoji_6),
//    XI_HUAN("[喜欢]", R.drawable.icon_emoji_7),
//    GUI_LIAN("[鬼脸]", R.drawable.icon_emoji_8),
//    WU_YU("[无语]", R.drawable.icon_emoji_9),
//    WEI_XIAO("[微笑]", R.drawable.icon_emoji_10),
//    ZHA_YAN("[眨眼]", R.drawable.icon_emoji_11),
//    FEI_WEN("[飞吻]", R.drawable.icon_emoji_12),
//    BI_SHI("[鄙视]", R.drawable.icon_emoji_13),
//    DA_XIAO("[大笑]", R.drawable.icon_emoji_14),
//    XIAO("[笑]", R.drawable.icon_emoji_15),
//    JIA_XIAO("[假笑]", R.drawable.icon_emoji_16),
//    QIAO_ZUI("[翘嘴]", R.drawable.icon_emoji_17),
//    QIAO_XIAO_LIAN("[翘笑脸]", R.drawable.icon_emoji_18),
//    FEN_NU("[愤怒]", R.drawable.icon_emoji_19),
//    KU("[哭]", R.drawable.icon_emoji_20),
//    BU_YAO("[不要]", R.drawable.icon_emoji_21),
//    TAN_QI("[叹气]", R.drawable.icon_emoji_22),
//    SHENG_BING("[生病]", R.drawable.icon_emoji_23),
//    HONG_LIAN("[红脸]", R.drawable.icon_emoji_24);

    HONG_LIAN("[红脸]", 0);

    private String name;
    private int resId;

    C_EMOJI(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}