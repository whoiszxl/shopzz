package com.whoiszxl.taowu.im.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 好友请求审批命令
 * @author whoiszxl
 */
@Data
@Schema(description = "好友请求审批命令")
public class FriendRequestApproveCommand {

    @Schema(description = "好友请求审批记录的主键ID")
    @NotNull(message = "审批记录的主键ID不能为空")
    private Long id;

    @Schema(description = "审批状态: 1-通过 2-拒绝")
    @NotNull(message = "审批状态不能为空")
    private Integer status;
}
