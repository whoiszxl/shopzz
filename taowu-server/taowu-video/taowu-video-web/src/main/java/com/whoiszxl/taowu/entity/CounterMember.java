package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户计数表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-29
 */
@Data
@TableName("base_counter_member")
@Schema(description = "用户计数表")
public class CounterMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "member_id", type = IdType.AUTO)
    private Integer memberId;

    @Schema(description = "获赞数")
    private Integer praisedCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "朋友数")
    private Integer friendCount;

    @Schema(description = "关注数")
    private Integer attentionCount;

    @Schema(description = "粉丝数")
    private Integer followerCount;


}
