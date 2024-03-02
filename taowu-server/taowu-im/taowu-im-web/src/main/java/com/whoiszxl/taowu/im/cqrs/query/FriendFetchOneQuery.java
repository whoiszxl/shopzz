package com.whoiszxl.taowu.im.cqrs.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 好友拉取查询条件
 * @author whoiszxl
 */
@Data
@Schema(description = "好友拉取查询条件")
public class FriendFetchOneQuery {

    @Schema(description = "好友账号的ID")
    @NotNull(message = "好友账号的ID不能为空")
    private Long toMemberId;

}
