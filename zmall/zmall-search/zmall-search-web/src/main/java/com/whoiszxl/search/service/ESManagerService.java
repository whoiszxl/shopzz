package com.whoiszxl.search.service;

/**
 * sku 搜索服务
 */
public interface ESManagerService {


    //创建索引库结构
    void createMappingAndIndex();

    //导入全部数据进入es
    void importAll();

    //根据spuid查询skuList,再导入索引库
    void importDataBySpuId(String spuId);

    //根据souid删除es索引库中相关的sku数据
    void delDataBySpuId(String spuId);

}
