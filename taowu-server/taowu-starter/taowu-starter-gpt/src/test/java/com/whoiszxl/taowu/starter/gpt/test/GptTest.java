package com.whoiszxl.taowu.starter.gpt.test;

import com.whoiszxl.taowu.starter.gpt.cqrs.response.ChatResponse;
import com.whoiszxl.taowu.starter.gpt.cqrs.response.CompletionsResponse;
import com.whoiszxl.taowu.starter.gpt.session.GptSession;
import com.whoiszxl.taowu.starter.gpt.session.GptSessionFactory;
import com.whoiszxl.taowu.starter.gpt.session.impl.OpenAiGptSessionFactory;
import com.whoiszxl.taowu.starter.gpt.cqrs.dto.Message;
import com.whoiszxl.taowu.starter.gpt.cqrs.request.ChatCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * gpt 测试用例
 * @author whoiszxl
 */
@Slf4j
public class GptTest {

    private GptSession gptSession;

    @Before
    public void init() {
        GptSessionFactory factory = new OpenAiGptSessionFactory("fk186570-xdIUt1gh8kojgckv7pmRwOoIE9TL1Szt", "https://openai.api2d.net/");
        this.gptSession = factory.openSession();
    }

    @Test
    public void testSayHello() {
        CompletionsResponse response = gptSession.completions("Say this is a test");
        log.info("返回结果: {}", response);
    }

    @Test
    public void testChat() {
        ChatCommand command = ChatCommand.builder()
                .messages(new ArrayList<>())
                .user("whoiszxl")
                .max_tokens(50)
                .build();
        command.getMessages().add(Message.builder().role("user").content("姜维是谁").build());
        ChatResponse chatResponse = gptSession.chat(command);
        log.info("返回结果: {}", chatResponse);

        command.getMessages().add(Message.builder().role(chatResponse.getChoices().get(0).getMessage().getRole()).content(chatResponse.getChoices().get(0).getMessage().getContent()).build());
        command.getMessages().add(Message.builder().role("user").content("他和孔明什么关系").build());

        chatResponse = gptSession.chat(command);
        log.info("返回结果: {}", chatResponse);

    }



}
