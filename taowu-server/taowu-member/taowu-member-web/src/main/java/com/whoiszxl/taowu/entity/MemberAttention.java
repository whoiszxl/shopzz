package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员关注表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-25
 */
@Builder
@Data
@TableName("ums_member_attention")
@Schema(description = "会员关注表")
public class MemberAttention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "被关注者ID")
    private Long attentionId;

    @Schema(description = "是否取消关注: 0-未取消 1-取消")
    private Integer cancel;


}
