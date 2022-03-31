package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "ProductColumn api对象", description = "商品专栏表")
public class ProductColumnApiResponse implements Serializable {


    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("专栏名称")
    private String name;

    @ApiModelProperty("专栏描述")
    private String descs;

    @ApiModelProperty("入口图片地址")
    private String enterImg;

    @ApiModelProperty("内部banner图片地址")
    private String bannerImg;

    @ApiModelProperty("上下线状态:0->下线;1->上线")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

}
