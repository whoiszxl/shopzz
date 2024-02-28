package com.whoiszxl.taowu.starter.doc;

import com.whoiszxl.taowu.common.properties.TaowuProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whoiszxl
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "taowu.docs.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

    private final TaowuProperties taowuProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title(taowuProperties.getProjectName() + "接口文档")
                .version(taowuProperties.getVersion())
                .description(taowuProperties.getDescription())
                .contact(taowuProperties.getContact())
                .license(taowuProperties.getLicense())
        );
    }

}
