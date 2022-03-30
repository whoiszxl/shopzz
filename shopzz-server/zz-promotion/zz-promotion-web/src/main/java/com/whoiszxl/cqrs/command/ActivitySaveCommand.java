package com.whoiszxl.cqrs.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@ApiModel(value = "Activity对象", description = "促销活动表")
public class ActivitySaveCommand implements Serializable {

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

    @ApiModelProperty("促销活动的状态,1:启用,2:停用")
    private Integer status;

}
