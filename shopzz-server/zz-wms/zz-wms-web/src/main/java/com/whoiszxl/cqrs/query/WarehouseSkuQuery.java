package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库SKU查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="仓库SKU查询对象", description="仓库SKU查询对象")
public class WarehouseSkuQuery extends PageQuery {

    @ApiModelProperty(value = "货架ID")
    private Long shelfId;

    @ApiModelProperty(value = "商品SKU名称")
    private String skuName;

    @ApiModelProperty(value = "商品SKU编号")
    private String skuCode;

}
