package com.whoiszxl.taowu.starter.gpt.cqrs.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whoiszxl.taowu.starter.gpt.cqrs.dto.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "聊天接口请求命令")
public class ChatCommand {

    @Builder.Default
    @Schema(description = "要使用的模型的ID。请参阅模型端点兼容性表，了解哪些模型适用于Chat API。")
    private String model = "gpt-3.5-turbo";

    @Schema(description = "由目前对话组成的消息列表。")
    private List<Message> messages;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "函数")
    public static class FunctionCall {

        @Schema(description = "函数的名称。")
        private String name;

        @Schema(description = "函数的参数。")
        private Map<String, Object> arguments;
    }

    @Schema(description = "模型可能为其生成JSON输入的函数列表。")
    private List<String> functions;

    @Schema(description = "控制模型如何响应函数调用。\"none\"表示模型不调用函数，响应给最终用户。\"auto\"表示模型可以选择最终用户或调用函数。通过{\"name\":\"my_function\"}指定特定函数会强制模型调用该函数。在没有函数的情况下，默认为\"none\"。如果存在函数，则默认为\"auto\"。")
    private String function_call;

    @Schema(description = "要使用的取样温度，介于0和2之间。较高的值（如0.8）会使输出更加随机，而较低的值（如0.2）会使其更加集中和确定性。")
    private Double temperature;

    @Schema(description = "取样的另一种方式是通过top_p概率质量来进行的，模型考虑具有top_p概率质量的令牌的结果。因此，0.1表示仅考虑组成前10%概率质量的令牌。")
    private Double top_p;

    @Schema(description = "为每个输入消息生成多少个聊天完成选项。")
    private Integer n;

    @Schema(description = "如果设置，将发送部分消息增量，类似于ChatGPT。令牌将作为数据-only服务器发送的事件，一旦可用，流将由数据：[DONE]消息终止。")
    private Boolean stream;

    @Schema(description = "最终的用户消息，数组或字符串，这些消息将停止进一步生成令牌。")
    private String[] stop;

    @Schema(description = "生成的聊天完成中生成的令牌的最大数量。")
    private Integer max_tokens;

    @Schema(description = "在文本中存在的令牌频率的惩罚因子，介于-2.0和2.0之间。正值会惩罚新令牌，根据它们是否在迄今为止的文本中出现，增加模型谈论新主题的可能性。")
    private Double presence_penalty;

    @Schema(description = "在迄今为止的文本中，基于其现有频率，对新令牌进行惩罚的因子，介于-2.0和2.0之间。正值会根据迄今为止的文本中的频率减少新令牌的重复率。")
    private Double frequency_penalty;

    @Schema(description = "修改生成完成中指定令牌出现的可能性。接受一个将令牌（通过标记器中的令牌ID指定）映射到从-100到100的关联偏差值的JSON对象。在数学上，在采样之前，模型生成的对数中添加了偏差。确切的影响会因模型而异，但-1到1之间的值应该减少或增加选择的可能性；像-100或100这样的值应该导致相关令牌的禁止或排他选择。")
    private Map<String, Double> logit_bias;

    @Schema(description = "代表您的最终用户的唯一标识符，可以帮助OpenAI监控和检测滥用。了解更多信息。")
    private String user;

}
