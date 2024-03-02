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
 * 对话表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-18
 */
@Data
@TableName("im_talk")
@Schema(description = "对话表")
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "对话类型: 1-单聊 2-群聊 3-ChatGPT 4-机器人")
    private Integer talkType;

    @Schema(description = "发起会话的用户ID")
    private Long fromMemberId;

    @Schema(description = "发起会话的用户信息")
    private String fromMemberInfo;

    @Schema(description = "接收会话的用户ID")
    private Long toMemberId;

    @Schema(description = "静音状态: 0-未静音 1-已静音")
    private Integer muteStatus;

    @Schema(description = "置顶状态: 0-未置顶 1-已置顶")
    private Integer topStatus;

    @Schema(description = "已读序列号，记录当前用户读到了哪里")
    private Long readSequence;

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
