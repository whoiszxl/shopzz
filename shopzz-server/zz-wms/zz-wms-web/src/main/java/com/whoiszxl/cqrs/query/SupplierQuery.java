package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采购订单查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="供应商查询对象", description="供应商查询对象")
public class SupplierQuery extends PageQuery {

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "供应商结算周期")
    private Integer accountPeriod;
}
