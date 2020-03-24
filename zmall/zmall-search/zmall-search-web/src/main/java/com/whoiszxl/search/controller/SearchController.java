package com.whoiszxl.search.controller;

import com.whoiszxl.search.service.ESManagerService;
import com.whoiszxl.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @description: 搜索控制器
 * @author: whoiszxl
 * @create: 2020-03-24
 **/
@RestController
@RequestMapping("/sku_search")
public class SearchController {


    @Autowired
    private ESManagerService esManagerService;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Map search(@RequestParam Map<String,String> searchMap){
        //特殊符号处理
        this.handleSearchMap(searchMap);
        Map searchResult = searchService.search(searchMap);
        return searchResult;
    }

    private void handleSearchMap(Map<String, String> searchMap) {
        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith("spec_")){
                searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
            }
        }
    }

}
