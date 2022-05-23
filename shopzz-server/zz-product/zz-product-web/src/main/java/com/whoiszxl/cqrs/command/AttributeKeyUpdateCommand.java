package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性key更新命令
 *
 * @author whoiszxl
 * @date 2022/3/21
 */
@Data
@ApiModel("属性key更新命令")
public class AttributeKeyUpdateCommand {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性单位")
    private String unit;

    @ApiModelProperty("是否为标准属性")
    private Integer standard;

    @ApiModelProperty("属性类型[0-销售属性,1-基本属性]")
    private Integer type;

}
