package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 采购入库订单条目表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchaseInboundOrderItem对象", description="采购入库订单条目表")
public class PurchaseInboundOrderItemVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "采购入库单ID")
    private Long purchaseInboundOrderId;

    @ApiModelProperty(value = "商品SKU ID")
    private Long productSkuId;

    @ApiModelProperty(value = "采购数量")
    private Integer purchaseCount;

    @ApiModelProperty(value = "采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "合格商品的数量")
    private Integer qualifiedCount;

    @ApiModelProperty(value = "到货的商品数量")
    private Integer arrivalCount;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
