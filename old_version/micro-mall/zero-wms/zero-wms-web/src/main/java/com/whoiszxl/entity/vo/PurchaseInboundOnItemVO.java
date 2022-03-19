package com.whoiszxl.entity.vo;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 采购入库订单条目关联的上架条目表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchaseInboundOnItem对象", description="采购入库订单条目关联的上架条目表")
public class PurchaseInboundOnItemVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "采购入库单条目ID")
    private Long purchaseOrderItemId;

    @ApiModelProperty(value = "货位ID")
    private Long productAllocationId;

    @ApiModelProperty(value = "商品SKU ID")
    private Long productSkuId;

    @ApiModelProperty(value = "上架数量")
    private Integer putOnShelvesCount;
}
