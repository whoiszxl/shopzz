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
 * 群组表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Data
@TableName("im_group_member")
@Schema(description = "群组表")
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "群组ID")
    private Long groupId;

    @Schema(description = "用户账号ID")
    private Long memberId;

    @Schema(description = "成员类型: 1-普通成员 2-群主")
    private Integer memberType;

    @Schema(description = "群昵称")
    private String alias;

    @Schema(description = "成员是否被禁言: 1-不禁言 2-禁言")
    private Integer isMute;

    @Schema(description = "加群方式: 1-扫码 2-群主邀请")
    private Integer joinType;

    @Schema(description = "创建时间")
    private LocalDateTime joinAt;

    @Schema(description = "扩展信息")
    private String extra;

    @Schema(description = "序列号")
    private Long sequence;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "群组状态 1-正常 2-解散")
    private Integer status;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
