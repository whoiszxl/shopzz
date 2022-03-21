package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品SKU库存表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_sku_stock")
@ApiModel(value = "SkuStock对象", description = "商品SKU库存表")
public class SkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品sku ID")
    private Long skuId;

    @ApiModelProperty("销售库存")
    private Integer saleStockQuantity;

    @ApiModelProperty("锁定库存")
    private Integer lockedStockQuantity;

    @ApiModelProperty("已销售库存")
    private Integer saledStockQuantity;

    @ApiModelProperty("库存状态,0:无库存,1:有库存")
    private Integer stockStatus;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
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
