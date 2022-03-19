package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 销售出库订单item表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_outbound_sell_order_item")
@ApiModel(value = "OutboundSellOrderItem对象", description = "销售出库订单item表")
public class OutboundSellOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("销售出库单ID")
    private Long outboundSellOrderId;

    @ApiModelProperty("商品sku ID")
    private Long skuId;

    @ApiModelProperty("商品sku编号")
    private String skuCode;

    @ApiModelProperty("商品名称")
    private String skuName;

    @ApiModelProperty("销售属性, 机身颜色:白色,内存容量:256G")
    private String sellProperties;

    @ApiModelProperty("销售出库数量")
    private Integer quantity;

    @ApiModelProperty("销售出库商品购买价格")
    private BigDecimal price;

    @ApiModelProperty("销售出库商品优惠后折算最终价格")
    private BigDecimal finalPrice;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除,  0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
