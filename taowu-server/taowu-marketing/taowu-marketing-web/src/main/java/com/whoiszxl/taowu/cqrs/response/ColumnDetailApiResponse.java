package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 专栏详情返回实体
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Data
@Schema(description = "专栏详情返回实体")
public class ColumnDetailApiResponse {

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "专栏名称")
    private String name;

    @Schema(description = "专栏描述")
    private String descs;

    @Schema(description = "入口图片地址")
    private String enterImg;

    @Schema(description = "内部banner图片地址")
    private String bannerImg;

    @Schema(description = "上下线状态:0->下线;1->上线")
    private Integer status;

    @Schema(description = "点击数")
    private Integer clickCount;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "专栏下SPU列表")
    List<ColumnSpuApiResponse> spuList;

}
