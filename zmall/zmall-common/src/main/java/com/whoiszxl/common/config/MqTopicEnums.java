package com.whoiszxl.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-04-01
 **/
@AllArgsConstructor
@Getter
public enum MqTopicEnums {

    PAY_RESULT_TOPIC("pay-result-topic"),
    PAY_QUERY_TOPIC("pay-query-topic"),
    SEARCH_TOPIC("search-topic");

    @Setter
    private String name;

}