package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "spu图片vo")
public class SpuImagesVO {

    @ApiModelProperty("id")
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