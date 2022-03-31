package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 专栏详情返回实体
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Data
public class ColumnDetailApiResponse {

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

    @ApiModelProperty("点击数")
    private Integer clickCount;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("专栏下SPU列表")
    List<ColumnSpuApiResponse> spuList;

}
