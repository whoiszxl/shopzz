package com.whoiszxl.xl.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.ui.recycler.DataConverter;
import com.whoiszxl.xl.ui.recycler.ItemType;
import com.whoiszxl.xl.ui.recycler.MultipleFields;
import com.whoiszxl.xl.ui.recycler.MultipleItemEntity;
import com.whoiszxl.xl.util.log.XLLogger;

import java.util.ArrayList;


public class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list");

        final int size = dataArray.size();

        for (int i = 0; i < size; i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final String id = data.getString("id");
            final String subTitle = data.getString("sub_title");
            final String name = data.getString("name");
            final String imageUrl = data.getString("imageHost")+data.getString("mainImage");
            final String price = data.getString("price");
            int type = 0;
            if(imageUrl == null && name != null){
                type = ItemType.TEXT;
            }else if(imageUrl != null && name == null) {
                type = ItemType.IMAGE;
            }else if(imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.SUB_TITLE, subTitle)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
                    .setField(MultipleFields.PRICE, price)
                    .setField(MultipleFields.SPAN_SIZE, 2)
                    .build();

            ENTITIES.add(entity);
        }

        return ENTITIES;
    }
}
