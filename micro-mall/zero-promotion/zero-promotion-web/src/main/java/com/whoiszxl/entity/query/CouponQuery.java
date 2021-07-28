package com.whoiszxl.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="优惠券查询参数", description="优惠券查询参数")
public class CouponQuery {

    @ApiModelProperty("是否使用了  1：使用了 0：未使用")
    private Integer isUsed;

}
