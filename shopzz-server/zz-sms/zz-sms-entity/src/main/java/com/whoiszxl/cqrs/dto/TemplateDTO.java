package com.whoiszxl.cqrs.dto;

import com.whoiszxl.entity.Template;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TemplateDTO extends Template {

    @ApiModelProperty("是否选中")
    private boolean selected;

    @ApiModelProperty(value = "通道模板 可能为空")
    private String channelTemplateCode;

    @ApiModelProperty(value = "通道id")
    private String channelId;
}
