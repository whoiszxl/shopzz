package com.whoiszxl.taowu.starter.gpt.cqrs.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author whoiszxl
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "内容补全接口请求命令")
public class CompletionsCommand implements Serializable {

    @NotNull
    @Builder.Default
    @Schema(description = "模型ID")
    private String model = "text-davinci-003";

    @NotNull
    @Schema(description = "生成补全的提示，可以是字符串、字符串数组、令牌数组或令牌数组的数组")
    private String prompt;

    @Schema(description = "插入文本完成后的后缀")
    private String suffix;

    @JsonProperty("max_tokens")
    @Builder.Default
    @Schema(description = "生成的补全中的最大令牌数")
    private Integer maxTokens = 100;

    @Schema(description = "采样温度，介于 0 到 2 之间")
    private Double temperature;

    @JsonProperty("top_p")
    @Schema(description = "nucleus 采样的概率阈值，介于 0 到 1 之间")
    private Double topP;

    @Schema(description = "每个提示生成的补全数量")
    private Integer n = 1;

    @Schema(description = "是否以数据流的方式返回部分进展")
    private Boolean stream;

    @Schema(description = "包括最有可能的 log 概率值的标记数")
    private Integer logprobs;

    @Schema(description = "是否回显提示，除了补全外还包括原始提示")
    private Boolean echo;

    @Schema(description = "停止生成令牌的序列")
    private String[] stop;

    @JsonProperty("frequency_penalty")
    @Schema(description = "惩罚新令牌的频率，介于 -2.0 到 2.0 之间")
    private Double presencePenalty;

    @JsonProperty("presence_penalty")
    @Schema(description = "惩罚现有文本中已有频率的新令牌，介于 -2.0 到 2.0 之间")
    private Double frequencyPenalty;

    @JsonProperty("best_of")
    @Schema(description = "生成候选补全并返回最佳补全")
    private Integer bestOf = 1;

    @JsonProperty("logit_bias")
    @Schema(description = "修改指定令牌出现在补全中的可能性")
    private Map<String, Double> logitBias;

    @Schema(description = "用户唯一标识符，用于监测和检测滥用")
    private String user;


}
