package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 属性值表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@ApiModel(value = "AttributeValue对象", description = "属性值表")
public class AttributeValueUpdateCommand implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("属性key主键id")
    private Long keyId;

    @ApiModelProperty("属性值")
    private String value;

}
