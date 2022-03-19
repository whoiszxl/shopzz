package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="品牌搜索查询条件", description="品牌搜索查询条件")
public class BrandQuery extends PageQuery {

    @ApiModelProperty("品牌名称：中文or英文or别名")
    private String brandName;

    @ApiModelProperty("品牌首字母")
    private String firstLetter;

    @ApiModelProperty("品牌显示状态")
    private Integer showStatus;
}