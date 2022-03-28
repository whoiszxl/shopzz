package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value = "Sku查询对象", description = "Sku查询对象")
public class SkuQuery extends PageQuery {

    @ApiModelProperty("SPU ID")
    private Long spuId;

    @ApiModelProperty("SKU名称")
    private String name;


}
