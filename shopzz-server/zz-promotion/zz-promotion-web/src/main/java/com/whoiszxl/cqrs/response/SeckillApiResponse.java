package com.whoiszxl.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@ApiModel(value = "Seckill 返回对象", description = "秒杀表")
public class SeckillApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("秒杀活动名称")
    private String name;

    @ApiModelProperty("秒杀活动描述")
    private String descs;

    @ApiModelProperty("秒杀活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty("秒杀活动banner图")
    private String img;

    @ApiModelProperty("秒杀活动的状态,1:启用,2:停用")
    private Integer status;


}
