package com.whoiszxl.taowu.im.cqrs.command;

import com.whoiszxl.taowu.im.entity.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 好友添加命令
 * @author whoiszxl
 */
@Data
@Schema(description = "好友批量添加命令")
public class FriendAddCommand extends BaseRequest {

    @Schema(description = "自身的账号ID")
    private Long fromMemberId;

    @Schema(description = "好友ITEM信息")
    @NotNull(message = "好友ITEM信息不能为空")
    private AddFriendItem addFriendItem;

    @Data
    @Schema(description = "好友ITEM信息")
    public static class AddFriendItem {

        @Schema(description = "好友的账号ID")
        private Long toMemberId;

        @Schema(description = "好友的备注")
        private String remark;

        @Schema(description = "好友添加来源")
        private String source;

        @Schema(description = "扩展信息")
        private String extra;

        @Schema(description = "形成好友关系时的附言信息")
        private String addWording;
    }

}
