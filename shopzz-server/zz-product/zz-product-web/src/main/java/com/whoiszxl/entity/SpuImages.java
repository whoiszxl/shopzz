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
 * 商品SPU图片表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_spu_images")
@ApiModel(value = "SpuImages对象", description = "商品SPU图片表")
public class SpuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品ID")
    private Long spuId;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("排序,降序排列")
    private Integer sort;

    @ApiModelProperty("是否默认图")
    private Integer isDefault;


}
