package com.whoiszxl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 采购订单状态类型
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum PurchaseOrderStatusEnum {

    EDITING(1, "编辑中"),
    WAIT_FOR_APPROVE(2, "待审核"),
    APPROVED(3, "已审核"),
    WAIT_FOR_INPUT(4, "待入库"),
    FINISHED_INPUT(5, "已入库"),
    WAIT_FOR_SETTLEMENT(6, "待结算"),
    FINISHED(7, "已完成"),
    ;
    private Integer code;
    private String desc;
}