package com.whoiszxl.taowu.starter.gpt;

import com.whoiszxl.taowu.starter.gpt.cqrs.response.ChatResponse;
import com.whoiszxl.taowu.starter.gpt.cqrs.response.CompletionsResponse;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.ChatCommand;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.CompletionsCommand;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * gpt 远程调用 api
 * @author whoiszxl
 */
public interface IGptApi {


    /**
     * Creates a completion for the provided prompt and parameters.
     * @see {@link <a href="https://platform.openai.com/docs/api-reference/completions/create">官方文档</a>}
     * @return CompletionsResponse
     */
    @POST("v1/completions")
    Single<CompletionsResponse> completions(@Body CompletionsCommand completionsCommand);



    @POST("v1/chat/completions")
    Single<ChatResponse> chat(@Body ChatCommand chatCommand);


}
