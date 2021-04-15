package com.whoiszxl.zero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户登记枚举
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum MemberGradeLevelEnum {

    LEVEL_ONE("1", "等级一"),
    LEVEL_TWO("2", "等级二"),
    LEVEL_THREE("3", "等级三"),
    LEVEL_FOUR("4", "等级四"),
    ;
    private String level;
    private String desc;
}