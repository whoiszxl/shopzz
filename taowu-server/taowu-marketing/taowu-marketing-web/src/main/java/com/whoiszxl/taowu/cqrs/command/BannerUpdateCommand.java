package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 轮播表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@Schema(description = "轮播表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerUpdateCommand implements Serializable {


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

    @Schema(description = "上下线状态:0->下线;1->上线")
    private Integer status;

    @Schema(description = "点击数")
    private Integer clickCount;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "排序")
    private Integer sort;

}
