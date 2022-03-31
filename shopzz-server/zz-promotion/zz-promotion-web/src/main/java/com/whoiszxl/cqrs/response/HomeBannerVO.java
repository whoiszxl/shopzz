package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "app home Banner对象", description = "app home Banner对象")
public class HomeBannerVO {

    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("轮播图名称")
    private String name;

    @ApiModelProperty("轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件")
    private Integer type;

    @ApiModelProperty("业务ID: spu_id or other")
    private Long bizId;

    @ApiModelProperty("图片地址")
    private String pic;

    @ApiModelProperty("链接地址")
    private String url;

    @ApiModelProperty("排序")
    private Integer sort;
}
