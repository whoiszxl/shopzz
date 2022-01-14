package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单列表查询
 *
 * @author whoiszxl
 * @date 2022/1/14
 */
@Data
@ApiModel("订单列表查询参数")
public class OrderListQuery extends PageQuery {

    @ApiModelProperty("订单状态：订单状态，1：待付款，2：已取消，3：待发货，4：待收货，" +
            "5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），" +
            "8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），" +
            "11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

}
