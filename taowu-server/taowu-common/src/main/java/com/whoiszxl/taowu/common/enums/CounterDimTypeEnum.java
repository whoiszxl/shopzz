package com.whoiszxl.taowu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CounterDimTypeEnum {
    USER(1, "用户维度"),
    CONTENT(2, "内容维度"),
    TAG(3, "标签维度"),
    COMMENT(4, "评论维度");

    private final Integer code;
    private final String desc;

}

