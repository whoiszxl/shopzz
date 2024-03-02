package com.whoiszxl.taowu.im.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "校验私聊发送权限参数")
public class CheckPrivateChatPermissionQuery implements Serializable {

    @Schema(description = "发送用户id")
    private Long fromMemberId;

    @Schema(description = "接收用户id")
    private Long toMemberId;

}
