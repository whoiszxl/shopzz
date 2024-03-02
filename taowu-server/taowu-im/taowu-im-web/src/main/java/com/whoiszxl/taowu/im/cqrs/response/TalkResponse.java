package com.whoiszxl.taowu.im.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "对话框列表返回实体")
public class TalkResponse {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "对话类型: 1-单聊 2-群聊 3-ChatGPT 4-机器人")
    private Integer talkType;

    @Schema(description = "发起会话的用户ID")
    private Long fromMemberId;

    @Schema(description = "发起会话的用户信息")
    private String fromMemberInfo;

    @Schema(description = "接收会话的用户ID")
    private Long toMemberId;

    @Schema(description = "静音状态: 0-未静音 1-已静音")
    private Integer muteStatus;

    @Schema(description = "置顶状态: 0-未置顶 1-已置顶")
    private Integer topStatus;

    @Schema(description = "已读序列号，记录当前用户读到了哪里")
    private Long readSequence;

    @Schema(description = "序列号")
    private Long sequence;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

}
