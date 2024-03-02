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
@TableName("im_group")
@Schema(description = "群组表")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "群主ID")
    private Long groupOwnerId;

    @Schema(description = "群组名称")
    private String groupName;

    @Schema(description = "群组类型")
    private Integer groupType;

    @Schema(description = "是否禁言: 1-不禁言 2-禁言")
    private Integer isMute;

    @Schema(description = "群组头像")
    private String portrait;

    @Schema(description = "当前群成员数")
    private Integer memberCount;

    @Schema(description = "最大群成员数")
    private Integer maxMemberCount;

    @Schema(description = "群公告")
    private String notice;

    @Schema(description = "加群审核类型: 1-任何人可加入 2-需要群主审批 3-禁止任何人加入")
    private Integer joinVerificationType;

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
