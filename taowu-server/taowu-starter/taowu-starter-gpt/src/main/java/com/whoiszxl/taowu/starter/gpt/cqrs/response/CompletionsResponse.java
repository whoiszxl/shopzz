package com.whoiszxl.taowu.starter.gpt.cqrs.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * {
 *   "id": "cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7",
 *   "object": "text_completion",
 *   "created": 1589478378,
 *   "model": "text-davinci-003",
 *   "choices": [
 *     {
 *       "text": "\n\nThis is indeed a test",
 *       "index": 0,
 *       "logprobs": null,
 *       "finish_reason": "length"
 *     }
 *   ],
 *   "usage": {
 *     "prompt_tokens": 5,
 *     "completion_tokens": 7,
 *     "total_tokens": 12
 *   }
 * }
 * @author whoiszxl
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "内容补全接口返回实体")
public class CompletionsResponse implements Serializable {

    @Schema(description = "内容补全返回信息的唯一标识符")
    private String id;

    @Schema(description = "对象类型，始终为“text_completion")
    private String object;

    @Schema(description = "创建完成时的 Unix 时间戳。")
    private Long created;

    @Schema(description = "模型ID, text-ada-001, text-babbage-001, text-curie-001, text-davinci-002")
    private String model;

    @Schema(description = "选项")
    private Choice[] choices;

    @Schema(description = "使用统计")
    private Usage usage;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "选项")
    public static class Choice implements Serializable {

        @Schema(description = "返回的文本内容")
        private String text;

        @Schema(description = "数组索引")
        private long index;

        @Schema(description = "Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens. For example, if logprobs is 5, the API will return a list of the 5 most likely tokens. The API will always return the logprob of the sampled token, so there may be up to logprobs+1 elements in the response.")
        private Object logprobs;

        @Schema(description = "终止返回的原因")
        private String finish_reason;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(description = "使用统计")
    public static class Usage implements Serializable {
        @Schema(description = "prompt中的token数量")
        private long prompt_tokens;

        @Schema(description = "completion中的token数量")
        private long completion_tokens;

        @Schema(description = "总共的token数量")
        private long total_tokens;

        @Schema(description = "xx")
        private long pre_total;

        @Schema(description = "xx")
        private long adjust_total;

        @Schema(description = "xx")
        private long final_total;
    }

}
