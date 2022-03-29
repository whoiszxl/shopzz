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
 * 商品专栏跟SPU关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@TableName("spms_product_column_spu")
@ApiModel(value = "ProductColumnSpu对象", description = "商品专栏跟SPU关联表")
public class ProductColumnSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("专栏主键ID")
    private Long columnId;

    @ApiModelProperty("SPU主键ID")
    private Long spuId;


}
