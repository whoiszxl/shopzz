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
public enum PayStatusEnum {

    NOT_PAY("0", "未支付"),
    ALREADY_PAY("1", "已支付"),
    FAIL_PAY("2", "支付失败");

    @Setter
    private String status;

    @Setter
    private String nameCn;


}