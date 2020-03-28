package com.whoiszxl.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-28
 **/
@AllArgsConstructor
@Getter
public enum BooleanEnum {

    IS_FALSE("0", "否"),
    IS_TRUE("1", "是");

    @Setter
    private String bool;

    @Setter
    private String nameCn;


}
