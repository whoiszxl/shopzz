package com.whoiszxl.cqrs.admin.query;

import com.whoiszxl.core.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@Schema(description = "订单查询条件")
public class OrderQuery implements Serializable {

    @Query(blurry = "order_no")
    @Schema(description = "订单编号")
    private String orderNo;

    @Query(blurry = "username")
    @Schema(description = "用户名")
    private String username;

    @Query
    @Schema(description = "订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

    @Query
    @Schema(description = "支付方式: [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

}
