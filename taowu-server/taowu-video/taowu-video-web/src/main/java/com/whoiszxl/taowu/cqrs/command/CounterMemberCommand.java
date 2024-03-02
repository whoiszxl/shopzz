package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员计数命令
 * @author whoiszxl
 */
@Data
@Schema(description = "会员计数命令")
public class CounterMemberCommand {

    @Schema(description = "计数类型: 0-获赞数 1-点赞数 2-朋友数 3-关注数 4-粉丝数")
    private Integer type;

    @Schema(description = "运算类型: -1-减一 1-加一")
    private Long operator;

    @Schema(description = "用户ID")
    private Long memberId;
}
