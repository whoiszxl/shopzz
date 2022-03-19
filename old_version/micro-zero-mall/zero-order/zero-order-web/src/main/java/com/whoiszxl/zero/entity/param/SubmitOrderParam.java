package com.whoiszxl.zero.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/27
 */
@Data
@ApiModel("下单参数")
public class SubmitOrderParam {

    @ApiModelProperty("购物车ID")
    private Long cartId;
}
