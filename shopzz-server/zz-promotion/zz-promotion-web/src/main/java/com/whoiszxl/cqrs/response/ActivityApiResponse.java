package com.whoiszxl.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whoiszxl.cqrs.vo.CouponApiVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动api返回实体
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Data
@ApiModel("活动api返回实体")
public class ActivityApiResponse {

    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("促销活动名称")
    private String name;

    @ApiModelProperty("活动描述")
    private String descs;

    @ApiModelProperty("促销活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty("促销活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty("活动banner图")
    private String img;

    @ApiModelProperty("优惠券列表")
    private List<CouponApiVO> couponList;
}
