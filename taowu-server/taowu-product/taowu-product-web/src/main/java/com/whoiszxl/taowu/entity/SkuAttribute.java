package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * SKU属性关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_sku_attribute")
@Schema(description = "SKU属性关联表")
public class SkuAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品spu ID")
    private Long spuId;

    @Schema(description = "商品sku ID")
    private Long skuId;

    @Schema(description = "属性key ID")
    private Long keyId;

    @Schema(description = "属性value ID")
    private Long valueId;


}
