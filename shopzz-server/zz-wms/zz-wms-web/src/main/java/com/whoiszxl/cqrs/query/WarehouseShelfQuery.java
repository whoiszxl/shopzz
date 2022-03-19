package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库货架查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="仓库货架查询对象", description="仓库货架查询对象")
public class WarehouseShelfQuery extends PageQuery {

    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

}
