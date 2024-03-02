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
 * 好友表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Data
@TableName("im_friend")
@Schema(description = "好友表")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户自身的ID")
    private Long fromMemberId;

    @Schema(description = "用户好友的ID")
    private Long toMemberId;

    @Schema(description = "好友备注")
    private String remark;

    @Schema(description = "好友添加来源")
    private String source;

    @Schema(description = "好友序列号")
    private Long friendSequence;

    @Schema(description = "扩展信息")
    private String extra;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "业务状态: 1-正常 2-删除 3-黑名单")
    private Integer status;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
