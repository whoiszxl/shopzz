package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 货位查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="货位查询对象", description="货位查询对象")
public class ProductAllocationQuery extends PageQuery {

    @ApiModelProperty(value = "商品货位编号")
    private String productAllocationCode;

}
