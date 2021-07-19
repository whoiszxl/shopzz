package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 采购订单查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="供应商查询对象", description="供应商查询对象")
public class SupplierQuery extends PageQuery {

    @ApiModelProperty(value = "供应商名称")
    private Integer supplierName;

    @ApiModelProperty(value = "供应商结算周期")
    private Integer accountPeriod;
}
