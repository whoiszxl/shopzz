package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("spms_seckill")
@ApiModel(value = "Seckill对象", description = "秒杀表")
public class Seckill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("秒杀活动名称")
    private String name;

    @ApiModelProperty("秒杀活动描述")
    private String descs;

    @ApiModelProperty("秒杀活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("秒杀活动banner图")
    private String img;

    @ApiModelProperty("秒杀活动的状态,1:启用,2:停用")
    private Integer status;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
