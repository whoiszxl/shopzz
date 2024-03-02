package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpuPublishStatusEnum {

    NOT_PUBLISH(0, "未上架"),
    PUBLISHED(1, "已上架"),
    ;
    private Integer code;
    private String desc;
}