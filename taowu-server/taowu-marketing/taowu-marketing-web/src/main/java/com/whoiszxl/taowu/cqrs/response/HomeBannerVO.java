package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "app home Banner对象")
public class HomeBannerVO {

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "轮播图名称")
    private String name;

    @Schema(description = "轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件")
    private Integer type;

    @Schema(description = "业务ID: spu_id or other")
    private Long bizId;

    @Schema(description = "图片地址")
    private String pic;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "排序")
    private Integer sort;
}
