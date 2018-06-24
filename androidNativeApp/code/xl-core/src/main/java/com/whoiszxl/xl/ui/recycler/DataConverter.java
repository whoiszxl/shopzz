package com.whoiszxl.xl.ui.recycler;

import java.util.ArrayList;

/**
 * @author whoiszxl
 * json數據轉換器
 */
public abstract class DataConverter {

    /**
     * 存储json数据转换实体的集合
     */
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if(mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("mJsonData is NULL");
        }
        return mJsonData;
    }
}
