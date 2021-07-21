package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("采购结算单查询参数")
public class PurchaseSettlementOrderQuery extends PageQuery {

    @ApiModelProperty("供应商id")
    private Integer supplierId;

    @ApiModelProperty("采购结算单状态")
    private Integer status;

}
