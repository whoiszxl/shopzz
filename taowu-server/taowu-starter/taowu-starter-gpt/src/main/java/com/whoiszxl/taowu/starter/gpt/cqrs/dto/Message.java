package com.whoiszxl.taowu.starter.gpt.cqrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.ChatCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * chatgpt 对话消息体
 * @author whoiszxl
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "消息体")
public class Message {

    @Schema(description = "消息作者的角色。其中之一：system、user、assistant或function。")
    private String role;

    @Schema(description = "消息的内容。content对于所有消息都是必需的，对于带有函数调用的助手消息可能为空。")
    private String content;

    @Schema(description = "此消息作者的姓名。如果角色是function，则name是必需的，它应该是在内容中的函数的名称。可以包含a-z、A-Z、0-9和下划线，最大长度为64个字符。")
    private String name;

    @Schema(description = "应该调用的函数的名称和参数，由模型生成。")
    private ChatCommand.FunctionCall functionCall;
}