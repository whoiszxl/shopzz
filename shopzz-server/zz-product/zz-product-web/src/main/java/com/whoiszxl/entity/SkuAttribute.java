package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SKU属性关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_sku_attribute")
@ApiModel(value = "SkuAttribute对象", description = "SKU属性关联表")
public class SkuAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品spu ID")
    private Long spuId;

    @ApiModelProperty("商品sku ID")
    private Long skuId;

    @ApiModelProperty("属性key ID")
    private Long keyId;

    @ApiModelProperty("属性value ID")
    private Long valueId;


}
