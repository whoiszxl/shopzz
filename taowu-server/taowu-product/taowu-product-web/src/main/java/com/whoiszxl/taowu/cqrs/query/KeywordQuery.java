package com.whoiszxl.taowu.cqrs.query;

import lombok.Data;

@Data
public class KeywordQuery {

    /**
     * 小米
     */
    private String keyword;

    /**
     * 从哪页开始查 1
     */
    private Integer from;

    /**
     * 每一页多少条记录 10
     */
    private Integer size;
}
