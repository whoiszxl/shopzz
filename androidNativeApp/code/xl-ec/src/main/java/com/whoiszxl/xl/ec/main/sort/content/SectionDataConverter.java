package com.whoiszxl.xl.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();

        //TODO 这里返回的json还不合app逻辑，还需要再修改接口
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

        final int size = dataArray.size();

        //TODO 分页content的id
        final int id = 1;
        //TODO 分页content的title
        final String title = "demo title";

        //添加title
        final SectionBean sectionTitleBean = new SectionBean(true, title);
        sectionTitleBean.setId(id);
        sectionTitleBean.setIsMore(true);
        dataList.add(sectionTitleBean);
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int categoryId = data.getInteger("id");
            final String name = data.getString("name");
            final String imageUrl = data.getString("imgHost")+data.getString("img");

            final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
            itemEntity.setCategoryId(categoryId);
            itemEntity.setCategoryName(name);
            itemEntity.setCategoryImg(imageUrl);
            //添加内容
            dataList.add(new SectionBean(itemEntity));

        }
        return dataList;
    }
}
