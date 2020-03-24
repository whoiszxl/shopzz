package com.whoiszxl.search.service;

import java.util.Map;

public interface SearchService {

    /**
     * 全文检索
     * @param params 查询参数
     * @return
     * @throws Exception
     */
    Map search(Map<String, String> params);

}
