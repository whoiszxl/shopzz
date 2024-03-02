package com.whoiszxl.taowu.im.cqrs.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 好友拉取查询条件
 * @author whoiszxl
 */
@Data
@Schema(description = "好友拉取查询条件")
public class FriendFetchQuery {

    @Schema(description = "自身的账号ID")
    @NotBlank(message = "自身的账号ID不能为空")
    private Long fromMemberId;

}
