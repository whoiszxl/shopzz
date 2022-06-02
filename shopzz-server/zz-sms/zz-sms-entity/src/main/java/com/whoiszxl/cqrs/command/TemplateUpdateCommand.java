package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 短信模板表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@ApiModel(value = "Template对象", description = "短信模板表")
public class TemplateUpdateCommand implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板编码")
    private String code;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板类型: 1-验证码 2-营销")
    private Integer type;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

}
