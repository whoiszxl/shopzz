package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 秒杀订单列表查询对象
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Getter
@Setter
@ApiModel(value = "秒杀订单列表查询对象", description = "秒杀订单列表查询对象")
public class SeckillOrderQuery extends PageQuery {

    @ApiModelProperty("sku名称")
    private String skuName;
}
