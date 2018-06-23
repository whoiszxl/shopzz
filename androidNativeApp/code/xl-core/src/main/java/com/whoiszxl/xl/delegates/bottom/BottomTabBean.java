package com.whoiszxl.xl.delegates.bottom;

/**
 * @author whoiszxl
 * 封装底部导航栏的图标和标题的实体类
 */
public final class BottomTabBean {

    /**
     * 图标
     */
    private final CharSequence ICON;

    /**
     * 标题
     */
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
