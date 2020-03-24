package com.whoiszxl.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.whoiszxl.search.entity.SkuInfo;
import com.whoiszxl.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-24
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /** 每页查询的条数 */
    @Value("${search.pageSize}")
    public String pageSize;

    @Override
    public Map search(Map<String, String> searchMap) {

        Map<String, Object> resultMap = new HashMap<>();

        //组合条件对象
        if(searchMap != null) {
            //原生搜索实现类
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            //1. 关键词匹配
            if(!StringUtils.isEmpty(searchMap.get("keywords"))) {
                boolQuery.must(QueryBuilders.matchQuery("name", searchMap.get("keywords")).operator(Operator.AND));
            }

            //2. 品牌匹配
            if(StringUtils.isNotEmpty(searchMap.get("brand"))) {
                boolQuery.filter(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
            }

            //3. 规格匹配
            for(String key : searchMap.keySet()) {
                if(key.startsWith("spec_")) {
                    String value = searchMap.get(key).replace("%2B","+");
                    //spec_网络制式
                    boolQuery.filter(QueryBuilders.termQuery(("specMap."+key.substring(5)+".keyword"),value));
                }
            }

            //4. 价格区间查询
            if(StringUtils.isNotEmpty(searchMap.get("price"))){
                String[] prices = searchMap.get("price").split("-");
                if(prices.length == 2) {
                    boolQuery.filter(QueryBuilders.rangeQuery("price").lte(prices[1]));
                }
                boolQuery.filter(QueryBuilders.rangeQuery("price").gte(prices[0]));
            }
            nativeSearchQueryBuilder.withQuery(boolQuery);

            //5. 品牌分组查询
            String skuBrand = "skuBrand";
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(skuBrand).field("brandName"));

            //6. 规格聚合查询
            String skuSpec="skuSpec";
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(skuSpec).field("spec.keyword"));

            //7. 开启分页查询
            String pageNum = searchMap.get("pageNum"); //当前页
            String pageSize = searchMap.get("pageSize"); //每页显示多少条
            if (StringUtils.isEmpty(pageNum)){
                pageNum = "1";
            }
            if (StringUtils.isEmpty(pageSize)){
                pageSize = this.pageSize;
            }
            //设置分页
            //第一个参数:当前页 是从0开始
            //第二个参数:每页显示多少条
            nativeSearchQueryBuilder.withPageable(PageRequest.of(Integer.parseInt(pageNum)-1,Integer.parseInt(pageSize)));

            //按照相关字段进行排序查询
            // 1.当前域 2.当前的排序操作(升序ASC,降序DESC)
            if (StringUtils.isNotEmpty(searchMap.get("sortField")) && StringUtils.isNotEmpty(searchMap.get("sortRule"))){
                if ("ASC".equals(searchMap.get("sortRule"))){
                    //升序
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort((searchMap.get("sortField"))).order(SortOrder.ASC));
                }else{
                    //降序
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort((searchMap.get("sortField"))).order(SortOrder.DESC));
                }
            }

            //设置高亮域以及高亮的样式
            HighlightBuilder.Field field = new HighlightBuilder.Field("name")//高亮域
                    .preTags("<span style='color:red'>")//高亮样式的前缀
                    .postTags("</span>");//高亮样式的后缀
            nativeSearchQueryBuilder.withHighlightFields(field);

            /**
             * 第一个参数: 条件构建对象
             * 第二个参数: 查询操作实体类
             * 第三个参数: 查询结果操作对象
             */
            //开启查询 封装查询结果
            AggregatedPage<SkuInfo> resultInfo = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    List<T> list = new ArrayList<>();
                    SearchHits hits = searchResponse.getHits();
                    if(hits != null) {
                        for (SearchHit hit : hits) {
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            if (highlightFields != null && highlightFields.size()>0){
                                //替换数据
                                skuInfo.setName(highlightFields.get("name").getFragments()[0].toString());
                            }
                            list.add((T) skuInfo);
                        }
                    }

                    return new AggregatedPageImpl<T>(list, pageable, hits.getTotalHits(), searchResponse.getAggregations());
                }
            });

            //设置总条数，总页数，查询结果集合
            resultMap.put("total", resultInfo.getTotalElements());
            resultMap.put("totalPages", resultInfo.getTotalPages());
            resultMap.put("rows", resultInfo.getContent());

            //封装品牌的分组结果
            StringTerms brandTerms = (StringTerms) resultInfo.getAggregation(skuBrand);
            List<String> brandList = brandTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            resultMap.put("brandList",brandList);

            //封装规格分组结果
            StringTerms specTerms= (StringTerms) resultInfo.getAggregation(skuSpec);
            List<String> specList = specTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            resultMap.put("specList",specList);

            //当前页
            resultMap.put("pageNum",pageNum);

            return resultMap;
        }
        return null;
    }
}
