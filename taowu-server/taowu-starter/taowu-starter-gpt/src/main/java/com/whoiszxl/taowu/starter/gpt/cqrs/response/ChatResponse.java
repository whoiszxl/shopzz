package com.whoiszxl.taowu.starter.gpt.cqrs.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.whoiszxl.taowu.starter.gpt.cqrs.dto.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 聊天接口返回实体
 * @author whoiszxl
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "聊天接口返回实体")
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse implements Serializable {

    @Schema(description = "唯一标识符")
    private String id;

    @Schema(description = "对象类型，始终为 chat.completion")
    private String object;

    @Schema(description = "创建时间戳")
    private int created;

    @Schema(description = "用于聊天完成的模型")
    private String model;

    @Schema(description = "聊天完成的选择列表")
    private List<CompletionChoice> choices;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "用于记录选择在列表中的索引")
    public static class CompletionChoice {
        @Schema(description = "在选择列表中的索引")
        private int index;

        @Schema(description = "由模型生成的聊天完成消息")
        private Message message;

        @Schema(description = "调用的函数的名称和参数")
        private FunctionCall function_call;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "应调用的函数的名称和参数")
    public static class FunctionCall {
        @Schema(description = "要调用的函数名称")
        private String name;

        @Schema(description = "由模型生成的 JSON 格式参数")
        private String arguments;
    }

    @Schema(description = "模型停止生成标记的原因")
    private String finish_reason;

    @Schema(description = "完成请求的使用统计")
    private CompletionUsage usage;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "完成请求的使用统计")
    public static class CompletionUsage {
        @Schema(description = "提示中的标记数")
        private int prompt_tokens;

        @Schema(description = "生成的完成标记数")
        private int completion_tokens;

        @Schema(description = "请求中使用的总标记数（提示 + 完成）")
        private int total_tokens;
    }

}
