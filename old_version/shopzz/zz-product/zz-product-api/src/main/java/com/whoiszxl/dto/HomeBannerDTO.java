package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 首页轮播表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HomeBanner对象", description="首页轮播表")
public class HomeBannerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
    private Integer id;

    @ApiModelProperty("轮播图名称")
    private String name;

    @ApiModelProperty("轮播位置:0->PC首页轮播；1->app首页轮播 2->app导航小组件")
    private Boolean type;

    @ApiModelProperty("图片地址")
    private String pic;

    @ApiModelProperty("上下线状态:0->下线；1->上线")
    private Integer status;

    @ApiModelProperty("点击数")
    private Integer clickCount;

    @ApiModelProperty("链接地址")
    private String url;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("展示开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("展示结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("乐观锁")
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;

}
