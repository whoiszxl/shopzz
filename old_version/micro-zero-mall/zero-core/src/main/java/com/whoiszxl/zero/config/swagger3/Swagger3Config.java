package com.whoiszxl.zero.config.swagger3;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * swagger3配置
 *
 * @author whoiszxl
 */
@Configuration
public class Swagger3Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securitySchemes(
                        Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build()))

                .securityContexts(
                        Collections.singletonList(SecurityContext.builder()
                                .securityReferences(Collections.singletonList(SecurityReference.builder()
                                .scopes(new AuthorizationScope[0]).reference("Authorization").build()))
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zero-mall接口文档")
                .description("zero mall api docs.")
                .contact(new Contact("whoiszxl", "http://whoiszxl.com", "whoiszxl@gmail.com"))
                .version("0.0")
                .termsOfServiceUrl("http://zero.whoiszxl.com")
                .build();
    }
}
