package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
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
@Schema(description = "秒杀表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeckillUpdateCommand implements Serializable {

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "秒杀活动名称")
    private String name;

    @Schema(description = "秒杀活动描述")
    private String descs;

    @Schema(description = "秒杀活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "秒杀活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Schema(description = "秒杀活动banner图")
    private String img;

    @Schema(description = "秒杀活动的状态,1:启用,2:停用")
    private Integer status;


}
