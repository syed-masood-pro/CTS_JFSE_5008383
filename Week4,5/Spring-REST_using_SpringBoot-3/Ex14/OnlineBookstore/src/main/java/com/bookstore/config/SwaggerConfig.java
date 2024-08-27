package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(swagger.model.api.ApiListing)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.bookstore.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Online Bookstore API",
                "API documentation for the Online Bookstore application.",
                "v1",
                "Terms of service",
                new Contact("Support", "www.example.com", "support@example.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
