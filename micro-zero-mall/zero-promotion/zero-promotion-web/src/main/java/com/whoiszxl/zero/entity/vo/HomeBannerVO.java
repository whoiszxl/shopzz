package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播表(PromotionHomeBanner)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class HomeBannerVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = -98477268373070661L;

    @ApiModelProperty(value = "自增主键ID")
    private Integer id;

    @ApiModelProperty(value = "轮播图名称")
    private String name;

    @ApiModelProperty(value = "轮播位置：0->PC首页轮播；1->app首页轮播 2->app导航小组件")
    private Integer type;

    @ApiModelProperty(value = "图片地址")
    private String pic;

    @ApiModelProperty(value = "上下线状态：0->下线；1->上线")
    private Integer status;

    @ApiModelProperty(value = "点击数")
    private Integer clickCount;

    @ApiModelProperty(value = "链接地址")
    private String url;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}