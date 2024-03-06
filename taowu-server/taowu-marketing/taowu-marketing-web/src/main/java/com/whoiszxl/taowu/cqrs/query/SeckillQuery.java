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
 * 秒杀表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "秒杀表")
public class SeckillQuery extends PageQuery {

    private static final long serialVersionUID = 1L;


    @Query(blurry = "name,descs")
    @Schema(description = "秒杀活动名称")
    private String name;

    @Schema(description = "秒杀活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "秒杀活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Query
    @Schema(description = "秒杀活动的状态,1:启用,2:停用")
    private Integer status;
}
