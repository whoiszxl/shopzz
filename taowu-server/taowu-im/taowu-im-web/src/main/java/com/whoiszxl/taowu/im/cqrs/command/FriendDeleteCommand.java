package com.whoiszxl.taowu.im.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 好友删除命令
 * @author whoiszxl
 */
@Data
@Schema(description = "好友删除命令")
public class FriendDeleteCommand {

    @Schema(description = "被删除好友的账号ID")
    @NotBlank(message = "被删除好友的账号ID不能为空")
    private String toMemberId;

}
