package com.whoiszxl.taowu.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whoiszxl.taowu.cqrs.vo.CouponApiVO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "活动api返回实体")
public class ActivityApiResponse {

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "促销活动名称")
    private String name;

    @Schema(description = "活动描述")
    private String descs;

    @Schema(description = "促销活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "促销活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Schema(description = "活动banner图")
    private String img;

    @Schema(description = "优惠券列表")
    private List<CouponApiVO> couponList;
}
