package com.whoiszxl.zhipin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员粉丝表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-25
 */
@Builder
@Data
@TableName("ums_member_follower")
@Schema(description = "会员粉丝表")
public class MemberFollower implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "粉丝ID")
    private Long followerId;

    @Schema(description = "是否取消关注: 0-未取消 1-取消")
    private Integer cancel;


}
