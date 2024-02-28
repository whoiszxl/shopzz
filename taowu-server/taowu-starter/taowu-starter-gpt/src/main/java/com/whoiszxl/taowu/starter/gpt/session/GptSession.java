package com.whoiszxl.taowu.starter.gpt.session;

import com.whoiszxl.taowu.starter.gpt.cqrs.response.ChatResponse;
import com.whoiszxl.taowu.starter.gpt.cqrs.response.CompletionsResponse;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.ChatCommand;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.CompletionsCommand;

/**
 * gpt session 接口
 * @author whoiszxl
 */
public interface GptSession {

    CompletionsResponse completions(CompletionsCommand completionsCommand);

    CompletionsResponse completions(String prompt);

    ChatResponse chat(ChatCommand chatCommand);

}
