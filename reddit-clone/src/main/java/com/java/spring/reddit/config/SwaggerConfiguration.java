package com.java.spring.reddit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket redditCloneApi(@Value("${swagger.enable}") final boolean isSwaggerEnabled) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.java.spring.reddit.controller"))
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.regex("/api.*"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .enable(isSwaggerEnabled);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Reddit Clone",
                "Spring Boot Reddit Clone.",
                "1.0-SNAPSHOT",
                "http://localhost:8080/api/v1/termsofuse",
                new Contact("Reddit Clone", "http://localhost:8080/api/v1/contact", "info@reddit.clone.local"),
                "Copyright (c) 2020 Reddit Clone",
                "",
                Collections.EMPTY_LIST);
    }
}
