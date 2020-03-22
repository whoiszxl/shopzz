package com.whoiszxl.search.mapper;

import com.whoiszxl.search.entity.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESManagerMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
