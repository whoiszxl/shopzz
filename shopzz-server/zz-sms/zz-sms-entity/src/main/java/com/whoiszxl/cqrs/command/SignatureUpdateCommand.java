package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 短信签名表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@ApiModel(value = "Signature对象", description = "短信签名表")
public class SignatureUpdateCommand implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("签名名称")
    private String name;

    @ApiModelProperty("签名编码")
    private String code;

    @ApiModelProperty("签名内容")
    private String content;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

}
