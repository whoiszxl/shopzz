package com.whoiszxl.taowu.cqrs.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 促销活动表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "促销活动表")
public class ActivityQuery extends PageQuery {

    @Query(blurry = "name")
    @Schema(description = "促销活动名称")
    private String name;

    @Query
    @Schema(description = "促销活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "促销活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Query
    @Schema(description = "促销活动的状态,1:启用,2:停用")
    private Integer status;
}
