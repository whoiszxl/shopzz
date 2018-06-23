package com.whoiszxl.xl.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * @author whoiszxl
 * 底部导航栏的建造者,链式添加Item
 */
public final class ItemBuilder {

    /**
     * 存储所有的底部导航栏的item
     */
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }


    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
