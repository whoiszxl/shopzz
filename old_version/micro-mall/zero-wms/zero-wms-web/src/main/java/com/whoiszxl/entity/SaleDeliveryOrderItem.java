package com.whoiszxl.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售出库订单条目表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_sale_delivery_order_item")
@ApiModel(value="SaleDeliveryOrderItem对象", description="销售出库订单条目表")
public class SaleDeliveryOrderItem extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "销售出库单ID")
    private Long saleDeliveryOrderId;

    @ApiModelProperty(value = "商品sku ID")
    private Long productSkuId;

    @ApiModelProperty(value = "商品sku编号")
    private String productSkuCode;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "销售属性，机身颜色:白色,内存容量:256G")
    private String saleProperties;

    @ApiModelProperty(value = "商品毛重")
    private BigDecimal productGrossWeight;

    @ApiModelProperty(value = "销售出库数量")
    private Integer quantity;

    @ApiModelProperty(value = "销售出库商品购买价格")
    private BigDecimal price;

    @ApiModelProperty(value = "促销活动ID")
    private Long promotionActivityId;

    @ApiModelProperty(value = "商品长度")
    private BigDecimal productLength;

    @ApiModelProperty(value = "商品宽度")
    private BigDecimal productWidth;

    @ApiModelProperty(value = "商品高度")
    private BigDecimal productHeight;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
