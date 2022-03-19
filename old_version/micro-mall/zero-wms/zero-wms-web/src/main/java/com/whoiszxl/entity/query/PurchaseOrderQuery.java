package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 采购订单查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="采购订单查询对象", description="采购订单查询对象")
public class PurchaseOrderQuery extends PageQuery {

    @ApiModelProperty(value = "供应商ID")
    private Integer supplierId;

    @ApiModelProperty(value = "采购订单状态")
    private Integer purchaseOrderStatus;
}
