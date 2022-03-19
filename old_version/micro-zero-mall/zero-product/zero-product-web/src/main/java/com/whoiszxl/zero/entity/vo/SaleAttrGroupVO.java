package com.whoiszxl.zero.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleAttrGroupVO {

    @ApiModelProperty("属性ID")
    private Long attrId;

    @ApiModelProperty("属性名称")
    private String attrName;

    @ApiModelProperty("销售属性列表")
    private List<SkuDetailSaleAttributeVO> attrValues;
}
