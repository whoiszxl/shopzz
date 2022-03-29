package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 轮播表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@ApiModel(value = "Banner查询对象", description = "Banner查询对象")
public class BannerQuery extends PageQuery {


    @ApiModelProperty("轮播图名称")
    private String name;

    @ApiModelProperty("轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件")
    private Integer type;

    @ApiModelProperty("业务ID: spu_id or other")
    private Long bizId;

    @ApiModelProperty("上下线状态:0->下线;1->上线")
    private Integer status;

}
