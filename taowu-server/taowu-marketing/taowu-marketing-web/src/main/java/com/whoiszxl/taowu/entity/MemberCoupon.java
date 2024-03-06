package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户领取优惠券表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_member_coupon")
@Schema(description = "用户领取优惠券表")
public class MemberCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "优惠券ID")
    private Long couponId;

    @Schema(description = "优惠券状态: 1-未使用 2-已使用 3-已过期")
    private Integer status;

    @Schema(description = "优惠券的领取时间")
    private LocalDateTime getTime;

    @Schema(description = "优惠券的使用时间")
    private LocalDateTime useTime;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
