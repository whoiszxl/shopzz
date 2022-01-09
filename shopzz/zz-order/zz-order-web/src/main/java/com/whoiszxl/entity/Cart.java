package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车记录表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Data
@TableName("oms_cart")
@ApiModel(value = "Cart对象", description = "购物车记录表")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("购物车主键ID")
    private Long id;

    @ApiModelProperty("购物车所属用户ID")
    private Long memberId;

    @ApiModelProperty("商品SPU ID")
    private Long productId;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("SKU名称")
    private String skuName;

    @ApiModelProperty("SKU图片")
    private String skuPic;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("销售属性")
    private String saleAttr;

    @ApiModelProperty("是否选中 0未选中 1选中")
    private Boolean checked;

    @ApiModelProperty("状态：0失效 1有效")
    private Boolean status;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
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
