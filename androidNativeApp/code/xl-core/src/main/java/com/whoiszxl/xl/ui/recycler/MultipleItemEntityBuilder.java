package com.whoiszxl.xl.ui.recycler;

import java.util.LinkedHashMap;

/**
 * @author whoiszxl
 * MultipleItemEntity建造者
 */
public class MultipleItemEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public MultipleItemEntityBuilder() {
        //先清除之前的數據
        FIELDS.clear();
    }


    public final MultipleItemEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleItemEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleItemEntityBuilder setFields(LinkedHashMap<?,?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }

}
