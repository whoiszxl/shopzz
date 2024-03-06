package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @since 2022-04-09
 */
@Data
@TableName("oms_cart")
@Schema(description = "购物车记录表")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "购物车主键ID")
    private Long id;

    @Schema(description = "购物车所属用户ID")
    private Long memberId;

    @Schema(description = "商品SPU ID")
    private Long productId;

    @Schema(description = "商品SKU ID")
    private Long skuId;

    @Schema(description = "SKU名称")
    private String skuName;

    @Schema(description = "SKU图片")
    private String skuPic;

    @Schema(description = "购买数量")
    private Integer quantity;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "销售属性")
    private String saleAttr;

    @Schema(description = "是否选中 0未选中 1选中")
    private Integer checked;

    @Schema(description = "状态：0失效 1有效")
    private Integer status;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
