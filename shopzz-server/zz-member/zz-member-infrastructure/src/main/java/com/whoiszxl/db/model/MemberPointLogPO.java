package com.whoiszxl.db.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员积分变动日志表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
@Getter
@Setter
@TableName("ums_member_point_log")
@ApiModel(value = "MemberPointLog对象", description = "会员积分变动日志表")
public class MemberPointLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("会员积分ID")
    private Long memberPointId;

    @ApiModelProperty("变动前的会员积分")
    private Long beforePoint;

    @ApiModelProperty("变动的会员积分")
    private Long changePoint;

    @ApiModelProperty("变动后的会员积分")
    private Long afterPoint;

    @ApiModelProperty("本次积分变动的原因")
    private String updateReason;

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
