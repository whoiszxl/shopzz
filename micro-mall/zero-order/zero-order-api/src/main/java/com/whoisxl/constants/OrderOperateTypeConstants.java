package com.whoisxl.constants;

import io.swagger.annotations.ApiModelProperty;

/**
 * 操作类型，
 * 1：创建订单，
 * 2：手动取消订单，
 * 3：自动取消订单，
 * 4：支付订单，
 * 5：手动确认收货，
 * 6：自动确认收货，
 * 7：商品发货，
 * 8：申请退货，
 * 9：退货审核不通过，
 * 10：退货审核通过，
 * 11：寄送退货商品，
 * 12：确认收到退货，
 * 13：退货已入库，
 * 14：完成退款
 */
public class OrderOperateTypeConstants {

    @ApiModelProperty("创建订单")
    public static final int CREATE_ORDER = 1;
    
    @ApiModelProperty("手动取消订单")
    public static final int CANCEL_ORDER = 2;

    @ApiModelProperty("自动取消订单")
    public static final int CANCEL_ORDER_AUTO = 3;
    
    @ApiModelProperty("支付订单")
    public static final int PAY_ORDER = 4;
    
    @ApiModelProperty("手动确认收货")
    public static final int CONFIRM_RECEIPT = 5;

    @ApiModelProperty("自动确认收货")
    public static final int CONFIRM_RECEIPT_AUTO = 6;
    
    @ApiModelProperty("商品发货")
    public static final int PRODUCT_DELIVERY = 7;
    
    @ApiModelProperty("申请退货")
    public static final int APPLY_RETURN_PRODUCT = 8;
    
    @ApiModelProperty("退货审核不通过")
    public static final int RETURN_PRODUCT_REJECTED = 9;
    
    @ApiModelProperty("退货审核通过")
    public static final int RETURN_PRODUCT_APPROVED = 10;
    
    @ApiModelProperty("寄送退货商品")
    public static final int SEND_OUT_RETURN_PRODUCT = 11;
    
    @ApiModelProperty("确认收到退货")
    public static final int CONFIRM_RETURN_PRODUCT_RECEIPT = 12;
    
    @ApiModelProperty("完成退货入库")
    public static final int FINISHED_RETURN_PRODUCT_INPUT = 13;
    
    @ApiModelProperty("完成退货退款")
    public static final int FINISHED_RETURN_PRODUCT_REFUND = 14;

}
