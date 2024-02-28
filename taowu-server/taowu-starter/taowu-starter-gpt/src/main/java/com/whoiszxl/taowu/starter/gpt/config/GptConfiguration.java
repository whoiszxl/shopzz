package com.whoiszxl.taowu.starter.gpt.config;

import com.whoiszxl.taowu.starter.gpt.session.impl.OpenAiGptSessionFactory;
import com.whoiszxl.taowu.starter.gpt.session.GptSession;
import com.whoiszxl.taowu.starter.gpt.session.GptSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gptSession configuration
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "taowu.gpt.enabled", havingValue = "true", matchIfMissing = true)
public class GptConfiguration {

    @Value("${taowu.gpt.key:fk186570-xdIUt1gh8kojgckv7pmRwOoIE9TL1Szt}")
    private String key;

    @Value("${taowu.gpt.endpoint:https://openai.api2d.net/}")
    private String endpoint;

    @Bean
    @ConditionalOnMissingBean
    public GptSession gptSession() {
        GptSessionFactory factory = new OpenAiGptSessionFactory(key, endpoint);
        return factory.openSession();
    }
}
