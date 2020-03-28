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
public enum ConsignStatusEnum {

    NOT_SEND("0", "未发货"),
    ALREADY_SEND("1", "已发货"),
    ALREADY_RECEIVED("2", "已收货");

    @Setter
    private String status;

    @Setter
    private String nameCn;


}