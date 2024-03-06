package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 轮播表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "Banner查询对象")
public class BannerQuery extends PageQuery {


    @Query(blurry = "name")
    @Schema(description = "轮播图名称")
    private String name;

    @Query
    @Schema(description = "轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件")
    private Integer type;

    @Query
    @Schema(description = "业务ID: spu_id or other")
    private Long bizId;

    @Query
    @Schema(description = "上下线状态:0->下线;1->上线")
    private Integer status;

}
