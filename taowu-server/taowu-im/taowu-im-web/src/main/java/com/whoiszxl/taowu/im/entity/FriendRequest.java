package com.whoiszxl.taowu.im.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好友申请表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Data
@TableName("im_friend_request")
@Schema(description = "好友申请表")
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "发起好友申请的用户ID")
    private Long fromMemberId;

    @Schema(description = "被发起好友申请的用户ID")
    private Long toMemberId;

    @Schema(description = "好友申请备注")
    private String remark;

    @Schema(description = "好友申请来源")
    private String source;

    @Schema(description = "好友验证信息")
    private String friendVerification;

    @Schema(description = "序列号")
    private Long sequence;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "审批状态: 1-通过 2-拒绝")
    private Integer status;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
