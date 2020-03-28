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
public enum OrderStatusEnum {

    NOT_COMPLETE("0", "未完成订单"),
    ALREADY_COMPLETE("1", "已完成订单"),
    ALREADY_RETURN("2", "已退货");

    @Setter
    private String status;

    @Setter
    private String nameCn;


}