package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
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
@Schema(description = "促销活动表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivitySaveCommand implements Serializable {

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

    @Schema(description = "促销活动的状态,1:启用,2:停用")
    private Integer status;

}
