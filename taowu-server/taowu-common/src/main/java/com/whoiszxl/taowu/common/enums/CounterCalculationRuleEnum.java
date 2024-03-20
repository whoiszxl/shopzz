package com.whoiszxl.taowu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CounterCalculationRuleEnum {
    INCREASE(1, "增加"),
    DECREASE(2, "减少");

    private final Integer code;
    private final String desc;
}
