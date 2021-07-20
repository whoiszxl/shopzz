package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/7/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="采购入库单查询对象", description="采购入库单查询对象")
public class PurchaseInBoundOrderQuery extends PageQuery {

    @ApiModelProperty(value = "供应商ID")
    private Integer supplierId;

    @ApiModelProperty(value = "采购入库订单状态")
    private Integer purchaseInboundOrderStatus;
}


