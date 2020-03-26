package com.whoiszxl.order.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: swagger配置文档
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Configuration
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInfo()).select() // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.whoiszxl.order.controller")).paths(PathSelectors.any()).build();
    }


    private ApiInfo buildApiInfo() {
        Contact contact = new Contact("whoiszxl", "", "whoiszxl@gmail.com");
        return new ApiInfoBuilder().title("ZMALL - 电商订单模块文档").description("zmall order module desc").contact(contact).version("1.0.0").build();
    }


}
