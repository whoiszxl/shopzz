package com.whoiszxl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 采购订单审核状态类型
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum PurchaseOrderApproveEnum {

    PASSED(1, "通过"),
    REJECTED(-1, "拒绝"),
    ;
    private Integer code;
    private String desc;
}