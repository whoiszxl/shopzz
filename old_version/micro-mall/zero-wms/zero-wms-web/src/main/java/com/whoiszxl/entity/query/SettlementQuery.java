package com.whoiszxl.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="采购结算的结算周期类型", description="采购结算的结算周期类型")
public class SettlementQuery {

    @ApiModelProperty("结算周期 1：周结算，2：月结算，3：季度结算")
    private Integer accountPeriod;

}
