package com.whoiszxl.cqrs.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "活动api查询对象", description = "活动api查询对象")
public class ActivityApiQuery {

    @NotNull(message = "活动ID不允许为空")
    @ApiModelProperty("活动ID")
    private Long id;

}
